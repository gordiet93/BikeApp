package com.example.projects.service;

import com.example.projects.data.StationRepository;
import com.example.projects.dto.StationDto;
import com.example.projects.model.Bike;
import com.example.projects.model.Country;
import com.example.projects.model.Journey;
import com.example.projects.model.Station;
import com.example.projects.util.Helper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class StationService {

    @Inject
    private Logger log;

    @Inject
    private StationRepository stationRepository;

    @Inject
    private BikeService bikeService;
    
    @Inject
    private JourneyService journeyService;

    public void checkArrivalsAndDepartures() {
        List<Station> updatedStations = loadStationsXml();

        checkForNewStations(updatedStations);

        for (Station updatedStation : updatedStations) {
            for (Bike updatedBike : updatedStation.getBikes()) {

                //Check for any arrivals
                if (Station.getBikesInTransit().containsKey(updatedBike.getBikeId())) {

                    Bike transitBike = Station.getBikesInTransit().get(updatedBike.getBikeId());
                    transitBike.setCurrentStation(updatedStation);

                    //if bike arrives at same station it departed form do not record
                    if (transitBike.getPreviousStation().getStationId().equals(updatedStation.getStationId())) {
                        log.info(transitBike + " arrived at " + transitBike.getCurrentStation() + " from "
                                + transitBike.getPreviousStation() + ". Not Recording journey as same start and end stations");
                    } else {
                        recordJourney(transitBike);
                    }

                    bikeService.merge(transitBike);
                    Station.getBikesInTransit().remove(transitBike.getBikeId());

                } else {
                    //get bike from database
                    Bike loadedBike = bikeService.findById(updatedBike.getBikeId());

                    //register bike if bike does not exist in the database
                    if (loadedBike == null) {

                        updatedBike.setTracked(true);
                        updatedBike.setCurrentStation(updatedStation);
                        bikeService.register(updatedBike);

                        //bike exists, update it's current station if need be
                    } else if (!loadedBike.isTracked()){
                        loadedBike.setTracked(true);
                        loadedBike.setCurrentStation(updatedStation);
                        bikeService.merge(loadedBike);
                        log.info(updatedBike + " arrived at " + updatedStation + ". Was not tracked");
                    }
                }
            }

            //Check Departures
            Station loadedStation = findById(updatedStation.getStationId());
            for (Bike loadedBike : loadedStation.getBikes()) {
                if (!updatedStation.getBikes().contains(loadedBike) && loadedBike.isTracked()) {

                    departBike(loadedBike, loadedStation, updatedStation);
                }
            }
        }

        log.info("Bikes in transit: " + Station.getBikesInTransit().size());
    }

    private void departBike(Bike bike, Station station, Station updatedStation) {
        bike.setCurrentStation(findById(0L));
        bike.setPreviousStation(station);
        bike.setDepartureTime(System.currentTimeMillis());

        String info = "";

        /*
        if (updatedStation.getBikes().size() == 5) {
            info = "Note: " + bike + " may still be at station but out of top 5";
            bike.setDepartedOutsideTopFive(true);
        }*/

        Station.getBikesInTransit().put(bike.getBikeId(), bike);
        // em.merge(bike);
        log.info(bike + " departed " + station + ". " + info);
    }


    private void recordJourney(Bike transitBike) {
        Journey journey = new Journey(transitBike, transitBike.getPreviousStation(),
                transitBike.getCurrentStation(),
                Helper.calDurationInMinutes(System.currentTimeMillis() - transitBike.getDepartureTime()),
                new Date(), transitBike.isDepartedOutsideTopFive());

        journeyService.recordJourney(journey);
    }

    public void registerStations() {
        bikeService.setAllBikeTrackedToFalse();

        List<Station> stations = loadStationsXml();
        Station transit = new Station(0L,"Transit");
        stations.add(transit);

        for (Station station : stations) {
            Station loadedStation = findById(station.getStationId());
            //If station does not exist yet, register it
            if (loadedStation == null) {
                register(station);
            }
            bikeService.updateOrRegisterBikes(station);
        }
    }

    private void checkForNewStations(List<Station> stations) {
        for (Station station : stations) {
            Station loadedStation = findById(station.getStationId());
            //If station does not exist yet, register it
            if (loadedStation == null) {
                register(station);
            }
        }
    }

    private List<Station> loadStationsXml() {
        List<Station> stations = new ArrayList<>();
        try {
            List<Country> countries = Helper.getData().getCountries();

            for(Country country : countries) {
                if (country.getCity() != null) {
                    stations.addAll(country.getCity().getStations());
                }
            }
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
        return stations;
    }

    public Station findById(Long stationId) {
        return stationRepository.findById(stationId);
    }

    public StationDto findByIdDto(Long stationId) {
        Station station = stationRepository.findById(stationId);
        return new StationDto(station.getStationId(), station.getStationName(), station.getTotalDepartures(),
                station.getTotalArrivals(), getBikeIds(station));
    }

    private void register(Station station) {
        log.info("Registering " + station);
        stationRepository.register(station);
    }

    private List<Station> findAllOrderedByName() {
        return stationRepository.findAllOrderedByName();
    }

    public List<StationDto> findAllOrderedByNameDto() {
        List<StationDto> stationDtos = new ArrayList<>();
        List<Station> stations = findAllOrderedByName();
        for (Station station : stations) {
            stationDtos.add(new StationDto(station.getStationId(), station.getStationName(),
                    station.getTotalDepartures(), station.getTotalArrivals(), getBikeIds(station)));
        }
        return stationDtos;
    }

    private List<Long> getBikeIds(Station station) {
        List<Long> bikes = new ArrayList<>();
        for (Bike bike : station.getBikes()) {
            bikes.add(bike.getBikeId());
        }
        return bikes;
    }
}

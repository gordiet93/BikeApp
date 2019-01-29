package com.example.projects.service;

import com.example.projects.data.StationRepository;
import com.example.projects.dto.StationDto;
import com.example.projects.model.*;
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
        List<Bike> transitBikes = bikeService.findByStatus(BikeStatus.TRANSIT);
        List<Bike> dockedBikes = bikeService.findByStatus(BikeStatus.DOCKED);
        List<Bike> unknownBikes = bikeService.findByStatus(BikeStatus.UNKNOWN);
        List<Station> updatedStations = loadStationsXml();

        checkForNewStations(updatedStations);

        checkArrivals(updatedStations, transitBikes, unknownBikes, dockedBikes);
        checkDepartures(dockedBikes);
    }

    private void checkArrivals(List<Station> updatedStations, List<Bike> transitBikes, List<Bike> unknownBikes,
                              List<Bike> dockedBikes) {
        for (Station updatedStation : updatedStations) {
            for (Bike updatedBike : updatedStation.getBikes()) {
                if (transitBikes.contains(updatedBike)) {
                    recordJourney(updatedBike);
                } else if (!dockedBikes.contains(updatedBike) && !unknownBikes.contains(updatedBike)) {
                    bikeService.register(updatedBike);
                    continue;
                }
                bikeService.merge(updatedBike);
            }
        }
    }

    private void checkDepartures(List<Bike> dockedBikes) {
        List<Long> bikeIds = Helper.getDataBikeIds();

        for (Bike bike : dockedBikes) {
            if (!bikeIds.contains(bike.getBikeId())) {
                departBike(bike);
            }
        }
    }

    private void departBike(Bike bike) {
        bike.setPreviousStation(bike.getCurrentStation());
        bike.setStatus(BikeStatus.TRANSIT);
        bike.setDepartureTime(System.currentTimeMillis());

        bikeService.merge(bike);

        log.info(bike + " departed " + bike.getCurrentStation());
    }


    private void recordJourney(Bike bike) {
        Journey journey = new Journey(bike, bike.getPreviousStation(),
                bike.getCurrentStation(),
                Helper.calDurationInMinutes(System.currentTimeMillis() - bike.getDepartureTime()),
                new Date(), bike.isDepartedOutsideTopFive());

        journeyService.recordJourney(journey);
    }

    public void registerStations() {
        bikeService.setAllStatusToUnknown();

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

                    for(Station station : stations) {
                        for (Bike bike : station.getBikes()) {
                            bike.setCurrentStation(station);
                        }
                    }
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

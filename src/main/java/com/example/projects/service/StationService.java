package com.example.projects.service;

import com.example.projects.data.DepartureRepository;
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
import java.util.Map;
import java.util.logging.Logger;

@Stateless
public class StationService {

    @Inject
    private Logger log;

    @Inject
    private StationRepository stationRepository;

    @Inject
    private DepartureRepository departureRepository;

    @Inject
    private BikeService bikeService;
    
    @Inject
    private JourneyService journeyService;

    public void checkArrivalsAndDepartures() {
        List<Bike> dockedBikes = bikeService.findByStatus(BikeStatus.DOCKED);
        Map<Long, Long> updatedBikes = bikeService.loadBikeDataFromXml();

        checkArrivals(updatedBikes);
        checkDepartures(updatedBikes, dockedBikes);
    }

    private void checkArrivals(Map<Long, Long> updatedBikes) {
        for (Map.Entry<Long, Long> updatedBike : updatedBikes.entrySet()) {

            Bike loadedBike = bikeService.findById(updatedBike.getKey());
            Station currentStation = stationRepository.findByIdRef(updatedBike.getValue());

            if (loadedBike == null) {
                Bike bike = new Bike(updatedBike.getKey(), currentStation);
                bikeService.register(bike);
                continue;
            }

            BikeStatus bikeStatus = loadedBike.getStatus();

            switch (bikeStatus) {
                case TRANSIT:
                    Departure departure = departureRepository.getByBike(loadedBike.getBikeId());
                    if (departure != null) {
                        loadedBike.setCurrentStation(currentStation);
                        recordJourney(loadedBike, departure);
                        departureRepository.deleteDeparture(departure);
                    }
                    break;
                case DOCKED:
                    if (loadedBike.getCurrentStation() != currentStation
                            && currentStation != null) {
                        loadedBike.setPreviousStation(loadedBike.getCurrentStation());
                        loadedBike.setCurrentStation(currentStation);
                    }
                    break;
                case UNKNOWN:
                    loadedBike.setCurrentStation(currentStation);
                    break;
                default:
                    System.out.println("Invalid bikeStatus " + bikeStatus);
            }

            loadedBike.setStatus(BikeStatus.DOCKED);
            //bikeService.merge(loadedBike);
        }
    }

    private void checkDepartures(Map<Long, Long> updatedBikes, List<Bike> dockedBikes) {
        for (Bike bike : dockedBikes) {
            if (!updatedBikes.containsKey(bike.getBikeId())) {
                departBike(bike);
            }
        }
    }

    private void departBike(Bike bike) {
        bike.setPreviousStation(bike.getCurrentStation());
        bike.setStatus(BikeStatus.TRANSIT);

        departureRepository.addDeparture(new Departure(bike, bike.getCurrentStation(), new Date()));
        log.info(bike + " departed " + bike.getCurrentStation());

        bike.setCurrentStation(null);
        bikeService.merge(bike);
    }

    private void recordJourney(Bike bike, Departure departure) {
        Journey journey = new Journey(bike, departure.getDepartureStation(), bike.getCurrentStation(),
                Helper.calDurationInMinutes(System.currentTimeMillis() - departure.getDepartureTime().getTime()),
                departure.getDepartureTime(), new Date(), bike.isDepartedOutsideTopFive());

        journeyService.recordJourney(journey);
    }

    public void registerStationsAndUpdateBikeLocations() {
        bikeService.setAllStatusToUnknown();
        bikeService.updateOrRegisterBikes(registerNewStationsAndLoadBikesFromXml());
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

    private List<Bike> registerNewStationsAndLoadBikesFromXml() {
        List<Station> stations = new ArrayList<>();
        List<Bike> bikes = new ArrayList<>();
        try {
            List<Country> countries = Helper.getData().getCountries();

            for(Country country : countries) {
                if (country.getCity() != null) {
                    stations.addAll(country.getCity().getStations());

                    for (Station station : stations) {
                        for (Bike bike : station.getBikes()) {
                            bike.setCurrentStation(station);
                        }

                        bikes.addAll(station.getBikes());
                    }
                }
            }

            checkForNewStations(stations);

        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
        return bikes;
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

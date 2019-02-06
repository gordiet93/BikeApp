package com.example.projects.service;

import com.example.projects.data.BikeRepository;
import com.example.projects.dto.BikeDto;
import com.example.projects.model.*;
import com.example.projects.util.Helper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by GTaggart on 01/03/2018.
 */
@Stateless
public class BikeService {

    @Inject
    private Logger log;

    @Inject
    private BikeRepository bikeRepository;

    public Bike findById(Long id) {
        //log.info("Finding bike " + id);
        return bikeRepository.findById(id);
    }

    public Bike findByIdRef(Long id) {
        return bikeRepository.findByIdRef(id);
    }

    public void register(Bike bike) {
        log.info("Registering " + bike + " at " + bike.getCurrentStation());
        bike.setStatus(BikeStatus.DOCKED);
        bikeRepository.register(bike);
    }

    public void merge(Bike bike) {
        bikeRepository.merge(bike);
    }

    public List<Bike> findAllOrderedByStationName() {
        return bikeRepository.findAllOrderedByStationName();
    }

    public List<Bike> findByStatus(BikeStatus status) {
        return bikeRepository.findByStatus(status);
    }

    public BikeDto findByIdDto(Long id) {
        Bike bike = findById(id);
        return createBikeDto(bike);
    }

    public List<BikeDto> findAllOrderedByStationNameDto() {
        List<BikeDto> bikeDtos = new ArrayList<>();
        List<Bike> bikes = findAllOrderedByStationName();
        for (Bike bike : bikes) {
            bikeDtos.add(createBikeDto(bike));
        }
        return bikeDtos;
    }

    private BikeDto createBikeDto(Bike bike) {
        String previousStationName;
        if (bike.getPreviousStation() == null) {
            previousStationName = "N/A";
        } else {
            previousStationName = bike.getPreviousStation().getStationName();
        }
        return new BikeDto(bike.getBikeId(), getJourneyIds(bike), bike.getCurrentStation().getStationName(), previousStationName);
    }

    private List<Long> getJourneyIds(Bike bike) {
        List<Long> journeys = new ArrayList<>();
        for (Journey journey : bike.getJourneys()) {
            journeys.add(journey.getJourneyId());
        }
        return journeys;
    }

    public void updateOrRegisterBikes(List<Bike> bikes) {
        for (Bike bike : bikes) {
            Bike loadedBike = findById(bike.getBikeId());
            //Bike does not exist yet, register it
            if (loadedBike == null) {
                register(bike);
            } else {
                merge(bike);
            }
        }
    }

    public void setAllStatusToUnknown() {
        bikeRepository.setAllStatusToUnknown();
    }

    public Map<Long , Long> loadBikeDataFromXml() {
        Map<Long, Long> bikeData = new HashMap<>();
        try {
            for(Country country : Helper.getData().getCountries()) {
                if (country.getCity() != null) {
                    for (Station station : country.getCity().getStations()) {
                        for (Bike bike : station.getBikes()) {
                            bikeData.put(bike.getBikeId(), station.getStationId());
                        }
                    }
                }
            }
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
        return bikeData;
    }
}

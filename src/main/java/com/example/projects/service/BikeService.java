package com.example.projects.service;

import com.example.projects.data.BikeRepository;
import com.example.projects.dto.BikeDto;
import com.example.projects.model.Bike;
import com.example.projects.model.Journey;
import com.example.projects.model.Station;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
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

    public void register(Bike bike) {
        log.info("Registering " + bike + " at " + bike.getCurrentStation());
        bikeRepository.register(bike);
    }

    public void merge(Bike bike) {
        bikeRepository.merge(bike);
    }

    public List<Bike> findAllOrderedByStationName() {
        return bikeRepository.findAllOrderedByStationName();
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

    public void updateOrRegisterBikes(Station station) {
        for (Bike bike : station.getBikes()) {
            Bike loadedBike = findById(bike.getBikeId());
            //Bike does not exist yet, register it
            if (loadedBike == null) {
                bike.setCurrentStation(station);
                bike.setTracked(true);
                register(bike);
            } else {
                loadedBike.setTracked(true);
                loadedBike.setCurrentStation(station);
            }
        }
    }

    public void setAllBikeTrackedToFalse() {
       // bikeRepository.setAllBikeTrackedToFalse();
    };
}

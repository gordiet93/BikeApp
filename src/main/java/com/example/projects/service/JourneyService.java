package com.example.projects.service;

import com.example.projects.data.JourneyRepository;
import com.example.projects.dto.JourneyDto;
import com.example.projects.model.Journey;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class JourneyService {

    @Inject
    private JourneyRepository journeyRepository;

    @Inject
    private Logger log;

    public Journey findById(long id) {
        return journeyRepository.findById(id);
    }

    public void recordJourney(Journey journey) {
        log.info("Recording journey with " + journey.getBike() + " " + journey.getStartStation()
                + " to " + journey.getEndStation());
        journeyRepository.recordJourney(journey);
    }

    public List<Journey> findAllOrderedById() {
        return journeyRepository.findAllOrderedById();
    }

    public JourneyDto findByIdDto(long id) {
        Journey journey = findById(id);
        return new JourneyDto(journey.getId(), journey.getBike().getId(),
                journey.getStartStation().getId(), journey.getEndStation().getId(),
                journey.getStartStation().getStationName(), journey.getEndStation().getStationName(),
                journey.getDuration(), journey.getArrivalTime(), journey.isOutsideTopFive());
    }

    public List<JourneyDto> findAllOrderedByIdDto() {
        return modelToDto(findAllOrderedById());
    }

    public List<JourneyDto> findByRouteDto(long startStation, long endStation) {
        return modelToDto(journeyRepository.findByRoute(startStation, endStation));
    }

    public void deleteJourney(long id) {
        journeyRepository.deleteJourney(id);
    }

    public List<JourneyDto> findByDuration(int duration, boolean greaterThan) {
        return modelToDto(journeyRepository.findByDuration(duration, greaterThan));
    }

    public List<JourneyDto> findBetweenDuration(int low, int high) {
        return modelToDto(journeyRepository.findBetweenDuration(low, high));
    }

    public List<JourneyDto> findBetweenDate(long start, long end) {
        return modelToDto(journeyRepository.findBetweenDate(start, end));
    }

    public List<JourneyDto> findByBike(long id) {
        return modelToDto(journeyRepository.findByBike(id));
    }

    private List<JourneyDto> modelToDto(List<Journey> journeys) {
        List<JourneyDto> journeyDtos = new ArrayList<>();
        for (Journey journey : journeys) {
            journeyDtos.add(new JourneyDto(journey.getId(), journey.getBike().getId(),
                    journey.getStartStation().getId(), journey.getEndStation().getId(),
                    journey.getStartStation().getStationName(), journey.getEndStation().getStationName(),
                    journey.getDuration(), journey.getArrivalTime(), journey.isOutsideTopFive()));
        }
        return journeyDtos;
    }
}

package com.example.projects.rest;

import com.example.projects.dto.JourneyDto;
import com.example.projects.model.Journey;
import com.example.projects.service.BikeService;
import com.example.projects.service.JourneyService;
import com.example.projects.service.StationService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Path("/journeys")
@RequestScoped
public class JourneyRestService {

    @Inject
    private JourneyService journeyService;

    @Inject
    private StationService stationService;

    @Inject
    private BikeService bikeService;

    @Inject
    private Logger log;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<JourneyDto> listAll() {
        return journeyService.findAllOrderedByIdDto();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JourneyDto find(@PathParam("id") long id) {
        return journeyService.findByIdDto(id);
    }

    @GET
    @Path("/route")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<JourneyDto> findByRoute(
            @Min(0)
            @QueryParam("start")
                    long startStation,
            @Min(0)
            @QueryParam("end")
                    long endStation) {
        return journeyService.findByRouteDto(startStation, endStation);
    }

    @GET
    @Path("/duration")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<JourneyDto> findByDuration(@QueryParam("value") int duration,
                                           @QueryParam("greaterthan") boolean greaterThan) {
        return journeyService.findByDuration(duration, greaterThan);
    }

    @GET
    @Path("/betweenduration")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<JourneyDto> findBetweenDuration(@QueryParam("low") int low, @QueryParam("high") int high) {
        return journeyService.findBetweenDuration(low, high);
    }

    @GET
    @Path("/betweendate")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<JourneyDto> findBetweenDate(@QueryParam("start") long start, @QueryParam("end") long end) {
        return journeyService.findBetweenDate(start, end);
    }

//    @POST
//    @Path("/add")
//    @Consumes({MediaType.APPLICATION_JSON})
//    public void addJourney(JourneyDto journeyDto) {
//        Journey journey = new Journey(bikeService.findById(journeyDto.getId()),
//                stationService.findById(journeyDto.getStartStationId()), stationService.findById(journeyDto.getEndStationId()),
//                journeyDto.getDuration(), new Date(), false);
//
//        journeyService.recordJourney(journey);
//        log.info("Adding journey via REST POST");
//    }

    @POST
    @Path("/test")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public JourneyDto test(JourneyDto journeyDto){

        return journeyDto;
    }

    @DELETE
    @Path("/{id}")
    public void deleteJourney(@PathParam("id") long id) {
        journeyService.deleteJourney(id);
    }
}

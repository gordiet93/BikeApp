package com.example.projects.rest;

import com.example.projects.dto.BikeDto;
import com.example.projects.dto.JourneyDto;
import com.example.projects.service.BikeService;
import com.example.projects.service.JourneyService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/bikes")
@RequestScoped
public class BikeRestService implements RestServiceInterface {

    @Inject
    private BikeService bikeService;

    @Inject
    private JourneyService journeyService;

    @Override
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<BikeDto> listAll() {
        return bikeService.findAllOrderedByStationNameDto();
    }

    @Override
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public BikeDto findById(@PathParam("id") Long id) {
        return bikeService.findByIdDto(id);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public BikeDto test(@Valid BikeDto bikeDto) {
        return bikeDto;
    }

    @GET
    @Path("/{id}/journeys")
    @Produces({MediaType.APPLICATION_JSON})
    public List<JourneyDto> getBikeJourneys(@PathParam("id") Long id) {
        return journeyService.findByBike(id);
    }

    @GET
    @Path("/{id}/journeys/{journeyid}")
    @Produces({MediaType.APPLICATION_JSON})
    public JourneyDto getBikeJourney(@PathParam("id") Long id, @PathParam("journeyid") Long journeyId) {
        return journeyService.findByIdDto(journeyId);
    }
}

package com.example.projects.rest;

import com.example.projects.dto.BikeDto;
import com.example.projects.service.BikeService;

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

    @Override
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
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
    @Path("/add")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public BikeDto test(@Valid BikeDto bikeDto) {
        return bikeDto;
    }

    @GET
    @Path("/yes")
    @Produces({MediaType.APPLICATION_JSON})
    public String yes() {
        return "Yes";
    }
}

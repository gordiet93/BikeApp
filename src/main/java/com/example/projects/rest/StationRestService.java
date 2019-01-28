package com.example.projects.rest;

import com.example.projects.dto.StationDto;
import com.example.projects.service.StationService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/stations")
@RequestScoped
public class StationRestService implements  RestServiceInterface {

    @Inject
    private StationService stationService;

    @Override
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<StationDto> listAll() { return stationService.findAllOrderedByNameDto(); }

    @Override
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public StationDto findById(@PathParam("id") Long id) {
        return stationService.findByIdDto(id);
    }
}

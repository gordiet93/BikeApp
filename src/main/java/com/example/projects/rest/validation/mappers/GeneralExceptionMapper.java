package com.example.projects.rest.validation.mappers;

import com.example.projects.rest.validation.ValidationError;

import javax.ejb.Singleton;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Singleton
@Provider
public class GeneralExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {

        if (exception instanceof WebApplicationException) {
            return ((WebApplicationException) exception).getResponse();
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(
                new ValidationError(exception.getClass().getName(), exception.getMessage())
        ).type(MediaType.APPLICATION_JSON).build();
    }
}

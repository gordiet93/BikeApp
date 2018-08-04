package com.example.projects.rest;


import javax.ejb.Singleton;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
@Provider
public class ConstraintViolationMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {

       List<ValidaitonError> errors = exception.getConstraintViolations().stream()
               .map(this::toValidationError)
               .collect(Collectors.toList());

       return Response.status(Response.Status.BAD_REQUEST).entity(errors)
               .type(MediaType.APPLICATION_JSON).build();
    }

    private ValidaitonError toValidationError(ConstraintViolation constraintViolation) {
        ValidaitonError error = new ValidaitonError();
        error.setPath(constraintViolation.getPropertyPath().toString());
        error.setMessgage(constraintViolation.getMessage());
        return error;
    }
}

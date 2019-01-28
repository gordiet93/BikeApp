package com.example.projects.rest.validation.mappers;

import com.example.projects.rest.validation.ValidationError;

import javax.ejb.Singleton;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Singleton
@Provider
public class ConstraintViolationMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {

        List<ValidationError> errors = exception.getConstraintViolations().stream()
                .map(this::toValidationError)
                .collect(Collectors.toList());

        return Response.status(Response.Status.BAD_REQUEST).entity(errors)
                .type(MediaType.APPLICATION_JSON).build();
    }

    private ValidationError toValidationError(ConstraintViolation constraintViolation) {
        Path.Node leafNode = getLeafNode(constraintViolation.getPropertyPath()).get();
        return new ValidationError("Constraint Violation",
                leafNode.getName() + " : " + constraintViolation.getMessage());
    }

    private Optional<Path.Node> getLeafNode(Path path) {
        return StreamSupport.stream(path.spliterator(), false).reduce((a, b) -> b);
    }
}

package com.aba.rover.boundary.v1.exception;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Catch constraint violation exceptions thrown BEFORE Endpoints are reached.
 *
 * @author cnadal
 */
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private static final Logger LOGGER = Logger.getLogger(ConstraintViolationExceptionMapper.class.getCanonicalName());

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        String errorMsg = exception.getMessage();
        if ((Objects.isNull(errorMsg) || errorMsg.length() == 0)
                && Objects.nonNull(exception.getCause())) {
            errorMsg = exception.getCause().getMessage();
        }
        LOGGER.log(Level.SEVERE, "ConstraintViolationException caused by {0}", errorMsg);
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}

package com.aba.rover.boundary.v1.exception;

import java.rmi.UnexpectedException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Intercepts and handles exceptions thrown without control. When an unchecked
 * exception is found, a well known UnexpectedException is thrown.
 *
 * @see ExceptionHandled
 */
@ExceptionHandled
@Interceptor
public class ExceptionInterceptor {

    private static final Logger LOGGER = Logger.getLogger(ExceptionInterceptor.class.getCanonicalName());

    @AroundInvoke
    public Object handlerMethod(InvocationContext ctx) throws UnexpectedException {
        Object returnValue = null;
        try {
            returnValue = ctx.proceed();
        } catch (WebApplicationException ex) {
            throw ex;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Something went wrong", ex);
            throw new ServerErrorException(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return returnValue;
    }
}

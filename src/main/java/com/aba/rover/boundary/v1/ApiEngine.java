package com.aba.rover.boundary.v1;

import com.aba.rover.boundary.v1.exception.ExceptionHandled;
import com.aba.rover.boundary.v1.model.MovementRequest;
import com.aba.rover.control.Direction;
import com.aba.rover.control.Engine;
import com.aba.rover.control.exception.EngineException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/engine")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ApiEngine {

    private static final Logger LOGGER = Logger.getLogger(ApiEngine.class.getName());

    @Inject
    Engine engine;

    @GET
    @Path("/move")
    @ExceptionHandled
    public Direction getDirection() {

        return this.engine.getCurrentDirection();
    }

    @POST
    @Path("/move")
    public void move(@NotNull @Valid MovementRequest request) {

        try {
            this.engine.moveDirection(request.getDirection());
        } catch (EngineException ex) {
            LOGGER.log(Level.SEVERE, "Something went wrong performing the movement", ex);
            throw new ServerErrorException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/battery/remaining")
    public String getRemainingBatteryCapacity() {

        Float capacity = this.engine.getRemainingBatteryCapacity();
        return new StringBuilder(capacity.toString()).append(" %").toString();
    }

    @POST
    @Path("/battery/charge")
    public void charge() {

        try {
            this.engine.chargeBatteries();
        } catch (EngineException ex) {
            LOGGER.log(Level.SEVERE, "Something went wrong charging the batteries", ex);
            throw new ServerErrorException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}

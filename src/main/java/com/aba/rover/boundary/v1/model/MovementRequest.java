package com.aba.rover.boundary.v1.model;

import com.aba.rover.control.Direction;
import javax.validation.constraints.NotNull;

public class MovementRequest {

    @NotNull
    private Direction direction;

    public MovementRequest() {
        super();
    }

    public MovementRequest(Direction direction) {
        this();
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

}

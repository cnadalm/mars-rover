package com.aba.rover.control;

import com.aba.rover.control.exception.EngineException;

public interface Engine {
    
    public Direction getCurrentDirection();
    
    public void moveDirection(Direction direction) throws EngineException;
    
    public Float getRemainingBatteryCapacity();
    
    public void chargeBatteries() throws EngineException;

}

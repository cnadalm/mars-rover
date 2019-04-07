package com.aba.rover.control;

import com.aba.rover.control.exception.EngineException;
import javax.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

//@QuarkusTest
public class EngineTest {

    @Inject
    Engine engine;

    @Disabled
    @Test
    public void testGetCurrentDirection() {

        Assertions.assertEquals(Direction.NONE, engine.getCurrentDirection());
    }

    @Disabled
    @Test
    public void shouldChargeBatteries() throws EngineException {

        // given
        Assertions.assertTrue(0F == engine.getRemainingBatteryCapacity());;

        // when
        engine.chargeBatteries();

        // then
        Assertions.assertTrue(100F == engine.getRemainingBatteryCapacity());
    }

}

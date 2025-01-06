package github.engine.legacy;

import io.github.engine.Display;
import io.github.engine.Entity;
import io.github.engine.Physics;
import io.github.engine.Synchronization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PhysicsTest {

    @Test
    public void testCalculateUARM(){
        //Xo = 10
        //Vo = 5
        //a = 2
        //t = 5

        // 10 + (5 * 5) + (2/2)*(5)^2
        // 10 + (25) + (1) * 25
        // 10 + 25 + 25 = 60



        assertEquals(60,Physics.calculateUARM(10, 5,2,5));
    }

    @Test
    public void testDeltaTimeBasedOnMovement(){
        Assertions.assertEquals(6,Math.round(Physics.deltaTimeBasedOnMovement(0,50,2,2)) );
    }
}

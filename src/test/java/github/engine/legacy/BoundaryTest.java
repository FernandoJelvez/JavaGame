package github.engine.legacy;

import io.github.engine.Boundary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoundaryTest {

    @Test
    public void testGetMaxY(){
        Boundary boundary = new Boundary(20,20,20,20);
        Assertions.assertEquals(40,boundary.getMaxY());
    }

    @Test
    public void getMaxX(){
        Boundary boundary = new Boundary(20,20,20,20);
        Assertions.assertEquals(40,boundary.getMaxX());

    }
}

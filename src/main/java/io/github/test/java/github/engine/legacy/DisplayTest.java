package github.engine.legacy;

import io.github.engine.Boundary;
import io.github.engine.Display;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DisplayTest {

    @Test
    public void getDisplayBounds(){
        Display.setup("ventana",800,600);
        Display.start();
        Assertions.assertEquals(0, Display.getDisplayBounds().getUnitX());
    }
}

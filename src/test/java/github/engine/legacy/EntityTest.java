package github.engine.legacy;

import io.github.engine.Display;
import io.github.engine.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EntityTest {
    @Test
    public void testGetGravity(){
        Display.setup("ventana",800,600);
        Display.start();

        Entity entity = new Entity(0,0,20,20,true,1) {
            @Override
            protected void refresh() {

            }
        };
        Assertions.assertEquals(0,entity.getGravity());
    }
}

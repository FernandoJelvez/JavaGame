package github.engine.legacy;

import io.github.engine.AbstractTile;
import io.github.engine.Display;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AbstractTileTest {

    @BeforeAll
    public static void beforeAll(){
        Display.setup("ventana",800,600);
    }


    @Test
    public void testIsSolid(){
        AbstractTile abstractTile = new AbstractTile(20,20,true,3) {
            @Override
            protected void refresh() {

            }
        };
        Assertions.assertEquals(true,abstractTile.isSolid());
    }

    @Test
    public void testGetLayer(){
        AbstractTile abstractTile = new AbstractTile(20,20,true,3) {
            @Override
            protected void refresh() {

            }
        };

        Assertions.assertEquals(3, abstractTile.getLayer());
    }

    @Test
    public void testGetWidth(){
        AbstractTile abstractTile = new AbstractTile(20,20,true,3) {
            @Override
            protected void refresh() {

            }
        };

        Assertions.assertEquals(20, abstractTile.getWidth());
    }

    @Test
    public void testGetHeight(){
        AbstractTile abstractTile = new AbstractTile(20,20,true,3) {
            @Override
            protected void refresh() {

            }
        };

        Assertions.assertEquals(20,abstractTile.getHeight());
    }
}

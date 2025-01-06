package github.engine.legacy;

import io.github.engine.AbstractTile;
import io.github.engine.ButtonNames;
import io.github.engine.Display;
import io.github.engine.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerTest {

    @Test
    public void testGetJumpForce(){
        Display.setup("ventana",800,600);
        Display.start();
        Player player = new Player(0,0,20,20,true,1) {
            @Override
            protected void refresh() {

            }

            @Override
            public void topCollision(AbstractTile tile) {

            }

            @Override
            public void sideCollision(AbstractTile tile) {

            }

            @Override
            public void press(ButtonNames name) {

            }

            @Override
            public void release(ButtonNames name) {

            }
        };

        Assertions.assertEquals(1,player.getJumpForce());
    }
}

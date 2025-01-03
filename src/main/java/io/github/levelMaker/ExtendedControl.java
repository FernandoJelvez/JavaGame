package io.github.levelMaker;

import io.github.engine.Control;
import io.github.engine.Controllable;
import io.github.engine.Texture;

import java.awt.event.KeyEvent;
import java.io.IOException;

import static io.github.levelMaker.ExtendedControlNames.*;

public class ExtendedControl extends Control {


    public ExtendedControl(ExtendedControlInterface controllable) {
        super(controllable);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        ExtendedControlInterface controllable = (ExtendedControlInterface) getControllable();

        if ((e.getKeyCode() <= 40 && e.getKeyCode() >= 37) || ( e.getKeyCode() == 88 || e.getKeyCode() == 90) ){
            super.keyPressed(e);
        } else {
            switch(e.getKeyCode()){
                case KeyEvent.VK_Q:
                    controllable.pressExtended(PREVIOUS_TILE);
                    break;
                case KeyEvent.VK_E:
                    controllable.pressExtended(NEXT_TILE);
                    break;
                case KeyEvent.VK_R:
                    controllable.pressExtended(PREVIOUS_SCREEN);
                    break;
                case KeyEvent.VK_T:
                    controllable.pressExtended(NEXT_SCREEN);
                    break;
                case KeyEvent.VK_Y:
                    controllable.pressExtended(CHANGE_SOLID);
                    break;
                case KeyEvent.VK_U:
                    controllable.pressExtended(CHANGE_LAYER);
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
    }
}

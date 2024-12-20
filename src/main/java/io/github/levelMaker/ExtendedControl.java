package io.github.levelMaker;

import io.github.engine.Control;
import io.github.engine.Controllable;

import java.awt.event.KeyEvent;

public class ExtendedControl extends Control {


    public ExtendedControl(Controllable controllable) {
        super(controllable);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
    }
}

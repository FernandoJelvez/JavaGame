package io.github.levelMaker;

import io.github.engine.Control;
import io.github.engine.Controllable;

import java.awt.event.KeyEvent;

import static io.github.levelMaker.ExtendedControlNames.chunkDerecha;
import static io.github.levelMaker.ExtendedControlNames.chunkIzquierda;

public class ExtendedControl extends Control {


    public ExtendedControl(Controllable controllable) {
        super(controllable);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        ExtendedControlInterface controllable = (ExtendedControlInterface) getControllable();

        if ((e.getKeyCode() <= 40 && e.getKeyCode() >= 37) || ( e.getKeyCode() == 88 || e.getKeyCode() == 90) ){
            super.keyPressed(e);
        } else {
            switch (e.getKeyCode()){
                case KeyEvent.VK_K:
                    controllable.pressExtended(chunkIzquierda);
                    System.out.println("izq");
                    if (Mundo.getIdPantalla() > -1){
                        Mundo.setIdPantalla(Mundo.getIdPantalla()-1);
                    }
                case KeyEvent.VK_E:
                    controllable.pressExtended(chunkDerecha);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

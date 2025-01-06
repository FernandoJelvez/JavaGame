package io.github.levelMaker;

import io.github.engine.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class Cursor extends Player implements ExtendedControlInterface{

    @Override
    public float getUnitX() {
        return super.getUnitX();
    }

    @Override
    public float getUnitY() {
        return super.getUnitY();
    }

    public Cursor(int x, int y, AbstractTile tile) {
        super(x, y, tile);
        changeLocalGravity(0);
        setUsingLocalGravity(true);
        setSolid(false);
    }

    public Cursor(int x, int y, int width, int height, boolean solid, int layer) {
        super(x, y, width, height, solid, layer);
        changeLocalGravity(0);
        setUsingLocalGravity(true);
        setSolid(false);
    }


    @Override
    protected void refresh() {

    }

    @Override
    public void press(ButtonNames name) {
        switch(name){
            case RIGHT:
                setLocation(getUnitX() + 4, getUnitY());
                break;
            case LEFT:
                if (getUnitX() > 0){
                    setLocation(getUnitX() - 4, getUnitY());
                }
                break;
            case UP:
                if (getUnitY() > 0){
                    setLocation(getUnitX(), getUnitY() - 4);
                }
                break;
            case DOWN:
                if (getUnitY() < 56){
                    setLocation(getUnitX(), getUnitY() + 4);
                }
                break;
            case PRIMARY:
                Mundo.colocarBloque(new Point((int) getUnitX(), (int) getUnitY()));
                break;
            case SECONDARY:
                Mundo.borrarBloque(new Point((int) getUnitX(), (int) getUnitY()));
                break;
        }
    }

    @Override
    public void topCollision(AbstractTile tile) {

    }

    @Override
    public void sideCollision(AbstractTile tile) {

    }

    @Override
    public void release(ButtonNames name) {
    }

    @Override
    public void pressExtended(ExtendedControlNames name) {
        switch (name){
            case ExtendedControlNames.PREVIOUS_TILE:
                if(LevelMaker.isStarter && LevelMaker.idBloque > 2){
                    LevelMaker.idBloque--;
                }
                break;
            case ExtendedControlNames.NEXT_TILE:
                if (LevelMaker.isStarter && LevelMaker.idBloque < Display.getTextures().size()-1){
                    LevelMaker.idBloque++;
                }
                break;
            case ExtendedControlNames.CHANGE_SOLID:
                if (LevelMaker.isStarter){
                    try {
                        //BLANCO = NO SOLIDO, ROJO = SOLIDO
                        if (Mundo.isSolid){
                            Mundo.isSolid = false;
                            LevelMaker.cursor.setTexture((Mundo.asignarTexturaAlBloque(0)));
                            //AHORA NO ES SOLIDO
                        } else {
                            Mundo.isSolid = true;
                            LevelMaker.cursor.setTexture((Mundo.asignarTexturaAlBloque(1)));
                            //AHORA ES SOLIDO
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case ExtendedControlNames.CHANGE_LAYER:
                if (LevelMaker.isStarter){
                    if (Mundo.layer < 3){
                        Mundo.layer++;
                        LevelMaker.label.setText("L=" + Mundo.layer);
                    } else {
                        Mundo.layer = 1;
                        LevelMaker.label.setText("L=" + 1);
                    }
                }
                break;
            case ExtendedControlNames.SAVE:
                Mundo.GuardarMundo();
                break;
        }
    }

    @Override
    public void releaseExtended(ExtendedControlNames name) {

    }
}

package io.github.levelMaker;

import io.github.engine.AbstractTile;
import io.github.engine.ButtonNames;
import io.github.engine.Display;
import io.github.engine.Texture;
import io.github.game.Player;
import io.github.game.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import static io.github.gameMakerTool.PlaceArgumentKeywords.tile;

public class Cursor extends Player {

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
                setLocation(getUnitX() - 4, getUnitY());
                break;
            case UP:
                setLocation(getUnitX(), getUnitY() - 4);
                break;
            case DOWN:
                setLocation(getUnitX(), getUnitY() + 4);
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

}

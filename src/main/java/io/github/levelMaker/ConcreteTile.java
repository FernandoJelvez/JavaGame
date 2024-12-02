package io.github.levelMaker;

import io.github.engine.AbstractTile;

import java.awt.*;

public class ConcreteTile {

    public Point p;
    public Dimension d;
    public boolean solid;
    public int layer;
    public int id;

    public ConcreteTile(Point p, Dimension d, boolean solid, int layer, int id) {
        this.p = p;
        this.d = d;
        this.solid = solid;
        this.layer = layer;
        this.id = id;
    }
}

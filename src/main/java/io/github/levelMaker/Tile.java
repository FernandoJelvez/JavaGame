package io.github.levelMaker;

import javax.swing.*;

public class Tile extends io.github.engine.Tile {
    public Tile(int width, int height, boolean solid, int layer) {
        super(width, height, solid, layer);
    }

    public Tile(float x, float y, float width, float height, boolean solid, int layer) {
        super(x, y, width, height, solid, layer);
    }

    @Override
    protected JLabel getLabel() {
        return super.getLabel();
    }
}

package io.github.levelMaker;

import javax.swing.*;

public class Tile extends io.github.game.Tile {
    public Tile(int width, int height, boolean solid, int layer) {
        super(width, height, solid, layer);
    }

    public Tile(int x, int y, int width, int height, boolean solid, int layer) {
        super(x, y, width, height, solid, layer);
    }

    @Override
    protected JLabel getLabel() {
        return super.getLabel();
    }
}

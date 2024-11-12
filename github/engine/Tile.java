package io.github.engine;

import java.awt.*;

public class Tile extends AbstractTile {
	public Tile(AbstractTile tile,String id) {
		super(tile.getLocation(),tile.getSize(),id);
	}
	public Tile(int width, int height,String id) {
		super(width, height, id);
	}

	public Tile(int x, int y, int width, int height,String id) {
		super(x, y, width, height,id);
	}

	public Tile(int x, int y, Dimension d,String id) {
		super(x, y, d,id);
	}
}

package io.github.game;

import io.github.engine.AbstractTile;
import io.github.engine.Collidable;
import io.github.engine.Entity;

public class Tile extends AbstractTile implements Collidable {
	public Tile(int width, int height, boolean solid, int layer) {
		super(width, height, solid, layer);
	}

	@Override
	public void refresh() {

	}

	public Tile(int x, int y, int width, int height, boolean solid, int layer) {
		super(width, height, solid, layer);
		setLocation(x,y);
	}


	//Collidable interface methods

	@Override
	public void topCollision(AbstractTile tile) {

	}

	@Override
	public void sideCollision(AbstractTile tile) {

	}
}

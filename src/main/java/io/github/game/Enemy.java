package io.github.game;

import io.github.engine.AbstractTile;
import io.github.engine.Collidable;
import io.github.engine.Entity;
import io.github.engine.Player;

public class Enemy extends Entity implements Collidable {
	public Enemy(int x, int y, AbstractTile tile) {
		super(x, y, tile);
		setXSpeed(4);
	}

	public Enemy(int x, int y, int width, int height, boolean solid, int layer) {
		super(x, y, width, height, solid, layer);
		setXSpeed(4);
	}
	@Override
	protected void refresh() {
		if(getUnitX()>20){
			setXSpeed(-4);
		} else if(getUnitX()<10){
			setXSpeed(4);
		}
	}

	@Override
	public void topCollision(AbstractTile tile) {

	}

	@Override
	public void sideCollision(AbstractTile tile) {
		if(tile instanceof Player){
			((Player) tile).setYSpeed(-10);
		}
	}
}

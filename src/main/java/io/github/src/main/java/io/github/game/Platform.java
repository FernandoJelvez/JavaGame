package io.github.game;

import io.github.engine.AbstractTile;
import io.github.engine.Collidable;
import io.github.engine.Entity;
import io.github.engine.Player;

public class Platform extends Entity implements Collidable{
	private final int travelSpeed;
	private final int movementWidth;
	private final int initialX;
	public Platform(int x, int y, AbstractTile tile, int travelSpeed, int movementWidth) {
		super(x, y, tile);
		setUsingLocalGravity(true);
		changeLocalGravity(0);
		initialX=x;
		this.travelSpeed=travelSpeed;
		this.movementWidth=movementWidth;
	}

	public Platform(int x, int y, int width, int height, boolean solid, int layer, int travelSpeed, int movementWidth) {
		super(x, y, width, height, solid, layer);
		setUsingLocalGravity(true);
		changeLocalGravity(0);
		initialX=x;
		this.travelSpeed=travelSpeed;
		this.movementWidth=movementWidth;
	}
	@Override
	protected void refresh() {
		if(getUnitX()>=(initialX+movementWidth)||(getXSpeed()==0)){
			setXSpeed((travelSpeed)*(-1));
		}
		if(getUnitX()<=(initialX)){
			setXSpeed((travelSpeed));
		}
	}

	@Override
	public void topCollision(AbstractTile tile) {
		if(tile instanceof Player) {
			((Entity)tile).setYSpeed(-10);
		}
	}

	@Override
	public void sideCollision(AbstractTile tile) {
	}
}

package io.github.game;

import io.github.engine.AbstractTile;
import io.github.engine.Collidable;
import io.github.engine.Entity;
import io.github.engine.Controllable;

public class Player extends Entity implements Controllable, Collidable {
	//jump force affects how much the player will jump, by multiplying the base jump speed
	int jumpForce=1;
	//physics is the physics engined being used by the player,


	public Player(int x, int y, AbstractTile tile) {
		super(x, y, tile);

	}

	public Player(int x, int y, int width, int height, boolean solid, int layer) {
		super(x, y, width, height, solid, layer);
	}

	@Override
	public void upButton() {
	}

	@Override
	public void downButton() {

	}

	@Override
	public void leftButton() {

	}

	@Override
	public void rightButton() {

	}

	@Override
	public void primaryButton() {
		setYSpeed(jumpForce*(-3)); //this is an example of jump, it is relevant to make the result negative by the nature of coordinates in javax swing
	}

	@Override
	public void secondaryButton() {

	}

	@Override
	public void topCollision() {

	}

	@Override
	public void sideCollision() {

	}

	@Override
	protected void refresh() {

	}
}

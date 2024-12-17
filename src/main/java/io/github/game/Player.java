package io.github.game;

import io.github.engine.*;

public abstract class Player extends Entity implements Controllable, Collidable {
	//jump force affects how much the player will jump, by multiplying the base jump speed
	private int jumpForce=3;
	//physics is the physics engined being used by the player,


	public Player(int x, int y, AbstractTile tile) {
		super(x, y, tile);

	}

	public Player(int x, int y, int width, int height, boolean solid, int layer) {
		super(x, y, width, height, solid, layer);
	}

	@Override
	public void press(ButtonNames name) {
	}

	public int getJumpForce() {
		return jumpForce;
	}

	public void setJumpForce(int jumpForce) {
		this.jumpForce = jumpForce;
	}
}

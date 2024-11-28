package io.github.game;

import io.github.engine.AbstractTile;
import io.github.engine.Controllable;
import io.github.engine.Entity;
import io.github.engine.Collidable;

public class Player extends Entity implements Controllable {
	//jump force affects how much the player will jump, by multiplying the base jump speed
	int jumpForce=1;
	//physics is the physics engined being used by the player,

	public Player(int xPos, int yPos, AbstractTile tile, String id){
		super(xPos,yPos,tile,id);
	}
	public Player(int x, int y, int width,int height,boolean solid, int layer){
		super(x,y,width,height,solid,layer);
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
		setySpeed(jumpForce*3);
	}

	@Override
	public void secondaryButton() {

	}
	@Override
	public void refresh() {
	}
}

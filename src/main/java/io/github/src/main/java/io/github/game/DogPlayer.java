package io.github.game;

import io.github.engine.AbstractTile;
import io.github.engine.ButtonNames;
import io.github.engine.Physics;
import io.github.engine.Player;

public class DogPlayer extends Player {
	private boolean beingControlled=false;
	public DogPlayer(int x, int y, AbstractTile tile) {
		super(x, y, tile);
		setJumpForce(3);
		changeLocalGravity(9);
		setUsingLocalGravity(true);
	}

	public DogPlayer(int x, int y, int width, int height, boolean solid, int layer) {
		super(x, y, width, height, solid, layer);
		setJumpForce(3);
		changeLocalGravity(9);
		setUsingLocalGravity(true);
	}
	public void jump(){
		if(isBottomLocked()) {
			setYSpeed(getJumpForce() * (jumpConstant));
			setBottomLocked(false);
		}
	}

	@Override
	public void release(ButtonNames name) {
		switch(name){
			case RIGHT, LEFT:
				beingControlled=false;
				break;
		}
	}

	@Override
	public void press(ButtonNames name) {
		switch(name){
			case RIGHT:
				if(getXSpeed()<8&&isBottomLocked()){
					setxAcceleration(12);
				} else {
					setxAcceleration(0);
					setXSpeed(8);
				}
				beingControlled=true;
				break;
			case LEFT:
				if(getXSpeed()>-8&&isBottomLocked()){
					setxAcceleration(-12);
				} else {
					setxAcceleration(0);
					setXSpeed(-8);
				}
				beingControlled=true;
				break;
			case PRIMARY:
				jump();
				break;
		}
	}

	@Override
	protected void refresh() {
		if(isBottomLocked()){
			decelerate();
		} else {
			setxAcceleration(0);
		}
	}
	public void decelerate(){
		if(getXSpeed()<=0&&isMovingRight()&&!beingControlled){
			setxAcceleration(0);
			if(getXSpeed()<0){
				setXSpeed(0);
			}
		} else if (getXSpeed()<=0&&!isMovingRight()&&!beingControlled){
			setxAcceleration(8);
		} else if(getXSpeed()>=0&&!isMovingRight()&&!beingControlled){
			setxAcceleration(0);
			if(getXSpeed()>0){
				setXSpeed(0);
			}
		} else if (getXSpeed()>=0&&isMovingRight()&&!beingControlled){
			setxAcceleration(-8);
		}
	}

	@Override
	public void topCollision(AbstractTile tile) {

	}

	@Override
	public void sideCollision(AbstractTile tile) {

	}

	@Override
	public void setXSpeed(double xSpeed) {
		super.setXSpeed(xSpeed);
		setMovingRight(xSpeed > 0);
	}
}

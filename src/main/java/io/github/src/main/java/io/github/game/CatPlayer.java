package io.github.game;

import io.github.engine.AbstractTile;
import io.github.engine.ButtonNames;
import io.github.engine.Synchronization;
import io.github.engine.Player;

import java.lang.management.MemoryUsage;
import java.util.logging.MemoryHandler;

public class CatPlayer extends Player {
	private float jumpCooldownTime=0;
	private int timesJumped=0;
	private boolean beingControlled=false;
	public CatPlayer(int x, int y, AbstractTile tile) {
		super(x, y, tile);
		setJumpForce(4);
	}

	public CatPlayer(int x, int y, int width, int height, boolean solid, int layer) {
		super(x, y, width, height, solid, layer);
		setJumpForce(4);
	}
	public void jump(){
		if(isBottomLocked()||(jumpCooldownTime>1&&timesJumped==1)||(!isBottomLocked()&&(jumpCooldownTime>1&&timesJumped==1)&&(isLeftLocked()||isRightLocked()))) {
			setYSpeed(getJumpForce() * (jumpConstant));
			setBottomLocked(false);
			timesJumped++;
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
					setxAcceleration(8);
				} else {
					setxAcceleration(0);
					setXSpeed(8);
				}
				beingControlled=true;
				break;
			case LEFT:
				if(getXSpeed()>-8&&isBottomLocked()){
					setxAcceleration(-8);
				} else {
					setxAcceleration(0);
					setXSpeed(-8);
				}
				beingControlled=true;
				break;
			case PRIMARY:
				jump();
				break;
			case SECONDARY:
				System.out.println("mark");
				break;
		}
	}

	@Override
	protected void refresh() {
		if (timesJumped==1){
			jumpCooldownTime+=Synchronization.getDeltaTime();
		}
		if(timesJumped>0&&isBottomLocked()){
			jumpCooldownTime=0;
			timesJumped=0;
		}
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

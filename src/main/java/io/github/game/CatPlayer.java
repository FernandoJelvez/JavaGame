package io.github.game;

import io.github.engine.AbstractTile;
import io.github.engine.ButtonNames;
import io.github.engine.DisplayRefresh;

public class CatPlayer extends Player {
	private float jumpCooldownTime=0;
	private int timesJumped=0;
	public CatPlayer(int x, int y, AbstractTile tile) {
		super(x, y, tile);
	}

	public CatPlayer(int x, int y, int width, int height, boolean solid, int layer) {
		super(x, y, width, height, solid, layer);
	}
	public void jump(){
		System.out.print("																																		primary");
		if(isBottomLocked()||(jumpCooldownTime>1&&timesJumped==1)) {
			setYSpeed(getJumpForce() * (-3));
			setBottomLocked(false);
			timesJumped++;
			System.out.print(" | jump | ");
		}
		System.out.println(jumpCooldownTime);
	}

	@Override
	public void release(ButtonNames name) {

	}

	@Override
	public void press(ButtonNames name) {
		switch(name){
			case RIGHT:
				setXSpeed(8);
				break;
			case LEFT:
				System.out.println("l");
				setXSpeed(-8);
				System.out.println("a: "+getxAcceleration());
				break;
			case PRIMARY:
				jump();
				break;
		}
	}

	@Override
	protected void refresh() {
		if (timesJumped==1){
			jumpCooldownTime+=DisplayRefresh.getDeltaTime();
		}
		if(timesJumped>0&&isBottomLocked()){
			System.out.println(true);
			jumpCooldownTime=0;
			timesJumped=0;
		}
	}

	@Override
	public void topCollision(AbstractTile tile) {

	}

	@Override
	public void sideCollision(AbstractTile tile) {

	}
}

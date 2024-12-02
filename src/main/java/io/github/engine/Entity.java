package io.github.engine;

import java.util.List;

public abstract class Entity extends AbstractTile{
	private double xSpeed,ySpeed;
	private int gravity=0;//the gravity the entity uses if it doesn't use the global gravity
	private boolean usingLocalGravity=false; //signals if the entity uses the default gravity(false) or its own(true)

	public Entity(int x, int y, AbstractTile tile){
		super(tile.getWidth(),tile.getHeight(),tile.isSolid(),tile.getLayer());
		tile.setLocation(x,y);
		ySpeed=0;
	}

	public Entity(int x, int y, int width,int height,boolean solid,int layer){
		super(width,height,solid,layer);
		setLocation(x,y);
		ySpeed=0;
	}

	public boolean isUsingLocalGravity() {
		return usingLocalGravity;
	}

	public void changeLocalGravity(int gravity) {
		this.gravity = gravity;
	}

	//setters and getters
	public void setUsingLocalGravity(boolean flag){
		this.usingLocalGravity=flag;
	}

	public double getYSpeed(){
		return ySpeed;
	}

	public void setYSpeed(double ySpeed){
		this.ySpeed=ySpeed;
	}

	public void setXSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}

	public double getXSpeed() {
		return xSpeed;
	}

	public int getGravity() {
		return gravity;
	}
}
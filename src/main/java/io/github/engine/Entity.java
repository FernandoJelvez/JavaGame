package io.github.engine;

public abstract class Entity extends AbstractTile {
	private double airTime;
	private double xSpeed,ySpeed;
	private int gravity;//the gravity the entity uses if it doesn't use the global gravity
	private boolean usingLocalGravity=false; //signals if the entity uses the default gravity(false) or its own(true)

	public Entity(int x, int y, AbstractTile tile){
		super(x,y,tile.getWidth(),tile.getHeight(),tile.isSolid(),tile.getLayer());
		this.usingGlobalGravity = true;
		ySpeed=0;
	}

	public Entity(int x, int y, int width,int height,boolean solid,int layer){
		super(x,y,width,height,solid,layer);
		this.usingGlobalGravity = true;
		ySpeed=0;
	}
	
	public boolean isUsingGlobalGravity() {
		return usingGlobalGravity;
	}

	public void setUsingLocalGravity(boolean flag){
		this.usinglocalGravity=flag;
	}

	public void changeLocalGravity(int gravity) {
		this.gravity = gravity;
	}

	public double getYSpeed(){
		return ySpeed;
	}
	public double getXSpeed() {
		return xSpeed;
	}

	public void setYSpeed(double ySpeed){
		this.ySpeed=ySpeed;
	}
	public void setXSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}
	public int getGravity() {
		return gravity;
	}
}

package io.github.engine;

public abstract class Entity extends AbstractTile{
	private double xSpeed,ySpeed;
	private int gravity=0;//the gravity the entity uses if it doesn't use the global gravity
	private boolean usingLocalGravity=false; //signals if the entity uses the default gravity(false) or its own(true)
	private boolean bottomLocked=false;
	private boolean topLocked=false;
	private boolean leftLocked=false;
	private boolean rightLocked=false;
	private int xAcceleration =0;

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

	public boolean isBottomLocked() {
		return bottomLocked;
	}

	public void setBottomLocked(boolean bottomLocked) {
		this.bottomLocked = bottomLocked;
	}

	public boolean isTopLocked() {
		return topLocked;
	}

	public void setTopLocked(boolean topLocked) {
		this.topLocked = topLocked;
	}

	public boolean isLeftLocked() {
		return leftLocked;
	}

	public void setLeftLocked(boolean leftLocked) {
		this.leftLocked = leftLocked;
	}

	public boolean isRightLocked() {
		return rightLocked;
	}

	public void setRightLocked(boolean rightLocked) {
		this.rightLocked = rightLocked;
	}

	public double getXSpeed() {
		return xSpeed;
	}

	public int getGravity() {
		return gravity;
	}
	public void setxAcceleration(int xAcceleration){
		this.xAcceleration = xAcceleration;
	}
	public int getxAcceleration(){
		return xAcceleration;
	}

	@Override
	public void update(AbstractTile tile) {
		super.update(tile);
		if(tile instanceof Entity){
			Entity entity=(Entity)tile;
			bottomLocked=entity.isBottomLocked();
			topLocked=entity.isTopLocked();
			rightLocked=entity.isRightLocked();
			leftLocked=entity.isLeftLocked();
			xSpeed=entity.getXSpeed();
			ySpeed=entity.getYSpeed();
			xAcceleration=entity.xAcceleration;
			gravity=entity.getGravity();
			usingLocalGravity=entity.usingLocalGravity;
		}
	}
}
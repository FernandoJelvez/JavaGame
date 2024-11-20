package io.github.engine;

public abstract class Entity extends AbstractTile {
	private double airTime;
	private double xSpeed,ySpeed;
	private int gravity;//the gravity the entity uses if it doesn't use the global gravity
	private boolean usingGlobalGravity; //signals if the entity uses the default gravity(true) or its own(false)

	public Entity(int x, int y, AbstractTile tile, boolean usingGlobalGravity){
		super(x,y,tile.isSolid(),tile.getLayer());
		this.usingGlobalGravity = usingGlobalGravity;
	}

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

	/**
	 * Updates the position of the object in screen, both horizontally and vertically
	 * @apiNote this method should be overrode by any subclass that needs a different movement behavior
	 */
	public void refresh(){
		int y =(int)(ySpeed*airTime);
		setLocation(getX()+(int)(xSpeed*DisplayRefresh.getDeltaTime()),y);
		airTime += DisplayRefresh.getDeltaTime();
	}

	public boolean isUsingGlobalGravity() {
		return usingGlobalGravity;
	}

	public void setUsingGlobalGravity(boolean flag){
		this.usingGlobalGravity=flag;
	}

	public void setGravity(int gravity) {
		this.gravity = gravity;
	}

	public double getYSpeed(){
		return ySpeed;
	}

	public void setYSpeed(double ySpeed){
		this.ySpeed=ySpeed;
	}

	public double getAirTime(){
		return airTime;
	}

	public void setXSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}
}

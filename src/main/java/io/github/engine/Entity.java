package io.github.engine;

public abstract class Entity extends AbstractTile {
	private long airTime;
	private int xSpeed,ySpeed = 0;
	private int gravity;//the gravity the entity uses if it doesn't use the global gravity
	private boolean usingGlobalGravity; //signals if the entity uses the default gravity(true) or its own(false)

	public Entity(int x, int y, AbstractTile tile, boolean usingGlobalGravity,String id){
		super(x,y,id);
		this.usingGlobalGravity = usingGlobalGravity;
	}

	public Entity(int x, int y, AbstractTile tile,String id){
		super(x,y,tile.getWidth(),tile.getHeight(),id);
		this.usingGlobalGravity = true;
	}

	public Entity(int x, int y, int width,int height,String id){
		super(x,y,width,height,id);
		this.usingGlobalGravity = true;
	}

	public void updatePosition(){
		super.setLocation(super.getX(), (int) (super.getY()+ySpeed*airTime));
		airTime+=UpdateManager.getDeltaTime();
	}

	public boolean isUsingGlobalGravity() {
		return usingGlobalGravity;
	}

	public int getYSpeed(){
		return ySpeed;
	}

	public void setYSpeed(int ySpeed){
		this.ySpeed=ySpeed;
	}

	public long getAirTime(){
		return airTime;
	}
}
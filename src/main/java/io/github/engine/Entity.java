package io.github.engine;

public abstract class Entity extends AbstractTile {
	private int airTime;
	private int xSpeed,ySpeed = 0;
	private int gravity;//the gravity the entity uses if it doesn't use the global gravity
	private boolean globalGravity; //signals if the entity uses the default gravity(true) or its own(false)


	/*
	*
	*
	*/
	public Entity(int x, int y, AbstractTile tile, boolean globalGravity,String id){
		super(x,y,tile.getSize(),id);
		this.globalGravity = globalGravity;
	}

	public Entity(int x, int y, AbstractTile tile,String id){
		super(x,y,tile.getSize(),id);
		this.globalGravity = true;
	}
	public Entity(int x, int y, int width,int height,String id){
		super(x,y,width,height,id);
		this.globalGravity = true;
	}

	public boolean isGlobalGravity() {
		return globalGravity;
	}
	public int getySpeed(){
		return ySpeed;
	}
	public void setySpeed(int ySpeed){
		this.ySpeed=ySpeed;
	}
	public int getAirTime(){
		return airTime;
	}
	public void updatePosition(){
		super.setLocation(super.getX(),super.getY()-ySpeed*airTime);
		airTime++;
	}
}
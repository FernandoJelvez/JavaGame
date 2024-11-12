package io.github.engine;

public abstract class Entity extends AbstractTile {
	private long airTime;
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
	public long getAirTime(){
		return airTime;
	}
	public void updatePosition(){
		System.out.println("Y"+super.getY());
		System.out.println("Speed*air time"+(ySpeed*airTime));
		System.out.println("nuevo Y ideal "+(super.getY()+ySpeed*airTime));
		super.setLocation(super.getX(), (int) (super.getY()+ySpeed*airTime));
		airTime+=UpdateManager.getDeltaTime();

		System.out.println("air time "+airTime);
	}
}
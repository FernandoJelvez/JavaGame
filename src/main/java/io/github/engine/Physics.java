package io.github.engine;

/**
 *Management of the interactions between game entities and their surroundings
 */
public final class Physics {
    private static double globalGravity;

	/**Applies calculations of projectile trajectory, changing its vertical speed and its position
	 * @param entity The Entity which will be affected by the calculation of its trajectory
	 */
	public static void applyProjectileCinematic(Entity entity){
		double deltaTime= DisplayRefresh.getDeltaTime();
		double gravity;

		if(entity.isUsingLocalGravity()){
			gravity=entity.getGravity();
		} else {
			gravity=globalGravity;
		}

		double newYSpeed = applyGravity(entity.getYSpeed(), gravity,deltaTime);
		entity.setYSpeed(newYSpeed);

		/* speed in units, newYSpeed in units per second, globalGravity in units per square second, the equation is
		(Yp = the previous Y value, v = speed, dt = deltaTime, g = gravity):
		Y=Yp + v*dt + (a/2)*(dt)^2 */
		float newY= (float)(entity.getY() + ((newYSpeed*deltaTime) + (gravity*deltaTime*deltaTime)/2));
		float newX= (float) (entity.getX() + entity.getXSpeed()*deltaTime);
		entity.setLocation(newX,newY);
	}
	
	public static void applyGravity(double ySpeed,double gravity,double time){
		return ySpeed + (gravity*time);
	}

	public static void setGlobalGravity(double gravity) {
		globalGravity = gravity;
	}
	public static void checkFreePath(){
		//TODO: build the path checker
		//TODO: check if this method must go in Entity instead
		//checks if an Entity can Travel one more step before colliding
		//if it cant, then the path is shortened to the more distant point the Entity can go without colliding
	}

	public static boolean checkColliding(AbstractTile tile){
		return false;
	}
	private static void callCollition(AbstractTile Tile1, AbstractTile Tile2){
		//TODO: add collition calling, this method must decide if the collition is top or side collition
	}
}

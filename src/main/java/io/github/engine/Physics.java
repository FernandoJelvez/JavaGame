package io.github.engine;

/**
 *Management of the interactions between game entities and their surroundings
 */
public final class Physics {
    private static double globalGravity;

	/**
	 *Applies gravity to an Entity, changing its vertical speed
	 * @param entity The Entity upon which gravity is applied
	 */
	public static void applyGravity(Entity entity){
		double newYSpeed = (entity.getYSpeed()+((globalGravity * Display.getHeigth()) /2000) *entity.getAirTime());
		entity.setYSpeed(newYSpeed);
		entity.refresh();
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

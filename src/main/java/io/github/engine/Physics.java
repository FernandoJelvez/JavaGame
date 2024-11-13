package io.github.engine;

/**
 *Management of the interactions between game entities and their surroundings
 */
public final class Physics {
    private static int globalGravity;

	/**
	 *Receives the vertical speed of an Entity and the time it has been on the air, and
	 * returns the new vertical speed according to the effect of gravity
	 *@param ySpeed The vertical speed of an Entity
	 *@param airTime The time that the Entity has spend in the air from the last time
	 *it touched a floor
	 */
    public static int applyGravity(int ySpeed,int airTime){
        ySpeed=ySpeed+globalGravity *airTime;
        return ySpeed;
    }

	/**
	 *Applies gravity to an Entity, changing its vertical speed
	 * @param entity The Entity upon which gravity is applied
	 */
	public static void applyGravity(Entity entity){
		entity.setYSpeed((int) (entity.getYSpeed()+globalGravity *entity.getAirTime()));
		entity.updatePosition();
	}

	public static void setGlobalGravity(int gravity) {
		globalGravity = gravity;
	}

	public static boolean checkCollition(int xPos, int yPos, int width, int height){
		//TODO: build the collition system
		return true;
	}
}

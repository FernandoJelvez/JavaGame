package io.github.engine;

/**
 *Management of the interactions between game entities and their surroundings
 */
public class Physics {
    private static int globalGravity; /*global gravity, the gravity that objects follow by default,
								 if not set otherwise by the entity*/

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
		entity.setySpeed(entity.getySpeed()-globalGravity *entity.getAirTime());
		entity.updatePosition();
	}

	public static void setGlobalGravity(int gravity) {
		globalGravity = gravity;
	}

	//public boolean checkCollition(Entity entity, Tile tile){
	//	ArrayList<Integer> i = new ArrayList<>();
	//}

	public static boolean checkCollition(int xPos, int yPos, int width, int height){
		//TODO: build the collition system
		return true;
	}
}

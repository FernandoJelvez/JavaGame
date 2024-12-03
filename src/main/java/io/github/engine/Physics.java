package io.github.engine;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 *Management of the interactions between game entities and their surroundings
 */
public class Physics {
	private static double globalGravity;

	/**
	 * Applies calculations of projectile trajectory, changing its vertical speed and its position
	 *
	 * @param entity The Entity which will be affected by the calculation of its trajectory
	 */
	public static void applyProjectileCinematic(Entity entity) {
		double deltaTime = DisplayRefresh.getDeltaTime();
		double unit=Display.getUnitValue();
		double gravity;
		if (entity.isUsingLocalGravity()) {
			gravity = entity.getGravity();
		} else {
			gravity = globalGravity;
		}
		double newYSpeed = applyGravity(entity.getYSpeed(), gravity, deltaTime);
		entity.setYSpeed(newYSpeed);
		float idealY = (float) (entity.getUnitY() + ((entity.getYSpeed() * deltaTime) + (gravity * deltaTime * deltaTime) / 2));
		float idealX = (float) (entity.getUnitX() + entity.getXSpeed() * deltaTime);
		Rectangle expectedEntityBoundary = (Rectangle) entity.getLabel().getBounds().clone();
		expectedEntityBoundary.setLocation((int)Math.round(idealX *unit),(int)Math.round(idealY *unit));
		List<AbstractTile> collidingTiles=getCollidingTiles(expectedEntityBoundary,entity);
		if(collidingTiles.isEmpty()){
			entity.setLocation(idealX, idealY);
		} else {
			shortenTrajectory(entity, expectedEntityBoundary, collidingTiles, gravity);
		}
	}

	private static void shortenTrajectory(Entity entity, Rectangle expectedEntityBoundary, List<AbstractTile> collidingTiles, double gravity) {
		long horizontallyColliding= checkHorizontalCollision(expectedEntityBoundary,collidingTiles);
		long verticallyColliding= checkVerticalCollision(expectedEntityBoundary,collidingTiles);
		double xSpeed=entity.getXSpeed()*Display.getUnitValue();
		double ySpeed=entity.getYSpeed()*Display.getUnitValue();
		double initialX=entity.getLabel().getBounds().getMinX();
		double initialY=entity.getLabel().getBounds().getMinY();
		double newX;
		double newY;
		//this works (bottom)
		if(horizontallyColliding!=0&&verticallyColliding==0){
			newX=calculateShortenedPathX(collidingTiles,horizontallyColliding, expectedEntityBoundary);
			newY = getYBasedOnX(newX, initialX, xSpeed, initialY, gravity, ySpeed);
			entity.setXSpeed(0);
		} else if (horizontallyColliding == 0 && verticallyColliding != 0) {
			newY=calculateShortenedPathY(collidingTiles,verticallyColliding, expectedEntityBoundary);
			newX = getXBasedOnY(newY, initialY, ySpeed, initialX, gravity, xSpeed);
			entity.setYSpeed(0);
			if(verticallyColliding<0){
				entity.setOnFloor(true);
			}
		}
		else if (horizontallyColliding!=0){
			newY=calculateShortenedPathY(collidingTiles,verticallyColliding, expectedEntityBoundary);
			newX=calculateShortenedPathX(collidingTiles,horizontallyColliding, expectedEntityBoundary);
			entity.setYSpeed(0);
			entity.setXSpeed(0);
			if(verticallyColliding<0){
				entity.setOnFloor(true);
			}
		}
		else {
			newX= expectedEntityBoundary.getX();
			newY= expectedEntityBoundary.getY();
		}
		entity.setLocation((float) (newX/Display.getUnitValue()), (float) (newY/Display.getUnitValue()));
		callCollsion(entity,collidingTiles);
	}

	private static void callCollsion(Entity entity, List<AbstractTile> collidingTiles) {
		collidingTiles.stream().filter((e)-> Arrays.stream(e.getClass().getInterfaces()).anyMatch((i)->i.isInstance(Collidable.class)))
				.map((e)->(Collidable)(e)).forEach((e)->e.collision(entity));
	}

	public static float calculateShortenedPathX(List<AbstractTile> collidingTile,long collidingSideIndex,Rectangle entityBoundary){
		if(collidingSideIndex>0){
			return (float) (collidingTile.stream().map((e)->e.getLabel().getBounds().getMinX())
					.filter((e)->e>entityBoundary.getMinX())
					.min(Double::compareTo).orElseThrow()-entityBoundary.getWidth());
		} else if (collidingSideIndex<0) {
			return collidingTile.stream().map((e)->e.getLabel().getBounds().getMaxX())
					.filter((e)->e<entityBoundary.getMaxX())
					.max(Double::compareTo).orElseThrow().floatValue();
		} else {
			return (float) entityBoundary.getX();
		}
	}
	public static float calculateShortenedPathY(List<AbstractTile> collidingTiles, long collidingSideIndex, Rectangle entityBoundary){
		if(collidingSideIndex<0){ //bottom collision
			return (float) ((collidingTiles.stream().map((e)->e.getLabel().getBounds()))
								.filter((e)->e.getMinY()<entityBoundary.getMaxY()&&e.getMaxY()>entityBoundary.getMaxY())
								.map(Rectangle::getMinY).filter((e)->e>0)
								.min(Double::compareTo).orElseThrow().floatValue()-entityBoundary.getSize().getHeight());
		} else if (collidingSideIndex>0) { //top collision
			return collidingTiles.stream().map((e)->e.getLabel().getBounds().getMaxY())
					.filter((e)->e<entityBoundary.getMaxY())
					.max(Double::compareTo).orElseThrow().floatValue();
		} else {
			return (float) entityBoundary.getY();
		}
	}

	/**
	 *This method checks from where is an {@code Entity} with respect of its vertical axis, it is considered that the
	 * {@code Entity} collides from the top or the bottom when there is more {@code AbstractTile} elements colliding from
	 * the top than from the bottom or the other way around, and it is considered that it is not colliding
	 * from the top or the bottom when both have the same number of colliding {@code AbstractTile} elements
	 * @param entityBoundary the {@code Rectangle} obtained from the {@code JLabel} obtained from the moving entity
	 * @param collidingTiles the {@code AbstractTile} elements which collide with the entity
	 * @return a positive long if the entity collides on its top, a negative value if it collides on its bottom, and zero
	 * if it does not collide or collides from top and bottom the same number of times
	 */
	public static long checkVerticalCollision(Rectangle entityBoundary, List<AbstractTile> collidingTiles){
		long bottomColliding = collidingTiles.stream()
				.map(AbstractTile::getLabel).filter((e)->e.getBounds().getMaxY()>entityBoundary.getMaxY()).count();
		long topColliding = collidingTiles.stream()
				.map(AbstractTile::getLabel).filter((e)->e.getBounds().getMinY()<entityBoundary.getMinY()).count();
		return topColliding-bottomColliding;
	}

	/**
	 *This method checks from where is an {@code Entity} with respect of its horizontal axis, it is considered that the
	 * {@code Entity} collides from the right or the left when there is more {@code AbstractTile} elements colliding from
	 * the top than from the bottom or the other way around, and it is considered that it is not colliding
	 * from the right or the left when both have the same number of colliding {@code AbstractTile} elements
	 * @param entityBoundary the {@code Rectangle} obtained from the {@code JLabel} obtained from the moving entity
	 * @param collidingTiles the {@code AbstractTile} elements which collide with the entity
	 * @return a positive long if the entity collides on its right, a negative value if it collides on its left, and zero
	 * if it does not collide or collides from top and bottom the same number of times
	 */
	public static long checkHorizontalCollision(Rectangle entityBoundary, List<AbstractTile> collidingTiles){
		//works alright
		long rightColliding= collidingTiles.stream().
				map(AbstractTile::getLabel).filter((e)->e.getBounds().getMinX()>entityBoundary.getMinX()).count();
		long leftColliding= collidingTiles.stream()
				.map(AbstractTile::getLabel).filter((e)->e.getBounds().getMaxX()<entityBoundary.getMaxX()).count();
		return rightColliding-leftColliding;
	}

	public static List<AbstractTile> getCollidingTiles(Rectangle entityBoundary, Entity entity){
		return Display.retrieveTiles().values().stream()
				.filter((e)->e!=entity).filter(AbstractTile::isSolid)
				.filter(e -> e.getLabel().getBounds().intersects(entityBoundary)).toList();
	}

	private static double getYBasedOnX(double finalX,double initialX,double xSpeed,double initialY,double gravity,double ySpeed){
		double deltaTime= (finalX-initialX)/xSpeed;
		if((deltaTime>0)) {
			return initialY + (ySpeed * deltaTime) + (gravity * deltaTime * deltaTime / 2);
		} else{
			return initialY;
		}
	}
	private static double getXBasedOnY(double finalY,double initialY,double ySpeed,double initialX,double gravity,double xSpeed){
		double negativeDeltaY = (initialY - finalY);
		double root = Math.sqrt(((ySpeed * ySpeed) - (2 * (negativeDeltaY) * gravity * Display.getUnitValue())));
		double firstDeltaTime = ((ySpeed * (-1)) + root) / (2 * (negativeDeltaY));
		double secondDeltaTime = (((-1) * ySpeed) - root) / (2 * (negativeDeltaY));
		double result;
		if ((!(firstDeltaTime>0)||(firstDeltaTime>secondDeltaTime))&&secondDeltaTime>0&&Math.abs(ySpeed)<150) {
			result= initialX+(xSpeed*secondDeltaTime);
		} else if (negativeDeltaY !=0&&Math.abs(ySpeed)<150) {
			result= initialX+(xSpeed*firstDeltaTime);
		}else{
			result= initialX+(xSpeed*DisplayRefresh.getDeltaTime());
		}
		if (result >2000||result<-2000) {
			return initialX;
		}
		else{
			return result;
		}
	}

	public static double applyGravity(double ySpeed,double gravity,double time){
		return ySpeed + (gravity*time);
	}

	//setter and getter
	public static void setGlobalGravity(double gravity) {
		globalGravity = gravity;
	}
}

package io.github.engine;

import java.util.Arrays;

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
		double gravity;
		//define gravity to be used
		if (entity.isUsingLocalGravity()) {
			gravity = entity.getGravity();
		} else {
			gravity = globalGravity;
		}
		//speed is changed according to gravity
		double newYSpeed;
		float idealY;
		newYSpeed=applyGravity(entity.getYSpeed(), gravity, deltaTime);
		idealY=(float) (entity.getUnitY() + ((entity.getYSpeed() * deltaTime) + (gravity * deltaTime * deltaTime) / 2));
		entity.setYSpeed(newYSpeed);
		//the new location is calculated
		double newXSpeed= applyGravity(entity.getXSpeed(),entity.getxAcceleration(),deltaTime);
		float idealX = (float) (entity.getUnitX() + ((entity.getXSpeed() * deltaTime)+(entity.getxAcceleration() * deltaTime * deltaTime) / 2));
		entity.setXSpeed(newXSpeed);
		//the expected future boundary is calculated
		Boundary expectedEntityBoundary = entity.getBounds();
		expectedEntityBoundary.setLocation(idealX,idealY);
		AbstractTile[] collidingTiles=getCollidingTiles(expectedEntityBoundary,entity);
		if(collidingTiles.length>0){
			shortenTrajectory(entity,expectedEntityBoundary,collidingTiles,gravity);
		} else {
			entity.setBottomLocked(false);
			entity.setTopLocked(false);
			entity.setRightLocked(false);
			entity.setLeftLocked(false);
			entity.setLocation(idealX, idealY);
		}
	}
	public static void shortenTrajectory(Entity entity, Boundary expectedBoundary,AbstractTile[] collidingTiles, double gravity){
		int horizontalCollisions=checkHorizontalCollision(expectedBoundary,collidingTiles);
		int verticalCollisions=checkVerticalCollision(expectedBoundary,collidingTiles);
		float newX,newY;
		if (horizontalCollisions!=0&&verticalCollisions!=0){
			newX=getShortenedX(horizontalCollisions,collidingTiles,expectedBoundary,entity);
			newY=getShortenedY(verticalCollisions,collidingTiles,expectedBoundary,entity);
		} else if(horizontalCollisions==0){
			newY=getShortenedY(verticalCollisions,collidingTiles,expectedBoundary,entity);
			float dT=deltaTimeBasedOnMovement(entity.getUnitY(),newY,entity.getYSpeed(),gravity);
			newX=calculateUARM(entity.getUnitX(),entity.getXSpeed(),entity.getxAcceleration(),dT);
		} else {
			newX = getShortenedX(horizontalCollisions, collidingTiles, expectedBoundary, entity);
			float dT=deltaTimeBasedOnMovement(entity.getUnitX(),newX,entity.getXSpeed(),entity.getxAcceleration());
			newY=calculateUARM(entity.getUnitY(),entity.getYSpeed(),gravity,dT);
		}
		entity.setLocation(newX,newY);
		applyEntityCollision(entity,expectedBoundary,newX,newY,verticalCollisions,horizontalCollisions);
	}
	public static void applyEntityCollision(Entity entity, Boundary expectedBoundary, float newX, float newY,float vCIndex, float hCIndex){
		if(newX!=expectedBoundary.getUnitX()&&hCIndex!=0&&!entity.isRightLocked()&&!entity.isLeftLocked()) {
			entity.setXSpeed(0);
			if (hCIndex > 0) {
				entity.setRightLocked(true);
				entity.setLeftLocked(false);
			} else {
				entity.setLeftLocked(true);
				entity.setRightLocked(false);
			}
		}
		if(newY!=expectedBoundary.getUnitY()&&vCIndex!=0&&!entity.isBottomLocked()&&!entity.isTopLocked()){
			entity.setYSpeed(0);
			if(hCIndex>0) {
				entity.setTopLocked(true);
				entity.setBottomLocked(false);
			} else {
				entity.setBottomLocked(true);
				entity.setTopLocked(false);
			}
		}
	}
	public static float calculateUARM(float initialA, double aSpeed, double aAccel,float deltaTime){
		return (float) (initialA+(aSpeed*deltaTime)+(aAccel/2)*deltaTime*deltaTime);
	}
	public static float deltaTimeBasedOnMovement(float initialB,float finalB,double bSpeed,double bAccel){
		float deltaB=initialB-finalB;
		float discriminant= (float) ((bSpeed*bSpeed)-(2* deltaB *bAccel));
		if(bAccel==0){
			bAccel=1;
		}
		float deltaTime = (float) (((bSpeed*(-1))-Math.sqrt(discriminant))/(bAccel));
		if((discriminant>0)) {
			float newDTime = (float) (((bSpeed * (-1)) + Math.sqrt(discriminant))/(bAccel));
			if((((newDTime<deltaTime)&&(newDTime>0))||(deltaTime<0&&!(newDTime<0)))&&deltaB!=0){
				deltaTime=newDTime;
			} else {
				deltaTime=DisplayRefresh.getDeltaTime();
			}
		} else {
			deltaTime=DisplayRefresh.getDeltaTime();
		}
		return deltaTime;
	}

	public static float getShortenedX(int collisionIndex, AbstractTile[] collidingTiles, Boundary entityBoundary, Entity entity){
		if(collisionIndex>0&&entity.getXSpeed()>0&&!entity.isRightLocked()){
			return Arrays.stream(collidingTiles).map(AbstractTile::getBounds)
					.map(Boundary::getMinX).min(Double::compare).orElseThrow()- entityBoundary.getUnitWidth();
		} else if(collisionIndex<0&&entity.getXSpeed()<0&&!entity.isLeftLocked()) {
			return Arrays.stream(collidingTiles).map(AbstractTile::getBounds)
					.map(Boundary::getMaxX).max(Double::compare).orElseThrow();
		} else if(entity.isRightLocked()||entity.isLeftLocked()) {
			return entity.getUnitX();
		} else {
			return entityBoundary.getUnitX();
		}
	}
	public static float getShortenedY(int collisionIndex, AbstractTile[] collidingTiles, Boundary entityBoundary,Entity entity){
		if(collisionIndex>0&&entity.getYSpeed()<0&&!entity.isTopLocked()){
			return Arrays.stream(collidingTiles).map(AbstractTile::getBounds)
					.map(Boundary::getMaxY).max(Double::compare).orElseThrow();
		} else if(collisionIndex<0&&entity.getYSpeed()>0&&!entity.isBottomLocked()){
			return Arrays.stream(collidingTiles).map(AbstractTile::getBounds)
					.map(Boundary::getMinY).min(Double::compare).orElseThrow()-entityBoundary.getUnitHeight();
		} else if(entity.isBottomLocked()||entity.isTopLocked()){
			return entity.getUnitY();
		} else {
			return entityBoundary.getUnitY();
		}
	}
	public static int checkHorizontalCollision(Boundary entityExpectedBoundary, AbstractTile[] collidingTiles){
		long leftCollisions=Arrays.stream(collidingTiles)
				.map(AbstractTile::getBounds).filter((e)->entityExpectedBoundary.getMinX()>e.getMinX()).count();
		long rightCollisions=Arrays.stream(collidingTiles)
				.map(AbstractTile::getBounds).filter((e)->entityExpectedBoundary.getMaxX()<e.getMaxX()).count();
		return (int) (rightCollisions-leftCollisions);
	}
	public static int checkVerticalCollision(Boundary entityExpectedBoundary,AbstractTile[] collidingTiles){
		long topCollisions=Arrays.stream(collidingTiles).map(AbstractTile::getBounds)
				.filter((e)->entityExpectedBoundary.getMinY()>e.getMinY()&&entityExpectedBoundary.getMinY()<e.getMaxY())
				.count();
		long bottomCollisions =Arrays.stream(collidingTiles)
				.map(AbstractTile::getBounds).filter((e)->entityExpectedBoundary.getMaxY()<e.getMaxY()).count();
		return (int) (topCollisions -bottomCollisions);
	}

	public static AbstractTile[] getCollidingTiles(Boundary expectedBoundary,Entity entity){
		return Display.retrieveTiles().values().stream()
				.filter((e)-> expectedBoundary.intersects(e.getBounds()))
				.filter((e)->!e.equals(entity)).toArray(AbstractTile[]::new);
	}

	public static double applyGravity(double ySpeed,double gravity,double time){
		return ySpeed + (gravity*time);
	}

	//setter and getter
	public static void setGlobalGravity(double gravity) {
		globalGravity = gravity;
	}
}

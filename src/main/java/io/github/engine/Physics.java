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
		double newYSpeed = applyGravity(entity.getYSpeed(), gravity, deltaTime);
		float idealY=(float) (entity.getUnitY() + ((entity.getYSpeed() * deltaTime) + (gravity * deltaTime * deltaTime) / 2));;
		entity.setYSpeed(newYSpeed);
		//the new location is calculated
		float idealX = (float) (entity.getUnitX() + entity.getXSpeed() * deltaTime);
		//the expected future boundary is calculated
		Boundary expectedEntityBoundary = entity.getBounds();
		expectedEntityBoundary.setLocation(idealX,idealY);
		AbstractTile[] collidingTiles=getCollidingTiles(entity);
		System.out.println(posABasedOnB(entity.getUnitX(),entity.getUnitY(),expectedEntityBoundary.getMinY(),entity.getXSpeed(),entity.getYSpeed(),0,gravity));
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
			System.out.println("sB");
			newX=getShortenedX(horizontalCollisions,collidingTiles,expectedBoundary,entity.getXSpeed());
			newY=getShortenedY(verticalCollisions,collidingTiles,expectedBoundary,entity.getYSpeed());
		} else if(horizontalCollisions==0){
			System.out.println("sY");
			newY=getShortenedY(verticalCollisions,collidingTiles,expectedBoundary,entity.getYSpeed());
			System.out.println("newY: "+newY);
			newX=posABasedOnB(entity.getUnitX(),entity.getUnitY(),newY,entity.getXSpeed(),entity.getYSpeed(),0,gravity);
		} else {
			System.out.println("sX");
			newX=getShortenedX(horizontalCollisions,collidingTiles,expectedBoundary,entity.getXSpeed());
			newY=posABasedOnB(entity.getUnitY(),entity.getUnitX(),newX,entity.getYSpeed(),entity.getXSpeed(),gravity,0);
		}
		if(entity.isBottomLocked()&&entity.getYSpeed()>0||entity.isTopLocked()&&entity.getYSpeed()<0){
			entity.setLocation(newX,entity.getUnitY());
		} else if (entity.isRightLocked()&&entity.getXSpeed()>0||entity.isLeftLocked()&&entity.getXSpeed()<0){
			entity.setLocation(entity.getUnitX(),newY);
		}else {
			entity.setLocation(newX,newY);
		}
		applyEntityCollision(entity,expectedBoundary,newX,newY,verticalCollisions,horizontalCollisions);
	}
	public static void applyEntityCollision(Entity entity, Boundary expectedBoundary, float newX, float newY,float vCIndex, float hCIndex){
		if(newX!=expectedBoundary.getUnitX()&&hCIndex!=0) {
			entity.setXSpeed(0);
			if (hCIndex > 0) {
				entity.setRightLocked(true);
			} else {
				entity.setLeftLocked(true);
			}
		}
		if(newY!=expectedBoundary.getUnitY()&&vCIndex!=0){
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
	public static float posABasedOnB(float initialA, float initialB, float finalB, double aSpeed, double bSpeed,double aAccel,double bAccel){
		float negativeDeltaB=initialB-finalB;

		float discriminant= (float) ((bSpeed*bSpeed)-(2*negativeDeltaB*bAccel));
		if(bAccel==0){
			bAccel=1;
		}
		float deltaTime = (float) (((bSpeed*(-1))-Math.sqrt(discriminant))/(bAccel));
		if(discriminant==0){
			deltaTime =0;
		}else if((discriminant>0)) {
			float newDTime = (float) (((bSpeed * (-1)) + Math.sqrt(discriminant))/(bAccel));
			if(((newDTime<deltaTime)&&(newDTime>0))||(deltaTime<0&&!(newDTime<0))){
				deltaTime=newDTime;
			} else {
				deltaTime=DisplayRefresh.getDeltaTime();
			}
		} else {
			deltaTime=DisplayRefresh.getDeltaTime();
		}
		return (float) (initialA+(aSpeed*deltaTime)+(aAccel/2)*deltaTime*deltaTime);
	}
	public static float getShortenedX(int collisionIndex, AbstractTile[] collidingTiles, Boundary entityBoundary, double xSpeed){
		if(collisionIndex>0&&xSpeed>0){
			return Arrays.stream(collidingTiles).map(AbstractTile::getBounds)
					.map(Boundary::getMinX).min(Double::compare).orElseThrow()- entityBoundary.getUnitWidth();
		} else if(collisionIndex<0&&xSpeed<0) {
			return Arrays.stream(collidingTiles).map(AbstractTile::getBounds)
					.map(Boundary::getMaxX).max(Double::compare).orElseThrow();
		} else {
			return entityBoundary.getUnitX();
		}
	}
	public static float getShortenedY(int collisionIndex, AbstractTile[] collidingTiles, Boundary entityBoundary,double ySpeed){
		if(collisionIndex>0&&ySpeed<0){
			return Arrays.stream(collidingTiles).map(AbstractTile::getBounds)
					.map(Boundary::getMaxY).max(Double::compare).orElseThrow();
		} else if(collisionIndex<0&&ySpeed>0){
			return Arrays.stream(collidingTiles).map(AbstractTile::getBounds)
					.map(Boundary::getMinY).min(Double::compare).orElseThrow()-entityBoundary.getUnitHeight();
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
		System.out.println(collidingTiles.length);
		long topCollisions=Arrays.stream(collidingTiles)
				.map(AbstractTile::getBounds).filter((e)->entityExpectedBoundary.getMinY()>e.getMinY()).count();
		long bottomCollisions =Arrays.stream(collidingTiles)
				.map(AbstractTile::getBounds).filter((e)->entityExpectedBoundary.getMaxY()<e.getMaxY()).count();
		return (int) (topCollisions -bottomCollisions);
	}

	public static AbstractTile[] getCollidingTiles(Entity entity){
		Boundary eBounds=entity.getBounds();
		return Display.retrieveTiles().values().stream()
				.filter((e)-> eBounds.intersects(e.getBounds())).toArray(AbstractTile[]::new);
	}

	public static double applyGravity(double ySpeed,double gravity,double time){
		return ySpeed + (gravity*time);
	}

	//setter and getter
	public static void setGlobalGravity(double gravity) {
		globalGravity = gravity;
	}
}

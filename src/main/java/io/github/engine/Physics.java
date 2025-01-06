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
		double deltaTime = Synchronization.getDeltaTime();
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
		AbstractTile[] validCollidingTiles=reduceCollidingTiles(entity,collidingTiles);
		if(validCollidingTiles.length>0){
			shortenTrajectory(entity,expectedEntityBoundary,validCollidingTiles,gravity);
		} else {
			entity.setBottomLocked(false);
			entity.setTopLocked(false);
			entity.setRightLocked(false);
			entity.setLeftLocked(false);
			entity.setLocation(idealX, idealY);
		}
	}
	public static void shortenTrajectory(Entity entity, Boundary expectedBoundary,AbstractTile[] collidingTiles, double gravity){
		int horizontalCollisions=checkHorizontalCollision(expectedBoundary,collidingTiles, entity);
		int verticalCollisions=checkVerticalCollision(expectedBoundary,collidingTiles, entity);
		float newX,newY;
		boolean verticallyLocked= entity.isBottomLocked() || entity.isTopLocked();
		boolean horizontallyLocked= entity.isLeftLocked() || entity.isRightLocked();
		if ((horizontalCollisions!=0||horizontallyLocked)&&(verticalCollisions!=0||verticallyLocked)){
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
		applyCollisionPhysicsToEntity(entity,expectedBoundary,newX,newY,verticalCollisions,horizontalCollisions);
		Arrays.stream(collidingTiles).forEach((e)->callCollisionEvents(e,entity,verticalCollisions,horizontalCollisions));
	}
	public static void applyCollisionPhysicsToEntity(Entity entity, Boundary expectedBoundary, float newX, float newY, float vCIndex, float hCIndex){
		AbstractTile[] collidingTiles=getCollidingTiles(expectedBoundary,entity);
		boolean hEdge= Arrays.stream(collidingTiles).anyMatch((e)->checkEntityHorizontallyOnEdge(entity,e));
		boolean vEdge= Arrays.stream(collidingTiles).anyMatch((e)->checkEntityVerticallyOnEdge(entity,e));
		if(newX!=expectedBoundary.getUnitX()&&hCIndex!=0&&!entity.isRightLocked()&&!entity.isLeftLocked()) {
			entity.setXSpeed(0);
			if (hCIndex > 0) {
				entity.setRightLocked(true);
				entity.setLeftLocked(false);
			} else {
				entity.setLeftLocked(true);
				entity.setRightLocked(false);
			}
		} else if (newX!=expectedBoundary.getUnitX()&&hCIndex!=0&&(entity.isLeftLocked()||entity.isRightLocked())&&!hEdge){
			entity.setXSpeed(0);
		} else if (newX!=expectedBoundary.getUnitX()&&(hCIndex==0||hEdge)&&(entity.isLeftLocked()||entity.isRightLocked())){
			entity.setXSpeed(0);
			entity.setRightLocked(false);
			entity.setLeftLocked(false);
		}
		if(newY!=expectedBoundary.getUnitY()&&vCIndex!=0&&!entity.isBottomLocked()&&!entity.isTopLocked()){
			entity.setYSpeed(0);
			if(vCIndex>0) {
				entity.setTopLocked(true);
				entity.setBottomLocked(false);
			} else {
				entity.setBottomLocked(true);
				entity.setTopLocked(false);
			}
		} else if (newY!=expectedBoundary.getUnitY()&&vCIndex!=0&&(entity.isBottomLocked()|| entity.isTopLocked())&&!vEdge) {
			entity.setYSpeed(0);
		} else if (newY!=expectedBoundary.getUnitY()&&(vCIndex==0||vEdge)&&(entity.isBottomLocked()|| entity.isTopLocked())) {
			entity.setYSpeed(0);
			entity.setTopLocked(false);
			entity.setRightLocked(false);
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
				deltaTime=Synchronization.getDeltaTime();
			}
		} else {
			deltaTime=Synchronization.getDeltaTime();
		}
		return deltaTime;
	}

	public static float getShortenedX(int collisionIndex, AbstractTile[] collidingTiles, Boundary entityBoundary, Entity entity){
		AbstractTile[] validCollidingTiles= reduceHorizontallyCollidingTiles(entity,collidingTiles);
		boolean entityOnTop= Arrays.stream(validCollidingTiles)
				.anyMatch((e)->e.getBounds().getMinY()>=entity.getBounds().getMaxY());
		boolean entityOnBottom= Arrays.stream(validCollidingTiles)
				.anyMatch((e)->e.getBounds().getMaxY()<=entity.getBounds().getMinY());
		double xSpeed=entity.getXSpeed();
		if(collisionIndex>0&&entity.getXSpeed()>0&&!entity.isRightLocked()&&(!entityOnTop&&!entityOnBottom)){
			return Arrays.stream(validCollidingTiles).map(AbstractTile::getBounds)
					.map(Boundary::getMinX).max(Double::compare).orElseThrow() - entityBoundary.getUnitWidth();
		} else if(collisionIndex<0&&entity.getXSpeed()<0&&!entity.isLeftLocked()&&(!entityOnTop&&!entityOnBottom)) {
			return Arrays.stream(validCollidingTiles).map(AbstractTile::getBounds)
					.map(Boundary::getMaxX).min(Double::compare).orElseThrow();
		} else if((entity.isRightLocked()&&collisionIndex>=0&&xSpeed>0)||(entity.isLeftLocked()&&collisionIndex<=0&&xSpeed<0)) {
			return entity.getUnitX();
		} else {
			return entityBoundary.getUnitX();
		}
	}
	public static float getShortenedY(int collisionIndex, AbstractTile[] collidingTiles, Boundary entityBoundary,Entity entity){
		AbstractTile[] validCollidingTiles = reduceVerticallyCollidingTiles(entity,collidingTiles);
		boolean entityOnLeft= Arrays.stream(validCollidingTiles)
				.anyMatch((e)->e.getBounds().getMinX()>=entity.getBounds().getMaxX());
		boolean entityOnRight= Arrays.stream(validCollidingTiles)
				.anyMatch((e)->e.getBounds().getMaxX()<=entity.getBounds().getMinX());
		if(collisionIndex>0&&entity.getYSpeed()<0&&!entity.isTopLocked()&&(!entityOnRight&&!entityOnLeft)){
			return Arrays.stream(collidingTiles).map(AbstractTile::getBounds)
					.map(Boundary::getMaxY).min(Double::compare).orElseThrow();
		} else if(collisionIndex<0&&entity.getYSpeed()>0&&!entity.isBottomLocked()&&(!entityOnRight&&!entityOnLeft)){
			return Arrays.stream(collidingTiles).map(AbstractTile::getBounds)
					.map(Boundary::getMinY).max(Double::compare).orElseThrow()-entityBoundary.getUnitHeight();
		} else if((entity.isBottomLocked()&&collisionIndex<=0)||(entity.isTopLocked()&&collisionIndex>=0)){ //(entity.isBottomLocked()&&collisionIndex<0)||(entity.isTopLocked()&&collisionIndex>0)
			return entity.getUnitY();
		} else {
			return entityBoundary.getUnitY();
		}
	}
	public static int checkHorizontalCollision(Boundary expectedBoundary, AbstractTile[] collidingTiles,Entity entity){
		AbstractTile[] tiles=reduceHorizontallyCollidingTiles(entity, collidingTiles);
		long leftCollisions=Arrays.stream(tiles)
				.map(AbstractTile::getBounds).filter((e)-> expectedBoundary.getMinX()>e.getMinX()).count();
		long rightCollisions=Arrays.stream(tiles)
				.map(AbstractTile::getBounds).filter((e)-> expectedBoundary.getMaxX()<e.getMaxX()).count();
		return (int) (rightCollisions-leftCollisions);
	}
	public static int checkVerticalCollision(Boundary entityExpectedBoundary,AbstractTile[] collidingTiles,Entity entity){
		AbstractTile[] tiles=reduceVerticallyCollidingTiles(entity, collidingTiles);
		long topCollisions=Arrays.stream(tiles).map(AbstractTile::getBounds)
				.filter((e)->entityExpectedBoundary.getMinY()>e.getMinY())
				.count();
		long bottomCollisions =Arrays.stream(tiles)
				.map(AbstractTile::getBounds).filter((e)->entityExpectedBoundary.getMaxY()<e.getMaxY()).count();
		return (int) (topCollisions -bottomCollisions);
	}

	public static AbstractTile[] getCollidingTiles(Boundary expectedBoundary,Entity entity){
		if(entity.isSolid()) {
			return Display.retrieveTiles().values().stream()
					.filter((e) -> expectedBoundary.intersects(e.getBounds()))
					.filter(AbstractTile::isSolid)
					.filter((e) -> !e.equals(entity)).toArray(AbstractTile[]::new);
		} else {
			return new AbstractTile[]{};
		}
	}

	public static double applyGravity(double ySpeed,double gravity,double time){
		return ySpeed + (gravity*time);
	}

	//setter and getter
	public static void setGlobalGravity(double gravity) {
		globalGravity = gravity;
	}
	public static void callCollisionEvents(AbstractTile subject, Entity object, int vCIndex, int hCIndex){
		if(vCIndex<0&& Arrays.asList(subject.getClass().getInterfaces()).contains(Collidable.class)){
			((Collidable)subject).topCollision(object);
		}
	}
	public static boolean checkEntityHorizontallyOnEdge(Entity entity, AbstractTile collidingTile){
		boolean entityOnTop= collidingTile.getBounds().getMinY()>=entity.getBounds().getMaxY();
		boolean entityOnBottom= collidingTile.getBounds().getMaxY()<=entity.getBounds().getMinY();
		return entityOnBottom||entityOnTop;
	}
	public static boolean checkEntityVerticallyOnEdge(Entity entity, AbstractTile collidingTile){
		boolean entityOnLeft= collidingTile.getBounds().getMinX()>=entity.getBounds().getMaxX();
		boolean entityOnRight= collidingTile.getBounds().getMaxX()<=entity.getBounds().getMinX();
		return entityOnLeft||entityOnRight;
	}
	public static AbstractTile[] reduceHorizontallyCollidingTiles(Entity entity, AbstractTile[] collidingTiles){
		AbstractTile[] tiles = Arrays.stream(collidingTiles)
				.filter((e)->!checkEntityHorizontallyOnEdge(entity,e)).toArray(AbstractTile[]::new);
		if(tiles.length==0){
			tiles=collidingTiles;
		}
		return tiles;
	}
	public static AbstractTile[] reduceVerticallyCollidingTiles(Entity entity, AbstractTile[] collidingTiles){
		AbstractTile[] tiles = Arrays.stream(collidingTiles)
				.filter((e)->!checkEntityVerticallyOnEdge(entity,e)).toArray(AbstractTile[]::new);
		if(tiles.length==0){
			tiles=collidingTiles;
		}
		return tiles;
	}
	
	public static AbstractTile[] reduceCollidingTiles(Entity entity, AbstractTile[] collidingTiles){
		AbstractTile[] tiles = Arrays.stream(collidingTiles)
				.filter((e)->!checkEntityVerticallyOnEdge(entity,e)||!checkEntityHorizontallyOnEdge(entity,e))
				.toArray(AbstractTile[]::new);
		if(tiles.length==0){
			tiles=collidingTiles;
		}
		return tiles;
	}
}

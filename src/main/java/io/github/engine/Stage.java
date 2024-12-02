package io.github.engine;

import io.github.game.Tile;
import java.util.HashMap;

public class Stage {
	HashMap<String, Entity> entities =new HashMap<>();
	HashMap<String, Tile> tiles=new HashMap<>();
	//width and height in units
	int width;
	int height;

	public Stage(int width,int height){
		this.width=width;
		this.height=height;
	}

	public void addEntity(Entity entity,String id){
		entities.put(id,entity);
	}
	public void addTile(Tile tile,String id){
		tiles.put(id,tile);
	}

	public HashMap<String,Entity> getEntitiesBetween(int initialX,int initialY, int finalX,int finalY){
		HashMap<String, Entity> localEntities=new HashMap<>();
		entities.entrySet().stream().filter((e)->e.getValue().getUnitX()<finalX && e.getValue().getUnitX()>initialX)
				.filter((e)->e.getValue().getUnitY()<finalY && e.getValue().getUnitY()>initialY)
				.forEach((e)->localEntities.put(e.getKey(),e.getValue()));
		return localEntities;
	}

	public HashMap<String, Tile> getTilesBetween(int initialX,int initialY,int finalX,int finalY){
		HashMap<String, Tile> localTiles=new HashMap<>();
		tiles.entrySet().stream().filter((e)->e.getValue().getUnitX()<finalX && e.getValue().getUnitX()>initialX)
				.filter((e)->e.getValue().getUnitY()<finalY && e.getValue().getUnitY()>initialY)
				.forEach((e)->localTiles.put(e.getKey(),e.getValue()));
		return localTiles;
	}
}
package io.github.engine;

import java.util.HashMap;

public abstract class AbstractLevel {
	HashMap<String,AbstractTile> tiles = new HashMap<>();

	public HashMap<String, AbstractTile> getTilesBetween(Boundary displayBoundary,int addedUnitWidth) {
		HashMap<String,AbstractTile> filteredTiles = new HashMap<>();
		tiles.entrySet().stream().filter((e)->e.getValue().getBounds().getMaxX()<displayBoundary.getMaxX()+addedUnitWidth)
				.filter((e)->e.getValue().getBounds().getMinX()>displayBoundary.getMinX()-addedUnitWidth)
				.forEach((e)->filteredTiles.put(e.getKey(),e.getValue()));
		return filteredTiles;
	}
	public void addTiles(String id, AbstractTile tile){
		tiles.put(id,tile);
	}
	public int getNumberOfTiles(){
		return tiles.size();
	}
}

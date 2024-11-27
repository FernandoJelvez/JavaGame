package io.github.gameMakerTool;

import io.github.engine.AbstractTile;
import io.github.engine.Display;
import io.github.engine.Entity;
import io.github.engine.Stage;
import io.github.game.Tile;

import java.awt.*;
import java.util.HashMap;

public class Tool {
	HashMap<String, Tile> tiles = new HashMap<>();
	HashMap<String, Entity> entities = new HashMap<>();
	private Stage stage;
	public Tool(Stage stage){
		this.stage=stage;
	}

	public void placeTile(String id, Point point) throws NotSuchTileException {
		Tile tile;
		if(tiles.containsKey(id)) {
			tile = tiles.get(id);
		} else {
			throw new NotSuchTileException();
		}
	}

	public void placeEntity(String id, Point point, int xSpeed,int ySpeed) throws NotSuchTileException {
		Entity entity;
		if(entities.containsKey(id)) {
			entity = entities.get(id);
		} else {
			throw new NotSuchTileException();
		}
	}
	public void placeEntity(String id, Point point) throws NotSuchTileException {
		Entity entity;
		if(entities.containsKey(id)) {
			entity = entities.get(id);
		} else {
			throw new NotSuchTileException();
		}
	}

	public void fillRectangle(String id, Point startingPoint, Point endingPoint) throws NotSuchTileException,ArithmeticException {
		int tilesCount=0;
		validateFill(id,startingPoint,endingPoint);
		Tile tile = tiles.get(id);
		int tileWidth=tile.getWidth();
		int tileHeight=tile.getHeight();
		int height= (int) (tileHeight*(startingPoint.getY()-endingPoint.getY()));
		int width= (int) (tileHeight*(startingPoint.getY()-endingPoint.getY()));
		if(startingPoint.getX()==endingPoint.getX()){
			width=1;
		} else if (startingPoint.getY()==endingPoint.getY()) {
			height=0;
		}
		for (int x =(int) startingPoint.getX(); x < width; x+= tileWidth) {
			for (int y = (int) startingPoint.getY(); y < height; y+=tileHeight) {
				stage.addTile(tile,id+"_"+tilesCount);
				tilesCount++;
			}
		}
	}

	public void fillStair(String id, int startingXCoordinate,int startingYCoordinate,int widthInTiles,String orientation)
			throws NotSuchTileException,ArithmeticException {
		Tile tile;
		if(tiles.containsKey(id)) {
			tile = tiles.get(id);
		} else {
			throw new NotSuchTileException();
		}

		if (widthInTiles==0){
			throw new ArithmeticException("Width can not be zero, use place instead");
		}
		for (int i = startingXCoordinate; i < 5; i++) {
			
		}
	}
	public void addTile(String id, Tile tile){
		tiles.put(id,tile);
	}

	public void setStage(Stage stage){
		this.stage=stage;
	}
	private void validateFill(String id, Point startingPoint, Point endingPoint) throws NotSuchTileException {
		if(!tiles.containsKey(id)){
			throw new NotSuchTileException();
		} else if (startingPoint.equals(endingPoint)) {
			throw new RuntimeException("starting point cannot be the same as the ending point, use place instead");
		}

	}
}

package io.github.gameMakerTool;


import io.github.engine.Display;
import io.github.engine.Stage;
import io.github.game.Tile;

public class Main {

	public static void main(String[] args) {
		Tool tool = new Tool(new Stage(200,60));
		for(int i=0;i<10;i++){
			tool.addTile("Tile_"+i,new Tile(10,10,10,10,true,0));
		}
		ToolUserInterface.startTool(new ExceptionManager(),tool);
	}
}

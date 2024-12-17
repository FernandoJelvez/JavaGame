package io.github.game;

import io.github.engine.Display;
import io.github.engine.DisplayRefresh;
import io.github.engine.Physics;
import io.github.engine.Song;

public class Main {
	public static void main(String[] args) {
		Display.setup("gameTest",800,600);
		CatPlayer player = new CatPlayer(5,10,4,6,true,0);
		player.setColor(0,0,0);
		Display.setPlayer(player);
		Display.start();
		Physics.setGlobalGravity(8);
		player.setXSpeed(8);
		Display.addToBuffer(player,"Player_1");
		Tile tile = new Tile(2,28,10,4,true,0);
		Tile tile2 = new Tile(0,50,40,20,true,0);
		Tile tile3 = new Tile(2,38,40,4,true,0);
		tile.setColor(255,0,0);
		tile2.setColor(255,0,0);
		tile3.setColor(255,0,0);
		Display.addToBuffer(tile,"Block");
		Display.addToBuffer(tile2,"Block_2");
		Display.addToBuffer(tile3,"Block_3");
		DisplayRefresh.startClock(60);
	}
}

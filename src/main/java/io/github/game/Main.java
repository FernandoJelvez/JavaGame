package io.github.game;

import io.github.engine.Display;
import io.github.engine.DisplayRefresh;
import io.github.engine.Physics;
import io.github.engine.Song;

public class Main {
	public static void main(String[] args) {
		Display.setup("gameTest",800,600);
		Player player = new Player(5,20,2,3,true,0);
		player.setColor(0,0,0);
		Display.setPlayer(player);
		Display.start();
		Physics.setGlobalGravity(8);
		player.setXSpeed(8);
		Display.addToBuffer(player,"Player_1");
		Tile tile = new Tile(2,30,10,2,true,0);
		Tile tile2 = new Tile(0,50,40,10,true,0);
		Tile tile3 = new Tile(2,45,40,2,true,0);
		tile.setColor(255,0,0);
		tile2.setColor(255,0,0);
		tile3.setColor(255,0,0);
		Display.addToBuffer(tile,"Block");
		Display.addToBuffer(tile2,"Block_2");
		Display.addToBuffer(tile3,"Block_3");
		DisplayRefresh.startClock(60);
	}
}

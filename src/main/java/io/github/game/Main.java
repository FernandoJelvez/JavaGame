package io.github.game;

import io.github.engine.Display;
import io.github.engine.DisplayRefresh;
import io.github.engine.Physics;
import io.github.engine.Song;

public class Main {
	public static void main(String[] args) {
		Display.setup("gameTest",800,600);
		Display.start();
		Physics.setGlobalGravity(15);
		Player player = new Player(5,20,1,1,true,0);
		player.setColor(0,0,0);
		player.setYSpeed(-20);
		player.setXSpeed(0);
		Display.addToBuffer(player,"Player_1");
		Tile tile = new Tile(2,10,10,2,true,0);
		Tile tile2 = new Tile(12,10,2,20,true,0);
		Tile tile3 = new Tile(2,30,10,2,true,0);
		tile.setColor(255,0,0);
		tile2.setColor(255,0,0);
		tile3.setColor(255,0,0);
		Display.addToBuffer(tile,"Block");
		Display.addToBuffer(tile2,"Block_2");
		Display.addToBuffer(tile3,"Block_3");
		Song song = new Song("C:\\Users\\Alumno\\Downloads\\wow2.wav");
		song.setVolumePercentage(100);
		song.loop();
		song.start();
		DisplayRefresh.startClock(60);
	}
}

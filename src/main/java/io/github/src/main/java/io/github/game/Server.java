package io.github.game;

import io.github.engine.*;
import io.github.engine.connectivity.Connection;

import java.io.IOException;
import java.util.Scanner;

public class Server {
	static Scanner input = new Scanner(System.in);
	public static void main(String[] args) throws IOException {
		Display.setup("Server", 800, 600);
		Display.searchTexturesFromJson("presets/textures.json");
		Player player = new CatPlayer(2, 8, 4, 8, true, 2);
		player.setOpaque(true);
		player.setColor(0,255,0);
		Display.setControl(new Control(player));
		Tile background = new Tile(0,0,320,60,false,10);
		background.setColor(100,150,200);
		background.setOpaque(true);
		Display.addToBuffer("background",background);
		Display.start();
		Level l = new Level("nivel.json");
		l.loadLevel();
		Display.setLevel(l);
		Physics.setGlobalGravity(8);
		player.setXSpeed(8);
		Display.addToBuffer("Player 1", player);
		Tile wall = new Tile(-2,0,2,60,true,0);
		wall.setVisible(false);
		Display.addToBuffer("wall", wall);
		Connection connection;
		connection=new Connection(8080,new GameSocketProtocol());
		connection.start();

		Synchronization.startClock(60);
	}
}

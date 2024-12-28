package io.github.game;

import io.github.engine.*;
import io.github.engine.connectivity.Connection;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	static Scanner input = new Scanner(System.in);
	public static void main(String[] args) throws IOException {
		Display.setup("gameTest",800,600);
		Player player;
		System.out.println("1. dog, 2. cat");
		if (input.nextInt() == 1) {
			player = new DogPlayer(2, 8, 4, 8, true, 0);
		} else {
			player = new CatPlayer(2, 8, 4, 8, true, 0);
		}
		/*System.out.println("1.grassland");
		int w = input.nextInt();*/
		player.setColor(0,255,0);
		Display.setControl(new Control(player));
		Display.start();
		Physics.setGlobalGravity(8);
		player.setXSpeed(8);
		Display.addToBuffer(player,"Player_1");
		Tile tile = new Tile(4,28,10,4,true,1);
		Tile tile2 = new Tile(0,50,40,20,true,2);
		Tile tile3 = new Tile(4,38,40,4,true,3);
		Tile tile4 = new Tile(0,22,4,10,true,3);
		Tile tile5 = new Tile(42,38,40,4,true,3);
		Display.addToBuffer(tile4,"wall2");
		Display.addToBuffer(tile5, "wall5");
		Tile wall = new Tile(-2,0,2,60,true,0);
		wall.setVisible(false);
		tile.setColor(0,0,0);
		tile2.setColor(0,0,0);
		tile3.setColor(0,0,0);
		tile4.setColor(0,0,0);
		tile5.setColor(125,125,125);
		Platform platform = new Platform(32,22,10,2,true,4,4,20);
		Display.addToBuffer(tile,"Block");
		Display.addToBuffer(tile2,"Block_2");
		Display.addToBuffer(tile3,"Block_3");
		Display.addToBuffer(platform,"platform");
		Display.addToBuffer(wall,"wall");
		platform.setColor(0,0,255);
		Enemy enemy = new Enemy(12,12,4,4,true,0);
		Display.addToBuffer(enemy,"poomba");
		enemy.setColor(255,0,0);
		DisplayRefresh.startClock(60);
		/*Connection connection1 = new Connection(8888,new GameSocketProtocol());
		Connection connection2 = new Connection("localhost",8888,new GameSocketProtocol());
		connection1.start();
		connection2.start();*/

	}
}

package io.github.game;

import io.github.engine.*;
import io.github.engine.connectivity.Connection;

import javax.swing.*;
import java.io.IOException;
import java.util.Scanner;

public class Main {
	static Scanner input = new Scanner(System.in);
	public static void main(String[] args) throws IOException {
		int option=1;
		System.out.println("1. server, 2. client");
		option=input.nextInt();
		Display.searchTexturesFromJson("presets/textures.json");
		Player player;

		if (option==1) {
			player = new CatPlayer(2, 8, 4, 8, true, 3);
		} else {
			player = new DogPlayer(2, 8, 4, 8, true, 3);
		}
		player.setOpaque(true);
		player.setColor(0,255,0);
		Display.setControl(new Control(player));
		Display.start();
		Level l = new Level("nivel.json");
		l.loadLevel();
		Display.setLevel(l);
		Physics.setGlobalGravity(8);
		player.setXSpeed(8);
		Display.addToBuffer("Player_1", player);
		Tile wall = new Tile(-2,0,2,60,true,0);
		wall.setVisible(false);
		Display.addToBuffer("wall", wall);
		Connection connection;
		if (option==1){
			connection=new Connection(8080,new GameSocketProtocolS());
		} else {
			connection=new Connection("localhost",8080,new GameSocketProtocol());
		}
		connection.start();

		Synchronization.startClock(60);
	}
}

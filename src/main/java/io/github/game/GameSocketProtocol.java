package io.github.game;

import io.github.engine.AbstractTile;
import io.github.engine.Display;
import io.github.engine.Entity;
import io.github.engine.Player;
import io.github.engine.connectivity.DefaultPackage;
import io.github.engine.connectivity.Package;
import io.github.engine.connectivity.Protocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.Instant;
import java.util.HashMap;

public class GameSocketProtocol implements Protocol {
	@Override
	public Package processRequest(ObjectInputStream input) throws IOException, ClassNotFoundException {
		Object pack;
		pack=input.readObject();
		if(pack instanceof GamePackage) {
			applyDataToEngine((GamePackage) pack);
		} else if(pack != null){
			System.out.println(((Package)pack).getMessage());
		}
		return createAnswer();
	}
	public void applyDataToEngine(GamePackage defaultPackage){
		Entity player = processData(defaultPackage);
		if(Display.retrieveTile("Player_2")!=null) {
			Display.retrieveTile("Player_2").update(player);
			System.out.println("Diagnostic");
			System.out.println(player.isVisible());
			System.out.println(defaultPackage.getInstant().getEpochSecond());
		} else {
			Display.addToBuffer("Player 2",processData(defaultPackage));
		}
	}
	public GamePackage createAnswer(){
		Player player=Display.getPlayer();
		return new GamePackage("player",Instant.now(),player);
	}
	public Entity processData(GamePackage gamePackage){
		Entity player = gamePackage.getPackageEntity();
		player.setOpaque(gamePackage.isOpaque());
		player.setColor(gamePackage.getR(), gamePackage.getG(), gamePackage.getB());
		player.changeLayer(gamePackage.getLayer());
		player.setSize(gamePackage.getUnitWidth(), gamePackage.getUnitHeight());
		player.setLocation(gamePackage.getUnitX(),gamePackage.getUnitY());
		if(gamePackage.getTextureID()!=null&&Display.getTexture(gamePackage.getTextureID())!=null) {
			player.setTexture(Display.getTexture(gamePackage.getTextureID()));
		}
		player.setVisible(gamePackage.isVisible());
		return player;
	}
}

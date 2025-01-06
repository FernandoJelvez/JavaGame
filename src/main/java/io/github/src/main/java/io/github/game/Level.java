package io.github.game;

import com.google.gson.Gson;
import io.github.engine.AbstractLevel;
import io.github.engine.AbstractTile;
import io.github.engine.Tile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class Level extends AbstractLevel {
	private final String jsonPath;
	public Level(String jsonPath){
		this.jsonPath=jsonPath;
	}
	public void loadLevel() {
		try {
			Gson gson = new Gson();
			BufferedReader r = new BufferedReader(new FileReader(jsonPath));
			Tile[] tiles = gson.fromJson(r,Tile[].class);
			Arrays.stream(tiles).forEach((e)->{
					addTiles(e.getTextureID()+" "+getNumberOfTiles(),e);
			});
			r.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

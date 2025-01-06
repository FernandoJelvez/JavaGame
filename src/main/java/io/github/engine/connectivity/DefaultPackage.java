package io.github.engine.connectivity;

import io.github.engine.AbstractTile;
import io.github.engine.Boundary;
import io.github.engine.Entity;
import io.github.engine.Player;

import java.time.Instant;
import java.util.HashMap;

public class DefaultPackage extends Package {
	private final HashMap<String,AbstractTile> tiles;
	private final Player player;
	private final Boundary viewportBounds;
	public DefaultPackage(String message, Instant instant, HashMap<String, AbstractTile> tiles, Player player, Boundary viewportBounds) {
		super(message, instant);
		this.tiles = tiles;
		this.player = player;
		this.viewportBounds = viewportBounds;
	}

	public Entity getPlayer() {
		return player;
	}

	public HashMap<String, AbstractTile> getTiles() {
		return tiles;
	}
	public Boundary getViewportBounds() {
		return viewportBounds;
	}
}

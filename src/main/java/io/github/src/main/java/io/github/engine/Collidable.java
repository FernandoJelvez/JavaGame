package io.github.engine;

public interface Collidable {
	void topCollision(AbstractTile tile);
	void sideCollision(AbstractTile tile);
}

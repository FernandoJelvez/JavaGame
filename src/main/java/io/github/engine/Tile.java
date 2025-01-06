package io.github.engine;

public class Tile extends AbstractTile implements Collidable {
	public Tile(int width, int height, boolean solid, int layer) {
		super(width, height, solid, layer);
	}

	@Override
	public void refresh() {

	}

	public Tile(float x, float y, float width,float height, boolean solid, int layer) {
		super(width, height, solid, layer);
		setLocation(x,y);
	}


	//Collidable interface methods

	@Override
	public void topCollision(AbstractTile tile) {

	}

	@Override
	public void sideCollision(AbstractTile tile) {

	}
}

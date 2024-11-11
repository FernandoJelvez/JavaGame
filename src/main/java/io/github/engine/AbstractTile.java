package io.github.engine;


import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Point;

public abstract class AbstractTile extends JPanel implements Comparable<AbstractTile> {
	private int layer=0;
	private Color color;
	//int x,y,width,height;
	public AbstractTile(int width, int height,String id){
		super();
		super.setSize(width,height);
	}
	public AbstractTile(int x, int y, int width, int height,String id){
		super();
		super.setSize(width,height);
		super.setLocation(x,y);
	}

	public AbstractTile(int x, int y, Dimension d,String id) {
		super();
		super.setSize(d);
	}
	public AbstractTile(Point p, Dimension d,String id) {
		super();
		super.setSize(d);
	}

	/**
	 * sets the drawing level of the Tile
	 * @param layer is the new drawing level for the object, a greater level will be displayed on top of a
	 * Tile object with a smaller drawing level
	 */
	public void setLayer(int layer){
		if(layer>10||layer<0){
			throw new ArithmeticException("Layer value must be a value from 0 to 10");
		} else {
			this.layer =layer;
		}
	}

	public int getLayer(){
		return layer;
	}

	@Deprecated
	@Override
	public int compareTo(AbstractTile tile) {
		return layer - tile.getLayer();
	}
}

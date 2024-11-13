package io.github.engine;


import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Point;

public abstract class AbstractTile extends JPanel implements Comparable<AbstractTile> {
	private int layer;
	public AbstractTile(int width, int height,String id){
		super();
		super.setSize(width,height);
		layer=0;
	}
	public AbstractTile(int x, int y, int width, int height,String id){
		super();
		super.setSize(width,height);
		super.setLocation(x,y);
		layer=0;
	}

	public AbstractTile(Point p, Dimension d,String id) {
		super();
		super.setSize(d);
		super.setLocation(p);
		layer=0;
	}

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

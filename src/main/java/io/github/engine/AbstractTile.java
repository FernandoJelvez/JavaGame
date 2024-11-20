package io.github.engine;


import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Point;

public abstract class AbstractTile extends JPanel {
	private boolean solid;
	private int layer;
	public AbstractTile(int width, int height,boolean solid,int layer){
		super.setSize(width,height);
		this.solid=solid;
		this.layer=layer;
	}

	public AbstractTile(int x, int y, int width, int height, boolean solid,int layer){
		super.setSize(width,height);
		super.setLocation(x,y);
		this.solid=solid;
		this.layer=layer;
	}

	public AbstractTile(Point p, Dimension d, boolean solid,int layer) {
		super.setSize(d);
		super.setLocation(p);
		this.solid=solid;
		this.layer=layer;
	}

	public boolean isSolid() {
		return solid;
	}

	public int getLayer() {
		return layer;
	}
}

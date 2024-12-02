package io.github.engine;


import javax.swing.JLabel;
import java.awt.Color;

public abstract class AbstractTile {
	private final JLabel label= new JLabel();
	private boolean solid;
	private int layer;
	private float unitHeight, unitWidth;
	private float unitX,unitY;
	
	public AbstractTile(int width, int height,boolean solid,int layer){
		label.setSize(width*Display.getUnitValue(),height*Display.getUnitValue());
		unitWidth=width;
		unitHeight=height;
		this.solid=solid;
		this.layer=layer;
		label.setOpaque(true);
	}
	
	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean flag){
		solid=flag;
	}
	
	public int getLayer() {
		return layer;
		layerChanged=true;
	}
	public JLabel getLabel(){
		return label;
	}
	public void setSize(int width,int height) {
		label.setSize(width*Display.getUnitValue(), height*Display.getUnitValue());
		unitWidth=width;
		unitHeight=height;
	}

	public int getWidth(){
		return (int)(label.getSize().getWidth()/Display.getUnitValue());
	}
	public int getHeight(){
		return (int)(label.getSize().getHeight()/Display.getUnitValue());
	}
	public void setLocation(float x, float y){
		unitX=x;
		unitY=y;
		label.setLocation(Math.round(x*Display.getUnitValue()),Math.round((y-getHeight())*Display.getUnitValue()));
	}
	public void setVisible(boolean flag){
		label.setVisible(flag);
	}
	public boolean isVisible(){
		return label.isVisible();
	}

	/**
	 * This method returns the x coordinate of the bottom left corner of the tile
	 * @return the x coordinate in units
	 */
	public float getUnitX(){
		return unitX
	}

	/**
	 * This method returns the y coordinate of the bottom left corner of the tile
	 * @return the y coordinate in units
	 */
	public float getUnitY(){
		return unitY
	}
	public void setColor(int r,int g,int b){
		label.setBackground(new Color(r,g,b));
		System.out.println(new Color(r,g,b));
	}

	public void adaptSize(){
		setSize(unitWidth,unitHeight);
	}
	public void adaptPosition(){
		setLocation(unitX,unitY)
	}
	/**
	*This method is called by the {@code Display} class every frame, it should be used
 	*with the purpose of changing size, color or any other refresh application
	*/
	public abstract void refresh();
}

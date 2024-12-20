package io.github.engine;

import javax.swing.*;
import java.awt.Color;

public abstract class AbstractTile {
	private Texture texturaEscalada;
	private Texture texturaOriginal;
	private final JLabel label = new JLabel();
	private boolean solid;
	private int layer;
	private float unitHeight, unitWidth;
	private float unitX,unitY;
	private boolean layerChanged;
	public AbstractTile(int width, int height,boolean solid,int layer){
		label.setSize(Math.round(width*Display.getUnitValue()),Math.round(height*Display.getUnitValue()));
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
	}

	public void changeLayer(int layer){
		this.layer=layer;
		layerChanged=true;
	}

	protected JLabel getLabel(){
		return label;
	}

	public void setSize(float width,float height) {
		label.setSize(Math.round(width*Display.getUnitValue()), Math.round(height*Display.getUnitValue()));
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
		label.setLocation(Math.round(x*Display.getUnitValue()),Math.round(y*Display.getUnitValue()));
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
		return unitX;
	}

	/**
	 * This method returns the y coordinate of the bottom left corner of the tile
	 * @return the y coordinate in units
	 */
	public float getUnitY(){
		return unitY;
	}

	public void setTexture(Texture texture){
	this.texturaOriginal = texture;
	this.label.setIcon(this.texturaOriginal.getImageIcon());
	adaptSize();
	}

	public void setColor(int r,int g,int b){
		label.setBackground(new Color(r,g,b));
	}

	protected void adaptSize(){ //Se "repite"
		setSize(unitWidth,unitHeight);
		try {
			this.texturaEscalada = new Texture(this.texturaOriginal.getImageIcon());
			this.texturaEscalada.escalarImagen((int) unitWidth * Display.getUnitValue(), (int) unitHeight * Display.getUnitValue());
			this.label.setIcon(texturaEscalada.getImageIcon());
		} catch (Exception err){

		}
	}
	protected abstract void refresh();

	protected void adaptPosition() {
		setLocation(unitX,unitY);
	}
	protected boolean isLayerChanged(){
		return layerChanged;
	}
	protected void setLayerChangedFalse(){
		layerChanged=false;
	}
	protected Boundary getBounds(){
		return new Boundary(unitX,unitY,unitWidth,unitHeight);
	}
}

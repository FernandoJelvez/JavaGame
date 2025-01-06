package io.github.engine;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

public abstract class AbstractTile implements Serializable {
	private String textureID;
	private transient JLabel label;
	private boolean solid;
	private int layer;
	private float unitHeight, unitWidth;
	private float unitX,unitY;
	private transient boolean layerChanged;
	private boolean isOpaque;
	private boolean isVisible;
	private int r,g,b;
	public AbstractTile(float width, float height, boolean solid, int layer){
		label=new JLabel();
		label.setSize(Math.round(width*Display.getUnitValue()),Math.round(height*Display.getUnitValue()));
		unitWidth=width;
		unitHeight=height;
		this.solid=solid;
		this.layer=layer;
		label.setOpaque(false);
		this.isOpaque=false;
	}
	public AbstractTile(float width, float height, boolean solid, int layer,boolean isOpaque){
		label=new JLabel();
		label.setSize(Math.round(width*Display.getUnitValue()),Math.round(height*Display.getUnitValue()));
		unitWidth=width;
		unitHeight=height;
		this.solid=solid;
		this.layer=layer;
		label.setOpaque(isOpaque);
		this.isOpaque=isOpaque;
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
		reloadLabel();
		return label;
	}

	public void setSize(float width,float height) {
		label.setSize(Math.round(width*Display.getUnitValue()), Math.round(height*Display.getUnitValue()));
		unitWidth=width;
		unitHeight=height;
	}

	public int getWidth(){
		reloadLabel();
		return (int)(label.getSize().getWidth()/Display.getUnitValue());
	}
	public int getHeight(){
		reloadLabel();
		return (int)(label.getSize().getHeight()/Display.getUnitValue());
	}

	public void setLocation(float x, float y){
		unitX=x;
		unitY=y;
		reloadLabel();
		label.setLocation(Math.round(x*Display.getUnitValue()),Math.round(y*Display.getUnitValue()));
	}
	public void setVisible(boolean flag){
		reloadLabel();
		isVisible=flag;
		label.setVisible(flag);
	}

	private void reloadLabel() {
		if (label==null){
			System.out.println("iO: "+isOpaque);
			label=new JLabel();
			System.out.println("iO_F: "+isOpaque);

		}
	}

	public boolean isVisible(){
		reloadLabel();
		label.setVisible(isVisible);
		return isVisible;
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
		this.textureID = texture.getId();
		reloadLabel();
		this.label.setIcon(texture.getScaledImageIcon());
		adaptSize();
	}

	public void setColor(int r,int g,int b){
		reloadLabel();
		label.setBackground(new Color(r,g,b));
		this.r=r;
		this.g=g;
		this.b=b;
	}

	public void setOpaque(boolean opaque){
		reloadLabel();
		isOpaque=opaque;
		label.setOpaque(opaque);
	}
	public boolean isOpaque(){
		reloadLabel();
		label.setOpaque(isOpaque);
		return isOpaque;
	}

	protected void adaptSize(){ //Se "repite"
		setSize(unitWidth,unitHeight);
		if(textureID!=null&&Display.getTexture(textureID)!=null) {
			reloadLabel();
			Display.getTexture(textureID).adaptSize(label.getWidth(), label.getHeight());
			label.setIcon(Display.getTexture(textureID).getScaledImageIcon());
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

	public String getTextureID() {
		return textureID;
	}
	public void reloadTexture(){
		if(textureID!=null&&Display.getTexture(textureID)!=null&&label.getIcon()==null) {
			reloadLabel();
			Display.getTexture(textureID).adaptSize(label.getWidth(), label.getHeight());
			label.setIcon(Display.getTexture(textureID).getScaledImageIcon());
		}
	}
	public void update(AbstractTile tile) {
		if (unitWidth != tile.getWidth()) {
			System.out.println("AT1");
			unitWidth = tile.getWidth();
		}
		if (unitHeight != tile.getHeight()){
			System.out.println("AT2");
			unitHeight = tile.getHeight();
		}
		if (unitX!=tile.getUnitX()) {
			System.out.println("AT3");
			unitX = tile.getUnitX();
		}
		if (unitY!=tile.getUnitY()) {
			System.out.println("AT4");
			unitY = tile.getUnitY();
		}
		if (!Objects.equals(textureID, tile.getTextureID())) {
			System.out.println("AT5");
			textureID = tile.getTextureID();
		}
		if (layer!=tile.getLayer()) {
			System.out.println("AT6");
			changeLayer(tile.getLayer());
		}
		if (solid!=tile.isSolid()) {
			System.out.println("AT8");
			solid = tile.isSolid();
		}
		if(tile.isVisible!=this.isVisible) {
			setVisible(tile.isVisible());
			System.out.println("AT9");
		}
		if(tile.isOpaque!=this.isOpaque) {
			isOpaque=tile.isOpaque;
			setOpaque(tile.isOpaque());
			System.out.println("AT10");
		}
		if(tile.r!=this.r||tile.b!=this.b||tile.g!=this.g) {
			r=tile.r;
			g=tile.g;
			b=tile.b;
			setColor(tile.r, tile.g, tile.b);
			System.out.println("AT11");
		}
	}

	public int getR() {
		return r;
	}

	public int getG() {
		return g;
	}

	public int getB() {
		return b;
	}
}

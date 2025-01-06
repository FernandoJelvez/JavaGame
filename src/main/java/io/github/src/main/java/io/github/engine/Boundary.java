package io.github.engine;

import java.io.Serializable;

public class Boundary implements Serializable {
	private float unitY;
	private float unitX;
	private float unitWidth;
	private float unitHeight;

	public Boundary(float unitX, float unitY, float unitWidth, float unitHeight) {
		this.unitX = unitX;
		this.unitY = unitY;
		this.unitWidth = unitWidth;
		this.unitHeight = unitHeight;
	}

	public float getUnitY() {
		return unitY;
	}

	public float getUnitX() {
		return unitX;
	}

	public float getUnitWidth() {
		return unitWidth;
	}

	public float getUnitHeight() {
		return unitHeight;
	}
	public float getMaxY(){
		return unitY+unitHeight;
	}
	public float getMinY(){
		return unitY;
	}
	public float getMaxX(){
		return unitX+unitWidth;
	}
	public float getMinX(){
		return unitX;
	}
	public void setLocation(float unitX,float unitY){
		this.unitX=unitX;
		this.unitY=unitY;
	}
	public void setSize(float unitWidth,float unitHeight){
		this.unitWidth=unitWidth;
		this.unitHeight=unitHeight;
	}

	public boolean intersects(Boundary boundary){
		boolean thisVertically=(getMaxY()<=boundary.getMaxY()&&getMaxY()>=boundary.getMinY())||(getMinY()<=boundary.getMaxY()&&getMinY()>=boundary.getMinY());
		boolean thisHorizontally=(getMaxX()<=boundary.getMaxX()&&getMaxX()>=boundary.getMinX())||(getMinX()<=boundary.getMaxX()&&getMinX()>=boundary.getMinX());
		boolean boundaryVertically=(boundary.getMaxY()<=getMaxY()&&boundary.getMaxY()>=getMinY())||(boundary.getMinY()<=getMaxY()&&boundary.getMinY()>=getMinY());
		boolean boundaryHorizontally=(boundary.getMaxX()<=getMaxX()&&boundary.getMaxX()>=getMinX())||(boundary.getMinX()<=getMaxX()&&boundary.getMinX()>=getMinX());
		return (thisVertically&&thisHorizontally)||(boundaryHorizontally&&boundaryVertically);
	}
}

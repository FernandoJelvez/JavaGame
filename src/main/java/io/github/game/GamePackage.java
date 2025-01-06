package io.github.game;

import io.github.engine.Entity;
import io.github.engine.connectivity.Package;

import java.io.Serializable;
import java.time.Instant;

public class GamePackage extends Package implements Serializable {
	private String textureID;
	private boolean solid;
	private int layer;
	private float unitHeight, unitWidth;
	private float unitX,unitY;
	private transient boolean layerChanged;
	private boolean isOpaque;
	private boolean isVisible;
	private int r,g,b;
	private Entity packageEntity;
	public GamePackage(String message, Instant instant, Entity entity) {
		super(message, instant);
		textureID=entity.getTextureID();
		solid = entity.isSolid();
		layer=entity.getLayer();
		unitHeight=entity.getHeight();
		unitWidth=entity.getWidth();
		unitX=entity.getUnitX();
		unitY=entity.getUnitY();
		isOpaque=entity.isOpaque();
		isVisible=entity.isVisible();
		r=entity.getR();
		g=entity.getG();
		b=entity.getB();
		packageEntity=entity;
	}

	public String getTextureID() {
		return textureID;
	}

	public boolean isSolid() {
		return solid;
	}

	public int getLayer() {
		return layer;
	}

	public float getUnitHeight() {
		return unitHeight;
	}

	public float getUnitWidth() {
		return unitWidth;
	}

	public float getUnitX() {
		return unitX;
	}

	public float getUnitY() {
		return unitY;
	}

	public boolean isLayerChanged() {
		return layerChanged;
	}

	public boolean isOpaque() {
		return isOpaque;
	}

	public boolean isVisible() {
		return isVisible;
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

	public Entity getPackageEntity() {
		return packageEntity;
	}
}

package io.github.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Texture implements Cloneable{

	private ImageIcon imageIcon;
	private ImageIcon scaledImageIcon;
	private String id;

	public Texture(String path, String id) {
		imageIcon = new ImageIcon(path);
		scaledImageIcon= new ImageIcon(path);
		this.id=id;
	}

	public Texture(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
		this.scaledImageIcon=new ImageIcon(imageIcon.getImage());
	}

	public Texture(ImageIcon imageIcon, String id) {
		this.imageIcon = imageIcon;
		this.scaledImageIcon=new ImageIcon(imageIcon.getImage());
		this.id=id;
	}

	public void adaptSize(int width,int height){
		try {
			this.scaledImageIcon.setImage(imageIcon.getImage().getScaledInstance(width,height, Image.SCALE_FAST));
		} catch (Exception err){

		}
	}

    @Override
    public Texture clone() {
        try {
            Texture clone = (Texture) super.clone();
			clone.imageIcon = new ImageIcon(imageIcon.getImage());
			clone.id=id;
			// TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

	public ImageIcon getScaledImageIcon() {
		return  scaledImageIcon;
	}

	public String getId() {
		return id;
	}
}

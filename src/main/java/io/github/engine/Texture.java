package io.github.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Texture implements Cloneable{

	private ImageIcon imageIcon;

	public Texture(String path) {
		imageIcon = new ImageIcon(path);
	}

	public Texture() {
	}

	public Texture(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
	}

	public void escalarImagen(float width, float height){
		this.imageIcon.setImage(imageIcon.getImage().getScaledInstance((int) width, (int) height, Image.SCALE_FAST));

	}

	protected ImageIcon getImageIcon() {
		Image imagenCopia = this.imageIcon.getImage();
		BufferedImage bufferedImage = new BufferedImage(imagenCopia.getWidth(null),imagenCopia.getHeight(null),BufferedImage.TYPE_INT_ARGB);
		ImageIcon imagenSuelta = new ImageIcon(bufferedImage);
		Graphics2D g2d = bufferedImage.createGraphics();
		g2d.drawImage(imagenCopia,0,0,null);
		g2d.dispose();

		return imagenSuelta;
	}


    @Override
    public Texture clone() {
        try {
            Texture clone = (Texture) super.clone();
			clone.imageIcon = new ImageIcon(imageIcon.getImage());
			// TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

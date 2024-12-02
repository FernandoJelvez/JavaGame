package io.github.engine;

import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStreamImpl;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Texture {
	ImageIcon texture;
	int spriteWidth;
	int spriteHeight;

	/**
	 * Class constructor, the width and height values are used by the class to cut the image by the specified dimensions to
	 * obtain the sprites, or specific textures, which allows more than one texture per image
	 * @param filePath     the path of the image file
	 * @param spriteWidth  the amount of pixels that each sprite uses in the image horizontally
	 * @param spriteHeight the amount of pixels that each sprite uses in the image vertically
	 */
	public Texture(String filePath, int spriteWidth, int spriteHeight) {
		texture = new ImageIcon(filePath);
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
	}
}

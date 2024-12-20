package io.github.levelMaker;

import io.github.engine.Display;
import io.github.engine.Texture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Mundo {

    private static HashMap<Integer, HashMap<Point, Tile>> pantallas = new HashMap<>();
    private static HashMap<Point, Tile> bloques = new HashMap<>();

    public static void colocarBloque(Point punto){
        if (bloques.get(punto) == null){
            System.out.println(" (" + punto.x + "," + punto.y  + ") ");
        } else {
            System.out.println("Ese bloque esta rellenado");
        }
        bloques.putIfAbsent(punto, new Tile((int) punto.getX(), (int) punto.getY(), 4, 4, true, 0));
        bloques.get(punto).setColor(255,0,0);
        try {
            bloques.get(punto).setTexture(new Texture(testbufferedImage()));
        } catch (Exception err){

        }
        Display.addToBuffer(bloques.get(punto), "bloque" + ": (" + punto.x + "," + punto.y  + ")");
    }

    public static void borrarBloque(Point punto){
        if (bloques.get(punto) != null){
            bloques.remove(punto);
            Display.removeTile("bloque" + ": (" + punto.x + "," + punto.y  + ")");
        } else {
            System.out.println("No existe un bloque en esa posicion");
        }
    }

    private static ImageIcon testbufferedImage() throws IOException {
        BufferedImage imagenCompleta = ImageIO.read(new File("C:\\Users\\Tomas\\OneDrive\\Desktop\\codigoEnTeoriaFinal\\JavaGame-main\\src\\main\\java\\io\\github\\presets\\texturas.png"));
        return new ImageIcon(imagenCompleta.getSubimage(20,60,20,20));
    }


}

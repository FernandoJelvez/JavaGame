package io.github.levelMaker;

import io.github.engine.Control;
import io.github.engine.Display;
import io.github.engine.Texture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Mundo {

    private static int idPantalla;
    public static int idBloque;
    public static boolean isSolid = true;
    public static int layer = 1;

    private static HashMap<Integer, HashMap<Point, Tile>> pantallas = new HashMap<>();
    private static HashMap<Point, Tile> bloques = new HashMap<>();
    public static  HashMap<Integer, ArrayList<String>> puntosEnCadaPantalla = new HashMap<>();
    private static ArrayList<String> puntosEnPantalla = new ArrayList<>();

    Mundo(){
    }

    public static void colocarBloque(Point punto){;
        if (bloques.get(punto) == null){
            System.out.println(" (" + punto.x + "," + punto.y  + ") ");
        } else {
            System.out.println("Ese bloque esta rellenado");
        }
        bloques.putIfAbsent(punto, new Tile((int) punto.getX(), (int) punto.getY(), 4, 4,isSolid , layer));
        bloques.get(punto).setOpaque(false);
        try {
            bloques.get(punto).setTexture(new Texture(asignarTexturaAlBloque(LevelMaker2.idBloque)));
        } catch (Exception err){

        }
        puntosEnPantalla.add("bloque" + ": (" + punto.x + "," + punto.y  + ")");
        puntosEnCadaPantalla.put(idPantalla,puntosEnPantalla);
        Display.addToBuffer(bloques.get(punto), "bloque" + ": (" + punto.x + "," + punto.y  + ")");
    }

    public static void borrarBloque(Point punto){
        if (bloques.get(punto) != null){
            bloques.remove(punto);
            Display.removeTile("bloque" + ": (" + punto.x + "," + punto.y  + ")");
            puntosEnPantalla.remove("bloque" + ": (" + punto.x + "," + punto.y  + ")");
        } else {
            System.out.println("No existe un bloque en esa posicion");
        }
    }

    public static ImageIcon asignarTexturaAlBloque(int idBloque) throws IOException {
        Mundo.idBloque = idBloque;
        BufferedImage imagenCompleta = ImageIO.read(new File("src/main/java/io/github/presets/texturas.png"));
        int x;
        int y = 0;
        if (idBloque < 10) {
            x = idBloque * 20;
        } else {
            x = (idBloque % 10) * 20;
            y = ((idBloque - (idBloque % 10)) / 10) * 20;
        }
        return new ImageIcon(imagenCompleta.getSubimage(x, y, 20, 20));
    }

    public static void setIdPantalla(int idPantalla) {
        Mundo.idPantalla = idPantalla;
    }

    public static int getIdPantalla() {
        return idPantalla;
    }

    public static void cambioDePantalla(int caso) {
        if (bloques != null) {
            pantallas.put(idPantalla,new HashMap<>(new HashMap<>(bloques)));
            bloques.clear();
        } else {
            pantallas.put(idPantalla,new HashMap<>());
        }
        puntosEnPantalla.forEach(Display::removeTile);
        if (caso == KeyEvent.VK_R){
            idPantalla--;
        } else {
            idPantalla++;
        }
        bloques = pantallas.get(idPantalla);
        try {
            bloques.forEach((punto, tile) -> {
                Display.addToBuffer(tile, "bloque" + ": (" + punto.x + "," + punto.y + ")");
            });
        } catch (NullPointerException e){
            System.out.println("Se ha pasado un error: " + e);
        }

        System.out.println("ID: " + idPantalla);
    }

    public static HashMap<Integer, HashMap<Point, Tile>> getPantallas() {
        return pantallas;
    }


}

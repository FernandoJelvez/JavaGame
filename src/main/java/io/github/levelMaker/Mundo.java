package io.github.levelMaker;

import com.google.gson.Gson;
import io.github.engine.AbstractTile;
import io.github.engine.Display;
import io.github.engine.Texture;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Mundo {

    private static int idPantalla;
    public static int idBloque;
    public static boolean isSolid = true;
    public static int layer = 1;
    private static final HashMap<Point, Tile> bloques = new HashMap<>();
    private static final ArrayList<String> puntosEnPantalla = new ArrayList<>();

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
            bloques.get(punto).setTexture((asignarTexturaAlBloque(LevelMaker.idBloque)));
        } catch (Exception err){

        }
        Display.addToBuffer("bloque" + ": (" + punto.x + "," + punto.y  + ")", bloques.get(punto));
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

    public static Texture asignarTexturaAlBloque(int idBloque) throws IOException {
        Mundo.idBloque = idBloque;
        System.out.println("id: "+idBloque);
        return (Display.getTextures().get(idBloque));
    }

    public static void GuardarMundo(){
        ArrayList<AbstractTile> bloquesParaGuardar = new ArrayList<>();
		bloquesParaGuardar.addAll(bloques.values());
        Gson json = new Gson();
        String jsonString = json.toJson(bloquesParaGuardar);

        File file = new File("nivel.json");
        try (FileWriter writer = new FileWriter(file)) {
            System.out.println("Guardando en: " + file.getAbsolutePath());
            writer.write(jsonString);
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }
    }


}

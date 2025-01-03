package io.github.levelMaker;

import com.google.gson.Gson;
import io.github.engine.Display;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GuardarMundo {

    public GuardarMundo(HashMap<Point, Tile> bloques){
        ArrayList<TileSimple> bloquesSimple = new ArrayList<>();
        bloques.forEach((punto, tile) -> {
            bloquesSimple.add(new TileSimple((int) tile.getUnitX(),(int) tile.getUnitY(), 20,20, tile.isSolid(), tile.getLayer(), 1, "bloque"));
        });
        Gson json = new Gson();
        String jsonString = json.toJson(bloquesSimple);

        File file = new File("nivel.json");
        try (FileWriter writer = new FileWriter(file)) {
            System.out.println("Guardando en: " + file.getAbsolutePath());
            writer.write(jsonString);
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }
    }
}

/*package io.github.levelMaker;

import com.google.gson.Gson;
import io.github.engine.AbstractTile;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GuardarMapa {

    private static ArrayList<HashMap<Integer, ArrayList<ConcreteTile>>> pantallas = new ArrayList<>();

    public static void main(String[] args) {
    }
    public static void guardarMapa(){
        Gson json = new Gson();

        String gson = json.toJson(pantallas);

        // Escribir el JSON en un archivo
        try (FileWriter fileWriter = new FileWriter("mundo.json")) {
            fileWriter.write(gson);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void agregarPantalla(Integer numPantalla, HashMap<Integer, ArrayList<AbstractTile>> pantalla) {
        ArrayList<ConcreteTile> concreteTileLista = new ArrayList<>();
        HashMap<Integer, ArrayList<ConcreteTile>> mapa = new HashMap<>();
        pantalla.get(numPantalla).forEach( valor ->{
            Point punto = valor.getLocation();
            Dimension dimension = valor.getSize();
            boolean isSolic = valor.isSolid();
            int layer = valor.getLayer();
            int id = valor.getId();
            concreteTileLista.add(new ConcreteTile(punto,dimension,isSolic,layer,id));
        });
        mapa.put(numPantalla,concreteTileLista);
        pantallas.add(mapa);
    }

}
*/

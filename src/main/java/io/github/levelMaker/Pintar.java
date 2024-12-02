package io.github.levelMaker;
import java.awt.*;
import java.util.HashSet;

public class Pintar {

    public static Point[] rectangle(Point puntoInicial, Point puntoFinal){
        HashSet<Point> puntos = new HashSet<>();
        Point pivote = pillarPuntoPivote(puntoInicial,puntoFinal);
        for (int y = (int) pivote.getY(); y <= Math.max(puntoInicial.getY(),puntoFinal.getY()); y++) {
            for (int x = (int) pivote.getX(); x <= Math.max(puntoInicial.getX(),puntoFinal.getX()); x++) {
                puntos.add(new Point(x,y));
            }
        }
        return puntos.toArray(new Point[0]);
    }


    private static Point pillarPuntoPivote(Point puntoInicial, Point puntoFinal){
        int x = (int) (Math.min(puntoInicial.getX(),puntoFinal.getX()));
        int y = (int) Math.min(puntoInicial.getY(),puntoFinal.getY());
        return new Point(x,y);
    }
}

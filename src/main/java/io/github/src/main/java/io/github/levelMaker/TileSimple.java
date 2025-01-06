package io.github.levelMaker;
public class TileSimple {
    private int unitX;
    private int unitY;
    private float width;
    private float height;
    private boolean solid;
    private int layer;
    private int idTextura;
    private String tipoBloque;

    TileSimple(int unitX, int unitY,float width, float height, boolean solid, int layer, int idTextura, String tipoBloque){
        this.unitX = unitX;
        this.unitY = unitY;
        this.width = width;
        this.height = height;
        this.solid = solid;
        this.layer = layer;
        this.idTextura = idTextura;
        this.tipoBloque = tipoBloque;
    }
}

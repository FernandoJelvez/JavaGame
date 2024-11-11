package io.github.engine;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Display{
    /* frame is the window in wich everything happens, this class only uses a couple of methods,
    because of that, inheritance from frame is not necesary */
    JFrame frame;
    //the buffer keeps the record of the tiles added to the Display, but not added to frame yet
    ArrayList<AbstractTile> buffer=new ArrayList<>();
    //activeTiles exist to keep the record of some features of AbrstractTile, like the layer
    ArrayList<AbstractTile> activeTiles=new ArrayList<>();
    private int width;
    private int height;
    private String name;
    //bufferChanged flag: indicates things have been added to the buffer since the last update
    private boolean bufferChanged;

    public Display(String name,int width, int height){
        this.height=height;
        this.width=width;
        this.name=name;
    }
    public void start(){
        frame = new JFrame(name);
        frame.setSize(width,height);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public void addToBuffer(AbstractTile tile){
        buffer.add(tile);
        bufferChanged=true;
    }

    /**
     * Erases all active tiles from the ActiveTiles ArrayList and clears the all
     * components from the JFrame, enssentially wiping out all the display data about Tiles.
     */
    public void clearDisplay(){
        for (Component component:frame.getComponents()) {
            frame.remove(component);
        }
    }
    public void wipeDisplayData(){
        activeTiles.clear();
        clearDisplay();
    }
    public void update(){
        if(bufferChanged) {
            for (AbstractTile tile:buffer) {
                activeTiles.add(tile);
            }
            bufferChanged=false;
        }
        clearDisplay();
        for (int layer = 0; layer < 10; layer++) {
            for (AbstractTile tile:activeTiles) {
                if(tile.getLayer()==layer){
                    frame.add(tile);
                    tile.setVisible(true);
                }
            }
        }
        frame.update(frame.getGraphics());
    }
}

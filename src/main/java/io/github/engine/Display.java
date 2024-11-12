package io.github.engine;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public final class Display{
    /* Display.frame is the window in wich everything happens, this class only uses a couple of methods,
    because of that, inheritance from Display.frame is not necesary */
    private static JFrame frame;
    //the buffer keeps the record of the tiles added to the Display, but not added to Display.frame yet
    private static ArrayList<AbstractTile> buffer=new ArrayList<>();
    //activeTiles exist to keep the record of some features of AbrstractTile, like the layer
    private static ArrayList<AbstractTile> activeTiles=new ArrayList<>();
    private static int width;
    private static int height;
    private static String name;
    //bufferChanged flag: indicates things have been added to the buffer since the last refresh
    private static boolean bufferChanged;

    public static void setup(String name,int width, int height){
        Display.height=height;
        Display.width=width;
        Display.name=name;
    }
    
    public static void start(){
        System.out.println(name+" "+width+" "+height);
        Display.frame = new JFrame(name);
        System.out.println(frame.getBounds());
        Display.frame.setSize(width,height);
        Display.frame.setVisible(true);
        Display.frame.setLayout(null);
        Display.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        System.out.println("good");
    }
    public static void addToBuffer(AbstractTile tile){
        buffer.add(tile);
        bufferChanged=true;
    }

    /**
     * Erases all active tiles from the ActiveTiles ArrayList and clears the all
     * components from the JFrame, essentially wiping out all the display data about Tiles.
     */
    public static void clearDisplay(){
        for (Component component:Display.frame.getComponents()) {
            Display.frame.remove(component);
        }
    }
    public static void wipeDisplayData(){
        activeTiles.clear();
        clearDisplay();
    }
    public static void refresh(){
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
                    if(tile.getClass().getSuperclass()==Entity.class){
                        ((Entity)tile).updatePosition();
                    }
                    Display.frame.add(tile);
                    tile.setVisible(true);
                }
            }
        }
        Display.frame.update(Display.frame.getGraphics());
    }
}

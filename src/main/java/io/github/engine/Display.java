package io.github.engine;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.util.HashMap;

/**
 * This class is in charge of rendering the objects in the window,
 * storing said objects and calling their refresh methods.
 * @apiNote This class contains only static methods as it meant for the Display to be unique. This class
 * was not designed for inheritance
 */
public final class Display {
    private static JFrame frame;
    private static HashMap<String,AbstractTile> buffer=new HashMap<>();
    private static HashMap<String, AbstractTile> tiles =new HashMap<>();
    private static boolean bufferChanged;

    /**
     * This method sets the value of the variables of this class, in this case, it sets the
     * characteristics of the window
     * @param name the name of the window
     * @param width the width of the window
     * @param height the height of the window
     */
    public static void setup(String name,int width, int height){
        frame = new JFrame(name);
        frame.setSize(width,height);
    }

    /**
     * Starts the window, making it visible and setting some of the Display's inner JFrame configurations
     * that allow the objects to display correctly
     */
    public static void start(){
        Display.frame.setVisible(true);
        Display.frame.setLayout(null);
        Display.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Adds an AbstractTile inheriting object to the buffer and sets the {@code bufferChanged} flag
     * @param tile the object belonging to a class inheriting from AbstractTile, which will be added later to the window
     * @param id the id of the tile, used to retrieve it from the Display if its parameters need to be changed
     */
    public static void addToBuffer(AbstractTile tile,String id){
        buffer.put(id,tile);
        bufferChanged=true;
    }

    public static void refresh(){
        if (bufferChanged){
            buffer.entrySet().stream().sorted()
                    .filter((e)->!tiles.containsKey(e.getKey()))
                    .forEach((e)->{
                        tiles.put(e.getKey(),e.getValue()); e.getValue().setVisible(true);frame.add(e.getValue());});
            buffer.clear();
        }
        tiles.values().stream()
                .filter(Entity.class::isInstance)
                .forEach((e)->{Physics.applyGravity((Entity)e);((Entity)e).refresh();});
        frame.repaint();
        bufferChanged=false;
    }

    public static double getHeigth(){
        return frame.getSize().getHeight();
    }
    public static HashMap<String,AbstractTile> retrieveTiles(){
        return tiles;
    }
    public static AbstractTile retrieveTile(String id){
        return tiles.get(id);
    }
}

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
    private static JPanel panel;
    private static HashMap<String,AbstractTile> buffer=new HashMap<>();
    private static HashMap<String, AbstractTile> tiles =new HashMap<>();
    private static boolean bufferChanged;
    private static int screenHeight

    /**
     * This method sets the value of the variables of this class, in this case, it sets the
     * characteristics of the window
     * @param name the name of the window
     * @param width the width of the window
     * @param height the height of the window
     */
    public static void setup(String name,int width, int height){
        frame = new JFrame(name);
        panel = new JPanel()
        frame.setSize(width,height);
        panel.setSize(width,height);
        Display.screenHeight = height;
        frame.add(panel);
    }

    /**
     * Starts the window, making it visible and setting some of the Display's inner JFrame configurations
     * that allow the objects to display correctly
     */
    public static void start(){
        Display.frame.setVisible(true);
        Display.panel.setVisible(true);
        Display.frame.setLayout(null);
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
        if(checkScreenHeightChanged()){
            System.out.println(true);
            refreshSize();
        }
        if (bufferChanged){
            buffer.entrySet().stream()
                    .filter((e)->!tiles.containsKey(e.getKey())).forEach((e)->{
                        tiles.put(e.getKey(),e.getValue()); 
                        e.getValue().setVisible(true);
                        panel.add(e.getValue().getLabel());
                    });
            buffer.clear();
        }
        tiles.values().stream()
                .filter(Entity.class::isInstance)
                .forEach((e)->{Physics.applyGravity((Entity)e);((Entity)e).refresh();});
        panel.revalidate();
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
    /**
     * Calculates the value of a unit, which is the an amount of pixels used as measurement by the other classes as the
     * standard measurement unit
     * @return an integer with the value of a unit measured in pixels
     */
    public static int getUnitValue(){
		return frame.getHeight() / 60;
    }
    public static boolean checkScreenHeightChanged(){
        System.out.println("check: "+ (frame.getHeight()==screenHeight));
        System.out.println("check: "+frame.getHeight());
        return !(frame.getHeight()==screenHeight);
    }
    public static void refreshSize(){
        panel.setSize(frame.getSize());
        screenHeight= frame.getHeight();
        tiles.values().forEach(AbstractTile::refreshSize);
    }
}

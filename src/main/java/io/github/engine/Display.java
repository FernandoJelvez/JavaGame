package io.github.engine;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.util.HashMap;

/**
 * This class is in charge of rendering the objects in the window,
 * storing said objects and calling their refresh methods.
 * @apiNote This class contains only static methods as it meant for the Display to be unique. This class
 * was not designed for inheritance, neither for making objects
 */
public final class Display {
    private static JFrame frame;
    private static JPanel panel;
    private static Controllable player;
    private static HashMap<String,AbstractTile> buffer=new HashMap<>();
    private static HashMap<String, AbstractTile> tiles =new HashMap<>();
    private static ArrayList<AbstractTile> deletedTiles = new ArrayList<>();
    private static boolean bufferChanged;
    private static boolean tilesChanged;
    private static int screenHeight;
    private static int windowsBarHeight;

    /**
     * This method sets the value of the variables of this class, in this case, it sets the
     * characteristics of the window
     * @param name the name of the window
     * @param width the width of the window
     * @param height the height of the window
     */
    public static void setup(String name,int width, int height){
        frame = new JFrame(name);
        panel = new JPanel();
        frame.setSize(width,height);
        panel.setSize(width,height);
        Display.screenHeight = height;
        Display.panel.setPreferredSize(new Dimension(width,height));
        frame.add(panel);
    }

    public static void setPlayer(Controllable player){
        Display.player = player;
    }
    /**
     * Starts the window, making it visible and setting some of the Display's inner JFrame configurations
     * that allow the objects to display correctly
     */
    public static void start(){
        Display.panel.setVisible(true);
        Display.frame.setVisible(true);
        Display.frame.pack();
        Display.panel.setLayout(null);
        Display.frame.setLayout(null);
        windowsBarHeight=frame.getHeight()-panel.getHeight();
        Display.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addKeyListener(new Control(player));
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

     public static void refresh() {
        if (checkScreenHeightChanged()) {
            adaptSize();
            adaptPosition();
        }
        //adds elements which were waiting in the buffer
        if (bufferChanged) {
            buffer.entrySet().stream()
                    .filter((e) -> !tiles.containsKey(e.getKey())).forEach((e) -> {
                        tiles.put(e.getKey(), e.getValue());
                        e.getValue().setVisible(true);
                        panel.add(e.getValue().getLabel(), Integer.valueOf(e.getValue().getLayer()));
                    });
            buffer.clear();
        }
        if (tilesChanged){
            deletedTiles.stream().forEach((e) -> panel.remove(e.getLabel()));
            deletedTiles.clear();
            tilesChanged = false;
        }
        //updates the layer in which the tile is displayed
        tiles.values().stream()
                .filter(AbstractTile::isLayerChanged).forEach(
                        (e)->{frame.setComponentZOrder(e.getLabel(),e.getLayer());
                        e.setLayerChangedFalse();}
                );
        //calls abstract refresh method in every tile
        tiles.values().forEach(AbstractTile::refresh);
        //applies projectile trajectory calculations to every Entity
        tiles.values().stream()
                .filter(Entity.class::isInstance)
                .forEach((e)->{
                    Physics.applyProjectileCinematic((Entity)e);
                });
        panel.revalidate();
        frame.repaint();
        bufferChanged=false;
    }

	public static double getHeight(){
        return frame.getSize().getHeight() / getUnitValue();
    }
	
    public static double getWidth() { return frame.getSize().getWidth() / getUnitValue() ;}
	
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
    public static float getUnitValue(){
		return (float) panel.getHeight() / 60;
    }
    public static boolean checkScreenHeightChanged(){
        return !(frame.getHeight()==screenHeight);
    }
    public static void adaptSize(){
        panel.setSize(frame.getWidth(),frame.getHeight()-windowsBarHeight);
        screenHeight= frame.getHeight();
        tiles.values().forEach(AbstractTile::adaptSize);
    }
    public static void adaptPosition(){
        tiles.values().forEach(AbstractTile::adaptPosition);
    }
	
	public static void removeTile(String id ){
        if (tiles.containsKey(id)){
            deletedTiles.add(tiles.get(id));
            tiles.remove(id);
            tilesChanged = true;
        }
    }
	
}

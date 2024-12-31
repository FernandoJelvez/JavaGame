package io.github.engine;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * This class is in charge of rendering the objects in the window,
 * storing said objects and calling their refresh methods.
 * @apiNote This class contains only static methods as it meant for the Display to be unique. This class
 * was not designed for inheritance, neither for making objects
 */
public final class Display {
    private static JFrame frame;
    public static JPanel panel; //cambiado private a public
    private static JViewport viewport;
	private static Controllable player;
    private static HashMap<String,AbstractTile> buffer=new HashMap<>();
    private static HashMap<String, AbstractTile> activeTiles =new HashMap<>();
    private static ArrayList<AbstractTile> deletedTiles=new ArrayList<>();
    private static boolean bufferChanged;
    private static boolean tilesChanged;
    private static int screenHeight;
    private static int screenWidth;
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
        viewport=new JViewport();
        frame.setSize(width,height);
		panel.setSize(width,height);
        Display.screenHeight = height;
        Display.screenWidth=width;
        Display.viewport.setPreferredSize(new Dimension(width,height));
        viewport.setView(panel);
        frame.add(viewport);
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
        Display.viewport.setLayout(null);
        windowsBarHeight=frame.getHeight()-panel.getHeight();
        Display.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public static void setScenarioSize(int width,int height){
        panel.setSize(width,height);
    }
    public static void setControl(Control control){
        frame.addKeyListener(control);
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
            adaptSize();
            adaptPosition();
        }
        //adds elements which were waiting in the buffer
        if (bufferChanged){
            sendBufferToActiveTiles();
        }
        if (tilesChanged){
            deletedTiles.forEach((e)->panel.remove(e.getLabel()));
            deletedTiles.clear();
            tilesChanged=false;
        }
        //updates the layer in which the tile is displayed
        activeTiles.values().stream()
                .filter(AbstractTile::isLayerChanged).forEach(
                        (e)->{frame.setComponentZOrder(e.getLabel(),e.getLayer());
                        e.setLayerChangedFalse();}
                );
        //calls abstract refresh method in every tile
        activeTiles.values().forEach(AbstractTile::refresh);
        //applies projectile trajectory calculations to every Entity
        //frame.revalidate();

        activeTiles.values().stream()
                .filter(Entity.class::isInstance)
                .forEach((e)->{
                    Physics.applyProjectileCinematic((Entity)e);
                });
        viewport.revalidate();
        viewport.repaint();
        Player player = activeTiles.values().stream()
                .filter(Player.class::isInstance).map((e)->(Player)e).findFirst().orElseThrow();
        if(player.getUnitX()>=(viewport.getWidth()/getUnitValue())/4) {
            viewport.setViewPosition(new Point((player.getLabel().getX() - (screenWidth / 4)), viewport.getY()));
        }
    }

    public static double getHeight(){
        return frame.getSize().getHeight()/60;
    }
    public static double getWidth() {
        return frame.getSize().getWidth()/60;
    }
    public static HashMap<String,AbstractTile> retrieveTiles(){
        return activeTiles;
    }
    public static AbstractTile retrieveTile(String id){
        return activeTiles.get(id);
    }
    public static AbstractTile searchTiles(String id){
        return activeTiles.get(id);
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
        float changePercentage;
        if(frame.getHeight()>=screenWidth){
            changePercentage= (float) (screenHeight * 100) /frame.getHeight();
        } else {
            changePercentage= (float) (frame.getHeight() * 100) /screenHeight;
        }
        panel.setSize((int) (panel.getWidth()*changePercentage),frame.getHeight()-windowsBarHeight);
        screenHeight= frame.getHeight();
        screenWidth=frame.getWidth();
        viewport.setSize(frame.getWidth(), frame.getHeight()-windowsBarHeight);
        activeTiles.values().forEach(AbstractTile::adaptSize);
    }
    public static void adaptPosition(){
        activeTiles.values().forEach(AbstractTile::adaptPosition);
    }
    public static void removeTile(String id){
        activeTiles.remove(id);
        tilesChanged=true;
    }
    private static void sendBufferToActiveTiles(){
        boolean previouslyEmpty=activeTiles.isEmpty();
        buffer.entrySet().stream()
                .filter((e)->!activeTiles.containsKey(e.getKey())).sorted(Comparator.comparingInt((e)->e.getValue().getLayer())).forEach((e)->{
                    activeTiles.put(e.getKey(),e.getValue());
                    e.getValue().setVisible(true);
                    panel.add(e.getValue().getLabel());
                });
        if(!previouslyEmpty) {
            activeTiles.values()
                    .forEach(Display::zAlignTile);
        }
        bufferChanged=false;
        buffer.clear();
    }
    private static void zAlignTile(AbstractTile tile){
        int maxLayer=activeTiles.values().stream().map(AbstractTile::getLayer).max(Integer::compareTo).orElseThrow();
        System.out.println(maxLayer);
        if(tile.getLayer()!=0) {
            panel.setComponentZOrder(tile.getLabel(), tile.getLayer()-1);
        } else {
            panel.setComponentZOrder(tile.getLabel(),0);
        }
    }
}

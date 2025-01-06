package io.github.engine;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
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
    private static ArrayList<Texture> textures=new ArrayList<>();
    private static AbstractLevel level;
    private static boolean levelChanged;

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
    @Deprecated
	public static void setPlayer(Controllable player){
        	Display.player = player;
    	}
    /**
     * Starts the window, making it visible and setting some of the Display's inner JFrame configurations
     * that allow the objects to display correctly
     */
    public static void start(){
        if(panel==null){
            throw new DisplayNotSetUpException();
        } else {
            Display.panel.setVisible(true);
            Display.frame.setVisible(true);
            Display.frame.pack();
            Display.panel.setLayout(null);
            Display.frame.setLayout(null);
            Display.viewport.setLayout(null);
            windowsBarHeight = frame.getHeight() - panel.getHeight();
            Display.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            if (frame.getKeyListeners().length == 0) {
                frame.addKeyListener(new Control(player));
            }
        }
    }
    public static void setScenarioSize(int width,int height){
        if(panel==null){
            throw new DisplayNotSetUpException();
        } else {
            panel.setSize(width, height);
        }
    }
    public static void setControl(Control control){
        frame.addKeyListener(control);
        player=control.getControllable();
    }


    /**
     * Adds an AbstractTile inheriting object to the buffer and sets the {@code bufferChanged} flag
     *
     * @param id   the id of the tile, used to retrieve it from the Display if its parameters need to be changed
     * @param tile the object belonging to a class inheriting from AbstractTile, which will be added later to the window
     */
    public static void addToBuffer(String id,AbstractTile tile){
        if (panel!=null) {
            if (!buffer.containsKey(id)) {
                buffer.put(id, tile);
                bufferChanged = true;
            }
        } else {
            throw new DisplayNotSetUpException();
        }
    }

    public static void refresh(){
        if(panel!=null) {
            if (checkScreenHeightChanged()) {
                adaptSize();
                adaptPosition();
            }
            //adds elements which were waiting in the buffer
            if (levelChanged) {
                cloneActiveTilesWithId().entrySet().stream().filter((e) -> !e.getClass().isInstance(Player.class))
                        .map(Map.Entry::getKey).forEach(Display::removeTile);
                HashMap<String, AbstractTile> t = level.getTilesBetween(getDisplayBounds(), 8);
                t.entrySet().forEach((e) -> addToBuffer(e.getKey(), e.getValue()));
            }
            if (bufferChanged) {
                sendBufferToActiveTiles();
                levelChanged = false;
            }
            if (tilesChanged) {
                deletedTiles.forEach((e) -> panel.remove(e.getLabel()));
                deletedTiles.clear();
                tilesChanged = false;
            }
            //updates the layer in which the tile is displayed
            /*activeTiles.values().stream()
                    .filter(AbstractTile::isLayerChanged).forEach(
                            (e) -> {
                                frame.setComponentZOrder(e.getLabel(), e.getLayer());
                                e.setLayerChangedFalse();
                            }
                    );*/
            //calls abstract refresh method in every tile
            activeTiles.values().forEach(AbstractTile::refresh);
            //applies projectile trajectory calculations to every Entity
            //frame.revalidate();

            activeTiles.values().stream()
                    .filter(Entity.class::isInstance)
                    .forEach((e) -> {
                        Physics.applyProjectileCinematic((Entity) e);
                    });
            viewport.revalidate();
            viewport.repaint();
            Player player = activeTiles.values().stream()
                    .filter((e) -> e.equals(Display.player)).map((e) -> (Player) e).findFirst().orElseThrow();
            if (player.getUnitX() >= (viewport.getWidth() / getUnitValue()) / 4) {
                viewport.setViewPosition(new Point((player.getLabel().getX() - (screenWidth / 4)), viewport.getY()));
            } else {
                viewport.setViewPosition(new Point(0, (int) viewport.getViewPosition().getY()));
            }
        } else {
            throw new DisplayNotSetUpException();
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
     * Calculates the value of a unit, which is the amount of pixels used as measurement by the other classes as the
     * standard measurement unit
     * @return an integer with the value of a unit measured in pixels
     */
    public static float getUnitValue(){
        if (panel!=null) {
            return (float) panel.getHeight() / 60;
        } else {
            throw new DisplayNotSetUpException();
        }
    }
    public static boolean checkScreenHeightChanged(){
        if (panel!=null) {
            return !(frame.getHeight() == screenHeight);
        } else {
            throw new DisplayNotSetUpException();
        }
    }
    public static void adaptSize(){
        if(panel!=null) {
            float changePercentage;
            if (frame.getHeight() >= screenWidth) {
                changePercentage = (float) (screenHeight * 100) / frame.getHeight();
            } else {
                changePercentage = (float) (frame.getHeight() * 100) / screenHeight;
            }
            panel.setSize((int) (panel.getWidth() * changePercentage), frame.getHeight() - windowsBarHeight);
            screenHeight = frame.getHeight();
            screenWidth = frame.getWidth();
            viewport.setSize(frame.getWidth(), frame.getHeight() - windowsBarHeight);
            activeTiles.values().forEach(AbstractTile::adaptSize);
        } else {
            throw new DisplayNotSetUpException();
        }
    }
    public static void adaptPosition(){
        if(panel!=null) {
            activeTiles.values().forEach(AbstractTile::adaptPosition);
        } else {
            throw new DisplayNotSetUpException();
        }
    }
    public static void removeTile(String id){
        if(panel!=null) {
            deletedTiles.add(activeTiles.get(id));
            activeTiles.remove(id);
            tilesChanged = true;
        } else {
            throw new DisplayNotSetUpException();
        }
    }
    private static void sendBufferToActiveTiles(){
        if (panel!=null) {
            boolean previouslyEmpty = activeTiles.isEmpty();
            System.out.println(previouslyEmpty);
            buffer.entrySet().stream()
                    .filter((e) -> !activeTiles.containsKey(e.getKey()))
                    .sorted(Comparator.comparingInt((e) -> e.getValue().getLayer())).forEach((e) -> {
                        activeTiles.put(e.getKey(), e.getValue());
                        e.getValue().setVisible(true);
                        panel.add(e.getValue().getLabel());
                        e.getValue().reloadTexture();
                        e.getValue().adaptPosition();
                        e.getValue().adaptSize();
                    });
            if (!previouslyEmpty) {
                activeTiles.values()
                        .forEach(Display::zAlignTile);
            }
            bufferChanged = false;
            buffer.clear();
        } else {
            throw new DisplayNotSetUpException();
        }
    }
    private static void zAlignTile(AbstractTile tile){
        if(panel!=null) {
            int maxLayer = activeTiles.values().stream().map((e) -> panel.getComponentZOrder(e.getLabel())).max(Integer::compareTo).orElse(0);
            System.out.println(maxLayer);
            if (tile.getLayer() != 0 && tile.getLayer() < maxLayer) {
                panel.setComponentZOrder(tile.getLabel(), tile.getLayer());
            } else if (tile.getLayer() >= maxLayer) {
                panel.setComponentZOrder(tile.getLabel(), maxLayer);
            } else {
                panel.setComponentZOrder(tile.getLabel(), 0);
            }
        } else {
            throw new DisplayNotSetUpException();
        }
    }
    public static Boundary getDisplayBounds(){
        if (panel!=null) {
            float x = viewport.getX() * getUnitValue();
            float y = viewport.getY() * getUnitValue();
            float w = viewport.getWidth() * getUnitValue();
            float h = viewport.getHeight() * getUnitValue();
            return new Boundary(x, y, w, h);
        } else {
            throw new DisplayNotSetUpException();
        }
    }
    public static HashMap<String,AbstractTile> cloneActiveTilesWithId() {
        if(panel!=null) {
            return (HashMap<String, AbstractTile>) activeTiles.clone();
        } else {
            throw new DisplayNotSetUpException();
        }
	}
    public static Player getPlayer(){
        return (Player) player;
    }
    public static void searchTexturesFromJson(String jsonPath){
        Gson gson = new Gson();
        try {
            BufferedReader r = new BufferedReader(new FileReader(jsonPath));
            String[] imagePaths = gson.fromJson(r, TypeToken.get(String[].class));
            Arrays.stream(imagePaths).forEachOrdered((e) -> textures.add(new Texture(new ImageIcon(e),e)));
            System.out.println(Arrays.toString(imagePaths));
            System.out.println(Display.getTextures().size());
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<Texture> getTextures(){
        return textures;
    }
    public static Texture getTexture(String id){
        return textures.stream().filter((e)->e.getId().equals(id)).findFirst()
                .orElseThrow(TextureNotFoundException::new);
    }

    public static void setLevel(AbstractLevel level) {
        Display.level = level;
        levelChanged=true;
    }
}

package io.github.engine;

import javax.swing.*;
import java.util.ArrayList;

public final class Display {
    private static JFrame frame;
    private static ArrayList<AbstractTile> buffer=new ArrayList<>();
    private static ArrayList<AbstractTile> activeTiles=new ArrayList<>();
    private static int width, height;
    private static String name;
    private static boolean bufferChanged;

    public static void setup(String name,int width, int height){
        Display.height=height;
        Display.width=width;
        Display.name=name;
    }

    public static void start(){
        Display.frame = new JFrame(name);
        Display.frame.setSize(width,height);
        Display.frame.setVisible(true);
        Display.frame.setLayout(null);
        Display.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void addToBuffer(AbstractTile tile){
        buffer.add(tile);
        bufferChanged=true;
    }

    public static void refresh(){
        if (bufferChanged){
            buffer.stream().sorted()
                    .filter((e)->!activeTiles.contains(e))
                    .forEach(activeTiles::add);
            buffer.clear();
        }
        activeTiles.stream()
                .filter(Entity.class::isInstance)
                .forEach((e)->((Entity)e).updatePosition());
    }
}

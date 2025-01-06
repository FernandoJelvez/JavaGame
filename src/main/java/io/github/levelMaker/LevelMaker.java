package io.github.levelMaker;

import io.github.engine.Display;
import io.github.engine.Synchronization;
import io.github.game.Level;

import javax.swing.*;
import java.io.IOException;

public class LevelMaker {
    public static boolean isStarter = false;
    public static int idBloque = 2;
    public static Cursor cursor;
    public static JLabel label = new JLabel();
    private LevelMaker(){
    }

    public static void main(String[] args) throws IOException {
        Display.setup("goal",800,600);
        Display.searchTexturesFromJson("presets/textures.json");
        isStarter = true;
        cursor = new Cursor(0,0,4,4,false,0);
        cursor.setTexture(Mundo.asignarTexturaAlBloque(1));
        Display.setControl(new ExtendedControl(cursor));
        Display.addToBuffer("cursor", cursor);
        Display.start();
        Synchronization.startClock(30);

        label.setLayout(null);
        label.setOpaque(true);
        label.setBounds(0,0,30,30);
        label.setText("L=1");
        Display.panel.add(label);
    }



}

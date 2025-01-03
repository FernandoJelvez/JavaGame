package io.github.levelMaker;

import io.github.engine.Display;
import io.github.engine.DisplayRefresh;
import io.github.engine.Texture;

import javax.swing.*;
import java.io.IOException;

public class LevelMaker2 {

    public static boolean isStarter = false;
    public static int idBloque = 2;
    public static Cursor cursor;
    public static JLabel label = new JLabel();

    public static void main(String[] args) throws IOException {
        Display.setup("goal",800,600);
        Mundo.setIdPantalla(0);
        isStarter = true;
        cursor = new Cursor(0,0,4,4,false,0);
        cursor.setTexture(new Texture(Mundo.asignarTexturaAlBloque(1)));
        Display.setControl(new ExtendedControl(cursor));
        Display.addToBuffer(cursor, "cursor");
        Display.start();
        DisplayRefresh.startClock(30);

        label.setLayout(null);
        label.setOpaque(true);
        label.setBounds(0,0,30,30);
        label.setText("L=1");
        Display.panel.add(label);
    }



}

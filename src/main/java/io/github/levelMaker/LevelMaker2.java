package io.github.levelMaker;

import io.github.engine.Display;
import io.github.engine.DisplayRefresh;
import io.github.engine.Texture;
import io.github.game.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
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
        cursor = new Cursor(0,0,4,4,false,2);
        cursor.setTexture(new Texture(Mundo.asignarTexturaAlBloque(1)));
        Display.setPlayer(cursor);
        //Tile fondoTextura = new Tile((int) Display.getWidth(), (int) Display.getHeight(), false,-1);
        //fondoTextura.setTexture(new Texture("C:\\Users\\Tomas\\OneDrive\\Desktop\\codigoEnTeoriaFinal\\JavaGame-main\\src\\main\\java\\io\\github\\presets\\FondoCreadorDeNivelesNuevo.png"));
        Display.addToBuffer(cursor, "cursor");
        //Display.addToBuffer(fondoTextura, "fondo");
        Display.start();
        DisplayRefresh.startClock(30);

        label.setLayout(null);
        label.setOpaque(true);
        label.setBounds(0,0,30,30);
        label.setText("L=0");
        Display.panel.add(label);
    }



}

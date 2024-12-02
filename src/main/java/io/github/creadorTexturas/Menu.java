package io.github.creadorTexturas;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Menu {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;


    public static void main(String[] args) throws IOException {

        JFrame menu = new JFrame("test");
        menu.setLayout(null);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setBounds(0,0,800,600);

        //AUDIOS
        Audio musicaDeFonodo = new Audio("C:\\Users\\Tomas\\OneDrive\\Desktop\\testeoCodigoFernando\\presets\\MusicaDeTesteo.wav");
        Audio sonidoDeBoton = new Audio("C:\\Users\\Tomas\\OneDrive\\Desktop\\testeoCodigoFernando\\presets\\SonidoBotonMenuTesteov2.wav");
        musicaDeFonodo.start();
        musicaDeFonodo.loop();

        //JPANEL QUE CUBRE TODA LA PANTALLA
        JPanel imagenesJPanel = new JPanel();
        imagenesJPanel.setLayout(null);
        imagenesJPanel.setBounds(0,0,800,600);

        //IMAGEN QUE CONTIENE TODAS LAS IMAGENES
        BufferedImage imagenCompleta = ImageIO.read(new File("C:\\Users\\Tomas\\OneDrive\\Desktop\\testeoCodigoFernando\\presets\\ImagenesDeMenuJuego.png"));

        //BOTON
        ImageIcon imagenBotonIcon = new ImageIcon(imagenCompleta.getSubimage(0,131,490,65)); //y = 131
        JButton botonJuego = new JButton("START GAME");
        botonJuego.setFont(new Font("Arial", Font.BOLD,20));
        botonJuego.setIcon(imagenBotonIcon);
        botonJuego.setBounds(WIDTH / 2 - ( imagenBotonIcon.getIconWidth() / 2  ),HEIGHT/2 - (imagenBotonIcon.getIconHeight() / 2),490,65);

        //LO QUE HACE EL BOTON
        botonJuego.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sonidoDeBoton.detener();
                sonidoDeBoton.start();
            }
        });

        //TITULO
        JLabel tituloDelJuegoJLabel = new JLabel(new ImageIcon(imagenCompleta.getSubimage(0,0,620,130)));
        tituloDelJuegoJLabel.setBounds(WIDTH / 2 - (320),40,620,130);

        //FONDO
        JLabel fondoJLabel = new JLabel(new ImageIcon(imagenCompleta.getSubimage(WIDTH,0,WIDTH,HEIGHT)));
        fondoJLabel.setBounds(0,0,WIDTH,HEIGHT);

        botonJuego.setHorizontalTextPosition(SwingConstants.CENTER);
        botonJuego.setVerticalTextPosition(SwingConstants.CENTER);

        imagenesJPanel.add(tituloDelJuegoJLabel);
        imagenesJPanel.add(fondoJLabel);
        imagenesJPanel.add(botonJuego);
        menu.add(imagenesJPanel);
        menu.setVisible(true);
    }

}

package io.github.levelMaker;

import com.google.gson.Gson;
import io.github.engine.AbstractTile;
import io.github.engine.Display;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class levelMaker {

    public static final int WIDTH_PANTALLA = 800;
    public static final int HEIGHT_PANTALLA = 600;
    public static int cantidad_De_Pantallas;
    public static BufferedImage imagenCompleta;
    public static boolean isSolid;
    public static int id_BloqueActual;
    public static int id_PantallaActual;
    public static JPanel panel_De_Bloques;
    public static JLabel previsualizador_Del_Bloque;
    public static HashMap<Point, AbstractTile> bloques;
    public static ArrayList<JPanel> bloquesChunk;
    public static JPanel panel_De_Control;
    public static JPanel panel_De_fondo;

    static {
        try {
            imagenCompleta = ImageIO.read(new File("C:\\Users\\Tomas\\Downloads\\JavaGame-main\\JavaGame-main\\src\\main\\presets\\imagenTesteo1.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JFrame frame;

    public static void main(String[] args) throws IOException {

        bloques = new HashMap<>();
        bloquesChunk = new ArrayList<>();

        cantidad_De_Pantallas = 5;
        for (int i = 0; i < cantidad_De_Pantallas; i++) {
            bloquesChunk.add(crearPanel(WIDTH_PANTALLA, HEIGHT_PANTALLA, false));
        }

        isSolid = true;
        id_BloqueActual = 2;

        Display.setup("Creador de niveles", WIDTH_PANTALLA, HEIGHT_PANTALLA);
        frame = Display.getFrame();
        frame.setLocationRelativeTo(null);

        panel_De_Control = crearPanel(WIDTH_PANTALLA, HEIGHT_PANTALLA, false);

        JPanel panel_De_Informacion = crearPanel(80, 80, false);
        panel_De_Informacion.setLocation(0, 0);
        panel_De_Informacion.setBackground(Color.lightGray);
        panel_De_Informacion.setOpaque(true);

        previsualizador_Del_Bloque = crearJLabel(20, 20);
        previsualizador_Del_Bloque.setLocation(10, 2);
        previsualizador_Del_Bloque.setIcon(asignarTextura(2));
        panel_De_Informacion.add(previsualizador_Del_Bloque);

        JLabel texto_ID_del_Bloque = crearJLabel(20, 20);
        texto_ID_del_Bloque.setLocation(10, 16);
        texto_ID_del_Bloque.setText("ID: 2");
        texto_ID_del_Bloque.setFont(new Font("Arial", Font.BOLD, 7));
        texto_ID_del_Bloque.setHorizontalAlignment(SwingConstants.CENTER);
        texto_ID_del_Bloque.setVerticalAlignment(SwingConstants.CENTER);
        panel_De_Informacion.add(texto_ID_del_Bloque);

        JLabel texto_bloqueSolido = crearJLabel(33, 20);
        texto_bloqueSolido.setLocation(5, 24);
        texto_bloqueSolido.setText("Solido: SI");
        texto_bloqueSolido.setFont(new Font("Arial", Font.BOLD, 6));
        texto_bloqueSolido.setHorizontalAlignment(SwingConstants.CENTER);
        texto_bloqueSolido.setVerticalAlignment(SwingConstants.CENTER);
        panel_De_Informacion.add(texto_bloqueSolido);

        JLabel detectorComando = crearJLabel(40, 40);
        detectorComando.setLocation(0, 40);
        detectorComando.setBackground(Color.green);
        detectorComando.setOpaque(true);
        panel_De_Informacion.add(detectorComando);

        JLabel texto_ID_De_La_Pantalla = crearJLabel(40, 40);
        texto_ID_De_La_Pantalla.setLocation(40, 0);
        texto_ID_De_La_Pantalla.setText("P: 0");
        texto_ID_De_La_Pantalla.setFont(new Font("Arial", Font.BOLD, 16));
        texto_ID_De_La_Pantalla.setHorizontalAlignment(SwingConstants.CENTER);
        texto_ID_De_La_Pantalla.setVerticalAlignment(SwingConstants.CENTER);
        panel_De_Informacion.add(texto_ID_De_La_Pantalla);

        panel_De_Bloques = crearPanel(WIDTH_PANTALLA, HEIGHT_PANTALLA, false);

        JPanel panel_De_Indicador = crearPanel(WIDTH_PANTALLA, HEIGHT_PANTALLA, false);
        JLabel indicador = crearIndicador(true);
        botones(indicador, texto_ID_del_Bloque, texto_bloqueSolido, texto_ID_De_La_Pantalla);
        panel_De_Indicador.add(indicador);

        panel_De_fondo = crearPanel(WIDTH_PANTALLA, HEIGHT_PANTALLA, true);
        JLabel imagenFondo = crearJLabel(WIDTH_PANTALLA, HEIGHT_PANTALLA);
        imagenFondo.setIcon(new ImageIcon("C:\\Users\\Tomas\\Downloads\\JavaGame-main\\JavaGame-main\\src\\main\\presets\\FondoCreadorDeNiveles.png"));
        panel_De_fondo.add(imagenFondo);

        panel_De_Control.add(panel_De_Indicador);
        panel_De_Control.add(panel_De_Informacion);

        frame.add(panel_De_Control);
        frame.add(panel_De_Bloques);
        frame.add(panel_De_fondo);
        frame.revalidate();
        frame.repaint();
        Display.start();
    }

    public static void botones(JLabel indicador, JLabel texto_ID_del_Bloque, JLabel texto_bloqueSolido, JLabel texto_ID_De_La_Pantalla) {
        frame.addKeyListener(new KeyListener() {
            final HashSet<Integer> teclaPresionada = new HashSet<>();
            int x = 0;
            int y = 0;
            final Dimension dimension = new Dimension(20, 20);

            final Timer cambiarIndicador = new Timer(200, _ -> indicador.setIcon(asignarTextura(0)));

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

                teclaPresionada.add(e.getKeyCode());

                //GUARDAR PANTALLA
                if (teclaPresionada.contains(KeyEvent.VK_C)) {
                    Point[] puntosARellenar = Comando.start(indicador.getLocation());
                    ImageIcon textura = asignarTextura(id_BloqueActual);

                    if (!Comando.getRellenar().equals("cancelar")) {
                        if (Comando.getRellenar().equals("place")) {
                            Arrays.stream(puntosARellenar)
                                    .filter(punto -> !bloques.containsKey(punto)).
                                    forEach(punto -> {
                                        bloques.put(punto, new AbstractTile(punto, dimension, isSolid, 1, id_BloqueActual) {
                                        });
                                        bloques.get(punto).setIcon(textura);
                                        panel_De_Bloques.add(bloques.get(punto));
                                    });
                            System.out.println("operacion exitosa, presiona 'c' de nuevo para ingresar otro comando");
                        } else {
                            System.out.println("remove");
                            Arrays.stream(puntosARellenar).
                                    filter(punto -> bloques.containsKey(punto))
                                    .forEach(punto -> {
                                        int i = 2; //SIN ESTO EL "PINTAR" NO FUNCIONA XDDDDD
                                        panel_De_Bloques.remove(bloques.get(punto));
                                        bloques.remove(punto);
                                    });
                            System.out.println("operacion exitosa, presiona 'c' de nuevo para ingresar otro comando");
                        }
                        frame.revalidate();
                        frame.repaint();
                    }
                }

                if (teclaPresionada.contains(KeyEvent.VK_H) && id_PantallaActual > 0) {
                    cambioDePantalla(-1);
                    texto_ID_De_La_Pantalla.setText("P: " + id_PantallaActual);
                }

                if (teclaPresionada.contains(KeyEvent.VK_J) && id_PantallaActual < cantidad_De_Pantallas - 1) {
                    cambioDePantalla(1);
                    texto_ID_De_La_Pantalla.setText("P: " + id_PantallaActual);
                }

                //ENTERRRRRR
                if (teclaPresionada.contains(KeyEvent.VK_ENTER) && bloques.get(indicador.getLocation()) == null) {
                    indicador.setIcon(asignarTextura(1));
                    cambiarIndicador.restart();
                    cambiarIndicador.start();

                    ImageIcon textura = asignarTextura(id_BloqueActual);
                    Point punto = new Point(x, y);

                    bloques.put(punto, new AbstractTile(punto, dimension, isSolid, 1, id_BloqueActual) {
                    });
                    bloques.get(punto).setIcon(textura);

                    panel_De_Bloques.add(bloques.get(punto));
                    frame.revalidate();
                    frame.repaint();
                }

                //REMOVE
                if (teclaPresionada.contains(KeyEvent.VK_BACK_SPACE) && bloques.get(indicador.getLocation()) != null) {
                    indicador.setIcon(asignarTextura(1));
                    cambiarIndicador.restart();
                    cambiarIndicador.start();

                    Point punto = new Point(x, y);
                    panel_De_Bloques.remove(bloques.get(punto));
                    bloques.remove(punto);
                }

                if (teclaPresionada.contains(KeyEvent.VK_Z) && id_BloqueActual > 2) {
                    id_BloqueActual = id_BloqueActual - 1;
                    previsualizador_Del_Bloque.setIcon(asignarTextura(id_BloqueActual));
                    texto_ID_del_Bloque.setText("ID: " + id_BloqueActual);
                }

                if (teclaPresionada.contains(KeyEvent.VK_X)) {
                    id_BloqueActual = id_BloqueActual + 1;
                    previsualizador_Del_Bloque.setIcon(asignarTextura(id_BloqueActual));
                    texto_ID_del_Bloque.setText("ID: " + id_BloqueActual);
                }

                if (teclaPresionada.contains(KeyEvent.VK_Q)) {
                    if (isSolid) {
                        isSolid = false;
                        texto_bloqueSolido.setText("Solido NO");
                    } else {
                        isSolid = true;
                        texto_bloqueSolido.setText("Solido SI");
                    }
                }

                if (teclaPresionada.contains(KeyEvent.VK_W) && indicador.getLocation().getY() > 0) {
                    y -= 20;
                    indicador.setLocation(x, y);
                } //ARRIBA
                if (teclaPresionada.contains(KeyEvent.VK_S) && indicador.getLocation().getY() < HEIGHT_PANTALLA - 20) {
                    y += 20;
                    indicador.setLocation(x, y);
                } //ABAJO
                if (teclaPresionada.contains(KeyEvent.VK_D) && indicador.getLocation().getX() < WIDTH_PANTALLA - 20) {
                    x += 20;
                    indicador.setLocation(x, y);
                } //DERECHA
                if (teclaPresionada.contains(KeyEvent.VK_A) && indicador.getLocation().getX() > 0) {
                    x -= 20;
                    indicador.setLocation(x, y);
                } //IZQUIERDA



                if (e.getKeyCode() == KeyEvent.VK_M) {
                    HashMap<Integer, ArrayList<AbstractTile>  > pantalla = new HashMap<>();
                    ArrayList<AbstractTile> componentes = new ArrayList<>();
                    int i = 0;
                    for (JPanel valor : bloquesChunk){
                        pantalla.clear();
                        componentes.clear();
                        Arrays.stream(valor.getComponents()).forEach( a->{
                            componentes.add((AbstractTile) a);
                        });
                        pantalla.put(i,componentes);
                        GuardarMapa.agregarPantalla(i,pantalla);
                    }
                    GuardarMapa.guardarMapa();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                teclaPresionada.remove(e.getKeyCode());
            }
        });
    }

    public static ImageIcon asignarTextura(int id_Bloque) {
        int x;
        int y = 0;
        if (id_Bloque < 10) {
            x = id_Bloque * 20;
        } else {
            x = (id_Bloque % 10) * 20;
            y = ((id_Bloque - (id_Bloque % 10)) / 10) * 20;
        }
        return new ImageIcon(imagenCompleta.getSubimage(x, y, 20, 20));
    }

    public static JLabel crearIndicador(boolean isNormal) throws IOException {
        JLabel indicador = crearJLabel(20, 20);
        indicador.setLocation(0, 100);
        File imagen_Completa = new File("C:\\Users\\Tomas\\Downloads\\JavaGame-main\\JavaGame-main\\src\\main\\presets\\imagenTesteo1.png");
        if (isNormal) {
            indicador.setIcon(new ImageIcon(ImageIO.read(imagen_Completa).getSubimage(0, 0, 20, 20)));
        } else {
            indicador.setIcon(new ImageIcon(ImageIO.read(imagen_Completa).getSubimage(20, 0, 20, 20)));
        }
        return indicador;
    }

    public static JPanel crearPanel(int witdh_Panel, int height_Panel, boolean isOpaque) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, witdh_Panel, height_Panel);
        panel.setOpaque(isOpaque);
        return panel;
    }

    public static JLabel crearJLabel(int witdh_Label, int height_Label) {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setBounds(0, 0, witdh_Label, height_Label);
        return label;
    }

    public static void cambioDePantalla(int sumador) {
        bloques.forEach((_, valor) -> bloquesChunk.get(id_PantallaActual).add(valor));
        bloques.clear();
        id_PantallaActual = id_PantallaActual + sumador;
        frame.remove(panel_De_Bloques);
        panel_De_Bloques = bloquesChunk.get(id_PantallaActual);
        Arrays.stream(bloquesChunk.get(id_PantallaActual).getComponents()).forEach(valor -> {
            bloques.put(valor.getLocation(), (AbstractTile) valor);
            panel_De_Bloques.add(valor);
        });
        frame.add(panel_De_Control);
        frame.add(panel_De_Bloques);
        frame.add(panel_De_fondo);
        frame.revalidate();
        frame.repaint();
    }

    public static void guardarMapa(){
        Gson hola = new Gson();
    }
}
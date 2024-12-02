package io.github.creadorTexturas;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

class Audio {

    //VOLUMENES PERMITIDOS
    //MAXIMO: 6.0 dB
    //MINIMO: -80.0 dB
    //NORMAL: 0 dB

    private FloatControl volumen;
    private Clip clip = null;
    private boolean isLoop = false;

    public Audio(String rutaArchivo) {
        File archivo = new File(rutaArchivo);
        clip = instanciarClip(clip, archivo);
    }

    public Audio(File archivo) {
        clip = instanciarClip(clip, archivo);
    }

    public Audio(File archivo, float volumen) {
        clip = instanciarClip(clip, archivo);
        setVolumen(volumen);
    }

    public void start (){
        clip.start();
        if (isLoop){
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    //"CLIP.STOP" solo pausa la reproduccion, si se inicia nuevamente se empezara desde el frame que quedo
    public void pausar(){
        clip.stop();
    }

    public void detener(){
        clip.stop();
        clip.setFramePosition(0);
    }


    private Clip instanciarClip(Clip clip, File archivo) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(archivo);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {
        }
        return clip;
    }

    public void setVolumen(float volumen) {
        this.volumen = (FloatControl) this.clip.getControl(FloatControl.Type.MASTER_GAIN);
        this.volumen.setValue(volumen);
    }

    public float getVolumen() {
        return this.volumen.getValue();
    }


    public void loop(){
        isLoop = true;
        clip.setLoopPoints(0,clip.getFrameLength()-1);
    }

    public boolean isLoop(){
        return isLoop;
    }

    public void loop(int primerFrame, int ultimoFrame){
        isLoop = true;
        clip.setLoopPoints(primerFrame,ultimoFrame);
    }

}

/*

        JFrame frame = new JFrame("hola");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);

        ArrayList<JLabel> informacionDeMapa = new ArrayList<>();

        JPanel panelDeMapa = new JPanel();
        panelDeMapa.setLayout(null);
        panelDeMapa.setBounds(0,0,800,600);

        JPanel panelDeBotones = new JPanel();
        panelDeBotones.setLayout(null);
        panelDeBotones.setBounds(0,0,800,600);

        ImageIcon imageIcon = new ImageIcon("C:\\Users\\Tomas\\OneDrive\\Desktop\\testeoCodigoFernando\\presets\\botonMenuPruebaPresionado.png");
        JLabel indicadorTile = new JLabel(imageIcon);
        indicadorTile.setLayout(null);

        int tamañoTileX = (int) frame.getWidth()  / 100;
        int tamañoTileY = (int) frame.getHeight() / 100;
        indicadorTile.setBounds(0,0,tamañoTileX,tamañoTileY);
        panelDeBotones.add(indicadorTile);


        frame.addKeyListener(new KeyListener() {
            int posicionX = 0;
            int posicionY = 0;
            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN){
                    posicionY = posicionY + tamañoTileY;
                    indicadorTile.setBounds(posicionX,posicionY,tamañoTileX,tamañoTileY);
                }
                if (e.getKeyCode() == KeyEvent.VK_UP){
                    posicionY = posicionY - tamañoTileY;
                    indicadorTile.setBounds(posicionX,posicionY,tamañoTileX,tamañoTileY);
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                    posicionX = posicionX + tamañoTileX;
                    indicadorTile.setBounds(posicionX,posicionY,tamañoTileX,tamañoTileY);
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT){
                    posicionX = posicionX - tamañoTileX;
                    indicadorTile.setBounds(posicionX,posicionY,tamañoTileX,tamañoTileY);

                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    JLabel bloque = new JLabel(imageIcon);
                    bloque.setLayout(null);
                    bloque.setBounds(posicionX, posicionY, tamañoTileX, tamañoTileY);
                    bloque.setOpaque(false);
                    panelDeMapa.add(bloque);
                }
                if (e.getKeyCode() == KeyEvent.VK_H){

                    panelDeMapa.setVisible(false);
                    System.out.println("BOTONES");
                    panelDeBotones.setVisible(true);
                    panelDeMapa.revalidate();
                    panelDeMapa.repaint();
                    System.out.println("ENTER");

                }
                        if (e.getKeyCode() == KeyEvent.VK_K){

                    panelDeMapa.setVisible(true);
                    System.out.println("MAPA");
                    panelDeBotones.setVisible(false);


        }


        }
@Override
public void keyReleased(KeyEvent e) {

}
        });



                frame.setFocusable(true);
        frame.requestFocusInWindow();

        frame.add(panelDeMapa);
        frame.add(panelDeBotones);
        frame.setVisible(true);
 */
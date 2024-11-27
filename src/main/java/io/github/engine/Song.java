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

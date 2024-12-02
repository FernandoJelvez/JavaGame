package io.github.engine;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
public class Song {

	//Allowed volumes
	//Max: 6.0 dB
	//Min: -80.0 dB
	//Normal: 0 dB

	private FloatControl volume;
	private Clip clip = null;
	private boolean isLoop = false;

	public Song(String fileRoute) {
		File file = new File(fileRoute);
		clip = instantiateClip(file);
	}

	public Song(File file) {
		clip = instantiateClip(file);
	}

	public Song(File file, float volume) {
		clip = instantiateClip(file);
		setVolume(volume);
	}

	public void start (){
		clip.start();
		if (isLoop){
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

	//"CLIP.STOP" solo pausa la reproduccion, si se inicia nuevamente se empezara desde el frame que quedo
	public void pause(){
		clip.stop();
	}

	public void stop(){
		clip.stop();
		clip.setFramePosition(0);
	}


	private Clip instantiateClip(File file) {
		Clip clip=null;
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return clip;
	}

	public void setVolume(float volume) {
		this.volume = (FloatControl) this.clip.getControl(FloatControl.Type.MASTER_GAIN);
		this.volume.setValue(volume);
	}
	public void setVolumePercentage(float volume) {
		this.volume = (FloatControl) this.clip.getControl(FloatControl.Type.MASTER_GAIN);
		this.volume.setValue((float) ((volume*0.86)-80));
	}

	public float getVolume() {
		return this.volume.getValue();
	}


	public void loop(){
		isLoop = true;
		clip.setLoopPoints(0,clip.getFrameLength()-1);
	}

	public boolean isLoop(){
		return isLoop;
	}

	public void loop(int firstSample, int lastSample){
		isLoop = true;
		clip.setLoopPoints(firstSample,lastSample);
	}

}
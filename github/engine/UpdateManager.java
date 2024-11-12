package io.github.engine;
import java.time.Duration;
import java.time.Instant;

/**
 * This class is in charge of measuring the delta time, which is in other words the
 * time between every refresh of the game, it is used to refresh the display and pass this value
 * to the Physics class, so that the trajectory of the an Entity corresponds with the time
 * elapsed between frames, this, in turn allows for the movements across time to be the same
 * between several devices, fast or slow (to some degree)
 */
public final class UpdateManager implements Runnable {
	private static long deltaTime;
	private UpdateManager(){
	}
	public static void startClock(){
		UpdateManager clock = new UpdateManager();
		Thread clockThread = new Thread(clock);
		clockThread.start();
	}
	public static long getDeltaTime(){
		return deltaTime;
	}
	@Override
	public void run() {
		while(true){
			Instant initialTime=Instant.now();
			Display.refresh();
			Instant finalTime = Instant.now();
			deltaTime=Duration.between(initialTime,finalTime).toMillis();
			while(deltaTime<1){
				finalTime = Instant.now();
				deltaTime=Duration.between(initialTime,finalTime).toMillis();
			}
			System.out.println("time!");
			System.out.println(deltaTime);
		}
	}
	/*
	public void refresh(){
		Display.refresh();
	}
	*/
}

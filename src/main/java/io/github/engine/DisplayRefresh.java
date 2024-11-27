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
public class DisplayRefresh implements Runnable {
	private static float deltaTime;
	private static float idealFrameRate;

	/**
	 * This class is in charge of measuring the delta-time between frames and calling the Display refresh method
	 * @see Display
	 */
	private DisplayRefresh(){
	}

	/**
	 * Starts the new thread, starting the Display Refresh cycle
	 */
	public static void startClock(){
		DisplayRefresh clock = new DisplayRefresh();
		Thread clockThread = new Thread(clock);
		clockThread.start();
	}

	/**
	 * @return {@code deltaTime}
	 */
	public static double getDeltaTime(){
		return deltaTime;
	}

	@Override
	public void run() {
		double idealDeltaTime = 1.0 / idealFrameRate;
		while(true){
			Instant initialTime=Instant.now(); //gets the starting point of the time measurement
			Display.refresh(); //calls the Display refresh method
			Instant finalTime = Instant.now(); //after the Display refresh ends, the ending instant is measured

			//the time elapsed between the two measurements converted to milliseconds, then multiplied by 0.001 to be
			//equivalent to seconds, but with the decimal part
			deltaTime=Duration.between(initialTime,finalTime).toMillis()*0.001;

			//the loop ensures that some time is elapsed in the cycle in order of allowing a consistent frame rate
			if(deltaTime<idealDeltaTime){
				try {
					Thread.sleep((int)((0.016-deltaTime)*1000));
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				finalTime=Instant.now();
				deltaTime = Duration.between(initialTime,finalTime).toMillis()*0.001;
			}
		}
	}
}

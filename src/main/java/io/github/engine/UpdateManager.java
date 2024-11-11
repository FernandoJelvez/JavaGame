package io.github.engine;
import java.time.Duration;
import java.time.Instant;

public class UpdateManager implements Runnable {
	public UpdateManager(){

	}
	public static void startClock(){
		UpdateManager clock = new UpdateManager();
		Thread clockThread = new Thread(clock);
		clockThread.start();
	}

	@Override
	public void run() {
		while(true){
			Instant initialTime=Instant.now();
			long elapsedTime =0;
			while(elapsedTime <1){
				Instant finalTime = Instant.now();
				elapsedTime=Duration.between(initialTime,finalTime).toSeconds();
			}
			System.out.println("time!");
		}
	}
}

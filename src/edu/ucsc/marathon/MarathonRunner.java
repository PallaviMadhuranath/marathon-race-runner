package edu.ucsc.marathon;

/**
 * This class implements a runnable interface. Hence, it has a run method that
 * calculates the distance covered by each thread until race is completed by any
 * one of the threads.
 * 
 * @author pallavidas
 *
 */
public class MarathonRunner implements Runnable {
	private String racerName;
	private int restValue, speed;
	private boolean stop = false;
	public final static int MAX_DISTANCE = 1000;

	/**
	 * Constructor with parameters.
	 * 
	 * @param racerName
	 *            - gets racerName from the data source.
	 * @param speed
	 *            - gets speed from the data source.
	 * @param restValue
	 *            - gets restvalue from the data source.
	 */
	public MarathonRunner(String racerName, int speed, int restValue) {
		this.racerName = racerName;
		this.speed = speed;
		this.restValue = restValue;
	}

	/**
	 * Its a get method that returns name of the runner.
	 * 
	 * @return - racerName of type String
	 */
	public String getRacerName() {
		return racerName;
	}

	/**
	 * Its a set method that sets the value of stop flag, that is used to
	 * control the running of threads.
	 * 
	 * @param stop
	 *            - boolean type
	 */
	public void setStop(boolean stop) {
		this.stop = stop;
	}

	@Override
	public String toString() {
		return "MarathonRunner [racerName=" + racerName + ", speed=" + speed + ", restValue=" + restValue + "]";
	}

	@Override
	public synchronized void run() {
		boolean raceComplete = false;
		int distance = 0;

		while (!stop && distance < MAX_DISTANCE) {
			try {
				boolean shouldRest = decideRest(restValue);
				if (!shouldRest && !stop) {
					distance = distance + speed;
					if (distance > MAX_DISTANCE) {
						distance = MAX_DISTANCE;
					}
					/*
					 * check point for status of the race.
					 */
					raceComplete = DecideWinner.raceStatus(distance, racerName);
				}
				Thread.sleep(100);
				if (raceComplete) {
					break;
				}

			} catch (InterruptedException e) {
				break;
			}

		}
	}

	/**
	 * This method decides if a thread has to rest or not. And thus, return a
	 * boolean value. (true - rest needed, false - no rest).
	 * 
	 * @param restValue
	 *            - type int
	 * @return - shouldRest of type boolean
	 */
	public boolean decideRest(int restValue) {
		int rand = (int) (Math.random() * 100);
		boolean shouldRest = true;
		if (restValue < rand) {
			shouldRest = false;
		} else {
			shouldRest = true;
		}

		return shouldRest;
	}
}

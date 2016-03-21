package edu.ucsc.marathon;

/**
 * This class declares winner name as soon as race is completed.
 * 
 * @author pallavidas
 *
 */
public class DecideWinner {
	/**
	 * This method returns status of the race.
	 * 
	 * @param totalDistanceCovered
	 *            distance covered by a runner.
	 * @param racerName
	 *            name of the runner
	 * @return -boolean value
	 */
	public static synchronized boolean raceStatus(int totalDistanceCovered, String racerName) {
		if (MarathonRaceApp.isRaceComplete()) {
			return true;
		}

		System.out.println("Distance covered by " + racerName + " is: " + totalDistanceCovered + "meters");

		if ((totalDistanceCovered == MarathonRunner.MAX_DISTANCE)) {
			MarathonRaceApp.finished(racerName);
			return true;
		} else {
			return false;
		}
	}

}

package edu.ucsc.marathon.datasources;

import java.util.ArrayList;

import edu.ucsc.marathon.MarathonRunner;

/**
 * This class implements an interface RunnersReader. It defines the abstract
 * method of the interface. It add default details of runners into an arrayList.
 * This arrayList contains the details of all the runners, such as name, speed
 * and rest value.
 * 
 * @author pallavidas
 *
 */
public class MarathonDefault implements RunnersReader {
	ArrayList<MarathonRunner> runnersList = null;

	/**
	 * This is a get method that returns an arrayList of default runners
	 * information.
	 * 
	 */
	public ArrayList<MarathonRunner> getRunnersInfo() {
		runnersList = new ArrayList<>();
		runnersList.add(new MarathonRunner("Tortoise", 10, 0));
		runnersList.add(new MarathonRunner("Hare", 100, 90));
		return runnersList;
	}
}

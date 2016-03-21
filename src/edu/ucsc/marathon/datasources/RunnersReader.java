package edu.ucsc.marathon.datasources;

import java.util.ArrayList;

import edu.ucsc.marathon.MarathonRunner;

/**
 * This interface consists of method that returns ArrayList that hold the
 * runners information.
 * 
 * @author pallavidas
 *
 */
public interface RunnersReader {
	/**
	 * This method returns an arrayList that contains runners information such
	 * as name, restValue and speed.
	 * 
	 * @return An arrayList of type MarathonRunner
	 */
	public ArrayList<MarathonRunner> getRunnersInfo();
}

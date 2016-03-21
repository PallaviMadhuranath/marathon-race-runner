package edu.ucsc.marathon.datasources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import edu.ucsc.marathon.MarathonRunner;
import edu.ucsc.marathon.validator.Validator;

/**
 * This class implements an interface RunnersReader. It defines the abstract
 * method of the interface. It reads all the data from a text file into an
 * arrayList. This arrayList contains the details of all the runners, such as
 * name, speed and rest value.
 * 
 * @author pallavidas
 *
 */
public class MarathonTextFile implements RunnersReader {
	private String fileName;

	/**
	 * Constructor of class MarathonTextFile.
	 * 
	 * @param fileName
	 *            - gets the filename from user.
	 */
	public MarathonTextFile(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * This returns an arrayList of runners information, after reading from a
	 * text file.
	 * 
	 */
	public ArrayList<MarathonRunner> getRunnersInfo() {
		ArrayList<MarathonRunner> runnersList = new ArrayList<>();

		if (!Validator.validateFileName(fileName)) {
			// Invalid file return no runners
			return runnersList;
		}

		final String FIELD_SEP = "\t";
		Path runnerListPath = Paths.get(fileName);
		File runnerFile = runnerListPath.toFile();
		try (BufferedReader in = new BufferedReader(new FileReader(runnerFile))) {
			String line = in.readLine();
			while (line != null) {
				String[] columns = line.split(FIELD_SEP);
				if (columns.length == 3) {
					MarathonRunner runnerObj = getRunnersObj(columns);
					runnersList.add(runnerObj);
				}
				line = in.readLine();
			}
			in.close();
		} catch (IOException | NumberFormatException e) {
			System.out.println("File is not in a valid format");
		}
		return runnersList;
	}

	/**
	 * this method returns an object that hold the details of a racer.
	 * 
	 * @param columns
	 *            - An array of type String
	 * @return An object of type MarathonRunner
	 */
	public MarathonRunner getRunnersObj(String[] columns) {
		MarathonRunner runnerObj = null;
		String runnerName = columns[0];
		String runnerSpeed = columns[1];
		String restPercent = columns[2];
		int runnersSpeed = Integer.parseInt(runnerSpeed);
		int restValue = Integer.parseInt(restPercent);
		runnerObj = new MarathonRunner(runnerName, runnersSpeed, restValue);
		return runnerObj;
	}
}

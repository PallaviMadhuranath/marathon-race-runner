package edu.ucsc.marathon.validator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import edu.ucsc.marathon.MarathonRunner;

/**
 * This class validates the input data. If the input data is of type
 * double/String, then the execution proceeds, else it continues to prompt the
 * type of error until user enters correct data.
 * 
 * @author pallavidas
 */
public class Validator {

	/**
	 * ** This method checks a value of type double. If its less than specified
	 * minimum value, then it throws an error.
	 * 
	 * @param sc
	 *            - Input sc of type Scanner
	 * @param prompt
	 *            - Takes message of type String
	 * @param min
	 *            - Minimum value of type int
	 * @param max
	 *            - Maximum value of type int
	 * @return - integer value
	 */
	public static int getIntWithinRange(Scanner sc, String prompt, int min, int max) {
		boolean isValid = false;
		int i = 0;
		while (isValid == false) {
			i = getInt(sc, prompt);
			if (i <= min || i > max) {
				System.out.println("Error!Enter value greater than" + " " + min + " and less than" + " " + (max + 1));
			} else {
				isValid = true;
			}

		}
		return i;
	}

	/**
	 * This method checks if the input has a value of type double. If not it
	 * throws an error.
	 * 
	 * @param sc
	 *            - Input sc of type Scanner
	 * @param prompt
	 *            - Takes message of type String
	 * @return - a value of type double
	 */
	public static int getInt(Scanner sc, String prompt) {

		int i = 0;
		boolean isValid = false;
		while (isValid == false) {
			System.out.print(prompt);
			if (sc.hasNextInt()) {
				i = sc.nextInt();
				isValid = true;
			} else {
				System.out.println("Error!Enter an Integer value\n");
			}

			sc.nextLine();
		}
		return i;
	}

	/**
	 * This method checks if the runnersList contains any data in it.If the file
	 * is not valid, size of the runnersList is zero. Then this method displays
	 * file not found message.
	 * 
	 * @param runnersList
	 *            runnersList that is returned after reading a data source.
	 * @return - true if runnersList is valid (between 0 and 5)
	 */
	public static boolean validateRunnersList(ArrayList<MarathonRunner> runnersList) {
		if (runnersList == null || (runnersList.size() <= 0)) {
			System.out.println("No runners found. Please try again.\n");
			return false;
		}
		if (runnersList.size() > 5) {
			System.out.println("Not more than 5 runners are allowed in Marathon. Try again!\n");
			return false;
		}
		System.out.println("Ready set..Go!\n");
		return true;
	}

	/**
	 * This method checks if the fileName is empty or not. And also checks if
	 * the file exists.
	 * 
	 * @param fileName
	 *            - fileName on which the check will be done.
	 * @return - true if fileName is valid gives a valid file.
	 */
	public static boolean validateFileName(String fileName) {
		if (fileName == null || fileName.trim().length() == 0) {
			System.out.println("Error! File not found.");
			return false;
		}
		Path filePath = Paths.get(fileName);
		if (!Files.exists(filePath)) {
			System.out.println("Error! File not found.");
			return false;
		}
		return true;
	}

}

package edu.ucsc.marathon;

import java.util.ArrayList;
import java.util.Scanner;

import edu.ucsc.marathon.datasources.MarathonDB;
import edu.ucsc.marathon.datasources.MarathonDefault;
import edu.ucsc.marathon.datasources.MarathonTextFile;
import edu.ucsc.marathon.datasources.MarathonXML;
import edu.ucsc.marathon.datasources.RunnersReader;
import edu.ucsc.marathon.validator.Validator;

/**
 * This Application simulates a marathon race between 2 or more participants. It
 * shows user a menu for the data sources to choose from. Based on the choice,
 * runners data is read from the source and race is run for that many of
 * runners. The data sources available are - Derby database, XML file, text file
 * and default. Once any racer has won the race, winner is declared and other
 * runner concedes. Then user is asked if he wants to continue to use this
 * application.
 * 
 * @author pallavidas
 *
 */
public class MarathonRaceApp {
	private static boolean isFinished = false;
	private static ArrayList<MarathonRunner> runnersList = null;
	private static boolean continueApp = true;
	private static Scanner sc;

	/**
	 * This method starts the application.
	 * 
	 * @param args
	 *            -An array of type String.
	 * @throws InterruptedException
	 *             - thrown while thread is sleeping
	 */
	public static void main(String[] args) throws InterruptedException {
		sc = new Scanner(System.in);
		while (continueApp) {
			System.out.println("Welcome to Marathon Race Runner Application\n");
			String inputChoice = "Select your data source:\n" + "1. Derby database\n" + "2. XML file\n"
					+ "3. Text file\n" + "4. Default two runners\n" + "5. Exit\n";
			int choice = Validator.getIntWithinRange(sc, inputChoice, 0, 5);
			if (choice == 5) {
				System.out.println("Thank you for using Marathon Race Application");
				break;
			} else {

				runnersList = menuChoice(choice);

				if (!Validator.validateRunnersList(runnersList)) {
					continue;
				}

				startRace();

				while (!isFinished) {
					// stays in this loop until isFinished becomes true.
					Thread.sleep(500);
				}

				continueApplication();
			}
		}
		sc.close();
	}

	/**
	 * This method returns an arrayList of runners information (name, speed and
	 * rest value) from the data source depending on the users choice from the
	 * menu.
	 * 
	 * @param choice
	 *            - choice of type int
	 * @return - An arrayList
	 */
	public static ArrayList<MarathonRunner> menuChoice(int choice) {
		RunnersReader runnersReader = null;
		if (choice == 1) {
			runnersReader = new MarathonDB();
		} else if (choice == 2) {
			System.out.print("Enter XML file name of the file: ");
			String fileName = sc.nextLine();
			runnersReader = new MarathonXML(fileName);
		} else if (choice == 3) {
			System.out.print("Enter Text file name of the file: ");
			String fileName = sc.nextLine();
			runnersReader = new MarathonTextFile(fileName);
		} else if (choice == 4) {
			runnersReader = new MarathonDefault();
		}
		return runnersReader.getRunnersInfo();
	}

	/**
	 * This method starts all the available racer threads.
	 */
	public static void startRace() {
		for (int i = 0; i < runnersList.size(); i++) {
			MarathonRunner threadRunnerObj = runnersList.get(i);
			Thread t1 = new Thread(threadRunnerObj);
			t1.start();
		}
	}

	/**
	 * This method return the race status.
	 * 
	 * @return -Boolean
	 */
	public static synchronized boolean isRaceComplete() {
		return isFinished;
	}

	/**
	 * This method declares the thread that calls it to be the winner and
	 * interrupts the other thread to concede the race.
	 * 
	 * @param winnerName
	 *            - Type String
	 */
	public static synchronized void finished(String winnerName) {

		if (!isFinished) {
			System.out.println("\n" + winnerName + ": " + "I finished!\n");
			System.out.println("The race is over! The " + winnerName + " is the winner\n");
			for (int i = 0; i < runnersList.size(); i++) {
				runnersList.get(i).setStop(true);
				if (!winnerName.equals(runnersList.get(i).getRacerName())) {
					System.out.println(runnersList.get(i).getRacerName() + ": You beat me fare and square!\n");
				}
			}
			isFinished = true;
		}

	}

	/**
	 * This method keeps the application running upon any key pressed
	 */
	public static void continueApplication() {
		System.out.print("press enter key to continue..");
		if ((sc.nextLine() != null)) {
			continueApp = true;
			isFinished = false;
			runnersList = null;
		}
	}
}

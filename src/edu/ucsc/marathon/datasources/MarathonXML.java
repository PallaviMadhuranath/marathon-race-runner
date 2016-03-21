package edu.ucsc.marathon.datasources;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import edu.ucsc.marathon.MarathonRunner;
import edu.ucsc.marathon.validator.Validator;

/**
 * This class implements an interface RunnersReader. It defines the abstract
 * method of the interface. It reads all the data from a xml file into an
 * arrayList. This arrayList contains the details of all the runners, such as
 * name, speed and rest value.
 * 
 * @author pallavidas
 *
 */
public class MarathonXML implements RunnersReader {
	private ArrayList<MarathonRunner> runnersList;
	private String fileName;

	/**
	 * Constructor of class MarathonXML.
	 * 
	 * @param fileName
	 *            - get fileName from user.
	 */
	public MarathonXML(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * This is a get method that returns an arrayList of runners information,
	 * after reading from a XML file.
	 */
	public ArrayList<MarathonRunner> getRunnersInfo() {

		runnersList = new ArrayList<>();

		if (!Validator.validateFileName(fileName)) {
			// Invalid file return no runners
			return runnersList;
		}

		String racerName = " ";
		int speed = 0, restValue = 0;
		MarathonRunner runnerObj = null;
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		try {
			Path runnersPath = Paths.get(fileName);
			FileReader fileReader = new FileReader(runnersPath.toFile());
			XMLStreamReader reader = inputFactory.createXMLStreamReader(fileReader);
			while (reader.hasNext()) {
				int eventType = reader.getEventType();
				switch (eventType) {
				case XMLStreamConstants.START_ELEMENT:
					String elementName = reader.getLocalName();
					if (elementName.equals("Runner")) {
						racerName = reader.getAttributeValue(0);

					}
					if (elementName.equals("RunnersMoveIncrement")) {
						String speedString = reader.getElementText();
						speed = Integer.parseInt(speedString);

					}
					if (elementName.equals("RestPercentage")) {
						String restValueString = reader.getElementText();
						restValue = Integer.parseInt(restValueString);
					}

					break;

				case XMLStreamConstants.END_ELEMENT:
					elementName = reader.getLocalName();
					if (elementName.equals("Runner")) {
						runnerObj = new MarathonRunner(racerName, speed, restValue);
						runnersList.add(runnerObj);
					}
				default:
					break;
				}
				reader.next();
			}

			reader.close();

		} catch (FileNotFoundException | XMLStreamException e) {
			System.out.println("Not a valid XML file!");
		}
		return runnersList;
	}
}

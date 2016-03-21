package edu.ucsc.marathon.datasources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.ucsc.marathon.MarathonRunner;

/**
 * This class implements an interface RunnersReader. It defines the abstract
 * method of the interface. It reads all the data from a databse into an
 * arrayList. This arrayList contains the details of all the runners, such as
 * name, speed and rest value.
 * 
 * @author pallavidas
 *
 */
public class MarathonDB implements RunnersReader {
	/**
	 * This method establishes connection with the derby database.
	 * 
	 * @return - Object of type Connection
	 */
	private Connection connect() {
		Connection connection = null;
		try {
			String dbDirectory = "Resources";
			System.setProperty("derby.system.home", dbDirectory);

			String dbUrl = "jdbc:derby:MarathonDB";

			String username = "";
			String password = "";
			connection = DriverManager.getConnection(dbUrl, username, password);
			return connection;
		} catch (Exception e) {
			System.err.println("Error loading database driver: " + e);
		}
		return connection;
	}

	/**
	 * This is a get method that returns an arrayList of runners information,
	 * after reading from a database.
	 */
	public ArrayList<MarathonRunner> getRunnersInfo() {
		try {
			Connection connection = connect();
			ArrayList<MarathonRunner> runnersList = new ArrayList<MarathonRunner>();
			String query = "SELECT RunnersName,RestValue,Speed " + "FROM RunnersInfo";

			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String RunnersName = rs.getString("RunnersName");
				int RestValue = rs.getInt("RestValue");
				int Speed = rs.getInt("Speed");

				MarathonRunner runnerObj = new MarathonRunner(RunnersName, Speed, RestValue);
				runnersList.add(runnerObj);
			}
			rs.close();
			ps.close();
			connection.close();
			return runnersList;
		} catch (SQLException sqle) {
			return null;
		}
	}
}

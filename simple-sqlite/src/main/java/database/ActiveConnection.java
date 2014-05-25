/**
 * ActiveConnection.java
 * Our ActiveConnection Object which stores the database
 * accessed and the open connection.
 * 
 * @author Henrik Jürges <juerges.henrik@gmail.com>
 * @version 
 * @date 25.05.2014
 * 
 */
package database;

import java.sql.Connection;

public class ActiveConnection {

	/** The database. */
	private String database = null;
	
	/** The connection. */
	private Connection connection = null;
	
	/**
	 * Instantiates a new active connection.
	 *
	 * @param database the database
	 * @param connection the connection
	 */
	public ActiveConnection(String database, Connection connection) {
		this.setConnection(connection);
		this.setDatabase(database);
	}

	/**
	 * Gets the database.
	 *
	 * @return the database
	 */
	public String getDatabase() {
		return database;
	}

	/**
	 * Sets the database.
	 *
	 * @param database the new database
	 */
	public void setDatabase(String database) {
		this.database = database;
	}

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 */
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}

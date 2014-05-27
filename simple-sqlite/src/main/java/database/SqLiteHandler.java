/**
 * SqLiteHandler.java
 * This is our SQlite database handler.
 * It manages activeConnections, opens and 
 * closes them for you. It's a singleton class. 
 * You can get the instance with: 
 * <code>private {@link SqLiteHandler} handler = SqLiteHandler.getInstance()</code>
 * 
 * @author Henrik Jürges <juerges.henrik@gmail.com>
 * @version 
 * @date 25.05.2014
 * 
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

public class SqLiteHandler {

	/** The instance. */
	private static volatile SqLiteHandler instance = null;
	
	/** The Constant log. */
	private static final Logger log = Logger.getLogger(SqLiteHandler.class);

	/** The active connections. 
	 * The concurrent hash map is used because junit requires this 
	 * for it's multithreaded testing enviroment.*/
	private static volatile ConcurrentHashMap<String,Connection> activeConnections = null;
	
	/** The query time out. */
	private static volatile int queryTimeOut = 30;
			
	/**
	 * Instantiates a new sqlite handler.
	 *
	 * @throws ClassNotFoundException sqllite driver not found
	 */
	private SqLiteHandler() throws ClassNotFoundException {
		// load JDBC Driver
		try {
			Class.forName("org.sqlite.JDBC");
			
			// initCapacity 16 - threshold before growing 0.9, current shards (threads) 1
			activeConnections = new ConcurrentHashMap<String, Connection>(16, 0.9f, 1);
		} catch (ClassNotFoundException e) {
			log.error("JDBC driver not found");
			throw new ClassNotFoundException("Could not find JDBC driver. " + e.getMessage());
		}
	}

	/**
	 * Gets the single instance of SqLiteHandler.
	 *
	 * @return single instance of SqLiteHandler
	 * @throws ClassNotFoundException sqlite driver not found
	 */
	public static SqLiteHandler getInstance() throws ClassNotFoundException {
		if (instance == null) {
			synchronized (SqLiteHandler.class) {
				instance = new SqLiteHandler();
			}
		}
		return instance;
	}

	/**
	 * Connect to database file.
	 * You only have to specify the part after <code>"jdbc:sqlite:"</code>
	 * See {@link DriverManager} for JDBC Driver for more information.
	 *
	 * @param database the database file path
	 * @return true, if successful
	 */
	public boolean connectToDatabaseFile(String database) {
		boolean connectionEstablished = false;
		Connection connection = null;
		if (activeConnections.containsKey(database)) {
			return false;
		}
		
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:" + database);
			connectionEstablished = true;
		
			activeConnections.putIfAbsent(database, connection);
		} catch (SQLException e) {
			log.error("Could not open connection to: " + database + "\n" + e.getMessage());
		}

		return connectionEstablished;
	}
	
	/**
	 * Gets the active connections.
	 *
	 * @return the active connections
	 */
	public Set<String> getActiveConnections() {
		return activeConnections.keySet();
	}

	/**
	 * Gets the query time out.
	 *
	 * @return the query time out
	 */
	public static int getQueryTimeOut() {
		return queryTimeOut;
	}

	/**
	 * Sets the query time out.
	 *
	 * @param queryTimeOut the new query time out
	 */
	public static void setQueryTimeOut(int queryTimeOut) {
		SqLiteHandler.queryTimeOut = queryTimeOut;
	}
	
	/**
	 * Close a single connection.
	 * We check if the connection exists or not, in both cases true is returned. 
	 *
	 * @param database the database
	 * @return true if successful. Even if closing fails it is still removed
	 * from the active connection hash map.
	 */
	public synchronized boolean closeConnection(String database) {
		boolean success = false;
		Connection connection = activeConnections.get(database);
		
		try {
			// check if another thread already removed the connection
			if (connection != null) {
				connection.close();
			}
			success = true;
			
		} catch (SQLException e) {
			log.error("Can not close Connection, try null..." + e.getMessage());
			connection = null;
		} finally {
			activeConnections.remove(database);
		}
		
		return success;
	}
	
	/**
	 * Destroys all active Connections. 
	 * This implementation uses the closeConnection(String database) method
	 * so it is race condition free.
	 *
	 * @return true if each connection is successfully removed.
	 */
	public boolean destroy() {
		Iterator<String> iter = activeConnections.keySet().iterator();	
		boolean success = false;
		
		while (iter.hasNext()) {
			String key = iter.next();
			iter.remove();
			success = closeConnection(key);
		}
		return success;
	}
	
	/**
	 * Execute query statement, but only if the connection exits.
	 *
	 * @param database the database on which the query runs.
	 * @param sqlStatement the query statement we execute on the database.
	 * @return the result set for the statement or null if the connection
	 * doesn't exists or an exception occurs.
	 */
	public ResultSet executeQueryStatement(String database, String sqlStatement) {
		Connection connection = activeConnections.get(database);
		ResultSet results = null;
		
		if (connection != null) {
			try {
				Statement stmt = connection.createStatement();
				stmt.setQueryTimeout(queryTimeOut);
				results = stmt.executeQuery(sqlStatement);
				
			} catch (SQLException e) {
				log.error("Can not execute SQL Statement. " + e.getMessage());
			}
		}
		return results;
	}
	
	/**
	 * Execute update statements for every entry in the
	 * sqlStatements array.
	 *
	 * @param database the database on which the update runs.
	 * @param sqlStatements the update statement we execute on the database.
	 * @return an array of integers for each update statement.
	 */
	public int[] executeUpdateStatement(String database, String[] sqlStatements) {
		Connection connection = activeConnections.get(database);
		int[] results = new int[sqlStatements.length];
		
		if (connection != null) {
			try {
				Statement stmt = connection.createStatement();
				stmt.setQueryTimeout(queryTimeOut);
				
				int count = 0;
				for (String update : sqlStatements) {
					results[count] = stmt.executeUpdate(update);
					count++;
				}
				
			} catch (SQLException e) {
				log.error("Can not execute SQL Statements." + e.getMessage());
			}
		}
		return results;
	}
}

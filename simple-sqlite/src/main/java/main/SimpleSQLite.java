/**
 * SimpleSQLite.java
 * 
 * @author Henrik Jürges <juerges.henrik@gmail.com>
 * @version 
 * @date 27.05.2014
 * 
 */
package main;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import database.SqLiteHandler;

public class SimpleSQLite {

	/** The handler. */
	private SqLiteHandler handler = null;
	
	/** The Constant log. */
	private static final Logger log = Logger.getLogger(SimpleSQLite.class);
	
	/**
	 * Instantiates a new simple sqlite.
	 *
	 * @param handler the handler
	 */
	public SimpleSQLite(SqLiteHandler handler) {
		this.handler = handler;
	}
	
	/**
	 * Execute some examples.
	 */
	public void executeSomeExamples() {
		String[] stmt = {"create table person(id integer, name string)", 
				"insert into person values (1, 'lea')", 
				"insert into person values (2, 'yui')"};
		
		handler.connectToDatabaseFile("/tmp/data.db");
		handler.executeUpdateStatement("/tmp/data.db", stmt);
		
		ResultSet results = handler.executeQueryStatement("/tmp/data.db", "select * from person");
		
			try {
				while (results.next()) {
					System.out.println("id: " + results.getString("id"));
					System.out.println("name: " + results.getString("name"));
				}
			} catch (SQLException e) {
				log.error("Something went wrong. " + e.getMessage());
			}
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// the traditional way
		try {
			SimpleSQLite lite = new SimpleSQLite(SqLiteHandler.getInstance());
			lite.executeSomeExamples();
		} catch (ClassNotFoundException e) {
			log.error("Uii... SQLite Driver not found." + e.getMessage());
		}
	}
}

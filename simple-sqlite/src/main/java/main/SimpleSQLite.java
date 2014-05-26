package main;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import database.SqLiteHandler;

public class SimpleSQLite {

	private SqLiteHandler handler = null;
	
	private static final Logger log = Logger.getLogger(SimpleSQLite.class);
	
	public SimpleSQLite(SqLiteHandler handler) {
		this.handler = handler;
	}
	
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

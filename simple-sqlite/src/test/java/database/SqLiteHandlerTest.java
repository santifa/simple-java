package database;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import database.SqLiteHandler;

public class SqLiteHandlerTest {

	private SqLiteHandler handler = null;
	
	@Before
	public void setUp() throws Exception {
		handler = SqLiteHandler.getInstance();
	}
	
	@After
	public void tearDown() {
		handler.destroy();
	}
	
	@Test
	public void testOpenConnection() {
		assertTrue(handler.connectToDatabaseFile("/tmp/data1.db"));
		assertEquals(1, handler.getActiveConnections().size());
	}
	
	@Test
	public void testOpenSameConnectionTwice() {
		assertTrue(handler.connectToDatabaseFile("/tmp/data1.db"));
		assertFalse(handler.connectToDatabaseFile("/tmp/data1.db"));
		assertEquals(1, handler.getActiveConnections().size());
	}
	
	@Test 
	public void testOpenMultipleConnections() {
		assertTrue(handler.connectToDatabaseFile("/tmp/data1.db"));
		assertTrue(handler.connectToDatabaseFile("/tmp/data2.db"));
		assertTrue(handler.connectToDatabaseFile("/tmp/data3.db"));
		assertEquals(3, handler.getActiveConnections().size());
	}

	@Test
	public void testDestroyConnection() {
		assertTrue(handler.connectToDatabaseFile("/tmp/data1.db"));
		assertTrue(handler.destroyConnection("/tmp/data1.db"));
		assertEquals(0, handler.getActiveConnections().size());
	}
	
	@Test
	public void testDestroySameConnectionTwice() {
		assertTrue(handler.connectToDatabaseFile("/tmp/data1.db"));
		assertTrue(handler.destroyConnection("/tmp/data1.db"));
		assertTrue(handler.destroyConnection("/tmp/data1.db"));
		assertEquals(0, handler.getActiveConnections().size());
	}
	
	@Test
	public void testDestroyMultpleConnections() {
		assertTrue(handler.connectToDatabaseFile("/tmp/data1.db"));
		assertTrue(handler.connectToDatabaseFile("/tmp/data2.db"));
		assertTrue(handler.connectToDatabaseFile("/tmp/data3.db"));
		assertEquals(3, handler.getActiveConnections().size());
		
		// reset singleton
		assertTrue(handler.destroy());
		assertEquals(0, handler.getActiveConnections().size());
	}
	
	@Test
	public void testUpdateStatement() {
		String[] stmt = {"create table person(id integer, name string)", 
				"insert into person values (1, 'lea')", 
				"insert into person values (2, 'yui')"};
		int[] expected = {0, 0, 0};
		assertTrue(handler.connectToDatabaseFile("/tmp/data.db"));
		assertArrayEquals(expected, handler.executeUpdateStatement("/tmp/data.db", stmt));
	}
	
	@Test
	public void testQueryStatement() {
		String[] stmt = {"create table person(id integer, name string)", 
				"insert into person values (1, 'lea')", 
				"insert into person values (2, 'yui')"};
		
		assertTrue(handler.connectToDatabaseFile("/tmp/data.db"));
		handler.executeUpdateStatement("/tmp/data.db", stmt);
		ResultSet results = handler.executeQueryStatement("/tmp/data.db", "select * from person");
		
		try {
			results.next();
			assertEquals("1", results.getString("id"));
			assertEquals("lea", results.getString("name"));
			results.next();
			assertEquals("2", results.getString("id"));
			assertEquals("yui", results.getString("name"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

/**
 * SimpleMoverTest.java
 * 
 * @author Henrik Jürges <juerges.henrik@gmail.com>
 * @version 
 * @date 24.05.2014
 * 
 */
package main;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import dependencies.JavaFileMover;
import dependencies.LinuxFileMover;
import dependencies.SimpleFileMover;

public class SimpleMoverTest {

	private File src = null;
	private File dest = null;
	
	@Before
	public void setUp() throws IOException {
		src = File.createTempFile("tmp", "file", new File("/tmp"));
		dest = new File("/tmp/newtmpfile");
	}
	
	/**
	 * Test linux file mover.
	 */
	@Test
	public void testLinuxFileMover() {
		final SimpleFileMover fileMover = new LinuxFileMover();
		SimpleMover mover = new SimpleMover(fileMover);
		
		assertFalse(mover.move(src, dest));
	}
	
	/**
	 * Test java file mover.
	 */
	@Test
	public void testJavaFileMover() {
		final SimpleFileMover fileMover = new JavaFileMover();
		SimpleMover mover = new SimpleMover(fileMover);
		
		assertTrue(mover.move(src, dest));
	}

}

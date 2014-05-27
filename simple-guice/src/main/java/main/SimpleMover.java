/**
 * SimpleMover.java
 * This is the main entry point for our guice example.
 * We "inject" the correct dependency according to which file mover was chosen by
 * constructing the class. In the main method we create the injector.
 * 
 * @author Henrik Jürges <juerges.henrik@gmail.com>
 * @version 0.1
 * @date 22.05.2014
 * 
 */
package main;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import dependencies.SimpleFileMover;

public class SimpleMover {

	private final SimpleFileMover fileMover;
	
	private final static Logger log = Logger.getLogger(SimpleMover.class);
	
	/**
	 * Instantiates a new simple mover.
	 *
	 * @param fileMover the file mover
	 */
	@Inject
	public SimpleMover(SimpleFileMover fileMover) {
		this.fileMover = fileMover;
	}
	
	/**
	 * Moves a file.
	 *
	 * @param src the src
	 * @param dest the dest
	 */
	public boolean move(File src, File dest) {
		boolean result = fileMover.move(src, dest);
		log.info("The file is moved? " + result);	
		return result;
	}

	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		// log4j initialization
		BasicConfigurator.configure();
		
		// Let's create our Injector
		Injector injector = Guice.createInjector(new SimpleMoverModule());
		
		// build the dependencies and create our object
		SimpleMover mover = injector.getInstance(SimpleMover.class);
		
		
		// some tmp files
		File src = File.createTempFile("tmp", "file", new File("/tmp"));
		File dest = new File("/tmp/newtmpfile");
		
		log.info("src is " + src.getPath());
		log.info("dest is" + dest.getPath());
		
		mover.move(src, dest);
	}
}

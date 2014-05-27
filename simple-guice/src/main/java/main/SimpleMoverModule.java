/**
 * SimpleMoverModule.java
 * Which implementation we want to use for our
 * parameters is handed over to guice using the
 * configure() method. Guice then binds the chosen
 * implementation to the contructor of the annotated class.
 * 
 *  
 * @author Henrik Jürges <juerges.henrik@gmail.com>
 * @version 
 * @date 22.05.2014
 * 
 */
package main;

import com.google.inject.AbstractModule;

import dependencies.LinuxFileMover;
import dependencies.SimpleFileMover;

public class SimpleMoverModule extends AbstractModule {

	/* (non-Javadoc)
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		// binds our linux file mover as default
		bind(SimpleFileMover.class).to(LinuxFileMover.class);
	}
	
}

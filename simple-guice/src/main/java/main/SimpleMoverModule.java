/**
 * SimpleMoverModule.java
 * Here we tell Guice about our dependencies.
 * Which implementation we want to use for our
 * parameters.
 * 
 * 
 * @author Henrik Jürges <juerges.henrik@gmail.com>
 * @version 
 * @date 22.05.2014
 * 
 */
package main;

import com.google.inject.AbstractModule;

import dependencies.JavaFileMover;
import dependencies.SimpleFileMover;

public class SimpleMoverModule extends AbstractModule {

	/* (non-Javadoc)
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		// binds our linux file mover per default
		bind(SimpleFileMover.class).to(JavaFileMover.class);
	}
	
	
}

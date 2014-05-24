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
import com.google.inject.name.Names;

import dependencies.JavaFileMover;
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
		// our fallback solution with a named annotation
		bind(SimpleFileMover.class).annotatedWith(Names.named("Fallback")).to(JavaFileMover.class);
	}
	
}

/**
 * LinuxFileMover.java
 * A simple file mover doing nothing. ;-)
 * 
 * @author Henrik Jürges <juerges.henrik@gmail.com>
 * @version 
 * @date 22.05.2014
 * 
 */
package dependencies;

import java.io.File;

import org.apache.log4j.Logger;

public class LinuxFileMover implements SimpleFileMover {

	public final static Logger log = Logger.getLogger(LinuxFileMover.class);
	
	/* (non-Javadoc)
	 * @see dependencies.SimpleFileMover#move(java.io.File, java.io.File)
	 */
	public boolean move(File src, File dest) {
		log.info("We're using the os method. Always returns false,"
				+ "because nothing is implemented. ;-)");
		return false;
	}

}

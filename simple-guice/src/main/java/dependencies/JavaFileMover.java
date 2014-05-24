/**
 * JavaFileMover.java
 * Moves a file to another destination using java built-in
 * renameTo(). This can break if the destination is on another hdd.
 * 
 * @author Henrik Jürges <juerges.henrik@gmail.com>
 * @version 
 * @date 22.05.2014
 * 
 */
package dependencies;

import java.io.File;

import org.apache.log4j.Logger;


public class JavaFileMover implements SimpleFileMover {

	protected final static Logger log = Logger.getLogger(JavaFileMover.class);
			
	/* (non-Javadoc)
	 * @see dependencies.SimpleFileMover#move(java.io.File, java.io.File)
	 */
	public boolean move(File src, File dest) {
		log.info("We're using the java method.");
		return src.renameTo(dest);
	}
}

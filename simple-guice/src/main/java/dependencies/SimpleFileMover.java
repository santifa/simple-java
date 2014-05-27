/**
 * SimpleFileMover.java
 * A simple interface to demonstrate google guice 
 * dependency injection. In order to show said injection,
 * we implement two file mover classes. One for linux and
 * one fallback solution using native java.
 * 
 * 
 * @author Henrik Jürges <juerges.henrik@gmail.com>
 * @version 0.1
 * @date 22.05.2014
 * 
 */
package dependencies;

import java.io.File;

public interface SimpleFileMover {

	/**
	 * Moves a file.
	 * 
	 * @param src
	 *            the src
	 * @param dest
	 *            the dest
	 * @return true, if successfully moved
	 */
	public boolean move(File src, File dest);
}

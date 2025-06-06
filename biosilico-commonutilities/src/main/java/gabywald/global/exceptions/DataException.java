package gabywald.global.exceptions;

import gabywald.global.data.Directory;
import gabywald.global.data.File;

/**
 * Aim of this kind of exception is to signal a I/O problem (creation of directory(ies)...). 
 * @author Gabriel Chandesris (2010, 2020)
 * @see Directory#createDir()
 * @see Directory#createDirs() 
 * @see File#printFile()
 */
@SuppressWarnings("serial")
public class DataException extends GenericException {
	/** To avoid Warning. */
	// private static final long serialVersionUID = 401L;
	/**
	 * Default Constructor with message
	 * @param request (String)
	 */
	public DataException(String request) { super(request); }
}

package gabywald.global.data;

import gabywald.global.exceptions.DataException;

import java.io.File;

/**
 * This class to gather information about a directory. 
 * http://www.exampledepot.com/egs/java.io/CreateDir.html?l=rel
 * @author Gabriel Chandesris (2009)
 * @see <a href="http://www.exampledepot.com/egs/java.io/CreateDir.html?l=rel">http://www.exampledepot.com/egs/java.io/CreateDir.html?l=rel</a>
 */
@SuppressWarnings("serial")
public class Directory extends java.io.File { 
	/** Name / path to the directory. */
	private String pathName;
	
	/**
	 * Constructor (no checking about name). 
	 * @param pathName (String)
	 */
	public Directory(String pathName) { 
		super((pathName != null) ? pathName : "." );
		this.pathName = pathName; 
	}
	
	/**
	 * Check if the directory exists.
	 * @return (boolean)
	 * @see java.io.File#exists()
	 */
	public boolean dirExists() { 
		if (this.pathName == null) { return false; }
		return (new File(this.pathName)).exists(); 
	}
	
	/**
	 * Overload.
	 * @see Directory#dirExists()
	 * @deprecated
	 */
	public boolean exists() { return this.dirExists(); }
	
	/** 
	 * Create the dir : the ancestors dirs must exist. 
	 * @throws DataException If cannot create dir.
	 */
	public void createDir() throws DataException {
		if (!this.dirExists()) {
			boolean success = (new File(this.pathName)).mkdir();
		    if (!success) { throw new DataException("Directory '"+this.pathName+"' cannot create dir !!"); }
		}
	}
	
	/** 
	 * Create the dir : the ancestors dirs are created. 
	 * @throws DataException If cannot create all dirs. 
	 */
	public void createDirs() throws DataException {
		if ( (!this.dirExists()) && (this.pathName != null) ) {
			boolean success = (new File(this.pathName)).mkdirs();
	    	if (!success) { throw new DataException("Directory '"+this.pathName+"' cannot create dirS"); }
		}
	}
	
	public void deleteDir() throws DataException {
		if (this.dirExists()) { 
			if (!this.delete()) 
				{ throw new DataException("Directory '"+this.pathName+"' cannot be deleted !!"); } 
		} else { throw new DataException("Directory '"+this.pathName+"' does not exist !!"); }
	}
	
	public static boolean deleteDirComplete(java.io.File path) { 
		boolean resultat = true; 
		// System.out.println(path.getName());
		if (path.exists()) { 
			File[] files = path.listFiles(); 
			for (int i = 0 ; i < files.length ; i++) { 
				if (files[i].isDirectory()) 
					{ resultat &= Directory.deleteDirComplete(files[i]); } 
				else { resultat &= files[i].delete(); } 
			} 
		} 
		resultat &= path.delete(); 
		return resultat; 
	}
	
	/**
	 * To get the name of directory
	 * @return (String)
	 */
	public String getDirName()	{ return this.pathName; } 
	
	public String toString()	{ return this.getDirName(); }

	public File[] getFiles()	{ return this.listFiles(); }
}

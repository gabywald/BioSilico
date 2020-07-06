package gabywald.global.data.samples;

import gabywald.global.data.Fichier;

/**
 * This class defines a generic access to data (and configuration) files. 
 * @author Gabriel Chandesris (2012)
 */
public abstract class DataFile extends Fichier {
	/** Base dir where are sub-directories and files ("resources/conf/"). */
	private static final String BASE_DATACONF_DIRECTORY	= "conf/";
	
	/**
	 * Default constructor which append the name after the DataFile's directory.  
	 * @param name (String)
	 * @see DataFile#BASE_DATACONF_DIRECTORY
	 */
	public DataFile(String name) 
		{ super(DataFile.BASE_DATACONF_DIRECTORY + name); }
	
	/**
	 * To get the (static) sub-directory of files. 
	 * @return (String)
	 */
	public abstract String getDefaultDirectory();
	
	/**
	 * To get the (static) names of files. 
	 * @return (String[])
	 */
	public abstract String[] getFileNamesSet();
}

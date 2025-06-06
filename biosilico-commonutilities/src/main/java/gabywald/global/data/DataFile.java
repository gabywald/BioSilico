package gabywald.global.data;

/**
 * This class defines a generic access to data (and configuration) files. 
 * @author Gabriel Chandesris (2012, 2022)
 */
@SuppressWarnings("serial")
public abstract class DataFile extends File {
	/** Base dir where are sub-directories and files ("resources/"). */
	protected static final String BASE_DIRECTORY_ALT			= "resources/";
	/** Base dir where are sub-directories and files ("src/main/resources/"). */
	protected static final String BASE_DIRECTORY				= "src/main/resources/";
	/** Base dir where are sub-directories and files ("conf/"). */
	protected static final String DATACONF						= "conf/";
	/** Base dir where are sub-directories and files ("resources/conf/"). */
	protected static final String BASE_DATACONF_DIRECTORY_ALT	= DataFile.BASE_DIRECTORY_ALT + DataFile.DATACONF;
	/** Base dir where are sub-directories and files ("src/main/resources/conf/"). */
	protected static final String BASE_DATACONF_DIRECTORY		= DataFile.BASE_DIRECTORY + DataFile.DATACONF;
	
	/**
	 * Default constructor which append the name after the DataFile's directory.  
	 * @param name (String)
	 * @see DataFile#BASE_DATACONF_DIRECTORY
	 */
	public DataFile(String name) 
		{ super(DataFile.BASE_DATACONF_DIRECTORY + name); }
	
	/**
	 * Default constructor which append the name after the DataFile's directory.  
	 * @param name (String)
	 * @see DataFile#BASE_DATACONF_DIRECTORY
	 */
	public DataFile(String name, boolean oldrep) 
		{ super(((oldrep)?DataFile.BASE_DATACONF_DIRECTORY_ALT:DataFile.BASE_DATACONF_DIRECTORY) + name); }
	
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

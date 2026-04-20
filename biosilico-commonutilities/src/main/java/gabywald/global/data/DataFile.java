package gabywald.global.data;

/**
 * This class defines a generic access to data (and configuration) files. 
 * @author Gabriel Chandesris (2012, 2022, 2026)
 */
@SuppressWarnings("serial")
public abstract class DataFile extends File {
	
	/** Base dir where are sub-directories and files ("src/main/resources/"). */
	private static final String BASE_DIRECTORY					= "src/main/resources/";
	/** Base dir where are sub-directories and files ("resources/"). */
	private static final String BASE_DIRECTORY_ALT				= "resources/";
	/** Base dir where are sub-directories and files ("conf/"). */
	private static final String DATACONF						= "conf/";
	/** Base dir where are sub-directories and files ("src/main/resources/conf/"). */
	protected static final String BASE_DATACONF_DIRECTORY		= DataFile.BASE_DIRECTORY + DataFile.DATACONF;
	/** Base dir where are sub-directories and files ("resources/conf/"). */
	protected static final String BASE_DATACONF_DIRECTORY_ALT	= DataFile.BASE_DIRECTORY_ALT + DataFile.DATACONF;
	
	
	// TODO change this in combination of oldrep / PREFIX (olrep is NOT maven (ex: ant)
	// public static String PREFIX = DataFileHelper.isInIDEorNot() ? ("" + DataFile.BASE_DIRECTORY + DataFile.DATACONF) : ("/" + DataFile.DATACONF);
	// private boolean isOldRep = false;
	
	public static String prefix(boolean isNOTmaven) {
		return DataFileHelper.isInIDEorNot()
				? (""  + ((isNOTmaven) ? DataFile.BASE_DATACONF_DIRECTORY_ALT : DataFile.BASE_DIRECTORY + DataFile.DATACONF) )
				: ("/" + ((isNOTmaven) ? DataFile.BASE_DATACONF_DIRECTORY_ALT : DataFile.DATACONF) );
	}
	
	/**
	 * Default constructor which append the name after the DataFile's directory. 
	 * @param type (String)
	 * @param name (String)
	 * @param isNOTmaven (String)
	 * @see DataFile#BASE_DATACONF_DIRECTORY
	 */
	protected DataFile(String type, String name, boolean isNOTmaven) 
		{ super(type, DataFile.prefix(isNOTmaven) + name); }
	
	/**
	 * Default constructor which append the name after the DataFile's directory. 
	 * <br/>Considered as is Maven for resources. 
	 * @param type (String)
	 * @param name (String)
	 * @see DataFile#BASE_DATACONF_DIRECTORY
	 */
	protected DataFile(String type, String name) 
		{ this(type, name, false); }
	
	/**
	 * Default constructor which append the name after the DataFile's directory. 
	 * <br/>Considered as is Maven for resources. 
	 * @param type (String)
	 * @param name (String)
	 * @see DataFile#BASE_DATACONF_DIRECTORY
	 */
	public DataFile(String name) 
		{ this("DATA_TYPE", name, false); }
	
	/**
	 * Default constructor which append the name after the DataFile's directory.  
	 * @param name (String)
	 * @param oldrepISnotMaven (String)
	 * @see DataFile#BASE_DATACONF_DIRECTORY
	 * @deprecated maven / ant / IDE and other detections !
	 */
	@SuppressWarnings("unused")
	private DataFile(String name, boolean oldrepISnotMaven) 
		{ this("DATA_TYPE", name, oldrepISnotMaven); }
	
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

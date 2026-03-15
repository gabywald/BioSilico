package gabywald.global.data;

import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * To detect some contexts for IDE / TESTS / JAR contexts. 
 * <br/> Useful for IDE + Maven / Ant compilations and JAR / IDE use !
 * @author Gabriel Chandesris (2026)
 */
public class DataFileHelper {
	
	// public static boolean IS_IN_IDE_OR_NOT = 
			// (DataFileHelper.getInstance().getClass().getResource("").toString().startsWith("file:") ); 
	
	public static boolean isInIDEorNot() {
		Logger.printlnLog(LoggerLevel.LL_NONE, DataFileHelper.getInstance() + "");
		Logger.printlnLog(LoggerLevel.LL_NONE, DataFileHelper.getInstance().getClass() + "");
		Logger.printlnLog(LoggerLevel.LL_NONE, DataFileHelper.getInstance().getClass().getResource("") + "");
		Logger.printlnLog(LoggerLevel.LL_NONE, DataFileHelper.getInstance().getClass().getResource("").toString());
		Logger.printlnLog(LoggerLevel.LL_NONE, "IS_IN_IDE_OR_NOT?" + DataFileHelper.getInstance().getClass().getResource("").toString().startsWith("file:"));
		// Logger.printlnLog(LoggerLevel.LL_NONE, "PATH: " + DataFileHelper.getInstance().getClass().getClassLoader().getResource(".").getPath() );
		// Logger.printlnLog(LoggerLevel.LL_NONE, "RSCS: " + DataFileHelper.getInstance().getClass().getClassLoader().getResource(".").toString() );
		return DataFileHelper.getInstance().getClass().getResource("").toString().startsWith("file:");
	}
	
	private static DataFileHelper instance = null;
	
	private DataFileHelper() { ; }
	
	public static DataFileHelper getInstance() {
		if (DataFileHelper.instance == null) 
			{ DataFileHelper.instance = new DataFileHelper(); }
		return  DataFileHelper.instance;
	}
	
}

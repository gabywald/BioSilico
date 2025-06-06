package gabywald.utilities.logger;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Logger Class (Singleton and usable as static way). 
 * <br/><i>Design-Pattern Singleton</i>
 * @author Gabriel Chandesris (2013, 2022)
 */
public class Logger {
	
	/** Format of date for GUI view. */
	public static SimpleDateFormat outputFormat = 
		new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	public enum LoggerLevel {
		LL_FORUSER		("FORUSER"), 
		LL_ERROR		("ERROR"), 
		LL_WARNING		("WARNING"), 
		LL_DEBUG		("DEBUG"), 
		LL_INFO			("INFO"), 
		LL_VERBOSE		("VERBOSE"), 
		LL_VERBOSER		("VERBOSER"), 
		LL_VERBOSEST	("VERBOSEST"),
		LL_NONE			("NONE");
		
		private String internalName;
		LoggerLevel(String name)	{ this.internalName = name; }
		public String toString()	{ return this.internalName; }
	}
	
	/** Unique instance. */
	private static Logger instance;
	/** Level of Logger. */
	private LoggerLevel level;
	
	/** Default Constructor. 
	 * Logger level is set to LL_INFO. */
	private Logger() { this.level = LoggerLevel.LL_INFO; }
	
	/**
	 * To get instance.  
	 * @return (Logger)
	 */
	public static Logger getInstance() { 
		if (Logger.instance == null) 
			{ Logger.instance = new Logger(); }
		return Logger.instance;
	}
	
	public void setLevel(LoggerLevel lvl) 
		{ this.level = lvl; }
	
	public static void setLogLevel(LoggerLevel lvl) 
		{ Logger.getInstance().level = lvl; }
	
	public void println(LoggerLevel lvl, String content) 
		{ this.print(lvl, content + "\n"); }
	
	public static boolean isLogLevelAccurate(LoggerLevel lvl) {
		if (lvl.equals(LoggerLevel.LL_NONE))			{ return false; }
		else if (lvl.equals(LoggerLevel.LL_FORUSER))	{ return true; }
		else if (lvl.ordinal() <= Logger.getInstance().level.ordinal())	
			{ return true; }
		return false;
	}
	
	public static void printlnLog(LoggerLevel lvl, String content) 
		{ Logger.getInstance().print(lvl, content + "\n"); }

	public static void printLog(LoggerLevel lvl, String content) 
		{ Logger.getInstance().print(lvl, content); }
	
	public void print(LoggerLevel lvl, String content) {
		if (lvl.equals(LoggerLevel.LL_NONE))			{ ; }
		else if (lvl.equals(LoggerLevel.LL_FORUSER))	{ System.out.print(content); }
		else if (lvl.ordinal() <= this.level.ordinal())	{ 
			GregorianCalendar currentTime	= new GregorianCalendar();
			String timeToPrint	= SomeUtilities.toString(currentTime, Logger.outputFormat);
			String lvlType		= new String(lvl.toString());
			System.out.print( timeToPrint + " " + lvlType + " " + content); 
		} // END "if (lvl.ordinal() >= this.level.ordinal())"
	}
	
}

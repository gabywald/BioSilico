package gabywald.utilities.logger;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Logger Class (Singleton and usable as static way). 
 * @author Gabriel Chandesris (2013)
 */
public class Logger {

	public enum LoggerLevel {
		LL_NONE		("NONE"), 
		LL_VERBOSE	("VERBOSE"), 
		LL_DEBUG	("DEBUG"), 
		LL_INFO		("INFO"), 
		LL_WARNING	("WARNING"), 
		LL_ERROR	("ERROR"), 
		LL_FORUSER	("FORUSER");
		
		private String internalName;
		LoggerLevel(String name)	{ this.internalName = name; }
		public String toString()	{ return this.internalName; }
	}
	
	/** Unique instance. */
	private static Logger instance;
	/** Level of Loggger. */
	private LoggerLevel level;
	
	/** Default Constructor. 
	 * Logger level is set to LL_INFO. */
	private Logger() { this.level = LoggerLevel.LL_INFO; }
	
	/** To get instance. */
	public static Logger getInstance() { 
		if (Logger.instance == null) 
			{ Logger.instance = new Logger(); }
		return Logger.instance;
	}
	
	public void setLevel(LoggerLevel lvl) 
		{ this.level = lvl; }
	
	public static void setLogLevel(LoggerLevel lvl) 
		{ Logger.getInstance().level = lvl; }
	
	public void println(LoggerLevel lvl, String content) {
		if (lvl.equals(LoggerLevel.LL_NONE))			{ ; }
		else if (lvl.equals(LoggerLevel.LL_FORUSER))	{ System.out.println(content); }
		else if (lvl.ordinal() >= this.level.ordinal())	{ 
			GregorianCalendar currentTime	= new GregorianCalendar();
			String timeToPrint	= new String("");
			int month	= (currentTime.get(Calendar.MONTH) + 1);
			int day		= currentTime.get(Calendar.DAY_OF_MONTH);
			int milli	= currentTime.get(Calendar.MILLISECOND);
			timeToPrint			+= currentTime.get(Calendar.YEAR) + "-";
			timeToPrint			+= ((month < 10)?"0":"") + month + "-";
			timeToPrint			+= ((day < 10)?"0":"") + day + " ";
			timeToPrint			+= currentTime.get(Calendar.HOUR_OF_DAY) + ":";
			timeToPrint			+= currentTime.get(Calendar.MINUTE) + ":";
			timeToPrint			+= currentTime.get(Calendar.SECOND) + ".";
			timeToPrint			+= ((milli < 100)?"0"+((milli < 10)?"0":""):"") + milli + "";
			String lvlType		= new String(lvl.toString());
			System.out.println( timeToPrint + " " + lvlType + " " + content); 
		} /** END "if (lvl.ordinal() >= this.level.ordinal())" */
	}
	
	public static void printlnLog(LoggerLevel lvl, String content) 
		{ Logger.getInstance().println(lvl, content); }
	
	public void print(LoggerLevel lvl, String content) {
		if (lvl.equals(LoggerLevel.LL_NONE))			{ ; }
		else if (lvl.equals(LoggerLevel.LL_FORUSER))	{ System.out.print(content); }
		else if (lvl.ordinal() >= this.level.ordinal())	{ 
			GregorianCalendar currentTime	= new GregorianCalendar();
			String timeToPrint	= new String("");
			int month	= (currentTime.get(Calendar.MONTH) + 1);
			int day		= currentTime.get(Calendar.DAY_OF_MONTH);
			int milli	= currentTime.get(Calendar.MILLISECOND);
			timeToPrint			+= currentTime.get(Calendar.YEAR) + "-";
			timeToPrint			+= ((month < 10)?"0":"") + month + "-";
			timeToPrint			+= ((day < 10)?"0":"") + day + " ";
			timeToPrint			+= currentTime.get(Calendar.HOUR_OF_DAY) + ":";
			timeToPrint			+= currentTime.get(Calendar.MINUTE) + ":";
			timeToPrint			+= currentTime.get(Calendar.SECOND) + ".";
			timeToPrint			+= ((milli < 100)?"0"+((milli < 10)?"0":""):"") + milli + "";
			String lvlType		= new String(lvl.toString());
			System.out.print( timeToPrint + " " + lvlType + " " + content); 
		} /** END "if (lvl.ordinal() >= this.level.ordinal())" */
	}
	
	public static void printLog(LoggerLevel lvl, String content) 
		{ Logger.getInstance().print(lvl, content); }
}

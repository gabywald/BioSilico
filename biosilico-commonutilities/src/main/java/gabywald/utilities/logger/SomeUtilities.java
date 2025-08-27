package gabywald.utilities.logger;

import gabywald.utilities.logger.Logger.LoggerLevel;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Some Utilities for File's and FileChooser's...
 * @author Gabriel Chandesris (2011)
 */
public class SomeUtilities {
	/** Image files' extension. */
	public static final String jpeg	= "jpeg", jpg	= "jpg", 
								gif	= "gif", tiff	= "tiff", 
								tif	= "tif", png	= "png";
	/** BibTeX files' extension. */
	public static final String bib	= "bib";
	/** Modelica files' extension. */
	public static final String mod	= "mo";
	/** CellML files' extension. */
	public static final String cml	= "cellml";
	/** SDF files' extension. */
	public static final String sdf	= "sdf";

	/**
	 * Get the extension of a file.
	 * @param f (File)
	 * @return (String)
	 */  
	public static String getExtension(File f) {
		String ext	= null;
		String s	= f.getName();
		int i		= s.lastIndexOf('.');
		if ( ( (i > 0) && (!f.isDirectory()) ) 
				&& (i < s.length() - 1) )
			{ ext = s.substring(i+1).toLowerCase(); }
		return ext;
	}
	
	/**
	 * Getting current "date + time".
	 * @return (String) 'YYYYMMDDHHMMSS'
	 */
	public static String getCurrentDateTime() {
		String toReturn = new String("");
		
		GregorianCalendar currentDateTime = new GregorianCalendar();
		String year		= ""+currentDateTime.get(Calendar.YEAR);
		String month	= ""+(currentDateTime.get(Calendar.MONTH)+1);
		String day		= ""+currentDateTime.get(Calendar.DAY_OF_MONTH);
		String hour		= ""+currentDateTime.get(Calendar.HOUR_OF_DAY);
		String minute	= ""+currentDateTime.get(Calendar.MINUTE);
		String second	= ""+currentDateTime.get(Calendar.SECOND);
		month	= ((month.length() < 2)?"0":"")+month;
		day		= ((day.length() < 2)?"0":"")+day;
		hour	= ((hour.length() < 2)?"0":"")+hour;
		minute	= ((minute.length() < 2)?"0":"")+minute;
		second	= ((second.length() < 2)?"0":"")+second;
		toReturn	= year+month+day+hour+minute+second;
		
		return toReturn;
	}
	
	
	
	public static String toString(GregorianCalendar gc, SimpleDateFormat sdt) 
		{ return sdt.format(gc.getTime()); }
	
	public static GregorianCalendar fromString(String stamp, SimpleDateFormat sdt) 
			throws ParseException {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(sdt.parse(stamp));
		return gc; 
	}
	
	
	// DONE replace with a SimpleDateFormat !
	
	/** @deprecated (20150129) */
	public static String toString(GregorianCalendar gc) {
		String timeToPrint	= new String("");
		int month	= (gc.get(Calendar.MONTH) + 1);
		int day		= gc.get(Calendar.DAY_OF_MONTH);
		int milli	= gc.get(Calendar.MILLISECOND);
		timeToPrint	+= gc.get(Calendar.YEAR) + "-";
		timeToPrint	+= ((month < 10)?"0":"") + month + "-";
		timeToPrint	+= ((day < 10)?"0":"") + day + " ";
		timeToPrint	+= gc.get(Calendar.HOUR_OF_DAY) + ":";
		timeToPrint	+= gc.get(Calendar.MINUTE) + ":";
		timeToPrint	+= gc.get(Calendar.SECOND) + ".";
		timeToPrint	+= ((milli < 100)?"0"+((milli < 10)?"0":""):"") + milli + "";
		return timeToPrint;
	}
	
	/** @deprecated (20150129) */
	public static GregorianCalendar fromString(String stamp) throws ParseException {
		String pattern = "^([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2}):([0-9]{2}).([0-9]{3})$";
		if ( ! stamp.matches(pattern))	{ throw new ParseException("Not matching Pattern !", 0); }
		// { return null; }
		Pattern pat	= Pattern.compile(pattern);
		Matcher m	= pat.matcher(stamp);
		if ( ! m.matches())				{ throw new ParseException("Not Matching pattern !", 0); }				
		// { return null; }
		GregorianCalendar toReturn = new GregorianCalendar();
		int yea = Integer.parseInt(m.group(1));
		int mon = Integer.parseInt(m.group(2)) - 1;
		int day = Integer.parseInt(m.group(3));
		int hou = Integer.parseInt(m.group(4));
		int min = Integer.parseInt(m.group(5));
		int sec = Integer.parseInt(m.group(6));
		toReturn.set(yea, mon, day, hou, min, sec);
		return toReturn;
	}
	
	/**
	 * Getting a random int between 0 (included) and sizeMax (excluded). 
	 * @param sizeMax (int)
	 * @return (int)
	 */
	public static int randomValue(int sizeMax) {
		int value = (int)Math.rint(Math.random()*sizeMax);
		while (value == sizeMax) 
			{ value = (int)Math.rint(Math.random()*sizeMax); }
		return value;
	}
	
	
	/**
	 * Make an append of txt 'multi' times. 
	 * @param txt (String)
	 * @param multi (String)
	 * @return (String) 'txt' appended (multi) times. 
	 * @deprecated Use {@linkplain SomeUtilities#repeat(String, int)} instead !!
	 */
	public static String multiple(String txt, int multi) 
		{ return SomeUtilities.repeat(txt, multi); }
	
	/**
	 * To repeat a given txt' 'times' times. 
	 * @param txt (String)
	 * @param times (int)
	 * @return (String)
	 */
	public static String repeat(String txt, int times) {
		String toReturn = new String("");
		for (int i = 0 ; i < times ; i++) 
			{ toReturn += txt; }
		return toReturn;
	}
	
	/** 
	 * Sort the content of a String, char by char, in alphabetical order...
	 * @param data (String)
	 * @return (String)
	 */
	public static String sort(String data) {
		char[] tabOfData	= data.toCharArray();
		boolean isSorted	= false;
		while(!isSorted) {
			isSorted = true;
			for (int i = 0 ; i < tabOfData.length ; i++) {
				for (int j = i+1 ; j < tabOfData.length ; j++) {
					char ichar = tabOfData[i];
					char jchar = tabOfData[j];
					if (ichar > jchar) {
						tabOfData[i] = jchar;
						tabOfData[j] = ichar;
						isSorted = false;
					}
				}
			}
		}
		return new String(tabOfData);
	}
	
	/**
	 * To check if a String instance contains only upperCase letters. 
	 * <br>Equivalent should be : <i>seq.toUpperCase().equals(seq)</i>. 
	 * @param seq (String)
	 * @return (boolean)
	 */
	public static boolean isAllUpperCase(String seq) {
		for (int i = 0 ; i < seq.length() ; i++) {
			if (Character.isLowerCase(seq.charAt(i))) 
				{ return false; }
		}
		return true;
	}
	
	/**
	 * String-ify content of a Matcher instance (if it matches...). 
	 * @param matcher (Matcher)
	 * @return (String)
	 */
	public static String toStringMatcher(Matcher matcher) {
		String toReturn	= new String("");
		if (matcher.matches()) {
			for (int m = 0 ; m <= matcher.groupCount() ; m++) 
				{ Logger.printlnLog(LoggerLevel.LL_INFO, "\t"+m+"\t'"+matcher.group(m)+"'"); }
		}
		return toReturn;
	}
	
	/**
	 * To String-ify a Hash with String keys ; output formatted keys and values as String. 
	 * @param hash (HashMap&lt;String, ?&gt;)
	 * @return (String)
	 */
	public static String toStringHashMore(HashMap<String, ?> hash) {
		String toReturn	= new String("");
		String[] tabOfKeys  = hash.keySet().toArray(new String[0]);
		toReturn += "\t'"+tabOfKeys.length+"' keys !!\n";
		for (int i = 0 ; i < tabOfKeys.length ; i++) 
			{ toReturn += "\t'"+tabOfKeys[i]+"'\t'"
				+hash.get(tabOfKeys[i].toString())+"'\n"; }
		return toReturn;
	}
	
	/**
	 * To String-ify a Hash with String keys ; output only values as String. 
	 * @param hash (HashMap&lt;String, ?&gt;)
	 * @return (String)
	 */
	public static String toStringHashGet(HashMap<String, ?> hash) throws Exception {
		String toReturn	= new String("");
		String[] tabOfKeys  = hash.keySet().toArray(new String[0]);
		
		for (int i = 0 ; i < tabOfKeys.length ; i++) 
			{ toReturn += hash.get(tabOfKeys[i].toString()); }
		
		return toReturn;
	}
	
	/**
	 * To string-ify a set of data. 
	 * @param set (List&lt;?&gt;)
	 * @return (String)
	 */
	public static String toStringList(List<?> set) {
		String toReturn	= new String("");
		for (int i = 0 ; i < set.size() ; i++) 
			{ toReturn += set.get(i).toString();  }
		return toReturn;
	}

}

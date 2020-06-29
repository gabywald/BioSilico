package gabywald.creatures.geneticReader;

import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;
import gabywald.utilities.others.PropertiesLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Gabriel Chandesris (2013)
 */
public class GeneticFileContent {
	private String fileName;
	private boolean isReadable;
	
	private BufferedReader buffer;
	
	public GeneticFileContent(String file) {
		this.fileName	= file;
		Logger.printlnLog(LoggerLevel.LL_INFO, this.fileName);
		// try {
			InputStream ips			= PropertiesLoader.openResource(this.fileName); 
			// new FileInputStream(this.fileName);
			InputStreamReader ipsr	= new InputStreamReader(ips);
			this.buffer				= new BufferedReader(ipsr);
			this.isReadable = true;
		// } 
		// catch (FileNotFoundException e) 
		// 	{ this.isReadable = false;Logger.printlnLog(LoggerLevel.LL_ERROR, "File Not Found !"); } 
	}

	public char nextChar() {
		int oneChar = -1;
		try { 
			if ((oneChar = this.buffer.read()) != -1) 
				{ return (char)oneChar; }
			else { 
				this.buffer.close();
				this.isReadable = false; 
			}
		} catch (IOException e) { 
			this.isReadable = false;
			e.printStackTrace(); 
		}
		return (char)-1; // Character.MIN_VALUE;
	}

	public String getFileName()	{ return this.fileName; }
	public boolean isReadable()	{ return this.isReadable; }
	
	public static boolean isAlphaNumeric(char elt) {
		Pattern pattern = Pattern.compile("[a-zA-Z0-9_\\- <>]");
		Matcher matcher = pattern.matcher(elt + "");
		return matcher.matches();
	}
	
	
}

package gabywald.creatures.geneticReader;

import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;
import gabywald.utilities.others.PropertiesLoader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Gabriel Chandesris (2013)
 * @deprecated see {@link GeneticFileContent}
 */
public class GeneticFileContentOld {
	private String fileName;
	private String content;
	private boolean isReaded;
	
	public GeneticFileContentOld(String file) {
		this.fileName	= file;
		this.content	= new String("");
		Logger.printlnLog(LoggerLevel.LL_INFO, this.fileName);
		try {
			InputStream ips			= PropertiesLoader.openResource(this.fileName); 
			// new FileInputStream(this.fileName);
			Logger.printlnLog(LoggerLevel.LL_VERBOSE, "00");
			InputStreamReader ipsr	= new InputStreamReader(ips);
			Logger.printlnLog(LoggerLevel.LL_VERBOSE, "01");
			BufferedReader br		= new BufferedReader(ipsr);
			Logger.printlnLog(LoggerLevel.LL_VERBOSE, "02");
			int oneChar = -1;
			while ((oneChar = br.read()) != -1) 
				{ this.content += new Character((char)oneChar); }
			// System.out.print(new Character((char)oneChar));
			Logger.printlnLog(LoggerLevel.LL_VERBOSE, "03");
			br.close(); 
			this.isReaded = true;
			Logger.printlnLog(LoggerLevel.LL_VERBOSE, "04");
		} 
		catch (FileNotFoundException e) 
			{ this.isReaded = false;Logger.printlnLog(LoggerLevel.LL_ERROR, "File Not Found !"); } 
		catch (IOException e) 
			{ this.isReaded = false;Logger.printlnLog(LoggerLevel.LL_ERROR, "IOException !"); }
	}


	public String getFileName()	{ return this.fileName; }
	public String getContent()	{ return this.content; }
	public boolean isReaded()	{ return this.isReaded; }
	
	public int length()				{ return this.content.length(); }
	public char charAt(int index)	{ return this.content.charAt(index); }
	public int toASCII(int index)	{ return (new Character(this.content.charAt(index)).hashCode()); }
	
	public boolean isAlphaNumeric(int index) {
		Pattern pattern = Pattern.compile("[a-zA-Z0-9_\\- <>]");
		Matcher matcher = pattern.matcher(this.charAt(index) + "");
		return matcher.matches();
	}
	
	
}

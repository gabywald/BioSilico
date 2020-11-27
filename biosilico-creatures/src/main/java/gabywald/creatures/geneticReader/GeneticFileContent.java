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
 * @author Gabriel Chandesris (2013, 2020)
 */
public class GeneticFileContent {
	private String fileName;
	private boolean isReadable;
	private InputStream ips;
	private InputStreamReader ipsr;
	private BufferedReader buffer;
	
	public GeneticFileContent(String file) {
		this.fileName	= file;
		
		Logger.printlnLog(LoggerLevel.LL_INFO, this.fileName);
		
		this.ips			= PropertiesLoader.openResource(this.fileName); 
		// new FileInputStream(this.fileName);
		this.ipsr		= new InputStreamReader(ips);
		this.buffer		= new BufferedReader(ipsr);
		this.isReadable = true;
	}
	
	public byte nextByte() {
		byte[] bRead = new byte[1];
		try {
			;
			if ( this.ips.read(bRead) != -1 ) 
				{ return bRead[0]; }
			else { 
				this.buffer.close();
				this.isReadable = false; 
			}
		} catch (IOException e) {
			this.isReadable = false;
			e.printStackTrace(); 
		}
		return Byte.MIN_VALUE;
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
	
	public static int charToNum(char c) {
		return (int)(c);
	}
	
	public static boolean isAlphaNumeric(char elt) {
		Pattern pattern = Pattern.compile("[a-zA-Z0-9_\\- <>]");
		Matcher matcher = pattern.matcher(elt + "");
		return matcher.matches();
	}
	
	
}

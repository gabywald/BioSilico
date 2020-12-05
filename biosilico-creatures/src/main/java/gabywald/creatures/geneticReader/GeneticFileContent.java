package gabywald.creatures.geneticReader;

import gabywald.creatures.genetics.simple.ICreaturesGene;
import gabywald.creatures.genetics.simple.factory.GeneCreaturesFactory;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;
import gabywald.utilities.others.PropertiesLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
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
		
		Logger.printlnLog(LoggerLevel.LL_INFO, "{" + this.fileName + "}");
		
		if ( (this.fileName == null) || (this.fileName.equals("")) ) 
			{ this.isReadable = false; }
		
		this.ips		= PropertiesLoader.openResource(this.fileName); 
		// new FileInputStream(this.fileName);
		this.ipsr		= new InputStreamReader(ips);
		this.buffer		= new BufferedReader(ipsr);
		this.isReadable = true;
	}
	
	public byte[] nextGene() {
		String sequence		= new String( this.nextSequenceOfBytes() );
		byte[] toReturn	= null;
		if (sequence.equals("gene")) { toReturn = new byte[0]; }
		
		if (sequence.endsWith("gene") || (sequence.endsWith("gend")) )
			{ toReturn = sequence.substring(0, sequence.length() - 4).getBytes(); }
		
		// ***** On the end of genome !
		this.isReadable = ( ! (sequence.endsWith("gend")) );
		// ***** On starting !
		if (toReturn == null) { throw new NullPointerException(); }
		if (toReturn.length == 0) { toReturn = this.nextGene(); }
		
		return toReturn;
	}
	
	private byte[] nextSequenceOfBytes() {
		List<Byte> lstBytes = new ArrayList<Byte>();
		
		while (this.isReadable() ) {
			lstBytes.add(this.nextByte());
			if (lstBytes.size() >= 4) {
				byte[] toTest = GeneticFileContent.convert(lstBytes);
				String strTest = new String(toTest);
				if ( // ( toTest.length > 4) && 
						(strTest.endsWith("gene") || strTest.endsWith("gend") ) ) {
					return GeneticFileContent.convert(lstBytes);
				}
			}
		}
		return GeneticFileContent.convert(lstBytes);
	}
	
	private static byte[] convert(List<Byte> lstBytes) {
		byte[] bytes = new byte[lstBytes.size()];
		int i = 0;
		for (Byte b : lstBytes) {
		    bytes[i++] = b.byteValue();
		}
		return bytes;
	}
	
	public byte nextByte() {
		byte[] bRead = new byte[1];
		try {
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
	
	public static ICreaturesGene readGene(String input) {
		int type = input.charAt( 0 );
		int subt = input.charAt( 1 );
		return GeneCreaturesFactory.generateFrom(	type + "-" + subt, 
													input.substring(0, input.length()));
	}
	
	public static int charToNum(char c) {
		return (int)(c);
	}
	
	public static boolean isAlphaNumeric(char elt) {
		Pattern pattern = Pattern.compile("[a-zA-Z0-9_\\- <>]");
		Matcher matcher = pattern.matcher(elt + "");
		return matcher.matches();
	}
	
	
}

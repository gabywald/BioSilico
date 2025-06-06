package gabywald.global.data;

import gabywald.global.exceptions.DataException;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;
import gabywald.utilities.others.PropertiesLoader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class to ensure a generic file format use.
 * <br>Overload of original {@linkplain java.io.File} to ensure a "real file" Object. 
 * @author St&eacute;fan Engelen (2006)
 * @author Gabriel Chandesris (2008-2010)
 * @see Directory
 */
@SuppressWarnings("serial")
public class File extends Directory {
	
	public static final String NOTYPE = "notype";
	
	/** Name / path to the directory (overload). */
	private String pathName;
	/** File type. */
	private String datatype;
	/** File name. */
	private String fileName;
	/** File String fields. */
	private List<String> champs;
	/** File is valid or not. */
	private boolean formatValide;

	/**
	 * Constructor with given name. 
	 * Type is empty. Champs has length of 0. 
	 * Validity is false (default).  
	 * @param fileName (String)
	 */
	public File(String fileName) {
		this(File.NOTYPE, fileName, null);
	}

	/**
	 * Constructor with given type and name. 
	 * Champs has length of 0. Validity is false (default). 
	 * @param type
	 * @param fileName (String)
	 */
	public File(String type, String fileName) {
		this(type, fileName, null);
	}

	/**
	 * Constructor with given name and champs. 
	 * Type is empty. Validity is false (default).  
	 * @param fileName
	 * @param champs
	 */
	public File(String fileName, String[] champs) {
		this(File.NOTYPE, fileName, champs);
	}

	/**
	 * Constructor with given type, name and champs. 
	 * Validity is false (default). 
	 * @param type
	 * @param fileName
	 * @param champs
	 */
	public File(String type, String fileName, String[] champs) {
		super(File.buildDir(fileName));
		
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "fileName: {" + fileName + "} of type {" + type + "}");
		
		this.datatype = type;
		this.fileName = File.removeDirFromName(fileName);
		this.pathName = super.getDirName();
		if (champs != null) {
			this.champs = new ArrayList<String>(champs.length); 
			this.champs.addAll(Arrays.asList(champs));
		} else {
			this.champs = new ArrayList<String>();
		}
		this.formatValide = false;
	}

	/**
	 * Check if the file exists.
	 * @return (boolean)
	 * @see java.io.File#exists()
	 */
	public boolean fileExists() { return (new java.io.File(this.pathName+this.fileName)).exists(); }

	/**
	 * Overload. 
	 * @see File#fileExists()
	 * @deprecated
	 */
	public boolean exists() { return this.fileExists(); }

	/**
	 * Build directory from given global name to a file. 
	 * @param fileName (String) Whole given name of file. 
	 * @return (String) directory (empty if name not contain it). 
	 */
	protected static String buildDir(String fileName) {
		Pattern dirDetection = Pattern.compile("^(/?.*/)*(.*)$");
		Matcher m	= dirDetection.matcher(fileName);
		String dir	= "";
		if (m.matches()) { dir = m.group(1); }
		return dir;
	}

	/**
	 * Get only the name of the file. 
	 * @param fileName (String) The whole name of the file. 
	 * @return (String) name of the file. 
	 */
	protected static String removeDirFromName(String fileName) {
		if (fileName.lastIndexOf("/") >= 0)
			{ return fileName.substring(fileName.lastIndexOf("/") + 1); }
		else 
			{ return fileName; }
	}

	public int lengthFile()			{ return this.champs.size(); }
	public String getType()			{ return this.datatype; }
	public String getFileName()		{ return this.fileName; }
	public String getDirName() 		{ return this.pathName; } 
	public String getChamp(int i)	{ return this.champs.get(i); }
	public boolean isValid() 		{ return this.formatValide; }

	public void addToChamps(String nextLine) 
		{ this.champs.add(nextLine); }
	
	public void setChamps(String[] champs) { 
		this.champs = new ArrayList<String>(champs.length); 
		this.champs.addAll(Arrays.asList(champs));
	}
	
	public void setChamps(int i, String champs)	
		{ this.champs.set(i, champs); }
	
	public void removeChamps(int i) 
		{ this.champs.remove(i); }

	// public String getDir() 					{ return this.getDirName(); }
	// public void setDir(String dir)			{ this.directory = new Directory(dir); }
	// protected void setDir(Directory dir)		{ this.directory = dir; }
	protected void setValid(boolean valid)		{ this.formatValide = valid; }
	protected void setType(String datatype)		{ this.datatype = datatype; }
	protected void setFileName(String fileName)	{ this.fileName = fileName; }
	protected void setPathName(String pathName)	{ this.pathName = pathName; }

	// TODO optimize this and dependancies
	public String[] getChampsAsTable() 
		{ return Collections.unmodifiableCollection( this.champs ).toArray( new String[0] ); }
	
	public String getChampsToString() {
		StringBuilder sbResult = new StringBuilder();
		this.champs.stream().forEach( f -> sbResult.append( f ).append( "\n" ) );
		return sbResult.toString();
	}

	/**
	 * To obtain the content of a given file. 
	 * Return name of the exception if throw...
	 * @param filename String. 
	 * @return (String) What the file contain or exception. 
	 * @throws IOException 
	 */
	public static String readFile(String filename) throws IOException {
		String contenuFichier	= "";
		BufferedReader br 		= null;
		try {
			br = new BufferedReader(new InputStreamReader( PropertiesLoader.openResource( filename ) ));
			String line = "";
			while((line = br.readLine()) != null)
				{ contenuFichier += line+"\n"; }
		} 
		catch (FileNotFoundException e) 
			{ contenuFichier = filename + " : File Not Found"; } 
		finally { 
			if (br != null) { br.close(); } 
		}
		return contenuFichier;
	}

	/**
	 * To obtain the content of a given file in an instance of this class. 
	 * Return name of the exception if throw...
	 * @param filename (String) 
	 * @return (File) File instance or exception. 
	 * @throws IOException 
	 */
	public static File loadFile(String filename) throws IOException {
		File instance		= new File( filename );
		BufferedReader br 	= null;
		try {
			br = new BufferedReader(new InputStreamReader( PropertiesLoader.openResource( filename ) ));
			String line = "";
			while((line = br.readLine()) != null)
				{ instance.addToChamps(line); }
		} 
		catch (FileNotFoundException e) 
			{ instance.addToChamps(filename + " : File Not Found"); } 
		finally { 
			if (br != null) { br.close(); } 
		}
		return instance;
	}

	/**
	 * To actualize the content in current instance. 
	 * Return name of the exception if throw...
	 * @throws IOException 
	 */
	public void load() throws IOException {
		this.champs = new ArrayList<String>();
		BufferedReader br 	= null;
		try {
			br = new BufferedReader(new InputStreamReader( PropertiesLoader.openResource( this.getDirName() + this.fileName )));
			String line = "";
			while ( (line = br.readLine()) != null )
				{ this.addToChamps(line); }
		} 
		catch (FileNotFoundException e) 
			{ this.addToChamps(this.getDirName() + this.fileName + " : File Not Found"); } 
		finally { 
			if (br != null) { br.close(); } 
		}
	}

	/**
	 * This method to create files. 
	 * @return String (action result). 
	 * @throws DataException 
	 */
	public String printFile() throws DataException {
		StringBuilder sb	= new StringBuilder();
		PrintWriter sortie	= null;
		try {
			if (!this.fileExists()) { super.createDirs(); }
			sb.append(this.datatype).append(" recording ").append(this.fileName).append(" ... ");
			sortie = new PrintWriter(new FileWriter( this.getDirName() + this.fileName ));
			
			for (String field : this.champs) {
				sortie.println(field);
			}
			
			sb.append("OK\n");
		}
		catch (IOException e) {
			sb.append("\nWriting is not permitted (1). \n");
			sb.append(e.getLocalizedMessage()).append("\n");
			sb.append(e.getMessage()).append("\n");
		}
		catch (ArrayIndexOutOfBoundsException e) { 
			sb.append("\nWriting is not permitted (2). \n");
			sb.append(e.getLocalizedMessage()).append("\n");
			sb.append(e.getMessage()).append("\n");
		}
		finally { 
			if (sortie != null) { sortie.close(); } 
		}
		return sb.toString();
	}

	/**
	 * To delete the file : overload of superClass about pathName. 
	 * @throws DataException
	 */
	public void deleteFile() throws DataException {
		Logger.printlnLog(LoggerLevel.LL_WARNING, this.delete() + ""); 
		try { Files.delete(Paths.get(this.pathName + this.fileName)); }
		catch (IOException e) { 
			e.printStackTrace();
		}
		// NOTE to delete complete dir of path, use superclass : 
		// Directory.deleteDirComplete(new File( this.pathName + this.fileName ));
	}
	
	public int nbLines()		{ return this.champs.size(); }
	public String line(int i)	{ return this.champs.get( i ); }
	
//	public boolean hasError() {
//		if (this.getLine(0).matches("ERROR(.*)")) {
//			System.out.println(this.getLine(0));
//			return true;
//		}
//		return false;
//	}
	
}

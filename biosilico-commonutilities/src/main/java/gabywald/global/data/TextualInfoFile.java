package gabywald.global.data;

import java.io.IOException;
import java.util.Hashtable;

import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * This class defines some access to textual datas for Graphical Interfaces (cf. resources/conf/TextualInfo* files). 
 * <br><i>Multiton ("multiple singleton")</i>
 * TODO change / tranfert to use 'File' class ! <=
 * @author Gabriel Chandesris (2011, 2022)
 */
@SuppressWarnings("serial")
public class TextualInfoFile extends File {
	/** Default configuration file path. */
	private static String CONF_PATH				= TextualInfoFile.getIndex().getValueOf("path");
	/** Set of contexts. */
	private static String[] CONTEXTS			= TextualInfoFile.getIndex().getValueOf("contexts").split(";");
	/** Some Specific context access. */
	private static TextualInfoFile[] contexts	= new TextualInfoFile[TextualInfoFile.CONTEXTS.length];
	/** Table of content. */
	private Hashtable<String, String> hash;

	private TextualInfoFile(String name) 
		{ super(name);this.initFile(); }

	/** Constructor helper, in the aim to load the configuration file. */
	private void initFile() {
		this.hash	= new Hashtable<String,String>();
		try {
			this.load();
		} catch (IOException e) {
			Logger.printlnLog(LoggerLevel.LL_ERROR, "TextualInfoFile {" + this.getName() + "} cannot be load !");
		}
		String table[] = this.getChampsAsTable();
		if (!table[0].matches("ERROR")) { 
			for (int i = 0 ; i < table.length ; i++) {
				String[] split = table[i].split("\t");
				if (split.length > 1) 
					{ this.hash.put(split[0], split[1]); }
				else { this.hash.put(split[0], ""); }
			}
		}
	}
	
	private static TextualInfoFile getIndex() 
		{ return new TextualInfoFile
					("src/main/resources/conf/textualInfos/index.conf"); }
	
	public static String toCapitalLetter(String txt) 
		{ return txt.substring(0, 1).toUpperCase()+txt.substring(1); }
	
	public static String getPathOfContexts()	{ return TextualInfoFile.CONF_PATH; }
	public static String[] getContexts()		{ return TextualInfoFile.CONTEXTS; }
	
	/**
	 * Get a specific TextualInfoFile with a given context or null.  
	 * @param index (int)
	 * @return (TextualInfoFile) or null if does not exist for this index. 
	 */
	public static TextualInfoFile getContext(int index) {
		if ( (index >= 0) && (index < TextualInfoFile.contexts.length) ) {
			if (TextualInfoFile.contexts[index] == null) 
			{ TextualInfoFile.contexts[index] = new  TextualInfoFile
				(TextualInfoFile.CONF_PATH+TextualInfoFile.CONTEXTS[index]); }
			return TextualInfoFile.contexts[index]; 
		} else { return null; }
	}
	
	public static TextualInfoFile getGeneralContext() 
		{ return TextualInfoFile.getContext(0); }
	
	public static TextualInfoFile getFrameGenericContext() 
		{ return TextualInfoFile.getContext(1); }
	
	public static TextualInfoFile getFrameClientContext() 
		{ return TextualInfoFile.getContext(2); }
	public static TextualInfoFile getFrameDaemonContext() 
		{ return TextualInfoFile.getContext(3); }
	public static TextualInfoFile getFrameServerContext() 
		{ return TextualInfoFile.getContext(4); }
	
	public static TextualInfoFile getFrameworkContext()
		{ return TextualInfoFile.getContext(5); }
	
	public static TextualInfoFile getRPGpersonnaeBuilder()
		{ return TextualInfoFile.getContext(6); }
	
	public String getValueOf(String key) { 
		if (this.hash.get(key) == null) 
			{ System.out.println("\t'" + key + "' not found in '" + this.getName() + "'!"); }
		return this.hash.get(key);
	}
}

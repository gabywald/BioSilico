package gabywald.global.data.samples;

import java.io.InputStream;
import java.util.Hashtable;

import gabywald.global.data.Fichier;
import gabywald.utilities.others.PropertiesLoader;

/**
 * This class defines some access to textual datas for Graphical Interfaces (cf. resources/conf/TextualInfo* files). 
 * <br><i>Multiton ("multiple singleton")</i>
 * @author Gabriel Chandesris (2011)
 */
public class TextualInfoFile extends Fichier {
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
	
	private TextualInfoFile(String name, InputStream is) 
		{ super(name, is);this.initFile(); }

	/** Constructor helper, in the aim to load the configuration file. */
	private void initFile() {
		this.hash	= new Hashtable<String,String>();
		String table[] = this.getTable();
		if (!table[0].matches("ERROR")) { 
			for (int i = 0 ; i < this.getNbLines() ; i++) {
				String[] split = table[i].split("\t");
				if (split.length > 1) 
					{ this.hash.put(split[0], split[1]); }
				else { this.hash.put(split[0], ""); }
			}
		}
	}
	
	private static TextualInfoFile getIndex() { 
		String inputFile = "conf/textualInfos/index.conf";
		return new TextualInfoFile(inputFile, PropertiesLoader.openResource(inputFile));
		// return new TextualInfoFile("conf/textualInfos/index.conf"); 
	}
	
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
			if (TextualInfoFile.contexts[index] == null) {
				String inputFile = TextualInfoFile.CONF_PATH+TextualInfoFile.CONTEXTS[index];
				TextualInfoFile.contexts[index] = new TextualInfoFile(inputFile, PropertiesLoader.openResource(inputFile));
			}
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
	
	public String getValueOf(String key) { 
		if (this.hash.get(key) == null) 
			{ System.out.println("\t'"+key+"' not found in '"+this.getFichier()+"'!"); }
		return this.hash.get(key);
	}
}

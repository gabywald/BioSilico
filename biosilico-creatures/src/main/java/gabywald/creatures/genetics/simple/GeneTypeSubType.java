package gabywald.creatures.genetics.simple;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import gabywald.global.data.File;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * This class represents the Gene's definitions. 
 * This class could be defined as an enum from a resource / configuration file. 
 * @author Gabriel Chandesris (2020)
 */
public class GeneTypeSubType {
	
	private static Map<String, GeneTypeSubType> geneTypesSubTypes = null;
	
	private int type;
	private int subtype;
	private int attemptedLength;
	private String name;
	
	private GeneTypeSubType(int type, int subtype, int attemptedLength, String name) {
		this.type = type;
		this.subtype = subtype;
		this.attemptedLength = attemptedLength;
		this.name = name;
	}
	
	private static Map<String, GeneTypeSubType> load() {
		Map<String, GeneTypeSubType> toReturn = new HashMap<String, GeneTypeSubType>();
		
		try {
			File geneDefinitions = File.loadFile( "creatures/creaturesDocs/geneticsParsing/data/geneC1C2definitions.txt" );
			for (int i = 0 ; (i < geneDefinitions.lengthFile()) ; i++) {
				String line			= geneDefinitions.getChamp(i);
				if (line.startsWith( "## " )) { continue; }
				String[] splitter	= line.split("\t");
				String key			= splitter[0] + "-" + splitter[1];
				try {
					GeneTypeSubType gtst	= new GeneTypeSubType(	Integer.parseInt(splitter[0]), Integer.parseInt(splitter[1]), 
																	Integer.parseInt(splitter[2]), 
																	splitter[3].split(" -- ")[1]);
					toReturn.put(key, gtst);
				} catch (NumberFormatException nfe) { Logger.printlnLog(LoggerLevel.LL_WARNING, "GeneTypeSubType NOT treated {" + line + "}"); }
			}
			/** TODO treatment : IOException */
		}		
		catch (IOException e) { e.printStackTrace(); }
		
		return toReturn;
	}
	
	public static GeneTypeSubType getGeneTypeSubType(String key) {
		if (GeneTypeSubType.geneTypesSubTypes == null) 
			{ GeneTypeSubType.geneTypesSubTypes = GeneTypeSubType.load(); }
		return GeneTypeSubType.geneTypesSubTypes.get( key );
	}

	public static Map<String, GeneTypeSubType> getGeneTypesSubTypes() {
		if (GeneTypeSubType.geneTypesSubTypes == null) 
			{ GeneTypeSubType.geneTypesSubTypes = GeneTypeSubType.load(); }
		return GeneTypeSubType.geneTypesSubTypes;
	}

	public int getType() 
		{ return this.type; }

	public int getSubtype() 
		{ return this.subtype; }

	public int getAttemptedLength() 
		{ return this.attemptedLength; }

	public String getName() 
		{ return this.name; }
	
	
}

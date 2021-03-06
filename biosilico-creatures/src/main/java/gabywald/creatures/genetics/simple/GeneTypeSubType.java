package gabywald.creatures.genetics.simple;

import java.io.IOException;
import java.util.Arrays;
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
	private int[] attemptedLength;
	private String name;
	
	private GeneTypeSubType(int type, int subtype, int[] attemptedLength, String name) {
		this.type = type;
		this.subtype = subtype;
		this.attemptedLength = attemptedLength;
		this.name = name;
	}
	
	private static Map<String, GeneTypeSubType> load() {
		Map<String, GeneTypeSubType> toReturn = new HashMap<String, GeneTypeSubType>();
		
		try {
			String path2geneTST = Creatures1GenomeParser.PROPERTIES.getProperty( "data.enum.creatures1.geneTypesSubTypes.creatures1and2" );
			File geneDefinitions = File.loadFile( path2geneTST );
			for (int i = 0 ; (i < geneDefinitions.lengthFile()) ; i++) {
				String line			= geneDefinitions.getChamp(i);
				if (line.startsWith( "## " )) { continue; }
				String[] splitter	= line.split("\t");
				String key			= splitter[0] + "-" + splitter[1];
				try {
					int[] attLengths = Arrays.asList( splitter[2].split(",") ).stream()
									.mapToInt( str -> Integer.parseInt(str) )
									.toArray();
					GeneTypeSubType gtst	= new GeneTypeSubType(	Integer.parseInt(splitter[0]), Integer.parseInt(splitter[1]), 
																	attLengths, // Integer.parseInt(splitter[2]), 
																	// splitter[3].split(" -- ")[1]);
																	splitter[3]);
					toReturn.put(key, gtst);
				} catch (NumberFormatException nfe) { Logger.printlnLog(LoggerLevel.LL_WARNING, "GeneTypeSubType NOT treated {" + line + "}"); }
			}
			/** TODO treatment : IOException */
		}		
		catch (IOException e) { e.printStackTrace(); }
		
		return toReturn;
	}
	
	public static GeneTypeSubType getGeneTypeSubType(int type, int subt) {
		return GeneTypeSubType.getGeneTypeSubType( type + "-" + subt );
	}
	
	public static GeneTypeSubType getGeneTypeSubType(String key) {
		return GeneTypeSubType.getGeneTypesSubTypes().get( key );
	}

	public static Map<String, GeneTypeSubType> getGeneTypesSubTypes() {
		if (GeneTypeSubType.geneTypesSubTypes == null) 
			{ GeneTypeSubType.geneTypesSubTypes = GeneTypeSubType.load(); }
		return GeneTypeSubType.geneTypesSubTypes;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)						{ return false; }
		if (obj.getClass() != this.getClass())	{ return false; }
		
		final GeneTypeSubType other = (GeneTypeSubType) obj;
		if (this.type != other.type)			{ return false; }
		if (this.subtype != other.subtype)		{ return false; }
		if (this.attemptedLength.length != other.attemptedLength.length)	{ return false; }
		for (int i = 0 ; i < this.attemptedLength.length ; i++) {
			if (this.attemptedLength[i] != other.attemptedLength[i])		{ return false; }
		}
		if ( ! this.name.equals(other.name))			{ return false; }
		
		return true;
	}

	public int getType() 
		{ return this.type; }

	public int getSubtype() 
		{ return this.subtype; }

	public int[] getAttemptedLength() 
		{ return this.attemptedLength; }
	
	public int getAttemptedLengthSpecific(int i) 
		{ return this.attemptedLength[ i ]; }
	
	public int getAttemptedLengthC1() 
		{ return this.getAttemptedLengthSpecific( 0 ); }
	
	public int getAttemptedLengthC2() 
		{ return this.getAttemptedLengthSpecific( 1 ); }
	
	public int getAttemptedLengthC3() 
		{ return this.getAttemptedLengthSpecific( 2 ); }
	
	public String getName() 
		{ return this.name; }
	
	public String getShortName() 
		{ return this.name.split(" -- ")[1]; }
	
	public String getGeneGroup() 
		{ return this.name.split(" -- ")[0]; }
	
	
}

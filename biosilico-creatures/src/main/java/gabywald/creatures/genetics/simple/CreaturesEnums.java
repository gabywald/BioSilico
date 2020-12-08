package gabywald.creatures.genetics.simple;

import java.util.Arrays;
import java.util.List;

/**
 * Some useful enumerations for genetic analysis of Creatures' genomes. 
 * @author Gabriel Chandesris (2020)
 */
public class CreaturesEnums {
	
	/**
	 * Link to properties' names. 
	 * @author Gabriel Chandesris (2020)
	 */
	private enum CreaturesEnumsProperties {
		LOBE_FLAGS		( "data.enum.creatures1.lobeflags" ), 
		GENE_BIT_FLAGS	( "data.enum.creatures1.genebitflags" ), 
		BODY_PARTS		( "data.enum.creatures1.bodyparts" ), 
		SWITCH_ON_STAGE	( "data.enum.creatures1.switchonstage" ), 
		SPECIES			( "data.enum.creatures1.species" ), 
		PIGMENT_COLOR	( "data.enum.creatures1.pigmentcolor" ), 
		SVRULES			( "data.enum.creatures1.svrules" );
		
		private String property = null;
		private CreaturesEnumsProperties(String prop) 
			{ this.property = prop; }
		
		public String getProperty() { return this.property; }
	}
	
	private static List<String> LOBE_FLAGS		= null;
	private static List<String> GENE_BIT_FLAGS	= null;
	private static List<String> BODY_PARTS		= null;
	private static List<String> SWITCH_ON_STAGE	= null;
	private static List<String> SPECIES			= null;
	private static List<String> PIGMENT_COLOR	= null;
	private static List<String> SVRULES			= null;
	
	private static List<CreaturesChemical> C1_CHEM = null;
	private static List<CreaturesChemical> C2_CHEM = null;
	private static List<CreaturesChemical> C3_CHEM = null;

	/**
	 * Get content from property and convert it to a List of String. 
	 * @param property
	 * @return Corresponding list. 
	 */
	private static List<String> getEnumFrom(String property) {
		String propertyContent = Creatures1GenomeParser.PROPERTIES.getProperty( property );
		return Arrays.asList( propertyContent.split(", ") );
	}
	
	public static List<String> getLobeFlags() {
		if (CreaturesEnums.LOBE_FLAGS == null) {
			CreaturesEnums.LOBE_FLAGS = CreaturesEnums.getEnumFrom
					( CreaturesEnumsProperties.LOBE_FLAGS.getProperty() );
		}
		return CreaturesEnums.LOBE_FLAGS;
	}
	
	public static List<String> getGeneBitFlags() {
		if (CreaturesEnums.GENE_BIT_FLAGS == null) {
			CreaturesEnums.GENE_BIT_FLAGS = CreaturesEnums.getEnumFrom
					( CreaturesEnumsProperties.GENE_BIT_FLAGS.getProperty() );
		}
		return CreaturesEnums.GENE_BIT_FLAGS;
	}
	
	public static List<String> getBodyParts() {
		if (CreaturesEnums.BODY_PARTS == null) {
			CreaturesEnums.BODY_PARTS = CreaturesEnums.getEnumFrom
					( CreaturesEnumsProperties.BODY_PARTS.getProperty() );
		}
		return CreaturesEnums.BODY_PARTS;
	}
	
	public static List<String> getSwitchOnStages() {
		if (CreaturesEnums.SWITCH_ON_STAGE == null) {
			CreaturesEnums.SWITCH_ON_STAGE = CreaturesEnums.getEnumFrom
					( CreaturesEnumsProperties.SWITCH_ON_STAGE.getProperty() );
		}
		return CreaturesEnums.SWITCH_ON_STAGE;
	}
	
	public static List<String> getSpecies() {
		if (CreaturesEnums.SPECIES == null) {
			CreaturesEnums.SPECIES = CreaturesEnums.getEnumFrom
					( CreaturesEnumsProperties.SPECIES.getProperty() );
		}
		return CreaturesEnums.SPECIES;
	}
	
	public static List<String> getPigmentColors() {
		if (CreaturesEnums.PIGMENT_COLOR == null) {
			CreaturesEnums.PIGMENT_COLOR = CreaturesEnums.getEnumFrom
					( CreaturesEnumsProperties.PIGMENT_COLOR.getProperty() );
		}
		return CreaturesEnums.PIGMENT_COLOR;
	}
	
	public static List<String> getSVRules() {
		if (CreaturesEnums.SVRULES == null) {
			CreaturesEnums.SVRULES = CreaturesEnums.getEnumFrom
					( CreaturesEnumsProperties.SVRULES.getProperty() );
		}
		return CreaturesEnums.SVRULES;
	}
	
	public static List<CreaturesChemical> getC1Chemicals() {
		if (CreaturesEnums.C1_CHEM == null) {
			CreaturesEnums.C1_CHEM = CreaturesChemical
					.getCreaturesChemicals(CreaturesVersion.CREATURES1);
		}
		return CreaturesEnums.C1_CHEM;
	}
	
	public static List<CreaturesChemical> getC2Chemicals() {
		if (CreaturesEnums.C2_CHEM == null) {
			CreaturesEnums.C2_CHEM = CreaturesChemical
					.getCreaturesChemicals(CreaturesVersion.CREATURES1);
		}
		return CreaturesEnums.C2_CHEM;
	}
	
	public static List<CreaturesChemical> getC3Chemicals() {
		if (CreaturesEnums.C3_CHEM == null) {
			CreaturesEnums.C3_CHEM = CreaturesChemical
					.getCreaturesChemicals(CreaturesVersion.CREATURES1);
		}
		return CreaturesEnums.C3_CHEM;
	}
	
}

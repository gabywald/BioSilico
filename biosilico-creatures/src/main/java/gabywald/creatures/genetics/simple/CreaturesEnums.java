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
		LOBE_FLAGS				( "data.enum.creatures1.brain.lobeflags" ), 
		PERCEPTION_LINK_FLAGS	( "data.enum.creatures1.brain.perceptionlobelink" ), 
		
		GENE_BIT_FLAGS	( "data.enum.creatures1.genebitflags" ), 
		STIMULUS_FLAGS	( "data.enum.creatures1.stimulusflag" ), 
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
	
	private static List<String> LOBE_FLAGS				= null;
	private static List<String> PERCEPTION_LINK_FLAGS	= null;
	
	private static List<String> GENE_BIT_FLAGS	= null;
	private static List<String> STIMULUS_FLAGS	= null;
	private static List<String> BODY_PARTS		= null;
	private static List<String> SWITCH_ON_STAGE	= null;
	private static List<String> SPECIES			= null;
	private static List<String> PIGMENT_COLOR	= null;
	private static List<String> SVRULES			= null;
	
	private static List<CreaturesChemical> C1_CHEM = null;
	private static List<CreaturesChemical> C2_CHEM = null;
	private static List<CreaturesChemical> C3_CHEM = null;

	private static List<String> getEnumFrom(String property) {
		String propertyContent = Creatures1GenomeParser.PROPERTIES.getProperty( property );
		return Arrays.asList( propertyContent.split(", ") );
	}
	
	private static List<String> checkReturnEnum(List<String> list, CreaturesEnumsProperties prop) {
		if (list == null) { list = CreaturesEnums.getEnumFrom( prop.getProperty() ); }
		return list;
	}
	
	private static List<CreaturesChemical> checkReturnEnumChemicals(List<CreaturesChemical> list, CreaturesVersion version) {
		if (list == null) { list = CreaturesChemical.getCreaturesChemicals( version ); }
		return list;
	}
	
	public static List<String> getLobeFlags() 
		{ return CreaturesEnums.checkReturnEnum(	
			CreaturesEnums.LOBE_FLAGS, CreaturesEnumsProperties.LOBE_FLAGS); }
	
	public static List<String> getPerceptionFlags() 
		{ return CreaturesEnums.checkReturnEnum(	
			CreaturesEnums.PERCEPTION_LINK_FLAGS, CreaturesEnumsProperties.PERCEPTION_LINK_FLAGS); }
	
	public static List<String> getGeneBitFlags() 
		{ return CreaturesEnums.checkReturnEnum(	
				CreaturesEnums.GENE_BIT_FLAGS, CreaturesEnumsProperties.GENE_BIT_FLAGS); }
	
	public static List<String> getStimulusFlags() 
		{ return CreaturesEnums.checkReturnEnum(	
				CreaturesEnums.STIMULUS_FLAGS, CreaturesEnumsProperties.STIMULUS_FLAGS); }
	
	public static List<String> getBodyParts() 
		{ return CreaturesEnums.checkReturnEnum(	
				CreaturesEnums.BODY_PARTS, CreaturesEnumsProperties.BODY_PARTS); }
	
	public static List<String> getSwitchOnStages() 
		{ return CreaturesEnums.checkReturnEnum(	
				CreaturesEnums.SWITCH_ON_STAGE, CreaturesEnumsProperties.SWITCH_ON_STAGE); }
	
	public static List<String> getSpecies() 
		{ return CreaturesEnums.checkReturnEnum(	
				CreaturesEnums.SPECIES, CreaturesEnumsProperties.SPECIES); }
	
	public static List<String> getPigmentColors() 
		{ return CreaturesEnums.checkReturnEnum(	
				CreaturesEnums.PIGMENT_COLOR, CreaturesEnumsProperties.PIGMENT_COLOR); }
	
	public static List<String> getSVRules() 
		{ return CreaturesEnums.checkReturnEnum(	
				CreaturesEnums.SVRULES, CreaturesEnumsProperties.SVRULES); }
	
	public static List<CreaturesChemical> getC1Chemicals() 
		{ return CreaturesEnums.checkReturnEnumChemicals(
				CreaturesEnums.C1_CHEM, CreaturesVersion.CREATURES1); }
	
	public static List<CreaturesChemical> getC2Chemicals() 
		{ return CreaturesEnums.checkReturnEnumChemicals(
				CreaturesEnums.C2_CHEM, CreaturesVersion.CREATURES2); }
	
	public static List<CreaturesChemical> getC3Chemicals() 
		{ return CreaturesEnums.checkReturnEnumChemicals(
				CreaturesEnums.C3_CHEM, CreaturesVersion.CREATURES3); }
	
}

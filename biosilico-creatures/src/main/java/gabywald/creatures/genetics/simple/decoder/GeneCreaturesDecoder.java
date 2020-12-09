package gabywald.creatures.genetics.simple.decoder;

import java.util.ArrayList;
import java.util.List;

import gabywald.creatures.genetics.simple.CreaturesChemical;
import gabywald.creatures.genetics.simple.CreaturesEnums;
import gabywald.creatures.model.UnsignedByte;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public abstract class GeneCreaturesDecoder implements IGeneCreaturesDecoder {
	
	private List<String> acceptedTST = new ArrayList<String>();

	public GeneCreaturesDecoder(List<String> acceptedTypeSubTypes) 
		{ this.acceptedTST = acceptedTypeSubTypes; }

	protected boolean check(UnsignedByte type, UnsignedByte subtype) {
		if (this.acceptedTST.contains("*")) { return true; }
		if (this.acceptedTST.contains(type + "-" + subtype)) { return true; }
		return false;
	}
	
	protected static String convertAsString(List<UnsignedByte> parent) {
		StringBuilder sbToReturn = new StringBuilder();
		
		for (UnsignedByte elt : parent) 
			{ sbToReturn.append( (char)elt.getValue() ); }
		
		return sbToReturn.toString();
	}
	
	private static String getFrom(List<String> values, UnsignedByte ub)
		{ return (ub.getValue() < values.size()) ? values.get( ub.getValue() ) : "" + ub.getValue() ; }
	
	private static String getFromChem(List<CreaturesChemical> values, UnsignedByte ub) 
		{ return (ub.getValue() < values.size()) ? values.get( ub.getValue() ).getName() : "" + ub.getValue() ; }
	
	protected static String getLobeFlags(UnsignedByte ub) 
		{ return GeneCreaturesDecoder.getFrom(CreaturesEnums.getLobeFlags(), ub); }
	
	protected static String getStimulusFlags(UnsignedByte ub) 
		{ return GeneCreaturesDecoder.getFrom(CreaturesEnums.getStimulusFlags(), ub); }
	
	protected static String getSpecie(UnsignedByte ub) 
		{ return GeneCreaturesDecoder.getFrom(CreaturesEnums.getSpecies(), ub); }
	
	protected static String getBodyPart(UnsignedByte ub) 
		{ return GeneCreaturesDecoder.getFrom(CreaturesEnums.getBodyParts(), ub); }

	protected static String getPigmentColor(UnsignedByte ub) 
		{ return GeneCreaturesDecoder.getFrom(CreaturesEnums.getPigmentColors(), ub); }
	
	protected static String getBreed(UnsignedByte ub) 
		{ return "" + ub.getValue(); }
	
	protected static String getC1ChemicalName(UnsignedByte ub) 
		{ return GeneCreaturesDecoder.getFromChem(CreaturesEnums.getC1Chemicals(), ub); }
	
	protected static String getC2ChemicalName(UnsignedByte ub) 
		{ return GeneCreaturesDecoder.getFromChem(CreaturesEnums.getC1Chemicals(), ub); }
	
}

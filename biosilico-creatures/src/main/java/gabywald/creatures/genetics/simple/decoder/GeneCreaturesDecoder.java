package gabywald.creatures.genetics.simple.decoder;

import java.util.ArrayList;
import java.util.List;

import gabywald.creatures.genetics.simple.CreaturesEnums;
import gabywald.creatures.model.UnsignedByte;

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
	
	protected static String getSpecie(UnsignedByte ub) 
		{ return CreaturesEnums.getSpecies().get( ub.getValue() ); }
	
	protected static String getBodyPart(UnsignedByte ub) 
		{ return CreaturesEnums.getBodyParts().get( ub.getValue() ); }
	
	protected static String getPigmentColot(UnsignedByte ub) 
		{ return CreaturesEnums.getPigmentColors().get( ub.getValue() ); }
	
	protected static String getBreed(UnsignedByte ub) 
		{ return "" + ub.getValue(); }

}

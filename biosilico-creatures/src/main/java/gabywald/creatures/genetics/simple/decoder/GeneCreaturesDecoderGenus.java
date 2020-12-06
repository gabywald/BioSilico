package gabywald.creatures.genetics.simple.decoder;

import java.util.List;

import gabywald.creatures.genetics.simple.CreaturesEnums;
import gabywald.creatures.genetics.simple.ICreaturesGene;
import gabywald.creatures.model.UnsignedByte;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class GeneCreaturesDecoderGenus implements IGeneCreaturesDecoder {
	
	@Override
	public String decodeFrom(ICreaturesGene inputGene) {
		StringBuilder sbToReturn = new StringBuilder();
		
		List<UnsignedByte> header = inputGene.getHeader();
		boolean testRecognizedTypeSubType = // NOTE : Specific for Genus gene type/subtype ! 
				( (header.get(0).getValue() == 2) && (header.get(1).getValue() == 1) );
		if ( ! testRecognizedTypeSubType) { return sbToReturn.toString(); }
		
		// ***** Content Part
		List<UnsignedByte> contents = inputGene.getContents();
		String specie = CreaturesEnums.getSpecies().get( contents.get(0).getValue() );
		sbToReturn.append( "\t Specie   : [" ).append( specie ).append( "]\n" );
		String parent1 = GeneCreaturesDecoderGenus.convertAsString( contents.subList(1, 5) );
		sbToReturn.append( "\t parent1  : [" ).append( parent1 ).append( "]\n" );
		String parent2 = GeneCreaturesDecoderGenus.convertAsString( contents.subList(5, 9) );
		sbToReturn.append( "\t parent2  : [" ).append( parent2 ).append( "]\n" );
		
		inputGene.addContentSTR(sbToReturn.toString().split("\n"));
		
		return sbToReturn.toString();
	}
	
	private static String convertAsString(List<UnsignedByte> parent) {
		StringBuilder sbToReturn = new StringBuilder();
		
		for (UnsignedByte elt : parent) 
			{ sbToReturn.append( (char)elt.getValue() ); }
		
		return sbToReturn.toString();
	}

}

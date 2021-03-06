package gabywald.creatures.genetics.simple.decoder;

import java.util.Arrays;
import java.util.List;

import gabywald.creatures.genetics.simple.ICreaturesGene;
import gabywald.creatures.model.UnsignedByte;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class GeneCreaturesDecoderAppearance extends GeneCreaturesDecoder {
	
	public GeneCreaturesDecoderAppearance() 
		{ super(Arrays.asList("2-2")); }

	@Override
	public String decodeFrom(ICreaturesGene inputGene) {
		StringBuilder sbToReturn = new StringBuilder();
		
		List<UnsignedByte> header = inputGene.getHeader();
		if ( ! this.check(header.get(0), header.get(1))) { return sbToReturn.toString(); }
		
		// ***** Content Part
		List<UnsignedByte> contents = inputGene.getContents();
		sbToReturn.append( "\t Body Part: [" ).append( GeneCreaturesDecoder.getBodyPart(contents.get(0)) ).append( "]\n" );
		sbToReturn.append( "\t Breed    : [" ).append( GeneCreaturesDecoder.getBreed(contents.get(1)) ).append( "]\n" );
		if (contents.size() >= 3) // for C2 and more !!
			{ sbToReturn.append( "\t Specie   : [" ).append( GeneCreaturesDecoder.getSpecie(contents.get(2)) ).append( "]\n" ); }
		
		inputGene.addContentSTR(sbToReturn.toString().split("\n"));
		
		return sbToReturn.toString();
	}
	
}

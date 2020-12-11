package gabywald.creatures.genetics.simple.decoder;

import java.util.Arrays;
import java.util.List;

import gabywald.creatures.genetics.simple.ICreaturesGene;
import gabywald.creatures.model.UnsignedByte;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class GeneCreaturesDecoderGenus extends GeneCreaturesDecoder {
	
	public GeneCreaturesDecoderGenus() 
		{ super(Arrays.asList("2-1")); }

	@Override
	public String decodeFrom(ICreaturesGene inputGene) {
		StringBuilder sbToReturn = new StringBuilder();
		
		List<UnsignedByte> header = inputGene.getHeader();
		if ( ! this.check(header.get(0), header.get(1)) ) { return sbToReturn.toString(); }
		
		// ***** Content Part
		List<UnsignedByte> contents = inputGene.getContents();
		sbToReturn.append( "\t Specie   : [" ).append( GeneCreaturesDecoder.getSpecie(contents.get(0)) ).append( "]\n" );
		String parent1 = GeneCreaturesDecoder.convertAsString( contents.subList(1, 5) );
		sbToReturn.append( "\t parent1  : [" ).append( parent1 ).append( "]\n" );
		String parent2 = GeneCreaturesDecoder.convertAsString( contents.subList(5, 9) );
		sbToReturn.append( "\t parent2  : [" ).append( parent2 ).append( "]\n" );
		
		inputGene.addContentSTR(sbToReturn.toString().split("\n"));
		
		return sbToReturn.toString();
	}
	
}

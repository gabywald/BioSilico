package gabywald.creatures.genetics.simple.decoder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import gabywald.creatures.genetics.simple.ICreaturesGene;
import gabywald.creatures.model.UnsignedByte;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class GeneCreaturesDecoderInstinct extends GeneCreaturesDecoder {
	
	public GeneCreaturesDecoderInstinct() 
		{ super(Arrays.asList("2-5")); }

	@Override
	public String decodeFrom(ICreaturesGene inputGene) {
		StringBuilder sbToReturn = new StringBuilder();
		
		List<UnsignedByte> header = inputGene.getHeader();
		if ( ! this.check(header.get(0), header.get(1))) { return sbToReturn.toString(); }
		
		// ***** Content Part
		List<UnsignedByte> contents = inputGene.getContents();
		IntStream.iterate(0, i -> i + 2).limit(3).forEach( i -> {
			int numberIndex = ( (i / 2) + 1 );
			sbToReturn.append( "\t Lobe Number").append( numberIndex ).append(": [" ).append( contents.get( i ) ).append( "]\n" );
			sbToReturn.append( "\t Cell Number").append( numberIndex ).append(": [" ).append( contents.get( i + 1) ).append( "]\n" );
		});
		sbToReturn.append( "\t Chemical: [" ).append( GeneCreaturesDecoder.getC1ChemicalName( contents.get(7) ) ).append( "]\n" );
		sbToReturn.append( "\t Amount--: [" ).append( contents.get(8) ).append( "]\n" );
		
		inputGene.addContentSTR(sbToReturn.toString().split("\n"));
		
		return sbToReturn.toString();
	}
	
}

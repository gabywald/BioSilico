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
public class GeneCreaturesDecoderGait extends GeneCreaturesDecoder {
	
	public GeneCreaturesDecoderGait() 
		{ super(Arrays.asList("2-4")); }

	@Override
	public String decodeFrom(ICreaturesGene inputGene) {
		StringBuilder sbToReturn = new StringBuilder();
		
		List<UnsignedByte> header = inputGene.getHeader();
		if ( ! this.check(header.get(0), header.get(1))) { return sbToReturn.toString(); }
		
		// ***** Content Part
		List<UnsignedByte> contents = inputGene.getContents();
		sbToReturn.append( "\t Gait Numb.: [" ).append( contents.get(0) ).append( "]\n" );
		IntStream.iterate(1, i -> i + 1).limit(8).forEach( i -> {
			sbToReturn.append( "\t Pose").append( i ).append(" Number: [" ).append( contents.get( i ) ).append( "]\n" );
		});
		
		inputGene.addContentSTR(sbToReturn.toString().split("\n"));
		
		return sbToReturn.toString();
	}
	
}

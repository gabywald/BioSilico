package gabywald.creatures.genetics.simple.decoder;

import java.util.Arrays;
import java.util.List;

import gabywald.creatures.genetics.simple.ICreaturesGene;
import gabywald.creatures.model.UnsignedByte;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class GeneCreaturesDecoderPose extends GeneCreaturesDecoder {
	
	public GeneCreaturesDecoderPose() 
		{ super(Arrays.asList("2-3")); }

	@Override
	public String decodeFrom(ICreaturesGene inputGene) {
		StringBuilder sbToReturn = new StringBuilder();
		
		List<UnsignedByte> header = inputGene.getHeader();
		if ( ! this.check(header.get(0), header.get(1))) { return sbToReturn.toString(); }
		
		// ***** Content Part
		List<UnsignedByte> contents = inputGene.getContents();
		sbToReturn.append( "\t Pose Numb.: [" ).append( contents.get(0) ).append( "]\n" );
		String poseSTR = GeneCreaturesDecoder.convertAsString( contents.subList(1, contents.size()) );
		sbToReturn.append( "\t Pose STR--: [" ).append( poseSTR ).append( "]\n" );
		
		inputGene.addContentSTR(sbToReturn.toString().split("\n"));
		
		return sbToReturn.toString();
	}
	
}

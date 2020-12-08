package gabywald.creatures.genetics.simple.decoder;

import java.util.Arrays;
import java.util.List;

import gabywald.creatures.genetics.simple.ICreaturesGene;
import gabywald.creatures.model.UnsignedByte;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class GeneCreaturesDecoderInitialConcentration extends GeneCreaturesDecoder {
	
	public GeneCreaturesDecoderInitialConcentration() 
		{ super(Arrays.asList("1-4")); }

	@Override
	public String decodeFrom(ICreaturesGene inputGene) {
		StringBuilder sbToReturn = new StringBuilder();
		
		List<UnsignedByte> header = inputGene.getHeader();
		if ( ! this.check(header.get(0), header.get(1))) { return sbToReturn.toString(); }
		
		// ***** Content Part
		List<UnsignedByte> contents = inputGene.getContents();
		sbToReturn.append( "\t Chemical: [" ).append( GeneCreaturesDecoder.getC1ChemicalName( contents.get(0) ) ).append( "]\n" );
		sbToReturn.append( "\t Amount--: [" ).append( contents.get(1) ).append( "]\n" );
		
		inputGene.addContentSTR(sbToReturn.toString().split("\n"));
		
		return sbToReturn.toString();
	}
	
}

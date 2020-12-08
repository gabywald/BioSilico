package gabywald.creatures.genetics.simple.decoder;

import java.util.Arrays;
import java.util.List;

import gabywald.creatures.genetics.simple.ICreaturesGene;
import gabywald.creatures.model.UnsignedByte;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class GeneCreaturesDecoderReceptorEmitter extends GeneCreaturesDecoder {
	
	public GeneCreaturesDecoderReceptorEmitter() 
		{ super(Arrays.asList("1-0", "1-1")); }

	@Override
	public String decodeFrom(ICreaturesGene inputGene) {
		StringBuilder sbToReturn = new StringBuilder();
		
		List<UnsignedByte> header = inputGene.getHeader();
		if ( ! this.check(header.get(0), header.get(1))) { return sbToReturn.toString(); }
		
		// ***** Content Part
		List<UnsignedByte> contents = inputGene.getContents();
		sbToReturn.append( "\t Organ----: [" ).append( contents.get(0) ).append( "]\n" );
		sbToReturn.append( "\t Tissue---: [" ).append( contents.get(1) ).append( "]\n" );
		sbToReturn.append( "\t Locus----: [" ).append( contents.get(2) ).append( "]\n" );
		sbToReturn.append( "\t Chemical-: [" ).append( GeneCreaturesDecoder.getC1ChemicalName( contents.get(3) ) ).append( "]\n" );
		sbToReturn.append( "\t Threshold: [" ).append( contents.get(4) ).append( "]\n" );
		sbToReturn.append( "\t Nominal--: [" ).append( contents.get(5) ).append( "]\n" );
		sbToReturn.append( "\t Gain-----: [" ).append( contents.get(6) ).append( "]\n" );
		sbToReturn.append( "\t Flags----: [" ).append( contents.get(7) ).append( "]\n" );
		
		inputGene.addContentSTR(sbToReturn.toString().split("\n"));
		
		return sbToReturn.toString();
	}
	
}

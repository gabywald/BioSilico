package gabywald.creatures.genetics.simple.decoder;

import java.util.Arrays;
import java.util.List;

import gabywald.creatures.genetics.simple.ICreaturesGene;
import gabywald.creatures.model.UnsignedByte;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class GeneCreaturesDecoderChemicalReaction extends GeneCreaturesDecoder {
	
	public GeneCreaturesDecoderChemicalReaction() 
		{ super(Arrays.asList("1-2")); }

	@Override
	public String decodeFrom(ICreaturesGene inputGene) {
		StringBuilder sbToReturn = new StringBuilder();
		
		List<UnsignedByte> header = inputGene.getHeader();
		if ( ! this.check(header.get(0), header.get(1)) ) { return sbToReturn.toString(); }
		
		// ***** Content Part
		List<UnsignedByte> contents = inputGene.getContents();
		char[] alphas	= new char[]{ 'A' , 'B' , 'C' , 'D' };
		int[] nums		= new int[]{ 0 , 2 , 4 , 6 };
		for (int i = 0 ; i < alphas.length ; i++) {
			char currentChar	= alphas[i];
			int currentInt		= nums[i];
			sbToReturn.append( "\t Amount ").append( currentChar ).append(" : [" ).append( contents.get( currentInt ) ).append( "]\n" );
			sbToReturn.append( "\t Chemical").append( currentChar ).append(": [" ).append( GeneCreaturesDecoder.getC1ChemicalName( contents.get( currentInt + 1) ) ).append( "]\n" );
		}
		
		sbToReturn.append( "\t ReactRate: [" ).append( contents.get(8) ).append( "]\n" );
			
		inputGene.addContentSTR(sbToReturn.toString().split("\n"));
		
		return sbToReturn.toString();
	}
	
}

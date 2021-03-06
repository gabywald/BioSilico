package gabywald.creatures.genetics.simple.decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import gabywald.creatures.genetics.simple.CreaturesEnums;
import gabywald.creatures.genetics.simple.ICreaturesGene;
import gabywald.creatures.model.UnsignedByte;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class GeneCreaturesDecoderHeader extends GeneCreaturesDecoder {
	
	public GeneCreaturesDecoderHeader() 
		// NOTE : Any type-subtype of gene !!
		{ super(Arrays.asList("*")); }
	
	@Override
	public String decodeFrom(ICreaturesGene inputGene) {
		StringBuilder sbToReturn = new StringBuilder();
		
		List<UnsignedByte> header = inputGene.getHeader();
		if ( ! this.check(header.get(0), header.get(1)) ) { return sbToReturn.toString(); }
		
		// ***** Header Part
		sbToReturn.append( "\t Gene Type: [" ).append(header.get(0)).append("]\n");
		sbToReturn.append( "\t Gene SubT: [" ).append(header.get(1)).append("]\n");
		sbToReturn.append( "\t Sequ Numb: [" ).append(header.get(2)).append("]\n");
		sbToReturn.append( "\t Dupl Numb: [" ).append(header.get(3)).append("]\n");
		// Swicth On Stage explicitation
		String strSOS = CreaturesEnums.getSwitchOnStages().get( header.get(4).getValue() );
		sbToReturn.append( "\t Switch ON: [" ).append( strSOS ).append("]\n");
		// Flags analysis (bit by bit). SEE NOTE BELOW !
		List<String> flags = new ArrayList<String>();
		String binaryFlags = Integer.toBinaryString(header.get(5).getValue());
		IntStream.range(0, CreaturesEnums.getGeneBitFlags().size()).forEach( i -> {
			if ( (binaryFlags.length() > i) && (binaryFlags.charAt( i ) == '1') ) 
				{ flags.add( CreaturesEnums.getGeneBitFlags().get( i ) ); }
		});
		sbToReturn.append( "\t HEAD FLAGS: ")
			.append("[" ).append( header.get(5).getValue() ).append("]")
			.append("[" ).append( Integer.toBinaryString(header.get(5).getValue()) ).append("]")
			.append( flags.toString() ).append("\n");
		
		if (header.size() > 6) // for C2 and more !!
			{ sbToReturn.append( "\t MutationR: [" ).append(header.get(6)).append("]\n"); }
		if (header.size() > 7) // for C3 !!
			{ sbToReturn.append( "\t ExpressVa: [" ).append(header.get(7)).append("]\n"); }
		
		// XXX NOTE : make / apply Visitor Design Pattern (successive applies) on condition ? (key of type-subt)
		// Add to STR content to give a better translation / comprehensive and human view of the gene !!
		
		// ***** Content Part
		// NOTE : see other decoders
		
		inputGene.addContentSTR(sbToReturn.toString().split("\n"));
		
		return sbToReturn.toString();
	}
	
	/* BIT Flags notes : 
	bit # 7 6 5 4 3 2 1 0
	 00 - 0 0 0 0 0 0 0 0
	 01 - 0 0 0 0 0 0 0 1
	 03 - 0 0 0 0 0 0 1 1
	 07 - 0 0 0 0 0 1 1 1
	 0F - 0 0 0 0 1 1 1 1
	 11 - 0 0 0 1 0 0 0 1
	 13 - 0 0 0 1 0 0 1 1
	 17 - 0 0 0 1 0 1 1 1
	 27 - 0 0 1 0 0 1 1 1
	 2F - 0 0 1 0 1 1 1 1
	 37 - 0 0 1 1 0 1 1 1
	
	Bit 0 - mutability
	Bit 1 - duplication
	Bit 2 - deletion
	Bit 3 - male-specific
	Bit 4 - female-specific
	Bit 5 - dormancy
	 */

}

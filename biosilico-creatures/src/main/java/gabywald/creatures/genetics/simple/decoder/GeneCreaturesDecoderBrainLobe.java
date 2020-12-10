package gabywald.creatures.genetics.simple.decoder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import gabywald.creatures.genetics.simple.CreaturesEnums;
import gabywald.creatures.genetics.simple.CreaturesVersion;
import gabywald.creatures.genetics.simple.ICreaturesGene;
import gabywald.creatures.model.UnsignedByte;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class GeneCreaturesDecoderBrainLobe extends GeneCreaturesDecoder {
	
	public GeneCreaturesDecoderBrainLobe() 
		{ super(Arrays.asList("0-0")); }

	@Override
	public String decodeFrom(ICreaturesGene inputGene) {
		StringBuilder sbToReturn = new StringBuilder();
		
		List<UnsignedByte> header = inputGene.getHeader();
		if ( ! this.check(header.get(0), header.get(1)) ) { return sbToReturn.toString(); }
		
		// ***** Content Part
		List<UnsignedByte> contents = inputGene.getContents();
		sbToReturn.append( "\t" );
		sbToReturn.append( "Lobe PosX: [" ).append( contents.get(0) ).append( "]  " );
		sbToReturn.append( "Lobe PosY: [" ).append( contents.get(1) ).append( "]  " );
		sbToReturn.append( "LobeWidth: [" ).append( contents.get(2) ).append( "]  " );
		sbToReturn.append( "LobeHeigt: [" ).append( contents.get(4) ).append( "]\n" );
		sbToReturn.append( "\t" );
		sbToReturn.append( "Perc Flag: [" ).append( contents.get(5) ).append( "]  " );
		sbToReturn.append( "Threshold: [" ).append( contents.get(6) ).append( "]  " );
		sbToReturn.append( "Leakage--: [" ).append( contents.get(7) ).append( "]  " );
		sbToReturn.append( "RestState: [" ).append( contents.get(8) ).append( "]\n" );
		sbToReturn.append( "\t" );
		sbToReturn.append( "InputGain: [" ).append( contents.get(9) ).append( "]\n" );
		
		// SVRules
		// NOTE : see also below the other SVR, parse as is ?
		int nextCode = GeneCreaturesDecoderBrainLobe.translateSVRules
				(	"SVR------", sbToReturn, 10, contents, CreaturesVersion.CREATURES1);
		
		String lobeFlag = GeneCreaturesDecoder.getLobeFlags( contents.get( nextCode ) );
		sbToReturn.append( "\tLobe Flag: [" ).append( lobeFlag ).append( "]\n" );
		
		IntStream.rangeClosed(0, 1).forEach( i -> {
			int index = (i == 0) ? nextCode + 1 : 41;
			sbToReturn.append( "\t Dendrite").append( i ).append(": \n" );
			sbToReturn.append( "\t\t" );
			sbToReturn.append( "Min Dendrites: [" ).append( contents.get( index ) ).append( "]  " );
			sbToReturn.append( "Max Dendrites: [" ).append( contents.get( index + 1 ) ).append( "]\n" );
			sbToReturn.append( "\t\t" );
			sbToReturn.append( "Spread-------: [" ).append( contents.get( index + 2 ) ).append( "]  " );
			sbToReturn.append( "Fanout-------: [" ).append( contents.get( index + 3 ) ).append( "]\n" );
			sbToReturn.append( "\t\t" );
			sbToReturn.append( "Min. LTW ----: [" ).append( contents.get( index + 4 ) ).append( "]  " );
			sbToReturn.append( "Max. LTW ----: [" ).append( contents.get( index + 5 ) ).append( "]\n" );
			sbToReturn.append( "\t\t" );
			sbToReturn.append( "Min. Init Str: [" ).append( contents.get( index + 6 ) ).append( "]  " );
			sbToReturn.append( "Max. Init Str: [" ).append( contents.get( index + 7 ) ).append( "]\n" );
			sbToReturn.append( "\t\t" );
			sbToReturn.append( "MigrationFlag: [" ).append( contents.get( index + 8 ) ).append( "]  " );
			sbToReturn.append( "Relaxation --: [" ).append( contents.get( index + 9 ) ).append( "]  " );
			sbToReturn.append( "RelaxationSTW: [" ).append( contents.get( index + 10 ) ).append( "]\n" );
			sbToReturn.append( "\t\t" );
			sbToReturn.append( "LTW gain rate: [" ).append( contents.get( index + 11 ) ).append( "]  " );
			sbToReturn.append( "Strength gain: [" ).append( contents.get( index + 12 ) ).append( "]\n" );
			int next = GeneCreaturesDecoderBrainLobe.translateSVRules
					("Str. gain SVR", sbToReturn, index + 13, contents, CreaturesVersion.CREATURES1);
			// sbToReturn.append( "\tStr. gain SVR: [" ).append( contents.get( index + 13 ) ).append( "]\n" );
			sbToReturn.append( "\t\tStr. loss ---: [" ).append( contents.get( next ) ).append( "]\n" );
			next = GeneCreaturesDecoderBrainLobe.translateSVRules
					("[" + (next+1) + "]" + "Str. loss SVR", sbToReturn, next + 1, contents, CreaturesVersion.CREATURES1);
			next = GeneCreaturesDecoderBrainLobe.translateSVRules
					("[" + next + "]" + "Suscepti. SVR", sbToReturn, next, contents, CreaturesVersion.CREATURES1);
			next = GeneCreaturesDecoderBrainLobe.translateSVRules
					("[" + next + "]" + "Relaxati. SVR", sbToReturn, next, contents, CreaturesVersion.CREATURES1);
			next = GeneCreaturesDecoderBrainLobe.translateSVRules
					("[" + next + "]" + "BackProp- SVR", sbToReturn, next, contents, CreaturesVersion.CREATURES1);
			next = GeneCreaturesDecoderBrainLobe.translateSVRules
					("[" + next + "]" + "ForwProp- SVR", sbToReturn, next, contents, CreaturesVersion.CREATURES1);
		});
		
		sbToReturn.append( "\t BRAIN LOBE SIZE: [" ).append( contents.size() ).append( "]\n" );
		
		inputGene.addContentSTR(sbToReturn.toString().split("\n"));
		
		return sbToReturn.toString();
	}
	
	/**
	 * Parse, decode and add SVRules to the StringBuilder. 
	 * @param sbToReturn (StringBuilder)
	 * @param startPosition (int)
	 * @param contents (List&lt;UnsignedByte&gt;)
	 * @return (int)Next position to continue on contents. 
	 */
	private static int translateSVRules(String nameSVR, StringBuilder sbToReturn, 
										int startPosition, List<UnsignedByte> contents, 
										CreaturesVersion cv) {
		List<String> svrules = CreaturesEnums.getSVRules();
		// TODO precise if has to be re-used for 'SVR' below ?!
		sbToReturn.append( "\t").append( nameSVR ).append(": [" );
		StringBuilder sbSVR = new StringBuilder();
		int indexStateVariableRule = startPosition; // 10;
		int numSVR = (cv == CreaturesVersion.CREATURES1) ? 8 : (cv == CreaturesVersion.CREATURES2) ? 12 : (cv == CreaturesVersion.CREATURES3) ? 16 : 1 ;
		IntStream.range(indexStateVariableRule, indexStateVariableRule + numSVR).forEach( i -> { 
			// Logger.printlnLog(LoggerLevel.LL_INFO, i + " -- " + contents.get( i ).getValue() );
			sbSVR	.append( "[" )
					.append( (contents.get( i ).getValue() < svrules.size()) 
							? svrules.get( contents.get( i ).getValue() )
							: "*" + contents.get( i ).getValue() + "*" )
					.append( "] => " );
		} );
		sbToReturn.append( sbSVR.substring(0, sbSVR.length() - 4)).append( "]\n" );
		
		return startPosition + numSVR + 1;
	}
	
//	private static int concatenateSVRules(	String nameSVR, StringBuilder sbToReturn, 
//											int startPosition, List<UnsignedByte> contents, 
//											CreaturesVersion cv) {
//		sbToReturn.append( "\t").append( nameSVR ).append(": [" );
//		StringBuilder sbSVR = new StringBuilder();
//		int indexStateVariableRule = startPosition; // 10;
//		int numSVR = (cv == CreaturesVersion.CREATURES1) ? 8 : (cv == CreaturesVersion.CREATURES2) ? 12 : (cv == CreaturesVersion.CREATURES3) ? 16 : 1 ;
//		IntStream.rangeClosed(indexStateVariableRule, indexStateVariableRule + numSVR).forEach( i -> { 
//			// Logger.printlnLog(LoggerLevel.LL_INFO, i + " -- " + contents.get( i ).getValue() );
//				sbSVR.append( "[" )
//					.append( contents.get( i ).getValue() )
//					.append( "] => " ); 
//		} );
//		sbToReturn.append( sbSVR.substring(0, sbSVR.length() - 4)).append( "]\n" );
//		
//		return startPosition + numSVR + 1;
//	}
	
}

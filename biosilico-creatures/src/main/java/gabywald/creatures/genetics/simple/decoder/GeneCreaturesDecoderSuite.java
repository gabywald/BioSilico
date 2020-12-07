package gabywald.creatures.genetics.simple.decoder;

import java.util.ArrayList;
import java.util.List;

public class GeneCreaturesDecoderSuite {

	public static List<IGeneCreaturesDecoder> getSuite() {
		List<IGeneCreaturesDecoder> toReturn = new ArrayList<IGeneCreaturesDecoder>();
		toReturn.add( new GeneCreaturesDecoderHeader() );
		toReturn.add( new GeneCreaturesDecoderGenus() );
		toReturn.add( new GeneCreaturesDecoderAppearance() );
		toReturn.add( new GeneCreaturesDecoderPigment() );
		toReturn.add( new GeneCreaturesDecoderPose() );
		// TODO continue the 'suite' with other decoders / visitors !!
		
		return toReturn;
	}
}

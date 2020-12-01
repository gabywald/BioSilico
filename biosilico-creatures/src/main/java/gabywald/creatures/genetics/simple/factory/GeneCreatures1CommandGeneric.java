package gabywald.creatures.genetics.simple.factory;

import gabywald.creatures.genetics.simple.GeneCreatures1;
import gabywald.creatures.genetics.simple.GeneTypeSubType;
import gabywald.creatures.model.UnsignedByte;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class GeneCreatures1CommandGeneric implements GeneCreatures1Command {
	
	private String key;
	
	GeneCreatures1CommandGeneric(String key) {
		this.key = key;
	}

	@Override
	public GeneCreatures1 generateFrom(String input) {
		GeneTypeSubType gtst = GeneTypeSubType.getGeneTypeSubType( this.key );
		
		UnsignedByte[] header = UnsignedByte.headerCutterBytes( input.substring(0, 7) );

		GeneCreatures1 gc1ToReturn = new GeneCreatures1(gtst.getName(), header, gtst.getAttemptedLength());
		
		gc1ToReturn.addContents( UnsignedByte.headerCutterBytes( input.substring(7) ) );
		
		return gc1ToReturn;
	}

}

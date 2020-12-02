package gabywald.creatures.genetics.simple.factory;

import gabywald.creatures.genetics.simple.Creatures1Gene;
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
	public Creatures1Gene generateFrom(String input) {
		GeneTypeSubType gtst = GeneTypeSubType.getGeneTypeSubType( this.key );
		
		// Header data : 6 first elements !
		UnsignedByte[] header = UnsignedByte.headerCutterBytes( input.substring(0, 6) );

		Creatures1Gene gc1ToReturn = new Creatures1Gene(gtst.getName(), header, gtst.getAttemptedLength());
		
		// Rest of data : from the 6th element !
		gc1ToReturn.addContents( UnsignedByte.headerCutterBytes( input.substring(6) ) );
		
		return gc1ToReturn;
	}

}

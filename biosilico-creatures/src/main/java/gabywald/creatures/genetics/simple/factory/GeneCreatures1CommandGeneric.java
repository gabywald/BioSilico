package gabywald.creatures.genetics.simple.factory;

import java.util.List;

import gabywald.creatures.genetics.simple.Creatures1Gene;
import gabywald.creatures.genetics.simple.GeneTypeSubType;
import gabywald.creatures.model.UnsignedByte;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class GeneCreatures1CommandGeneric implements IGeneCreatures1Command {
	
	private String key;
	
	GeneCreatures1CommandGeneric(String key) {
		this.key = key;
	}

	@Override
	public Creatures1Gene generateFrom(String input) {
		GeneTypeSubType gtst = GeneTypeSubType.getGeneTypeSubType( this.key );
		
		// Header data : 6 first elements !
		List<UnsignedByte> header = UnsignedByte.headerCutterBytesAsList( input.substring(0, 6) );

		Creatures1Gene gc1ToReturn = new Creatures1Gene(gtst, header);
		
		// Rest of data : from the 6th element !
		gc1ToReturn.addContents( UnsignedByte.headerCutterBytes( input.substring(6) ) );
		
		return gc1ToReturn;
	}

}

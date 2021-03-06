package gabywald.creatures.genetics.simple.factory;

import java.util.List;

import gabywald.creatures.genetics.simple.Creatures1Gene;
import gabywald.creatures.genetics.simple.GeneTypeSubType;
import gabywald.creatures.genetics.simple.ICreaturesGene;
import gabywald.creatures.model.UnsignedByte;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class GeneCreaturesStrategyGeneric implements IGeneCreaturesStrategy {
	
	private String key;
	
	GeneCreaturesStrategyGeneric(String key) {
		this.key = key;
	}

	@Override
	public ICreaturesGene generateFrom(String input) {
		GeneTypeSubType gtst = GeneTypeSubType.getGeneTypeSubType( this.key );
		
		// Header data : 6 first elements for C1, 7 for C2 and 8 for C3 !
		// TODO load from cinfiguration / properties in GeneTypeSubType(-1, -1) !!
		int headerlimit = 6; 
		List<UnsignedByte> header = UnsignedByte.headerCutterBytesAsList( input.substring(0, headerlimit) );

		ICreaturesGene gc1ToReturn = new Creatures1Gene(gtst, header);
		
		// Rest of data : from the 6th element !
		gc1ToReturn.addContents( UnsignedByte.headerCutterBytes( input.substring( headerlimit ) ) );
		
		return gc1ToReturn;
	}

}

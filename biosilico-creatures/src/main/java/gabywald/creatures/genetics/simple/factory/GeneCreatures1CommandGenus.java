package gabywald.creatures.genetics.simple.factory;

import gabywald.creatures.genetics.simple.GeneCreatures1;
import gabywald.creatures.genetics.simple.GeneTypeSubType;
import gabywald.creatures.model.UnsignedByte;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class GeneCreatures1CommandGenus implements GeneCreatures1Command {
	
	private String key;
	
	GeneCreatures1CommandGenus() {
		this.key = "2-1";
	}

	@Override
	public GeneCreatures1 generateFrom(String input) {
		GeneTypeSubType gtst = GeneTypeSubType.getGeneTypeSubType( this.key );
		
		UnsignedByte[] header = UnsignedByte.headerCutterBytes( input.substring(0, 6) );

		GeneCreatures1 gc1ToReturn = new GeneCreatures1(gtst.getName(), header, gtst.getAttemptedLength());
		
		String parent1 = input.substring( 7, 11);
		String parent2 = input.substring(11, 15);
		
		gc1ToReturn.addContentSTR( parent1 );
		gc1ToReturn.addContentSTR( parent2 );
		
		return gc1ToReturn;
	}

}

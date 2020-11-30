package gabywald.creatures.genetics.main.genesfactory;

import gabywald.creatures.genetics.main.GeneCreatures1;
import gabywald.creatures.genetics.main.GeneTypeSubType;

public class GeneCreatures1CommandGeneric implements GeneCreatures1Command {
	
	private String key;
	
	GeneCreatures1CommandGeneric(String key) {
		this.key = key;
	}

	@Override
	public GeneCreatures1 generateFrom(String input) {
		GeneTypeSubType gtst = GeneTypeSubType.getGeneTypeSubType( this.key );
		// TODO Auto-generated method stub
		GeneCreatures1 gc1ToReturn = new GeneCreatures1(gtst.getName(), input.substring(0, 7).toCharArray(), gtst.getAttemptedLength());
		
		
		return gc1ToReturn;
	}

}

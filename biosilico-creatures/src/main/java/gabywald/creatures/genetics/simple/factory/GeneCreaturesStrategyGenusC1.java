package gabywald.creatures.genetics.simple.factory;

import java.util.List;

import gabywald.creatures.genetics.simple.Creatures1Gene;
import gabywald.creatures.genetics.simple.GeneTypeSubType;
import gabywald.creatures.genetics.simple.ICreaturesGene;
import gabywald.creatures.model.UnsignedByte;

/**
 * 
 * @author Gabriel Chandesris (2020)
 * @deprecated Do not use ; will be replaced by a 'translator' (DP Visitor)
 */
public class GeneCreaturesStrategyGenusC1 implements IGeneCreaturesStrategy {
	
	private String key;
	
	GeneCreaturesStrategyGenusC1() {
		this.key = "2-1";
	}

	@Override
	public ICreaturesGene generateFrom(String input) {
		GeneTypeSubType gtst = GeneTypeSubType.getGeneTypeSubType( this.key );
		
		List<UnsignedByte> header = UnsignedByte.headerCutterBytesAsList( input.substring(0, 6) );

		ICreaturesGene gc1ToReturn = new Creatures1Gene(gtst, header);
		
		String parent1 = input.substring( 7, 11);
		String parent2 = input.substring(11, 15);
		
		gc1ToReturn.addContentSTR( parent1 );
		gc1ToReturn.addContentSTR( parent2 );
		
		return gc1ToReturn;
	}

}

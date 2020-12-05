package gabywald.creatures.genetics.simple.factory;

import gabywald.creatures.genetics.simple.ICreaturesGene;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public interface IGeneCreaturesStrategy {
	public ICreaturesGene generateFrom(String input);
}

package gabywald.creatures.genetics.simple.decoder;

import gabywald.creatures.genetics.simple.ICreaturesGene;

/**
 * 
 * NOTE to apply a "Visitor Design Pattern". 
 * @author Gabriel Chandesris (2020)
 */
public interface IGeneCreaturesDecoder {
	public String decodeFrom(ICreaturesGene inputGene);
}

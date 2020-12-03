package gabywald.creatures.genetics.simple;

import gabywald.creatures.model.UnsignedByte;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public interface ICreaturesGene {
	
	public GeneTypeSubType getType();
	
	public UnsignedByte[] getHeader();
	
	public String printInline();
	
	public String toString();
}

package gabywald.creatures.genetics;

import gabywald.creatures.model.UnsignedByte;

import java.util.List;

/**
 * Tract define connections between lobes and detail the number of dendrites and the style in which they are attached and migrate.
 * <br><i>C3-specific</i> 
 * @author Gabriel Chandesris (2013)
 */
public class BrainTractGene extends CreatureGene {
	
	public BrainTractGene(List<UnsignedByte> datas) throws CreatureGeneException 
		{ super(0, 2, datas, 5); /* size expected is 5 ! */ }
	
	// TODO
}

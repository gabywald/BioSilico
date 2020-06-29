package gabywald.creatures.genetics;

import java.util.List;

import gabywald.creatures.model.UnsignedByte;

/**
 * These alter the colours specified by the pigments. They are used to again, provide some way of varying appearance.
 * @author Gabriel Chandesris (2013)
 * @see PigmentGene
 * @since Creatures 2
 */
public class PigmentBleedingGene extends CreatureGene {
	/**
	 * Old variant of the constructor. 
	 * @param rotate (int)
	 * @param swap (int)
	 * @deprecated Use {@link PigmentBleedingGene#PigmentBleedingGene(List)} instead !
	 */
	private PigmentBleedingGene(int rotate, int swap) {
		super(2, 7);
		this.data.add(new UnsignedByte(rotate));
		this.data.add(new UnsignedByte(swap));
	}
	
	public PigmentBleedingGene(List<UnsignedByte> datas) throws CreatureGeneException 
		{ super(2, 7, datas, 2); /* size expected is 2 ! */ }
	
	public int getRotation()	{ return this.data.get(0).getValue(); }
	public int getSwap()		{ return this.data.get(1).getValue(); }
}

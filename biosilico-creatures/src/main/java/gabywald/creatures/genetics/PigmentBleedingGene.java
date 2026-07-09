package gabywald.creatures.genetics;

import gabywald.creatures.model.UnsignedByte;

/**
 * These alter the colours specified by the pigments. They are used to again, provide some way of varying appearance.
 * @author Gabriel Chandesris (2013, 2026)
 * @see PigmentGene
 * @since Creatures 2
 */
public class PigmentBleedingGene extends CreatureGene {
	/**
	 * Old variant of the constructor. 
	 * @param rotate (UnsignedByte)
	 * @param swap (UnsignedByte)
	 */
	public PigmentBleedingGene(UnsignedByte rotate, UnsignedByte swap) {
		super(2, 7);
		this.data.add(rotate);
		this.data.add(swap);
	}
	
	public int getRotation()	{ return this.data.get(0).getValue(); }
	public int getSwap()		{ return this.data.get(1).getValue(); }
}

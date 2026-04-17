package gabywald.creatures.genetics;

import gabywald.creatures.model.UnsignedByte;

/**
 * These simple genes are used to give the creature a variation of colouring from the base colour of the variant sprites.
 * @author Gabriel Chandesris (2013, 2026)
 */
public class PigmentGene extends CreatureGene {
	/**
	 * Old variant of the constructor. 
	 * @param color (UnsignedByte)
	 * @param amount (UnsignedByte)
	 */
	public PigmentGene(UnsignedByte color, UnsignedByte amount) {
		super(2, 6);
		this.data.add(color);
		this.data.add(amount);
	}
	
	public int getColour()	{ return this.data.get(0).getValue(); }
	public int getAmount()	{ return this.data.get(1).getValue(); }
}

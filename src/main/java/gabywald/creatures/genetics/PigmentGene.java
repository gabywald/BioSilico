package gabywald.creatures.genetics;

import java.util.List;

import gabywald.creatures.model.UnsignedByte;

/**
 * These simple genes are used to give the creature a variation of colouring from the base colour of the variant sprites.
 * @author Gabriel Chandesris (2013)
 */
public class PigmentGene extends CreatureGene {
	/**
	 * Old variant of the constructor. 
	 * @param color (int)
	 * @param amount (int)
	 * @deprecated Use {@link PigmentGene#PigmentGene(List)} instead !
	 */
	private PigmentGene(int color, int amount) {
		super(2, 6);
		this.data.add(new UnsignedByte(color));
		this.data.add(new UnsignedByte(amount));
	}
	
	public PigmentGene(List<UnsignedByte> datas) throws CreatureGeneException 
		{ super(2, 6, datas, 2); /* size expected is 2 ! */ }
	
	public int getColour()	{ return this.data.get(0).getValue(); }
	public int getAmount()	{ return this.data.get(1).getValue(); }
}

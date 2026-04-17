package gabywald.creatures.genetics;

import gabywald.creatures.model.UnsignedByte;

/**
 * These genes allow initial amounts of certain chemicals to be present in the creature at birth, giving a certain level of energy etc to the new born. 
 * @author Gabriel Chandesris (2013, 2026)
 */
public class InitialConcentrationGene extends CreatureGene {
	/**
	 * Old variant of the constructor. 
	 * @param chemical (UnsignedByte)
	 * @param amount (UnsignedByte)
	 */
	public InitialConcentrationGene(UnsignedByte chemical, UnsignedByte amount) {
		super(2, 6);
		this.data.add(chemical);
		this.data.add(amount);
	}
	
	public int getChemical()	{ return this.data.get(0).getValue(); }
	public int getAmount()		{ return this.data.get(1).getValue(); }
}

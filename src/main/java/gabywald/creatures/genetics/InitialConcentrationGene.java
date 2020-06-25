package gabywald.creatures.genetics;

import java.util.List;

import gabywald.creatures.model.UnsignedByte;

/**
 * These genes allow initial amounts of certain chemicals to be present in the creature at birth, giving a certain level of energy etc to the new born. 
 * @author Gabriel Chandesris (2013)
 */
public class InitialConcentrationGene extends CreatureGene {
	/**
	 * Old variant of the constructor. 
	 * @param chemical (int)
	 * @param amount (int)
	 * TODO convert to InitialConcentrationGene#InitialConcentrationGene(List)
	 * @deprecated Use {@link InitialConcentrationGene#InitialConcentrationGene(List)} instead !
	 */
	private InitialConcentrationGene(int chemical, int amount) {
		super(2, 6);
		this.data.add(new UnsignedByte(chemical));
		this.data.add(new UnsignedByte(amount));
	}
	
	public InitialConcentrationGene(List<UnsignedByte> datas) throws CreatureGeneException 
		{ super(2, 6, datas, 2); /* size expected is 2 ! */ }
	
	public int getChemical()	{ return this.data.get(0).getValue(); }
	public int getAmount()		{ return this.data.get(1).getValue(); }
}

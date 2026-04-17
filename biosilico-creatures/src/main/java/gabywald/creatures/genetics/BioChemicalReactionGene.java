package gabywald.creatures.genetics;

import java.util.ArrayList;

import gabywald.creatures.model.UnsignedByte;

/**
 * These genes govern the reactions, which occur within the Biochemical Sea of the creature. The only type of reaction which is explicitly prevented is a "Nothing -> Something" reaction. 
 * @author Gabriel Chandesris (2013, 2026)
 */
public class BioChemicalReactionGene extends CreatureGene {
	/**
	 * Old variant of the constructor. 
	 * @param qnt1 (UnsignedByte)
	 * @param react1 (UnsignedByte)
	 * @param qnt2 (UnsignedByte)
	 * @param react2 (UnsignedByte)
	 * @param qnt3 (UnsignedByte)
	 * @param react3 (UnsignedByte)
	 * @param qnt4 (UnsignedByte)
	 * @param react4 (UnsignedByte)
	 * @param rate (UnsignedByte)
	 */
	public BioChemicalReactionGene(UnsignedByte qnt1, UnsignedByte react1, UnsignedByte qnt2, UnsignedByte react2, 
									UnsignedByte qnt3, UnsignedByte react3, UnsignedByte qnt4, UnsignedByte react4, 
									UnsignedByte rate) {
		super(1, 2);
		this.data = new ArrayList<UnsignedByte>(9);
		this.data.add(qnt1);
		this.data.add(react1);
		this.data.add(qnt2);
		this.data.add(react2);
		this.data.add(qnt3);
		this.data.add(react3);
		this.data.add(qnt4);
		this.data.add(react4);
		this.data.add(rate);
	}
	
	public int getQuantity1()		{ return this.data.get(0).getValue(); }
	public int getQuantity2()		{ return this.data.get(2).getValue(); }
	public int getQuantity3()		{ return this.data.get(4).getValue(); }
	public int getQuantity4()		{ return this.data.get(6).getValue(); }
	
	public int getReactant1()		{ return this.data.get(1).getValue(); }
	public int getReactant2()		{ return this.data.get(3).getValue(); }
	public int getReactant3()		{ return this.data.get(5).getValue(); }
	public int getReactant4()		{ return this.data.get(7).getValue(); }
	
	public int getRateOfReaction()	{ return this.data.get(8).getValue(); }
}

package gabywald.creatures.genetics;

import gabywald.creatures.model.UnsignedByte;

import java.util.ArrayList;
import java.util.List;

/**
 * These genes govern the reactions, which occur within the Biochemical Sea of the creature. The only type of reaction which is explicitly prevented is a "Nothing -> Something" reaction. 
 * @author Gabriel Chandesris (2013)
 */
public class BioChemicalReactionGene extends CreatureGene {
	/**
	 * Old variant of the constructor. 
	 * @param qnt1 (int)
	 * @param react1 (int)
	 * @param qnt2 (int)
	 * @param react2 (int)
	 * @param qnt3 (int)
	 * @param react3 (int)
	 * @param qnt4 (int)
	 * @param react4 (int)
	 * @param rate (int)
	 * @deprecated Use {@link BioChemicalReactionGene#ChemicalReactionGene(List)} instead !
	 */
	private BioChemicalReactionGene(int qnt1, int react1, int qnt2, int react2, 
									int qnt3, int react3, int qnt4, int react4, 
									int rate) {
		super(1, 2);
		this.data = new ArrayList<UnsignedByte>(9);
		this.data.add(new UnsignedByte(qnt1));
		this.data.add(new UnsignedByte(react1));
		this.data.add(new UnsignedByte(qnt2));
		this.data.add(new UnsignedByte(react2));
		this.data.add(new UnsignedByte(qnt3));
		this.data.add(new UnsignedByte(react3));
		this.data.add(new UnsignedByte(qnt4));
		this.data.add(new UnsignedByte(react4));
		this.data.add(new UnsignedByte(rate));
	}
	
	public BioChemicalReactionGene(List<UnsignedByte> datas) throws CreatureGeneException 
		{ super(1, 2, datas, 9); /* size expected is 9 ! */ }
	
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

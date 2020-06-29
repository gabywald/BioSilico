package gabywald.creatures.genetics;

import gabywald.creatures.model.UnsignedByte;

import java.util.ArrayList;
import java.util.List;

/**
 * These are processed during embryology and whilst the creature is asleep. They provide a way of providing the creature wish some basic information like "Eat Food When Hungry = Good" and "Do Nothing When Bored = Bad" 
 * @author Gabriel Chandesris (2013)
 */
public class InstinctGene extends CreatureGene {
	/** Lobe0 ; Neuron0 ; Lobe1 ; Neuron1 ; Lobe2 ; Neuron2 ; ...
	 * ... ; Action ; ReinforcementDrive ; ReinforcementLevel */
	
	/**
	 * Old variant of constructor. 
	 * @param lob1 (int)
	 * @param neu1 (int)
	 * @param lob2 (int)
	 * @param neu2 (int)
	 * @param lob3 (int)
	 * @param neu3 (int)
	 * @param action (int)
	 * @param reinfDrive (int)
	 * @param reinfLvl (int)
	 * @deprecated Use {@link InstinctGene#InstinctGene(List)} instead !
	 */
	private InstinctGene(	int lob1, int neu1, 
							int lob2, int neu2, 
							int lob3, int neu3, 
							int action, int reinfDrive, int reinfLvl) {
		super(2, 5);
		this.data = new ArrayList<UnsignedByte>(9);
		this.data.add(new UnsignedByte(lob1));
		this.data.add(new UnsignedByte(neu1));
		this.data.add(new UnsignedByte(lob2));
		this.data.add(new UnsignedByte(neu2));
		this.data.add(new UnsignedByte(lob3));
		this.data.add(new UnsignedByte(neu3));
		this.data.add(new UnsignedByte(action));
		this.data.add(new UnsignedByte(reinfDrive));
		this.data.add(new UnsignedByte(reinfLvl));
	}
		
	public InstinctGene(List<UnsignedByte> datas) throws CreatureGeneException {
		super(2, 5);
		int expectedSize = 9;
		if (datas.size() != expectedSize) 
			{ throw new CreatureGeneException(2, 5, "Bad number of datas e:" + expectedSize + " [" + datas.size() + "]"); }
		this.data = datas; // size expected is 9 !
	}
	
	public int getLobe1()		{ return this.data.get(0).getValue(); }
	public int getLobe2()		{ return this.data.get(2).getValue(); }
	public int getLobe3()		{ return this.data.get(4).getValue(); }
	
	public int getNeurone1()	{ return this.data.get(1).getValue(); }
	public int getNeurone2()	{ return this.data.get(3).getValue(); }
	public int getNeurone3()	{ return this.data.get(5).getValue(); }
	
	public int getAction()				{ return this.data.get(6).getValue(); }
	public int getReinforcementDrive()	{ return this.data.get(7).getValue(); }
	public int getReinforcementLevel()	{ return this.data.get(8).getValue(); }
}

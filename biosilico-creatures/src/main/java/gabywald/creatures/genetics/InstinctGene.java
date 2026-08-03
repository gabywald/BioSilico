package gabywald.creatures.genetics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gabywald.creatures.model.UnsignedByte;
import gabywald.global.structures.PairSimple;

/**
 * These are processed during embryology and whilst the creature is asleep. They provide a way of providing the creature wish some basic information like "Eat Food When Hungry = Good" and "Do Nothing When Bored = Bad" 
 * @author Gabriel Chandesris (2013, 2026)
 */
public class InstinctGene extends CreatureGene {
	/** Lobe0 ; Neuron0 ; Lobe1 ; Neuron1 ; Lobe2 ; Neuron2 ; ...
	 * ... ; Action ; ReinforcementDrive ; ReinforcementLevel */
	
	/**
	 * Old variant of constructor. 
	 * @param lob1 (UnsignedByte)
	 * @param neu1 (UnsignedByte)
	 * @param lob2 (UnsignedByte)
	 * @param neu2 (UnsignedByte)
	 * @param lob3 (UnsignedByte)
	 * @param neu3 (UnsignedByte)
	 * @param action (UnsignedByte)
	 * @param reinfDrive (UnsignedByte)
	 * @param reinfLvl (UnsignedByte)
	 */
	public InstinctGene(	UnsignedByte lob1, UnsignedByte neu1, 
							UnsignedByte lob2, UnsignedByte neu2, 
							UnsignedByte lob3, UnsignedByte neu3, 
							UnsignedByte action, UnsignedByte reinfDrive, UnsignedByte reinfLvl) {
		super(2, 5);
		this.data = new ArrayList<UnsignedByte>(9);
		this.data.add(lob1);
		this.data.add(neu1);
		this.data.add(lob2);
		this.data.add(neu2);
		this.data.add(lob3);
		this.data.add(neu3);
		this.data.add(action);
		this.data.add(reinfDrive);
		this.data.add(reinfLvl);
	}
	
	public int getLobe1()		{ return this.data.get(0).getValue(); }
	public int getLobe2()		{ return this.data.get(2).getValue(); }
	public int getLobe3()		{ return this.data.get(4).getValue(); }
	
	public int getNeurone1()	{ return this.data.get(1).getValue(); }
	public int getNeurone2()	{ return this.data.get(3).getValue(); }
	public int getNeurone3()	{ return this.data.get(5).getValue(); }
	
	public List<PairSimple<UnsignedByte, UnsignedByte> > getConditions() {
		return Arrays.asList( PairSimple.of(this.data.get(0), this.data.get(1)), 
							  PairSimple.of(this.data.get(2), this.data.get(3)), 
							  PairSimple.of(this.data.get(4), this.data.get(5)) );
	}
	
	public int getAction()				{ return this.data.get(6).getValue(); }
	public int getReinforcementDrive()	{ return this.data.get(7).getValue(); }
	public int getReinforcementLevel()	{ return this.data.get(8).getValue(); }
}

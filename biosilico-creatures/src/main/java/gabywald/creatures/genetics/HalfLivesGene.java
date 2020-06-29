package gabywald.creatures.genetics;

import gabywald.creatures.model.UnsignedByte;

import java.util.List;

/**
 * These are the decay rates of all of the chemicals within the creature's Biochemical Sea. They are arranged in the same way as the reaction rate. 
 * @author Gabriel Chandesris (2013)
 */
public class HalfLivesGene extends CreatureGene {
	
	public HalfLivesGene(List<UnsignedByte> datas) throws CreatureGeneException 
		{ super(1, 3, datas, 256 /** C2 : 255 */); }
	
	public UnsignedByte getValueAt(int i) {
		if ( (i < 0) || (i >= this.data.size()) ) 
			{ return new UnsignedByte(0); }
		return this.data.get(i);
	}
	
	public int getIntValueAt(int i) {
		if ( (i < 0) || (i >= this.data.size()) ) 
			{ return 0; }
		return this.data.get(i).getValue();
	}
}

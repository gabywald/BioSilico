package gabywald.creatures.genetics;

import gabywald.creatures.exceptions.CreatureGeneException;
import gabywald.creatures.model.UnsignedByte;

import java.util.List;

/**
 * These are the decay rates of all of the chemicals within the creature's Biochemical Sea. They are arranged in the same way as the reaction rate. 
 * @author Gabriel Chandesris (2013, 2026)
 */
public class HalfLivesGene extends CreatureGene {
	
	/**
	 * 
	 * @param datas
	 * @throws CreatureGeneException
	 */
	public HalfLivesGene(List<UnsignedByte> datas)
		{ super(1, 3);this.data.addAll(datas); }
	
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

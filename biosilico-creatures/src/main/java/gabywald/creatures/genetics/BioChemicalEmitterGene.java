package gabywald.creatures.genetics;

import gabywald.creatures.model.UnsignedByte;

import java.util.ArrayList;
import java.util.List;

/**
 * Emitters tie to loci as do receptors. They read the value of the locus, and emit chemicals based on their input states.
 * <br>{ Organ ; Tissue ; Locus ; Chemical ; Threshold ; Rate ; Gain ; Flags } 
 * <br>The Emitter flags can be: 1: Clear input signal after reading or 2: Invert Input Signal.
 * @author Gabriel Chandesris (2013)
 */
public class BioChemicalEmitterGene extends CreatureGene {
	/**
	 * Old variant of the constructor. 
	 * @param orga (int)
	 * @param tissue (int)
	 * @param locus (int)
	 * @param chemical (int)
	 * @param threshold (int)
	 * @param nominal (int)
	 * @param gain (int)
	 * @param flags (int)
	 * @deprecated Use {@link BioChemicalEmitterGene#EmitterGene(List)} instead !
	 */
	private BioChemicalEmitterGene(	int orga, int tissue, int locus, int chemical, 
									int threshold, int nominal, int gain, int flags) {
		super(1, 1);
		this.data = new ArrayList<UnsignedByte>(8);
		this.data.add(new UnsignedByte(orga));
		this.data.add(new UnsignedByte(tissue));
		this.data.add(new UnsignedByte(locus));
		this.data.add(new UnsignedByte(chemical));
		this.data.add(new UnsignedByte(threshold));
		this.data.add(new UnsignedByte(nominal));
		this.data.add(new UnsignedByte(gain));
		this.data.add(new UnsignedByte(flags));
	}
	
	public BioChemicalEmitterGene(List<UnsignedByte> datas) throws CreatureGeneException 
		{ super(1, 1, datas, 8); /* size expected is 8 ! */ }
	
	public int getOrgan()		{ return this.data.get(0).getValue(); }
	public int getTissue()		{ return this.data.get(1).getValue(); }
	public int getLocus()		{ return this.data.get(2).getValue(); }
	public int getChemical()	{ return this.data.get(3).getValue(); }
	public int getTheshold()	{ return this.data.get(4).getValue(); }
	public int getRate()		{ return this.data.get(5).getValue(); }
	public int getGain()		{ return this.data.get(6).getValue(); }
	public int getFlags()		{ return this.data.get(7).getValue(); }
	
	// TODO see in [http://meliweb.net/creatures/emitter.htm]
	public static String[] EMITTER_ORGANS_C1	= { "Brain", "Creature" };
	public static String[] EMITTER_TISSUES_C1	= { "Somatic", "Circulatory", "Reproductive", "Immune", "Sensorymotor", "Drive Levels" };
	
	// TODO C2/C3 organs and tissues....
}

package gabywald.creatures.genetics;

import java.util.ArrayList;

import gabywald.creatures.model.UnsignedByte;

/**
 * Emitters tie to loci as do receptors. They read the value of the locus, and emit chemicals based on their input states.
 * <br>{ Organ ; Tissue ; Locus ; Chemical ; Threshold ; Rate ; Gain ; Flags } 
 * <br>The Emitter flags can be: 1: Clear input signal after reading or 2: Invert Input Signal.
 * @author Gabriel Chandesris (2013, 2026)
 */
public class BioChemicalEmitterGene extends CreatureGene {
	/**
	 * Old variant of the constructor. 
	 * @param orga (UnsignedByte)
	 * @param tissue (UnsignedByte)
	 * @param locus (UnsignedByte)
	 * @param chemical (UnsignedByte)
	 * @param threshold (UnsignedByte)
	 * @param nominal (UnsignedByte)
	 * @param gain (UnsignedByte)
	 * @param flags (UnsignedByte)
	 */
	public BioChemicalEmitterGene(	UnsignedByte orga, UnsignedByte tissue, UnsignedByte locus, UnsignedByte chemical, 
									UnsignedByte threshold, UnsignedByte nominal, UnsignedByte gain, UnsignedByte flags) {
		super(1, 1);
		this.data = new ArrayList<UnsignedByte>(8);
		this.data.add(orga);
		this.data.add(tissue);
		this.data.add(locus);
		this.data.add(chemical);
		this.data.add(threshold);
		this.data.add(nominal);
		this.data.add(gain);
		this.data.add(flags);
	}
	
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

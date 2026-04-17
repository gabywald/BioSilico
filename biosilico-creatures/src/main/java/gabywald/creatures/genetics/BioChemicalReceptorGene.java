package gabywald.creatures.genetics;

import java.util.ArrayList;

import gabywald.creatures.model.UnsignedByte;

/**
 * The biochemical receptors latch onto a locus within the brain or the current organ, and provide feedback to the brain about chemicals within the body.
 * <br>{ Organ ; Tissue ; Locus ; Chemical ; Threshold ; Nominal ; Gain ; Flags}
 * <br>The flags indicate if the receptor output is inverted (1) or if it is digital (2).
 * <br>A digital receptor has its state calculated as: <i>Output = Nominal &plusmn; Gain If Signal &gt; Threshold</i>
 * <br>
 * @author Gabriel Chandesris (2013, 2026)
 */
public class BioChemicalReceptorGene extends CreatureGene {
	/**
	 * Old variant of the constructor. 
	 * <br>Organ, tissue, locus (first chemicals), Chemical, Threshold, Nominal, Gain, Flags. 
	 * <br>The flags indicate if the receptor output is inverted (1) or if it is digital (2). 
	 * @param orga (UnsignedByte)
	 * @param tissue (UnsignedByte)
	 * @param locus (UnsignedByte)
	 * @param chemical (UnsignedByte)
	 * @param threshold (UnsignedByte)
	 * @param nominal (UnsignedByte)
	 * @param gain (UnsignedByte)
	 * @param flags (UnsignedByte)
	 */
	public BioChemicalReceptorGene(UnsignedByte orga, UnsignedByte tissue, UnsignedByte locus, UnsignedByte chemical, 
									UnsignedByte threshold, UnsignedByte nominal, UnsignedByte gain, UnsignedByte flags) {
		super(1, 0);
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
	public int getNominal()		{ return this.data.get(5).getValue(); }
	public int getGain()		{ return this.data.get(6).getValue(); }
	public int getFlags()		{ return this.data.get(7).getValue(); }
	
	// TODO see in [http://meliweb.net/creatures/receptor.htm]
	public static String[] RECEPTOR_ORGANS_C1	= { "Brain", "Creature" };
	public static String[] RECEPTOR_TISSUES_C1	= { "Somatic", "Circulatory", "Reproductive", "Immune", "Sensorymotor", "Drive Levels" };
	
	// TODO C2/C3 organs and tissues....
}

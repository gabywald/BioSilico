package gabywald.creatures.genetics;

import gabywald.creatures.model.UnsignedByte;

import java.util.ArrayList;
import java.util.List;

/**
 * The biochemical receptors latch onto a locus within the brain or the current organ, and provide feedback to the brain about chemicals within the body.
 * <br>{ Organ ; Tissue ; Locus ; Chemical ; Threshold ; Nominal ; Gain ; Flags}
 * <br>The flags indicate if the receptor output is inverted (1) or if it is digital (2).
 * <br>A digital receptor has its state calculated as: <i>Output = Nominal &plusmn; Gain If Signal &gt; Threshold</i>
 * <br>
 * @author Gabriel Chandesris (2013)
 */
public class BioChemicalReceptorGene extends CreatureGene {
	/**
	 * Old variant of the constructor. 
	 * <br>Organ, tissue, locus (first chemicals), Chemical, Threshold, Nominal, Gain, Flags. 
	 * <br>The flags indicate if the receptor output is inverted (1) or if it is digital (2). 
	 * @param orga (int)
	 * @param tissue (int)
	 * @param locus (int)
	 * @param chemical (int)
	 * @param threshold (int)
	 * @param nominal (int)
	 * @param gain (int)
	 * @param flags (int)
	 * @deprecated Use {@link BioChemicalReceptorGene#ReceptorGene(List)} instead !
	 */
	private BioChemicalReceptorGene(	int orga, int tissue, int locus, int chemical, 
							int threshold, int nominal, int gain, int flags) {
		super(1, 0);
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
	
	public BioChemicalReceptorGene(List<UnsignedByte> datas) throws CreatureGeneException 
		{ super(1, 0, datas, 8); /* size expected is 8 ! */ }
	
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

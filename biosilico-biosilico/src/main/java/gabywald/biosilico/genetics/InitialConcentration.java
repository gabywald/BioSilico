package gabywald.biosilico.genetics;

import gabywald.biosilico.exceptions.GeneException;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.structures.GeneticTranslator;

/**
 * This type of Gene has to initiate variables of an organism on a starting life.<br>
 * <b>sex</b>, <b>ageMax</b> and <b>ageMin</b> should be 0 (all and at start).
 * <br><i>Creatures inspired. </i>
 * @author Gabriel Chandesris (2009, 2020)
 */
public class InitialConcentration extends GeneGattaca {
	/** The variable indice to be affected. */
	private int varia;
	/** The value to be given to variable. */
	private int value;

	/**
	 * Main Constructor for this kind of Gattaca Gene. <br>
	 * Aim of this type of Gene is to set a certain amount of a chemical. 
	 * @param mutate (boolean) If Gene can be mutated. 
	 * @param duplicate (boolean) if Gene can, be duplicated. 
	 * @param delete (boolean) If Gene can be deleted. 
	 * @param activ (boolean) If Gene is globally activated. 
	 * @param ageMin (int) Minimal age of activation. <b>In general is 0. </b>
	 * @param ageMax (int) Maximal age of activation. <b>In general is 0. </b>
	 * @param sex (int) Sex of activation. 
	 * @param mutRate (int) Rate of mutation of this Gene. 
	 * @param var (int) Number of the chemical. 
	 * @param val (int) Amount to be set
	 * @see GeneGattaca#GeneGattaca(boolean, boolean, boolean, boolean, int, int, int, int)
	 * @see Gene#Gene(boolean, boolean, boolean, boolean, int, int, int, int)
	 */
	public InitialConcentration(
			boolean mutate, boolean duplicate, boolean delete, boolean activ, 
			int ageMin, int ageMax, int sex, int mutRate,
			int var, int val) {
		super(mutate, duplicate, delete, activ, ageMin, ageMax, sex, mutRate);
		this.varia = Gene.obtainValue(0,  999, var);
		this.value = Gene.obtainValue(0,  999, val);
	}
	
	public String reverseTranslation(boolean end) {
		String result		= super.reverseTranslation(false);
		StringBuilder tmp	= new StringBuilder();
		tmp.append(Gene.convert0to999(this.varia));
		tmp.append(Gene.convert0to999(this.value));
		
		result += GeneticTranslator.reverseSequenceGattaca( tmp.toString() );
		
		// end is given here "GGT" 
		return result+GeneticTranslator.reverseGattaca("*");
	}

	protected void exec(Organism orga) throws GeneException {
		orga.getChemicals().setVariable(this.varia, this.value); 
	}
	
	public int getVariable()	{ return this.varia; }
	public int getValue()		{ return this.value; }
	
	public String toString() {
		String stringenize = this.reverseTranslation(true)+"\t"+
							super.toString()+varia+"\t"+value+"\t";
		return stringenize;
	}

}

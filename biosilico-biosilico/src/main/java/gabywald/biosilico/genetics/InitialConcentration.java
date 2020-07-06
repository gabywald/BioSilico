package gabywald.biosilico.genetics;

import gabywald.biosilico.exceptions.GeneException;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.structures.GeneticTranslator;

/**
 * This type of Gene has to initiate variables of an organism on a starting life.<br>
 * <b>sex</b>, <b>age_max</b> and <b>age_min</b> should be 0 (all and at start).
 * <br><i>Creatures inspired. </i>
 * @author Gabriel Chandesris (2009)
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
	 * @param age_min (int) Minimal age of activation. <b>In general is 0. </b>
	 * @param age_max (int) Maximal age of activation. <b>In general is 0. </b>
	 * @param sex (int) Sex of activation. 
	 * @param mut_rate (int) Rate of mutation of this Gene. 
	 * @param var (int) Number of the chemical. 
	 * @param val (int) Amount to be set
	 * @see GeneGattaca#GeneGattaca(boolean, boolean, boolean, boolean, int, int, int, int)
	 * @see Gene#Gene(boolean, boolean, boolean, boolean, int, int, int, int)
	 */
	public InitialConcentration(
			boolean mutate, boolean duplicate,boolean delete, boolean activ, 
			int age_min, int age_max, int sex, int mut_rate,
			int var,int val) {
		super(mutate, duplicate, delete, activ, age_min, age_max, sex, mut_rate);
		this.varia = (var < 0)?0:((var > 999)?999:var);
		this.value = (val < 0)?0:((val > 999)?999:val);
	}
	
	public String reverseTranslation(boolean end) {
		String result = super.reverseTranslation(false);
		String tmp = "";
		tmp += ((this.varia < 100)?"0"+((this.varia < 10)?"0":""):"")+this.varia;
		tmp += ((this.value < 100)?"0"+((this.value < 10)?"0":""):"")+this.value;
		/** if (this.val > 99) { tmp += this.val; }
		else if (this.val > 9) { tmp += "0"+this.val; }
		else { tmp += "00"+this.val; } */
		for (int i = 0 ; i < tmp.length() ; i++) 
			{ result += GeneticTranslator.reverseGattaca(tmp.charAt(i)+""); }
		 /** end is given here "GGT" */
		return result+GeneticTranslator.reverseGattaca("*");
	}

	protected void exec(Organism orga) throws GeneException 
		{ orga.getChemicals().setVariable(this.varia,this.value); }
	
	public int getVariable()	{ return this.varia; }
	public int getValue()		{ return this.value; }
	
	public String toString() {
		String stringenize = this.reverseTranslation(true)+"\t"+
							super.toString()+varia+"\t"+value+"\t";
		return stringenize;
	}

}

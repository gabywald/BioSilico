package gabywald.biosilico.genetics;

import gabywald.biosilico.exceptions.GeneException;
import gabywald.biosilico.model.Brain;
import gabywald.biosilico.model.Neuron;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.structures.GeneticTranslator;

/**
 * This type of gene instanciates connections between Neurons in the Brain. 
 * <br><i>Creatures inspired. </i>
 * @author Gabriel Chandesris (2009)
 */
public class Instinct extends GeneGattaca {
	/** Height position of input Neuron in Brain. */
	private int inputPosX;
	/** Width position of input Neuron in Brain. */
	private int inputPosY;
	/** Height position of output Neuron in Brain. */
	private int outputPosX;
	/** Width position of output Neuron in Brain. */
	private int outputPosY;
	/** Weight of dendritic connection. */
	private int weight;
	/** Chemical to test to apply Instinct. */
	private int variable;
	/** Minimal Threshold of chemical to apply Instinct. */
	private int threshold;
	/** If input Neuron already present set weight or simply add dendrit. */
	private boolean check;

	/**
	 * 
	 * @param mutate (boolean) If Gene can be mutated. 
	 * @param duplicate (boolean) if Gene can, be duplicated. 
	 * @param delete (boolean) If Gene can be deleted. 
	 * @param activ (boolean) If Gene is globally activated. 
	 * @param age_min (int) Minimal age of activation. <b>In general is 0. </b>
	 * @param age_max (int) Maximal age of activation. <b>In general is 999. </b>
	 * @param sex (int) Sex of activation. 
	 * @param mut_rate (int) Rate of mutation of this Gene. 
	 * @param in_posx (int) Height position of input Neuron in Brain.
	 * @param in_posy (int) Width position of input Neuron in Brain.
	 * @param out_posx (int) Height position of output Neuron in Brain.
	 * @param out_posy (int) Width position of output Neuron in Brain.
	 * @param weight (int) Weight of dendritic connection.
	 * @param var (int) Chemical to test to apply Instinct.
	 * @param thr (int) Minimal Threshold of chemical to apply Instinct.
	 * @param check (boolean) If input Neuron already present set weight or simply add Neuron.
	 */
	public Instinct(
			boolean mutate, boolean duplicate,boolean delete, boolean activ, 
			int age_min, int age_max, int sex, int mut_rate,
			int in_posx,int in_posy,int out_posx,int out_posy,int weight,
			int var,int thr, boolean check) {
		super(mutate, duplicate, delete, activ, age_min, age_max, sex, mut_rate);
		this.inputPosX	= Gene.obtainValue(0, 99, in_posx);
		this.inputPosY	= Gene.obtainValue(0, 99, in_posy);
		this.outputPosX	= Gene.obtainValue(0, 99, out_posx);
		this.outputPosY	= Gene.obtainValue(0, 99, out_posy);
		this.weight		= Gene.obtainValue(0, 999, weight);
		
		this.variable	= Gene.obtainValue(0, 999, var);
		this.threshold	= Gene.obtainValue(0, 999, thr);
		this.check		= check;
	}
	
	public String reverseTranslation(boolean end) {
		String result = super.reverseTranslation(false);
		String tmp = "";
		tmp += ((this.inputPosX < 10)?"0":"")+this.inputPosX;
		tmp += ((this.inputPosY < 10)?"0":"")+this.inputPosY;
		tmp += ((this.outputPosX < 10)?"0":"")+this.outputPosX;
		tmp += ((this.outputPosY < 10)?"0":"")+this.outputPosY;
		tmp += ((this.weight < 100)?"0"+((this.weight < 10)?"0":""):"")+this.weight;
		tmp += ((this.variable < 100)?"0"+((this.variable < 10)?"0":""):"")+this.variable;
		tmp += ((this.threshold < 100)?"0"+((this.threshold < 10)?"0":""):"")+this.threshold;
		tmp += (this.check)?"0":"1"; 
		
		for (int i = 0 ; i < tmp.length() ; i++) 
			{ result += GeneticTranslator.reverseGattaca(tmp.charAt(i)+""); }
		 /** end is given here "GGT" */
		return result+GeneticTranslator.reverseGattaca("*");
	}

	protected void exec(Organism orga) throws GeneException {
		Brain brain = orga.getBrain();
		if (brain == null) { throw new GeneException("Organism has no Brain. "); }
		/** Re-load Instincts on each execution. Nothing if error. */
		if (orga.getChemicals().getVariable(this.variable) >= this.threshold) {
			Neuron input = brain.getNeuronAt(this.inputPosX, this.inputPosY);
			Neuron output = brain.getNeuronAt(this.outputPosX, this.outputPosY);
			int control = -1; /** to check if input already connected to output. */
			if (this.check) { control = output.getConnectPosition(input); }
			if ( ( (input != null) && (output != null) ) && (control == -1) ) 
				{ output.addConnection(input, this.weight); }
			/** if already connected and checked : set weight */
			if (control != -1) { output.getWeights().set(this.weight, control); }
		}
	}
	
	public int getPosXOrg()		{ return this.inputPosX; }
	public int getPosYOrg()		{ return this.inputPosY; }
	public int getPosXDes()		{ return this.outputPosX; }
	public int getPosYDes()		{ return this.outputPosY; }
	public int getWeight()		{ return this.weight; }
	public int getVariable()	{ return this.variable; }
	public int getThreshold()	{ return this.threshold; }

	public boolean getCheck()	{ return this.check;  }
	
	
	public String toString() {
		String stringenize = this.reverseTranslation(true)+"\t"+
							super.toString()+
							this.inputPosX+"\t"+this.inputPosY+"\t"+
							this.outputPosX+"\t"+this.outputPosY+"\t"+
							this.weight+"\t"+this.variable+"\t"+
							this.threshold+"\t"+this.check+"\t";
		return stringenize;
	}
}

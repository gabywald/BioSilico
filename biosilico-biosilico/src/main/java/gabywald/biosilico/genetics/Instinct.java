package gabywald.biosilico.genetics;

import java.util.Random;

import gabywald.biosilico.exceptions.GeneException;
import gabywald.biosilico.interfaces.IGeneMutation;
import gabywald.biosilico.model.Brain;
import gabywald.biosilico.model.Neuron;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.structures.GeneticTranslator;

/**
 * This type of gene instanciates connections between Neurons in the Brain. 
 * <br><i>Creatures inspired. </i>
 * @author Gabriel Chandesris (2009, 2020)
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
	/** Link is positive (or negative. */
	private boolean isPositive;

	/**
	 * 
	 * @param mutate (boolean) If Gene can be mutated. 
	 * @param duplicate (boolean) if Gene can, be duplicated. 
	 * @param delete (boolean) If Gene can be deleted. 
	 * @param activ (boolean) If Gene is globally activated. 
	 * @param ageMin (int) Minimal age of activation. <b>In general is 0. </b>
	 * @param ageMax (int) Maximal age of activation. <b>In general is 999 (or 0 if only at initial connections). </b>
	 * @param sex (int) Sex of activation. 
	 * @param mutRate (int) Rate of mutation of this Gene. 
	 * @param inPosX (int) Height position of input Neuron in Brain.
	 * @param inPosY (int) Width position of input Neuron in Brain.
	 * @param outPosX (int) Height position of output Neuron in Brain.
	 * @param outPosY (int) Width position of output Neuron in Brain.
	 * @param weight (int) Weight of dendritic connection.
	 * @param var (int) Chemical to test to apply Instinct.
	 * @param thr (int) Minimal Threshold of chemical to apply Instinct.
	 * @param check (boolean) If input Neuron already present set weight or simply add Neuron.
	 * @param check (boolean) If connection is positive or negative. 
	 */
	public Instinct(
			boolean mutate, boolean duplicate,boolean delete, boolean activ, 
			int ageMin, int ageMax, int sex, int mutRate,
			int inPosX, int inPosY, int outPosX, int outPosY, int weight, 
			int var, int thr, boolean check, boolean posi) {
		super(mutate, duplicate, delete, activ, ageMin, ageMax, sex, mutRate);
		this.inputPosX	= Gene.obtainValue(0, 99, inPosX);
		this.inputPosY	= Gene.obtainValue(0, 99, inPosY);
		this.outputPosX	= Gene.obtainValue(0, 99, outPosX);
		this.outputPosY	= Gene.obtainValue(0, 99, outPosY);
		
		this.weight		= Gene.obtainValue(0, 999, weight);
		this.variable	= Gene.obtainValue(0, 999, var);
		this.threshold	= Gene.obtainValue(0, 999, thr);
		
		this.check		= check;
		this.isPositive	= posi;
	}
	
	@Override
	public String reverseTranslation(boolean end) {
		String result		= super.reverseTranslation(false);
		StringBuilder tmp	= new StringBuilder();
		tmp.append(Gene.convert0to99(this.inputPosX));
		tmp.append(Gene.convert0to99(this.inputPosY));
		tmp.append(Gene.convert0to99(this.outputPosX));
		tmp.append(Gene.convert0to99(this.outputPosY));
		tmp.append(Gene.convert0to999(this.weight));
		tmp.append(Gene.convert0to999(this.variable));
		tmp.append(Gene.convert0to999(this.threshold));
		tmp.append((this.check)?"0":"1");
		tmp.append((this.isPositive)?"2":"3");
		
		result += GeneticTranslator.reverseSequenceGattaca( tmp.toString() );
		
		// end is given here "GGT" 
		return result+GeneticTranslator.reverseGattaca("*");
	}

	@Override
	protected void exec(Organism orga) throws GeneException {
		Brain brain = orga.getBrain();
		if (brain == null) { throw new GeneException("Organism has no Brain. "); }
		// ***** Re-load Instincts on each execution. Nothing if error. 
		if (orga.getChemicals().getVariable(this.variable) >= this.threshold) {
			Neuron input	= brain.getNeuronAt(this.inputPosX, this.inputPosY);
			Neuron output	= brain.getNeuronAt(this.outputPosX, this.outputPosY);
			int control		= -1; /** to check if input already connected to output. */
			if (this.check) { control = output.getConnectPosition(input); }
			if ( ( (input != null) && (output != null) ) && (control == -1) ) 
				{ output.addConnection(input, this.weight * (this.isPositive ? +1 : -1 ) ); }
			// ***** if already connected and checked : set weight */
			if (control != -1) { output.getWeights().set(control, this.weight * (this.isPositive ? +1 : -1 ) ); }
		} // END "if (orga.getChemicals().getVariable(this.variable) >= this.threshold)"
	}
	
	public int getPosXOrg()		{ return this.inputPosX; }
	public int getPosYOrg()		{ return this.inputPosY; }
	public int getPosXDes()		{ return this.outputPosX; }
	public int getPosYDes()		{ return this.outputPosY; }
	public int getWeight()		{ return this.weight; }
	public int getVariable()	{ return this.variable; }
	public int getThreshold()	{ return this.threshold; }

	public boolean getCheck()	{ return this.check;  }
	public boolean isPositive()	{ return this.isPositive;  }
	
	@Override
	public String toString() {
		String stringenize = this.reverseTranslation(true)+"\t"+
							super.toString()+
							this.inputPosX+"\t"+this.inputPosY+"\t"+
							this.outputPosX+"\t"+this.outputPosY+"\t"+
							this.weight+"\t"+this.variable+"\t"+
							this.threshold+"\t"+this.check+"\t"+this.isPositive+"\t";
		return stringenize;
	}
	
	@Override
	public Gene clone() {
		Gene toReturn = new Instinct(	this.canMutate(), this.canDuplicate(), this.canDelete(), this.isActiv(), 
										this.getAgeMin(), this.getAgeMax(), this.getSexAct(), this.getMutationRate(), 
										this.inputPosX, this.inputPosY, this.outputPosX, this.outputPosY, 
										this.weight, this.variable, this.threshold, this.check, this.isPositive);
		toReturn.setName( this.getName() );
		return toReturn;
	}
	
	@Override
	public void mutationChanges() {
		Random rand				= new Random();
		int selectedAttribute	= rand.nextInt(8);
		boolean moreOrLess		= rand.nextBoolean();
		switch( selectedAttribute ) {
		case(0):	this.inputPosX	= IGeneMutation.mutate(this.inputPosX, moreOrLess, 99); break;
		case(1):	this.inputPosY	= IGeneMutation.mutate(this.inputPosY, moreOrLess, 99); break;
		case(2):	this.outputPosX	= IGeneMutation.mutate(this.outputPosX, moreOrLess, 99); break;
		case(3):	this.outputPosY	= IGeneMutation.mutate(this.outputPosY, moreOrLess, 99); break;
		case(4):	this.weight		= IGeneMutation.mutate(this.weight, moreOrLess, 999); break;
		case(5):	this.variable	= IGeneMutation.mutate(this.variable, moreOrLess, 999); break;
		case(6):	this.threshold	= IGeneMutation.mutate(this.threshold, moreOrLess, 999); break;
		case(7):	this.check		= moreOrLess; break;
		case(8):	this.isPositive	= moreOrLess; break;
		}
	}
	
}

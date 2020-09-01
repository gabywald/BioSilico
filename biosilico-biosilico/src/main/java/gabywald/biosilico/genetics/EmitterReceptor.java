package gabywald.biosilico.genetics;

import java.util.Random;

import gabywald.biosilico.exceptions.GeneException;
import gabywald.biosilico.interfaces.IGeneMutation;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.structures.GeneticTranslator;

/**
 * This type of Gene is to input data from chemicals to brain
 * (or out put from brain to chemicals). First aim is to work on
 * Variables (chemicals) and first and last neurons of Brain 
 * (a specific lobe / neurons can be build for emitters / receptors). <br>
 * Those marked external are inputted / outputed from / to external chemicals. 
 * @author Gabriel Chandesris (2009, 2020)
 */
public class EmitterReceptor extends GeneGattaca {
	/** Chemical variable input / output ; [000-999]. */
	private int variable;
	/** Threshold of activation for transmission ; [000-999]. */
	private int threshold;
	/** Input or Output signal (in neuron if receptor or in chemical if emitter). */
	private int ioput;
	/** Height position of the neuron in the brain ; [00-99]. */
	private int posx;
	/** Width position of the neuron in the brain ; [00-99].  */
	private int posy;
	/** If this Gene is Receptor or Emitter. */
	private boolean receptor;
	/** If this Gene work on internal Variables or external. */
	private boolean internal;

	/**
	 * Main Constructor for this kind of Gattaca Gene. <br>
	 * Aim of this type of Gene is to transmit information between brain and chemicals. 
	 * @param mutate (boolean) If Gene can be mutated. 
	 * @param duplicate (boolean) if Gene can, be duplicated. 
	 * @param delete (boolean) If Gene can be deleted. 
	 * @param activ (boolean) If Gene is globally activated. 
	 * @param ageMin (int) Minimal age of activation. <b>In general is 0. </b>
	 * @param ageMax (int) Maximal age of activation. <b>In general is 999. </b>
	 * @param sex (int) Sex of activation. 
	 * @param mutRate (int) Rate of mutation of this Gene. 
	 * @param var (int) Chemical variable input / output ; [000-999].
	 * @param thr (int) Threshold of activation for transmission ; [000-999].
	 * @param put (int) Input or Output signal (in neuron if receptor or in chemical if emitter).
	 * @param posx (int) Height position of the neuron in the brain ; [00-99].
	 * @param posy (int) Width position of the neuron in the brain ; [00-99].
	 * @param receptor (boolean) If this Gene is Receptor or Emitter.
	 * @param internal (boolean) If this Gene act in internal or external of current Organism.
	 */
	public EmitterReceptor(
			boolean mutate, boolean duplicate, boolean delete, boolean activ, 
			int ageMin, int ageMax, int sex, int mutRate,
			int var, int thr, int put, int posx, int posy, 
			boolean receptor, boolean internal) {
		super(mutate, duplicate, delete, activ, ageMin, ageMax, sex, mutRate);
		this.variable	= Gene.obtainValue(0, 999, var);
		this.threshold	= Gene.obtainValue(0, 999, thr);
		this.ioput		= Gene.obtainValue(0, 999, put);
		this.posx		= Gene.obtainValue(0, 99, posx);
		this.posy		= Gene.obtainValue(0, 99, posy);
		this.receptor	= receptor;
		this.internal	= internal;
	}
	
	@Override
	public String reverseTranslation(boolean end) {
		String result		= super.reverseTranslation(false);
		StringBuilder tmp	= new StringBuilder();
		tmp.append(Gene.convert0to999(this.variable));
		tmp.append(Gene.convert0to999(this.threshold));
		tmp.append(Gene.convert0to999(this.ioput));
		tmp.append(Gene.convert0to99(this.posx));
		tmp.append(Gene.convert0to99(this.posy));
		tmp.append((this.receptor)?"0":"1"); 
		tmp.append((this.internal)?"2":"3");
		
		result += GeneticTranslator.reverseSequenceGattaca( tmp.toString() );
		
		// end is given here "GGT" 
		return result+GeneticTranslator.reverseGattaca("*");
	}

	/**
	 * EmitterReceptor, Some explanations (works only if Brain present and position of Neuronis not null) :
	 * (internal) && (receptor) => if (internal variable >= threshold) => activity at (x, y) increase by put
	 * (internal) && ( ! receptor) => if (activity at (x, y) >= threshold) => internal variable increase by put
	 * ( ! internal) && (receptor) => if (WorldCase variable >= threshold) => activity at (x, y) increase by put
	 * ( ! internal) && ( ! receptor) => if (activity at (x, y) >= threshold) => external variable increase by put
	 */
	@Override
	protected void exec(Organism orga) throws GeneException {
		// Work only if a brain and a neuron are on position
		if ( (orga.getBrain() == null) 
				|| (orga.getBrain().getNeuronAt(this.posx, this.posy) == null) ) 
			{ return; }
		if (this.internal) {
			// **** Internal Receptor / Emitter
			// Loopback and detections upon biochemical actions
			if (this.receptor) {
				// Internal Receptor 
				int chemicalVal = orga.getChemicals().getVariable(this.variable);
				if (chemicalVal >= this.threshold) 
					{ orga.getBrain().getNeuronAt(this.posx, this.posy).addActivity(this.ioput); }
			} else {
				// Internal Emitter 
				int activityNeu = orga.getBrain().getNeuronAt(this.posx, this.posy).getActivity();
				if (activityNeu > this.threshold) 
					{ orga.getChemicals().setVarPlus(this.variable, this.ioput); }
			}
		} else { 
			// ***** External Receptor / Emitter
			// Example : pheromone detection / excretion
			if (this.receptor) {
				// External Receptor
				int chemicalVal = orga.getCurrentWorldCase().getVariables().getVariable(this.variable);
				if (chemicalVal >= this.threshold) 
					{ orga.getBrain().getNeuronAt(this.posx, this.posy).addActivity(this.ioput); }
			} else { 
				// External Emitter
				int activityNeu = orga.getBrain().getNeuronAt(this.posx, this.posy).getActivity();
				if (activityNeu > this.threshold) 
					{ orga.getCurrentWorldCase().getVariables().setVarPlus(this.variable, this.ioput); }
			}
		}
	}
	
	public int getVariable()		{ return this.variable; }
	public int getThreshold()		{ return this.threshold; }
	public int getIOnput()			{ return this.ioput; }
	public int getPosXNeurone()		{ return this.posx; }
	public int getPosYNeurone()		{ return this.posy; }
	public boolean getReceptor()	{ return this.receptor; }
	public boolean getInternal()	{ return this.internal; }

	@Override
	public String toString() {
		String stringenize = this.reverseTranslation(true)+"\t"+
							super.toString()+
							this.variable+"\t"+this.threshold+"\t"+
							this.ioput+"\t"+this.posx+"\t"+this.posy+"\t"+
							this.receptor+"\t"+this.internal+"\t";
		return stringenize;
	}
	
	@Override
	public Gene clone() {
		return new EmitterReceptor(	this.canMutate(), this.canDuplicate(), this.canDelete(), this.isActiv(), 
									this.getAgeMin(), this.getAgeMax(), this.getSexAct(), this.getMutationRate(), 
									this.variable, this.threshold, this.ioput, this.posx, this.posy, 
									this.receptor, this.internal);
	}

	@Override
	public void mutationChanges() {
		Random rand				= new Random();
		int selectedAttribute	= rand.nextInt(7);
		boolean moreOrLess		= rand.nextBoolean();
		switch( selectedAttribute ) {
		case(0):	this.variable	= IGeneMutation.mutate(this.variable, moreOrLess, 999); break;
		case(1):	this.threshold	= IGeneMutation.mutate(this.threshold, moreOrLess, 999); break;
		case(2):	this.ioput		= IGeneMutation.mutate(this.ioput, moreOrLess, 999); break;
		case(3):	this.posx		= IGeneMutation.mutate(this.posx, moreOrLess, 99); break;
		case(4):	this.posy		= IGeneMutation.mutate(this.posy, moreOrLess, 99); break;
		case(5):	this.receptor	= moreOrLess; break;
		case(6):	this.internal	= moreOrLess; break;
		}
	}
	
}

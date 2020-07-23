package gabywald.biosilico.genetics;

import gabywald.biosilico.exceptions.GeneException;
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
	 * @param age_min (int) Minimal age of activation. <b>In general is 0. </b>
	 * @param age_max (int) Maximal age of activation. <b>In general is 999. </b>
	 * @param sex (int) Sex of activation. 
	 * @param mut_rate (int) Rate of mutation of this Gene. 
	 * @param var (int) Chemical variable input / output ; [000-999].
	 * @param thr (int) Threshold of activation for transmission ; [000-999].
	 * @param put (int) Input or Output signal (in neuron if receptor or in chemical if emitter).
	 * @param posx (int) Height position of the neuron in the brain ; [00-99].
	 * @param posy (int) Width position of the neuron in the brain ; [00-99].
	 * @param receptor (boolean) If this Gene is Receptor or Emitter.
	 */
	public EmitterReceptor(
			boolean mutate, boolean duplicate, boolean delete, boolean activ, 
			int age_min, int age_max, int sex, int mut_rate,
			int var, int thr, int put, int posx, int posy, 
			boolean receptor, boolean internal) {
		super(mutate, duplicate, delete, activ, age_min, age_max, sex, mut_rate);
		this.variable	= Gene.obtainValue(0, 999, var);
		this.threshold	= Gene.obtainValue(0, 999, thr);
		this.ioput		= Gene.obtainValue(0, 999, put);
		this.posx		= Gene.obtainValue(0, 99, posx);
		this.posy		= Gene.obtainValue(0, 99, posy);
		this.receptor	= receptor;this.internal = internal;
	}
	
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
		
		for (int i = 0 ; i < tmp.length() ; i++) 
			{ result += GeneticTranslator.reverseGattaca(tmp.charAt(i)+""); }
		
		// end is given here "GGT" 
		return result+GeneticTranslator.reverseGattaca("*");
	}

	protected void exec(Organism orga) throws GeneException {
		if (this.internal) {
			// **** Internal Receptor / Emitter
			// l*Loopback and detections upon biochemical actions
			// Work only if a brain and a neuron are on position
			if ( (orga.getBrain() != null) 
					&& (orga.getBrain().getNeuronAt(this.posx, this.posy) 
							!= null) ) {
				if (this.receptor) {
					// internal Receptor 
					int chemicalVal = orga.getChemicals().getVariable(this.variable);
					if (chemicalVal >= this.threshold) 
						{ orga.getBrain()
								.getNeuronAt(this.posx, this.posy)
									.addActivity(this.ioput); }
				} else {
					// internal Emitter 
					int activityNeu = orga.getBrain()
									.getNeuronAt(this.posx, this.posy)
										.getActivity();
					if (activityNeu > this.threshold) 
						{ orga.getChemicals().setVarPlus(this.variable, this.ioput); }
				}
			}
		} else { 
			// ***** External Receptor / Emitter
			// example : pheromone detection
			if ( (orga.getBrain() != null) 
					&& (orga.getBrain().getNeuronAt(this.posx, this.posy) 
							!= null) ) {
				if (this.receptor) {
					// external Receptor
					int chemicalVal = orga.getLocation().getVariables()
											.getVariable(this.variable);
					if (chemicalVal >= this.threshold) 
						{ orga.getBrain().getNeuronAt(this.posx, this.posy)
									.addActivity(this.ioput); }
				} else { 
					// external Emitter
					int activityNeu = orga.getBrain()
										.getNeuronAt(this.posx, this.posy)
												.getActivity();
				if (activityNeu > this.threshold) 
					{ orga.getLocation().getVariables()
								.setVarPlus(this.variable, this.ioput); }
				}
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

	
	public String toString() {
		String stringenize = this.reverseTranslation(true)+"\t"+
							super.toString()+
							this.variable+"\t"+this.threshold+"\t"+
							this.ioput+"\t"+this.posx+"\t"+this.posy+"\t"+
							this.receptor+"\t"+this.internal+"\t";
		return stringenize;
	}
}

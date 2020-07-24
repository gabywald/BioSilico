package gabywald.biosilico.genetics;

import gabywald.biosilico.exceptions.BrainLengthException;
import gabywald.biosilico.exceptions.BrainLobeReplaceException;
import gabywald.biosilico.exceptions.GeneException;
import gabywald.biosilico.model.Brain;
import gabywald.biosilico.model.Neuron;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.structures.GeneticTranslator;

/**
 * This type of Gene is to instantiate Neuron's and create lobes in Brain created by a previous BrainGene.  <br /> 
 * Specificity occurs here for some Neuron's (tests are good to understand how it works). <br />
 * NOTE : It is a 2D implemntation of a Brain (could be extended to be 2D / 3D). 
 * @author Gabriel Chandesris (2009, 2020)
 */
public class BrainLobeGene extends GeneGattaca {
	/** Rest state of neurons. */
	private int rest;
	/** Threshold activation of neurons.  */
	private int thre;
	/** Descent from activity to rest of neurons. */
	private int desc;
	/** Minimal number of dendrites for neurons. */
	private int dendriticmin;
	/** Maximal number of dendrites for neurons. */
	private int dendriticmax;
	/** Proximity to search dendrites / connections. */
	private int prox;
	/** Reproduction property of neurons. */
	private boolean repr;
	/** Number of reproduction for neurons. */
	private int repy;
	/** Winner Take All property of neurons. */
	private boolean wta;
	/** Height of the lobe. */
	private int height;
	/** Width of the lobe. */
	private int width;
	/** Position height of the lobe in the brain. */
	private int posx;
	/** Position width of the lobe in the brain. */
	private int posy;
	/** If can replace others neurons when lobe is set. */
	private boolean replace;

	/**
	 * Main Constructor for this kind of Gattaca Gene. <br>
	 * Aim of this type of Gene is to create lobes in the brain and instantiate Neurons. 
	 * @param mutate (boolean) If Gene can be mutated. 
	 * @param duplicate (boolean) if Gene can, be duplicated. 
	 * @param delete (boolean) If Gene can be deleted. 
	 * @param activ (boolean) If Gene is globally activated. 
	 * @param age_min (int) Minimal age of activation. <b>In general is 0. </b>
	 * @param age_max (int) Maximal age of activation. <b>In general is 0. </b>
	 * @param sex (int) Sex of activation. 
	 * @param mut_rate (int) Rate of mutation of this Gene. 
	 * @param rest (int) Rest state of neurons.
	 * @param thre (int) Threshold activation of neurons.
	 * @param desc (int) Descent from activity to rest of neurons.
	 * @param dendriticmin (int) Minimal number of dendrites for neurons.
	 * @param dendriticmax (int) Maximal number of dendrites for neurons.
	 * @param prox (int) Proximity to search dendrites / connections.
	 * @param repr (boolean) Reproduction property of neurons.
	 * @param repy (int) Number of reproduction for neurons.
	 * @param wta (boolean) 'Winner Take All' property of neurons.
	 * @param height (int) Height of the lobe.
	 * @param width (int) Width of the lobe.
	 * @param posx (int) Position height of the lobe in the brain
	 * @param posy (int) Position width of the lobe in the brain.
	 * @param replace (boolean) If can replace others neurons when lobe is set.
	 * @see Brain
	 * @see BrainGene
	 * @see GeneGattaca#GeneGattaca(boolean, boolean, boolean, boolean, int, int, int, int)
	 * @see Gene#Gene(boolean, boolean, boolean, boolean, int, int, int, int)
	 */
	public BrainLobeGene(
			boolean mutate, boolean duplicate,boolean delete, boolean activ, 
			int age_min, int age_max, int sex, int mut_rate,
			int rest, int thre, int desc, int dendriticmin, int dendriticmax,
			int prox, boolean repr, int repy, boolean wta,
			int height, int width, int posx, int posy, boolean replace) {
		super(mutate, duplicate, delete, activ, age_min, age_max, sex, mut_rate);
		this.rest = rest;this.thre = thre;this.desc = desc;
		this.dendriticmin	= Gene.obtainValue(0, 999, dendriticmin);
		this.dendriticmax	= Gene.obtainValue(0, 999, dendriticmax);
		this.prox			= Gene.obtainValue(0, 999, prox);
		this.repr = repr;this.wta = wta;this.replace = replace;
		this.repy			= Gene.obtainValue(0, 999, repy);
		this.height			= Gene.obtainValue(0, 99, height);
		this.width			= Gene.obtainValue(0, 99, width);
		this.posx			= Gene.obtainValue(0, 99, posx);
		this.posy			= Gene.obtainValue(0, 99, posy);
	}
	
	public String reverseTranslation(boolean end) {
		String result		= super.reverseTranslation(false);
		StringBuilder tmp	= new StringBuilder();
		tmp.append(Gene.convert0to999(this.rest));
		tmp.append(Gene.convert0to999(this.thre));
		tmp.append(Gene.convert0to999(this.desc));
		tmp.append(Gene.convert0to999(this.dendriticmin));
		tmp.append(Gene.convert0to999(this.dendriticmax));
		tmp.append(Gene.convert0to999(this.prox));
		tmp.append((this.repr)?"0":"1"); 
		tmp.append(Gene.convert0to999(this.repy));
		tmp.append((this.wta)?"2":"3");
		tmp.append(Gene.convert0to99(this.height));
		tmp.append(Gene.convert0to99(this.width));
		tmp.append(Gene.convert0to99(this.posx));
		tmp.append(Gene.convert0to99(this.posy));
		tmp.append( (this.replace)?"4":"5");
		
		result += GeneticTranslator.reverseSequenceGattaca( tmp.toString() );
		
		//  end is given here "GGT" 
		return result+GeneticTranslator.reverseGattaca("*");
	}

	protected void exec(Organism orga) throws GeneException {
		Brain brain = orga.getBrain();
		if (brain == null) { throw new GeneException("Organism has no Brain. "); }
		
		// ***** Re-load brain lobes on each execution. Nothing if error. 
		// XXX NOTE just initiate on first execution of this gene ??
		Neuron sample = new Neuron(	this.rest, this.thre, this.desc, 
									this.dendriticmin, this.dendriticmax, 
									this.prox, this.repr, this.repy, this.wta);
		try {
			brain.setLobe(	this.height, 	this.width, 
							this.posx, 		this.posy, 
							sample, 		this.replace);
		} 
		catch (BrainLengthException e)		{ /** e.printStackTrace() */; } 
		catch (BrainLobeReplaceException e)	{ /** e.printStackTrace() */; }
	}
	
	public int getRestState()		{ return this.rest; }
	public int getThreshold()		{ return this.thre; }
	public int getDescent() 		{ return this.desc; }
	public int getDendritMin()		{ return this.dendriticmin; }
	public int getDendritMax()		{ return this.dendriticmax; }
	public int getProximity()		{ return this.prox; }
	public boolean getReproduce()	{ return this.repr; }
	public int getReproduct()		{ return this.repy; }
	public boolean getWTA()			{ return this.wta; }
	public int getLobeHeight()		{ return this.height; }
	public int getLobeWidth()		{ return this.width; }
	public int getLobePosX()		{ return this.posx; }
	public int getLobePosY()		{ return this.posy; }
	public boolean getReplace() 	{ return this.replace; }
	

	public String toString() {
		String stringenize = this.reverseTranslation(true)+"\t"+
							super.toString()+
							this.rest+"\t"+this.thre+"\t"+this.desc+"\t"+
							this.dendriticmin+"\t"+this.dendriticmax+"\t"+
							this.prox+"\t"+this.repr+"\t"+this.repy+"\t"+
							this.wta+"\t"+this.height+"\t"+this.width+"\t"+
							this.posx+"\t"+this.posy+"\t"+this.replace+"\t";
		return stringenize;
	}
}

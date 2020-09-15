package gabywald.biosilico.genetics;

import java.util.Random;

import gabywald.biosilico.exceptions.BrainLengthException;
import gabywald.biosilico.exceptions.GeneException;
import gabywald.biosilico.interfaces.IGeneMutation;
import gabywald.biosilico.model.Brain;
import gabywald.biosilico.model.BrainBuilder;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.structures.GeneticTranslator;

/**
 * This type of Gene is to instanciate a brain for organism, with specific size (2-Dimentionnal).
 * The third argument (depth) is here to differentiate this kind of GeneGattaca. 
 * Instantiation of lobes and Neurons is done with other genes. 
 * @author Gabriel Chandesris (2009, 2020)
 */
public class BrainGene extends GeneGattaca {
	/** Height of brain [00-99]. */
	private int height;
	/** Width of brain [00-99]. */
	private int width;
	/** Depth of brain [00-99]. */
	private int depth;
	/** 4th dimension of the brain [00-99]. */
	private int more;
	/** Current instance of Brain. */
	private Brain currentBrain;
	
	/**
	 * Main Constructor for this kind of Gattaca Gene. <br>
	 * Aim of this type of Gene is to instanciate a Brain into Organism. 
	 * @param mutate (boolean) If Gene can be mutated. 
	 * @param duplicate (boolean) if Gene can, be duplicated. 
	 * @param delete (boolean) If Gene can be deleted. 
	 * @param activ (boolean) If Gene is globally activated. 
	 * @param ageMin (int) Minimal age of activation. <b>In general is 0. </b>
	 * @param ageMax (int) Maximal age of activation. <b>In general is 0. </b>
	 * @param sex (int) Sex of activation. 
	 * @param mutRate (int) Rate of mutation of this Gene. 
	 * @param height (int) Height of the map of the brain. 
	 * @param width (int) Width of the map of the brain. 
	 * @param depth (int) Depth of the map of the brain (if 3D brain) [not useful].
	 * @param more (int) More dimensional / parameter of the brain [not useful]. 
	 * @see Brain
	 * @see BrainLobeGene
	 * @see GeneGattaca#GeneGattaca(boolean, boolean, boolean, boolean, int, int, int, int)
	 * @see Gene#Gene(boolean, boolean, boolean, boolean, int, int, int, int)
	 */
	public BrainGene(
			boolean mutate, boolean duplicate,boolean delete, boolean activ, 
			int ageMin, int ageMax, int sex, int mutRate,
			int height, int width, int depth, int more) {
		super(mutate, duplicate, delete, activ, ageMin, ageMax, sex, mutRate);
		this.height	= BrainGene.obtainValueSpecific(0, Brain.MAX_HEIGHT, height);
		this.width	= BrainGene.obtainValueSpecific(0, Brain.MAX_WIDTH, width);
		this.depth	= BrainGene.obtainValueSpecific(0, Brain.MAX_DEPTH, depth);
		this.more = more;
		/** Instantiation of Brain done only one time 
		 * (not at each execution of BrainGene if it is case). */
		try { this.currentBrain = BrainBuilder.brainBuilder(height, width); } 
		catch (BrainLengthException e) { this.currentBrain = null; }
	}
	
	/**
	 * Specific obtain value method for BrainGene. <br/>
	 * if val under or equal 0 : max value ; val over max : max value. 
	 * @param min (int) Minimal value. 
	 * @param max (int) Maximal value. 
	 * @param val (int) Current value of attribute. 
	 * @return (int) Minimal, maximal or value. 
	 */
	public static final int obtainValueSpecific(int min, int max, int val) { 
		return (val > max) ? max : ((val <= 0) ? max : val);
	}

	@Override
	public String reverseTranslation(boolean end) {
		String result		= super.reverseTranslation(false);
		StringBuilder tmp	= new StringBuilder();
		tmp.append(Gene.convert0to99(this.height));
		tmp.append(Gene.convert0to99(this.width));
		tmp.append(Gene.convert0to99(this.depth));
		tmp.append(Gene.convert0to99(this.more));
		
		result += GeneticTranslator.reverseSequenceGattaca( tmp.toString() );
		
		// end is given here "GGT" 
		return result+GeneticTranslator.reverseGattaca("*");
	}
	
	@Override
	protected void exec(Organism orga) throws GeneException { 
		orga.setBrain(this.currentBrain); 
	}
	
	public int getBrainHeight()	{ return this.height; }
	public int getBrainWidth()	{ return this.width; }
	public int getBrainDepth()	{ return this.depth; }
	public int getBrainMore()	{ return this.more; }
	
	@Override
	public String toString() {
		String stringenize = this.reverseTranslation(true)+"\t"+
							super.toString()+
							this.height+"\t"+this.width+"\t"+
							this.depth+"\t"+this.more+"\t";
		return stringenize;
	}

	@Override
	public Gene clone() {
		Gene toReturn = new BrainGene(	this.canMutate(), this.canDuplicate(), this.canDelete(), this.isActiv(), 
										this.getAgeMin(), this.getAgeMax(), this.getSexAct(), this.getMutationRate(), 
										this.height, this.width, this.depth, this.more);
		toReturn.setName( this.getName() );
		return toReturn;
	}

	@Override
	public void mutationChanges() {
		Random rand				= new Random();
		int selectedAttribute	= rand.nextInt(4);
		boolean moreOrLess		= rand.nextBoolean();
		switch( selectedAttribute ) {
		case(0):	this.height	= IGeneMutation.mutate(this.height, moreOrLess, 99); break;
		case(1):	this.width	= IGeneMutation.mutate(this.width, moreOrLess, 99); break;
		case(2):	this.depth	= IGeneMutation.mutate(this.depth, moreOrLess, 99); break;
		case(3):	this.more	= IGeneMutation.mutate(this.more, moreOrLess, 99); break;
		}
	}
	
}

package gabywald.biosilico.genetics;

import gabywald.biosilico.exceptions.BrainLengthException;
import gabywald.biosilico.exceptions.GeneException;
import gabywald.biosilico.model.Brain;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.structures.GeneticTranslator;

/**
 * This type of Gene is to instanciate a brain for organism, with specific size (2-Dimentionnal).
 * The third argument (depth) is here to differentiate this kind of GeneGattaca. 
 * Instantiation of lobes and Neurons is done with other genes. 
 * @author Gabriel Chandesris (2009)
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
	 * @param age_min (int) Minimal age of activation. <b>In general is 0. </b>
	 * @param age_max (int) Maximal age of activation. <b>In general is 0. </b>
	 * @param sex (int) Sex of activation. 
	 * @param mut_rate (int) Rate of mutation of this Gene. 
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
			int age_min, int age_max, int sex, int mut_rate,
			int height,int width,int depth,int more) {
		super(mutate, duplicate, delete, activ, age_min, age_max, sex, mut_rate);
		this.height = (height > Brain.getMaxHeight())?
				Brain.getMaxHeight():((height <= 0)?
						Brain.getMaxHeight():height);
		this.width = (width > Brain.getMaxWidth())?
				Brain.getMaxWidth():((width <= 0)?
						Brain.getMaxWidth():width);
		this.depth = (depth > Brain.getMaxDepth())?
				Brain.getMaxDepth():((depth <= 0)?
						Brain.getMaxDepth():depth);
		this.more = more;
		/** Instantiation of Brain done only one time 
		 * (not at each execution of BrainGene if it is case). */
		try { this.currentBrain = new Brain(height,width); } 
		catch (BrainLengthException e) { this.currentBrain = null; }
	}

	public String reverseTranslation(boolean end) {
		String result = super.reverseTranslation(false);
		String tmp = "";
		tmp += ((this.height < 10)?"0":"")+this.height;
		tmp += ((this.width < 10)?"0":"")+this.width;
		tmp += ((this.depth < 10)?"0":"")+this.depth;
		tmp += ((this.more < 10)?"0":"")+this.more;
		for (int i = 0 ; i < tmp.length() ; i++) 
			{ result += GeneticTranslator.reverseGattaca(tmp.charAt(i)+""); }
		 /** end is given here "GGT" */
		return result+GeneticTranslator.reverseGattaca("*");
	}
	
	protected void exec(Organism orga) throws GeneException 
		{ orga.setBrain(this.currentBrain); }
	
	public int getBrainHeight()	{ return this.height; }
	public int getBrainWidth()	{ return this.width; }
	public int getBrainDepth()	{ return this.depth; }
	public int getBrainMore()	{ return this.more; }
	
	
	public String toString() {
		String stringenize = this.reverseTranslation(true)+"\t"+
							super.toString()+
							this.height+"\t"+this.width+"\t"+
							this.depth+"\t"+this.more+"\t";
		return stringenize;
	}
	
}

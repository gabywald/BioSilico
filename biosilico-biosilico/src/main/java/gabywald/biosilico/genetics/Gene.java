package gabywald.biosilico.genetics;

import gabywald.biosilico.exceptions.GeneException;
import gabywald.biosilico.model.Organism;


/**
 * This class define a generic gene.<br>
 * The aim of building constructor and Getters / Setters is to instanciate a gene
 * and do not change it after that (genes can be added). 
 * @author Gabriel Chandesris (2009, 2020)
 * @see gabywald.biosilico.genetics.BiochemicalReaction
 * @see gabywald.biosilico.genetics.InitialConcentration
 */
public abstract class Gene {
	/** Default name if not attributed. */
	public static final String DEFAULT_GENE_NAME = "UnNamedGene";
	/** Gene name / identification. */
	private String name;
	/** If this gene can mutate. */
	private boolean mutate;
	/** If this gene can be duplicated. */
	private boolean duplicate;
	/** If this gene can be deleted. */
	private boolean delete;
	/** If this gene is activated. */
	private boolean activ;
	/** Minimal activation age of the gene (state from 000 to 999). */
	private int ageMin;
	/** Maximal activation age of the gene (state from 000 to 999). */
	private int ageMax;
	/** Activation sex of the gene (if sex for organism, from 000 to 999, 0 for "asexual"). */
	private int sex;
	/** Rate of mutation, duplication and deletion (if can occur). */
	private int matationRate;

	/**
	 * Main constructor of a Gene, with all elements in header.
	 * @param mutate (boolean) If Gene can be mutated. 
	 * @param duplicate (boolean) if Gene can, be duplicated. 
	 * @param delete (boolean) If Gene can be deleted. 
	 * @param activ (boolean) If Gene is globally activated. 
	 * @param ageMin (int) Minimal age of activation. 
	 * @param ageMax (int) Maximal age of activation. 
	 * @param sex (int) Sex of activation. 
	 * @param mutRate (int) Mutation rate. 
	 */
	public Gene(boolean mutate, boolean duplicate, boolean delete, boolean activ, 
				int ageMin, int ageMax, int sex, int mutRate) {
		this.mutate		= mutate;
		this.duplicate	= duplicate;
		this.delete		= delete;
		this.activ		= activ;
		this.ageMin		= Gene.obtainValue(0, 999, ageMin);
		this.ageMax		= Gene.obtainValue(0, 999, ageMax);
		this.sex		= Gene.obtainValue(0, 999, sex);
		this.matationRate = Gene.obtainValue(0, 99, mutRate);
	}
	
	/**
	 * To execute current Gene. Controls of activation are made HERE. 
	 * @param orga (Organism) Current organism. 
	 */
	public void execution(Organism orga) {
		// ***** If gene is active and age is good. 
		if ( (this.activ) && 
				( (orga.getCycle() >= this.ageMin) 
						&& (orga.getCycle() <= this.ageMax) ) ) {
			// If sex is strictly null or defined. 
			if ( (this.sex <= 0) || (this.sex == orga.getSex()) ) {
				// Execute current gene, can be excepted (nothing happened). 
				try { this.exec(orga); } 
				catch (GeneException e) { ; }
			}
		}
	}
	
	public boolean canMutate()		{ return this.mutate; }
	public boolean canDuplicate()	{ return this.duplicate; }
	public boolean canDelete()		{ return this.delete; }
	
	public boolean isActiv()		{ return this.activ; }
	public int getAgeMin()			{ return this.ageMin; }
	public int getAgeMax()			{ return this.ageMax; }
	public int getSexAct()			{ return this.sex; }
	public int getMutationRate()	{ return this.matationRate; }
	
	public void setName(String name) { this.name = name; }
	
	public String getName() {
		if (this.name == null) 
			{ return Gene.DEFAULT_GENE_NAME; }
		else { return this.name; }
	}
	
	public String toString() {
		String stringenize = 
			this.mutate+"\t"+this.duplicate+"\t"+this.delete+"\t"+
			this.activ+"\t"+this.ageMin+"\t"+this.ageMax+"\t"+
			this.sex+"\t"+this.matationRate+"\t";
		return stringenize;
	}
	
	/**
	 * The true execution of gene (specific code), excepted if bad parameters. 
	 * @param orga (Organism) variable of current organism.
	 * @throws GeneException To stop execution of current gene. 
	 */
	protected abstract void exec(Organism orga) throws GeneException;
	
	/**
	 * To apply a reverse translation to current Gene. 
	 * @param end (boolean) If end has to be set now. 
	 * @return (String) Encoded Gene.
	 */
	public abstract String reverseTranslation(boolean end);
	
	/**
	 * Creating Gene from sequence. 
	 * @param sequence (String)
	 * @return (Gene) 'null' if not recognized properly. 
	 */
	public static Gene loadGene(String sequence) {
		/** Standard genetic code application : nothing */
		if ( (sequence.matches("[aguc]+")) || (sequence.matches("[agtc]+"))  )
			{ return null; }
		/** 'Gattaca' Genetic code application. */
		if (sequence.matches("[ACTG]+")) 
			{ return GeneGattaca.getInstance(sequence); }
		/** 'Phase II' Genetic code application. */
		if (sequence.matches("[UBVP]+")) { ; }
		/** Not recognized. */
		return null;
	}
	
	/**
	 * For some int attributes which have a minimal or maximal value. 
	 * Noted final as to be optimized by compiler (considered as inline). 
	 * @param min (int) Minimal value. 
	 * @param max (int) Maximal value. 
	 * @param val (int) Current value of attribute. 
	 * @return (int) Minimal, maximal or value. 
	 */
	public static final int obtainValue(int min, int max, int val) 
		{ return (val < min)?min:((val > max)?max:val); }
	
	/**
	 * To export values. 
	 * @param value (int)
	 * @return '000' to '999'. 
	 */
	public static String convert0to999(int value) {
		return ((value < 100) ? "0" + ((value < 10) ? "0" : "" ) : "" ) + value;
	}
	
	/**
	 * To export values. 
	 * @param value (int)
	 * @return '00' to '99'. 
	 */
	public static String convert0to99(int value) {
		return ((value < 10) ? "0" : "" ) + value;
	}
	
}

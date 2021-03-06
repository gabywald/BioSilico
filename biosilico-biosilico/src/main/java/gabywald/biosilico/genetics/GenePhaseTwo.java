package gabywald.biosilico.genetics;

import gabywald.biosilico.exceptions.GeneException;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.structures.GeneticTranslator;

/**
 * Complete code gene. 
 * @author Gabriel Chandesris (2010, 2020)
 */
public class GenePhaseTwo extends Gene {
	/** Translated in a human-readable text. */
	private String translated;
	/** Reverse translated into Phase II code. */
	private String reversetra;
	/** Internal code of gene (without header). */
	private String internalCode;

	/**
	 * Basic constructor with parameters and empty internal code. 
	 * @param mutate (boolean) If Gene can be mutated. 
	 * @param duplicate (boolean) if Gene can, be duplicated. 
	 * @param delete (boolean) If Gene can be deleted. 
	 * @param activ (boolean) If Gene is globally activated. 
	 * @param ageMin (int) Minimal age of activation. 
	 * @param ageMax (int) Maximal age of activation. 
	 * @param sex (int) Sex of activation. 
	 * @param mutRate (int) Mutation rate.
	 */
	public GenePhaseTwo(boolean mutate, boolean duplicate, boolean delete,
			boolean activ, int ageMin, int ageMax, int sex, int mutRate) { 
		this(mutate, duplicate, delete, activ, ageMin, ageMax, sex, mutRate, "");
	}
	
	/**
	 * Constructor with parameters and internal code to translate. 
	 * @param mutate (boolean) If Gene can be mutated. 
	 * @param duplicate (boolean) if Gene can, be duplicated. 
	 * @param delete (boolean) If Gene can be deleted. 
	 * @param activ (boolean) If Gene is globally activated. 
	 * @param ageMin (int) Minimal age of activation. 
	 * @param ageMax (int) Maximal age of activation. 
	 * @param sex (int) Sex of activation. 
	 * @param mutRate (int) Mutation rate.
	 * @param internal (String)
	 */
	public GenePhaseTwo(boolean mutate, boolean duplicate, boolean delete,
			boolean activ, int ageMin, int ageMax, int sex, int mutRate,
			String internal) { 
		super(mutate, duplicate, delete, activ, ageMin, ageMax, sex, mutRate);
		this.setInternal(internal);
	}
	
	/**
	 * Constructor with only internal code to translate. 
	 * @param internal (String)
	 */
	public GenePhaseTwo(String internal) { 
		this(true, true, true, true, 0, 999, 0, 25, internal);
	}
	
	/**
	 * To change and translate given String to translate. 
	 * @param internal (String)
	 */
	public void setInternal(String internal) {
		this.translated		= internal;
		this.reversetra		= this.reverseTranslation(true);
	}
	
	/**
	 * To get the whole translation in Phase II code (header included); 
	 * @return (String)
	 */
	public String getTranscription()		{ return this.reversetra; }
	
	/**
	 * To get the saple translation in Phase II code (header excluded). 
	 * @return (String)
	 */
	public String getSampleTranscription()	{ return this.internalCode; }

	/** TODO dynamic code execution (auto-compilation ?). */
	@Override
	protected void exec(Organism orga) throws GeneException { ; }
	
	/**
	 * Translation method for Gene encoded in Phase II code. 
	 * @param sequence (String)
	 * @param startpos (int) Start position in sequence (just after 'M' : 0, 1 or 2).
	 * @return (String) translated sequence. 
	 * @see GeneGattaca#getInstance(String)
	 */
	public static String translation(String sequence, int startpos) {
		String result = "";
		for (int i = startpos ; 
				(i < sequence.length()) && (i+3 < sequence.length()) ; 
				i = i+4) {
			String triplet = sequence.substring(i, i+4);
			result += GeneticTranslator.translationPhaseTwo(triplet.toUpperCase());
		}
		return result;
	}

	/**
	 * To apply a reverse translation to current GeneGattaca. 
	 * @param end (boolean) If end has to be set now. 
	 * @return (String) Mostly header (if 'end' is false). 
	 */
	@Override
	public String reverseTranslation(boolean end) {
		/** Start with 'M' (begin) : "GGC" */
		String result = GeneticTranslator.reversePhaseTwo("M");
		/** true => 0 2 4 6 8 (divided by 2 rest is 0) */
		/** false => 1 3 5 7 9 (divided by 2 rest is 1) */
		result +=  (this.canMutate()) ? GeneticTranslator.reversePhaseTwo("0") 
									  : GeneticTranslator.reversePhaseTwo("1");
		result += (this.canDuplicate()) ? GeneticTranslator.reversePhaseTwo("2")
									  : GeneticTranslator.reversePhaseTwo("3"); 
		result += (this.canDelete()) ? GeneticTranslator.reversePhaseTwo("4")
									 : GeneticTranslator.reversePhaseTwo("5");
		result += (this.isActiv()) ? GeneticTranslator.reversePhaseTwo("6")
								   : GeneticTranslator.reversePhaseTwo("7");

		String agemin = Gene.convert0to999( this.getAgeMin() );
		String agemax = Gene.convert0to999( this.getAgeMax() );
		String sexact = Gene.convert0to999( this.getSexAct() );
		String mutrat = Gene.convert0to99( this.getMutationRate() );
		
		result += GeneticTranslator.reversePhaseTwo( agemin );
		result += GeneticTranslator.reversePhaseTwo( agemax );
		result += GeneticTranslator.reversePhaseTwo( sexact );
		result += GeneticTranslator.reversePhaseTwo( mutrat );
		
		// ***** adding ":" to end header. 
		result += GeneticTranslator.reversePhaseTwo(":");

		/** ================================================================ */
		/** Translate code content... */
		/** TODO 
		 * Attention aux mots-clefs (détecter, parser...)
		 * Sinon caractère par caractère... 
		 */
		this.internalCode = new String("");
		for (int i = 0 ; i < this.translated.length() ; i++) {
			char current = this.translated.charAt(i);
			result += GeneticTranslator.reversePhaseTwo(current+"");
			this.internalCode += GeneticTranslator.reversePhaseTwo(current+"");
		}
		
		/** ================================================================ */

		// ***** End with '*' (end) if ended here, only header). 
		end = true; /** Always in phase II code. */
		return (end)?result+GeneticTranslator.reversePhaseTwo("*"):result; 
	}

	@Override
	public Gene clone() {
		return new GenePhaseTwo(this.canMutate(), this.canDuplicate(), this.canDelete(), 
								this.isActiv(), this.getAgeMin(), this.getAgeMax(), this.getSexAct(), 
								this.getMutationRate(), this.translated);
	}
	
	@Override
	public void mutationChanges() {
		// TODO GenePhaseTwo.mutationChanges()
	}
}

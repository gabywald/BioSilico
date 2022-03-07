package gabywald.crypto.model;

/**
 * This class defines some elements for specific adding in encoding. 
 * <br/>TODO complete this class and encoding function in GeneticTranslationClass !
 * @author Gabriel Chandesris (2022)
 */
public class GeneticTranslatorRandomizer {
	private boolean addRandBases = false;
	private char[] alphabet = null; // this.genCode.getAlphabet();
	private int numBefore	= 0;
	private int numAfter	= 0;
	
	public GeneticTranslatorRandomizer(boolean add, char[] alpha, int before, int after) {
		this.addRandBases = add;
		this.alphabet  = alpha;
		this.numBefore = before;
		this.numAfter  = after;
	}
	
	public boolean addRandomBases() { return this.addRandBases; }
	
	public char[] getAlphabet()		{ return this.alphabet; }
	
	public int getBefore()			{ return this.numBefore; }
	public int getAfter()			{ return this.numAfter; }
}

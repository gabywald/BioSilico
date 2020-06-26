package gabywald.biosilico.structures;

import gabywald.biosilico.exceptions.GeneticTreeNodeException;

/**
 * This class is an helper for translation and retro-translation on genetic codes. 
 * @author Gabriel Chandesris (2009)
 * @see GeneticTreeNode
 */
public abstract class GeneticTranslator {
	/** For standard Genetic Code Tree. */
	private static GeneticTreeNode standard = null;
	/** For 'Gattaca' Genetic Code Tree. */
	private static GeneticTreeNode gattaca = null;
	/** For 'Phase II' Genetic Code Tree. */
	private static GeneticTreeNode phasetwo = null;
	
	/**
	 * To translate a codon (triplet) using standard code (aguc). 
	 * @param codon (String) Triplet of "aguc". 
	 * @return (String) 1-char String or empty String (if exception). 
	 * @see GeneticTreeNode#getGeneticCodeStandard()
	 * @see GeneticTreeNode#getValue(String)
	 * @see GeneticTranslator#standard
	 */
	public static String translationStandard(String codon) {
		if (GeneticTranslator.standard == null) 
			{ GeneticTranslator.standard = 
				GeneticTreeNode.getGeneticCodeStandard(); }
		try { return GeneticTranslator.standard.getValue(codon); } 
		catch (GeneticTreeNodeException e) { return ""; }
	}
	
	/**
	 * To translate a codon (triplet) using 'Gattaca' code (ACTG). 
	 * @param codon (String) Triplet of "ACTG". 
	 * @return (String) 1-char String or empty String (if exception). 
	 * @see GeneticTreeNode#getGeneticCodeGattaca()
	 * @see GeneticTreeNode#getValue(String)
	 * @see GeneticTranslator#gattaca
	 */
	public static String translationGattaca(String codon) {
		if (GeneticTranslator.gattaca == null) 
			{ GeneticTranslator.gattaca = 
				GeneticTreeNode.getGeneticCodeGattaca(); }
		try { return GeneticTranslator.gattaca.getValue(codon); } 
		catch (GeneticTreeNodeException e) { return ""; }
	}
	
	/**
	 * To translate a codon (quadruplet) using 'Phase II' code (UBVP). 
	 * @param codon (String) Quadruplet of "UBVP". 
	 * @return (String) Corresponding String or empty String (if exception). 
	 * @see GeneticTreeNode#getGeneticCodePhaseTwo()
	 * @see GeneticTreeNode#getValue(String)
	 * @see GeneticTranslator#phasetwo
	 */
	public static String translationPhaseTwo(String codon) {
		if (GeneticTranslator.phasetwo == null) 
			{ GeneticTranslator.phasetwo = 
				GeneticTreeNode.getGeneticCodePhaseTwo(); }
		try { return GeneticTranslator.phasetwo.getValue(codon); } 
		catch (GeneticTreeNodeException e) { return ""; }
	}
	
	
	/**
	 * To make a reverse translation with standard Genetic Code. 
	 * @param value (String) A 1-letter Amino-Acid representation. 
	 * @return (String) First corresponding codon or empty String (if exception). 
	 * @see GeneticTreeNode#getGeneticCodeStandard()
	 * @see GeneticTreeNode#getCode(String)
	 * @see GeneticTranslator#standard
	 */
	public static String reverseStandard(String value) {
		if (GeneticTranslator.standard == null) 
			{ GeneticTranslator.standard = 
				GeneticTreeNode.getGeneticCodeStandard(); }
		try { return GeneticTranslator.standard.getCode(value); } 
		catch (GeneticTreeNodeException e) { return ""; }
	}
	
	/**
	 * To make a reverse translation with 'Gattaca' Genetic Code. 
	 * @param value (String) A 1-letter String. 
	 * @return (String) First corresponding codon or empty String (if exception). 
	 * @see GeneticTreeNode#getGeneticCodeGattaca()
	 * @see GeneticTreeNode#getCode(String)
	 * @see GeneticTranslator#gattaca
	 */
	public static String reverseGattaca(String value) {
		if (GeneticTranslator.gattaca == null) 
			{ GeneticTranslator.gattaca = 
				GeneticTreeNode.getGeneticCodeGattaca(); }
		try { return GeneticTranslator.gattaca.getCode(value); } 
		catch (GeneticTreeNodeException e) { return ""; }
	}
	
	/**
	 * To make a reverse translation with 'Phase II' Genetic Code. 
	 * @param value (String) A value in Phase II end values. 
	 * @return (String) First corresponding codon or empty String (if exception). 
	 * @see GeneticTreeNode#getGeneticCodePhaseTwo()
	 * @see GeneticTreeNode#getCode(String)
	 * @see GeneticTranslator#phasetwo
	 */
	public static String reversePhaseTwo(String value) {
		if (GeneticTranslator.phasetwo == null) 
			{ GeneticTranslator.phasetwo = 
				GeneticTreeNode.getGeneticCodePhaseTwo(); }
		try { return GeneticTranslator.phasetwo.getCode(value); } 
		catch (GeneticTreeNodeException e) { return ""; }
	}
}

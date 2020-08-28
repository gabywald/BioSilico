package gabywald.cellmodel.model;

import gabywald.cellmodel.exceptions.TripletException;

/**
 * This class to treat 3-char length String (codons) of 'biological nucleotidic sequences'. 
 * @author Gabriel Chandesris (2009)
 */
public class Triplet {
	/** The current 3-chars. */
	private String codon;
	
	/**
	 * Constructor. 
	 * @param codon (String) a 3-chars length. 
	 * @throws TripletException In case of not-long enough String.
	 */
	public Triplet(String codon) throws TripletException {
		if (codon.length() == 3) { this.codon = codon.toUpperCase(); }
		if (codon.length() > 3) { this.codon = codon.substring(0,3).toUpperCase(); }
		if (codon.length() < 3) { throw new TripletException("Codon a length under 3. "); }
	}
	
	/**
	 * To get the 3-chars. 
	 * @return (String)
	 */
	public String getCodon() { return this.codon; }
	
	/**
	 * Translate a given Triplet to the amino-acid (standard genetic code). 
	 * @param elt (Triplet)
	 * @return (char) code of Amino-Acid. 
	 */
	public static char standardGeneticCode(Triplet elt) {
		if (elt.getCodon().equals("GCU")) { return 'A'; }
		if (elt.getCodon().equals("GCC")) { return 'A'; }
		if (elt.getCodon().equals("GCA")) { return 'A'; }
		if (elt.getCodon().equals("GCG")) { return 'A'; }
		if (elt.getCodon().equals("CGU")) { return 'R'; }
		if (elt.getCodon().equals("CGC")) { return 'R'; }
		if (elt.getCodon().equals("CGA")) { return 'R'; }
		if (elt.getCodon().equals("CGG")) { return 'R'; }
		if (elt.getCodon().equals("AGA")) { return 'R'; }
		if (elt.getCodon().equals("AGG")) { return 'R'; }
		if (elt.getCodon().equals("AAU")) { return 'N'; }
		if (elt.getCodon().equals("AAC")) { return 'N'; }
		if (elt.getCodon().equals("GAU")) { return 'D'; }
		if (elt.getCodon().equals("GAC")) { return 'D'; }
		if (elt.getCodon().equals("UGU")) { return 'C'; }
		if (elt.getCodon().equals("UGC")) { return 'C'; }
		if (elt.getCodon().equals("CAA")) { return 'Q'; }
		if (elt.getCodon().equals("CAG")) { return 'Q'; }
		if (elt.getCodon().equals("GAA")) { return 'E'; }
		if (elt.getCodon().equals("GAG")) { return 'E'; }
		if (elt.getCodon().equals("GGU")) { return 'G'; }
		if (elt.getCodon().equals("GGC")) { return 'G'; }
		if (elt.getCodon().equals("GGA")) { return 'G'; }
		if (elt.getCodon().equals("GGG")) { return 'G'; }
		if (elt.getCodon().equals("CAU")) { return 'H'; }
		if (elt.getCodon().equals("CAC")) { return 'H'; }
		if (elt.getCodon().equals("AUU")) { return 'I'; }
		if (elt.getCodon().equals("AUC")) { return 'I'; }
		if (elt.getCodon().equals("AUA")) { return 'I'; }
		if (elt.getCodon().equals("UUA")) { return 'L'; }
		if (elt.getCodon().equals("UUG")) { return 'L'; }
		if (elt.getCodon().equals("CUU")) { return 'L'; }
		if (elt.getCodon().equals("CUC")) { return 'L'; }
		if (elt.getCodon().equals("CUA")) { return 'L'; }
		if (elt.getCodon().equals("CUG")) { return 'L'; }
		if (elt.getCodon().equals("AAA")) { return 'K'; }
		if (elt.getCodon().equals("AAG")) { return 'K'; }
		if (elt.getCodon().equals("AUG")) { return 'M'; } // start
		if (elt.getCodon().equals("UUU")) { return 'F'; }
		if (elt.getCodon().equals("UUC")) { return 'F'; }
		if (elt.getCodon().equals("CCU")) { return 'P'; }
		if (elt.getCodon().equals("CCC")) { return 'P'; }
		if (elt.getCodon().equals("CCA")) { return 'P'; }
		if (elt.getCodon().equals("CCG")) { return 'P'; }
		if (elt.getCodon().equals("UCU")) { return 'S'; }
		if (elt.getCodon().equals("UCC")) { return 'S'; }
		if (elt.getCodon().equals("UCA")) { return 'S'; }
		if (elt.getCodon().equals("UCG")) { return 'S'; }
		if (elt.getCodon().equals("AGU")) { return 'S'; }
		if (elt.getCodon().equals("AGC")) { return 'S'; }
		if (elt.getCodon().equals("ACU")) { return 'T'; }
		if (elt.getCodon().equals("ACC")) { return 'T'; }
		if (elt.getCodon().equals("ACA")) { return 'T'; }
		if (elt.getCodon().equals("ACG")) { return 'T'; }
		if (elt.getCodon().equals("UGG")) { return 'W'; }
		if (elt.getCodon().equals("UAU")) { return 'Y'; }
		if (elt.getCodon().equals("UAC")) { return 'Y'; }
		if (elt.getCodon().equals("GUU")) { return 'V'; }
		if (elt.getCodon().equals("GUC")) { return 'V'; }
		if (elt.getCodon().equals("GUA")) { return 'V'; }
		if (elt.getCodon().equals("GUG")) { return 'V'; }
		if (elt.getCodon().equals("UAG")) { return '*'; }
		if (elt.getCodon().equals("UGA")) { return '*'; }
		if (elt.getCodon().equals("UAA")) { return '*'; }
		return '-';
	}
}


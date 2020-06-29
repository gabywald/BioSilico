package gabywald.cellmodel.model;

/**
 * This class to represent Object which have sequence inside. 
 * @author Gabriel Chandesris (2008) 
 * @see ARN
 * @see Chromosome
 * @see Protein
 */
public class SequencableEntity {
	/** The sequence inside. */
	protected String sequence;
	
	/** 
	 * To get the length of the sequence.
	 * @return (int) seuqence.length()
	 */
	public int length() { return this.sequence.length(); }
	
	/** 
	 * To get a part of the sequence. 
	 * @param begin (int)
	 * @param end (int)
	 * @return (String) sub-sequence. 
	 */
	public String getSubsequence(int begin,int end) {
		if (begin > end) { int tmp = begin;begin = end;end = tmp; }
		if ( (begin < 0) || (end > this.sequence.length()) ) { return ""; }
		return this.sequence.substring(begin,end);
	}
	
	/**
	 * To get the whole sequence. 
	 * @return (String)
	 */
	public String getSequence() { return this.sequence; }
}

package gabywald.crypto.model;

import java.util.Vector;

/**
 * Aim of this class is to ensure a Genetic Encryption according to a selected code. 
 * @author Gabriel Chandesris (2011, 2022, 2025)
 */
public class GeneticTranslator {
	/** Genetic code selected. */
	private GeneticCode genCode;
	/** Encoding tree selected. */
	private EncodingNode root;
	/** index of the ncbi code (if any). */
	private int ncbiIndex;
	/** Reference or not of the ncbi index (if apply). */
	private boolean refOrNot;

	/**
	 * Constructor with NCBI Genetic codes. 
	 * @param ncbi (int) index of the code. 
	 * @param ref (boolean) if taking from the reference (true) of from the features (false). 
	 */
	public GeneticTranslator(int ncbi, boolean ref) {
		GeneticCode[] table = null;
		this.ncbiIndex	= ncbi;
		this.refOrNot	= ref;
		if (ref)	{ table = GeneticCode.readFromNCBIG01(); } 
		else		{ table = GeneticCode.readFromNCBIG02(); }
		/** TODO [DP builder] make exception if (ncbiIndex < 0) or ( ncbiIndex > table.length) ?? */
		this.genCode	= table[ncbi];
		this.root		= this.genCode.makeAsEncodingNode();
	}
	
	/**
	 * Constructor with a given instance of GeneticCode. 
	 * @param code (GeneticCode)
	 */
	public GeneticTranslator(GeneticCode code) {
		this.genCode	= code;
		this.root		= this.genCode.makeAsEncodingNode();
	}
	
	/**
	 * Constructor with a given instance of EncodingNode (root of tree). 
	 * @param tree (EncodingNode)
	 */
	public GeneticTranslator(EncodingNode tree) {
		this.root		= tree.getRoot();
		this.genCode	= GeneticCode.fromEncodingNode(this.root);
	}
	
	/**
	 * To decode a sequence. 
	 * <br><i>start</i> and <i>frame</i> both to 0 if decode from beginning. 
	 * @param sequence (String)
	 * @param start (int) start point
	 * @param frame (int) which 'modificator' / reading frame. 
	 * @return (String) decoded
	 */
	public String decode(String sequence, int start, int frame) {
		int codonLength	= this.genCode.getCodonLength();
		if ( (frame < 0) || (frame > codonLength-1) ) 
			{ return new String(""); }
		int index		= start + frame;
		String result	= new String("");
		for (int i = index ; i <= (sequence.length() - codonLength) ; i += codonLength) {
			String codon = new String("");
			for (int j = 0 ; j < codonLength ; j++) 
				{ codon += sequence.charAt(j+i); }
			result += ((codon.equals(""))?"-":this.root.getValue(codon));
		}
		return result;
	}
	
	/**
	 * To decode a sequence, according to start / stop codons (and only between them). 
	 * <br><i>start</i> and <i>frame</i> both to 0 if decode from beginning. 
	 * @param sequence (String)
	 * @param start (int) start point
	 * @param frame (int) which 'modificator' / reading frame. 
	 * @return (String)
	 */
	public String decodeWithStartStopCodons(String sequence, int start, int frame) {
		boolean started	= false;
		int codonLength	= this.genCode.getCodonLength();
		if ( (frame < 0) || (frame > codonLength-1) ) 
			{ return new String(""); }
		int index		= start + frame;
		String result	= new String("");
		for (int i = index ; i <= (sequence.length() - codonLength) ; i += codonLength) {
			String codon = new String("");
			for (int j = 0 ; j < codonLength ; j++) 
				{ codon += sequence.charAt(j+i); }
			if (started) {
				started = (!this.root.getNode(codon).isStop());
				if (this.root.getNode(codon).isStop()) 
					{ return result; }
				result += ((codon.equals(""))?"-":this.root.getValue(codon));
			} else { started = this.root.getNode(codon).isStart(); }
		}
		return result;
	}

	/**
	 * Encoding given sequence and add a 'start' and an 'stop' n-uplet / codon. 
	 * <br>If asked, add randomly generated chars from Genetic Code. 
	 * @param sequence (String) whet to encode. 
	 * @param which (int) 0 : simple encoding ; 1 : More encoding ; 2 (default) : random encoding. 
	 * @return (String)
	 */
	public String encode(String sequence, int which) 
		{ return this.encode(sequence, which, null); }
	
	/**
	 * Encoding given sequence and add a 'start' and an 'stop' n-uplet / codon. 
	 * <br>If asked, add randomly generated chars from Genetic Code. 
	 * @param sequence (String) whet to encode. 
	 * @param which (int) 0 : simple encoding ; 1 : More encoding ; 2 (default) : random encoding. 
	 * @param gtr (GeneticTranslatorRandomizer) to add random bases at begin and end...
	 * @return (String)
	 * TODO avoid "no encoding" when a character is not in 'AA' list"...
	 */
	public String encode(String sequence, int which, GeneticTranslatorRandomizer gtr) {
		String toReturn = new String("");
		
		switch(which) {
		case(0):toReturn = this.encode(sequence);break;
		case(1):toReturn = this.encodeMore(sequence);break;
		case(2):/** toReturn = this.encodeRand(sequence);break; */
		default:toReturn = this.encodeRand(sequence);break;
		}
		
		/** Searching a START / STOPPER and add one of each. */
		// ***** List the possible start and stop codons. 
		Vector<EncodingNode> starters = new Vector<EncodingNode>();
		Vector<EncodingNode> stoppers = new Vector<EncodingNode>();
		EncodingNode[] leaves = this.root.getLeaves();
		for (int i = 0 ; i < leaves.length ; i++) {
			if (leaves[i].isStart())	{ starters.add(leaves[i]); }
			if (leaves[i].isStop())		{ stoppers.add(leaves[i]); }
		}
		// ***** Choose randomly start and stop codons. 
		if ( (starters.size() != 0) && (stoppers.size() != 0) ) {
			int selectedStart = (int)Math.rint(Math.random() * starters.size());
			while (selectedStart == starters.size()) 
				{ selectedStart	= (int)Math.rint(Math.random() * starters.size()); }
			int selectedStopp = (int)Math.rint(Math.random() * stoppers.size());
			while (selectedStopp == stoppers.size()) 
				{ selectedStopp	= (int)Math.rint(Math.random() * stoppers.size()); }
			
			toReturn = starters.get(selectedStart).getCurrentCode()
						+ toReturn + 
						stoppers.get(selectedStopp).getCurrentCode();
		}
		
		/** TODO Adding random characters before and after the encoded sequence. */
		if ( (gtr != null) && (gtr.addRandomBases()) ) {
//			char[] alphabet = gtr.getAlphabet(); // this.genCode.getAlphabet();
//			gtr.getBefore();
//			gtr.getAfter();
			// TODO adding random characters at begin and end of the encoded sequence
			// how to returning it for decoding purposes ?
			// then put it into an 'CDS information' tag or 'TATA box' position !?
		}
		
		return toReturn;
	}
	
	/** TODO encoding ; take care if any "more than one character"... */
	/** TODO encoding ; add random chars from bases at beginning and end 
	 * 		(and retain where is ORF / which frame [0-n] is start codon). */
	
	/**
	 * Simply encode with first encounter occurrence of 'AA' in the Genetic Code. 
	 * @param sequence (String)
	 * @return (String) Encoded sequence. 
	 */
	public String encode(String sequence) {
		String inWork		= new String(sequence);
		String result 		= new String("");
		String[] valuesList	= this.root.getValuesAA();
		
		while (inWork.length() > 0) {
			int len = inWork.length();
			for (int i = 0 ; i < valuesList.length ; i++) {
				if (inWork.startsWith(valuesList[i])) {
					result += this.root.getLeaves()[i].getCurrentCode();
					inWork = inWork.substring(valuesList[i].length());
					break;
				}
			}
			if (inWork.length() == len) 
				{ return result; }
		}
		return result;
	}
	
	/**
	 * Encode with first then next encounter occurrence of 'AA' in the Genetic Code. 
	 * @param sequence (String)
	 * @return (String) Encoded sequence. 
	 */
	public String encodeMore(String sequence) {
		String inWork = new String(sequence);
		String result = new String("");
		String[] valuesList = this.root.getValuesAA();
		
		while (inWork.length() > 0) {
			int len = inWork.length();
			for (int i = 0 ; i < valuesList.length ; i++) {
				/** 'aagg' et 'aatc' */
				if (inWork.startsWith(valuesList[i])) {
					result += this.root.getLeaves()[i].getCurrentCode();
					inWork = inWork.substring(valuesList[i].length());
					/** break; */ /** !! */
				}
			}
			if (inWork.length() == len) 
				{ return result; }
		}
		return result;
	}
	
	/**
	 * Encode with a random codon for each 'AA' in its set if it has much codons. 
	 * @param sequence (String)
	 * @return (Strring)
	 */
	public String encodeRand(String sequence) {
		String inWork = new String(sequence);
		String result = new String("");
		String[] valuesList = this.root.getValuesAA();
		
		while (inWork.length() > 0) {
			int len = inWork.length();
			for (int i = 0 ; i < valuesList.length ; i++) {
				if (inWork.startsWith(valuesList[i])) {
					/** Getting possible codons for this value. */
					Vector<EncodingNode> setOfSameValues = new Vector<EncodingNode>();
					EncodingNode[] leaves = this.root.getLeaves();
					for (int j = 0 ; j < leaves.length ; j++) {
						if (leaves[j].getCurrentValue().equals(valuesList[i])) 
							{ setOfSameValues.add(leaves[j]); }
					}
					
					int seizing		= setOfSameValues.size();
					int choice		= (int)Math.rint(Math.random() * seizing);
					while (choice == seizing) 
						{ choice	= (int)Math.rint(Math.random() * seizing); }
					result += setOfSameValues.get(choice).getCurrentCode();
					inWork = inWork.substring(valuesList[i].length());
					break;
				}
			}
			if (inWork.length() == len) 
				{ return result; /** return "!! !! !! !! \n\n"+result+"\n\n"+inWork; */ }
		}
		return result;
	}
	
	public String toString() {
		String toReturn = new String("");
		
		toReturn += "\tGenetic Translator (ncbi : '"
			+this.genCode.getID()+"' -- index : '"
			+this.ncbiIndex+"' -- Ref. : 'NCBI"
			+((this.refOrNot)?"01":"02")+"')\n";
		
		toReturn += ((this.refOrNot)?this.genCode.toStringNCBI01():
									 this.genCode.toStringNCBI02())+"\n";
		/** toReturn += EncodingNode.treeView(this.root)+"\n"; */
		return toReturn;
	}
	
}

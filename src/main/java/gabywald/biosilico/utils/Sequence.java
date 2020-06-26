package gabywald.biosilico.utils;

/**
 * This class to define a nucleic sequence / <u>alignment</u> 
 * in different output format. 
 * @author Gabriel Chandesris (2008-2010)
 */
public class Sequence {
	/** Name of the sequence */
	private String name;
	/** Sequence length, without gap / indel count. */
	private int length;
	/** Alignment length. */
	private int lengthAlign;
	/** Base tabled / formatted sequence without gap's/ indel's. */
	private Base[] sequenceBase;
	/** Char tabled / formatted sequence without gap's / indel's. */
	private char[] sequenceChar;
	/** Original String alignment / sequence. */
	private String sequenceString;

	/** Default constructor (empty name and sequence). */
	public Sequence() 
		{ this.name = "";this.putSequence(""); }

	/**
	 * Name and whole sequence constructor.  <br>
	 * Sequence will be uppercase, number of nucleotides will be counted
	 * when sequence is put in memory. 
	 * @param nom String
	 * @param sequence String
	 * @see Sequence#putSequence(String)
	 */
	public Sequence(String name,String sequence){
		this.name = name;
		this.putSequence(sequence); 
	}

	/**
	 * Factorization of input in memory of the sequence. 
	 * @param sequence (String)
	 * @see Sequence#Sequence(String, String)
	 */
	private void putSequence (String sequence) {
		sequence = sequence.toUpperCase();
		/** Reading base number of sequence. */
		char letter;
		int l = sequence.length();
		int length = 0;
		for (int i = 0 ; i < l ; i++) {
			letter = sequence.charAt(i);
			if (Base.isBase(letter)) { length++; }
		}
		this.length = length;
		this.lengthAlign = sequence.length();
		/** Stockage de la sequence dans un tableau. */
		this.sequenceBase = new Base[this.length];
		this.sequenceChar = new char[this.length];
		this.sequenceString = sequence;
		int i = 0,j = 0,k = 0;
		while (i < this.length){
			letter = sequence.charAt(k);
			if (Base.isBaseOrGap(letter)){
				if (Base.isBase(letter)){
					this.sequenceBase[i] = new Base(letter,j);
					this.sequenceChar[i] = letter;
					i++; /** Count number of bases / chars. */
				}
				j++; /** Count real nucleotids / gaps. */
			}
			k++; /** Count real length of sequence submitted.  */
		}
	}

	public void setNom(String name) { this.name = name; }
	public String getNom() { return this.name; }
	public int length() { return this.length; }
	public int lengthAlign() { return this.lengthAlign; }
	public String getSequence() { return this.sequenceString; }
	public Base[] getSequenceBase() { return this.sequenceBase; }
	public String getSequenceToString() { return this.getSequenceBaseToString(); }
	public String getSequenceBaseToString() { 
		String sequenceString = "";
		/** pourrait aussi etre : i < this.longueur et += this.getBaseChar(i)... */
		for (int i = 0 ; i < this.sequenceBase.length ; i++ ) 
		{ sequenceString += this.sequenceBase[i].getBase(); }
		return sequenceString; 
	}
	public char[] getSequenceChar() { return this.sequenceChar; }
	public String getSequenceCharToString() { 
		String sequenceString = "";
		/** pourrait aussi etre : i < this.longueur et += this.getBaseChar(i)... */
		for (int i = 0 ; i < this.sequenceChar.length ; i++ ) 
		{ sequenceString += this.sequenceChar[i]; }
		return sequenceString; 
	}

	/**
	 * To get a subsequence of the sequence (not of the alignment), 
	 * start from 0 (positions 'begin' AND 'end' are included).
	 * @param begin (int) Beginning of the sequence in the sequence. 
	 * @param end (int) End of the subsequence in the sequence. 
	 * @return Base[] (more useful for informations)
	 * @see Sequence#convertBasesToString(Base[])
	 */
	public Base[] getSubSequence(int begin, int end){
		Base[] subseq;
		if (begin < 0) { begin = 0; }
		if (end > this.length) { end = this.length; }
		if ( (begin > end) && (end != 0) ) 
			{ int tmp = end;end = begin;begin = tmp; } 
		/** subsequence empty in case of an empty sequence 
		 * or a gappy alignment ('-----------------------'). */
		if ( (begin == end) || (end == 0) ) { subseq = new Base[0]; }
		else { 
			subseq = new Base[end-begin+1];
			for (int i = begin ; i <= end ; i++)
			{ subseq[i-begin] = this.sequenceBase[i]; }
		}
		return subseq;
	}

	/**
	 * To get a String from a Base[] (maybe useful after getting a subsequence). 
	 * @param subsequence Base[] of subsequence (for example).
	 * @return String of the subsequence. 
	 * @see Sequence#getSubSequence(int, int)
	 */
	public static String convertBasesToString(Base[] subsequence) {
		String subseq = "";
		for (int i = 0 ; i < subsequence.length ; i++) 
			{ subseq += subsequence[i].getBase(); }
		return subseq;
	}

	/**
	 * To get the Base at position [position] in the table of Base's. 
	 * @param position int
	 * @return Base
	 * @see Base
	 * @see Sequence#sequenceBase
	 */
	public Base getBase(int position) { return this.sequenceBase[position]; }

	/**
	 * To get the character at position [position] in the table of character's.
	 * @param position int
	 * @return char
	 * @see Sequence#sequenceChar
	 */
	public char getBaseIn(int position) { return this.sequenceChar[position]; }

	/**
	 * To get the character at position [position] in the alignment. 
	 * If position is under 0 : position is considered as 0.
	 * If position upper than alignment length : position of last char 
	 * in alignment. 
	 * @param position int
	 * @return char
	 * @see Sequence#sequenceString
	 */
	public char getBaseAt(int position) {
		if (position < 0) { position = 0; }
		if (position >= this.lengthAlign) { position = this.lengthAlign-1; }
		return this.sequenceString.charAt(position); 
	}

	/**
	 * To get the char of the Base at position [position] in the Base table. 
	 * @param position int
	 * @return char
	 * @see Base#getBase()
	 * @see Sequence#sequenceBase
	 */
	public char getBaseChar(int position) 
		{ return this.sequenceBase[position].getBase(); }

	/**
	 * To get the original position of the Base at position [position]
	 * in the Base table. If under 0 : 0. If more than length of sequence : 
	 * position of last element in alignment. 
	 * @param position int
	 * @return int (original position in alignment)
	 * @see Base#getPosition()
	 * @see Sequence#sequenceBase
	 * @see Sequence#lengthAlign
	 */
	public int getBasePos(int position) { 
		if (position < 0) { return 0; }
		if (position >= this.sequenceBase.length) { return this.lengthAlign-1; }
		return this.sequenceBase[position].getPosition(); 
	}

	/**
	 * Sum the percentage of each nucleotide. 
	 * @return String
	 */
	public String pourcentageBases(){     	
		String res = "";
		double[] tab = new double[] {0.0,0.0,0.0,0.0};
		for(int i = 0 ; i < this.length() ; i++){
			if (this.getBaseChar(i) == 'A') { tab[0]++; }
			if (this.getBaseChar(i) == 'C') { tab[1]++; }
			if (this.getBaseChar(i) == 'G') { tab[2]++; }
			if ( (this.getBaseChar(i) == 'U')
					|| (this.getBaseChar(i) == 'T') ) { tab[3]++; }
		}
		double total = tab[0]+tab[1]+tab[2]+tab[3];
		if (total == 0.0) { total = 1; }
		res = (tab[0]/(total))*100+" % A - "+(tab[1]/(total))*100+" % C  - "
		+(tab[2]/(total))*100+" % G  - "+(tab[3]/(total))*100+" % U ";
		return res;
	}

	/**
	 * To remove a subSequence to the Base and Char Sequence,
	 * then including of alignment (removing bases replacing with gap's). 
	 * Useful for removing begin and end of palindrome of initial sequence. 
	 * Beginning of sequence is [0], consider sequence and not alignment !.  
	 * @param begin (int) Beginning of removing (excluded). 
	 * @param end (int) End of removing (excluded). 
	 */
	public void removeSubSequence(int begin,int end) {
		if (begin < 0) { begin = 0; }
		if (end > this.length) { end = this.length; }
		if ( (begin > end) && (end != 0) ) 
			{ int tmp = end;end = begin;begin = tmp; }
		if ( (begin >= 0) && (end < this.length) ) {
			int decalage = (end-begin+1);
			Base[] newBaseSeq = new Base[this.length-decalage];
			char[] newCharSeq = new char[this.length-decalage];
			int j = 0;
			/** performing the new sequences */
			for (int i = 0 ; i < this.length ; i++) {
				if (i < begin) {
					newBaseSeq[j] = this.sequenceBase[i];
					newCharSeq[j] = this.sequenceChar[i];
					j++;
				}
				if (i > end) {
					newBaseSeq[j] = this.sequenceBase[i];
					/** int oldPos = this.sequenceBase[i].getPosition(); */
					/** newBaseSeq[j].setPosition(oldPos-decalage); */
					newCharSeq[j] = this.sequenceChar[i];
					j++;
				}
			}
			/** performing the new alignment */
			String newAlign = "";
			int pos = 0,i = 0;
			while (i < newBaseSeq.length) {
				char chartmp = newBaseSeq[i].getBase();
				if (pos < newBaseSeq[i].getPosition()) 
					{ pos++;newAlign += "-"; }
				else {
					newAlign += chartmp;
					pos++;i++; 
				}
			}
			this.putSequence(newAlign);
		}
	}

	/**
	 * To know if instance of the class and an other are the same. 
	 * @param toCompare An other Sequence.
	 * @return boolean
	 */
	public boolean equals(Sequence toCompare) {
		/** if (!this.nom.equals(toCompare.getNom())) { return false; } */
		if (this.length != toCompare.length()) { return false; }
		if (this.lengthAlign != toCompare.lengthAlign()) { return false; }
		if (!this.sequenceString.equals(toCompare.getSequence())) 
			{ return false; }
		return true;
	}

	/**
	 * To get a copy of the current Sequence (only name and sequence).
	 * @return (Sequence)
	 */ 
	public Sequence getCopy() {
		Sequence nouvelleSequence = 
			new Sequence(this.name+"**clone",this.getSequence());
		return nouvelleSequence;
	}
	
	public String toString() 
		{ return ">"+this.name+"\n"+this.sequenceString+"\n"; }

}


/**
 * Model (MVC). 
 */
package gabywald.biosilico.utils;

/**
 * A class for codification and input memory sequences. At start, it was only 
 * for cible sequences (original sequences were used as test char sequences in some
 * parts of algorithms - profil computed data). 
 * @author Gabriel Chandesris (2008)
 * @see Sequence
 */
public class Base {
	private char acgt;
	private int position;

	/**
	 * Standard constructor to record a sequence in Base structure 
	 * without gaps for example. (structure could be table, List, Vector...). 
	 * @param base char
	 * @param position int
	 */
	public Base (char base, int position) {
		this.acgt = base;
		this.position = position;
	}


	public char getBase ()				{ return this.acgt; }
	public int getPosition ()			{ return this.position; }

	public void setBase (char base)		{ this.acgt = base; }
	public void setPosition (int pos)	{ this.position = pos; }


	/**
	 * To know if instance of the class and an other are the same. 
	 * @param toCompare An other Base.
	 * @return boolean
	 */
	public boolean equals(Base toCompare) {
		if (this.acgt != toCompare.getBase())			{ return false; }
		if (this.position != toCompare.getPosition())	{ return false; }
		return true;
	}

	/**
	 * To test if a char symbolize a nucleotids, 
	 * all nucleotidic code is tested as UPPERCASE. 
	 * <br>Table below is given as information, only <b>bold bases</b>
	 * are used in computing in algorithms and should be completed. 
	 * <br><table>
	 * <tr><td><b>Adenine</b></td><td><b>A</b></td></tr>
	 * <tr><td><b>Thymine</b></td><td><b>T</b></td></tr>
	 * <tr><td><b>Guanine</b></td><td><b>G</b></td></tr>
	 * <tr><td><b>Cytosine</b></td><td><b>C</b></td></tr>
	 * <tr><td><b>Uracile</b></td><td><b>U</b></td></tr>
	 * <tr><td><i>Inosine</i></td><td><i>I</i></td></tr>
	 * <tr><td><b>Purine</b> : Adenine or Guanine</td><td><b>R</b></td></tr>
	 * <tr><td><b>Pyrimidine</b> : Thymine or Cytosine</td><td><b>Y</b></td></tr>
	 * <tr><td><u>Weak Hydrogen bonds</u> : Adenine or Thymine</td><td>W</td></tr>
	 * <tr><td><u>Keto in major groove</u> : Thymine or Guanine</td><td>K</td></tr>
	 * <tr><td><u>Strong hydrogen bonds</u> : Cytosine or Guanine</td><td>S</td></tr>
	 * <tr><td><u>Amino in major groove</u> : Adenine or Cytosine</td><td>M</td></tr>
	 * <tr><td>Not Adenine</td><td>B</td></tr>
	 * <tr><td>Not Thymine</td><td>V</td></tr>
	 * <tr><td>Not Cytosine</td><td>D</td></tr>
	 * <tr><td>Not Guanine</td><td>H</td></tr>
	 * <tr><td><b>Any</b></td><td><b>N</b></td></tr>
	 * <tr><td><b><i>Gap / Indel</i></b></td><td><b><i>-</i></b></td></tr>
	 * </table>
	 * @param base char
	 * @return boolean
	 */
	public static boolean isBase(char base) {
		String regExpBases = "^[ACGTUIRYWKSMBVDHN]$";
		String baseString = base+"";
		if (baseString.matches(regExpBases)) { return true; }
		else { return false; }
	}

	/**
	 * To test if nucleotids or gap / indel symbolized with '-'.  
	 * @param base char
	 */
	public static boolean isBaseOrGap(char base) {
		if ( (Base.isBase(base)) || (base == '-') ) { return true; }
		else { return false; }
	}



	/**
	 * Score for two nuleotids (char):  Watson-Cricks and Wooble accepted. 
	 * <br> 1 pour GU/UG ; 2 pour AU/UA ; 3 pour CG/GC. 
	 * @param x char
	 * @param y char
	 * @return int
	 */
	public static int scoreBases(char x, char y) {
		int sc = 0;
		if ((x == 'A') && (y == 'T') || (x == 'T') && (y == 'A')) { return 2; }
		if ((x == 'A') && (y == 'U') || (x == 'U') && (y == 'A')) { return 2; }
		if ((x == 'C') && (y == 'G') || (x == 'G') && (y == 'C')) { return 3; }
		if ((x == 'G') && (y == 'U') || (x == 'U') && (y == 'G')) { return 1; }
		return sc;
	}

	/**
	 * To know if two nucleotids (char) can go together.
	 * @param x char
	 * @param y char
	 * @return boolean
	 */
	public static boolean matchBases(char x, char y) {
		if ( (x == 'A') && (y == 'U')
				|| (x == 'C') && (y == 'G')
				|| (x == 'G') && (y == 'C')
				|| (x == 'U') && (y == 'A')
				|| (x == 'A') && (y == 'T')
				|| (x == 'T') && (y == 'A')
				|| (x == 'G') && (y == 'U')
				|| (x == 'U') && (y == 'G') ) {
			return true;
		} else { return false; }
	}

	/**
	 * To know if two nucleotids (char) can go together (including gap's).  
	 * @param x char
	 * @param y char
	 * @return boolean
	 */
	public static boolean matchBasesGap(char x, char y) {
		if ( (Base.matchBases(x,y)) || ( (x == '-') && ( y == '-') ) ) { return true; }
		else { return false; }
	}

	/**
	 * To know if two Base's can go together.  
	 * @param x Base
	 * @param y Base
	 * @return boolean
	 */
	public static boolean matchBases(Base x, Base y) 
		{ return Base.matchBases(x.getBase(), y.getBase()); }

	/**
	 * To know if two Base's can go together (including gap's). 
	 * @param x Base
	 * @param y Base
	 * @return boolean
	 */
	public static boolean matchBasesGap(Base x, Base y) 
		{ return Base.matchBasesGap(x.getBase(), y.getBase()); }

}

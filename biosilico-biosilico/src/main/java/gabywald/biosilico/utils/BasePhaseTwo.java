package gabywald.biosilico.utils;

/**
 * This class to limit to letters {U,B,V,P}. 
 * Special reference to Nash Vol. 3 "La reine des anges"
 * @author Gabriel Chandesris (2009)
 */
public class BasePhaseTwo extends Base {

	public BasePhaseTwo(char base, int position) 
		{ super(base, position); }
	
	/**
	 * To test if a char symbolize a nucleotids, 
	 * all nucleotidic code is tested as UPPERCASE. 
	 * <br>Table below is given as information, only <b>bold bases</b>
	 * are used in computing in algorithms and should be completed. 
	 * <br><table>
	 * <tr><td><b>Uracine</b></td><td><b>U</b></td></tr>
	 * <tr><td><b>Betamine</b></td><td><b>B</b></td></tr>
	 * <tr><td><b>Varinine</b></td><td><b>V</b></td></tr>
	 * <tr><td><b>Polamine</b></td><td><b>P</b></td></tr>
	 * @param base char
	 * @return boolean
	 */
	public static boolean isBase(char base){
		String regExpBases = "^[UBVP]$";
		String baseString = base+"";
		if (baseString.matches(regExpBases)) { return true; }
		else { return false; }
	}

	/**
	 * To test if nucleotids or gap / indel symbolized with '-'.  
	 * @param base char
	 */
	public static boolean isBaseOrGap(char base){
		if ( (BasePhaseTwo.isBase(base)) || (base == '-') ) { return true; }
		else { return false; }
	}

	
	/**
	 * To know if two nucleotids (char) can go together.
	 * U and V are acids ; B and P are amins. 
	 * U with B ; P with V.
	 * @param x char
	 * @param y char
	 * @return boolean
	 */
	public static boolean matchBases(char x,char y){
		if ( (x == 'B') && (y == 'U')
				|| (x == 'U') && (y == 'B')
				|| (x == 'P') && (y == 'V')
				|| (x == 'V') && (y == 'P') ) {
			return true;
		} else { return false; }
	}
	
	/**
	 * To know if two nucleotids (char) can go together (including gap's).  
	 * @param x char
	 * @param y char
	 * @return boolean
	 */
	public static boolean matchBasesGap(char x,char y){
		if ( (BasePhaseTwo.matchBases(x,y)) 
				|| ( (x == '-') && ( y == '-') ) ) { return true; }
		else { return false; }
	}

	/**
	 * To know if two Base's can go together.  
	 * @param x BasePhaseTwo
	 * @param y BasePhaseTwo
	 * @return boolean
	 */
	public static boolean matchBases(BasePhaseTwo x,BasePhaseTwo y) 
	{ return BasePhaseTwo.matchBases(x.getBase(), y.getBase()); }

	/**
	 * To know if two Base's can go together (including gap's). 
	 * @param x BasePhaseTwo
	 * @param y BasePhaseTwo
	 * @return boolean
	 */
	public static boolean matchBasesGap(BasePhaseTwo x,BasePhaseTwo y) 
	{ return BasePhaseTwo.matchBasesGap(x.getBase(), y.getBase()); }
}

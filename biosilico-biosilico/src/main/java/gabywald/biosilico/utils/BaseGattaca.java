package gabywald.biosilico.utils;

/**
 * This class to limit to letters {A,C,G,T}
 * @author Gabriel Chandesris (2009)
 */
public class BaseGattaca extends Base {

	public BaseGattaca(char base, int position) 
		{ super(base, position); }
	
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
	 * @param base char
	 * @return boolean
	 */
	public static boolean isBase(char base){
		String regExpBases = "^[ACGT]$";
		String baseString = base+"";
		if (baseString.matches(regExpBases)) { return true; }
		else { return false; }
	}

	/**
	 * To test if nucleotids or gap / indel symbolized with '-'.  
	 * @param base char
	 */
	public static boolean isBaseOrGap(char base){
		if ( (BaseGattaca.isBase(base)) || (base == '-') ) { return true; }
		else { return false; }
	}

}

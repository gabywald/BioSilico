package gabywald.biosilico.genetics;

import java.util.ArrayList;
import java.util.List;

import gabywald.biosilico.exceptions.GeneException;
import gabywald.biosilico.structures.GeneticTranslator;

/**
 * This class has aim to categorize some genes inspired by <i>Creatures</i> works. 
 * <br>Types of GeneGattaca (defined for {@linkplain gabywald.biosilico.view.GeneParametersViewer} : 
 * <ul>
 * <li><b>1 : </b>InitialConcentration</li>
 * <li><b>2 : </b>BiochemicalReaction</li>
 * <li><b>3 : </b>BrainGene</li>
 * <li><b>4 : </b>BrainLobeGene</li>
 * <li><b>5 : </b>EmitterReceptor</li>
 * <li><b>6 : </b>StimulusDecision</li>
 * <li><b>7 : </b>Instinct</li>
 * </ul>
 * @see InitialConcentration
 * @see BiochemicalReaction
 * @see BrainGene
 * @see BrainLobeGene
 * @see EmitterReceptor
 * @see Instinct
 * @see StimulusDecision
 * @author Gabriel Chandesris (2009, 2020)
 */
public abstract class GeneGattaca extends Gene {
	
	/**
	 * Enumeration types of this kind of Gene (GeneGattaca). 
	 * @author Gabriel Chandesris (2020)
	 */
	public enum GeneGattacaType {
		INITIAL_CONCENTRATION, 
		BIOCHEMICHAL_REACTION, 
		BRAIN, 
		BRAIN_LOBE, 
		EMITTER_RECEPTOR, 
		STIMULUS_DECISION, 
		INSTINCT
	}

	/**
	 * Main "default" Constructor.  
	 * @param mutate (boolean) If Gene can be mutated. 
	 * @param duplicate (boolean) if Gene can, be duplicated. 
	 * @param delete (boolean) If Gene can be deleted. 
	 * @param activ (boolean) If Gene is globally activated. 
	 * @param ageMin (int) Minimal age of activation. 
	 * @param ageMax (int) Maximal age of activation. 
	 * @param sex (int) Sex of activation. 
	 * @param mutationRate (int) Mutation rate.
	 * @see Gene#Gene(boolean, boolean, boolean, boolean, int, int, int, int)
	 */
	public GeneGattaca(	boolean mutate, boolean duplicate, boolean delete, boolean activ, 
						int ageMin, int ageMax, int sex, int mutationRate) 
		{ super(mutate, duplicate, delete, activ, ageMin, ageMax, sex, mutationRate); }

	/**
	 * Translation method for Gene encoded in Gattaca code. 
	 * @param sequence (String)
	 * @param startpos (int) Start position in sequence (just after 'M' : 0, 1 or 2).
	 * @return (String) translated sequence. 
	 * @see GeneGattaca#getInstance(String)
	 */
	public static String translation(String sequence, int startpos) {
		String result = "";
		for (int i = startpos ; 
				(i < sequence.length()) && (i+2 < sequence.length()) ; 
				i = i+3) {
			String triplet = sequence.substring(i, i+3);
			result += GeneticTranslator.translationGattaca(triplet.toUpperCase());
			// GeneGattaca.gattacaCode(triplet.toUpperCase());
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
		// Start with 'M' (begin) : "GGC" 
		String result = GeneticTranslator.reverseGattaca("M");
		// true => 0 2 4 6 8 (divided by 2 rest is 0) : "CTG" 
		// false => 1 3 5 7 9 (divided by 2 rest is 1) : "CGT" 
		result +=  (this.canMutate()) ? GeneticTranslator.reverseGattaca("0") 
					: GeneticTranslator.reverseGattaca("1");
		result += (this.canDuplicate()) ? GeneticTranslator.reverseGattaca("2")
					: GeneticTranslator.reverseGattaca("3"); 
		result += (this.canDelete()) ? GeneticTranslator.reverseGattaca("4")
					:GeneticTranslator.reverseGattaca("5");
		result += (this.isActiv()) ? GeneticTranslator.reverseGattaca("6")
					: GeneticTranslator.reverseGattaca("7");

		String agemin = Gene.convert0to999(this.getAgeMin());
		String agemax = Gene.convert0to999(this.getAgeMax());
		String sexact = Gene.convert0to999(this.getSexAct());
		String mutrat = ((this.getMutationRate() < 10)?"0":"")+this.getMutationRate();
		
		result += GeneticTranslator.reverseSequenceGattaca( agemin );
		result += GeneticTranslator.reverseSequenceGattaca( agemax );
		result += GeneticTranslator.reverseSequenceGattaca( sexact );
		result += GeneticTranslator.reverseSequenceGattaca( mutrat );
		
		// adding ":" to end header. 
		result += GeneticTranslator.reverseGattaca(":");
		// End with '*' (end) if ended here, only header). "GGT" 
		return (end)?result+GeneticTranslator.reverseGattaca("*"):result; 
	}
	
	public static boolean detectBooleanValue(char testChar) 
			throws GeneException {
		if ( ! Character.isDigit(testChar)) {
			throw new GeneException("Not a digit [" + testChar + "]");
		}
		int testInt = Integer.parseInt(testChar+"");
		if (testInt%2 == 0) { return true; }
		else { return false; }
	}
	
	public static int detectIntegerValue(char... testChar) 
			throws GeneException {
		int toReturn	= 0;
		int maxIndex	= testChar.length;
		int index		= 1;
		for (char aChar : testChar) {
			if ( ! Character.isDigit(aChar)) { 
				StringBuilder sb = new StringBuilder();
				sb.append("Not a digit [").append(aChar).append("] in {").append(testChar).append("}");
				throw new GeneException(sb.toString());
			}
			toReturn	+= Integer.parseInt(aChar + "") * ( (int)Math.pow(10, (maxIndex - index) ) );
			index++;
		}
		return toReturn;
	}
	
	public static int detectIntValue(int... intElt) {
		int toReturn	= 0;
		int maxIndex	= intElt.length;
		int index		= 1;
		for (int aInt : intElt) {
			toReturn	+= aInt * ( (int)Math.pow(10, (maxIndex - index) ) );
			index++;
		}
		return toReturn;
	}
	
	/**
	 * This method has for aim to return a valid GattacaGene or 'null' with a 
	 * given sequence. Search into the three possible reading frames, return 
	 * at first good translation found. <br>
	 * Analyse first the header (all genes) then study rest of sequence. 
	 * @param sequence (String)
	 * @return (GattacaGene) Could be 'null'. 
	 * @see GeneGattaca#getIC(boolean, boolean, boolean, boolean, int, int, int, int, List)
	 * @see GeneGattaca#getBR(boolean, boolean, boolean, boolean, int, int, int, int, List)
	 * @see GeneGattaca#getBG(boolean, boolean, boolean, boolean, int, int, int, int, List)
	 * @see GeneGattaca#getBLG(boolean, boolean, boolean, boolean, int, int, int, int, List)
	 * @see GeneGattaca#getER(boolean, boolean, boolean, boolean, int, int, int, int, List)
	 * @see GeneGattaca#getSD(boolean, boolean, boolean, boolean, int, int, int, int, List)
	 * @see GeneGattaca#getIN(boolean, boolean, boolean, boolean, int, int, int, int, List)
	 * TODO [review]design pattern to do this better !!
	 */
	public static GeneGattaca getInstance(String sequence) {
		/** no GeneGattaca under 10 triplet length : start+7 elts of header+1 elt+stop */
		if (sequence.length() < (3*10) ) { return null; }
		
		// ***** Searching in 3 frames : 0 ; 1 ; 2. 
		for (int i = 0 ; i < 3 ; i++) {
			String translated = GeneGattaca.translation(sequence, i);
			int startIndex = translated.indexOf("M");
			int stopsIndex = translated.indexOf("*");
			int diff = (stopsIndex-startIndex);
			// ***** 15 is length of translated header 
			if (diff > 15) { 
				// ***** Default values
				boolean mutate		= false;
				boolean duplicate	= false;
				boolean delete		= false;
				boolean activ		= false;
				int ageMin			= 0;
				int ageMax			= 0;
				int sex				= 0;
				int mutationRate	= 0;
				// ***** start from 1 to ignore first letter 'M' 
				try {
					// ***** header [1] : mutate flag 
					mutate			= GeneGattaca.detectBooleanValue(translated.charAt( 1));
					// ***** header [2] : duplicate flag 
					duplicate		= GeneGattaca.detectBooleanValue(translated.charAt( 2));
					// ***** header [3] : delete flag 
					delete			= GeneGattaca.detectBooleanValue(translated.charAt( 3));
					// ***** header [4] : activity flag 
					activ			= GeneGattaca.detectBooleanValue(translated.charAt( 4));
					// ***** header [5-7] : ageMin flag 
					ageMin			= GeneGattaca.detectIntegerValue(translated.charAt( 5), translated.charAt( 6), translated.charAt( 7));
					// ***** header [8-10] : ageMax flag
					ageMax			= GeneGattaca.detectIntegerValue(translated.charAt( 8), translated.charAt( 9), translated.charAt(10));
					// ***** header [11-13] : sex flag
					sex				= GeneGattaca.detectIntegerValue(translated.charAt(11), translated.charAt(12), translated.charAt(13));
					// ***** header [14-15] : mutationRate flag
					mutationRate	= GeneGattaca.detectIntegerValue(translated.charAt(14), translated.charAt(15));
				} catch (GeneException e) {
					return null;
				}
				List<Integer> memory = new ArrayList<Integer>();
				
				for (int j = startIndex ; j < stopsIndex ; j++) {
					char testChar = translated.charAt(j);
					// ***** 15 = ":", from 16: rest of the parameters in memory. 
					if (j > 16) {
						if (Character.isDigit(testChar)) 
							{ memory.add(new Integer(Integer.parseInt(testChar+""))); } 
						else { return null; }
					}
				} // END "for (int j = startIndex ; j < stopsIndex ; j++)" 
				
				// TODO [optimization] switch / enum ?
				
				/** Initial Concentration Gene	length is 6 */
				/** ex : M024600000000025:010100* */
				if (memory.size() == (2*3) ) {
					return GeneGattaca.getIC(mutate, duplicate, delete, activ, ageMin, ageMax, sex, mutationRate, memory);
				}
				
				/** Biochemical Reaction Gene 	length is 27 */
				/** ex : M135600099900025:001010002010003010004010005* */
				if (memory.size() == (9*3) ) {
					return GeneGattaca.getBR(mutate, duplicate, delete, activ, ageMin, ageMax, sex, mutationRate, memory);
				}
				
				/** BrainGene 					length is 8 */
				/** ex : M024600000000025:30303030* */
				if (memory.size() == (4*2) ) {
					return GeneGattaca.getBG(mutate, duplicate, delete, activ, ageMin, ageMax, sex, mutationRate, memory);
				}
				
				/** BrainLobeGene 				length is 32 */
				/** ex : M024600000000025:00001000100000000010003013000005*
						 M024600000000025:00001000100101600410002011529005*
						 M024600000000025:00001000100801600410003011529155*
						 M024600000000025:00001000100200400401003013001005*
				*/
				if (memory.size() == ((7*3)+(4*2)+3) ) {
					return GeneGattaca.getBLG(mutate, duplicate, delete, activ, ageMin, ageMax, sex, mutationRate, memory);
				}
				
				/** EmitterReceptor 			length is 15 */
				/** ex : M024600099900025:001010010001502*	 
				  		 M024600099900025:001010010001512*	 */
				if (memory.size() == ((3*3)+(2*2)+2) ) {
					return GeneGattaca.getER(mutate, duplicate, delete, activ, ageMin, ageMax, sex, mutationRate, memory);
				}
				
				/** StimulusDecision 			length is 20 */
				/** ex : M024600099900025:13800100880020020850*
				 		 M024600099900025:12800100880020020850*
				 		 M024600099900025:03800100880020020850*
				 		 M024600099900025:02800100880020020850*		*/
				if (memory.size() == (2+(6*3)) ) {
					return GeneGattaca.getSD(mutate, duplicate, delete, activ, ageMin, ageMax, sex, mutationRate, memory);
				}
				
				/** Instinct		 			length is 18 */
				/** ex : M024600099900025:001099105550200201*
				 		 M024600099900025:001099105550200200*	*/
				if (memory.size() == ((4*2)+(3*3)+1) ) {
					return GeneGattaca.getIN(mutate, duplicate, delete, activ, ageMin, ageMax, sex, mutationRate, memory);
				}
				
				return null;
			} // ***** END if (diff > 13) ; no header 
		}
		return null;
	}
	
	/**
	 * Instantiate a InitialConcentration GeneGattaca. 
	 * @param mutate (boolean) If Gene can be mutated. 
	 * @param duplicate (boolean) if Gene can, be duplicated. 
	 * @param delete (boolean) If Gene can be deleted. 
	 * @param activ (boolean) If Gene is globally activated. 
	 * @param ageMin (int) Minimal age of activation. 
	 * @param ageMax (int) Maximal age of activation. 
	 * @param sex (int) Sex of activation. 
	 * @param mutationRate (int) Mutation rate.
	 * @param memory (IntegerListe)
	 * @return (InitialConcentration) (GeneGattaca) (Gene)
	 * @see GeneGattaca#getInstance(String)
	 * @see Gene#Gene(boolean, boolean, boolean, boolean, int, int, int, int)
	 */
	private static InitialConcentration getIC(
			boolean mutate, boolean duplicate, boolean delete, boolean activ,
			int ageMin, int ageMax, int sex, int mutationRate,
			List<Integer> memory) {
		int var = GeneGattaca.detectIntValue(memory.get(0), memory.get(1), memory.get(2));
		int val = GeneGattaca.detectIntValue(memory.get(3), memory.get(4), memory.get(5));
		return new InitialConcentration(mutate, duplicate, delete, activ, ageMin, ageMax, sex, mutationRate,
										var, val);
	}
	
	/**
	 * Instantiate a BiochemicalReaction GeneGattaca. 
	 * @param mutate (boolean) If Gene can be mutated. 
	 * @param duplicate (boolean) if Gene can, be duplicated. 
	 * @param delete (boolean) If Gene can be deleted. 
	 * @param activ (boolean) If Gene is globally activated. 
	 * @param ageMin (int) Minimal age of activation. 
	 * @param ageMax (int) Maximal age of activation. 
	 * @param sex (int) Sex of activation. 
	 * @param mutationRate (int) Mutation rate.
	 * @param memory (IntegerListe)
	 * @return (BiochemicalReaction) (GeneGattaca) (Gene)
	 * @see GeneGattaca#getInstance(String)
	 * @see Gene#Gene(boolean, boolean, boolean, boolean, int, int, int, int)
	 */
	private static BiochemicalReaction getBR(
			boolean mutate, boolean duplicate, boolean delete, boolean activ,
			int ageMin, int ageMax, int sex, int mutationRate,
			List<Integer> memory) {
		int Achem	= GeneGattaca.detectIntValue(memory.get( 0), memory.get( 1), memory.get( 2));
		int Acoef	= GeneGattaca.detectIntValue(memory.get( 3), memory.get( 4), memory.get( 5));
		int Bchem	= GeneGattaca.detectIntValue(memory.get( 6), memory.get( 7), memory.get( 8));
		int Bcoef	= GeneGattaca.detectIntValue(memory.get( 9), memory.get(10), memory.get(11));
		int Cchem	= GeneGattaca.detectIntValue(memory.get(12), memory.get(13), memory.get(14));
		int Ccoef	= GeneGattaca.detectIntValue(memory.get(15), memory.get(16), memory.get(17));
		int Dchem	= GeneGattaca.detectIntValue(memory.get(18), memory.get(19), memory.get(20));
		int Dcoef	= GeneGattaca.detectIntValue(memory.get(21), memory.get(22), memory.get(23));
		int KMVM	= GeneGattaca.detectIntValue(memory.get(24), memory.get(25), memory.get(26));
		return new BiochemicalReaction(	mutate, duplicate, delete, activ, ageMin, ageMax, sex, mutationRate, 
										Achem, Acoef, Bchem, Bcoef, Cchem, Ccoef, Dchem, Dcoef, KMVM);
	}
	
	/**
	 * Instantiate a BrainGene GeneGattaca. 
	 * @param mutate (boolean) If Gene can be mutated. 
	 * @param duplicate (boolean) if Gene can, be duplicated. 
	 * @param delete (boolean) If Gene can be deleted. 
	 * @param activ (boolean) If Gene is globally activated. 
	 * @param ageMin (int) Minimal age of activation. 
	 * @param ageMax (int) Maximal age of activation. 
	 * @param sex (int) Sex of activation. 
	 * @param mutationRate (int) Mutation rate.
	 * @param memory (IntegerListe)
	 * @return (BrainGene) (GeneGattaca) (Gene)
	 * @see GeneGattaca#getInstance(String)
	 * @see Gene#Gene(boolean, boolean, boolean, boolean, int, int, int, int)
	 */
	private static BrainGene getBG(
			boolean mutate, boolean duplicate, boolean delete, boolean activ, 
			int ageMin, int ageMax, int sex, int mutationRate, 
			List<Integer> memory) {
		int height	= GeneGattaca.detectIntValue(memory.get( 0), memory.get( 1));
		int width	= GeneGattaca.detectIntValue(memory.get( 2), memory.get( 3));
		int depth	= GeneGattaca.detectIntValue(memory.get( 4), memory.get( 5));
		int more	= GeneGattaca.detectIntValue(memory.get( 6), memory.get( 7));
		return new BrainGene(	mutate, duplicate, delete, activ, ageMin, ageMax, sex, mutationRate, 
								height, width, depth, more);
	}
	/**
	 * Instantiate a BrainLobeGene GeneGattaca. 
	 * @param mutate (boolean) If Gene can be mutated. 
	 * @param duplicate (boolean) if Gene can, be duplicated. 
	 * @param delete (boolean) If Gene can be deleted. 
	 * @param activ (boolean) If Gene is globally activated. 
	 * @param ageMin (int) Minimal age of activation. 
	 * @param ageMax (int) Maximal age of activation. 
	 * @param sex (int) Sex of activation. 
	 * @param mutationRate (int) Mutation rate.
	 * @param memory (IntegerListe)
	 * @return (BrainLobeGene) (GeneGattaca) (Gene)
	 * @see GeneGattaca#getInstance(String)
	 * @see Gene#Gene(boolean, boolean, boolean, boolean, int, int, int, int)
	 */
	private static BrainLobeGene getBLG(
			boolean mutate, boolean duplicate, boolean delete, boolean activ, 
			int ageMin, int ageMax, int sex, int mutationRate, 
			List<Integer> memory) {
		int rest = GeneGattaca.detectIntValue(memory.get( 0), memory.get( 1), memory.get( 2));
		int thre = GeneGattaca.detectIntValue(memory.get( 3), memory.get( 4), memory.get( 5));
		int desc = GeneGattaca.detectIntValue(memory.get( 6), memory.get( 7), memory.get( 8));
		int dmin = GeneGattaca.detectIntValue(memory.get( 9), memory.get(10), memory.get(11));
		int dmax = GeneGattaca.detectIntValue(memory.get(12), memory.get(13), memory.get(14));
		int prox = GeneGattaca.detectIntValue(memory.get(15), memory.get(16), memory.get(17));
		boolean repr	= (memory.get(18).intValue()%2 == 0);
		int repy		= GeneGattaca.detectIntValue(memory.get(19), memory.get(20), memory.get(21));
		boolean wta 	= (memory.get(22).intValue()%2 == 0);
		int height		= GeneGattaca.detectIntValue(memory.get(23), memory.get(24));
		int width		= GeneGattaca.detectIntValue(memory.get(25), memory.get(26));
		int posx		= GeneGattaca.detectIntValue(memory.get(27), memory.get(28));
		int posy		= GeneGattaca.detectIntValue(memory.get(29), memory.get(30));
		boolean replace = (memory.get(31).intValue()%2 == 0);
		return new BrainLobeGene(	mutate, duplicate, delete, activ, ageMin, ageMax, sex, mutationRate, 
									rest, thre, desc, dmin, dmax, prox, repr, 
									repy, wta, height, width, posx, posy, replace);
	}
	
	/**
	 * Instantiate a EmitterReceptor GeneGattaca. 
	 * @param mutate (boolean) If Gene can be mutated. 
	 * @param duplicate (boolean) if Gene can, be duplicated. 
	 * @param delete (boolean) If Gene can be deleted. 
	 * @param activ (boolean) If Gene is globally activated. 
	 * @param ageMin (int) Minimal age of activation. 
	 * @param ageMax (int) Maximal age of activation. 
	 * @param sex (int) Sex of activation. 
	 * @param mutationRate (int) Mutation rate.
	 * @param memory (IntegerListe)
	 * @return (EmitterReceptor) (GeneGattaca) (Gene)
	 * @see GeneGattaca#getInstance(String)
	 * @see Gene#Gene(boolean, boolean, boolean, boolean, int, int, int, int)
	 */
	private static EmitterReceptor getER(
			boolean mutate, boolean duplicate, boolean delete, boolean activ, 
			int ageMin, int ageMax, int sex, int mutationRate, 
			List<Integer> memory) {
		int var		= GeneGattaca.detectIntValue(memory.get( 0), memory.get( 1), memory.get( 2));
		int thr		= GeneGattaca.detectIntValue(memory.get( 3), memory.get( 4), memory.get( 5));
		int put		= GeneGattaca.detectIntValue(memory.get( 6), memory.get( 7), memory.get( 8));
		int posx	= GeneGattaca.detectIntValue(memory.get( 9), memory.get(10));
		int posy	= GeneGattaca.detectIntValue(memory.get(11), memory.get(12));
		boolean receptor = (memory.get(13).intValue()%2 == 0);
		boolean internal = (memory.get(14).intValue()%2 == 0);
		return new EmitterReceptor(	mutate, duplicate, delete, activ, ageMin, ageMax, sex, mutationRate, 
									var, thr, put, posx, posy, receptor, internal);
	}
	
	private static StimulusDecision getSD(
			boolean mutate, boolean duplicate, boolean delete, boolean activ, 
			int ageMin, int ageMax, int sex, int mutationRate, 
			List<Integer> memory) {
		boolean perc = (memory.get(0).intValue()%2 == 0);
		boolean obje = (memory.get(1).intValue()%2 == 0);
		int indi = GeneGattaca.detectIntValue(memory.get( 2), memory.get( 3), memory.get( 4));
		int thre = GeneGattaca.detectIntValue(memory.get( 5), memory.get( 6), memory.get( 7));
		int attr = GeneGattaca.detectIntValue(memory.get( 8), memory.get( 9), memory.get(10));
		int vari = GeneGattaca.detectIntValue(memory.get(11), memory.get(12), memory.get(13));
		int valu = GeneGattaca.detectIntValue(memory.get(14), memory.get(15), memory.get(16));
		int scri = GeneGattaca.detectIntValue(memory.get(17), memory.get(18), memory.get(19));
		return new StimulusDecision(	mutate, duplicate, delete, activ, ageMin, ageMax, sex, mutationRate,
										perc, obje, indi, thre, attr, vari, valu, scri);
	}
	
	private static Instinct getIN(
			boolean mutate, boolean duplicate, boolean delete, boolean activ, 
			int ageMin, int ageMax, int sex, int mutationRate, 
			List<Integer> memory) {
		int inPosX	= GeneGattaca.detectIntValue(memory.get( 0), memory.get( 1));
		int inPosY	= GeneGattaca.detectIntValue(memory.get( 2), memory.get( 3));
		int outPosX	= GeneGattaca.detectIntValue(memory.get( 4), memory.get( 5));
		int outPosY	= GeneGattaca.detectIntValue(memory.get( 6), memory.get( 7));
		int weight	= GeneGattaca.detectIntValue(memory.get( 8), memory.get( 9), memory.get(10));
		int var		= GeneGattaca.detectIntValue(memory.get(11), memory.get(12), memory.get(13));
		int thr		= GeneGattaca.detectIntValue(memory.get(14), memory.get(15), memory.get(16));
		boolean check = (memory.get(17).intValue()%2 == 0);
		return new Instinct(	mutate, duplicate, delete, activ, ageMin, ageMax, sex, mutationRate, 
								inPosX, inPosY, outPosX, outPosY, weight, var, thr, check);
	}
	
}

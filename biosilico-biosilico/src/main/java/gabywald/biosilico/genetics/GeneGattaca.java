package gabywald.biosilico.genetics;

import java.util.ArrayList;
import java.util.List;

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
 * @author Gabriel Chandesris (2009, 2020)
 */
public abstract class GeneGattaca extends Gene {

	/**
	 * Main "default" Constructor.  
	 * @param mutate (boolean) If Gene can be mutated. 
	 * @param duplicate (boolean) if Gene can, be duplicated. 
	 * @param delete (boolean) If Gene can be deleted. 
	 * @param activ (boolean) If Gene is globally activated. 
	 * @param age_min (int) Minimal age of activation. 
	 * @param age_max (int) Maximal age of activation. 
	 * @param sex (int) Sex of activation. 
	 * @param mut_rate (int) Mutation rate.
	 * @see InitialConcentration
	 * @see BiochemicalReaction
	 * @see BrainGene
	 * @see BrainLobeGene
	 * @see EmitterReceptor
	 * @see Gene#Gene(boolean, boolean, boolean, boolean, int, int, int, int)
	 */
	public GeneGattaca(	boolean mutate, boolean duplicate, boolean delete, boolean activ, 
						int age_min, int age_max, int sex, int mut_rate) 
		{ super(mutate, duplicate, delete, activ, age_min, age_max, sex, mut_rate); }

	/**
	 * Translation method for Gene encoded in Gattaca code. 
	 * @param sequence (String)
	 * @param startpos (int) Start position in sequence (just after 'M' : 0, 1 or 2).
	 * @return (String) translated sequence. 
	 * @see GeneGattaca#getInstance(String)
	 */
	public static String translation(String sequence,int startpos) {
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
		// if (this.getAgeMin() < 100)	{ agemin = "0"+agemin; } 
		// if (this.getAgeMin() < 10)	{ agemin = "0"+agemin; } 
		String agemin = Gene.convert0to999(this.getAgeMin());
		String agemax = Gene.convert0to999(this.getAgeMax());
		String sexact = Gene.convert0to999(this.getSexAct());
		String mutrat = ((this.getMutationRate() < 10)?"0":"")+this.getMutationRate();
		for (int i = 0 ; i < agemin.length() ; i++) 
			{ result += GeneticTranslator.reverseGattaca(agemin.charAt(i)+""); }
		for (int i = 0 ; i < agemax.length() ; i++) 
			{ result += GeneticTranslator.reverseGattaca(agemax.charAt(i)+""); }
		for (int i = 0 ; i < sexact.length() ; i++) 
			{ result += GeneticTranslator.reverseGattaca(sexact.charAt(i)+""); }
		for (int i = 0 ; i < mutrat.length() ; i++) 
			{ result += GeneticTranslator.reverseGattaca(mutrat.charAt(i)+""); }
		// adding ":" to end header. 
		result += GeneticTranslator.reverseGattaca(":");
		// End with '*' (end) if ended here, only header). "GGT" 
		return (end)?result+GeneticTranslator.reverseGattaca("*"):result; 
	}
	
	/**
	 * This method has for aim to return a valid GattacaGene or 'null' with a 
	 * given sequence. Search into the three possible reading frames, return 
	 * at first good translation found. <br>
	 * Analyse first the header (all genes) then study rest of sequence. 
	 * @param sequence (String)
	 * @return (GattacaGene) Could be 'null'. 
	 * @see GeneGattaca#getIC(boolean, boolean, boolean, boolean, int, int, int, int, IntegerListe)
	 * @see GeneGattaca#getBR(boolean, boolean, boolean, boolean, int, int, int, int, IntegerListe)
	 * @see GeneGattaca#getBG(boolean, boolean, boolean, boolean, int, int, int, int, IntegerListe)
	 * @see GeneGattaca#getBLG(boolean, boolean, boolean, boolean, int, int, int, int, IntegerListe)
	 * @see GeneGattaca#getER(boolean, boolean, boolean, boolean, int, int, int, int, IntegerListe)
	 */
	public static GeneGattaca getInstance(String sequence) {
		/** no GeneGattaca under 10 triplet length : start+7 elts of header+1 elt+stop */
		if (sequence.length() < (3*10) ) { return null; }
		
		/** Searching in 3 frames : 0 ; 1 ; 2. */
		for (int i = 0 ; i < 3 ; i++) {
			String translated = GeneGattaca.translation(sequence, i);
			int start_index = translated.indexOf("M");
			int stops_index = translated.indexOf("*");
			int diff = (stops_index-start_index);
			if (diff > 15) { /** 15 is length of translated header */
				/** Default values for header in Gene. */
				boolean mutate		= false;
				boolean duplicate	= false;
				boolean delete		= false;
				boolean activ		= false;
				int age_min			= 0;
				int age_max			= 0;
				int sex				= 0;
				int mut_rate		= 0;
				List<Integer> memory = new ArrayList<Integer>();
				
				// TODO optimize this part !!
				for (int j = start_index ; j < stops_index ; j++) {
					char test_char = translated.charAt(j);
					/** start from 1 to ignore first letter 'M' */
					/** header 1 : mutate flag */
					if (j == 1) { 
						if (Character.isDigit(test_char)) {
							int test_int = Integer.parseInt(test_char+"");
							if (test_int%2 == 0) { mutate = true; }
						} else { return null; }
					}
					/** header 2 : duplicate flag */
					if (j == 2) { 
						if (Character.isDigit(test_char)) {
							int test_int = Integer.parseInt(test_char+"");
							if (test_int%2 == 0) { duplicate = true; }
						} else { return null; }
					}
					/** header 3 : delete flag */
					if (j == 3) { 
						if (Character.isDigit(test_char)) {
							int test_int = Integer.parseInt(test_char+"");
							if (test_int%2 == 0) { delete = true; }
						} else { return null; }
					}
					/** header 4 : activ flag */
					if (j == 4) { 
						if (Character.isDigit(test_char)) {
							int test_int = Integer.parseInt(test_char+"");
							if (test_int%2 == 0) { activ = true; }
						} else { return null; }
					}
					
					/** header [5-7] : age_min flag */
					if ( (j >= 5) && (j <= 7) )  { 
						if (Character.isDigit(test_char)) { 
							if (j == 5) { age_min += Integer.parseInt(test_char+"")*100; }
							if (j == 6) { age_min += Integer.parseInt(test_char+"")*10; }
							if (j == 7) { age_min += Integer.parseInt(test_char+"")*1; }
						} else { return null; }
					}
					/** header [8-10] : age_max flag */
					if ( (j >= 8) && (j <= 10) ) { 
						if (Character.isDigit(test_char)) { 
							if (j == 8) { age_max += Integer.parseInt(test_char+"")*100; }
							if (j == 9) { age_max += Integer.parseInt(test_char+"")*10; }
							if (j == 10) { age_max += Integer.parseInt(test_char+"")*1; }
						} else { return null; }
					}
					/** header [11-13] : sex flag */
					if ( (j >= 11) && (j <= 13) ) { 
						if (Character.isDigit(test_char)) { 
							if (j == 11) { sex += Integer.parseInt(test_char+"")*100; }
							if (j == 12) { sex += Integer.parseInt(test_char+"")*10; }
							if (j == 13) { sex += Integer.parseInt(test_char+"")*1; }
						} else { return null; }
					}
					/** header [14-15] : mut_rate flag */
					if ( (j >= 14) && (j <= 15) ) { 
						if (Character.isDigit(test_char)) { 
							if (j == 14) { mut_rate += Integer.parseInt(test_char+"")*10; }
							if (j == 15) { mut_rate += Integer.parseInt(test_char+"")*1; }
						} else { return null; }
					}
					
					/** 15 = ":", from 16: rest of the parameters in memory. */
					if (j > 16) {
						if (Character.isDigit(test_char)) 
							{ memory.add(new Integer(Integer.parseInt(test_char+""))); } 
						else { return null; }
					}
				} /** END for (int j = start_index ; j < stops_index ; j++) */
				
				// TODO [optimization] switch / enum ?
				
				/** Initial Concentration Gene	length is 6 */
				/** ex : M024600000000025:010100* */
				if (memory.size() == (2*3) ) {
					return GeneGattaca.getIC(mutate, duplicate, delete, activ, 
							age_min, age_max, sex, mut_rate, memory);
				}
				
				/** Biochemical Reaction Gene 	length is 27 */
				/** ex : M135600099900025:001010002010003010004010005* */
				if (memory.size() == (9*3) ) {
					return GeneGattaca.getBR(mutate, duplicate, delete, activ, 
							age_min, age_max, sex, mut_rate, memory);
				}
				
				/** BrainGene 					length is 8 */
				/** ex : M024600000000025:30303030* */
				if (memory.size() == (4*2) ) {
					return GeneGattaca.getBG(mutate, duplicate, delete, activ, 
							age_min, age_max, sex, mut_rate, memory);
				}
				
				/** BrainLobeGene 				length is 32 */
				/** ex : M024600000000025:00001000100000000010003013000005*
						 M024600000000025:00001000100101600410002011529005*
						 M024600000000025:00001000100801600410003011529155*
						 M024600000000025:00001000100200400401003013001005*
				*/
				if (memory.size() == ((7*3)+(4*2)+3) ) {
					return GeneGattaca.getBLG(mutate, duplicate, delete, activ, 
							age_min, age_max, sex, mut_rate, memory);
				}
				
				/** EmitterReceptor 			length is 15 */
				/** ex : M024600099900025:001010010001502*	 
				  		 M024600099900025:001010010001512*	 */
				if (memory.size() == ((3*3)+(2*2)+2) ) {
					return GeneGattaca.getER(mutate, duplicate, delete, activ, 
							age_min, age_max, sex, mut_rate, memory);
				}
				
				/** StimulusDecision 			length is 20 */
				/** ex : M024600099900025:13800100880020020850*
				 		 M024600099900025:12800100880020020850*
				 		 M024600099900025:03800100880020020850*
				 		 M024600099900025:02800100880020020850*		*/
				if (memory.size() == (2+(6*3)) ) {
					return GeneGattaca.getSD(mutate, duplicate, delete, activ, 
							age_min, age_max, sex, mut_rate, memory);
				}
				
				/** Instinct		 			length is 18 */
				/** ex : M024600099900025:001099105550200201*
				 		 M024600099900025:001099105550200200*	*/
				if (memory.size() == ((4*2)+(3*3)+1) ) {
					return GeneGattaca.getIN(mutate, duplicate, delete, activ, 
							age_min, age_max, sex, mut_rate, memory);
				}
				
				/** [WAS until 20090716] : Not a 'Gattaca' gene because memory do not contain a multiple of 3. */
				// if (memory.length()%3 != 0) { return null; }
				/** [20090716] not detected as a valid GeneGattaca, null. */
				return null;
			} /** END if (diff > 13) ; no header */
		}
		return null;
	}
	
	/**
	 * Instantiate a InitialConcentration GeneGattaca. 
	 * @param mutate (boolean) If Gene can be mutated. 
	 * @param duplicate (boolean) if Gene can, be duplicated. 
	 * @param delete (boolean) If Gene can be deleted. 
	 * @param activ (boolean) If Gene is globally activated. 
	 * @param age_min (int) Minimal age of activation. 
	 * @param age_max (int) Maximal age of activation. 
	 * @param sex (int) Sex of activation. 
	 * @param mut_rate (int) Mutation rate.
	 * @param memory (IntegerListe)
	 * @return (InitialConcentration) (GeneGattaca) (Gene)
	 * @see GeneGattaca#getInstance(String)
	 * @see Gene#Gene(boolean, boolean, boolean, boolean, int, int, int, int)
	 */
	private static InitialConcentration getIC(
			boolean mutate, boolean duplicate, boolean delete, boolean activ,
			int age_min, int age_max, int sex, int mut_rate,
			List<Integer> memory) {
		int var = memory.get(0).intValue()*100
				+ memory.get(1).intValue()*10
				+ memory.get(2).intValue()*1;
		int val = memory.get(3).intValue()*100
				+ memory.get(4).intValue()*10
				+ memory.get(5).intValue()*1;
		return new InitialConcentration(
				mutate, duplicate, delete, activ, age_min, age_max, sex, mut_rate,
				var, val);
	}
	
	/**
	 * Instantiate a BiochemicalReaction GeneGattaca. 
	 * @param mutate (boolean) If Gene can be mutated. 
	 * @param duplicate (boolean) if Gene can, be duplicated. 
	 * @param delete (boolean) If Gene can be deleted. 
	 * @param activ (boolean) If Gene is globally activated. 
	 * @param age_min (int) Minimal age of activation. 
	 * @param age_max (int) Maximal age of activation. 
	 * @param sex (int) Sex of activation. 
	 * @param mut_rate (int) Mutation rate.
	 * @param memory (IntegerListe)
	 * @return (BiochemicalReaction) (GeneGattaca) (Gene)
	 * @see GeneGattaca#getInstance(String)
	 * @see Gene#Gene(boolean, boolean, boolean, boolean, int, int, int, int)
	 */
	private static BiochemicalReaction getBR(
			boolean mutate, boolean duplicate, boolean delete, boolean activ,
			int age_min, int age_max, int sex, int mut_rate,
			List<Integer> memory) {
		int Achem = memory.get(0).intValue()*100
					+ memory.get(1).intValue()*10
					+ memory.get(2).intValue()*1;
		int Acoef = memory.get(3).intValue()*100
					+ memory.get(4).intValue()*10
					+ memory.get(5).intValue()*1;
		int Bchem = memory.get(6).intValue()*100
					+ memory.get(7).intValue()*10
					+ memory.get(8).intValue()*1;
		int Bcoef = memory.get(9).intValue()*100
					+ memory.get(10).intValue()*10
					+ memory.get(11).intValue()*1;
		int Cchem = memory.get(12).intValue()*100
					+ memory.get(13).intValue()*10
					+ memory.get(14).intValue()*1;
		int Ccoef = memory.get(15).intValue()*100
					+ memory.get(16).intValue()*10
					+ memory.get(17).intValue()*1;
		int Dchem = memory.get(18).intValue()*100
					+ memory.get(19).intValue()*10
					+ memory.get(20).intValue()*1;
		int Dcoef = memory.get(21).intValue()*100
					+ memory.get(22).intValue()*10
					+ memory.get(23).intValue()*1;
		int KMVM = memory.get(24).intValue()*100
					+ memory.get(25).intValue()*10
					+ memory.get(26).intValue()*1;
		return new BiochemicalReaction(
				mutate, duplicate, delete, activ, age_min, age_max, sex, mut_rate, 
				Achem, Acoef, Bchem, Bcoef, Cchem, Ccoef, Dchem, Dcoef, 
				KMVM);
	}
	
	/**
	 * Instantiate a BrainGene GeneGattaca. 
	 * @param mutate (boolean) If Gene can be mutated. 
	 * @param duplicate (boolean) if Gene can, be duplicated. 
	 * @param delete (boolean) If Gene can be deleted. 
	 * @param activ (boolean) If Gene is globally activated. 
	 * @param age_min (int) Minimal age of activation. 
	 * @param age_max (int) Maximal age of activation. 
	 * @param sex (int) Sex of activation. 
	 * @param mut_rate (int) Mutation rate.
	 * @param memory (IntegerListe)
	 * @return (BrainGene) (GeneGattaca) (Gene)
	 * @see GeneGattaca#getInstance(String)
	 * @see Gene#Gene(boolean, boolean, boolean, boolean, int, int, int, int)
	 */
	private static BrainGene getBG(
			boolean mutate, boolean duplicate, boolean delete, boolean activ, 
			int age_min, int age_max, int sex, int mut_rate, 
			List<Integer> memory) {
		int height = memory.get(0).intValue()*10
					+ memory.get(1).intValue()*1;
		int width = memory.get(2).intValue()*10
					+ memory.get(3).intValue()*1;
		int depth = memory.get(4).intValue()*10
					+ memory.get(5).intValue()*1;
		int more = memory.get(6).intValue()*10
					+ memory.get(7).intValue()*1;
		return new BrainGene(
				mutate,duplicate,delete,activ,age_min,age_max,sex,mut_rate,
				height,width,depth,more);
	}
	/**
	 * Instantiate a BrainLobeGene GeneGattaca. 
	 * @param mutate (boolean) If Gene can be mutated. 
	 * @param duplicate (boolean) if Gene can, be duplicated. 
	 * @param delete (boolean) If Gene can be deleted. 
	 * @param activ (boolean) If Gene is globally activated. 
	 * @param age_min (int) Minimal age of activation. 
	 * @param age_max (int) Maximal age of activation. 
	 * @param sex (int) Sex of activation. 
	 * @param mut_rate (int) Mutation rate.
	 * @param memory (IntegerListe)
	 * @return (BrainLobeGene) (GeneGattaca) (Gene)
	 * @see GeneGattaca#getInstance(String)
	 * @see Gene#Gene(boolean, boolean, boolean, boolean, int, int, int, int)
	 */
	private static BrainLobeGene getBLG(
			boolean mutate, boolean duplicate, boolean delete, boolean activ, 
			int age_min, int age_max, int sex, int mut_rate, 
			List<Integer> memory) {
		int rest = memory.get(0).intValue()*100
					+ memory.get(1).intValue()*10
					+ memory.get(2).intValue()*1;
		int thre = memory.get(3).intValue()*100
					+ memory.get(4).intValue()*10
					+ memory.get(5).intValue()*1;
		int desc = memory.get(6).intValue()*100
					+ memory.get(7).intValue()*10
					+ memory.get(8).intValue()*1;
		int dendriticmin = memory.get(9).intValue()*100
					+ memory.get(10).intValue()*10
					+ memory.get(11).intValue()*1;
		int dendriticmax = memory.get(12).intValue()*100
					+ memory.get(13).intValue()*10
					+ memory.get(14).intValue()*1;
		int prox = memory.get(15).intValue()*100
					+ memory.get(16).intValue()*10
					+ memory.get(17).intValue()*1;
		boolean repr = (memory.get(18).intValue()%2 == 0);
		int repy = memory.get(19).intValue()*100
					+ memory.get(20).intValue()*10
					+ memory.get(21).intValue()*1;
		boolean wta = (memory.get(22).intValue()%2 == 0);
		int height = memory.get(23).intValue()*10
					+ memory.get(24).intValue()*1;
		int width = memory.get(25).intValue()*10
					+ memory.get(26).intValue()*1;
		int posx = memory.get(27).intValue()*10
					+ memory.get(28).intValue()*1;
		int posy = memory.get(29).intValue()*10
					+ memory.get(30).intValue()*1;
		boolean replace = (memory.get(31).intValue()%2 == 0);
		return new BrainLobeGene(
				mutate,duplicate,delete,activ,age_min,age_max,sex,mut_rate,
				rest,thre,desc,dendriticmin,dendriticmax,prox,repr,
				repy,wta,height,width,posx,posy,replace);
	}
	
	/**
	 * Instantiate a EmitterReceptor GeneGattaca. 
	 * @param mutate (boolean) If Gene can be mutated. 
	 * @param duplicate (boolean) if Gene can, be duplicated. 
	 * @param delete (boolean) If Gene can be deleted. 
	 * @param activ (boolean) If Gene is globally activated. 
	 * @param age_min (int) Minimal age of activation. 
	 * @param age_max (int) Maximal age of activation. 
	 * @param sex (int) Sex of activation. 
	 * @param mut_rate (int) Mutation rate.
	 * @param memory (IntegerListe)
	 * @return (EmitterReceptor) (GeneGattaca) (Gene)
	 * @see GeneGattaca#getInstance(String)
	 * @see Gene#Gene(boolean, boolean, boolean, boolean, int, int, int, int)
	 */
	private static EmitterReceptor getER(
			boolean mutate, boolean duplicate, boolean delete, boolean activ, 
			int age_min, int age_max, int sex, int mut_rate, 
			List<Integer> memory) {
		int var = memory.get(0).intValue()*100
					+ memory.get(1).intValue()*10
					+ memory.get(2).intValue()*1;
		int thr = memory.get(3).intValue()*100
					+ memory.get(4).intValue()*10
					+ memory.get(5).intValue()*1;
		int put = memory.get(6).intValue()*100
					+ memory.get(7).intValue()*10
					+ memory.get(8).intValue()*1;
		int posx = memory.get(9).intValue()*10
					+ memory.get(10).intValue()*1;
		int posy = memory.get(11).intValue()*10
					+ memory.get(12).intValue()*1;
		boolean receptor = (memory.get(13).intValue()%2 == 0);
		boolean internal = (memory.get(14).intValue()%2 == 0);
		return new EmitterReceptor(
				mutate,duplicate,delete,activ,age_min,age_max,sex,mut_rate,
				var,thr,put,posx,posy,receptor,internal);
	}
	
	private static StimulusDecision getSD(
			boolean mutate, boolean duplicate, boolean delete, boolean activ, 
			int age_min, int age_max, int sex, int mut_rate, 
			List<Integer> memory) {
		boolean perc = (memory.get(0).intValue()%2 == 0);
		boolean obje = (memory.get(1).intValue()%2 == 0);
		int indi = memory.get(2).intValue()*100
				+ memory.get(3).intValue()*10
				+ memory.get(4).intValue()*1;
		int thre = memory.get(5).intValue()*100
				+ memory.get(6).intValue()*10
				+ memory.get(7).intValue()*1;
		int attr = memory.get(8).intValue()*100
				+ memory.get(9).intValue()*10
				+ memory.get(10).intValue()*1;
		int vari = memory.get(11).intValue()*100
				+ memory.get(12).intValue()*10
				+ memory.get(13).intValue()*1;
		int valu = memory.get(14).intValue()*100
				+ memory.get(15).intValue()*10
				+ memory.get(16).intValue()*1;
		int scri = memory.get(17).intValue()*100
				+ memory.get(18).intValue()*10
				+ memory.get(19).intValue()*1;
		return new StimulusDecision(
				mutate,duplicate,delete,activ,age_min,age_max,sex,mut_rate,
				perc,obje,indi,thre,attr,vari,valu,scri);
	}
	
	private static Instinct getIN(
			boolean mutate, boolean duplicate, boolean delete, boolean activ, 
			int age_min, int age_max, int sex, int mut_rate, 
			List<Integer> memory) {
		int in_posx = memory.get(0).intValue()*10
					+ memory.get(1).intValue()*1;
		int in_posy = memory.get(2).intValue()*10
					+ memory.get(3).intValue()*1;
		int out_posx = memory.get(4).intValue()*10
					 + memory.get(5).intValue()*1;
		int out_posy = memory.get(6).intValue()*10
					 + memory.get(7).intValue()*1;
		int weight = memory.get(8).intValue()*100
				+ memory.get(9).intValue()*10
				+ memory.get(10).intValue()*1;
		int var = memory.get(11).intValue()*100
				+ memory.get(12).intValue()*10
				+ memory.get(13).intValue()*1;
		int thr = memory.get(14).intValue()*100
				+ memory.get(15).intValue()*10
				+ memory.get(16).intValue()*1;
		boolean check = (memory.get(17).intValue()%2 == 0);
		return new Instinct(
				mutate, duplicate, delete, activ, age_min, age_max, sex, mut_rate, 
				in_posx, in_posy, out_posx, out_posy, weight, var, thr, check);
	}
	
	/**
	 * The private internal code to translate genetic code to GeneGattaca real sense. 
	 * @param elt (String) a 3-length String. 
	 * @return (char)
	 *//**
	private static char gattacaCode(String elt) {
		if (elt.length() != 3) { return '-'; }
		if (elt.equals("AAA")) { return 'a'; }
		if (elt.equals("AAC")) { return 'b'; }
		if (elt.equals("AAT")) { return 'c'; }
		if (elt.equals("AAG")) { return 'd'; }
		if (elt.equals("ACA")) { return 'e'; }
		if (elt.equals("ACC")) { return 'f'; }
		if (elt.equals("ACT")) { return 'g'; }
		if (elt.equals("ACG")) { return 'h'; }
		if (elt.equals("ATA")) { return 'i'; }
		if (elt.equals("ATC")) { return 'j'; }
		if (elt.equals("ATT")) { return 'k'; }
		if (elt.equals("ATG")) { return 'l'; }
		if (elt.equals("AGA")) { return 'm'; }
		if (elt.equals("AGC")) { return 'n'; }
		if (elt.equals("AGT")) { return 'o'; }
		if (elt.equals("AGG")) { return 'p'; }
		if (elt.equals("CAA")) { return 'q'; }
		if (elt.equals("CAC")) { return 'r'; }
		if (elt.equals("CAT")) { return 's'; }
		if (elt.equals("CAG")) { return 't'; }
		if (elt.equals("CCA")) { return 'u'; }
		if (elt.equals("CCC")) { return 'v'; }
		if (elt.equals("CCT")) { return 'w'; }
		if (elt.equals("CCG")) { return 'x'; }
		if (elt.equals("CTA")) { return 'y'; }
		if (elt.equals("CTC")) { return 'z'; }
		if (elt.equals("CTT")) { return '0'; }
		if (elt.equals("CTG")) { return '0'; }
		if (elt.equals("CGA")) { return '0'; }
		if (elt.equals("CGC")) { return '1'; }
		if (elt.equals("CGT")) { return '1'; }
		if (elt.equals("CGG")) { return '1'; }
		if (elt.equals("TAA")) { return '2'; }
		if (elt.equals("TAC")) { return '2'; }
		if (elt.equals("TAT")) { return '2'; }
		if (elt.equals("TAG")) { return '3'; }
		if (elt.equals("TCA")) { return '3'; }
		if (elt.equals("TCC")) { return '3'; }
		if (elt.equals("TCT")) { return '4'; }
		if (elt.equals("TCG")) { return '4'; }
		if (elt.equals("TTA")) { return '4'; }
		if (elt.equals("TTC")) { return '5'; }
		if (elt.equals("TTT")) { return '5'; }
		if (elt.equals("TTG")) { return '5'; }
		if (elt.equals("TGA")) { return '6'; }
		if (elt.equals("TGC")) { return '6'; }
		if (elt.equals("TGT")) { return '6'; }
		if (elt.equals("TGG")) { return '7'; }
		if (elt.equals("GAA")) { return '7'; }
		if (elt.equals("GAC")) { return '7'; }
		if (elt.equals("GAT")) { return '8'; }
		if (elt.equals("GAG")) { return '8'; }
		if (elt.equals("GCA")) { return '8'; }
		if (elt.equals("GCC")) { return '9'; }
		if (elt.equals("GCT")) { return '9'; }
		if (elt.equals("GCG")) { return '9'; }
		if (elt.equals("GTA")) { return ':'; }
		if (elt.equals("GTC")) { return ':'; }
		if (elt.equals("GTT")) { return '\''; }
		if (elt.equals("GTG")) { return '\''; }
		if (elt.equals("GGA")) { return 'M'; }
		if (elt.equals("GGC")) { return 'M'; }
		if (elt.equals("GGT")) { return '*'; }
		if (elt.equals("GGG")) { return '*'; }
		return '-';
	}*/
}

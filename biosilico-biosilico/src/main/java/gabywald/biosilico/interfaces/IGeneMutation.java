package gabywald.biosilico.interfaces;

import java.util.Arrays;
import java.util.Random;

import gabywald.biosilico.genetics.Gene;

/**
 * To provide changes methods for mutation. 
 * @author Gabriel Chandesris (2020)
 */
public interface IGeneMutation {
	/**
	 * Compute changes of mutation on current Gene's instance. 
	 * <br/><i>indication : </i>Clone Gene's instance ! 
	 */
	public void mutationChanges();
	
	/** 
	 * Indicated if mutation apply from ratio indicated. 
	 * @param mutRate From 0 to 100. 
	 * @return If mutation / duplication / deletion could apply !
	 */
	public static boolean mutate(int mutRate) {
		Random mut = new Random();
		return (mut.nextInt(100) < mutRate);
	}
	
	/**
	 * Minimal mutation ; chosen and preffered (than change in sequence). 
	 * <br/>Alternates changes in centon (100) or decon (10) 
	 * than in simple unity due to objects, locations... 
	 * <br/>To see with mutation on complete reversed sequence. 
	 * @param value Value to change. 
	 * @param moreOrLess +1 or -1. 
	 * @return the changed value. 
	 */
	public static int mutate(int value, boolean moreOrLess, int max) {
		int result = value + ((moreOrLess) ? +1 : -1);
		return (result < 0)? 0 : (result > max) ? max : result;
	}
	
	/**
	 * Mutation done via the sequence (more accurate for biology / bioinformatics). 
	 * @param gene
	 * @return Mutated Gene instance. 
	 * @deprecated TODO [ mutate(Gene gene)] Make better version and test it !!
	 */
	public static Gene mutate(Gene gene) {
		String reversed	= gene.reverseTranslation(true);
		Gene toReturn	= null;
		
		// TODO make alternate for PhaseII (Arrays.asList('U', 'B', 'V', 'P'))
		if (reversed.matches("[ACTG]+")) {
			do {
				Random rand	= new Random();
				int index	= rand.nextInt(reversed.length());
				char[] revChars	= reversed.toCharArray();
				revChars[index] = Arrays.asList('A', 'C', 'G', 'T').get(rand.nextInt(4));
				toReturn = Gene.loadGene( new String(revChars) );
			} while (toReturn != null);
		}
		
		return toReturn; 
	}
	
}

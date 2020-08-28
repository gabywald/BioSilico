package gabywald.biosilico.interfaces;

import java.util.Random;

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
	 * Minimal mutation ; choosen and preffered (than change in sequence). 
	 * <br/>Alternates changes in centon (100) or decon (10) than in simple unity due to objects, locations...
	 * @param value
	 * @param moreOrLess
	 * @return the changed value. 
	 */
	public static int mutate(int value, boolean moreOrLess, int max) {
		int result = value + ((moreOrLess) ? -1 : +1);
		return (result < 0)? 0 : (result > max) ? max : result;
	}
}

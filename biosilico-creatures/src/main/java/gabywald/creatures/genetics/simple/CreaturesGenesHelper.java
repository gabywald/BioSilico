package gabywald.creatures.genetics.simple;

import java.util.List;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public final class CreaturesGenesHelper {
	
	/** Avoid instanciation. */
	private CreaturesGenesHelper() { ; }
	
	/**
	 * 
	 * @param <T> Common class used here !
	 * @param lstContent
	 * @param attemptedLength
	 * @param classe
	 * @return Number of errors. 
	 */
	public static <T> int applyCheckContent(List<T> lstContent, int attemptedLength, Class<T> classe) {
		int haserror = 0;
		
		while (lstContent.size() < attemptedLength) {
			try { lstContent.add( classe.newInstance() ); } 
			// XXX NOTE : throw an exception here ?!
			catch (InstantiationException e)	{ e.printStackTrace(); } 
			catch (IllegalAccessException e)	{ e.printStackTrace(); }
			haserror++;
		}
		if (lstContent.size() > attemptedLength) 
			{ lstContent.subList(attemptedLength, lstContent.size()).clear(); }
		return haserror;
	}
}

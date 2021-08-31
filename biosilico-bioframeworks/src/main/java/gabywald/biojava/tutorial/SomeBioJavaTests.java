package gabywald.biojava.tutorial;

import org.biojava.nbio.core.exceptions.CompoundNotFoundException;
import org.biojava.nbio.core.sequence.RNASequence;

import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class SomeBioJavaTests {

	public static void main(String[] args) {
		
		try {
			RNASequence rnaSequence = new RNASequence("GUAC");
			Logger.printlnLog(LoggerLevel.LL_INFO, rnaSequence.toString());
		} catch (CompoundNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

}

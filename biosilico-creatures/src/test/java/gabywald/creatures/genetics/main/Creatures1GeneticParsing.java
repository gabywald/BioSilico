package gabywald.creatures.genetics.main;

import gabywald.creatures.geneticReader.GeneticFileContent;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class Creatures1GeneticParsing {

	public static void main(String[] args) {
		if (args.length < 1) {
			Logger.printlnLog(LoggerLevel.LL_INFO, "No FilePath in argument !");
			System.exit(1);
		}
		
		GeneticFileContent gfc01 = new GeneticFileContent( args[0] );
		while (gfc01.isReadable()) {
			char tmpChar = gfc01.nextChar();
			if ( (tmpChar != -1) ) {
				Logger.printLog(LoggerLevel.LL_FORUSER, " [" + tmpChar + "]");
			} // END "if (tmpChar != -1)" 
		} // END "while (gfc.isReadable()))" */
		Logger.printlnLog(LoggerLevel.LL_FORUSER, "");
		
		GeneticFileContent gfc02 = new GeneticFileContent( args[0] );
		while (gfc02.isReadable()) {
			byte tmpByte = gfc02.nextByte();
			if ( (tmpByte != -1) /** && (GeneticFileContent.isAlphaNumeric(tmpChar)) */ ) {
				Logger.printLog(LoggerLevel.LL_FORUSER, " [" + tmpByte + "]");
			} // END "if (tmpByte != -1)" 
		} // END "while (gfc.isReadable()))" */
		Logger.printlnLog(LoggerLevel.LL_FORUSER, "");
		
		GeneticFileContent gfc03 = new GeneticFileContent( args[0] );
		while (gfc03.isReadable()) {
			String tmpBytes = new String( gfc03.nextSequenceOfBytes() );
			Logger.printLog(LoggerLevel.LL_FORUSER, " [" + tmpBytes + "]");
		} // END "while (gfc.isReadable()))" */
		Logger.printlnLog(LoggerLevel.LL_FORUSER, "");
		
		
	}

}

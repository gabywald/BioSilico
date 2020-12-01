package gabywald.creatures.genetics.simple;

import gabywald.creatures.geneticReader.GeneticFileContent;
import gabywald.creatures.genetics.simple.factory.GeneCreatures1Factory;
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
		
//		GeneticFileContent gfc01 = new GeneticFileContent( args[0] );
//		while (gfc01.isReadable()) {
//			char tmpChar = gfc01.nextChar();
//			if ( (tmpChar != -1) ) {
//				Logger.printLog(LoggerLevel.LL_FORUSER, " [" + tmpChar + "]");
//			} // END "if (tmpChar != -1)" 
//		} // END "while (gfc.isReadable()))" */
//		Logger.printlnLog(LoggerLevel.LL_FORUSER, "");
//		
//		GeneticFileContent gfc02 = new GeneticFileContent( args[0] );
//		while (gfc02.isReadable()) {
//			byte tmpByte = gfc02.nextByte();
//			if ( (tmpByte != -1) /** && (GeneticFileContent.isAlphaNumeric(tmpChar)) */ ) {
//				Logger.printLog(LoggerLevel.LL_FORUSER, " [" + tmpByte + "]");
//			} // END "if (tmpByte != -1)" 
//		} // END "while (gfc.isReadable()))" */
//		Logger.printlnLog(LoggerLevel.LL_FORUSER, "");
		
		GeneticFileContent gfc03 = new GeneticFileContent( args[0] );
		while (gfc03.isReadable()) {
			String tmpBytes = new String( gfc03.nextSequenceOfBytes() );
			// Logger.printLog(LoggerLevel.LL_FORUSER, " [" + tmpBytes + "]");
			if (tmpBytes.equals("gene") || tmpBytes.equals("gend")) {
				Logger.printlnLog(LoggerLevel.LL_INFO, "GENE / GEND");
				continue;
			}
			
			Creatures1GeneticParsing.readGene(tmpBytes, (byte)1);
			
		} // END "while (gfc.isReadable()))" */
		Logger.printlnLog(LoggerLevel.LL_FORUSER, "");
		
		
	}
	
	public static void readGene(String input, byte creatureVersion) {
		int index	= 0;
		int type	= input.charAt(index++); // 0
		int subt	= input.charAt(index++); // 1
//		int iden	= input.charAt(index++); // 2
//		int numg	= input.charAt(index++); // 3
//		int agee	= input.charAt(index++); // 4
//		int flags	= input.charAt(index++); // 5
//		/** For Creatures 2 and Creatures 3 : mutation rate. */
//		int mutr	= 80; /** C1 by  default. */
//		if (creatureVersion >= 2) 
//			{ mutr  = input.charAt(index++); }  // 10 // mutability
//		/** For Creature 3 / Village. */
//		// ... 
		
		// Logger.printlnLog(LoggerLevel.LL_FORUSER, "TYPE: [" + type + "] \t SUBT: [" + subt + "]");
		
		GeneCreatures1 gc1 = GeneCreatures1Factory.generateFrom(type + "-" + subt, 
													input.substring(0, input.length() - 4));
		
		if (gc1 != null) {
			Logger.printlnLog(LoggerLevel.LL_FORUSER, gc1.printInline());
		}
	}

}

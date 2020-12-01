package gabywald.creatures.genetics.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gabywald.creatures.geneticReader.GeneticFileContent;
import gabywald.creatures.genetics.simple.factory.GeneCreatures1Factory;
import gabywald.global.data.File;
import gabywald.global.exceptions.DataException;
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
		
		List<GeneCreatures1> lstGenesC1 = new ArrayList<GeneCreatures1>();
		GeneticFileContent gfc03 = new GeneticFileContent( args[0] );
		while (gfc03.isReadable()) {
			String tmpBytes = new String( gfc03.nextSequenceOfBytes() );
			// Logger.printLog(LoggerLevel.LL_FORUSER, " [" + tmpBytes + "]");
			if (tmpBytes.equals("gene")) {
				Logger.printlnLog(LoggerLevel.LL_INFO, "GENE");
				continue;
			}
			// if ( (tmpBytes.endsWith("gene")) || (tmpBytes.endsWith("gend")) )
			// 	{ tmpBytes = tmpBytes.substring(0, tmpBytes.length() - 4); }
			if (tmpBytes.endsWith("gend")) {
				// XXX NOTE last gene ? 
				Logger.printlnLog(LoggerLevel.LL_INFO, "GEND" + tmpBytes);
				break;
			}
			
			if ( (tmpBytes.endsWith("gene")) ) // || (tmpBytes.endsWith("gend")) )
				{ tmpBytes = tmpBytes.substring(0, tmpBytes.length() - 4); }
			
			GeneCreatures1 gc1 = Creatures1GeneticParsing.readGene( tmpBytes );
			if (gc1 != null) { Logger.printlnLog(LoggerLevel.LL_INFO, gc1.printInline()); }
			if (gc1 != null) { lstGenesC1.add( gc1 ); }
			
		} // END "while (gfc.isReadable()))" */
		
		Pattern pGenome = Pattern.compile(".*?\\/([0-9A-Za-z]{4})\\.gen$");
		Matcher mGenome = pGenome.matcher( args[0] );
		String genomeName = (mGenome.matches()) ? mGenome.group(1) : "UNK0";
		Logger.printlnLog(LoggerLevel.LL_INFO, "{" + args[0] + "}\t{" + genomeName + "}");
		
		StringBuilder sbToExport = new StringBuilder();
		for (GeneCreatures1 gc : lstGenesC1) {
			sbToExport.append( gc.printInline()).append("\n");
		}
		sbToExport	.append("Number of genes (").append(lstGenesC1.size())
					.append(") {").append(genomeName).append("}\n");
		Logger.printlnLog(LoggerLevel.LL_FORUSER, sbToExport.toString());
		
		String basePathToExport	= "resources/creatures/creaturesDocs/geneticsParsing/java/exported/";
		String exportExtension	= ".export";
		
		String fileNameToExport	= basePathToExport + genomeName + exportExtension;
		
		Logger.printlnLog(LoggerLevel.LL_FORUSER, fileNameToExport);
		
		try {
			// XXX NOTE : better location for export (here mis-located)
			// TODO better location of export see biosilico-common && utilities (from PropertiesLoader for OutputStream !)
			File exportFile = new File(	fileNameToExport, sbToExport.toString().split("\n"));
			Logger.printlnLog(LoggerLevel.LL_INFO, exportFile.printFile() );
		} catch (DataException e) {
			e.printStackTrace();
		}
		
	}
	
	public static GeneCreatures1 readGene(String input) {
		int type = input.charAt( 0 );
		int subt = input.charAt( 1 );
		return GeneCreatures1Factory.generateFrom(	type + "-" + subt, 
													input.substring(0, input.length()));
	}

}

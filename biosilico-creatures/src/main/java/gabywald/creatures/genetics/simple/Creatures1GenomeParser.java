package gabywald.creatures.genetics.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gabywald.creatures.exceptions.ParserException;
import gabywald.creatures.geneticReader.GeneticFileContent;
import gabywald.global.data.File;
import gabywald.global.exceptions.DataException;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;
import gabywald.utilities.others.PropertiesLoader;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class Creatures1GenomeParser {
	
	public static final PropertiesLoader PROPERTIES = new PropertiesLoader( "configuration.properties" );

	public static CreaturesGenome parseGenome(String filePath)  
			throws ParserException {
		if (filePath == null)		{ throw new ParserException( "File Path is null !" ); }
		if (filePath.equals(""))	{ throw new ParserException( "File Path is empty !" ); }
		
		List<ICreaturesGene> lstGenesC1 = new ArrayList<ICreaturesGene>();
		GeneticFileContent gfc = new GeneticFileContent( filePath );
		while (gfc.isReadable()) {
			String nextGene		= new String( gfc.nextGene() );
			Creatures1Gene gc1	= GeneticFileContent.readGene( nextGene );
			if (gc1 != null) { 
				Logger.printlnLog(LoggerLevel.LL_DEBUG, gc1.printInline());
				gc1 = gc1.autocheck();
				lstGenesC1.add( gc1 ); 
			}
		} // END "while (gfc.isReadable()))" */
		
		Pattern pGenome = Pattern.compile(".*?\\/([0-9A-Za-z]{4})\\.gen$");
		Matcher mGenome = pGenome.matcher( filePath );
		String genomeName = (mGenome.matches()) ? mGenome.group(1) : "UNK0";
		Logger.printlnLog(LoggerLevel.LL_INFO, "{" + filePath + "}\t{" + genomeName + "}");
		
		StringBuilder sbToExport = new StringBuilder();
		for (ICreaturesGene gc : lstGenesC1) 
			{ sbToExport.append( gc.printInline()).append("\n"); }
		sbToExport	.append("Number of genes (").append(lstGenesC1.size())
					.append(") {").append(genomeName).append("}\n");
		Logger.printlnLog(LoggerLevel.LL_FORUSER, sbToExport.toString());
		
		String basePathToExport	= Creatures1GenomeParser.PROPERTIES.getProperty( "parsing.export.creatures1" );
		String exportExtension	= Creatures1GenomeParser.PROPERTIES.getProperty( "parsing.export.extension" );
		
		String fileNameToExport	= basePathToExport + genomeName + exportExtension;
		
		Logger.printlnLog(LoggerLevel.LL_FORUSER, fileNameToExport);
		Logger.printlnLog(LoggerLevel.LL_FORUSER, PropertiesLoader.resolvePath( fileNameToExport ) );
		
		try {
			File exportFile = new File(	fileNameToExport, sbToExport.toString().split("\n"));
			Logger.printlnLog(LoggerLevel.LL_INFO, exportFile.printFile() );
		} catch (DataException e) {
			e.printStackTrace();
		}
		
		return new CreaturesGenome(genomeName, filePath, lstGenesC1);
	}
	


}

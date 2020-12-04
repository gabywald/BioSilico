package gabywald.creatures.genetics.simple;

import java.util.List;

import gabywald.global.data.File;
import gabywald.global.exceptions.DataException;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;
import gabywald.utilities.others.PropertiesLoader;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class CreaturesGenome {
	private String name;
	private String pathOfFile;
	private List<ICreaturesGene> genome;
	
	public CreaturesGenome(String name, String path, List<ICreaturesGene> genome) {
		this.name		= name;
		this.pathOfFile	= path;
		this.genome		= genome;
	}

	public String getName()
		{ return this.name; }

	public String getPathOfFile() 
		{ return this.pathOfFile; }

	public List<ICreaturesGene> getGenome() 
		{ return this.genome; }
	
	/**
	 * In order to export genome as "txt". 
	 */
	public void exportGenome() {
		StringBuilder sbToExport = new StringBuilder();
		for (ICreaturesGene gc : this.genome) 
			{ sbToExport.append( gc.printInline()).append("\n"); }
		sbToExport	.append("Number of genes (").append(this.genome.size())
					.append(") {").append( this.name ).append("}\n");
		Logger.printlnLog(LoggerLevel.LL_FORUSER, sbToExport.toString());
		
		String basePathToExport	= Creatures1GenomeParser.PROPERTIES.getProperty( "parsing.export.creatures1" );
		String exportExtension	= Creatures1GenomeParser.PROPERTIES.getProperty( "parsing.export.extension" );
		
		String fileNameToExport	= basePathToExport + this.name + exportExtension;
		
		Logger.printlnLog(LoggerLevel.LL_FORUSER, fileNameToExport);
		Logger.printlnLog(LoggerLevel.LL_FORUSER, PropertiesLoader.resolvePath( fileNameToExport ) );
		
		try {
			File exportFile = new File(	fileNameToExport, sbToExport.toString().split("\n"));
			Logger.printlnLog(LoggerLevel.LL_INFO, exportFile.printFile() );
		} catch (DataException e) {
			e.printStackTrace();
		}
	}
	
}

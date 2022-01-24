package gabywald.biosilico.anthill.launcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import gabywald.biosilico.model.Agent;
import gabywald.biosilico.model.Chromosome;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.environment.World2DCase;
import gabywald.biosilico.model.utils.agents.EnergySource;
import gabywald.global.data.File;
import gabywald.global.data.StringUtils;
import gabywald.global.exceptions.DataException;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020, 2022)
 */
public abstract class BuildingGenomeHelper {
	
	public static final int indexLessRemoveDirection = 800;
	
	public static final int indexLessRemovePheromone = 350;

	public static void exportAsTXTfile(String fileName, Organism testAnt) {
		File antGeneticData = new File("src/test/resources/" + fileName);
		antGeneticData.setChamps( testAnt.toString().split("\n") );
		antGeneticData.setChamps(0, "TAXON ID\tRecording from test. ");
		try {
			antGeneticData.printFile();
		} catch (DataException e) {
			// e.printStackTrace();
			String msg = "Cannot write {" + antGeneticData.getFileName() + "} ; DataException {" + e.getMessage() + "}";
			Logger.printlnLog(LoggerLevel.LL_ERROR, msg);
		}
	}
	
	public static void exportGenome(String fileName, Organism testAnt) {
		StringBuilder result = new StringBuilder();
		List<Chromosome> genome = testAnt.getGenome();
		for (int i = 0 ; i < genome.size() ; i++) {
			Chromosome chr = genome.get(i);
			result.append("## ").append(chr.getName()).append("\n");
			chr.streamGene().forEach( g -> {
				result.append(g.getName()).append( "\t" ).append(g.toString()).append( "\n" );
			});
		} // END "for (int i = 0 ; i < this.genome.size() ; i++)"
		
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "\n" + result.toString());
		
		File geneticData = new File("src/test/resources/" + fileName);
		geneticData.setChamps( result.toString().split("\n") );
		try {
			geneticData.printFile();
		} catch (DataException e) {
			// e.printStackTrace();
			String msg = "Cannot write {" + geneticData.getFileName() + "} ; DataException {" + e.getMessage() + "}";
			Logger.printlnLog(LoggerLevel.LL_ERROR, msg);
		}
	}
	
	public static void copyMoveGenome(String sourceFile, String destinationFile) {
		try {
			Files.copy( Paths.get("src/test/resources/" + sourceFile), Paths.get("src/main/resources/anthill/" + destinationFile), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// e.printStackTrace();
			String msg = "Cannot move {" + "src/test/resources/" + sourceFile + "} to {" + "src/main/resources/anthill/" + destinationFile + "}; IOException {" + e.getMessage() + "}";
			Logger.printlnLog(LoggerLevel.LL_ERROR, msg);
		}
	}
	
	public static void removeACGTsequence(String destinationFile) {
		
		String filePath = "src/main/resources/anthill/" + destinationFile;
		
		try {
			File geneticData = File.loadFile( filePath );
			
			for (int i = 0 ; i < geneticData.lengthFile() ; i++) {
				String dataLine = geneticData.getChamp( i );
				geneticData.setChamps(i, dataLine.replaceAll("\\t[ACGT]+\\t", "\t"));
			}
			
			geneticData.printFile();
			
		} catch (IOException e) {
			// e.printStackTrace();
			String msg = "Cannot read {" + filePath + "} ; IOException {" + e.getMessage() + "}";
			Logger.printlnLog(LoggerLevel.LL_ERROR, msg);
		} catch (DataException e) {
			// e.printStackTrace();
			String msg = "Cannot print {" + filePath + "} ; DataException {" + e.getMessage() + "}";
			Logger.printlnLog(LoggerLevel.LL_ERROR, msg);
		}
		
	}
	
	public static void show(Agent testAgent) {
		BuildingGenomeHelper.show((Organism) testAgent, null, null);
	}
	
	public static void show(Organism testOrg, World2DCase wc) {
		BuildingGenomeHelper.show(testOrg, null, wc);
	}
	
	public static void show(Organism testOrg, EnergySource es, World2DCase wc) {
		if (testOrg != null) { 
			Logger.printlnLog(LoggerLevel.LL_DEBUG, testOrg.toString() ); 
			Logger.printlnLog(LoggerLevel.LL_DEBUG, StringUtils.repeat("+", 80) );
		}
		if (es != null) { 
			Logger.printlnLog(LoggerLevel.LL_DEBUG, es.toString() );
			Logger.printlnLog(LoggerLevel.LL_DEBUG, StringUtils.repeat("+", 80) );
		}
		if (wc != null) { 
			Logger.printlnLog(LoggerLevel.LL_DEBUG, wc.toString() );
			Logger.printlnLog(LoggerLevel.LL_DEBUG, StringUtils.repeat("*", 80) );
		}
	}
	
	/**
	 * SOME LOGS ! (change it if needed to LL_INFO // LL_FORUSER
	 * @param ant
	 * @param plant
	 * @param wcs
	 */
	public static void showAll(Organism ant, Organism plant, List<World2DCase> wcs) {
		BuildingGenomeHelper.show( ant );
		BuildingGenomeHelper.show( plant );
		wcs.stream().forEach( wc -> {
			Logger.printlnLog(LoggerLevel.LL_DEBUG, wc.toString());
			Logger.printlnLog(LoggerLevel.LL_DEBUG, StringUtils.repeat("+", 80) );
		});
		Logger.printlnLog(LoggerLevel.LL_DEBUG, StringUtils.repeat("*", 80) );
	}
	
}

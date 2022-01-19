package gabywald.biosilico.anthill.computations;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.anthill.Ant;
import gabywald.biosilico.anthill.Plant;
import gabywald.biosilico.anthill.launcher.AntHillExampleHelper;
import gabywald.biosilico.anthill.launcher.BuildingGenomeHelper;
import gabywald.biosilico.model.enums.SomeChemicals;
import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.environment.World2D;
import gabywald.biosilico.model.environment.World2DCase;
import gabywald.biosilico.model.utils.agents.EnergySource;
import gabywald.global.data.File;
import gabywald.global.exceptions.DataException;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;


/**
 * 
 * @author Gabriel Chandesris (2022)
 */
class DataExporterAndViewAnalysis {
	
	/**
	 * Import / Export ANT
	 */
	@Test
	void testLoadingAnt() {
		
		Ant testAnt = new Ant();
		Assertions.assertNotNull( testAnt );
		Assertions.assertEquals(0, testAnt.getGenome().size());
		
		testAnt.setRank("Rank Test");
		testAnt.setNameCommon("Test Starting Ant");
		testAnt.setNameBiosilico("AntHill Ant Example");
		testAnt.setDivision("TESTS");
		
		testAnt.setGenome( AntHillExampleHelper.loadingAntGenome() );
		
		// ***** Export Ant as a TXT file !
		BuildingGenomeHelper.exportAsTXTfile("InitialAntTest.txt", testAnt);
	}

	/**
	 * Import / Export PLANT
	 */
	@Test
	void testLoadingPlant() {
		
		Plant testPlant = new Plant();
		Assertions.assertNotNull( testPlant );
		Assertions.assertEquals(0, testPlant.getGenome().size());
		
		testPlant.setRank("Rank Test");
		testPlant.setNameCommon("Test Starting Plant");
		testPlant.setNameBiosilico("AntHill Plant Example");
		testPlant.setDivision("TESTS");
		
		testPlant.setGenome( AntHillExampleHelper.loadingPlantGenome() );
		
		// ***** Export Plant as a TXT file !
		BuildingGenomeHelper.exportAsTXTfile("InitialPlantTest.txt", testPlant);
	}

	@Test
	void testPlantExportChemicalsOnSteps() {
		
		Plant testPlant = new Plant();
		Assertions.assertNotNull( testPlant );
		Assertions.assertEquals(0, testPlant.getGenome().size());
		
		testPlant.setRank("Rank Test");
		testPlant.setNameCommon("Test Starting Plant");
		testPlant.setNameBiosilico("AntHill Plant Example");
		testPlant.setDivision("TESTS");
		
		testPlant.setGenome( AntHillExampleHelper.loadingPlantGenome() );
		
		// ***** test with a World and WorldCase
		World2D w		= new World2D(1, 1);
		World2DCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testPlant.setCurrentWorldCase( wc );
		// ***** Put DiOxygen && H2O && Energy in local WorldCase !!
		wc.getChemicals().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 	100);
		wc.getChemicals().setVariable(SomeChemicals.WATER.getIndex(), 		100);
		EnergySource es = new EnergySource();
		wc.addAgent( es );
		
		StringBuilder sbExportData = new StringBuilder();
		
		// ***** one execution in this context
		IntStream.range(0, 200).forEach( i -> {
			w.execution();
			testPlant.cyclePlusPlus(); // Aging organism
			sbExportData.append( "STEP [")
						.append( testPlant.getChemicals().getVariable(StateType.AGING.getIndex()) ) 
						.append("]\n");
			sbExportData.append( testPlant.getChemicals().toString() ).append( "*****\n" );
			sbExportData.append( wc.getChemicals().toString() ).append( "*****\n" );
		});
		
		File statisticsData = new File( "src/test/resources/" + "ExportPlantStatistics.txt" );
		statisticsData.setChamps( sbExportData.toString().split("\n") );
		try {
			statisticsData.printFile();
		} catch (DataException e) {
			// e.printStackTrace();
			String msg = "Cannot write {" + statisticsData.getFileName() + "} ; DataException {" + e.getMessage() + "}";
			Logger.printlnLog(LoggerLevel.LL_ERROR, msg);
		}
	}
	
	@Test
	void testAntExportChemicalsOnSteps() {
		
		Ant testAnt = new Ant();
		Assertions.assertNotNull( testAnt );
		Assertions.assertEquals(0, testAnt.getGenome().size());
		
		testAnt.setRank("Rank Test");
		testAnt.setNameCommon("Test Starting Ant");
		testAnt.setNameBiosilico("AntHill Ant Example");
		testAnt.setDivision("TESTS");
		
		testAnt.setGenome( AntHillExampleHelper.loadingAntGenome() );
		
		// ***** test with a World and WorldCase
		World2D w		= new World2D(1, 1);
		World2DCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testAnt.setCurrentWorldCase( wc );
		// ***** Put DiOxygen && H2O && Energy in local WorldCase !!
		wc.getChemicals().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 	100);
		wc.getChemicals().setVariable(SomeChemicals.WATER.getIndex(), 		100);
		EnergySource es = new EnergySource();
		wc.addAgent( es );
		
		StringBuilder sbExportData = new StringBuilder();
		
		// ***** one execution in this context
		IntStream.range(0, 200).forEach( i -> {
			w.execution();
			testAnt.cyclePlusPlus(); // Aging organism
			sbExportData.append( "STEP [")
						.append( testAnt.getChemicals().getVariable(StateType.AGING.getIndex()) ) 
						.append("]\n");
			sbExportData.append( testAnt.getChemicals().toString() ).append( "*****\n" );
			sbExportData.append( wc.getChemicals().toString() ).append( "*****\n" );
		});
		
		File statisticsData = new File( "src/test/resources/" + "ExportAntStatistics.txt" );
		statisticsData.setChamps( sbExportData.toString().split("\n") );
		try {
			statisticsData.printFile();
		} catch (DataException e) {
			// e.printStackTrace();
			String msg = "Cannot write {" + statisticsData.getFileName() + "} ; DataException {" + e.getMessage() + "}";
			Logger.printlnLog(LoggerLevel.LL_ERROR, msg);
		}
	}
	
	@Test
	void testAntAndPlantExportChemicalsOnSteps() {
		
		Ant testAnt = new Ant();
		Assertions.assertNotNull( testAnt );
		Assertions.assertEquals(0, testAnt.getGenome().size());
		
		testAnt.setRank("Rank Test");
		testAnt.setNameCommon("Test Starting Ant");
		testAnt.setNameBiosilico("AntHill Ant Example");
		testAnt.setDivision("TESTS");
		
		testAnt.setGenome( AntHillExampleHelper.loadingAntGenome() );
		
		Plant testPlant = new Plant();
		Assertions.assertNotNull( testPlant );
		Assertions.assertEquals(0, testPlant.getGenome().size());
		
		testPlant.setRank("Rank Test");
		testPlant.setNameCommon("Test Starting Plant");
		testPlant.setNameBiosilico("AntHill Plant Example");
		testPlant.setDivision("TESTS");
		
		testPlant.setGenome( AntHillExampleHelper.loadingPlantGenome() );
		
		// ***** test with a World and WorldCase
		World2D w		= new World2D(1, 1);
		World2DCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testAnt.setCurrentWorldCase( wc );
		testPlant.setCurrentWorldCase( wc );
		// ***** Put DiOxygen && H2O && Energy in local WorldCase !!
		wc.getChemicals().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 	100);
		wc.getChemicals().setVariable(SomeChemicals.WATER.getIndex(), 		100);
		EnergySource es = new EnergySource();
		wc.addAgent( es );
		
		StringBuilder sbExportData = new StringBuilder();
		
		// ***** one execution in this context
		IntStream.range(0, 200).forEach( i -> {
			w.execution();
			testAnt.cyclePlusPlus(); // Aging organism
			testPlant.cyclePlusPlus(); // Aging organism
			sbExportData.append( "STEP [")
						.append( testAnt.getChemicals().getVariable(StateType.AGING.getIndex()) ) 
						.append("][")
						.append( testPlant.getChemicals().getVariable(StateType.AGING.getIndex()) ) 
						.append("]\n");
			sbExportData.append( testAnt.getChemicals().toString() ).append( "*****\n" );
			sbExportData.append( testPlant.getChemicals().toString() ).append( "*****\n" );
			sbExportData.append( wc.getChemicals().toString() ).append( "*****\n" );
		});
		
		File statisticsData = new File( "src/test/resources/" + "ExportAntAndPlantStatistics.txt" );
		statisticsData.setChamps( sbExportData.toString().split("\n") );
		try {
			statisticsData.printFile();
		} catch (DataException e) {
			// e.printStackTrace();
			String msg = "Cannot write {" + statisticsData.getFileName() + "} ; DataException {" + e.getMessage() + "}";
			Logger.printlnLog(LoggerLevel.LL_ERROR, msg);
		}
	}
	
}

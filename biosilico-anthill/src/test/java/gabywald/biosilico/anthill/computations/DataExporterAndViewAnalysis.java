package gabywald.biosilico.anthill.computations;

import java.util.ArrayList;
import java.util.List;
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
 * Tests t export and analyse datas for evolution about a plant / an ant / combination. 
 * With Source of energy (both heat and solar), and O2 and CO2. 
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

	@Test
	void testAntAndPlantExportChemicalsOnSteps02() {
		
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
		IntStream.range(0, 100).forEach( i -> {
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

		wc.getChemicals().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 	100);
		wc.getChemicals().setVariable(SomeChemicals.WATER.getIndex(), 		100);
		
		IntStream.range(101, 200).forEach( i -> {
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

		wc.getChemicals().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 	100);
		wc.getChemicals().setVariable(SomeChemicals.WATER.getIndex(), 		100);
		
		IntStream.range(201, 300).forEach( i -> {
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
		
		File statisticsData = new File( "src/test/resources/" + "ExportAntAndPlantStatistics02.txt" );
		statisticsData.setChamps( sbExportData.toString().split("\n") );
		try {
			statisticsData.printFile();
		} catch (DataException e) {
			// e.printStackTrace();
			String msg = "Cannot write {" + statisticsData.getFileName() + "} ; DataException {" + e.getMessage() + "}";
			Logger.printlnLog(LoggerLevel.LL_ERROR, msg);
		}
	}

	
	public static final List<SomeChemicals> toFilterInInt = new ArrayList<SomeChemicals>();
	static {
		toFilterInInt.add(SomeChemicals.ENERGY_HEAT);
		toFilterInInt.add(SomeChemicals.ENERGY_SOLAR);
		toFilterInInt.add(SomeChemicals.DIOXYGEN);
		toFilterInInt.add(SomeChemicals.CARBON_DIOXYDE);
		toFilterInInt.add(SomeChemicals.WATER);
		toFilterInInt.add(SomeChemicals.GLUCOSE);
		toFilterInInt.add(SomeChemicals.STARCH);
		// toFilterInInt.add(StateType.AGING);
	}
	
	@Test
	void testPlantExportImageData() {
		
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
		
		DataCollector sbExportData = new DataCollector("Plant Analysis", "Steps", "Values of Chemicals");
		
		// ***** one execution in this context
		IntStream.range(0, 200).forEach( i -> {
			w.execution();
			testPlant.cyclePlusPlus(); // Aging organism
			int steps = i;
			int aging = testPlant.getChemicals().getVariable(StateType.AGING.getIndex());
			sbExportData.addValue(	aging, StateType.AGING.name(), steps + "" );
			toFilterInInt.stream().forEach( chem -> {
				sbExportData.addValue(	testPlant.getChemicals().getVariable( chem.getIndex() ), 
										"plant" + chem.getName(), steps + "");
				sbExportData.addValue(	wc.getChemicals().getVariable( chem.getIndex() ), 
										"wc" + chem.getName(), steps + "");				
			});
		});
		
		sbExportData.buildImage( "src/test/resources/" + "ExportPlantStatistics.jpeg" );
	}
	
	@Test
	void testAntExportImageData() {
		
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
		
		DataCollector sbExportData = new DataCollector("Ant Analysis", "Steps", "Values of Chemicals");
		
		// ***** one execution in this context
		IntStream.range(0, 200).forEach( i -> {
			w.execution();
			testAnt.cyclePlusPlus(); // Aging organism
			int steps = i;
			int aging = testAnt.getChemicals().getVariable(StateType.AGING.getIndex());
			sbExportData.addValue(	aging, StateType.AGING.name(), steps + "" );
			toFilterInInt.stream().forEach( chem -> {
				sbExportData.addValue(	testAnt.getChemicals().getVariable( chem.getIndex() ), 
										"ant" + chem.getName(), steps + "");
				sbExportData.addValue(	wc.getChemicals().getVariable( chem.getIndex() ), 
										"wc*" + chem.getName(), steps + "");				
			});
		});
		
		sbExportData.buildImage( "src/test/resources/" + "ExportAntStatistics.jpeg" );
	}
	
	@Test
	void testAntAndPlantExportImageData() {
		
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
		
		
		DataCollector sbExportData = new DataCollector("Plant Analysis", "Steps", "Values of Chemicals");
		
		// ***** one execution in this context
		IntStream.range(0, 200).forEach( i -> {
			w.execution();
			testPlant.cyclePlusPlus(); // Aging organism
			testAnt.cyclePlusPlus(); // Aging organism
			int steps = i;
			int aging = testPlant.getChemicals().getVariable(StateType.AGING.getIndex());
			// int aging = testAnt.getChemicals().getVariable(StateType.AGING.getIndex());
			sbExportData.addValue(	aging, StateType.AGING.name(), steps + "" );
			toFilterInInt.stream().forEach( chem -> {
				sbExportData.addValue(	testPlant.getChemicals().getVariable( chem.getIndex() ), 
										"plant" + chem.getName(), steps + "");
				sbExportData.addValue(	testAnt.getChemicals().getVariable( chem.getIndex() ), 
										"ant" + chem.getName(), steps + "");
				sbExportData.addValue(	wc.getChemicals().getVariable( chem.getIndex() ), 
										"wc" + chem.getName(), steps + "");				
			});
		});
		
		sbExportData.buildImage( "src/test/resources/" + "ExportAntAndPlantStatistics.jpeg" );
	}

	@Test
	void testAntAndPlantExportImageData02() {
		
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
		
		
		DataCollector sbExportData = new DataCollector("Plant Analysis", "Steps", "Values of Chemicals");
		
		// ***** one execution in this context
		IntStream.range(0, 100).forEach( i -> {
			w.execution();
			testPlant.cyclePlusPlus(); // Aging organism
			testAnt.cyclePlusPlus(); // Aging organism
			int steps = i;
			int aging = testPlant.getChemicals().getVariable(StateType.AGING.getIndex());
			// int aging = testAnt.getChemicals().getVariable(StateType.AGING.getIndex());
			sbExportData.addValue(	aging, StateType.AGING.name(), steps + "" );
			toFilterInInt.stream().forEach( chem -> {
				sbExportData.addValue(	testPlant.getChemicals().getVariable( chem.getIndex() ), 
										"plant" + chem.getName(), steps + "");
				sbExportData.addValue(	testAnt.getChemicals().getVariable( chem.getIndex() ), 
										"ant" + chem.getName(), steps + "");
				sbExportData.addValue(	wc.getChemicals().getVariable( chem.getIndex() ), 
										"wc" + chem.getName(), steps + "");				
			});
		});
		
		// ***** Put DiOxygen && H2O && Energy in local WorldCase !!
		wc.getChemicals().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 	100);
		wc.getChemicals().setVariable(SomeChemicals.WATER.getIndex(), 		100);
		
		IntStream.range(101, 200).forEach( i -> {
			w.execution();
			testPlant.cyclePlusPlus(); // Aging organism
			testAnt.cyclePlusPlus(); // Aging organism
			int steps = i;
			int aging = testPlant.getChemicals().getVariable(StateType.AGING.getIndex());
			// int aging = testAnt.getChemicals().getVariable(StateType.AGING.getIndex());
			sbExportData.addValue(	aging, StateType.AGING.name(), steps + "" );
			toFilterInInt.stream().forEach( chem -> {
				sbExportData.addValue(	testPlant.getChemicals().getVariable( chem.getIndex() ), 
										"plant" + chem.getName(), steps + "");
				sbExportData.addValue(	testAnt.getChemicals().getVariable( chem.getIndex() ), 
										"ant" + chem.getName(), steps + "");
				sbExportData.addValue(	wc.getChemicals().getVariable( chem.getIndex() ), 
										"wc" + chem.getName(), steps + "");				
			});
		});
		
		// ***** Put DiOxygen && H2O && Energy in local WorldCase !!
		wc.getChemicals().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 	100);
		wc.getChemicals().setVariable(SomeChemicals.WATER.getIndex(), 		100);
		
		IntStream.range(201, 300).forEach( i -> {
			w.execution();
			testPlant.cyclePlusPlus(); // Aging organism
			testAnt.cyclePlusPlus(); // Aging organism
			int steps = i;
			int aging = testPlant.getChemicals().getVariable(StateType.AGING.getIndex());
			// int aging = testAnt.getChemicals().getVariable(StateType.AGING.getIndex());
			sbExportData.addValue(	aging, StateType.AGING.name(), steps + "" );
			toFilterInInt.stream().forEach( chem -> {
				sbExportData.addValue(	testPlant.getChemicals().getVariable( chem.getIndex() ), 
										"plant" + chem.getName(), steps + "");
				sbExportData.addValue(	testAnt.getChemicals().getVariable( chem.getIndex() ), 
										"ant" + chem.getName(), steps + "");
				sbExportData.addValue(	wc.getChemicals().getVariable( chem.getIndex() ), 
										"wc" + chem.getName(), steps + "");				
			});
		});
		
		sbExportData.buildImage( "src/test/resources/" + "ExportAntAndPlantStatistics02.jpeg" );
	}

}

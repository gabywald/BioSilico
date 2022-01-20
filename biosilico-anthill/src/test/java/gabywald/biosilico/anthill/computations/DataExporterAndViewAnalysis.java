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
import gabywald.biosilico.genetics.Gene;
import gabywald.biosilico.genetics.builders.StimulusDecisionBuilder;
import gabywald.biosilico.model.enums.DecisionType;
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
		IntStream.range(0, 5).forEach( j -> {
			IntStream.range(j*BASE_COMPUTATION, j*BASE_COMPUTATION+BASE_COMPUTATION+1).forEach( i -> {
				w.execution();
				testPlant.cyclePlusPlus(); // Aging organism
				sbExportData.append( "STEP [")
							.append( testPlant.getChemicals().getVariable(StateType.AGING.getIndex()) ) 
							.append("]\n");
				sbExportData.append( testPlant.getChemicals().toString() ).append( "*****\n" );
				sbExportData.append( wc.getChemicals().toString() ).append( "*****\n" );
			});
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
		IntStream.range(0, 5).forEach( j -> {
			IntStream.range(j*BASE_COMPUTATION, j*BASE_COMPUTATION+BASE_COMPUTATION+1).forEach( i -> {
				w.execution();
				testAnt.cyclePlusPlus(); // Aging organism
				sbExportData.append( "STEP [")
							.append( testAnt.getChemicals().getVariable(StateType.AGING.getIndex()) ) 
							.append("]\n");
				sbExportData.append( testAnt.getChemicals().toString() ).append( "*****\n" );
				sbExportData.append( wc.getChemicals().toString() ).append( "*****\n" );
			});
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
		IntStream.range(0, 5).forEach( j -> {
			IntStream.range(j*BASE_COMPUTATION, j*BASE_COMPUTATION+BASE_COMPUTATION+1).forEach( i -> {
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
		
		IntStream.range(0, 5).forEach( j -> {
			IntStream.range(j*BASE_COMPUTATION, j*BASE_COMPUTATION+BASE_COMPUTATION+1).forEach( i -> {
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
	
	public static final int BASE_COMPUTATION = 50;
	
	public static final List<SomeChemicals> TO_FILTER_IN_INT = new ArrayList<SomeChemicals>();
	static {
		TO_FILTER_IN_INT.add(SomeChemicals.ENERGY_HEAT);
		TO_FILTER_IN_INT.add(SomeChemicals.ENERGY_SOLAR);
		TO_FILTER_IN_INT.add(SomeChemicals.DIOXYGEN);
		TO_FILTER_IN_INT.add(SomeChemicals.CARBON_DIOXYDE);
		TO_FILTER_IN_INT.add(SomeChemicals.WATER);
		TO_FILTER_IN_INT.add(SomeChemicals.GLUCOSE);
		TO_FILTER_IN_INT.add(SomeChemicals.STARCH);
		// TO_FILTER_IN_INT.add(StateType.AGING);
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
		IntStream.range(0, 1).forEach( j -> {
			IntStream.range(j*BASE_COMPUTATION, j*BASE_COMPUTATION+BASE_COMPUTATION+1).forEach( i -> {
				w.execution();
				testPlant.cyclePlusPlus(); // Aging organism
				int steps = i;
				int aging = testPlant.getChemicals().getVariable(StateType.AGING.getIndex());
				sbExportData.addValue(	aging, StateType.AGING.name(), steps + "" );
				TO_FILTER_IN_INT.stream().forEach( chem -> {
					sbExportData.addValue(	testPlant.getChemicals().getVariable( chem.getIndex() ), 
											"plant" + chem.getName(), steps + "");
					sbExportData.addValue(	wc.getChemicals().getVariable( chem.getIndex() ), 
											"wc" + chem.getName(), steps + "");				
				});
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
		IntStream.range(0, 1).forEach( j -> {
			IntStream.range(j*BASE_COMPUTATION, j*BASE_COMPUTATION+BASE_COMPUTATION+1).forEach( i -> {
				w.execution();
				testAnt.cyclePlusPlus(); // Aging organism
				int steps = i;
				int aging = testAnt.getChemicals().getVariable(StateType.AGING.getIndex());
				sbExportData.addValue(	aging, StateType.AGING.name(), steps + "" );
				TO_FILTER_IN_INT.stream().forEach( chem -> {
					sbExportData.addValue(	testAnt.getChemicals().getVariable( chem.getIndex() ), 
											"ant" + chem.getName(), steps + "");
					sbExportData.addValue(	wc.getChemicals().getVariable( chem.getIndex() ), 
											"wc*" + chem.getName(), steps + "");				
				});
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
		IntStream.range(0, 1).forEach( j -> {
			IntStream.range(j*BASE_COMPUTATION, j*BASE_COMPUTATION+BASE_COMPUTATION+1).forEach( i -> {
				w.execution();
				testPlant.cyclePlusPlus(); // Aging organism
				testAnt.cyclePlusPlus(); // Aging organism
				int steps = i;
				int aging = testPlant.getChemicals().getVariable(StateType.AGING.getIndex());
				// int aging = testAnt.getChemicals().getVariable(StateType.AGING.getIndex());
				sbExportData.addValue(	aging, StateType.AGING.name(), steps + "" );
				TO_FILTER_IN_INT.stream().forEach( chem -> {
					sbExportData.addValue(	testPlant.getChemicals().getVariable( chem.getIndex() ), 
											"plant" + chem.getName(), steps + "");
					sbExportData.addValue(	testAnt.getChemicals().getVariable( chem.getIndex() ), 
											"ant" + chem.getName(), steps + "");
					sbExportData.addValue(	wc.getChemicals().getVariable( chem.getIndex() ), 
											"wc" + chem.getName(), steps + "");				
				});
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
		
		IntStream.range(0, 5).forEach( j -> {
			IntStream.range(j*BASE_COMPUTATION, j*BASE_COMPUTATION+BASE_COMPUTATION+1).forEach( i -> {
				w.execution();
				testPlant.cyclePlusPlus();	// Aging organism
				testAnt.cyclePlusPlus();	// Aging organism
				int steps = i;
				int aging = testPlant.getChemicals().getVariable(StateType.AGING.getIndex());
				// int aging = testAnt.getChemicals().getVariable(StateType.AGING.getIndex());
				sbExportData.addValue(	aging, StateType.AGING.name(), steps + "" );
				TO_FILTER_IN_INT.stream().forEach( chem -> {
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
		});
		
		sbExportData.buildImage( "src/test/resources/" + "ExportAntAndPlantStatistics02.jpeg" );
	}

	@Test
	void testAntAndPlantExportImageData03() {
		
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
		
		// HERE adding new GeneS
		StimulusDecisionBuilder sdb = new StimulusDecisionBuilder();
		// New gene to EMIT water if more than 350 !
		Gene waterEmitter = sdb		.perception( false ).object( false )
									.indicator( 800 ).threshold( 350 )
									.attribute( SomeChemicals.WATER.getIndex() ).varia( SomeChemicals.WATER.getIndex() )
									.value( 30 ).script( DecisionType.EMIT.getIndex() )
								.mutate( true )	.duplicate( true )	.delete( true )	.activ( true )
								.agemin( 0 )	.agemax( 999 )		.sex( 0 )		.mutation( 5 )
								.name("StimulusDecision EMIT WATER (added)")
					.build();
		testPlant.getGenome().get(testPlant.getGenome().size() - 1 /** !! */).addGene( waterEmitter );
		// New gene to EMIT dioxygen if more than 350 !
		Gene dioxygenEmitter = sdb		.perception( false ).object( false )
										.indicator( 800 ).threshold( 350 )
										.attribute( SomeChemicals.DIOXYGEN.getIndex() ).varia( SomeChemicals.DIOXYGEN.getIndex() )
										.value( 30 ).script( DecisionType.EMIT.getIndex() )
									.mutate( true )	.duplicate( true )	.delete( true )	.activ( true )
									.agemin( 0 )	.agemax( 999 )		.sex( 0 )		.mutation( 5 )
									.name("StimulusDecision EMIT DIOXYGEN (added)")
						.build();
		testAnt.getGenome().get(testAnt.getGenome().size() - 2 /** !! */).addGene( dioxygenEmitter );

		
		Gene energyHeatEmitter = sdb		.perception( false ).object( false )
											.indicator( 800 ).threshold( 500 )
											.attribute( SomeChemicals.ENERGY_HEAT.getIndex() ).varia( SomeChemicals.ENERGY_HEAT.getIndex() )
											.value( 100 ).script( DecisionType.EMIT.getIndex() )
										.mutate( true )	.duplicate( true )	.delete( true )	.activ( true )
										.agemin( 0 )	.agemax( 999 )		.sex( 0 )		.mutation( 5 )
										.name("StimulusDecision EMIT ENERGY_HEAT (added)").build();
		Gene energySolarEmitter = sdb		.perception( false ).object( false )
											.indicator( 800 ).threshold( 500 )
											.attribute( SomeChemicals.ENERGY_SOLAR.getIndex() ).varia( SomeChemicals.ENERGY_SOLAR.getIndex() )
											.value( 100 ).script( DecisionType.EMIT.getIndex() )
										.mutate( true )	.duplicate( true )	.delete( true )	.activ( true )
										.agemin( 0 )	.agemax( 999 )		.sex( 0 )		.mutation( 5 )
										.name("StimulusDecision EMIT ENERGY_SOLAR (added)").build();
		testPlant.getGenome().get(testPlant.getGenome().size() - 1 /** !! */).addGene( energyHeatEmitter );
		testAnt.getGenome().get(testAnt.getGenome().size() - 2 /** !! */).addGene( energyHeatEmitter.clone() );
		testPlant.getGenome().get(testPlant.getGenome().size() - 1 /** !! */).addGene( energySolarEmitter );
		// testAnt.getGenome().get(testAnt.getGenome().size() - 2 /** !! */).addGene( energySolarEmitter.clone() );
		
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
		
		DataCollector sbExportData		= new DataCollector("Plant Analysis", "Steps", "Values of Chemicals");
		StringBuilder sbExportDataSTR	= new StringBuilder();
		
		IntStream.range(0, 5).forEach( j -> {
			IntStream.range(j*BASE_COMPUTATION, j*BASE_COMPUTATION+BASE_COMPUTATION+1).forEach( i -> {
				w.execution();
				testPlant.cyclePlusPlus();	// Aging organism
				testAnt.cyclePlusPlus();	// Aging organism
				int steps = i;
				int aging = testPlant.getChemicals().getVariable(StateType.AGING.getIndex());
				// int aging = testAnt.getChemicals().getVariable(StateType.AGING.getIndex());
				sbExportData.addValue(	aging, StateType.AGING.name(), steps + "" );
				TO_FILTER_IN_INT.stream().forEach( chem -> {
					sbExportData.addValue(	testPlant.getChemicals().getVariable( chem.getIndex() ), 
											"plant" + chem.getName(), steps + "");
					sbExportData.addValue(	testAnt.getChemicals().getVariable( chem.getIndex() ), 
											"ant" + chem.getName(), steps + "");
					sbExportData.addValue(	wc.getChemicals().getVariable( chem.getIndex() ), 
											"wc" + chem.getName(), steps + "");				
				});
				
				sbExportDataSTR.append( "STEP [")
							.append( testAnt.getChemicals().getVariable(StateType.AGING.getIndex()) ) 
							.append("][")
							.append( testPlant.getChemicals().getVariable(StateType.AGING.getIndex()) ) 
							.append("]\n");
				sbExportDataSTR.append( testAnt.getChemicals().toString() ).append( "*****\n" );
				sbExportDataSTR.append( testPlant.getChemicals().toString() ).append( "*****\n" );
				sbExportDataSTR.append( wc.getChemicals().toString() ).append( "*****\n" );
			});
			// ***** Put DiOxygen && H2O && Energy in local WorldCase !!
			wc.getChemicals().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 	100);
			wc.getChemicals().setVariable(SomeChemicals.WATER.getIndex(), 		100);
		});
		
		sbExportData.buildImage( "src/test/resources/" + "ExportAntAndPlantStatistics03.jpeg" );
		
		File statisticsData = new File( "src/test/resources/" + "ExportAntAndPlantStatistics03.txt" );
		statisticsData.setChamps( sbExportDataSTR.toString().split("\n") );
		try {
			statisticsData.printFile();
		} catch (DataException e) {
			// e.printStackTrace();
			String msg = "Cannot write {" + statisticsData.getFileName() + "} ; DataException {" + e.getMessage() + "}";
			Logger.printlnLog(LoggerLevel.LL_ERROR, msg);
		}
		
		BuildingGenomeHelper.exportAsTXTfile("ExtendedAntTest.txt", testAnt);
		BuildingGenomeHelper.exportAsTXTfile("ExtendedPlantTest.txt", testPlant);
	}
	
}

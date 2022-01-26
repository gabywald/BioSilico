package gabywald.biosilico.anthill.computations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.anthill.Ant;
import gabywald.biosilico.anthill.Plant;
import gabywald.biosilico.anthill.graphics.data.DataCollector;
import gabywald.biosilico.anthill.launcher.AntHillExampleHelper;
import gabywald.biosilico.anthill.launcher.BuildingGenomeHelper;
import gabywald.biosilico.genetics.Gene;
import gabywald.biosilico.genetics.builders.StimulusDecisionBuilder;
import gabywald.biosilico.model.enums.DecisionType;
import gabywald.biosilico.model.enums.SomeChemicals;
import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.environment.World2D;
import gabywald.biosilico.model.environment.World2DCase;
import gabywald.biosilico.model.utils.agents.BlackHole;
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
		String exportName = "InitialAntTest.txt";
		BuildingGenomeHelper.exportAsTXTfile(exportName, testAnt);
		DataExporterAndViewAnalysis.testFileExists( BASE_EXPORT_TEST_DIR + exportName );
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
		String exportName = "InitialPlantTest.txt";
		BuildingGenomeHelper.exportAsTXTfile("InitialPlantTest.txt", testPlant);
		DataExporterAndViewAnalysis.testFileExists( BASE_EXPORT_TEST_DIR + exportName );
	}

	public static void testFileExists(String path2File) {
		File file = new File( path2File );
		Assertions.assertTrue( file.fileExists() );
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
	
	public static final String BASE_EXPORT_TEST_DIR = "src/test/resources/";
	
	public static void exportChemicalDataFileContent(String filePath, StringBuilder sbExportDataSTR) {
		File statisticsData = new File( filePath );
		statisticsData.setChamps( sbExportDataSTR.toString().split("\n") );
		try {
			statisticsData.printFile();
		} catch (DataException e) {
			// e.printStackTrace();
			String msg = "Cannot write {" + statisticsData.getFileName() + "} ; DataException {" + e.getMessage() + "}";
			Logger.printlnLog(LoggerLevel.LL_ERROR, msg);
		}
	}
	
	@Test
	void testPlantExportImageAndChemicalsData() {
		
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
		wc.addAgent( new EnergySource() );
		
		DataCollector sbExportData		= new DataCollector("Plant Analysis", "Steps", "Values of Chemicals");
		StringBuilder sbExportDataSTR	= new StringBuilder();
		
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
				
				
				sbExportDataSTR.append( "STEP [")
							.append( testPlant.getChemicals().getVariable(StateType.AGING.getIndex()) ) 
							.append("]\n");
				sbExportDataSTR.append( testPlant.getChemicals().toString() ).append( "*****\n" );
				sbExportDataSTR.append( wc.getChemicals().toString() ).append( "*****\n" );
			});
		});
		
		String exportImageName = BASE_EXPORT_TEST_DIR + "ExportPlantStatistics.jpeg";
		sbExportData.buildImage( exportImageName );
		DataExporterAndViewAnalysis.testFileExists( exportImageName );
		
		String exportCDataName = BASE_EXPORT_TEST_DIR + "ExportPlantStatistics.txt";
		DataExporterAndViewAnalysis.exportChemicalDataFileContent( exportCDataName, sbExportDataSTR );
		DataExporterAndViewAnalysis.testFileExists( exportCDataName );
	}
	
	@Test
	void testAntExportImageAndChemicalsData() {
		
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
		wc.addAgent( new EnergySource() );
		
		DataCollector sbExportData		= new DataCollector("Ant Analysis", "Steps", "Values of Chemicals");
		StringBuilder sbExportDataSTR	= new StringBuilder();
		
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
				
				sbExportDataSTR.append( "STEP [")
							.append( testAnt.getChemicals().getVariable(StateType.AGING.getIndex()) ) 
							.append("]\n");
				sbExportDataSTR.append( testAnt.getChemicals().toString() ).append( "*****\n" );
				sbExportDataSTR.append( wc.getChemicals().toString() ).append( "*****\n" );
			});
		});
		
		String exportImageName = BASE_EXPORT_TEST_DIR + "ExportAntStatistics.jpeg";
		sbExportData.buildImage( exportImageName );
		DataExporterAndViewAnalysis.testFileExists( exportImageName );
		
		String exportCDataName = BASE_EXPORT_TEST_DIR + "ExportAntStatistics.txt";
		DataExporterAndViewAnalysis.exportChemicalDataFileContent( exportCDataName, sbExportDataSTR );
		DataExporterAndViewAnalysis.testFileExists( exportCDataName );
	}
	
	@Test
	void testAntAndPlantExportImageAndChemicalsData() {
		
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
		wc.addAgent( new EnergySource() );
		
		DataCollector sbExportData		= new DataCollector("Ant and Plant Analysis", "Steps", "Values of Chemicals");
		StringBuilder sbExportDataSTR	= new StringBuilder();
		
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
				
				sbExportDataSTR.append( "STEP [")
							.append( testAnt.getChemicals().getVariable(StateType.AGING.getIndex()) ) 
							.append("][")
							.append( testPlant.getChemicals().getVariable(StateType.AGING.getIndex()) ) 
							.append("]\n");
				sbExportDataSTR.append( testAnt.getChemicals().toString() ).append( "*****\n" );
				sbExportDataSTR.append( testPlant.getChemicals().toString() ).append( "*****\n" );
				sbExportDataSTR.append( wc.getChemicals().toString() ).append( "*****\n" );
			});
		});
		
		String exportImageName = BASE_EXPORT_TEST_DIR + "ExportAntAndPlantStatistics.jpeg";
		sbExportData.buildImage( exportImageName );
		DataExporterAndViewAnalysis.testFileExists( exportImageName );
		
		String exportCDataName = BASE_EXPORT_TEST_DIR + "ExportAntAndPlantStatistics.txt";
		DataExporterAndViewAnalysis.exportChemicalDataFileContent( exportCDataName, sbExportDataSTR );
		DataExporterAndViewAnalysis.testFileExists( exportCDataName );
	}

	@Test
	void testAntAndPlantExportImageAndChemicalsData02() {
		
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
		wc.addAgent( new EnergySource() );
		
		DataCollector sbExportData		= new DataCollector("Ant and Plant Analysis", "Steps", "Values of Chemicals");
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
		
		String exportImageName = BASE_EXPORT_TEST_DIR + "ExportAntAndPlantStatistics02.jpeg";
		sbExportData.buildImage( exportImageName );
		DataExporterAndViewAnalysis.testFileExists( exportImageName );
		
		String exportCDataName = BASE_EXPORT_TEST_DIR + "ExportAntAndPlantStatistics02.txt";
		DataExporterAndViewAnalysis.exportChemicalDataFileContent( exportCDataName, sbExportDataSTR );
		DataExporterAndViewAnalysis.testFileExists( exportCDataName );	}

	@Test
	void testAntAndPlantExportImageAndChemicalsData03() {
		
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
		wc.addAgent( new EnergySource() );
		
		DataCollector sbExportData		= new DataCollector("Ant and Plant Analysis", "Steps", "Values of Chemicals");
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
		
		String exportImageName = BASE_EXPORT_TEST_DIR + "ExportAntAndPlantStatistics03.jpeg";
		sbExportData.buildImage( exportImageName );
		DataExporterAndViewAnalysis.testFileExists( exportImageName );
		
		String exportCDataName = BASE_EXPORT_TEST_DIR + "ExportAntAndPlantStatistics03.txt";
		DataExporterAndViewAnalysis.exportChemicalDataFileContent( exportCDataName, sbExportDataSTR );
		DataExporterAndViewAnalysis.testFileExists( exportCDataName );
		
		// Export new modified genomes !
		BuildingGenomeHelper.exportAsTXTfile("ExtendedAntTest.txt", testAnt);
		BuildingGenomeHelper.exportAsTXTfile("ExtendedPlantTest.txt", testPlant);
		DataExporterAndViewAnalysis.testFileExists( BASE_EXPORT_TEST_DIR + "ExtendedAntTest.txt" );
		DataExporterAndViewAnalysis.testFileExists( BASE_EXPORT_TEST_DIR + "ExtendedPlantTest.txt" );
	}
	
	@Test
	void testAntAndPlantExportImageAndChemicalsData04() {
		
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
		wc.addAgent( new EnergySource() );
		wc.addAgent( new BlackHole() );
		
		DataCollector sbExportData		= new DataCollector("Ant and Plant Analysis", "Steps", "Values of Chemicals");
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
		
		String exportImageName = BASE_EXPORT_TEST_DIR + "ExportAntAndPlantStatistics04.jpeg";
		sbExportData.buildImage( exportImageName );
		DataExporterAndViewAnalysis.testFileExists( exportImageName );
		
		String exportCDataName = BASE_EXPORT_TEST_DIR + "ExportAntAndPlantStatistics04.txt";
		DataExporterAndViewAnalysis.exportChemicalDataFileContent( exportCDataName, sbExportDataSTR );
		DataExporterAndViewAnalysis.testFileExists( exportCDataName );
		
	}
	
	@Test
	void testAntAndPlantExportImageAndChemicalsData05() {
		
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
		wc.addAgent( new EnergySource() );
		wc.addAgent( new BlackHole() );
		
		DataCollector sbExportData		= new DataCollector("Ant and Plant Analysis", "Steps", "Values of Chemicals");
		StringBuilder sbExportDataSTR	= new StringBuilder();
		
		sbExportData.showJFrame();
		
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
				
				try { Thread.sleep(100); }
				catch (InterruptedException e) { e.printStackTrace(); }
			});
			// ***** Put DiOxygen && H2O && Energy in local WorldCase !!
			wc.getChemicals().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 	100);
			wc.getChemicals().setVariable(SomeChemicals.WATER.getIndex(), 		100);
			
			try { Thread.sleep(1000); }
			catch (InterruptedException e) { e.printStackTrace(); }
		});
		
		String exportImageName = BASE_EXPORT_TEST_DIR + "ExportAntAndPlantStatistics05.jpeg";
		sbExportData.buildImage( exportImageName );
		DataExporterAndViewAnalysis.testFileExists( exportImageName );
		
		String exportCDataName = BASE_EXPORT_TEST_DIR + "ExportAntAndPlantStatistics05.txt";
		DataExporterAndViewAnalysis.exportChemicalDataFileContent( exportCDataName, sbExportDataSTR );
		DataExporterAndViewAnalysis.testFileExists( exportCDataName );

		try { System.in.read(); } // TODO better than that to avoid automatic closing JFrame at end !
		catch (IOException e) { e.printStackTrace(); }
	}
	
}

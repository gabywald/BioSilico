package gabywald.biosilico.anthill.tests;

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
		
		testAnt.setGenome( AntHillExampleHelper.loadingGenome(AntPlantLoadingTests.GENOME_ANT_2020) );
		// testAnt.setGenome( AntHillExampleHelper.loadingAntGenome() );
		
		// ***** Export Ant as a TXT file !
		String exportName = AntPlantLoadingTests.GENOME_ANT_RE_2020;
		BuildingGenomeHelper.exportAsTXTfile(exportName, testAnt);
		DataExporterAndViewAnalysis.testFileExists( AntPlantLoadingTests.BASE_EXPORT_TEST_DIR + exportName );
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
		
		testPlant.setGenome( AntHillExampleHelper.loadingGenome(AntPlantLoadingTests.GENOME_PLANT_2020) );
		// testAnt.setGenome( AntHillExampleHelper.loadingPlantGenome() );
		
		// ***** Export Plant as a TXT file !
		String exportName = AntPlantLoadingTests.GENOME_PLANT_RE_2020;
		BuildingGenomeHelper.exportAsTXTfile(exportName, testPlant);
		DataExporterAndViewAnalysis.testFileExists( AntPlantLoadingTests.BASE_EXPORT_TEST_DIR + exportName );
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
	void testAntExportImageAndChemicalsData() {
		
		Ant testAnt = new Ant();
		Assertions.assertNotNull( testAnt );
		Assertions.assertEquals(0, testAnt.getGenome().size());
		
		testAnt.setRank("Rank Test");
		testAnt.setNameCommon("Test Starting Ant");
		testAnt.setNameBiosilico("AntHill Ant Example");
		testAnt.setDivision("TESTS");
		
		testAnt.setGenome( AntHillExampleHelper.loadingGenome(AntPlantLoadingTests.GENOME_ANT_2020) );
		// testAnt.setGenome( AntHillExampleHelper.loadingAntGenome() );
		
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
		
		String exportImageName = AntPlantLoadingTests.BASE_EXPORT_TEST_DIR + "ExportAntStatistics.jpeg";
		sbExportData.buildImage( exportImageName );
		DataExporterAndViewAnalysis.testFileExists( exportImageName );
		
		String exportCDataName = AntPlantLoadingTests.BASE_EXPORT_TEST_DIR + "ExportAntStatistics.txt";
		DataExporterAndViewAnalysis.exportChemicalDataFileContent( exportCDataName, sbExportDataSTR );
		DataExporterAndViewAnalysis.testFileExists( exportCDataName );
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
		
		testPlant.setGenome( AntHillExampleHelper.loadingGenome(AntPlantLoadingTests.GENOME_PLANT_2020) );
		// testPlant.setGenome( AntHillExampleHelper.loadingAntGenome() );
		
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
		
		String exportImageName = AntPlantLoadingTests.BASE_EXPORT_TEST_DIR + "ExportPlantStatistics.jpeg";
		sbExportData.buildImage( exportImageName );
		DataExporterAndViewAnalysis.testFileExists( exportImageName );
		
		String exportCDataName = AntPlantLoadingTests.BASE_EXPORT_TEST_DIR + "ExportPlantStatistics.txt";
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
		
		testAnt.setGenome( AntHillExampleHelper.loadingGenome(AntPlantLoadingTests.GENOME_ANT_2020) );
		// testAnt.setGenome( AntHillExampleHelper.loadingAntGenome() );
		
		Plant testPlant = new Plant();
		Assertions.assertNotNull( testPlant );
		Assertions.assertEquals(0, testPlant.getGenome().size());
		
		testPlant.setRank("Rank Test");
		testPlant.setNameCommon("Test Starting Plant");
		testPlant.setNameBiosilico("AntHill Plant Example");
		testPlant.setDivision("TESTS");
		
		testPlant.setGenome( AntHillExampleHelper.loadingGenome(AntPlantLoadingTests.GENOME_PLANT_2020) );
		// testPlant.setGenome( AntHillExampleHelper.loadingAntGenome() );
		
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
		
		/*
*****
STEP [51][51]
	169	25	Glucose(G6)
	180	125	DiOxygen
	330	25	Starch (Amidon)
	332	25	Glycogen
	391	275	heat energy
	951	51	aging
	952	943	agentype => anima
	953	912	typeof => agent
	954	921	status => egg
*****
	169	25	Glucose(G6)
	181	45	Carbon DiOxid
	182	145	DiHydrogen Monoxid (Water / Eau)
	330	25	Starch (Amidon)
	390	999	solar energy => <NONEXX>
	391	525	heat energy
	951	51	aging
	952	942	agentype => plant
	953	912	typeof => agent
	954	922	status => embryo
*****
	181	5	Carbon DiOxid
	182	5	DiHydrogen Monoxid (Water / Eau)
	390	275	solar energy
	391	525	heat energy
*****
		 */
		Assertions.assertEquals(BASE_COMPUTATION+1, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()) );
		Assertions.assertEquals(BASE_COMPUTATION+1, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()) );
		
		Assertions.assertEquals(5, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(5, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(275, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(525, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		
		String exportImageName = AntPlantLoadingTests.BASE_EXPORT_TEST_DIR + "ExportAntAndPlantStatistics.jpeg";
		sbExportData.buildImage( exportImageName );
		DataExporterAndViewAnalysis.testFileExists( exportImageName );
		
		String exportCDataName = AntPlantLoadingTests.BASE_EXPORT_TEST_DIR + "ExportAntAndPlantStatistics.txt";
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
		
		testAnt.setGenome( AntHillExampleHelper.loadingGenome(AntPlantLoadingTests.GENOME_ANT_2020) );
		// testAnt.setGenome( AntHillExampleHelper.loadingAntGenome() );
		
		Plant testPlant = new Plant();
		Assertions.assertNotNull( testPlant );
		Assertions.assertEquals(0, testPlant.getGenome().size());
		
		testPlant.setRank("Rank Test");
		testPlant.setNameCommon("Test Starting Plant");
		testPlant.setNameBiosilico("AntHill Plant Example");
		testPlant.setDivision("TESTS");
		
		testPlant.setGenome( AntHillExampleHelper.loadingGenome(AntPlantLoadingTests.GENOME_PLANT_2020) );
		// testPlant.setGenome( AntHillExampleHelper.loadingAntGenome() );
		
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
		
		/*
STEP [999][999]
	169	25	Glucose(G6)
	180	515	DiOxygen
	330	25	Starch (Amidon)
	332	25	Glycogen
	391	999	heat energy => <NONEXX>
	951	999	aging => <NONEXX>
	952	943	agentype => anima
	953	912	typeof => agent
	954	928	status => dead
*****
	169	25	Glucose(G6)
	181	45	Carbon DiOxid
	182	525	DiHydrogen Monoxid (Water / Eau)
	330	25	Starch (Amidon)
	390	999	solar energy => <NONEXX>
	391	999	heat energy => <NONEXX>
	951	999	aging => <NONEXX>
	952	942	agentype => plant
	953	912	typeof => agent
	954	928	status => dead
*****
	180	10	DiOxygen
	181	5	Carbon DiOxid
	182	20	DiHydrogen Monoxid (Water / Eau)
	390	999	solar energy => <NONEXX>
	391	999	heat energy => <NONEXX>
*****
		 */
		// 5*BASE_COMPUTATION+1
		Assertions.assertEquals(999, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()) );
		Assertions.assertEquals(999, testAnt.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertFalse( testAnt.isAlive() );
		
		Assertions.assertEquals(999, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()) );
		Assertions.assertEquals(999, testPlant.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(999, testPlant.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertFalse( testPlant.isAlive() );
		
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(5, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(999, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(999, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		
		String exportImageName = AntPlantLoadingTests.BASE_EXPORT_TEST_DIR + "ExportAntAndPlantStatistics02.jpeg";
		sbExportData.buildImage( exportImageName );
		DataExporterAndViewAnalysis.testFileExists( exportImageName );
		
		String exportCDataName = AntPlantLoadingTests.BASE_EXPORT_TEST_DIR + "ExportAntAndPlantStatistics02.txt";
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
		
		testAnt.setGenome( AntHillExampleHelper.loadingGenome(AntPlantLoadingTests.GENOME_ANT_2020) );
		// testAnt.setGenome( AntHillExampleHelper.loadingAntGenome() );
		
		Plant testPlant = new Plant();
		Assertions.assertNotNull( testPlant );
		Assertions.assertEquals(0, testPlant.getGenome().size());
		
		testPlant.setRank("Rank Test");
		testPlant.setNameCommon("Test Starting Plant");
		testPlant.setNameBiosilico("AntHill Plant Example");
		testPlant.setDivision("TESTS");
		
		testPlant.setGenome( AntHillExampleHelper.loadingGenome(AntPlantLoadingTests.GENOME_PLANT_2020) );
		// testPlant.setGenome( AntHillExampleHelper.loadingAntGenome() );
		
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
		
		/*
*****
STEP [255][255]
	169	25	Glucose(G6)
	180	25	DiOxygen
	330	25	Starch (Amidon)
	332	25	Glycogen
	391	95	heat energy
	951	255	aging
	952	943	agentype => anima
	953	912	typeof => agent
	954	921	status => egg
*****
	169	25	Glucose(G6)
	181	45	Carbon DiOxid
	182	25	DiHydrogen Monoxid (Water / Eau)
	330	25	Starch (Amidon)
	390	5	solar energy
	391	65	heat energy
	951	255	aging
	952	942	agentype => plant
	953	912	typeof => agent
	954	922	status => embryo
*****
	180	100	DiOxygen
	181	5	Carbon DiOxid
	182	100	DiHydrogen Monoxid (Water / Eau)
	390	999	solar energy => <NONEXX>
	391	999	heat energy => <NONEXX>
*****
		*/
		// 5*BASE_COMPUTATION+5
		Assertions.assertEquals(5*BASE_COMPUTATION+5, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()) );
		Assertions.assertEquals( 95, testAnt.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertTrue( testAnt.isAlive() );
		
		Assertions.assertEquals(5*BASE_COMPUTATION+5, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()) );
		Assertions.assertEquals(  5, testPlant.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals( 65, testPlant.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertTrue( testPlant.isAlive() );
		
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(5, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(999, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(999, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		
		String exportImageName = AntPlantLoadingTests.BASE_EXPORT_TEST_DIR + "ExportAntAndPlantStatistics03.jpeg";
		sbExportData.buildImage( exportImageName );
		DataExporterAndViewAnalysis.testFileExists( exportImageName );
		
		String exportCDataName = AntPlantLoadingTests.BASE_EXPORT_TEST_DIR + "ExportAntAndPlantStatistics03.txt";
		DataExporterAndViewAnalysis.exportChemicalDataFileContent( exportCDataName, sbExportDataSTR );
		DataExporterAndViewAnalysis.testFileExists( exportCDataName );
		
		// Export new modified genomes !
		String antGenomeFile	= AntPlantLoadingTests.GENOME_ANT_EXT_2022;
		String plantGenomeFile	= AntPlantLoadingTests.GENOME_PLANT_EXT_2022;
		BuildingGenomeHelper.exportAsTXTfile(antGenomeFile, testAnt);
		BuildingGenomeHelper.exportAsTXTfile(plantGenomeFile, testPlant);
		DataExporterAndViewAnalysis.testFileExists( AntPlantLoadingTests.BASE_EXPORT_TEST_DIR + antGenomeFile );
		DataExporterAndViewAnalysis.testFileExists( AntPlantLoadingTests.BASE_EXPORT_TEST_DIR + plantGenomeFile );
		
		BuildingGenomeHelper.exportGenome(AntPlantLoadingTests.BASE_EXPORT_TEST_DIR + AntPlantLoadingTests.GENOME_ANT_2022, testAnt);
		BuildingGenomeHelper.exportGenome(AntPlantLoadingTests.BASE_EXPORT_TEST_DIR + AntPlantLoadingTests.GENOME_PLANT_2022, testPlant);
		DataExporterAndViewAnalysis.testFileExists( AntPlantLoadingTests.BASE_EXPORT_TEST_DIR + AntPlantLoadingTests.GENOME_ANT_2022 );
		DataExporterAndViewAnalysis.testFileExists( AntPlantLoadingTests.BASE_EXPORT_TEST_DIR + AntPlantLoadingTests.GENOME_PLANT_2022 );
		
		// BuildingGenomeHelper.copyMoveGenome("GenomeAntCompleteGenome.txt", "baseGenomeAnt.txt");
		BuildingGenomeHelper.removeACGTsequence( AntPlantLoadingTests.BASE_EXPORT_TEST_DIR + AntPlantLoadingTests.GENOME_ANT_2022 );
		BuildingGenomeHelper.removeACGTsequence( AntPlantLoadingTests.BASE_EXPORT_TEST_DIR + AntPlantLoadingTests.GENOME_PLANT_2022 );
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
		
		testAnt.setGenome( AntHillExampleHelper.loadingGenome(AntPlantLoadingTests.GENOME_ANT_2020) );
		// testAnt.setGenome( AntHillExampleHelper.loadingAntGenome() );
		
		Plant testPlant = new Plant();
		Assertions.assertNotNull( testPlant );
		Assertions.assertEquals(0, testPlant.getGenome().size());
		
		testPlant.setRank("Rank Test");
		testPlant.setNameCommon("Test Starting Plant");
		testPlant.setNameBiosilico("AntHill Plant Example");
		testPlant.setDivision("TESTS");
		
		testPlant.setGenome( AntHillExampleHelper.loadingGenome(AntPlantLoadingTests.GENOME_PLANT_2020) );
		// testPlant.setGenome( AntHillExampleHelper.loadingAntGenome() );
		
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
		
		/*
*****
STEP [255][255]
	169	25	Glucose(G6)
	180	25	DiOxygen
	330	25	Starch (Amidon)
	332	25	Glycogen
	391	95	heat energy
	951	255	aging
	952	943	agentype => anima
	953	912	typeof => agent
	954	921	status => egg
*****
	169	25	Glucose(G6)
	181	45	Carbon DiOxid
	182	25	DiHydrogen Monoxid (Water / Eau)
	330	25	Starch (Amidon)
	390	5	solar energy
	391	65	heat energy
	951	255	aging
	952	942	agentype => plant
	953	912	typeof => agent
	954	922	status => embryo
*****
	180	100	DiOxygen
	181	5	Carbon DiOxid
	182	100	DiHydrogen Monoxid (Water / Eau)
	390	170	solar energy
	391	190	heat energy
*****
		 */
		// 5*BASE_COMPUTATION+5
		Assertions.assertEquals(5*BASE_COMPUTATION+5, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()) );
		Assertions.assertEquals( 95, testAnt.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertTrue( testAnt.isAlive() );
		
		Assertions.assertEquals(5*BASE_COMPUTATION+5, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()) );
		Assertions.assertEquals(  5, testPlant.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals( 65, testPlant.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertTrue( testPlant.isAlive() );
		
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(5, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(170, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(190, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		
		String exportImageName = AntPlantLoadingTests.BASE_EXPORT_TEST_DIR + "ExportAntAndPlantStatistics04.jpeg";
		sbExportData.buildImage( exportImageName );
		DataExporterAndViewAnalysis.testFileExists( exportImageName );
		
		String exportCDataName = AntPlantLoadingTests.BASE_EXPORT_TEST_DIR + "ExportAntAndPlantStatistics04.txt";
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
		
		testAnt.setGenome( AntHillExampleHelper.loadingGenome(AntPlantLoadingTests.BASE_EXPORT_TEST_DIR + AntPlantLoadingTests.GENOME_ANT_2022) );
		// testAnt.setGenome( AntHillExampleHelper.loadingAntGenome() );
		
		Plant testPlant = new Plant();
		Assertions.assertNotNull( testPlant );
		Assertions.assertEquals(0, testPlant.getGenome().size());
		
		testPlant.setRank("Rank Test");
		testPlant.setNameCommon("Test Starting Plant");
		testPlant.setNameBiosilico("AntHill Plant Example");
		testPlant.setDivision("TESTS");
		
		testPlant.setGenome( AntHillExampleHelper.loadingGenome(AntPlantLoadingTests.BASE_EXPORT_TEST_DIR + AntPlantLoadingTests.GENOME_PLANT_2022) );
		// testPlant.setGenome( AntHillExampleHelper.loadingAntGenome() );
		
		// HERE NEW GENES ARE ALREADY ADDED (see above)
		
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
		
		/*
*****
STEP [255][255]
	169	25	Glucose(G6)
	180	25	DiOxygen
	330	25	Starch (Amidon)
	332	25	Glycogen
	391	95	heat energy
	951	255	aging
	952	943	agentype => anima
	953	912	typeof => agent
	954	921	status => egg
*****
	169	25	Glucose(G6)
	181	45	Carbon DiOxid
	182	25	DiHydrogen Monoxid (Water / Eau)
	330	25	Starch (Amidon)
	390	5	solar energy
	391	65	heat energy
	951	255	aging
	952	942	agentype => plant
	953	912	typeof => agent
	954	922	status => embryo
*****
	180	100	DiOxygen
	181	5	Carbon DiOxid
	182	100	DiHydrogen Monoxid (Water / Eau)
	390	170	solar energy
	391	190	heat energy
*****
		 */
		// 5*BASE_COMPUTATION+5
		Assertions.assertEquals(5*BASE_COMPUTATION+5, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()) );
		Assertions.assertEquals( 95, testAnt.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertTrue( testAnt.isAlive() );
		
		Assertions.assertEquals(5*BASE_COMPUTATION+5, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()) );
		Assertions.assertEquals(  5, testPlant.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals( 65, testPlant.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertTrue( testPlant.isAlive() );
		
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(5, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(170, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(190, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		
		String exportImageName = AntPlantLoadingTests.BASE_EXPORT_TEST_DIR + "ExportAntAndPlantStatistics05.jpeg";
		sbExportData.buildImage( exportImageName );
		DataExporterAndViewAnalysis.testFileExists( exportImageName );
		
		String exportCDataName = AntPlantLoadingTests.BASE_EXPORT_TEST_DIR + "ExportAntAndPlantStatistics05.txt";
		DataExporterAndViewAnalysis.exportChemicalDataFileContent( exportCDataName, sbExportDataSTR );
		DataExporterAndViewAnalysis.testFileExists( exportCDataName );

		// try { System.in.read(); } // TODO better than that to avoid automatic closing JFrame at end !
		// catch (IOException e) { e.printStackTrace(); }
		
		// sbExportData.hideJFrame();
	}
	
}

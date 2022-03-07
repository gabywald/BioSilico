package gabywald.biosilico.anthill.tests;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.anthill.Ant;
import gabywald.biosilico.anthill.GeneratorReceptionChemicals;
import gabywald.biosilico.anthill.Plant;
import gabywald.biosilico.anthill.helpers.AntHillExampleHelper;
import gabywald.biosilico.anthill.helpers.BuildingGenomeHelper;
import gabywald.biosilico.model.chemicals.ChemicalsHelper;
import gabywald.biosilico.model.enums.ObjectType;
import gabywald.biosilico.model.enums.SomeChemicals;
import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.environment.World2D;
import gabywald.biosilico.model.environment.World2DCase;
import gabywald.biosilico.model.utils.agents.EnergySource;
import gabywald.biosilico.model.utils.agents.TestObjectFoodEgg;
import gabywald.global.data.StringUtils;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020, 2022)
 */
class AntPlantLoadingTests {
	
	public static final String EXPORT_DIR_2020		= "anthill2020/";
	public static final String EXPORT_DIR_2022		= "anthill2022/";
	
	public static final String BASE_EXPORT_TEST_DIR = AntHillExampleHelper.BASE_EXPORT_TEST_DIR;
	
	public static final String GENOME_ANT_2020		= AntPlantLoadingTests.EXPORT_DIR_2020 + "2020baseGenomeAnt.txt";
	public static final String GENOME_ANT_RE_2020	= AntPlantLoadingTests.EXPORT_DIR_2020 + "2020InitialAntTest.txt";
	public static final String GENOME_ANT_2022		= AntPlantLoadingTests.EXPORT_DIR_2022 + "2022baseGenomeAnt.txt";
	public static final String GENOME_ANT_RE_2022	= AntPlantLoadingTests.EXPORT_DIR_2022 + "2022InitialAntTest.txt";
	public static final String GENOME_ANT_EXT_2022	= AntPlantLoadingTests.EXPORT_DIR_2022 + "2022ExtendedAntTest.txt";
	
	public static final String GENOME_PLANT_2020		= AntPlantLoadingTests.EXPORT_DIR_2020 + "2020baseGenomePlant.txt";
	public static final String GENOME_PLANT_RE_2020		= AntPlantLoadingTests.EXPORT_DIR_2020 + "2020InitialPlantTest.txt";
	public static final String GENOME_PLANT_2022		= AntPlantLoadingTests.EXPORT_DIR_2022 + "2022baseGenomePlant.txt";
	public static final String GENOME_PLANT_RE_2022		= AntPlantLoadingTests.EXPORT_DIR_2022 + "2022InitialPlantTest.txt";
	public static final String GENOME_PLANT_EXT_2022	= AntPlantLoadingTests.EXPORT_DIR_2022 + "2022ExtendedPlantTest.txt";
	
	/**
	 * Import / Export
	 */
	@Test
	void testLoadingAnt2020() {
		
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
	 * Import / Export
	 */
	@Test
	void testLoadingAnt2022() {
		
		Ant testAnt = new Ant();
		Assertions.assertNotNull( testAnt );
		Assertions.assertEquals(0, testAnt.getGenome().size());
		
		testAnt.setRank("Rank Test");
		testAnt.setNameCommon("Test Starting Ant");
		testAnt.setNameBiosilico("AntHill Ant Example");
		testAnt.setDivision("TESTS");
		
		testAnt.setGenome( AntHillExampleHelper.loadingGenome(AntPlantLoadingTests.GENOME_ANT_2022) );
		// testAnt.setGenome( AntHillExampleHelper.loadingAntGenome() );
		
		// ***** Export Ant as a TXT file !
		String exportName = AntPlantLoadingTests.GENOME_ANT_RE_2022;
		BuildingGenomeHelper.exportAsTXTfile(exportName, testAnt);
		DataExporterAndViewAnalysis.testFileExists( AntPlantLoadingTests.BASE_EXPORT_TEST_DIR + exportName );
	}

	/**
	 * Simply test in a Unique WorldCase !
	 */
	@Test
	void testLoadingAnt01() {
		
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
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getChemicals().getVariable(k) );
		});
		
		// ***** one execution in this context
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 20, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals(  5, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  5, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		
		// ***** one execution in this context
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 15, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  2, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 10, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  5, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));

	}
	
	/**
	 * Simply test in a Unique WorldCase With DiOxygen !
	 */
	@Test
	void testLoadingAnt02() {
		
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
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getChemicals().getVariable(k) );
		});
		
		// ***** Put DiOxygen in local WorldCase !!
		wc.getChemicals().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 100);
		
		// ***** one execution in this context
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 35, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 20, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 90, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  5, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  5, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		
		// ***** one execution in this context
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 45, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 15, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));		
		Assertions.assertEquals(  2, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 80, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 10, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  5, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));

	}
	
	/**
	 * Simply test in a Unique WorldCase With DiOxygen and Water !
	 */
	@Test
	void testLoadingAnt03() {
		
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
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getChemicals().getVariable(k) );
		});
		
		// ***** Put DiOxygen && H2O in local WorldCase !!
		wc.getChemicals().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 	100);
		wc.getChemicals().setVariable(SomeChemicals.WATER.getIndex(), 		100);
		
		// ***** one execution in this context -- 1
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 35, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 20, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 90, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  5, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		
		// ***** one execution in this context -- 2
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 45, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 15, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  2, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 80, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 10, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		
		// ***** one execution in this context -- 3
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 55, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 10, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  3, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 70, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 15, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		
		// ***** one execution in this context -- 4
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 65, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  4, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 60, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 20, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		
		// ***** one execution in this context -- 5
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 75, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 50, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));

	}
	
	/**
	 * Simply test in a Unique WorldCase With DiOxygen and Water and EnergySource !
	 */
	@Test
	void testLoadingAnt04() {
		
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
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getChemicals().getVariable(k) );
		});
		
		// ***** Put DiOxygen && H2O in local WorldCase !!
		wc.getChemicals().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 	100);
		wc.getChemicals().setVariable(SomeChemicals.WATER.getIndex(), 		100);
		EnergySource es = new EnergySource();
		wc.addAgent( es );
		
		// ***** one execution in this context -- 1
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 35, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 20, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 90, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  5, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		
		// ***** one execution in this context -- 2
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 45, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 15, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  2, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 80, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 10, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		
		// ***** one execution in this context -- 3
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 55, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 10, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  3, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 70, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 15, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		
		// ***** one execution in this context -- 4
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 65, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  4, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 60, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 20, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		
		// ***** one execution in this context -- 5
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		Logger.printlnLog(LoggerLevel.LL_DEBUG, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_DEBUG, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_DEBUG, es.toString() );
		Logger.printlnLog(LoggerLevel.LL_DEBUG, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_DEBUG, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_DEBUG, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 75, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 50, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));

	}
	
	/**
	 * Simply test in a Unique WorldCase With DiOxygen and Water and EnergySource !
	 * <br/> Running World !!
	 */
	@Test
	void testLoadingAnt05() {
		
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
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getChemicals().getVariable(k) );
		});
		
		// ***** Put DiOxygen && H2O in local WorldCase !!
		wc.getChemicals().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 	100);
		wc.getChemicals().setVariable(SomeChemicals.WATER.getIndex(), 		100);
		EnergySource es = new EnergySource();
		wc.addAgent( es );
		
		// ***** one execution in this context -- 1
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 35, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 20, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals( 90, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  5, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** one execution in this context -- 2
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 45, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 15, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  2, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals( 80, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 10, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 50, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals( 45, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** one execution in this context -- 3
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 55, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 10, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  3, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals( 70, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 15, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 75, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals( 65, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** one execution in this context -- 4
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 65, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  4, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals( 60, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 20, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals( 85, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** one execution in this context -- 5
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, es, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 75, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals( 50, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(125, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** Put some pheromones on local (00 : ch650)
		wc.getChemicals().setVarPlus(SomeChemicals.PHEROMONE_00.getIndex(), 50);
		// ***** MORE executionS in this context -- 6
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, es, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 85, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  6, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		// ***** TRUE ??
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals( 40, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(150, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(125, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals( 50, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** Loading Chemicals HalfLives in World !!
		w.loadHalLives();
		
		// ***** MORE executionS in this context -- 6
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, es, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 95, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  7, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		// *** TRUE ??
		Assertions.assertTrue( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals( 30, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(155, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(135, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals( 40, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** MORE executionS in this context -- 7
		testAnt.getChemicals().setVarPlus(SomeChemicals.HEXOKINASE.getIndex(), 15);
		testAnt.getChemicals().setVarPlus(SomeChemicals.GLUCOSE.getIndex(), 50);
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, es, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 15, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 75, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(105, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  8, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		// *** TRUE ??
		Assertions.assertTrue( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals( 20, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(160, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(145, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals( 30, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** MORE executionS in this context -- 8
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, es, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 15, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 75, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(115, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  9, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		// *** TRUE ??
		Assertions.assertTrue( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals( 10, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(165, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(155, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals( 20, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** MORE executionS in this context -- 9
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, es, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 15, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 75, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(125, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals( 10, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		// *** TRUE ??
		Assertions.assertTrue( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(170, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(165, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals( 10, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));

	}
	
	/**
	 * Simply test in a Unique WorldCase With DiOxygen and Water and EnergySource !
	 * <br/> Running World (3, 3) !!
	 */
	@Test
	void testLoadingAnt06() {
		
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
		
		World2D w		= new World2D(3, 3);
		World2DCase wc	= w.getWorldCase(1, 1);
		Assertions.assertNotNull( wc );
		
		testAnt.setCurrentWorldCase( wc );
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getChemicals().getVariable(k) );
		});
		
		// ***** Put DiOxygen && H2O in local WorldCase !!
		wc.getChemicals().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 	100);
		wc.getChemicals().setVariable(SomeChemicals.WATER.getIndex(), 		100);
		EnergySource es = new EnergySource();
		wc.addAgent( es );
		
		// ***** one execution in this context -- 1
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 35, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 20, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals( 90, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  5, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** one execution in this context -- 2
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 45, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 15, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  2, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals( 80, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 10, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 50, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals( 45, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** one execution in this context -- 3
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 55, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 10, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  3, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals( 70, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 15, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 75, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals( 65, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** one execution in this context -- 4
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 65, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  4, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals( 60, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 20, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals( 85, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** one execution in this context -- 5
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, es, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 75, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals( 50, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(125, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** Put some pheromones on local (00 : ch650)
		wc.getChemicals().setVarPlus(SomeChemicals.PHEROMONE_00.getIndex(), 50);
		// ***** MORE executionS in this context -- 6
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, es, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 85, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  6, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		// ***** TRUE ??
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals( 40, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(150, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(125, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals( 50, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** Loading Chemicals HalfLives in World !!
		w.loadHalLives();
		
		// ***** MORE executionS in this context -- 6
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, es, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 95, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  7, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		// *** TRUE ??
		Assertions.assertTrue( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals( 30, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(155, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(135, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals( 40, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** MORE executionS in this context -- 7
		testAnt.getChemicals().setVarPlus(SomeChemicals.HEXOKINASE.getIndex(), 15);
		testAnt.getChemicals().setVarPlus(SomeChemicals.GLUCOSE.getIndex(), 50);
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, es, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 15, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 75, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(105, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  8, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		// *** TRUE ??
		Assertions.assertTrue( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals( 20, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(160, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(145, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals( 30, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** MORE executionS in this context -- 8
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, es, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 15, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 75, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(115, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  9, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		// *** TRUE ??
		Assertions.assertTrue( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals( 10, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(165, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(155, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals( 20, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** MORE executionS in this context -- 9
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, es, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 15, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 75, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(125, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals( 10, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		// *** TRUE ??
		Assertions.assertTrue( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(170, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(165, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals( 10, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));

	}

	/**
	 * Simply test in a Unique WorldCase With DiOxygen and Water and EnergySource !
	 * <br/> Running World (3, 3) !!
	 */
	@Test
	void testLoadingAnt07() {
		
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
		
		World2D w		= new World2D(3, 3);
		World2DCase wc	= w.getWorldCase(1, 1);
		Assertions.assertNotNull( wc );
		
		testAnt.setCurrentWorldCase( wc );
		
		w.getWorldCase(0, 0).addAgent(new TestObjectFoodEgg());
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getChemicals().getVariable(k) );
		});
		
		// ***** Put DiOxygen && H2O in local WorldCase !!
		wc.getChemicals().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 	100);
		wc.getChemicals().setVariable(SomeChemicals.WATER.getIndex(), 		100);
		EnergySource es = new EnergySource();
		wc.addAgent( es );
		
		// ***** one execution in this context -- 1
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 35, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 20, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals( 90, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  5, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** one execution in this context -- 2
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 45, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 15, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  2, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals( 80, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 10, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 50, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals( 45, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** one execution in this context -- 3
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 55, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 10, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  3, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals( 70, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 15, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 75, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals( 65, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** one execution in this context -- 4
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 65, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  4, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals( 60, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 20, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals( 85, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** one execution in this context -- 5
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, es, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 75, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals( 50, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(125, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** Put some pheromones on local (00 : ch650)
		wc.getChemicals().setVarPlus(SomeChemicals.PHEROMONE_00.getIndex(), 50);
		// ***** MORE executionS in this context -- 6
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, es, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 85, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  6, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		// ***** TRUE ??
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals( 40, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(150, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(125, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals( 50, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** Loading Chemicals HalfLives in World !!
		w.loadHalLives();
		
		// ***** MORE executionS in this context -- 6
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, es, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals( 95, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  7, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		// *** TRUE ??
		Assertions.assertTrue( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals( 30, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(155, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(135, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals( 40, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** MORE executionS in this context -- 7
		testAnt.getChemicals().setVarPlus(SomeChemicals.HEXOKINASE.getIndex(), 15);
		testAnt.getChemicals().setVarPlus(SomeChemicals.GLUCOSE.getIndex(), 50);
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, es, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 15, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 75, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(105, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  8, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		// *** TRUE ??
		Assertions.assertTrue( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals( 20, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(160, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(145, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals( 30, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** MORE executionS in this context -- 8
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, es, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 15, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 75, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(115, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  9, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		// *** TRUE ??
		Assertions.assertTrue( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals( 10, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(165, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(155, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals( 20, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** MORE executionS in this context -- 9
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testAnt, es, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 15, testAnt.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 75, testAnt.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(125, testAnt.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals( 10, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		// *** TRUE ??
		Assertions.assertTrue( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(89, 10).ckActivated() );
		
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(170, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(165, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals( 10, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));

	}
	
	/* ***** ***** ***** ***** ***** */
	/* ***** ***** ***** ***** ***** */
	/* ***** ***** ***** ***** ***** */
	
	/**
	 * Import / Export
	 */
	@Test
	void testLoadingPlant2020() {
		
		Plant testPlant = new Plant();
		Assertions.assertNotNull( testPlant );
		Assertions.assertEquals(0, testPlant.getGenome().size());
		
		testPlant.setRank("Rank Test");
		testPlant.setNameCommon("Test Starting Plant");
		testPlant.setNameBiosilico("AntHill Plant Example");
		testPlant.setDivision("TESTS");
		
		testPlant.setGenome( AntHillExampleHelper.loadingGenome(AntPlantLoadingTests.GENOME_PLANT_2020) );
		// testPlant.setGenome( AntHillExampleHelper.loadingPlantGenome() );
		
		// ***** Export Plant as a TXT file !
		String exportName = AntPlantLoadingTests.GENOME_PLANT_RE_2020;
		BuildingGenomeHelper.exportAsTXTfile(exportName, testPlant);
		DataExporterAndViewAnalysis.testFileExists( AntPlantLoadingTests.BASE_EXPORT_TEST_DIR + exportName );
	}
	
	/**
	 * Import / Export
	 */
	@Test
	void testLoadingPlant2022() {
		
		Plant testPlant = new Plant();
		Assertions.assertNotNull( testPlant );
		Assertions.assertEquals(0, testPlant.getGenome().size());
		
		testPlant.setRank("Rank Test");
		testPlant.setNameCommon("Test Starting Plant");
		testPlant.setNameBiosilico("AntHill Plant Example");
		testPlant.setDivision("TESTS");
		
		testPlant.setGenome( AntHillExampleHelper.loadingGenome(AntPlantLoadingTests.GENOME_PLANT_2022) );
		// testPlant.setGenome( AntHillExampleHelper.loadingPlantGenome() );
		
		// ***** Export Plant as a TXT file !
		String exportName = AntPlantLoadingTests.GENOME_PLANT_RE_2022;
		BuildingGenomeHelper.exportAsTXTfile(exportName, testPlant);
		DataExporterAndViewAnalysis.testFileExists( AntPlantLoadingTests.BASE_EXPORT_TEST_DIR + exportName );
	}

	/**
	 * Simply test in a Unique WorldCase !
	 */
	@Test
	void testLoadingPlant01() {
		
		Plant testPlant = new Plant();
		Assertions.assertNotNull( testPlant );
		Assertions.assertEquals(0, testPlant.getGenome().size());
		
		testPlant.setRank("Rank Test");
		testPlant.setNameCommon("Test Starting Plant");
		testPlant.setNameBiosilico("AntHill Plant Example");
		testPlant.setDivision("TESTS");
		
		testPlant.setGenome( AntHillExampleHelper.loadingGenome(AntPlantLoadingTests.GENOME_PLANT_2020) );
		// testPlant.setGenome( AntHillExampleHelper.loadingPlantGenome() );
		
		// ***** test with a World and WorldCase
		
		World2D w		= new World2D(1, 1);
		World2DCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testPlant.setCurrentWorldCase( wc );
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testPlant.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getChemicals().getVariable(k) );
		});
		
		// ***** one execution in this context
		testPlant.execution( wc );
		testPlant.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));

		Assertions.assertEquals( 21, testPlant.getChemicals().getVariable(SomeChemicals.STARCH.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  1, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testPlant.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		
		// ***** one execution in this context
		testPlant.execution( wc );
		testPlant.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));

		Assertions.assertEquals( 17, testPlant.getChemicals().getVariable(SomeChemicals.STARCH.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  2, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testPlant.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));

	}
	
	/**
	 * Simply test in a Unique WorldCase With DiOxygen !
	 */
	@Test
	void testLoadingPlant02() {
		
		Plant testPlant = new Plant();
		Assertions.assertNotNull( testPlant );
		Assertions.assertEquals(0, testPlant.getGenome().size());
		
		testPlant.setRank("Rank Test");
		testPlant.setNameCommon("Test Starting Plant");
		testPlant.setNameBiosilico("AntHill Plant Example");
		testPlant.setDivision("TESTS");
		
		testPlant.setGenome( AntHillExampleHelper.loadingGenome(AntPlantLoadingTests.GENOME_PLANT_2020) );
		// testPlant.setGenome( AntHillExampleHelper.loadingPlantGenome() );
		
		// ***** test with a World and WorldCase
		
		World2D w		= new World2D(1, 1);
		World2DCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testPlant.setCurrentWorldCase( wc );
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));

		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.STARCH.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testPlant.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
//		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
//		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
//		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getChemicals().getVariable(k) );
		});
		
		// ***** Put DiOxygen in local WorldCase !!
		wc.getChemicals().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 100);
		
		// ***** one execution in this context
		testPlant.execution( wc );
		testPlant.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));

		Assertions.assertEquals( 21, testPlant.getChemicals().getVariable(SomeChemicals.STARCH.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  1, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testPlant.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		
		// ***** one execution in this context
		testPlant.execution( wc );
		testPlant.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));

		Assertions.assertEquals( 17, testPlant.getChemicals().getVariable(SomeChemicals.STARCH.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  2, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testPlant.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));

	}
	
	/**
	 * Simply test in a Unique WorldCase With DiOxygen and Water !
	 */
	@Test
	void testLoadingPlant03() {
		
		Plant testPlant = new Plant();
		Assertions.assertNotNull( testPlant );
		Assertions.assertEquals(0, testPlant.getGenome().size());
		
		testPlant.setRank("Rank Test");
		testPlant.setNameCommon("Test Starting Plant");
		testPlant.setNameBiosilico("AntHill Plant Example");
		testPlant.setDivision("TESTS");
		
		testPlant.setGenome( AntHillExampleHelper.loadingGenome(AntPlantLoadingTests.GENOME_PLANT_2020) );
		// testPlant.setGenome( AntHillExampleHelper.loadingPlantGenome() );
		
		// ***** test with a World and WorldCase
		
		World2D w		= new World2D(1, 1);
		World2DCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testPlant.setCurrentWorldCase( wc );
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testPlant.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getChemicals().getVariable(k) );
		});
		
		// ***** Put DiOxygen && H2O in local WorldCase !!
		wc.getChemicals().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 	100);
		wc.getChemicals().setVariable(SomeChemicals.WATER.getIndex(), 		100);
		
		// ***** one execution in this context -- 1
		testPlant.execution( wc );
		testPlant.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 45, testPlant.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  1, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testPlant.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 80, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		
		// ***** one execution in this context -- 2
		testPlant.execution( wc );
		testPlant.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 65, testPlant.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  2, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testPlant.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 60, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		
		// ***** one execution in this context -- 3
		testPlant.execution( wc );
		testPlant.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 85, testPlant.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  3, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testPlant.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 40, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		
		// ***** one execution in this context -- 4
		testPlant.execution( wc );
		testPlant.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, testPlant.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  4, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testPlant.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 20, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		
		// ***** one execution in this context -- 5
		testPlant.execution( wc );
		testPlant.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(125, testPlant.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  5, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testPlant.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));

	}
	
	/**
	 * Simply test in a Unique WorldCase With DiOxygen and Water and EnergySource !
	 */
	@Test
	void testLoadingPlant04() {
		
		Plant testPlant = new Plant();
		Assertions.assertNotNull( testPlant );
		Assertions.assertEquals(0, testPlant.getGenome().size());
		
		testPlant.setRank("Rank Test");
		testPlant.setNameCommon("Test Starting Plant");
		testPlant.setNameBiosilico("AntHill Plant Example");
		testPlant.setDivision("TESTS");
		
		testPlant.setGenome( AntHillExampleHelper.loadingGenome(AntPlantLoadingTests.GENOME_PLANT_2020) );
		// testPlant.setGenome( AntHillExampleHelper.loadingPlantGenome() );
		
		// ***** test with a World and WorldCase
		
		World2D w		= new World2D(1, 1);
		World2DCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testPlant.setCurrentWorldCase( wc );
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testPlant.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getChemicals().getVariable(k) );
		});
		
		// ***** Put DiOxygen && H2O in local WorldCase !!
		wc.getChemicals().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 	100);
		wc.getChemicals().setVariable(SomeChemicals.WATER.getIndex(), 		100);
		EnergySource es = new EnergySource();
		wc.addAgent( es );
		
		// ***** one execution in this context -- 1
		testPlant.execution( wc );
		testPlant.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 45, testPlant.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  1, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testPlant.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 80, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		
		// ***** one execution in this context -- 2
		testPlant.execution( wc );
		testPlant.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 65, testPlant.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  2, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testPlant.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 60, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		
		// ***** one execution in this context -- 3
		testPlant.execution( wc );
		testPlant.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 85, testPlant.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  3, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testPlant.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 40, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		
		// ***** one execution in this context -- 4
		testPlant.execution( wc );
		testPlant.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, testPlant.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  4, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testPlant.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 20, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		
		// ***** one execution in this context -- 5
		testPlant.execution( wc );
		testPlant.cyclePlusPlus(); // Aging organism
		
		Logger.printlnLog(LoggerLevel.LL_DEBUG, testPlant.toString() );
		Logger.printlnLog(LoggerLevel.LL_DEBUG, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_DEBUG, es.toString() );
		Logger.printlnLog(LoggerLevel.LL_DEBUG, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_DEBUG, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_DEBUG, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(125, testPlant.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  5, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testPlant.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));

	}
	
	/**
	 * Simply test in a Unique WorldCase With DiOxygen and Water and EnergySource !
	 * <br/> Running World !!
	 */
	@Test
	void testLoadingPlant05() {
		
		Plant testPlant = new Plant();
		Assertions.assertNotNull( testPlant );
		Assertions.assertEquals(0, testPlant.getGenome().size());
		
		testPlant.setRank("Rank Test");
		testPlant.setNameCommon("Test Starting Plant");
		testPlant.setNameBiosilico("AntHill Plant Example");
		testPlant.setDivision("TESTS");
		
		testPlant.setGenome( AntHillExampleHelper.loadingGenome(AntPlantLoadingTests.GENOME_PLANT_2020) );
		// testPlant.setGenome( AntHillExampleHelper.loadingPlantGenome() );
		
		// ***** test with a World and WorldCase
		
		World2D w		= new World2D(1, 1);
		World2DCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testPlant.setCurrentWorldCase( wc );
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testPlant.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getChemicals().getVariable(k) );
		});
		
		// ***** Put DiOxygen && H2O in local WorldCase !!
		wc.getChemicals().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 	100);
		wc.getChemicals().setVariable(SomeChemicals.WATER.getIndex(), 		100);
		EnergySource es = new EnergySource();
		wc.addAgent( es );
		
		// ***** one execution in this context -- 1
		w.execution();
		testPlant.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 45, testPlant.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  1, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testPlant.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 80, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** one execution in this context -- 2
		w.execution();
		testPlant.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 65, testPlant.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  2, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testPlant.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 60, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 30, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals( 40, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** one execution in this context -- 3
		w.execution();
		testPlant.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 85, testPlant.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  3, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testPlant.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 40, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 35, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals( 55, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** one execution in this context -- 4
		w.execution();
		testPlant.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, testPlant.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  4, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testPlant.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 20, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 40, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals( 70, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** one execution in this context -- 5
		w.execution();
		testPlant.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testPlant, es, wc);
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(125, testPlant.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  5, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testPlant.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 45, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals( 85, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** Put some pheromones on local (00 : ch650)
		wc.getChemicals().setVarPlus(SomeChemicals.PHEROMONE_00.getIndex(), 50);
		// ***** MORE executionS in this context -- 6
		w.execution();
		testPlant.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testPlant, es, wc);
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(125, testPlant.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  6, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testPlant.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 50, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals( 50, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** Loading Chemicals HalfLives in World !!
		w.loadHalLives();
		
		// ***** MORE executionS in this context -- 6
		w.execution();
		testPlant.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testPlant, es, wc);
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(125, testPlant.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  7, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testPlant.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 35, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(105, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals( 40, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** MORE executionS in this context -- 7
		testPlant.getChemicals().setVarPlus(SomeChemicals.HEXOKINASE.getIndex(), 15);
		testPlant.getChemicals().setVarPlus(SomeChemicals.GLUCOSE.getIndex(), 50);
		w.execution();
		testPlant.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testPlant, es, wc);
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 15, testPlant.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 75, testPlant.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(125, testPlant.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  8, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testPlant.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 20, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(110, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals( 30, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** MORE executionS in this context -- 8
		w.execution();
		testPlant.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testPlant, es, wc);
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 15, testPlant.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 75, testPlant.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(125, testPlant.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  9, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testPlant.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  5, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(115, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals( 20, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** MORE executionS in this context -- 9
		w.execution();
		testPlant.cyclePlusPlus(); // Aging organism
		
		BuildingGenomeHelper.show(testPlant, es, wc);
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.PHOSPHOR.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ATP.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ADP.getIndex()));

		Assertions.assertEquals( 15, testPlant.getChemicals().getVariable(SomeChemicals.HEXOKINASE.getIndex()));
		Assertions.assertEquals( 75, testPlant.getChemicals().getVariable(SomeChemicals.GLUCOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.FRUCTOSE.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.G6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.F6P.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(125, testPlant.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals( 10, testPlant.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testPlant.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 10, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(120, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals( 10, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));

	}

}

package gabywald.biosilico.anthill.tests;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.anthill.Ant;
import gabywald.biosilico.anthill.GeneratorReceptionChemicals;
import gabywald.biosilico.anthill.launcher.AntHillExampleHelper;
import gabywald.biosilico.anthill.launcher.BuildingGenomeHelper;
import gabywald.biosilico.model.chemicals.ChemicalsHelper;
import gabywald.biosilico.model.enums.ObjectType;
import gabywald.biosilico.model.enums.SomeChemicals;
import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.environment.World2D;
import gabywald.biosilico.model.environment.World2DCase;
import gabywald.biosilico.model.tests.TestObjectFoodEgg;
import gabywald.biosilico.model.utils.agents.EnergySource;
import gabywald.global.data.StringUtils;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020, 2022)
 */
class AntLoadingTests {
	
	/**
	 * Import / Export
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
		
		testAnt.setGenome( AntHillExampleHelper.loadingAntGenome() );
		
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
		Assertions.assertEquals( 20, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertEquals( 15, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  2, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 10, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 10, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));

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
		
		testAnt.setGenome( AntHillExampleHelper.loadingAntGenome() );
		
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
		Assertions.assertEquals( 20, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertEquals( 15, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));		
		Assertions.assertEquals(  2, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 80, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 10, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 10, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));

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
		
		testAnt.setGenome( AntHillExampleHelper.loadingAntGenome() );
		
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
		Assertions.assertEquals( 20, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertEquals( 15, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  2, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 80, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 10, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(110, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		
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
		Assertions.assertEquals( 10, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  3, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 70, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 15, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(115, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		
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
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  4, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 60, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 20, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(120, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		
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
		Assertions.assertEquals(125, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));

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
		
		testAnt.setGenome( AntHillExampleHelper.loadingAntGenome() );
		
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
		Assertions.assertEquals( 20, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertEquals( 15, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  2, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 80, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 10, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(110, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertEquals( 10, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  3, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 70, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 15, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(115, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  4, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 60, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 20, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(120, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		
		// ***** one execution in this context -- 5
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		Logger.printlnLog(LoggerLevel.LL_INFO, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, es.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		
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
		Assertions.assertEquals(125, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		
		testAnt.setGenome( AntHillExampleHelper.loadingAntGenome() );
		
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
		Assertions.assertEquals( 20, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
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
		Assertions.assertEquals( 15, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  2, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
		Assertions.assertEquals( 80, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 10, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(110, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertEquals( 10, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  3, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
		Assertions.assertEquals( 70, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 15, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(115, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  4, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
		Assertions.assertEquals( 60, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 20, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(120, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
		Assertions.assertEquals( 50, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(125, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
		Assertions.assertEquals( 40, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(125, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
		Assertions.assertEquals( 30, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(125, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
		Assertions.assertEquals( 20, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(125, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
		Assertions.assertEquals( 10, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(125, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(125, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		
		testAnt.setGenome( AntHillExampleHelper.loadingAntGenome() );
		
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
		Assertions.assertEquals( 20, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
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
		Assertions.assertEquals( 15, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  2, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
		Assertions.assertEquals( 80, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 10, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(110, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertEquals( 10, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  3, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
		Assertions.assertEquals( 70, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 15, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(115, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  4, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
		Assertions.assertEquals( 60, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 20, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(120, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
		Assertions.assertEquals( 50, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(125, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
		Assertions.assertEquals( 40, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(125, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
		Assertions.assertEquals( 30, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(125, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
		Assertions.assertEquals( 20, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(125, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
		Assertions.assertEquals( 10, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(125, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(125, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		
		testAnt.setGenome( AntHillExampleHelper.loadingAntGenome() );
		
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
		Assertions.assertEquals( 20, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
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
		Assertions.assertEquals( 15, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  2, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
		Assertions.assertEquals( 80, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 10, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(110, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertEquals( 10, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  3, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
		Assertions.assertEquals( 70, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 15, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(115, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  4, testAnt.getChemicals().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3,  1).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
		Assertions.assertEquals( 60, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 20, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(120, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
		Assertions.assertEquals( 50, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(125, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
		Assertions.assertEquals( 40, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(125, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
		Assertions.assertEquals( 30, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(125, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
		Assertions.assertEquals( 20, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(125, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
		Assertions.assertEquals( 10, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(125, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
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
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90,  0).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(90, 10).ckActivated() );
		
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getChemicals().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(125, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(170, wc.getChemicals().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(165, wc.getChemicals().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals( 10, wc.getChemicals().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));

	}

}

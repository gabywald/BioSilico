package gabywald.biosilico.anthill.tests;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.anthill.Plant;
import gabywald.biosilico.anthill.GeneratorReceptionChemicals;
import gabywald.biosilico.anthill.launcher.AntHillExampleHelper;
import gabywald.biosilico.anthill.launcher.BuildingGenomeHelper;
import gabywald.biosilico.model.chemicals.ChemicalsHelper;
import gabywald.biosilico.model.enums.ObjectType;
import gabywald.biosilico.model.enums.SomeChemicals;
import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.environment.World2D;
import gabywald.biosilico.model.environment.World2DCase;
import gabywald.biosilico.model.utils.agents.EnergySource;
import gabywald.global.data.StringUtils;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2022)
 */
class PlantLoadingTest {

	/**
	 * Import / Export
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
		
		testPlant.setGenome( AntHillExampleHelper.loadingPlantGenome() );
		
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

		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.STARCH.getIndex()));
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

		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.STARCH.getIndex()));
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
		
		testPlant.setGenome( AntHillExampleHelper.loadingPlantGenome() );
		
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

		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.STARCH.getIndex()));
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

		Assertions.assertEquals( 25, testPlant.getChemicals().getVariable(SomeChemicals.STARCH.getIndex()));
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
		
		testPlant.setGenome( AntHillExampleHelper.loadingPlantGenome() );
		
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
		
		testPlant.setGenome( AntHillExampleHelper.loadingPlantGenome() );
		
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
		
		Logger.printlnLog(LoggerLevel.LL_INFO, testPlant.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, es.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		
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
		
		testPlant.setGenome( AntHillExampleHelper.loadingPlantGenome() );
		
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

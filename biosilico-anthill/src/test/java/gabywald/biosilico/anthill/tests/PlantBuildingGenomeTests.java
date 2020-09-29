package gabywald.biosilico.anthill.tests;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.anthill.Plant;
import gabywald.biosilico.anthill.launcher.BuildingGenomeHelper;
import gabywald.biosilico.genetics.builders.BiochemicalReactionBuilder;
import gabywald.biosilico.genetics.builders.InitialConcentrationBuilder;
import gabywald.biosilico.genetics.builders.StimulusDecisionBuilder;
import gabywald.biosilico.model.Chromosome;
import gabywald.biosilico.model.enums.AgentType;
import gabywald.biosilico.model.enums.DecisionType;
import gabywald.biosilico.model.enums.DirectionWorld;
import gabywald.biosilico.model.enums.ObjectType;
import gabywald.biosilico.model.enums.SomeChemicals;
import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.enums.StatusType;
import gabywald.biosilico.model.environment.World2D;
import gabywald.biosilico.model.environment.World2DCase;
import gabywald.biosilico.model.reproduction.ReproductionHelper;
import gabywald.biosilico.model.utils.agents.Condensator;
import gabywald.biosilico.model.utils.agents.EnergySource;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class PlantBuildingGenomeTests {

	@Test
	void testPlantBiochemicalGenome() {
		Chromosome chrBiochemical			= new Chromosome();
		Chromosome chrSDdecision			= new Chromosome();
		Chromosome chrSDdetection			= new Chromosome();
		
		chrBiochemical.setName("Basic Biochemicals");
		chrSDdecision.setName("Decisions");
		chrSDdetection.setName("Emit / Receive some Chemicals");
		
		// ***** Only InitialConcentration && StimulusDecision && BiochemicalReaction
		InitialConcentrationBuilder icb		= new InitialConcentrationBuilder();
		BiochemicalReactionBuilder brb		= new BiochemicalReactionBuilder();
		StimulusDecisionBuilder sdgb		= new StimulusDecisionBuilder();
		
		// ***** InitialConcentration
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.GLUCOSE.getIndex() ).value( 25 )
				.name( "InitConc GLUCOSE 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.STARCH.getIndex() ).value( 25 )
				.name( "InitConc STARCH 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.CARBON_DIOXYDE.getIndex() ).value( 25 )
				.name( "InitConc CARBON_DIOXYDE 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.WATER.getIndex() ).value( 25 )
				.name( "InitConc WATER 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.ENERGY_HEAT.getIndex() ).value( 25 )
				.name( "InitConc ENERGY_HEAT 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.ENERGY_SOLAR.getIndex() ).value( 25 )
				.name( "InitConc ENERGY_SOLAR 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		// ***** BiochemicalReaction
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.GLUCOSE.getIndex()).acoef( 6 )
				.bchem(SomeChemicals.NEUTRAL.getIndex()).bcoef( 0 )
				.cchem(SomeChemicals.STARCH.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : 6 GLUCOSE to 1 STARCH !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.STARCH.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.NEUTRAL.getIndex()).bcoef( 0 )
				.cchem(SomeChemicals.GLUCOSE.getIndex()).ccoef( 6 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 1 )
			.name( "BiochemicalReaction : 1 STARCH to 6 GLUCOSE !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.CARBON_DIOXYDE.getIndex()).acoef( 5 )
				.bchem(SomeChemicals.WATER.getIndex()).bcoef( 5 )
				.cchem(SomeChemicals.COMBINATOR_2.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination 2 of H2O and CO2 (5 each) !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.CARBON_DIOXYDE.getIndex()).acoef( 6 )
				.bchem(SomeChemicals.WATER.getIndex()).bcoef( 6 )
				.cchem(SomeChemicals.COMBINATOR_1.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination 1 of H2O and CO2 (6 each) !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.CARBON_DIOXYDE.getIndex()).acoef( 5 )
				.bchem(SomeChemicals.WATER.getIndex()).bcoef( 5 )
				.cchem(SomeChemicals.COMBINATOR_2.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 20 )
			.name( "BiochemicalReaction : Combination 2 of H2O and CO2 (5 each) ! (2)" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.CARBON_DIOXYDE.getIndex()).acoef( 6 )
				.bchem(SomeChemicals.WATER.getIndex()).bcoef( 6 )
				.cchem(SomeChemicals.COMBINATOR_1.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 20 )
			.name( "BiochemicalReaction : Combination 1 of H2O and CO2 (6 each) ! (2)" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.CARBON_DIOXYDE.getIndex()).acoef( 11 )
				.bchem(SomeChemicals.WATER.getIndex()).bcoef( 11 )
				.cchem(SomeChemicals.COMBINATOR_1.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.COMBINATOR_2.getIndex()).dcoef( 1 )
				.kmvm( 5 )
			.name( "BiochemicalReaction : Combination 1 and 2 of H2O and CO2 (6 each) ! (3)" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.COMBINATOR_2.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_SOLAR.getIndex()).bcoef( 1 )
				.cchem(SomeChemicals.FRUCTOSE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination2(H2O, CO2) + 'Solar NRJ' gives Fructose !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.COMBINATOR_2.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_HEAT.getIndex()).bcoef( 3 )
				.cchem(SomeChemicals.FRUCTOSE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination2(H2O, CO2) + 'Heat NRJ' gives Fructose !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.COMBINATOR_1.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_SOLAR.getIndex()).bcoef( 1 )
				.cchem(SomeChemicals.GLUCOSE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination1(H2O, CO2) + 'Solar NRJ' gives Glucose !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.COMBINATOR_1.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_HEAT.getIndex()).bcoef( 3 )
				.cchem(SomeChemicals.GLUCOSE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination1(H2O, CO2) + 'Heat NRJ' gives Glucose !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.STARCH.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_SOLAR.getIndex()).bcoef( 5 )
				.cchem(DecisionType.MAKE_GAMET.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.STARCH.getIndex()).dcoef( 1 )
				.kmvm( 3 )
			.name( "BiochemicalReaction : STARCH + NRJ SOLAR => MAKE_GAMET !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.STARCH.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_HEAT.getIndex()).bcoef( 5 )
				.cchem(DecisionType.MATE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.STARCH.getIndex()).dcoef( 1 )
				.kmvm( 1 )
			.name( "BiochemicalReaction : STARCH + NRJ HEAT => MATE !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(StateType.PREGNANT.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_SOLAR.getIndex()).bcoef( 5 )
				.cchem(DecisionType.LAY_EGG.getIndex()).ccoef( 1 )
				.dchem(StateType.PREGNANT.getIndex()).dcoef( 1 )
				.kmvm( 1 )
			.name( "BiochemicalReaction : PREGNANT + NRJ SOLAR => LAY_EGG !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.FRUCTOSE.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.NEUTRAL.getIndex()).bcoef( 0 )
				.cchem(StateType.FERTILE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 1 )
			.name( "BiochemicalReaction : 1 FRUCTOSE MAKE FERTILITY !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		// ***** StimulusDecision (detection)
		chrSDdetection.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.DIOXYGEN.getIndex() )
				.varia( SomeChemicals.DIOXYGEN.getIndex() )
				.value( 20 ).script( DecisionType.EMIT.getIndex() )
			.name( "StimulusDecision EMIT DIOXYGEN" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrSDdetection.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.CARBON_DIOXYDE.getIndex() )
				.varia( SomeChemicals.CARBON_DIOXYDE.getIndex() )
				.value( 20 ).script( DecisionType.RECEIVE.getIndex() )
			.name( "StimulusDecision RECEIVE CARBON_DIOXYDE" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrSDdetection.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.WATER.getIndex() )
				.varia( SomeChemicals.WATER.getIndex() )
				.value( 20 ).script( DecisionType.RECEIVE.getIndex() )
			.name( "StimulusDecision RECEIVE WATER" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrSDdetection.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.ENERGY_HEAT.getIndex() )
				.varia( SomeChemicals.ENERGY_HEAT.getIndex() )
				.value( 10 ).script( DecisionType.RECEIVE.getIndex() )
			.name( "StimulusDecision RECEIVE ENERGY_HEAT" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrSDdetection.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.ENERGY_SOLAR.getIndex() )
				.varia( SomeChemicals.ENERGY_SOLAR.getIndex() )
				.value( 20 ).script( DecisionType.RECEIVE.getIndex() )
			.name( "StimulusDecision RECEIVE ENERGY_SOLAR" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrSDdecision.addGene( sdgb.perception( false ).object( true ) 
				.indicator( 0 ).threshold( 1 )
				.attribute( AgentType.BIOSILICO_VIRIDITA.getIndex() )
				.varia( 0 ).value( 0 )						// varia && value are irrelevant here !
				.script( DecisionType.LAY_EGG.getIndex() )	// What to do !
			.name( "StimulusDecisionGene_LAY_EGG" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrSDdecision.addGene( sdgb.perception( false ).object( true ) 
				.indicator( 0 ).threshold( 1 )
				.attribute( AgentType.BIOSILICO_VIRIDITA.getIndex() )
				.varia( 0 ).value( 0 )							// varia && value are irrelevant here !
				.script( DecisionType.MAKE_GAMET.getIndex() )	// What to do !
			.name( "StimulusDecisionGene_MAKE_GAMET_AS_FOOD" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrSDdecision.addGene( sdgb.perception( false ).object( true ) 
				.indicator( 0 ).threshold( 1 )
				.attribute( 0 ).varia( 0 ).value( 0 )	// varia && value are irrelevant here !
				.script( DecisionType.MATE.getIndex() )	// What to do !
			.name( "StimulusDecisionGene_MATE" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrSDdecision.addGene( sdgb.perception( false ).object( false ) 
				.indicator( StatusType.EGG.getIndex() ).threshold( 10 )
				.attribute( 0 ).varia( 0 ).value( 0 )	// varia && value are irrelevant here !
				.script( DecisionType.LAY_EGG.getIndex() )	// What to do !
			.name( "StimulusDecisionGene_LAY_EGG_FromEGG" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrSDdecision.addGene( sdgb.perception( false ).object( false ) 
				.indicator( StatusType.GAMET.getIndex() ).threshold( 2 )
				.attribute( 0 ).varia( 0 ).value( 0 )	// varia && value are irrelevant here !
				.script( DecisionType.MATE.getIndex() )	// What to do !
			.name( "StimulusDecisionGene_MATE_FromGAMET" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		int thresholdGlucose2kill = 500;
		chrSDdecision.addGene( sdgb	.perception( false ).object( false )
				.indicator( SomeChemicals.GLUCOSE.getIndex() ).threshold( thresholdGlucose2kill )
				.attribute( SomeChemicals.GLUCOSE.getIndex() )
				.varia( StateType.AGING.getIndex() ).value( 999 )
				.script( DecisionType.DEATH.getIndex() )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.name( "DEATH of GLUCOSE over " + thresholdGlucose2kill )
			.build() );
		chrSDdecision.addGene( sdgb	.perception( false ).object( false )
				.indicator( SomeChemicals.CARBON_DIOXYDE.getIndex() ).threshold( thresholdGlucose2kill )
				.attribute( SomeChemicals.CARBON_DIOXYDE.getIndex() )
				.varia( StateType.AGING.getIndex() ).value( 999 )
				.script( DecisionType.DEATH.getIndex() )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.name( "DEATH of CARBON_DIOXYDE over " + thresholdGlucose2kill )
			.build() );
		chrSDdecision.addGene( sdgb	.perception( false ).object( false )
				.indicator( SomeChemicals.DIOXYGEN.getIndex() ).threshold( thresholdGlucose2kill )
				.attribute( SomeChemicals.DIOXYGEN.getIndex() )
				.varia( StateType.AGING.getIndex() ).value( 999 )
				.script( DecisionType.DEATH.getIndex() )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.name( "DEATH of DIOXYGEN over " + thresholdGlucose2kill )
			.build() );
		chrSDdecision.addGene( sdgb	.perception( false ).object( false )
				.indicator( SomeChemicals.WATER.getIndex() ).threshold( thresholdGlucose2kill )
				.attribute( SomeChemicals.WATER.getIndex() )
				.varia( StateType.AGING.getIndex() ).value( 999 )
				.script( DecisionType.DEATH.getIndex() )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.name( "DEATH of WATER over " + thresholdGlucose2kill )
			.build() );
		
		chrSDdecision.addGene( sdgb	.perception( false ).object( false )
				.indicator( StateType.AGING.getIndex() ).threshold( 950 )
				.attribute( StateType.AGING.getIndex() )
				.varia( StateType.AGING.getIndex() ).value( 999 )
				.script( DecisionType.DEATH.getIndex() )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.name( "DEATH of AGING over 950" )
			.build() );
		
		// ***** Instantiate Plant and Organism With Genome !!
		Plant testPlant = new Plant( Arrays.asList( chrBiochemical, chrSDdecision, chrSDdetection ) );
		Assertions.assertNotNull( testPlant );
		Assertions.assertEquals(3, testPlant.getGenome().size());
	
		Assertions.assertEquals(chrBiochemical.length(), 	testPlant.getGenome().get( 0 ).length());
		Assertions.assertEquals(chrSDdecision.length(), 	testPlant.getGenome().get( 1 ).length());
		Assertions.assertEquals(chrSDdetection.length(), 	testPlant.getGenome().get( 2 ).length());
		
		Assertions.assertEquals( 21, testPlant.getGenome().get( 0 ).length());
		Assertions.assertEquals( 10, testPlant.getGenome().get( 1 ).length());
		Assertions.assertEquals(  5, testPlant.getGenome().get( 2 ).length());
		
		Integer genesNumber = ReproductionHelper.sizeOfGenome( testPlant );
		Logger.printlnLog(LoggerLevel.LL_DEBUG, genesNumber.toString());
		Assertions.assertEquals( 36, genesNumber.intValue() );
		
		List<Integer> listLengthGenomes = testPlant.getGenome().stream().map( Chromosome::length ).collect(Collectors.toList());
		Assertions.assertEquals( 36, listLengthGenomes.stream().reduce(0, Integer::sum).intValue());
		
		testPlant.setRank("Rank Test");
		testPlant.setNameCommon("Test Starting Plant");
		testPlant.setNameBiosilico("PlantHill Plant Example");
		testPlant.setDivision("TESTS");
		
		// ***** ***** ***** ***** ***** ***** ***** ***** ***** 
		// ***** ***** ***** ***** ***** ***** ***** ***** ***** 
		// ***** test with a World and WorldCase
		
		World2D w		= new World2D(3, 3);
		World2DCase wc	= w.getWorldCase(1, 1);
		Assertions.assertNotNull( wc );
		
		testPlant.setCurrentWorldCase( wc );
	
		// ***** one execution in this context
		w.execution();
		testPlant.cyclePlusPlus(); // to permit correct initialization of "starting genes"
		
		Assertions.assertEquals( 0, testPlant.hasObjectType(ObjectType.FOOD));
		Assertions.assertEquals( 1, testPlant.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals( 0, testPlant.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals( 0, testPlant.lineageSize());
		Assertions.assertEquals( 1, wc.hasAgentType(AgentType.BIOSILICO_VIRIDITA));
		Assertions.assertEquals( 1, wc.getAgentListe().size());
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		IntStream.range(0, 20).forEach( i -> {
			w.execution();
			Assertions.assertEquals( 0, testPlant.hasObjectType(ObjectType.FOOD));
			Assertions.assertTrue(testPlant.hasAgentStatus(StatusType.GAMET) >= 0);
			Assertions.assertTrue(testPlant.hasAgentStatus(StatusType.EGG) == 0);
			Assertions.assertEquals( 0, testPlant.lineageSize());
			// Assertions.assertEquals( 2, wc.hasAgentType(AgentType.BIOSILICO_VIRIDITA));
			Assertions.assertTrue(wc.hasAgentType(AgentType.BIOSILICO_VIRIDITA) >= 2);
			// Assertions.assertEquals( 3, wc.getAgentListe().size());
			Assertions.assertTrue( wc.getAgentListe().size() >= 2);
		});
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		wc.addAgent(new EnergySource());
		wc.addAgent(new Condensator());
		
		// ***** one execution in this context
		w.execution();
		testPlant.cyclePlusPlus(); // to permit correct initialization of "starting genes"
		
		Assertions.assertEquals( 0, testPlant.hasObjectType(ObjectType.FOOD));
		Assertions.assertEquals( 0, testPlant.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals( 0, testPlant.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals( 0, testPlant.lineageSize());
		Assertions.assertEquals( 2, wc.hasAgentType(AgentType.BIOSILICO_VIRIDITA));
		Assertions.assertEquals( 4, wc.getAgentListe().size());
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		IntStream.range(0, 20).forEach( i -> {
			w.execution();
		});
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		// ***** one execution in this context
		w.execution();
		testPlant.cyclePlusPlus(); // to permit correct initialization of "starting genes"
		
		BuildingGenomeHelper.show(testPlant, wc);
		
//		List<Agent> plants = IAgentContent.getListOfType(AgentType.BIOSILICO_VIRIDITA, StateType.AGENT_TYPE.getIndex(), wc.getAgentListe());
//		plants.stream().forEach( p -> {
//			BuildingGenomeHelper.show( p );
//		});
		
		// ***** Export Ant as a TXT file !
		BuildingGenomeHelper.exportAntAsTXTfile("TestPlantBiochemicalGenome.txt", testPlant);
		
		BuildingGenomeHelper.exportGenome("GenomePlantCompleteGenome.txt", testPlant);
		
		BuildingGenomeHelper.copyMoveGenome("GenomePlantCompleteGenome.txt", "baseGenomePlant.txt");
		BuildingGenomeHelper.removeACGTsequence( "baseGenomePlant.txt" );
	}

	@Test
	void testPlantBiochemicalReactionGenome() {
		Chromosome chrBiochemical			= new Chromosome();
		
		chrBiochemical.setName("Basic Biochemicals");
		
		// ***** Only InitialConcentration && StimulusDecision && BiochemicalReaction
		InitialConcentrationBuilder icb		= new InitialConcentrationBuilder();
		BiochemicalReactionBuilder brb		= new BiochemicalReactionBuilder();
		
		// ***** InitialConcentration
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.GLUCOSE.getIndex() ).value( 25 )
				.name( "InitConc GLUCOSE 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.STARCH.getIndex() ).value( 25 )
				.name( "InitConc STARCH 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.CARBON_DIOXYDE.getIndex() ).value( 25 )
				.name( "InitConc CARBON_DIOXYDE 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.WATER.getIndex() ).value( 25 )
				.name( "InitConc WATER 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.ENERGY_HEAT.getIndex() ).value( 25 )
				.name( "InitConc ENERGY_HEAT 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.ENERGY_SOLAR.getIndex() ).value( 25 )
				.name( "InitConc ENERGY_SOLAR 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		// ***** BiochemicalReaction
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.GLUCOSE.getIndex()).acoef( 6 )
				.bchem(SomeChemicals.NEUTRAL.getIndex()).bcoef( 0 )
				.cchem(SomeChemicals.STARCH.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : 6 GLUCOSE to 1 STARCH !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.STARCH.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.NEUTRAL.getIndex()).bcoef( 0 )
				.cchem(SomeChemicals.GLUCOSE.getIndex()).ccoef( 6 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 1 )
			.name( "BiochemicalReaction : 1 STARCH to 6 GLUCOSE !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.CARBON_DIOXYDE.getIndex()).acoef( 5 )
				.bchem(SomeChemicals.WATER.getIndex()).bcoef( 5 )
				.cchem(SomeChemicals.COMBINATOR_2.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination 2 of H2O and CO2 (5 each) !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.CARBON_DIOXYDE.getIndex()).acoef( 6 )
				.bchem(SomeChemicals.WATER.getIndex()).bcoef( 6 )
				.cchem(SomeChemicals.COMBINATOR_1.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination 1 of H2O and CO2 (6 each) !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.CARBON_DIOXYDE.getIndex()).acoef( 5 )
				.bchem(SomeChemicals.WATER.getIndex()).bcoef( 5 )
				.cchem(SomeChemicals.COMBINATOR_2.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 20 )
			.name( "BiochemicalReaction : Combination 2 of H2O and CO2 (5 each) ! (2)" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.CARBON_DIOXYDE.getIndex()).acoef( 6 )
				.bchem(SomeChemicals.WATER.getIndex()).bcoef( 6 )
				.cchem(SomeChemicals.COMBINATOR_1.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 20 )
			.name( "BiochemicalReaction : Combination 1 of H2O and CO2 (6 each) ! (2)" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.CARBON_DIOXYDE.getIndex()).acoef( 11 )
				.bchem(SomeChemicals.WATER.getIndex()).bcoef( 11 )
				.cchem(SomeChemicals.COMBINATOR_1.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.COMBINATOR_2.getIndex()).dcoef( 1 )
				.kmvm( 5 )
			.name( "BiochemicalReaction : Combination 1 and 2 of H2O and CO2 (6 each) ! (3)" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.COMBINATOR_2.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_SOLAR.getIndex()).bcoef( 1 )
				.cchem(SomeChemicals.FRUCTOSE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination2(H2O, CO2) + 'Solar NRJ' gives Fructose !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.COMBINATOR_2.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_HEAT.getIndex()).bcoef( 3 )
				.cchem(SomeChemicals.FRUCTOSE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination2(H2O, CO2) + 'Heat NRJ' gives Fructose !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.COMBINATOR_1.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_SOLAR.getIndex()).bcoef( 1 )
				.cchem(SomeChemicals.GLUCOSE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination1(H2O, CO2) + 'Solar NRJ' gives Glucose !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.COMBINATOR_1.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_HEAT.getIndex()).bcoef( 3 )
				.cchem(SomeChemicals.GLUCOSE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination1(H2O, CO2) + 'Heat NRJ' gives Glucose !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.STARCH.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_SOLAR.getIndex()).bcoef( 5 )
				.cchem(DecisionType.MAKE_GAMET.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.STARCH.getIndex()).dcoef( 1 )
				.kmvm( 3 )
			.name( "BiochemicalReaction : STARCH + NRJ SOLAR => MAKE_GAMET !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.STARCH.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_HEAT.getIndex()).bcoef( 5 )
				.cchem(DecisionType.MATE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.STARCH.getIndex()).dcoef( 1 )
				.kmvm( 1 )
			.name( "BiochemicalReaction : STARCH + NRJ HEAT => MATE !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(StateType.PREGNANT.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_SOLAR.getIndex()).bcoef( 5 )
				.cchem(DecisionType.LAY_EGG.getIndex()).ccoef( 1 )
				.dchem(StateType.PREGNANT.getIndex()).dcoef( 1 )
				.kmvm( 1 )
			.name( "BiochemicalReaction : PREGNANT + NRJ SOLAR => LAY_EGG !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.FRUCTOSE.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.NEUTRAL.getIndex()).bcoef( 0 )
				.cchem(StateType.FERTILE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 1 )
			.name( "BiochemicalReaction : 1 FRUCTOSE MAKE FERTILITY !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		
		// ***** Instantiate Plant and Organism With Genome !!
		Plant testPlant = new Plant( Arrays.asList( chrBiochemical ) );
		Assertions.assertNotNull( testPlant );
		Assertions.assertEquals(1, testPlant.getGenome().size());

		Assertions.assertEquals(chrBiochemical.length(), 	testPlant.getGenome().get( 0 ).length());
		
		Assertions.assertEquals( 21, testPlant.getGenome().get( 0 ).length());
		
		Integer genesNumber = ReproductionHelper.sizeOfGenome( testPlant );
		Logger.printlnLog(LoggerLevel.LL_DEBUG, genesNumber.toString());
		Assertions.assertEquals( 21, genesNumber.intValue() );
		
		List<Integer> listLengthGenomes = testPlant.getGenome().stream().map( Chromosome::length ).collect(Collectors.toList());
		Assertions.assertEquals( 21, listLengthGenomes.stream().reduce(0, Integer::sum).intValue());
		
		testPlant.setRank("Rank Test");
		testPlant.setNameCommon("Test Starting Plant");
		testPlant.setNameBiosilico("PlantHill Plant Example");
		testPlant.setDivision("TESTS");
		
		// ***** ***** ***** ***** ***** ***** ***** ***** ***** 
		// ***** ***** ***** ***** ***** ***** ***** ***** ***** 
		// ***** test with a World and WorldCase
		
		World2D w			= new World2D(3, 3);
		World2DCase wc	= w.getWorldCase(1, 1);
		Assertions.assertNotNull( wc );
		
		testPlant.setCurrentWorldCase( wc );

		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		// ***** one execution in this context -- 1
		w.execution();
		testPlant.cyclePlusPlus(); // to permit correct initialization of "starting genes"
		
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals(  7, testPlant.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals(  4, testPlant.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 28, testPlant.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals(  5, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals( 20, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals( 10, testPlant.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  1, testPlant.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		// ***** one execution in this context -- 2
		w.execution();
		testPlant.cyclePlusPlus(); // to permit correct initialization of "starting genes"
		
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals(  7, testPlant.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals(  3, testPlant.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 28, testPlant.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals( 15, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals( 10, testPlant.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  2, testPlant.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		// ***** one execution in this context -- 3
		w.execution();
		testPlant.cyclePlusPlus(); // to permit correct initialization of "starting genes"
		
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals(  7, testPlant.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals(  2, testPlant.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 28, testPlant.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals( 10, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals( 10, testPlant.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  3, testPlant.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		// ***** Export Ant as a TXT file !
		BuildingGenomeHelper.exportAntAsTXTfile("TestPlantBiochemicalReactionGenome.txt", testPlant);
		
	}
	
	@Test
	void testPlantBiochemicalReactionGenomeWithEnergies01() {
		Chromosome chrBiochemical			= new Chromosome();
		Chromosome chrSDdetection			= new Chromosome();
		
		chrBiochemical.setName("Basic Biochemicals");
		chrSDdetection.setName("Emit / Receive some Chemicals");
		
		// ***** Only InitialConcentration && StimulusDecision && BiochemicalReaction
		InitialConcentrationBuilder icb		= new InitialConcentrationBuilder();
		BiochemicalReactionBuilder brb		= new BiochemicalReactionBuilder();
		StimulusDecisionBuilder sdgb		= new StimulusDecisionBuilder();
		
		// ***** InitialConcentration
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.GLUCOSE.getIndex() ).value( 25 )
				.name( "InitConc GLUCOSE 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.STARCH.getIndex() ).value( 25 )
				.name( "InitConc STARCH 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.CARBON_DIOXYDE.getIndex() ).value( 25 )
				.name( "InitConc CARBON_DIOXYDE 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.WATER.getIndex() ).value( 25 )
				.name( "InitConc WATER 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.ENERGY_HEAT.getIndex() ).value( 25 )
				.name( "InitConc ENERGY_HEAT 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.ENERGY_SOLAR.getIndex() ).value( 25 )
				.name( "InitConc ENERGY_SOLAR 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		// ***** BiochemicalReaction
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.GLUCOSE.getIndex()).acoef( 6 )
				.bchem(SomeChemicals.NEUTRAL.getIndex()).bcoef( 0 )
				.cchem(SomeChemicals.STARCH.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : 6 GLUCOSE to 1 STARCH !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.STARCH.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.NEUTRAL.getIndex()).bcoef( 0 )
				.cchem(SomeChemicals.GLUCOSE.getIndex()).ccoef( 6 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 1 )
			.name( "BiochemicalReaction : 1 STARCH to 6 GLUCOSE !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.CARBON_DIOXYDE.getIndex()).acoef( 5 )
				.bchem(SomeChemicals.WATER.getIndex()).bcoef( 5 )
				.cchem(SomeChemicals.COMBINATOR_2.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination 2 of H2O and CO2 (5 each) !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.CARBON_DIOXYDE.getIndex()).acoef( 6 )
				.bchem(SomeChemicals.WATER.getIndex()).bcoef( 6 )
				.cchem(SomeChemicals.COMBINATOR_1.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination 1 of H2O and CO2 (6 each) !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.CARBON_DIOXYDE.getIndex()).acoef( 5 )
				.bchem(SomeChemicals.WATER.getIndex()).bcoef( 5 )
				.cchem(SomeChemicals.COMBINATOR_2.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 20 )
			.name( "BiochemicalReaction : Combination 2 of H2O and CO2 (5 each) ! (2)" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.CARBON_DIOXYDE.getIndex()).acoef( 6 )
				.bchem(SomeChemicals.WATER.getIndex()).bcoef( 6 )
				.cchem(SomeChemicals.COMBINATOR_1.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 20 )
			.name( "BiochemicalReaction : Combination 1 of H2O and CO2 (6 each) ! (2)" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.CARBON_DIOXYDE.getIndex()).acoef( 11 )
				.bchem(SomeChemicals.WATER.getIndex()).bcoef( 11 )
				.cchem(SomeChemicals.COMBINATOR_1.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.COMBINATOR_2.getIndex()).dcoef( 1 )
				.kmvm( 5 )
			.name( "BiochemicalReaction : Combination 1 and 2 of H2O and CO2 (6 each) ! (3)" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.COMBINATOR_2.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_SOLAR.getIndex()).bcoef( 1 )
				.cchem(SomeChemicals.FRUCTOSE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination2(H2O, CO2) + 'Solar NRJ' gives Fructose !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.COMBINATOR_2.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_HEAT.getIndex()).bcoef( 3 )
				.cchem(SomeChemicals.FRUCTOSE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination2(H2O, CO2) + 'Heat NRJ' gives Fructose !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.COMBINATOR_1.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_SOLAR.getIndex()).bcoef( 1 )
				.cchem(SomeChemicals.GLUCOSE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination1(H2O, CO2) + 'Solar NRJ' gives Glucose !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.COMBINATOR_1.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_HEAT.getIndex()).bcoef( 3 )
				.cchem(SomeChemicals.GLUCOSE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination1(H2O, CO2) + 'Heat NRJ' gives Glucose !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.STARCH.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_SOLAR.getIndex()).bcoef( 5 )
				.cchem(DecisionType.MAKE_GAMET.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.STARCH.getIndex()).dcoef( 1 )
				.kmvm( 3 )
			.name( "BiochemicalReaction : STARCH + NRJ SOLAR => MAKE_GAMET !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.STARCH.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_HEAT.getIndex()).bcoef( 5 )
				.cchem(DecisionType.MATE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.STARCH.getIndex()).dcoef( 1 )
				.kmvm( 1 )
			.name( "BiochemicalReaction : STARCH + NRJ HEAT => MATE !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(StateType.PREGNANT.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_SOLAR.getIndex()).bcoef( 5 )
				.cchem(DecisionType.LAY_EGG.getIndex()).ccoef( 1 )
				.dchem(StateType.PREGNANT.getIndex()).dcoef( 1 )
				.kmvm( 1 )
			.name( "BiochemicalReaction : PREGNANT + NRJ SOLAR => LAY_EGG !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.FRUCTOSE.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.NEUTRAL.getIndex()).bcoef( 0 )
				.cchem(StateType.FERTILE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 1 )
			.name( "BiochemicalReaction : 1 FRUCTOSE MAKE FERTILITY !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		// ***** StimulusDecision (detection)
		chrSDdetection.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.DIOXYGEN.getIndex() )
				.varia( SomeChemicals.DIOXYGEN.getIndex() )
				.value( 20 ).script( DecisionType.EMIT.getIndex() )
			.name( "StimulusDecision EMIT DIOXYGEN" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrSDdetection.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.CARBON_DIOXYDE.getIndex() )
				.varia( SomeChemicals.CARBON_DIOXYDE.getIndex() )
				.value( 20 ).script( DecisionType.RECEIVE.getIndex() )
			.name( "StimulusDecision RECEIVE CARBON_DIOXYDE" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrSDdetection.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.WATER.getIndex() )
				.varia( SomeChemicals.WATER.getIndex() )
				.value( 20 ).script( DecisionType.RECEIVE.getIndex() )
			.name( "StimulusDecision RECEIVE WATER" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrSDdetection.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.ENERGY_HEAT.getIndex() )
				.varia( SomeChemicals.ENERGY_HEAT.getIndex() )
				.value( 10 ).script( DecisionType.RECEIVE.getIndex() )
			.name( "StimulusDecision RECEIVE ENERGY_HEAT" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrSDdetection.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.ENERGY_SOLAR.getIndex() )
				.varia( SomeChemicals.ENERGY_SOLAR.getIndex() )
				.value( 15 ).script( DecisionType.RECEIVE.getIndex() )
			.name( "StimulusDecision RECEIVE ENERGY_SOLAR" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		
		// ***** Instantiate Plant and Organism With Genome !!
		Plant testPlant = new Plant( Arrays.asList( chrBiochemical, chrSDdetection ) );
		Assertions.assertNotNull( testPlant );
		Assertions.assertEquals(2, testPlant.getGenome().size());

		Assertions.assertEquals(chrBiochemical.length(), 	testPlant.getGenome().get( 0 ).length());
		Assertions.assertEquals(chrSDdetection.length(), 	testPlant.getGenome().get( 1 ).length());
		
		Assertions.assertEquals( 21, testPlant.getGenome().get( 0 ).length());
		Assertions.assertEquals(  5, testPlant.getGenome().get( 1 ).length());
		
		Integer genesNumber = ReproductionHelper.sizeOfGenome( testPlant );
		Logger.printlnLog(LoggerLevel.LL_DEBUG, genesNumber.toString());
		Assertions.assertEquals( 26, genesNumber.intValue() );
		
		List<Integer> listLengthGenomes = testPlant.getGenome().stream().map( Chromosome::length ).collect(Collectors.toList());
		Assertions.assertEquals( 26, listLengthGenomes.stream().reduce(0, Integer::sum).intValue());
		
		testPlant.setRank("Rank Test");
		testPlant.setNameCommon("Test Starting Plant");
		testPlant.setNameBiosilico("PlantHill Plant Example");
		testPlant.setDivision("TESTS");
		
		// ***** ***** ***** ***** ***** ***** ***** ***** ***** 
		// ***** ***** ***** ***** ***** ***** ***** ***** ***** 
		// ***** test with a World and WorldCase
		
		World2D w			= new World2D(3, 3);
		World2DCase wc	= w.getWorldCase(1, 1);
		Assertions.assertNotNull( wc );
		
		testPlant.setCurrentWorldCase( wc );

		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		// ***** one execution in this context -- 1
		w.execution();
		testPlant.cyclePlusPlus(); // to permit correct initialization of "starting genes"
		
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals(  7, testPlant.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals(  4, testPlant.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 28, testPlant.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals(  5, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals( 20, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals( 10, testPlant.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  1, testPlant.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		// ***** one execution in this context -- 2
		w.execution();
		testPlant.cyclePlusPlus(); // to permit correct initialization of "starting genes"
		
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals(  7, testPlant.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals(  3, testPlant.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 28, testPlant.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals( 15, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals( 10, testPlant.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  2, testPlant.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		// ***** one execution in this context -- 3
		w.execution();
		testPlant.cyclePlusPlus(); // to permit correct initialization of "starting genes"
		
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals(  7, testPlant.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals(  2, testPlant.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 28, testPlant.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals( 10, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals( 10, testPlant.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  3, testPlant.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		// ***** Export Ant as a TXT file !
		BuildingGenomeHelper.exportAntAsTXTfile("TestPlantBiochemicalReactionGenomeWithEnergies01.txt", testPlant);
		
	}

	@Test
	void testPlantBiochemicalReactionGenomeWithEnergies02() {
		Chromosome chrBiochemical			= new Chromosome();
		Chromosome chrSDdetection			= new Chromosome();
		
		chrBiochemical.setName("Basic Biochemicals");
		chrSDdetection.setName("Emit / Receive some Chemicals");
		
		// ***** Only InitialConcentration && StimulusDecision && BiochemicalReaction
		InitialConcentrationBuilder icb		= new InitialConcentrationBuilder();
		BiochemicalReactionBuilder brb		= new BiochemicalReactionBuilder();
		StimulusDecisionBuilder sdgb		= new StimulusDecisionBuilder();
		
		// ***** InitialConcentration
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.GLUCOSE.getIndex() ).value( 25 )
				.name( "InitConc GLUCOSE 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.STARCH.getIndex() ).value( 25 )
				.name( "InitConc STARCH 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.CARBON_DIOXYDE.getIndex() ).value( 25 )
				.name( "InitConc CARBON_DIOXYDE 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.WATER.getIndex() ).value( 25 )
				.name( "InitConc WATER 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.ENERGY_HEAT.getIndex() ).value( 25 )
				.name( "InitConc ENERGY_HEAT 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.ENERGY_SOLAR.getIndex() ).value( 25 )
				.name( "InitConc ENERGY_SOLAR 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		// ***** BiochemicalReaction
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.GLUCOSE.getIndex()).acoef( 6 )
				.bchem(SomeChemicals.NEUTRAL.getIndex()).bcoef( 0 )
				.cchem(SomeChemicals.STARCH.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : 6 GLUCOSE to 1 STARCH !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.STARCH.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.NEUTRAL.getIndex()).bcoef( 0 )
				.cchem(SomeChemicals.GLUCOSE.getIndex()).ccoef( 6 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 1 )
			.name( "BiochemicalReaction : 1 STARCH to 6 GLUCOSE !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.CARBON_DIOXYDE.getIndex()).acoef( 5 )
				.bchem(SomeChemicals.WATER.getIndex()).bcoef( 5 )
				.cchem(SomeChemicals.COMBINATOR_2.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination 2 of H2O and CO2 (5 each) !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.CARBON_DIOXYDE.getIndex()).acoef( 6 )
				.bchem(SomeChemicals.WATER.getIndex()).bcoef( 6 )
				.cchem(SomeChemicals.COMBINATOR_1.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination 1 of H2O and CO2 (6 each) !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.CARBON_DIOXYDE.getIndex()).acoef( 5 )
				.bchem(SomeChemicals.WATER.getIndex()).bcoef( 5 )
				.cchem(SomeChemicals.COMBINATOR_2.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 20 )
			.name( "BiochemicalReaction : Combination 2 of H2O and CO2 (5 each) ! (2)" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.CARBON_DIOXYDE.getIndex()).acoef( 6 )
				.bchem(SomeChemicals.WATER.getIndex()).bcoef( 6 )
				.cchem(SomeChemicals.COMBINATOR_1.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 20 )
			.name( "BiochemicalReaction : Combination 1 of H2O and CO2 (6 each) ! (2)" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.CARBON_DIOXYDE.getIndex()).acoef( 11 )
				.bchem(SomeChemicals.WATER.getIndex()).bcoef( 11 )
				.cchem(SomeChemicals.COMBINATOR_1.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.COMBINATOR_2.getIndex()).dcoef( 1 )
				.kmvm( 5 )
			.name( "BiochemicalReaction : Combination 1 and 2 of H2O and CO2 (6 each) ! (3)" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.COMBINATOR_2.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_SOLAR.getIndex()).bcoef( 1 )
				.cchem(SomeChemicals.FRUCTOSE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination2(H2O, CO2) + 'Solar NRJ' gives Fructose !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.COMBINATOR_2.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_HEAT.getIndex()).bcoef( 3 )
				.cchem(SomeChemicals.FRUCTOSE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination2(H2O, CO2) + 'Heat NRJ' gives Fructose !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.COMBINATOR_1.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_SOLAR.getIndex()).bcoef( 1 )
				.cchem(SomeChemicals.GLUCOSE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination1(H2O, CO2) + 'Solar NRJ' gives Glucose !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.COMBINATOR_1.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_HEAT.getIndex()).bcoef( 3 )
				.cchem(SomeChemicals.GLUCOSE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination1(H2O, CO2) + 'Heat NRJ' gives Glucose !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.STARCH.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_SOLAR.getIndex()).bcoef( 5 )
				.cchem(DecisionType.MAKE_GAMET.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.STARCH.getIndex()).dcoef( 1 )
				.kmvm( 3 )
			.name( "BiochemicalReaction : STARCH + NRJ SOLAR => MAKE_GAMET !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.STARCH.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_HEAT.getIndex()).bcoef( 5 )
				.cchem(DecisionType.MATE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.STARCH.getIndex()).dcoef( 1 )
				.kmvm( 1 )
			.name( "BiochemicalReaction : STARCH + NRJ HEAT => MATE !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(StateType.PREGNANT.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_SOLAR.getIndex()).bcoef( 5 )
				.cchem(DecisionType.LAY_EGG.getIndex()).ccoef( 1 )
				.dchem(StateType.PREGNANT.getIndex()).dcoef( 1 )
				.kmvm( 1 )
			.name( "BiochemicalReaction : PREGNANT + NRJ SOLAR => LAY_EGG !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.FRUCTOSE.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.NEUTRAL.getIndex()).bcoef( 0 )
				.cchem(StateType.FERTILE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 1 )
			.name( "BiochemicalReaction : 1 FRUCTOSE MAKE FERTILITY !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		// ***** StimulusDecision (detection)
		chrSDdetection.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.DIOXYGEN.getIndex() )
				.varia( SomeChemicals.DIOXYGEN.getIndex() )
				.value( 20 ).script( DecisionType.EMIT.getIndex() )
			.name( "StimulusDecision EMIT DIOXYGEN" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrSDdetection.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.CARBON_DIOXYDE.getIndex() )
				.varia( SomeChemicals.CARBON_DIOXYDE.getIndex() )
				.value( 20 ).script( DecisionType.RECEIVE.getIndex() )
			.name( "StimulusDecision RECEIVE CARBON_DIOXYDE" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrSDdetection.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.WATER.getIndex() )
				.varia( SomeChemicals.WATER.getIndex() )
				.value( 20 ).script( DecisionType.RECEIVE.getIndex() )
			.name( "StimulusDecision RECEIVE WATER" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrSDdetection.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.ENERGY_HEAT.getIndex() )
				.varia( SomeChemicals.ENERGY_HEAT.getIndex() )
				.value( 10 ).script( DecisionType.RECEIVE.getIndex() )
			.name( "StimulusDecision RECEIVE ENERGY_HEAT" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrSDdetection.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.ENERGY_SOLAR.getIndex() )
				.varia( SomeChemicals.ENERGY_SOLAR.getIndex() )
				.value( 15 ).script( DecisionType.RECEIVE.getIndex() )
			.name( "StimulusDecision RECEIVE ENERGY_SOLAR" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		
		// ***** Instantiate Plant and Organism With Genome !!
		Plant testPlant = new Plant( Arrays.asList( chrBiochemical, chrSDdetection ) );
		Assertions.assertNotNull( testPlant );
		Assertions.assertEquals(2, testPlant.getGenome().size());

		Assertions.assertEquals(chrBiochemical.length(), 	testPlant.getGenome().get( 0 ).length());
		Assertions.assertEquals(chrSDdetection.length(), 	testPlant.getGenome().get( 1 ).length());
		
		Assertions.assertEquals( 21, testPlant.getGenome().get( 0 ).length());
		Assertions.assertEquals(  5, testPlant.getGenome().get( 1 ).length());
		
		Integer genesNumber = ReproductionHelper.sizeOfGenome( testPlant );
		Logger.printlnLog(LoggerLevel.LL_DEBUG, genesNumber.toString());
		Assertions.assertEquals( 26, genesNumber.intValue() );
		
		List<Integer> listLengthGenomes = testPlant.getGenome().stream().map( Chromosome::length ).collect(Collectors.toList());
		Assertions.assertEquals( 26, listLengthGenomes.stream().reduce(0, Integer::sum).intValue());
		
		testPlant.setRank("Rank Test");
		testPlant.setNameCommon("Test Starting Plant");
		testPlant.setNameBiosilico("PlantHill Plant Example");
		testPlant.setDivision("TESTS");
		
		// ***** ***** ***** ***** ***** ***** ***** ***** ***** 
		// ***** ***** ***** ***** ***** ***** ***** ***** ***** 
		// ***** test with a World and WorldCase
		
		World2D w			= new World2D(3, 3);
		World2DCase wc	= w.getWorldCase(1, 1);
		Assertions.assertNotNull( wc );
		
		wc.addAgent( new EnergySource() );
		
		testPlant.setCurrentWorldCase( wc );

		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		// ***** one execution in this context -- 1
		w.execution();
		testPlant.cyclePlusPlus(); // to permit correct initialization of "starting genes"
		
		Assertions.assertEquals( 10, wc.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals( 15, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals(  7, testPlant.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals(  4, testPlant.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 28, testPlant.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals( 20, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals( 30, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals( 10, testPlant.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  1, testPlant.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		// ***** one execution in this context -- 2
		w.execution();
		testPlant.cyclePlusPlus(); // to permit correct initialization of "starting genes"
		
		Assertions.assertEquals( 20, wc.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals( 30, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals(  7, testPlant.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals(  3, testPlant.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 28, testPlant.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals( 20, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals( 35, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals( 10, testPlant.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  2, testPlant.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		// ***** one execution in this context -- 3
		w.execution();
		testPlant.cyclePlusPlus(); // to permit correct initialization of "starting genes"
		
		Assertions.assertEquals( 30, wc.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals( 45, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals(  7, testPlant.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals(  2, testPlant.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 28, testPlant.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals( 20, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals( 40, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals( 10, testPlant.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  3, testPlant.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		// ***** one execution in this context -- 4
		w.execution();
		testPlant.cyclePlusPlus(); // to permit correct initialization of "starting genes"
		
		Assertions.assertEquals( 40, wc.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals( 60, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals(  7, testPlant.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals(  1, testPlant.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 28, testPlant.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals( 20, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals( 45, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals( 10, testPlant.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  4, testPlant.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		// ***** one execution in this context -- 5
		w.execution();
		testPlant.cyclePlusPlus(); // to permit correct initialization of "starting genes"
		
		Assertions.assertEquals( 50, wc.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals( 75, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals(  7, testPlant.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 28, testPlant.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals( 20, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals( 50, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals( 10, testPlant.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  5, testPlant.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		// ***** Export Ant as a TXT file !
		BuildingGenomeHelper.exportAntAsTXTfile("TestPlantBiochemicalReactionGenomeWithEnergies02.txt", testPlant);
		
	}

	@Test
	void testPlantBiochemicalReactionGenomeWithEnergies03() {
		Chromosome chrBiochemical			= new Chromosome();
		Chromosome chrSDdetection			= new Chromosome();
		
		chrBiochemical.setName("Basic Biochemicals");
		chrSDdetection.setName("Emit / Receive some Chemicals");
		
		// ***** Only InitialConcentration && StimulusDecision && BiochemicalReaction
		InitialConcentrationBuilder icb		= new InitialConcentrationBuilder();
		BiochemicalReactionBuilder brb		= new BiochemicalReactionBuilder();
		StimulusDecisionBuilder sdgb		= new StimulusDecisionBuilder();
		
		// ***** InitialConcentration
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.GLUCOSE.getIndex() ).value( 25 )
				.name( "InitConc GLUCOSE 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.STARCH.getIndex() ).value( 25 )
				.name( "InitConc STARCH 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.CARBON_DIOXYDE.getIndex() ).value( 25 )
				.name( "InitConc CARBON_DIOXYDE 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.WATER.getIndex() ).value( 25 )
				.name( "InitConc WATER 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.ENERGY_HEAT.getIndex() ).value( 25 )
				.name( "InitConc ENERGY_HEAT 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrBiochemical.addGene( icb	
				.varia( SomeChemicals.ENERGY_SOLAR.getIndex() ).value( 25 )
				.name( "InitConc ENERGY_SOLAR 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		// ***** BiochemicalReaction
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.GLUCOSE.getIndex()).acoef( 6 )
				.bchem(SomeChemicals.NEUTRAL.getIndex()).bcoef( 0 )
				.cchem(SomeChemicals.STARCH.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : 6 GLUCOSE to 1 STARCH !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.STARCH.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.NEUTRAL.getIndex()).bcoef( 0 )
				.cchem(SomeChemicals.GLUCOSE.getIndex()).ccoef( 6 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 1 )
			.name( "BiochemicalReaction : 1 STARCH to 6 GLUCOSE !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.CARBON_DIOXYDE.getIndex()).acoef( 5 )
				.bchem(SomeChemicals.WATER.getIndex()).bcoef( 5 )
				.cchem(SomeChemicals.COMBINATOR_2.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination 2 of H2O and CO2 (5 each) !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.CARBON_DIOXYDE.getIndex()).acoef( 6 )
				.bchem(SomeChemicals.WATER.getIndex()).bcoef( 6 )
				.cchem(SomeChemicals.COMBINATOR_1.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination 1 of H2O and CO2 (6 each) !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.CARBON_DIOXYDE.getIndex()).acoef( 5 )
				.bchem(SomeChemicals.WATER.getIndex()).bcoef( 5 )
				.cchem(SomeChemicals.COMBINATOR_2.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 20 )
			.name( "BiochemicalReaction : Combination 2 of H2O and CO2 (5 each) ! (2)" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.CARBON_DIOXYDE.getIndex()).acoef( 6 )
				.bchem(SomeChemicals.WATER.getIndex()).bcoef( 6 )
				.cchem(SomeChemicals.COMBINATOR_1.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 20 )
			.name( "BiochemicalReaction : Combination 1 of H2O and CO2 (6 each) ! (2)" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.CARBON_DIOXYDE.getIndex()).acoef( 11 )
				.bchem(SomeChemicals.WATER.getIndex()).bcoef( 11 )
				.cchem(SomeChemicals.COMBINATOR_1.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.COMBINATOR_2.getIndex()).dcoef( 1 )
				.kmvm( 5 )
			.name( "BiochemicalReaction : Combination 1 and 2 of H2O and CO2 (6 each) ! (3)" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.COMBINATOR_2.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_SOLAR.getIndex()).bcoef( 1 )
				.cchem(SomeChemicals.FRUCTOSE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination2(H2O, CO2) + 'Solar NRJ' gives Fructose !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.COMBINATOR_2.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_HEAT.getIndex()).bcoef( 3 )
				.cchem(SomeChemicals.FRUCTOSE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination2(H2O, CO2) + 'Heat NRJ' gives Fructose !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.COMBINATOR_1.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_SOLAR.getIndex()).bcoef( 1 )
				.cchem(SomeChemicals.GLUCOSE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination1(H2O, CO2) + 'Solar NRJ' gives Glucose !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.COMBINATOR_1.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_HEAT.getIndex()).bcoef( 3 )
				.cchem(SomeChemicals.GLUCOSE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.DIOXYGEN.getIndex()).dcoef( 1 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : Combination1(H2O, CO2) + 'Heat NRJ' gives Glucose !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.STARCH.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_SOLAR.getIndex()).bcoef( 5 )
				.cchem(DecisionType.MAKE_GAMET.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.STARCH.getIndex()).dcoef( 1 )
				.kmvm( 3 )
			.name( "BiochemicalReaction : STARCH + NRJ SOLAR => MAKE_GAMET !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.STARCH.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_HEAT.getIndex()).bcoef( 5 )
				.cchem(DecisionType.MATE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.STARCH.getIndex()).dcoef( 1 )
				.kmvm( 1 )
			.name( "BiochemicalReaction : STARCH + NRJ HEAT => MATE !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(StateType.PREGNANT.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_SOLAR.getIndex()).bcoef( 5 )
				.cchem(DecisionType.LAY_EGG.getIndex()).ccoef( 1 )
				.dchem(StateType.PREGNANT.getIndex()).dcoef( 1 )
				.kmvm( 1 )
			.name( "BiochemicalReaction : PREGNANT + NRJ SOLAR => LAY_EGG !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrBiochemical.addGene( brb
				.achem(SomeChemicals.FRUCTOSE.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.NEUTRAL.getIndex()).bcoef( 0 )
				.cchem(StateType.FERTILE.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 1 )
			.name( "BiochemicalReaction : 1 FRUCTOSE MAKE FERTILITY !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		// ***** StimulusDecision (detection)
		chrSDdetection.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.DIOXYGEN.getIndex() )
				.varia( SomeChemicals.DIOXYGEN.getIndex() )
				.value( 20 ).script( DecisionType.EMIT.getIndex() )
			.name( "StimulusDecision EMIT DIOXYGEN" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrSDdetection.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.CARBON_DIOXYDE.getIndex() )
				.varia( SomeChemicals.CARBON_DIOXYDE.getIndex() )
				.value( 20 ).script( DecisionType.RECEIVE.getIndex() )
			.name( "StimulusDecision RECEIVE CARBON_DIOXYDE" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrSDdetection.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.WATER.getIndex() )
				.varia( SomeChemicals.WATER.getIndex() )
				.value( 20 ).script( DecisionType.RECEIVE.getIndex() )
			.name( "StimulusDecision RECEIVE WATER" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrSDdetection.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.ENERGY_HEAT.getIndex() )
				.varia( SomeChemicals.ENERGY_HEAT.getIndex() )
				.value( 10 ).script( DecisionType.RECEIVE.getIndex() )
			.name( "StimulusDecision RECEIVE ENERGY_HEAT" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrSDdetection.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.ENERGY_SOLAR.getIndex() )
				.varia( SomeChemicals.ENERGY_SOLAR.getIndex() )
				.value( 15 ).script( DecisionType.RECEIVE.getIndex() )
			.name( "StimulusDecision RECEIVE ENERGY_SOLAR" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		
		// ***** Instantiate Plant and Organism With Genome !!
		Plant testPlant = new Plant( Arrays.asList( chrBiochemical, chrSDdetection ) );
		Assertions.assertNotNull( testPlant );
		Assertions.assertEquals(2, testPlant.getGenome().size());

		Assertions.assertEquals(chrBiochemical.length(), 	testPlant.getGenome().get( 0 ).length());
		Assertions.assertEquals(chrSDdetection.length(), 	testPlant.getGenome().get( 1 ).length());
		
		Assertions.assertEquals( 21, testPlant.getGenome().get( 0 ).length());
		Assertions.assertEquals(  5, testPlant.getGenome().get( 1 ).length());
		
		Integer genesNumber = ReproductionHelper.sizeOfGenome( testPlant );
		Logger.printlnLog(LoggerLevel.LL_DEBUG, genesNumber.toString());
		Assertions.assertEquals( 26, genesNumber.intValue() );
		
		List<Integer> listLengthGenomes = testPlant.getGenome().stream().map( Chromosome::length ).collect(Collectors.toList());
		Assertions.assertEquals( 26, listLengthGenomes.stream().reduce(0, Integer::sum).intValue());
		
		testPlant.setRank("Rank Test");
		testPlant.setNameCommon("Test Starting Plant");
		testPlant.setNameBiosilico("PlantHill Plant Example");
		testPlant.setDivision("TESTS");
		
		// ***** ***** ***** ***** ***** ***** ***** ***** ***** 
		// ***** ***** ***** ***** ***** ***** ***** ***** ***** 
		// ***** test with a World and WorldCase
		
		World2D w			= new World2D(3, 3);
		World2DCase wc	= w.getWorldCase(1, 1);
		Assertions.assertNotNull( wc );
		
		wc.addAgent( new EnergySource() );
		wc.addAgent( new Condensator() );
		
		testPlant.setCurrentWorldCase( wc );

		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		// ***** one execution in this context -- 1
		w.execution();
		testPlant.cyclePlusPlus(); // to permit correct initialization of "starting genes"
		
		Assertions.assertEquals( 10, wc.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals( 15, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  5, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  5, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals(  7, testPlant.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals(  4, testPlant.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 28, testPlant.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals( 20, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals( 30, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 20, testPlant.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 20, testPlant.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals( 10, testPlant.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  1, testPlant.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		// ***** one execution in this context -- 2
		w.execution();
		testPlant.cyclePlusPlus(); // to permit correct initialization of "starting genes"
		
		Assertions.assertEquals( 20, wc.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals( 30, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 10, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals( 10, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals(  7, testPlant.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals(  7, testPlant.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 28, testPlant.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals( 16, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals( 35, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 20, testPlant.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 20, testPlant.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals( 18, testPlant.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  2, testPlant.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		// ***** one execution in this context -- 3
		w.execution();
		testPlant.cyclePlusPlus(); // to permit correct initialization of "starting genes"
		
		Assertions.assertEquals( 30, wc.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals( 45, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 15, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 20, wc.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals( 15, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals(  7, testPlant.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals( 10, testPlant.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 28, testPlant.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals( 17, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals( 40, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 20, testPlant.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 20, testPlant.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals(  6, testPlant.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  3, testPlant.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		// ***** one execution in this context -- 4
		w.execution();
		testPlant.cyclePlusPlus(); // to permit correct initialization of "starting genes"
		
		Assertions.assertEquals( 40, wc.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals( 60, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 20, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 20, wc.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals( 20, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals(  7, testPlant.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals( 13, testPlant.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 28, testPlant.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals( 18, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals( 45, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 20, testPlant.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 20, testPlant.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals( 14, testPlant.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  4, testPlant.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		// ***** one execution in this context -- 5
		w.execution();
		testPlant.cyclePlusPlus(); // to permit correct initialization of "starting genes"
		
		Assertions.assertEquals( 50, wc.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals( 75, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 25, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 40, wc.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals( 25, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals(  7, testPlant.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals( 16, testPlant.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 28, testPlant.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals( 19, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex()) );
		Assertions.assertEquals( 50, testPlant.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 20, testPlant.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 20, testPlant.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals(  2, testPlant.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testPlant.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  5, testPlant.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		BuildingGenomeHelper.show(testPlant, wc);
		
		// ***** Export Ant as a TXT file !
		BuildingGenomeHelper.exportAntAsTXTfile("TestPlantBiochemicalReactionGenomeWithEnergies03.txt", testPlant);
		
	}

	
}

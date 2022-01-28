package gabywald.biosilico.anthill.tests;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.anthill.Ant;
import gabywald.biosilico.anthill.GeneratorReceptionChemicals;
import gabywald.biosilico.anthill.launcher.AntHillExampleHelper;
import gabywald.biosilico.anthill.launcher.BuildingGenomeHelper;
import gabywald.biosilico.genetics.builders.BiochemicalReactionBuilder;
import gabywald.biosilico.genetics.builders.BrainGeneBuilder;
import gabywald.biosilico.genetics.builders.BrainLobeGeneBuilder;
import gabywald.biosilico.genetics.builders.EmitterReceptorBuilder;
import gabywald.biosilico.genetics.builders.InitialConcentrationBuilder;
import gabywald.biosilico.genetics.builders.InstinctBuilder;
import gabywald.biosilico.genetics.builders.StimulusDecisionBuilder;
import gabywald.biosilico.model.Chromosome;
import gabywald.biosilico.model.chemicals.ChemicalsHelper;
import gabywald.biosilico.model.enums.AgentType;
import gabywald.biosilico.model.enums.DecisionType;
import gabywald.biosilico.model.enums.DirectionWorld;
import gabywald.biosilico.model.enums.ObjectType;
import gabywald.biosilico.model.enums.SomeChemicals;
import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.environment.World2D;
import gabywald.biosilico.model.environment.World2DCase;
import gabywald.biosilico.model.reproduction.ReproductionHelper;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020, 2022)
 */
class AntBuildingGenomeComplete {
	
	public static final String SRC_TEST_RSC			= AntHillExampleHelper.BASE_EXPORT_TEST_DIR;
	public static final String SRC_MAIN_RSC_ANTHILL	= AntHillExampleHelper.BASE_EXPORT_MAIN_DIR_ANTHILL;

	@Test
	void testAntCompleteGenome() {

		Chromosome chrBrain					= new Chromosome();
		Chromosome chrEmitReceiv			= new Chromosome();
		Chromosome chrInstinct				= new Chromosome();
		Chromosome chrSDdecision			= new Chromosome();
		Chromosome chrSDdetection			= new Chromosome();
		Chromosome chrInitialConcentrations	= new Chromosome();
		Chromosome chrBiochemicalReactions	= new Chromosome();
		
		List<DirectionWorld> selectedDirs	= DirectionWorld.values2DasList();
		List<SomeChemicals> someChems		= Arrays.asList(SomeChemicals.PHEROMONE_00, 
															SomeChemicals.PHEROMONE_01);
		List<SomeChemicals> allPheromones	= Arrays.asList(SomeChemicals.PHEROMONE_00, 
															SomeChemicals.PHEROMONE_01, SomeChemicals.PHEROMONE_02, SomeChemicals.PHEROMONE_03, 
															SomeChemicals.PHEROMONE_04, SomeChemicals.PHEROMONE_05, SomeChemicals.PHEROMONE_06, 
															SomeChemicals.PHEROMONE_07, SomeChemicals.PHEROMONE_08, SomeChemicals.PHEROMONE_09 );
		
		BrainGeneBuilder bgb				= new BrainGeneBuilder();
		BrainLobeGeneBuilder blgb			= new BrainLobeGeneBuilder();
		EmitterReceptorBuilder erb			= new EmitterReceptorBuilder();
		InitialConcentrationBuilder icb		= new InitialConcentrationBuilder();
		InstinctBuilder igb					= new InstinctBuilder();
		StimulusDecisionBuilder sdgb		= new StimulusDecisionBuilder();
		BiochemicalReactionBuilder brb		= new BiochemicalReactionBuilder();
		
		// ***** ***** ***** ***** ***** 
		// ***** Building Brain and BrainLobe Genes
		chrBrain.setName( "Brain and Connections" );
		// *** "Basic" Brain ... 
		chrBrain.addGene( bgb.heigth( 100 ).width( 100 ).depth( 1 ).more( 0 )
			.name( "Brain Gene 100*100*1*0" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 )
			.build() );
		// *** Input neurons : one group of 27 neurons => pheromones_00 directions ! (9 used for new)
		chrBrain.addGene( blgb 
				.rest( 0 ).threshold( 10 ).desc( 5 )
				.dmin( 0 ).dmax( 0 ).prox( 0 )
				.repr( false ).repy( 0 ).wta( false )
				.heigth( 1 ).width( 27 ).posx( 0 ).posy( 0 )
				.replace( false )
			.name( "Brain Lobe Gene Data Input DW Ph_00" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 )
			.build() );
		// *** Input neurons : one group of 27 neurons => pheromones_01 directions ! (9 used for new)
		chrBrain.addGene( blgb 
				.rest( 0 ).threshold( 10 ).desc( 5 )
				.dmin( 0 ).dmax( 0 ).prox( 0 )
				.repr( false ).repy( 0 ).wta( false )
				.heigth( 1 ).width( 27 ).posx( 1 ).posy( 0 )
				.replace( false )
			.name( "Brain Lobe Gene Data Input DW Ph_01" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 )
			.build() );
		// *** Input neurons : one group of 27 neurons => FOOD directions ! (9 used for new)
		chrBrain.addGene( blgb 
				.rest( 0 ).threshold( 10 ).desc( 5 )
				.dmin( 0 ).dmax( 0 ).prox( 0 )
				.repr( false ).repy( 0 ).wta( false )
				.heigth( 1 ).width( 27 ).posx( 2 ).posy( 0 )
				.replace( false )
			.name( "Brain Lobe Gene Data Input DW FOOD " )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 )
			.build() );
		
		// *** Input neurons : Internal Detection Of Pheromones
		chrBrain.addGene( blgb 
				.rest( 0 ).threshold( 10 ).desc( 2 )
				.dmin( 0 ).dmax( 0 ).prox( 0 )
				.repr( false ).repy( 0 ).wta( false )
				.heigth( 1 ).width( 16 ).posx( 3 ).posy( 0 )
				.replace( false )
			.name( "Brain Lobe Gene Data Input Pheromones" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 )
			.build() );
		
		// *** "Concept Lobe I"
		chrBrain.addGene( blgb 
				.rest( 0 ).threshold( 10 ).desc( 5 )
				.dmin( 3 ).dmax( 9 ).prox( 2 )
				.repr( false ).repy( 0 ).wta( false )
				.heigth( 10 ).width( 30 ).posx( 10 ).posy( 0 )
				.replace( false )
			.name( "Brain Lobe Gene 'Concept Lobe I' (for associations of Inputs)" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 )
			.build() );
		
		// *** "Concept Lobe II"
		chrBrain.addGene( blgb 
				.rest( 0 ).threshold( 10 ).desc( 5 )
				.dmin( 3 ).dmax( 9 ).prox( 2 )
				.repr( false ).repy( 0 ).wta( false )
				.heigth( 10 ).width( 30 ).posx( 30 ).posy( 0 )
				.replace( false )
			.name( "Brain Lobe Gene 'Concept Lobe II' (for associations of Inputs)" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 )
			.build() );
		
		// *** Output neurons : two (2) groups of 10 WTA neurons
		// // // first group : to be linked with choice / decision of directions (for now only 9 out of 27)
		// // // + MOVE_AWAY + GET(FOOD) + DROP(FOOD) + EAT(FOOD) + DEATH
		chrBrain.addGene( blgb 
				.rest( 0 ).threshold( 20 ).desc( 10 )
				.dmin( 5 ).dmax( 9 ).prox( 2 )
				.repr( false ).repy( 0 ).wta( true )
				.heigth( 1 ).width( selectedDirs.size() + 3 ).posx( 90 ).posy( 0 )
				.replace( false )
			.name( "Brain Lobe Gene Data Output 1 : directions + MOVE_AWAY + GET(FOOD) + DROP(FOOD) + EAT(FOOD) + DEATH" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 )
			.build() );
		// // // second group : to be linked with other decisions
		chrBrain.addGene( blgb 
				.rest( 0 ).threshold( 50 ).desc( 10 )
				.dmin( 5 ).dmax( 9 ).prox( 2 )
				.repr( false ).repy( 0 ).wta( true )
				.heigth( 1 ).width( 10 ).posx( 90 ).posy( 50 )
				.replace( false )
			.name( "Brain Lobe Gene Data Output 2 : other decisions / Decision Lobe" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 )
			.build() );
		
		// ***** ***** ***** ***** ***** 
		chrEmitReceiv.setName("EmitterReceptor's input / output");
		// *** EmitterReceptor Input => to first line (inputs) of Brain !! (Ph_00 & direction)
		String nameFirstPartERinput	= "ER receptor input ";
		for (DirectionWorld dw : selectedDirs) {
			GeneratorReceptionChemicals arc = GeneratorReceptionChemicals.getFrom( dw, SomeChemicals.PHEROMONE_00 );
			chrEmitReceiv.addGene( erb
					.variable( arc.getIndex() ).threshold( 5 ).ioput( 20 )
					.posx( 0 ).posy( dw.getIndex() - BuildingGenomeHelper.indexLessRemoveDirection )
					.receptor( true ).internal( true )
				.name( nameFirstPartERinput + arc.getName() )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
				.build() );
		} // END "for (DirectionWorld dw : selectedDirs)"
		// *** EmitterReceptor Input => to second line (inputs) of Brain !! (Ph_01 & direction)
		for (DirectionWorld dw : selectedDirs) {
			GeneratorReceptionChemicals arc = GeneratorReceptionChemicals.getFrom( dw, SomeChemicals.PHEROMONE_01 );
			chrEmitReceiv.addGene( erb
					.variable( arc.getIndex() ).threshold( 5 ).ioput( 20 )
					.posx( 1 ).posy( dw.getIndex() - BuildingGenomeHelper.indexLessRemoveDirection )
					.receptor( true ).internal( true )
				.name( nameFirstPartERinput + arc.getName() )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
				.build() );
		} // END "for (DirectionWorld dw : selectedDirs)"
		// *** EmitterReceptor Input => to third line (inputs) of Brain !! (FOOD & direction)
		for (DirectionWorld dw : selectedDirs) {
			GeneratorReceptionChemicals arc = GeneratorReceptionChemicals.getFrom( dw, ObjectType.FOOD );
			chrEmitReceiv.addGene( erb
					.variable( arc.getIndex() ).threshold( 5 ).ioput( 20 )
					.posx( 2 ).posy( dw.getIndex() - BuildingGenomeHelper.indexLessRemoveDirection )
					.receptor( true ).internal( true )
				.name( nameFirstPartERinput + arc.getName() )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
				.build() );
		} // END "for (DirectionWorld dw : selectedDirs)"
		
		// *** *** *** StimulusDecision : more impact if PHEROMONE_01 if HIGH
		GeneratorReceptionChemicals arcCurrentPh01 = GeneratorReceptionChemicals.getFrom( DirectionWorld.CURRENT, SomeChemicals.PHEROMONE_01 );
		chrEmitReceiv.addGene( erb
				.variable( arcCurrentPh01.getIndex() ).threshold(  99 ).ioput( 100 )
				.posx( 1 ).posy( 1 )
				.receptor( true ).internal( true )
			.name( nameFirstPartERinput + arcCurrentPh01.getName() + " FORCE" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		// *** *** *** StimulusDecision : internal detection of pheromones (and others)
		// // // useful for HAS(FOOD)
		for (SomeChemicals pheromone : allPheromones) {
			chrEmitReceiv.addGene( erb
					.variable( pheromone.getIndex() ).threshold( 5 ).ioput( 15 )
					.posx( 3 ).posy( pheromone.getIndex() - BuildingGenomeHelper.indexLessRemovePheromone )
					.receptor( true ).internal( true )
				.name( nameFirstPartERinput + pheromone.getName() )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
				.build() );
		} // END "for (SomeChemicals scPhero : allPheromones)"
		
		chrEmitReceiv.addGene( erb
				.variable( SomeChemicals.GLUCOSE.getIndex() ).threshold( 5 ).ioput( 15 )
				.posx( 3 ).posy( allPheromones.size() + 0 )
				.receptor( true ).internal( true )
			.name( nameFirstPartERinput + "GLUCOSE input ")
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrEmitReceiv.addGene( erb
				.variable( SomeChemicals.FRUCTOSE.getIndex() ).threshold( 5 ).ioput( 15 )
				.posx( 3 ).posy( allPheromones.size() + 1 )
				.receptor( true ).internal( true )
			.name( nameFirstPartERinput + "FRUCTOSE input ")
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		// *** EmitterReceptorOutput
		String nameFirstPartERoutput	= "ER emitter output ";
		for (DirectionWorld dw : selectedDirs) {
			// AntEmissionChemicals aec = AntEmissionChemicals.getFrom( dw, SomeChemicals.PHEROMONE_00 );
			chrEmitReceiv.addGene( erb
					.variable( dw.getIndex() ).threshold( 5 ).ioput( 20 )
					.posx( 90 ).posy( dw.getIndex() - BuildingGenomeHelper.indexLessRemoveDirection )
					.receptor( false ).internal( true )
				.name( nameFirstPartERoutput + dw.getName() )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
				.build() );
		} // END "for (DirectionWorld dw : selectedDirs)"
		
		// ER MOVE_AWAY ... 
		final int outputPosYMOVEAWAY = selectedDirs.size() + 0;
		chrEmitReceiv.addGene( erb
				.variable( DecisionType.MOVE_AWAY.getIndex() ).threshold( 10 ).ioput( 10 )
				.posx( 90 ).posy( outputPosYMOVEAWAY )
				.receptor( false ).internal( true )
			.name( nameFirstPartERoutput + DecisionType.MOVE_AWAY.getName() )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		// ER GET FOOD ... 
		final int outputPosYGETFOOD = selectedDirs.size() + 1;
		chrEmitReceiv.addGene( erb
				.variable( DecisionType.GET.getIndex() ).threshold( 10 ).ioput( 10 )
				.posx( 90 ).posy( outputPosYGETFOOD )
				.receptor( false ).internal( true )
			.name( nameFirstPartERoutput + DecisionType.GET.getName() + " FOOD" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		// ER DROP FOOD ... 
		final int outputPosYDROPFOOD = selectedDirs.size() + 2;
		chrEmitReceiv.addGene( erb
				.variable( DecisionType.DROP.getIndex() ).threshold( 10 ).ioput( 10 )
				.posx( 90 ).posy( outputPosYDROPFOOD )
				.receptor( false ).internal( true )
			.name( nameFirstPartERoutput + DecisionType.DROP.getName() + " FOOD" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		// ER DROP FOOD ... 
		final int outputPosYEATFOOD = selectedDirs.size() + 3;
		chrEmitReceiv.addGene( erb
				.variable( DecisionType.EAT.getIndex() ).threshold( 10 ).ioput( 10 )
				.posx( 90 ).posy( outputPosYEATFOOD )
				.receptor( false ).internal( true )
			.name( nameFirstPartERoutput + DecisionType.EAT.getName() + " FOOD" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		// ***** ***** ***** ***** ***** 
		chrInstinct.setName("Instinct : genetically-defined connections within Brain");
		// *** Instincts !! 
		// => TODO choose another chemical to initiate instinct ?!
		chrInstinct.addGene( icb	
				.varia( SomeChemicals.GLUCOSE.getIndex() ).value( 25 )
				.name( "InitConc GLUCOSE 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		// *** Instinct to centralize for any destinations !
		IntStream.range(0, 3).forEach( i -> {
			for (DirectionWorld dw : selectedDirs) {
				int neuronYIndex = dw.getIndex() - BuildingGenomeHelper.indexLessRemoveDirection;
				chrInstinct.addGene( igb
					.inputPosX( i ).inputPosY( neuronYIndex )
					.outputPosX( 90 ).outputPosY( neuronYIndex )
					.weight( 10 ).variable( SomeChemicals.GLUCOSE.getIndex() )
					.threshold( 5 ).check( false ).positiv( true )
					.name( "Instinct (" + i + ", " + neuronYIndex + ") to (90, " + neuronYIndex + ") " 
								+ dw.getName() + " [" + SomeChemicals.GLUCOSE.getName() + "]")
						.mutate( true ).duplicate( true ).delete( true ).activ( true )
						.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 )
						.build() );
			} // END "for (DirectionWorld dw : selectedDirs)"
		});
		// Linked instinct for MOVE_AWAY
		chrInstinct.addGene( igb
				.inputPosX( 3 ).inputPosY( SomeChemicals.PHEROMONE_00.getIndex() - BuildingGenomeHelper.indexLessRemovePheromone )
				.outputPosX( 90 ).outputPosY( outputPosYMOVEAWAY )
				.weight(  5 ).variable( SomeChemicals.GLUCOSE.getIndex() )
				.threshold( 5 ).check( false ).positiv( true )
				.name( "Instinct (" + 0 + ", " + 0 + ") to (90, " + outputPosYMOVEAWAY + ") " 
							+ "INT PHER01 => MOVE_AWAY" + " [" + SomeChemicals.GLUCOSE.getName() + "]")
					.mutate( true ).duplicate( true ).delete( true ).activ( true )
					.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 )
					.build() );
		// // // negative impact for MOVE_AWAY
		IntStream.range(0, 3).forEach( i -> {
			for (DirectionWorld dw : selectedDirs) {
				int neuronYIndex = dw.getIndex() - BuildingGenomeHelper.indexLessRemoveDirection;
				chrInstinct.addGene( igb
					.inputPosX( i ).inputPosY( neuronYIndex )
					.outputPosX( 90 ).outputPosY( outputPosYMOVEAWAY )
					.weight( 1 ).variable( SomeChemicals.GLUCOSE.getIndex() )
					.threshold( 5 ).check( false ).positiv( false )
					.name( "Instinct (" + i + ", " + neuronYIndex + ") to (90, " + outputPosYMOVEAWAY + ") " 
								+ dw.getName() + " => MOVE_AWAY [" + SomeChemicals.GLUCOSE.getName() + "] (negative)")
						.mutate( true ).duplicate( true ).delete( true ).activ( true )
						.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 )
						.build() );
			} // END "for (DirectionWorld dw : selectedDirs)"
		});
		// Linked instinct for GET(FOOD) : if FOOD in CURRENT
		chrInstinct.addGene( igb
				.inputPosX( 0 ).inputPosY( 0 )
				.outputPosX( 90 ).outputPosY( outputPosYGETFOOD )
				.weight( 10 ).variable( SomeChemicals.GLUCOSE.getIndex() )
				.threshold( 5 ).check( false ).positiv( true )
				.name( "Instinct (" + 0 + ", " + 0 + ") to (90, " + outputPosYGETFOOD + ") " 
							+ "GET(FOOD)" + " [" + SomeChemicals.GLUCOSE.getName() + "]")
					.mutate( true ).duplicate( true ).delete( true ).activ( true )
					.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 )
					.build() );
		// Linked instinct for DROP(FOOD) : HAS(FOOD) + if PHEROMONE_01 is detected enough
		chrInstinct.addGene( igb
				.inputPosX( 1 ).inputPosY( 1 )
				.outputPosX( 90 ).outputPosY( outputPosYDROPFOOD )
				.weight( 10 ).variable( SomeChemicals.GLUCOSE.getIndex() )
				.threshold( 5 ).check( false ).positiv( true )
				.name( "Instinct (" + 1 + ", " + 1 + ") to (90, " + outputPosYDROPFOOD + ") " 
							+ "LLL PHER01 => DROP(FOOD)" + " [" + SomeChemicals.GLUCOSE.getName() + "]")
					.mutate( true ).duplicate( true ).delete( true ).activ( true )
					.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 )
					.build() );
		chrInstinct.addGene( igb
				.inputPosX( 3 ).inputPosY( SomeChemicals.PHEROMONE_01.getIndex() - BuildingGenomeHelper.indexLessRemovePheromone )
				.outputPosX( 90 ).outputPosY( outputPosYDROPFOOD )
				.weight( 10 ).variable( SomeChemicals.GLUCOSE.getIndex() )
				.threshold( 5 ).check( false ).positiv( true )
				.name( "Instinct (" + 3 + ", " + (SomeChemicals.PHEROMONE_01.getIndex() - BuildingGenomeHelper.indexLessRemovePheromone) + ") to (90, " + outputPosYDROPFOOD + ") " 
							+ "INT PHER01 => HAS(FOOD)" + " [" + SomeChemicals.GLUCOSE.getName() + "]")
					.mutate( true ).duplicate( true ).delete( true ).activ( true )
					.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 )
					.build() );
		
		chrInstinct.addGene( igb
				.inputPosX( 3 ).inputPosY( allPheromones.size() + 0 )
				.outputPosX( 90 ).outputPosY( outputPosYEATFOOD )
				.weight( 1 ).variable( SomeChemicals.GLUCOSE.getIndex() )
				.threshold( 5 ).check( false ).positiv( false )
				.name( "Instinct (" + 3 + ", " + (allPheromones.size() + 0) + ") to (90, " + outputPosYEATFOOD + ") " 
							+ SomeChemicals.GLUCOSE.getName() + " => EAT(FOOD) [" + SomeChemicals.GLUCOSE.getName() + "] (negative)")
					.mutate( true ).duplicate( true ).delete( true ).activ( true )
					.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 )
					.build() );
		chrInstinct.addGene( igb
				.inputPosX( 3 ).inputPosY( allPheromones.size() + 1 )
				.outputPosX( 90 ).outputPosY( outputPosYEATFOOD )
				.weight( 1 ).variable( SomeChemicals.FRUCTOSE.getIndex() )
				.threshold( 5 ).check( false ).positiv( false )
				.name( "Instinct (" + 3 + ", " + (allPheromones.size() + 1) + ") to (90, " + outputPosYEATFOOD + ") " 
							+ SomeChemicals.FRUCTOSE.getName() + " => EAT(FOOD) [" + SomeChemicals.GLUCOSE.getName() + "] (negative)")
					.mutate( true ).duplicate( true ).delete( true ).activ( true )
					.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 )
					.build() );
		// TODO more instincts !!

		// *** StimulusDecision for Decisions !! 
		chrSDdecision.setName( "StimulusDecision : Taking and Apply Decisions" );
		// // // Directions !!
		for (DirectionWorld dw : selectedDirs) {
			// Act on object : direction !
			chrSDdecision.addGene( sdgb.perception( false ).object( true ) 
					.indicator( dw.getIndex() ) 				// to be detected
					.threshold( 10 )							// detection
					.attribute( dw.getIndex() ) 				// where to go !
					.varia( 0 ).value( 0 )		// varia && value are irrelevant here !
					.script( DecisionType.MOVE_TO.getIndex() )	// What to do !
				.name( "StimulusDecisionGene_MOVETO_" + dw.getName() )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
				.build() );
		} // END "for (DirectionWorld dw : selectedDirs) "

		// Decision MOVE_AWAY
		chrSDdecision.addGene( sdgb.perception( false ).object( true ) 
				.indicator( DecisionType.MOVE_AWAY.getIndex() ) // to be detected
				.threshold( 0 ).attribute( 0 ).varia( 0 ).value( 0 )			// attribute && varia && value are irrelevant here !
				.script( DecisionType.MOVE_AWAY.getIndex() )	// What to do !
			.name( "StimulusDecisionGene_MOVEAWAY" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		// Decision GET FOOD
		chrSDdecision.addGene( sdgb.perception( false ).object( true ) 
				.indicator( ObjectType.FOOD.getIndex() ).threshold( 0 )
				.attribute( AgentType.BIOSILICO_VIRIDITA.getIndex() )
				.varia( 0 ).value( 0 )										// varia && value are irrelevant here !
				.script( DecisionType.GET.getIndex() )						// What to do !
			.name( "StimulusDecisionGene_GET_FOOD" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		// Decision DROP FOOD
		chrSDdecision.addGene( sdgb.perception( false ).object( true ) 
				.indicator( ObjectType.FOOD.getIndex() ).threshold( 0 )
				.attribute( AgentType.BIOSILICO_VIRIDITA.getIndex() )
				.varia( 0 ).value( 0 )										// varia && value are irrelevant here !
				.script( DecisionType.DROP.getIndex() )						// What to do !
			.name( "StimulusDecisionGene_DROP_FOOD" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		// TODO EAT FOOD
		chrSDdecision.addGene( sdgb.perception( false ).object( true ) 
				.indicator( ObjectType.FOOD.getIndex() ).threshold( 0 )
				.attribute( AgentType.BIOSILICO_VIRIDITA.getIndex() )
				.varia( 0 ).value( 0 )										// varia && value are irrelevant here !
				.script( DecisionType.EAT.getIndex() )						// What to do !
			.name( "StimulusDecisionGene_EAT_FOOD" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		// TODO EMIT Pheromone_00
		// TODO EMIT Pheromone_01
		
		// TODO DEATH : which provokes DEATH + SD of decision !
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
		
		// ***** ***** ***** ***** ***** 
		chrSDdetection.setName( "StimulusDecision : Pheromones and FOOD detection !" );
		// ***** Building StimulusDecisionGene for detection !!
		for (DirectionWorld dw : selectedDirs) {
			
			for (SomeChemicals scPHE : someChems) {
				// ***** Detect Pheromone_0X at WorldCase with threshold 10, change variable 70X set to 5
				// basicChromosome.setName( scPHE.getName() + " detection !" );
				GeneratorReceptionChemicals arc = GeneratorReceptionChemicals.getFrom( dw, scPHE );
				chrSDdetection.addGene( sdgb.perception( true ).object( false ) 
						.indicator( scPHE.getIndex() ).threshold( 10 )
						.attribute( dw.getIndex() )
						.varia( arc.getIndex() )
						.value( 5 ).script( 0 )
					.name( "StimulusDecisionGene" + dw.getName() + "_" + arc.getName() )
					.mutate( true ).duplicate( true ).delete( true ).activ( true )
					.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
					.build() );
			} // END "for (SomeChemicals scPHE : someChems)"
			
			// ***** FOOD Detection !!
			GeneratorReceptionChemicals arc = GeneratorReceptionChemicals.getFrom(dw, ObjectType.FOOD);
			chrSDdetection.addGene( sdgb.perception( true ).object( true ) 
					.indicator( ObjectType.FOOD.getIndex() ).threshold( 0 )
					.attribute( dw.getIndex() )
					.varia( arc.getIndex() ).value( 5 ).script( 0 )
				.name( "StimulusDecisionGene" + arc.getName() )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
				.build() );
			
		} // END "for (DirectionWorld dw : selectedDirs)"
		// TODO continuing detection !!
		
		// ***** FOOD Detection !! (internal) => HAS FOOD ! (XXX indicates in PHEROMONE_01)
		chrSDdetection.addGene( sdgb.perception( false ).object( false ) 
				.indicator( ObjectType.FOOD.getIndex() )
				.threshold( 0 )
				.attribute( AgentType.BIOSILICO_VIRIDITA.getIndex() )
				.varia( SomeChemicals.PHEROMONE_01.getIndex() ).value( 100 )
				.script( DecisionType.HAS.getIndex() )
			.name( "StimulusDecision : HAS FOOD" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		// NOTE biochemistry to make PHEROMONE_01 breakdown !!
		chrSDdetection.addGene( brb
				.achem(SomeChemicals.PHEROMONE_01.getIndex()).acoef( 5 )
				.bchem(SomeChemicals.NEUTRAL.getIndex()).bcoef( 0 )
				.cchem(SomeChemicals.NEUTRAL.getIndex()).ccoef( 0 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 1 )
			.name( "BiochemicalReaction : Pheromone_01 breakdown !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		chrInitialConcentrations.setName("Initial Concentration's");
		chrBiochemicalReactions.setName("Biochemical Reaction's");
		
		chrInitialConcentrations.addGene( icb	
				.varia( SomeChemicals.GLUCOSE.getIndex() ).value( 25 )
				.name( "InitConc GLUCOSE 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrInitialConcentrations.addGene( icb	
				.varia( SomeChemicals.STARCH.getIndex() ).value( 25 )
				.name( "InitConc STARCH 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrInitialConcentrations.addGene( icb	
				.varia( SomeChemicals.ENERGY_HEAT.getIndex() ).value( 25 )
				.name( "InitConc ENERGY_HEAT 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrInitialConcentrations.addGene( icb	
				.varia( SomeChemicals.GLYCOGEN.getIndex() ).value( 25 )
				.name( "InitConc GLYCOGEN 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrInitialConcentrations.addGene( icb	
				.varia( SomeChemicals.CARBON_DIOXYDE.getIndex() ).value( 25 )
				.name( "InitConc CARBON_DIOXYDE 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrInitialConcentrations.addGene( icb	
				.varia( SomeChemicals.DIOXYGEN.getIndex() ).value( 25 )
				.name( "InitConc DIOXYGEN 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		chrInitialConcentrations.addGene( icb	
				.varia( SomeChemicals.WATER.getIndex() ).value( 25 )
				.name( "InitConc WATER 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 5 ).build() );
		
		chrInitialConcentrations.addGene( sdgb		
				.perception( false ).object( false )
				.indicator( 800 ).threshold( 350 )
				.attribute( SomeChemicals.DIOXYGEN.getIndex() ).varia( SomeChemicals.DIOXYGEN.getIndex() )
				.value( 30 ).script( DecisionType.EMIT.getIndex() )
			.mutate( true )	.duplicate( true )	.delete( true )	.activ( true )
			.agemin( 0 )	.agemax( 999 )		.sex( 0 )		.mutation( 5 )
			.name("StimulusDecision EMIT DIOXYGEN (added)").build() );
		
		chrInitialConcentrations.addGene( sdgb	
				.perception( false ).object( false )
				.indicator( 800 ).threshold( 500 )
				.attribute( SomeChemicals.ENERGY_HEAT.getIndex() ).varia( SomeChemicals.ENERGY_HEAT.getIndex() )
				.value( 100 ).script( DecisionType.EMIT.getIndex() )
			.mutate( true )	.duplicate( true )	.delete( true )	.activ( true )
			.agemin( 0 )	.agemax( 999 )		.sex( 0 )		.mutation( 5 )
			.name("StimulusDecision EMIT ENERGY_HEAT (added)").build() );
		
		// EMISSION / RECEPTION ***** Use Heat NRJ, Oxygen !
		chrInitialConcentrations.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.ENERGY_HEAT.getIndex() )
				.varia( SomeChemicals.ENERGY_HEAT.getIndex() )
				.value( 5 ).script( DecisionType.RECEIVE.getIndex() )
			.name( "StimulusDecision RECEIVE ENERGY_HEAT" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrInitialConcentrations.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.DIOXYGEN.getIndex() )
				.varia( SomeChemicals.DIOXYGEN.getIndex() )
				.value( 10 ).script( DecisionType.RECEIVE.getIndex() )
			.name( "StimulusDecision RECEIVE DIOXYGEN" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrInitialConcentrations.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.CARBON_DIOXYDE.getIndex() )
				.varia( SomeChemicals.CARBON_DIOXYDE.getIndex() )
				.value( 5 ).script( DecisionType.EMIT.getIndex() )
			.name( "StimulusDecision EMIT DIOXYGEN" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrInitialConcentrations.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.WATER.getIndex() )
				.varia( SomeChemicals.WATER.getIndex() )
				.value( 5 ).script( DecisionType.EMIT.getIndex() )
			.name( "StimulusDecision EMIT WATER" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		// ***** BiochemicalReaction's
		chrBiochemicalReactions.addGene( brb
				.achem(SomeChemicals.STARCH.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.NEUTRAL.getIndex()).bcoef( 0 )
				.cchem(SomeChemicals.GLUCOSE.getIndex()).ccoef( 6 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 1 )
			.name( "BiochemicalReaction : 1 STARCH to 6 GLUCOSE !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemicalReactions.addGene( brb
				.achem(SomeChemicals.HEXOKINASE.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.GLUCOSE.getIndex()).bcoef( 1 )
				.cchem(SomeChemicals.CARBON_DIOXYDE.getIndex()).ccoef( 6 )
				.dchem(SomeChemicals.WATER.getIndex()).dcoef( 6 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : HEXOKINASE + GLUCOSE => CO2 + H20 !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemicalReactions.addGene( brb
				.achem(SomeChemicals.HEXOKINASE.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.FRUCTOSE.getIndex()).bcoef( 1 )
				.cchem(SomeChemicals.CARBON_DIOXYDE.getIndex()).ccoef( 5 )
				.dchem(SomeChemicals.WATER.getIndex()).dcoef( 5 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : HEXOKINASE + FRUCTOSE => CO2 + H20 !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemicalReactions.addGene( brb
				.achem(SomeChemicals.GLUCOSE.getIndex()).acoef( 10 )
				.bchem(SomeChemicals.DIOXYGEN.getIndex()).bcoef( 5 )
				.cchem(SomeChemicals.GLYCOGEN.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : 10 GLUCOSE to 1 GLYCOGEN !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemicalReactions.addGene( brb
				.achem(SomeChemicals.FRUCTOSE.getIndex()).acoef( 15 )
				.bchem(SomeChemicals.DIOXYGEN.getIndex()).bcoef( 5 )
				.cchem(SomeChemicals.GLYCOGEN.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : 15 FRUCTOSE to 1 GLYCOGEN !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemicalReactions.addGene( brb
				.achem(SomeChemicals.GLYCOGEN.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.NEUTRAL.getIndex()).bcoef( 0 )
				.cchem(SomeChemicals.GLUCOSE.getIndex()).ccoef( 10 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 1 )
			.name( "BiochemicalReaction : 1 GLYCOGEN to 10 GLUCOSE !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		chrBiochemicalReactions.addGene( brb
				.achem(SomeChemicals.GLYCOGEN.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.HEXOKINASE.getIndex()).bcoef( 1 )
				.cchem(SomeChemicals.GLUCOSE.getIndex()).ccoef( 8 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 3 )
			.name( "BiochemicalReaction : 1 GLYCOGEN to 8 GLUCOSE ! (hexokinase catalysis)" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 5 )
			.build() );
		
		// ***** Instantiate Ant and Organism With Genome !!
		Ant testAnt = new Ant( Arrays.asList( chrBrain, chrEmitReceiv, chrInstinct, chrSDdecision, chrSDdetection, 
												chrInitialConcentrations, chrBiochemicalReactions) );
		Assertions.assertNotNull( testAnt );
		Assertions.assertEquals(7, testAnt.getGenome().size());

		Assertions.assertEquals(chrBrain.length(), 			testAnt.getGenome().get( 0 ).length());
		Assertions.assertEquals(chrEmitReceiv.length(), 	testAnt.getGenome().get( 1 ).length());
		Assertions.assertEquals(chrInstinct.length(), 		testAnt.getGenome().get( 2 ).length());
		Assertions.assertEquals(chrSDdecision.length(), 	testAnt.getGenome().get( 3 ).length());
		Assertions.assertEquals(chrSDdetection.length(), 	testAnt.getGenome().get( 4 ).length());
		Assertions.assertEquals(chrInitialConcentrations.length(), 	testAnt.getGenome().get( 5 ).length());
		Assertions.assertEquals(chrBiochemicalReactions.length(), 	testAnt.getGenome().get( 6 ).length());
		
		Assertions.assertEquals(  9, testAnt.getGenome().get( 0 ).length());
		Assertions.assertEquals( 53, testAnt.getGenome().get( 1 ).length());
		Assertions.assertEquals( 61, testAnt.getGenome().get( 2 ).length());
		Assertions.assertEquals( 18, testAnt.getGenome().get( 3 ).length()); 
		Assertions.assertEquals( 29, testAnt.getGenome().get( 4 ).length());
		Assertions.assertEquals( 13, testAnt.getGenome().get( 5 ).length());
		Assertions.assertEquals(  7, testAnt.getGenome().get( 6 ).length());
		
		Integer genesNumber = ReproductionHelper.sizeOfGenome( testAnt );
		Logger.printlnLog(LoggerLevel.LL_DEBUG, genesNumber.toString());
		Assertions.assertEquals(190, genesNumber.intValue() );
		
		List<Integer> listLengthGenomes = testAnt.getGenome().stream().map( Chromosome::length ).collect(Collectors.toList());
		Assertions.assertEquals(190, listLengthGenomes.stream().reduce(0, Integer::sum).intValue());
		
		testAnt.setRank("Rank Test");
		testAnt.setNameCommon("Test Starting Ant");
		testAnt.setNameBiosilico("AntHill Ant Example");
		testAnt.setDivision("TESTS");
		
		// ***** ***** ***** ***** ***** ***** ***** ***** ***** 
		// ***** ***** ***** ***** ***** ***** ***** ***** ***** 
		// ***** test with a World and WorldCase
		
		World2D w			= new World2D(3, 3);
		World2DCase wc	= w.getWorldCase(1, 1);
		Assertions.assertNotNull( wc );
		
		testAnt.setCurrentWorldCase( wc );
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getChemicals().getVariable(k) );
		});
		
		// ***** one execution in this context
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // to permit correct initialization of "starting genes"
		
		// ***** Export Ant as a TXT file !
		BuildingGenomeHelper.exportAsTXTfile("TestAntCompleteGenome.txt", testAnt);
		
		BuildingGenomeHelper.exportGenome(AntBuildingGenomeComplete.SRC_TEST_RSC + "GenomeAntCompleteGenome.txt", testAnt);
		
		BuildingGenomeHelper.copyMoveGenome(AntBuildingGenomeComplete.SRC_TEST_RSC + "GenomeAntCompleteGenome.txt", 
											AntBuildingGenomeComplete.SRC_MAIN_RSC_ANTHILL + "baseGenomeAnt.txt");
		BuildingGenomeHelper.removeACGTsequence( AntBuildingGenomeComplete.SRC_MAIN_RSC_ANTHILL + "baseGenomeAnt.txt" );
		
		DataExporterAndViewAnalysis.testFileExists( AntBuildingGenomeComplete.SRC_TEST_RSC + "TestAntCompleteGenome.txt" );
		DataExporterAndViewAnalysis.testFileExists( AntBuildingGenomeComplete.SRC_TEST_RSC + "GenomeAntCompleteGenome.txt" );
		DataExporterAndViewAnalysis.testFileExists( AntBuildingGenomeComplete.SRC_MAIN_RSC_ANTHILL + "baseGenomeAnt.txt" );
	}

}

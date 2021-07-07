package gabywald.biosilico.anthill.tests;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.anthill.Ant;
import gabywald.biosilico.anthill.AntReceptionChemicals;
import gabywald.biosilico.anthill.launcher.BuildingGenomeHelper;
import gabywald.biosilico.genetics.builders.BiochemicalReactionBuilder;
import gabywald.biosilico.genetics.builders.BrainGeneBuilder;
import gabywald.biosilico.genetics.builders.BrainLobeGeneBuilder;
import gabywald.biosilico.genetics.builders.EmitterReceptorBuilder;
import gabywald.biosilico.genetics.builders.InitialConcentrationBuilder;
import gabywald.biosilico.genetics.builders.InstinctBuilder;
import gabywald.biosilico.genetics.builders.StimulusDecisionBuilder;
import gabywald.biosilico.model.Chromosome;
import gabywald.biosilico.model.Neuron;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.chemicals.ChemicalsHelper;
import gabywald.biosilico.model.enums.AgentType;
import gabywald.biosilico.model.enums.DecisionType;
import gabywald.biosilico.model.enums.DirectionWorld;
import gabywald.biosilico.model.enums.ObjectType;
import gabywald.biosilico.model.enums.SomeChemicals;
import gabywald.biosilico.model.environment.World2D;
import gabywald.biosilico.model.environment.World2DCase;
import gabywald.biosilico.model.reproduction.ReproductionHelper;
import gabywald.biosilico.model.tests.TestObjectFoodEgg;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class AntBuildingGenomeWire {

	@Test
	void testWireBrainWithDecisionAndInstincts() {

		Chromosome chrBrain					= new Chromosome();
		Chromosome chrEmitReceiv			= new Chromosome();
		Chromosome chrInstinct				= new Chromosome();
		Chromosome chrSDdecision			= new Chromosome();
		Chromosome chrSDdetection			= new Chromosome();
		
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
			.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
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
			.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
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
			.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
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
			.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
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
			.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
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
			.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
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
			.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
			.build() );
		
		// *** Output neurons : two (2) groups of 10 WTA neurons
		// // // first group : to be linked with choice / decision of directions (for now only 9 out of 27)
		// // // + MOVE_AWAY + GET(FOOD) + DROP(FOOD)
		chrBrain.addGene( blgb 
				.rest( 0 ).threshold( 20 ).desc( 10 )
				.dmin( 5 ).dmax( 9 ).prox( 2 )
				.repr( false ).repy( 0 ).wta( true )
				.heigth( 1 ).width( selectedDirs.size() + 3 ).posx( 90 ).posy( 0 )
				.replace( false )
			.name( "Brain Lobe Gene Data Output 1 : directions + MOVE_AWAY + GET(FOOD) + DROP(FOOD)" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
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
			.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
			.build() );
		
		// ***** ***** ***** ***** ***** 
		chrEmitReceiv.setName("EmitterReceptor's input / output");
		// *** EmitterReceptor Input => to first line (inputs) of Brain !! (Ph_00 & direction)
		String nameFirstPartERinput	= "ER receptor input ";
		for (DirectionWorld dw : selectedDirs) {
			AntReceptionChemicals arc = AntReceptionChemicals.getFrom( dw, SomeChemicals.PHEROMONE_00 );
			chrEmitReceiv.addGene( erb
					.variable( arc.getIndex() ).threshold( 5 ).ioput( 20 )
					.posx( 0 ).posy( dw.getIndex() - BuildingGenomeHelper.indexLessRemoveDirection )
					.receptor( true ).internal( true )
				.name( nameFirstPartERinput + arc.getName() )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
				.build() );
		} // END "for (DirectionWorld dw : selectedDirs)"
		// *** EmitterReceptor Input => to second line (inputs) of Brain !! (Ph_01 & direction)
		for (DirectionWorld dw : selectedDirs) {
			AntReceptionChemicals arc = AntReceptionChemicals.getFrom( dw, SomeChemicals.PHEROMONE_01 );
			chrEmitReceiv.addGene( erb
					.variable( arc.getIndex() ).threshold( 5 ).ioput( 20 )
					.posx( 1 ).posy( dw.getIndex() - BuildingGenomeHelper.indexLessRemoveDirection )
					.receptor( true ).internal( true )
				.name( nameFirstPartERinput + arc.getName() )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
				.build() );
		} // END "for (DirectionWorld dw : selectedDirs)"
		// *** EmitterReceptor Input => to third line (inputs) of Brain !! (FOOD & direction)
		for (DirectionWorld dw : selectedDirs) {
			AntReceptionChemicals arc = AntReceptionChemicals.getFrom( dw, ObjectType.FOOD );
			chrEmitReceiv.addGene( erb
					.variable( arc.getIndex() ).threshold( 5 ).ioput( 20 )
					.posx( 2 ).posy( dw.getIndex() - BuildingGenomeHelper.indexLessRemoveDirection )
					.receptor( true ).internal( true )
				.name( nameFirstPartERinput + arc.getName() )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
				.build() );
		} // END "for (DirectionWorld dw : selectedDirs)"
		
		// *** *** *** StimulusDecision : more impact if PHEROMONE_01 if HIGH
		AntReceptionChemicals arcCurrentPh01 = AntReceptionChemicals.getFrom( DirectionWorld.CURRENT, SomeChemicals.PHEROMONE_01 );
		chrEmitReceiv.addGene( erb
				.variable( arcCurrentPh01.getIndex() ).threshold(  99 ).ioput( 100 )
				.posx( 1 ).posy( 1 )
				.receptor( true ).internal( true )
			.name( nameFirstPartERinput + arcCurrentPh01.getName() + " FORCE" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
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
				.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
				.build() );
		} // END "for (SomeChemicals scPhero : allPheromones)"
		
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
				.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
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
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		// ER GET FOOD ... 
		final int outputPosYGETFOOD = selectedDirs.size() + 1;
		chrEmitReceiv.addGene( erb
				.variable( DecisionType.GET.getIndex() ).threshold( 10 ).ioput( 10 )
				.posx( 90 ).posy( outputPosYGETFOOD )
				.receptor( false ).internal( true )
			.name( nameFirstPartERoutput + DecisionType.GET.getName() + " FOOD" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		// ER DROP FOOD ... 
		final int outputPosYDROPFOOD = selectedDirs.size() + 2;
		chrEmitReceiv.addGene( erb
				.variable( DecisionType.DROP.getIndex() ).threshold( 10 ).ioput( 10 )
				.posx( 90 ).posy( outputPosYDROPFOOD )
				.receptor( false ).internal( true )
			.name( nameFirstPartERoutput + DecisionType.DROP.getName() + " FOOD" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		
		// ***** ***** ***** ***** ***** 
		chrInstinct.setName("Instinct : genetically-defined connections within Brain");
		// *** Instincts !! 
		// => TODO choose another chemical to initiate instinct ?!
		chrInstinct.addGene( icb	
				.varia( SomeChemicals.GLUCOSE.getIndex() ).value( 25 )
				.name( "InitConc GLUCOSE 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
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
						.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
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
					.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
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
						.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
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
					.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
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
					.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
					.build() );
		chrInstinct.addGene( igb
				.inputPosX( 3 ).inputPosY( SomeChemicals.PHEROMONE_01.getIndex() - BuildingGenomeHelper.indexLessRemovePheromone )
				.outputPosX( 90 ).outputPosY( outputPosYDROPFOOD )
				.weight( 10 ).variable( SomeChemicals.GLUCOSE.getIndex() )
				.threshold( 5 ).check( false ).positiv( true )
				.name( "Instinct (" + 3 + ", " + (SomeChemicals.PHEROMONE_01.getIndex() - BuildingGenomeHelper.indexLessRemovePheromone) + ") to (90, " + outputPosYDROPFOOD + ") " 
							+ "INT PHER01 => HAS(FOOD)" + " [" + SomeChemicals.GLUCOSE.getName() + "]")
					.mutate( true ).duplicate( true ).delete( true ).activ( true )
					.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
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
				.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
				.build() );
		} // END "for (DirectionWorld dw : selectedDirs) "

		// Decision MOVE_AWAY
		chrSDdecision.addGene( sdgb.perception( false ).object( true ) 
				.indicator( DecisionType.MOVE_AWAY.getIndex() ) // to be detected
				.threshold( 0 ).attribute( 0 ).varia( 0 ).value( 0 )			// attribute && varia && value are irrelevant here !
				.script( DecisionType.MOVE_AWAY.getIndex() )	// What to do !
			.name( "StimulusDecisionGene_MOVEAWAY" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		
		// Decision GET FOOD
		chrSDdecision.addGene( sdgb.perception( false ).object( true ) 
				.indicator( ObjectType.FOOD.getIndex() ).threshold( 0 )
				.attribute( AgentType.BIOSILICO_VIRIDITA.getIndex() )
				.varia( 0 ).value( 0 )										// varia && value are irrelevant here !
				.script( DecisionType.GET.getIndex() )						// What to do !
			.name( "StimulusDecisionGene_GET_FOOD" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		
		// Decision DROP FOOD
		chrSDdecision.addGene( sdgb.perception( false ).object( true ) 
				.indicator( ObjectType.FOOD.getIndex() ).threshold( 0 )
				.attribute( AgentType.BIOSILICO_VIRIDITA.getIndex() )
				.varia( 0 ).value( 0 )										// varia && value are irrelevant here !
				.script( DecisionType.DROP.getIndex() )						// What to do !
			.name( "StimulusDecisionGene_DROP_FOOD" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		// TODO EAT FOOD
		// TODO EMIT Pheromone_00
		// TODO EMIT Pheromone_01
		
		// ***** ***** ***** ***** ***** 
		chrSDdetection.setName( "StimulusDecision : Pheromones and FOOD detection !" );
		// ***** Building StimulusDecisionGene for detection !!
		for (DirectionWorld dw : selectedDirs) {
			
			for (SomeChemicals scPHE : someChems) {
				// ***** Detect Pheromone_0X at WorldCase with threshold 10, change variable 70X set to 5
				// basicChromosome.setName( scPHE.getName() + " detection !" );
				AntReceptionChemicals arc = AntReceptionChemicals.getFrom( dw, scPHE );
				chrSDdetection.addGene( sdgb.perception( true ).object( false ) 
						.indicator( scPHE.getIndex() ).threshold( 10 )
						.attribute( dw.getIndex() )
						.varia( arc.getIndex() )
						.value( 5 ).script( 0 )
					.name( "StimulusDecisionGene" + dw.getName() + "_" + arc.getName() )
					.mutate( true ).duplicate( true ).delete( true ).activ( true )
					.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
					.build() );
			} // END "for (SomeChemicals scPHE : someChems)"
			
			// ***** FOOD Detection !!
			AntReceptionChemicals arc = AntReceptionChemicals.getFrom(dw, ObjectType.FOOD);
			chrSDdetection.addGene( sdgb.perception( true ).object( true ) 
					.indicator( ObjectType.FOOD.getIndex() ).threshold( 0 )
					.attribute( dw.getIndex() )
					.varia( arc.getIndex() ).value( 5 ).script( 0 )
				.name( "StimulusDecisionGene" + arc.getName() )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
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
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
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
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		
		// ***** Instantiate Ant and Organism With Genome !!
		Ant testAnt = new Ant( Arrays.asList( chrBrain, chrEmitReceiv, chrInstinct, chrSDdecision, chrSDdetection ) );
		Assertions.assertNotNull( testAnt );
		Assertions.assertEquals(5, testAnt.getGenome().size());

		Assertions.assertEquals(chrBrain.length(), 			testAnt.getGenome().get( 0 ).length());
		Assertions.assertEquals(chrEmitReceiv.length(), 	testAnt.getGenome().get( 1 ).length());
		Assertions.assertEquals(chrInstinct.length(), 		testAnt.getGenome().get( 2 ).length());
		Assertions.assertEquals(chrSDdecision.length(), 	testAnt.getGenome().get( 3 ).length());
		Assertions.assertEquals(chrSDdetection.length(), 	testAnt.getGenome().get( 4 ).length());
		
		Assertions.assertEquals(  9, testAnt.getGenome().get( 0 ).length());
		Assertions.assertEquals( 50, testAnt.getGenome().get( 1 ).length());
		Assertions.assertEquals( 32 + 27, testAnt.getGenome().get( 2 ).length());
		Assertions.assertEquals( 12, testAnt.getGenome().get( 3 ).length()); 
		Assertions.assertEquals( 29, testAnt.getGenome().get( 4 ).length());
		
		Integer genesNumber = ReproductionHelper.sizeOfGenome( testAnt );
		Logger.printlnLog(LoggerLevel.LL_DEBUG, genesNumber.toString());
		Assertions.assertEquals(159, genesNumber.intValue() );
		
		List<Integer> listLengthGenomes = testAnt.getGenome().stream().map( Chromosome::length ).collect(Collectors.toList());
		Assertions.assertEquals(159, listLengthGenomes.stream().reduce(0, Integer::sum).intValue());
		
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
		
		BuildingGenomeHelper.show(testAnt, wc);
	
		// ***** one MORE execution in this context
		testAnt.execution( wc );
		
		for (DirectionWorld dw : selectedDirs) {
			
			for (SomeChemicals scPHE : someChems) {
				
				wc.getDirection( dw ).getChemicals().setVariable(scPHE.getIndex(), 11);
				
				testAnt.execution( wc );
				testAnt.execution( wc );
			
			} // END "for (SomeChemicals scPHE : someChems)"
			
			BuildingGenomeHelper.show(testAnt, wc);
			
			int posYindex = dw.getIndex() - BuildingGenomeHelper.indexLessRemoveDirection;
			Assertions.assertTrue( testAnt.getBrain().getNeuronAt(  0, posYindex).ckActivated() );
			Assertions.assertTrue( testAnt.getBrain().getNeuronAt(  1, posYindex).ckActivated() );
			Assertions.assertTrue( testAnt.getBrain().getNeuronAt( 90, posYindex).ckActivated() );
			
			Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 90,  9).ckActivated() );
			
			for (SomeChemicals scPHE : someChems) {
				for (DirectionWorld dwCleaning : selectedDirs) 
					{ wc.getDirection( dwCleaning ).getChemicals().setVariable(scPHE.getIndex(),  0); }
			} // END "for (SomeChemicals scPHE : someChems)"
			
			while ( (testAnt.getBrain().getNeuronAt(  0, posYindex).ckActivated()) 
					|| (testAnt.getBrain().getNeuronAt(  1, posYindex).ckActivated()) 
					|| (testAnt.getBrain().getNeuronAt( 90, posYindex).ckActivated()) )
				{ testAnt.execution( wc ); }
			
			BuildingGenomeHelper.show(testAnt, wc);
			
			Assertions.assertFalse( testAnt.getBrain().getNeuronAt(  0, posYindex).ckActivated() );
			Assertions.assertFalse( testAnt.getBrain().getNeuronAt(  1, posYindex).ckActivated() );
			Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 90, posYindex).ckActivated() );
			
			Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 90,  9).ckActivated() );
			
			// ***** Test on food detection !
			Organism food = new TestObjectFoodEgg();
			wc.getDirection( dw ).addAgent( food );
			
			testAnt.execution( wc );
			testAnt.execution( wc );
			
			BuildingGenomeHelper.show(testAnt, wc);
			
			Assertions.assertTrue( testAnt.getBrain().getNeuronAt(  2, posYindex).ckActivated() );
			
			wc.getDirection( dw ).remAgent( food );
			
			while ( (testAnt.getBrain().getNeuronAt(  2, posYindex).ckActivated()) )
				{ testAnt.execution( wc ); }
			
			Assertions.assertFalse( testAnt.getBrain().getNeuronAt(  2, posYindex).ckActivated() );
			
			Assertions.assertFalse( testAnt.getBrain().getNeuronAt(  0, posYindex).ckActivated() );
			Assertions.assertFalse( testAnt.getBrain().getNeuronAt(  1, posYindex).ckActivated() );
			Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 90, posYindex).ckActivated() );
			
			Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 90,  9).ckActivated() );
			
			// ***** ***** ***** 
			
		} // END "for (DirectionWorld dw : selectedDirs)"
		
		TestObjectFoodEgg food = new TestObjectFoodEgg();
		testAnt.addAgent( food );
		
		for (SomeChemicals pheromone : allPheromones) {
			switch(pheromone) {
			case PHEROMONE_01:
			Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( pheromone.getIndex() ) );
			break;
			default:
				Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( pheromone.getIndex() ) );
			}
		} // END "for (SomeChemicals pheromone : allPheromones)"
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3, 1).ckActivated() );
		
		// ***** execution
		testAnt.execution( wc );
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		for (SomeChemicals pheromone : allPheromones) {
			switch(pheromone) {
			case PHEROMONE_01:
			Assertions.assertEquals( 95, testAnt.getChemicals().getVariable( pheromone.getIndex() ) );
			break;
			default:
				Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( pheromone.getIndex() ) );
			}
		} // END "for (SomeChemicals pheromone : allPheromones)"
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3, 1).ckActivated() );
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		// ***** execution
		testAnt.execution( wc );
		
		for (SomeChemicals pheromone : allPheromones) {
			switch(pheromone) {
			case PHEROMONE_01:
			Assertions.assertEquals(190, testAnt.getChemicals().getVariable( pheromone.getIndex() ) );
			break;
			default:
				Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( pheromone.getIndex() ) );
			}
		} // END "for (SomeChemicals pheromone : allPheromones)"

		Assertions.assertTrue( testAnt.getBrain().getNeuronAt( 3, 1).ckActivated() );
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		testAnt.remAgent( food );
		
		// ***** execution
		testAnt.execution( wc );
		testAnt.execution( wc );
		
		for (SomeChemicals pheromone : allPheromones) {
			switch(pheromone) {
			case PHEROMONE_01:
			Assertions.assertEquals(180, testAnt.getChemicals().getVariable( pheromone.getIndex() ) );
			break;
			default:
				Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( pheromone.getIndex() ) );
			}
		} // END "for (SomeChemicals pheromone : allPheromones)"
		
		Assertions.assertTrue( testAnt.getBrain().getNeuronAt( 3, 1).ckActivated() );
		
		// ***** Cleaning before the following
		testAnt.getChemicals().setVariable(SomeChemicals.PHEROMONE_01.getIndex(), 0);
		while ( (testAnt.getBrain().getNeuronAt(  3,  1).ckActivated()) )
			{ testAnt.execution( wc ); }
		
		// ***** one MORE execution in this context
		testAnt.execution( wc );
		
		Neuron neuronMOVEAWAY_90o = testAnt.getBrain().getNeuronAt(90, outputPosYMOVEAWAY);
		neuronMOVEAWAY_90o.setActivity(100);
		Assertions.assertEquals(100, neuronMOVEAWAY_90o.getActivity() );
		Assertions.assertTrue(neuronMOVEAWAY_90o.ckActivated());
		Assertions.assertEquals( 0, testAnt.getChemicals().getVariable(DecisionType.MOVE_AWAY.getIndex()) );
		
		testAnt.execution( wc );
		
		Assertions.assertEquals(90, neuronMOVEAWAY_90o.getActivity() );
		Assertions.assertTrue(neuronMOVEAWAY_90o.ckActivated());
		Assertions.assertEquals( 9, testAnt.getChemicals().getVariable(DecisionType.MOVE_AWAY.getIndex()) );
		
		testAnt.deplace();
		
		Assertions.assertEquals(90, neuronMOVEAWAY_90o.getActivity() );
		Assertions.assertTrue(neuronMOVEAWAY_90o.ckActivated());	
		Assertions.assertEquals( 9, testAnt.getChemicals().getVariable(DecisionType.MOVE_AWAY.getIndex()) );

		Assertions.assertEquals( 0, wc.getAgentListLength() );
		Assertions.assertEquals(wc.getDirection(testAnt.getDirection()), testAnt.getCurrentEnvironmentItem());
		Assertions.assertNotEquals(DirectionWorld.CURRENT, testAnt.getDirection());
		
		neuronMOVEAWAY_90o.setActivity( 0 );
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		// ***** one MORE execution in this context
		testAnt.execution( wc );
		
		wc.addAgent( new TestObjectFoodEgg() );
		
		Neuron neuronGETFOOD_90o = testAnt.getBrain().getNeuronAt(90, outputPosYGETFOOD);
		neuronGETFOOD_90o.setActivity(100);
		Assertions.assertEquals(100, neuronGETFOOD_90o.getActivity() );
		Assertions.assertTrue(neuronGETFOOD_90o.ckActivated());
		Assertions.assertEquals( 0, testAnt.getChemicals().getVariable(DecisionType.GET.getIndex()) );
		Assertions.assertEquals( 0, testAnt.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals( 1, wc.hasObjectType(ObjectType.FOOD) );
		
		testAnt.execution( wc );
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(90, neuronGETFOOD_90o.getActivity() );
		Assertions.assertTrue(neuronGETFOOD_90o.ckActivated());
		Assertions.assertEquals( 9, testAnt.getChemicals().getVariable(DecisionType.GET.getIndex()) );
		Assertions.assertEquals( 1, testAnt.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals( 0, wc.hasObjectType(ObjectType.FOOD) );
		
		neuronGETFOOD_90o.setActivity( 0 );
		testAnt.getChemicals().setVariable(DecisionType.GET.getIndex(), 0);
		// ***** Cleaning before the following
		testAnt.getChemicals().setVariable(SomeChemicals.PHEROMONE_01.getIndex(), 0);
		while ( (testAnt.getBrain().getNeuronAt(  3,  1).ckActivated()) )
			{ testAnt.execution( wc ); }
		
		Neuron neuronDROPFOOD_90o = testAnt.getBrain().getNeuronAt(90, outputPosYDROPFOOD);
		neuronDROPFOOD_90o.setActivity(100);
		Assertions.assertEquals(100, neuronDROPFOOD_90o.getActivity() );
		Assertions.assertTrue(neuronDROPFOOD_90o.ckActivated());
		Assertions.assertEquals( 0, testAnt.getChemicals().getVariable(DecisionType.DROP.getIndex()) );
		Assertions.assertEquals( 1, testAnt.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals( 0, wc.hasObjectType(ObjectType.FOOD) );
		
		testAnt.execution( wc );
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(90, neuronDROPFOOD_90o.getActivity() );
		Assertions.assertTrue(neuronDROPFOOD_90o.ckActivated());
		Assertions.assertEquals( 9, testAnt.getChemicals().getVariable(DecisionType.DROP.getIndex()) );
		Assertions.assertEquals( 0, testAnt.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals( 1, wc.hasObjectType(ObjectType.FOOD) );
		
		neuronDROPFOOD_90o.setActivity( 0 );
		testAnt.getChemicals().setVariable(DecisionType.DROP.getIndex(), 0);
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		// ***** Export Ant as a TXT file !
		BuildingGenomeHelper.exportAsTXTfile("TestWireBrainWithDecisionAndInstincts.txt", testAnt);
		
		BuildingGenomeHelper.exportGenome("GenomeWireBrainWithDecisionAndInstincts.txt", testAnt);
		
	}

}

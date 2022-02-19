package gabywald.biosilico.anthill.tests;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.anthill.Ant;
import gabywald.biosilico.anthill.GeneratorReceptionChemicals;
import gabywald.biosilico.anthill.helpers.BuildingGenomeHelper;
import gabywald.biosilico.genetics.builders.BiochemicalReactionBuilder;
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
import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.environment.World2D;
import gabywald.biosilico.model.environment.World2DCase;
import gabywald.biosilico.model.reproduction.ReproductionHelper;
import gabywald.biosilico.model.tests.TestObjectFoodEgg;
import gabywald.global.data.StringUtils;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020, 2022)
 */
class AntBuildingGenomeTests {
	
	@Test
	void testStimulusDecisionCurrent() {
		
		Chromosome basicGenome			= new Chromosome();
		
		StimulusDecisionBuilder sdgb	= new StimulusDecisionBuilder();

		// ***** Detect Pheromone_00 at current WorldCase with threshold 10, change variable 700 set to 5
		basicGenome.addGene( sdgb.perception( true ).object( false ) 
				.indicator( SomeChemicals.PHEROMONE_00.getIndex() ).threshold( 10 )
				.attribute( DirectionWorld.CURRENT.getIndex() )
				.varia( GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex() ).value( 5 ).script( 0 )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		
		Ant testAnt = new Ant( basicGenome );
		Assertions.assertNotNull( testAnt );
		Assertions.assertEquals(1, testAnt.getGenome().size());
		
		testAnt.setRank("Rank Test");
		testAnt.setNameCommon("Test Starting Ant");
		testAnt.setNameBiosilico("AntHill Ant Example");
		testAnt.setDivision("TESTS");
		
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
		testAnt.execution( wc );
		testAnt.execution( wc );
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_East.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_North.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_NorthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_NorthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_South.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_SouthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_SouthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_West.getIndex() ) );
		
		wc.getChemicals().setVariable(SomeChemicals.PHEROMONE_00.getIndex(), 11);
		
		// ***** one MORE execution in this context
		testAnt.execution( wc );
		testAnt.execution( wc );
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_East.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_North.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_NorthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_NorthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_South.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_SouthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_SouthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_West.getIndex() ) );
		
	}
	
	@Test
	void testStimulusDecisionAllDirections() {
		
		Chromosome basicGenome				= new Chromosome();
		
		StimulusDecisionBuilder sdgb		= new StimulusDecisionBuilder();
		List<DirectionWorld> selectedDirs	= DirectionWorld.values2DasList();
		for (DirectionWorld dw : selectedDirs) {
			// ***** Detect Pheromone_00 at current WorldCase with threshold 10, change variable 700 set to 5
			GeneratorReceptionChemicals arc = GeneratorReceptionChemicals.getFrom( dw, SomeChemicals.PHEROMONE_00 );
			basicGenome.addGene( sdgb.perception( true ).object( false ) 
					.indicator( SomeChemicals.PHEROMONE_00.getIndex() ).threshold( 10 )
					.attribute( dw.getIndex() )
					.varia( arc.getIndex() )
					.value( 5 ).script( 0 )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
				.build() );
			
			Ant testAnt = new Ant( basicGenome );
			Assertions.assertNotNull( testAnt );
			Assertions.assertEquals(1, testAnt.getGenome().size());
			
			testAnt.setRank("Rank Test");
			testAnt.setNameCommon("Test Starting Ant");
			testAnt.setNameBiosilico("AntHill Ant Example");
			testAnt.setDivision("TESTS");
			
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
			testAnt.execution( wc );
			testAnt.execution( wc );
			
			BuildingGenomeHelper.show(testAnt, wc);
			
			Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_East.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_North.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_NorthEast.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_NorthWest.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_South.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_SouthEast.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_SouthWest.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_West.getIndex() ) );
		
			wc.getDirection( dw ).getChemicals().setVariable(SomeChemicals.PHEROMONE_00.getIndex(), 11);
			
			// ***** one MORE execution in this context
			testAnt.execution( wc );
			testAnt.execution( wc );
			
			BuildingGenomeHelper.show(testAnt, wc);
			
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.CURRENT)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.East)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_East.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.North)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_North.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthEast)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_NorthEast.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthWest)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_NorthWest.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.South)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_South.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthEast)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_SouthEast.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthWest)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_SouthWest.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.West)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_West.getIndex() ) );
		
		} // END "for (DirectionWorld dw : dirs)"
		
	}
	
	@Test
	void testStimulusDecisionAllDirectionsVariant() {
		
		Chromosome basicChromosome			= new Chromosome();
		
		StimulusDecisionBuilder sdgb		= new StimulusDecisionBuilder();
		List<DirectionWorld> selectedDirs	= DirectionWorld.values2DasList();
		basicChromosome.setName( "Pheromone 00 detection !" );
		for (DirectionWorld dw : selectedDirs) {
			// ***** Detect Pheromone_00 at current WorldCase with threshold 10, change variable 700 set to 5
			GeneratorReceptionChemicals arc = GeneratorReceptionChemicals.getFrom( dw, SomeChemicals.PHEROMONE_00 );
			basicChromosome.addGene( sdgb.perception( true ).object( false ) 
					.indicator( SomeChemicals.PHEROMONE_00.getIndex() ).threshold( 10 )
					.attribute( dw.getIndex() )
					.varia( arc.getIndex() )
					.value( 5 ).script( 0 )
				.name( "StimulusGene" + dw.getName() + "_" + arc.getName() )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
				.build() );
			
		} // END "for (DirectionWorld dw : selectedDirs)"
			
		Ant testAnt = new Ant( basicChromosome );
		Assertions.assertNotNull( testAnt );
		Assertions.assertEquals(1, testAnt.getGenome().size());
		
		testAnt.setRank("Rank Test");
		testAnt.setNameCommon("Test Starting Ant");
		testAnt.setNameBiosilico("AntHill Ant Example");
		testAnt.setDivision("TESTS");
		
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
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_East.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_North.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_NorthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_NorthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_South.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_SouthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_SouthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_West.getIndex() ) );
	
		// ***** one MORE execution in this context
		testAnt.execution( wc );
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		for (DirectionWorld dw : selectedDirs) {
			
			for (DirectionWorld dwCleaning : selectedDirs) 
				{ wc.getDirection( dwCleaning ).getChemicals()
					.setVariable(SomeChemicals.PHEROMONE_00.getIndex(),  0); }
			
			wc.getDirection( dw ).getChemicals().setVariable(SomeChemicals.PHEROMONE_00.getIndex(), 11);
			
			// ***** one MORE execution in this context
			testAnt.execution( wc );
			
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.CURRENT)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.East)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_East.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.North)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_North.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthEast)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_NorthEast.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthWest)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_NorthWest.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.South)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_South.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthEast)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_SouthEast.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthWest)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_SouthWest.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.West)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_West.getIndex() ) );
			
		} // END "for (DirectionWorld dw : selectedDirs)"
		
		// ***** Export Ant as a TXT file !
		BuildingGenomeHelper.exportAsTXTfile("TestAntstStimulusDecisionAllDirectionsVariant.txt", testAnt);
		DataExporterAndViewAnalysis.testFileExists( AntBuildingGenomeComplete.SRC_TEST_RSC + "TestAntstStimulusDecisionAllDirectionsVariant.txt" );
		
	}
	
	@Test
	void testStimulusDecisionAllDirectionsTwoPheromones() {
		
		Chromosome basicChromosome			= new Chromosome();
		
		StimulusDecisionBuilder sdgb		= new StimulusDecisionBuilder();
		List<DirectionWorld> selectedDirs	= DirectionWorld.values2DasList();
		List<SomeChemicals> someChems		= Arrays.asList(SomeChemicals.PHEROMONE_00, 
															SomeChemicals.PHEROMONE_01);
		
		basicChromosome.setName( "Pheromones detection !" );
		
		for (DirectionWorld dw : selectedDirs) {
			
			for (SomeChemicals scPHE : someChems) {
				// ***** Detect Pheromone_0X at current WorldCase with threshold 10, change variable 700 set to 5
				// basicChromosome.setName( scPHE.getName() + " detection !" );
				GeneratorReceptionChemicals arc = GeneratorReceptionChemicals.getFrom( dw, scPHE );
				basicChromosome.addGene( sdgb.perception( true ).object( false ) 
						.indicator( scPHE.getIndex() ).threshold( 10 )
						.attribute( dw.getIndex() )
						.varia( arc.getIndex() )
						.value( 5 ).script( 0 )
					.name( "StimulusGene" + dw.getName() + "_" + arc.getName() )
					.mutate( true ).duplicate( true ).delete( true ).activ( true )
					.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
					.build() );
				
			} // END "for (SomeChemicals scPHE : someChems)"
		} // END "for (DirectionWorld dw : selectedDirs)"
			
		Ant testAnt = new Ant( basicChromosome );
		Assertions.assertNotNull( testAnt );
		Assertions.assertEquals(1, testAnt.getGenome().size());
		
		testAnt.setRank("Rank Test");
		testAnt.setNameCommon("Test Starting Ant");
		testAnt.setNameBiosilico("AntHill Ant Example");
		testAnt.setDivision("TESTS");
		
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
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_East.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_North.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_NorthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_NorthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_South.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_SouthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_SouthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_West.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_CURRENT.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_East.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_North.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_NorthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_NorthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_South.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_SouthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_SouthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_West.getIndex() ) );
	
		// ***** one MORE execution in this context
		testAnt.execution( wc );
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		for (DirectionWorld dw : selectedDirs) {
			
			for (SomeChemicals scPHE : someChems) {
				
				wc.getDirection( dw ).getChemicals().setVariable(scPHE.getIndex(), 11);
				
				// ***** one MORE execution in this context
				testAnt.execution( wc );
				
				switch( scPHE ) {
				case PHEROMONE_00 :
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.CURRENT)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.East)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_East.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.North)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_North.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthEast)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_NorthEast.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthWest)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_NorthWest.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.South)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_South.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthEast)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_SouthEast.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthWest)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_SouthWest.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.West)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_West.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_CURRENT.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_East.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_North.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_NorthEast.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_NorthWest.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_South.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_SouthEast.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_SouthWest.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_West.getIndex() ) );
					break;
				case PHEROMONE_01 : 
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_East.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_North.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_NorthEast.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_NorthWest.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_South.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_SouthEast.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_SouthWest.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_West.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.CURRENT)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_CURRENT.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.East)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_East.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.North)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_North.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthEast)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_NorthEast.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthWest)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_NorthWest.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.South)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_South.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthEast)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_SouthEast.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthWest)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_SouthWest.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.West)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_West.getIndex() ) );
					break;
				default:
					String msg = "NOT recognized Chemical !";
					Logger.printlnLog(LoggerLevel.LL_ERROR, msg);
					Assertions.fail( msg );
					break;
				}
				
				wc.getDirection( dw ).getChemicals().setVariable(scPHE.getIndex(),  0);
			
			} // END "for (SomeChemicals scPHE : someChems)"
			
			for (SomeChemicals scPHE : someChems) {
				for (DirectionWorld dwCleaning : selectedDirs) 
					{ wc.getDirection( dwCleaning ).getChemicals()
						.setVariable(scPHE.getIndex(),  0); }
			} // END "for (SomeChemicals scPHE : someChems)"
			
		} // END "for (DirectionWorld dw : selectedDirs)"
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		// ***** Export Ant as a TXT file !
		BuildingGenomeHelper.exportAsTXTfile("TestStimulusDecisionAllDirectionsTwoPheromones.txt", testAnt);
		DataExporterAndViewAnalysis.testFileExists( AntBuildingGenomeComplete.SRC_TEST_RSC + "TestStimulusDecisionAllDirectionsTwoPheromones.txt" );
		
	}
	
	@Test
	void testStimulusDecisionAllDirectionsFOOD() {
		
		Chromosome basicChromosome			= new Chromosome();
		
		StimulusDecisionBuilder sdgb		= new StimulusDecisionBuilder();
		List<DirectionWorld> selectedDirs	= DirectionWorld.values2DasList();
		basicChromosome.setName( "FOOD detection !" );		
		for (DirectionWorld dw : selectedDirs) {
			
			// ***** FOOD Detection !!
			GeneratorReceptionChemicals arc = GeneratorReceptionChemicals.getFrom(dw, ObjectType.FOOD);
			basicChromosome.addGene( sdgb.perception( true ).object( true ) 
					.indicator( ObjectType.FOOD.getIndex() ).threshold( 0 )
					.attribute( dw.getIndex() )
					.varia( arc.getIndex() ).value( 5 ).script( 0 )
				.name( "StimulusGene_" + arc.getName() )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
				.build() );
			
		} // END "for (DirectionWorld dw : selectedDirs)"
		
		Ant testAnt = new Ant( basicChromosome );
		Assertions.assertNotNull( testAnt );
		Assertions.assertEquals(1, testAnt.getGenome().size());
		
		testAnt.setRank("Rank Test");
		testAnt.setNameCommon("Test Starting Ant");
		testAnt.setNameBiosilico("AntHill Ant Example");
		testAnt.setDivision("TESTS");
		
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
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_CURRENT.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_East.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_North.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_NorthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_NorthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_South.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_SouthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_SouthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_West.getIndex() ) );
	
		// ***** one MORE execution in this context
		testAnt.execution( wc );
		
		for (DirectionWorld dw : selectedDirs) {
			
			// ***** Test on food detection !
			Organism food = new TestObjectFoodEgg();
			wc.getDirection( dw ).addAgent( food );
			
			// ***** execution
			testAnt.execution( wc );
			
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.CURRENT)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_CURRENT.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.East)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_East.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.North)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_North.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthEast)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_NorthEast.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthWest)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_NorthWest.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.South)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_South.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthEast)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_SouthEast.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthWest)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_SouthWest.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.West)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_West.getIndex() ) );
			
			wc.getDirection( dw ).remAgent( food );
			
			BuildingGenomeHelper.show(testAnt, wc);
			
		} // END "for (DirectionWorld dw : selectedDirs)"
		
		// ***** Export Ant as a TXT file !
		BuildingGenomeHelper.exportAsTXTfile("TestStimulusDecisionAllDirectionsFOOD.txt", testAnt);
		DataExporterAndViewAnalysis.testFileExists( AntBuildingGenomeComplete.SRC_TEST_RSC + "TestStimulusDecisionAllDirectionsFOOD.txt" );
		
	}
	
	@Test
	void testStimulusDecisionAllDirectionsTwoPheromonesAndFOOD() {
		
		Chromosome basicChromosome			= new Chromosome();
		
		StimulusDecisionBuilder sdgb		= new StimulusDecisionBuilder();
		List<DirectionWorld> selectedDirs	= DirectionWorld.values2DasList();
		List<SomeChemicals> someChems		= Arrays.asList(SomeChemicals.PHEROMONE_00, 
															SomeChemicals.PHEROMONE_01);
		
		basicChromosome.setName( "Pheromones and FOOD detection !" );
		for (DirectionWorld dw : selectedDirs) {
			
			for (SomeChemicals scPHE : someChems) {
	
				// ***** Detect Pheromone_0X at current WorldCase with threshold 10, change variable 700 set to 5
				// basicChromosome.setName( scPHE.getName() + " detection !" );
				GeneratorReceptionChemicals arc = GeneratorReceptionChemicals.getFrom( dw, scPHE );
				basicChromosome.addGene( sdgb.perception( true ).object( false ) 
						.indicator( scPHE.getIndex() ).threshold( 10 )
						.attribute( dw.getIndex() )
						.varia( arc.getIndex() )
						.value( 5 ).script( 0 )
					.name( "StimulusGene" + dw.getName() + "_" + arc.getName() )
					.mutate( true ).duplicate( true ).delete( true ).activ( true )
					.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
					.build() );
				
			} // END "for (SomeChemicals scPHE : someChems)"
			
			// ***** FOOD Detection !!
			GeneratorReceptionChemicals arc = GeneratorReceptionChemicals.getFrom(dw, ObjectType.FOOD);
			basicChromosome.addGene( sdgb.perception( true ).object( true ) 
					.indicator( ObjectType.FOOD.getIndex() ).threshold( 0 )
					.attribute( dw.getIndex() )
					.varia( arc.getIndex() ).value( 5 ).script( 0 )
				.name( "StimulusGene_" + arc.getName() )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
				.build() );
			
		} // END "for (DirectionWorld dw : selectedDirs)"
		
		Ant testAnt = new Ant( basicChromosome );
		Assertions.assertNotNull( testAnt );
		Assertions.assertEquals(1, testAnt.getGenome().size());
		
		testAnt.setRank("Rank Test");
		testAnt.setNameCommon("Test Starting Ant");
		testAnt.setNameBiosilico("AntHill Ant Example");
		testAnt.setDivision("TESTS");
		
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
		
		Logger.printlnLog(LoggerLevel.LL_DEBUG, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_DEBUG, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_East.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_North.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_NorthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_NorthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_South.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_SouthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_SouthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_West.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_CURRENT.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_East.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_North.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_NorthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_NorthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_South.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_SouthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_SouthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_West.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_CURRENT.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_East.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_North.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_NorthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_NorthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_South.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_SouthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_SouthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_West.getIndex() ) );
	
		// ***** one MORE execution in this context
		testAnt.execution( wc );
		
		for (DirectionWorld dw : selectedDirs) {
			
			for (SomeChemicals scPHE : someChems) {
				
				wc.getDirection( dw ).getChemicals().setVariable(scPHE.getIndex(), 11);
				
				// ***** one MORE execution in this context
				testAnt.execution( wc );
				
				switch( scPHE ) {
				case PHEROMONE_00 :
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.CURRENT)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.East)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_East.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.North)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_North.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthEast)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_NorthEast.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthWest)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_NorthWest.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.South)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_South.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthEast)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_SouthEast.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthWest)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_SouthWest.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.West)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_West.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_CURRENT.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_East.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_North.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_NorthEast.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_NorthWest.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_South.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_SouthEast.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_SouthWest.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_West.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_CURRENT.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_East.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_North.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_NorthEast.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_NorthWest.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_South.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_SouthEast.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_SouthWest.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_West.getIndex() ) );
					break;
				case PHEROMONE_01 : 
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_East.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_North.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_NorthEast.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_NorthWest.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_South.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_SouthEast.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_SouthWest.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_West.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.CURRENT)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_CURRENT.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.East)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_East.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.North)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_North.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthEast)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_NorthEast.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthWest)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_NorthWest.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.South)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_South.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthEast)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_SouthEast.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthWest)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_SouthWest.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.West)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_West.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_CURRENT.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_East.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_North.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_NorthEast.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_NorthWest.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_South.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_SouthEast.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_SouthWest.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_West.getIndex() ) );
					break;
				default:
					String msg = "NOT recognized Chemical !";
					Logger.printlnLog(LoggerLevel.LL_ERROR, msg);
					Assertions.fail( msg );
					break;
				}
				
				wc.getDirection( dw ).getChemicals().setVariable(scPHE.getIndex(),  0);
			
			} // END "for (SomeChemicals scPHE : someChems)"
			
			for (SomeChemicals scPHE : someChems) {
				for (DirectionWorld dwCleaning : selectedDirs) 
					{ wc.getDirection( dwCleaning ).getChemicals()
						.setVariable(scPHE.getIndex(),  0); }
			} // END "for (SomeChemicals scPHE : someChems)"
			
			BuildingGenomeHelper.show(testAnt, wc);
			
			// ***** Test on food detection !
			Organism food = new TestObjectFoodEgg();
			wc.getDirection( dw ).addAgent( food );
			
			// ***** execution
			testAnt.execution( wc );
			
			Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_CURRENT.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_East.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_North.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_NorthEast.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_NorthWest.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_South.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_SouthEast.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_SouthWest.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_00_West.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_CURRENT.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_East.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_North.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_NorthEast.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_NorthWest.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_South.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_SouthEast.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_SouthWest.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.PHEROMONE_01_West.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.CURRENT)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_CURRENT.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.East)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_East.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.North)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_North.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthEast)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_NorthEast.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthWest)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_NorthWest.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.South)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_South.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthEast)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_SouthEast.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthWest)) ?	 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_SouthWest.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.West)) ?		 5 : 0 ), testAnt.getChemicals().getVariable( GeneratorReceptionChemicals.FOOD_West.getIndex() ) );
			
			wc.getDirection( dw ).remAgent( food );
			
			BuildingGenomeHelper.show(testAnt, wc);
			
		} // END "for (DirectionWorld dw : selectedDirs)"
		
		// ***** Export Ant as a TXT file !
		BuildingGenomeHelper.exportAsTXTfile("TestStimulusDecisionAllDirectionsTwoPheromonesAndFOOD.txt", testAnt);
		DataExporterAndViewAnalysis.testFileExists( AntBuildingGenomeComplete.SRC_TEST_RSC + "TestStimulusDecisionAllDirectionsTwoPheromonesAndFOOD.txt" );
		
	}
	
	@Test
	void testStimulusDecisionHasFOOD() {
		
		Chromosome basicChromosome			= new Chromosome();
		
		StimulusDecisionBuilder sdgb		= new StimulusDecisionBuilder();

		basicChromosome.setName( "HAS FOOD detection !" );		
		// ***** FOOD Detection !! (internal) => HAS FOOD !
		basicChromosome.addGene( sdgb.perception( false ).object( false ) 
				.indicator( ObjectType.FOOD.getIndex() )
				.threshold( 0 )
				.attribute( AgentType.BIOSILICO_VIRIDITA.getIndex() )
				.varia( SomeChemicals.PHEROMONE_09.getIndex() ).value( 45 )
				.script( DecisionType.HAS.getIndex() )
			.name( "StimulusDecision : HAS FOOD" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		
		Ant testAnt = new Ant( basicChromosome );
		Assertions.assertNotNull( testAnt );
		Assertions.assertEquals(1, testAnt.getGenome().size());
		
		testAnt.setRank("Rank Test");
		testAnt.setNameCommon("Test Starting Ant");
		testAnt.setNameBiosilico("AntHill Ant Example");
		testAnt.setDivision("TESTS");
		
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
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		// ***** one MORE execution in this context
		testAnt.execution( wc );
		
		// ***** Test on food detection !
		Organism food = new TestObjectFoodEgg();
		wc.addAgent( food );
		
		// ***** execution
		testAnt.execution( wc );
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHEROMONE_09.getIndex()));
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		wc.remAgent( food );
		testAnt.addAgent( food );
		// testAnt.getChemicals().setVariable(ObjectType.FOOD.getIndex(), 1);
		
		// ***** execution
		testAnt.execution( wc );
		
		Assertions.assertEquals( 45, testAnt.getChemicals().getVariable(SomeChemicals.PHEROMONE_09.getIndex()));
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		// ***** Export Ant as a TXT file !
		BuildingGenomeHelper.exportAsTXTfile("TestStimulusDecisionHasFOOD.txt", testAnt);
		DataExporterAndViewAnalysis.testFileExists( AntBuildingGenomeComplete.SRC_TEST_RSC + "TestStimulusDecisionHasFOOD.txt" );
		
	}
	
	@Test
	void testWireBrainDecisionMOVEAWAY() {

		Chromosome chrBrain					= new Chromosome();
		Chromosome chrEmitReceiv			= new Chromosome();
		Chromosome chrInstinct				= new Chromosome();
		Chromosome chrSDdecision			= new Chromosome();
		Chromosome chrSDdetection			= new Chromosome();
		
		List<DirectionWorld> selectedDirs	= DirectionWorld.values2DasList();
		List<SomeChemicals> allPheromones	= Arrays.asList(SomeChemicals.PHEROMONE_00, 
															SomeChemicals.PHEROMONE_01, SomeChemicals.PHEROMONE_02, SomeChemicals.PHEROMONE_03, 
															SomeChemicals.PHEROMONE_04, SomeChemicals.PHEROMONE_05, SomeChemicals.PHEROMONE_06, 
															SomeChemicals.PHEROMONE_07, SomeChemicals.PHEROMONE_08, SomeChemicals.PHEROMONE_09 );
		
		BrainLobeGeneBuilder blgb			= new BrainLobeGeneBuilder();
		EmitterReceptorBuilder erb			= new EmitterReceptorBuilder();
		InitialConcentrationBuilder icb		= new InitialConcentrationBuilder();
		InstinctBuilder igb					= new InstinctBuilder();
		StimulusDecisionBuilder sdgb		= new StimulusDecisionBuilder();
		BiochemicalReactionBuilder brb		= new BiochemicalReactionBuilder();
		
		// ***** ***** ***** ***** ***** 
		// ***** Building Brain and BrainLobe Genes
		chrBrain.setName( "Brain and Connections" );
		chrBrain.addGene( AntBuildingGenomeDataHelper.buildBasicBrainGene() );
		// *** Input neurons : one group of 27 neurons => pheromones_00 directions ! (9 used for now)
		chrBrain.addGene( blgb 
				.rest( 0 ).threshold( 10 ).desc( 5 )
				.dmin( 0 ).dmax( 0 ).prox( 0 )
				.repr( false ).repy( 0 ).wta( false )
				.heigth( 1 ).width( 27 ).posx( 0 ).posy( 0 )
				.replace( false )
			.name( "Brain Lobe Gene Data Input DW Ph_00 " )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
			.build() );
		// *** Input neurons : one group of 27 neurons => pheromones_01 directions ! (9 used for now)
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
		// *** Input neurons : one group of 27 neurons => FOOD directions ! (9 used for now)
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
		
		// *** Output neurons : two (2) groups of 10 WTA neurons
		// // // first group : to be linked with choice / decision of directions (for now only 9 out of 27)
		// // // + MOVE_AWAY + GET(FOOD) + DROP(FOOD)
		chrBrain.addGene( blgb 
				.rest( 0 ).threshold( 20 ).desc( 10 )
				.dmin( 5 ).dmax( 9 ).prox( 2 )
				.repr( false ).repy( 0 ).wta( true )
				.heigth( 1 ).width( selectedDirs.size() + 3 ).posx( 89 ).posy( 0 )
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
				.heigth( 1 ).width( 10 ).posx( 89 ).posy( 50 )
				.replace( false )
			.name( "Brain Lobe Gene Data Output 2 : other decisions / Decision Lobe" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
			.build() );
		
		// ***** ***** ***** ***** ***** 
		chrEmitReceiv.setName("EmitterReceptor's input / output");
		// *** EmitterReceptor Input => to first line (inputs) of Brain !! (Ph_00 & direction)
		String nameFirstPartERinput	= "ER receptor input ";
		
		// *** *** *** StimulusDecision : more impact if PHEROMONE_01 if HIGH
		GeneratorReceptionChemicals arcCurrentPh01 = GeneratorReceptionChemicals.getFrom( DirectionWorld.CURRENT, SomeChemicals.PHEROMONE_01 );
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
		// DONE ER MOVE_AWAY ... 
		final int outputPosYMOVEAWAY = selectedDirs.size() + 0;
		chrEmitReceiv.addGene( erb
				.variable( DecisionType.MOVE_AWAY.getIndex() ).ioput( 10 )
				.posx( 89 ).posy( outputPosYMOVEAWAY ).threshold( 10 )
				.internal( true ).receptor( false )
			.name( nameFirstPartERoutput + DecisionType.MOVE_AWAY.getName() )
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
		// Linked instinct for MOVE_AWAY
		chrInstinct.addGene( igb
				.inputPosX( 3 ).inputPosY( SomeChemicals.PHEROMONE_00.getIndex() - BuildingGenomeHelper.indexLessRemovePheromone )
				.outputPosX( 89 ).outputPosY( outputPosYMOVEAWAY )
				.weight(  5 ).variable( SomeChemicals.GLUCOSE.getIndex() )
				.threshold( 5 ).check( false ).positiv( true )
				.name( "Instinct (" + 0 + ", " + 0 + ") to (89, " + outputPosYMOVEAWAY + ") " 
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
					.outputPosX( 89 ).outputPosY( outputPosYMOVEAWAY )
					.weight( 1 ).variable( SomeChemicals.GLUCOSE.getIndex() )
					.threshold( 5 ).check( false ).positiv( false )
					.name( "Instinct (" + i + ", " + neuronYIndex + ") to (89, " + outputPosYMOVEAWAY + ") " 
								+ dw.getName() + " => MOVE_AWAY [" + SomeChemicals.GLUCOSE.getName() + "] (negative)")
						.mutate( true ).duplicate( true ).delete( true ).activ( true )
						.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
						.build() );
			} // END "for (DirectionWorld dw : selectedDirs)"
		});

		// *** StimulusDecision for Decisions !! 
		chrSDdecision.setName( "StimulusDecision : Taking and Apply Decisions" );
		// Decision MOVE_AWAY
		chrSDdecision.addGene( sdgb.perception( false ).object( true ) 
				.indicator( DecisionType.MOVE_AWAY.getIndex() )			// ... 
				.threshold( 0 ).attribute( 0 ).varia( 0 ).value( 0 )	// attribute && varia && value are irrelevant here !
				.script( DecisionType.MOVE_AWAY.getIndex() )			// What to do !
			.name( "StimulusDecisionGene_MOVEAWAY" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		
		// ***** ***** ***** ***** ***** 
		chrSDdetection.setName( "StimulusDecision : Pheromones and FOOD detection !" );
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
		
		Assertions.assertEquals(  7, testAnt.getGenome().get( 0 ).length());
		Assertions.assertEquals( 12, testAnt.getGenome().get( 1 ).length());
		Assertions.assertEquals( 29, testAnt.getGenome().get( 2 ).length());
		Assertions.assertEquals(  1, testAnt.getGenome().get( 3 ).length());
		Assertions.assertEquals(  2, testAnt.getGenome().get( 4 ).length());
		
		Integer genesNumber = ReproductionHelper.sizeOfGenome( testAnt );
		Logger.printlnLog(LoggerLevel.LL_DEBUG, genesNumber.toString());
		Assertions.assertEquals( 51, genesNumber.intValue() );
		
		List<Integer> listLengthGenomes = testAnt.getGenome().stream().map( Chromosome::length ).collect(Collectors.toList());
		Assertions.assertEquals( 51, listLengthGenomes.stream().reduce(0, Integer::sum).intValue());
		
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
		
		Neuron neuronMOVEAWAY_90o = testAnt.getBrain().getNeuronAt(89, outputPosYMOVEAWAY);
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
		
		// ***** Cleaning before recording file of genome !!
		neuronMOVEAWAY_90o.setActivity( 0 );
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		// ***** Export Ant as a TXT file !
		BuildingGenomeHelper.exportAsTXTfile("TestWireBrainDecisionMOVEAWAY.txt", testAnt);
		DataExporterAndViewAnalysis.testFileExists( AntBuildingGenomeComplete.SRC_TEST_RSC + "TestWireBrainDecisionMOVEAWAY.txt" );
		
	}
	
	@Test
	void testBiochemicalReactions01() {

		Chromosome chrInitialConcentrations	= new Chromosome();
		Chromosome chrBiochemicalReactions1	= new Chromosome();
		Chromosome chrBiochemicalReactions2	= new Chromosome();
		
		InitialConcentrationBuilder icb		= new InitialConcentrationBuilder();
		BiochemicalReactionBuilder brb		= new BiochemicalReactionBuilder();
		StimulusDecisionBuilder sdgb		= new StimulusDecisionBuilder();
		
		chrInitialConcentrations.setName("Initial Concentration's");
		chrBiochemicalReactions1.setName("Biochemical Reaction's");
		chrBiochemicalReactions2.setName("Biochemical Reaction's (2)");
		
		chrInitialConcentrations.addGene( icb	
				.varia( SomeChemicals.GLUCOSE.getIndex() ).value( 25 )
				.name( "InitConc GLUCOSE 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		chrInitialConcentrations.addGene( icb	
				.varia( SomeChemicals.STARCH.getIndex() ).value( 25 )
				.name( "InitConc STARCH 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		chrInitialConcentrations.addGene( icb	
				.varia( SomeChemicals.ENERGY_HEAT.getIndex() ).value( 25 )
				.name( "InitConc STARCH 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		chrInitialConcentrations.addGene( icb	
				.varia( SomeChemicals.GLYCOGEN.getIndex() ).value( 25 )
				.name( "InitConc GLYCOGEN 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		chrInitialConcentrations.addGene( icb	
				.varia( SomeChemicals.CARBON_DIOXYDE.getIndex() ).value( 25 )
				.name( "InitConc CARBON_DIOXYDE 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		chrInitialConcentrations.addGene( icb	
				.varia( SomeChemicals.DIOXYGEN.getIndex() ).value( 25 )
				.name( "InitConc CARBON_DIOXYDE 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		chrInitialConcentrations.addGene( icb	
				.varia( SomeChemicals.WATER.getIndex() ).value( 25 )
				.name( "InitConc WATER 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );

//		chrInitialConcentrations.addGene( icb	
//		.varia( SomeChemicals.PHOSPHOR.getIndex() ).value( 25 )
//		.name( "InitConc PHOSPHOR 25" )
//		.mutate( true ).duplicate( true ).delete( true ).activ( true )
//		.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
//chrInitialConcentrations.addGene( icb	
//		.varia( SomeChemicals.AMP.getIndex() ).value( 25 )
//		.name( "InitConc AMP 25" )
//		.mutate( true ).duplicate( true ).delete( true ).activ( true )
//		.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		
		// EMISSION / RECEPTION ***** Use Heat NRJ, Oxygen !
		chrInitialConcentrations.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.ENERGY_HEAT.getIndex() )
				.varia( SomeChemicals.ENERGY_HEAT.getIndex() )
				.value( 5 ).script( DecisionType.RECEIVE.getIndex() )
			.name( "StimulusDecision RECEIVE ENERGY_HEAT" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		chrInitialConcentrations.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.DIOXYGEN.getIndex() )
				.varia( SomeChemicals.DIOXYGEN.getIndex() )
				.value( 10 ).script( DecisionType.RECEIVE.getIndex() )
			.name( "StimulusDecision RECEIVE DIOXYGEN" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		chrInitialConcentrations.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.CARBON_DIOXYDE.getIndex() )
				.varia( SomeChemicals.CARBON_DIOXYDE.getIndex() )
				.value( 5 ).script( DecisionType.EMIT.getIndex() )
			.name( "StimulusDecision EMIT DIOXYGEN" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		chrInitialConcentrations.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.WATER.getIndex() )
				.varia( SomeChemicals.WATER.getIndex() )
				.value( 5 ).script( DecisionType.EMIT.getIndex() )
			.name( "StimulusDecision EMIT WATER" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		
		// ***** BiochemicalReaction's
		chrBiochemicalReactions1.addGene( brb
				.achem(SomeChemicals.STARCH.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.NEUTRAL.getIndex()).bcoef( 0 )
				.cchem(SomeChemicals.GLUCOSE.getIndex()).ccoef( 6 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 1 )
			.name( "BiochemicalReaction : 1 STARCH to 6 GLUCOSE !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		chrBiochemicalReactions1.addGene( brb
				.achem(SomeChemicals.HEXOKINASE.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.GLUCOSE.getIndex()).bcoef( 1 )
				.cchem(SomeChemicals.CARBON_DIOXYDE.getIndex()).ccoef( 6 )
				.dchem(SomeChemicals.WATER.getIndex()).dcoef( 6 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : HEXOKINASE + GLUCOSE => CO2 + H20 !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		chrBiochemicalReactions1.addGene( brb
				.achem(SomeChemicals.HEXOKINASE.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.FRUCTOSE.getIndex()).bcoef( 1 )
				.cchem(SomeChemicals.CARBON_DIOXYDE.getIndex()).ccoef( 5 )
				.dchem(SomeChemicals.WATER.getIndex()).dcoef( 5 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : HEXOKINASE + FRUCTOSE => CO2 + H20 !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		chrBiochemicalReactions1.addGene( brb
				.achem(SomeChemicals.GLUCOSE.getIndex()).acoef( 10 )
				.bchem(SomeChemicals.DIOXYGEN.getIndex()).bcoef( 5 )
				.cchem(SomeChemicals.GLYCOGEN.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : 10 GLUCOSE to 1 GLYCOGEN !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		chrBiochemicalReactions1.addGene( brb
				.achem(SomeChemicals.FRUCTOSE.getIndex()).acoef( 15 )
				.bchem(SomeChemicals.DIOXYGEN.getIndex()).bcoef( 5 )
				.cchem(SomeChemicals.GLYCOGEN.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : 15 FRUCTOSE to 1 GLYCOGEN !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		chrBiochemicalReactions1.addGene( brb
				.achem(SomeChemicals.GLYCOGEN.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.NEUTRAL.getIndex()).bcoef( 0 )
				.cchem(SomeChemicals.GLUCOSE.getIndex()).ccoef( 10 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 1 )
			.name( "BiochemicalReaction : 1 GLYCOGEN to 10 GLUCOSE !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		chrBiochemicalReactions1.addGene( brb
				.achem(SomeChemicals.GLYCOGEN.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.HEXOKINASE.getIndex()).bcoef( 1 )
				.cchem(SomeChemicals.GLUCOSE.getIndex()).ccoef( 8 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 3 )
			.name( "BiochemicalReaction : 1 GLYCOGEN to 8 GLUCOSE ! (hexokinase catalysis)" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		
		chrBiochemicalReactions2.addGene( brb
				.achem(SomeChemicals.GLUCOSE.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ATP.getIndex()).bcoef( 1 )
				.cchem(SomeChemicals.G6P.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.ADP.getIndex()).dcoef( 1 )
				.kmvm( 1 )
			.name( "BRGlyc01:Glucose=>G6P" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		chrBiochemicalReactions2.addGene( brb
				.achem(SomeChemicals.FRUCTOSE.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ATP.getIndex()).bcoef( 1 )
				.cchem(SomeChemicals.F6P.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.ADP.getIndex()).dcoef( 1 )
				.kmvm( 1 )
			.name( "BRGlyc01:Fructose=>F6P" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		chrBiochemicalReactions2.addGene( brb
				.achem(SomeChemicals.PHOSPHOR.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_HEAT.getIndex()).bcoef( 3 )
				.cchem(SomeChemicals.COMBINATOR_3.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : P + NRJ(HEAT) => Combinator 3" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		chrBiochemicalReactions2.addGene( brb
				.achem(SomeChemicals.AMP.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.COMBINATOR_3.getIndex()).bcoef( 1 )
				.cchem(SomeChemicals.ADP.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : AMP + P => ADP" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		chrBiochemicalReactions2.addGene( brb
				.achem(SomeChemicals.ADP.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.COMBINATOR_3.getIndex()).bcoef( 1 )
				.cchem(SomeChemicals.ATP.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : ADP + P => ATP" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		
		// ***** Instantiate Ant and Organism With Genome !!
		Ant testAnt = new Ant( Arrays.asList( chrInitialConcentrations, chrBiochemicalReactions1, chrBiochemicalReactions2 ) );
		Assertions.assertNotNull( testAnt );
		Assertions.assertEquals(3, testAnt.getGenome().size());

		Assertions.assertEquals(chrInitialConcentrations.length(), 	testAnt.getGenome().get( 0 ).length());
		Assertions.assertEquals(chrBiochemicalReactions1.length(), 	testAnt.getGenome().get( 1 ).length());
		Assertions.assertEquals(chrBiochemicalReactions2.length(), 	testAnt.getGenome().get( 2 ).length());
		
		Assertions.assertEquals( 11, testAnt.getGenome().get( 0 ).length());
		Assertions.assertEquals(  7, testAnt.getGenome().get( 1 ).length());
		Assertions.assertEquals(  5, testAnt.getGenome().get( 2 ).length());
		
		Integer genesNumber = ReproductionHelper.sizeOfGenome( testAnt );
		Logger.printlnLog(LoggerLevel.LL_DEBUG, genesNumber.toString());
		Assertions.assertEquals( 23, genesNumber.intValue() );
		
		List<Integer> listLengthGenomes = testAnt.getGenome().stream().map( Chromosome::length ).collect(Collectors.toList());
		Assertions.assertEquals( 23, listLengthGenomes.stream().reduce(0, Integer::sum).intValue());
		
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
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_STRICT_CHEM).forEach( k -> {
			Assertions.assertEquals( 0, testAnt.getChemicals().getVariable(k) );
		});
		
		// ***** one execution in this context
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // to permit correct initialization of "starting genes"

		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  5, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  5, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals( 11, testAnt.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 24, testAnt.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 20, testAnt.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 20, testAnt.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals( 27, testAnt.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals( 10, testAnt.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		// ***** one MORE execution in this context 1
		testAnt.execution( wc );
		
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 10, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 10, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals( 17, testAnt.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 23, testAnt.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 15, testAnt.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 15, testAnt.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals( 27, testAnt.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		// ***** one MORE execution in this context 2
		testAnt.execution( wc );
		
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 15, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 15, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals( 23, testAnt.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 22, testAnt.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 10, testAnt.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 10, testAnt.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals( 27, testAnt.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		// ***** one MORE execution in this context
		testAnt.getChemicals().setVariable(SomeChemicals.FRUCTOSE.getIndex(), 20);
		testAnt.execution( wc );
		
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 20, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 20, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals( 39, testAnt.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals( 20, testAnt.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 21, testAnt.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals( 26, testAnt.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		// ***** one MORE execution in this context
		testAnt.getChemicals().setVarPlus(SomeChemicals.FRUCTOSE.getIndex(), 25);
		testAnt.getChemicals().setVariable(SomeChemicals.HEXOKINASE.getIndex(), 1);
		testAnt.execution( wc );
		
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 25, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 25, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals( 54, testAnt.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals( 45, testAnt.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 20, testAnt.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  6, testAnt.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  6, testAnt.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		// ***** one MORE execution in this context
		testAnt.execution( wc );
		
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 30, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 30, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals( 70, testAnt.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals( 45, testAnt.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 19, testAnt.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals( 24, testAnt.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		// ***** one MORE execution in this context
		testAnt.getChemicals().setVarPlus(SomeChemicals.HEXOKINASE.getIndex(), 9);
		testAnt.execution( wc );
		
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 30, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 30, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals( 77, testAnt.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals( 45, testAnt.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 18, testAnt.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 55, testAnt.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 55, testAnt.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals( 23, testAnt.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		// ***** one MORE execution in this context
		testAnt.getChemicals().setVarPlus(SomeChemicals.HEXOKINASE.getIndex(), 5);
		testAnt.execution( wc );
		
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 35, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 35, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals( 88, testAnt.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals( 45, testAnt.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 17, testAnt.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 80, testAnt.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 80, testAnt.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals( 22, testAnt.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		// ***** Export Ant as a TXT file !
		BuildingGenomeHelper.exportAsTXTfile("TestBiochemicalReactions.txt", testAnt);
		
		BuildingGenomeHelper.exportGenome(AntBuildingGenomeComplete.SRC_TEST_RSC + "GenomeBiochemicalReactions.txt", testAnt);
		
		DataExporterAndViewAnalysis.testFileExists( AntBuildingGenomeComplete.SRC_TEST_RSC + "TestBiochemicalReactions.txt" );
		DataExporterAndViewAnalysis.testFileExists( AntBuildingGenomeComplete.SRC_TEST_RSC + "GenomeBiochemicalReactions.txt" );
		
	}

	@Test
	void testBiochemicalReactions02() {

		Chromosome chrInitialConcentrations	= new Chromosome();
		Chromosome chrBiochemicalReactions1	= new Chromosome();
		Chromosome chrBiochemicalReactions2	= new Chromosome();
		
		InitialConcentrationBuilder icb		= new InitialConcentrationBuilder();
		BiochemicalReactionBuilder brb		= new BiochemicalReactionBuilder();
		StimulusDecisionBuilder sdgb		= new StimulusDecisionBuilder();
		
		chrInitialConcentrations.setName("Initial Concentration's");
		chrBiochemicalReactions1.setName("Biochemical Reaction's");
		chrBiochemicalReactions2.setName("Biochemical Reaction's (2)");
		
		chrInitialConcentrations.addGene( icb	
				.varia( SomeChemicals.GLUCOSE.getIndex() ).value( 25 )
				.name( "InitConc GLUCOSE 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		chrInitialConcentrations.addGene( icb	
				.varia( SomeChemicals.STARCH.getIndex() ).value( 25 )
				.name( "InitConc STARCH 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		chrInitialConcentrations.addGene( icb	
				.varia( SomeChemicals.ENERGY_HEAT.getIndex() ).value( 25 )
				.name( "InitConc STARCH 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		chrInitialConcentrations.addGene( icb	
				.varia( SomeChemicals.GLYCOGEN.getIndex() ).value( 25 )
				.name( "InitConc GLYCOGEN 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		chrInitialConcentrations.addGene( icb	
				.varia( SomeChemicals.CARBON_DIOXYDE.getIndex() ).value( 25 )
				.name( "InitConc CARBON_DIOXYDE 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		chrInitialConcentrations.addGene( icb	
				.varia( SomeChemicals.DIOXYGEN.getIndex() ).value( 25 )
				.name( "InitConc CARBON_DIOXYDE 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		chrInitialConcentrations.addGene( icb	
				.varia( SomeChemicals.WATER.getIndex() ).value( 25 )
				.name( "InitConc WATER 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );

//		chrInitialConcentrations.addGene( icb	
//		.varia( SomeChemicals.PHOSPHOR.getIndex() ).value( 25 )
//		.name( "InitConc PHOSPHOR 25" )
//		.mutate( true ).duplicate( true ).delete( true ).activ( true )
//		.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
//chrInitialConcentrations.addGene( icb	
//		.varia( SomeChemicals.AMP.getIndex() ).value( 25 )
//		.name( "InitConc AMP 25" )
//		.mutate( true ).duplicate( true ).delete( true ).activ( true )
//		.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		
		// EMISSION / RECEPTION ***** Use Heat NRJ, Oxygen !
		chrInitialConcentrations.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.ENERGY_HEAT.getIndex() )
				.varia( SomeChemicals.ENERGY_HEAT.getIndex() )
				.value( 5 ).script( DecisionType.RECEIVE.getIndex() )
			.name( "StimulusDecision RECEIVE ENERGY_HEAT" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		chrInitialConcentrations.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.DIOXYGEN.getIndex() )
				.varia( SomeChemicals.DIOXYGEN.getIndex() )
				.value( 10 ).script( DecisionType.RECEIVE.getIndex() )
			.name( "StimulusDecision RECEIVE DIOXYGEN" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		chrInitialConcentrations.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.CARBON_DIOXYDE.getIndex() )
				.varia( SomeChemicals.CARBON_DIOXYDE.getIndex() )
				.value( 5 ).script( DecisionType.EMIT.getIndex() )
			.name( "StimulusDecision EMIT DIOXYGEN" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		chrInitialConcentrations.addGene(	sdgb.perception( false ).object( false ) 
				.indicator( DirectionWorld.CURRENT.getIndex() ).threshold( 0 )
				.attribute( SomeChemicals.WATER.getIndex() )
				.varia( SomeChemicals.WATER.getIndex() )
				.value( 5 ).script( DecisionType.EMIT.getIndex() )
			.name( "StimulusDecision EMIT WATER" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		
		// ***** BiochemicalReaction's
		chrBiochemicalReactions1.addGene( brb
				.achem(SomeChemicals.STARCH.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.NEUTRAL.getIndex()).bcoef( 0 )
				.cchem(SomeChemicals.GLUCOSE.getIndex()).ccoef( 6 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 1 )
			.name( "BiochemicalReaction : 1 STARCH to 6 GLUCOSE !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		chrBiochemicalReactions1.addGene( brb
				.achem(SomeChemicals.HEXOKINASE.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.GLUCOSE.getIndex()).bcoef( 1 )
				.cchem(SomeChemicals.CARBON_DIOXYDE.getIndex()).ccoef( 6 )
				.dchem(SomeChemicals.WATER.getIndex()).dcoef( 6 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : HEXOKINASE + GLUCOSE => CO2 + H20 !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		chrBiochemicalReactions1.addGene( brb
				.achem(SomeChemicals.HEXOKINASE.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.FRUCTOSE.getIndex()).bcoef( 1 )
				.cchem(SomeChemicals.CARBON_DIOXYDE.getIndex()).ccoef( 5 )
				.dchem(SomeChemicals.WATER.getIndex()).dcoef( 5 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : HEXOKINASE + FRUCTOSE => CO2 + H20 !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		chrBiochemicalReactions1.addGene( brb
				.achem(SomeChemicals.GLUCOSE.getIndex()).acoef( 10 )
				.bchem(SomeChemicals.DIOXYGEN.getIndex()).bcoef( 5 )
				.cchem(SomeChemicals.GLYCOGEN.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : 10 GLUCOSE to 1 GLYCOGEN !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		chrBiochemicalReactions1.addGene( brb
				.achem(SomeChemicals.FRUCTOSE.getIndex()).acoef( 15 )
				.bchem(SomeChemicals.DIOXYGEN.getIndex()).bcoef( 5 )
				.cchem(SomeChemicals.GLYCOGEN.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : 15 FRUCTOSE to 1 GLYCOGEN !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		chrBiochemicalReactions1.addGene( brb
				.achem(SomeChemicals.GLYCOGEN.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.NEUTRAL.getIndex()).bcoef( 0 )
				.cchem(SomeChemicals.GLUCOSE.getIndex()).ccoef( 10 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 1 )
			.name( "BiochemicalReaction : 1 GLYCOGEN to 10 GLUCOSE !" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		chrBiochemicalReactions1.addGene( brb
				.achem(SomeChemicals.GLYCOGEN.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.HEXOKINASE.getIndex()).bcoef( 1 )
				.cchem(SomeChemicals.GLUCOSE.getIndex()).ccoef( 8 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 3 )
			.name( "BiochemicalReaction : 1 GLYCOGEN to 8 GLUCOSE ! (hexokinase catalysis)" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		
		chrBiochemicalReactions2.addGene( brb
				.achem(SomeChemicals.GLUCOSE.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ATP.getIndex()).bcoef( 1 )
				.cchem(SomeChemicals.G6P.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.ADP.getIndex()).dcoef( 1 )
				.kmvm( 1 )
			.name( "BRGlyc01:Glucose=>G6P" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		chrBiochemicalReactions2.addGene( brb
				.achem(SomeChemicals.FRUCTOSE.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ATP.getIndex()).bcoef( 1 )
				.cchem(SomeChemicals.F6P.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.ADP.getIndex()).dcoef( 1 )
				.kmvm( 1 )
			.name( "BRGlyc01:Fructose=>F6P" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		chrBiochemicalReactions2.addGene( brb
				.achem(SomeChemicals.PHOSPHOR.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.ENERGY_HEAT.getIndex()).bcoef( 3 )
				.cchem(SomeChemicals.COMBINATOR_3.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : P + NRJ(HEAT) => Combinator 3" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		chrBiochemicalReactions2.addGene( brb
				.achem(SomeChemicals.AMP.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.COMBINATOR_3.getIndex()).bcoef( 1 )
				.cchem(SomeChemicals.ADP.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : AMP + P => ADP" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		chrBiochemicalReactions2.addGene( brb
				.achem(SomeChemicals.ADP.getIndex()).acoef( 1 )
				.bchem(SomeChemicals.COMBINATOR_3.getIndex()).bcoef( 1 )
				.cchem(SomeChemicals.ATP.getIndex()).ccoef( 1 )
				.dchem(SomeChemicals.NEUTRAL.getIndex()).dcoef( 0 )
				.kmvm( 10 )
			.name( "BiochemicalReaction : ADP + P => ATP" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		
		// ***** Instantiate Ant and Organism With Genome !!
		Ant testAnt = new Ant( Arrays.asList( chrInitialConcentrations, chrBiochemicalReactions1, chrBiochemicalReactions2 ) );
		Assertions.assertNotNull( testAnt );
		Assertions.assertEquals(3, testAnt.getGenome().size());

		Assertions.assertEquals(chrInitialConcentrations.length(), 	testAnt.getGenome().get( 0 ).length());
		Assertions.assertEquals(chrBiochemicalReactions1.length(), 	testAnt.getGenome().get( 1 ).length());
		Assertions.assertEquals(chrBiochemicalReactions2.length(), 	testAnt.getGenome().get( 2 ).length());
		
		Assertions.assertEquals( 11, testAnt.getGenome().get( 0 ).length());
		Assertions.assertEquals(  7, testAnt.getGenome().get( 1 ).length());
		Assertions.assertEquals(  5, testAnt.getGenome().get( 2 ).length());
		
		Integer genesNumber = ReproductionHelper.sizeOfGenome( testAnt );
		Logger.printlnLog(LoggerLevel.LL_DEBUG, genesNumber.toString());
		Assertions.assertEquals( 23, genesNumber.intValue() );
		
		List<Integer> listLengthGenomes = testAnt.getGenome().stream().map( Chromosome::length ).collect(Collectors.toList());
		Assertions.assertEquals( 23, listLengthGenomes.stream().reduce(0, Integer::sum).intValue());
		
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
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_STRICT_CHEM).forEach( k -> {
			Assertions.assertEquals( 0, testAnt.getChemicals().getVariable(k) );
		});
		
		// ***** With DIOXYGEN !!
		wc.getChemicals().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 100);
		
		// ***** one execution in this context
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // to permit correct initialization of "starting genes"

		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  5, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  5, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals( 11, testAnt.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 24, testAnt.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 20, testAnt.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 20, testAnt.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals( 27, testAnt.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals( 20, testAnt.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		BuildingGenomeHelper.show(testAnt, wc);
		
		// ***** one MORE execution in this context 1
		testAnt.execution( wc );
		
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 10, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 10, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals( 17, testAnt.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 23, testAnt.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 15, testAnt.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 15, testAnt.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals( 27, testAnt.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		// ***** one MORE execution in this context 2
		testAnt.execution( wc );
		
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 15, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 15, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals( 13, testAnt.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 22, testAnt.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 10, testAnt.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 10, testAnt.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals( 28, testAnt.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		// ***** one MORE execution in this context
		testAnt.getChemicals().setVariable(SomeChemicals.FRUCTOSE.getIndex(), 20);
		testAnt.execution( wc );
		
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 20, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 20, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals( 19, testAnt.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 21, testAnt.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  5, testAnt.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals( 29, testAnt.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		// ***** one MORE execution in this context
		testAnt.getChemicals().setVarPlus(SomeChemicals.FRUCTOSE.getIndex(), 25);
		testAnt.getChemicals().setVariable(SomeChemicals.HEXOKINASE.getIndex(), 1);
		testAnt.execution( wc );
		
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 25, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 25, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals( 14, testAnt.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 20, testAnt.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  6, testAnt.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  6, testAnt.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals( 32, testAnt.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals( 15, testAnt.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		// ***** one MORE execution in this context
		testAnt.execution( wc );
		
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 30, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 30, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals( 10, testAnt.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 19, testAnt.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals( 33, testAnt.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals( 15, testAnt.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		// ***** one MORE execution in this context
		testAnt.getChemicals().setVarPlus(SomeChemicals.HEXOKINASE.getIndex(), 9);
		testAnt.execution( wc );
		
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 30, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 30, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals( 17, testAnt.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 18, testAnt.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 55, testAnt.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 55, testAnt.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals( 32, testAnt.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		// ***** one MORE execution in this context
		testAnt.getChemicals().setVarPlus(SomeChemicals.HEXOKINASE.getIndex(), 5);
		testAnt.execution( wc );
		
		Assertions.assertEquals(  0, wc.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 35, wc.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 35, wc.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		
		Assertions.assertEquals( 18, testAnt.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.HEXOKINASE.getIndex()) );
		Assertions.assertEquals( 17, testAnt.getChemicals().getVariable( SomeChemicals.STARCH.getIndex()) );
		Assertions.assertEquals( 25, testAnt.getChemicals().getVariable( SomeChemicals.ENERGY_HEAT.getIndex()) );
		Assertions.assertEquals( 80, testAnt.getChemicals().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex()) );
		Assertions.assertEquals( 80, testAnt.getChemicals().getVariable( SomeChemicals.WATER.getIndex()) );
		Assertions.assertEquals( 32, testAnt.getChemicals().getVariable( SomeChemicals.GLYCOGEN.getIndex()) );
		Assertions.assertEquals( 30, testAnt.getChemicals().getVariable( SomeChemicals.DIOXYGEN.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.PHOSPHOR.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.AMP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ADP.getIndex()) );
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable( SomeChemicals.ATP.getIndex()) );
		Assertions.assertEquals(  1, testAnt.getChemicals().getVariable( StateType.AGING.getIndex()) );
		
		BuildingGenomeHelper.show(testAnt, wc);
		
	}

}

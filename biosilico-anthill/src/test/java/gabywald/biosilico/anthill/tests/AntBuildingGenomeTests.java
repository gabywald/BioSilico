package gabywald.biosilico.anthill.tests;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.anthill.Ant;
import gabywald.biosilico.anthill.AntReceptionChemicals;
import gabywald.biosilico.genetics.builders.BiochemicalReactionBuilder;
import gabywald.biosilico.genetics.builders.BrainGeneBuilder;
import gabywald.biosilico.genetics.builders.BrainLobeGeneBuilder;
import gabywald.biosilico.genetics.builders.EmitterReceptorBuilder;
import gabywald.biosilico.genetics.builders.InitialConcentrationBuilder;
import gabywald.biosilico.genetics.builders.InstinctBuilder;
import gabywald.biosilico.genetics.builders.StimulusDecisionBuilder;
import gabywald.biosilico.model.Chromosome;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.World;
import gabywald.biosilico.model.WorldCase;
import gabywald.biosilico.model.chemicals.ChemicalsHelper;
import gabywald.biosilico.model.enums.AgentType;
import gabywald.biosilico.model.enums.DecisionType;
import gabywald.biosilico.model.enums.DirectionWorld;
import gabywald.biosilico.model.enums.ObjectType;
import gabywald.biosilico.model.enums.SomeChemicals;
import gabywald.global.data.File;
import gabywald.global.data.StringUtils;
import gabywald.global.exceptions.DataException;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

class AntBuildingGenomeTests {
	
	private static void exportAntAsTXTfile(String fileName, Ant testAnt) {
		File antGeneticData = new File("src/test/resources/" + fileName);
		antGeneticData.setChamps( testAnt.toString().split("\n") );
		antGeneticData.setChamps(0, "TAXON ID\tRecording from test. ");
		try {
			antGeneticData.printFile();
		} catch (DataException e) {
			// e.printStackTrace();
			String msg = "Cannot write {" + antGeneticData.getFileName() + "} ; DataException {" + e.getMessage() + "}";
			Logger.printlnLog(LoggerLevel.LL_ERROR, msg);
			Assertions.fail( msg );
		}
	}
	
	@Test
	void testStimulusDecisionCurrent() {
		
		Chromosome basicGenome			= new Chromosome();
		
		StimulusDecisionBuilder sdgb	= new StimulusDecisionBuilder();

		// ***** Detect Pheromone_00 at current WorldCase with threshold 10, change variable 700 set to 5
		basicGenome.addGene( sdgb.perception( true ).object( false ) 
				.indicator( SomeChemicals.PHEROMONE_00.getIndex() ).threshold( 10 )
				.attribute( DirectionWorld.CURRENT.getIndex() )
				.varia( AntReceptionChemicals.PHEROMONE_00_CURRENT.getIndex() ).value( 5 ).script( 0 )
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
		
		World w			= new World(3, 3);
		WorldCase wc	= w.getWorldCase(1, 1);
		Assertions.assertNotNull( wc );
		
		testAnt.setCurrentWorldCase( wc );
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getVariables().getVariable(k) );
		});
		
		// ***** one execution in this context
		testAnt.execution( wc );
		testAnt.execution( wc );
		testAnt.execution( wc );
		
		Logger.printlnLog(LoggerLevel.LL_FORUSER, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_CURRENT.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_East.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_North.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_NorthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_NorthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_South.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_SouthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_SouthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_West.getIndex() ) );
		
		wc.getChemicals().setVariable(SomeChemicals.PHEROMONE_00.getIndex(), 11);
		
		// ***** one MORE execution in this context
		testAnt.execution( wc );
		testAnt.execution( wc );
		
		Logger.printlnLog(LoggerLevel.LL_FORUSER, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("*", 80) );
		
		Assertions.assertEquals(  5, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_CURRENT.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_East.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_North.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_NorthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_NorthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_South.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_SouthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_SouthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_West.getIndex() ) );
		
	}
	
	@Test
	void testStimulusDecisionAllDirections() {
		
		Chromosome basicGenome				= new Chromosome();
		
		StimulusDecisionBuilder sdgb		= new StimulusDecisionBuilder();
		List<DirectionWorld> selectedDirs	= DirectionWorld.values2DasList();
		for (DirectionWorld dw : selectedDirs) {
			// ***** Detect Pheromone_00 at current WorldCase with threshold 10, change variable 700 set to 5
			AntReceptionChemicals arc = AntReceptionChemicals.getFrom( dw, SomeChemicals.PHEROMONE_00 );
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
			
			World w			= new World(3, 3);
			WorldCase wc	= w.getWorldCase(1, 1);
			Assertions.assertNotNull( wc );
			
			testAnt.setCurrentWorldCase( wc );
			
			IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
				Assertions.assertEquals( 0, wc.getVariables().getVariable(k) );
			});
			
			// ***** one execution in this context
			testAnt.execution( wc );
			testAnt.execution( wc );
			testAnt.execution( wc );
			
			Logger.printlnLog(LoggerLevel.LL_FORUSER, testAnt.toString() );
			Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("+", 80) );
			
			Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_CURRENT.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_East.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_North.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_NorthEast.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_NorthWest.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_South.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_SouthEast.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_SouthWest.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_West.getIndex() ) );
		
			wc.getDirection( dw ).getChemicals().setVariable(SomeChemicals.PHEROMONE_00.getIndex(), 11);
			
			// ***** one MORE execution in this context
			testAnt.execution( wc );
			testAnt.execution( wc );
			
			Logger.printlnLog(LoggerLevel.LL_FORUSER, testAnt.toString() );
			Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("+", 80) );
			Logger.printlnLog(LoggerLevel.LL_FORUSER, wc.toString() );
			Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("*", 80) );
			
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.CURRENT)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_CURRENT.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.East)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_East.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.North)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_North.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthEast)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_NorthEast.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthWest)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_NorthWest.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.South)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_South.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthEast)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_SouthEast.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthWest)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_SouthWest.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.West)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_West.getIndex() ) );
		
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
			AntReceptionChemicals arc = AntReceptionChemicals.getFrom( dw, SomeChemicals.PHEROMONE_00 );
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
		
		World w			= new World(3, 3);
		WorldCase wc	= w.getWorldCase(1, 1);
		Assertions.assertNotNull( wc );
		
		testAnt.setCurrentWorldCase( wc );
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getVariables().getVariable(k) );
		});
		
		// ***** one execution in this context
		testAnt.execution( wc );
		
		Logger.printlnLog(LoggerLevel.LL_FORUSER, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_CURRENT.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_East.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_North.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_NorthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_NorthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_South.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_SouthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_SouthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_West.getIndex() ) );
	
		// ***** one MORE execution in this context
		testAnt.execution( wc );
		
		Logger.printlnLog(LoggerLevel.LL_FORUSER, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("*", 80) );
		
		for (DirectionWorld dw : selectedDirs) {
			
			for (DirectionWorld dwCleaning : selectedDirs) 
				{ wc.getDirection( dwCleaning ).getChemicals()
					.setVariable(SomeChemicals.PHEROMONE_00.getIndex(),  0); }
			
			wc.getDirection( dw ).getChemicals().setVariable(SomeChemicals.PHEROMONE_00.getIndex(), 11);
			
			// ***** one MORE execution in this context
			testAnt.execution( wc );
			
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.CURRENT)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_CURRENT.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.East)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_East.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.North)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_North.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthEast)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_NorthEast.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthWest)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_NorthWest.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.South)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_South.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthEast)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_SouthEast.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthWest)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_SouthWest.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.West)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_West.getIndex() ) );
			
		} // END "for (DirectionWorld dw : selectedDirs)"
		
		// ***** Export Ant as a TXT file !
		AntBuildingGenomeTests.exportAntAsTXTfile("TestAntstStimulusDecisionAllDirectionsVariant.txt", testAnt);
		
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
				AntReceptionChemicals arc = AntReceptionChemicals.getFrom( dw, scPHE );
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
		
		World w			= new World(3, 3);
		WorldCase wc	= w.getWorldCase(1, 1);
		Assertions.assertNotNull( wc );
		
		testAnt.setCurrentWorldCase( wc );
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getVariables().getVariable(k) );
		});
		
		// ***** one execution in this context
		testAnt.execution( wc );
		
		Logger.printlnLog(LoggerLevel.LL_FORUSER, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_CURRENT.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_East.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_North.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_NorthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_NorthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_South.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_SouthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_SouthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_West.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_CURRENT.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_East.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_North.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_NorthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_NorthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_South.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_SouthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_SouthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_West.getIndex() ) );
	
		// ***** one MORE execution in this context
		testAnt.execution( wc );
		
		Logger.printlnLog(LoggerLevel.LL_FORUSER, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("*", 80) );
		
		for (DirectionWorld dw : selectedDirs) {
			
			for (SomeChemicals scPHE : someChems) {
				
				wc.getDirection( dw ).getChemicals().setVariable(scPHE.getIndex(), 11);
				
				// ***** one MORE execution in this context
				testAnt.execution( wc );
				
				switch( scPHE ) {
				case PHEROMONE_00 :
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.CURRENT)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_CURRENT.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.East)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_East.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.North)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_North.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthEast)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_NorthEast.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthWest)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_NorthWest.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.South)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_South.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthEast)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_SouthEast.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthWest)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_SouthWest.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.West)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_West.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_CURRENT.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_East.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_North.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_NorthEast.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_NorthWest.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_South.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_SouthEast.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_SouthWest.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_West.getIndex() ) );
					break;
				case PHEROMONE_01 : 
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_CURRENT.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_East.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_North.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_NorthEast.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_NorthWest.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_South.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_SouthEast.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_SouthWest.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_West.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.CURRENT)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_CURRENT.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.East)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_East.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.North)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_North.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthEast)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_NorthEast.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthWest)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_NorthWest.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.South)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_South.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthEast)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_SouthEast.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthWest)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_SouthWest.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.West)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_West.getIndex() ) );
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
		
		Logger.printlnLog(LoggerLevel.LL_FORUSER, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("*", 80) );
		
		// ***** Export Ant as a TXT file !
		AntBuildingGenomeTests.exportAntAsTXTfile("TestStimulusDecisionAllDirectionsTwoPheromones.txt", testAnt);
		
	}
	
	@Test
	void testStimulusDecisionAllDirectionsFOOD() {
		
		Chromosome basicChromosome			= new Chromosome();
		
		StimulusDecisionBuilder sdgb		= new StimulusDecisionBuilder();
		List<DirectionWorld> selectedDirs	= DirectionWorld.values2DasList();
		basicChromosome.setName( "FOOD detection !" );		
		for (DirectionWorld dw : selectedDirs) {
			
			// ***** FOOD Detection !!
			AntReceptionChemicals arc = AntReceptionChemicals.getFrom(dw, ObjectType.FOOD);
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
		
		World w			= new World(3, 3);
		WorldCase wc	= w.getWorldCase(1, 1);
		Assertions.assertNotNull( wc );
		
		testAnt.setCurrentWorldCase( wc );
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getVariables().getVariable(k) );
		});
		
		// ***** one execution in this context
		testAnt.execution( wc );
		
		Logger.printlnLog(LoggerLevel.LL_FORUSER, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_CURRENT.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_East.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_North.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_NorthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_NorthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_South.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_SouthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_SouthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_West.getIndex() ) );
	
		// ***** one MORE execution in this context
		testAnt.execution( wc );
		
		for (DirectionWorld dw : selectedDirs) {
			
			// ***** Test on food detection !
			Organism food = new TestObjectFoodEgg();
			wc.getDirection( dw ).addAgent( food );
			
			// ***** execution
			testAnt.execution( wc );
			
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.CURRENT)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_CURRENT.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.East)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_East.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.North)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_North.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthEast)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_NorthEast.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthWest)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_NorthWest.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.South)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_South.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthEast)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_SouthEast.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthWest)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_SouthWest.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.West)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_West.getIndex() ) );
			
			wc.getDirection( dw ).remAgent( food );
			
			Logger.printlnLog(LoggerLevel.LL_FORUSER, testAnt.toString() );
			Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("+", 80) );
			Logger.printlnLog(LoggerLevel.LL_FORUSER, wc.toString() );
			Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("*", 80) );
			
		} // END "for (DirectionWorld dw : selectedDirs)"
		
		// ***** Export Ant as a TXT file !
		AntBuildingGenomeTests.exportAntAsTXTfile("TestStimulusDecisionAllDirectionsFOOD.txt", testAnt);
		
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
				AntReceptionChemicals arc = AntReceptionChemicals.getFrom( dw, scPHE );
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
			AntReceptionChemicals arc = AntReceptionChemicals.getFrom(dw, ObjectType.FOOD);
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
		
		World w			= new World(3, 3);
		WorldCase wc	= w.getWorldCase(1, 1);
		Assertions.assertNotNull( wc );
		
		testAnt.setCurrentWorldCase( wc );
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getVariables().getVariable(k) );
		});
		
		// ***** one execution in this context
		testAnt.execution( wc );
		
		Logger.printlnLog(LoggerLevel.LL_FORUSER, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_CURRENT.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_East.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_North.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_NorthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_NorthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_South.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_SouthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_SouthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_West.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_CURRENT.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_East.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_North.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_NorthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_NorthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_South.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_SouthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_SouthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_West.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_CURRENT.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_East.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_North.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_NorthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_NorthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_South.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_SouthEast.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_SouthWest.getIndex() ) );
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_West.getIndex() ) );
	
		// ***** one MORE execution in this context
		testAnt.execution( wc );
		
		for (DirectionWorld dw : selectedDirs) {
			
			for (SomeChemicals scPHE : someChems) {
				
				wc.getDirection( dw ).getChemicals().setVariable(scPHE.getIndex(), 11);
				
				// ***** one MORE execution in this context
				testAnt.execution( wc );
				
				switch( scPHE ) {
				case PHEROMONE_00 :
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.CURRENT)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_CURRENT.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.East)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_East.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.North)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_North.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthEast)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_NorthEast.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthWest)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_NorthWest.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.South)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_South.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthEast)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_SouthEast.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthWest)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_SouthWest.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.West)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_West.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_CURRENT.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_East.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_North.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_NorthEast.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_NorthWest.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_South.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_SouthEast.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_SouthWest.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_West.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_CURRENT.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_East.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_North.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_NorthEast.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_NorthWest.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_South.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_SouthEast.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_SouthWest.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_West.getIndex() ) );
					break;
				case PHEROMONE_01 : 
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_CURRENT.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_East.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_North.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_NorthEast.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_NorthWest.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_South.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_SouthEast.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_SouthWest.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_West.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.CURRENT)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_CURRENT.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.East)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_East.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.North)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_North.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthEast)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_NorthEast.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthWest)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_NorthWest.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.South)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_South.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthEast)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_SouthEast.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthWest)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_SouthWest.getIndex() ) );
					Assertions.assertEquals( ( (dw.equals(DirectionWorld.West)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_West.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_CURRENT.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_East.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_North.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_NorthEast.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_NorthWest.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_South.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_SouthEast.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_SouthWest.getIndex() ) );
					Assertions.assertEquals( 0, testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_West.getIndex() ) );
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
			
			Logger.printlnLog(LoggerLevel.LL_FORUSER, testAnt.toString() );
			Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("+", 80) );
			Logger.printlnLog(LoggerLevel.LL_FORUSER, wc.toString() );
			Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("*", 80) );
			
			// ***** Test on food detection !
			Organism food = new TestObjectFoodEgg();
			wc.getDirection( dw ).addAgent( food );
			
			// ***** execution
			testAnt.execution( wc );
			
			Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_CURRENT.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_East.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_North.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_NorthEast.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_NorthWest.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_South.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_SouthEast.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_SouthWest.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_00_West.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_CURRENT.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_East.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_North.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_NorthEast.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_NorthWest.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_South.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_SouthEast.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_SouthWest.getIndex() ) );
			Assertions.assertEquals(  0, testAnt.getVariables().getVariable( AntReceptionChemicals.PHEROMONE_01_West.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.CURRENT)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_CURRENT.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.East)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_East.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.North)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_North.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthEast)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_NorthEast.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.NorthWest)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_NorthWest.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.South)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_South.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthEast)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_SouthEast.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.SouthWest)) ?	 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_SouthWest.getIndex() ) );
			Assertions.assertEquals( ( (dw.equals(DirectionWorld.West)) ?		 5 : 0 ), testAnt.getVariables().getVariable( AntReceptionChemicals.FOOD_West.getIndex() ) );
			
			wc.getDirection( dw ).remAgent( food );
			
			Logger.printlnLog(LoggerLevel.LL_FORUSER, testAnt.toString() );
			Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("+", 80) );
			Logger.printlnLog(LoggerLevel.LL_FORUSER, wc.toString() );
			Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("*", 80) );
			
		} // END "for (DirectionWorld dw : selectedDirs)"
		
		// ***** Export Ant as a TXT file !
		AntBuildingGenomeTests.exportAntAsTXTfile("TestStimulusDecisionAllDirectionsTwoPheromonesAndFOOD.txt", testAnt);
		
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
		
		World w			= new World(3, 3);
		WorldCase wc	= w.getWorldCase(1, 1);
		Assertions.assertNotNull( wc );
		
		testAnt.setCurrentWorldCase( wc );
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getVariables().getVariable(k) );
		});
		
		// ***** one execution in this context
		testAnt.execution( wc );
		
		Logger.printlnLog(LoggerLevel.LL_FORUSER, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("+", 80) );
		
		// ***** one MORE execution in this context
		testAnt.execution( wc );
		
		// ***** Test on food detection !
		Organism food = new TestObjectFoodEgg();
		wc.addAgent( food );
		
		// ***** execution
		testAnt.execution( wc );
		
		Assertions.assertEquals(  0, testAnt.getChemicals().getVariable(SomeChemicals.PHEROMONE_09.getIndex()));
		
		Logger.printlnLog(LoggerLevel.LL_FORUSER, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("*", 80) );
		
		wc.remAgent( food );
		testAnt.addAgent( food );
		
		// ***** execution
		testAnt.execution( wc );
		
		Assertions.assertEquals( 45, testAnt.getChemicals().getVariable(SomeChemicals.PHEROMONE_09.getIndex()));
		
		Logger.printlnLog(LoggerLevel.LL_FORUSER, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("*", 80) );
		
		// ***** Export Ant as a TXT file !
		AntBuildingGenomeTests.exportAntAsTXTfile("TestStimulusDecisionHasFOOD.txt", testAnt);
		
	}
	
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
			.name( "Brain Gene 100*100*1*0 " )
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
			.name( "Brain Lobe Gene Data Input DW Ph_00 " )
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
					// TODO DW base indexLessRemove
					.posx( 0 ).posy( dw.getIndex() - 800 )
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
					// TODO DW base indexLessRemove
					.posx( 1 ).posy( dw.getIndex() - 800 )
					.receptor( true ).internal( true )
				.name( nameFirstPartERinput + arc.getName() )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
				.build() );
		} // END "for (DirectionWorld dw : selectedDirs)"
		// *** EmitterReceptor Input => to second line (inputs) of Brain !! (FOOD & direction)
		for (DirectionWorld dw : selectedDirs) {
			AntReceptionChemicals arc = AntReceptionChemicals.getFrom( dw, ObjectType.FOOD );
			chrEmitReceiv.addGene( erb
					.variable( arc.getIndex() ).threshold( 5 ).ioput( 20 )
					// TODO DW base indexLessRemove
					.posx( 2 ).posy( dw.getIndex() - 800 )
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
					// TODO pheromone base indexLessRemove
					.posx( 3 ).posy( pheromone.getIndex() - 350 )
					.receptor( true ).internal( true )
				.name( nameFirstPartERinput + pheromone.getName() )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
				.build() );
		} // END "for (SomeChemicals scPhero : allPheromones)"
		// TODO continuing detection !!
		
		// *** EmitterReceptorOutput
		String nameFirstPartERoutput	= "ER emitter output ";
		for (DirectionWorld dw : selectedDirs) {
			// AntEmissionChemicals aec = AntEmissionChemicals.getFrom( dw, SomeChemicals.PHEROMONE_00 );
			chrEmitReceiv.addGene( erb
					.variable( dw.getIndex() ).threshold( 5 ).ioput( 20 )
					// TODO DW base indexLessRemove
					.posx( 90 ).posy( dw.getIndex() - 800 )
					.receptor( false ).internal( true )
				.name( nameFirstPartERoutput + dw.getName() )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
				.build() );
		} // END "for (DirectionWorld dw : selectedDirs)"
		
		// TODO ER MOVE_AWAY ... 
		chrEmitReceiv.addGene( erb
				.variable( DecisionType.MOVE_AWAY.getIndex() ).threshold( 5 ).ioput( 20 )
				.posx( 90 ).posy( selectedDirs.size() + 0 )
				.receptor( false ).internal( true )
			.name( nameFirstPartERoutput + DecisionType.MOVE_AWAY.getName() )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
			.build() );
		// TODO ER GET FOOD ... 
		chrEmitReceiv.addGene( erb
				.variable( DecisionType.GET.getIndex() ).threshold( 5 ).ioput( 20 )
				.posx( 90 ).posy( selectedDirs.size() + 1 )
				.receptor( false ).internal( true )
			.name( nameFirstPartERoutput + DecisionType.GET.getName() + " FOOD" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
			.build() );
		// TODO ER DROP FOOD ... 
		chrEmitReceiv.addGene( erb
				.variable( DecisionType.DROP.getIndex() ).threshold( 5 ).ioput( 20 )
				.posx( 90 ).posy( selectedDirs.size() + 2 )
				.receptor( false ).internal( true )
			.name( nameFirstPartERoutput + DecisionType.DROP.getName() + " FOOD" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
			.build() );
		
		// ***** ***** ***** ***** ***** 
		chrInstinct.setName("Instinct : connections within Brain");
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
				// TODO DW base indexLessRemove
				int neuronYIndex = dw.getIndex() - 800;
				chrInstinct.addGene( igb
					.inputPosX( i ).inputPosY( neuronYIndex )
					.outputPosX( 90 ).outputPosY( neuronYIndex )
					.weight( 10 ).variable( SomeChemicals.GLUCOSE.getIndex() )
					.threshold( 5 ).check( false )
					.name( "Instinct (" + i + ", " + neuronYIndex + ") to (90, " + neuronYIndex + ") " 
								+ dw.getName() + " [" + SomeChemicals.GLUCOSE.getName() + "]")
						.mutate( true ).duplicate( true ).delete( true ).activ( true )
						.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
						.build() );
			} // END "for (DirectionWorld dw : selectedDirs)"
		});
		// Linked instinct for MOVE_AWAY
		// // // 90 + outputPosY : selectedDirs.size() + 1
		int outputPosY = selectedDirs.size() + 0;
		chrInstinct.addGene( igb
				// TODO pheromone base indexLessRemove
				.inputPosX( 3 ).inputPosY( SomeChemicals.PHEROMONE_00.getIndex() - 350 )
				.outputPosX( 90 ).outputPosY( outputPosY )
				.weight(  5 ).variable( SomeChemicals.GLUCOSE.getIndex() )
				.threshold( 5 ).check( false )
				.name( "Instinct (" + 0 + ", " + 0 + ") to (90, " + outputPosY + ") " 
							+ "INT PHER01 => DROP(FOOD)" + " [" + SomeChemicals.GLUCOSE.getName() + "]")
					.mutate( true ).duplicate( true ).delete( true ).activ( true )
					.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
					.build() );
		// Linked instinct for GET(FOOD) : if FOOD in CURRENT
		// // // 90 + outputPosY : selectedDirs.size() + 2
		outputPosY = selectedDirs.size() + 1;
		chrInstinct.addGene( igb
				.inputPosX( 0 ).inputPosY( 0 )
				.outputPosX( 90 ).outputPosY( outputPosY )
				.weight( 10 ).variable( SomeChemicals.GLUCOSE.getIndex() )
				.threshold( 5 ).check( false )
				.name( "Instinct (" + 0 + ", " + 0 + ") to (90, " + outputPosY + ") " 
							+ "GET(FOOD)" + " [" + SomeChemicals.GLUCOSE.getName() + "]")
					.mutate( true ).duplicate( true ).delete( true ).activ( true )
					.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
					.build() );
		// Linked instinct for DROP(FOOD) : HAS(FOOD) + if PHEROMONE_01 is detected enough
		// // // 90 + outputPosY : selectedDirs.size() + 3
		outputPosY = selectedDirs.size() + 2;
		chrInstinct.addGene( igb
				.inputPosX( 1 ).inputPosY( 1 )
				.outputPosX( 90 ).outputPosY( outputPosY )
				.weight( 10 ).variable( SomeChemicals.GLUCOSE.getIndex() )
				.threshold( 5 ).check( false )
				.name( "Instinct (" + 1 + ", " + 1 + ") to (90, " + outputPosY + ") " 
							+ "LLL PHER01 => DROP(FOOD)" + " [" + SomeChemicals.GLUCOSE.getName() + "]")
					.mutate( true ).duplicate( true ).delete( true ).activ( true )
					.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
					.build() );
		chrInstinct.addGene( igb
				// TODO pheromone base indexLessRemove
				.inputPosX( 3 ).inputPosY( SomeChemicals.PHEROMONE_01.getIndex() - 350 )
				.outputPosX( 90 ).outputPosY( outputPosY )
				.weight( 10 ).variable( SomeChemicals.GLUCOSE.getIndex() )
				.threshold( 5 ).check( false )
				.name( "Instinct (" + 3 + ", " + (SomeChemicals.PHEROMONE_01.getIndex() - 350) + ") to (90, " + outputPosY + ") " 
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
				.threshold( 20 )								// detection
				.attribute( 0 ).varia( 0 ).value( 0 )			// attribute && varia && value are irrelevant here !
				.script( DecisionType.MOVE_AWAY.getIndex() )	// What to do !
			.name( "StimulusDecisionGene_MOVEAWAY" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		
		// TODO Decision GET FOOD
		chrSDdecision.addGene( sdgb.perception( false ).object( true ) 
				.indicator( DecisionType.GET.getIndex() ).threshold( 10 ) 	// to be detected && detection
				.attribute( ObjectType.FOOD.getIndex() )
				.varia( 0 ).value( 0 )										// varia && value are irrelevant here !
				.script( DecisionType.GET.getIndex() )						// What to do !
			.name( "StimulusDecisionGene_GET_FOOD" )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.build() );
		// TODO Decision DROP FOOD
		chrSDdecision.addGene( sdgb.perception( false ).object( true ) 
				.indicator( DecisionType.DROP.getIndex() ).threshold( 10 ) 	// to be detected && detection
				.attribute( ObjectType.FOOD.getIndex() )
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
		
		// ***** FOOD Detection !! (internal) => HAS FOOD ! (XXX indicates in PHEROMONE_01)
		chrSDdetection.addGene( sdgb.perception( false ).object( true ) 
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
		Assertions.assertEquals( 32, testAnt.getGenome().get( 2 ).length());
		Assertions.assertEquals( 12, testAnt.getGenome().get( 3 ).length());
		Assertions.assertEquals( 29, testAnt.getGenome().get( 4 ).length());
		
		testAnt.setRank("Rank Test");
		testAnt.setNameCommon("Test Starting Ant");
		testAnt.setNameBiosilico("AntHill Ant Example");
		testAnt.setDivision("TESTS");
		
		// ***** ***** ***** ***** ***** ***** ***** ***** ***** 
		// ***** ***** ***** ***** ***** ***** ***** ***** ***** 
		// ***** test with a World and WorldCase
		
		World w			= new World(3, 3);
		WorldCase wc	= w.getWorldCase(1, 1);
		Assertions.assertNotNull( wc );
		
		testAnt.setCurrentWorldCase( wc );
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getVariables().getVariable(k) );
		});
		
		// ***** one execution in this context
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // to permit correct initialization of "starting genes"
		
		Logger.printlnLog(LoggerLevel.LL_FORUSER, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("+", 80) );
	
		// ***** one MORE execution in this context
		testAnt.execution( wc );
		
		for (DirectionWorld dw : selectedDirs) {
			
			for (SomeChemicals scPHE : someChems) {
				
				wc.getDirection( dw ).getChemicals().setVariable(scPHE.getIndex(), 11);
				
				testAnt.execution( wc );
				testAnt.execution( wc );
			
			} // END "for (SomeChemicals scPHE : someChems)"
			
			Logger.printlnLog(LoggerLevel.LL_FORUSER, testAnt.toString() );
			Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("+", 80) );
			Logger.printlnLog(LoggerLevel.LL_FORUSER, wc.toString() );
			Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("*", 80) );
			
			int posYindex = dw.getIndex() - 800;
			Assertions.assertTrue( testAnt.getBrain().getNeuronAt(  0, posYindex).ckActivated() );
			Assertions.assertTrue( testAnt.getBrain().getNeuronAt(  1, posYindex).ckActivated() );
			Assertions.assertTrue( testAnt.getBrain().getNeuronAt( 90, posYindex).ckActivated() );
			
			for (SomeChemicals scPHE : someChems) {
				for (DirectionWorld dwCleaning : selectedDirs) 
					{ wc.getDirection( dwCleaning ).getChemicals().setVariable(scPHE.getIndex(),  0); }
			} // END "for (SomeChemicals scPHE : someChems)"
			
			while ( (testAnt.getBrain().getNeuronAt(  0, posYindex).ckActivated()) 
					|| (testAnt.getBrain().getNeuronAt(  1, posYindex).ckActivated()) 
					|| (testAnt.getBrain().getNeuronAt( 90, posYindex).ckActivated()) )
				{ testAnt.execution( wc ); }
			
			Logger.printlnLog(LoggerLevel.LL_FORUSER, testAnt.toString() );
			Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("+", 80) );
			Logger.printlnLog(LoggerLevel.LL_FORUSER, wc.toString() );
			Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("*", 80) );
			
			Assertions.assertFalse( testAnt.getBrain().getNeuronAt(  0, posYindex).ckActivated() );
			Assertions.assertFalse( testAnt.getBrain().getNeuronAt(  1, posYindex).ckActivated() );
			Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 90, posYindex).ckActivated() );
			
			// ***** Test on food detection !
			Organism food = new TestObjectFoodEgg();
			wc.getDirection( dw ).addAgent( food );
			
			testAnt.execution( wc );
			testAnt.execution( wc );
			
			Logger.printlnLog(LoggerLevel.LL_FORUSER, testAnt.toString() );
			Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("+", 80) );
			Logger.printlnLog(LoggerLevel.LL_FORUSER, wc.toString() );
			Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("*", 80) );
			
			Assertions.assertTrue( testAnt.getBrain().getNeuronAt(  2, posYindex).ckActivated() );
			
			wc.getDirection( dw ).remAgent( food );
			
			while ( (testAnt.getBrain().getNeuronAt(  2, posYindex).ckActivated()) )
				{ testAnt.execution( wc ); }
			
			Assertions.assertFalse( testAnt.getBrain().getNeuronAt(  2, posYindex).ckActivated() );
			
			// ***** ***** ***** 
			
		} // END "for (DirectionWorld dw : selectedDirs)"
		
		TestObjectFoodEgg food = new TestObjectFoodEgg();
		testAnt.addAgent( food );
		
		for (SomeChemicals pheromone : allPheromones) {
			switch(pheromone) {
			case PHEROMONE_01:
			Assertions.assertEquals(  0, testAnt.getVariables().getVariable( pheromone.getIndex() ) );
			break;
			default:
				Assertions.assertEquals(  0, testAnt.getVariables().getVariable( pheromone.getIndex() ) );
			}
		} // END "for (SomeChemicals pheromone : allPheromones)"
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3, 1).ckActivated() );
		
		// ***** execution
		testAnt.execution( wc );
		
		for (SomeChemicals pheromone : allPheromones) {
			switch(pheromone) {
			case PHEROMONE_01:
			Assertions.assertEquals( 95, testAnt.getVariables().getVariable( pheromone.getIndex() ) );
			break;
			default:
				Assertions.assertEquals(  0, testAnt.getVariables().getVariable( pheromone.getIndex() ) );
			}
		} // END "for (SomeChemicals pheromone : allPheromones)"
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 3, 1).ckActivated() );
		
		Logger.printlnLog(LoggerLevel.LL_FORUSER, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("*", 80) );
		
		// ***** execution
		testAnt.execution( wc );
		
		for (SomeChemicals pheromone : allPheromones) {
			switch(pheromone) {
			case PHEROMONE_01:
			Assertions.assertEquals(190, testAnt.getVariables().getVariable( pheromone.getIndex() ) );
			break;
			default:
				Assertions.assertEquals(  0, testAnt.getVariables().getVariable( pheromone.getIndex() ) );
			}
		} // END "for (SomeChemicals pheromone : allPheromones)"

		Assertions.assertTrue( testAnt.getBrain().getNeuronAt( 3, 1).ckActivated() );
		
		Logger.printlnLog(LoggerLevel.LL_FORUSER, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("*", 80) );
		
		testAnt.remAgent( food );
		
		// ***** execution
		testAnt.execution( wc );
		testAnt.execution( wc );
		
		for (SomeChemicals pheromone : allPheromones) {
			switch(pheromone) {
			case PHEROMONE_01:
			Assertions.assertEquals(180, testAnt.getVariables().getVariable( pheromone.getIndex() ) );
			break;
			default:
				Assertions.assertEquals(  0, testAnt.getVariables().getVariable( pheromone.getIndex() ) );
			}
		} // END "for (SomeChemicals pheromone : allPheromones)"
		
		Assertions.assertTrue( testAnt.getBrain().getNeuronAt( 3, 1).ckActivated() );
		
		// ***** Cleaning before recording file of genome !!
		testAnt.getVariables().setVariable(SomeChemicals.PHEROMONE_01.getIndex(), 0);
		while ( (testAnt.getBrain().getNeuronAt(  3,  1).ckActivated()) )
			{ testAnt.execution( wc ); }
		
		Logger.printlnLog(LoggerLevel.LL_FORUSER, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_FORUSER, StringUtils.repeat("*", 80) );
		
		// TODO test GET FOOD / DROP FOOD / ...
		
		// ***** Export Ant as a TXT file !
		AntBuildingGenomeTests.exportAntAsTXTfile("TestWireBrainWithDecisionAndInstincts.txt", testAnt);
		
	}
	
	// TODO taking really decision !
	// // // Brain and BrainLobes : groups of WTA neurones for "incompatible decisions"
	// // // EmitterReceptor as receptor for Stimulus => "first line of BrainLobe"
	// // // EmitterReceptor as emitter for Stimulus => "last line of BrainLobe"
	// // // Instinct between the two lines (different weights to apply priorities)
	// // // StimulusDecision as "not receptor" to take decision

//	@Test
//	void test01() {
//		
//		Chromosome basicGenome		= new Chromosome();
//		
//		BrainGeneBuilder bgb		= new BrainGeneBuilder();
//		BrainLobeGeneBuilder blgb	= new BrainLobeGeneBuilder();
//		EmitterReceptorBuilder ergb	= new EmitterReceptorBuilder();
//
//		basicGenome.addGene( bgb.heigth(10).width(10).depth(1)
//				.agemin(0).agemax(0).mutation(25).activ(true).build() );
//		
//		basicGenome.addGene( blgb.heigth(1).width(1)
//				.rest(0).threshold(10).desc(1).dmin(0).dmax(0)
//				.prox(0).repr(false).repy(0).wta(false)
//				.posx(1).posy(1).replace(false)
//				.agemin(0).agemax(0).mutation(25).activ(true).build() );
//		
//		basicGenome.addGene( ergb.variable( SomeChemicals.ENERGY_SOLAR.getIndex() )
//				.threshold( 10 ).ioput( 100 ).posx( 1 ).posy( 1 )
//				.receptor( true ).internal( false )
//			.mutate( true ).duplicate( true ).delete( true ).activ( true )
//			.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
//			.build() );
//		
//		Ant testAnt = new Ant( basicGenome );
//		Assertions.assertNotNull( testAnt );
//		Assertions.assertEquals(1, testAnt.getGenome().size());
//		
//		testAnt.setRank("Rank Test");
//		testAnt.setNameCommon("Test Starting Ant");
//		testAnt.setNameBiosilico("AntHill Ant Example");
//		testAnt.setDivision("TESTS");
//		
//		// ***** test with a World and WorldCase
//		
//		World w			= new World(3, 3);
//		WorldCase wc	= w.getWorldCase(1, 1);
//		Assertions.assertNotNull( wc );
//		
//		testAnt.setCurrentWorldCase( wc );
//		
//		
//	}

}

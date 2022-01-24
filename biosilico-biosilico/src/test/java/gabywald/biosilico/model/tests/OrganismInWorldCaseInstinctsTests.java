package gabywald.biosilico.model.tests;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.genetics.builders.BrainGeneBuilder;
import gabywald.biosilico.genetics.builders.BrainLobeGeneBuilder;
import gabywald.biosilico.genetics.builders.InitialConcentrationBuilder;
import gabywald.biosilico.genetics.builders.InstinctBuilder;
import gabywald.biosilico.model.Chromosome;
import gabywald.biosilico.model.Neuron;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.enums.SomeChemicals;
import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.environment.World2D;
import gabywald.biosilico.model.environment.World2DCase;
import gabywald.global.data.StringUtils;

import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class OrganismInWorldCaseInstinctsTests {

	/**
	 * Test WITHOUT Chemical to create instinct (here GLUCOSE (ch169) )
	 */
	@Test
	void testInstincts01() {

		Chromosome basicGenome				= new Chromosome();
		
		BrainGeneBuilder bgb				= new BrainGeneBuilder();
		BrainLobeGeneBuilder blgb			= new BrainLobeGeneBuilder();
		InstinctBuilder igb					= new InstinctBuilder();
		
		InitialConcentrationBuilder icb		= new InitialConcentrationBuilder();

		// ***** brain
		basicGenome.addGene( bgb.heigth(10).width(10).depth(1)
				.name( "BrainGene 10*10*1" )
				.agemin(0).agemax(0).mutation(25).activ(true).build() );
		// ***** a lobe on first line
		basicGenome.addGene( blgb
				.rest(0).threshold(10).desc(1).dmin(0).dmax(0)
				.prox(0).repr(false).repy(0).wta(false)
				.posx(0).posy(0).heigth(1).width(10).replace(false)
				.name("BrainLobeGene (0, 0), 1, 10, ")
				.agemin(0).agemax(0).mutation(25).activ(true).build() );
		// ***** a lobe on last line
		basicGenome.addGene( blgb
				.rest(0).threshold(10).desc(1).dmin(0).dmax(0)
				.prox(0).repr(false).repy(0).wta(false)
				.posx(9).posy(0).heigth(1).width(10).replace(false)
				.name("BrainLobeGene (9, 0), 1, 10, ")
				.agemin(0).agemax(0).mutation(25).activ(true).build() );
		
		basicGenome.addGene( icb	
				.varia( SomeChemicals.GLUCOSE.getIndex() ).value( 25 )
				.name( "InitConc GLUCOSE 25" )
					.mutate( true ).duplicate( true ).delete( true ).activ( true )
					.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		
		basicGenome.addGene( igb
				.inputPosX( 0 ).inputPosY( 0 ).outputPosX( 9 ).outputPosY( 0 )
				.weight( 42 ).variable( SomeChemicals.GLUCOSE.getIndex() )
				.threshold( 5 ).check( false )
					.name( "Instinct (0, 0) => (9, 0) GLUCOSE > 25 ; weigth 42" )
					.mutate( true ).duplicate( true ).delete( true ).activ( false )
					.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
					.build() );
		
		Organism testOrga		= new Organism(basicGenome);

		Assertions.assertEquals(5, basicGenome.length());
		Assertions.assertEquals(1, testOrga.getGenome().size());
		Assertions.assertEquals(5, testOrga.getGenome().get(0).length());
		
		// ***** test with a World and WorldCase
		
		World2D w			= new World2D(1, 1);
		World2DCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		
		Logger.printlnLog(LoggerLevel.LL_NONE, testOrga.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  1, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		
		Assertions.assertNotNull( testOrga.getBrain() );
		Neuron testNeuronAt9dot0 = testOrga.getBrain().getNeuronAt(9, 0);
		Assertions.assertNotNull( testNeuronAt9dot0 );
		Neuron testNeuronAt0dot0 = testOrga.getBrain().getNeuronAt(0, 0);
		Assertions.assertNotNull( testNeuronAt0dot0 );
		
		Logger.printlnLog(LoggerLevel.LL_NONE, testOrga.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, StringUtils.repeat("+", 80) );
		
		// ***** Check connection from (9, 0) to (0, 0) and weights
		List<Neuron> testConnections9dot0	= testNeuronAt9dot0.getConnections();
		Assertions.assertNotNull( testConnections9dot0 );
		Assertions.assertEquals(  0, testConnections9dot0.size() );
		
		List<Integer> testWeights9to0		= testNeuronAt9dot0.getWeights();
		Assertions.assertNotNull( testWeights9to0 );
		Assertions.assertEquals(  0, testWeights9to0.size() );
		
	}
	
	/**
	 * Test WITH Chemical to create instinct (here GLUCOSE (ch169) )
	 */
	@Test
	void testInstincts02() {

		Chromosome basicGenome				= new Chromosome();
		
		BrainGeneBuilder bgb				= new BrainGeneBuilder();
		BrainLobeGeneBuilder blgb			= new BrainLobeGeneBuilder();
		InstinctBuilder igb					= new InstinctBuilder();
		
		InitialConcentrationBuilder icb		= new InitialConcentrationBuilder();

		// ***** brain
		basicGenome.addGene( bgb.heigth(10).width(10).depth(1)
				.name( "BrainGene 10*10*1" )
				.agemin(0).agemax(0).mutation(25).activ(true).build() );
		// ***** a lobe on first line
		basicGenome.addGene( blgb
				.rest(0).threshold(10).desc(1).dmin(0).dmax(0)
				.prox(0).repr(false).repy(0).wta(false)
				.posx(0).posy(0).heigth(1).width(10).replace(false)
				.name("BrainLobeGene (0, 0), 1, 10, ")
				.agemin(0).agemax(0).mutation(25).activ(true).build() );
		// ***** a lobe on last line
		basicGenome.addGene( blgb
				.rest(0).threshold(10).desc(1).dmin(0).dmax(0)
				.prox(0).repr(false).repy(0).wta(false)
				.posx(9).posy(0).heigth(1).width(10).replace(false)
				.name("BrainLobeGene (9, 0), 1, 10, ")
				.agemin(0).agemax(0).mutation(25).activ(true).build() );
		
		basicGenome.addGene( icb	
				.varia( SomeChemicals.GLUCOSE.getIndex() ).value( 25 )
				.name( "InitConc GLUCOSE 25" )
					.mutate( true ).duplicate( true ).delete( true ).activ( true )
					.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		
		basicGenome.addGene( igb
				.inputPosX( 0 ).inputPosY( 0 ).outputPosX( 9 ).outputPosY( 0 )
				.weight( 42 ).variable( SomeChemicals.GLUCOSE.getIndex() )
				.threshold( 5 ).check( false )
					.name( "Instinct (0, 0) => (9, 0) GLUCOSE > 25 ; weigth 42" )
					.mutate( true ).duplicate( true ).delete( true ).activ( true )
					.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
					.build() );
		
		Organism testOrga		= new Organism(basicGenome);

		Assertions.assertEquals(5, basicGenome.length());
		Assertions.assertEquals(1, testOrga.getGenome().size());
		Assertions.assertEquals(5, testOrga.getGenome().get(0).length());
		
		// ***** test with a World and WorldCase
		
		World2D w			= new World2D(1, 1);
		World2DCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		
		Logger.printlnLog(LoggerLevel.LL_NONE, testOrga.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  1, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		
		Assertions.assertNotNull( testOrga.getBrain() );
		Neuron testNeuronAt9dot0 = testOrga.getBrain().getNeuronAt(9, 0);
		Assertions.assertNotNull( testNeuronAt9dot0 );
		Neuron testNeuronAt0dot0 = testOrga.getBrain().getNeuronAt(0, 0);
		Assertions.assertNotNull( testNeuronAt0dot0 );
		
		Logger.printlnLog(LoggerLevel.LL_NONE, testOrga.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, StringUtils.repeat("+", 80) );
		
		// ***** Check connection from (9, 0) to (0, 0) and weights
		List<Neuron> testConnections9dot0	= testNeuronAt9dot0.getConnections();
		Assertions.assertNotNull( testConnections9dot0 );
		Assertions.assertEquals(  1, testConnections9dot0.size() );
		Assertions.assertTrue( testNeuronAt0dot0.equals(testConnections9dot0.get(0)) );
		
		List<Integer> testWeights9to0		= testNeuronAt9dot0.getWeights();
		Assertions.assertNotNull( testWeights9to0 );
		Assertions.assertEquals(  1, testWeights9to0.size() );
		Assertions.assertEquals( 42, testWeights9to0.get(0).intValue() );
		
		// ***** one MORE execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  2, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		
		Assertions.assertEquals(  0, testNeuronAt0dot0.getActivity() );
		
		Logger.printlnLog(LoggerLevel.LL_NONE, testOrga.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, StringUtils.repeat("+", 80) );
		
		// ***** Check connection from (9, 0) to (0, 0) and weights
		// List<Neuron> testConnections9dot0	= testNeuronAt9dot0.getConnections();
		Assertions.assertNotNull( testConnections9dot0 );
		Assertions.assertEquals(  1, testConnections9dot0.size() );
		Assertions.assertTrue( testNeuronAt0dot0.equals(testConnections9dot0.get(0)) );
		
		// List<Integer> testWeights9to0		= testNeuronAt9dot0.getWeights();
		Assertions.assertNotNull( testWeights9to0 );
		Assertions.assertEquals(  1, testWeights9to0.size() );
		Assertions.assertEquals( 42, testWeights9to0.get(0).intValue() );
		
	}
	
	@Test
	void testInstincts03() {

		Chromosome basicGenome				= new Chromosome();
		
		BrainGeneBuilder bgb				= new BrainGeneBuilder();
		BrainLobeGeneBuilder blgb			= new BrainLobeGeneBuilder();
		InstinctBuilder igb					= new InstinctBuilder();
		
		InitialConcentrationBuilder icb		= new InitialConcentrationBuilder();

		// ***** brain
		basicGenome.addGene( bgb.heigth(10).width(10).depth(1)
				.name( "BrainGene 10*10*1" )
				.agemin(0).agemax(0).mutation(25).activ(true).build() );
		// ***** a lobe on first line
		basicGenome.addGene( blgb
				.rest(0).threshold(10).desc(1).dmin(0).dmax(0)
				.prox(0).repr(false).repy(0).wta(false)
				.posx(0).posy(0).heigth(1).width(10).replace(false)
				.name("BrainLobeGene (0, 0), 1, 10, ")
				.agemin(0).agemax(0).mutation(25).activ(true).build() );
		// ***** a lobe on last line
		basicGenome.addGene( blgb
				.rest(0).threshold(10).desc(1).dmin(0).dmax(0)
				.prox(0).repr(false).repy(0).wta(false)
				.posx(9).posy(0).heigth(1).width(10).replace(false)
				.name("BrainLobeGene (9, 0), 1, 10, ")
				.agemin(0).agemax(0).mutation(25).activ(true).build() );
		
		basicGenome.addGene( icb	
				.varia( SomeChemicals.GLUCOSE.getIndex() ).value( 25 )
				.name( "InitConc GLUCOSE 25" )
					.mutate( true ).duplicate( true ).delete( true ).activ( true )
					.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		
		basicGenome.addGene( igb
				.inputPosX( 0 ).inputPosY( 0 ).outputPosX( 9 ).outputPosY( 0 )
				.weight( 42 ).variable( SomeChemicals.GLUCOSE.getIndex() )
				.threshold( 5 ).check( false )
					.name( "Instinct (0, 0) => (9, 0) GLUCOSE > 25 ; weigth 42" )
					.mutate( true ).duplicate( true ).delete( true ).activ( true )
					.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
					.build() );
		
		Organism testOrga		= new Organism(basicGenome);

		Assertions.assertEquals(5, basicGenome.length());
		Assertions.assertEquals(1, testOrga.getGenome().size());
		Assertions.assertEquals(5, testOrga.getGenome().get(0).length());
		
		// ***** test with a World and WorldCase
		
		World2D w			= new World2D(1, 1);
		World2DCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		
		Logger.printlnLog(LoggerLevel.LL_NONE, testOrga.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  1, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		
		Assertions.assertNotNull( testOrga.getBrain() );
		Neuron testNeuronAt9dot0 = testOrga.getBrain().getNeuronAt(9, 0);
		Assertions.assertNotNull( testNeuronAt9dot0 );
		Neuron testNeuronAt0dot0 = testOrga.getBrain().getNeuronAt(0, 0);
		Assertions.assertNotNull( testNeuronAt0dot0 );
		
		Logger.printlnLog(LoggerLevel.LL_NONE, testOrga.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, StringUtils.repeat("+", 80) );
		
		// ***** Check connection from (9, 0) to (0, 0) and weights
		List<Neuron> testConnections9dot0	= testNeuronAt9dot0.getConnections();
		Assertions.assertNotNull( testConnections9dot0 );
		Assertions.assertEquals(  1, testConnections9dot0.size() );
		Assertions.assertTrue( testNeuronAt0dot0.equals(testConnections9dot0.get(0)) );
		
		List<Integer> testWeights9to0		= testNeuronAt9dot0.getWeights();
		Assertions.assertNotNull( testWeights9to0 );
		Assertions.assertEquals(  1, testWeights9to0.size() );
		Assertions.assertEquals( 42, testWeights9to0.get(0).intValue() );
		
		Assertions.assertEquals(  0, testNeuronAt0dot0.getActivity() );
		testNeuronAt0dot0.addActivity( 100 );
		Assertions.assertEquals(100, testNeuronAt0dot0.getActivity() );
		
		// ***** one MORE execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  2, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		
		Logger.printlnLog(LoggerLevel.LL_NONE, testOrga.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(99, testNeuronAt0dot0.getActivity() );
		Assertions.assertEquals(42, testNeuronAt9dot0.getActivity() );
		
		// ***** Check connection from (9, 0) to (0, 0) and weights
		Assertions.assertTrue( testNeuronAt9dot0.hasConnected(testNeuronAt0dot0) );
		Assertions.assertEquals( 0, testNeuronAt9dot0.getConnectPosition(testNeuronAt0dot0) );
		Assertions.assertFalse( testNeuronAt0dot0.hasConnected(testNeuronAt9dot0) );
		Assertions.assertEquals(-1, testNeuronAt0dot0.getConnectPosition(testNeuronAt9dot0) );
		// List<Neuron> testConnections9dot0	= testNeuronAt9dot0.getConnections();
		Assertions.assertNotNull( testConnections9dot0 );
		Assertions.assertEquals(  1, testConnections9dot0.size() );
		Assertions.assertTrue( testNeuronAt0dot0.equals(testConnections9dot0.get(0)) );
		
		// List<Integer> testWeights9to0		= testNeuronAt9dot0.getWeights();
		Assertions.assertNotNull( testWeights9to0 );
		Assertions.assertEquals(  1, testWeights9to0.size() );
		Assertions.assertEquals( 42, testWeights9to0.get(0).intValue() );
		
		// ***** one MORE MORE execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  3, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		
		Logger.printlnLog(LoggerLevel.LL_NONE, testOrga.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(98, testNeuronAt0dot0.getActivity() );
		Assertions.assertEquals(83, testNeuronAt9dot0.getActivity() ); // 42 + 42 - 1
		
		// ***** Check connection from (9, 0) to (0, 0) and weights
		Assertions.assertTrue( testNeuronAt9dot0.hasConnected(testNeuronAt0dot0) );
		Assertions.assertEquals( 0, testNeuronAt9dot0.getConnectPosition(testNeuronAt0dot0) );
		Assertions.assertFalse( testNeuronAt0dot0.hasConnected(testNeuronAt9dot0) );
		Assertions.assertEquals(-1, testNeuronAt0dot0.getConnectPosition(testNeuronAt9dot0) );
		// List<Neuron> testConnections9dot0	= testNeuronAt9dot0.getConnections();
		Assertions.assertNotNull( testConnections9dot0 );
		Assertions.assertEquals(  1, testConnections9dot0.size() );
		Assertions.assertTrue( testNeuronAt0dot0.equals(testConnections9dot0.get(0)) );
		
		// List<Integer> testWeights9to0		= testNeuronAt9dot0.getWeights();
		Assertions.assertNotNull( testWeights9to0 );
		Assertions.assertEquals(  1, testWeights9to0.size() );
		Assertions.assertEquals( 42, testWeights9to0.get(0).intValue() );
		
	}
	
	@Test
	void testInstincts04() {

		Chromosome basicGenome				= new Chromosome();
		
		BrainGeneBuilder bgb				= new BrainGeneBuilder();
		BrainLobeGeneBuilder blgb			= new BrainLobeGeneBuilder();
		InstinctBuilder igb					= new InstinctBuilder();
		
		InitialConcentrationBuilder icb		= new InitialConcentrationBuilder();

		// ***** brain
		basicGenome.addGene( bgb.heigth(10).width(10).depth(1)
				.name( "BrainGene 10*10*1" )
				.agemin(0).agemax(0).mutation(25).activ(true).build() );
		// ***** a lobe on first line
		basicGenome.addGene( blgb
				.rest(0).threshold(10).desc(1).dmin(0).dmax(0)
				.prox(0).repr(false).repy(0).wta(false)
				.posx(0).posy(0).heigth(1).width(10).replace(false)
				.name("BrainLobeGene (0, 0), 1, 10, ")
				.agemin(0).agemax(0).mutation(25).activ(true).build() );
		// ***** a lobe on last line
		basicGenome.addGene( blgb
				.rest(0).threshold(10).desc(1).dmin(0).dmax(0)
				.prox(0).repr(false).repy(0).wta(false)
				.posx(9).posy(0).heigth(1).width(10).replace(false)
				.name("BrainLobeGene (9, 0), 1, 10, ")
				.agemin(0).agemax(0).mutation(25).activ(true).build() );
		
		basicGenome.addGene( icb	
				.varia( SomeChemicals.GLUCOSE.getIndex() ).value( 25 )
				.name( "InitConc GLUCOSE 25" )
					.mutate( true ).duplicate( true ).delete( true ).activ( true )
					.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		
		basicGenome.addGene( igb
				.inputPosX( 0 ).inputPosY( 0 ).outputPosX( 9 ).outputPosY( 0 )
				.weight( 42 ).variable( SomeChemicals.GLUCOSE.getIndex() )
				.threshold( 5 ).check( false ).positiv( true )
					.name( "Instinct (0, 0) => (9, 0) GLUCOSE > 25 ; weigth 42 (+)" )
					.mutate( true ).duplicate( true ).delete( true ).activ( true )
					.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
					.build() );
		
		basicGenome.addGene( igb
				.inputPosX( 0 ).inputPosY( 0 ).outputPosX( 9 ).outputPosY( 0 )
				.weight( 41 ).variable( SomeChemicals.GLUCOSE.getIndex() )
				.threshold( 5 ).check( false ).positiv( false )
					.name( "Instinct (0, 0) => (9, 0) GLUCOSE > 25 ; weigth 42 (-)" )
					.mutate( true ).duplicate( true ).delete( true ).activ( true )
					.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
					.build() );
		
		Organism testOrga		= new Organism(basicGenome);

		Assertions.assertEquals(6, basicGenome.length());
		Assertions.assertEquals(1, testOrga.getGenome().size());
		Assertions.assertEquals(6, testOrga.getGenome().get(0).length());
		
		// ***** test with a World and WorldCase
		
		World2D w			= new World2D(1, 1);
		World2DCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		
		Logger.printlnLog(LoggerLevel.LL_NONE, testOrga.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  1, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		
		Assertions.assertNotNull( testOrga.getBrain() );
		Neuron testNeuronAt9dot0 = testOrga.getBrain().getNeuronAt(9, 0);
		Assertions.assertNotNull( testNeuronAt9dot0 );
		Neuron testNeuronAt0dot0 = testOrga.getBrain().getNeuronAt(0, 0);
		Assertions.assertNotNull( testNeuronAt0dot0 );
		
		Logger.printlnLog(LoggerLevel.LL_NONE, testOrga.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, StringUtils.repeat("+", 80) );
		
		// ***** Check connection from (9, 0) to (0, 0) and weights
		List<Neuron> testConnections9dot0	= testNeuronAt9dot0.getConnections();
		Assertions.assertNotNull( testConnections9dot0 );
		Assertions.assertEquals(  2, testConnections9dot0.size() );
		Assertions.assertTrue( testNeuronAt0dot0.equals(testConnections9dot0.get(0)) );
		
		List<Integer> testWeights9to0		= testNeuronAt9dot0.getWeights();
		Assertions.assertNotNull( testWeights9to0 );
		Assertions.assertEquals(  2, testWeights9to0.size() );
		Assertions.assertEquals( 42, testWeights9to0.get(0).intValue() );
		
		Assertions.assertEquals(  0, testNeuronAt0dot0.getActivity() );
		testNeuronAt0dot0.addActivity( 100 );
		Assertions.assertEquals(100, testNeuronAt0dot0.getActivity() );
		
		// ***** one MORE execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  2, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		
		Logger.printlnLog(LoggerLevel.LL_NONE, testOrga.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(99, testNeuronAt0dot0.getActivity() );
		Assertions.assertEquals( 1, testNeuronAt9dot0.getActivity() );
		
		// ***** Check connection from (9, 0) to (0, 0) and weights
		Assertions.assertTrue( testNeuronAt9dot0.hasConnected(testNeuronAt0dot0) );
		Assertions.assertEquals( 0, testNeuronAt9dot0.getConnectPosition(testNeuronAt0dot0) );
		Assertions.assertFalse( testNeuronAt0dot0.hasConnected(testNeuronAt9dot0) );
		Assertions.assertEquals(-1, testNeuronAt0dot0.getConnectPosition(testNeuronAt9dot0) );
		// List<Neuron> testConnections9dot0	= testNeuronAt9dot0.getConnections();
		Assertions.assertNotNull( testConnections9dot0 );
		Assertions.assertEquals(  2, testConnections9dot0.size() );
		Assertions.assertTrue( testNeuronAt0dot0.equals(testConnections9dot0.get(0)) );
		
		// List<Integer> testWeights9to0		= testNeuronAt9dot0.getWeights();
		Assertions.assertNotNull( testWeights9to0 );
		Assertions.assertEquals(  2, testWeights9to0.size() );
		Assertions.assertEquals( 42, testWeights9to0.get(0).intValue() );
		
		// ***** one MORE MORE execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  3, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		
		Logger.printlnLog(LoggerLevel.LL_NONE, testOrga.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(98, testNeuronAt0dot0.getActivity() );
		Assertions.assertEquals( 1, testNeuronAt9dot0.getActivity() ); // 42 + 42 - 1
		
		// ***** Check connection from (9, 0) to (0, 0) and weights
		Assertions.assertTrue( testNeuronAt9dot0.hasConnected(testNeuronAt0dot0) );
		Assertions.assertEquals( 0, testNeuronAt9dot0.getConnectPosition(testNeuronAt0dot0) );
		Assertions.assertFalse( testNeuronAt0dot0.hasConnected(testNeuronAt9dot0) );
		Assertions.assertEquals(-1, testNeuronAt0dot0.getConnectPosition(testNeuronAt9dot0) );
		// List<Neuron> testConnections9dot0	= testNeuronAt9dot0.getConnections();
		Assertions.assertNotNull( testConnections9dot0 );
		Assertions.assertEquals(  2, testConnections9dot0.size() );
		Assertions.assertTrue( testNeuronAt0dot0.equals(testConnections9dot0.get(0)) );
		
		// List<Integer> testWeights9to0		= testNeuronAt9dot0.getWeights();
		Assertions.assertNotNull( testWeights9to0 );
		Assertions.assertEquals(  2, testWeights9to0.size() );
		Assertions.assertEquals( 42, testWeights9to0.get(0).intValue() );
		
	}
	
	@Test
	void testInstincts05() {

		Chromosome basicGenome				= new Chromosome();
		
		BrainGeneBuilder bgb				= new BrainGeneBuilder();
		BrainLobeGeneBuilder blgb			= new BrainLobeGeneBuilder();
		InstinctBuilder igb					= new InstinctBuilder();
		
		InitialConcentrationBuilder icb		= new InitialConcentrationBuilder();

		// ***** brain
		basicGenome.addGene( bgb.heigth(10).width(10).depth(1)
				.name( "BrainGene 10*10*1" )
				.agemin(0).agemax(0).mutation(25).activ(true).build() );
		// ***** a lobe on first line
		basicGenome.addGene( blgb
				.rest(0).threshold(10).desc(1).dmin(0).dmax(0)
				.prox(0).repr(false).repy(0).wta(false)
				.posx(0).posy(0).heigth(1).width(10).replace(false)
				.name("BrainLobeGene (0, 0), 1, 10, ")
				.agemin(0).agemax(0).mutation(25).activ(true).build() );
		// ***** a lobe on last line
		basicGenome.addGene( blgb
				.rest(0).threshold(10).desc(1).dmin(0).dmax(0)
				.prox(0).repr(false).repy(0).wta(false)
				.posx(9).posy(0).heigth(1).width(10).replace(false)
				.name("BrainLobeGene (9, 0), 1, 10, ")
				.agemin(0).agemax(0).mutation(25).activ(true).build() );
		
		basicGenome.addGene( icb	
				.varia( SomeChemicals.GLUCOSE.getIndex() ).value( 25 )
				.name( "InitConc GLUCOSE 25" )
					.mutate( true ).duplicate( true ).delete( true ).activ( true )
					.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		
		basicGenome.addGene( igb
				.inputPosX( 0 ).inputPosY( 0 ).outputPosX( 9 ).outputPosY( 0 )
				.weight( 42 ).variable( SomeChemicals.GLUCOSE.getIndex() )
				.threshold( 5 ).check( false ).positiv( true )
					.name( "Instinct (0, 0) => (9, 0) GLUCOSE > 25 ; weigth 42 (+)" )
					.mutate( true ).duplicate( true ).delete( true ).activ( true )
					.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
					.build() );
		
		basicGenome.addGene( igb
				.inputPosX( 0 ).inputPosY( 1 ).outputPosX( 9 ).outputPosY( 0 )
				.weight( 41 ).variable( SomeChemicals.GLUCOSE.getIndex() )
				.threshold( 5 ).check( false ).positiv( false )
					.name( "Instinct (0, 0) => (9, 0) GLUCOSE > 25 ; weigth 42 (-)" )
					.mutate( true ).duplicate( true ).delete( true ).activ( true )
					.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
					.build() );
		
		Organism testOrga		= new Organism(basicGenome);

		Assertions.assertEquals(6, basicGenome.length());
		Assertions.assertEquals(1, testOrga.getGenome().size());
		Assertions.assertEquals(6, testOrga.getGenome().get(0).length());
		
		// ***** test with a World and WorldCase
		
		World2D w			= new World2D(1, 1);
		World2DCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		
		Logger.printlnLog(LoggerLevel.LL_NONE, testOrga.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  1, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		
		Assertions.assertNotNull( testOrga.getBrain() );
		Neuron testNeuronAt9dot0 = testOrga.getBrain().getNeuronAt(9, 0);
		Assertions.assertNotNull( testNeuronAt9dot0 );
		Neuron testNeuronAt0dot0 = testOrga.getBrain().getNeuronAt(0, 0);
		Assertions.assertNotNull( testNeuronAt0dot0 );
		Neuron testNeuronAt0dot1 = testOrga.getBrain().getNeuronAt(0, 1);
		Assertions.assertNotNull( testNeuronAt0dot1 );
		
		Logger.printlnLog(LoggerLevel.LL_NONE, testOrga.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, StringUtils.repeat("+", 80) );
		
		// ***** Check connection from (9, 0) to (0, 0) and weights
		List<Neuron> testConnections9dot0	= testNeuronAt9dot0.getConnections();
		Assertions.assertNotNull( testConnections9dot0 );
		Assertions.assertEquals(  2, testConnections9dot0.size() );
		Assertions.assertTrue( testNeuronAt0dot0.equals(testConnections9dot0.get(0)) );
		
		List<Integer> testWeights9to0		= testNeuronAt9dot0.getWeights();
		Assertions.assertNotNull( testWeights9to0 );
		Assertions.assertEquals(  2, testWeights9to0.size() );
		Assertions.assertEquals( 42, testWeights9to0.get(0).intValue() );
		
		Assertions.assertEquals(  0, testNeuronAt0dot0.getActivity() );
		testNeuronAt0dot0.addActivity( 100 );
		Assertions.assertEquals(100, testNeuronAt0dot0.getActivity() );
		
		Assertions.assertEquals(  0, testNeuronAt0dot1.getActivity() );
		
		// ***** one MORE execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  2, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		
		Logger.printlnLog(LoggerLevel.LL_NONE, testOrga.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(99, testNeuronAt0dot0.getActivity() );
		Assertions.assertEquals( 0, testNeuronAt0dot1.getActivity() );
		Assertions.assertEquals(42, testNeuronAt9dot0.getActivity() );
		
		// ***** Check connection from (9, 0) to (0, 0) and weights
		Assertions.assertTrue( testNeuronAt9dot0.hasConnected(testNeuronAt0dot0) );
		Assertions.assertEquals( 0, testNeuronAt9dot0.getConnectPosition(testNeuronAt0dot0) );
		Assertions.assertFalse( testNeuronAt0dot0.hasConnected(testNeuronAt9dot0) );
		Assertions.assertEquals(-1, testNeuronAt0dot0.getConnectPosition(testNeuronAt9dot0) );
		// List<Neuron> testConnections9dot0	= testNeuronAt9dot0.getConnections();
		Assertions.assertNotNull( testConnections9dot0 );
		Assertions.assertEquals(  2, testConnections9dot0.size() );
		Assertions.assertTrue( testNeuronAt0dot0.equals(testConnections9dot0.get(0)) );
		
		// List<Integer> testWeights9to0		= testNeuronAt9dot0.getWeights();
		Assertions.assertNotNull( testWeights9to0 );
		Assertions.assertEquals(  2, testWeights9to0.size() );
		Assertions.assertEquals( 42, testWeights9to0.get(0).intValue() );
		
		// ***** one MORE MORE execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  3, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		
		Logger.printlnLog(LoggerLevel.LL_NONE, testOrga.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(98, testNeuronAt0dot0.getActivity() );
		Assertions.assertEquals( 0, testNeuronAt0dot1.getActivity() );
		Assertions.assertEquals(83, testNeuronAt9dot0.getActivity() ); // 42 + 42 - 1
		
		// ***** Check connection from (9, 0) to (0, 0) and weights
		Assertions.assertTrue( testNeuronAt9dot0.hasConnected(testNeuronAt0dot0) );
		Assertions.assertEquals( 0, testNeuronAt9dot0.getConnectPosition(testNeuronAt0dot0) );
		Assertions.assertFalse( testNeuronAt0dot0.hasConnected(testNeuronAt9dot0) );
		Assertions.assertEquals(-1, testNeuronAt0dot0.getConnectPosition(testNeuronAt9dot0) );
		// List<Neuron> testConnections9dot0	= testNeuronAt9dot0.getConnections();
		Assertions.assertNotNull( testConnections9dot0 );
		Assertions.assertEquals(  2, testConnections9dot0.size() );
		Assertions.assertTrue( testNeuronAt0dot0.equals(testConnections9dot0.get(0)) );
		
		// List<Integer> testWeights9to0		= testNeuronAt9dot0.getWeights();
		Assertions.assertNotNull( testWeights9to0 );
		Assertions.assertEquals(  2, testWeights9to0.size() );
		Assertions.assertEquals( 42, testWeights9to0.get(0).intValue() );
		
	}
	
	@Test
	void testInstincts06() {

		Chromosome basicGenome				= new Chromosome();
		
		BrainGeneBuilder bgb				= new BrainGeneBuilder();
		BrainLobeGeneBuilder blgb			= new BrainLobeGeneBuilder();
		InstinctBuilder igb					= new InstinctBuilder();
		
		InitialConcentrationBuilder icb		= new InitialConcentrationBuilder();

		// ***** brain
		basicGenome.addGene( bgb.heigth(10).width(10).depth(1)
				.name( "BrainGene 10*10*1" )
				.agemin(0).agemax(0).mutation(25).activ(true).build() );
		// ***** a lobe on first line
		basicGenome.addGene( blgb
				.rest(0).threshold(10).desc(1).dmin(0).dmax(0)
				.prox(0).repr(false).repy(0).wta(false)
				.posx(0).posy(0).heigth(1).width(10).replace(false)
				.name("BrainLobeGene (0, 0), 1, 10, ")
				.agemin(0).agemax(0).mutation(25).activ(true).build() );
		// ***** a lobe on last line
		basicGenome.addGene( blgb
				.rest(0).threshold(10).desc(1).dmin(0).dmax(0)
				.prox(0).repr(false).repy(0).wta(false)
				.posx(9).posy(0).heigth(1).width(10).replace(false)
				.name("BrainLobeGene (9, 0), 1, 10, ")
				.agemin(0).agemax(0).mutation(25).activ(true).build() );
		
		basicGenome.addGene( icb	
				.varia( SomeChemicals.GLUCOSE.getIndex() ).value( 25 )
				.name( "InitConc GLUCOSE 25" )
					.mutate( true ).duplicate( true ).delete( true ).activ( true )
					.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		
		basicGenome.addGene( igb
				.inputPosX( 0 ).inputPosY( 0 ).outputPosX( 9 ).outputPosY( 0 )
				.weight( 42 ).variable( SomeChemicals.GLUCOSE.getIndex() )
				.threshold( 5 ).check( false ).positiv( true )
					.name( "Instinct (0, 0) => (9, 0) GLUCOSE > 25 ; weigth 42 (+)" )
					.mutate( true ).duplicate( true ).delete( true ).activ( true )
					.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
					.build() );
		
		basicGenome.addGene( igb
				.inputPosX( 0 ).inputPosY( 1 ).outputPosX( 9 ).outputPosY( 0 )
				.weight( 41 ).variable( SomeChemicals.GLUCOSE.getIndex() )
				.threshold( 5 ).check( false ).positiv( false )
					.name( "Instinct (0, 0) => (9, 0) GLUCOSE > 25 ; weigth 42 (-)" )
					.mutate( true ).duplicate( true ).delete( true ).activ( true )
					.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
					.build() );
		
		Organism testOrga		= new Organism(basicGenome);

		Assertions.assertEquals(6, basicGenome.length());
		Assertions.assertEquals(1, testOrga.getGenome().size());
		Assertions.assertEquals(6, testOrga.getGenome().get(0).length());
		
		// ***** test with a World and WorldCase
		
		World2D w			= new World2D(1, 1);
		World2DCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		
		Logger.printlnLog(LoggerLevel.LL_NONE, testOrga.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  1, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		
		Assertions.assertNotNull( testOrga.getBrain() );
		Neuron testNeuronAt9dot0 = testOrga.getBrain().getNeuronAt(9, 0);
		Assertions.assertNotNull( testNeuronAt9dot0 );
		Neuron testNeuronAt0dot0 = testOrga.getBrain().getNeuronAt(0, 0);
		Assertions.assertNotNull( testNeuronAt0dot0 );
		Neuron testNeuronAt0dot1 = testOrga.getBrain().getNeuronAt(0, 1);
		Assertions.assertNotNull( testNeuronAt0dot1 );
		
		Logger.printlnLog(LoggerLevel.LL_NONE, testOrga.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, StringUtils.repeat("+", 80) );
		
		// ***** Check connection from (9, 0) to (0, 0) and weights
		List<Neuron> testConnections9dot0	= testNeuronAt9dot0.getConnections();
		Assertions.assertNotNull( testConnections9dot0 );
		Assertions.assertEquals(  2, testConnections9dot0.size() );
		Assertions.assertTrue( testNeuronAt0dot0.equals(testConnections9dot0.get(0)) );
		Assertions.assertTrue( testNeuronAt0dot1.equals(testConnections9dot0.get(1)) );
		
		List<Integer> testWeights9to0		= testNeuronAt9dot0.getWeights();
		Assertions.assertNotNull( testWeights9to0 );
		Assertions.assertEquals(  2, testWeights9to0.size() );
		Assertions.assertEquals( 42, testWeights9to0.get(0).intValue() );
		
		Assertions.assertEquals(  0, testNeuronAt0dot0.getActivity() );
		testNeuronAt0dot0.addActivity( 100 );
		Assertions.assertEquals(100, testNeuronAt0dot0.getActivity() );
		
		Assertions.assertEquals(  0, testNeuronAt0dot1.getActivity() );
		testNeuronAt0dot1.addActivity( 100 );
		Assertions.assertEquals(100, testNeuronAt0dot1.getActivity() );
		
		// ***** one MORE execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  2, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		
		Logger.printlnLog(LoggerLevel.LL_NONE, testOrga.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(99, testNeuronAt0dot0.getActivity() );
		Assertions.assertEquals(99, testNeuronAt0dot1.getActivity() );
		Assertions.assertEquals( 1, testNeuronAt9dot0.getActivity() );
		
		// ***** Check connection from (9, 0) to (0, 0) and weights
		Assertions.assertTrue( testNeuronAt9dot0.hasConnected(testNeuronAt0dot0) );
		Assertions.assertEquals( 0, testNeuronAt9dot0.getConnectPosition(testNeuronAt0dot0) );
		Assertions.assertFalse( testNeuronAt0dot0.hasConnected(testNeuronAt9dot0) );
		Assertions.assertEquals(-1, testNeuronAt0dot0.getConnectPosition(testNeuronAt9dot0) );
		Assertions.assertFalse( testNeuronAt0dot1.hasConnected(testNeuronAt9dot0) );
		Assertions.assertEquals(-1, testNeuronAt0dot1.getConnectPosition(testNeuronAt9dot0) );
		// List<Neuron> testConnections9dot0	= testNeuronAt9dot0.getConnections();
		Assertions.assertNotNull( testConnections9dot0 );
		Assertions.assertEquals(  2, testConnections9dot0.size() );
		Assertions.assertTrue( testNeuronAt0dot0.equals(testConnections9dot0.get(0)) );
		Assertions.assertTrue( testNeuronAt0dot1.equals(testConnections9dot0.get(1)) );
		
		// List<Integer> testWeights9to0		= testNeuronAt9dot0.getWeights();
		Assertions.assertNotNull( testWeights9to0 );
		Assertions.assertEquals(  2, testWeights9to0.size() );
		Assertions.assertEquals( 42, testWeights9to0.get(0).intValue() );
		
		// ***** one MORE MORE execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  3, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		
		Logger.printlnLog(LoggerLevel.LL_NONE, testOrga.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(98, testNeuronAt0dot0.getActivity() );
		Assertions.assertEquals(98, testNeuronAt0dot1.getActivity() );
		Assertions.assertEquals( 1, testNeuronAt9dot0.getActivity() ); // 42 + 42 - 1
		
		// ***** Check connection from (9, 0) to (0, 0) and weights
		Assertions.assertTrue( testNeuronAt9dot0.hasConnected(testNeuronAt0dot0) );
		Assertions.assertEquals( 0, testNeuronAt9dot0.getConnectPosition(testNeuronAt0dot0) );
		Assertions.assertFalse( testNeuronAt0dot0.hasConnected(testNeuronAt9dot0) );
		Assertions.assertEquals(-1, testNeuronAt0dot0.getConnectPosition(testNeuronAt9dot0) );
		Assertions.assertFalse( testNeuronAt0dot1.hasConnected(testNeuronAt9dot0) );
		Assertions.assertEquals(-1, testNeuronAt0dot1.getConnectPosition(testNeuronAt9dot0) );
		// List<Neuron> testConnections9dot0	= testNeuronAt9dot0.getConnections();
		Assertions.assertNotNull( testConnections9dot0 );
		Assertions.assertEquals(  2, testConnections9dot0.size() );
		Assertions.assertTrue( testNeuronAt0dot0.equals(testConnections9dot0.get(0)) );
		Assertions.assertTrue( testNeuronAt0dot1.equals(testConnections9dot0.get(1)) );
		
		// List<Integer> testWeights9to0		= testNeuronAt9dot0.getWeights();
		Assertions.assertNotNull( testWeights9to0 );
		Assertions.assertEquals(  2, testWeights9to0.size() );
		Assertions.assertEquals( 42, testWeights9to0.get(0).intValue() );
		
	}

}

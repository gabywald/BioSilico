package gabywald.biosilico.model.tests;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.genetics.BiochemicalReaction;
import gabywald.biosilico.genetics.BrainGene;
import gabywald.biosilico.genetics.BrainLobeGene;
import gabywald.biosilico.genetics.InitialConcentration;
import gabywald.biosilico.genetics.builders.BrainGeneBuilder;
import gabywald.biosilico.genetics.builders.BrainLobeGeneBuilder;
import gabywald.biosilico.model.Brain;
import gabywald.biosilico.model.Chromosome;
import gabywald.biosilico.model.Neuron;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.enums.StateType;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class OrganismBuildExampleTests {

	@Test
	void testInitConc001() {

		Chromosome basicGenome = new Chromosome();
		basicGenome.addGene(new InitialConcentration(false, false, false, false, 0, 0, 0, 0,
								1, 100));
		Organism test		= new Organism(basicGenome);

		Assertions.assertEquals(  0, test.getVariables().getVariable( 1 ) );

		Assertions.assertEquals(0, test.getVariables().getVariable( StateType.AGING.getIndex() ) );
		test.execution(null);
		Assertions.assertEquals(0, test.getVariables().getVariable( StateType.AGING.getIndex() ) );

		Assertions.assertEquals(1, basicGenome.length());
		Assertions.assertEquals(1, test.getGenome().size());
		Assertions.assertEquals(1, test.getGenome().get(0).length());

		Assertions.assertEquals(0, test.getVariables().getVariable( 1 ) );
	}

	@Test
	void testInitConc002() {

		Chromosome basicGenome = new Chromosome();
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
								1, 100));
		Organism test		= new Organism(basicGenome);

		Assertions.assertEquals(  0, test.getVariables().getVariable( 1 ) );

		Assertions.assertEquals(0, test.getVariables().getVariable( StateType.AGING.getIndex() ) );
		test.execution(null);
		Assertions.assertEquals(0, test.getVariables().getVariable( StateType.AGING.getIndex() ) );

		Assertions.assertEquals(1, basicGenome.length());
		Assertions.assertEquals(1, test.getGenome().size());
		Assertions.assertEquals(1, test.getGenome().get(0).length());

		Assertions.assertEquals(100, test.getVariables().getVariable( 1 ) );
	}

	@Test
	void testInitConc003() {

		Chromosome basicGenome = new Chromosome();
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
								1, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
								2, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
								3, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
								4, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
								5, 100));
		Organism test		= new Organism(basicGenome);

		Assertions.assertEquals(  0, test.getVariables().getVariable( 1 ) );
		Assertions.assertEquals(  0, test.getVariables().getVariable( 2 ) );
		Assertions.assertEquals(  0, test.getVariables().getVariable( 3 ) );
		Assertions.assertEquals(  0, test.getVariables().getVariable( 4 ) );
		Assertions.assertEquals(  0, test.getVariables().getVariable( 5 ) );

		Assertions.assertEquals(0, test.getVariables().getVariable( StateType.AGING.getIndex() ) );
		test.execution(null);
		Assertions.assertEquals(0, test.getVariables().getVariable( StateType.AGING.getIndex() ) );

		Assertions.assertEquals(5, basicGenome.length());
		Assertions.assertEquals(1, test.getGenome().size());
		Assertions.assertEquals(5, test.getGenome().get(0).length());

		Assertions.assertEquals(100, test.getVariables().getVariable( 1 ) );
		Assertions.assertEquals(100, test.getVariables().getVariable( 2 ) );
		Assertions.assertEquals(100, test.getVariables().getVariable( 3 ) );
		Assertions.assertEquals(100, test.getVariables().getVariable( 4 ) );
		Assertions.assertEquals(100, test.getVariables().getVariable( 5 ) );
	}

	@Test
	void testInitConcWithBiochemicalReaction01() {

		Logger.setLogLevel(LoggerLevel.LL_DEBUG);

		Chromosome basicGenome = new Chromosome();
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						1, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						2, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						3, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						4, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						5, 100));
		basicGenome.addGene(new BiochemicalReaction(false, false, false, true, 0, 10, 0, 0,
						1, 10, 
						2, 10, 
						6, 5, 
						7, 5, 
						1));

		Organism test		= new Organism(basicGenome);
		Assertions.assertEquals(0, test.getVariables().getVariable( StateType.AGING.getIndex() ) );

		// first execution / cycle 0
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "cycle 0");
		test.execution(null);
		test.cyclePlusPlus();
		
		Assertions.assertEquals(1, test.getVariables().getVariable( StateType.AGING.getIndex() ) );

		Assertions.assertEquals(6, basicGenome.length());
		Assertions.assertEquals(1, test.getGenome().size());
		Assertions.assertEquals(6, test.getGenome().get(0).length());

		Assertions.assertEquals( 90, test.getVariables().getVariable( 1 ) );
		Assertions.assertEquals( 90, test.getVariables().getVariable( 2 ) );
		Assertions.assertEquals(100, test.getVariables().getVariable( 3 ) );
		Assertions.assertEquals(100, test.getVariables().getVariable( 4 ) );
		Assertions.assertEquals(100, test.getVariables().getVariable( 5 ) );
		Assertions.assertEquals(  5, test.getVariables().getVariable( 6 ) );
		Assertions.assertEquals(  5, test.getVariables().getVariable( 7 ) );

		// second execution / cycle 1
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "cycle 1");
		test.execution(null);
		test.cyclePlusPlus();
		
		Assertions.assertEquals(2, test.getVariables().getVariable( StateType.AGING.getIndex() ) );

		Assertions.assertEquals(6, basicGenome.length());
		Assertions.assertEquals(1, test.getGenome().size());
		Assertions.assertEquals(6, test.getGenome().get(0).length());

		Assertions.assertEquals( 80, test.getVariables().getVariable( 1 ) );
		Assertions.assertEquals( 80, test.getVariables().getVariable( 2 ) );
		Assertions.assertEquals(100, test.getVariables().getVariable( 3 ) );
		Assertions.assertEquals(100, test.getVariables().getVariable( 4 ) );
		Assertions.assertEquals(100, test.getVariables().getVariable( 5 ) );
		Assertions.assertEquals( 10, test.getVariables().getVariable( 6 ) );
		Assertions.assertEquals( 10, test.getVariables().getVariable( 7 ) );

		// third execution / cycle 2
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "cycle 2");
		test.execution(null);
		test.cyclePlusPlus();
		
		Assertions.assertEquals(3, test.getVariables().getVariable( StateType.AGING.getIndex() ) );

		Assertions.assertEquals(6, basicGenome.length());
		Assertions.assertEquals(1, test.getGenome().size());
		Assertions.assertEquals(6, test.getGenome().get(0).length());

		Assertions.assertEquals( 70, test.getVariables().getVariable( 1 ) );
		Assertions.assertEquals( 70, test.getVariables().getVariable( 2 ) );
		Assertions.assertEquals(100, test.getVariables().getVariable( 3 ) );
		Assertions.assertEquals(100, test.getVariables().getVariable( 4 ) );
		Assertions.assertEquals(100, test.getVariables().getVariable( 5 ) );
		Assertions.assertEquals( 15, test.getVariables().getVariable( 6 ) );
		Assertions.assertEquals( 15, test.getVariables().getVariable( 7 ) );

		Logger.printlnLog(LoggerLevel.LL_DEBUG, "*****");

	}

	@Test
	void testInitConcWithBiochemicalReaction02() {

		Logger.setLogLevel(LoggerLevel.LL_DEBUG);

		Chromosome basicGenome = new Chromosome();
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						1, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						2, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						3, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						4, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						5, 100));
		basicGenome.addGene(new BiochemicalReaction(false, false, false, true, 
						0, 10, 0, 0,
						1, 10, 
						2, 10, 
						6, 5, 
						7, 5, 
						2));

		Organism test		= new Organism(basicGenome);
		
		Assertions.assertEquals(0, test.getVariables().getVariable( StateType.AGING.getIndex() ) );

		// first execution / cycle 0
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "cycle 0");
		test.execution(null);
		test.cyclePlusPlus();
		
		Assertions.assertEquals(1, test.getVariables().getVariable( StateType.AGING.getIndex() ) );

		Assertions.assertEquals(6, basicGenome.length());
		Assertions.assertEquals(1, test.getGenome().size());
		Assertions.assertEquals(6, test.getGenome().get(0).length());

		Assertions.assertEquals( 80, test.getVariables().getVariable( 1 ) );
		Assertions.assertEquals( 80, test.getVariables().getVariable( 2 ) );
		Assertions.assertEquals(100, test.getVariables().getVariable( 3 ) );
		Assertions.assertEquals(100, test.getVariables().getVariable( 4 ) );
		Assertions.assertEquals(100, test.getVariables().getVariable( 5 ) );
		Assertions.assertEquals( 10, test.getVariables().getVariable( 6 ) );
		Assertions.assertEquals( 10, test.getVariables().getVariable( 7 ) );

		// second execution / cycle 1
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "cycle 1");
		test.execution(null);
		test.cyclePlusPlus();
		
		Assertions.assertEquals(2, test.getVariables().getVariable( StateType.AGING.getIndex() ) );

		Assertions.assertEquals(6, basicGenome.length());
		Assertions.assertEquals(1, test.getGenome().size());
		Assertions.assertEquals(6, test.getGenome().get(0).length());

		Assertions.assertEquals( 60, test.getVariables().getVariable( 1 ) );
		Assertions.assertEquals( 60, test.getVariables().getVariable( 2 ) );
		Assertions.assertEquals(100, test.getVariables().getVariable( 3 ) );
		Assertions.assertEquals(100, test.getVariables().getVariable( 4 ) );
		Assertions.assertEquals(100, test.getVariables().getVariable( 5 ) );
		Assertions.assertEquals( 20, test.getVariables().getVariable( 6 ) );
		Assertions.assertEquals( 20, test.getVariables().getVariable( 7 ) );

		Logger.printlnLog(LoggerLevel.LL_DEBUG, "*****");
	}

	@Test
	void testInitConcWithBiochemicalReaction03() {

		Logger.setLogLevel(LoggerLevel.LL_NONE);

		Chromosome basicGenome = new Chromosome();
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						1, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						2, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						3, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						4, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						5, 100));
		basicGenome.addGene(new BiochemicalReaction(false, false, false, false, 
						0, 10, 0, 0,
						1, 10, 
						2, 10, 
						6, 5, 
						7, 5, 
						1));

		Organism test		= new Organism(basicGenome);
		
		Assertions.assertEquals(0, test.getVariables().getVariable( StateType.AGING.getIndex() ) );

		Assertions.assertEquals(6, basicGenome.length());
		Assertions.assertEquals(1, test.getGenome().size());
		Assertions.assertEquals(6, test.getGenome().get(0).length());

		Assertions.assertEquals(  0, test.getVariables().getVariable( 1 ) );
		Assertions.assertEquals(  0, test.getVariables().getVariable( 2 ) );
		Assertions.assertEquals(  0, test.getVariables().getVariable( 3 ) );
		Assertions.assertEquals(  0, test.getVariables().getVariable( 4 ) );
		Assertions.assertEquals(  0, test.getVariables().getVariable( 5 ) );
		Assertions.assertEquals(  0, test.getVariables().getVariable( 6 ) );
		Assertions.assertEquals(  0, test.getVariables().getVariable( 7 ) );

		test.execution(null);
		
		Assertions.assertEquals(0, test.getVariables().getVariable( StateType.AGING.getIndex() ) );

		Assertions.assertEquals(6, basicGenome.length());
		Assertions.assertEquals(1, test.getGenome().size());
		Assertions.assertEquals(6, test.getGenome().get(0).length());

		Assertions.assertEquals(100, test.getVariables().getVariable( 1 ) );
		Assertions.assertEquals(100, test.getVariables().getVariable( 2 ) );
		Assertions.assertEquals(100, test.getVariables().getVariable( 3 ) );
		Assertions.assertEquals(100, test.getVariables().getVariable( 4 ) );
		Assertions.assertEquals(100, test.getVariables().getVariable( 5 ) );
		Assertions.assertEquals(  0, test.getVariables().getVariable( 6 ) );
		Assertions.assertEquals(  0, test.getVariables().getVariable( 7 ) );
	}

	@Test
	void testBrainGene01() {

		Chromosome basicGenome = new Chromosome();
		basicGenome.addGene(
				new BrainGene(	false, false, false, false, 
						0, 0, 0, 0,
						100, 200, 0, 0));
		Organism test		= new Organism(basicGenome);
		
		test.execution(null);
		
		Assertions.assertEquals(1, basicGenome.length());
		Assertions.assertEquals(1, test.getGenome().size());
		Assertions.assertEquals(1, test.getGenome().get(0).length());

		Assertions.assertNull(test.getBrain());

	}

	@Test
	void testBrainGene02() {

		Chromosome basicGenome = new Chromosome();
		basicGenome.addGene(
				new BrainGene(	false, false, false, true, 
						0, 0, 0, 0,
						100, 200, 0, 0));
		Organism test		= new Organism(basicGenome);

		Assertions.assertNull(test.getBrain());
		
		test.execution(null);
		
		Assertions.assertEquals(1, basicGenome.length());
		Assertions.assertEquals(1, test.getGenome().size());
		Assertions.assertEquals(1, test.getGenome().get(0).length());

		Assertions.assertNotNull(test.getBrain());

		Assertions.assertEquals(100, test.getBrain().getHeight() );
		Assertions.assertEquals(100, test.getBrain().getWidth() );
	}

	@Test
	void testBrainGene03() {

		Chromosome basicGenome = new Chromosome();
		basicGenome.addGene(
				new BrainGene(	false, false, false, true, 
						0, 0, 0, 0,
						100, 50, 0, 0));
		Organism test		= new Organism(basicGenome);

		Assertions.assertNull(test.getBrain());

		test.execution(null);
		test.cyclePlusPlus();

		Assertions.assertEquals(1, basicGenome.length());
		Assertions.assertEquals(1, test.getGenome().size());
		Assertions.assertEquals(1, test.getGenome().get(0).length());

		Assertions.assertNotNull(test.getBrain());

		Assertions.assertEquals(100, test.getBrain().getHeight() );
		Assertions.assertEquals( 50, test.getBrain().getWidth() );
	}

	@Test
	void testBrainGene04() {

		Chromosome basicGenome = new Chromosome();
		basicGenome.addGene(
				new BrainGene(	false, false, false, true, 
						0, 0, 0, 0,
						100, 100, 0, 0));
		Organism test		= new Organism(basicGenome);

		Assertions.assertNull(test.getBrain());

		test.execution(null);
		test.cyclePlusPlus();

		Assertions.assertEquals(1, basicGenome.length());
		Assertions.assertEquals(1, test.getGenome().size());
		Assertions.assertEquals(1, test.getGenome().get(0).length());

		Assertions.assertNotNull(test.getBrain());

		Assertions.assertEquals(100, test.getBrain().getHeight() );
		Assertions.assertEquals(100, test.getBrain().getWidth() );
	}

	@Test
	void testBrainGene05() {

		Chromosome basicGenome = new Chromosome();

		BrainGeneBuilder bgb	= new BrainGeneBuilder();
		BrainGene brainGene		= bgb.heigth(100).width(100).depth(1).activ(true)
				.agemin(0).agemax(0).mutation(0).build();

		basicGenome.addGene( brainGene );

		BrainLobeGeneBuilder blgb	= new BrainLobeGeneBuilder();
		BrainLobeGene brainLobeGene	= blgb.heigth(1).width(50)
				.rest(0).threshold(100).desc(10).dmin(0).dmax(0)
				.prox(0).repr(false).repy(0).wta(false)
				.posx(0).posy(0).replace(false)
				.agemin(0).agemax(0).mutation(25).activ(true).build();

		basicGenome.addGene( brainLobeGene );

		Organism test		= new Organism(basicGenome);

		test.execution(null);
		test.cyclePlusPlus();

		Assertions.assertEquals(2, basicGenome.length());
		Assertions.assertEquals(1, test.getGenome().size());
		Assertions.assertEquals(2, test.getGenome().get(0).length());

		Assertions.assertNotNull(test.getBrain());

		Assertions.assertEquals(100, test.getBrain().getHeight() );
		Assertions.assertEquals(100, test.getBrain().getWidth() );

		Brain testBrain = test.getBrain();
		IntStream.range(0, 50).forEach( j -> {
			Neuron currentNeuron = testBrain.getNeuronAt(0, j);
			Assertions.assertNotNull( currentNeuron );

			Assertions.assertEquals(    0, currentNeuron.getRestState());
			Assertions.assertEquals(  100, currentNeuron.getThreshold());
			Assertions.assertEquals(   10, currentNeuron.getDescent());
			Assertions.assertEquals(    0, currentNeuron.getDmin());
			Assertions.assertEquals(    0, currentNeuron.getDmax());
			Assertions.assertEquals(    0, currentNeuron.getProximity());
			Assertions.assertEquals(false, currentNeuron.getReproduction());
			Assertions.assertEquals(    0, currentNeuron.getReproductibility());
			Assertions.assertEquals(false, currentNeuron.isWTA());

		});

	}

	@Test
	void testBrainGene06() {

		Chromosome basicGenome = new Chromosome();

		BrainGeneBuilder bgb	= new BrainGeneBuilder();
		BrainGene brainGene		= bgb.heigth(100).width(100).depth(1).activ(true)
				.agemin(0).agemax(0).mutation(0).build();

		basicGenome.addGene( brainGene );

		BrainLobeGeneBuilder blgb		= new BrainLobeGeneBuilder();
		BrainLobeGene brainLobeGene01	= blgb.heigth(1).width(50)
				.rest(0).threshold(100).desc(10).dmin(0).dmax(0)
				.prox(0).repr(false).repy(0).wta(false)
				.posx(0).posy(0).replace(false)
				.agemin(0).agemax(0).mutation(25).activ(true).build();

		basicGenome.addGene( brainLobeGene01 );

		BrainLobeGene brainLobeGene02	= blgb.heigth(1).width(25)
				.rest(0).threshold(10).desc( 9).dmin(1).dmax(5)
				.prox(3).repr(false).repy(10).wta(true)
				.posx(2).posy(0).replace(false)
				.agemin(0).agemax(0).mutation(25).activ(true).build();

		basicGenome.addGene( brainLobeGene02 );

		Organism test		= new Organism(basicGenome);

		test.execution(null);
		test.cyclePlusPlus();

		Assertions.assertEquals(3, basicGenome.length());
		Assertions.assertEquals(1, test.getGenome().size());
		Assertions.assertEquals(3, test.getGenome().get(0).length());

		Assertions.assertNotNull(test.getBrain());

		Assertions.assertEquals(100, test.getBrain().getHeight() );
		Assertions.assertEquals(100, test.getBrain().getWidth() );

		Brain testBrain = test.getBrain();
		IntStream.range(0, 50).forEach( j -> {
			Neuron currentNeuron = testBrain.getNeuronAt(0, j);
			Assertions.assertNotNull( currentNeuron );

			Assertions.assertEquals(    0, currentNeuron.getRestState());
			Assertions.assertEquals(  100, currentNeuron.getThreshold());
			Assertions.assertEquals(   10, currentNeuron.getDescent());
			Assertions.assertEquals(    0, currentNeuron.getDmin());
			Assertions.assertEquals(    0, currentNeuron.getDmax());
			Assertions.assertEquals(    0, currentNeuron.getProximity());
			Assertions.assertEquals(false, currentNeuron.getReproduction());
			Assertions.assertEquals(    0, currentNeuron.getReproductibility());
			Assertions.assertEquals(false, currentNeuron.isWTA());

		});

		IntStream.range(0, 25).forEach( j -> {
			Neuron currentNeuron = testBrain.getNeuronAt(2, j);
			Assertions.assertNotNull( currentNeuron );

			Assertions.assertEquals(    0, currentNeuron.getRestState());
			Assertions.assertEquals(   10, currentNeuron.getThreshold());
			Assertions.assertEquals(    9, currentNeuron.getDescent());
			Assertions.assertEquals(    1, currentNeuron.getDmin());
			Assertions.assertEquals(    5, currentNeuron.getDmax());
			Assertions.assertEquals(    3, currentNeuron.getProximity());
			Assertions.assertEquals(false, currentNeuron.getReproduction());
			Assertions.assertEquals(   10, currentNeuron.getReproductibility());
			Assertions.assertEquals( true, currentNeuron.isWTA());

		});

	}

	@Test
	void testBrainGene07() {

		Chromosome basicGenome = new Chromosome();

		BrainGeneBuilder bgb	= new BrainGeneBuilder();
		BrainGene brainGene		= bgb.heigth(100).width(100).depth(1).activ(true)
				.agemin(0).agemax(0).mutation(0).build();

		basicGenome.addGene( brainGene );

		BrainLobeGeneBuilder blgb		= new BrainLobeGeneBuilder();
		BrainLobeGene brainLobeGene01	= blgb.heigth(1).width(50)
				.rest(0).threshold(100).desc(10).dmin(0).dmax(0)
				.prox(0).repr(false).repy(0).wta(false)
				.posx(0).posy(0).replace(false)
				.agemin(0).agemax(0).mutation(25).activ(true).build();

		basicGenome.addGene( brainLobeGene01 );

		BrainLobeGene brainLobeGene02	= blgb.heigth(1).width(25)
				.rest(0).threshold(10).desc( 9).dmin(1).dmax(5)
				.prox(3).repr(false).repy(10).wta(true)
				.posx(2).posy(0).replace(false)
				.agemin(0).agemax(0).mutation(25).activ(true).build();

		basicGenome.addGene( brainLobeGene02 );

		BrainLobeGene brainLobeGene03	= blgb.heigth(3).width(5)
				.rest(0).threshold(10).desc( 9).dmin(1).dmax(5)
				.prox(3).repr(true).repy(10).wta(true)
				.posx(5).posy(1).replace(false)
				.agemin(0).agemax(0).mutation(25).activ(true).build();

		basicGenome.addGene( brainLobeGene03 );

		BrainLobeGene brainLobeGene03b	= blgb.heigth(3).width(5)
				.rest(0).threshold(10).desc( 9).dmin(1).dmax(5)
				.prox(3).repr(true).repy(10).wta(true)
				.posx(5).posy(12).replace(false)
				.agemin(0).agemax(0).mutation(25).activ(true).build();

		basicGenome.addGene( brainLobeGene03b );

		Organism test		= new Organism(basicGenome);

		test.execution(null);
		test.cyclePlusPlus();

		Assertions.assertEquals(5, basicGenome.length());
		Assertions.assertEquals(1, test.getGenome().size());
		Assertions.assertEquals(5, test.getGenome().get(0).length());

		Assertions.assertNotNull(test.getBrain());

		Assertions.assertEquals(100, test.getBrain().getHeight() );
		Assertions.assertEquals(100, test.getBrain().getWidth() );

		Brain testBrain = test.getBrain();
		IntStream.range(0, 50).forEach( j -> {
			Neuron currentNeuron = testBrain.getNeuronAt(0, j);
			Assertions.assertNotNull( currentNeuron );

			Assertions.assertEquals(    0, currentNeuron.getRestState());
			Assertions.assertEquals(  100, currentNeuron.getThreshold());
			Assertions.assertEquals(   10, currentNeuron.getDescent());
			Assertions.assertEquals(    0, currentNeuron.getDmin());
			Assertions.assertEquals(    0, currentNeuron.getDmax());
			Assertions.assertEquals(    0, currentNeuron.getProximity());
			Assertions.assertEquals(false, currentNeuron.getReproduction());
			Assertions.assertEquals(    0, currentNeuron.getReproductibility());
			Assertions.assertEquals(false, currentNeuron.isWTA());

		});

		IntStream.range(0, 25).forEach( j -> {
			Neuron currentNeuron = testBrain.getNeuronAt(2, j);
			Assertions.assertNotNull( currentNeuron );

			Assertions.assertEquals(    0, currentNeuron.getRestState());
			Assertions.assertEquals(   10, currentNeuron.getThreshold());
			Assertions.assertEquals(    9, currentNeuron.getDescent());
			Assertions.assertEquals(    1, currentNeuron.getDmin());
			Assertions.assertEquals(    5, currentNeuron.getDmax());
			Assertions.assertEquals(    3, currentNeuron.getProximity());
			Assertions.assertEquals(false, currentNeuron.getReproduction());
			Assertions.assertEquals(   10, currentNeuron.getReproductibility());
			Assertions.assertEquals( true, currentNeuron.isWTA());

		});

		IntStream.range(1,  6).forEach( j -> {
			Neuron currentNeuron = testBrain.getNeuronAt(5, j);
			Assertions.assertNotNull( currentNeuron );

			Assertions.assertEquals(    0, currentNeuron.getRestState());
			Assertions.assertEquals(   10, currentNeuron.getThreshold());
			Assertions.assertEquals(    9, currentNeuron.getDescent());
			Assertions.assertEquals(    1, currentNeuron.getDmin());
			Assertions.assertEquals(    5, currentNeuron.getDmax());
			Assertions.assertEquals(    3, currentNeuron.getProximity());
			Assertions.assertEquals( true, currentNeuron.getReproduction());
			Assertions.assertEquals(   10, currentNeuron.getReproductibility());
			Assertions.assertEquals( true, currentNeuron.isWTA());

		});

		IntStream.range(12, 17).forEach( j -> {
			Neuron currentNeuron = testBrain.getNeuronAt(5, j);
			Assertions.assertNotNull( currentNeuron );

			Assertions.assertEquals(    0, currentNeuron.getRestState());
			Assertions.assertEquals(   10, currentNeuron.getThreshold());
			Assertions.assertEquals(    9, currentNeuron.getDescent());
			Assertions.assertEquals(    1, currentNeuron.getDmin());
			Assertions.assertEquals(    5, currentNeuron.getDmax());
			Assertions.assertEquals(    3, currentNeuron.getProximity());
			Assertions.assertEquals( true, currentNeuron.getReproduction());
			Assertions.assertEquals(   10, currentNeuron.getReproductibility());
			Assertions.assertEquals( true, currentNeuron.isWTA());

		});

	}

}

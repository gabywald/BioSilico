package gabywald.biosilico.model.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.genetics.BiochemicalReaction;
import gabywald.biosilico.genetics.BrainGene;
import gabywald.biosilico.genetics.InitialConcentration;
import gabywald.biosilico.genetics.builders.BrainGeneBuilder;
import gabywald.biosilico.genetics.builders.BrainLobeGeneBuilder;
import gabywald.biosilico.model.Chromosome;
import gabywald.biosilico.model.Organism;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

class OrganismBuildExampleTests {

	@Test
	void testInitConc001() {
		
		Chromosome basic_genome = new Chromosome();
		basic_genome.addGene(
				new InitialConcentration(false, false, false, false, 
										 0, 0, 0, 0,
										 1, 100));
		Organism test		= new Organism(basic_genome);
		
		test.execution(null);
		
		Assertions.assertEquals(1, basic_genome.length());
		Assertions.assertEquals(1, test.getGenome().size());
		Assertions.assertEquals(1, test.getGenome().get(0).length());
		
		Assertions.assertEquals(0, test.getVariables().getVariable( 1 ) );
	}
	
	@Test
	void testInitConc002() {
		
		Chromosome basic_genome = new Chromosome();
		basic_genome.addGene(
				new InitialConcentration(false, false, false, true, 
										 0, 0, 0, 0,
										 1, 100));
		Organism test		= new Organism(basic_genome);
		
		test.execution(null);
		
		Assertions.assertEquals(1, basic_genome.length());
		Assertions.assertEquals(1, test.getGenome().size());
		Assertions.assertEquals(1, test.getGenome().get(0).length());
		
		Assertions.assertEquals(100, test.getVariables().getVariable( 1 ) );
	}
	
	@Test
	void testInitConc003() {
		
		Chromosome basic_genome = new Chromosome();
		basic_genome.addGene(
				new InitialConcentration(false, false, false, true, 
										 0, 0, 0, 0,
										 1, 100));
		basic_genome.addGene(
				new InitialConcentration(false, false, false, true, 
										 0, 0, 0, 0,
										 2, 100));
		basic_genome.addGene(
				new InitialConcentration(false, false, false, true, 
										 0, 0, 0, 0,
										 3, 100));
		basic_genome.addGene(
				new InitialConcentration(false, false, false, true, 
										 0, 0, 0, 0,
										 4, 100));
		basic_genome.addGene(
				new InitialConcentration(false, false, false, true, 
										 0, 0, 0, 0,
										 5, 100));
		Organism test		= new Organism(basic_genome);
		
		test.execution(null);
		
		Assertions.assertEquals(5, basic_genome.length());
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
		
		Chromosome basic_genome = new Chromosome();
		basic_genome.addGene(
				new InitialConcentration(false, false, false, true, 
										 0, 0, 0, 0,
										 1, 100));
		basic_genome.addGene(
				new InitialConcentration(false, false, false, true, 
										 0, 0, 0, 0,
										 2, 100));
		basic_genome.addGene(
				new InitialConcentration(false, false, false, true, 
										 0, 0, 0, 0,
										 3, 100));
		basic_genome.addGene(
				new InitialConcentration(false, false, false, true, 
										 0, 0, 0, 0,
										 4, 100));
		basic_genome.addGene(
				new InitialConcentration(false, false, false, true, 
										 0, 0, 0, 0,
										 5, 100));
		basic_genome.addGene(new BiochemicalReaction(false, false, false, true, 
				0, 10, 0, 0,
				1, 10, 
				2, 10, 
				6, 5, 
				7, 5, 
				1));
		
		Organism test		= new Organism(basic_genome);
		
		// first execution / cycle 0
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "cycle 0");
		test.execution(null);
		test.cyclePlusPlus();
		
		Assertions.assertEquals(6, basic_genome.length());
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
		
		Assertions.assertEquals(6, basic_genome.length());
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
		
		Assertions.assertEquals(6, basic_genome.length());
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
		
		Chromosome basic_genome = new Chromosome();
		basic_genome.addGene(
				new InitialConcentration(false, false, false, true, 
										 0, 0, 0, 0,
										 1, 100));
		basic_genome.addGene(
				new InitialConcentration(false, false, false, true, 
										 0, 0, 0, 0,
										 2, 100));
		basic_genome.addGene(
				new InitialConcentration(false, false, false, true, 
										 0, 0, 0, 0,
										 3, 100));
		basic_genome.addGene(
				new InitialConcentration(false, false, false, true, 
										 0, 0, 0, 0,
										 4, 100));
		basic_genome.addGene(
				new InitialConcentration(false, false, false, true, 
										 0, 0, 0, 0,
										 5, 100));
		basic_genome.addGene(new BiochemicalReaction(false, false, false, true, 
				0, 10, 0, 0,
				1, 10, 
				2, 10, 
				6, 5, 
				7, 5, 
				2));
		
		Organism test		= new Organism(basic_genome);
		
		// first execution / cycle 0
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "cycle 0");
		test.execution(null);
		test.cyclePlusPlus();
		
		Assertions.assertEquals(6, basic_genome.length());
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
		
		Assertions.assertEquals(6, basic_genome.length());
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
		
		Chromosome basic_genome = new Chromosome();
		basic_genome.addGene(
				new InitialConcentration(false, false, false, true, 
										 0, 0, 0, 0,
										 1, 100));
		basic_genome.addGene(
				new InitialConcentration(false, false, false, true, 
										 0, 0, 0, 0,
										 2, 100));
		basic_genome.addGene(
				new InitialConcentration(false, false, false, true, 
										 0, 0, 0, 0,
										 3, 100));
		basic_genome.addGene(
				new InitialConcentration(false, false, false, true, 
										 0, 0, 0, 0,
										 4, 100));
		basic_genome.addGene(
				new InitialConcentration(false, false, false, true, 
										 0, 0, 0, 0,
										 5, 100));
		basic_genome.addGene(new BiochemicalReaction(false, false, false, false, 
				0, 10, 0, 0,
				1, 10, 
				2, 10, 
				6, 5, 
				7, 5, 
				1));
		
		Organism test		= new Organism(basic_genome);
		
		test.execution(null);
		
		Assertions.assertEquals(6, basic_genome.length());
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
		
		Chromosome basic_genome = new Chromosome();
		basic_genome.addGene(
				new BrainGene(	false, false, false, false, 
								 0, 0, 0, 0,
								 100, 200, 0, 0));
		Organism test		= new Organism(basic_genome);
		
		test.execution(null);
		
		Assertions.assertEquals(1, basic_genome.length());
		Assertions.assertEquals(1, test.getGenome().size());
		Assertions.assertEquals(1, test.getGenome().get(0).length());
		
		Assertions.assertNull(test.getBrain());
		
	}
	
	@Test
	void testBrainGene02() {
		
		Chromosome basic_genome = new Chromosome();
		basic_genome.addGene(
				new BrainGene(	false, false, false, true, 
								 0, 0, 0, 0,
								 100, 200, 0, 0));
		Organism test		= new Organism(basic_genome);
		
		test.execution(null);
		
		Assertions.assertEquals(1, basic_genome.length());
		Assertions.assertEquals(1, test.getGenome().size());
		Assertions.assertEquals(1, test.getGenome().get(0).length());
		
		Assertions.assertNotNull(test.getBrain());
		
		Assertions.assertEquals(100, test.getBrain().getHeight() );
		Assertions.assertEquals(100, test.getBrain().getWidth() );
	}
	
	@Test
	void testBrainGene03() {
		
		Chromosome basic_genome = new Chromosome();
		basic_genome.addGene(
				new BrainGene(	false, false, false, true, 
								 0, 0, 0, 0,
								 100, 50, 0, 0));
		Organism test		= new Organism(basic_genome);
		
		test.execution(null);
		test.cyclePlusPlus();
		
		Assertions.assertEquals(1, basic_genome.length());
		Assertions.assertEquals(1, test.getGenome().size());
		Assertions.assertEquals(1, test.getGenome().get(0).length());
		
		Assertions.assertNotNull(test.getBrain());
		
		Assertions.assertEquals(100, test.getBrain().getHeight() );
		Assertions.assertEquals( 50, test.getBrain().getWidth() );
	}
	
	@Test
	void testBrainGene04() {
		
		Chromosome basic_genome = new Chromosome();
		basic_genome.addGene(
				new BrainGene(	false, false, false, true, 
								 0, 0, 0, 0,
								 100, 100, 0, 0));
		Organism test		= new Organism(basic_genome);
		
		test.execution(null);
		test.cyclePlusPlus();
		
		Assertions.assertEquals(1, basic_genome.length());
		Assertions.assertEquals(1, test.getGenome().size());
		Assertions.assertEquals(1, test.getGenome().get(0).length());
		
		Assertions.assertNotNull(test.getBrain());
		
		Assertions.assertEquals(100, test.getBrain().getHeight() );
		Assertions.assertEquals(100, test.getBrain().getWidth() );
	}
	
	@Test
	void testBrainGene05() {
		
		Chromosome basic_genome = new Chromosome();
		
		BrainGeneBuilder bgb	= new BrainGeneBuilder();
		BrainGene brainGene		= bgb.heigth(100).width(100).depth(1).activ(true)
									.agemin(0).agemax(0).mutation(0).build();
		
		basic_genome.addGene( brainGene );
		
		basic_genome.addGene( BrainLobeGeneBuilder.generateReceptorLobeGene() );
		
		// TODO TESTS BrainLobeGene !!
		
		Organism test		= new Organism(basic_genome);
		
		test.execution(null);
		test.cyclePlusPlus();
		
		Assertions.assertEquals(2, basic_genome.length());
		Assertions.assertEquals(1, test.getGenome().size());
		Assertions.assertEquals(2, test.getGenome().get(0).length());
		
		Assertions.assertNotNull(test.getBrain());
		
		Assertions.assertEquals(100, test.getBrain().getHeight() );
		Assertions.assertEquals(100, test.getBrain().getWidth() );
		
		// TODO TESTS BrainLobeGene !!
		
	}

}

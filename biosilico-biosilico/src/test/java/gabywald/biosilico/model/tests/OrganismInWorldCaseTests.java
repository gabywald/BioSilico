package gabywald.biosilico.model.tests;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.genetics.EmitterReceptor;
import gabywald.biosilico.genetics.InitialConcentration;
import gabywald.biosilico.genetics.StimulusDecision;
import gabywald.biosilico.genetics.builders.BrainGeneBuilder;
import gabywald.biosilico.genetics.builders.BrainLobeGeneBuilder;
import gabywald.biosilico.genetics.builders.InitialConcentrationBuilder;
import gabywald.biosilico.genetics.builders.InstinctBuilder;
import gabywald.biosilico.genetics.builders.StimulusDecisionBuilder;
import gabywald.biosilico.model.Chromosome;
import gabywald.biosilico.model.Neuron;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.World;
import gabywald.biosilico.model.WorldCase;
import gabywald.biosilico.model.chemicals.ChemicalsHelper;
import gabywald.biosilico.model.decisions.tests.TestObjectFoodEgg;
import gabywald.biosilico.model.enums.AgentType;
import gabywald.biosilico.model.enums.DecisionType;
import gabywald.biosilico.model.enums.DirectionWorld;
import gabywald.biosilico.model.enums.ObjectType;
import gabywald.biosilico.model.enums.SomeChemicals;
import gabywald.biosilico.model.enums.StateType;
import gabywald.global.data.StringUtils;

/**
 * 
 * @author Gabriel Chandesris (2020)
 * TODO review and replace "System.out.println(" with "Logger.printlnLog(LoggerLevel.LL_NONE, "
 */
class OrganismInWorldCaseTests {

	@Test
	void testStimulusDecisionReceiveEmit() {

		Chromosome basicGenome = new Chromosome();
		
		// ***** some initialisations !!
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
				SomeChemicals.DIOXYGEN.getIndex(), 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
				SomeChemicals.CARBON_DIOXYDE.getIndex(), 100));
		
		// ***** Receive O2 from environment
		basicGenome.addGene(new StimulusDecision(	false, false, false, true, 0, 999, 0, 0,
				false, false, 
				800, 0, SomeChemicals.DIOXYGEN.getIndex(), SomeChemicals.DIOXYGEN.getIndex(), 5, DecisionType.RECEIVE.getIndex()) );
		// ***** Emit CO2 to environment
		basicGenome.addGene(new StimulusDecision(	false, false, false, true, 0, 999, 0, 0,
				false, false, 
				800, 0, SomeChemicals.CARBON_DIOXYDE.getIndex(), SomeChemicals.CARBON_DIOXYDE.getIndex(), 10, DecisionType.EMIT.getIndex()) );
				
		Organism test		= new Organism(basicGenome);

		Assertions.assertEquals(  0, test.getVariables().getVariable( 1 ) );
		
		Assertions.assertEquals(4, basicGenome.length());
		Assertions.assertEquals(1, test.getGenome().size());
		Assertions.assertEquals(4, test.getGenome().get(0).length());

		Assertions.assertEquals(0, test.getVariables().getVariable( SomeChemicals.DIOXYGEN.getIndex() ) );
		Assertions.assertEquals(0, test.getVariables().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex() ) );
		Assertions.assertEquals(0, test.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		// ***** test with a World and WorldCase
		
		World w			= new World(1, 1);
		WorldCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getVariables().getVariable(k) );
		});
		
		// ***** set some O2 and CO2 in current WorldCase's instance
		wc.getVariables().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 100);
		wc.getVariables().setVariable(SomeChemicals.CARBON_DIOXYDE.getIndex(), 100);
		
		Assertions.assertEquals(100, wc.getVariables().getVariable( SomeChemicals.DIOXYGEN.getIndex() ) );
		Assertions.assertEquals(100, wc.getVariables().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex() ) );
		
		// ***** one execution in this context
		test.execution( wc );
		test.cyclePlusPlus(); // Aging organism
		
		// Changes in Organism instance
		Assertions.assertEquals(105, test.getVariables().getVariable( SomeChemicals.DIOXYGEN.getIndex() ) );
		Assertions.assertEquals( 90, test.getVariables().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex() ) );
		Assertions.assertEquals(  1, test.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		// changes in WorldCase instance
		Assertions.assertEquals( 95, wc.getVariables().getVariable( SomeChemicals.DIOXYGEN.getIndex() ) );
		Assertions.assertEquals(110, wc.getVariables().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex() ) );
		
		// ***** one MORE execution in this context
		test.execution( wc );
		test.cyclePlusPlus(); // Aging organism
		
		// Changes in Organism instance
		Assertions.assertEquals(110, test.getVariables().getVariable( SomeChemicals.DIOXYGEN.getIndex() ) );
		Assertions.assertEquals( 80, test.getVariables().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex() ) );
		Assertions.assertEquals(  2, test.getVariables().getVariable( StateType.AGING.getIndex() ) );
		// changes in WorldCase instance
		Assertions.assertEquals( 90, wc.getVariables().getVariable( SomeChemicals.DIOXYGEN.getIndex() ) );
		Assertions.assertEquals(120, wc.getVariables().getVariable( SomeChemicals.CARBON_DIOXYDE.getIndex() ) );

	}
	
	@Test
	void testEmitterReceptorExternalAsReceptorInBrain() {

		Chromosome basicGenome		= new Chromosome();
		
		BrainGeneBuilder bgb		= new BrainGeneBuilder();
		BrainLobeGeneBuilder blgb	= new BrainLobeGeneBuilder();

		basicGenome.addGene( bgb.heigth(10).width(10).depth(1)
				.agemin(0).agemax(0).mutation(25).activ(true).build() );
		
		basicGenome.addGene( blgb.heigth(1).width(1)
				.rest(0).threshold(10).desc(1).dmin(0).dmax(0)
				.prox(0).repr(false).repy(0).wta(false)
				.posx(1).posy(1).replace(false)
				.agemin(0).agemax(0).mutation(25).activ(true).build() );
		
		// ***** Detect 'Solar Energy' (> 10) from environment, and put signal 100 at (1, 1) in Brain. 
		basicGenome.addGene(new EmitterReceptor( false, false, false, true, 0, 999, 0, 0,
					SomeChemicals.ENERGY_SOLAR.getIndex(), 10, 100, 1, 1, true, false ) );
				
		Organism test		= new Organism(basicGenome);

		Assertions.assertEquals(  0, test.getVariables().getVariable( 1 ) );
		
		Assertions.assertEquals(3, basicGenome.length());
		Assertions.assertEquals(1, test.getGenome().size());
		Assertions.assertEquals(3, test.getGenome().get(0).length());

		// ***** test with a World and WorldCase
		
		World w			= new World(1, 1);
		WorldCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getVariables().getVariable(k) );
		});
		
		// ***** set some 'solar energy' in current WorldCase's instance
		wc.getVariables().setVariable(SomeChemicals.ENERGY_SOLAR.getIndex(),  50);
		
		Assertions.assertEquals( 50, wc.getVariables().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex() ) );
		
		Assertions.assertEquals(  0, test.getVariables().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex() ) );
		Assertions.assertEquals(  0, test.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		// ***** one execution in this context
		test.execution( wc );
		test.cyclePlusPlus(); // Aging organism
		
		// Changes in Organism instance
		Assertions.assertEquals(  0, test.getVariables().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex() ) );
		Assertions.assertEquals(  1, test.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		Assertions.assertNotNull( test.getBrain() );
		Assertions.assertNotNull( test.getBrain().getNeuronAt(1, 1) );
		Assertions.assertTrue( test.getBrain().getNeuronAt(1, 1).isActivated() );
		Assertions.assertEquals( 99, test.getBrain().getNeuronAt(1, 1).getActivity() );
		
		// No changes in WorldCase instance
		Assertions.assertEquals( 50, wc.getVariables().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex() ) );

		// ***** one MORE execution in this context
		test.execution( wc );
		test.cyclePlusPlus(); // Aging organism
		
		// Changes in Organism instance
		Assertions.assertEquals(  0, test.getVariables().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex() ) );
		Assertions.assertEquals(  2, test.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		Assertions.assertNotNull( test.getBrain() );
		Assertions.assertNotNull( test.getBrain().getNeuronAt(1, 1) );
		Assertions.assertTrue( test.getBrain().getNeuronAt(1, 1).isActivated() );
		Assertions.assertEquals(198, test.getBrain().getNeuronAt(1, 1).getActivity() );
		
		// No changes in WorldCase instance
		Assertions.assertEquals( 50, wc.getVariables().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex() ) );

	}
	
	@Test
	void testEmitterReceptorExternalAsEmitterInWorldCase() {

		Chromosome basicGenome		= new Chromosome();
		
		BrainGeneBuilder bgb		= new BrainGeneBuilder();
		BrainLobeGeneBuilder blgb	= new BrainLobeGeneBuilder();

		basicGenome.addGene( bgb.heigth(10).width(10).depth(1)
				.agemin(0).agemax(0).mutation(25).activ(true).build() );
		
		basicGenome.addGene( blgb.heigth(1).width(1)
				.rest(0).threshold(10).desc(1).dmin(0).dmax(0)
				.prox(0).repr(false).repy(0).wta(false)
				.posx(9).posy(5).replace(false)
				.agemin(0).agemax(0).mutation(25).activ(true).build() );
		
		// ***** Emit 'PHEROMONE_00' (25) if activity from itself at (9, 5) > 10, and put 25 of pheromone_00 in current location.  
		basicGenome.addGene(new EmitterReceptor( false, false, false, true, 0, 999, 0, 0,
					SomeChemicals.PHEROMONE_00.getIndex(), 10, 25, 9, 5, false, false ) );
				
		Organism test		= new Organism(basicGenome);

		Assertions.assertEquals(  0, test.getVariables().getVariable( 1 ) );
		
		Assertions.assertEquals(3, basicGenome.length());
		Assertions.assertEquals(1, test.getGenome().size());
		Assertions.assertEquals(3, test.getGenome().get(0).length());

		// ***** test with a World and WorldCase
		
		World w			= new World(1, 1);
		WorldCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getVariables().getVariable(k) );
		});
		
		// ***** set some 'solar energy' in current WorldCase's instance
		wc.getVariables().setVariable(SomeChemicals.ENERGY_SOLAR.getIndex(),  50);
		
		Assertions.assertEquals(  0, wc.getVariables().getVariable( SomeChemicals.PHEROMONE_00.getIndex() ) );
		
		Assertions.assertEquals(  0, test.getVariables().getVariable( SomeChemicals.PHEROMONE_00.getIndex() ) );
		Assertions.assertEquals(  0, test.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		// ***** one execution in this context
		test.execution( wc );
		test.cyclePlusPlus(); // Aging organism
		
		// Changes in Organism instance
		Assertions.assertEquals(  0, test.getVariables().getVariable( SomeChemicals.PHEROMONE_00.getIndex() ) );
		Assertions.assertEquals(  1, test.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		Assertions.assertNotNull( test.getBrain() );
		Assertions.assertNotNull( test.getBrain().getNeuronAt(9, 5) );
		Assertions.assertFalse( test.getBrain().getNeuronAt(9, 5).isActivated() );
		Assertions.assertEquals( 0, test.getBrain().getNeuronAt(9, 5).getActivity() );
		
		// Changes in WorldCase instance
		Assertions.assertEquals(  0, wc.getVariables().getVariable( SomeChemicals.PHEROMONE_00.getIndex() ) );

		test.getBrain().getNeuronAt(9, 5).addActivity( 100 );
		
		// ***** one MORE execution in this context
		test.execution( wc );
		test.cyclePlusPlus(); // Aging organism
		
		// Changes in Organism instance
		Assertions.assertEquals(  0, test.getVariables().getVariable( SomeChemicals.PHEROMONE_00.getIndex() ) );
		Assertions.assertEquals(  2, test.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		Assertions.assertNotNull( test.getBrain() );
		Assertions.assertNotNull( test.getBrain().getNeuronAt(9, 5) );
		Assertions.assertTrue( test.getBrain().getNeuronAt(9, 5).isActivated() );
		Assertions.assertEquals( 99, test.getBrain().getNeuronAt(9, 5).getActivity() );
		
		// Changes in WorldCase instance
		Assertions.assertEquals( 25, wc.getVariables().getVariable( SomeChemicals.PHEROMONE_00.getIndex() ) );

		// ***** one MORE MORE execution in this context
		test.execution( wc );
		test.cyclePlusPlus(); // Aging organism
		
		// Changes in Organism instance
		Assertions.assertEquals(  0, test.getVariables().getVariable( SomeChemicals.PHEROMONE_00.getIndex() ) );
		Assertions.assertEquals(  3, test.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		Assertions.assertNotNull( test.getBrain() );
		Assertions.assertNotNull( test.getBrain().getNeuronAt(9, 5) );
		Assertions.assertTrue( test.getBrain().getNeuronAt(9, 5).isActivated() );
		Assertions.assertEquals( 98, test.getBrain().getNeuronAt(9, 5).getActivity() );
		
		// Changes in WorldCase instance
		Assertions.assertEquals( 50, wc.getVariables().getVariable( SomeChemicals.PHEROMONE_00.getIndex() ) );

	}
	
	@Test
	void testEmitterReceptorInternalAsReceptorInBrain() {

		Chromosome basicGenome		= new Chromosome();
		
		BrainGeneBuilder bgb		= new BrainGeneBuilder();
		BrainLobeGeneBuilder blgb	= new BrainLobeGeneBuilder();

		basicGenome.addGene( bgb.heigth(10).width(10).depth(1)
				.agemin(0).agemax(0).mutation(25).activ(true).build() );
		
		basicGenome.addGene( blgb.heigth(1).width(1)
				.rest(0).threshold(10).desc(1).dmin(0).dmax(0)
				.prox(0).repr(false).repy(0).wta(false)
				.posx(1).posy(1).replace(false)
				.agemin(0).agemax(0).mutation(25).activ(true).build() );
		
		// ***** Detect 'Heat Energy' (> 10) from internal variables, and put signal 100 at (1, 1) in Brain. 
		basicGenome.addGene(new EmitterReceptor( false, false, false, true, 0, 999, 0, 0,
					SomeChemicals.ENERGY_HEAT.getIndex(), 10, 100, 1, 1, true, true ) );
				
		Organism test		= new Organism(basicGenome);

		Assertions.assertEquals(  0, test.getVariables().getVariable( 1 ) );
		
		Assertions.assertEquals(3, basicGenome.length());
		Assertions.assertEquals(1, test.getGenome().size());
		Assertions.assertEquals(3, test.getGenome().get(0).length());

		// ***** test with a World and WorldCase
		
		World w			= new World(1, 1);
		WorldCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getVariables().getVariable(k) );
		});
		
		// ***** set some 'solar energy' in current WorldCase's instance
		wc.getVariables().setVariable(SomeChemicals.ENERGY_HEAT.getIndex(),  50);
		
		Assertions.assertEquals( 50, wc.getVariables().getVariable( SomeChemicals.ENERGY_HEAT.getIndex() ) );
		
		Assertions.assertEquals(  0, test.getVariables().getVariable( SomeChemicals.ENERGY_HEAT.getIndex() ) );
		Assertions.assertEquals(  0, test.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		// ***** one execution in this context
		test.execution( wc );
		test.cyclePlusPlus(); // Aging organism
		
		// Changes in Organism instance
		Assertions.assertEquals(  0, test.getVariables().getVariable( SomeChemicals.ENERGY_HEAT.getIndex() ) );
		Assertions.assertEquals(  1, test.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		Assertions.assertNotNull( test.getBrain() );
		Assertions.assertNotNull( test.getBrain().getNeuronAt(1, 1) );
		Assertions.assertFalse( test.getBrain().getNeuronAt(1, 1).isActivated() );
		Assertions.assertEquals(  0, test.getBrain().getNeuronAt(1, 1).getActivity() );
		Assertions.assertEquals(  0, test.getVariables().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()) );
		
		// No changes in WorldCase instance
		Assertions.assertEquals( 50, wc.getVariables().getVariable( SomeChemicals.ENERGY_HEAT.getIndex() ) );

		test.getVariables().setVariable(SomeChemicals.ENERGY_HEAT.getIndex(), 100);
		
		// ***** one MORE execution in this context
		test.execution( wc );
		test.cyclePlusPlus(); // Aging organism
		
		// Changes in Organism instance
		Assertions.assertEquals(100, test.getVariables().getVariable( SomeChemicals.ENERGY_HEAT.getIndex() ) );
		Assertions.assertEquals(  2, test.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		Assertions.assertNotNull( test.getBrain() );
		Assertions.assertNotNull( test.getBrain().getNeuronAt(1, 1) );
		Assertions.assertTrue( test.getBrain().getNeuronAt(1, 1).isActivated() );
		Assertions.assertEquals( 99, test.getBrain().getNeuronAt(1, 1).getActivity() );
		Assertions.assertEquals(100, test.getVariables().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()) );
		
		// No changes in WorldCase instance
		Assertions.assertEquals( 50, wc.getVariables().getVariable( SomeChemicals.ENERGY_HEAT.getIndex() ) );

		
		// ***** one MORE MORE execution in this context
		test.execution( wc );
		test.cyclePlusPlus(); // Aging organism
		
		// Changes in Organism instance
		Assertions.assertEquals(100, test.getVariables().getVariable( SomeChemicals.ENERGY_HEAT.getIndex() ) );
		Assertions.assertEquals(  3, test.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		Assertions.assertNotNull( test.getBrain() );
		Assertions.assertNotNull( test.getBrain().getNeuronAt(1, 1) );
		Assertions.assertTrue( test.getBrain().getNeuronAt(1, 1).isActivated() );
		Assertions.assertEquals(198, test.getBrain().getNeuronAt(1, 1).getActivity() );
		Assertions.assertEquals(100, test.getVariables().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()) );
		
		// No changes in WorldCase instance
		Assertions.assertEquals( 50, wc.getVariables().getVariable( SomeChemicals.ENERGY_HEAT.getIndex() ) );

	}
	
	@Test
	void testEmitterReceptorInternalAsEmitterInChemical() {

		Chromosome basicGenome		= new Chromosome();
		
		BrainGeneBuilder bgb		= new BrainGeneBuilder();
		BrainLobeGeneBuilder blgb	= new BrainLobeGeneBuilder();

		basicGenome.addGene( bgb.heigth(10).width(10).depth(1)
				.agemin(0).agemax(0).mutation(25).activ(true).build() );
		
		basicGenome.addGene( blgb.heigth(1).width(1)
				.rest(0).threshold(10).desc(1).dmin(0).dmax(0)
				.prox(0).repr(false).repy(0).wta(false)
				.posx(9).posy(5).replace(false)
				.agemin(0).agemax(0).mutation(25).activ(true).build() );
		
		// ***** Emit 'PHEROMONE_00' (25) if activity from itself at (9, 5) > 10, and put 25 of pheromone_00 in current location.  
		basicGenome.addGene(new EmitterReceptor( false, false, false, true, 0, 999, 0, 0,
					SomeChemicals.PHEROMONE_00.getIndex(), 10, 25, 9, 5, false, true ) );
				
		Organism test		= new Organism(basicGenome);

		Assertions.assertEquals(  0, test.getVariables().getVariable( 1 ) );
		
		Assertions.assertEquals(3, basicGenome.length());
		Assertions.assertEquals(1, test.getGenome().size());
		Assertions.assertEquals(3, test.getGenome().get(0).length());

		// ***** test with a World and WorldCase
		
		World w			= new World(1, 1);
		WorldCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getVariables().getVariable(k) );
		});
		
		// ***** set some 'solar energy' in current WorldCase's instance
		wc.getVariables().setVariable(SomeChemicals.ENERGY_SOLAR.getIndex(),  50);
		
		Assertions.assertEquals(  0, wc.getVariables().getVariable( SomeChemicals.PHEROMONE_00.getIndex() ) );
		
		Assertions.assertEquals(  0, test.getVariables().getVariable( SomeChemicals.PHEROMONE_00.getIndex() ) );
		Assertions.assertEquals(  0, test.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		// ***** one execution in this context
		test.execution( wc );
		test.cyclePlusPlus(); // Aging organism
		
		// Changes in Organism instance
		Assertions.assertEquals(  0, test.getVariables().getVariable( SomeChemicals.PHEROMONE_00.getIndex() ) );
		Assertions.assertEquals(  1, test.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		Assertions.assertNotNull( test.getBrain() );
		Assertions.assertNotNull( test.getBrain().getNeuronAt(9, 5) );
		Assertions.assertFalse( test.getBrain().getNeuronAt(9, 5).isActivated() );
		Assertions.assertEquals( 0, test.getBrain().getNeuronAt(9, 5).getActivity() );
		
		// Changes in WorldCase instance
		Assertions.assertEquals(  0, wc.getVariables().getVariable( SomeChemicals.PHEROMONE_00.getIndex() ) );

		test.getBrain().getNeuronAt(9, 5).addActivity( 100 );
		
		// ***** one MORE execution in this context
		test.execution( wc );
		test.cyclePlusPlus(); // Aging organism
		
		// Changes in Organism instance
		Assertions.assertEquals( 25, test.getVariables().getVariable( SomeChemicals.PHEROMONE_00.getIndex() ) );
		Assertions.assertEquals(  2, test.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		Assertions.assertNotNull( test.getBrain() );
		Assertions.assertNotNull( test.getBrain().getNeuronAt(9, 5) );
		Assertions.assertTrue( test.getBrain().getNeuronAt(9, 5).isActivated() );
		Assertions.assertEquals( 99, test.getBrain().getNeuronAt(9, 5).getActivity() );
		
		// No changes in WorldCase instance
		Assertions.assertEquals(  0, wc.getVariables().getVariable( SomeChemicals.PHEROMONE_00.getIndex() ) );

		// ***** one MORE MORE execution in this context
		test.execution( wc );
		test.cyclePlusPlus(); // Aging organism
		
		// Changes in Organism instance
		Assertions.assertEquals( 50, test.getVariables().getVariable( SomeChemicals.PHEROMONE_00.getIndex() ) );
		Assertions.assertEquals(  3, test.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		Assertions.assertNotNull( test.getBrain() );
		Assertions.assertNotNull( test.getBrain().getNeuronAt(9, 5) );
		Assertions.assertTrue( test.getBrain().getNeuronAt(9, 5).isActivated() );
		Assertions.assertEquals( 98, test.getBrain().getNeuronAt(9, 5).getActivity() );
		
		// No changes in WorldCase instance
		Assertions.assertEquals(  0, wc.getVariables().getVariable( SomeChemicals.PHEROMONE_00.getIndex() ) );

	}
	
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
		
		World w			= new World(1, 1);
		WorldCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  1, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		Assertions.assertNotNull( testOrga.getBrain() );
		Neuron testNeuronAt9dot0 = testOrga.getBrain().getNeuronAt(9, 0);
		Assertions.assertNotNull( testNeuronAt9dot0 );
		Neuron testNeuronAt0dot0 = testOrga.getBrain().getNeuronAt(0, 0);
		Assertions.assertNotNull( testNeuronAt0dot0 );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
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
		
		World w			= new World(1, 1);
		WorldCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  1, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		Assertions.assertNotNull( testOrga.getBrain() );
		Neuron testNeuronAt9dot0 = testOrga.getBrain().getNeuronAt(9, 0);
		Assertions.assertNotNull( testNeuronAt9dot0 );
		Neuron testNeuronAt0dot0 = testOrga.getBrain().getNeuronAt(0, 0);
		Assertions.assertNotNull( testNeuronAt0dot0 );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
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
		
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  2, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		Assertions.assertEquals(  0, testNeuronAt0dot0.getActivity() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
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
		
		World w			= new World(1, 1);
		WorldCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  1, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		Assertions.assertNotNull( testOrga.getBrain() );
		Neuron testNeuronAt9dot0 = testOrga.getBrain().getNeuronAt(9, 0);
		Assertions.assertNotNull( testNeuronAt9dot0 );
		Neuron testNeuronAt0dot0 = testOrga.getBrain().getNeuronAt(0, 0);
		Assertions.assertNotNull( testNeuronAt0dot0 );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
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
		
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  2, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
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
		
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  3, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
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
		
		World w			= new World(1, 1);
		WorldCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  1, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		Assertions.assertNotNull( testOrga.getBrain() );
		Neuron testNeuronAt9dot0 = testOrga.getBrain().getNeuronAt(9, 0);
		Assertions.assertNotNull( testNeuronAt9dot0 );
		Neuron testNeuronAt0dot0 = testOrga.getBrain().getNeuronAt(0, 0);
		Assertions.assertNotNull( testNeuronAt0dot0 );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
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
		
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  2, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
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
		
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  3, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
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
		
		World w			= new World(1, 1);
		WorldCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  1, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		Assertions.assertNotNull( testOrga.getBrain() );
		Neuron testNeuronAt9dot0 = testOrga.getBrain().getNeuronAt(9, 0);
		Assertions.assertNotNull( testNeuronAt9dot0 );
		Neuron testNeuronAt0dot0 = testOrga.getBrain().getNeuronAt(0, 0);
		Assertions.assertNotNull( testNeuronAt0dot0 );
		Neuron testNeuronAt0dot1 = testOrga.getBrain().getNeuronAt(0, 1);
		Assertions.assertNotNull( testNeuronAt0dot1 );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
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
		
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  2, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
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
		
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  3, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
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
		
		World w			= new World(1, 1);
		WorldCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  1, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		Assertions.assertNotNull( testOrga.getBrain() );
		Neuron testNeuronAt9dot0 = testOrga.getBrain().getNeuronAt(9, 0);
		Assertions.assertNotNull( testNeuronAt9dot0 );
		Neuron testNeuronAt0dot0 = testOrga.getBrain().getNeuronAt(0, 0);
		Assertions.assertNotNull( testNeuronAt0dot0 );
		Neuron testNeuronAt0dot1 = testOrga.getBrain().getNeuronAt(0, 1);
		Assertions.assertNotNull( testNeuronAt0dot1 );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
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
		
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  2, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
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
		
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  3, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
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
	
	@Test
	void testDecisionThink01() {

		Chromosome basicGenome			= new Chromosome();
		basicGenome.setName( "testDecision01 chromosome" );
		
		BrainGeneBuilder bgb			= new BrainGeneBuilder();
		BrainLobeGeneBuilder blgb		= new BrainLobeGeneBuilder();
		InstinctBuilder igb				= new InstinctBuilder();
		InitialConcentrationBuilder icb	= new InitialConcentrationBuilder();
		StimulusDecisionBuilder sdgb	= new StimulusDecisionBuilder();

		// ***** brain
		basicGenome.addGene( bgb.heigth(10).width(10).depth(1)
				.name( "Brain Gene" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		// ***** a lobe on first line
		basicGenome.addGene( blgb.heigth(1).width(10)
				.rest(0).threshold(10).desc(1).dmin(0).dmax(0)
				.prox(0).repr(false).repy(0).wta(false)
				.posx(0).posy(0).replace(false)
				.name( "First Line Lobe" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		// ***** a lobe on last line
		basicGenome.addGene( blgb.heigth(1).width(10)
				.rest(0).threshold(10).desc(1).dmin(0).dmax(0)
				.prox(0).repr(false).repy(0).wta( true )
				.posx(9).posy(0).replace(false)
				.name( "Last Line Lobe" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		
		basicGenome.addGene( icb	.varia( SomeChemicals.GLUCOSE.getIndex() ).value( 25 )
				.name( "InitConc GLUCOSE 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		
		// ***** instinct connection from first line of neurons to last line of neurons !
		int iPosX = 0, iPosY = 0, oPosX = 9;
		IntStream.range(0, 10).forEach( i -> {
			basicGenome.addGene( igb
					.inputPosX( iPosX ).inputPosY( iPosY ).outputPosX( oPosX ).outputPosY( i )
					.weight( 42 ).variable( SomeChemicals.GLUCOSE.getIndex() )
					.threshold( 5 ).check( false )
						.mutate( true ).duplicate( true ).delete( true ).activ( true )
						.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
						.name( "Instinct " + i  + " (" + iPosX + ", " + iPosY + ", " + oPosX + ", " + i + ")")
						.build() );
		});
		
		// ***** StimulusDecision : not perception ; act on object ?! ; choice of decision on 'scrip'
		basicGenome.addGene( sdgb	.perception( false ).object( true )
				.indicator( SomeChemicals.GLUCOSE.getIndex() ).threshold( 1 )
				.attribute( DirectionWorld.CURRENT.getIndex() )
				.varia( SomeChemicals.PHEROMONE_01.getIndex() ).value( 42 )
				.script( DecisionType.THINK.getIndex() )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.name( "StimulusDecision test " )
			.build() );
		
		Organism testOrga		= new Organism(basicGenome);

		Assertions.assertEquals(15, basicGenome.length());
		Assertions.assertEquals( 1, testOrga.getGenome().size());
		Assertions.assertEquals(15, testOrga.getGenome().get(0).length());
		
		// ***** test with a World and WorldCase
		
		World w			= new World(1, 1);
		WorldCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( DecisionType.THINK.getIndex() ) );
		
		// ***** Indicates THINK
		testOrga.getVariables().setVariable(DecisionType.THINK.getIndex(), 5);
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  1, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals( testOrga.getUniqueID() + "::think about [Glucose]\t", testOrga.getState() );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  4, testOrga.getVariables().getVariable( DecisionType.THINK.getIndex() ) );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  2, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  testOrga.getUniqueID() + "::think about [Glucose]\t", testOrga.getState() );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  3, testOrga.getVariables().getVariable( DecisionType.THINK.getIndex() ) );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
	}
	
	@Test
	void testDecisionThink02() {

		Chromosome basicGenome			= new Chromosome();
		basicGenome.setName( "testDecision02 chromosome" );
		
		BrainGeneBuilder bgb			= new BrainGeneBuilder();
		BrainLobeGeneBuilder blgb		= new BrainLobeGeneBuilder();
		InstinctBuilder igb				= new InstinctBuilder();
		InitialConcentrationBuilder icb	= new InitialConcentrationBuilder();
		StimulusDecisionBuilder sdgb	= new StimulusDecisionBuilder();

		// ***** brain
		basicGenome.addGene( bgb.heigth(10).width(10).depth(1)
				.name( "Brain Gene" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		// ***** a lobe on first line
		basicGenome.addGene( blgb.heigth(1).width(10)
				.rest(0).threshold(10).desc(1).dmin(0).dmax(0)
				.prox(0).repr(false).repy(0).wta(false)
				.posx(0).posy(0).replace(false)
				.name( "First Line Lobe" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		// ***** a lobe on last line
		basicGenome.addGene( blgb.heigth(1).width(10)
				.rest(0).threshold(10).desc(1).dmin(0).dmax(0)
				.prox(0).repr(false).repy(0).wta( true )
				.posx(9).posy(0).replace(false)
				.name( "Last Line Lobe" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		
		basicGenome.addGene(
				icb	.varia( SomeChemicals.GLUCOSE.getIndex() ).value( 25 )
				.name( "InitConc GLUCOSE 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		
		// ***** instinct connection from first line of neurons to last line of neurons !
		int iPosX = 0, iPosY = 0, oPosX = 9;
		IntStream.range(0, 10).forEach( i -> {
			basicGenome.addGene( igb
					.inputPosX( iPosX ).inputPosY( iPosY ).outputPosX( oPosX ).outputPosY( i )
					.weight( 42 ).variable( SomeChemicals.GLUCOSE.getIndex() )
					.threshold( 5 ).check( false )
						.mutate( true ).duplicate( true ).delete( true ).activ( true )
						.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
						.name( "Instinct " + i  + " (" + iPosX + ", " + iPosY + ", " + oPosX + ", " + i + ")")
						.build() );
		});
		
		// ***** StimulusDecision : perception ; act on object ?! ; Detection of FOOD on local !!
		basicGenome.addGene( sdgb	.perception( true ).object( true )
				.indicator( ObjectType.FOOD.getIndex() ).threshold( 0 ) // Detection of local FOOD (at least 1) on local !!
				.attribute( DirectionWorld.CURRENT.getIndex() )
				.varia( SomeChemicals.PHEROMONE_01.getIndex() ).value( 42 )
				.script( DecisionType.THINK.getIndex() )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.name( "StimulusDecision test " )
			.build() );
		
		Organism testOrga		= new Organism(basicGenome);

		Assertions.assertEquals(15, basicGenome.length());
		Assertions.assertEquals( 1, testOrga.getGenome().size());
		Assertions.assertEquals(15, testOrga.getGenome().get(0).length());
		
		// ***** test with a World and WorldCase
		
		World w			= new World(1, 1);
		WorldCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  1, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals( "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  2, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		wc.addAgent( new TestObjectFoodEgg() );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  3, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals( 42, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
	}
	
	@Test
	void testDecisionHASfood() {

		Chromosome basicGenome			= new Chromosome();
		basicGenome.setName( "testDecisionHASfood chromosome" );
		
		BrainGeneBuilder bgb			= new BrainGeneBuilder();
		BrainLobeGeneBuilder blgb		= new BrainLobeGeneBuilder();
		InstinctBuilder igb				= new InstinctBuilder();
		InitialConcentrationBuilder icb	= new InitialConcentrationBuilder();
		StimulusDecisionBuilder sdgb	= new StimulusDecisionBuilder();

		// ***** brain
		basicGenome.addGene( bgb.heigth(10).width(10).depth(1)
				.name( "Brain Gene" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		// ***** a lobe on first line
		basicGenome.addGene( blgb.heigth(1).width(10)
				.rest(0).threshold(10).desc(1).dmin(0).dmax(0)
				.prox(0).repr(false).repy(0).wta(false)
				.posx(0).posy(0).replace(false)
				.name( "First Line Lobe" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		// ***** a lobe on last line
		basicGenome.addGene( blgb.heigth(1).width(10)
				.rest(0).threshold(10).desc(1).dmin(0).dmax(0)
				.prox(0).repr(false).repy(0).wta( true )
				.posx(9).posy(0).replace(false)
				.name( "Last Line Lobe" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		
		basicGenome.addGene(
				icb	.varia( SomeChemicals.GLUCOSE.getIndex() ).value( 25 )
				.name( "InitConc GLUCOSE 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		
		// ***** instinct connection from first line of neurons to last line of neurons !
		int iPosX = 0, iPosY = 0, oPosX = 9;
		IntStream.range(0, 10).forEach( i -> {
			basicGenome.addGene( igb
					.inputPosX( iPosX ).inputPosY( iPosY ).outputPosX( oPosX ).outputPosY( i )
					.weight( 42 ).variable( SomeChemicals.GLUCOSE.getIndex() )
					.threshold( 5 ).check( false )
						.mutate( true ).duplicate( true ).delete( true ).activ( true )
						.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
						.name( "Instinct " + i  + " (" + iPosX + ", " + iPosY + ", " + oPosX + ", " + i + ")")
						.build() );
		});
		
		// ***** StimulusDecision : NOT perception ; act on object ?! ;
		basicGenome.addGene( sdgb	.perception( false ).object( true )
				.indicator( ObjectType.FOOD.getIndex() ).threshold( 0 ) // Detection of local FOOD (at least 1) on local !!
				.attribute( AgentType.BIOSILICO_VIRIDITA.getIndex() )
				.varia( SomeChemicals.PHEROMONE_09.getIndex() ).value( 42 )
				.script( DecisionType.HAS.getIndex() )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.name( "StimulusDecision test " )
			.build() );
		
		Organism testOrga		= new Organism(basicGenome);

		Assertions.assertEquals(15, basicGenome.length());
		Assertions.assertEquals( 1, testOrga.getGenome().size());
		Assertions.assertEquals(15, testOrga.getGenome().get(0).length());
		
		// ***** test with a World and WorldCase
		
		World w			= new World(1, 1);
		WorldCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals(  0, wc.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  0, testOrga.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( DecisionType.HAS.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  1, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
		Assertions.assertEquals( "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals(  0, wc.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  0, testOrga.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( DecisionType.HAS.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  2, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
		Assertions.assertEquals(  "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		wc.addAgent( new TestObjectFoodEgg() );
		Assertions.assertEquals(  1, wc.hasObjectType(ObjectType.FOOD) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism

		Assertions.assertEquals(  1, wc.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  0, testOrga.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( DecisionType.HAS.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  3, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
		Assertions.assertEquals(  "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		testOrga.addAgent( new TestObjectFoodEgg() );
		
		// ***** Indicates HAS
		testOrga.getVariables().setVariable(DecisionType.HAS.getIndex(), 5);
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals(  1, wc.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  1, testOrga.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  4, testOrga.getVariables().getVariable( DecisionType.HAS.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  4, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals( 42, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
		Assertions.assertEquals(  "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
	}
	
	@Test
	void testDecisionGETfood() {

		Chromosome basicGenome			= new Chromosome();
		basicGenome.setName( "testDecisionGETfood chromosome" );
		
		BrainGeneBuilder bgb			= new BrainGeneBuilder();
		BrainLobeGeneBuilder blgb		= new BrainLobeGeneBuilder();
		InstinctBuilder igb				= new InstinctBuilder();
		InitialConcentrationBuilder icb	= new InitialConcentrationBuilder();
		StimulusDecisionBuilder sdgb	= new StimulusDecisionBuilder();

		// ***** brain
		basicGenome.addGene( bgb.heigth(10).width(10).depth(1)
				.name( "Brain Gene" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		// ***** a lobe on first line
		basicGenome.addGene( blgb.heigth(1).width(10)
				.rest(0).threshold(10).desc(1).dmin(0).dmax(0)
				.prox(0).repr(false).repy(0).wta(false)
				.posx(0).posy(0).replace(false)
				.name( "First Line Lobe" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		// ***** a lobe on last line
		basicGenome.addGene( blgb.heigth(1).width(10)
				.rest(0).threshold(10).desc(1).dmin(0).dmax(0)
				.prox(0).repr(false).repy(0).wta( true )
				.posx(9).posy(0).replace(false)
				.name( "Last Line Lobe" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		
		basicGenome.addGene(
				icb	.varia( SomeChemicals.GLUCOSE.getIndex() ).value( 25 )
				.name( "InitConc GLUCOSE 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		
		// ***** instinct connection from first line of neurons to last line of neurons !
		int iPosX = 0, iPosY = 0, oPosX = 9;
		IntStream.range(0, 10).forEach( i -> {
			basicGenome.addGene( igb
					.inputPosX( iPosX ).inputPosY( iPosY ).outputPosX( oPosX ).outputPosY( i )
					.weight( 42 ).variable( SomeChemicals.GLUCOSE.getIndex() )
					.threshold( 5 ).check( false )
						.mutate( true ).duplicate( true ).delete( true ).activ( true )
						.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
						.name( "Instinct " + i  + " (" + iPosX + ", " + iPosY + ", " + oPosX + ", " + i + ")")
						.build() );
		});
		
		// ***** StimulusDecision : NOT perception ; act on object ?! ; 
		basicGenome.addGene( sdgb	.perception( false ).object( true )
				.indicator( ObjectType.FOOD.getIndex() ).threshold( 0 ) // Detection of local FOOD (at least 1) on local !!
				.attribute( AgentType.BIOSILICO_VIRIDITA.getIndex() )
				.varia( SomeChemicals.PHEROMONE_09.getIndex() ).value( 42 )
				.script( DecisionType.GET.getIndex() )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.name( "StimulusDecision test " )
			.build() );
		
		Organism testOrga		= new Organism(basicGenome);

		Assertions.assertEquals(15, basicGenome.length());
		Assertions.assertEquals( 1, testOrga.getGenome().size());
		Assertions.assertEquals(15, testOrga.getGenome().get(0).length());
		
		// ***** test with a World and WorldCase
		
		World w			= new World(1, 1);
		WorldCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals(  0, testOrga.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( DecisionType.GET.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  1, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
		Assertions.assertEquals( "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals(  0, testOrga.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( DecisionType.GET.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  2, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
		Assertions.assertEquals(  "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		wc.addAgent( new TestObjectFoodEgg() );
		Assertions.assertEquals(  1, wc.hasObjectType(ObjectType.FOOD) );
		
		// ***** Indicates GET
		testOrga.getVariables().setVariable(DecisionType.GET.getIndex(), 5);
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism

		Assertions.assertEquals(  0, wc.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  1, testOrga.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  4, testOrga.getVariables().getVariable( DecisionType.GET.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  3, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
		Assertions.assertEquals(  "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		testOrga.addAgent( new TestObjectFoodEgg() );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals(  0, wc.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  2, testOrga.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  3, testOrga.getVariables().getVariable( DecisionType.GET.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  4, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
		Assertions.assertEquals(  "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals(  0, wc.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  2, testOrga.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  2, testOrga.getVariables().getVariable( DecisionType.GET.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  5, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
		Assertions.assertEquals(  "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
	}
	
	@Test
	void testDecisionDROPfood() {

		Chromosome basicGenome			= new Chromosome();
		basicGenome.setName( "testDecisionDROPfood chromosome" );
		
		BrainGeneBuilder bgb			= new BrainGeneBuilder();
		BrainLobeGeneBuilder blgb		= new BrainLobeGeneBuilder();
		InstinctBuilder igb				= new InstinctBuilder();
		InitialConcentrationBuilder icb	= new InitialConcentrationBuilder();
		StimulusDecisionBuilder sdgb	= new StimulusDecisionBuilder();

		// ***** brain
		basicGenome.addGene( bgb.heigth(10).width(10).depth(1)
				.name( "Brain Gene" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		// ***** a lobe on first line
		basicGenome.addGene( blgb.heigth(1).width(10)
				.rest(0).threshold(10).desc(1).dmin(0).dmax(0)
				.prox(0).repr(false).repy(0).wta(false)
				.posx(0).posy(0).replace(false)
				.name( "First Line Lobe" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		// ***** a lobe on last line
		basicGenome.addGene( blgb.heigth(1).width(10)
				.rest(0).threshold(10).desc(1).dmin(0).dmax(0)
				.prox(0).repr(false).repy(0).wta( true )
				.posx(9).posy(0).replace(false)
				.name( "Last Line Lobe" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		
		basicGenome.addGene(
				icb	.varia( SomeChemicals.GLUCOSE.getIndex() ).value( 25 )
				.name( "InitConc GLUCOSE 25" )
				.mutate( true ).duplicate( true ).delete( true ).activ( true )
				.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 ).build() );
		
		// ***** instinct connection from first line of neurons to last line of neurons !
		int iPosX = 0, iPosY = 0, oPosX = 9;
		IntStream.range(0, 10).forEach( i -> {
			basicGenome.addGene( igb
					.inputPosX( iPosX ).inputPosY( iPosY ).outputPosX( oPosX ).outputPosY( i )
					.weight( 42 ).variable( SomeChemicals.GLUCOSE.getIndex() )
					.threshold( 5 ).check( false )
						.mutate( true ).duplicate( true ).delete( true ).activ( true )
						.agemin( 0 ).agemax( 0 ).sex( 0 ).mutation( 25 )
						.name( "Instinct " + i  + " (" + iPosX + ", " + iPosY + ", " + oPosX + ", " + i + ")")
						.build() );
		});
		
		// ***** StimulusDecision : NOT perception ; act on object ?! ; 
		basicGenome.addGene( sdgb	.perception( false ).object( true )
				.indicator( ObjectType.FOOD.getIndex() ).threshold( 0 )
				.attribute( AgentType.BIOSILICO_VIRIDITA.getIndex() )
				.varia( SomeChemicals.PHEROMONE_09.getIndex() ).value( 42 )
				.script( DecisionType.DROP.getIndex() )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.name( "StimulusDecision test " )
			.build() );
		
		Organism testOrga		= new Organism(basicGenome);

		Assertions.assertEquals(15, basicGenome.length());
		Assertions.assertEquals( 1, testOrga.getGenome().size());
		Assertions.assertEquals(15, testOrga.getGenome().get(0).length());
		
		// ***** test with a World and WorldCase
		
		World w			= new World(1, 1);
		WorldCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		
		
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals(  0, testOrga.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( DecisionType.DROP.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  1, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
		Assertions.assertEquals( "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals(  0, testOrga.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( DecisionType.DROP.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  2, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
		Assertions.assertEquals(  "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		wc.addAgent( new TestObjectFoodEgg() );
		Assertions.assertEquals(  1, wc.hasObjectType(ObjectType.FOOD) );
		
		// ***** Indicates DROP
		testOrga.getVariables().setVariable(DecisionType.DROP.getIndex(), 5);
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism

		Assertions.assertEquals(  1, wc.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  0, testOrga.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  4, testOrga.getVariables().getVariable( DecisionType.DROP.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  3, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
		Assertions.assertEquals(  "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		testOrga.addAgent( new TestObjectFoodEgg() );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals(  2, wc.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  0, testOrga.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  3, testOrga.getVariables().getVariable( DecisionType.DROP.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  4, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
		Assertions.assertEquals(  "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals(  2, wc.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  0, testOrga.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  2, testOrga.getVariables().getVariable( DecisionType.DROP.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getVariables().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  5, testOrga.getVariables().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getVariables().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
		Assertions.assertEquals(  "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
	}
	
	// TODO continuing with other (more) StimulsDecision and EmitterReceptor !!

}

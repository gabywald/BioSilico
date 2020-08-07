package gabywald.biosilico.model.tests;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.genetics.EmitterReceptor;
import gabywald.biosilico.genetics.InitialConcentration;
import gabywald.biosilico.genetics.StimulusDecision;
import gabywald.biosilico.genetics.builders.BrainGeneBuilder;
import gabywald.biosilico.genetics.builders.BrainLobeGeneBuilder;
import gabywald.biosilico.model.Chromosome;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.World;
import gabywald.biosilico.model.WorldCase;
import gabywald.biosilico.model.chemicals.ChemicalsHelper;
import gabywald.biosilico.model.enums.DecisionType;
import gabywald.biosilico.model.enums.SomeChemicals;
import gabywald.biosilico.model.enums.StateType;

/**
 * 
 * @author Gabriel Chandesris (2020)
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

		basicGenome.addGene( bgb.heigth(10).width(10).depth(1).activ(true)
				.agemin(0).agemax(0).mutation(25).build() );
		
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

		basicGenome.addGene( bgb.heigth(10).width(10).depth(1).activ(true)
				.agemin(0).agemax(0).mutation(25).build() );
		
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

		basicGenome.addGene( bgb.heigth(10).width(10).depth(1).activ(true)
				.agemin(0).agemax(0).mutation(25).build() );
		
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

		basicGenome.addGene( bgb.heigth(10).width(10).depth(1).activ(true)
				.agemin(0).agemax(0).mutation(25).build() );
		
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
	
	// TODO instinct tests !!
	
}

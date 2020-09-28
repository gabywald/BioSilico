package gabywald.biosilico.model.tests;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.genetics.builders.BrainGeneBuilder;
import gabywald.biosilico.genetics.builders.BrainLobeGeneBuilder;
import gabywald.biosilico.genetics.builders.InitialConcentrationBuilder;
import gabywald.biosilico.genetics.builders.InstinctBuilder;
import gabywald.biosilico.genetics.builders.StimulusDecisionBuilder;
import gabywald.biosilico.model.Chromosome;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.enums.AgentType;
import gabywald.biosilico.model.enums.DecisionType;
import gabywald.biosilico.model.enums.DirectionWorld;
import gabywald.biosilico.model.enums.ObjectType;
import gabywald.biosilico.model.enums.SomeChemicals;
import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.enums.StatusType;
import gabywald.biosilico.model.environment.World2D;
import gabywald.biosilico.model.environment.World2DCase;
import gabywald.global.data.StringUtils;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class OrganismInWorldCaseDecisionsTests {

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
		
		World2D w			= new World2D(1, 1);
		World2DCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( DecisionType.THINK.getIndex() ) );
		
		// ***** Indicates THINK
		testOrga.getChemicals().setVariable(DecisionType.THINK.getIndex(), 5);
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  1, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals( testOrga.getUniqueID() + "::think about [Glucose]\t", testOrga.getState() );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  4, testOrga.getChemicals().getVariable( DecisionType.THINK.getIndex() ) );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  2, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  testOrga.getUniqueID() + "::think about [Glucose]\t", testOrga.getState() );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  3, testOrga.getChemicals().getVariable( DecisionType.THINK.getIndex() ) );
		
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
		
		World2D w			= new World2D(1, 1);
		World2DCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  1, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals( "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  2, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		wc.addAgent( new TestObjectFoodEgg() );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  3, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals( 42, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
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
		
		World2D w			= new World2D(1, 1);
		World2DCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals(  0, wc.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  0, testOrga.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( DecisionType.HAS.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  1, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
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
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( DecisionType.HAS.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  2, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
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
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( DecisionType.HAS.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  3, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
		Assertions.assertEquals(  "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		testOrga.addAgent( new TestObjectFoodEgg() );
		
		// ***** Indicates HAS
		testOrga.getChemicals().setVariable(DecisionType.HAS.getIndex(), 5);
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals(  1, wc.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  1, testOrga.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  4, testOrga.getChemicals().getVariable( DecisionType.HAS.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  4, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals( 42, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
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
		
		World2D w			= new World2D(1, 1);
		World2DCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals(  0, testOrga.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( DecisionType.GET.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  1, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
		Assertions.assertEquals( "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals(  0, testOrga.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( DecisionType.GET.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  2, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
		Assertions.assertEquals(  "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		wc.addAgent( new TestObjectFoodEgg() );
		Assertions.assertEquals(  1, wc.hasObjectType(ObjectType.FOOD) );
		
		// ***** Indicates GET
		testOrga.getChemicals().setVariable(DecisionType.GET.getIndex(), 5);
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism

		Assertions.assertEquals(  0, wc.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  1, testOrga.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  4, testOrga.getChemicals().getVariable( DecisionType.GET.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  3, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
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
		Assertions.assertEquals(  3, testOrga.getChemicals().getVariable( DecisionType.GET.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  4, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
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
		Assertions.assertEquals(  2, testOrga.getChemicals().getVariable( DecisionType.GET.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  5, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
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
		
		World2D w			= new World2D(1, 1);
		World2DCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		
		
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals(  0, testOrga.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( DecisionType.DROP.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  1, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
		Assertions.assertEquals( "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals(  0, testOrga.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( DecisionType.DROP.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  2, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
		Assertions.assertEquals(  "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		wc.addAgent( new TestObjectFoodEgg() );
		Assertions.assertEquals(  1, wc.hasObjectType(ObjectType.FOOD) );
		
		// ***** Indicates DROP
		testOrga.getChemicals().setVariable(DecisionType.DROP.getIndex(), 5);
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism

		Assertions.assertEquals(  1, wc.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  0, testOrga.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  4, testOrga.getChemicals().getVariable( DecisionType.DROP.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.STARCH.getIndex() ) );
		Assertions.assertEquals(  3, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
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
		Assertions.assertEquals(  3, testOrga.getChemicals().getVariable( DecisionType.DROP.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.STARCH.getIndex() ) );
		Assertions.assertEquals(  4, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
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
		Assertions.assertEquals(  2, testOrga.getChemicals().getVariable( DecisionType.DROP.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.STARCH.getIndex() ) );
		Assertions.assertEquals(  5, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
		Assertions.assertEquals(  "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
	}

	@Test
	void testDecisionEATfood() {

		Chromosome basicGenome			= new Chromosome();
		basicGenome.setName( "testDecisionEATfood chromosome" );
		
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
				.script( DecisionType.EAT.getIndex() )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.name( "StimulusDecision test " )
			.build() );
		
		Organism testOrga		= new Organism(basicGenome);

		Assertions.assertEquals(15, basicGenome.length());
		Assertions.assertEquals( 1, testOrga.getGenome().size());
		Assertions.assertEquals(15, testOrga.getGenome().get(0).length());
		
		// ***** test with a World and WorldCase
		
		World2D w			= new World2D(1, 1);
		World2DCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals(  0, testOrga.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( DecisionType.DROP.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.STARCH.getIndex() ) );
		Assertions.assertEquals(  1, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
		Assertions.assertEquals( "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals(  0, testOrga.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( DecisionType.DROP.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.STARCH.getIndex() ) );
		Assertions.assertEquals(  2, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
		Assertions.assertEquals(  "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		wc.addAgent( new TestObjectFoodEgg() );
		Assertions.assertEquals(  1, wc.hasObjectType(ObjectType.FOOD) );
		
		// ***** Indicates EAT
		testOrga.getChemicals().setVariable(DecisionType.EAT.getIndex(), 5);
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism

		Assertions.assertEquals(  1, wc.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  0, testOrga.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  4, testOrga.getChemicals().getVariable( DecisionType.EAT.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.STARCH.getIndex() ) );
		Assertions.assertEquals(  3, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
		Assertions.assertEquals(  "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		testOrga.addAgent( new TestObjectFoodEgg() );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals(  1, wc.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  0, testOrga.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  3, testOrga.getChemicals().getVariable( DecisionType.EAT.getIndex() ) );
		Assertions.assertEquals( 50, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.STARCH.getIndex() ) );
		Assertions.assertEquals(  4, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
		Assertions.assertEquals(  "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertEquals(  1, wc.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  0, testOrga.hasObjectType(ObjectType.FOOD) );
		Assertions.assertEquals(  2, testOrga.getChemicals().getVariable( DecisionType.EAT.getIndex() ) );
		Assertions.assertEquals( 50, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.FRUCTOSE.getIndex() ) );
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.STARCH.getIndex() ) );
		Assertions.assertEquals(  5, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_01.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.PHEROMONE_09.getIndex() ) );
		Assertions.assertEquals(  "", testOrga.getState() );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
	}
	
	@Test
	void testDecisionDEATH() {

		Chromosome basicGenome			= new Chromosome();
		basicGenome.setName( "testDecisionEATfood chromosome" );
		
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
		int thresholdGlucose2kill = 500;
		basicGenome.addGene( sdgb	.perception( false ).object( false )
				.indicator( SomeChemicals.GLUCOSE.getIndex() ).threshold( thresholdGlucose2kill )
				.attribute( SomeChemicals.GLUCOSE.getIndex() )
				.varia( StateType.AGING.getIndex() ).value( 999 )
				.script( DecisionType.DEATH.getIndex() )
			.mutate( true ).duplicate( true ).delete( true ).activ( true )
			.agemin( 0 ).agemax( 999 ).sex( 0 ).mutation( 25 )
			.name( "StimulusDecision test " )
			.build() );
		
		Organism testOrga		= new Organism(basicGenome);

		Assertions.assertEquals(15, basicGenome.length());
		Assertions.assertEquals( 1, testOrga.getGenome().size());
		Assertions.assertEquals(15, testOrga.getGenome().get(0).length());
		
		// ***** test with a World and WorldCase
		
		World2D w			= new World2D(1, 1);
		World2DCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		
		System.out.println( testOrga.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		Assertions.assertTrue( testOrga.isAlive() );
		Assertions.assertNotEquals( StatusType.DEAD, testOrga.getOrganismStatus() );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertTrue( testOrga.isAlive() );
		Assertions.assertNotEquals( StatusType.DEAD, testOrga.getOrganismStatus() );
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(  1, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
		
		testOrga.getChemicals().setVariable(SomeChemicals.GLUCOSE.getIndex(), thresholdGlucose2kill + 100);
		
		// ***** one execution in this context
		testOrga.execution( wc );
		testOrga.cyclePlusPlus(); // Aging organism
		
		Assertions.assertFalse( testOrga.isAlive() );
		Assertions.assertEquals( StatusType.DEAD, testOrga.getOrganismStatus() );
		Assertions.assertEquals(600, testOrga.getChemicals().getVariable( SomeChemicals.GLUCOSE.getIndex() ) );
		Assertions.assertEquals(999, testOrga.getChemicals().getVariable( StateType.AGING.getIndex() ) );
	}

}

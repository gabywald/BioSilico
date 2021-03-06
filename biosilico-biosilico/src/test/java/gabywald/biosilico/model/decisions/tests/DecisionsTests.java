package gabywald.biosilico.model.decisions.tests;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.interfaces.IEnvironmentItem;
import gabywald.biosilico.model.Agent;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.chemicals.ChemicalsHelper;
import gabywald.biosilico.model.decisions.DecisionBuilder;
import gabywald.biosilico.model.decisions.DecisionToMove;
import gabywald.biosilico.model.decisions.IDecision;
import gabywald.biosilico.model.enums.AgentType;
import gabywald.biosilico.model.enums.DecisionType;
import gabywald.biosilico.model.enums.DirectionWorld;
import gabywald.biosilico.model.enums.ObjectType;
import gabywald.biosilico.model.enums.SomeChemicals;
import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.enums.StatusType;
import gabywald.biosilico.model.environment.World2D;
import gabywald.biosilico.model.environment.World2DCase;
import gabywald.biosilico.model.tests.TestObjectFoodEgg;
import gabywald.global.structures.StringCouple;

/**
 * 
 * @author Gabriel Chandesris (2020)
 * TODO review and replace "System.out.println(" with "Logger.printlnLog(LoggerLevel.LL_NONE, "
 */
class DecisionsTests {

	@Test
	void testDecisionSTAY() {
		int which		= DecisionType.STAY.getIndex();
		int object		= -1;
		int threshold	= -1;
		int attribute	= -1;
		int variable	= -1;
		int value		= -1;
		
		DecisionType dType = DecisionType.getFrom( which );
		Assertions.assertNotNull( dType );
		
		Organism testOrga	= new Organism();
		Assertions.assertNotNull( testOrga );
		
		World2D w			= new World2D(3, 3);
		Assertions.assertNotNull( w );
		World2DCase wc	= w.getWorldCase(1,  1);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());
		
		DecisionBuilder db	= new DecisionBuilder();
		Assertions.assertNotNull( db );
		IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
								.attribute(attribute).variable(variable).value(value).build();
		Assertions.assertNotNull( decision );
		if (decision != null) { decision.action(); }
		
		Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());
	}
	
	@Test
	void testDecisionTHINK() {
		int which		= DecisionType.THINK.getIndex();
		// int object		= -1;
		int threshold	= -1;
		int attribute	= -1;
		int variable	= -1;
		int value		= -1;
		
		List<ObjectType> oTypes = Arrays.asList( ObjectType.values() );
		oTypes.stream().forEach( oType -> {
			// System.out.println( "dir: " + dir.getName() + " (" + dir.getIndex() + ")" );
			int object			= oType.getIndex();

			DecisionType dType	= DecisionType.getFrom( which );
			Assertions.assertNotNull( dType );

			Organism testOrga	= new Organism();
			Assertions.assertNotNull( testOrga );

			World2D w				= new World2D(3, 3);
			Assertions.assertNotNull( w );
			World2DCase wc		= w.getWorldCase(1,  1);
			Assertions.assertNotNull( wc );

			testOrga.setCurrentWorldCase( wc );
			Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());

			DecisionBuilder db	= new DecisionBuilder();
			Assertions.assertNotNull( db );
			IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
					.attribute(attribute).variable(variable).value(value).build();
			Assertions.assertNotNull( decision );
			if (decision != null) { decision.action(); }
			
			// Assertions.assertEquals(testOrga.getUniqueID() + "::" + "think about [" + oType.getIndex() + "]\t", testOrga.getState());
			try {
				StringCouple scObject = ChemicalsHelper.getChemicalListe().get( object );
				Assertions.assertEquals(testOrga.getUniqueID() + "::" + "think about [" + scObject.getValueA() + "]\t", testOrga.getState());
			} catch (IndexOutOfBoundsException iobe) {
				Assertions.fail( iobe.getMessage() );
			}
			
		});
	}
	
	@Test
	void testDecisionRandomDirection2D() {
		DirectionWorld.values2DasList().stream().forEach( dw -> {
			System.out.println( "dw: " + dw.getName() + " (" + dw.getIndex() + ")" );
			IntStream.range(0, 10).forEach( i -> {
				DirectionWorld direction = DecisionToMove.getRandomDirection2D( dw );
				System.out.println( "\t direction: " + direction );
				Assertions.assertTrue( ( (direction.getIndex() >= 800) && (direction.getIndex() <= 808) ) );
			});
		});
	}
	
	@Test
	void testDecisionRandomDirection3D() {
		DirectionWorld.valuesAsList().stream().forEach( dw -> {
			System.out.println( "dw: " + dw.getName() + " (" + dw.getIndex() + ")" );
			IntStream.range(0, 10).forEach( i -> {
				DirectionWorld direction = DecisionToMove.getRandomDirection3D( dw );
				System.out.println( "\t direction: " + direction );
				Assertions.assertTrue( ( (direction.getIndex() >= 800) && (direction.getIndex() <= 826) ) );
			});
		});
	}
	
	@Test
	void testDecisionMOVEAWAY() {
		int which		= DecisionType.MOVE_AWAY.getIndex();
		int object		= -1;
		int threshold	= -1;
		int attribute	= -1;
		int variable	= -1;
		int value		= -1;
		
		DecisionType dType = DecisionType.getFrom( which );
		Assertions.assertNotNull( dType );
		
		Organism testOrga	= new Organism();
		Assertions.assertNotNull( testOrga );
		
		World2D w			= new World2D(3, 3);
		Assertions.assertNotNull( w );
		World2DCase wc	= w.getWorldCase(1,  1);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());
		
		DecisionBuilder db	= new DecisionBuilder();
		Assertions.assertNotNull( db );
		IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
								.attribute(attribute).variable(variable).value(value).build();
		Assertions.assertNotNull( decision );
		if (decision != null) { decision.action(); }
		
		Assertions.assertEquals( 1, wc.getAgentListe().size());
		Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());
		Assertions.assertNotEquals(DirectionWorld.CURRENT, testOrga.getDirection());
		
		// ***** Really moving here !!
		testOrga.deplace();
		Assertions.assertEquals( 0, wc.getAgentListe().size());
		Assertions.assertEquals(wc.getDirection(testOrga.getDirection()), testOrga.getCurrentEnvironmentItem());
		Assertions.assertNotEquals(DirectionWorld.CURRENT, testOrga.getDirection());
	}
	
	@Test
	void testDecisionMOVETO() {
		int which		= DecisionType.MOVE_TO.getIndex();
		int object		= -1;
		int threshold	= -1;
		// int attribute	= -1;
		int variable	= -1;
		int value		= -1;
		
		List<DirectionWorld> dirs = Arrays.asList(	DirectionWorld.CURRENT, 
						DirectionWorld.East, 		DirectionWorld.North, 
						DirectionWorld.South, 		DirectionWorld.West, 
						DirectionWorld.NorthEast, 	DirectionWorld.NorthWest, 
						DirectionWorld.SouthEast, 	DirectionWorld.SouthWest );
		dirs.stream().forEach( dir -> {
			// System.out.println( "dir: " + dir.getName() + " (" + dir.getIndex() + ")" );
			int attribute	= dir.getIndex();
			
			DecisionType dType = DecisionType.getFrom( which );
			Assertions.assertNotNull( dType );
			
			Organism testOrga	= new Organism();
			Assertions.assertNotNull( testOrga );
			
			World2D w			= new World2D(3, 3);
			Assertions.assertNotNull( w );
			World2DCase wc	= w.getWorldCase(1,  1);
			Assertions.assertNotNull( wc );
			
			testOrga.setCurrentWorldCase( wc );
			Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());
			
			DecisionBuilder db	= new DecisionBuilder();
			Assertions.assertNotNull( db );
			IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
									.attribute(attribute).variable(variable).value(value).build();
			Assertions.assertNotNull( decision );
			if (decision != null) { decision.action(); }
			
			// ***** apply movement here !! (after decision is made !)
			testOrga.deplace();
			
			IEnvironmentItem nextWC = wc.getDirection(dir);
			// System.out.println( "\t next: (" + nextWC.getPosX() + ", " + nextWC.getPosY() + ")" );
			Assertions.assertEquals(nextWC, testOrga.getCurrentEnvironmentItem());
		});

	}
	
	@Test
	void testDecisionGET() {
		int which		= DecisionType.GET.getIndex();
		int object		= ObjectType.FOOD.getIndex();
		int threshold	= -1;
		int attribute	= -1;
		int variable	= -1;
		int value		= -1;
		
		DecisionType dType = DecisionType.getFrom( which );
		Assertions.assertNotNull( dType );
		
		Organism testOrga	= new Organism();
		Assertions.assertNotNull( testOrga );
		
		World2D w			= new World2D(3, 3);
		Assertions.assertNotNull( w );
		World2DCase wc	= w.getWorldCase(1,  1);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());
		
		DecisionBuilder db	= new DecisionBuilder();
		Assertions.assertNotNull( db );
		IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
								.attribute(attribute).variable(variable).value(value).build();
		Assertions.assertNotNull( decision );
		if (decision != null) { decision.action(); }
		
		Assertions.assertEquals(0, testOrga.getAgentListLength());
		
		// ***** create and add food !!
		Organism food = new Organism();
		food.setObjectType(ObjectType.FOOD);
		wc.addAgent(food);
		
		Assertions.assertEquals(2, wc.getAgentListLength());
		Assertions.assertEquals(0, testOrga.getAgentListLength());
		
		// ***** re-launche decision
		if (decision != null) { decision.action(); }
		
		Assertions.assertEquals(1, wc.getAgentListLength());
		Assertions.assertEquals(1, testOrga.getAgentListLength());
	}
	
	@Test
	void testDecisionDROP() {
		int which		= DecisionType.DROP.getIndex();
		int object		= ObjectType.FOOD.getIndex();
		int threshold	= -1;
		int attribute	= -1;
		int variable	= -1;
		int value		= -1;
		
		DecisionType dType = DecisionType.getFrom( which );
		Assertions.assertNotNull( dType );
		
		Organism testOrga	= new Organism();
		Assertions.assertNotNull( testOrga );
		
		World2D w			= new World2D(3, 3);
		Assertions.assertNotNull( w );
		World2DCase wc	= w.getWorldCase(1,  1);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());
		
		DecisionBuilder db	= new DecisionBuilder();
		Assertions.assertNotNull( db );
		IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
								.attribute(attribute).variable(variable).value(value).build();
		Assertions.assertNotNull( decision );
		if (decision != null) { decision.action(); }
		
		Assertions.assertEquals(0, testOrga.getAgentListLength());
		
		// ***** create and add food !!
		Organism food = new Organism();
		food.setObjectType(ObjectType.FOOD);
		testOrga.addAgent(food);
		
		Assertions.assertEquals(1, wc.getAgentListLength());
		Assertions.assertEquals(1, testOrga.getAgentListLength());
		
		// ***** re-launche decision
		if (decision != null) { decision.action(); }
		
		Assertions.assertEquals(2, wc.getAgentListLength());
		Assertions.assertEquals(0, testOrga.getAgentListLength());
	}
	
	@Test
	void testDecisionPULLoPUSHoSTOPorSLAP() {
		// int which		= DecisionType.PULL.getIndex();
		int object		= ObjectType.FOOD.getIndex();
		int threshold	= -1;
		int attribute	= -1;
		int variable	= -1;
		int value		= -1;
		
		List<DecisionType> dTypes = Arrays.asList( 	DecisionType.PUSH, DecisionType.PULL, 
													DecisionType.STOP, DecisionType.SLAP );
		
		// ***** test with inexistent object type in current WC !
		dTypes.stream().forEach( sdType -> {
			
			DecisionType dType = DecisionType.getFrom( sdType.getIndex() );
			Assertions.assertNotNull( dType );
			
			Organism testOrga	= new Organism();
			Assertions.assertNotNull( testOrga );
			
			World2D w			= new World2D(3, 3);
			Assertions.assertNotNull( w );
			World2DCase wc	= w.getWorldCase(1,  1);
			Assertions.assertNotNull( wc );
			
			testOrga.setCurrentWorldCase( wc );
			Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());
			
			DecisionBuilder db	= new DecisionBuilder();
			Assertions.assertNotNull( db );
			IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
									.attribute(attribute).variable(variable).value(value).build();
			Assertions.assertNotNull( decision );
			if (decision != null) { decision.action(); }
			
			Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());
			
			DecisionType attemptedDType = DecisionType.getFrom(testOrga.getChemicals().getVariable(DecisionType.RECORDSTATE.getIndex()));
			Assertions.assertEquals(attemptedDType, null);
		});
		
		// ***** test with existent object type in current WC !
		dTypes.stream().forEach( oType -> {
			DecisionType dType = DecisionType.getFrom( oType.getIndex() );
			Assertions.assertNotNull( dType );
			
			Organism testOrga	= new Organism();
			Assertions.assertNotNull( testOrga );
			
			World2D w			= new World2D(3, 3);
			Assertions.assertNotNull( w );
			World2DCase wc	= w.getWorldCase(1,  1);
			Assertions.assertNotNull( wc );
			
			TestObjectFoodEgg tofg = new TestObjectFoodEgg();
			wc.addAgent( tofg );
			
			testOrga.setCurrentWorldCase( wc );
			Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());
			
			DecisionBuilder db	= new DecisionBuilder();
			Assertions.assertNotNull( db );
			IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
									.attribute(attribute).variable(variable).value(value).build();
			Assertions.assertNotNull( decision );
			if (decision != null) { decision.action(); }
			
			Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());
			
			DecisionType attemptedDType = DecisionType.getFrom(testOrga.getChemicals().getVariable(DecisionType.RECORDSTATE.getIndex()));
			Assertions.assertEquals(attemptedDType, null);
			
			DecisionType attemptedDTypeTOFG = DecisionType.getFrom(tofg.getChemicals().getVariable(DecisionType.RECORDSTATE.getIndex()));
			Assertions.assertEquals(attemptedDTypeTOFG, dType);
			
		});
	}
	
	@Test
	void testDecisionSLEEPorREST() {
		// int which		= DecisionType.REST.getIndex();
		int object		= ObjectType.CURRENT.getIndex();
		int threshold	= -1;
		int attribute	= -1;
		int variable	= -1;
		int value		= -1;
		
		List<DecisionType> dTypes = Arrays.asList( DecisionType.REST, DecisionType.SLEEP );
		
		// ***** test with inexistent object type in current WC !
		dTypes.stream().forEach( sdType -> {
			
			DecisionType dType = DecisionType.getFrom( sdType.getIndex() );
			Assertions.assertNotNull( dType );
			
			Organism testOrga	= new Organism();
			Assertions.assertNotNull( testOrga );
			
			World2D w			= new World2D(3, 3);
			Assertions.assertNotNull( w );
			World2DCase wc	= w.getWorldCase(1,  1);
			Assertions.assertNotNull( wc );
			
			testOrga.setCurrentWorldCase( wc );
			Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());
			
			DecisionBuilder db	= new DecisionBuilder();
			Assertions.assertNotNull( db );
			IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
									.attribute(attribute).variable(variable).value(value).build();
			Assertions.assertNotNull( decision );
			if (decision != null) { decision.action(); }
			
			Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());
			
			DecisionType attemptedDType = DecisionType.getFrom(testOrga.getChemicals().getVariable(DecisionType.RECORDSTATE.getIndex()));
			Assertions.assertEquals(attemptedDType, dType);
		});
		
		// ***** test with existent object type in current WC !
		dTypes.stream().forEach( oType -> {
			DecisionType dType = DecisionType.getFrom( oType.getIndex() );
			Assertions.assertNotNull( dType );
			
			Organism testOrga	= new Organism();
			Assertions.assertNotNull( testOrga );
			
			World2D w			= new World2D(3, 3);
			Assertions.assertNotNull( w );
			World2DCase wc	= w.getWorldCase(1,  1);
			Assertions.assertNotNull( wc );
			
			wc.addAgent(new TestObjectFoodEgg());
			
			testOrga.setCurrentWorldCase( wc );
			Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());
			
			DecisionBuilder db	= new DecisionBuilder();
			Assertions.assertNotNull( db );
			IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
									.attribute(attribute).variable(variable).value(value).build();
			Assertions.assertNotNull( decision );
			if (decision != null) { decision.action(); }
			
			Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());
			
			DecisionType attemptedDType = DecisionType.getFrom(testOrga.getChemicals().getVariable(DecisionType.RECORDSTATE.getIndex()));
			Assertions.assertEquals(attemptedDType, dType);
		});
	}
	
	@Test
	void testDecisionEAT() {
		int which		= DecisionType.EAT.getIndex();
		int object		= ObjectType.FOOD.getIndex();
		int threshold	= 3; // !! 
		int attribute	= -1;
		int variable	= -1;
		int value		= -1;
		
		DecisionType dType = DecisionType.getFrom( which );
		Assertions.assertNotNull( dType );
		
		Organism testOrga	= new Organism();
		Assertions.assertNotNull( testOrga );
		
		Assertions.assertEquals( 0, testOrga.getAgentListe().size());
		
		DecisionBuilder db	= new DecisionBuilder();
		Assertions.assertNotNull( db );
		IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
								.attribute(attribute).variable(variable).value(value).build();
		Assertions.assertNotNull( decision );
		if (decision != null) { decision.action(); }
		Assertions.assertEquals( 0, testOrga.getAgentListe().size());
		
		Assertions.assertEquals( 0, testOrga.getAgentListe().size());
		testOrga.addAgent(new TestObjectFoodEgg());
		Assertions.assertEquals( 1, testOrga.getAgentListe().size());
		
		if (decision != null) { decision.action(); }
		Assertions.assertEquals( 1, testOrga.getAgentListe().size());
		
		IntStream.range(0, 10).forEach( i-> {
			testOrga.addAgent(new TestObjectFoodEgg());
		});

		Assertions.assertEquals(11, testOrga.getAgentListe().size());
		
		if (decision != null) { decision.action(); }
		Assertions.assertEquals(10, testOrga.getAgentListe().size());
		
		if (decision != null) { decision.action(); }
		Assertions.assertEquals( 9, testOrga.getAgentListe().size());
		
		if (decision != null) { decision.action(); }
		Assertions.assertEquals( 8, testOrga.getAgentListe().size());
		
		if (decision != null) { decision.action(); }
		Assertions.assertEquals( 7, testOrga.getAgentListe().size());
		
		if (decision != null) { decision.action(); }
		Assertions.assertEquals( 6, testOrga.getAgentListe().size());
		
		if (decision != null) { decision.action(); }
		Assertions.assertEquals( 5, testOrga.getAgentListe().size());
		
		if (decision != null) { decision.action(); }
		Assertions.assertEquals( 4, testOrga.getAgentListe().size());
		
		if (decision != null) { decision.action(); }
		Assertions.assertEquals( 3, testOrga.getAgentListe().size());
		
		if (decision != null) { decision.action(); }
		Assertions.assertEquals( 3, testOrga.getAgentListe().size());
	}
	
	@Test
	void testDecisionMAKEGAMET() {
		int which		= DecisionType.MAKE_GAMET.getIndex();
		int object		= -1;
		int threshold	= -1;
		int attribute	= -1;
		int variable	= -1;
		int value		= -1;
		
		DecisionType dType = DecisionType.getFrom( which );
		Assertions.assertNotNull( dType );
		
		Organism testOrga	= new Organism();
		testOrga.setOrganismStatus(StatusType.NOT_ACCURATE);
		Assertions.assertNotNull( testOrga );
		
		World2D w			= new World2D(3, 3);
		Assertions.assertNotNull( w );
		World2DCase wc	= w.getWorldCase(1,  1);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());
		
		DecisionBuilder db	= new DecisionBuilder();
		Assertions.assertNotNull( db );
		IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
								.attribute(attribute).variable(variable).value(value).build();
		Assertions.assertNotNull( decision );
		
		// ***** When not fertile
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.GAMET));
		if (decision != null) { decision.action(); }
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.GAMET));
		if (decision != null) { decision.action(); }
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.GAMET));
		
		Assertions.assertNotNull(ObjectType.AGENT);
		Assertions.assertEquals(ObjectType.AGENT, testOrga.getObjectType());
		
		// ***** When fertile !!
		testOrga.getChemicals().setVariable(StateType.FERTILE.getIndex(), 42);
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.GAMET));
		if (decision != null) { decision.action(); }
		Assertions.assertEquals(1, testOrga.hasAgentStatus(StatusType.GAMET));
		
		Assertions.assertNotNull(ObjectType.AGENT);
		Assertions.assertEquals(ObjectType.AGENT, testOrga.getObjectType());
		
		Agent age = testOrga.getAgentStatus(StatusType.GAMET);
		Assertions.assertEquals(ObjectType.AGENT, age.getObjectType());
		Assertions.assertEquals(AgentType.BIOSILICO_DAEMON, age.getAgentType());
		// Assertions.assertEquals(ObjectType.AGENT, age.getObjectType());
		
		decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
				.attribute(attribute).variable(variable).value(value).build();
		Assertions.assertNotNull( decision );
		if (decision != null) { decision.action(); }
		Assertions.assertEquals(2, testOrga.hasAgentStatus(StatusType.GAMET));
		if (decision != null) { decision.action(); }
		Assertions.assertEquals(3, testOrga.hasAgentStatus(StatusType.GAMET));
		
	}
	
	@Test
	void testDecisionMAKEGAMET_ASFOOD() {
		int which		= DecisionType.MAKE_GAMET.getIndex();
		int object		= -1;
		int threshold	= -1;
		int attribute	= ObjectType.FOOD.getIndex();
		int variable	= -1;
		int value		= -1;
		
		DecisionType dType = DecisionType.getFrom( which );
		Assertions.assertNotNull( dType );
		
		Organism testOrga	= new Organism();
		testOrga.setOrganismStatus(StatusType.NOT_ACCURATE);
		Assertions.assertNotNull( testOrga );
		
		World2D w			= new World2D(3, 3);
		Assertions.assertNotNull( w );
		World2DCase wc	= w.getWorldCase(1,  1);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());
		
		DecisionBuilder db	= new DecisionBuilder();
		Assertions.assertNotNull( db );
		IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
								.attribute(attribute).variable(variable).value(value).build();
		Assertions.assertNotNull( decision );
		
		// ***** When not fertile
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.GAMET));
		if (decision != null) { decision.action(); }
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.GAMET));
		if (decision != null) { decision.action(); }
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.GAMET));
		
		Assertions.assertNotNull(ObjectType.AGENT);
		Assertions.assertEquals(ObjectType.AGENT, testOrga.getObjectType());
		
		// ***** When fertile !!
		testOrga.getChemicals().setVariable(StateType.FERTILE.getIndex(), 42);
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.GAMET));
		if (decision != null) { decision.action(); }
		Assertions.assertEquals(1, testOrga.hasAgentStatus(StatusType.GAMET));
		
		Assertions.assertNotNull(ObjectType.AGENT);
		Assertions.assertEquals(ObjectType.AGENT, testOrga.getObjectType());
		
		Agent age = testOrga.getAgentStatus(StatusType.GAMET);
		Assertions.assertEquals(ObjectType.FOOD, age.getObjectType());
		Assertions.assertEquals(AgentType.BIOSILICO_DAEMON, age.getAgentType());
		// Assertions.assertEquals(ObjectType.AGENT, age.getObjectType());
		
		decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
				.attribute(attribute).variable(variable).value(value).build();
		Assertions.assertNotNull( decision );
		if (decision != null) { decision.action(); }
		Assertions.assertEquals(2, testOrga.hasAgentStatus(StatusType.GAMET));
		if (decision != null) { decision.action(); }
		Assertions.assertEquals(3, testOrga.hasAgentStatus(StatusType.GAMET));
		
	}
	
	@Test
	void testDecisionLAYEGG() {
		int which		= DecisionType.LAY_EGG.getIndex();
		int object		= -1;
		int threshold	= -1;
		int attribute	= -1;
		int variable	= -1;
		int value		= -1;
		
		DecisionType dType = DecisionType.getFrom( which );
		Assertions.assertNotNull( dType );
		
		Organism testOrga	= new Organism();
		Assertions.assertNotNull( testOrga );
		
		World2D w			= new World2D(3, 3);
		Assertions.assertNotNull( w );
		World2DCase wc	= w.getWorldCase(1,  1);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());
		
		DecisionBuilder db	= new DecisionBuilder();
		Assertions.assertNotNull( db );
		IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
								.attribute(attribute).variable(variable).value(value).build();
		Assertions.assertNotNull( decision );
		
		// ***** When not fertile
		Assertions.assertEquals(1, wc.getAgentListLength());
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.EGG));
		if (decision != null) { decision.action(); }
		Assertions.assertEquals(1, wc.getAgentListLength());
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.EGG));
		if (decision != null) { decision.action(); }
		Assertions.assertEquals(1, wc.getAgentListLength());
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.EGG));
		
		// ***** When pregnant !!
		testOrga.addAgent(new TestObjectFoodEgg());
		Assertions.assertEquals(1, wc.getAgentListLength());
		Assertions.assertEquals(1, testOrga.hasAgentStatus(StatusType.EGG));
		if (decision != null) { decision.action(); }
		Assertions.assertEquals(2, wc.getAgentListLength());
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.EGG));
		
	}
	
	@Test
	void testDecisionDEATH() {
		int which		= DecisionType.DEATH.getIndex();
		int object		= -1;
		int threshold	= 900;
		int attribute	= StateType.AGING.getIndex();
		int variable	= StateType.AGING.getIndex();
		int value		= 999;
		
		DecisionType dType = DecisionType.getFrom( which );
		Assertions.assertNotNull( dType );
		
		Organism testOrga	= new Organism();
		Assertions.assertNotNull( testOrga );
		testOrga.getChemicals().setVariable(StateType.AGING.getIndex(), threshold + 5);
		
		Assertions.assertTrue( testOrga.isAlive() );
		Assertions.assertNotEquals( StatusType.DEAD, testOrga.getOrganismStatus() );
		Assertions.assertEquals( threshold + 5, testOrga.getChemicals().getVariable(StateType.AGING.getIndex()));
		
		DecisionBuilder db	= new DecisionBuilder();
		Assertions.assertNotNull( db );
		IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
								.attribute(attribute).variable(variable).value(value).build();
		Assertions.assertNotNull( decision );
		if (decision != null) { decision.action(); }
		
		Assertions.assertFalse( testOrga.isAlive() );
		Assertions.assertEquals( StatusType.DEAD, testOrga.getOrganismStatus() );
		Assertions.assertEquals(999, testOrga.getChemicals().getVariable(StateType.AGING.getIndex()));
	}
	
	@Test
	void testDecisionEMIT() {
		int which		= DecisionType.EMIT.getIndex();
		int object		= DirectionWorld.CURRENT.getIndex();
		int threshold	= -1;
		int attribute	= -1;
		int variable	= SomeChemicals.WATER.getIndex();
		int value		= 50;
		
		DecisionType dType = DecisionType.getFrom( which );
		Assertions.assertNotNull( dType );
		
		Organism testOrga	= new Organism();
		Assertions.assertNotNull( testOrga );
		
		World2D w			= new World2D(3, 3);
		Assertions.assertNotNull( w );
		World2DCase wc	= w.getWorldCase(1,  1);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());
		
		testOrga.getChemicals().setVariable(SomeChemicals.WATER.getIndex(), 100);
		
		Assertions.assertEquals(100, testOrga.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		
		DecisionBuilder db	= new DecisionBuilder();
		Assertions.assertNotNull( db );
		IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
								.attribute(attribute).variable(variable).value(value).build();
		Assertions.assertNotNull( decision );
		if (decision != null) { decision.action(); }
		
		Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());
		
		Assertions.assertEquals( 50, testOrga.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 50, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		
		if (decision != null) { decision.action(); }
		
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		
		if (decision != null) { decision.action(); }
		
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
	}
	
	@Test
	void testDecisionRECEIVE() {
		int which		= DecisionType.RECEIVE.getIndex();
		int object		= DirectionWorld.CURRENT.getIndex();
		int threshold	= -1;
		int attribute	= -1;
		int variable	= SomeChemicals.WATER.getIndex();
		int value		= 50;
		
		DecisionType dType = DecisionType.getFrom( which );
		Assertions.assertNotNull( dType );
		
		Organism testOrga	= new Organism();
		Assertions.assertNotNull( testOrga );
		
		World2D w			= new World2D(3, 3);
		Assertions.assertNotNull( w );
		World2DCase wc	= w.getWorldCase(1,  1);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());
		
		testOrga.getChemicals().setVariable(SomeChemicals.WATER.getIndex(),   0);
		wc.getChemicals().setVariable(SomeChemicals.WATER.getIndex(), 100);
		
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(100, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		
		DecisionBuilder db	= new DecisionBuilder();
		Assertions.assertNotNull( db );
		IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
								.attribute(attribute).variable(variable).value(value).build();
		Assertions.assertNotNull( decision );
		if (decision != null) { decision.action(); }
		
		Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());
		
		Assertions.assertEquals( 50, testOrga.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 50, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		
		if (decision != null) { decision.action(); }
		
		Assertions.assertEquals(100, testOrga.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		
		if (decision != null) { decision.action(); }
		
		Assertions.assertEquals(100, testOrga.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, wc.getChemicals().getVariable(SomeChemicals.WATER.getIndex()));
	}
	
	@Test
	void testDecisionHAS() {
		int which		= DecisionType.HAS.getIndex();
		int object		= ObjectType.FOOD.getIndex();
		int threshold	= 0;
		int attribute	= AgentType.BIOSILICO_VIRIDITA.getIndex();
		int variable	= SomeChemicals.PHEROMONE_09.getIndex();
		int value		= 25;
		
		DecisionType dType = DecisionType.getFrom( which );
		Assertions.assertNotNull( dType );
		
		Organism testOrga	= new Organism();
		Assertions.assertNotNull( testOrga );
		
		DecisionBuilder db	= new DecisionBuilder();
		Assertions.assertNotNull( db );
		IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
								.attribute(attribute).variable(variable).value(value).build();
		Assertions.assertNotNull( decision );
		if (decision != null) { decision.action(); }
		
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable(SomeChemicals.PHEROMONE_09.getIndex()));
		
		testOrga.addAgent(new TestObjectFoodEgg());
		
		if (decision != null) { decision.action(); }
		
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable(SomeChemicals.PHEROMONE_09.getIndex()));
	}
	
	@Test
	void testDecisionHASmore() {
		int which		= DecisionType.HAS.getIndex();
		int object		= ObjectType.FOOD.getIndex();
		int threshold	= 1;
		int attribute	= AgentType.BIOSILICO_VIRIDITA.getIndex();
		int variable	= SomeChemicals.PHEROMONE_09.getIndex();
		int value		= 25;
		
		DecisionType dType = DecisionType.getFrom( which );
		Assertions.assertNotNull( dType );
		
		Organism testOrga	= new Organism();
		Assertions.assertNotNull( testOrga );
		
		DecisionBuilder db	= new DecisionBuilder();
		Assertions.assertNotNull( db );
		IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
								.attribute(attribute).variable(variable).value(value).build();
		Assertions.assertNotNull( decision );
		if (decision != null) { decision.action(); }
		
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable(SomeChemicals.PHEROMONE_09.getIndex()));
		
		testOrga.addAgent(new TestObjectFoodEgg());
		
		if (decision != null) { decision.action(); }
		
		Assertions.assertEquals(  0, testOrga.getChemicals().getVariable(SomeChemicals.PHEROMONE_09.getIndex()));
		
		testOrga.addAgent(new TestObjectFoodEgg());
		
		if (decision != null) { decision.action(); }
		
		Assertions.assertEquals( 25, testOrga.getChemicals().getVariable(SomeChemicals.PHEROMONE_09.getIndex()));
	}
	
	@Test
	void testDecisionIS_AgentType() {
		int which		= DecisionType.IS.getIndex();
		int object		= -1;
		int threshold	= -1;
		int attribute	= -1;
		int variable	= StateType.AGENT_TYPE.getIndex();
		int value		= AgentType.BIOSILICO_ANIMA.getIndex();
		
		DecisionType dType = DecisionType.getFrom( which );
		Assertions.assertNotNull( dType );
		
		Organism testOrga	= new Organism();
		Assertions.assertNotNull( testOrga );
		
		Assertions.assertNotEquals(AgentType.BIOSILICO_ANIMA.getIndex(), testOrga.getChemicals().getVariable(StateType.AGENT_TYPE.getIndex()));
		
		DecisionBuilder db	= new DecisionBuilder();
		Assertions.assertNotNull( db );
		IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
								.attribute(attribute).variable(variable).value(value).build();
		Assertions.assertNotNull( decision );
		if (decision != null) { decision.action(); }
		
		Assertions.assertEquals(AgentType.BIOSILICO_ANIMA.getIndex(), testOrga.getChemicals().getVariable(StateType.AGENT_TYPE.getIndex()));
		
	}
	
	@Test
	void testDecisionIS_ObjectType() {
		int which		= DecisionType.IS.getIndex();
		int object		= -1;
		int threshold	= -1;
		int attribute	= -1;
		int variable	= StateType.TYPEOF.getIndex();
		int value		= ObjectType.PHONE.getIndex();
		
		DecisionType dType = DecisionType.getFrom( which );
		Assertions.assertNotNull( dType );
		
		Organism testOrga	= new Organism();
		Assertions.assertNotNull( testOrga );
		
		Assertions.assertNotEquals(ObjectType.PHONE.getIndex(), testOrga.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
		DecisionBuilder db	= new DecisionBuilder();
		Assertions.assertNotNull( db );
		IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
								.attribute(attribute).variable(variable).value(value).build();
		Assertions.assertNotNull( decision );
		if (decision != null) { decision.action(); }
		
		Assertions.assertEquals(ObjectType.PHONE.getIndex(), testOrga.getChemicals().getVariable(StateType.TYPEOF.getIndex()));
		
	}
	
	@Test
	void testDecisionIS_StatusType() {
		int which		= DecisionType.IS.getIndex();
		int object		= -1;
		int threshold	= -1;
		int attribute	= -1;
		int variable	= StateType.STATUS.getIndex();
		int value		= StatusType.ADULT.getIndex();
		
		DecisionType dType = DecisionType.getFrom( which );
		Assertions.assertNotNull( dType );
		
		Organism testOrga	= new Organism();
		Assertions.assertNotNull( testOrga );
		
		Assertions.assertNotEquals(StatusType.ADULT.getIndex(), testOrga.getChemicals().getVariable(StateType.STATUS.getIndex()));
		
		DecisionBuilder db	= new DecisionBuilder();
		Assertions.assertNotNull( db );
		IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
								.attribute(attribute).variable(variable).value(value).build();
		Assertions.assertNotNull( decision );
		if (decision != null) { decision.action(); }
		
		Assertions.assertEquals(StatusType.ADULT.getIndex(), testOrga.getChemicals().getVariable(StateType.STATUS.getIndex()));
		
	}
	
	// XXX NOTE Decision MATE : see ReproductionTests !

	@Test
	void testDecisionMATE_DAEMON_ITSELF() {
		int which		= DecisionType.MATE.getIndex();
		int object		= -1;
		int threshold	= -1;
		int attribute	= -1;
		int variable	= -1;
		int value		= -1;
		
		DecisionType dType = DecisionType.getFrom( which );
		Assertions.assertNotNull( dType );
		
		Organism testOrga	= new Organism();
		Assertions.assertNotNull( testOrga );
		
		testOrga.getChemicals().setVariable(StateType.FERTILE.getIndex(), 100);
		testOrga.setOrganismStatus(StatusType.NOT_ACCURATE);
		
		Assertions.assertEquals( 0, testOrga.getAgentListe().size());
		
		DecisionBuilder db	= new DecisionBuilder();
		Assertions.assertNotNull( db );
		IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
								.attribute(attribute).variable(variable).value(value).build();
		Assertions.assertNotNull( decision );
		if (decision != null) { decision.action(); }
		Assertions.assertEquals( 1, testOrga.getAgentListe().size());
		Assertions.assertEquals( 1, testOrga.hasAgentStatus(StatusType.EGG));
		
		if (decision != null) { decision.action(); }
		Assertions.assertEquals( 2, testOrga.getAgentListe().size());
		Assertions.assertEquals( 2, testOrga.hasAgentStatus(StatusType.EGG));
		
	}
	

	
}

package gabywald.biosilico.model.decisions.tests;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.World;
import gabywald.biosilico.model.WorldCase;
import gabywald.biosilico.model.decisions.DecisionBuilder;
import gabywald.biosilico.model.decisions.DecisionToMove;
import gabywald.biosilico.model.decisions.IDecision;
import gabywald.biosilico.model.enums.DecisionType;
import gabywald.biosilico.model.enums.DirectionWorld;
import gabywald.biosilico.model.enums.ObjectType;
import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.enums.StatusType;

/**
 * 
 * @author Gabriel Chandesris (2020)
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
		
		DecisionType dType = DecisionType.getValue( which );
		Assertions.assertNotNull( dType );
		
		Organism testOrga	= new Organism();
		Assertions.assertNotNull( testOrga );
		
		World w			= new World(3, 3);
		Assertions.assertNotNull( w );
		WorldCase wc	= w.getWorldCase(1,  1);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		Assertions.assertEquals(wc, testOrga.getCurrentWorldCase());
		
		DecisionBuilder db	= new DecisionBuilder();
		Assertions.assertNotNull( db );
		IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
								.attribute(attribute).variable(variable).value(value).build();
		Assertions.assertNotNull( decision );
		if (decision != null) { decision.action(); }
		
		Assertions.assertEquals(wc, testOrga.getCurrentWorldCase());
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

			DecisionType dType	= DecisionType.getValue( which );
			Assertions.assertNotNull( dType );

			Organism testOrga	= new Organism();
			Assertions.assertNotNull( testOrga );

			World w				= new World(3, 3);
			Assertions.assertNotNull( w );
			WorldCase wc		= w.getWorldCase(1,  1);
			Assertions.assertNotNull( wc );

			testOrga.setCurrentWorldCase( wc );
			Assertions.assertEquals(wc, testOrga.getCurrentWorldCase());

			DecisionBuilder db	= new DecisionBuilder();
			Assertions.assertNotNull( db );
			IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
					.attribute(attribute).variable(variable).value(value).build();
			Assertions.assertNotNull( decision );
			if (decision != null) { decision.action(); }
			
			Assertions.assertEquals("think about [" + oType.getIndex() + "]\t", testOrga.getState());
		});
	}
	
	@Test
	void testDecisionRandomDirection() {
		Stream.of(DirectionWorld.values()).forEach( dw -> {
			// System.out.println( "dw: " + dw.getName() + " (" + dw.getIndex() + ")" );
			IntStream.range(0, 10).forEach( i -> {
				int direction = DecisionToMove.getRandomDirection( dw.getIndex() );
				// System.out.println( "\t direction: " + direction );
				Assertions.assertTrue( ( (direction >= 800) && (direction <= 808) ) );
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
		
		DecisionType dType = DecisionType.getValue( which );
		Assertions.assertNotNull( dType );
		
		Organism testOrga	= new Organism();
		Assertions.assertNotNull( testOrga );
		
		World w			= new World(3, 3);
		Assertions.assertNotNull( w );
		WorldCase wc	= w.getWorldCase(1,  1);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		Assertions.assertEquals(wc, testOrga.getCurrentWorldCase());
		
		DecisionBuilder db	= new DecisionBuilder();
		Assertions.assertNotNull( db );
		IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
								.attribute(attribute).variable(variable).value(value).build();
		Assertions.assertNotNull( decision );
		if (decision != null) { decision.action(); }
		
		// XXX move away, really ??
		Assertions.assertEquals(wc, testOrga.getCurrentWorldCase());
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
			
			DecisionType dType = DecisionType.getValue( which );
			Assertions.assertNotNull( dType );
			
			Organism testOrga	= new Organism();
			Assertions.assertNotNull( testOrga );
			
			World w			= new World(3, 3);
			Assertions.assertNotNull( w );
			WorldCase wc	= w.getWorldCase(1,  1);
			Assertions.assertNotNull( wc );
			
			testOrga.setCurrentWorldCase( wc );
			Assertions.assertEquals(wc, testOrga.getCurrentWorldCase());
			
			DecisionBuilder db	= new DecisionBuilder();
			Assertions.assertNotNull( db );
			IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
									.attribute(attribute).variable(variable).value(value).build();
			Assertions.assertNotNull( decision );
			if (decision != null) { decision.action(); }
			
			// ***** apply movement here !! (after decision is made !)
			testOrga.deplace();
			
			WorldCase nextWC = wc.getDirection(dir);
			// System.out.println( "\t next: (" + nextWC.getPosX() + ", " + nextWC.getPosY() + ")" );
			Assertions.assertEquals(nextWC, testOrga.getCurrentWorldCase());
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
		
		DecisionType dType = DecisionType.getValue( which );
		Assertions.assertNotNull( dType );
		
		Organism testOrga	= new Organism();
		Assertions.assertNotNull( testOrga );
		
		World w			= new World(3, 3);
		Assertions.assertNotNull( w );
		WorldCase wc	= w.getWorldCase(1,  1);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		Assertions.assertEquals(wc, testOrga.getCurrentWorldCase());
		
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
		
		DecisionType dType = DecisionType.getValue( which );
		Assertions.assertNotNull( dType );
		
		Organism testOrga	= new Organism();
		Assertions.assertNotNull( testOrga );
		
		World w			= new World(3, 3);
		Assertions.assertNotNull( w );
		WorldCase wc	= w.getWorldCase(1,  1);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		Assertions.assertEquals(wc, testOrga.getCurrentWorldCase());
		
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
	void testDecisionPULLoPUSHoSTOP() {
		// int which		= DecisionType.PULL.getIndex();
		int object		= ObjectType.FOOD.getIndex();
		int threshold	= -1;
		int attribute	= -1;
		int variable	= -1;
		int value		= -1;
		
		List<DecisionType> oTypes = Arrays.asList( 	DecisionType.PUSH, DecisionType.PULL, 
													DecisionType.STOP, DecisionType.SLAP );
		
		// ***** test with inexistent object type in current WC !
		oTypes.stream().forEach( oType -> {
			DecisionType dType = DecisionType.getValue( oType.getIndex() );
			Assertions.assertNotNull( dType );
			
			Organism testOrga	= new Organism();
			Assertions.assertNotNull( testOrga );
			
			World w			= new World(3, 3);
			Assertions.assertNotNull( w );
			WorldCase wc	= w.getWorldCase(1,  1);
			Assertions.assertNotNull( wc );
			
			testOrga.setCurrentWorldCase( wc );
			Assertions.assertEquals(wc, testOrga.getCurrentWorldCase());
			
			DecisionBuilder db	= new DecisionBuilder();
			Assertions.assertNotNull( db );
			IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
									.attribute(attribute).variable(variable).value(value).build();
			Assertions.assertNotNull( decision );
			if (decision != null) { decision.action(); }
			
			Assertions.assertEquals(wc, testOrga.getCurrentWorldCase());
		});
		
		// ***** test with existent object type in current WC !
		oTypes.stream().forEach( oType -> {
			DecisionType dType = DecisionType.getValue( oType.getIndex() );
			Assertions.assertNotNull( dType );
			
			Organism testOrga	= new Organism();
			Assertions.assertNotNull( testOrga );
			
			World w			= new World(3, 3);
			Assertions.assertNotNull( w );
			WorldCase wc	= w.getWorldCase(1,  1);
			Assertions.assertNotNull( wc );
			
			wc.addAgent(new TestObject());
			
			testOrga.setCurrentWorldCase( wc );
			Assertions.assertEquals(wc, testOrga.getCurrentWorldCase());
			
			DecisionBuilder db	= new DecisionBuilder();
			Assertions.assertNotNull( db );
			IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
									.attribute(attribute).variable(variable).value(value).build();
			Assertions.assertNotNull( decision );
			if (decision != null) { decision.action(); }
			
			Assertions.assertEquals(wc, testOrga.getCurrentWorldCase());
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
		
		DecisionType dType = DecisionType.getValue( which );
		Assertions.assertNotNull( dType );
		
		Organism testOrga	= new Organism();
		Assertions.assertNotNull( testOrga );
		
		World w			= new World(3, 3);
		Assertions.assertNotNull( w );
		WorldCase wc	= w.getWorldCase(1,  1);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		Assertions.assertEquals(wc, testOrga.getCurrentWorldCase());
		Assertions.assertEquals( 0, testOrga.getAgentListe().size());
		
		DecisionBuilder db	= new DecisionBuilder();
		Assertions.assertNotNull( db );
		IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
								.attribute(attribute).variable(variable).value(value).build();
		Assertions.assertNotNull( decision );
		if (decision != null) { decision.action(); }
		Assertions.assertEquals( 0, testOrga.getAgentListe().size());
		
		Assertions.assertEquals(wc, testOrga.getCurrentWorldCase());
		
		Assertions.assertEquals( 0, testOrga.getAgentListe().size());
		testOrga.addAgent(new TestObject());
		Assertions.assertEquals( 1, testOrga.getAgentListe().size());
		
		if (decision != null) { decision.action(); }
		Assertions.assertEquals( 1, testOrga.getAgentListe().size());
		
		IntStream.range(0, 10).forEach( i-> {
			testOrga.addAgent(new TestObject());
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
		
		DecisionType dType = DecisionType.getValue( which );
		Assertions.assertNotNull( dType );
		
		Organism testOrga	= new Organism();
		Assertions.assertNotNull( testOrga );
		
		World w			= new World(3, 3);
		Assertions.assertNotNull( w );
		WorldCase wc	= w.getWorldCase(1,  1);
		Assertions.assertNotNull( wc );
		
		testOrga.setCurrentWorldCase( wc );
		Assertions.assertEquals(wc, testOrga.getCurrentWorldCase());
		
		DecisionBuilder db	= new DecisionBuilder();
		Assertions.assertNotNull( db );
		IDecision decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
								.attribute(attribute).variable(variable).value(value).build();
		Assertions.assertNotNull( decision );
		
		// ***** When not fertile
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.EGG));
		if (decision != null) { decision.action(); }
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.EGG));
		if (decision != null) { decision.action(); }
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.EGG));
		
		Assertions.assertNotNull(ObjectType.AGENT);
		Assertions.assertEquals(ObjectType.AGENT, testOrga.getObjectType());
		
		// ***** When fertile !!
		testOrga.getChemicals().setVariable(StateType.FERTILE.getIndex(), 42);
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.EGG));
		if (decision != null) { decision.action(); }
		Assertions.assertEquals(1, testOrga.hasAgentStatus(StatusType.EGG));
		
		Assertions.assertNotNull(ObjectType.AGENT);
		Assertions.assertEquals(ObjectType.AGENT, testOrga.getObjectType());
		
		decision	= db.type(dType).organism( testOrga ).object(object).threshold(threshold)
				.attribute(attribute).variable(variable).value(value).build();
		Assertions.assertNotNull( decision );
		if (decision != null) { decision.action(); }
		Assertions.assertEquals(2, testOrga.hasAgentStatus(StatusType.EGG));
		if (decision != null) { decision.action(); }
		Assertions.assertEquals(3, testOrga.hasAgentStatus(StatusType.EGG));
		
	}
	
	// TODO Decision DEATH
	
	// TODO Decision REST
	
	// TODO Decision SLEEP
	
	// TODO Decision EMIT
	
	// TODO Decision RECEIVE
	
	// TODO Decision HAS
	
	// TODO Decision IS
	
	// TODO Decision LAY_EGG
	
	
	// TODO Decision MATE
	
	
	// TODO continuing DecisionsTests !!

}

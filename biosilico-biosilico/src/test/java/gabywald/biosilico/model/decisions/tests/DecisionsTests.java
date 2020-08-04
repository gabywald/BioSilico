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
		
		List<ObjectType> oTypes = Arrays.asList(	ObjectType.values() );
		oTypes.stream().forEach( oType -> {
			// System.out.println( "dir: " + dir.getName() + " (" + dir.getIndex() + ")" );
			int object	= oType.getIndex();

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
	
	// TODO continuing DecisionsTests !!

}

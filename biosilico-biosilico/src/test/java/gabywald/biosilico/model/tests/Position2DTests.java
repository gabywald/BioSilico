package gabywald.biosilico.model.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.interfaces.IPosition;
import gabywald.biosilico.model.environment.PositionBuilder;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class Position2DTests {

	@Test
	void testPosition() {
		IPosition pos = PositionBuilder.buildPosition( 10, 500);
		Assertions.assertEquals( 10, pos.getPosX());
		Assertions.assertEquals(500, pos.getPosY());
	}

	@Test
	void testEuclidianDistanceFrom() {
		IPosition posA1 = PositionBuilder.buildPosition( 10, 500);
		IPosition posA2 = PositionBuilder.buildPosition(500,  10);
		Assertions.assertEquals(490, posA1.euclidianDistanceFrom( posA2 ));
		
		IPosition posB1 = PositionBuilder.buildPosition( 10,  10);
		IPosition posB2 = PositionBuilder.buildPosition( 10,  10);
		Assertions.assertEquals(0, posB1.euclidianDistanceFrom( posB2 ));
		
		IPosition posC1 = PositionBuilder.buildPosition( 10, -10);
		IPosition posC2 = PositionBuilder.buildPosition(-10,  10);
		Assertions.assertEquals(20, posC1.euclidianDistanceFrom( posC2 ));
	}

	@Test
	void testGetCopy() {
		IPosition posA = PositionBuilder.buildPosition( 41,  43);
		IPosition posB = posA.clone();
		Assertions.assertEquals( posA.getPosX(), posB.getPosX() );
		Assertions.assertEquals( posA.getPosY(), posB.getPosY() );
	}

	@Test
	void testSetPosXSetPosY() {
		IPosition pos = PositionBuilder.buildPosition( 41,  43);
		Assertions.assertEquals( 41, pos.getPosX());
		Assertions.assertEquals( 43, pos.getPosY());
		pos.setPosX( 42 );
		Assertions.assertEquals( 42, pos.getPosX());
		pos.setPosY( 42 );
		Assertions.assertEquals( 42, pos.getPosY());
	}

	@Test
	void testEqualsPosition() {
		IPosition posA = PositionBuilder.buildPosition( 41,  43);
		IPosition posB = posA.clone();
		
		IPosition posC = PositionBuilder.buildPosition(1, 2);
		
		Assertions.assertTrue(posA.equals(posB));
		
		Assertions.assertFalse(posA.equals(posC));
		
		Assertions.assertFalse(posB.equals(posC));
		
		Assertions.assertFalse(posC.equals(posA));
		
		Assertions.assertFalse(posC.equals(posB));
	}

}

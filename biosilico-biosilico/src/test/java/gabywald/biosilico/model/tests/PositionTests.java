package gabywald.biosilico.model.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.model.Position;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class PositionTests {

	@Test
	void testPosition() {
		Position pos = new Position( 10, 500);
		Assertions.assertEquals( 10, pos.getPosX());
		Assertions.assertEquals(500, pos.getPosY());
	}

	@Test
	void testEuclidianDistanceFrom() {
		Position posA1 = new Position( 10, 500);
		Position posA2 = new Position(500,  10);
		Assertions.assertEquals(490, posA1.euclidianDistanceFrom( posA2 ));
		
		Position posB1 = new Position( 10,  10);
		Position posB2 = new Position( 10,  10);
		Assertions.assertEquals(0, posB1.euclidianDistanceFrom( posB2 ));
		
		Position posC1 = new Position( 10, -10);
		Position posC2 = new Position(-10,  10);
		Assertions.assertEquals(20, posC1.euclidianDistanceFrom( posC2 ));
	}

	@Test
	void testGetCopy() {
		Position posA = new Position( 41,  43);
		Position posB = posA.getCopy();
		Assertions.assertEquals( posA.getPosX(), posB.getPosX() );
		Assertions.assertEquals( posA.getPosY(), posB.getPosY() );
	}

	@Test
	void testSetPosXSetPosY() {
		Position pos = new Position( 41,  43);
		Assertions.assertEquals( 41, pos.getPosX());
		Assertions.assertEquals( 43, pos.getPosY());
		pos.setPosX( 42 );
		Assertions.assertEquals( 42, pos.getPosX());
		pos.setPosY( 42 );
		Assertions.assertEquals( 42, pos.getPosY());
	}

	@Test
	void testEqualsPosition() {
		Position posA = new Position( 41,  43);
		Position posB = posA.getCopy();
		
		Position posC = new Position(1, 2);
		
		Assertions.assertTrue(posA.equals(posB));
		
		Assertions.assertFalse(posA.equals(posC));
		
		Assertions.assertFalse(posB.equals(posC));
		
		Assertions.assertFalse(posC.equals(posA));
		
		Assertions.assertFalse(posC.equals(posB));
	}

}

package gabywald.creatures.genetics.simple.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.creatures.genetics.simple.GeneCreatures1;
import gabywald.creatures.model.UnsignedByte;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class GeneCreatures1Tests {
	
	@Test
	void testGeneCreatures1() {
		String nameType = "type";
		UnsignedByte[] header = UnsignedByte.headerCutterBytes( "1234567" );
		int attempted = 0;
		
		GeneCreatures1 gc1 = new GeneCreatures1(nameType, header, 0);
		Assertions.assertNotNull( gc1 );
		Assertions.assertEquals(nameType, gc1.getType());
		Assertions.assertEquals(header, gc1.getHeader());
		Assertions.assertEquals(attempted, gc1.getAttemptedLength());
		Assertions.assertEquals("GeneCreatures1 ( type , [49, 50, 51, 52, 53, 54, 55] )	 contents: []\n", 
								gc1.toString());
		Assertions.assertEquals("type : [49, 50, 51, 52, 53, 54, 55] => []", gc1.printInline());
	}
	
	// TODO GeneCreatures1Tests

//	@Test
//	void testGetType() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSetType() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetHeader() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSetHeader() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetAttemptedLength() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSetAttemptedLength() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetContents() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSetContents() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetHaserror() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSetHaserror() {
//		fail("Not yet implemented");
//	}

}

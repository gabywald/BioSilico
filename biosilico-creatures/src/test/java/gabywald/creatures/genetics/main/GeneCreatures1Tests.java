package gabywald.creatures.genetics.main;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GeneCreatures1Tests {
	
	// TODO GeneCreatures1Tests

	@Test
	void testGeneCreatures1() {
		String nameType = "type";
		char[] header = "1234567".toCharArray();
		int attempted = 0;
		GeneCreatures1 gc1 = new GeneCreatures1(nameType, header, 0);
		Assertions.assertNotNull( gc1 );
		Assertions.assertEquals(nameType, gc1.getType());
		Assertions.assertEquals(header, gc1.getHeader());
		Assertions.assertEquals(attempted, gc1.getAttemptedLength());
		Assertions.assertEquals("GeneCreatures1 ( type , [1, 2, 3, 4, 5, 6, 7] )	 contents: []\n", 
								gc1.toString());
		Assertions.assertEquals("type : [1, 2, 3, 4, 5, 6, 7] => []", gc1.printInline());
	}

	//	@Test
//	void testToString() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testPrintInline() {
//		fail("Not yet implemented");
//	}
//
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

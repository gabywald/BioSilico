package gabywald.biosilico.structures.tests;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.structures.PathwayListe;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class PathwayListeTests {

	@Test
	void testPathwayListe() {
		PathwayListe pl = new PathwayListe();
		Assertions.assertNotNull( pl );
		Assertions.assertEquals(0, pl.length());
	}
	
	// TODO complete these tests !! PathwayListeTests

//	@Test
//	void testGetPathway() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSetListe() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSetPathway() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testAddPathwayPathway() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testAddPathwayPathwayInt() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testHas() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testRemovePathwayPathway() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testRemovePathwayInt() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testReadFile() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testPrintFile() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testAddToChamps() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSetChamps() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testRemoveChamps() {
//		fail("Not yet implemented");
//	}

}

package gabywald.biosilico.model.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.model.WorldCase;

class WorldCaseTests {

	@Test
	void testWorldCase() {
		WorldCase wc = new WorldCase();
		
		Assertions.assertNotNull( wc );
		Assertions.assertNotNull( wc.getVariables() );
		Assertions.assertNull( wc.getWorld() );
		Assertions.assertEquals(-1, wc.getPosX());
		Assertions.assertEquals(-1, wc.getPosY());
	}
	
	// TODO complete these tests !! WorldCaseTests

//	@Test
//	void testWorldCaseWorldIntInt() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSetPosX() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSetPosY() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetDirection() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetVariables() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetAgentListLength() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetAgentListe() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testRemAgent() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetAgent() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testHasAgentType() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetAgentType() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testAddAgent() {
//		fail("Not yet implemented");
//	}

}

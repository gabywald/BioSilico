package gabywald.biosilico.model.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.model.World;

class WorldTests {

	@Test
	void testWorld() {
		World w = new World();
		
		Assertions.assertNotNull( w );
		Assertions.assertNotNull( w.getVariables() ); // XXX half-lives
	}
	
	// TODO complete these tests !! WorldTests

//	@Test
//	void testGetDirection() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testExecution() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetVariables() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testAddToVariable() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSetVariable() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetVariable() {
//		fail("Not yet implemented");
//	}

}

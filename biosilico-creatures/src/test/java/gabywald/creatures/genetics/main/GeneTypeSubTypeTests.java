package gabywald.creatures.genetics.main;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GeneTypeSubTypeTests {
	
	@Test
	void testGetGeneTypeSubType() {
		Assertions.assertNotNull( GeneTypeSubType.getGeneTypeSubType("2-1") );
	}

	@Test
	void testGetGeneTypesSubTypes() {
		Assertions.assertNotNull( GeneTypeSubType.getGeneTypesSubTypes() );
	}
	
	// TODO GeneTypeSubTypeTests continuing

//	@Test
//	void testGetType() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetSubtype() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetAttemptedLength() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetName() {
//		fail("Not yet implemented");
//	}

}

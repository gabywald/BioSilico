package gabywald.crypto.data.ioput.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.crypto.data.composition.Organism;
import gabywald.crypto.data.composition.Reference;
import gabywald.crypto.data.ioput.BiologicalFileCreatorHelper;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class BiologicalFileCreatorHelperTests {

	@Test
	void testCreateOrganism() {
		Organism testOrganism = BiologicalFileCreatorHelper.createOrganism();
		
		System.out.println( testOrganism );
		
		Assertions.assertNotNull( testOrganism );
		Assertions.assertNotNull( testOrganism.toStringEMBL() );
		Assertions.assertNotNull( testOrganism.toStringGeneBank() );
		Assertions.assertNotNull( testOrganism.getOrganiName() );
		Assertions.assertNotNull( testOrganism.getSourceName() );
	}

	@Test
	void testCreateReference() {
		Reference testReference = BiologicalFileCreatorHelper.createReference(0, 2020, 1, 42);
		
		System.out.println( testReference );
		
		Assertions.assertNotNull( testReference );
		Assertions.assertNotNull( testReference.toStringEMBL() );
		Assertions.assertNotNull( testReference.toStringGeneBank() );
		
	}

//	@Test
//	void testCreateTitle() {
//		fail("Not yet implemented");
//	}

}

package gabywald.crypto.model.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.crypto.model.GenBankFileCreator;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class GenBankFileCreatorTests {

	@Test
	void testGenBankFileCreator() {
		GenBankFileCreator gbfc = new GenBankFileCreator();
		Assertions.assertNotNull( gbfc );
		
		Assertions.assertEquals(0, gbfc.getEncodedCont().size());
		Assertions.assertEquals(0, gbfc.getEncodedPath().size());
		
		System.out.println( gbfc.toString() );
		
		System.out.println( gbfc.getFullEncryption() );
		
	}

	void testGenBankFileCreatorString() {
		GenBankFileCreator gbfc = new GenBankFileCreator("some content");
		Assertions.assertNotNull( gbfc );
		
		Assertions.assertEquals(1, gbfc.getEncodedCont().size());
		Assertions.assertEquals(0, gbfc.getEncodedPath().size());
		
		System.out.println( gbfc.toString() );
		
		System.out.println( gbfc.getFullEncryption() );
		
	}

	void testGenBankFileCreatorStringString() {

		GenBankFileCreator gbfc = new GenBankFileCreator("//path/to/data", "some content");
		Assertions.assertNotNull( gbfc );
		
		Assertions.assertEquals(1, gbfc.getEncodedCont().size());
		Assertions.assertEquals(1, gbfc.getEncodedPath().size());
		
		System.out.println( gbfc.toString() );
		
		System.out.println( gbfc.getFullEncryption() );
		
	}
	
	// TODO complete these tests !! GenBankFileCreatorTests

}

package gabywald.crypto.data.ioput.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.crypto.data.ioput.EmblFileCreator;
import gabywald.global.data.StringUtils;

class EmblFileCreatorTests {

	@Test
	void testEmblFileCreator01() {
		EmblFileCreator ffc = new EmblFileCreator("//path/to/data", "some content");
		Assertions.assertNotNull( ffc );
		
		Assertions.assertEquals(1, ffc.getEncodedCont().size());
		Assertions.assertEquals(1, ffc.getEncodedPath().size());
		
		System.out.println( ffc.toString() );
		
		System.out.println( ffc.getFullEncryption() );
	}
	
	@Test
	void testEmblFileCreator02() {
		EmblFileCreator ffc = new EmblFileCreator("//path/to/data", "some content" + StringUtils.repeat("acgt", 200));
		Assertions.assertNotNull( ffc );
		
		Assertions.assertEquals(1, ffc.getEncodedCont().size());
		Assertions.assertEquals(1, ffc.getEncodedPath().size());
		
		System.out.println( ffc.toString() );
		
		System.out.println( ffc.getFullEncryption() );
	}

}

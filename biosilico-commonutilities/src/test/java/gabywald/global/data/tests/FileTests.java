package gabywald.global.data.tests;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.global.data.File;

class FileTests {

	@Test
	void testBasics() {
		String pathNameFile01	= "src/test/resources/" + "testFile.txt";
		File baseFile01			= new File( pathNameFile01 );
		Assertions.assertNotNull( baseFile01 );
		Assertions.assertEquals(File.NOTYPE, baseFile01.getType());
		
		Assertions.assertEquals(0, baseFile01.lengthFile());
		Assertions.assertEquals("testFile.txt", baseFile01.getFileName());
		Assertions.assertEquals("src/test/resources/", baseFile01.getDirName());
		
		try {
			String content01 = File.readFile( pathNameFile01 );
			Assertions.assertEquals("some data in test file\n", content01);
			
			File baseFile02			= new File( pathNameFile01, content01.split("\n") );
			Assertions.assertNotNull( baseFile02 );
			Assertions.assertEquals(File.NOTYPE, baseFile02.getType());
			
			Assertions.assertEquals(1, baseFile02.lengthFile());
			Assertions.assertEquals("testFile.txt", baseFile02.getFileName());
			Assertions.assertEquals("src/test/resources/", baseFile02.getDirName());
		} catch (IOException e) {
			e.printStackTrace();
			Assertions.fail( e.getMessage() );
		}
	}

}

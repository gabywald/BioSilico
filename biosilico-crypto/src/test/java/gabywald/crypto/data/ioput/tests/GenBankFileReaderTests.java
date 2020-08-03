package gabywald.crypto.data.ioput.tests;


import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.crypto.data.ioput.GenBankFileReader;
import gabywald.global.data.File;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class GenBankFileReaderTests {

	@Test
	void testGenBankFileReader01() {
		GenBankFileReader gbfr = new GenBankFileReader();
		Assertions.assertEquals("", gbfr.getPath());
		Assertions.assertEquals("", gbfr.getContent());

		// ***** ***** ***** ***** ***** 
		
		String testDataFile = "GeneFileCreatorReaderTests.txt";
		// File toread = new File( testDataFile );
		String content01 = null;
		try {
			content01 = File.readFile( testDataFile );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assertions.assertNotNull( content01 );
		String[] content2read = content01.split("\\*\\*\\*\\*\\* \\*\\*\\*\\*\\* \\*\\*\\*\\*\\* \\*\\*\\*\\*\\* \\*\\*\\*\\*\\* \n");
		
		System.out.println( content2read.length );
		Assertions.assertEquals(10, content2read.length);
		
//		for (String data : content2read) {
//			System.out.println( data );
//			GenBankFileReader gbfrTMP = new GenBankFileReader( data );
//			System.out.println( gbfrTMP.getPath() + "*****");
//			System.out.println( gbfrTMP.getContent() + "*****");
//		}
		
		GenBankFileReader gbfr00 = new GenBankFileReader( content2read[0] );
		Assertions.assertEquals("", gbfr00.getPath());
		Assertions.assertEquals("some content\n" + 
				"================================================================================\n" + 
				"", gbfr00.getContent());
		
		GenBankFileReader gbfr01 = new GenBankFileReader( content2read[1] );
		Assertions.assertEquals("", gbfr01.getPath());
		Assertions.assertEquals("some content\n" + 
				"================================================================================\n" + 
				"", gbfr01.getContent());
		
		GenBankFileReader gbfr02 = new GenBankFileReader( content2read[2] );
		Assertions.assertEquals("", gbfr02.getPath());
		Assertions.assertEquals("some content\n" + 
				"acgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacg\n" + 
				"================================================================================\n" + 
				"", gbfr02.getContent());
		
		GenBankFileReader gbfr03 = new GenBankFileReader( content2read[3] );
		Assertions.assertEquals("", gbfr03.getPath());
		Assertions.assertEquals("someTXT\n" + 
				"================================================================================\n" + 
				"", gbfr03.getContent());
		
		GenBankFileReader gbfr04 = new GenBankFileReader( content2read[4] );
		Assertions.assertEquals("", gbfr04.getPath());
		Assertions.assertEquals("\n" + 
				"================================================================================\n" + 
				"", gbfr04.getContent());
		
		GenBankFileReader gbfr05 = new GenBankFileReader( content2read[5] );
		Assertions.assertEquals("", gbfr05.getPath());
		Assertions.assertEquals("some content\n" + 
				"================================================================================\n" + 
				"", gbfr05.getContent());
		
		GenBankFileReader gbfr06 = new GenBankFileReader( content2read[6] );
		Assertions.assertEquals("", gbfr06.getPath());
		Assertions.assertEquals("some content\n" + 
				"acgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacg\n" + 
				"================================================================================\n" + 
				"", gbfr06.getContent());
		
		GenBankFileReader gbfr07 = new GenBankFileReader( content2read[7] );
		Assertions.assertEquals("", gbfr07.getPath());
		Assertions.assertEquals("someTXT\n" + 
				"================================================================================\n" + 
				"", gbfr07.getContent());
		
		GenBankFileReader gbfr08 = new GenBankFileReader( content2read[8] );
		Assertions.assertEquals("", gbfr08.getPath());
		Assertions.assertEquals("\n" + 
				"================================================================================\n" + 
				"", gbfr08.getContent());
		
		GenBankFileReader gbfr09 = new GenBankFileReader( content2read[9] );
		Assertions.assertEquals("", gbfr09.getPath());
		Assertions.assertEquals("some content\n" + 
				"================================================================================\n" + 
				"", gbfr09.getContent());
		
		
	}

}

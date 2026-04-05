package gabywald.crypto.data.ioput.tests;


import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.crypto.data.BiologicalUtils;
import gabywald.crypto.data.ioput.DirectFileReader;
import gabywald.crypto.model.ITranslator;
import gabywald.global.data.File;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020, 2026)
 */
class DirectFileReaderTests {

	@Test
	void testDirectFileReader01() {
		DirectFileReader gbfr = new DirectFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple );
		
		Assertions.assertEquals(null, gbfr.getDecodedPath());
		Assertions.assertEquals(null, gbfr.getDecodedContent());
		
		gbfr.setFileContent("");
		
		Assertions.assertEquals("", gbfr.getDecodedPath());
		Assertions.assertEquals("", gbfr.getDecodedContent());

		// ***** ***** ***** ***** ***** 
		
		String testDataFile = "encodeDecode/DirectFileCreatorReaderTests.txt";
		// File toread = new File( testDataFile );
		String content01 = null;
		try {
			content01 = File.readFile( testDataFile );
		} catch (IOException e) {
			e.printStackTrace();
			Assertions.fail(e.getMessage());
		}
		Assertions.assertNotNull( content01 );
		String[] content2read = content01.split("\n");
		
		Logger.printlnLog(LoggerLevel.LL_NONE, "'" + content2read.length + "'" );
		Assertions.assertEquals(10, content2read.length);
		
		DirectFileReader gbfrBASE = new DirectFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfrBASE.setFileContent( "" );
		Assertions.assertNotNull(gbfrBASE);
		Assertions.assertNotNull(gbfrBASE.getCompanion());
		Assertions.assertNotNull(gbfrBASE.getCompanion().getForFileContent());
		Assertions.assertNotNull(gbfrBASE.getCompanion().getForPathDirName());
		Logger.printlnLog(LoggerLevel.LL_NONE, gbfrBASE.getCompanion().getForFileContent().toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, gbfrBASE.getCompanion().getForPathDirName().toString() );
		
		DirectFileReader gbfr00 = new DirectFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr00.setFileContent( content2read[0] );
		Assertions.assertNotNull(gbfr00);
		Assertions.assertEquals("", gbfr00.getDecodedPath());
		Assertions.assertEquals("some content", gbfr00.getDecodedContent());
		
		DirectFileReader gbfr01 = new DirectFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr01.setFileContent( content2read[1] );
		Assertions.assertNotNull(gbfr01);
		Assertions.assertEquals("//path/to/data", gbfr01.getDecodedPath());
		Assertions.assertEquals("some content", gbfr01.getDecodedContent());
		
		DirectFileReader gbfr02 = new DirectFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr02.setFileContent( content2read[2] );
		Assertions.assertNotNull(gbfr02);
		Assertions.assertEquals("//path/to/data", gbfr02.getDecodedPath());
		Assertions.assertEquals("some content\n" + 
				"acgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacg", gbfr02.getDecodedContent());
		
		DirectFileReader gbfr03 = new DirectFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr03.setFileContent( content2read[3] );
		Assertions.assertNotNull(gbfrBASE);
		Assertions.assertEquals("//path/to/data", gbfr03.getDecodedPath());
		Assertions.assertEquals("someTXT", gbfr03.getDecodedContent());
		
		DirectFileReader gbfr04 = new DirectFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr04.setFileContent( content2read[4] );
		Assertions.assertNotNull(gbfr04);
		Assertions.assertEquals("", gbfr04.getDecodedPath());
		Assertions.assertEquals("", gbfr04.getDecodedContent());
		
		DirectFileReader gbfr05 = new DirectFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr05.setFileContent( content2read[5] );
		Assertions.assertNotNull(gbfrBASE);
		Assertions.assertEquals("", gbfr05.getDecodedPath());
		Assertions.assertEquals("some content", gbfr05.getDecodedContent());
		
		DirectFileReader gbfr06 = new DirectFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr06.setFileContent( content2read[6] );
		Assertions.assertNotNull(gbfr06);
		Assertions.assertEquals("//path/to/data", gbfr06.getDecodedPath());
		Assertions.assertEquals("some content\n" + 
				"acgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacg", gbfr06.getDecodedContent());
		
		DirectFileReader gbfr07 = new DirectFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr07.setFileContent( content2read[7] );
		Assertions.assertNotNull(gbfr07);
		Assertions.assertEquals("//path/to/data", gbfr07.getDecodedPath());
		Assertions.assertEquals("someTXT", gbfr07.getDecodedContent());
		
		DirectFileReader gbfr08 = new DirectFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr08.setFileContent( content2read[8] );
		Assertions.assertNotNull(gbfr08);
		Assertions.assertEquals("", gbfr08.getDecodedPath());
		Assertions.assertEquals("", gbfr08.getDecodedContent());
		
		DirectFileReader gbfr09 = new DirectFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr09.setFileContent( content2read[9] );
		Assertions.assertNotNull(gbfr09);
		Assertions.assertEquals("", gbfr09.getDecodedPath());
		Assertions.assertEquals("some content", gbfr09.getDecodedContent());
		
	}
	
	@Test
	void testDirectFileReaderGBFRbase() {
		DirectFileReader gbfr00 = new DirectFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr00.setFileContent( ":@@@@@tttactatcgttcgtccgccagaacgatcgttcgtgctcacgcccgtgctcatttg:" );
		Assertions.assertNotNull(gbfr00);
		Assertions.assertEquals("", gbfr00.getDecodedPath());
		Assertions.assertEquals("some content", gbfr00.getDecodedContent());
	}
	
	@Test
	void testDirectFileReaderGBFR00() {
		DirectFileReader gbfr00 = new DirectFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr00.setFileContent( ":@@@@@tttactatcgttcgtccgccagaacgatcgttcgtgctcacgcccgtgctcatttg:" );
		Assertions.assertNotNull(gbfr00);
		Assertions.assertEquals("", gbfr00.getDecodedPath());
		Assertions.assertEquals("some content", gbfr00.getDecodedContent());
	}

}

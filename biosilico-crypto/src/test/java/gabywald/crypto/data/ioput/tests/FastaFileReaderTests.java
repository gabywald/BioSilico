package gabywald.crypto.data.ioput.tests;


import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.crypto.data.BiologicalUtils;
import gabywald.crypto.data.ioput.FastaFileReader;
import gabywald.crypto.model.ITranslator;
import gabywald.global.data.File;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020, 2026)
 */
class FastaFileReaderTests {

	@Test
	void testFastaFileReader01() {
		FastaFileReader gbfr = new FastaFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple );
		
		Assertions.assertEquals(null, gbfr.getDecodedPath());
		Assertions.assertEquals(null, gbfr.getDecodedContent());
		
		gbfr.setFileContent("");
		
		Assertions.assertEquals("", gbfr.getDecodedPath());
		Assertions.assertEquals("", gbfr.getDecodedContent());

		// ***** ***** ***** ***** ***** 
		
		String testDataFile = "encodeDecode/FastaFileCreatorReaderTests.txt";
		// File toread = new File( testDataFile );
		String content01 = null;
		try {
			content01 = File.readFile( testDataFile );
		} catch (IOException e) {
			e.printStackTrace();
			Assertions.fail(e.getMessage());
		}
		Assertions.assertNotNull( content01 );
		String[] content2read = content01.split("\\*\\*\\*\\*\\* \\*\\*\\*\\*\\* \\*\\*\\*\\*\\* \\*\\*\\*\\*\\* \\*\\*\\*\\*\\* \n");
		
		Logger.printlnLog(LoggerLevel.LL_NONE, "'" + content2read.length + "'" );
		Assertions.assertEquals(6, content2read.length);
		
		FastaFileReader gbfrBASE = new FastaFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfrBASE.setFileContent( "" );
		Assertions.assertNotNull(gbfrBASE);
		Assertions.assertNotNull(gbfrBASE.getCompanion());
		Assertions.assertNotNull(gbfrBASE.getCompanion().getForFileContent());
		Assertions.assertNotNull(gbfrBASE.getCompanion().getForPathDirName());
		Logger.printlnLog(LoggerLevel.LL_NONE, gbfrBASE.getCompanion().getForFileContent().toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, gbfrBASE.getCompanion().getForPathDirName().toString() );
		
		FastaFileReader gbfr00 = new FastaFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr00.setFileContent( content2read[0] );
		Assertions.assertNotNull(gbfr00);
		Assertions.assertEquals("", gbfr00.getDecodedPath());
		Assertions.assertEquals("some content", gbfr00.getDecodedContent());
		
		FastaFileReader gbfr01 = new FastaFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr01.setFileContent( content2read[1] );
		Assertions.assertNotNull(gbfr01);
		Assertions.assertEquals("//path/to/data", gbfr01.getDecodedPath());
		Assertions.assertEquals("", gbfr01.getDecodedContent());
		
		FastaFileReader gbfr02 = new FastaFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr02.setFileContent( content2read[2] );
		Assertions.assertNotNull(gbfr02);
		Assertions.assertEquals("//path/to/data", gbfr02.getDecodedPath());
		Assertions.assertEquals("some content", gbfr02.getDecodedContent());
		
		FastaFileReader gbfr03 = new FastaFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr03.setFileContent( content2read[3] );
		Assertions.assertNotNull(gbfrBASE);
		Assertions.assertEquals("//path/to/data", gbfr03.getDecodedPath());
		Assertions.assertEquals("some contentacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgt", gbfr03.getDecodedContent());
		
		FastaFileReader gbfr04 = new FastaFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr04.setFileContent( content2read[4] );
		Assertions.assertNotNull(gbfr04);
		Assertions.assertEquals("", gbfr04.getDecodedPath());
		Assertions.assertEquals("", gbfr04.getDecodedContent());
		
		FastaFileReader gbfr05 = new FastaFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr05.setFileContent( content2read[5] );
		Assertions.assertNotNull(gbfrBASE);
		Assertions.assertEquals("", gbfr05.getDecodedPath());
		Assertions.assertEquals("", gbfr05.getDecodedContent());
		
	}
	
	@Test
	void testFastaFileReaderGBFRbase() {
		FastaFileReader gbfr00 = new FastaFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr00.setFileContent( 
				">HIW_738886847|null|HIW_738886847|48 bp|\n"
				+ "ctatcgttcgtccgccagaacgatcgttcgtgctcacgcccgtgctca" );
		Assertions.assertNotNull(gbfr00);
		Assertions.assertEquals("", gbfr00.getDecodedPath());
		Assertions.assertEquals("some content", gbfr00.getDecodedContent());
	}
	
	@Test
	void testFastaFileReaderGBFR00() {
		FastaFileReader gbfr00 = new FastaFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr00.setFileContent( 
				">HIW_738886847|null|HIW_738886847|48 bp|CHCHCVCKCPDECHCPCTCHDACKCPCK\n"
				+ "ctatcgttcgtccgccagaacgatcgttcgtgctcacgcccgtgctca" );
		Assertions.assertNotNull(gbfr00);
		Assertions.assertEquals("//path/to/data", gbfr00.getDecodedPath());
		Assertions.assertEquals("some content", gbfr00.getDecodedContent());
	}

}

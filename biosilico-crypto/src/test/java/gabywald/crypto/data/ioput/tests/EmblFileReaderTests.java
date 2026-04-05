package gabywald.crypto.data.ioput.tests;


import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.crypto.data.BiologicalUtils;
import gabywald.crypto.data.ioput.EmblFileReader;
import gabywald.crypto.model.ITranslator;
import gabywald.global.data.File;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020, 2026)
 */
class EmblFileReaderTests {

	@Test
	void testEmblFileReader01() {
		EmblFileReader gbfr = new EmblFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple );
		
		Assertions.assertEquals(null, gbfr.getDecodedPath());
		Assertions.assertEquals(null, gbfr.getDecodedContent());
		
		gbfr.setFileContent("");
		
		Assertions.assertEquals("", gbfr.getDecodedPath());
		Assertions.assertEquals("", gbfr.getDecodedContent());

		// ***** ***** ***** ***** ***** 
		
		String testDataFile = "encodeDecode/EmblFileCreatorReaderTests.txt";
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
		
		EmblFileReader gbfrBASE = new EmblFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfrBASE.setFileContent( "" );
		Assertions.assertNotNull(gbfrBASE);
		Assertions.assertNotNull(gbfrBASE.getCompanion());
		Assertions.assertNotNull(gbfrBASE.getCompanion().getForFileContent());
		Assertions.assertNotNull(gbfrBASE.getCompanion().getForPathDirName());
		Logger.printlnLog(LoggerLevel.LL_NONE, gbfrBASE.getCompanion().getForFileContent().toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, gbfrBASE.getCompanion().getForPathDirName().toString() );
		
		EmblFileReader gbfr00 = new EmblFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr00.setFileContent( content2read[0] );
		Assertions.assertNotNull(gbfr00);
		Assertions.assertEquals("", gbfr00.getDecodedPath());
		Assertions.assertEquals("", gbfr00.getDecodedContent());
		
		EmblFileReader gbfr01 = new EmblFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr01.setFileContent( content2read[1] );
		Assertions.assertNotNull(gbfr01);
		Assertions.assertEquals("", gbfr01.getDecodedPath());
		Assertions.assertEquals("", gbfr01.getDecodedContent());
		
		EmblFileReader gbfr02 = new EmblFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr02.setFileContent( content2read[2] );
		Assertions.assertNotNull(gbfr02);
		Assertions.assertEquals("", gbfr02.getDecodedPath());
		Assertions.assertEquals("some content", gbfr02.getDecodedContent());
		
		EmblFileReader gbfr03 = new EmblFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr03.setFileContent( content2read[3] );
		Assertions.assertNotNull(gbfrBASE);
		Assertions.assertEquals("", gbfr03.getDecodedPath());
		Assertions.assertEquals("", gbfr03.getDecodedContent());
		
		EmblFileReader gbfr04 = new EmblFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr04.setFileContent( content2read[4] );
		Assertions.assertNotNull(gbfr04);
		Assertions.assertEquals("", gbfr04.getDecodedPath());
		Assertions.assertEquals("some content", gbfr04.getDecodedContent());
		
		EmblFileReader gbfr05 = new EmblFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr05.setFileContent( content2read[5] );
		Assertions.assertNotNull(gbfrBASE);
		Assertions.assertEquals("", gbfr05.getDecodedPath());
		Assertions.assertEquals("some contentacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgt", gbfr05.getDecodedContent());
		
	}
	
	@Test
	void testEmblFileReaderGBFRbase() {
		EmblFileReader gbfr00 = new EmblFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr00.setFileContent( 
				"ID   YM962084522; SV YM962084522.1; linear; precursor RNA; FUN; 48 BP.\n"
				+ "XX\n"
				+ "AC   \n"
				+ "XX\n"
				+ "FH   Key             Location/Qualifiers\n"
				+ "FH\n"
				+ "FT   CDS             1..49\n"
				+ "FT                   /codon_start=\"1\"\n"
				+ "FT                   /product=\"*****\"\n"
				+ "FT                   /gene=\"LOC752919355\"\n"
				+ "FT                   /translation=\"CHCHCVCKCPDECHCPCTCHDACKCPCK\"\n"
				+ "FT   source          1..49\n"
				+ "FT                   /organism=\"Paris tetraphylla\"\n"
				+ "FT                   /mol_type=\"precursor RNA\"\n"
				+ "FT   gene            1..49\n"
				+ "FT                   /note=\"***** part [1] *****\"\n"
				+ "FT                   /gene=\"LOC752919355\"\n"
				+ "XX\n"
				+ "SQ   Sequence 48 BP; 18 c; 12 t; 7 a; 11 g; 0 other;\n"
				+ "     ctatcgttcg tccgccagaa cgatcgttcg tgctcacgcc cgtgctca                     48\n"
				+ "//" );
		Assertions.assertNotNull(gbfr00);
		Assertions.assertEquals("", gbfr00.getDecodedPath());
		Assertions.assertEquals("some content", gbfr00.getDecodedContent());
	}
	
	@Test
	void testEmblFileReaderGBFR00() {
		EmblFileReader gbfr00 = new EmblFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr00.setFileContent( 
						"ID   YM962084522; SV YM962084522.1; linear; precursor RNA; FUN; 48 BP.\n"
						+ "XX\n"
						+ "AC   \n"
						+ "XX\n"
						+ "KW   \n"
						+ "XX\n"
						+ "OS   Paris tetraphylla\n"
						+ "OC   Eukaryota; Viridiplantae; Streptophyta; Embryophyta; Tracheophyta;\n"
						+ "OC   Spermatophyta; Magnoliophyta; Liliopsida; Liliales; Melanthiaceae; Paris; Paris.\n"
						+ "XX\n"
						+ "RN   [1]\n"
						+ "RC   REVIEW\n"
						+ "RP   1-48\n"
						+ "RA   Smyth Richard, Bolton Vick, Steel Fraiser, Lacey Jason, Venni Kevin,\n"
						+ "RA   Boyer Frank, Sexton Thomas;\n"
						+ "RT   title of the reference 1;\n"
						+ "XX\n"
						+ "RN   [2]\n"
						+ "RP   1-48\n"
						+ "RA   Rennie Bran, Stonelake Keith, Tothill Sam, Beckwith Reeves, Gaskin Stan,\n"
						+ "RA   Batty Manuel, Burgess Dale, Nightingale Madison, Eades Ian, Grant Jon,;\n"
						+ "RA   Levers Chris, Merwood Matt;\n"
						+ "RT   title of the reference 2;\n"
						+ "XX\n"
						+ "RN   [3]\n"
						+ "RP   1-48\n"
						+ "RA   Merwood Todd, Morgan William, Osborne Marlo, Venning Willis,\n"
						+ "RA   Pidgeon Samuel, Boyer Alberto, Smyth Lane, Mathwani Gregory, Taylor William;\n"
						+ "RT   title of the reference 3;\n"
						+ "XX\n"
						+ "RN   [4]\n"
						+ "RP   1-48\n"
						+ "RA   Sadler Corey, Chappell Val, Frost Allen, Lock Murray, Crease Manuel;\n"
						+ "RT   title of the reference 4;\n"
						+ "XX\n"
						+ "XX\n"
						+ "FH   Key             Location/Qualifiers\n"
						+ "FH\n"
						+ "FT   CDS             1..49\n"
						+ "FT                   /codon_start=\"1\"\n"
						+ "FT                   /product=\"*****\"\n"
						+ "FT                   /gene=\"LOC752919355\"\n"
						+ "FT                   /translation=\"CHCHCVCKCPDECHCPCTCHDACKCPCK\"\n"
						+ "FT   source          1..49\n"
						+ "FT                   /organism=\"Paris tetraphylla\"\n"
						+ "FT                   /mol_type=\"precursor RNA\"\n"
						+ "FT   gene            1..49\n"
						+ "FT                   /note=\"***** part [1] *****\"\n"
						+ "FT                   /gene=\"LOC752919355\"\n"
						+ "XX\n"
						+ "SQ   Sequence 48 BP; 18 c; 12 t; 7 a; 11 g; 0 other;\n"
						+ "     ctatcgttcg tccgccagaa cgatcgttcg tgctcacgcc cgtgctca                     48\n"
						+ "//" );
		Assertions.assertNotNull(gbfr00);
		Assertions.assertEquals("", gbfr00.getDecodedPath());
		Assertions.assertEquals("some content", gbfr00.getDecodedContent());
	}

}

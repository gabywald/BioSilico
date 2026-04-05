package gabywald.crypto.data.ioput.tests;


import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.crypto.data.BiologicalUtils;
import gabywald.crypto.data.ioput.GenBankFileReader;
import gabywald.crypto.model.ITranslator;
import gabywald.global.data.File;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020, 2026)
 */
class GenBankFileReaderTests {

	@Test
	void testGenBankFileReader01() {
		GenBankFileReader gbfr = new GenBankFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple );
		
		Assertions.assertEquals(null, gbfr.getDecodedPath());
		Assertions.assertEquals(null, gbfr.getDecodedContent());
		
		gbfr.setFileContent("");
		
		Assertions.assertEquals("", gbfr.getDecodedPath());
		Assertions.assertEquals("", gbfr.getDecodedContent());

		// ***** ***** ***** ***** ***** 
		
		String testDataFile = "encodeDecode/GeneBankFileCreatorReaderTests.txt";
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
		Assertions.assertEquals(10, content2read.length);
		
		GenBankFileReader gbfrBASE = new GenBankFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfrBASE.setFileContent( "" );
		Assertions.assertNotNull(gbfrBASE);
		Assertions.assertNotNull(gbfrBASE.getCompanion());
		Assertions.assertNotNull(gbfrBASE.getCompanion().getForFileContent());
		Assertions.assertNotNull(gbfrBASE.getCompanion().getForPathDirName());
		Logger.printlnLog(LoggerLevel.LL_NONE, gbfrBASE.getCompanion().getForFileContent().toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, gbfrBASE.getCompanion().getForPathDirName().toString() );
		
		GenBankFileReader gbfr00 = new GenBankFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr00.setFileContent( content2read[0] );
		Assertions.assertNotNull(gbfr00);
		Assertions.assertEquals("", gbfr00.getDecodedPath());
		Assertions.assertEquals("some content", gbfr00.getDecodedContent());
		
		GenBankFileReader gbfr01 = new GenBankFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr01.setFileContent( content2read[1] );
		Assertions.assertNotNull(gbfr01);
		Assertions.assertEquals("", gbfr01.getDecodedPath());
		Assertions.assertEquals("some content", gbfr01.getDecodedContent());
		
		GenBankFileReader gbfr02 = new GenBankFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr02.setFileContent( content2read[2] );
		Assertions.assertNotNull(gbfr02);
		Assertions.assertEquals("", gbfr02.getDecodedPath());
		Assertions.assertEquals("some content\n" + 
				"acgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacg", gbfr02.getDecodedContent());
		
		GenBankFileReader gbfr03 = new GenBankFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr03.setFileContent( content2read[3] );
		Assertions.assertNotNull(gbfrBASE);
		Assertions.assertEquals("", gbfr03.getDecodedPath());
		Assertions.assertEquals("someTXT", gbfr03.getDecodedContent());
		
		GenBankFileReader gbfr04 = new GenBankFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr04.setFileContent( content2read[4] );
		Assertions.assertNotNull(gbfr04);
		Assertions.assertEquals("", gbfr04.getDecodedPath());
		Assertions.assertEquals("", gbfr04.getDecodedContent());
		
		GenBankFileReader gbfr05 = new GenBankFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr05.setFileContent( content2read[5] );
		Assertions.assertNotNull(gbfrBASE);
		Assertions.assertEquals("", gbfr05.getDecodedPath());
		Assertions.assertEquals("some content", gbfr05.getDecodedContent());
		
		GenBankFileReader gbfr06 = new GenBankFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr06.setFileContent( content2read[6] );
		Assertions.assertNotNull(gbfr06);
		Assertions.assertEquals("", gbfr06.getDecodedPath());
		Assertions.assertEquals("some content\n" + 
				"acgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacgtacg", gbfr06.getDecodedContent());
		
		GenBankFileReader gbfr07 = new GenBankFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr07.setFileContent( content2read[7] );
		Assertions.assertNotNull(gbfr07);
		Assertions.assertEquals("", gbfr07.getDecodedPath());
		Assertions.assertEquals("someTXT", gbfr07.getDecodedContent());
		
		GenBankFileReader gbfr08 = new GenBankFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr08.setFileContent( content2read[8] );
		Assertions.assertNotNull(gbfr08);
		Assertions.assertEquals("", gbfr08.getDecodedPath());
		Assertions.assertEquals("", gbfr08.getDecodedContent());
		
		GenBankFileReader gbfr09 = new GenBankFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr09.setFileContent( content2read[9] );
		Assertions.assertNotNull(gbfr09);
		Assertions.assertEquals("", gbfr09.getDecodedPath());
		Assertions.assertEquals("some content", gbfr09.getDecodedContent());
		
	}
	
	@Test
	void testGenBankFileReaderGBFRbase() {
		GenBankFileReader gbfr00 = new GenBankFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr00.setFileContent( 
				"LOCUS       DVS_3971646124         56 bp     DNA    linear   HTC 14-NOV-2055\n"
				+ "DEFINITION  Salmo salar (LOC662698378), DNA.\n"
				+ "ACCESSION   DVS_3971646124\n"
				+ "VERSION     DVS_3971646124.0\n"
				+ "KEYWORDS    .\n"
				+ "FEATURES             Location/Qualifiers\n"
				+ "     CDS             1..57\n"
				+ "                     /codon_start=\"1\"\n"
				+ "                     /product=\"*****\"\n"
				+ "                     /gene=\"LOC662698378\"\n"
				+ "                     /translation=\"PYCHCICVHKHPIENHNPNTTHVACKCPHKRW\"\n"
				+ "     source          1..57\n"
				+ "                     /organism=\"Salmo salar\"\n"
				+ "                     /mol_type=\"DNA\"\n"
				+ "     gene            1..57\n"
				+ "                     /note=\"***** part [1] *****\"\n"
				+ "                     /gene=\"LOC662698378\"\n"
				+ "BASE COUNT       18 t      8 a     18 c     12 g      0 other\n"
				+ "ORIGIN      \n"
				+ "        1 tttactatcg ttcgtccgcc agaacgatcg ttcgtgctca cgcccgtgct catttg\n"
				+ "//" );
		Assertions.assertNotNull(gbfr00);
		Assertions.assertEquals("", gbfr00.getDecodedPath());
		Assertions.assertEquals("some content", gbfr00.getDecodedContent());
	}
	
	@Test
	void testGenBankFileReaderGBFR00() {
		GenBankFileReader gbfr00 = new GenBankFileReader( BiologicalUtils.getGenericCrypto( 0 ), BiologicalUtils.getGenericCrypto( 1 ), ITranslator.TranslatorEnum.simple ); 
		gbfr00.setFileContent( 
				"LOCUS       DVS_3971646124         56 bp     DNA    linear   HTC 14-NOV-2055\n"
				+ "DEFINITION  Salmo salar (LOC662698378), DNA.\n"
				+ "ACCESSION   DVS_3971646124\n"
				+ "VERSION     DVS_3971646124.0\n"
				+ "KEYWORDS    .\n"
				+ "SOURCE      Salmo salar\n"
				+ "  ORGANISM  Salmo salar\n"
				+ "            Eukaryota; Metazoa; Chordata; Craniata; Vertebrata; Euteleostomi;\n"
				+ "            Actinopterygii; Neopterygii; Teleostei; Euteleostei;\n"
				+ "            Protacanthopterygii; Salmoniformes; Salmonidae; Salmoninae; Salmo; Salmo.\n"
				+ "REFERENCE   1  (bases 1 to 57)\n"
				+ "  AUTHORS   Harvey,Tim, Bolton,Daniel, Boyes,Alfred, Tapper,Hubert,\n"
				+ "            Underwood,Matthew, Winer,Stew, Timms,Axel, Hurdley,Arthur,\n"
				+ "            Woollven,Madison, Fletcher,Rich, Harvey,Patrick and Luther,Di\n"
				+ "  TITLE     title of the reference 1\n"
				+ "  JOURNAL   Virtual Technology Journal. 4060, 1095-1096 (2017)\n"
				+ "REFERENCE   2  (bases 1 to 57)\n"
				+ "  AUTHORS   Williams,Shawn, Green,Garret, Stonelake,Matt, Barnard,Warren,\n"
				+ "            Elison,Rudy, Matile,Howell, Olberechts,Floyd and Roach,Elv\n"
				+ "  TITLE     title of the reference 2\n"
				+ "  JOURNAL   Epson Communications Fanzine. 1065, 616-628 (2050)\n"
				+ "REFERENCE   3  (bases 1 to 57)\n"
				+ "  AUTHORS   Pennock,Bernie, Coombs,Reeves, Jones,Willis, Barry,Donald,\n"
				+ "            Boyes,Ian, Corner,Allen, Ansley,Bradley, Pearce,Warren,\n"
				+ "            Cliefe,Joey, Cliefe,Scott, Kates,Michael, Newell,Sebastian,\n"
				+ "            Turner,Baxter, Eaton,Harrison, Shelley,Lobo and Balthazor,Ph\n"
				+ "  TITLE     title of the reference 3\n"
				+ "  JOURNAL   Center Systems Publications. 901, 6658-6665 (2050)\n"
				+ "REFERENCE   4  (bases 1 to 57)\n"
				+ "  AUTHORS   Howell,Brad, Forrest,Homer, Pratt,Joe, Frost,Ward, Eades,Dan,\n"
				+ "            Gaskin,Marlo, Lock,Marlo and Waymark,Cyr\n"
				+ "  TITLE     title of the reference 4\n"
				+ "  JOURNAL   Optical Tech. Gazette. 747 (10), 9771-9771 (2055)\n"
				+ "REFERENCE   5  (bases 1 to 57)\n"
				+ "  AUTHORS   Paton,Steven, Nightingale,Thomas, Donelly,Rayyan, Hurdley,Jarvis,\n"
				+ "            Totham,Otto, Williams,David, Gullidge,Pete, Gullidge,Sean,\n"
				+ "            Ward,Jason, Lawrence,Barney and McEvoy,Hube\n"
				+ "  TITLE     title of the reference 5\n"
				+ "  JOURNAL   Costco Foundation Fanzine. 203 (40), 2499-2506 (2043)\n"
				+ "REFERENCE   6  (bases 1 to 57)\n"
				+ "  AUTHORS   Marples,Cecil, Griffin,Cody, Roach,Wayne, Almond,Paul, Wyatt,Glenn,\n"
				+ "            Ingham,Abe, Holt,William, Jones,Dan, Cantlow,Alberto, Morgan,Glenn,\n"
				+ "            Davies,Samuel, Paton,Hamlet, Harris,Corey, Hackett,Tyson and Sexton,Erne\n"
				+ "  TITLE     title of the reference 6\n"
				+ "  JOURNAL   Firaxis Technology Journal. 1876, 6747-6751 (2011)\n"
				+ "REFERENCE   7  (bases 1 to 57)\n"
				+ "  AUTHORS   Winer,Dennis, Howell,Tyson, Balthazor,Murray, Leversedge,Jackie,\n"
				+ "            King,Keith, Woollven,Paul, Edwards,Samuel, Waterhouse,Stanley,\n"
				+ "            Brown,Carter, Comrie,Rod, Bolton,Leo, Kates,Gareth, Daymond,Carter,\n"
				+ "            Rogers,Benjamin, Eaton,Garret, Pennington,Todd, Woollven,Tim and Winer,Dav\n"
				+ "  TITLE     title of the reference 7\n"
				+ "  JOURNAL   Norton Solutions Publications. 9367 (64), 952-964 (2051)\n"
				+ "  REMARK    REVIEW\n"
				+ "REFERENCE   8  (bases 1 to 57)\n"
				+ "  AUTHORS   Hackett,Kamil, Lindsay,Kami, Hey,Todd, Headland,Waldo, Roach,Gaby,\n"
				+ "            Knight,Otto, Harris,Lewis, Edwards,Michael, Smith,Peter,\n"
				+ "            Redman,Tycho, Boyes,Nick, Balthazor,Paul, Page,Hamlet,\n"
				+ "            Roach,Murray, Hancock,Matt, Shopland,Gabe and Paton,Ry\n"
				+ "  TITLE     title of the reference 8\n"
				+ "  JOURNAL   CNet Developments Magazine. 555 (14), 1416-1428 (2033)\n"
				+ "REFERENCE   9  (bases 1 to 57)\n"
				+ "  AUTHORS   Griffin,Iggy, Martin,Val, Slater,Scott, Waymark,Ward,\n"
				+ "            Colley,George, Daymond,Herbert, Stubbs,Benjamin, Sexton,Joey, Matile,Dennis and Jackson,Curt\n"
				+ "  TITLE     title of the reference 9\n"
				+ "  JOURNAL   Toaplan Labs HackZine. 5461 (30), 9019-9026 (2036)\n"
				+ "REFERENCE   10 (bases 1 to 57)\n"
				+ "  AUTHORS   Jones,Sean, Trish,Bernie, Burgess,Hanna, Welch,Duncan,\n"
				+ "            Gilmour,Kevin, Trish,Fernado, Nowell,Jackie, Roach,Edward,\n"
				+ "            Harris,Gerald, Luther,Dennis, Simpson,Filbert, Bailey,George,\n"
				+ "            Roach,William, Winer,Lewis, Lindsay,Abe, Kates,Pete, Mitchell,Dale and Daymond,Ferna\n"
				+ "  TITLE     title of the reference 10\n"
				+ "  JOURNAL   MAF Inc Journal. 591, 752-757 (2025)\n"
				+ "  REMARK    REVIEW\n"
				+ "FEATURES             Location/Qualifiers\n"
				+ "     CDS             1..57\n"
				+ "                     /codon_start=\"1\"\n"
				+ "                     /product=\"*****\"\n"
				+ "                     /gene=\"LOC662698378\"\n"
				+ "                     /translation=\"PYCHCICVHKHPIENHNPNTTHVACKCPHKRW\"\n"
				+ "     source          1..57\n"
				+ "                     /organism=\"Salmo salar\"\n"
				+ "                     /mol_type=\"DNA\"\n"
				+ "     gene            1..57\n"
				+ "                     /note=\"***** part [1] *****\"\n"
				+ "                     /gene=\"LOC662698378\"\n"
				+ "BASE COUNT       18 t      8 a     18 c     12 g      0 other\n"
				+ "ORIGIN      \n"
				+ "        1 tttactatcg ttcgtccgcc agaacgatcg ttcgtgctca cgcccgtgct catttg\n"
				+ "//" );
		Assertions.assertNotNull(gbfr00);
		Assertions.assertEquals("", gbfr00.getDecodedPath());
		Assertions.assertEquals("some content", gbfr00.getDecodedContent());
	}

}

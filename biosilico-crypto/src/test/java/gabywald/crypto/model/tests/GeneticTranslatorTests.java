package gabywald.crypto.model.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.crypto.data.BiologicalUtils;
import gabywald.crypto.model.GeneticTranslator;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020, 2025-2026)
 */
class GeneticTranslatorTests {
	
	@Test
	void testGeneticTranslatorSomeTests() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator gt01REF = new GeneticTranslator(1, true);
		Assertions.assertNotNull( gt01REF );
		Logger.printlnLog(LoggerLevel.LL_NONE, gt01REF.toString());
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeSimple() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
		Assertions.assertNotNull( forFileContent );
		Logger.printlnLog(LoggerLevel.LL_NONE, forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encode(toEncrypt);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent + "]");
		
		Assertions.assertEquals("ctcacgccctatctcaagaactcacgccctatctcaatagagaactcacgccctatctcaatat", encodedFileContent);
		
		String isDecrypted = forFileContent.decode(encodedFileContent, 0, 0);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(isDecrypted, toEncrypt);
		
		Logger.printlnLog(LoggerLevel.LL_NONE, "");
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeMore() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
		Assertions.assertNotNull( forFileContent );
		Logger.printlnLog(LoggerLevel.LL_NONE, forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encodeMore(toEncrypt);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent + "]");
		
		Assertions.assertEquals("ctcacgccctatctcaagaactcacgccctatctcaatagagaactcacgccctatctcaatat", encodedFileContent);
		
		String isDecrypted = forFileContent.decode(encodedFileContent, 0, 0);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(isDecrypted, toEncrypt);
		
		Logger.printlnLog(LoggerLevel.LL_NONE, "");
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeRand() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
		Assertions.assertNotNull( forFileContent );
		Logger.printlnLog(LoggerLevel.LL_NONE, forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encodeRand(toEncrypt);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent + "]");
		
		Assertions.assertEquals("ctcacgccctatctcaagaactcacgccctatctcaatagagaactcacgccctatctcaatat", encodedFileContent);
		
		String isDecrypted = forFileContent.decode(encodedFileContent, 0, 0);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(isDecrypted, toEncrypt);
		
		Logger.printlnLog(LoggerLevel.LL_NONE, "");
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeFile() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
		Assertions.assertNotNull( forFileContent );
		Logger.printlnLog(LoggerLevel.LL_NONE, forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encode(toEncrypt, 0); // NOTE second argument here !! (multiple starts / stops are possibles)
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent.substring(3, encodedFileContent.length()-3) + "]");
		
		String isDecrypted = forFileContent.decodeWithStartStopCodons(encodedFileContent, 0, 0);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(isDecrypted, toEncrypt);
		
		Logger.printlnLog(LoggerLevel.LL_NONE, "");
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodePath() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forPathDirName = BiologicalUtils.getGenericCrypto(1);
		Assertions.assertNotNull( forPathDirName );
		Logger.printlnLog(LoggerLevel.LL_NONE, forPathDirName.toString());
		String toEncrypt = "/home/user/file.txt";
		String encodedFilePath = forPathDirName.encode(toEncrypt, 1);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFilePath + "]" + "\n");

		// Assertions.assertEquals("PWCHDEHTIINMTHTRTYCMCNHHICNSPHTMWACPDLHPYY", encodedFilePath);
		
		// Assertions.assertEquals("PWCHDEHTIINMTHTRTYCMCNHHICNSPHTMWACPDLHPYY", forPathDirName.encode("/home/user/file.txt", 0));
		// Assertions.assertEquals("PWCHDEHTIINMTHTRTYCMCNHHICNSPHTMWACPDLHPYY", forPathDirName.encode("/home/user/file.txt", 1));
		
		// String isDecrypted00 = forPathDirName.decode(encodedFilePath.substring(3, encodedFilePath.length()-3), 0, 0);
		String isDecrypted01 = forPathDirName.decodeWithStartStopCodons(encodedFilePath, 0, 0); // Use specific decoder !!
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + isDecrypted01 + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(isDecrypted01, toEncrypt);
	
		Logger.printlnLog(LoggerLevel.LL_NONE, "");
	}

	// TODO complete these tests !! GeneticTranslatorTests

//	void testGeneticTranslatorIntBoolean() {
//		fail("Not yet implemented");
//	}
//
//	void testGeneticTranslatorGeneticCode() {
//		fail("Not yet implemented");
//	}
//
//	void testGeneticTranslatorEncodingNode() {
//		fail("Not yet implemented");
//	}
//
//	void testDecode() {
//		fail("Not yet implemented");
//	}
//
//	void testDecodeWithStartStopCodons() {
//		fail("Not yet implemented");
//	}
//
//	void testEncodeStringIntBoolean() {
//		fail("Not yet implemented");
//	}
//
//	void testEncodeString() {
//		fail("Not yet implemented");
//	}
//
//	void testEncodeMore() {
//		fail("Not yet implemented");
//	}
//
//	void testEncodeRand() {
//		fail("Not yet implemented");
//	}

}

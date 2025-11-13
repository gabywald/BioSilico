package gabywald.crypto.model.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.crypto.data.BiologicalUtils;
import gabywald.crypto.model.GeneticTranslator;

/**
 * 
 * @author Gabriel Chandesris (2020, 2025)
 */
class GeneticTranslatorTests {
	
	@Test
	void testGeneticTranslatorSomeTests() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator gt01REF = new GeneticTranslator(1, true);
		System.out.println(gt01REF.toString());
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeSimple() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
		System.out.println(forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encode(toEncrypt);
		System.out.println("[" + encodedFileContent + "]");
		
		Assertions.assertEquals("ctcacgccctatctcaagaactcacgccctatctcaatagagaactcacgccctatctcaatat", encodedFileContent);
		
		String isDecrypted = forFileContent.decode(encodedFileContent, 0, 0);
		System.out.println("[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(isDecrypted, toEncrypt);
		
		System.out.println();
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeMore() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
		System.out.println(forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encodeMore(toEncrypt);
		System.out.println("[" + encodedFileContent + "]");
		
		Assertions.assertEquals("ctcacgccctatctcaagaactcacgccctatctcaatagagaactcacgccctatctcaatat", encodedFileContent);
		
		String isDecrypted = forFileContent.decode(encodedFileContent, 0, 0);
		System.out.println("[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(isDecrypted, toEncrypt);
		
		System.out.println();
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeRand() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
		System.out.println(forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encodeRand(toEncrypt);
		System.out.println("[" + encodedFileContent + "]");
		
		Assertions.assertEquals("ctcacgccctatctcaagaactcacgccctatctcaatagagaactcacgccctatctcaatat", encodedFileContent);
		
		String isDecrypted = forFileContent.decode(encodedFileContent, 0, 0);
		System.out.println("[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(isDecrypted, toEncrypt);
		
		System.out.println();
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeFile() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
		System.out.println(forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encode(toEncrypt, 0);
		System.out.println("[" + encodedFileContent + "]");
		System.out.println("[" + encodedFileContent.substring(3, encodedFileContent.length()-3) + "]");
		
		// Assertions.assertEquals("tttactcacgccctatctcaagaactcacgccctatctcaatagagaactcacgccctatctcaatattttg", encodedFileContent);
		
		// Assertions.assertEquals("ttgtctcacgccctatctcaagaactcacgccctatctcaatagagaactcacgccctatctcaatattttt", forFileContent.encode(toEncrypt, 0));
		// Assertions.assertEquals("ttgtctcacgccctatctcaagaactcacgccctatctcaatagagaactcacgccctatctcaatattttg", forFileContent.encode(toEncrypt, 1));
		
		// Assertions.assertEquals("tctcacgccctatctcaagaactcacgccctatctcaatagagaactcacgccctatctcaatatt", forFileContent.encode(toEncrypt, 0).substring(3, encodedFileContent.length()-3));
		// Assertions.assertEquals("tctcacgccctatctcaagaactcacgccctatctcaatagagaactcacgccctatctcaatatt", forFileContent.encode(toEncrypt, 1).substring(3, encodedFileContent.length()-3));
				
		// String isDecrypted00 = forFileContent.decode(encodedFileContent.substring(3, encodedFileContent.length()-3), 0, 0);
		String isDecrypted01 = forFileContent.decodeWithStartStopCodons(encodedFileContent, 0, 0);
		System.out.println("[" + isDecrypted01 + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(isDecrypted01, toEncrypt);
		
		System.out.println();
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodePath() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forPathDirName = BiologicalUtils.getGenericCrypto(1);
		System.out.println(forPathDirName.toString());
		String toEncrypt = "/home/user/file.txt";
		String encodedFilePath = forPathDirName.encode(toEncrypt, 1);
		System.out.println("[" + encodedFilePath + "]" + "\n");

		// Assertions.assertEquals("PWCHDEHTIINMTHTRTYCMCNHHICNSPHTMWACPDLHPYY", encodedFilePath);
		
		// Assertions.assertEquals("PWCHDEHTIINMTHTRTYCMCNHHICNSPHTMWACPDLHPYY", forPathDirName.encode("/home/user/file.txt", 0));
		// Assertions.assertEquals("PWCHDEHTIINMTHTRTYCMCNHHICNSPHTMWACPDLHPYY", forPathDirName.encode("/home/user/file.txt", 1));
		
		// String isDecrypted00 = forPathDirName.decode(encodedFilePath.substring(3, encodedFilePath.length()-3), 0, 0);
		String isDecrypted01 = forPathDirName.decodeWithStartStopCodons(encodedFilePath, 0, 0);
		System.out.println("[" + isDecrypted01 + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(isDecrypted01, toEncrypt);
	
		System.out.println();
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

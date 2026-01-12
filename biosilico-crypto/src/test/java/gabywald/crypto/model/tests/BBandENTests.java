package gabywald.crypto.model.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gabywald.crypto.data.BiologicalUtils;
import gabywald.crypto.model.BinaryConversion;
import gabywald.crypto.model.GeneticTranslator;

/**
 * 
 * @author Gabriel Chandesris (2026)
 */
class BBandENTests {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

//	@Test
//	void test() {
//		// TODO make global tests combining EncodingNodes and BinaryBits
//		fail("Not yet implemented");
//	}
	
	@Test
	void testGeneticTranslatorSomeTests() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator gt01REF = new GeneticTranslator(1, true);
		Assertions.assertNotNull( gt01REF );
		System.out.println(gt01REF.toString());
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeSimple() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
		Assertions.assertNotNull( forFileContent );
		System.out.println(forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encode(toEncrypt);
		System.out.println("[" + encodedFileContent + "]");
		
		Assertions.assertEquals("ctcacgccctatctcaagaactcacgccctatctcaatagagaactcacgccctatctcaatat", encodedFileContent);
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		System.out.println("[" + binaryEncodedFileContent + "]");
		
		Assertions.assertEquals("01110100011001010111001101110100001000000111010001100101011100110111010000110010001000000111010001100101011100110111010000110011", binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(binaryEncodedFileContent);
		System.out.println("[" + decodedBinaryEncodedFileContent + "]");
		
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
		System.out.println("[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(isDecrypted, toEncrypt);
		
		System.out.println();
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeMore() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
		Assertions.assertNotNull( forFileContent );
		System.out.println(forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encodeMore(toEncrypt);
		System.out.println("[" + encodedFileContent + "]");
		
		Assertions.assertEquals("ctcacgccctatctcaagaactcacgccctatctcaatagagaactcacgccctatctcaatat", encodedFileContent);
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		System.out.println("[" + binaryEncodedFileContent + "]");
		
		Assertions.assertEquals("01110100011001010111001101110100001000000111010001100101011100110111010000110010001000000111010001100101011100110111010000110011", binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(binaryEncodedFileContent);
		System.out.println("[" + decodedBinaryEncodedFileContent + "]");
		
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
		System.out.println("[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(isDecrypted, toEncrypt);
		
		System.out.println();
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeRand() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
		Assertions.assertNotNull( forFileContent );
		System.out.println(forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encodeRand(toEncrypt);
		System.out.println("[" + encodedFileContent + "]");
		
		Assertions.assertEquals("ctcacgccctatctcaagaactcacgccctatctcaatagagaactcacgccctatctcaatat", encodedFileContent);
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		System.out.println("[" + binaryEncodedFileContent + "]");
		
		Assertions.assertEquals("01110100011001010111001101110100001000000111010001100101011100110111010000110010001000000111010001100101011100110111010000110011", binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(binaryEncodedFileContent);
		System.out.println("[" + decodedBinaryEncodedFileContent + "]");
		
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
		System.out.println("[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(isDecrypted, toEncrypt);
		
		System.out.println();
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeFile() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
		Assertions.assertNotNull( forFileContent );
		System.out.println(forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encode(toEncrypt, 0); // NOTE second argument here !! (multiple starts / stops are possibles)
		System.out.println("[" + encodedFileContent + "]");
		System.out.println("[" + encodedFileContent.substring(3, encodedFileContent.length()-3) + "]");
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		System.out.println("[" + binaryEncodedFileContent + "]");
		// Assertions.assertEquals("111110010111010001100101011100110111010000100000011101000110010101110011011101000011001000100000011101000110010101110011011101000011001111111111", binaryEncodedFileContent);
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(binaryEncodedFileContent);
		System.out.println("[" + decodedBinaryEncodedFileContent + "]");
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decodeWithStartStopCodons(decodedBinaryEncodedFileContent, 0, 0); // Use specific decoder !!
		System.out.println("[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(isDecrypted, toEncrypt);
		
		System.out.println();
	}
	
}

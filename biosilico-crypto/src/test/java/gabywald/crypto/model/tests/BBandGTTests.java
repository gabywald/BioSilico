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
class BBandGTTests {

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
		// System.out.println(forFileContent.toString());
		
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
	void testGeneticTranslatorMoreTestsEncodeSimple2txtCrypto00() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
		Assertions.assertNotNull( forFileContent );
		// System.out.println(forFileContent.toString());
		
		String toEncrypt = "test1 test2 test3";
		String encodedFileContent = forFileContent.encode(toEncrypt);
		System.out.println("encodedFileContent: [" + encodedFileContent + "]");
		
		Assertions.assertEquals("ctcacgccctatctcaatacagaactcacgccctatctcaatagagaactcacgccctatctcaatat", encodedFileContent);
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		System.out.println("binaryEncodedFileContent: [" + binaryEncodedFileContent + "]");
		
		Assertions.assertEquals("0111010001100101011100110111010000110001001000000111010001100101011100110111010000110010001000000111010001100101011100110111010000110011", binaryEncodedFileContent);
		
		System.out.println( "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		System.out.println("binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent00 + "]");
		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		System.out.println("binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent01 + "]");
		// NOTE : 'border effect here due to similarity and simple use of "ASCII" : change if alphabet is different !!
		Assertions.assertEquals("test1 test2 test3", binary2txtEncodedFileContent01); // ??
		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
		System.out.println("txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(binaryEncodedFileContent);
		System.out.println("decodedBinaryEncodedFileContent: [" + decodedBinaryEncodedFileContent + "]");
		
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
		System.out.println("[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(isDecrypted, toEncrypt);
		
		System.out.println();
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeSimple2txtCrypto20() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(2); // NOTE '2' here !!
		Assertions.assertNotNull( forFileContent );
		// System.out.println(forFileContent.toString());
		
		String toEncrypt = "test1 test2 test3";
		String encodedFileContent = forFileContent.encode(toEncrypt);
		System.out.println("encodedFileContent: [" + encodedFileContent + "]");
		
		Assertions.assertEquals("cgctctcgcggacgctatgaagaacgctctcgcggacgctatctagaacgctctcgcggacgctatcg", encodedFileContent);
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		System.out.println("binaryEncodedFileContent: [" + binaryEncodedFileContent + "]");
		
		Assertions.assertEquals("0110011101110110011010000110011100111000001000000110011101110110011010000110011100110111001000000110011101110110011010000110011100110110", binaryEncodedFileContent);
		
		System.out.println( "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		System.out.println("binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent00 + "]");
		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		System.out.println("binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent01 + "]");
		// NOTE : 'border effect here due to similarity and simple use of "ASCII" : change if alphabet is different !!
		Assertions.assertEquals("gvhg8 gvhg7 gvhg6", binary2txtEncodedFileContent01); // ??
		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
		System.out.println("txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(binaryEncodedFileContent);
		System.out.println("decodedBinaryEncodedFileContent: [" + decodedBinaryEncodedFileContent + "]");
		
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
		System.out.println("[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(isDecrypted, toEncrypt);
		
		System.out.println();
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeSimple2txtCrypto30() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(3); // NOTE '3' here !!
		Assertions.assertNotNull( forFileContent );
		// System.out.println(forFileContent.toString());
		
		String toEncrypt = "test1 test2 test3";
		String encodedFileContent = forFileContent.encode(toEncrypt);
		System.out.println("encodedFileContent: [" + encodedFileContent + "]");
		
		Assertions.assertEquals("tgaatgtttgactgaagtacgcgctgaatgtttgactgaagtaagcgctgaatgtttgactgaaggtt", encodedFileContent);
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		System.out.println("binaryEncodedFileContent: [" + binaryEncodedFileContent + "]");
		
		Assertions.assertEquals("1110000011101111111000011110000010110001100110011110000011101111111000011110000010110000100110011110000011101111111000011110000010101111", binaryEncodedFileContent);
		
		System.out.println( "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		System.out.println("binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent00 + "]");
		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		System.out.println("binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent01 + "]");
		Assertions.assertEquals("àïáà±àïáà°àïáà¯", binary2txtEncodedFileContent01); // ??
		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
		System.out.println("txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(binaryEncodedFileContent);
		System.out.println("decodedBinaryEncodedFileContent: [" + decodedBinaryEncodedFileContent + "]");
		
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
		System.out.println("[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(isDecrypted, toEncrypt);
		
		System.out.println();
	}
	
	// TODO study further and resolve these test (mis-retro-translation ?)
//	@Test
//	void testGeneticTranslatorMoreTestsEncodeSimple2txtCrypto01() {
//		// TODO 'DP builder' for Genetic Translator
//		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
//		Assertions.assertNotNull( forFileContent );
//		// System.out.println(forFileContent.toString());
//		
//		String toEncrypt = "test1 test2 test3";
//		String encodedFileContent = forFileContent.encode(toEncrypt);
//		System.out.println("encodedFileContent: [" + encodedFileContent + "]");
//		
//		Assertions.assertEquals("ctcacgccctatctcaatacagaactcacgccctatctcaatagagaactcacgccctatctcaatat", encodedFileContent);
//		
//		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent, 1);
//		System.out.println("binaryEncodedFileContent: [" + binaryEncodedFileContent + "]");
//		Assertions.assertEquals("10100110111010100110100101011001110101101001101110101001101001010111011101011010011011101010011010010101", binaryEncodedFileContent);
//		System.out.println( "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
//		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
//		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
//		System.out.println("binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent00 + "]");
//		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
//		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
//		System.out.println("binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent01 + "]");
//		Assertions.assertEquals("¦êiYÖ©¥wZn¦", binary2txtEncodedFileContent01); // ??
//		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
//		System.out.println("txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
//		Assertions.assertEquals(binaryEncodedFileContent.length(), txt2binaryEncodedFileContent.length());
//		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
//		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(binaryEncodedFileContent, 1);
//		System.out.println("decodedBinaryEncodedFileContent: [" + decodedBinaryEncodedFileContent + "]");
//		
//		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
//		
//		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
//		System.out.println("[" + isDecrypted + "] <= [" + toEncrypt + "]");
//		Assertions.assertEquals(isDecrypted, toEncrypt);
//		
//		System.out.println();
//	}
	
	// TODO study further and resolve these test (length of binary not multiple of 8)
//	@Test
//	void testGeneticTranslatorMoreTestsEncodeSimple2txtCrypto21() {
//		// TODO 'DP builder' for Genetic Translator
//		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(2); // NOTE '2' here !!
//		Assertions.assertNotNull( forFileContent );
//		// System.out.println(forFileContent.toString());
//		
//		String toEncrypt = "test1 test2 test3";
//		String encodedFileContent = forFileContent.encode(toEncrypt);
//		System.out.println("encodedFileContent: [" + encodedFileContent + "]");
//		
//		Assertions.assertEquals("cgctctcgcggacgctatgaagaacgctctcgcggacgctatctagaacgctctcgcggacgctatcg", encodedFileContent);
//		
//		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent, 1);
//		System.out.println("binaryEncodedFileContent: [" + binaryEncodedFileContent + "]");
//		Assertions.assertEquals("10111010101110111101101110011101011101011011101010111011110110111001100111010110111010101110111101101110011011", binaryEncodedFileContent);
//		System.out.println( "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
//		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
//		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
//		System.out.println("binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent00 + "]");
//		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
//		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
//		System.out.println("binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent01 + "]");
//		Assertions.assertEquals("º»Ûuº»ÛÖêïn", binary2txtEncodedFileContent01); // ??
//		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
//		System.out.println("txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
//		Assertions.assertEquals(binaryEncodedFileContent.length(), txt2binaryEncodedFileContent.length());
//		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
//		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(binaryEncodedFileContent, 1);
//		System.out.println("decodedBinaryEncodedFileContent: [" + decodedBinaryEncodedFileContent + "]");
//		
//		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
//		
//		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
//		System.out.println("[" + isDecrypted + "] <= [" + toEncrypt + "]");
//		Assertions.assertEquals(isDecrypted, toEncrypt);
//		
//		System.out.println();
//	}
	
	// TODO study further and resolve these test (length of binary not multiple of 8)
//	@Test
//	void testGeneticTranslatorMoreTestsEncodeSimple2txtCrypto31() {
//		// TODO 'DP builder' for Genetic Translator
//		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(3); // NOTE '3' here !!
//		Assertions.assertNotNull( forFileContent );
//		// System.out.println(forFileContent.toString());
//		
//		String toEncrypt = "test1 test2 test3";
//		String encodedFileContent = forFileContent.encode(toEncrypt);
//		System.out.println("encodedFileContent: [" + encodedFileContent + "]");
//		
//		Assertions.assertEquals("tgaatgtttgactgaagtacgcgctgaatgtttgactgaagtaagcgctgaatgtttgactgaaggtt", encodedFileContent);
//		
//		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent, 1);
//		System.out.println("binaryEncodedFileContent: [" + binaryEncodedFileContent + "]");
//		Assertions.assertEquals("11010111110110110101110110111011101101011111011011010111010111101110110101111101101101011111", binaryEncodedFileContent);
//		System.out.println( "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
//		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
//		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
//		System.out.println("binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent00 + "]");
//		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
//		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
//		System.out.println("binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent01 + "]");
//		// NOTE : 'border effect here due to similarity and simple use of "ASCII" : change if alphabet is different !!
//		Assertions.assertEquals("×Û]»µö×^í}µ", binary2txtEncodedFileContent01); // ??
//		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
//		System.out.println("txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
//		Assertions.assertEquals(binaryEncodedFileContent.length(), txt2binaryEncodedFileContent.length());
//		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
//		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(binaryEncodedFileContent, 1);
//		System.out.println("decodedBinaryEncodedFileContent: [" + decodedBinaryEncodedFileContent + "]");
//		
//		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
//		
//		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
//		System.out.println("[" + isDecrypted + "] <= [" + toEncrypt + "]");
//		Assertions.assertEquals(isDecrypted, toEncrypt);
//		
//		System.out.println();
//	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeMore() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
		Assertions.assertNotNull( forFileContent );
		// System.out.println(forFileContent.toString());
		
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
	void testGeneticTranslatorMoreTestsEncodeMore2txtCrypto0() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
		Assertions.assertNotNull( forFileContent );
		// System.out.println(forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encodeMore(toEncrypt);
		System.out.println("[" + encodedFileContent + "]");
		
		Assertions.assertEquals("ctcacgccctatctcaagaactcacgccctatctcaatagagaactcacgccctatctcaatat", encodedFileContent);
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		System.out.println("[" + binaryEncodedFileContent + "]");
		
		Assertions.assertEquals("01110100011001010111001101110100001000000111010001100101011100110111010000110010001000000111010001100101011100110111010000110011", binaryEncodedFileContent);
		
		System.out.println( "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		System.out.println("binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent00 + "]");
		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		System.out.println("binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent01 + "]");
		// NOTE : 'border effect here due to similarity and simple use of "ASCII" : change if alphabet is different !!
		Assertions.assertEquals("test test2 test3", binary2txtEncodedFileContent01); // ??
		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
		System.out.println("txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(txt2binaryEncodedFileContent);
		System.out.println("[" + decodedBinaryEncodedFileContent + "]");
		
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
		System.out.println("[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(isDecrypted, toEncrypt);
		
		System.out.println();
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeMore2txtCrypto2() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(2);
		Assertions.assertNotNull( forFileContent );
		// System.out.println(forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encodeMore(toEncrypt);
		System.out.println("[" + encodedFileContent + "]");
		
		Assertions.assertEquals("cgctctcgcggacgctagaacgctctcgcggacgctatctagaacgctctcgcggacgctatcg", encodedFileContent);
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		System.out.println("[" + binaryEncodedFileContent + "]");
		
		Assertions.assertEquals("01100111011101100110100001100111001000000110011101110110011010000110011100110111001000000110011101110110011010000110011100110110", binaryEncodedFileContent);
		
		System.out.println( "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		System.out.println("binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent00 + "]");
		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		System.out.println("binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent01 + "]");
		// NOTE : 'border effect here due to similarity and simple use of "ASCII" : change if alphabet is different !!
		Assertions.assertEquals("gvhg gvhg7 gvhg6", binary2txtEncodedFileContent01); // ??
		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
		System.out.println("txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(txt2binaryEncodedFileContent);
		System.out.println("[" + decodedBinaryEncodedFileContent + "]");
		
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
		System.out.println("[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(isDecrypted, toEncrypt);
		
		System.out.println();
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeMore2txtCrypto3() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(3);
		Assertions.assertNotNull( forFileContent );
		// System.out.println(forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encodeMore(toEncrypt);
		System.out.println("[" + encodedFileContent + "]");
		
		Assertions.assertEquals("tgaatgtttgactgaagcgctgaatgtttgactgaagtaagcgctgaatgtttgactgaaggtt", encodedFileContent);
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		System.out.println("[" + binaryEncodedFileContent + "]");
		
		Assertions.assertEquals("11100000111011111110000111100000100110011110000011101111111000011110000010110000100110011110000011101111111000011110000010101111", binaryEncodedFileContent);
		
		System.out.println( "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		System.out.println("binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent00 + "]");
		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		System.out.println("binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent01 + "]");
		// NOTE : 'border effect here due to similarity and simple use of "ASCII" : change if alphabet is different !!
		Assertions.assertEquals("àïáààïáà°àïáà¯", binary2txtEncodedFileContent01); // ??
		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
		System.out.println("txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(txt2binaryEncodedFileContent);
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
		// System.out.println(forFileContent.toString());
		
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
	void testGeneticTranslatorMoreTestsEncodeRand2txtCrypto0() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
		Assertions.assertNotNull( forFileContent );
		// System.out.println(forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encodeRand(toEncrypt);
		System.out.println("[" + encodedFileContent + "]");
		
		Assertions.assertEquals("ctcacgccctatctcaagaactcacgccctatctcaatagagaactcacgccctatctcaatat", encodedFileContent);
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		System.out.println("[" + binaryEncodedFileContent + "]");
		
		Assertions.assertEquals("01110100011001010111001101110100001000000111010001100101011100110111010000110010001000000111010001100101011100110111010000110011", binaryEncodedFileContent);
		
		System.out.println( "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		System.out.println("binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent00 + "]");
		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		System.out.println("binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent01 + "]");
		// NOTE : 'border effect here due to similarity and simple use of "ASCII" : change if alphabet is different !!
		Assertions.assertEquals("test test2 test3", binary2txtEncodedFileContent01); // ??
		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
		System.out.println("txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(txt2binaryEncodedFileContent);
		System.out.println("[" + decodedBinaryEncodedFileContent + "]");
		
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
		System.out.println("[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(isDecrypted, toEncrypt);
		
		System.out.println();
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeRand2txtCrypto2() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(2);
		Assertions.assertNotNull( forFileContent );
		// System.out.println(forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encodeRand(toEncrypt);
		System.out.println("[" + encodedFileContent + "]");
		
		Assertions.assertEquals("cgctctcgcggacgctagaacgctctcgcggacgctatctagaacgctctcgcggacgctatcg", encodedFileContent);
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		System.out.println("[" + binaryEncodedFileContent + "]");
		
		Assertions.assertEquals("01100111011101100110100001100111001000000110011101110110011010000110011100110111001000000110011101110110011010000110011100110110", binaryEncodedFileContent);
		
		System.out.println( "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		System.out.println("binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent00 + "]");
		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		System.out.println("binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent01 + "]");
		// NOTE : 'border effect here due to similarity and simple use of "ASCII" : change if alphabet is different !!
		Assertions.assertEquals("gvhg gvhg7 gvhg6", binary2txtEncodedFileContent01); // ??
		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
		System.out.println("txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(txt2binaryEncodedFileContent);
		System.out.println("[" + decodedBinaryEncodedFileContent + "]");
		
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
		System.out.println("[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(isDecrypted, toEncrypt);
		
		System.out.println();
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeRand2txtCrypto3() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(3);
		Assertions.assertNotNull( forFileContent );
		// System.out.println(forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encodeRand(toEncrypt);
		System.out.println("[" + encodedFileContent + "]");
		
		Assertions.assertEquals("tgaatgtttgactgaagcgctgaatgtttgactgaagtaagcgctgaatgtttgactgaaggtt", encodedFileContent);
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		System.out.println("[" + binaryEncodedFileContent + "]");
		
		Assertions.assertEquals("11100000111011111110000111100000100110011110000011101111111000011110000010110000100110011110000011101111111000011110000010101111", binaryEncodedFileContent);
		
		System.out.println( "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		System.out.println("binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent00 + "]");
		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		System.out.println("binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent01 + "]");
		// NOTE : 'border effect here due to similarity and simple use of "ASCII" : change if alphabet is different !!
		Assertions.assertEquals("àïáààïáà°àïáà¯", binary2txtEncodedFileContent01); // ??
		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
		System.out.println("txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(txt2binaryEncodedFileContent);
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
		// System.out.println(forFileContent.toString());
		
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
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeFile2txtCrypto0() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
		Assertions.assertNotNull( forFileContent );
		// System.out.println(forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encode(toEncrypt, 0); // NOTE second argument here !! (multiple starts / stops are possibles)
		System.out.println("[" + encodedFileContent + "]");
		System.out.println("[" + encodedFileContent.substring(3, encodedFileContent.length()-3) + "]");
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		System.out.println("[" + binaryEncodedFileContent + "]");
		// Assertions.assertEquals("01110100011001010111001101110100001000000111010001100101011100110111010000110010001000000111010001100101011100110111010000110011", binaryEncodedFileContent);
		System.out.println( "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		System.out.println("binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent00 + "]");
		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		System.out.println("binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent01 + "]");
		// NOTE : 'border effect here due to similarity and simple use of "ASCII" : change if alphabet is different !!
		Assertions.assertEquals("test test2 test3", binary2txtEncodedFileContent01.substring(1, binary2txtEncodedFileContent01.length() - 1)); // ??
		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
		System.out.println("txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(txt2binaryEncodedFileContent);
		System.out.println("[" + decodedBinaryEncodedFileContent + "]");
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decodeWithStartStopCodons(decodedBinaryEncodedFileContent, 0, 0); // Use specific decoder !!
		System.out.println("[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(isDecrypted, toEncrypt);
		
		System.out.println();
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeFile2txtCrypto2() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(2);
		Assertions.assertNotNull( forFileContent );
		// System.out.println(forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encode(toEncrypt, 0); // NOTE second argument here !! (multiple starts / stops are possibles)
		System.out.println("[" + encodedFileContent + "]");
		System.out.println("[" + encodedFileContent.substring(3, encodedFileContent.length()-3) + "]");
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		System.out.println("[" + binaryEncodedFileContent + "]");
		// Assertions.assertEquals("01110100011001010111001101110100001000000111010001100101011100110111010000110010001000000111010001100101011100110111010000110011", binaryEncodedFileContent);
		System.out.println( "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		System.out.println("binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent00 + "]");
		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		System.out.println("binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent01 + "]");
		// NOTE : 'border effect here due to similarity and simple use of "ASCII" : change if alphabet is different !!
		Assertions.assertEquals("gvhg gvhg7 gvhg6", binary2txtEncodedFileContent01.substring(1, binary2txtEncodedFileContent01.length() - 1)); // ??
		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
		System.out.println("txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(txt2binaryEncodedFileContent);
		System.out.println("[" + decodedBinaryEncodedFileContent + "]");
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decodeWithStartStopCodons(decodedBinaryEncodedFileContent, 0, 0); // Use specific decoder !!
		System.out.println("[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(isDecrypted, toEncrypt);
		
		System.out.println();
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeFile2txtCrypto3() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(3);
		Assertions.assertNotNull( forFileContent );
		// System.out.println(forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encode(toEncrypt, 0); // NOTE second argument here !! (multiple starts / stops are possibles)
		System.out.println("[" + encodedFileContent + "]");
		System.out.println("[" + encodedFileContent.substring(3, encodedFileContent.length()-3) + "]");
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		System.out.println("[" + binaryEncodedFileContent + "]");
		// Assertions.assertEquals("01110100011001010111001101110100001000000111010001100101011100110111010000110010001000000111010001100101011100110111010000110011", binaryEncodedFileContent);
		System.out.println( "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		System.out.println("binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent00 + "]");
		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		System.out.println("binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent01 + "]");
		// NOTE : 'border effect here due to similarity and simple use of "ASCII" : change if alphabet is different !!
		Assertions.assertEquals("àïáààïáà°àïáà¯", binary2txtEncodedFileContent01.substring(1, binary2txtEncodedFileContent01.length() - 1)); // ??
		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
		System.out.println("txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(txt2binaryEncodedFileContent);
		System.out.println("[" + decodedBinaryEncodedFileContent + "]");
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decodeWithStartStopCodons(decodedBinaryEncodedFileContent, 0, 0); // Use specific decoder !!
		System.out.println("[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(isDecrypted, toEncrypt);
		
		System.out.println();
	}
	
}

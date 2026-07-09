package gabywald.crypto.model.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gabywald.crypto.data.BiologicalUtils;
import gabywald.crypto.model.ITranslator;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;
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
		Logger.printlnLog(LoggerLevel.LL_NONE, gt01REF.toString());
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeSimple() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
		Assertions.assertNotNull( forFileContent );
		// Logger.printlnLog(LoggerLevel.LL_NONE, forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encode(toEncrypt);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent.length()+ "] ; [" + toEncrypt.length() + "]");
		Assertions.assertEquals(toEncrypt.length() * 4, encodedFileContent.length()); // because 4-uplets
		Assertions.assertEquals("ctcacgccctatctcaagaactcacgccctatctcaatagagaactcacgccctatctcaatat", encodedFileContent);
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent.length()+ "] ; [" + encodedFileContent.length() + "]");
		Assertions.assertEquals(encodedFileContent.length() * 2, binaryEncodedFileContent.length());
		Assertions.assertEquals("01110100011001010111001101110100001000000111010001100101011100110111010000110010001000000111010001100101011100110111010000110011", binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(binaryEncodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + decodedBinaryEncodedFileContent + "]");
		
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(toEncrypt, isDecrypted);
		
		Logger.printlnLog(LoggerLevel.LL_NONE, "" );
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeSimple2txtCrypto00() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
		Assertions.assertNotNull( forFileContent );
		// Logger.printlnLog(LoggerLevel.LL_NONE, forFileContent.toString());
		
		String toEncrypt = "test1 test2 test3";
		String encodedFileContent = forFileContent.encode(toEncrypt);
		Logger.printlnLog(LoggerLevel.LL_NONE, "encodedFileContent: [" + encodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent.length()+ "] ; [" + toEncrypt.length() + "]");
		Assertions.assertEquals(toEncrypt.length() * 4, encodedFileContent.length()); // because 4-uplets
		Assertions.assertEquals("ctcacgccctatctcaatacagaactcacgccctatctcaatagagaactcacgccctatctcaatat", encodedFileContent);
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "binaryEncodedFileContent: [" + binaryEncodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent.length()+ "] ; [" + encodedFileContent.length() + "]");
		Assertions.assertEquals(encodedFileContent.length() * 2, binaryEncodedFileContent.length());
		Assertions.assertEquals("0111010001100101011100110111010000110001001000000111010001100101011100110111010000110010001000000111010001100101011100110111010000110011", binaryEncodedFileContent);
		
		Logger.printlnLog(LoggerLevel.LL_NONE,  "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent00 + "]");
		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent01 + "]");
		// NOTE : 'border effect here due to similarity and simple use of "ASCII" : change if alphabet is different !!
		Assertions.assertEquals("test1 test2 test3", binary2txtEncodedFileContent01); // ??
		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
		Logger.printlnLog(LoggerLevel.LL_NONE, "txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(binaryEncodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "decodedBinaryEncodedFileContent: [" + decodedBinaryEncodedFileContent + "]");
		
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(toEncrypt, isDecrypted);
		
		Logger.printlnLog(LoggerLevel.LL_NONE, "" );
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeSimple2txtCrypto20() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(2); // NOTE '2' here !!
		Assertions.assertNotNull( forFileContent );
		// Logger.printlnLog(LoggerLevel.LL_NONE, forFileContent.toString());
		
		String toEncrypt = "test1 test2 test3";
		String encodedFileContent = forFileContent.encode(toEncrypt);
		Logger.printlnLog(LoggerLevel.LL_NONE, "encodedFileContent: [" + encodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent.length()+ "] ; [" + toEncrypt.length() + "]");
		Assertions.assertEquals(toEncrypt.length() * 4, encodedFileContent.length()); // because 4-uplets
		Assertions.assertEquals("cgctctcgcggacgctatgaagaacgctctcgcggacgctatctagaacgctctcgcggacgctatcg", encodedFileContent);
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "binaryEncodedFileContent: [" + binaryEncodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent.length()+ "] ; [" + encodedFileContent.length() + "]");
		Assertions.assertEquals(encodedFileContent.length() * 2, binaryEncodedFileContent.length());
		Assertions.assertEquals("0110011101110110011010000110011100111000001000000110011101110110011010000110011100110111001000000110011101110110011010000110011100110110", binaryEncodedFileContent);
		
		Logger.printlnLog(LoggerLevel.LL_NONE,  "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent00 + "]");
		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent01 + "]");
		// NOTE : 'border effect here due to similarity and simple use of "ASCII" : change if alphabet is different !!
		Assertions.assertEquals("gvhg8 gvhg7 gvhg6", binary2txtEncodedFileContent01); // ??
		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
		Logger.printlnLog(LoggerLevel.LL_NONE, "txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(binaryEncodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "decodedBinaryEncodedFileContent: [" + decodedBinaryEncodedFileContent + "]");
		
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(toEncrypt, isDecrypted);
		
		Logger.printlnLog(LoggerLevel.LL_NONE, "" );
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeSimple2txtCrypto30() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(3); // NOTE '3' here !!
		Assertions.assertNotNull( forFileContent );
		// Logger.printlnLog(LoggerLevel.LL_NONE, forFileContent.toString());
		
		String toEncrypt = "test1 test2 test3";
		String encodedFileContent = forFileContent.encode(toEncrypt);
		Logger.printlnLog(LoggerLevel.LL_NONE, "encodedFileContent: [" + encodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent.length()+ "] ; [" + toEncrypt.length() + "]");
		Assertions.assertEquals(toEncrypt.length() * 4, encodedFileContent.length()); // because 4-uplets
		Assertions.assertEquals("tgaatgtttgactgaagtacgcgctgaatgtttgactgaagtaagcgctgaatgtttgactgaaggtt", encodedFileContent);
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "binaryEncodedFileContent: [" + binaryEncodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent.length()+ "] ; [" + encodedFileContent.length() + "]");
		Assertions.assertEquals(encodedFileContent.length() * 2, binaryEncodedFileContent.length());
		Assertions.assertEquals("1110000011101111111000011110000010110001100110011110000011101111111000011110000010110000100110011110000011101111111000011110000010101111", binaryEncodedFileContent);
		
		Logger.printlnLog(LoggerLevel.LL_NONE,  "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent00 + "]");
		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent01 + "]");
		Assertions.assertEquals("àïáà±àïáà°àïáà¯", binary2txtEncodedFileContent01); // ??
		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
		Logger.printlnLog(LoggerLevel.LL_NONE, "txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(binaryEncodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "decodedBinaryEncodedFileContent: [" + decodedBinaryEncodedFileContent + "]");
		
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(toEncrypt, isDecrypted);
		
		Logger.printlnLog(LoggerLevel.LL_NONE, "" );
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeSimple2txtCrypto01() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
		Assertions.assertNotNull( forFileContent );
		// Logger.printlnLog(LoggerLevel.LL_NONE, forFileContent.toString());
		
		String toEncrypt = "test1 test2 test3";
		String encodedFileContent = forFileContent.encode(toEncrypt);
		Logger.printlnLog(LoggerLevel.LL_NONE, "encodedFileContent: [" + encodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent.length()+ "] ; [" + toEncrypt.length() + "]");
		Assertions.assertEquals(toEncrypt.length() * 4, encodedFileContent.length()); // because 4-uplets
		Assertions.assertEquals("ctcacgccctatctcaatacagaactcacgccctatctcaatagagaactcacgccctatctcaatat", encodedFileContent);
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent, 1);
		Logger.printlnLog(LoggerLevel.LL_NONE, "binaryEncodedFileContent: [" + binaryEncodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent.length()+ "] ; [" + encodedFileContent.length() + "]");
		Assertions.assertEquals(encodedFileContent.length() * 2, binaryEncodedFileContent.length());
		Assertions.assertEquals("1000100110111010100001001000100101000110011101011000100110111010100001001000100101000111011101011000100110111010100001001000100101000100", binaryEncodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE,  "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent00 + "]");
		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent01 + "]");
		Assertions.assertEquals("ºFuºGuºD", binary2txtEncodedFileContent01); // ??
		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
		Logger.printlnLog(LoggerLevel.LL_NONE, "txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE,  "prettyBinary begin: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
		Logger.printlnLog(LoggerLevel.LL_NONE,  "prettyBinary end--: [" + BinaryConversion.prettyBinary(txt2binaryEncodedFileContent, 8, " ") + "]");
		Assertions.assertEquals(binaryEncodedFileContent.length(), txt2binaryEncodedFileContent.length());
		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(binaryEncodedFileContent, 3);
		Logger.printlnLog(LoggerLevel.LL_NONE, "decodedBinaryEncodedFileContent: [" + decodedBinaryEncodedFileContent + "]");
		
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(toEncrypt, isDecrypted);
		
		Logger.printlnLog(LoggerLevel.LL_NONE, "" );
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeSimple2txtCrypto21() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(2); // NOTE '2' here !!
		Assertions.assertNotNull( forFileContent );
		// Logger.printlnLog(LoggerLevel.LL_NONE, forFileContent.toString());
		
		String toEncrypt = "test1 test2 test3";
		String encodedFileContent = forFileContent.encode(toEncrypt);
		Logger.printlnLog(LoggerLevel.LL_NONE, "encodedFileContent: [" + encodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent.length()+ "] ; [" + toEncrypt.length() + "]");
		Assertions.assertEquals(toEncrypt.length() * 4, encodedFileContent.length()); // because 4-uplets
		Assertions.assertEquals("cgctctcgcggacgctatgaagaacgctctcgcggacgctatctagaacgctctcgcggacgctatcg", encodedFileContent);
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent, 1);
		Logger.printlnLog(LoggerLevel.LL_NONE, "binaryEncodedFileContent: [" + binaryEncodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent.length()+ "] ; [" + encodedFileContent.length() + "]");
		Assertions.assertEquals(encodedFileContent.length() * 2, binaryEncodedFileContent.length());
		Assertions.assertEquals("1011100010001011101111011011100001001101011101011011100010001011101111011011100001001000011101011011100010001011101111011011100001001011", binaryEncodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE,  "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent00 + "]");
		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent01 + "]");
		Assertions.assertEquals("¸½¸Mu¸½¸Hu¸½¸K", binary2txtEncodedFileContent01); // ??
		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
		Logger.printlnLog(LoggerLevel.LL_NONE, "txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
		Assertions.assertEquals(binaryEncodedFileContent.length(), txt2binaryEncodedFileContent.length());
		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(binaryEncodedFileContent, 3);
		Logger.printlnLog(LoggerLevel.LL_NONE, "decodedBinaryEncodedFileContent: [" + decodedBinaryEncodedFileContent + "]");
		
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(toEncrypt, isDecrypted);
		
		Logger.printlnLog(LoggerLevel.LL_NONE, "" );
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeSimple2txtCrypto31() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(3); // NOTE '3' here !!
		Assertions.assertNotNull( forFileContent );
		// Logger.printlnLog(LoggerLevel.LL_NONE, forFileContent.toString());
		
		String toEncrypt = "test1 test2 test3";
		String encodedFileContent = forFileContent.encode(toEncrypt);
		Logger.printlnLog(LoggerLevel.LL_NONE, "encodedFileContent: [" + encodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent.length()+ "] ; [" + toEncrypt.length() + "]");
		Assertions.assertEquals(toEncrypt.length() * 4, encodedFileContent.length()); // because 4-uplets
		Assertions.assertEquals("tgaatgtttgactgaagtacgcgctgaatgtttgactgaagtaagcgctgaatgtttgactgaaggtt", encodedFileContent);
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent, 1);
		Logger.printlnLog(LoggerLevel.LL_NONE, "binaryEncodedFileContent: [" + binaryEncodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent.length()+ "] ; [" + encodedFileContent.length() + "]");
		Assertions.assertEquals(encodedFileContent.length() * 2, binaryEncodedFileContent.length());
		Assertions.assertEquals("0011010100110000001101100011010111000110111011100011010100110000001101100011010111000101111011100011010100110000001101100011010111110000", binaryEncodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE,  "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent00 + "]");
		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent01 + "]");
		// NOTE : 'border effect here due to similarity and simple use of "ASCII" : change if alphabet is different !!
		Assertions.assertEquals("5065Æî5065Åî5065ð", binary2txtEncodedFileContent01); // ??
		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
		Logger.printlnLog(LoggerLevel.LL_NONE, "txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
		Assertions.assertEquals(binaryEncodedFileContent.length(), txt2binaryEncodedFileContent.length());
		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(binaryEncodedFileContent, 3);
		Logger.printlnLog(LoggerLevel.LL_NONE, "decodedBinaryEncodedFileContent: [" + decodedBinaryEncodedFileContent + "]");
		
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(toEncrypt, isDecrypted);
		
		Logger.printlnLog(LoggerLevel.LL_NONE, "" );
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeMore() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
		Assertions.assertNotNull( forFileContent );
		// Logger.printlnLog(LoggerLevel.LL_NONE, forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encodeMore(toEncrypt);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent.length()+ "] ; [" + toEncrypt.length() + "]");
		Assertions.assertEquals(toEncrypt.length() * 4, encodedFileContent.length()); // because 4-uplets
		Assertions.assertEquals("ctcacgccctatctcaagaactcacgccctatctcaatagagaactcacgccctatctcaatat", encodedFileContent);
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent.length()+ "] ; [" + encodedFileContent.length() + "]");
		Assertions.assertEquals(encodedFileContent.length() * 2, binaryEncodedFileContent.length());
		Assertions.assertEquals("01110100011001010111001101110100001000000111010001100101011100110111010000110010001000000111010001100101011100110111010000110011", binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(binaryEncodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + decodedBinaryEncodedFileContent + "]");
		
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(toEncrypt, isDecrypted);
		
		Logger.printlnLog(LoggerLevel.LL_NONE, "" );
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeMore2txtCrypto0() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
		Assertions.assertNotNull( forFileContent );
		// Logger.printlnLog(LoggerLevel.LL_NONE, forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encodeMore(toEncrypt);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent.length()+ "] ; [" + toEncrypt.length() + "]");
		Assertions.assertEquals(toEncrypt.length() * 4, encodedFileContent.length()); // because 4-uplets
		Assertions.assertEquals("ctcacgccctatctcaagaactcacgccctatctcaatagagaactcacgccctatctcaatat", encodedFileContent);
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent.length()+ "] ; [" + encodedFileContent.length() + "]");
		Assertions.assertEquals(encodedFileContent.length() * 2, binaryEncodedFileContent.length());
		Assertions.assertEquals("01110100011001010111001101110100001000000111010001100101011100110111010000110010001000000111010001100101011100110111010000110011", binaryEncodedFileContent);
		
		Logger.printlnLog(LoggerLevel.LL_NONE,  "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent00 + "]");
		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent01 + "]");
		// NOTE : 'border effect here due to similarity and simple use of "ASCII" : change if alphabet is different !!
		Assertions.assertEquals("test test2 test3", binary2txtEncodedFileContent01); // ??
		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
		Logger.printlnLog(LoggerLevel.LL_NONE, "txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(txt2binaryEncodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + decodedBinaryEncodedFileContent + "]");
		
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(toEncrypt, isDecrypted);
		
		Logger.printlnLog(LoggerLevel.LL_NONE, "" );
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeMore2txtCrypto2() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(2);
		Assertions.assertNotNull( forFileContent );
		// Logger.printlnLog(LoggerLevel.LL_NONE, forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encodeMore(toEncrypt);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent.length()+ "] ; [" + toEncrypt.length() + "]");
		Assertions.assertEquals(toEncrypt.length() * 4, encodedFileContent.length()); // because 4-uplets
		Assertions.assertEquals("cgctctcgcggacgctagaacgctctcgcggacgctatctagaacgctctcgcggacgctatcg", encodedFileContent);
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent.length()+ "] ; [" + encodedFileContent.length() + "]");
		Assertions.assertEquals(encodedFileContent.length() * 2, binaryEncodedFileContent.length());
		Assertions.assertEquals("01100111011101100110100001100111001000000110011101110110011010000110011100110111001000000110011101110110011010000110011100110110", binaryEncodedFileContent);
		
		Logger.printlnLog(LoggerLevel.LL_NONE,  "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent00 + "]");
		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent01 + "]");
		// NOTE : 'border effect here due to similarity and simple use of "ASCII" : change if alphabet is different !!
		Assertions.assertEquals("gvhg gvhg7 gvhg6", binary2txtEncodedFileContent01); // ??
		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
		Logger.printlnLog(LoggerLevel.LL_NONE, "txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(txt2binaryEncodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + decodedBinaryEncodedFileContent + "]");
		
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(toEncrypt, isDecrypted);
		
		Logger.printlnLog(LoggerLevel.LL_NONE, "" );
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeMore2txtCrypto3() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(3);
		Assertions.assertNotNull( forFileContent );
		// Logger.printlnLog(LoggerLevel.LL_NONE, forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encodeMore(toEncrypt);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent.length()+ "] ; [" + toEncrypt.length() + "]");
		Assertions.assertEquals(toEncrypt.length() * 4, encodedFileContent.length()); // because 4-uplets
		Assertions.assertEquals("tgaatgtttgactgaagcgctgaatgtttgactgaagtaagcgctgaatgtttgactgaaggtt", encodedFileContent);
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent.length()+ "] ; [" + encodedFileContent.length() + "]");
		Assertions.assertEquals(encodedFileContent.length() * 2, binaryEncodedFileContent.length());
		Assertions.assertEquals("11100000111011111110000111100000100110011110000011101111111000011110000010110000100110011110000011101111111000011110000010101111", binaryEncodedFileContent);
		
		Logger.printlnLog(LoggerLevel.LL_NONE,  "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent00 + "]");
		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent01 + "]");
		// NOTE : 'border effect here due to similarity and simple use of "ASCII" : change if alphabet is different !!
		Assertions.assertEquals("àïáààïáà°àïáà¯", binary2txtEncodedFileContent01); // ??
		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
		Logger.printlnLog(LoggerLevel.LL_NONE, "txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(txt2binaryEncodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + decodedBinaryEncodedFileContent + "]");
		
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(toEncrypt, isDecrypted);
		
		Logger.printlnLog(LoggerLevel.LL_NONE, "" );
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeRand() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
		Assertions.assertNotNull( forFileContent );
		// Logger.printlnLog(LoggerLevel.LL_NONE, forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encodeRand(toEncrypt);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent.length()+ "] ; [" + toEncrypt.length() + "]");
		Assertions.assertEquals(toEncrypt.length() * 4, encodedFileContent.length()); // because 4-uplets
		Assertions.assertEquals("ctcacgccctatctcaagaactcacgccctatctcaatagagaactcacgccctatctcaatat", encodedFileContent);
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent.length()+ "] ; [" + encodedFileContent.length() + "]");
		Assertions.assertEquals(encodedFileContent.length() * 2, binaryEncodedFileContent.length());
		Assertions.assertEquals("01110100011001010111001101110100001000000111010001100101011100110111010000110010001000000111010001100101011100110111010000110011", binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(binaryEncodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + decodedBinaryEncodedFileContent + "]");
		
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(toEncrypt, isDecrypted);
		
		Logger.printlnLog(LoggerLevel.LL_NONE, "" );
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeRand2txtCrypto0() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
		Assertions.assertNotNull( forFileContent );
		// Logger.printlnLog(LoggerLevel.LL_NONE, forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encodeRand(toEncrypt);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent.length()+ "] ; [" + toEncrypt.length() + "]");
		Assertions.assertEquals(toEncrypt.length() * 4, encodedFileContent.length()); // because 4-uplets
		Assertions.assertEquals("ctcacgccctatctcaagaactcacgccctatctcaatagagaactcacgccctatctcaatat", encodedFileContent);
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent.length()+ "] ; [" + encodedFileContent.length() + "]");
		Assertions.assertEquals(encodedFileContent.length() * 2, binaryEncodedFileContent.length());
		Assertions.assertEquals("01110100011001010111001101110100001000000111010001100101011100110111010000110010001000000111010001100101011100110111010000110011", binaryEncodedFileContent);
		
		Logger.printlnLog(LoggerLevel.LL_NONE,  "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent00 + "]");
		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent01 + "]");
		// NOTE : 'border effect here due to similarity and simple use of "ASCII" : change if alphabet is different !!
		Assertions.assertEquals("test test2 test3", binary2txtEncodedFileContent01); // ??
		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
		Logger.printlnLog(LoggerLevel.LL_NONE, "txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(txt2binaryEncodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + decodedBinaryEncodedFileContent + "]");
		
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(toEncrypt, isDecrypted);
		
		Logger.printlnLog(LoggerLevel.LL_NONE, "" );
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeRand2txtCrypto2() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(2);
		Assertions.assertNotNull( forFileContent );
		// Logger.printlnLog(LoggerLevel.LL_NONE, forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encodeRand(toEncrypt);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent.length()+ "] ; [" + toEncrypt.length() + "]");
		Assertions.assertEquals(toEncrypt.length() * 4, encodedFileContent.length()); // because 4-uplets
		Assertions.assertEquals("cgctctcgcggacgctagaacgctctcgcggacgctatctagaacgctctcgcggacgctatcg", encodedFileContent);
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent.length()+ "] ; [" + encodedFileContent.length() + "]");
		Assertions.assertEquals(encodedFileContent.length() * 2, binaryEncodedFileContent.length());
		Assertions.assertEquals("01100111011101100110100001100111001000000110011101110110011010000110011100110111001000000110011101110110011010000110011100110110", binaryEncodedFileContent);
		
		Logger.printlnLog(LoggerLevel.LL_NONE,  "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent00 + "]");
		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent01 + "]");
		// NOTE : 'border effect here due to similarity and simple use of "ASCII" : change if alphabet is different !!
		Assertions.assertEquals("gvhg gvhg7 gvhg6", binary2txtEncodedFileContent01); // ??
		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
		Logger.printlnLog(LoggerLevel.LL_NONE, "txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(txt2binaryEncodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + decodedBinaryEncodedFileContent + "]");
		
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(toEncrypt, isDecrypted);
		
		Logger.printlnLog(LoggerLevel.LL_NONE, "" );
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeRand2txtCrypto3() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(3);
		Assertions.assertNotNull( forFileContent );
		// Logger.printlnLog(LoggerLevel.LL_NONE, forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encodeRand(toEncrypt);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent.length()+ "] ; [" + toEncrypt.length() + "]");
		Assertions.assertEquals(toEncrypt.length() * 4, encodedFileContent.length()); // because 4-uplets
		Assertions.assertEquals("tgaatgtttgactgaagcgctgaatgtttgactgaagtaagcgctgaatgtttgactgaaggtt", encodedFileContent);
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent.length()+ "] ; [" + encodedFileContent.length() + "]");
		Assertions.assertEquals(encodedFileContent.length() * 2, binaryEncodedFileContent.length());
		Assertions.assertEquals("11100000111011111110000111100000100110011110000011101111111000011110000010110000100110011110000011101111111000011110000010101111", binaryEncodedFileContent);
		
		Logger.printlnLog(LoggerLevel.LL_NONE,  "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent00 + "]");
		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent01 + "]");
		// NOTE : 'border effect here due to similarity and simple use of "ASCII" : change if alphabet is different !!
		Assertions.assertEquals("àïáààïáà°àïáà¯", binary2txtEncodedFileContent01); // ??
		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
		Logger.printlnLog(LoggerLevel.LL_NONE, "txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(txt2binaryEncodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + decodedBinaryEncodedFileContent + "]");
		
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(toEncrypt, isDecrypted);
		
		Logger.printlnLog(LoggerLevel.LL_NONE, "" );
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeFile() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
		Assertions.assertNotNull( forFileContent );
		// Logger.printlnLog(LoggerLevel.LL_NONE, forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encode(toEncrypt, ITranslator.TranslatorEnum.simple);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent.substring(3, encodedFileContent.length()-3) + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent.length()+ "] ; [" + toEncrypt.length() + "]");
		// Assertions.assertEquals(toEncrypt.length() * 4, encodedFileContent.length()); // because 4-uplets
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent.length()+ "] ; [" + encodedFileContent.length() + "]");
		Assertions.assertEquals(encodedFileContent.length() * 2, binaryEncodedFileContent.length());
		// Assertions.assertEquals("111110010111010001100101011100110111010000100000011101000110010101110011011101000011001000100000011101000110010101110011011101000011001111111111", binaryEncodedFileContent);
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(binaryEncodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + decodedBinaryEncodedFileContent + "]");
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0); // Use specific decoder !!
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(toEncrypt, isDecrypted);
		
		Logger.printlnLog(LoggerLevel.LL_NONE, "" );
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeFile2txtCrypto0() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(0);
		Assertions.assertNotNull( forFileContent );
		// Logger.printlnLog(LoggerLevel.LL_NONE, forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encode(toEncrypt, ITranslator.TranslatorEnum.simple);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent.substring(3, encodedFileContent.length()-3) + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent.length()+ "] ; [" + toEncrypt.length() + "]");
		// Assertions.assertEquals(toEncrypt.length() * 4, encodedFileContent.length()); // because 4-uplets
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent.length()+ "] ; [" + encodedFileContent.length() + "]");
		Assertions.assertEquals(encodedFileContent.length() * 2, binaryEncodedFileContent.length());
		// Assertions.assertEquals("01110100011001010111001101110100001000000111010001100101011100110111010000110010001000000111010001100101011100110111010000110011", binaryEncodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE,  "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent00 + "]");
		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent01 + "]");
		// NOTE : 'border effect here due to similarity and simple use of "ASCII" : change if alphabet is different !!
		Assertions.assertEquals("test test2 test3", binary2txtEncodedFileContent01);
		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
		Logger.printlnLog(LoggerLevel.LL_NONE, "txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(txt2binaryEncodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + decodedBinaryEncodedFileContent + "]");
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(toEncrypt, isDecrypted);
		
		Logger.printlnLog(LoggerLevel.LL_NONE, "" );
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeFile2txtCrypto2() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(2);
		Assertions.assertNotNull( forFileContent );
		// Logger.printlnLog(LoggerLevel.LL_NONE, forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encode(toEncrypt, ITranslator.TranslatorEnum.simple);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent.substring(3, encodedFileContent.length()-3) + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent.length()+ "] ; [" + toEncrypt.length() + "]");
		// Assertions.assertEquals(toEncrypt.length() * 4, encodedFileContent.length()); // because 4-uplets
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent.length()+ "] ; [" + encodedFileContent.length() + "]");
		Assertions.assertEquals(encodedFileContent.length() * 2, binaryEncodedFileContent.length());
		// Assertions.assertEquals("01110100011001010111001101110100001000000111010001100101011100110111010000110010001000000111010001100101011100110111010000110011", binaryEncodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE,  "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent00 + "]");
		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent01 + "]");
		// NOTE : 'border effect here due to similarity and simple use of "ASCII" : change if alphabet is different !!
		Assertions.assertEquals("gvhg gvhg7 gvhg6", binary2txtEncodedFileContent01);
		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
		Logger.printlnLog(LoggerLevel.LL_NONE, "txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(txt2binaryEncodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + decodedBinaryEncodedFileContent + "]");
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(toEncrypt, isDecrypted);
		
		Logger.printlnLog(LoggerLevel.LL_NONE, "" );
	}
	
	@Test
	void testGeneticTranslatorMoreTestsEncodeFile2txtCrypto3() {
		// TODO 'DP builder' for Genetic Translator
		GeneticTranslator forFileContent = BiologicalUtils.getGenericCrypto(3);
		Assertions.assertNotNull( forFileContent );
		// Logger.printlnLog(LoggerLevel.LL_NONE, forFileContent.toString());
		
		String toEncrypt = "test test2 test3";
		String encodedFileContent = forFileContent.encode(toEncrypt, ITranslator.TranslatorEnum.simple);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent.substring(3, encodedFileContent.length()-3) + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + encodedFileContent.length()+ "] ; [" + toEncrypt.length() + "]");
		// Assertions.assertEquals(toEncrypt.length() * 4, encodedFileContent.length()); // because 4-uplets
		
		String binaryEncodedFileContent = BinaryConversion.sequence2binary(encodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent + "]");
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + binaryEncodedFileContent.length()+ "] ; [" + encodedFileContent.length() + "]");
		Assertions.assertEquals(encodedFileContent.length() * 2, binaryEncodedFileContent.length());
		// Assertions.assertEquals("01110100011001010111001101110100001000000111010001100101011100110111010000110010001000000111010001100101011100110111010000110011", binaryEncodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE,  "prettyBinary: [" + BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") + "]" );
		// String binary2txtEncodedFileContent = BinaryConversion.convertBinaryToString(binaryEncodedFileContent);
		String binary2txtEncodedFileContent00 = BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent00: [" + binary2txtEncodedFileContent00 + "]");
		String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii (binaryEncodedFileContent);
		// String binary2txtEncodedFileContent01 = BinaryConversion.convertBinaryToAscii ( BinaryConversion.prettyBinary(binaryEncodedFileContent, 8, " ") );
		Logger.printlnLog(LoggerLevel.LL_NONE, "binary2txtEncodedFileContent01: [" + binary2txtEncodedFileContent01 + "]");
		// NOTE : 'border effect here due to similarity and simple use of "ASCII" : change if alphabet is different !!
		Assertions.assertEquals("àïáààïáà°àïáà¯", binary2txtEncodedFileContent01);
		String txt2binaryEncodedFileContent = BinaryConversion.convertStringToBinary(binary2txtEncodedFileContent01);
		Logger.printlnLog(LoggerLevel.LL_NONE, "txt2binaryEncodedFileContent: [" + txt2binaryEncodedFileContent + "]");
		Assertions.assertEquals(binaryEncodedFileContent, txt2binaryEncodedFileContent);
		
		String decodedBinaryEncodedFileContent = BinaryConversion.binary2sequence(txt2binaryEncodedFileContent);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + decodedBinaryEncodedFileContent + "]");
		Assertions.assertEquals(decodedBinaryEncodedFileContent, encodedFileContent);
		
		String isDecrypted = forFileContent.decode(decodedBinaryEncodedFileContent, 0, 0);
		Logger.printlnLog(LoggerLevel.LL_NONE, "[" + isDecrypted + "] <= [" + toEncrypt + "]");
		Assertions.assertEquals(toEncrypt, isDecrypted);
		
		Logger.printlnLog(LoggerLevel.LL_NONE, "");
	}
	
}

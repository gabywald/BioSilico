package gabywald.crypto.model.tests;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.crypto.model.BinaryConversion;

/**
 * 
 * @author Gabriel Chandesris (2023)
 */
class BinaryBitsTests {

	@Test
	void test() {
		
		String input = "Hello";
		String result = BinaryConversion.convertStringToBinary(input);
		Assertions.assertNotNull(result);
		Assertions.assertEquals("0100100001100101011011000110110001101111", 
								BinaryConversion.convertStringToBinary(input));
		Assertions.assertEquals("01001000 01100101 01101100 01101100 01101111", 
								BinaryConversion.prettyBinary(result, 8, " "));
		
		String input02 = "a";
		String result02 = BinaryConversion.convertByteArraysToBinary(input02.getBytes(StandardCharsets.UTF_8));
		Assertions.assertEquals(result02, BinaryConversion.prettyBinary(result02, 8, " "));

		// 0100100001100101011011000110110001101111
		// 01001000 01100101 01101100 01101100 01101111
		String binary01 = "0100100001100101011011000110110001101111";
		String binary02 = "01001000 01100101 01101100 01101100 01101111";
		Assertions.assertEquals(input, BinaryConversion.convertBinaryToString(binary02));
		Assertions.assertEquals(input, BinaryConversion.convertBinaryToAscii(binary01));
		
		// ACGT <=> 00 01 10 11
//		System.out.println((int)'A'); // 65
//		System.out.println((int)'C'); // 67
//		System.out.println((int)'G'); // 71
//		System.out.println((int)'T'); // 84
//		System.out.println((int)'U'); // 85
		
		String sequenceACTG = "ACGT";
		String sequenceBINA = "00011011";
		Assertions.assertEquals(sequenceBINA, BinaryConversion.sequence2binary(sequenceACTG));
		Assertions.assertEquals(sequenceACTG, BinaryConversion.binary2sequence(sequenceBINA));
		
		Assertions.assertEquals("", BinaryConversion.convertBinaryToString(BinaryConversion.sequence2binary("ACGT")));
		Assertions.assertEquals("ACGT", BinaryConversion.binary2sequence(BinaryConversion.convertStringToBinary("")));
		
		Assertions.assertEquals("00011011000110110001101100011011", BinaryConversion.sequence2binary("ACGTACGTACGTACGT"));
		Assertions.assertEquals("ᬛ", BinaryConversion.convertBinaryToString(BinaryConversion.sequence2binary("ACGTACGTACGTACGT")));
		
	}

}

package gabywald.crypto.model.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gabywald.crypto.model.BinaryConversion;

class BinaryBitsTests {

    @BeforeEach
    void setUp() throws Exception {
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void test() {
		String sequenceACTG = "ACGT";
		String sequenceBINAACGT = "00011011";
		Assertions.assertEquals(sequenceBINAACGT, BinaryConversion.sequence2binaryACGT(sequenceACTG));
		Assertions.assertEquals(sequenceACTG, BinaryConversion.binary2sequenceACGT(sequenceBINAACGT));
		
		String sequenceacgt = "acgt";
		String sequencebinaACGT = "";
		Assertions.assertEquals(sequencebinaACGT, BinaryConversion.sequence2binaryACGT(sequenceacgt));
		Assertions.assertEquals(sequencebinaACGT, BinaryConversion.binary2sequenceACGT(sequencebinaACGT));
		
		// String sequenceACTG = "ACGT";
		String sequenceBINAacgt = "";
		Assertions.assertEquals(sequenceBINAacgt, BinaryConversion.sequence2binary(sequenceACTG));
		Assertions.assertEquals(sequenceBINAacgt, BinaryConversion.binary2sequence(sequenceBINAacgt));
		
		// String sequenceacgt = "acgt";
		String sequencebinaacgt = "00011011";
		Assertions.assertEquals(sequencebinaacgt, BinaryConversion.sequence2binary(sequenceacgt));
		Assertions.assertEquals(sequenceacgt, BinaryConversion.binary2sequence(sequencebinaacgt));
		
		// ACGT <=> 00 01 10 11
		// System.out.println((int)'A'); // 65
		// System.out.println((int)'C'); // 67
		// System.out.println((int)'G'); // 71
		// System.out.println((int)'T'); // 84
		// System.out.println((int)'U'); // 85
		
		// System.out.println((int)'a'); // 97
		// System.out.println((int)'c'); // 99
		// System.out.println((int)'g'); // 103
		// System.out.println((int)'t'); // 116
		// System.out.println((int)'u'); // 117
		
		Assertions.assertEquals("00011011000110110001101100011011", BinaryConversion.sequence2binaryACGT("ACGTACGTACGTACGT"));
		Assertions.assertEquals("00011011000110110001101100011011", BinaryConversion.sequence2binary    ("acgtacgtacgtacgt"));
    }

}

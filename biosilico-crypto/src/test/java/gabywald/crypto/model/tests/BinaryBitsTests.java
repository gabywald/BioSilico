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
		String sequenceacgt = "acgt";
		String sequenceZION = "00011011";
		Assertions.assertEquals(sequenceZION, BinaryConversion.sequence2binary(sequenceACTG));
		Assertions.assertEquals(sequenceacgt, BinaryConversion.binary2sequence(sequenceZION));
		Assertions.assertEquals(sequenceZION, BinaryConversion.sequence2binary(sequenceacgt));
		Assertions.assertEquals(sequenceacgt, BinaryConversion.binary2sequence(sequenceZION));
		Assertions.assertEquals(sequenceZION, BinaryConversion.sequence2binary(sequenceacgt));
		Assertions.assertEquals("", BinaryConversion.binary2sequence(""));
		Assertions.assertEquals("", BinaryConversion.sequence2binary(""));
		
		// ACGT <=> 00 01 10 11
		// System.out.println((int)'A'); // 65
		// System.out.println((int)'C'); // 67
		// System.out.println((int)'G'); // 71
		// System.out.println((int)'T'); // 84
		// System.out.println((int)'U'); // 85
		
		// acgt <=> 00 01 10 11
		// System.out.println((int)'a'); // 97
		// System.out.println((int)'c'); // 99
		// System.out.println((int)'g'); // 103
		// System.out.println((int)'t'); // 116
		// System.out.println((int)'u'); // 117
		
		Assertions.assertEquals("00011011000110110001101100011011", BinaryConversion.sequence2binary("ACGTACGTACGTACGT"));
		Assertions.assertEquals("00011011000110110001101100011011", BinaryConversion.sequence2binary("acgtacgtacgtacgt"));
		Assertions.assertEquals("acgtacgtacgtacgt", BinaryConversion.binary2sequence("00011011000110110001101100011011"));
		
		Assertions.assertEquals("0000000000", BinaryConversion.sequence2binary("aaaaa"));
		Assertions.assertEquals("0101010101", BinaryConversion.sequence2binary("ccccc"));
		Assertions.assertEquals("1010101010", BinaryConversion.sequence2binary("ggggg"));
		Assertions.assertEquals("1111111111", BinaryConversion.sequence2binary("ttttt"));
		Assertions.assertEquals("aaaaa", BinaryConversion.binary2sequence("0000000000"));
		Assertions.assertEquals("ccccc", BinaryConversion.binary2sequence("0101010101"));
		Assertions.assertEquals("ggggg", BinaryConversion.binary2sequence("1010101010"));
		Assertions.assertEquals("ttttt", BinaryConversion.binary2sequence("1111111111"));
		
		Assertions.assertEquals("0000000000", BinaryConversion.sequence2binary("aaaaa", 0));
		Assertions.assertEquals("0101010101", BinaryConversion.sequence2binary("ccccc", 0));
		Assertions.assertEquals("1010101010", BinaryConversion.sequence2binary("ggggg", 0));
		Assertions.assertEquals("1111111111", BinaryConversion.sequence2binary("ttttt", 0));
		Assertions.assertEquals("aaaaa", BinaryConversion.binary2sequence("0000000000", 0));
		Assertions.assertEquals("ccccc", BinaryConversion.binary2sequence("0101010101", 0));
		Assertions.assertEquals("ggggg", BinaryConversion.binary2sequence("1010101010", 0));
		Assertions.assertEquals("ttttt", BinaryConversion.binary2sequence("1111111111", 0));
		
		Assertions.assertEquals("0101010101", BinaryConversion.sequence2binary("aaaaa", 1));
		Assertions.assertEquals("1010101010", BinaryConversion.sequence2binary("ccccc", 1));
		Assertions.assertEquals("1111111111", BinaryConversion.sequence2binary("ggggg", 1));
		Assertions.assertEquals("0000000000", BinaryConversion.sequence2binary("ttttt", 1));
		Assertions.assertEquals("aaaaa", BinaryConversion.binary2sequence("1111111111", 1));
		Assertions.assertEquals("ccccc", BinaryConversion.binary2sequence("0000000000", 1));
		Assertions.assertEquals("ggggg", BinaryConversion.binary2sequence("0101010101", 1));
		Assertions.assertEquals("ttttt", BinaryConversion.binary2sequence("1010101010", 1));
		
		Assertions.assertEquals("1010101010", BinaryConversion.sequence2binary("aaaaa", 2));
		Assertions.assertEquals("1111111111", BinaryConversion.sequence2binary("ccccc", 2));
		Assertions.assertEquals("0000000000", BinaryConversion.sequence2binary("ggggg", 2));
		Assertions.assertEquals("0101010101", BinaryConversion.sequence2binary("ttttt", 2));
		Assertions.assertEquals("aaaaa", BinaryConversion.binary2sequence("1010101010", 2));
		Assertions.assertEquals("ccccc", BinaryConversion.binary2sequence("1111111111", 2));
		Assertions.assertEquals("ggggg", BinaryConversion.binary2sequence("0000000000", 2));
		Assertions.assertEquals("ttttt", BinaryConversion.binary2sequence("0101010101", 2));
		
		Assertions.assertEquals("1111111111", BinaryConversion.sequence2binary("aaaaa", 3));
		Assertions.assertEquals("0000000000", BinaryConversion.sequence2binary("ccccc", 3));
		Assertions.assertEquals("0101010101", BinaryConversion.sequence2binary("ggggg", 3));
		Assertions.assertEquals("1010101010", BinaryConversion.sequence2binary("ttttt", 3));
		Assertions.assertEquals("aaaaa", BinaryConversion.binary2sequence("0101010101", 3));
		Assertions.assertEquals("ccccc", BinaryConversion.binary2sequence("1010101010", 3));
		Assertions.assertEquals("ggggg", BinaryConversion.binary2sequence("1111111111", 3));
		Assertions.assertEquals("ttttt", BinaryConversion.binary2sequence("0000000000", 3));
		
		/* ***** ***** */
		Assertions.assertEquals("1111111111", BinaryConversion.sequence2binary("aaaaa", 4));
		Assertions.assertEquals("1010101010", BinaryConversion.sequence2binary("ccccc", 4));
		Assertions.assertEquals("0101010101", BinaryConversion.sequence2binary("ggggg", 4));
		Assertions.assertEquals("0000000000", BinaryConversion.sequence2binary("ttttt", 4));
		Assertions.assertEquals("aaaaa", BinaryConversion.binary2sequence("1111111111", 4));
		Assertions.assertEquals("ccccc", BinaryConversion.binary2sequence("1010101010", 4));
		Assertions.assertEquals("ggggg", BinaryConversion.binary2sequence("0101010101", 4));
		Assertions.assertEquals("ttttt", BinaryConversion.binary2sequence("0000000000", 4));
		
		Assertions.assertEquals("1010101010", BinaryConversion.sequence2binary("aaaaa", 5));
		Assertions.assertEquals("0101010101", BinaryConversion.sequence2binary("ccccc", 5));
		Assertions.assertEquals("0000000000", BinaryConversion.sequence2binary("ggggg", 5));
		Assertions.assertEquals("1111111111", BinaryConversion.sequence2binary("ttttt", 5));
		Assertions.assertEquals("aaaaa", BinaryConversion.binary2sequence("1010101010", 5));
		Assertions.assertEquals("ccccc", BinaryConversion.binary2sequence("0101010101", 5));
		Assertions.assertEquals("ggggg", BinaryConversion.binary2sequence("0000000000", 5));
		Assertions.assertEquals("ttttt", BinaryConversion.binary2sequence("1111111111", 5));
		
		Assertions.assertEquals("0101010101", BinaryConversion.sequence2binary("aaaaa", 6));
		Assertions.assertEquals("0000000000", BinaryConversion.sequence2binary("ccccc", 6));
		Assertions.assertEquals("1111111111", BinaryConversion.sequence2binary("ggggg", 6));
		Assertions.assertEquals("1010101010", BinaryConversion.sequence2binary("ttttt", 6));
		Assertions.assertEquals("aaaaa", BinaryConversion.binary2sequence("0101010101", 6));
		Assertions.assertEquals("ccccc", BinaryConversion.binary2sequence("0000000000", 6));
		Assertions.assertEquals("ggggg", BinaryConversion.binary2sequence("1111111111", 6));
		Assertions.assertEquals("ttttt", BinaryConversion.binary2sequence("1010101010", 6));
		
		Assertions.assertEquals("0000000000", BinaryConversion.sequence2binary("aaaaa", 7));
		Assertions.assertEquals("1111111111", BinaryConversion.sequence2binary("ccccc", 7));
		Assertions.assertEquals("1010101010", BinaryConversion.sequence2binary("ggggg", 7));
		Assertions.assertEquals("0101010101", BinaryConversion.sequence2binary("ttttt", 7));
		Assertions.assertEquals("aaaaa", BinaryConversion.binary2sequence("0000000000", 7));
		Assertions.assertEquals("ccccc", BinaryConversion.binary2sequence("1111111111", 7));
		Assertions.assertEquals("ggggg", BinaryConversion.binary2sequence("1010101010", 7));
		Assertions.assertEquals("ttttt", BinaryConversion.binary2sequence("0101010101", 7));
		
		/* ***** ***** */
		Assertions.assertEquals("", BinaryConversion.convertBinaryToAscii("00011011000110110001101100011011"));
		Assertions.assertEquals("ᬛ", BinaryConversion.convertBinaryToString("00011011000110110001101100011011"));
		
		// 0111010001100101011100110111010000110001001000000111010001100101011100110111010000110010001000000111010001100101011100110111010000110011
		Assertions.assertEquals("0111010001100101011100110111010000110001001000000111010001100101011100110111010000110010001000000111010001100101011100110111010000110011", BinaryConversion.convertStringToBinary("test1 test2 test3") );
		Assertions.assertEquals("test1 test2 test3", BinaryConversion.convertBinaryToString( BinaryConversion.prettyBinary("0111010001100101011100110111010000110001001000000111010001100101011100110111010000110010001000000111010001100101011100110111010000110011", 8, " ") ) );
		Assertions.assertEquals("test1 test2 test3", BinaryConversion.convertBinaryToAscii ( "0111010001100101011100110111010000110001001000000111010001100101011100110111010000110010001000000111010001100101011100110111010000110011" ) );
    }

}

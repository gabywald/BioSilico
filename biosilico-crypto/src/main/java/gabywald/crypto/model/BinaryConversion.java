package gabywald.crypto.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2023, 2026)
 */
public class BinaryConversion {
	
	/** 'A', 'C', 'G', 'T', 'U' */
	// private static char[] upperACGT = new char[]{ 65, 67, 71, 84, 85 };
	/** 'a', 'c', 'g', 't', 'u' */
	private static char[] lowerACGT = new char[]{ 97, 99, 103, 116, 117 };
	/** "00", "01", "10", "11", "" */
	private static String[] binaryTargets = new String[] { "00", "01", "10", "11", "\t\t" };
	
	/**
	 * Index Changer. 
	 * <br/>NOTE : index 1 is complementary reverse of index 3
	 */
	private static int[][] changeIndexes = new int[][] {
		{ 0, 1, 2, 3, 4 }, 
		{ 1, 2, 3, 0, 4 }, 
		{ 2, 3, 0, 1, 4 }, 
		{ 3, 0, 1, 2, 4 }, 
		/* ***** ***** */
		{ 3, 2, 1, 0, 4 },
		{ 2, 1, 0, 3, 4 },
		{ 1, 0, 3, 2, 4 },
		{ 0, 3, 2, 1, 4 }
		/* ***** ***** */
		// TODO add more combinations here !
	};
	
	/**
	 * Convert from "ACGT" or "acgt" sequences to binary strings. 
	 * @param input "ACGT" or "acgt" sequence (make lowercase). 
	 * @param decalage Index in {@link BinaryConversion#changeIndexes}
	 * @return Succession of '0' and '1'. 
	 */
	public static String sequence2binary(String input, int decalage) {
		if ( (decalage < 0) || (decalage >= BinaryConversion.changeIndexes.length ) ) 
			{ return ""; } // TODO throw exception here ??
		StringBuilder result = new StringBuilder();
		for (char c : input.toLowerCase().toCharArray()) {
			int index = 0;
			for ( ; index < BinaryConversion.lowerACGT.length ; index++) {
				if (c == BinaryConversion.lowerACGT[index]) { 
					Logger.printlnLog(LoggerLevel.LL_NONE, "'" + BinaryConversion.lowerACGT[index] + "' => '" + BinaryConversion.binaryTargets[ BinaryConversion.changeIndexes[decalage][index] ] + "' (" + BinaryConversion.changeIndexes[decalage][index] + ")");
					result.append( BinaryConversion.binaryTargets
							[ BinaryConversion.changeIndexes[decalage][index] ] );
					break; 
				}
			}
		}
		return result.toString();
	}
	
	/**
	 * Same as {@link BinaryConversion#sequence2binaryGeneric(String, int)}, but with decalage at 0. 
	 * @param input "ACGT" or "acgt" sequence (make lowercase). 
	 * @return Succession of '0' and '1'. 
	 */
	public static String sequence2binary(String input) 
		{ return BinaryConversion.sequence2binary(input, 0); }
	
	/**
	 * Sequence of '0' and '1' to 'acgt' sequence. 
	 * @param input sequence of '0' and '1'. 
	 * @param decalage Index in {@link BinaryConversion#changeIndexes}
	 * @return Succession of 'acgt' characters. 
	 */
	public static String binary2sequence(String input, int decalage) {
		if ( (decalage < 0) || (decalage >= BinaryConversion.changeIndexes.length ) ) 
			{ return ""; } // TODO throw exception here ??
		StringBuilder output = new StringBuilder();
		for (int i = 0 ; i <= input.length() - 2 ; i += 2) {
			String b = input.substring(i, i + 2);
			int index = 0;
			for ( ; index < BinaryConversion.binaryTargets.length ; index++) {
				if ( b.equals( BinaryConversion.binaryTargets[index] ) ) { 
					Logger.printlnLog(LoggerLevel.LL_NONE, "'" + BinaryConversion.binaryTargets[index] + "' => '" + BinaryConversion.lowerACGT[ BinaryConversion.changeIndexes[decalage][index] ] + "' (" + BinaryConversion.changeIndexes[decalage][index] + ")");
					output.append( (char) BinaryConversion.lowerACGT[ BinaryConversion.changeIndexes[decalage][index] ] );
					break; 
				}
			}
		}
		return output.toString();
	}
	
	/**
	 * Same as {@link BinaryConversion#binary2sequence(String, int)}, but with decalage at 1. 
	 * @param input input sequence of '0' and '1'. 
	 * @return Succession of 'acgt' characters. 
	 */
	public static String binary2sequence(String input) 
		{ return BinaryConversion.binary2sequence(input, 0); }

	
	/* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */

	public static String convertStringToBinary(String input) {
		StringBuilder result = new StringBuilder();
		char[] chars = input.toCharArray();
		for (char aChar : chars) {
			// char -> int, auto-cast ; zero pads
			result.append( String.format("%8s", Integer.toBinaryString(aChar)).replaceAll(" ", "0") );
		}
		
		return result.toString();
	}
	
	public static String convertByteArraysToBinary(byte[] input) {
		StringBuilder result = new StringBuilder();
		for (byte b : input) {
			int val = b;
			for (int i = 0; i < 8; i++) {
				result.append((val & 128) == 0 ? 0 : 1);	// 128 = 1000 0000
				val <<= 1;
			}
		}
		return result.toString();
	}
	
	public static String convertBinaryToString(String input) {
		// return Arrays.stream(input.split(" ")).map(binary -> Integer.parseInt(binary, 2)).map(Character::toString).collect(Collectors.joining());
		// return Arrays.stream(input.split(" ")).map(b -> Integer.parseInt(b, 2)).map(b -> Character.toString(b)).collect(Collectors.joining());
		// return Arrays.stream(input.split(" ")).map(b -> Integer.parseInt(b, 2)).map(b -> Integer.toString(b)).collect(Collectors.joining());
		return Arrays.stream(input.split(" ")).map(b -> Integer.parseInt(b, 2)).map(b -> ((char)b.intValue()) + "").collect(Collectors.joining());
	}
	
	public static String convertBinaryToAscii(String binary) {
		// Convert binary string into ASCII.
		StringBuilder output = new StringBuilder();
		for (int i = 0 ; i <= binary.length() - 8 ; i += 8) {
			int k = Integer.parseInt(binary.substring(i, i + 8), 2);
			output.append((char) k);
		}
		return output.toString();
	}

	public static String prettyBinary(String binary, int blockSize, String separator) {
		if (blockSize <= 0) { return ""; } // TODO BinaryException ?!
		List<String> result = new ArrayList<>();
		int index = 0;
		while (index < binary.length()) {
			result.add(binary.substring(index, Math.min(index + blockSize, binary.length())));
			index += blockSize;
		}

		return result.stream().collect(Collectors.joining(separator));
	}
}

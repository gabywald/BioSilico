package gabywald.crypto.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @author Gabriel Chandesris (2023)
 */
public class BinaryConversion {

	public static String sequence2binary(String input) {
		// input.chars().map( c -> ((c == 65)?"00":((c == 67)?"01":((c == 71)?"10":((c == 84)?"11":""))))).forEach(System.out::println); // .collect(Collectors.joining());
		StringBuilder result = new StringBuilder();
		for (char c : input.toCharArray()) {
			result.append(((c == 65)?"00":((c == 67)?"01":((c == 71)?"10":((c == 84)?"11":"")))));
		}
		return result.toString();
	}
	
	public static String binary2sequence(String input) {
		StringBuilder output = new StringBuilder();
		for (int i = 0 ; i <= input.length() - 2 ; i += 2) {
			String b = input.substring(i, i + 2);
			int k = ((b.equals("00"))?65:((b.equals("01"))?67:((b.equals("10"))?71:((b.equals("11"))?84:85))));
			output.append((char) k);
		}
		return output.toString();
	}

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
				result.append((val & 128) == 0 ? 0 : 1);	  // 128 = 1000 0000
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
		List<String> result = new ArrayList<>();
		int index = 0;
		while (index < binary.length()) {
			result.add(binary.substring(index, Math.min(index + blockSize, binary.length())));
			index += blockSize;
		}

		return result.stream().collect(Collectors.joining(separator));
	}
}

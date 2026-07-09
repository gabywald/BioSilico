package gabywald.utilities.others;

/**
 * 
 * <br/>From {@linkplain https://www.baeldung.com/java-convert-int-to-hex }
 * <br/>See also {@linkplain https://www.baeldung.com/java-convert-hex-string-to-integer }
 * @author Gabriel Chandesris (2026)
 */
public class IntegerToHexa {
	
	private static final String digits = "0123456789ABCDEF";
	
	public static String decimal2hexadecimal(int input) {
		if (input <= 0) {  return "0"; }
		StringBuilder hex = new StringBuilder();
		while (input > 0) {
			int digit = input % 16;
			hex.insert(0, IntegerToHexa.digits.charAt(digit));
			input = input / 16;
		}
		return hex.toString();
	}
	
}

package gabywald.global.data;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public abstract class StringUtils {

	/**
	 * Aim of this method is to repeat a String 'time' times. 
	 * @param elt (String)
	 * @param time (int)
	 * @return (String) elt concatenates 'time' times to itself. 
	 */
	public static String repeat(String elt, int time) {
		StringBuilder sbResult = new StringBuilder();
		java.util.stream.IntStream.iterate(1, i -> ++i)
			.limit(time)
			.forEach( i-> sbResult.append( elt ) );
		return sbResult.toString();
	}
	
}

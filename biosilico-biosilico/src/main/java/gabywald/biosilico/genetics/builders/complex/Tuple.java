package gabywald.biosilico.genetics.builders.complex;

/** 
 * Tuple class 
 * @author Gabriel Chandesris (2021)
 */
public class Tuple {

	// private final List<Object> elements;
	
	private int[] elements;

	private Tuple(int size, int... elements) {
		this.elements = new int[size];
		for (int i = 0 ; (i < size) && (i < elements.length) ; i++) {
			this.elements[ i ] = elements[ i ];
		}
	}
	
	public int getElementAt(int i) {
		return this.elements[ i ];
	}
	
	public int[] getElements() {
		return this.elements;
	}
	
	/**
	 * Build a Typle (check size according to elements given). 
	 * @param size
	 * @param elements
	 * @return A Tuple instance, null if size is bigger than eleemnts length. 
	 */
	public static Tuple build(int size, int... elements) {
		if (size <= elements.length) { 
			return new Tuple(size, elements);
		} else {
			return null;
		}
	}

}

package gabywald.biosilico.genetics.builders;

/** 
 * Pair class 
 * see https://www.techiedelight.com/implement-pair-class-java/
 * @author Gabriel Chandesris (2020, 2021)
 */
public final class Pair<U, V> {
	/** First field of a Pair */
	public final U first;
	/** Second field of a Pair */
	public final V second; 

	/** Constructs a new Pair with specified values. */
	Pair(U first, V second) {
		this.first = first;
		this.second = second;
	}

	/**
	 * Checks specified object is "equal to" current object or not
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) 
			{ return true; }

		if ( (o == null) || (this.getClass() != o.getClass()) )
			{ return false; }

		Pair<?, ?> pair = (Pair<?, ?>) o;

		// Call equals() method of the underlying objects
		if (!first.equals(pair.first))
			{ return false; }
		return second.equals(pair.second);
	}

	/**
	 * Computes hash code for an object to support hash tables. 
	 */
	@Override
	public int hashCode() {
		// Use hash codes of the underlying objects
		return 31 * this.first.hashCode() + this.second.hashCode();
	}

	@Override
	public String toString() {
		return "(" + this.first + ", " + this.second + ")";
	}

	/** Factory method for creating a Typed Pair immutable instance. */
	public static <U, V> Pair <U, V> of(U a, V b) {
		// Calls private constructor
		return new Pair<>(a, b);
	}
}

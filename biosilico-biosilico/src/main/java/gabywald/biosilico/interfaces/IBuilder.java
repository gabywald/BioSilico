package gabywald.biosilico.interfaces;

/**
 * Interface definition of Builder. 
 * <br><i>Design-Pattern Builder. </i>
 * @author Gabriel Chandesris (2022)
 * @param <T> Class to build / instanciate. 
 */
public interface IBuilder<T> {
	/** Basic build function / method. */
	public T build();
}

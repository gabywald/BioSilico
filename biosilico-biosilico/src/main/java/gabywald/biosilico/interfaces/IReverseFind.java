package gabywald.biosilico.interfaces;

/**
 * 
 * @author Gabriel Chandesris (2020)
 * @param <T>
 */
public interface IReverseFind<T extends IChemicalsType> {
	
	/**
	 * Retrieve element from enum from its index. 
	 * @param index Applying int index. 
	 * @return Element from current enum. Could be null. 
	 */
	public T getFrom(int index);
	
}

package gabywald.biosilico.model.enums;

public interface IReverseFind<T> {
	
	/**
	 * Retrieve element from enum from its index. 
	 * @param index Applying int index. 
	 * @return Element from current enum. Could be null. 
	 */
	public T getFrom(int index);
	
}

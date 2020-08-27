package gabywald.biosilico.interfaces;

public interface IReverseFind<T extends IChemicalsType> {
	
	/**
	 * Retrieve element from enum from its index. 
	 * @param index Applying int index. 
	 * @return Element from current enum. Could be null. 
	 */
	public T getFrom(int index);
	
}

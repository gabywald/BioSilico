package gabywald.biosilico.interfaces;

import gabywald.biosilico.model.enums.DirectionWorld;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public interface IEnvironment {
	
	/**
	 * Gives environment's item at given position. 
	 * @param pos (IPosition)
	 * @return (IEnvironmentItem)
	 */
	public IEnvironmentItem getEnvironmentItem(IPosition pos);

	/**
	 * From a given direction and position, gives the next environment's item. 
	 * @param dir (DirectionWorld)
	 * @param pos (IPosition)
	 * @return (IEnvironmentItem)
	 */
	public IEnvironmentItem getDirection(DirectionWorld dir, IPosition pos);
	
	/**
	 * If a IEnvironmentItem exists at this position. 
	 * @param position (OPosition)
	 * @return 'true' if apply', 'false' otherwise. 
	 */
	public boolean isExisting(IPosition position);
	
}

package gabywald.biosilico.interfaces;

import gabywald.biosilico.model.enums.DirectionWorld;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public interface IEnvironmentItem extends IChemicalsContent, IAgentContent {
	
	public IPosition getPosition();
	
	public IEnvironment getEnvironment();
	
	public IEnvironmentItem getDirection(DirectionWorld direction);
	
}

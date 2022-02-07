package gabywald.biosilico.interfaces;

import gabywald.biosilico.interfaces.functionnals.INamedElement;
import gabywald.biosilico.model.enums.DirectionWorld;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public interface IEnvironmentItem extends IChemicalsContent, IAgentContent, INamedElement {
	
	public IPosition getPosition();
	
	public IEnvironment getEnvironment();
	
	public IEnvironmentItem getDirection(DirectionWorld direction);
	
}

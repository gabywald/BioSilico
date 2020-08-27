package gabywald.biosilico.model.decisions;

import gabywald.biosilico.model.Organism;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public abstract class BaseDecisionOnlyOneAttribute 
		extends BaseDecision 
	{
	
	/**
	 * 
	 * @param orga
	 * @param BaseDecisionOnlyOneAttribute objet : type of agent ; location : destination. 
	 */
	BaseDecisionOnlyOneAttribute(Organism orga, int oneAttribute) {
		super(orga, oneAttribute);
	}
	
	// TODO java 8 lambda with Functionnal attribute passed !! => IDecisionSimple
	
}

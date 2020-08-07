package gabywald.biosilico.model.decisions;

import gabywald.biosilico.model.Organism;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public abstract class BaseDecisionOnlyOneAttribute 
		extends BaseDecision 
		// implements IDecisionSimple<Organism> 
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
	
//	public <T extends Agent> void action(T ofType, Function<T, Void> function) 
//		{ function.apply(ofType); }

//	public  void action(Organism ofType, Function<Organism, Void> function) 
//		{ function.apply(ofType); }

}

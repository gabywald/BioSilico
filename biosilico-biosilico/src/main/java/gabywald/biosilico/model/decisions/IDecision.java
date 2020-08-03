package gabywald.biosilico.model.decisions;

import gabywald.biosilico.model.Organism;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
@FunctionalInterface
public interface IDecision {

	/**
	 * Defines the action to make. 
	 * @param orga
	 * @param variables
	 */
	public void action(Organism orga, int... variables);
	
}

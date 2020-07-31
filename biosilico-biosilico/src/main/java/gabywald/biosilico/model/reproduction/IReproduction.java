package gabywald.biosilico.model.reproduction;

import gabywald.biosilico.model.Organism;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
@FunctionalInterface
public interface IReproduction {

	/**
	 * Defines the reproduction for a group of organism. 
	 * @param orga Which instance of organism. 
	 * @param variables On which conditions. 
	 */
	public void action(Organism... organisms);
}

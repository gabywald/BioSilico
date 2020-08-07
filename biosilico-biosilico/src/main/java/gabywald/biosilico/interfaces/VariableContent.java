package gabywald.biosilico.interfaces;

import gabywald.biosilico.model.Chemicals;

/**
 * This interface defines use of chemical variables in Agent's or sub-classes. 
 * @author Gabriel Chandesris (2009-2010, 2020)
 */
public interface VariableContent {
	/** To get the instance of Variable's used. */
	public Chemicals getVariables();
}

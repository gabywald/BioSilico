package gabywald.biosilico.model.decisions;

import gabywald.biosilico.model.Organism;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public abstract class BaseDecision implements IDecision {
	private Organism orga;
	
	private int[] variables;
	
	BaseDecision(Organism orga) {
		this(orga, null);
	}
	
	BaseDecision(Organism orga, int... vars) {
		this.orga		= orga;
		this.variables	= vars;
	}

	public Organism getOrga()		{ return this.orga; }
	
	public int getVariablesLength()	{ return this.variables.length; }
	public int getVariable(int i)	{ return this.variables[i]; }

}

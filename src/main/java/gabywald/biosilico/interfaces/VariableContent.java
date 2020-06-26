package gabywald.biosilico.interfaces;

import gabywald.biosilico.model.Chemicals;

/**
 * This interface defines use of chemical variables in Agent's or sub-classes. 
 * @author Gabriel Chandesris (2009-2010)
 */
public interface VariableContent {
	/** To get the injstance of Variable's used. */
	public Chemicals getVariables();
	/** 
	public int getVariable(int i);
	public void setVariable(int i,int val);
	public void addToVariable(int i,int val);
	public void remtoVariable(int i,int val);
	*/
}

/**
	private / protected Variables variables;
	public Variables getVariables() { return this.variables; }
	public int getVariable(int i) { return this.variables.getVariable(i); }
	public void setVariable(int i,int val) 
		{ this.variables.setVariable(i, val); }
	public void addToVariable(int i,int val) 
		{ this.variables.setVarPlus(i, val); }
	public void remToVariable(int i,int val)
		{ this.variables.setVarLess(i,val); }
 */

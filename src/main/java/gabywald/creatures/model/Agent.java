package gabywald.creatures.model;


import java.util.ArrayList;
import java.util.List;

/**
 * This class defines 'object' which possesses some variables. 
 * @author Gabriel Chandesris (2013)
 * @see Variable
 */
public abstract class Agent {
	private List<Variable> variables;
	
	protected Agent() 
		{ this.variables = new ArrayList<Variable>(); }
	
	protected Agent(int numberOfVariables) 
		{ this.variables = new ArrayList<Variable>(numberOfVariables); }
	
	protected Agent(List<Variable> vars) 
		{ this.variables = vars; }
	
	public int lengthOfVariables() 
		{ return this.variables.size(); }
	
	public Variable getVariable(int i) {
		if ( (i < 0) || (i >= this.variables.size())) 
			{ return null; }
		return this.variables.get(i);
	}
	
	/** Execution of the Agent's (instance) behavior. */ 
	public abstract void run();
	/** TODO ?? make Agent as Runnable (interface to Thread). ?? */
}

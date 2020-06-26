package gabywald.biosilico.fourmis;

/**
 * To give a specific table of variables and tools to use it. 
 * XXX name of this class could be "Chemicals". 
 * At position 0, is the 'default variable' (for some reactions). 
 * @author Gabriel Chandesris (2009)
 */
public class Variables {
	/** The table of variables. */
	private int vars[];
	
	/** Default constructor (length of 1000). */
	public Variables() { 
		this.vars = new int[1000];
		for (int i = 0 ; i < this.vars.length ; i++) 
			{ this.vars[i] = 0; }
	}
	
	public int length() { return this.vars.length; }
	public int getVariable(int i) { return this.vars[i]; }
	public void setVariable(int i,int value) {
		if ( (i >= 0) && (i < this.vars.length) )
			{ this.vars[i] = value; }
		this.regulate(i);
	}
	
	/**
	 * To add val to variable at indice i. 
	 * @param i (int) indice of variable to change.
	 * @param val (int) value to add. 
	 */
	public void setVarPlus(int i, int val) {
		if ( (i >= 0) && (i < this.vars.length) )
			{ this.vars[i] += val; }
		this.regulate(i);
	}
	
	/**
	 * To add 1 at a specific indice. 
	 * @param i (int) indice of variable to change.
	 */
	public void setVarPlusPlus(int i) {
		if ( (i >= 0) && (i < this.vars.length) )
			{ this.vars[i]++; }
		this.regulate(i);
	}
	
	/**
	 * To remove val to variable at indice i. 
	 * @param i (int) indice of variable to change.
	 * @param val (int) value to remove.
	 */
	public void setVarLess(int i, int val) {
		if ( (i >= 0) && (i < this.vars.length) )
			{ this.vars[i] -= val; }
		this.regulate(i);
	}
	
	/**
	 * To remove 1 at a specific indice.
	 * @param i (int) indice of variable to change.
	 */
	public void setVarLessLess(int i) {
		if ( (i >= 0) && (i < this.vars.length) )
			{ this.vars[i]--; }
		this.regulate(i);
	}
	
	/**
	 * To "regulate" variables between 0 and 999. 
	 * @param i (int) variable to regulate. 
	 */
	private void regulate(int i) {
		if (this.vars[i] < 0) { this.vars[i] = 0; }
		if (this.vars[i] > 999) { this.vars[i] = 999; }
	}
	
	/**
	 * To add a Variables set added to current Variables.
	 * @param toSum (Variables) Vars to sum. 
	 */
	public void incorporate(Variables toSum) {
		for (int i = 0 ; i < this.vars.length ; i++) 
			{ this.vars[i] += toSum.getVariable(i);this.regulate(i); }
	}
}

package gabywald.biosilico.interfaces;

/**
 * To represent actions on 'chemicals'. 
 * @author Gabriel Chandesris (2020)
 */
public interface IChemicals {

	public int length();
	
	public int getVariable(int i);
	
	public void setVariable(int i, int value);
	
	/**
	 * To add val to variable at indice i. 
	 * @param i (int) indice of variable to change.
	 * @param val (int) value to add. 
	 */
	public void setVarPlus(int i, int val);
	
	/**
	 * To add 1 at a specific indice. 
	 * @param i (int) indice of variable to change.
	 */
	public void setVarPlusPlus(int i);
	
	/**
	 * To remove val to variable at indice i. 
	 * @param i (int) indice of variable to change.
	 * @param val (int) value to remove.
	 */
	public void setVarLess(int i, int val);
	
	/**
	 * To remove 1 at a specific indice.
	 * @param i (int) indice of variable to change.
	 */
	public void setVarLessLess(int i);
	
	/**
	 * To add a Variables set added to current Variables.
	 * @param toSum (Variables) Vars to sum. 
	 */
	public void incorporate(IChemicals toSum);
	
}

package gabywald.biosilico.interfaces;

/**
 * To represent actions on 'chemicals'. 
 * @author Gabriel Chandesris (2020, 2022)
 */
public interface IChemicals {

	/** Length / size of the Chemical repository. */
	public int length();
	
	/**
	 * Value of the variable. 
	 * @param i (int) Index. 
	 * @return (int)
	 */
	public int getVariable(int i);
	
	/** 
	 * To set a value to variable at index i. 
	 * @param i (int) index of variable to change. 
	 * @param val (int) value to set. 
	 */
	public void setVariable(int i, int value);
	
	/**
	 * To add val to variable at index i. 
	 * @param i (int) index of variable to change. 
	 * @param val (int) value to add. 
	 */
	public void setVarPlus(int i, int val);
	
	/**
	 * To add 1 at a specific index. 
	 * @param i (int) index of variable to change.
	 */
	public void setVarPlusPlus(int i);
	
	/**
	 * To remove val to variable at indice i. 
	 * @param i (int) indice of variable to change.
	 * @param val (int) value to remove.
	 */
	public void setVarLess(int i, int val);
	
	/**
	 * To remove 1 at a specific index.
	 * @param i (int) index of variable to change.
	 */
	public void setVarLessLess(int i);
	
	/**
	 * To add a IChemicals set added to current Variables.
	 * @param toSum (IChemicals) Variables to sum. 
	 */
	public void incorporate(IChemicals toSum);
	
}

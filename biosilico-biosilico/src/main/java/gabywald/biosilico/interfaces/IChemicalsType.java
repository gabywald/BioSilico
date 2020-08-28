package gabywald.biosilico.interfaces;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public interface IChemicalsType {
	
	/**
	 * Index value in IChemicals. 
	 * @return index. 
	 */
	public int getIndex();
	
	/**
	 * Name of the Type in IChemicals. 
	 * @return name. 
	 */
	public String getName();
	
	/**
	 * Definition / Description of the Type in IChemicals. 
	 * @return definition. 
	 */
	public String getDefinition();
	
}

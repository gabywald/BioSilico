package gabywald.biosilico.interfaces;

/**
 * This interface defines use of chemical variables in Agent's or sub-classes. 
 * @author Gabriel Chandesris (2009-2010, 2020)
 */
public interface IChemicalsContent {
	/** To get the instance of Variable's used. 
	 * @deprecated Use {@link IChemicalsContent#getChemicals()}
	 */
	public IChemicals getVariables();
	
	/** To get the instance of Variable's used. */
	public IChemicals getChemicals();
}

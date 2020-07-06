package gabywald.creatures.model;

/**
 * This class defines organs, containing specific variables, work a sub-part of GenomicAgent. 
 * @author Gabriel Chandesris (2013)
 * @see Variable
 */
public class Organ extends Agent {
	/** Name of the Organ. */
	private String name;
	
	/** 
	 * 'Default' constructor of an organ.
	 * @param name (String)
	 */
	public Organ(String name)
		{ super();this.name = name; }
	
	/**
	 * 'Precise' constructor of an organ. 
	 * @param name (String)
	 * @param numberOfVariables (int)
	 */
	public Organ(String name, int numberOfVariables)
		{ super(numberOfVariables);this.name = name; }
	
	public String getName()
		{ return this.name; }
		
	/** Agent of type Organ consumes their variables via half-life rules. */
	public void run() {
		/** DONE internal use of some variable (consumption / half-life). */
		for (int i = 0 ; i < this.lengthOfVariables() ; i++) 
			{ this.getVariable(i).consumption(); }
		/** TODO other changes ? */
	}
}

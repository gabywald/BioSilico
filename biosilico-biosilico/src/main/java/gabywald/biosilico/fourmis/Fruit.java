package gabywald.biosilico.fourmis;

/**
 * This class defines a specific element which is not alive 
 * but can be move and eat by agents in the system. 
 * Produced by plants, do not do anything either. 
 * @author Gabriel Chandesris (2009)
 * @see Plante#exportFruits(WorldCase)
 * @see Ant#eatFruit()
 * @see Ant#getFruit(WorldCase)
 * @see Ant#dropFruits(WorldCase)
 */
public class Fruit extends Agent {
	
	/**
	 * Default constructor for Fruit.
	 */
	public Fruit() 
		{ super(false,true,true); }
	
	/**
	 * Constructor of a Fruit which have only one variable to feed.  
	 * @param var (int)
	 * @param val (int)
	 * @see Variables#setVarPlus(int, int)
	 */
	public Fruit(int var,int val) { 
		super(false,true,true);
		this.variables.setVarPlus(var, val);
	}
	
	/** 
	 * Constructor with pre-established Variables set. 
	 * @param toFeed (Variables)
	 */
	public Fruit(Variables toFeed) {
		super(false,true,true);
		this.variables = toFeed;
	}

	public void execution(WorldCase local) { ; }
	public void deplace(WorldCase local) { ; }
	protected void biochemistery() { ; }
	protected void emitterReceptor(WorldCase local) { ; }
	protected void stimulus(WorldCase local) { ; }
}

package gabywald.biosilico.anthill;

import gabywald.biosilico.model.Chemicals;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.enums.AgentType;

/**
 * This class defines a specific element which is not alive but can be move and eat by agents in the system. 
 * <br />Produced by plants, do not do anything either. 
 * @author Gabriel Chandesris (2009, 2020)
 */
public class Fruit extends Organism {
	
	/**
	 * Default constructor for Fruit.
	 */
	public Fruit() { 
		super();
		this.setAlive( false );
		this.setEatable( true );
		this.setMovable( true );
		
		this.setNameCommon("Fruit");
		this.setOrganismType(AgentType.BIOSILICO_VIRIDITA);
	}
	
	/**
	 * Constructor of a Fruit which have only one variable to feed.  
	 * @param var (int)
	 * @param val (int)
	 * @see Chemicals#setVarPlus(int, int)
	 */
	public Fruit(int var, int val) { 
		this();
		this.variables.setVarPlus(var, val);
	}
	
	/** 
	 * Constructor with pre-established Variables set. 
	 * @param toFeed (Chemicals)
	 */
	public Fruit(Chemicals toFeed) {
		this();
		this.variables = toFeed;
	}

}

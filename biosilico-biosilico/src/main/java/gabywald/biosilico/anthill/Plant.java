package gabywald.biosilico.anthill;

import gabywald.biosilico.model.Organism;

/**
 * This class describes plants which produce fruits. 
 * @author Gabriel Chandesris (2009, 2020)
 * @see Fruit
 */
public class Plant extends Organism {

	/** Default Constructor. */
	public Plant() { 
		super();
		this.setAlive( true );
		this.setEatable( false );
		this.setMovable( false );
		
		this.setNameCommon("Plant");
		this.setOrganismType(OrganismType.BIOSILICO_VIRIDITA);
		
		// ***** Here creation of 'egg' => create fruits !
		
	}
	
}

package gabywald.biosilico.anthill;

import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.enums.AgentType;
import gabywald.biosilico.model.enums.ObjectType;
import gabywald.biosilico.model.enums.StatusType;

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
		this.setObjectType(ObjectType.AGENT);
		this.setAgentType(AgentType.BIOSILICO_VIRIDITA);
		this.setOrganismStatus(StatusType.EMBRYO);
		
		// ***** Here creation of 'egg' => create fruits !
		// ***** Take 'Solar Energy' to build some molecules !
		
	}
	
}

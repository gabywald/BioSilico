package gabywald.biosilico.anthill;

import gabywald.biosilico.model.Organism;

/**
 * 
 * @author Gabriel Chandesris (2009, 2020)
 */
public class Ant extends Organism {
	
	public enum AntStatus {
		EGG_LARVA, 	// (0, 1), 
		WORKER, 	// (2, 3, 4, 5, 6, 7), 
		SOLDIER, 	// (8), 
		QUEEN; 		// (9);
	}
	/**
	 * Status of ant. 
	 * <p></p>
	 * <ul>
	 * <li><b>0-1</b> : Egg / Larva</li>
	 * <li><b>2-7</b> : Worker</li>
	 * <li><b>8</b> : Soldier</li>
	 * <li><b>9</b> : Queen</li>
	 * </ul>
	 */
	private int status;

	/** Default constructor of an Ant agent (alive). */
	public Ant() { 
		super();
		this.setAlive( true );
		this.setEatable( false );
		this.setMovable( false );
		
		this.setNameCommon("Ant");
		this.setOrganismType(OrganismType.BIOSILICO_ANIMA);
		
		this.status		= 0; /** start with an egg. */
	}
	
}

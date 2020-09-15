package gabywald.biosilico.model.utils.agents;

import gabywald.biosilico.model.enums.SomeChemicals;

/**
 * To provide some Chemicals during tests (or for other purposes). 
 * @author Gabriel Chandesris (2020)
 */
public class Condensator extends UtilAgent {

	public static final int BASIC_ENERGY_LEVEL			= 25;
	
	public static final String COMMON_BIOSILICO_NAME	= "Condensator";
	
	/**
	 * Default Constructor (not alive, not movable, not eatable). 
	 */
	public Condensator() {
		this.setName( Condensator.COMMON_BIOSILICO_NAME );
		
		this.variables.setVariable(SomeChemicals.CARBON_DIOXYDE.getIndex(), Condensator.BASIC_ENERGY_LEVEL);
		this.variables.setVariable(SomeChemicals.WATER.getIndex(), Condensator.BASIC_ENERGY_LEVEL);
	}

}

package gabywald.biosilico.model.utils.agents;

import gabywald.biosilico.model.enums.AgentType;
import gabywald.biosilico.model.enums.ObjectType;
import gabywald.biosilico.model.enums.StatusType;

/**
 * Convert "Plant EGG" (fruits) to FOOD (as object status). 
 * @author Gabriel Chandesris (2020)
 */
public class ConverterPlantEgg2Fruit extends Converter {
	
	public ConverterPlantEgg2Fruit() {
		super(AgentType.BIOSILICO_VIRIDITA, StatusType.EGG, ObjectType.FOOD);
	}

}

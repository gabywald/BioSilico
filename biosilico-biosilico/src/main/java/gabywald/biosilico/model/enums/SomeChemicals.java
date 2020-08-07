package gabywald.biosilico.model.enums;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public enum SomeChemicals implements IChemicalsType {
	DIOXYGEN		(180, "O2", 	"O2"), 
	CARBON_DIOXYDE	(181, "CO2", 	"CO2"), 
	WATER			(182, "H2O", 	"DiHydrogen Monoxid (Water / Eau)"), 
	
	ENERGY_SOLAR	(500, 	"Solar Energy", "solar energy"), 
	ENERGY_HEAT		(501, 	"Heat Energy", 	"heat energy"), 
	
	GAMET			(919, 	"Gamet", 		"gamet"), 
	EGG				(920, 	"Egg", 			"egg"), 
	
	PHEROMONE_00	(650, "Pheromone00", "pheromonone 00"), 
	PHEROMONE_01	(651, "Pheromone01", "pheromonone 01"),
	PHEROMONE_02	(652, "Pheromone02", "pheromonone 02"),
	PHEROMONE_03	(653, "Pheromone03", "pheromonone 03"),
	PHEROMONE_04	(654, "Pheromone04", "pheromonone 04"),
	PHEROMONE_05	(655, "Pheromone05", "pheromonone 05"),
	PHEROMONE_06	(656, "Pheromone06", "pheromonone 06"),
	PHEROMONE_07	(657, "Pheromone07", "pheromonone 07"),
	PHEROMONE_08	(658, "Pheromone08", "pheromonone 08"),
	PHEROMONE_09	(659, "Pheromone09", "pheromonone 09"),
	;
	
	private int index;
	private String name, definition;

	private SomeChemicals(int index, String name, String definition) {
		this.index		= index;
		this.name		= name;
		this.definition	= definition;
	}

	@Override
	public int getIndex()			{ return index; }
	@Override
	public String getName()			{ return name; }
	@Override
	public String getDefinition()	{ return definition; }
}

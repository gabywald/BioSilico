package gabywald.biosilico.model.enums;

import gabywald.biosilico.interfaces.IChemicalsType;

/**
 * Enumeration with some 'easy accessible' checmicals and indexes (under 600). 
 * @author Gabriel Chandesris (2020)
 */
public enum SomeChemicals implements IChemicalsType {
	/** For this one see : ChemicalsHelper.CHEMICAL_STRICT_CHEM.getIndex(). */
	NEUTRAL			(600, "Neutral", "Neutral In Reactions"), 
	
	PHOSPHOR		( 15, "P", 		"Phosphor"), 
	
	ATP				(150, "ATP", 	"ATP"), 
	ADP				(151, "ADP", 	"ADP"), 
	AMP				(152, "AMP", 	"AMP"), 
	
	GLUCOSE			(169, "G6", 	"Glucose"), 
	FRUCTOSE		(170, "F5", 	"Fructose"), 
	G6P				(171, "G6P", 	"Glucose-6-Phosphate"), 
	F6P				(172, "F6P", 	"Fructose-6-Phosphate"), 
	
	DIOXYGEN		(180, "O2", 		"O2"), 
	CARBON_DIOXYDE	(181, "CO2", 		"CO2"), 
	WATER			(182, "H2O", 		"DiHydrogen Monoxid (Water / Eau)"), 
	HYDROXYD		(183, "HO-", 		"Hydroxyd"), 
	ACETYLCOA		(184, "AcetylCoA", 	"AcetylCoA"), 
	
	STARCH			(330, "Starch",			"Starch (Amidon)"), 
	HEXOKINASE		(331, "Hexokinase",		"Hexokinase"), 
	GLYCOGEN		(332, "Glycogen",		"Glycogen"), 
	COMBINATOR_1	(333, "Combinator1",	"6 H2O + 6 CO2"), 
	COMBINATOR_2	(334, "Combinator2",	"5 H20 + 5 C02"), 
	COMBINATOR_3	(335, "Combinator3",	"P + NRJ"), 
	
	ENERGY_SOLAR	(390, "Solar Energy", 	"solar energy"), 
	ENERGY_HEAT		(391, "Heat Energy", 	"heat energy"), 
	
	PHEROMONE_00	(350, "Pheromone00", "pheromonone 00"), 
	PHEROMONE_01	(351, "Pheromone01", "pheromonone 01"),
	PHEROMONE_02	(352, "Pheromone02", "pheromonone 02"),
	PHEROMONE_03	(353, "Pheromone03", "pheromonone 03"),
	PHEROMONE_04	(354, "Pheromone04", "pheromonone 04"),
	PHEROMONE_05	(355, "Pheromone05", "pheromonone 05"),
	PHEROMONE_06	(356, "Pheromone06", "pheromonone 06"),
	PHEROMONE_07	(357, "Pheromone07", "pheromonone 07"),
	PHEROMONE_08	(358, "Pheromone08", "pheromonone 08"),
	PHEROMONE_09	(359, "Pheromone09", "pheromonone 09"),
	
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

package gabywald.biosilico.model.enums;

import gabywald.biosilico.interfaces.IChemicalsType;

/**
 * For applyance for Variable 'ch942' (STATUS). 
 * @author Gabriel Chandesris (2020)
 * @see StateType#STATUS
 */
public enum StatusType implements IChemicalsType {
	GAMET			(920, 	"Gamet", 		"gamet"), 
	EGG				(921, 	"Egg", 			"egg"), 
	EMBRYO			(922, 	"Embryo", 		"embryo"), 
	LARVA			(923, 	"Larva", 		"larva"), 
	CHILD			(924, 	"Child", 		"child"), 
	TEEN			(925, 	"Teen", 		"teen"), 
	ADULT			(926, 	"Adult", 		"adult"), 
	SENIOR			(927, 	"Senior", 		"senior"), 
	DEAD			(928, 	"Dead", 		"dead"), 
	NOT_ACCURATE	(929, 	"Not Accurate", "notaccurate"), 
	;

	private int index;
	private String name, definition;

	private StatusType(int index, String name, String definition) {
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
	
	public static StatusType getFrom(int index) {
		for (StatusType at : StatusType.values()) {
			if (index == at.index) 
				{ return at; }
		}
		return null;
	}

}

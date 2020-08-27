package gabywald.biosilico.model.enums;

import gabywald.biosilico.interfaces.IChemicalsType;

/**
 * For applyance for Variable 'ch942' (STATUS). 
 * @author Gabriel Chandesris (2020)
 * @see StateType#STATUS
 */
public enum StatusType implements IChemicalsType {
	GAMET			(919, 	"Gamet", 		"gamet"), 
	EGG				(920, 	"Egg", 			"egg"), 
	EMBRYO			(921, 	"Embryo", 		"embryo"), 
	LARVA			(922, 	"Larva", 		"larva"), 
	CHILD			(923, 	"Child", 		"child"), 
	TEEN			(924, 	"Teen", 		"teen"), 
	ADULT			(925, 	"Adult", 		"adult"), 
	SENIOR			(926, 	"Senior", 		"senior"), 
	DEAD			(927, 	"Dead", 		"dead"), 
	NOT_ACCURATE	(928, 	"Not Accurate", "notaccurate"), 
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

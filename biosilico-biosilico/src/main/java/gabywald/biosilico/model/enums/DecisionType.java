package gabywald.biosilico.model.enums;

import gabywald.biosilico.interfaces.IChemicalsType;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public enum DecisionType implements IChemicalsType {
	STAY		(850, "Stay", 		"stay"), 
	PUSH		(851, "Push", 		"push"), 
	PULL		(852, "Pull", 		"pull"), 
	STOP		(853, "Stop", 		"stop"), 
	MOVE_TO		(854, "Move to",	"move to"), 
	MOVE_AWAY	(855, "Move Away", 	"move away"), 
	GET			(856, "Get", 		"get"), 
	DROP		(857, "Drop", 		"drop"), 
	THINK		(858, "Think", 		"think"), 
	SLAP		(859, "Slap", 		"slap"), 
	REST		(860, "Rest", 		"rest"), 
	SLEEP		(861, "Sleep", 		"sleep"), 
	EAT			(862, "Eat", 		"eat"), 
	DEATH		(863, "Death", 		"death"), 
	EMIT		(864, "Emit", 		"emit"), 
	RECEIVE		(865, "Receive", 	"receive"), 
	HAS			(866, "Has", 		"has"), 
	IS			(867, "Is", "		is"), 
	MAKE_GAMET	(868, "Make Gamet", "make gamet"), 
	LAY_EGG		(869, "LayEgg", 	"lay egg"), 
	MATE		(870, "Mate", 		"mate"), 
	CREATE_EGG	(871, "Create Egg", "create egg"), 
	// ***** XXX NOTE : 872 to 880 are free. 
	;
	
	private int index;
	private String name, definition;

	private DecisionType(int index, String name, String definition) {
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
	
	/**
	 * To select a DecisionType according to given index. 
	 * @param index (int)
	 * @return Corresponding DecisionType, could be null. 
	 */
	public static DecisionType getValue(int index) {
		for (DecisionType dt : DecisionType.values()) {
			if (dt.index == index) {
				return dt;
			}
		}
		return null;
	}
}

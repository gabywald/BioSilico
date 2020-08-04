package gabywald.biosilico.model.enums;

/**
 * For Direct appliance for corresponding variables indexes. 
 * @author Gabriel Chandesris (2020)
 */
public enum StateType implements IChemicalsType {
	GENDER  	(939, "Gender", 	"gender"), 
	AGING   	(940, "Aging", 		"aging"), 
	TYPEOF  	(941, "TypeOf", 	"typeof"), 
	STATUS		(942, "Status", 	"status"), 
	MOVABLE		(943, "Movable", 	"movable"), 
	EATABLE 	(944, "Eatable", 	"eatable"), 
	FERTILE 	(945, "Fertile", 	"fertile"), 
	PREGNANT	(946, "Pregnant", 	"pregnant"), 
	;
	
	private int index;
	private String name, definition;

	private StateType(int index, String name, String definition) {
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

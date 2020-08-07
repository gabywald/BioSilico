package gabywald.biosilico.model.enums;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public enum ObjectType implements IChemicalsType {
	CURRENT		(900, "Current", 	"current"), 
	SMALL_ELT	(901, "Small Elt", 	"smallelt"), 
	MIDD_ELT	(902, "Midd Elt", 	"middelt"), 
	BIG_ELT		(903, "Big Elt", 	"bigelt"), 
	FOOD		(904, "Food", 		"food"), 
	DRINK		(905, "Drink", 		"drink"), 
	VEHICLE		(906, "Vehicle", 	"vehicle"), 
	AUTOMATON	(907, "Automaton", 	"automaton"), 
	COMPUTER	(908, "Computer", 	"computer"), 
	LAPTOP		(909, "Laptop", 	"laptop"), 
	PHONE		(910, "Phone", 		"phone"), 
	CELLPHONE	(911, "CellPhone", 	"cellphone"), 
	ANT			(912, "Ant", 		"ant");
	
	private int index;
	private String name, definition;

	private ObjectType(int index, String name, String definition) {
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

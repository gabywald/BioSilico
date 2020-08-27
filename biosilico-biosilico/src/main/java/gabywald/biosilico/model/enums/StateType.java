package gabywald.biosilico.model.enums;

import gabywald.biosilico.interfaces.IChemicalsType;

/**
 * For Direct appliance for corresponding variables indexes. 
 * @author Gabriel Chandesris (2020)
 */
public enum StateType implements IChemicalsType {
	GENDER  	(939, "Gender", 	"gender"), 
	AGING   	(940, "Aging", 		"aging"), 
	AGENT_TYPE 	(941, "AgentType", 	"agenttype"), 
	TYPEOF  	(942, "TypeOf", 	"typeof"), 
	STATUS		(943, "Status", 	"status"), 
	MOVABLE		(944, "Movable", 	"movable"), 
	EATABLE 	(945, "Eatable", 	"eatable"), 
	FERTILE 	(946, "Fertile", 	"fertile"), 
	PREGNANT	(947, "Pregnant", 	"pregnant"), 
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

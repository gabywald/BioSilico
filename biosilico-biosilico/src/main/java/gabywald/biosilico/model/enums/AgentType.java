package gabywald.biosilico.model.enums;

import gabywald.biosilico.interfaces.IChemicalsType;

/**
 * For applyance for Variable 'ch941' (TYPEOF). 
 * @author Gabriel Chandesris (2020)
 * @see StateType#TYPEOF
 */
public enum AgentType implements IChemicalsType {
	BIOSILICO_DAEMON	(940, "Daemon", "daemon"), 
	BIOSILICO_BACTA		(941, "Bacta", "bacta"), 
	BIOSILICO_VIRIDITA	(942, "Plant", "plant"), 
	BIOSILICO_ANIMA		(943, "Anima", "anima"), 
	BIOSILICO_VIRIA		(944, "Virus", "virus"), 
	;
	
	private int index;
	private String name, definition;

	private AgentType(int index, String name, String definition) {
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

	public static AgentType getFrom(int index) {
		for (AgentType at : AgentType.values()) {
			if (index == at.index) 
				{ return at; }
		}
		return null;
	}
	
}

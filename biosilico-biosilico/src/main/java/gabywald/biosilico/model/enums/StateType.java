package gabywald.biosilico.model.enums;

import java.util.Arrays;
import java.util.List;

import gabywald.biosilico.interfaces.IChemicalsType;

/**
 * For Direct appliance for corresponding variables indexes. 
 * @author Gabriel Chandesris (2020)
 */
public enum StateType implements IChemicalsType {
	GENDER  	(950, "Gender", 	"gender"), 
	AGING   	(951, "Aging", 		"aging"), 
	AGENT_TYPE 	(952, "AgentType", 	"agenttype"), 
	TYPEOF  	(953, "TypeOf", 	"typeof"), 
	STATUS		(954, "Status", 	"status"), 
	MOVABLE		(955, "Movable", 	"movable"), 
	EATABLE 	(956, "Eatable", 	"eatable"), 
	FERTILE 	(957, "Fertile", 	"fertile"), 
	PREGNANT	(958, "Pregnant", 	"pregnant"), 
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
	
	/**
	 * To get indexes of elements of enum which are numeric !
	 * @return Indexes of {AGING, EATABLE, FERTILE, MOVABLE, GENDER, MOVABLE, PREGNANT}. 
	 */
	public static List<Integer> numericList() {
		return Arrays.asList(	StateType.AGING.index, StateType.EATABLE.index, StateType.FERTILE.index, 
								StateType.GENDER.index, StateType.MOVABLE.index, StateType.PREGNANT.index);
	}
}

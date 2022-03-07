package gabywald.biosilico.model.environment;

import java.util.ArrayList;
import java.util.List;

import gabywald.biosilico.interfaces.IAgentContent;
import gabywald.biosilico.interfaces.IChemicals;
import gabywald.biosilico.interfaces.IEnvironment;
import gabywald.biosilico.interfaces.IEnvironmentItem;
import gabywald.biosilico.interfaces.IPosition;
import gabywald.biosilico.model.Agent;
import gabywald.biosilico.model.chemicals.ChemicalsBuilder;
import gabywald.biosilico.model.enums.AgentType;
import gabywald.biosilico.model.enums.DirectionWorld;
import gabywald.biosilico.model.enums.ObjectType;
import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.enums.StatusType;

/**
 * This classe defines elements of the simulation environment where Agent's evolve. 
 * @author Gabriel Chandesris (2009, 2020, 2022)
 */
public class World2DCase implements IEnvironmentItem {
	/** Chemical list of current element. */
	private IChemicals variables;
	/** Set of Agents and items in current element. */ 
	private List<Agent> liste;
	/** Environment where this current element is included in.  */
	private World2D world;
	/** Position of this element (x -> height, y -> width). */
	private IPosition pos;
	
	private String name = null;
	
	/** Default constructor of element of environment. */
	public World2DCase() {
		this(null, PositionBuilder.buildPosition(-1, -1) );
	}
	
	/**
	 * Constructor of an element of environment with given global. 
	 * @param world (World) Global environment. 
	 * @param position (IPosition) position in environment. 
	 */
	public World2DCase(World2D world, IPosition position) {
		this.variables	= ChemicalsBuilder.build();
		this.liste		= new ArrayList<Agent>();
		this.world		= world;
		this.pos		= position;
		
		// Setting a default name !
		this.setName( ( (this.world != null) ? this.world.getName() : "Isolated")
						+ ":" + 
						( (this.pos != null) ? this.pos.toString() : "No Position !") );
	}
	
	@Override
	public String getName() 
		{ return this.name; }
	
	public void setName(String name) 
		{ this.name = name; }
	
	@Override
	public IPosition getPosition()			{ return this.pos; }
	
	@Override
	public IEnvironment getEnvironment()	{ return this.world; }
	
	/**
	 * To get a WorldCase in a given direction of environment. 
	 * @param dir (DirectionWorld) direction. 
	 * @return (Worldcase) Can be null. 
	 */
	@Override
	public IEnvironmentItem getDirection(DirectionWorld dir) { 
		return (this.world == null) ? null : this.world.getDirection(dir, this.pos); 
	}
	
	@Override
	public IChemicals getChemicals()	{ return this.variables; }
	@Override
	public int getAgentListLength()		{ return this.liste.size(); }
	@Override
	public List<Agent> getAgentListe()	{ return this.liste; }
	@Override
	public Agent remAgent(int i)		{ return this.liste.remove(i); }
	@Override
	public boolean remAgent(Agent o)	{ return this.liste.remove(o); }
	@Override
	public Agent getAgent(int i)		{ return this.liste.get(i); }	
	
	@Override
	public int hasObjectType(ObjectType type) {
		if (type == null) { return -1; }
		return IAgentContent.hasType(type, StateType.TYPEOF.getIndex(), this.liste);
	}
	
	@Override
	public Agent getObjectType(ObjectType type) {
		if (type == null) { return null; }
		return IAgentContent.getType(type, StateType.TYPEOF.getIndex(), this.liste);
	}
	
	@Override
	public int hasAgentType(AgentType type) {
		return IAgentContent.hasType(type, StateType.AGENT_TYPE.getIndex(), this.liste);
	}
	
	@Override
	public Agent getAgentType(AgentType type) {
		return IAgentContent.getType(type, StateType.AGENT_TYPE.getIndex(), this.liste);
	}
	
	@Override
	public int hasAgentStatus(StatusType status) {
		return IAgentContent.hasType(status, StateType.STATUS.getIndex(), this.liste);
	}
	
	@Override
	public Agent getAgentStatus(StatusType status) {
		return IAgentContent.getType(status, StateType.STATUS.getIndex(), this.liste);
	}
	
	@Override
	public int hasAgentWithName(String name) 
		{ return IAgentContent.hasName(name, this.liste); }

	@Override
	public Agent getAgentWithName(String name) 
		{ return IAgentContent.getName(name, this.liste); }
	
	@Override
	public void addAgent(Agent object) { 
		if (object != null) { this.liste.add(object); } 
		// ***** setting current WC already done in Agent !
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("POSITION\t").append(this.getPosition().getPosX()).append("\t")
									.append(this.getPosition().getPosY()).append("\n");
		result.append("AGENT LIST\n");
		if (this.liste.size() != 0) {
			this.liste.stream().forEach( ag -> result.append( "\t" ).append( ag.getScientificName() ).append(" (").append( ag.getUniqueID() ).append(")\n") );
		}
		result	.append("CHEMICAL VARIABLES\n").append(this.variables.toString()).append( "\n" );
		return result.toString();
	}

}

package gabywald.biosilico.model;

import java.util.ArrayList;
import java.util.List;

import gabywald.biosilico.interfaces.IAgentContent;
import gabywald.biosilico.interfaces.IChemicals;
import gabywald.biosilico.interfaces.IChemicalsContent;
import gabywald.biosilico.model.chemicals.ChemicalsBuilder;
import gabywald.biosilico.model.enums.AgentType;
import gabywald.biosilico.model.enums.DirectionWorld;
import gabywald.biosilico.model.enums.ObjectType;
import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.enums.StatusType;

/**
 * This classe defines elements of the simulation environment where Agent's evolve. 
 * @author Gabriel Chandesris (2009, 2020)
 */
public class WorldCase implements IChemicalsContent, IAgentContent {
	/** Chemical list of current element. */
	private IChemicals variables;
	/** Set of Agents and items in current element. */ 
	private List<Agent> liste;
	/** Environment where this current element is included in.  */
	private World world;
	/** Position of this element (x -> height, y -> width). */
	private Position pos;
	
	/** Default constructor of element of environment. */
	public WorldCase() {
		this (null, -1, -1);
	}
	
	/**
	 * Constructor of an element of environment with given global. 
	 * @param world (World) Global environment. 
	 */
	public WorldCase(World world, int posx, int posy) {
		this.variables	= ChemicalsBuilder.build();
		this.liste		= new ArrayList<Agent>();
		this.world		= world;
		this.pos		= new Position(posx, posy);
	}
	
	public int getPosX()	{ return this.pos.getPosX(); }
	public int getPosY()	{ return this.pos.getPosY(); }
	
	public World getWorld()	{ return this.world; }
	
	/**
	 * To get a WorldCase in a given direction of environment. 
	 * @param dir (int) direction (from 800 to 829)
	 * @return (Worldcase) Can be null. 
	 */
	public WorldCase getDirection(int dir) { 
		return (this.world == null) ? null : this.world.getDirection(dir, this.pos.getPosX(), this.pos.getPosY()); 
	}
	
	/**
	 * To get a WorldCase in a given direction of environment. 
	 * @param dir (DirectionWorld) direction. 
	 * @return (Worldcase) Can be null. 
	 */
	public WorldCase getDirection(DirectionWorld dir) { 
		return (this.world == null) ? null : this.world.getDirection(dir, this.pos.getPosX(), this.pos.getPosY()); 
	}
	
	@Override
	public IChemicals getChemicals()	{ return this.variables; }
	@Override
	public IChemicals getVariables()	{ return this.variables; }
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
		return IAgentContent.hasType(type, StateType.TYPEOF.getIndex(), this.liste);
	}
	
	@Override
	public Agent getObjectType(ObjectType type) {
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
	public void addAgent(Agent object) { 
		if (object != null) { this.liste.add(object); } 
		// ***** setting current WC already done in Agent !
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("POSITION\t").append(this.getPosX()).append("\t").append(this.getPosY()).append("\n");
		result.append("AGENT LIST\n");
		if (this.liste.size() == 0)
			{ result.append("\tNO DATA\n"); }
		else {
			this.liste.stream().forEach( ag -> result.append( "\t" ).append( ag.getScientificName() ).append(" (").append( ag.getUniqueID() ).append(")\n") );
		}
		result	.append("CHEMICAL VARIABLES\n").append(this.variables.toString()).append( "\n" );
		return result.toString();
	}

}

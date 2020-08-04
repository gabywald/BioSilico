package gabywald.biosilico.model;

import java.util.ArrayList;
import java.util.List;

import gabywald.biosilico.interfaces.AgentContent;
import gabywald.biosilico.interfaces.VariableContent;
import gabywald.biosilico.model.enums.AgentType;
import gabywald.biosilico.model.enums.DirectionWorld;
import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.enums.StatusType;

/**
 * This classe defines elements of the simulation environment where Agent's evolve. 
 * @author Gabriel Chandesris (2009, 2020)
 */
public class WorldCase implements VariableContent, AgentContent {
	/** Chemical list of current element. */
	private Chemicals variables;
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
		this.variables	= new Chemicals();
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
	public Chemicals getVariables()		{ return this.variables; }
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
	public int hasAgentType(AgentType type) {
		return (int)this.liste.stream().filter( a -> (a.getChemicals().getVariable(StateType.TYPEOF.getIndex()) == type.getIndex()) ).count();
	}
	
	@Override
	public Agent getAgentType(AgentType type) {
		if (this.liste.stream().anyMatch( p -> p.getChemicals().getVariable(StateType.TYPEOF.getIndex()) == type.getIndex() )) {
			for (int i = 0 ; i < this.liste.size() ; i++) {
				if (this.liste.get(i).getChemicals().getVariable(StateType.TYPEOF.getIndex()) == type.getIndex()) { 
					return this.liste.get(i);
				}
			}
		}
		return null;
	}
	
	@Override
	public int hasAgentStatus(StatusType type) {
		return (int)this.liste.stream().filter( a -> (a.getChemicals().getVariable(StateType.STATUS.getIndex()) == type.getIndex()) ).count();
	}
	
	@Override
	public Agent getAgentStatus(StatusType type) {
		if (this.liste.stream().anyMatch( p -> p.getChemicals().getVariable(StateType.STATUS.getIndex()) == type.getIndex() )) {
			for (int i = 0 ; i < this.liste.size() ; i++) {
				if (this.liste.get(i).getChemicals().getVariable(StateType.STATUS.getIndex()) == type.getIndex()) { 
					return this.liste.get(i);
				}
			}
		}
		return null;
	}
	
	@Override
	public void addAgent(Agent object) { 
		if (object != null) { this.liste.add(object); } 
		// ***** setting current WC already done in Agent !
	}

}

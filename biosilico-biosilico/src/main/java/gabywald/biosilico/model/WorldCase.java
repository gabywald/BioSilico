package gabywald.biosilico.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import gabywald.biosilico.interfaces.AgentContent;
import gabywald.biosilico.interfaces.VariableContent;

/**
 * This classe defines elements of the simulation environment where Agent's evolve. 
 * @author Gabriel Chandesris (2009, 2020)
 */
public class WorldCase implements VariableContent,AgentContent {
	/** Chemical list of current element. */
	private Chemicals variables;
	/** Set of Agents and items in current element. */ 
	private List<Agent> liste;
	/** Environment where this current element is included in.  */
	private World world;
	/** Position of this element (x -> height, yb -> width). */
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
	
	public void setPosX(int posx) { this.pos.setPosX( posx ); }
	public void setPosY(int posy) { this.pos.setPosY( posy ); }
	
	public int getPosX() { return this.pos.getPosX(); }
	public int getPosY() { return this.pos.getPosY(); }
	
	public World getWorld() { return this.world; }
	
	/**
	 * To get a WorldCase in a given direction of environment. 
	 * @param dir (int) direction (from 800 to 829)
	 * @return (Worldcase) Can be null. 
	 */
	public WorldCase getDirection(int dir) { 
		return (this.world == null) ? null : this.world.getDirection(dir, this.pos.getPosX(), this.pos.getPosY()); 
	}
	
	public Chemicals getVariables()		{ return this.variables; }
	
	public int getAgentListLength()		{ return this.liste.size(); }
	public List<Agent> getAgentListe()	{ return this.liste; }
	public Agent remAgent(int i)		{ return this.liste.remove(i); }
	public boolean remAgent(Agent o)	{ return this.liste.remove(o); }
	public Agent getAgent(int i)		{ return this.liste.get(i); }	
	
	public int hasAgentType(int type) {
		return (int) this.liste.stream().filter( a -> (a.getChemicals().getVariable(942) == type) ).count();
	}
	
	public Agent getAgentType(int type) {
		// TODO optimize with Java 8 stream ?!
		if (this.liste.stream().anyMatch( p -> p.getChemicals().getVariable(942) == type )) {
			
			Optional<Agent> optAgent = this.liste.stream().filter( ( p -> p.getChemicals().getVariable(942) == type ) ).findAny();
			if (optAgent.isPresent()) {
				return optAgent.get();
			}
		}
		return null;
	} 
	
	public void addAgent(Agent object) { 
		if (object != null) { this.liste.add(object); } 
	}
}

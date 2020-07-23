package gabywald.biosilico.model;

import java.util.ArrayList;
import java.util.List;

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
	/** Height position of this element. */
	private int posx;
	/** width position of this element. */
	private int posy;
	
	/** Default constructor of element of environment. */
	public WorldCase() {
		this.variables	= new Chemicals();
		this.liste		= new ArrayList<Agent>();
		this.world		= null;
		this.posx		= -1;
		this.posy		= -1;
	}
	
	/**
	 * Constructor of an element of environment with given global. 
	 * @param world (World) Global environment. 
	 */
	public WorldCase(World world) {
		this.variables	= new Chemicals();
		this.liste		= new ArrayList<Agent>();
		this.world		= world;
		this.posx		= World.MAX_HEIGHT/2;
		this.posy		= World.MAX_WIDTH/2;
	}
	
	public void setPosX(int posx) { this.posx = posx; }
	public void setPosY(int posy) { this.posy = posy; }
	
	/**
	 * To get a WorldCase in a given direction of environment. 
	 * @param dir (int) direction (from 800 to 829)
	 * @return (Worldcase) Can be null. 
	 */
	public WorldCase getDirection(int dir) { 
		return (this.world == null) ? null : this.world.getDirection(dir, this.posx, this.posy); 
	}
	
	public Chemicals getVariables()		{ return this.variables; }
	
	public int getAgentListLength()		{ return this.liste.size(); }
	public List<Agent> getAgentListe()	{ return this.liste; }
	public Agent remAgent(int i)		{ return this.liste.remove(i); }
	public Agent getAgent(int i)		{ return this.liste.get(i); }	
	
	public int hasAgentType(int type) {
		return (int) this.liste.stream().filter( a -> (a.getChemicals().getVariable(942) == type) ).count();
	}
	
	public Agent getAgentType(int type) {
		// TODO optimize with Java 8 stream ?!
		for (int i = 0 ; i < this.liste.size() ; i++) {
			if (this.liste.get(i).getChemicals().getVariable(942) == type) { 
				return this.liste.remove(i);
			}
		}
		return null;
	} 
	
	public void addAgent(Agent object) { 
		if (object != null) { this.liste.add(object); } 
	}
}

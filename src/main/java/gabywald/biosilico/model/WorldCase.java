package gabywald.biosilico.model;

import gabywald.biosilico.interfaces.AgentContent;
import gabywald.biosilico.interfaces.VariableContent;
import gabywald.biosilico.structures.AgentListe;

/**
 * This classe defines elements of the simulation environment where Agent's evolve. 
 * @author Gabriel Chandesris (2009)
 */
public class WorldCase implements VariableContent,AgentContent {
	/** Chemical list of current element. */
	private Chemicals variables;
	/** Set of Agents and items in current element. */ 
	private AgentListe liste;
	/** Environment where this current element is included in.  */
	private World world;
	/** Height position of this element. */
	private int posx;
	/** width position of this element. */
	private int posy;
	
	/** Default constructor of element of environment. */
	public WorldCase() {
		this.variables = new Chemicals();
		this.liste = new AgentListe();
		this.world = null;
		this.posx = -1;this.posy = -1;
	}
	
	/**
	 * Constructor of an element of environment with given global. 
	 * @param world (World) Global environment. 
	 */
	public WorldCase(World world) {
		this.variables = new Chemicals();
		this.liste = new AgentListe();
		this.world = world;
		this.posx = World.MAX_HEIGHT/2;
		this.posy = World.MAX_WIDTH/2;
	}
	
	public void setPosX(int posx) { this.posx = posx; }
	public void setPosY(int posy) { this.posy = posy; }
	
	/**
	 * To get a WorldCase in a given direction of environment. 
	 * @param dir (int) direction (from 800 to 829)
	 * @return (Worldcase) Can be null. 
	 */
	public WorldCase getDirection(int dir) 
		{ return (this.world == null)?null:
			this.world.getDirection(dir,this.posx,this.posy); }
	
	public Chemicals getVariables() { return this.variables; }
	/**
	public int getVariable(int i) { return this.variables.getVariable(i); }
	public void setVariable(int i,int val) 
		{ this.variables.setVariable(i, val); }
	public void addToVariable(int i,int val) 
		{ this.variables.setVarPlus(i, val); }*/ 
	
	public int getAgentListLength() { return this.liste.length(); }
	public AgentListe getAgentListe() { return this.liste; }
	public void remAgent(int i) { this.liste.removeAgent(i); }
	public Agent getAgent(int i) { return this.liste.getAgent(i); }	
	
	public int hasAgentType(int type) {
		int count  = 0;
		for (int i = 0 ; i < this.liste.length() ; i++) {
			if (this.liste.getAgent(i).getChemicals()
					.getVariable(942) == type) { count ++; }
		}
		return count;
	}
	
	public Agent getAgentType(int type) {
		for (int i = 0 ; i < this.liste.length() ; i++) {
			if (this.liste.getAgent(i).getChemicals()
					.getVariable(942) == type) { 
				Agent tmp = this.liste.getAgent(i);
				this.liste.removeAgent(i);
				return tmp;
			}
		}
		return null;
	} 
	
	public void addAgent(Agent object) 
		{ if (object != null) { this.liste.addAgent(object); } }
}

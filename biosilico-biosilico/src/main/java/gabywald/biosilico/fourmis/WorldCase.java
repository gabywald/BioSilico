package gabywald.biosilico.fourmis;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Gabriel Chandesris (2009, 2020)
 */
public class WorldCase implements VariableContent, AgentContent {
	private Variables variables;
	private List<Agent> liste;
	private World world;
	private int posx;
	private int posy;
	
	public WorldCase() {
		this(null, -1, -1);
	}
	
	public WorldCase(World world) {
		this(world, World.HEIGHT / 2, World.WIDTH / 2);
	}
	
	private WorldCase(World world, int posx, int posy) {
		this.variables	= new Variables();
		this.liste		= new ArrayList<Agent>();
		this.world		= world;
		this.posx		= posx;
		this.posy		= posy;
	}
	
	public boolean hasFruit() {
		for (int i = 0 ; i < this.liste.size() ; i++) {
			if (this.liste.get(i).isEatable())
				{ return true; }
		}
		return false;
	}
	
	public void setPosX(int posx) { this.posx = posx; }
	public void setPosY(int posy) { this.posy = posy; }
	
	public WorldCase getNW() 
		{ return (this.world == null) ? null : this.world.getNW(this.posx, this.posy); }
	public WorldCase getNN() 
		{ return (this.world == null) ? null : this.world.getNorth(this.posx, this.posy); }
	public WorldCase getNE() 
		{ return (this.world == null) ? null : this.world.getNE(this.posx, this.posy); }
	public WorldCase getEE() 
		{ return (this.world == null) ? null : this.world.getEast(this.posx, this.posy); }
	public WorldCase getSE() 
		{ return (this.world == null) ? null : this.world.getSE(this.posx, this.posy); }
	public WorldCase getSS() 
		{ return (this.world == null) ? null : this.world.getSouth(this.posx, this.posy); }
	public WorldCase getSW() 
		{ return (this.world == null) ? null : this.world.getSW(this.posx, this.posy); }
	public WorldCase getWW() 
		{ return (this.world == null) ? null : this.world.getWest(this.posx, this.posy); }
	
	public Variables getVariables()				{ return this.variables; }
	public int getVariable(int i)				{ return this.variables.getVariable(i); }
	public void setVariable(int i, int val)		{ this.variables.setVariable(i, val); }
	public void addToVariable(int i, int val)	{ this.variables.setVarPlus(i, val); }
	
	public int getAgentListLength()		{ return this.liste.size(); }
	public List<Agent> getAgentListe()	{ return this.liste; }
	public void addAgent(Agent elt)		{ this.liste.add(elt); }
	public void remAgent(int i)			{ this.liste.remove(i); }
	public Agent getAgent(int i)		{ return this.liste.get(i); }
	
	public boolean isAgentAlive(int i) 		{ return this.liste.get(i).isAlive(); }
	public boolean isAgentMovable(int i) 	{ return this.liste.get(i).isMovable(); }
	public boolean isAgentEatable(int i)	{ return this.liste.get(i).isEatable(); }
}

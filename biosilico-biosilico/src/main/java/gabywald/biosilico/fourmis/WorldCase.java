package gabywald.biosilico.fourmis;

/**
 * 
 * @author Gabriel Chandesris (2009)
 */
public class WorldCase implements VariableContent, AgentContent {
	private Variables variables;
	private AgentListe liste;
	private World world;
	private int posx;
	private int posy;
	
	public WorldCase() {
		this.variables = new Variables();
		this.liste = new AgentListe();
		this.world = null;
		this.posx = -1;this.posy = -1;
	}
	
	public WorldCase(World world) {
		this.variables = new Variables();
		this.liste = new AgentListe();
		this.world = world;
		this.posx = World.HEIGHT/2;
		this.posy = World.WIDTH/2;
	}
	
	public boolean hasFruit() {
		for (int i = 0 ; i < this.liste.length() ; i++) {
			if (this.liste.getAgent(i).isEatable())
				{ return true; }
		}
		return false;
	}
	
	public void setPosX(int posx) { this.posx = posx; }
	public void setPosY(int posy) { this.posy = posy; }
	
	public WorldCase getNW() 
		{ return (this.world==null)?null:
				this.world.getNW(this.posx, this.posy); }
	public WorldCase getNN() 
		{ return (this.world==null)?null:
				this.world.getNorth(this.posx, this.posy); }
	public WorldCase getNE() 
		{ return (this.world==null)?null:
				this.world.getNE(this.posx, this.posy); }
	public WorldCase getEE() 
		{ return (this.world==null)?null:
				this.world.getEast(this.posx, this.posy); }
	public WorldCase getSE() 
		{ return (this.world==null)?null:
				this.world.getSE(this.posx, this.posy); }
	public WorldCase getSS() 
		{ return (this.world==null)?null:
				this.world.getSouth(this.posx, this.posy); }
	public WorldCase getSW() 
		{ return (this.world==null)?null:
				this.world.getSW(this.posx, this.posy); }
	public WorldCase getWW() 
		{ return (this.world==null)?null:
				this.world.getWest(this.posx, this.posy); }
	
	public Variables getVariables() { return this.variables; }
	public int getVariable(int i) { return this.variables.getVariable(i); }
	public void setVariable(int i,int val) 
		{ this.variables.setVariable(i, val); }
	public void addToVariable(int i,int val) 
		{ this.variables.setVarPlus(i, val); }
	
	public int getAgentListLength()		{ return this.liste.length(); }
	public AgentListe getAgentListe()	{ return this.liste; }
	public void addAgent(Agent elt)		{ this.liste.addAgent(elt); }
	public void remAgent(int i)			{ this.liste.removeAgent(i); }
	public Agent getAgent(int i)		{ return this.liste.getAgent(i); }
	public boolean isAgentAlive(int i) 
		{ return this.liste.getAgent(i).isAlive(); }
	public boolean isAgentMovable(int i) 
		{ return this.liste.getAgent(i).isMovable(); }
	public boolean isAgentEatable(int i)
		{ return this.liste.getAgent(i).isEatable(); }
}

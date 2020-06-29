package gabywald.biosilico.fourmis;

/**
 * 
 * @author Gabriel Chandesris (2009)
 */
public class World implements VariableContent {
	private WorldCase[][] map;
	private Variables half_lives;
	
	public static final int HEIGHT = 20;
	public static final int WIDTH = 20;
	
	public World() {
		this.half_lives = new Variables();
		this.map = new WorldCase[World.HEIGHT][World.WIDTH];
		for (int i = 0 ; i < this.map.length ; i++) {
			for (int j = 0 ; j < this.map[i].length ; j++) { 
				this.map[i][j] = new WorldCase(this); 
				this.map[i][j].setPosX(i);
				this.map[i][j].setPosY(j);
			}
		}
	}
	
	private boolean getNextCase(int posx,int posy) {
		return ( ( (posx <= 0) || (posx >= this.map.length) )
				&& ( (posy <= 0) || (posy >= this.map[0].length) ) );
	}
	
	public WorldCase getNorth(int posx,int posy) 
	{ return (this.getNextCase(posx, posy))?null:this.map[posx-1][posy]; }
	
	public WorldCase getSouth(int posx,int posy) 
	{ return (this.getNextCase(posx, posy))?null:this.map[posx+1][posy]; }
	
	public WorldCase getWest(int posx,int posy) 
	{ return (this.getNextCase(posx, posy))?null:this.map[posx][posy-1]; }
	
	public WorldCase getEast(int posx,int posy) 
	{ return (this.getNextCase(posx, posy))?null:this.map[posx][posy+1]; }
	
	public WorldCase getNW(int posx,int posy) 
	{ return (this.getNextCase(posx, posy))?null:this.map[posx-1][posy-1]; }
	
	public WorldCase getNE(int posx,int posy) 
	{ return (this.getNextCase(posx, posy))?null:this.map[posx-1][posy+1]; }
	
	public WorldCase getSE(int posx,int posy) 
	{ return (this.getNextCase(posx, posy))?null:this.map[posx+1][posy+1]; }
	
	public WorldCase getSW(int posx,int posy) 
	{ return (this.getNextCase(posx, posy))?null:this.map[posx+1][posy-1]; }
	
	/**
	 * To rule the world in synchronicity. 
	 */
	public void execution() {
		/** Execution of agents. */
		for (int i = 0 ; i < this.map.length ; i++) {
			for (int j = 0 ; j < this.map[i].length ; j++) {
				for (int k = 0 ; 
						k < this.map[i][j].getAgentListLength() ; 
						k++)
				{ this.map[i][j].getAgent(k).execution(this.map[i][j]); }
			}
		}
		/** Ending action synchronicity. */
		for (int i = 0 ; i < this.map.length ; i++) {
			for (int j = 0 ; j < this.map[i].length ; j++) { 
				/** Half-lives application */
				this.applyHalfLives(this.map[i][j].getVariables());
				/** Movement of agents */
				for (int k = 0 ; 
					k < this.map[i][j].getAgentListLength() ; 
					k++)
				{ this.map[i][j].getAgent(k).deplace(this.map[i][j]); }
			}
		}
	}
	
	private void applyHalfLives(Variables vars) {
		for (int i = 0 ; i < vars.length() ;i++) 
			{ vars.setVarLess(i, this.half_lives.getVariable(i)); }
	}

	public Variables getVariables() { return this.half_lives; }
	public void addToVariable(int i, int val) { ; }
	public void setVariable(int i, int val) { ; }
	public int getVariable(int i) { return this.half_lives.getVariable(i); }	
}

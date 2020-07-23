package gabywald.biosilico.model;

import gabywald.biosilico.interfaces.VariableContent;

/**
 * This class defines global environment for simulation and containing WorldCases. 
 * @author Gabriel Chandesris (2009, 2020)
 */
public class World implements VariableContent {
	/** The map of WorldCase's contained. */
	private WorldCase[][] map;
	/** A special set of variables which defines 
	 * half lives of chemicals in environment. */
	private Chemicals halfLives;
	
	public static final int MAX_HEIGHT	= 20;
	public static final int MAX_WIDTH	= 20;
	
	/** Default constructor of global environment. */
	public World() {
		this.halfLives = new Chemicals();
		this.map = new WorldCase[World.MAX_HEIGHT][World.MAX_WIDTH];
		for (int i = 0 ; i < this.map.length ; i++) {
			for (int j = 0 ; j < this.map[i].length ; j++) { 
				this.map[i][j] = new WorldCase(this); 
				this.map[i][j].setPosX(i);
				this.map[i][j].setPosY(j);
			}
		}
	}
	
	/**
	 * This method to check if next case exist. 
	 * @param posx (int) x position to test
	 * @param posy (int) y position to test
	 * @return (boolean) true if exist, false is over limits.
	 */
	private boolean getNextCase(int posx,int posy) {
		return ( ( (posx < 0) || (posx >= this.map.length) )
				&& ( (posy < 0) || (posy >= this.map[0].length) ) );
	}
	
	/**
	 * This function permits to get a WorldCase with a given direction 
	 * and positions of current WorldCase. Null if over limits. 
	 * @param dir (int) direction (from 800 to 829)
	 * @param posx (int) height position. 
	 * @param posy (int) width position. 
	 * @return (WorldCase) Can be null.
	 */
	public WorldCase getDirection(int dir, int posx, int posy) {
		/** default values / Case 800 */
		int nextposx = posx;int nextposy = posy;
		switch(dir) {
		case(801):nextposx = posx-1;nextposy = posy-1;break; /** NW */
		case(802):nextposx = posx-1;nextposy = posy;  break; /** NN */
		case(803):nextposx = posx-1;nextposy = posy+1;break; /** NE */
		case(804):nextposx = posx;  nextposy = posy+1;break; /** EE */
		case(805):nextposx = posx+1;nextposy = posy+1;break; /** SE */
		case(806):nextposx = posx+1;nextposy = posy;  break; /** SS */
		case(807):nextposx = posx+1;nextposy = posy-1;break; /** SW */
		case(808):nextposx = posx;  nextposy = posy-1;break; /** WW */
		}
		return (this.getNextCase(nextposx, nextposy))?null:
						this.map[nextposx][nextposy];
	}
	
	/** To run the world in synchronicity. */
	public void execution() {
		// ***** Execution of agents. 
		for (int i = 0 ; i < this.map.length ; i++) {
			for (int j = 0 ; j < this.map[i].length ; j++) {
				for (int k = 0 ; 
						k < this.map[i][j].getAgentListLength() ; 
						k++)
				{ this.map[i][j].getAgent(k).execution(this.map[i][j]); }
			}
		}
		// ***** Ending action synchronicity. 
		for (int i = 0 ; i < this.map.length ; i++) {
			for (int j = 0 ; j < this.map[i].length ; j++) { 
				// ***** Half-lives application 
				this.applyHalfLives(this.map[i][j].getVariables());
				// ***** Movement of agents 
				this.map[i][j].getAgentListe().stream().forEach( a -> a.deplace() );
			}
		}
	}
	
	private void applyHalfLives(Chemicals vars) {
		for (int i = 0 ; i < vars.length() ;i++) 
			{ vars.setVarLess(i, this.halfLives.getVariable(i)); }
	}

	public Chemicals getVariables()				{ return this.halfLives; }
	public void addToVariable(int i, int val)	{ ; }
	public void setVariable(int i, int val)		{ ; }
	public int getVariable(int i)				{ return this.halfLives.getVariable(i); }
	
	/**
	 * To enumerate some directions in 2D. 
	 * @author Gabriel Chandesris (2020)
	 */
	enum WorldDirection {
		NW(801), NN(802), NE(803), 
		EE(804), 
		SE(805), SS(806), SW(807), 
		WW(808);
		
		private int index;
		
		private WorldDirection(int index) {
			this.index = index;
		}
		
		public int getIndex() {
			return this.index;
		}
	}
}

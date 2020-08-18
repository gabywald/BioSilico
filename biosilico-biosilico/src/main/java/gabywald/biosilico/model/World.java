package gabywald.biosilico.model;

import java.util.stream.IntStream;

import gabywald.biosilico.interfaces.IChemicals;
import gabywald.biosilico.interfaces.IChemicalsContent;
import gabywald.biosilico.model.chemicals.ChemicalsBuilder;
import gabywald.biosilico.model.enums.DirectionWorld;

/**
 * This class defines global environment for simulation and containing WorldCases. 
 * @author Gabriel Chandesris (2009, 2020)
 */
public class World implements IChemicalsContent {
	/** The map of WorldCase's contained. */
	private WorldCase[][] map;
	/** A special set of variables which defines 
	 * half lives of chemicals in environment. */
	private IChemicals halfLives;
	
	public static final int MAX_HEIGHT	= 20;
	public static final int MAX_WIDTH	= 20;
	
	/** Default constructor of global environment. */
	public World() {
		this(World.MAX_HEIGHT, World.MAX_WIDTH);
	}
	
	public World(int height, int width) {
		this.halfLives	= ChemicalsBuilder.build();
		this.map		= new WorldCase[height][width];
		
		IntStream.range(0, this.map.length).forEach( i -> {
			IntStream.range(0, this.map[i].length).forEach( j -> {
				this.map[i][j] = new WorldCase(this, i, j); 
			});
		});
	}
	
	public WorldCase getWorldCase(Position pos) {
		return this.getWorldCase(pos.getPosX(), pos.getPosY());
	}
	
	public WorldCase getWorldCase(int posx, int posy) {
		return (this.isExistingCase(posx, posy)) ? this.map[posx][posy] : null;
	}
	
	/**
	 * This method to check if next case exist. 
	 * @param posx (int) x position to test
	 * @param posy (int) y position to test
	 * @return (boolean) true if exist, false is over limits.
	 */
	private boolean isExistingCase(int posx, int posy) {
		return ( ( (posx >= 0) && (posx < this.map.length) )
				&& ( (posy >= 0) && (posy < this.map[0].length) ) );
	}
	
	/**
	 * This function permits to get a WorldCase with a given direction and positions of current WorldCase. 
	 * <br/>Null if over limits or direction unavailable. . 
	 * @param dir (int) direction (from 800 to 829)
	 * @param posx (int) height position. 
	 * @param posy (int) width position. 
	 * @return (WorldCase) Can be null.
	 */
	public WorldCase getDirection(int dir, int posx, int posy) {
		// ***** default values / Case 800 !!
		int nextposx = posx, nextposy = posy;
		switch(dir) {
		case(800):nextposx = posx;	nextposy = posy;break; /** NW */
		case(801):nextposx = posx-1;nextposy = posy-1;break; /** NW */
		case(802):nextposx = posx-1;nextposy = posy;  break; /** NN */
		case(803):nextposx = posx-1;nextposy = posy+1;break; /** NE */
		case(804):nextposx = posx;  nextposy = posy+1;break; /** EE */
		case(805):nextposx = posx+1;nextposy = posy+1;break; /** SE */
		case(806):nextposx = posx+1;nextposy = posy;  break; /** SS */
		case(807):nextposx = posx+1;nextposy = posy-1;break; /** SW */
		case(808):nextposx = posx;  nextposy = posy-1;break; /** WW */
		default:
			nextposx = -1;nextposy = -1;
		}
		return (this.isExistingCase(nextposx, nextposy)) ? this.map[nextposx][nextposy] : null;
	}
	
	/**
	 * This function permits to get a WorldCase with a given direction and positions of current WorldCase. 
	 * <br/>Null if over limits or direction unavailable. . 
	 * @param dir (DirectionWorld) direction. 
	 * @param posx (int) height position. 
	 * @param posy (int) width position. 
	 * @return (WorldCase) Can be null.
	 */
	public WorldCase getDirection(DirectionWorld dir, int posx, int posy) {
		return this.getDirection(dir.getIndex(), posx, posy);
	}
	
	/** To run the world in synchronicity. */
	public void execution() {
		// ***** Execution of agents. 
		IntStream.range(0, this.map.length).forEach( i -> {
			IntStream.range(0, this.map[i].length).forEach( j -> {
				IntStream.range(0, this.map[i][j].getAgentListLength()).forEach( k -> {
					this.map[i][j].getAgent(k).execution(this.map[i][j]); 
				});
			});
		});
		// ***** Ending action synchronicity. 
		IntStream.range(0, this.map.length).forEach( i -> {
			IntStream.range(0, this.map[i].length).forEach( j -> {
				// ***** Half-lives application 
				this.applyHalfLives(this.map[i][j].getVariables());
				// ***** Movement of agents 
				this.map[i][j].getAgentListe().stream().forEach( a -> a.deplace() ); 
			});
		});
	}
	
	private void applyHalfLives(IChemicals vars) {
		for (int i = 0 ; i < vars.length() ;i++) 
			{ vars.setVarLess(i, this.halfLives.getVariable(i)); }
	}

	@Override
	public IChemicals getChemicals()	{ return this.halfLives; }
	@Override
	public IChemicals getVariables()	{ return this.halfLives; }
	
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

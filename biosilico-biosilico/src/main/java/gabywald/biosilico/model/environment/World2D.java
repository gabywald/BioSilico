package gabywald.biosilico.model.environment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import gabywald.biosilico.interfaces.IChemicals;
import gabywald.biosilico.interfaces.IChemicalsContent;
import gabywald.biosilico.interfaces.IEnvironment;
import gabywald.biosilico.interfaces.IEnvironmentItem;
import gabywald.biosilico.interfaces.IPosition;
import gabywald.biosilico.model.chemicals.ChemicalsBuilder;
import gabywald.biosilico.model.chemicals.ChemicalsHelper;
import gabywald.biosilico.model.enums.DirectionWorld;

/**
 * This class defines global environment for simulation and containing WorldCases. 
 * @author Gabriel Chandesris (2009, 2020, 2022)
 */
public class World2D implements IEnvironment, IChemicalsContent {
	/** The map of WorldCase's contained. */
	private World2DCase[][] map;
	/** A special set of variables which defines 
	 * half lives of chemicals in environment. */
	private IChemicals halfLives;
	
	private List<World2DCase> wcs = null;
	
	private String name = null;
	
	public static final int MAX_HEIGHT	= 20;
	public static final int MAX_WIDTH	= 20;
	
	public World2D(int height, int width) {
		this.halfLives	= ChemicalsBuilder.build();
		this.map		= new World2DCase[height][width];
		
		// Setting a default name !
		this.setName(this.getClass().getSimpleName() + "[" + height + ", " + width + "]");
		
		IntStream.range(0, this.map.length).forEach( i -> {
			IntStream.range(0, this.map[i].length).forEach( j -> {
				this.map[i][j] = new World2DCase(this, PositionBuilder.buildPosition(i, j)); 
			});
		});
	}
	
	@Override
	public String getName() 
		{ return this.name; }
	
	public void setName(String name) 
		{ this.name = name; }
	
	@Override
	public IEnvironmentItem getEnvironmentItem(IPosition position) {
		return (this.isExisting(position)) ? this.map[position.getPosX()][position.getPosY()] : null;
	}
	
	public World2DCase getWorldCase(int posx, int posy) {
		return (World2DCase) this.getEnvironmentItem(PositionBuilder.buildPosition(posx, posy));
	}
	
	/**
	 * This method to check if next case exist. 
	 * @param posx (int) x position to test
	 * @param posy (int) y position to test
	 * @return (boolean) true if exist, false is over limits.
	 */
	@Override
	public boolean isExisting(IPosition position) {
		return ( ( (position.getPosX() >= 0) && (position.getPosX() < this.map.length) )
				&& ( (position.getPosY() >= 0) && (position.getPosY() < this.map[0].length) ) );
	}
	
	/**
	 * This function permits to get a WorldCase with a given direction and positions of current WorldCase. 
	 * <br/>Null if over limits or direction unavailable. . 
	 * @param dir (int) direction (from 800 to 829)
	 * @param posx (int) height position. 
	 * @param posy (int) width position. 
	 * @return (WorldCase) Can be null.
	 */
	private World2DCase getDirection(int dir, int posx, int posy) {
		// ***** default values / Case 800 !!
		int nextposx = posx, nextposy = posy;
		switch(dir) {
		case(800):nextposx = posx;	nextposy = posy;break; /** LL */
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
		return (this.isExisting(PositionBuilder.buildPosition(nextposx, nextposy))) ? this.map[nextposx][nextposy] : null;
	}
	
	/**
	 * This function permits to get a WorldCase with a given direction and positions of current WorldCase. 
	 * <br/>Null if over limits or direction unavailable. . 
	 * @param dir (DirectionWorld) direction. 
	 * @param pos (IPosition) position. 
	 * @return (World2DCase) Can be null.
	 */
	@Override
	public World2DCase getDirection(DirectionWorld dir, IPosition pos) {
		return this.getDirection(dir.getIndex(), pos.getPosX(), pos.getPosY());
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
				this.applyHalfLives(this.map[i][j].getChemicals());
				// ***** Movement of agents 
				this.map[i][j].getAgentListe().stream().forEach( a -> a.deplace() ); 
			});
		});
	}
	
	private void applyHalfLives(IChemicals vars) {
		for (int i = 0 ; i < vars.length() ;i++) 
			{ vars.setVarLess(i, this.halfLives.getVariable(i)); }
	}
	
	/**
	 * Load Configuration file for half-lives. 
	 * @return
	 */
	public boolean loadHalLives() {
		Map<Integer, Integer> halfLives = ChemicalsHelper.loadDefaultChemicalsHalfLives();
		halfLives.keySet().stream().forEach( key -> this.halfLives.setVariable(key, halfLives.get(key)) );
		return (halfLives.size() > 0);
	}

	@Override
	public IChemicals getChemicals()	{ return this.halfLives; }
	
	public List<World2DCase> getWorldCases() {
		if (this.wcs == null) {
			List<World2DCase> wcs = new ArrayList<World2DCase>(World2D.MAX_HEIGHT * World2D.MAX_HEIGHT);
			
			IntStream.range(0, this.map.length).forEach( i -> {
				IntStream.range(0, this.map[i].length).forEach( j -> {
					wcs.add( this.map[i][j]);
				});
			});
			
			this.wcs = Collections.unmodifiableList( wcs );
		}
		return this.wcs;
	}
	
}

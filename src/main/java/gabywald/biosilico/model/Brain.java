package gabywald.biosilico.model;

import gabywald.biosilico.exceptions.BrainLengthException;
import gabywald.biosilico.exceptions.BrainLobeReplaceException;
import gabywald.biosilico.structures.NeuronListe;
import gabywald.global.structures.ObservableObject;

/**
 * Brain is a defined class of a two-dimensionnal neural network of Neuron's. 
 * @author Gabriel Chandesris (2009)
 */
public class Brain extends ObservableObject {
	/** Unique brain instance. */
	private static Brain instance = null;
	/** A two-dimensional table of Neuron. */
	private Neuron map[][];
	/** Height of current Brain. */
	private int max_height;
	/** Width of current Brain. */
	private int max_width;
	/** Depth of current Brain. */
	private int max_depth;
	
	/** The maximum height of a Brain. (100) */
	private static final int MAX_HEIGHT = 100; // 20;
	/** The maximum width of a Brain.  (100) */
	private static final int MAX_WIDTH = 100; // 20;
	/** The maximum depth (3D) of a Brain.  (100) */
	private static final int MAX_DEPTH = 100; // 20;
	
	
	/**
	 * Standard constructor, height and width must be between 0 and MAX for each. 
	 * @param height (int) Height of the Brain map. 
	 * @param width (int) Width of the Brain map. 
	 * @throws BrainLengthException If height or width under or equal 0. 
	 * @see Brain#setLobe(int, int, int, int, Neuron, boolean)
	 * @see Brain#setNeuronAt(Position, Neuron)
	 */
	public Brain(int height,int width,int depth) throws BrainLengthException {
		if (height > Brain.MAX_HEIGHT)	{ height = Brain.MAX_HEIGHT; }
		if (width > Brain.MAX_WIDTH) { width = Brain.MAX_WIDTH; }
		if (width > Brain.MAX_DEPTH) { width = Brain.MAX_DEPTH; }
		if (height <= 0) { throw new BrainLengthException("Height cannot be under 0. "); }
		if (width <= 0) { throw new BrainLengthException("Width cannot be under 0. "); }
		if (depth <= 0) { throw new BrainLengthException("depth cannot be under 0. "); }
		this.max_height	= height;
		this.max_width	= width;
		this.max_depth	= depth;
		this.map = new Neuron[this.max_height][this.max_width];
	}
	
	/**
	 * Standard constructor, height and width must be between 0 and MAX for each. 
	 * @param height (int) Height of the Brain map. 
	 * @param width (int) Width of the Brain map. 
	 * @throws BrainLengthException If height or width under or equal 0. 
	 * @see Brain#setLobe(int, int, int, int, Neuron, boolean)
	 * @see Brain#setNeuronAt(Position, Neuron)
	 */
	public Brain(int height,int width) throws BrainLengthException {
		if (height > Brain.MAX_HEIGHT) { height = Brain.MAX_HEIGHT; }
		if (width > Brain.MAX_WIDTH) { width = Brain.MAX_WIDTH; }
		if (height <= 0) { throw new BrainLengthException("Height cannot be under 0. "); }
		if (width <= 0) { throw new BrainLengthException("Width cannot be under 0. "); }
		this.max_height	= height;
		this.max_width	= width;
		this.max_depth	= 0;
		this.map = new Neuron[this.max_height][this.max_width];
	}
	
	/** Default constructor : MAX_HEIGHT*MAX_WIDTH map. 
	 * (<u>no instanciation of Neuron</u>) 
	 * @see Brain#getBrain()
	 * @see Brain#setLobe(int, int, int, int, Neuron, boolean)
	 * @see Brain#setNeuronAt(Position, Neuron)
	 * @see Brain#MAX_HEIGHT
	 * @see Brain#MAX_WIDTH
	 */
	private Brain() { 
		this.max_height	= Brain.MAX_HEIGHT;
		this.max_width	= Brain.MAX_WIDTH;
		this.max_depth	= 0;
		this.map = new Neuron[this.max_height][this.max_width];
	}
	
	/**
	 * In order to get an example instance of Brain
	 * (and instantiate it if not).
	 * @return (Brain)
	 * @see Brain#Brain()
	 */
	public static Brain getBrain() {
		if (Brain.instance == null) { Brain.instance = new Brain(); }
		return Brain.instance;
	}
	
	public static int getMaxHeight() { return Brain.MAX_HEIGHT; }
	public static int getMaxWidth() { return Brain.MAX_WIDTH; }
	public static int getMaxDepth() { return Brain.MAX_DEPTH; }
	
	public int getHeight() { return this.max_height; }
	public int getWidth() { return this.max_width; }
	public int getDepth() { return this.max_depth; }
	
	/**
	 * Get a neuron at a specific position. 
	 * <b>Return could be null. </b>
	 * @param x (int) between 0 and height
	 * @param y (int) between 0 and width
	 * @return (Neuron) can be null !
	 */
	public Neuron getNeuronAt(int x,int y) {
		if  ( ( (x < 0) && (x >= this.max_height) )
				&& ( (y < 0) && (y >= this.max_width) ) ) 
			{ return null; }
		return this.map[x][y];
	}
	
	/**
	 * Get a neuron at a specific position. 
	 * <b>Return could be null. </b>
	 * @param pos (Position)
	 * @return (Neuron) can be null !
	 * @see Brain#getNeuronAt(int, int)
	 */
	public Neuron getNeuronAt(Position pos) 
		{ return this.getNeuronAt(pos.getPosX(), pos.getPosY()); }
	
	/**
	 * Aim of this method is to answer a set of Neuron's before a
	 * specific given position and a given proximity. <br>
	 * First search done by height, then little width (activated Neuron's). 
	 * @param pos (Position) to be search in the map before this.
	 * @param p (int) proximity (how deeply back)
	 * @return (NeuronList)
	 */
	public NeuronListe getNeuronBefore(Position pos,int p) {
		NeuronListe tmp = new NeuronListe();
		int x = pos.getPosX();
		int y = pos.getPosY();
		/** here (x) or (x-1) (strict ?) */
		for (int i = (x-p) ; i <= (x-1) ; i++) { 
			for (int j = (y-(p/2)) ; j <= (y+(p/2)) ; j++) {
				/** compute only in the limits of the brain */
				if  ( ( (i >= 0) && (i < this.max_height) )
						&& ( (j >= 0) && (j < this.max_width) ) ) {
					if ( (this.map[i][j] != null) 
							&& (this.map[i][j].isActivated()) ) 
						{ tmp.addNeuron(this.map[i][j]); }
					/** Only existant and activated neurons. */
				}
			}
		}
		return tmp;
	}
	
	/**
	 * To get activity before a position of a given neuron. 
	 * @param pos (Position) position of current neuron. 
	 * @param p (int) proximity (best is 1). 
	 * @return Number of <b>unactive</b> neurons
	 */
	public int getActivityBefore(Position pos,int p) {
		int activityNear = 0; /** count activated neurons */
		int neighboors = 0; /** count neighboors */
		int x = pos.getPosX();
		int y = pos.getPosY();
		for (int i = (x-p) ; i <= (x) ; i++) {
			for (int j = (y-p) ; j <= (y+p) ; j++) {
				/** compute only in the limits of the brain */
				if  ( ( (i >= 0) && (i < this.max_height) )
						&& ( (j >= 0) && (j < this.max_width) ) ) {
					/** Checking NOT the current neuron and null ones */
					if (this.map[i][j] != null) {
						if( (i != x) && (j != y) ) {
							if (this.map[i][j].ckActivated())
								{ activityNear++; }
							neighboors++;
						}
					} else { neighboors++; } /** null neighboor count ! */
				}
			}
		}
		return (neighboors-activityNear);
	}
	
	/**
	 * To get activity near a position of a given neuron. 
	 * @param pos (Position) position of current neuron. 
	 * @param p (int) proximity (best is 1). 
	 * @return Number of <b>unactive</b> neurons
	 */
	public int getActivityNear(Position pos,int p) {
		int activityNear = 0; /** count activated neurons */
		int neighboors = 0; /** count neighboors */
		int x = pos.getPosX();
		int y = pos.getPosY();
		for (int i = (x-p) ; i <= (x+p) ; i++) {
			for (int j = (y-p) ; j <= (y+p) ; j++) {
				/** compute only in the limits of the brain */
				if  ( ( (i >= 0) && (i < this.max_height) )
						&& ( (j >= 0) && (j < this.max_width) ) ) {
					/** Checking NOT the current neuron and null ones */
					if (this.map[i][j] != null) {
						if( (i != x) && (j != y) ) {
							if (this.map[i][j].ckActivated())
								{ activityNear++; }
							neighboors++;
						}
					} else { neighboors++; } /** null neighboor count ! */
				}
			}
		}
		return (neighboors-activityNear);
	}
	
	/**
	 * After getting activity near a position, getting a free position 
	 * (null neuron in map) or an inactive place.
	 * @param pos (Position) position of current neuron. 
	 * @param p (int) proximity (best is 1). 
	 * @return (Position)
	 * @see Neuron#reproduce(Brain)
	 */
	public Position getBestPositionNear(Position pos,int p) {
		int x = pos.getPosX();
		int y = pos.getPosY();
		for (int i = (x-p) ; i <= (x+p) ; i++) {
			for (int j = (y-p) ; j <= (y+p) ; j++) {
				/** compute in the limits of the brain
				 * except first and last line.  */
				if  ( ( (i >= 1) && (i < (this.max_height-1)) )
						&& ( (j >= 0) && (j < this.max_width) ) ) {
					/** Checking null or inactivated neuron. */
					if ( (this.map[i][j] == null) 
								/**|| (!this.map[i][j].isActivated()) */) {
						/** Count NOT the current neuron */
						if( (i != x) && (j != y) ) 
							{ return new Position(i,j); }
					}
				}
			}
		}
		return null; /** If no position found. */
	}
	
	public void setNeuronAt(Position pos,Neuron neu) {
		int x = pos.getPosX();
		int y = pos.getPosY();
		if  ( ( (x >= 0) && (x < this.max_height) )
				&& ( (y >= 0) && (y < this.max_width) ) ) 
			{ this.map[x][y] = neu;neu.setPosition(x,y); }
	}
	
	public void remNeuronAt(Position pos) {
		int x = pos.getPosX();
		int y = pos.getPosY();
		if  ( ( (x >= 0) && (x < this.max_height) )
				&& ( (y >= 0) && (y < this.max_width) ) ) 
			{ this.map[x][y] = null; }
	}
	
	/**
	 * In order to create a lobe of Neuron in the brain.
	 * @param height (int) height of the lobe. 
	 * @param width (int) width of the lobe. 
	 * @param x (int) top-start position of the lobe. 
	 * @param y (int) left-start position of the lobe. 
	 * @param sample (Neuron) a classical instance of Neuron to be in this lobe. 
	 * @param replace (boolean) replace of not if Neuron are already present.
	 * TODO lobe setting ; 
	 * XXX lobe exception if replace is false and place is occupied ??
	 */
	public void setLobe (int height,int width,int x,int y,
						Neuron sample,boolean replace) 
						throws BrainLengthException,BrainLobeReplaceException
						{
		/** Throwing exceptions if necessary. */
		boolean lengthExcept = false;
		if ( (height == 0) || (width == 0) ) { lengthExcept = true; }
		if ( (x >= this.map.length) || (y >= this.map[0].length) ) 
			{ lengthExcept = true; }
		if ( (x+height) > this.map.length) { lengthExcept = true; }
		if ( (y+width) > this.map[0].length) { lengthExcept = true; }
		if (lengthExcept) { throw new BrainLengthException("Capacity of brain exceeded. "); }
		NeuronListe currentLobe = new NeuronListe();
		if (!replace) {
			/** Checking BEFORE changing anything !! */
			for (int i = x ; i < (x+height) ; i++) {
				for (int j = y ; j < (y+width) ; j++) {
					if (this.map[i][j] != null) { 
						throw new BrainLobeReplaceException(
								"Position ("+i+","+j+") is not empty. ");
					}
				}
			}
			/** If no exception thrown here... */
			for (int i = x ; i < (x+height) ; i++) {
				for (int j = y ; j < (y+width) ; j++) { 
					this.map[i][j] = sample.getCopy();
					this.map[i][j].setPosition(i,j);
					currentLobe.addNeuron(this.map[i][j]);
					this.map[i][j].setLobe(currentLobe);
				}
			}
		} else {
			/** if replace can be done */
			for (int i = x ; i < (x+height) ; i++) {
				for (int j = y ; j < (y+width) ; j++) { 
					/** XXX removing Neuron from lobes when replace ? */
					this.map[i][j] = sample.getCopy(); 
					this.map[i][j].setPosition(i,j);
					currentLobe.addNeuron(this.map[i][j]);
					this.map[i][j].setLobe(currentLobe);
				}
			}
		}
	}

	/**
	 * This method to compute activity of neurons and modulate their 
	 * reconnection or reproduction if activated or not. 
	 */
	public void networking() {
		for (int i = 0 ; i < this.max_height ; i++) {
			for (int j = 0 ; j < this.max_width ; j++) {
				if (this.map[i][j] != null) { 
					this.map[i][j].recompute();
					if (!this.map[i][j].isActivated())
						{ this.map[i][j].reconnection(this); }
					else { this.map[i][j].reproduce(this); }
				}
			}
		}
	}
	
	public void run() {
		for (int i = 0 ; i < 1000 ; i++)
			{ this.networking();this.change(); }
	}
	
}

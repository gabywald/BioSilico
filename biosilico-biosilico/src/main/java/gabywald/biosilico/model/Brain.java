package gabywald.biosilico.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import gabywald.biosilico.exceptions.BrainLengthException;
import gabywald.biosilico.exceptions.BrainLobeReplaceException;
import gabywald.biosilico.genetics.Gene;
import gabywald.global.structures.ObservableObject;

/**
 * Brain is a defined class of a two-dimensionnal neural network of Neuron's. 
 * @author Gabriel Chandesris (2009, 2020)
 */
public class Brain extends ObservableObject {
	/** A two-dimensional table of Neuron. */
	private Neuron map[][];
	/** Height of current Brain. */
	private int maxHeight;
	/** Width of current Brain. */
	private int maxWidth;
	/** Depth of current Brain. */
	private int maxDepth;

	/** The maximum height of a Brain. (100) */
	public static final int MAX_HEIGHT	= 100;
	/** The maximum width of a Brain.  (100) */
	public static final int MAX_WIDTH	= 100;
	/** The maximum depth (3D) of a Brain.  (100) */
	public static final int MAX_DEPTH	= 100;
	
	/** The number of iterations on each run. */
	public static final int ITERATIONS_ON_EACH_RUN = 1000;

	/**
	 * Standard constructor, height and width must be between 0 and MAX for each. 
	 * @param height (int) Height of the Brain map. 
	 * @param width (int) Width of the Brain map. 
	 * @throws BrainLengthException If height or width under or equal 0. 
	 * @see Brain#setLobe(int, int, int, int, Neuron, boolean)
	 * @see Brain#setNeuronAt(Position, Neuron)
	 */
	Brain(int height, int width, int depth) {
		this.maxHeight	= height;
		this.maxWidth	= width;
		this.maxDepth	= depth;
		this.map = new Neuron[this.maxHeight][this.maxWidth];
	}

	/**
	 * Standard constructor, height and width must be between 0 and MAX for each. 
	 * @param height (int) Height of the Brain map. 
	 * @param width (int) Width of the Brain map. 
	 * @throws BrainLengthException If height or width under or equal 0. 
	 * @see Brain#setLobe(int, int, int, int, Neuron, boolean)
	 * @see Brain#setNeuronAt(Position, Neuron)
	 */
	Brain(int height, int width) {
		this(height, width, 0);
	}

	/** Default constructor : MAX_HEIGHT*MAX_WIDTH map. 
	 * (<u>no instanciation of Neuron</u>) 
	 * @throws BrainLengthException 
	 * @see Brain#getBrain()
	 * @see Brain#setLobe(int, int, int, int, Neuron, boolean)
	 * @see Brain#setNeuronAt(Position, Neuron)
	 * @see Brain#MAX_HEIGHT
	 * @see Brain#MAX_WIDTH
	 */
	Brain() { 
		this(Brain.MAX_HEIGHT, Brain.MAX_WIDTH, 0);
	}

	public int getHeight()		{ return this.maxHeight; }
	public int getWidth()		{ return this.maxWidth; }
	public int getDepth()		{ return this.maxDepth; }

	/**
	 * Get a neuron at a specific position. 
	 * <b>Return could be null. </b>
	 * @param x (int) between 0 and height
	 * @param y (int) between 0 and width
	 * @return (Neuron) can be null !
	 */
	public Neuron getNeuronAt(int x,int y) {
		if  ( ( (x < 0) && (x >= this.maxHeight) )
				&& ( (y < 0) && (y >= this.maxWidth) ) ) 
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
	 * @param prox (int) proximity (how deeply back)
	 * @return (NeuronList)
	 */
	public List<Neuron> getNeuronsBefore(Position pos, int prox) {
		List<Neuron> toReturn = new ArrayList<Neuron>();
		int x = pos.getPosX();
		int y = pos.getPosY();

		// ***** compute only in the limits of the brain 
		int diffHeight = Gene.obtainValue(0, this.maxHeight-1, x-prox);
		int diffWidth1 = Gene.obtainValue(0, this.maxWidth-1, y-prox);
		int diffWidth2 = Gene.obtainValue(0, this.maxWidth-1, y+prox);
		// System.out.println( "h" + pos + "::" + prox + " -> " + diffHeight + "\t" + (x-1) + " =>... w" + pos + "::" + prox + " -> " + diffWidth1 + "\t" + diffWidth2 );

		// ***** in same Y-pos first !!
		IntStream.range(diffHeight, x).forEach( i -> {
			Neuron current = this.map[i][pos.getPosY()];
			// ***** Only existent and activated neurons.
			if ( (current != null) && (current.isActivated()) ) 
				{ toReturn.add( current ); }
		});
		
		// ***** then in width proximity
		IntStream.range(diffHeight, x).forEach( i -> {
			// TODO width proximity : changing diffWidth from distance (increasing fibonacci distance ?!)
			IntStream.range(diffWidth1, diffWidth2 + 1).forEach( j -> {
				// ***** Only existent and activated neurons.
				Neuron current = this.map[i][j];
				if ( (current != null) && (current.isActivated()) ) 
					{ toReturn.add( current ); }
			});
		});
		
		// NOTE : do not check if a Neuron's instance is already present
		// it is pertinent that a dendrites connects same twice or more !
		
		return toReturn;
	}
	
	private class ActivityNeighboors {
		private int activityNear	= 0; /** count activated neurons */
		private int neighboors		= 0; /** count neighboors */
	}

	/**
	 * To get activity before a position of a given neuron. 
	 * @param pos (Position) position of current neuron. 
	 * @param prox (int) proximity (best is 1). 
	 * @return Number of <b>unactive</b> neurons
	 */
	public int getActivityBefore(Position pos, int prox) {
		ActivityNeighboors an = new ActivityNeighboors();
		int x = pos.getPosX();
		int y = pos.getPosY();
		
		// ***** compute only in the limits of the brain 
		int diffHeight = Gene.obtainValue(0, this.maxHeight-1, x-prox);
		int diffWidth1 = Gene.obtainValue(0, this.maxWidth-1, y-prox);
		int diffWidth2 = Gene.obtainValue(0, this.maxWidth-1, y+prox);
		// System.out.println( "h" + pos + "::" + prox + " -> " + diffHeight + "\t" + (x-1) + " =>... w" + pos + "::" + prox + " -> " + diffWidth1 + "\t" + diffWidth2 );

		IntStream.range(diffHeight, x).forEach( i -> {
			IntStream.range(diffWidth1, diffWidth2 + 1).forEach( j -> {
				// ***** Checking NOT the current neuron and null ones 
				if (this.map[i][j] != null) {
					if ( (i != x) && (j != y) ) {
						if (this.map[i][j].ckActivated()) { an.neighboors++; }
					}
				}
			});
		});
		
		return (an.neighboors-an.activityNear);
	}

	/**
	 * To get activity near a position of a given neuron. 
	 * @param pos (Position) position of current neuron. 
	 * @param prox (int) proximity (best is 1). 
	 * @return Number of <b>unactive</b> neurons
	 */
	public int getActivityNear(Position pos, int prox) {
		ActivityNeighboors an = new ActivityNeighboors();
		int x = pos.getPosX();
		int y = pos.getPosY();

		// ***** compute only in the limits of the brain 
		int diffHeight1 = Gene.obtainValue(0, this.maxHeight-1, x-prox);
		int diffHeight2 = Gene.obtainValue(0, this.maxHeight-1, x+prox);
		int diffWidth1 = Gene.obtainValue(0, this.maxWidth-1, y-prox);
		int diffWidth2 = Gene.obtainValue(0, this.maxWidth-1, y+prox);
		// System.out.println( "h" + pos + "::" + prox + " -> " + diffHeight + "\t" + (x-1) + " =>... w" + pos + "::" + prox + " -> " + diffWidth1 + "\t" + diffWidth2 );

		IntStream.range(diffHeight1, diffHeight2 + 1).forEach( i -> {
			IntStream.range(diffWidth1, diffWidth2 + 1).forEach( j -> {
				// ***** Checking NOT the current neuron and null ones 
				if (this.map[i][j] != null) {
					if ( (i != x) && (j != y) ) {
						if (this.map[i][j].ckActivated()) { an.neighboors++; }
					}
				}
			});
		});
		
		return (an.neighboors-an.activityNear);
	}

	/**
	 * After getting activity near a position, getting a free position (null neuron position in map) or an inactive place. <br />
	 * <i>Used for reproduction of Neurons instances to reinforce network. </i><br />
	 * XXX NOTE : best if in limit of lobe ??
	 * @param pos (Position) position of current neuron. 
	 * @param prox (int) proximity (best is 1). 
	 * @return (Position)
	 * @see Neuron#reproduce(Brain)
	 */
	public Position getBestPositionNear(Position pos, int prox) {
		int x = pos.getPosX();
		int y = pos.getPosY();

		// ***** compute only in the limits of the brain 
		int diffHeight1 = Gene.obtainValue(0, this.maxHeight-1, x-prox);
		int diffHeight2 = Gene.obtainValue(0, this.maxHeight-1, x+prox);
		int diffWidth1 = Gene.obtainValue(0, this.maxWidth-1, y-prox);
		int diffWidth2 = Gene.obtainValue(0, this.maxWidth-1, y+prox);
		// System.out.println( "h" + pos + "::" + prox + " -> " + diffHeight + "\t" + (x-1) + " =>... w" + pos + "::" + prox + " -> " + diffWidth1 + "\t" + diffWidth2 );

		for (int i = diffHeight1 ; i < diffHeight2 ; i++) {
			for (int j = diffWidth1 ; j < diffWidth2 ; j++) {
				// ***** Checking NOT the current neuron and null ones 
				if (this.map[i][j] != null) {
//					if ( (i != x) && (j != y) ) {
//						if ( ! this.map[i][j].ckActivated()) 
//							{ return new Position(i, j); }
//					}
				} else { 
					return new Position(i, j);
				}
			}
		}
		
		return null; /** If no position found. */
	}

	// TODO change exposure of this method !!
	public void setNeuronAt(Position pos, Neuron neu) {
		int x = pos.getPosX();
		int y = pos.getPosY();
		if  ( ( (x >= 0) && (x < this.maxHeight) )
				&& ( (y >= 0) && (y < this.maxWidth) ) ) 
			{ this.map[x][y] = neu;neu.setPosition(x,y); }
	}

	// TODO change exposure of this method !!
	public void remNeuronAt(Position pos) {
		int x = pos.getPosX();
		int y = pos.getPosY();
		if  ( ( (x >= 0) && (x < this.maxHeight) )
				&& ( (y >= 0) && (y < this.maxWidth) ) ) 
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
	public void setLobe(int height, int width, int x, int y, 
						Neuron sample, boolean replace) 
					throws BrainLengthException, BrainLobeReplaceException
	{
		// ***** Throwing exceptions if necessary. 
		boolean lengthExcept = false;
		if ( (height == 0) || (width == 0) ) 	
			{ lengthExcept = true; }
		if ( (x >= this.map.length) || (y >= this.map[0].length) ) 
			{ lengthExcept = true; }
		if ( (x+height) > this.map.length) 
			{ lengthExcept = true; }
		if ( (y+width) > this.map[0].length) 
			{ lengthExcept = true; }
		if (lengthExcept) 
			{ throw new BrainLengthException( "Capacity of brain exceeded. " ); }
		List<Neuron> currentLobe = new ArrayList<Neuron>();
		if (!replace) {
			// Checking BEFORE changing anything !! 
			for (int i = x ; i < (x+height) ; i++) {
				for (int j = y ; j < (y+width) ; j++) {
					if (this.map[i][j] != null) { 
						throw new BrainLobeReplaceException( "Position ("+i+","+j+") is not empty. " );
					}
				}
			}
			// If no exception thrown here... 
			for (int i = x ; i < (x+height) ; i++) {
				for (int j = y ; j < (y+width) ; j++) { 
					this.map[i][j] = sample.getCopy();
					this.map[i][j].setPosition(i, j);
					currentLobe.add(this.map[i][j]);
					this.map[i][j].setLobe(currentLobe);
				}
			}
		} else {
			// ***** if replace can be done 
			for (int i = x ; i < (x+height) ; i++) {
				for (int j = y ; j < (y+width) ; j++) { 
					/** XXX removing Neuron from lobes when replace ? */
					this.map[i][j] = sample.getCopy(); 
					this.map[i][j].setPosition(i, j);
					currentLobe.add(this.map[i][j]);
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
		for (int i = 0 ; i < this.maxHeight ; i++) {
			for (int j = 0 ; j < this.maxWidth ; j++) {
				Neuron currentNeuron = this.map[i][j];
				if (currentNeuron != null) { 
					currentNeuron.recompute();
					if ( ! currentNeuron.isActivated())
						{ currentNeuron.reconnection(this); }
					else { currentNeuron.reproduce(this); }
				}
			}
		}
	}

	@Override
	public void run() {
		for (int i = 0 ; i < Brain.ITERATIONS_ON_EACH_RUN ; i++)
			{ this.networking();this.change(); }
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append("\tBRAIN HEIGHT\t")	.append(Gene.convert0to99(this.getHeight())).append( "\n" );
		result.append("\tBRAIN WIDTH\t")	.append(Gene.convert0to99(this.getWidth() )).append( "\n" );
		result.append("\tBRAIN DEPTH\t")	.append(Gene.convert0to99(this.getDepth() )).append( "\n" );
		// ***** result.append("NEURONS LIST DESCRIPTION\n\tNO DATA\n");
		for (int i = 0 ; i < this.map.length ; i++) {
			for (int j = 0 ; j < this.map[i].length ; j++) {
				if (this.map[i][j] != null) { 
					result.append(this.map[i][j].toString()).append("\n"); 
				}
			}
		}
		return result.toString();
	}

}

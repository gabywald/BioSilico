package gabywald.biosilico.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Brain is a defined class of a two-dimensionnal part of neural network / brain. 
 * XXX Test to see if it is better with this class. ?? 
 * @author Gabriel Chandesris (2009, 2020)
 * @Deprecated Do not use. 
 */
public class BrainLobe {
	/** Rest state of neurons. */
	private int restState;
	/** Threshold activation of neurons.  */
	private int threshold;
	/** Descent from activity to rest of neurons. */
	private int descent;
	/** Minimal number of dendrites for neurons. */
	private int dendriticmin;
	/** Maximal number of dendrites for neurons. */
	private int dendriticmax;
	/** Proximity to search dendrites / connections. */
	private int proximity;
	/** Reproduction property of neurons. */
	private boolean reproduction;
	/** Number of reproduction for neurons. */
	private int reproductibility;
	/** Height of the lobe. */
	private int height;
	/** Width of the lobe. */
	private int width;
	/** Position in the neural network. */
	private Position position;
	/** If current Neuron is on a WTA Lobe (one neuron activated). */
	private boolean winnerTakeAll;
	
	/** Lobe contains Neuron's instance.  */
	private List<Neuron> neurons;
	
	/** 
	 * Private complete constructor (for copy / clone object).
	 * @param rest (int) rest state. 
	 * @param thre (int) threshold. 
	 * @param desc (int) descent.
	 * @param dmin (int) dendritic minimal connections. 
	 * @param dmax (int) dendritic maximal connections. 
	 * @param prox (int) proximity. 
	 * @param repr (boolean) if current Neuron can reproduce. 
	 * @param repy (int) number of maximum reproductions. 
	 * @param pos (Position)
	 * @see Neuron#getCopy()
	 */
	BrainLobe(	int rest, int thre, int desc, int dmin, int dmax, 
				int prox, boolean repr, int repy, boolean wta, Position pos) {
		this.restState			= rest;
		this.threshold			= thre;
		this.descent			= desc;
		
		this.dendriticmax		= dmax;
		this.dendriticmin		= dmin;
		
		this.position			= pos;

		this.proximity			= prox;
		this.reproduction		= repr;
		this.reproductibility	= repy;
		this.winnerTakeAll		= wta;
		
		this.neurons		= new ArrayList<Neuron>();
	}

	public int getRestState() {
		return this.restState;
	}

	public int getThreshold() {
		return this.threshold;
	}

	public int getDescent() {
		return this.descent;
	}

	public int getDendriticmin() {
		return this.dendriticmin;
	}

	public int getDendriticmax() {
		return this.dendriticmax;
	}

	public int getProximity() {
		return this.proximity;
	}

	public boolean isReproduction() {
		return this.reproduction;
	}

	public int getReproductibility() {
		return this.reproductibility;
	}

	public int getHeight() {
		return this.height;
	}

	public int getWidth() {
		return this.width;
	}

	public Position getPosition() {
		return this.position;
	}

	public boolean isWinnerTakeAll() {
		return this.winnerTakeAll;
	}

	public List<Neuron> getNeurons() {
		return Collections.unmodifiableList(this.neurons);
	}
	
}

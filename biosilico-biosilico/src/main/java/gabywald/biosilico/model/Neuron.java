package gabywald.biosilico.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gabywald.biosilico.interfaces.IPosition;
import gabywald.biosilico.model.environment.PositionBuilder;
import gabywald.global.structures.ObservableObject;

/**
 * This class describes a neurone and its properties
 * (activation, threshold, rest state, and inputing connections). 
 * @author Gabriel Chandesris (2009, 2020, 2022)
 */
public class Neuron extends ObservableObject {
	/** Rest state of the neuron. */
	private int restState;
	/** Activation level of the neuron. */
	private int threshold;
	/** Current activity of the neuron. */
	private int activity;
	/** How quickly activity goes to rest state. MUST BE POSITIVE */
	private int descent;
	
	/** Position in the neural network. */
	private IPosition position;
	/** Minimal number of dendritic connections (inputs) [0 to 8]. */
	private int dmin;
	/** Maximal number of dendritic connections (inputs) [0 to 8]. */
	private int dmax;
	/** Table of neurones current Neuron is connected with (as dendrites). */
	private List<Neuron> connections;
	/** Respective weights attributed to input connections. */
	private List<Integer> weights;
	/** Current numbers of connections. */
	// private int index;
	/** Which distance searching connections and reproduction. */
	private int proximity;
	/** Current Neuron can reproduce or not. */
	private boolean reproduction;
	/** Number of times a neuron can reproduce. */
	private int reproductibility;
	
	/** If current Neuron is on a WTA Lobe (one neuron activated). */
	private boolean winnerTakeAll;
	/** Lobe which Neuron is part of.  */
	private List<Neuron> currentLobe;
	
	/**
	 * Constructor with basics parameters. 
	 * Position has to be set before first activation of neural network. 
	 * @param rest (int) rest state. 
	 * @param thre (int) threshold. 
	 * @param desc (int) descent. 
	 * @param dendriticmin (int) dendritic minimal connections. 
	 * @param dendriticmax (int) dendritic maximal connections. 
	 * @param prox (int) proximity (best is 1). 
	 * @param repr (boolean) if current Neuron can reproduce. 
	 * @param repy (int) number of maximum reproductions. 
	 * @see Neuron#setPosition(int, int)
	 */
	public Neuron(	int rest, int thre, int desc, int dendriticmin, int dendriticmax,
					int prox, boolean repr, int repy) {
		this(rest, thre, 0, desc, dendriticmin, dendriticmax, prox, repr, repy, false, null, new ArrayList<Neuron>(), new ArrayList<Integer>());
	}
	
	/**
	 * Constructor with basics parameters. 
	 * Position has to be set before first activation of neural network. 
	 * @param rest (int) rest state. 
	 * @param thre (int) threshold. 
	 * @param desc (int) descent. 
	 * @param dendriticmin (int) dendritic minimal connections. 
	 * @param dendriticmax (int) dendritic maximal connections. 
	 * @param prox (int) proximity (best is 1). 
	 * @param repr (boolean) if current Neuron can reproduce. 
	 * @param repy (int) number of maximum reproductions. 
	 * @param wta (boolean) Winner Take All parameter. 
	 * @see Neuron#setPosition(int, int)
	 */
	public Neuron(	int rest, int thre, int desc, int dendriticmin, int dendriticmax,
					int prox, boolean repr, int repy, boolean wta) {
		this(rest, thre, 0, desc, dendriticmin, dendriticmax, prox, repr, repy, wta, null, new ArrayList<Neuron>(), new ArrayList<Integer>());
	}
	
	/** 
	 * Private complete constructor (for copy / clone object).
	 * @param rest (int) rest state. 
	 * @param thre (int) threshold. 
	 * @param acti (int) activity. 
	 * @param desc (int) descent.
	 * @param dmin (int) dendritic minimal connections. 
	 * @param dmax (int) dendritic maximal connections. 
	 * @param prox (int) proximity. 
	 * @param repr (boolean) if current Neuron can reproduce. 
	 * @param repy (int) number of maximum reproductions. 
	 * @param pos (IPosition)
	 * @param conn (Neuron's Liste) set of input dendrites. 
	 * @param weights (Integer's Liste) set of weights of intputs. 
	 * @see Neuron#getCopy()
	 */
	Neuron(	int rest, int thre, int acti, int desc, int dmin, int dmax, int prox, boolean repr, int repy, boolean wta, 
			IPosition pos, List<Neuron> conn, List<Integer> weights) {
		this.restState			= rest;
		this.threshold			= thre;
		this.activity			= acti;
		this.descent			= desc;
		
		this.dmax				= dmax;
		this.dmin				= dmin;
		
		this.position			= pos;

		this.proximity			= prox;
		this.reproduction		= repr;
		this.reproductibility	= repy;
		this.winnerTakeAll		= wta;
		
		this.connections		= conn;
		this.weights			= weights;
		this.currentLobe		= new ArrayList<Neuron>();
	}
	
	/**
	 * Add a dendrite connection to current Neuron. 
	 * @param input (Neuron)
	 * @param weight (int)
	 */
	public void addConnection(Neuron input,int weight) {
		this.connections.add(input);
		this.weights.add(weight);
	}
	
	/**
	 * Check if current Neuron has already a connection with a Neuron. 
	 * @param input (Neuron)
	 * @return (boolean) true if present, false if not. 
	 */
	public boolean hasConnected(Neuron input) {
		for (int i = 0 ; i < this.connections.size() ; i++) {
			if (this.connections.get(i).equals(input, true)) { return true; }
		}
		return false;
	}
	
	public int getConnectPosition(Neuron input) {
		for (int i = 0 ; i < this.connections.size() ; i++) {
			if (this.connections.get(i).equals(input, true)) { return i; }
		}
		return -1;
	}
	
	/** This method to recalculate activity of current Neuron. */
	public void recompute() {
		if (this.activity != this.restState) {
			int relative = (this.threshold - this.restState);
			int i = 0;
			while ( (i < this.descent) && (this.activity != this.restState) ) {
				if (relative > 0)	{ this.activity--; }
				else				{ this.activity++; }
				i++;
			}
		}
		for (int i = 0 ; i < this.connections.size() ; i++) {
			if (this.connections.get(i).isActivated()) 
				{ this.activity += this.weights.get(i).intValue(); }
		}
		this.activity = (this.activity < 0) ? 0 : (this.activity > 999) ? 999 : this.activity;
		
		this.change();
	}
	
	/**
	 * This method to force current Neuron to (re)make inputs connections. 
	 * Inputed dendrites / neurons are in back of the map.
	 * @param nn (Brain) Neural Network where current Neuron is.
	 */
	public void reconnection(Brain nn) {
		
		if (nn == null) { return; }
		
		// ***** Not removing / changing if under dmin 
		if (this.connections.size() > this.dmin) {
			// ***** starting from the last created connection
			for (int i = this.connections.size()-1 ; i >= this.dmin ; i--) {
				// ***** Change weights of Neurons, if activated or not. 
				// TODO change with a specific chemical ?? reinforcement or not !!
//				if (this.connections.get(i).isActivated()) {
//					if (this.weights.get(i).intValue() < 999)
//						{ this.weights.set(i, this.weights.get(i) + 1); }
//				} else {
//					if (this.weights.get(i).intValue() > 10)
//						{ this.weights.set(i, this.weights.get(i) - 1); }
//				}
				// ***** If weight is 0 : remove. 
				// if ( (!this.winnerTakeAll) && (this.weights.get(i).intValue() == 0) ) { 
				if ( (this.weights.get(i).intValue() == 0) 
						&& (this.connections.size() > this.dmin) ) {
					this.connections.remove(i);
					this.weights.remove(i);
				}
			}
		}
		// ***** Add new Neuron's if necessary. 
		if ( (this.connections.size() < this.dmin) && (this.position != null) ) {
			// ***** Here a set of activated Neurons 
			List<Neuron> candidates			= nn.getNeuronsBefore(this.position, this.proximity);
			Iterator<Neuron> iteOnNeuron	= candidates.iterator();
			while ( (this.connections.size() < this.dmax) && (iteOnNeuron.hasNext()) ) {
				Neuron candidate = iteOnNeuron.next();
				// ***** Test if same Neuron at same position is present. 
				if ( ! this.connections.contains( candidate )) { 
					this.connections.add( candidate );
					this.weights.add(new Integer(this.proximity));
				}
			}
		}
	}
	
	/**
	 * This method to make current Neuron reproduce itself if he can be 
	 * and is activated. Idea is to make reinforcement in neural network. 
	 * @param nn (Brain) Neural Network where current Neuron is.
	 */
	public void reproduce(Brain nn) {
		
		if (nn == null) { return; }
		
		int proximity = 1;
		if ( (this.reproduction) && (this.isActivated()) ) {
			if ( (this.reproductibility > 0) && (this.position != null) ) { 
				if (nn.getActivityBefore(this.position, proximity) <= this.dmin) { 
					IPosition nextpos = nn.getBestPositionNear(this.position, proximity);
					if (nextpos != null) {
						// ***** change reproductibility before cloning, avoid 'eternal reproduction' from clones. 
						this.reproductibility--; 
						Neuron nextneu = this.getCopy();
						nn.setNeuronAt(nextpos, nextneu);
					}
				}
			}
		} else {
			if ( (nn.getActivityNear(this.position, proximity) == 0)
					&& (this.currentLobe.size() == 0) ) 
				{ nn.remNeuronAt(this.position); }
		}
	}
	
	/**
	 * To know if current Neuron is activated. 
	 * Main to use in Neural Network. 
	 * @return (boolean)
	 */
	public boolean isActivated() {
		if (this.winnerTakeAll) {
			int max = Neuron.getHighestActivity( this.currentLobe );
			if ( (max != 0) && (this.activity != max) )
				{ this.activity = 0; }
			// XXX if some neurons have the same maximal activity ?!
			// keep it all these activated !
		}
		return this.ckActivated();
	}
	
	/**
	 * To only check activity of a Neuron. 
	 * @return (boolean)
	 * @see gabywald.biosilico.structures.NeuronListe#getHighestActivity()
	 * @see gabywald.biosilico.structures.NeuronListe#getFirestNeuron()
	 * @see Brain#getActivityNear(Position2D, int)
	 */
	public boolean ckActivated() {
		int relative = (this.threshold - this.restState);
		if ( ( (relative > 0) && (this.activity >= this.threshold) )
				|| ( (relative < 0) && (this.activity <= this.threshold) ) )
			{ return true; }
		else { return false; }
	}
	
	/**
	 * To get a clone of the object (identical properties and inputs). 
	 * If position is null, setting one at (-1,-1). 
	 * @return (Neuron) 'Exact clone of current Neuron'
	 */
	public Neuron getCopy() {
		/** If changes here of translating in an other language as C++
		 * take aware of transmission of data, in JAVA, this is copy
		 * of simple types and references for other : copy content !*/
		List<Neuron> tmpconn	= new ArrayList<Neuron>();
		List<Integer> tmpweig	= new ArrayList<Integer>();
		for (int i = 0 ; i < this.connections.size() ; i++) { 
			tmpconn.add(this.connections.get(i));
			tmpweig.add(this.weights.get(i));
		}
		// ***** Avoiding a nullException... 
		if (this.position == null) { this.position = PositionBuilder.buildPosition(-1,-1); }
		// ***** Returning instanciation of copy / clone... 
		return new Neuron(this.restState, this.threshold,
						  this.activity, this.descent, 
						  this.dmin, this.dmax, 
						  this.proximity, 
						  this.reproduction, 
						  this.reproductibility, 
						  this.winnerTakeAll, 
						  this.position.clone(), 
						  tmpconn, tmpweig);
	}
	
	/**
	 * To set the position of a Neuron. 
	 * @param position (IPosition) position. 
	 */
	public void setPosition(IPosition position)
		{ this.position = position; }
	
	/**
	 * To get Position of current Neuron. 
	 * @return (IPosition)
	 */
	public IPosition getPosition() 
		{ return this.position; }
	
	/**
	 * This method compare two instances of Neuron, only on their 'basics' 
	 * (i.e. not activity neither connections content and weights). 
	 * @param toCompare (Neuron)
	 * @return (boolean)
	 */
	public boolean equals(Neuron toCompare) {
		if (this.restState		!= toCompare.restState)		{ return false; }
		if (this.threshold		!= toCompare.threshold)		{ return false; }
		if (this.descent		!= toCompare.descent)		{ return false; }
		if (this.dmin			!= toCompare.dmin)			{ return false; }
		if (this.dmax			!= toCompare.dmax)			{ return false; }
		if (this.proximity		!= toCompare.proximity)		{ return false; }
		if (this.reproduction	!= toCompare.reproduction)	{ return false; }
		if (this.winnerTakeAll	!= toCompare.winnerTakeAll)	{ return false; }
		// ***** if any of previous is not happend !
		return true;
	}
	
	/**
	 * Compare two instances of Neuron and positions if asked. 
	 * @param toCompare (Neuron)
	 * @param hasPos (boolean) if true, compare positions. 
	 * @return (boolean)
	 * @see Neuron#equals(Neuron)
	 */
	public boolean equals(Neuron toCompare, boolean hasPos) {
		if (!this.equals(toCompare)) { return false; }
		if (hasPos) { return this.position.equals(toCompare.position); }
		return true;
	}
		
	public int getRestState()				{ return this.restState; }
	public int getThreshold()				{ return this.threshold; }
	public int getDescent()					{ return this.descent; }
	public int getDmin()					{ return this.dmin; }
	public int getDmax()					{ return this.dmax; }
	public int getProximity()				{ return this.proximity; }
	public boolean getReproduction()		{ return this.reproduction; }
	public int getReproductibility()		{ return this.reproductibility; }

	public int getActivity()				{ return this.activity; }
	public List<Neuron> getConnections()	{ return this.connections; }
	public List<Integer> getWeights()		{ return this.weights; }
	
	public void setActivity(int activity)	{ this.activity = activity; }
	public void addActivity(int more)		{ this.activity += more; }
	
	public boolean isWTA()					{ return this.winnerTakeAll; }
	public void setLobe(List<Neuron> lobe)	{ this.currentLobe = lobe; }
	public List<Neuron> getLobe()			{ return this.currentLobe; }
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("\t").append(this.restState);
		result.append("\t").append(this.threshold);
		result.append("\t").append(this.activity).append(" (").append(this.ckActivated()).append(")");
		result.append("\t").append(this.descent);
		result.append("\t").append(this.position);
		result.append("\t").append(this.dmin).append(":").append(this.dmax).append(" (").append(this.connections.size()).append(")");
		result.append("\t").append(this.connections.size());
		result.append("\t").append(this.reproduction?"true":"false");
		result.append("\t").append(this.reproductibility);
		result.append("\t").append(this.winnerTakeAll?"true":"false");
		result.append("\t").append(this.currentLobe.size()).append("\n");
		
		result.append( this.toStringWithConnectionsAndWeights());
		
		return result.toString();
	}
	
	private String toStringWithConnectionsAndWeights() {
		final StringBuilder result = new StringBuilder();
		// result.append(this.toString()).append("\n");
		if (this.connections.size() == 0) {
			// result.append("\tNo Backward connections. \n");
		} else {
			for (int i = 0 ; i < this.connections.size() ; i++) {
				result.append("\tBackLink").append( this.connections.get(i).getPosition().toString() );
				result.append("***").append( this.weights.get(i).toString() ).append("\n");
			}
		}
		return result.toString();
	}
	
	/**
	 * Get the highest activity in a set of Neuron's. 
	 * @param lobe (List&lt;Neuron&gt;)
	 * @return (int)
	 * @see gabywald.biosilico.model.Neuron#ckActivated()
	 */
	public static int getHighestActivity(List<Neuron> lobe) {
		int max = 0;
		for (int i = 0 ; i < lobe.size() ; i++) {
			Neuron tmp = lobe.get(i);
			if ( (tmp.ckActivated()) && (tmp.getActivity() > max) )
				{ max = tmp.getActivity(); }
		}
		return max;
	}
	
}

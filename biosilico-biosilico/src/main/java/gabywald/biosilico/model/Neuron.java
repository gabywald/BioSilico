package gabywald.biosilico.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class describes a neurone and its properties
 * (activation, threshold, rest state, and inputing connections). 
 * @author Gabriel Chandesris (2009, 2020)
 */
public class Neuron {
	/** Rest state of the neuron. */
	private int rest_state;
	/** Activation level of the neuron. */
	private int threshold;
	/** Current activity of the neuron. */
	private int activity;
	/** How quickly activity goes to rest state. MUST BE POSITIVE */
	private int descent;
	
	/** Position in the neural network. */
	private Position position;
	/** Minimal number of dendritic connections (inputs) [0 to 8]. */
	private int dendritic_min;
	/** Maximal number of dendritic connections (inputs) [0 to 8]. */
	private int dendritic_max;
	/** Table of neurones current Neuron is connected with (as dendrites). */
	private List<Neuron> connections;
	/** Respective weights attributed to input connections. */
	private List<Integer> weights;
	/** Current numbers of connections. */
	private int index;
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
	 * Default constructor, rest at 0, threshold at 100, 
	 * min and max dendrites at 0 (and 0 spaces for connections).
	 */
	public Neuron() {
		this(0, 100, 0, 10, null, 0, 0, new ArrayList<Neuron>(), new ArrayList<Integer>(), 0, 1, false, 0, false);
		// this((int rest, int thre, int acti, int desc, Position pos, int dmin, int dmax, NeuronListe conn, List<Integer> weights, 
		// int ind, int prox, boolean repr, int repy, boolean wta);
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
	 * @see Neuron#setPosition(int, int)
	 */
	public Neuron (int rest, int thre, int desc,
			int dendriticmin, int dendriticmax,
			int prox, boolean repr, int repy) {
		this(rest, thre, rest, rest, null, dendriticmin, dendriticmax, new ArrayList<Neuron>(), new ArrayList<Integer>(), 0, prox, repr, repy, false);
		// this((int rest, int thre, int acti, int desc, Position pos, int dmin, int dmax, NeuronListe conn, List<Integer> weights, 
		// int ind, int prox, boolean repr, int repy, boolean wta);
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
	public Neuron (int rest, int thre, int desc, 
			int dendriticmin, int dendriticmax,
			int prox, boolean repr, int repy, 
			boolean wta) {
		this(rest, thre, rest, rest, null, dendriticmin, dendriticmax, new ArrayList<Neuron>(), new ArrayList<Integer>(), 0, prox, repr, repy, wta);
		// this((int rest, int thre, int acti, int desc, Position pos, int dmin, int dmax, NeuronListe conn, List<Integer> weights, 
		// int ind, int prox, boolean repr, int repy, boolean wta);
	}
	
	/** 
	 * Private complete constructor (for copy / clone object).
	 * @param rest (int) rest state. 
	 * @param thre (int) threshold. 
	 * @param acti (int) activity. 
	 * @param desc (int) descent.
	 * @param pos (Position)
	 * @param dmin (int) dendritic minimal connections. 
	 * @param dmax (int) dendritic maximal connections. 
	 * @param conn (NeuronListe) set of input dendrites. 
	 * @param weights (IntegerListe) set of weights of intputs. 
	 * @param ind (int) index of sets. 
	 * @param prox (int) proximity. 
	 * @param repr (boolean) if current Neuron can reproduce. 
	 * @param repy (int) number of maximum reproductions. 
	 * @see Neuron#getCopy()
	 */
	private Neuron (int rest, int thre, int acti, int desc, 
					Position pos, int dmin, int dmax, 
					List<Neuron> conn, List<Integer> weights, 
					int ind, int prox, boolean repr, int repy, 
					boolean wta) {
		this.activity			= acti;
		this.connections		= conn;
		this.dendritic_max		= dmax;
		this.dendritic_min		= dmin;
		this.descent			= desc;
		this.index				= ind;
		this.position			= pos;
		this.rest_state			= rest;
		this.threshold			= thre;
		this.weights			= weights;
		this.proximity			= prox;
		this.reproduction		= repr;
		this.reproductibility	= repy;
		this.winnerTakeAll		= wta;
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
		if (this.activity != this.rest_state) {
			int relative = (this.threshold-this.rest_state);
			int i = 0;
			while ( (i < this.descent) 
					&& (this.activity != this.rest_state) ) {
				if (relative > 0) { this.activity--; }
				else { this.activity++; }
				i++;
			}
		}
		for (int i = 0 ; i < this.index ; i++) {
			if (this.connections.get(i).isActivated()) 
				{ this.activity += this.weights.get(i).intValue(); }
		}
		this.activity = 
			(this.activity<0)?0:(this.activity>999)?999:this.activity;
	}
	
	/**
	 * This method to force current Neuron to (re)make inputs connections. 
	 * Inputted dendrites / neurons are in back of the map.
	 * @param nn (Brain) Neural Network where current Neuron is.
	 */
	public void reconnection(Brain nn) {
		/** Not removing / changing if under dendritic_min */
		if (this.connections.size() > this.dendritic_min) {
			for (int i = this.index-1 
					; i >= this.dendritic_min /** 0 */ 
					; i--) {
				// ***** Change weights of Neurons, if activated or not. 
				if (this.connections.get(i).isActivated()) {
					if (this.weights.get(i).intValue() < 999)
						{ this.weights.set(i,  this.weights.get(i) + 1); }
				} else {/**  (!this.connections.getNeuron(i).isActivated()) */ 
					if (this.weights.get(i).intValue() > 0)
						{ this.weights.set(i,  this.weights.get(i) - 1); }
				}
				// ***** If weight is 0 or less : remove. 
				if ( (!this.winnerTakeAll) 
							&& (this.weights.get(i).intValue() <= 0) ) { 
						this.connections.remove(i);
						this.weights.remove(i);
						this.index--;
				}
			}
		}
		/** Add new Neuron's if necessary. */
		if ( (this.index < this.dendritic_min) && (this.position != null) ){
			List<Neuron> candidates = /** here a set of activated Neurons */
				nn.getNeuronBefore(this.position,this.proximity);
			while ( (this.index < this.dendritic_max) 
						&& candidates.size() > 0 ) {
				Neuron candidate = candidates.get(0);
				// ***** Test if same Neuron at same position is present. 
				if (!this.connections.contains(candidate)) { 
					this.connections.add(candidates.get(0));
					this.weights.add(new Integer(this.proximity));
//					int activ = this.connections.get(this.index).getActivity();
//					this.weights.add(new Integer(activ/2));
					this.index++;
				}
				candidates.remove(0);
			}
		}
	}
	
	/**
	 * This method to make current Neuron reproduce itself if he can be 
	 * and is activated. Idea is to make reinforcement in neural network. 
	 * @param nn (Brain) Neural Network where current Neuron is.
	 */
	public void reproduce(Brain nn) {
		int proximity = 1;
		if ( (this.reproduction) && (this.isActivated()) ) {
			if ( (this.reproductibility > 0) && (this.position != null) ){
				if (nn.getActivityBefore(this.position, proximity) 
						<= this.dendritic_min) { 
					Position nextpos = 
						nn.getBestPositionNear(this.position, proximity);
					if (nextpos != null) {
						/** change reproductibility before cloning, 
						 * avoid 'eternal reproduction' from clones. */
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
			int val = Neuron.getHighestActivity( this.currentLobe );
			if ( (val != 0) && (this.activity != val) )
				{ this.activity = 0; }
		}
		int relative = (this.threshold - this.rest_state);
		if ( (relative > 0) && (this.activity >= this.threshold) )
			{ return true; }
		else {
			if ( (relative < 0) && (this.activity <= this.threshold) )
				{ return true; }
			else { return false; }
		}
	}
	
	/**
	 * To only check activity of a Neuron. 
	 * @return (boolean)
	 * @see gabywald.biosilico.structures.NeuronListe#getHighestActivity()
	 * @see gabywald.biosilico.structures.NeuronListe#getFirestNeuron()
	 * @see Brain#getActivityNear(Position, int)
	 */
	public boolean ckActivated() {
		int relative = (this.threshold-this.rest_state);
		if ( (relative > 0) && (this.activity >= this.threshold) )
			{ return true; }
		else {
			if ( (relative < 0) && (this.activity <= this.threshold) )
				{ return true; }
			else { return false; }
		}
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
		/** Avoiding a nullException... */
		if (this.position == null) { this.position = new Position(-1,-1); }
		/** Returning instanciation of copy / clone... */
		return new Neuron(this.rest_state,this.threshold,
						  this.activity,this.descent, 
						  this.position.getCopy(), 
						  this.dendritic_min, 
						  this.dendritic_max, 
						  tmpconn, tmpweig, this.index, 
						  this.proximity, 
						  this.reproduction, 
						  this.reproductibility, 
						  this.winnerTakeAll);
	}
	
	/**
	 * To set the position of a Neuron. 
	 * @param posx (int) height position. 
	 * @param posy (int) width position. 
	 */
	public void setPosition(int posx,int posy)
		{ this.position = new Position(posx, posy); }
	
	/**
	 * To get Position of current Neuron. 
	 * @return (Position)
	 */
	public Position getPosition() { return this.position; }
	
	/**
	 * This method compare two instances of Neuron, only on their 'basics' 
	 * (i.e. not activity neither connections content and weights). 
	 * @param toCompare (Neuron)
	 * @return (boolean)
	 */
	public boolean equals(Neuron toCompare) {
		if (this.rest_state != toCompare.rest_state)		{ return false; }
		if (this.threshold != toCompare.threshold)			{ return false; }
		if (this.descent != toCompare.descent)				{ return false; }
		if (this.dendritic_min != toCompare.dendritic_min)	{ return false; }
		if (this.dendritic_max != toCompare.dendritic_max)	{ return false; }
		if (this.proximity != toCompare.proximity)			{ return false; }
		if (this.reproduction != toCompare.reproduction)	{ return false; }
		if (this.winnerTakeAll != toCompare.winnerTakeAll)	{ return false; }
		/** if any of previous has not append */
		return true;
	}
	
	/**
	 * Compare two instances of Neuron and positions if asked. 
	 * @param toCompare (Neuron)
	 * @param hasPos (boolean) if true, compare positions. 
	 * @return (boolean)
	 * @see Neuron#equals(Neuron)
	 */
	public boolean equals(Neuron toCompare,boolean hasPos) {
		if (!this.equals(toCompare)) { return false; }
		if (hasPos) { return this.position.equals(toCompare.position); }
		return true;
	}
		
	public int getRestState()				{ return this.rest_state; }
	public int getThreshold()				{ return this.threshold; }
	public int getDescent()					{ return this.descent; }
	public int getActivity()				{ return this.activity; }
	public List<Neuron> getConnections()	{ return this.connections; }
	public List<Integer> getWeights()		{ return this.weights; }
	
	public void setActivity(int activity)	{ this.activity = activity; }
	public void addActivity(int more)		{ this.activity += more; }
	
	public boolean isWTA()					{ return this.winnerTakeAll; }
	public void setLobe(List<Neuron> lobe)	{ this.currentLobe = lobe; }
	public List<Neuron> getLobe()			{ return this.currentLobe; }

	/**
	 * A typically receptor / perception Neuron. Rest at 0, threshold at 100 
	 * (?) and descent at 10. All other parameters at 0 or false. 
	 * WTA is false. 
	 * @return (Neuron) typical receptor. 
	 */
	public static Neuron getReceptorNeuron() 
		{ return new Neuron(0, 100, 10, 0, 0, 0, false, 0, false); }
	
	/**
	 * A typically emitter Neuron. Rest at 0, threshold at 100 
	 * (?) and descent at 10. Minimal dendritic inputs is 8 and maximal 
	 * is 16 (very high ??). Proximity of 4. No reproduction (false and 0). 
	 * WTA is false.  
	 * @return (Neuron) typical emitter. 
	 */
	public static Neuron getEmitterNeuron() 
		{ return new Neuron (0, 100, 10, 8, 16, 4, false, 0, false); }
	
	/**
	 * A typically decision Neuron. Rest at 0, threshold at 100 
	 * (?) and descent at 10. Minimal dendritic inputs is 8 and maximal 
	 * is 16 (very high ??). Proximity of 4. No reproduction (false and 0). 
	 * <b>WTA is true.</b> 
	 * @return (Neuron) typical decision. 
	 */
	public static Neuron getDecisionNeuron() 
		{ return new Neuron (0, 100, 10, 8, 16, 4, false, 0, true); }
	
	/**
	 * A typically conception / middle network Neuron. Rest at 0, 
	 * threshold at 100 (?) and descent at 10. 
	 * Minimal dendritic input is 3, maximal is 8, proximity of 2. 
	 * Can reproduce itself ten times (and copy inherits properties). 
	 * WTA is false. 
	 * @return (Neuron) typical conception. 
	 */
	public static Neuron getConceptionNeuron()
		{ return new Neuron (0, 100, 10, 3, 8, 2, true, 10, false); }
	
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

package gabywald.neuralnetwork.essentials.version2;

import java.util.Observable;


public class Neuron extends Observable {
	/** Length of inputs and weights associated. */
	private int inputLength;
	/** Inputs of current instance of Neuron. */
	private Neuron[] inputs;
	/** Weights of current instance of Neuron. */
	private double[] weights;
	/** Indicates if is activator or inhibiter. */
	private boolean activator;
	/** indicate if it is active or not (gives a result or not). */
	private boolean active;
	/** Activation level and output. */
	private double threshold, outputIfActive;
	/** Position in the Brain instance. */
	private int posX,posY;
	/** Instance of Brain where the instance of Neuron is. */
	private Brain brain;

	public Neuron(int xPos, int yPos, Brain study) 
		{ this.init(true, xPos, yPos, study); }

	public Neuron(boolean activator, int xPos, int yPos, Brain study) 
		{ this.init(activator, xPos, yPos, study); }

	private void init(boolean activator, int xPos, int yPos, Brain study) {
		this.inputLength	= 0;
		this.inputs			= new Neuron[this.inputLength];
		this.weights		= new double[this.inputLength];
		this.activator		= activator;
		this.active			= false;
		this.threshold		= 0.001;
		this.outputIfActive	= (this.activator)?1:-1;

		this.posX			= xPos;
		this.posY			= yPos;
		this.brain			= study;
	}

	public int getPosX()		{ return this.posX; }
	public int getPosY()		{ return this.posY; }
	public int getDistance(int xPos, int yPos) {
		int x = (int)Math.pow((this.posX-xPos), 2);
		int y = (int)Math.pow((this.posY-yPos), 2);
		int res = (int)Math.sqrt(x+y);
		return res;
	}

	public int getDistance(Neuron other) 
		{ return this.getDistance(other.posX, other.posY); }

	public static int getDistance(Neuron un, Neuron de) 
		{ return un.getDistance(de); }
	
	public Neuron[] getInputs() { return this.inputs; }

	public void setInputs(Neuron[] inputs) {
		this.inputs			= inputs;
		this.inputLength	= this.inputs.length;
		this.weights		= new double[this.inputLength];
		for (int  i = 0 ; i < this.inputLength ; i++) 
		{ this.weights[i] = 1.0; }
	}

	public void addInput(Neuron input) 
		{ this.addInput(input, 1); }

	public void addInput(Neuron input, double weight) {
		if (input != null) {
			Neuron[] nextInputs		= new Neuron[this.inputLength+1];
			double[] nextWeights	= new double[this.inputLength+1]; 
			for (int i = 0 ; i < this.inputLength ; i++) {
				nextInputs[i]	= this.inputs[i];
				nextWeights[i]	= this.weights[i];
			}
			nextInputs[this.inputLength]	= input;
			nextWeights[this.inputLength]	= weight;
			this.inputLength++; /** Update the length of inputs and weights. */
			this.inputs						= nextInputs;
			this.weights					= nextWeights;
		}
	}

	/**
	 * Removing an input at a certain position. 
	 * @param i (int) Position of the input to remove. 
	 */
	private void remInput(int i) {
		Neuron[] nextInputs		= new Neuron[this.inputLength-1];
		double[] nextWeights	= new double[this.inputLength-1];
		for (int k = 0 ; k < i ; k++) {
			nextInputs[k]	= this.inputs[k];
			nextWeights[k]	= this.weights[k];
		}
		for (int k = i+1 ; k < this.inputLength ; k++) {
			nextInputs[k-1]	= this.inputs[k];
			nextWeights[k-1]	= this.weights[k];
		}
		this.inputLength--; /** Update the length of inputs and weights. */
		this.inputs			= nextInputs;
		this.weights		= nextWeights;
	}

	public void activate()					
		{ this.active = true; }
	
	public void activateWith(double data)	{ 
		this.active = (data >= this.threshold);
		this.changeAndNotify();
		// System.out.println("\t"+data+"\t"+this.active+"\t"+this.output());
	}

	public void compute() {
		double result = 0;
		for (int i = 0 ; i < this.inputLength ; i++) { 
			result += this.inputs[i].output()*this.weights[i];
			/** compute is done separately to avoid border effects. */
		}
		this.active = (result >= this.threshold);
		
		/** if (this.active) 
		 * { System.out.println("\t"+this.toStringCoordinates()
		 * +" : "+this.output()); } */
		
		this.changeAndNotify();
		
		this.applyChanges();
	}

	public double output() 
		{ return (this.active)?this.outputIfActive:0; }

	/** Modifications in Neuron !! */
	private void applyChanges() {
		if (!this.active) { return; } /** Do NOT apply if not active... */
		for (int i = 0 ; i < this.inputLength ; i++) {
			int distance = this.inputs[i].getDistance(this.posX, this.posY);
			/** XXX to study changes in output : gain and / or loss of weight... */
			/** This input is enough to activate : gain of weight (10%). */ 
			if (this.inputs[i].output() > this.threshold) 
				{ this.increaseWeight(i, distance); }
			/** This input is not activated : loss of weight (10%). */
			if (this.inputs[i].output() == 0) 
				{ this.decreaseWeight(i, distance); }
			/** Weight is at 0 or less : removing input ! */
			if (this.weights[i] <= 0) { this.remInput(i); }
		}

		/** XXX to study changes in output : positive reception... */
		/** Value gets better if activator and active. */
		if ( (this.activator) && (this.active) ) 
			{ this.outputIfActive++; }
		/** Value gets lesser if inhibiter and active. */
		if ( (!this.activator) && (this.active) ) 
			{ this.outputIfActive--; }

		if ( (this.inputLength <= 5) && (this.posX != 0) ) 
			{ this.addInput(this.brain.getNeuron(this.posX-1, this.posY)); }
	}
	
	private void increaseWeight(int i, int distance)  { 
		if (distance > 0) 
			{ this.weights[i] += (this.weights[i]/100) * (10/distance); }
	}
	
	private void decreaseWeight(int i, int distance)  { 
		if (distance > 0) 
			{ this.weights[i] -= (this.weights[i]/100) * (10/distance); }
	}
	
	public void increaseAllWeights(int distance) {
		for (int i = 0 ; i < this.inputLength ; i++) 
			{ this.increaseWeight(i, distance); }
	}
	
	public void decreaseAllWeights(int distance) {
		for (int i = 0 ; i < this.inputLength ; i++) 
			{ this.decreaseWeight(i, distance); }
	}

	public int getInputLength() { return this.inputLength; }

	public String toStringCoordinates() 
		{ return "("+this.posX+", "+this.posY+")"; }

	public String toString() {
		String result = new String("");
		result += this.toStringCoordinates();
		result += "[\n";
		for (int i = 0 ; i < this.inputLength ; i++) 
			{ result += (this.inputs[i]!=null)?"\t"+this.weights[i]+"\t"
							+this.inputs[i].toStringCoordinates()+"\n":"\n"; }
		result += "]\n\n";
		return result;
	}
	
	
	public void changeAndNotify() {
		this.setChanged();
		this.notifyObservers();
	}
}

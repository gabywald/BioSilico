package gabywald.neuralnetwork.essentials.version1;

import gabywald.neuralnetwork.view.version1.NeuronButton;

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
	/** studyGain,studyLoss,studyPosRecept,studyNegRecept */
	private boolean[] studies;
	
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
		
		this.studies		= new boolean[4];
		for (int i = 0 ; i < this.studies.length ; i++) 
			{ this.studies[i] = false; }
		
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
	
	public void compute() {
		// if (this.posX == 0) { return; }
		double result = 0;
		for (int i = 0 ; i < this.inputLength ; i++) { 
			/** this.inputs[i].compute(); */ /** AVOID border effects. */
			result += this.inputs[i].output()*this.weights[i];
		}
		this.active = (result >= this.threshold);
		this.changeAndNotify();
		this.applyChanges();

	}
	
	/** Modifications in Neuron !! */
	private void applyChanges() {
		if (!this.active) { return; } /** Do NOT apply if not active... */
		for (int i = 0 ; i < this.inputLength ; i++) {
			int distance = this.inputs[i].getDistance(this.posX, this.posY);
			/** XXX to study changes in output : gain and / or loss of weight... */
			if (this.studies[0]) {
				/** This input is enough to activate : gain of weight (10%). */ 
				if (this.inputs[i].output() > this.threshold) 
					{ this.weights[i] += (this.weights[i]/100) * (10/distance); /** 1 || this.weights[i]/10.0 */; }
			}
			if (this.studies[1]) {
				/** This input is not activated : loss of weight (10%). */
				if (this.inputs[i].output() == 0) 
					{ this.weights[i] -= (this.weights[i]/100) * (50/distance); }
			}
			/** Weight is at 0 or less : removing input ! */
			if (this.weights[i] <= 0) { this.remInput(i); }
		}
		
		/** XXX to study changes in output : positive reception... */
		if (this.studies[2]) {
			/** Value gets better if activator and active. */
			if ( (this.activator) && (this.active) ) 
				{ this.outputIfActive++; }
			/** Value gets lesser if inhibiter and active. */
			if ( (!this.activator) && (this.active) ) 
				{ this.outputIfActive--; }
		}
		
		if (this.studies[3]) {
			if ( (this.inputLength < 2) && (this.posX != 0) ) { 
				this.addInput(this.brain.getNeuron(this.posX-1, this.posY));
				System.out.println("Add an input !! "+this.toStringCoordinates());
			}
		}
	}
	
	public double output() 
		{ return (this.active)?this.outputIfActive:0; }
	
	public void activate()					{ this.active = true; }
	
	public void activateWith(double data)	{ 
		this.active = (data >= this.threshold);
		this.changeAndNotify();
	}
	public void setOutput(double output)	{ this.outputIfActive = output; }
	
	
	public void activateStudy(int i) {
		if ( (i >= 0) && (i < this.studies.length) ) 
			{ this.studies[i] = true; }
	}
	
	
	
	public void addNeuronButtonObserver(NeuronButton button) 
		{ this.addObserver(button); }
	
	public void changeAndNotify() {
		// System.out.println("\t"+this.active+"\t"+this.threshold+"\t"+this.outputIfActive);
		this.setChanged();
		this.notifyObservers();
	}
	
	public Neuron[] getInputs() 
		{ return this.inputs; }
	
	public Neuron[] getInputsNotNull() {
		int count = 0;
		for (int i = 0 ; i < this.inputLength ; i++) 
			{ if (this.inputs[i] != null) { count++; } }
		Neuron[] res = new Neuron[count];
		for (int i = 0 ; i < this.inputLength ; i++) {
			if ( (this.inputs[i] != null) 
					&& (count < res.length) ) 
				{ res[count++] = this.inputs[i]; }
		}
		return res;
	}
	
	public String toStringCoordinates() 
		{ return "("+this.posX+", "+this.posY+") "; }

	public String toString() {
		String result = new String("");
		result += this.toStringCoordinates();
		result += "[\n";
		for (int i = 0 ; i < this.inputLength ; i++) 
			{ result += (this.inputs[i]!=null)?"\t"+this.weights[i]+"\t"+this.inputs[i].toStringCoordinates()+"\n":"\n"; }
		result += "]\n\n";
		return result;
	}
	
}

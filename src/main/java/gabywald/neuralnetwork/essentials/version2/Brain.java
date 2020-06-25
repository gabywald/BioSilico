package gabywald.neuralnetwork.essentials.version2;

import java.util.ArrayList;


public class Brain extends Thread {
	private Neuron[][] content;

	private boolean continued;

	private int count;

	public Brain(int width, int height) { 
		this.content	= new Neuron[width][height];
		this.continued	= false;
		this.count		= 0;
	}

	public int getWidth()	{ return this.content.length; }
	public int getHeight()	
		{ return (this.content.length > 0)?this.content[0].length:0; }

	public Neuron getNeuron(int x, int y) {
		if ( ( (x >= 0) && (x < this.getWidth()) )
				&& ( (y >= 0) && (y < this.getHeight()) ) ) {
			Neuron neu = this.content[x][y];
			if (neu != null) { return neu; }
		}
		return null;
	}

	public void setNeuron(int x, int y, Neuron neu) {
		if ( ( (x >= 0) && (x < this.getWidth()) )
				&& ( (y >= 0) && (y < this.getHeight()) ) )
		{ this.content[x][y] = neu; }
	}

	public void compute() {
		for (int i = this.getWidth()-1 ; i >= 0 ; i--) {
			for (int j = this.getHeight()-1 ; j > 0 ; j--) { 
				if (this.content[i][j] != null) 
					{ this.content[i][j].compute(); }
			}
		}
	}

	public void submit(double[] dataEnter) {
		for (int i = 0 ; (i < this.getWidth()) 
		&& (i < dataEnter.length) ; i++) 
		{ this.content[i][0].activateWith(dataEnter[i]); }
		this.compute();
	}

	public Neuron[] getLineAt(int height) {
		if ( (height >= 0) && (height < this.getHeight()) ) {
			Neuron[] result = new Neuron[this.getWidth()];
			for (int i = 0 ; i < this.getWidth() ; i++) 
			{ result[i] = this.content[i][height]; }
			/** System.out.println("\t"+i+"\t"+height+"\t"+((result[i]!=null)?result[i].toStringCoordinates():"null")); */
			/** System.out.println(result); */
			return result;
		} else { return new Neuron[0]; }
	}

	public Neuron[] getLastLine() 
	{ return this.getLineAt(this.getHeight()-1); }


	/**
	 * To get a whole list from a Neuron instance to start. 
	 * @param last (Neuron) where to start. 
	 * @return (Neuron[])
	 */
	public static Neuron[] backtracking(Neuron last) {
		ArrayList<Neuron> listing = new ArrayList<Neuron>();
		Neuron[] inputs = last.getInputs();
		for (int i = 0 ; i < inputs.length ; i++) { 
			listing.add(inputs[i]);
			Neuron[] more = Brain.backtracking(inputs[i]);
			for (int j = 0 ; j < more.length ; j++) 
			{ listing.add(more[j]); }
		}
		return listing.toArray(new Neuron[0]);
	}

	/**
	 * To get a whole list from a Neuron instance to a certain depth.
	 * @param last (Neuron) where to start. 
	 * @param depth (int) maximal depth (0 for first level). 
	 * @return (Neuron[])
	 */
	public static Neuron[] backtracking(Neuron last, int depth) {
		ArrayList<Neuron> listing = new ArrayList<Neuron>();
		Neuron[] inputs = last.getInputs();
		for (int i = 0 ; i < inputs.length ; i++) { 
			listing.add(inputs[i]);
			if (depth > 0) { 
				Neuron[] more = Brain.backtracking(inputs[i], depth-1);
				for (int j = 0 ; j < more.length ; j++) 
				{ listing.add(more[j]); }
			}
		}
		return listing.toArray(new Neuron[0]);
	}

	/**
	 * Useful to un-learn a path...
	 * @param list (Neuron[])
	 */
	public static void decreaseWeight(Neuron[] list) {
		for (int i = 0 ; i < list.length ; i++) 
		{ list[i].decreaseAllWeights(1); }
	}

	public void run() {
		while(true) {
			try {
				while (this.continued) {
					this.compute();
					Thread.sleep(100);
					this.count++;
					// System.out.println(this.count);
					if (this.count%(this.getHeight()/2) == 0) {
						// double[] datas = new double[this.getWidth()];
						// int loc = (int)(Math.random()*datas.length); // this.getWidth()/2; // 
						// for (int j = 0 ; j < datas.length ; j++) { 
						// 		if (j == loc) { datas[j] = 200; }
						//		else { datas[j] = 0; }
						// }
						double[] datas = new double[this.getWidth()];
						for (int j = 0 ; j < datas.length ; j++) { 
							datas[j] = (int)(Math.random()*5);
						}
						this.submit(datas);
					}
				}
				if (!this.continued) 
					{ while (!this.continued)	{ Thread.sleep(100); } }
			} catch (InterruptedException e)	{ e.printStackTrace(); }
		} /** END while(true) */
	}

	public synchronized void begin() { this.continued = true; }
	public synchronized void endin() { this.continued = false; }
	public synchronized boolean getContinued()	{ return this.continued; }


}

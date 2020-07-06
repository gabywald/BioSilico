package gabywald.neuralnetwork.essentials.version1;

public class Brain extends Thread {
	private int width,height;
	private Neuron[][] map;

	public Brain(int width, int height) {
		this.width	= width;
		this.height	= height;
		this.map	= new Neuron[this.width][this.height];
	}

	public int getWith()	{ return this.width; }
	public int getHeight()	{ return this.height; }

	public Neuron getNeuron(int x, int y) {
		if ( ( (x >= 0) && (x < this.width) )
				&& ( (y >= 0) && (y < this.height) ) ) {
			Neuron neu = this.map[x][y];
			if (neu != null) { return neu; }
		}
		return null;
	}

	public void setNeuron(int x, int y, Neuron neu) {
		if ( ( (x >= 0) && (x < this.width) )
				&& ( (y >= 0) && (y < this.height) ) )
		{ this.map[x][y] = neu; }
	}

	public Neuron[] getLineAt(int height) {
		if ( (height >= 0) && (height < this.height) ) {
			Neuron[] result = new Neuron[this.width];
			for (int i = 0 ; i < this.width ; i++) 
			{ result[i] = this.map[i][height]; }
			/** System.out.println("\t"+i+"\t"+height+"\t"+((result[i]!=null)?result[i].toStringCoordinates():"null")); */
			/** System.out.println(result); */
			return result;
		} else { return new Neuron[0]; }
	}

	public void compute() {
		//		for (int i = 0 ; i < this.width ; i++) {
		//			for (int j = 0 ; j < this.height ; j++) { 
		for (int i = this.width-1 ; i >= 0 ; i--) {
			for (int j = this.height-1 ; j >= 0 ; j--) { 
				// System.out.println(i+"\t"+j);
				this.map[i][j].compute(); 
			}
		}
	}

	public void submit(double[] dataEnter) {
		for (int i = 0 ; (i < this.width) 
				&& (i < dataEnter.length) ; i++) 
			{ this.map[i][0].activateWith(dataEnter[i]); }
		this.compute();
	}


	public void run() {
		int count = 0;
		while (true) {
			this.compute();
			try { Thread.sleep(100); }
			catch (InterruptedException e) 
			{ e.printStackTrace(); }
			count++;
			if (count%(this.height/2) == 0) {
				double[] datas = new double[this.width];
				//    			for (int j = 0 ; j < datas.length ; j++) 
				//				{ datas[j] = Math.random()*10+1; }
				int loc = 0; // (int)(Math.random()*datas.length);
				// System.out.println(loc+"");
				for (int j = 0 ; j < datas.length ; j++) { 
					if (j == loc) { datas[j] = 1; }
					else { datas[j] = 0; }
				}
				this.submit(datas);
//	    		try { Thread.sleep((int)Math.random()*100); }
//	    		catch (InterruptedException e) 
//	    			{ e.printStackTrace(); }
			}
		}
	}

	public String toString() {
		String result = new String("");
		result += "Width : "+this.width+"\tHeight : "+this.height+"\n";
		for (int i = 0 ; i < this.width ; i++) {
			for (int  j = 0 ; j < this.height ; j++) {
				result += "("+j+", "+i+") : \n"
				+((this.map[i][j]!=null)?this.map[i][j].toString():"null\n");
			}
		}
		return result;
	}
}

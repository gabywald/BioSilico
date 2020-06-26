package gabywald.cellmodel.model;


import gabywald.cellmodel.structures.ARNFile;
import gabywald.global.structures.ObservableObject;

import java.util.Random;



public class Noyau extends ObservableObject implements ContentARN { // extends Thread
	private Chromosome[] genome;
	private ARNFile tempo;
	
	/** For the runnable part. */
	private boolean alive;
	
	public Noyau() {
		this.genome = new Chromosome[1];
		this.genome[0] = new Chromosome();
		this.tempo = new ARNFile();
		
		this.alive = true;
	}

	public boolean addARN(ARN elt) {
		this.tempo.push(elt);
		return true;
	}

	public boolean remARN(ARN elt) 
		{ return this.tempo.remove(elt); }
	
	public ARN popARN() { return this.tempo.pop(); }
	
	public int length() { return this.tempo.length(); }
	
	public void transcription() {
		Random gen = new Random();
		int chrom_loc = gen.nextInt(this.genome.length);		
		int depart = gen.nextInt(this.genome[chrom_loc].length());
		int fin = gen.nextInt(this.genome[chrom_loc].length());
		while (fin < depart) 
			{ fin = gen.nextInt(this.genome[chrom_loc].length()); }
		this.addARN(new ARN(this.genome[chrom_loc].getSubsequence(depart, fin)));
	}

	
	
	public void kill() { this.alive = false; }
	
	public void run() {
		while(this.alive) {
			Random gen = new Random();
			for (int i = 0 ; i < gen.nextInt(1000) ; i++)
				{ this.transcription(); }
			this.setState(" Noyau "+this.length());
			try { Thread.sleep(1000); } 
			catch (InterruptedException e) { e.printStackTrace(); }
			this.change();
		}
	}
	
	public synchronized void event(boolean threadSuspended) 
			throws InterruptedException {
		if (threadSuspended) { this.notify(); } 
		else { this.wait(); }
	}
}

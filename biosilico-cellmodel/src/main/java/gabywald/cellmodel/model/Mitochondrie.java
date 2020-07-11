package gabywald.cellmodel.model;

import gabywald.cellmodel.structures.ARNFile;
import gabywald.cellmodel.structures.ProteinFile;
import gabywald.cellmodel.structures.RibosomeListe;
import gabywald.global.structures.ObservableObject;

import java.util.List;
import java.util.Random;

/**
 * 
 * @author Gabriel Chandesris (2009, 2020)
 */
public class Mitochondrie 
		extends ObservableObject 
		implements ContentARN,ContentProtein {
	private Chromosome[] genome;
	private ARNFile tempo;
	private ProteinFile tempa;
	private RibosomeListe tempi;
	
	public static final int USURE_PROTEIQUE = 100;
	
	/** For the Thread part. */
	private boolean alive;
	
	public Mitochondrie() {
		this.genome = new Chromosome[1];
		this.genome[0] = new Chromosome();
		this.tempo = new ARNFile();
		this.tempa = new ProteinFile();
		this.tempi = new RibosomeListe();
		
		this.alive = true;
	}
	
	public boolean addARN(ARN elt) {
		this.tempo.push(elt);
		return true;
	}

	public boolean remARN(ARN elt)
		{ return this.tempo.remove(elt); }

	public boolean addProtein(Protein elt) {
		this.tempa.push(elt);
		return true;
	}

	public boolean remProtein(Protein elt) 
		{ return this.tempa.remove(elt); }
	
	public boolean isEmpty() { return (this.tempa.length() == 0); }
	public int length() { return 0; }
	public int length_arn() { return this.tempo.length(); }
	public int length_pro() { return this.tempa.length(); }
	public int length_rib() { return this.tempi.length(); }
	public Protein popProtein() { return this.tempa.pop(); }

	/**
	 * Generate a list of Vesicule's. 
	 * @return (VesiculeListe) here is <b>null</b> because do not externalize proteines
	 * @see Vesicule#VESICULE_LEVEL
	 */
	public List<Vesicule> transport() 
		{ return null; }
	
	
	public void transcription() {
		Random gen = new Random();
		int chrom_loc = gen.nextInt(this.genome.length);		
		int depart = gen.nextInt(this.genome[chrom_loc].length());
		int fin = gen.nextInt(this.genome[chrom_loc].length());
		while (fin < depart) 
			{ fin = gen.nextInt(this.genome[chrom_loc].length()); }
		this.addARN(new ARN(this.genome[chrom_loc].getSubsequence(depart, fin)));
	}
	
	public void traduction() {
		if (this.tempo.length() > Ribosome.DEFAULT_START_USE) {
			ARN un = this.tempo.pop();
			ARN de = this.tempo.pop();
			ARN tr = this.tempo.pop();
			this.tempi.addRibosome(new Ribosome(un,de,tr));
		}
		for (int i = 0 ; 
		(i < this.tempi.length()) 
		&& (!this.tempo.isFileEmpty()) ; 
		i++) {
			Ribosome tmprib = this.tempi.getRibosome(i);
			ARN tmparn = this.tempo.pop();
			Protein tmppro = tmprib.traduction(tmparn);
			this.addProtein(tmppro);
			if (tmprib.used()) { this.tempi.removeRibosome(tmprib); }
		}
	}

	public void usure() {
		if (this.length_pro() >= Mitochondrie.USURE_PROTEIQUE) {
			// int taux_usure = this.length_pro()/MitochondrieObservable.USURE_PROTEIQUE;
			Random gen = new Random();
			for (int i = 0 ; i < gen.nextInt(Mitochondrie.USURE_PROTEIQUE) ; i++)
				{ this.tempa.pop(); }
		}
	}
	
	public void kill() { this.alive = false; }

	public void run() {
		while(this.alive) {
			if (this.length_pro() < Mitochondrie.USURE_PROTEIQUE) {
				Random gen = new Random();
				for (int i = 0 ; i < gen.nextInt(Mitochondrie.USURE_PROTEIQUE) ; i++)
					{ this.transcription(); }
			}
			this.traduction();
			this.usure();
			
			this.setState(" Mitochondrie "+this.tempo.length()+":"+this.tempa.length()+":"+this.tempi.length());
			try { Thread.sleep(1000); } 
			catch (InterruptedException e) { e.printStackTrace(); }
		}
	}
	
	public synchronized void event(boolean threadSuspended) 
			throws InterruptedException {
		if (threadSuspended) { this.notify(); } 
		else { this.wait(); }
	}
}

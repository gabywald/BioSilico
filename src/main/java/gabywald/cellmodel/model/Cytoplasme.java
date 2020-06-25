package gabywald.cellmodel.model;

import gabywald.cellmodel.structures.ARNFile;
import gabywald.cellmodel.structures.ProteinFile;
import gabywald.cellmodel.structures.RibosomeListe;
import gabywald.cellmodel.structures.VesiculeListe;
import gabywald.global.structures.ObservableObject;

public class Cytoplasme extends ObservableObject implements ContentARN,ContentProtein { // extends Thread
	private ARNFile tempo;
	private ProteinFile tempa;
	private RibosomeListe tempi;

	/** For the runnable part. */
	private boolean alive;

	public Cytoplasme() {
		this.tempa = new ProteinFile();
		this.tempo = new ARNFile();
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
	 * @return (VesiculeListe)
	 * @see Vesicule#VESICULE_LEVEL
	 */
	public VesiculeListe transport() {
		VesiculeListe liste = new VesiculeListe();
		if (this.tempa.length() > Vesicule.VESICULE_LEVEL) {
			for (int i = 0 ; i < this.tempa.length()%Vesicule.VESICULE_LEVEL ; i++) {
				int j = 0;
				Vesicule vesi = new Vesicule(Vesicule.TYPE_GOLGI);
				Protein tmp = this.tempa.pop();
				while( (tmp != null) && (j < Vesicule.VESICULE_LEVEL) ) {
					vesi.addProtein(tmp);
					tmp = this.tempa.pop();
					j++;
				}
				if (!vesi.isEmpty()) { liste.addVesicule(vesi); }
			}
		}
		return liste;
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
	

	public void kill() { this.alive = false; }

	public void run() {
		while(this.alive) {
			this.traduction();
			this.setState(" Cytoplasme "+this.tempo.length()+":"+this.tempa.length()+":"+this.tempi.length());
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

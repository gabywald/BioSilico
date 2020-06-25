package gabywald.cellmodel.model;

import gabywald.cellmodel.structures.ProteinFile;
import gabywald.cellmodel.structures.VesiculeListe;
import gabywald.global.structures.ObservableObject;
import java.util.Random;

public class AppareilDeGolgi 
		extends ObservableObject 
		implements Runnable,ContentProtein {
	private ProteinFile tempa;
	
	/** For the runnable part. */
	private boolean alive;
	
	
	public AppareilDeGolgi() {
		this.tempa = new ProteinFile();
		this.alive = true;
	}

	public boolean addProtein(Protein elt) {
		this.tempa.push(elt);
		return true;
	}

	public boolean remProtein(Protein elt) 
		{ return this.tempa.remove(elt); }
	
	public boolean isEmpty() { return (this.tempa.length() == 0); }
	public int length() { return this.tempa.length(); }
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
				Random type_generator = new Random();
				Vesicule vesi = new Vesicule(type_generator.nextInt(Vesicule.HIGHER_TYPE));
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

	
	
	
	public void kill() { this.alive = false; }
	
	public void run() {
		while(this.alive) {
			this.setState(" AppareilDeGolgi "+this.tempa.length());
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

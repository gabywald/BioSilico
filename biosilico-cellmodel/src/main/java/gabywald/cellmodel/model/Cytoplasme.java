package gabywald.cellmodel.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import gabywald.global.structures.ObservableObject;

/**
 * 
 * @author Gabriel Chandesris (2009)
 */
public class Cytoplasme extends ObservableObject implements ContentARN,ContentProtein { // extends Thread
	private Queue<ARN> arns;
	private Queue<Protein> proteins;
	private List<Ribosome> ribosomes;

	/** For the runnable part. */
	private boolean alive;

	public Cytoplasme() {
		this.proteins	= new LinkedList<Protein>();
		this.arns		= new LinkedList<ARN>();
		this.ribosomes	= new ArrayList<Ribosome>();

		this.alive = true;
	}

	public boolean addARN(ARN elt) {
		return this.arns.add(elt);
	}

	public boolean remARN(ARN elt) { 
		return this.arns.remove(elt);
	}

	public boolean addProtein(Protein elt) {
		return this.proteins.add(elt);
	}

	public boolean remProtein(Protein elt) { 
		return this.proteins.remove(elt);
	}
	
	public boolean isEmpty()	{ return (this.proteins.size() == 0); }
	public int length()			{ return 0; }
	public int lengthARN() 		{ return this.arns.size(); }
	public int lengthProtein() 	{ return this.proteins.size(); }
	public int lengthRibosome() { return this.ribosomes.size(); }
	public Protein popProtein()	{ return this.proteins.poll(); }

	/**
	 * Generate a list of Vesicule's. 
	 * @return (VesiculeListe)
	 * @see Vesicule#VESICULE_LEVEL
	 */
	public List<Vesicule> transport() {
		List<Vesicule> liste = new ArrayList<Vesicule>();
		if (this.proteins.size() > Vesicule.VESICULE_LEVEL) {
			for (int i = 0 ; i < this.proteins.size()%Vesicule.VESICULE_LEVEL ; i++) {
				int j = 0;
				Vesicule vesi	= new Vesicule(Vesicule.TYPE_GOLGI);
				Protein tmp		= this.proteins.poll();
				while( (tmp != null) && (j < Vesicule.VESICULE_LEVEL) ) {
					vesi.addProtein(tmp);
					tmp = this.proteins.poll();
					j++;
				}
				if (!vesi.isEmpty()) { liste.add(vesi); }
			}
		}
		return liste;
	}
	
	
	public void traduction() {
		if (this.arns.size() > Ribosome.DEFAULT_START_USE) {
			ARN un = this.arns.poll();
			ARN de = this.arns.poll();
			ARN tr = this.arns.poll();
			this.ribosomes.add(new Ribosome(un,de,tr));
		}
		for (int i = 0 ; 
				(i < this.ribosomes.size()) && (!this.arns.isEmpty()) ; 
				i++) {
			Ribosome tmprib = this.ribosomes.get(i);
			ARN tmparn		= this.arns.poll();
			Protein tmppro	= tmprib.traduction(tmparn);
			this.addProtein(tmppro);
			if (tmprib.used()) 
				{ this.ribosomes.remove(tmprib); }
		}
	}
	

	public void kill() { this.alive = false; }

	public void run() {
		while(this.alive) {
			this.traduction();
			this.setState(" Cytoplasme "+this.arns.size()+":"+this.proteins.size()+":"+this.ribosomes.size());
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

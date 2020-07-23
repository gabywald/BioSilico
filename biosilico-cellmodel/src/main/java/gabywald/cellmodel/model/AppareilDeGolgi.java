package gabywald.cellmodel.model;

import gabywald.global.structures.ObservableObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * 
 * @author Gabriel Chandesris (2009, 2020)
 */
public class AppareilDeGolgi 
		extends ObservableObject 
		implements Runnable, ContentProtein {
	private Queue<Protein> proteins;
	
	/** For the runnable part. */
	private boolean alive;
	
	public AppareilDeGolgi() {
		this.proteins	= new LinkedList<Protein>();
		this.alive			= true;
	}

	public boolean addProtein(Protein elt) {
		this.proteins.add(elt);
		return true;
	}

	public boolean remProtein(Protein elt) 
		{ return this.proteins.remove(elt); }
	
	public boolean isEmpty()	{ return (this.proteins.size() == 0); }
	public int length()			{ return this.proteins.size(); }
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
				Random type_generator = new Random();
				Vesicule vesi	= new Vesicule(type_generator.nextInt(Vesicule.HIGHER_TYPE));
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
	
	public void kill() { this.alive = false; }
	
	public void run() {
		while(this.alive) {
			this.setState(" AppareilDeGolgi "+this.proteins.size());
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

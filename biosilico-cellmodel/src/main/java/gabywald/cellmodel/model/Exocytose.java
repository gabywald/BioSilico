package gabywald.cellmodel.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 
 * @author Gabriel Chandesris (2009)
 */
public class Exocytose implements ContentProtein {
	private Queue<Protein> proteins;
	
	public Exocytose() 
		{ this.proteins = new LinkedList<Protein>(); }
	
	public boolean addProtein(Protein elt) {
		this.proteins.add(elt);
		return true;
	}

	public boolean remProtein(Protein elt) 
	{ return this.proteins.remove(elt); }
	
	public boolean isEmpty()	{ return (this.proteins.size() == 0); }
	public int length()			{ return this.proteins.size(); }
	public Protein popProtein()	{ return this.proteins.poll(); }

	public List<Vesicule> transport() {
		this.proteins = new LinkedList<Protein>();
		return new ArrayList<Vesicule>();
	}

}

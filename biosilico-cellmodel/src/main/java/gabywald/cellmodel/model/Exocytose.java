package gabywald.cellmodel.model;

import gabywald.cellmodel.structures.ProteinFile;
import gabywald.cellmodel.structures.VesiculeListe;

/**
 * 
 * @author Gabriel Chandesris (2009)
 */
public class Exocytose implements ContentProtein {
	private ProteinFile tempa;
	
	public Exocytose() 
		{ this.tempa = new ProteinFile(); }
	
	public boolean addProtein(Protein elt) {
		this.tempa.push(elt);
		return true;
	}

	public boolean remProtein(Protein elt) 
	{ return this.tempa.remove(elt); }
	
	public boolean isEmpty() { return (this.tempa.length() == 0); }
	public int length() { return this.tempa.length(); }
	public Protein popProtein() { return this.tempa.pop(); }

	public VesiculeListe transport() {
		this.tempa = new ProteinFile();
		return new VesiculeListe();
	}

}

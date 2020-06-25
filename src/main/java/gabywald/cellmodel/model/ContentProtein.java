package gabywald.cellmodel.model;

import gabywald.cellmodel.structures.VesiculeListe;

public interface ContentProtein extends Content {
	// abstract private ProteinFile tempa;
	abstract public boolean addProtein(Protein elt);
	abstract public boolean remProtein(Protein elt);
	abstract public boolean isEmpty();
	abstract public int length();
	abstract public Protein popProtein();
	abstract public VesiculeListe transport();
	
}

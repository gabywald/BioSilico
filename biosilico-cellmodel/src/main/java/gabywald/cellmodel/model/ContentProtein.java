package gabywald.cellmodel.model;

import java.util.List;

/**
 * 
 * @author Gabriel Chandesris (2009, 2020)
 */
public interface ContentProtein extends Content {
	public boolean addProtein(Protein elt);
	public boolean remProtein(Protein elt);
	public boolean isEmpty();
	public int length();
	public Protein popProtein();
	public List<Vesicule> transport();
}

package gabywald.cellmodel.model;

/**
 * 
 * @author Gabriel Chandesris (2009)
 */
public class Ribosome {
	private int use;
	
	public static final int DEFAULT_START_USE = 50;

	public Ribosome (ARN un, ARN de, ARN tr) {
		this.use = Ribosome.DEFAULT_START_USE;
	}

	public Protein traduction(ARN current) {
		String proteic_sequence = "";
		if (this.use <= 0) { return null; }
		if (current == null) { return null; }
		for (int i = 0 ; i < current.length() ; i += 3) {
			Triplet elt = current.getCodon(i);
			if (elt != null)
				{ proteic_sequence += Triplet.standardGeneticCode(elt); }
			proteic_sequence += "-";
		}
		this.use--;
		return new Protein(proteic_sequence);
	}
	
	public boolean used() {	return (this.use <=0 ); }
}

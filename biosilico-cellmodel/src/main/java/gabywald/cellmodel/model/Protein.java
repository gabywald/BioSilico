package gabywald.cellmodel.model;

/**
 * 
 * @author Gabriel Chandesris (2009)
 */
public class Protein extends SequencableEntity {

	public Protein(String sequence) {
		if (sequence.matches("[ARNDCQEGHILKMFPSTWYV*]+")) 
			{ this.sequence = sequence; } 
		else { this.sequence = ""; }
	}
	
	public boolean equals(Protein elt) {
		return (elt.getSequence().equals(this.getSequence()));
	}
	
}

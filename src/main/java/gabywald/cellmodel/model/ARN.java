package gabywald.cellmodel.model;

import gabywald.biosilico.exceptions.TripletException;

public class ARN extends SequencableEntity {
	
	public ARN(String sequence) 
		{ this.sequence = ARN.ADNtoARN(sequence); }
	
	private static String ADNtoARN(String sequence) {
		if (sequence.matches("[acgt]+")) {
			sequence.replace('t', 'u');
			return sequence;
		} else { return ""; }
	}
	
	public Triplet getCodon(int pos) {
		if ( (pos < 0) || (pos > this.sequence.length()) )
			{ return null; }
		if ( (pos+3) > this.sequence.length())
			{ return null; }
		Triplet tmp = null;
		try {
			tmp = new Triplet(this.sequence.substring(pos,pos+3));
		} catch (TripletException e) { tmp = null; }
		return tmp;
	}
	
	public boolean equals(ARN elt) {
		return (elt.getSequence().equals(this.getSequence()));
	}
}

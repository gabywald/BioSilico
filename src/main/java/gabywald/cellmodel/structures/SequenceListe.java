package gabywald.cellmodel.structures;

import gabywald.biosilico.utils.Sequence;
import gabywald.global.structures.ObjectListe;

/**
 * This class to provide easy-to-use Sequence tables / Lists. 
 * @author Gabriel Chandesris (2008)
 */
public class SequenceListe extends ObjectListe {
	/** The list of Sequence's. */
	private Sequence[] liste;
	
	/** Default constructor with a list of 0 elements. */
	public SequenceListe() 
		{ this.liste = new Sequence[0]; }
	
	/**
	 * Constructor with a pre-made table of Sequence. 
	 * @param liste
	 */
	public SequenceListe(Sequence[] liste) 
		{ this.liste = liste; }
	
	public int length() { return this.liste.length; }
	public Sequence[] getListe() { return this.liste; }
	public Sequence getSequence(String nom) {
		for (int i = 0 ; i < this.liste.length ; i++) {
			if (this.liste[i].getNom().equals(nom))
				{ return this.liste[i]; }
		}
		return null;
	}
	public Sequence getSequence(int i) { 
		/** These controls with if to be sure... */ 
		if (i >= this.liste.length) { return null; }
		if (i < 0) { return null; }
		return this.liste[i]; 
	}
	public void setListe(Sequence[] liste) { this.liste = liste; }
	public void setSequence(Sequence elt,int i) {
		if ( (i < this.liste.length) && (i >= 0) )
			{ this.liste[i] = elt; }
	}
	
	/**
	 * To add a Sequence at the end of the list. 
	 * @param elt Sequence to add. 
	 */
	public void addSequence(Sequence elt) {
		Sequence[] nextListe = new Sequence[this.liste.length+1];
		for (int i = 0 ; i < this.liste.length ; i++) 
			{ nextListe[i] = this.liste[i]; }
		nextListe[this.liste.length] = elt;
		this.liste = nextListe;
	}
	
	/**
	 * To add an Sequence at a specific position. 
	 * <br>If Position greater than current length : added at end of the list. 
	 * @param elt (Sequence)
	 * @param pos (int)
	 * @see SequenceListe#addSequence(Sequence)
	 */
	public void addSequence(Sequence elt,int pos) {
		if (pos >= this.liste.length) { this.addSequence(elt); }
		Sequence[] nextListe = new Sequence[this.liste.length+1];
		for (int i = 0 ; i < pos ; i++) 
			{ nextListe[i] = this.liste[i]; }
		nextListe[pos] = elt;
		for (int i = pos ; i < this.liste.length ; i++) 
			{ nextListe[i+1] = this.liste[i]; }	
		this.liste = nextListe;
	}
	
	/**
	 * To know if a Sequence is in this list. 
	 * @param elt Sequence. 
	 * @return boolean
	 */
	public boolean has(Sequence elt) {
		if (elt == null) { return false; }
		for (int i = 0 ; i < this.liste.length ; i++) {
			if (this.liste[i].equals(elt)) { return true; }
		}
		return false;
	}
	
	/**
	 * To remove a specific Sequence. 
	 * @param elt Sequence to remove
	 */
	public void removeSequence(Sequence elt) {
		if (this.has(elt)) {
			Sequence[] nextListe = new Sequence[this.liste.length-1];
			int i = 0;
			while ( (i < this.liste.length) && (!this.liste[i].equals(elt)) ) 
				{ nextListe[i] = this.liste[i];i++; }
			if (this.liste[i].equals(elt)) {
				i++;
				while (i < this.liste.length)
					{ nextListe[i-1] = this.liste[i];i++; }
			}
			this.liste = nextListe;
		}
	}
	
	/**
	 * To remove a Sequence at a specific place in the list. 
	 * @param nbElt Position of the Sequence. 
	 */
	public void removeSequence(int nbElt) {
		if ( (nbElt >= 0) && (nbElt < this.liste.length) ) {
			Sequence[] nextListe = new Sequence[this.liste.length-1];
			int i = 0;
			while ( (i < this.liste.length) && (i != nbElt) ) 
				{ nextListe[i] = this.liste[i];i++; }
			if (i == nbElt) {
				i++;
				while (i < this.liste.length)
					{ nextListe[i-1] = this.liste[i];i++; }
			}
			this.liste = nextListe;
		}
	}
	
	
	
	/**
	 * To know if two SequenceListe are equals (local instance and an other). 
	 * @param toCompare Other SequenceListe. 
	 * @return boolean
	 */
	public boolean equals(SequenceListe toCompare) {
		if (this.liste.length != toCompare.length()) { return false; }
		for (int i = 0 ; i < this.liste.length ; i++) {
			if (!this.liste[i].equals(toCompare.getSequence(i)))
				{ return false; }
		}
		return true;
	}
	
	/**
	 * To get a copy of the current SequenceListe.
	 * @return (SequenceListe)
	 */
	public SequenceListe getCopy() {
		SequenceListe next = new SequenceListe();
		for (int i = 0 ; i < this.length() ; i++) 
			{ next.addSequence((Sequence)this.getSequence(i).getCopy()); }
		return next;
	}
	
	/**
	 * To know if current instance of SequenceListe is in a table 
	 * of SequenceListe's.  
	 * @param listes (SequenceListe[]) (table of SequenceListe's). 
	 * @return (boolean) true if is in liste, else false;
	 */
	public boolean isIn(SequenceListe[] listes) {
		for (int i = 0 ; i < listes.length ; i++) 
			{ if (this.equals(listes[i])) { return true; } }
		return false;
	}
	
}

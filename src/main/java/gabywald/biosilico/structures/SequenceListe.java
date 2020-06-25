package gabywald.biosilico.structures;

import gabywald.biosilico.utils.Sequence;
import gabywald.global.structures.ObjectListe;

/**
 * This class to provide easy-to-use Sequence tables / Lists. 
 * @author Gabriel Chandesris (2008)
 */
public class SequenceListe extends ObjectListe {
	/**
	 * To get the length of the current list of Object's
	 * @return (int)
	 * @see ObjectListe#length()
	 * @see ObjectListe#liste
	 */
	public int length() { return this.liste.length; }
	
	/** 
	 * Default constructor with a list of 0 elements.
	 * @see ObjectListe#ObjectListe() */
	public SequenceListe() { super(); }
	
	/**
	 * Constructor with a pre-made table of Sequence's. 
	 * @param liste (Sequence[])
	 * @see ObjectListe#ObjectListe(Object[])
	 */
	public SequenceListe(Sequence[] liste) { super(liste); }
	
	/**
	 * To get the current list of Sequence's as a table. 
	 * @return (Sequence[]) A table of Sequence's.
	 * @see ObjectListe#getListeObjects()
	 */
	public Sequence[] getListeSequences() {
		Sequence[] tabReturn = new Sequence[this.length()];
		for (int i = 0 ; i < this.length() ; i++) 
			{ tabReturn[i] = this.getSequence(i); }
		return tabReturn;
		// return (Sequence[])super.getListeObjects(); 
	} 
	
	/**
	 * To get a specific Sequence of the list. 
	 * @param i (int) Position of the Sequence in the list. 
	 * @return (Sequence) Sequence at position i. 
	 * @see ObjectListe#getObject(int)
	 */
	public Sequence getSequence(int i) 
		{ return (Sequence)super.getObject(i); }
	
	/**
	 * To set a new list in the instance of SequenceListe. 
	 * @param liste (Sequence[]) A table of Sequence's. 
	 * @see ObjectListe#setListe(Object[])
	 */
	public void setListe(Sequence[] liste) 
		{ super.setListe(liste); }
	
	/**
	 * To set an Sequence at a specific place in the list, replace the old one. 
	 * @param elt (Sequence) Sequence to set. 
	 * @param i (int) Greater or equal to 0, and lower to liste.length.
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void setSequence(Sequence elt,int i) 
		{ super.setObject(elt, i); }
	
	/**
	 * To add an Sequence to the end of the list. 
	 * @param elt (Sequence) Sequence to add. 
	 * @see ObjectListe#addObject(Object)
	 */
	public void addSequence(Sequence elt) 
		{ super.addObject(elt); }
	
	/**
	 * To add an Sequence at a specific position. 
	 * <br>If Position greater than current length : added at end of the list. 
	 * @param elt (Sequence)
	 * @param pos (int)
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void addSequence(Sequence elt,int pos) 
		{ super.addObject(elt, pos); }
	
	/**
	 * To know if an Sequence is present is the list. 
	 * @param elt (Sequence)
	 * @return (boolean)
	 * @see ObjectListe#has(Object)
	 */
	public boolean has(Sequence elt) 
		{ return super.has(elt); }
	
	/**
	 * To remove a specific Sequence (nothing append if not present). 
	 * @param elt (Sequence) Sequence to remove. 
	 * @see ObjectListe#removeObject(Object)
	 */
	public void removeSequence(Sequence elt) 
		{ super.removeObject(elt); }
	
	/**
	 * To remove an Sequence at a specific place in the liste.
	 * @param nbElt (int) Position of the Sequence
	 * @see ObjectListe#removeObject(int)
	 */
	public void removeSequence(int nbElt) 
		{ super.removeObject(nbElt); }
	
	/**
	 * Adapted place for methods and functions of sorting is here. 
	 * Your Sequence and Classes used to make the table[] liste must
	 * have something to sort them...
	 */
	
	/**
	 * To get a copy of the current SequenceListe.
	 * @return (SequenceListe)Sequence
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

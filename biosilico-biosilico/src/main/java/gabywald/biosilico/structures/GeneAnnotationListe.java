package gabywald.biosilico.structures;

import gabywald.biosilico.genetics.GeneAnnotation;
import gabywald.global.structures.ObjectListe;

/**
 * 
 * @author Gabriel Chandesris (2010)
 * @deprecated to List !!
 */
public class GeneAnnotationListe extends ObjectListe {
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
	public GeneAnnotationListe() { super(); }
	
	/**
	 * Constructor with a pre-made table of GeneAnnotation's. 
	 * @param liste (GeneAnnotation[])
	 * @see ObjectListe#ObjectListe(Object[])
	 */
	public GeneAnnotationListe(GeneAnnotation[] liste) { super(liste); }
	
	/**
	 * To get the current list of GeneAnnotation's as a table. 
	 * @return (GeneAnnotation[]) A table of GeneAnnotation's.
	 * @see ObjectListe#getListeObjects()
	 */
	public GeneAnnotation[] getListeGeneAnnotations() {
		GeneAnnotation[] tabReturn = new GeneAnnotation[this.length()];
		for (int i = 0 ; i < this.length() ; i++) 
			{ tabReturn[i] = this.getGeneAnnotation(i); }
		return tabReturn;
		// return (GeneAnnotation[])super.getListeObjects(); 
	} 
	
	/**
	 * To get a specific GeneAnnotation of the list. 
	 * @param i (int) Position of the GeneAnnotation in the list. 
	 * @return (GeneAnnotation) GeneAnnotation at position i. 
	 * @see ObjectListe#getObject(int)
	 */
	public GeneAnnotation getGeneAnnotation(int i) 
		{ return (GeneAnnotation)super.getObject(i); }
	
	/**
	 * To set a new list in the instance of GeneAnnotationListe. 
	 * @param liste (GeneAnnotation[]) A table of GeneAnnotation's. 
	 * @see ObjectListe#setListe(Object[])
	 */
	public void setListe(GeneAnnotation[] liste) 
		{ super.setListe(liste); }
	
	/**
	 * To set an GeneAnnotation at a specific place in the list, replace the old one. 
	 * @param elt (GeneAnnotation) GeneAnnotation to set. 
	 * @param i (int) Greater or equal to 0, and lower to liste.length.
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void setGeneAnnotation(GeneAnnotation elt,int i) 
		{ super.setObject(elt, i); }
	
	/**
	 * To add an GeneAnnotation to the end of the list. 
	 * @param elt (GeneAnnotation) GeneAnnotation to add. 
	 * @see ObjectListe#addObject(Object)
	 */
	public void addGeneAnnotation(GeneAnnotation elt) 
		{ super.addObject(elt); }
	
	/**
	 * To add an GeneAnnotation at a specific position. 
	 * <br>If Position greater than current length : added at end of the list. 
	 * @param elt (GeneAnnotation)
	 * @param pos (int)
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void addGeneAnnotation(GeneAnnotation elt,int pos) 
		{ super.addObject(elt, pos); }
	
	/**
	 * To know if an GeneAnnotation is present is the list. 
	 * @param elt (GeneAnnotation)
	 * @return (boolean)
	 * @see ObjectListe#has(Object)
	 */
	public boolean has(GeneAnnotation elt) 
		{ return super.has(elt); }
	
	/**
	 * To remove a specific GeneAnnotation (nothing append if not present). 
	 * @param elt (GeneAnnotation) GeneAnnotation to remove. 
	 * @see ObjectListe#removeObject(Object)
	 */
	public void removeGeneAnnotation(GeneAnnotation elt) 
		{ super.removeObject(elt); }
	
	/**
	 * To remove an GeneAnnotation at a specific place in the liste.
	 * @param nbElt (int) Position of the GeneAnnotation
	 * @see ObjectListe#removeObject(int)
	 */
	public void removeGeneAnnotation(int nbElt) 
		{ super.removeObject(nbElt); }
	
	/**
	 * Adapted place for methods and functions of sorting is here. 
	 * Your GeneAnnotation and Classes used to make the table[] liste must
	 * have something to sort them...
	 */
	
	/**
	 * To get the SequenceListe of annotated genes. 
	 * @return (SequenceListe)
	 */
	public SequenceListe getSequenceListe() {
		SequenceListe geneListe = new SequenceListe();
		for (int i = 0 ; i < this.length() ; i++) 
			{ geneListe.addSequence(this.getGeneAnnotation(i).getSequence()); }
		return geneListe;
	}
	
	public GeneAnnotation getLastGeneAnnotation() 
		{ return (GeneAnnotation)this.liste[this.length()-1]; }

}

package gabywald.biosilico.structures;

import gabywald.biosilico.genetics.GeneAnnotationFeature;
import gabywald.global.structures.ObjectListe;

/**
 * To provide a set of GeneAnnotationFeature. 
 * @author Gabriel Chandesris (2010)
 */
public class GeneAnnotationFeatureListe extends ObjectListe {
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
	public GeneAnnotationFeatureListe() { super(); }
	
	/**
	 * Constructor with a pre-made table of GeneAnnotationFeature's. 
	 * @param liste (GeneAnnotationFeature[])
	 * @see ObjectListe#ObjectListe(Object[])
	 */
	public GeneAnnotationFeatureListe(GeneAnnotationFeature[] liste) { super(liste); }
	
	/**
	 * To get the current list of GeneAnnotationFeature's as a table. 
	 * @return (GeneAnnotationFeature[]) A table of GeneAnnotationFeature's.
	 * @see ObjectListe#getListeObjects()
	 */
	public GeneAnnotationFeature[] getListeGeneAnnotationFeatures() {
		GeneAnnotationFeature[] tabReturn = new GeneAnnotationFeature[this.length()];
		for (int i = 0 ; i < this.length() ; i++) 
			{ tabReturn[i] = this.getGeneAnnotationFeature(i); }
		return tabReturn;
		// return (GeneAnnotationFeature[])super.getListeObjects(); 
	}
	
	/**
	 * To get a specific GeneAnnotationFeature of the list. 
	 * @param i (int) Position of the GeneAnnotationFeature in the list. 
	 * @return (GeneAnnotationFeature) GeneAnnotationFeature at position i. 
	 * @see ObjectListe#getObject(int)
	 */
	public GeneAnnotationFeature getGeneAnnotationFeature(int i) 
		{ return (GeneAnnotationFeature)super.getObject(i); }
	
	/**
	 * To set a new list in the instance of GeneAnnotationFeatureListe. 
	 * @param liste (GeneAnnotationFeature[]) A table of GeneAnnotationFeature's. 
	 * @see ObjectListe#setListe(Object[])
	 */
	public void setListe(GeneAnnotationFeature[] liste) 
		{ super.setListe(liste); }
	
	/**
	 * To set an GeneAnnotationFeature at a specific place in the list, replace the old one. 
	 * @param elt (GeneAnnotationFeature) GeneAnnotationFeature to set. 
	 * @param i (int) Greater or equal to 0, and lower to liste.length.
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void setGeneAnnotationFeature(GeneAnnotationFeature elt,int i) 
		{ super.setObject(elt, i); }
	
	/**
	 * To add an GeneAnnotationFeature to the end of the list. 
	 * @param elt (GeneAnnotationFeature) GeneAnnotationFeature to add. 
	 * @see ObjectListe#addObject(Object)
	 */
	public void addGeneAnnotationFeature(GeneAnnotationFeature elt) 
		{ super.addObject(elt); }
	
	/**
	 * To add an GeneAnnotationFeature at a specific position. 
	 * <br>If Position greater than current length : added at end of the list. 
	 * @param elt (GeneAnnotationFeature)
	 * @param pos (int)
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void addGeneAnnotationFeature(GeneAnnotationFeature elt,int pos) 
		{ super.addObject(elt, pos); }
	
	/**
	 * To know if an GeneAnnotationFeature is present is the list. 
	 * @param elt (GeneAnnotationFeature)
	 * @return (boolean)
	 * @see ObjectListe#has(Object)
	 */
	public boolean has(GeneAnnotationFeature elt) 
		{ return super.has(elt); }
	
	/**
	 * To remove a specific GeneAnnotationFeature (nothing append if not present). 
	 * @param elt (GeneAnnotationFeature) GeneAnnotationFeature to remove. 
	 * @see ObjectListe#removeObject(Object)
	 */
	public void removeGeneAnnotationFeature(GeneAnnotationFeature elt) 
		{ super.removeObject(elt); }
	
	/**
	 * To remove an GeneAnnotationFeature at a specific place in the liste.
	 * @param nbElt (int) Position of the GeneAnnotationFeature
	 * @see ObjectListe#removeObject(int)
	 */
	public void removeGeneAnnotationFeature(int nbElt) 
		{ super.removeObject(nbElt); }
	
	/**
	 * Adapted place for methods and functions of sorting is here. 
	 * Your GeneAnnotationFeature and Classes used to make the table[] liste must
	 * have something to sort them...
	 */
}

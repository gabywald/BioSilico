package gabywald.biosilico.structures;

import gabywald.biosilico.genetics.Gene;
import gabywald.global.structures.ObjectListe;

/**
 * This class defines a list of Gene's instances. 
 * @author Gabriel Chandesris (2010)
 * @deprecated to List !!
 */
public class GeneListe extends ObjectListe {
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
	public GeneListe() { super(); }
	
	/**
	 * Constructor with a pre-made table of Gene's. 
	 * @param liste (Gene[])
	 * @see ObjectListe#ObjectListe(Object[])
	 */
	public GeneListe(Gene[] liste) { super(liste); }
	
	/**
	 * To get the current list of Gene's as a table. 
	 * @return (Gene[]) A table of Gene's.
	 * @see ObjectListe#getListeObjects()
	 */
	public Gene[] getListeGenes() {
		Gene[] tabReturn = new Gene[this.length()];
		for (int i = 0 ; i < this.length() ; i++) 
			{ tabReturn[i] = this.getGene(i); }
		return tabReturn;
		// return (Gene[])super.getListeObjects(); 
	}
	
	/**
	 * To get a specific Gene of the list. 
	 * @param i (int) Position of the Gene in the list. 
	 * @return (Gene) Gene at position i. 
	 * @see ObjectListe#getObject(int)
	 */
	public Gene getGene(int i) 
		{ return (Gene)super.getObject(i); }
	
	/**
	 * To set a new list in the instance of GeneListe. 
	 * @param liste (Gene[]) A table of Gene's. 
	 * @see ObjectListe#setListe(Object[])
	 */
	public void setListe(Gene[] liste) 
		{ super.setListe(liste); }
	
	/**
	 * To set an Gene at a specific place in the list, replace the old one. 
	 * @param elt (Gene) Gene to set. 
	 * @param i (int) Greater or equal to 0, and lower to liste.length.
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void setGene(Gene elt,int i) 
		{ super.setObject(elt, i); }
	
	/**
	 * To add an Gene to the end of the list. 
	 * @param elt (Gene) Gene to add. 
	 * @see ObjectListe#addObject(Object)
	 */
	public void addGene(Gene elt) 
		{ super.addObject(elt); }
	
	/**
	 * To add an Gene at a specific position. 
	 * <br>If Position greater than current length : added at end of the list. 
	 * @param elt (Gene)
	 * @param pos (int)
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void addGene(Gene elt,int pos) 
		{ super.addObject(elt, pos); }
	
	/**
	 * To know if an Gene is present is the list. 
	 * @param elt (Gene)
	 * @return (boolean)
	 * @see ObjectListe#has(Object)
	 */
	public boolean has(Gene elt) 
		{ return super.has(elt); }
	
	/**
	 * To remove a specific Gene (nothing append if not present). 
	 * @param elt (Gene) Gene to remove. 
	 * @see ObjectListe#removeObject(Object)
	 */
	public void removeGene(Gene elt) 
		{ super.removeObject(elt); }
	
	/**
	 * To remove an Gene at a specific place in the liste.
	 * @param nbElt (int) Position of the Gene
	 * @see ObjectListe#removeObject(int)
	 */
	public void removeGene(int nbElt) 
		{ super.removeObject(nbElt); }
	
	/**
	 * Adapted place for methods and functions of sorting is here. 
	 * Your Gene and Classes used to make the table[] liste must
	 * have something to sort them...
	 */
}

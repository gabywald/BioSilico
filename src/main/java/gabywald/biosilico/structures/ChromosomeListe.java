package gabywald.biosilico.structures;

import gabywald.biosilico.model.Chromosome;
import gabywald.global.structures.ObjectListe;

/**
 * To provide a set of Chromosome. 
 * @author Gabriel Chandesris (2008-2010)
 */
public class ChromosomeListe extends ObjectListe {
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
	public ChromosomeListe() { super(); }
	
	/**
	 * Constructor with a pre-made table of Chromosome's. 
	 * @param liste (Chromosome[])
	 * @see ObjectListe#ObjectListe(Object[])
	 */
	public ChromosomeListe(Chromosome[] liste) { super(liste); }
	
	/**
	 * To get the current list of Chromosome's as a table. 
	 * @return (Chromosome[]) A table of Chromosome's.
	 * @see ObjectListe#getListeObjects()
	 */
	public Chromosome[] getListeChromosomes() {
		Chromosome[] tabReturn = new Chromosome[this.length()];
		for (int i = 0 ; i < this.length() ; i++) 
			{ tabReturn[i] = this.getChromosome(i); }
		return tabReturn;
		// return (Chromosome[])super.getListeObjects(); 
	}
	
	/**
	 * To get a specific Chromosome of the list. 
	 * @param i (int) Position of the Chromosome in the list. 
	 * @return (Chromosome) Chromosome at position i. 
	 * @see ObjectListe#getObject(int)
	 */
	public Chromosome getChromosome(int i) 
		{ return (Chromosome)super.getObject(i); }
	
	/**
	 * To set a new list in the instance of ChromosomeListe. 
	 * @param liste (Chromosome[]) A table of Chromosome's. 
	 * @see ObjectListe#setListe(Object[])
	 */
	public void setListe(Chromosome[] liste) 
		{ super.setListe(liste); }
	
	/**
	 * To set an Chromosome at a specific place in the list, replace the old one. 
	 * @param elt (Chromosome) Chromosome to set. 
	 * @param i (int) Greater or equal to 0, and lower to liste.length.
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void setChromosome(Chromosome elt,int i) 
		{ super.setObject(elt, i); }
	
	/**
	 * To add an Chromosome to the end of the list. 
	 * @param elt (Chromosome) Chromosome to add. 
	 * @see ObjectListe#addObject(Object)
	 */
	public void addChromosome(Chromosome elt) 
		{ super.addObject(elt); }
	
	/**
	 * To add an Chromosome at a specific position. 
	 * <br>If Position greater than current length : added at end of the list. 
	 * @param elt (Chromosome)
	 * @param pos (int)
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void addChromosome(Chromosome elt,int pos) 
		{ super.addObject(elt, pos); }
	
	/**
	 * To know if an Chromosome is present is the list. 
	 * @param elt (Chromosome)
	 * @return (boolean)
	 * @see ObjectListe#has(Object)
	 */
	public boolean has(Chromosome elt) 
		{ return super.has(elt); }
	
	/**
	 * To remove a specific Chromosome (nothing append if not present). 
	 * @param elt (Chromosome) Chromosome to remove. 
	 * @see ObjectListe#removeObject(Object)
	 */
	public void removeChromosome(Chromosome elt) 
		{ super.removeObject(elt); }
	
	/**
	 * To remove an Chromosome at a specific place in the liste.
	 * @param nbElt (int) Position of the Chromosome
	 * @see ObjectListe#removeObject(int)
	 */
	public void removeChromosome(int nbElt) 
		{ super.removeObject(nbElt); }
	
	/**
	 * Adapted place for methods and functions of sorting is here. 
	 * Your Chromosome and Classes used to make the table[] liste must
	 * have something to sort them...
	 */
}

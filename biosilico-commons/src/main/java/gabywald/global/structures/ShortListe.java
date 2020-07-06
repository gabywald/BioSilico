package gabywald.global.structures;

/**
 * A set of Short. 
 * @author Gabriel Chandesris (2010)
 * @deprecated to List<Short>
 */
public class ShortListe extends ObjectListe {
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
	public ShortListe() { super(); }
	
	/**
	 * Constructor with a pre-made table of Short's. 
	 * @param liste (Short[])
	 * @see ObjectListe#ObjectListe(Object[])
	 */
	public ShortListe(Short[] liste) { super(liste); }
	
	/**
	 * To get the current list of Short's as a table. 
	 * @return (Short[]) A table of Short's.
	 * @see ObjectListe#getListeObjects()
	 */
	public Short[] getListeShorts() {
		Short[] tabReturn = new Short[this.length()];
		for (int i = 0 ; i < this.length() ; i++) 
			{ tabReturn[i] = this.getShort(i); }
		return tabReturn;
		// return (Short[])super.getListeObjects(); 
	} 
	
	/**
	 * To get a specific Short of the list. 
	 * @param i (int) Position of the Short in the list. 
	 * @return (Short) Short at position i. 
	 * @see ObjectListe#getObject(int)
	 */
	public Short getShort(int i) 
		{ return (Short)super.getObject(i); }
	
	/**
	 * To set a new list in the instance of ShortListe. 
	 * @param liste (Short[]) A table of Short's. 
	 * @see ObjectListe#setListe(Object[])
	 */
	public void setListe(Short[] liste) 
		{ super.setListe(liste); }
	
	/**
	 * To set an Short at a specific place in the list, replace the old one. 
	 * @param elt (Short) Short to set. 
	 * @param i (int) Greater or equal to 0, and lower to liste.length.
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void setShort(Short elt,int i) 
		{ super.setObject(elt, i); }
	
	/**
	 * To add an Short to the end of the list. 
	 * @param elt (Short) Short to add. 
	 * @see ObjectListe#addObject(Object)
	 */
	public void addShort(Short elt) 
		{ super.addObject(elt); }
	
	/**
	 * To add an Short at a specific position. 
	 * <br>If Position greater than current length : added at end of the list. 
	 * @param elt (Short)
	 * @param pos (int)
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void addShort(Short elt,int pos) 
		{ super.addObject(elt, pos); }
	
	/**
	 * To know if an Short is present is the list. 
	 * @param elt (Short)
	 * @return (boolean)
	 * @see ObjectListe#has(Object)
	 */
	public boolean has(Short elt) 
		{ return super.has(elt); }
	
	/**
	 * To remove a specific Short (nothing append if not present). 
	 * @param elt (Short) Short to remove. 
	 * @see ObjectListe#removeObject(Object)
	 */
	public void removeShort(Short elt) 
		{ super.removeObject(elt); }
	
	/**
	 * To remove an Short at a specific place in the liste.
	 * @param nbElt (int) Position of the Short
	 * @see ObjectListe#removeObject(int)
	 */
	public void removeShort(int nbElt) 
		{ super.removeObject(nbElt); }
	
	/**
	 * Adapted place for methods and functions of sorting is here. 
	 * Your Short and Classes used to make the table[] liste must
	 * have something to sort them...
	 */


}

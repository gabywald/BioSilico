package gabywald.global.structures;

/**
 * this class defines a set of StringListe's. 
 * @author Gabriel Chandesris (2010)
 * @deprecated to List<List<String> > (2020)
 */
public class StringListeListe extends ObjectListe {
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
	public StringListeListe() { super(); }
	
	/**
	 * Constructor with a pre-made table of StringListe's. 
	 * @param liste (StringListe[])
	 * @see ObjectListe#ObjectListe(Object[])
	 */
	public StringListeListe(StringListe[] liste) { super(liste); }
	
	/**
	 * To get the current list of StringListe's as a table. 
	 * @return (StringListe[]) A table of StringListe's.
	 * @see ObjectListe#getListeObjects()
	 */
	public StringListe[] getListeStringListes() {
		StringListe[] tabReturn = new StringListe[this.length()];
		for (int i = 0 ; i < this.length() ; i++) 
			{ tabReturn[i] = this.getStringListe(i); }
		return tabReturn;
		// return (StringListe[])super.getListeObjects(); 
	} 
	
	/**
	 * To get a specific StringListe of the list. 
	 * @param i (int) Position of the StringListe in the list. 
	 * @return (StringListe) StringListe at position i. 
	 * @see ObjectListe#getObject(int)
	 */
	public StringListe getStringListe(int i) 
		{ return (StringListe)super.getObject(i); }
	
	/**
	 * To set a new list in the instance of StringListeListe. 
	 * @param liste (StringListe[]) A table of StringListe's. 
	 * @see ObjectListe#setListe(Object[])
	 */
	public void setListe(StringListe[] liste) 
		{ super.setListe(liste); }
	
	/**
	 * To set an StringListe at a specific place in the list, replace the old one. 
	 * @param elt (StringListe) StringListe to set. 
	 * @param i (int) Greater or equal to 0, and lower to liste.length.
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void setStringListe(StringListe elt,int i) 
		{ super.setObject(elt, i); }
	
	/**
	 * To add an StringListe to the end of the list. 
	 * @param elt (StringListe) StringListe to add. 
	 * @see ObjectListe#addObject(Object)
	 */
	public void addStringListe(StringListe elt) 
		{ super.addObject(elt); }
	
	/**
	 * To add an StringListe at a specific position. 
	 * <br>If Position greater than current length : added at end of the list. 
	 * @param elt (StringListe)
	 * @param pos (int)
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void addStringListe(StringListe elt,int pos) 
		{ super.addObject(elt, pos); }
	
	/**
	 * To know if an StringListe is present is the list. 
	 * @param elt (StringListe)
	 * @return (boolean)
	 * @see ObjectListe#has(Object)
	 */
	public boolean has(StringListe elt) 
		{ return super.has(elt); }
	
	/**
	 * To remove a specific StringListe (nothing append if not present). 
	 * @param elt (StringListe) StringListe to remove. 
	 * @see ObjectListe#removeObject(Object)
	 */
	public void removeStringListe(StringListe elt) 
		{ super.removeObject(elt); }
	
	/**
	 * To remove an StringListe at a specific place in the liste.
	 * @param nbElt (int) Position of the StringListe
	 * @see ObjectListe#removeObject(int)
	 */
	public void removeStringListe(int nbElt) 
		{ super.removeObject(nbElt); }
	
	/**
	 * Adapted place for methods and functions of sorting is here. 
	 * Your StringListe and Classes used to make the table[] liste must
	 * have something to sort them...
	 */
}

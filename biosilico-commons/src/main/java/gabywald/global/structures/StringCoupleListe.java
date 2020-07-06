package gabywald.global.structures;

/**
 * To provide a set of StringCouple's. 
 * @author Gabriel Chandesris (2010)
 * @deprecated ... 
 */
public class StringCoupleListe extends ObjectListe {
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
	public StringCoupleListe() { super(); }
	
	/**
	 * Constructor with a pre-made table of StringCouple's. 
	 * @param liste (StringCouple[])
	 * @see ObjectListe#ObjectListe(Object[])
	 */
	public StringCoupleListe(StringCouple[] liste) { super(liste); }
	
	/**
	 * To get the current list of StringCouple's as a table. 
	 * @return (StringCouple[]) A table of StringCouple's.
	 * @see ObjectListe#getListeObjects()
	 */
	public StringCouple[] getListeStringCouples() {
		StringCouple[] tabReturn = new StringCouple[this.length()];
		for (int i = 0 ; i < this.length() ; i++) 
			{ tabReturn[i] = this.getStringCouple(i); }
		return tabReturn;
		// return (StringCouple[])super.getListeObjects(); 
	} 
	
	/**
	 * To get a specific StringCouple of the list. 
	 * @param i (int) Position of the StringCouple in the list. 
	 * @return (StringCouple) StringCouple at position i. 
	 * @see ObjectListe#getObject(int)
	 */
	public StringCouple getStringCouple(int i) 
		{ return (StringCouple)super.getObject(i); }
	
	/**
	 * To set a new list in the instance of StringCoupleListe. 
	 * @param liste (StringCouple[]) A table of StringCouple's. 
	 * @see ObjectListe#setListe(Object[])
	 */
	public void setListe(StringCouple[] liste) 
		{ super.setListe(liste); }
	
	/**
	 * To set an StringCouple at a specific place in the list, replace the old one. 
	 * @param elt (StringCouple) StringCouple to set. 
	 * @param i (int) Greater or equal to 0, and lower to liste.length.
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void setStringCouple(StringCouple elt,int i) 
		{ super.setObject(elt, i); }
	
	/**
	 * To add an StringCouple to the end of the list. 
	 * @param elt (StringCouple) StringCouple to add. 
	 * @see ObjectListe#addObject(Object)
	 */
	public void addStringCouple(StringCouple elt) 
		{ super.addObject(elt); }
	
	/**
	 * To add an StringCouple at a specific position. 
	 * <br>If Position greater than current length : added at end of the list. 
	 * @param elt (StringCouple)
	 * @param pos (int)
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void addStringCouple(StringCouple elt,int pos) 
		{ super.addObject(elt, pos); }
	
	/**
	 * To know if an StringCouple is present is the list. 
	 * @param elt (StringCouple)
	 * @return (boolean)
	 * @see ObjectListe#has(Object)
	 */
	public boolean has(StringCouple elt) 
		{ return super.has(elt); }
	
	/**
	 * To remove a specific StringCouple (nothing append if not present). 
	 * @param elt (StringCouple) StringCouple to remove. 
	 * @see ObjectListe#removeObject(Object)
	 */
	public void removeStringCouple(StringCouple elt) 
		{ super.removeObject(elt); }
	
	/**
	 * To remove an StringCouple at a specific place in the liste.
	 * @param nbElt (int) Position of the StringCouple
	 * @see ObjectListe#removeObject(int)
	 */
	public void removeStringCouple(int nbElt) 
		{ super.removeObject(nbElt); }
	
	/**
	 * Adapted place for methods and functions of sorting is here. 
	 * Your StringCouple and Classes used to make the table[] liste must
	 * have something to sort them...
	 */
	
	
	
	/**
	 * To extract one or other elements in the couple in a StringListe.  
	 * @param AorB boolean. 
	 * @return (StringListe)
	 */
	public StringListe getStringListe(boolean AorB) {
		StringListe extractListe = new StringListe();
		for (int i = 0 ; i < this.length() ; i++) {
			StringCouple tmp = this.getStringCouple(i);
			String toAdd = (AorB)?tmp.getValueA():tmp.getValueB();
			extractListe.addString(toAdd);
		}
		return extractListe;
	}
	
	public String toStringStandard() {
		String result = new String();
		for (int i = 0 ; i < this.liste.length ; i++) 
			{ result += i+"\t"+this.liste[i].toString()+"\n"; }
		return result;
	}
	
}

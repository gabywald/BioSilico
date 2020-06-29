package gabywald.global.structures;

/**
 * An example of simple use of the ObjectListe class inheritance. 
 * <br>Just replacing <i>Object</i> by <i>ObjectExample</i>. 
 * <br>Removing function and methods which do not need cast !!
 * @author Gabriel Chandesris (2010)
 * @see ObjectListe
 */
public class ObjectExampleListe extends ObjectListe {
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
	public ObjectExampleListe() { super(); }
	
	/**
	 * Constructor with a pre-made table of ObjectExample's. 
	 * @param liste (ObjectExample[])
	 * @see ObjectListe#ObjectListe(Object[])
	 */
	public ObjectExampleListe(ObjectExample[] liste) { super(liste); }
	
	/**
	 * To get the current list of ObjectExample's as a table. 
	 * @return (ObjectExample[]) A table of ObjectExample's.
	 * @see ObjectListe#getListeObjects()
	 */
	public ObjectExample[] getListeObjectExamples() {
		ObjectExample[] tabReturn = new ObjectExample[this.length()];
		for (int i = 0 ; i < this.length() ; i++) 
			{ tabReturn[i] = this.getObjectExample(i); }
		return tabReturn;
		// return (ObjectExample[])super.getListeObjects(); 
	} 
	
	/**
	 * To get a specific ObjectExample of the list. 
	 * @param i (int) Position of the ObjectExample in the list. 
	 * @return (ObjectExample) ObjectExample at position i. 
	 * @see ObjectListe#getObject(int)
	 */
	public ObjectExample getObjectExample(int i) 
		{ return (ObjectExample)super.getObject(i); }
	
	/**
	 * To set a new list in the instance of ObjectExampleListe. 
	 * @param liste (ObjectExample[]) A table of ObjectExample's. 
	 * @see ObjectListe#setListe(Object[])
	 */
	public void setListe(ObjectExample[] liste) 
		{ super.setListe(liste); }
	
	/**
	 * To set an ObjectExample at a specific place in the list, replace the old one. 
	 * @param elt (ObjectExample) ObjectExample to set. 
	 * @param i (int) Greater or equal to 0, and lower to liste.length.
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void setObjectExample(ObjectExample elt,int i) 
		{ super.setObject(elt, i); }
	
	/**
	 * To add an ObjectExample to the end of the list. 
	 * @param elt (ObjectExample) ObjectExample to add. 
	 * @see ObjectListe#addObject(Object)
	 */
	public void addObjectExample(ObjectExample elt) 
		{ super.addObject(elt); }
	
	/**
	 * To add an ObjectExample at a specific position. 
	 * <br>If Position greater than current length : added at end of the list. 
	 * @param elt (ObjectExample)
	 * @param pos (int)
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void addObjectExample(ObjectExample elt,int pos) 
		{ super.addObject(elt, pos); }
	
	/**
	 * To know if an ObjectExample is present is the list. 
	 * @param elt (ObjectExample)
	 * @return (boolean)
	 * @see ObjectListe#has(Object)
	 */
	public boolean has(ObjectExample elt) 
		{ return super.has(elt); }
	
	/**
	 * To remove a specific ObjectExample (nothing append if not present). 
	 * @param elt (ObjectExample) ObjectExample to remove. 
	 * @see ObjectListe#removeObject(Object)
	 */
	public void removeObjectExample(ObjectExample elt) 
		{ super.removeObject(elt); }
	
	/**
	 * To remove an ObjectExample at a specific place in the liste.
	 * @param nbElt (int) Position of the ObjectExample
	 * @see ObjectListe#removeObject(int)
	 */
	public void removeObjectExample(int nbElt) 
		{ super.removeObject(nbElt); }
	
	/**
	 * Adapted place for methods and functions of sorting is here. 
	 * Your ObjectExample and Classes used to make the table[] liste must
	 * have something to sort them...
	 */

}

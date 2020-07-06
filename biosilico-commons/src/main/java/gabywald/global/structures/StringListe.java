package gabywald.global.structures;

/**
 * To provide a set of String's. 
 * @author Gabriel Chandesris (2008)
 * @deprecated to List<String> (2020)
 */
public class StringListe extends ObjectListe {
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
	public StringListe() { super(); }
	
	/**
	 * Constructor with a pre-made table of String's. 
	 * @param liste (String[])
	 * @see ObjectListe#ObjectListe(Object[])
	 */
	public StringListe(String[] liste) { super(liste); }
	
	/**
	 * To get the current list of String's as a table. 
	 * @return (String[]) A table of String's.
	 * @see ObjectListe#getListeObjects()
	 */
	public String[] getListeStrings() {
		String[] tabReturn = new String[this.length()];
		for (int i = 0 ; i < this.length() ; i++) 
			{ tabReturn[i] = this.getString(i); }
		return tabReturn;
		// return (String[])super.getListeObjects(); 
	}
	
	/**
	 * To get a specific String of the list. 
	 * @param i (int) Position of the String in the list. 
	 * @return (String) String at position i. 
	 * @see ObjectListe#getObject(int)
	 */
	public String getString(int i) 
		{ return (String)super.getObject(i); }
	
	/**
	 * To set a new list in the instance of StringListe. 
	 * @param liste (String[]) A table of String's. 
	 * @see ObjectListe#setListe(Object[])
	 */
	public void setListe(String[] liste) 
		{ super.setListe(liste); }
	
	/**
	 * To set an String at a specific place in the list, replace the old one. 
	 * @param elt (String) String to set. 
	 * @param i (int) Greater or equal to 0, and lower to liste.length.
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void setString(String elt,int i) 
		{ super.setObject(elt, i); }
	
	/**
	 * To add an String to the end of the list. 
	 * @param elt (String) String to add. 
	 * @see ObjectListe#addObject(Object)
	 */
	public void addString(String elt) 
		{ super.addObject(elt); }
	
	/**
	 * To add an String at a specific position. 
	 * <br>If Position greater than current length : added at end of the list. 
	 * @param elt (String)
	 * @param pos (int)
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void addString(String elt,int pos) 
		{ super.addObject(elt, pos); }
	
	/**
	 * To know if an String is present is the list. 
	 * @param elt (String)
	 * @return (boolean)
	 * @see ObjectListe#has(Object)
	 */
	public boolean has(String elt) 
		{ return super.has(elt); }
	
	/**
	 * To remove a specific String (nothing append if not present). 
	 * @param elt (String) String to remove. 
	 * @see ObjectListe#removeObject(Object)
	 */
	public void removeString(String elt) 
		{ super.removeObject(elt); }
	
	/**
	 * To remove an String at a specific place in the liste.
	 * @param nbElt (int) Position of the String
	 * @see ObjectListe#removeObject(int)
	 */
	public void removeString(int nbElt) 
		{ super.removeObject(nbElt); }
	
	
	/**
	 * Adapted place for methods and functions of sorting is here. 
	 * Your String and Classes used to make the table[] liste must
	 * have something to sort them...
	 */
	
	/**
	 * Aim of this method is to repeat a String 'time' times. 
	 * @param elt (String)
	 * @param time (int)
	 * @return (String) elt concatenates 'time' times to itself. 
	 */
	public static String repeat(String elt,int time) {
		String result = "";
		for (int i = 0 ; i < time ; i++) { result += elt; }
		return result;
	}
	
	
	
}

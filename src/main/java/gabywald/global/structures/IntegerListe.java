package gabywald.global.structures;

/**
 * To provide a set of Integer's. 
 * @author Gabriel Chandesris (2008)
 */
public class IntegerListe extends ObjectListe {
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
	public IntegerListe() { super(); }
	
	/**
	 * Constructor with a pre-made table of Integer's. 
	 * @param liste (Integer[])
	 * @see ObjectListe#ObjectListe(Object[])
	 */
	public IntegerListe(Integer[] liste) { super(liste); }
	
	/**
	 * To get the current list of Integer's as a table. 
	 * @return (Integer[]) A table of Integer's.
	 * @see ObjectListe#getListeObjects()
	 */
	public Integer[] getListeIntegers() {
		Integer[] tabReturn = new Integer[this.length()];
		for (int i = 0 ; i < this.length() ; i++) 
			{ tabReturn[i] = this.getInteger(i); }
		return tabReturn;
		// return (Integer[])super.getListeObjects(); 
	}
	
	/**
	 * To get a specific Integer of the list. 
	 * @param i (int) Position of the Integer in the list. 
	 * @return (Integer) Integer at position i. 
	 * @see ObjectListe#getObject(int)
	 */
	public Integer getInteger(int i) 
		{ return (Integer)super.getObject(i); }
	
	/**
	 * To set a new list in the instance of IntegerListe. 
	 * @param liste (Integer[]) A table of Integer's. 
	 * @see ObjectListe#setListe(Object[])
	 */
	public void setListe(Integer[] liste) 
		{ super.setListe(liste); }
	
	/**
	 * To set an Integer at a specific place in the list, replace the old one. 
	 * @param elt (Integer) Integer to set. 
	 * @param i (int) Greater or equal to 0, and lower to liste.length.
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void setInteger(Integer elt,int i) 
		{ super.setObject(elt, i); }
	
	/**
	 * To add an Integer to the end of the list. 
	 * @param elt (Integer) Integer to add. 
	 * @see ObjectListe#addObject(Object)
	 */
	public void addInteger(Integer elt) 
		{ super.addObject(elt); }
	
	/**
	 * To add an Integer at a specific position. 
	 * <br>If Position greater than current length : added at end of the list. 
	 * @param elt (Integer)
	 * @param pos (int)
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void addInteger(Integer elt,int pos) 
		{ super.addObject(elt, pos); }
	
	/**
	 * To know if an Integer is present is the list. 
	 * @param elt (Integer)
	 * @return (boolean)
	 * @see ObjectListe#has(Object)
	 */
	public boolean has(Integer elt) 
		{ return super.has(elt); }
	
	/**
	 * To remove a specific Integer (nothing append if not present). 
	 * @param elt (Integer) Integer to remove. 
	 * @see ObjectListe#removeObject(Object)
	 */
	public void removeInteger(Integer elt) 
		{ super.removeObject(elt); }
	
	/**
	 * To remove an Integer at a specific place in the liste.
	 * @param nbElt (int) Position of the Integer
	 * @see ObjectListe#removeObject(int)
	 */
	public void removeInteger(int nbElt) 
		{ super.removeObject(nbElt); }
	
	/**
	 * Adapted place for methods and functions of sorting is here. 
	 * Your Integer and Classes used to make the table[] liste must
	 * have something to sort them...
	 */
	
	/**
	 * To add an Integer to the end of the list. 
	 * @param elt (Integer) Integer to add. 
	 */
	public void addInteger(int elt) 
		{ super.addObject(new Integer(elt)); }
	
	/**
	 * To set an Integer at a specific place in the list, replace the old one. 
	 * @param elt (int) Integer to set. 
	 * @param i (int) Greater or equal to 0, and lower to liste.length.
	 */
	public void setInteger(int elt,int i) 
		{ super.setObject(new Integer(elt), i); }
	
	
	
	/**
	 * Add val to the Integer at position i.
	 * @param i (int) position
	 * @param val (int) value to add
	 */
	public void setIntegerPlus(int i,int val) {
		int tmp = ((Integer)this.liste[i]).intValue() + val;
		this.setInteger(new Integer(tmp), i);
	}
	
	/**
	 * Remove one (1) to the Integer at position i.
	 * @param i (int) position
	 * @param val (int) value to remove
	 */
	public void setIntegerLess(int i,int val) {
		int tmp = ((Integer)this.liste[i]).intValue() - val;
		this.setInteger(new Integer(tmp), i);
	}
	
	/**
	 * Add one (1) to the Integer at position i.
	 * @param i (int) position
	 */
	public void setIntegerPlusPlus(int i) {
		int tmp = ((Integer)this.liste[i]).intValue() + 1;
		this.setInteger(new Integer(tmp), i);
	}
	
	/**
	 * Remove one (1) to the Integer at position i.
	 * @param i (int) position
	 */
	public void setIntegerLessLess(int i) {
		int tmp = ((Integer)this.liste[i]).intValue() - 1;
		this.setInteger(new Integer(tmp), i);
	}
}

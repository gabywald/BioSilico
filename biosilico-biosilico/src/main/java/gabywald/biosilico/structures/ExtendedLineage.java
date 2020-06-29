package gabywald.biosilico.structures;

import java.util.ArrayList;
import java.util.List;

import gabywald.global.structures.ObjectListe;

/**
 * To provide a set of ExtendedLineageItem. 
 * @author Gabriel Chandesris (2010)
 */
public class ExtendedLineage extends ObjectListe {
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
	public ExtendedLineage() { super(); }
	
	/**
	 * Constructor with a pre-made table of ExtendedLineageItem's. 
	 * @param liste (ExtendedLineageItem[])
	 * @see ObjectListe#ObjectListe(Object[])
	 */
	public ExtendedLineage(ExtendedLineageItem[] liste) { super(liste); }
	
	/**
	 * To get the current list of ExtendedLineageItem's as a table. 
	 * @return (ExtendedLineageItem[]) A table of ExtendedLineageItem's.
	 * @see ObjectListe#getListeObjects()
	 */
	public ExtendedLineageItem[] getListeExtendedLineageItems() {
		ExtendedLineageItem[] tabReturn = new ExtendedLineageItem[this.length()];
		for (int i = 0 ; i < this.length() ; i++) 
			{ tabReturn[i] = this.getExtendedLineageItem(i); }
		return tabReturn;
		// return (ExtendedLineageItem[])super.getListeObjects(); 
	} 
	
	/**
	 * To get a specific ExtendedLineageItem of the list. 
	 * @param i (int) Position of the ExtendedLineageItem in the list. 
	 * @return (ExtendedLineageItem) ExtendedLineageItem at position i. 
	 * @see ObjectListe#getObject(int)
	 */
	public ExtendedLineageItem getExtendedLineageItem(int i) 
		{ return (ExtendedLineageItem)super.getObject(i); }
	
	/**
	 * To set a new list in the instance of ExtendedLineageItemListe. 
	 * @param liste (ExtendedLineageItem[]) A table of ExtendedLineageItem's. 
	 * @see ObjectListe#setListe(Object[])
	 */
	public void setListe(ExtendedLineageItem[] liste) 
		{ super.setListe(liste); }
	
	/**
	 * To set an ExtendedLineageItem at a specific place in the list, replace the old one. 
	 * @param elt (ExtendedLineageItem) ExtendedLineageItem to set. 
	 * @param i (int) Greater or equal to 0, and lower to liste.length.
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void setExtendedLineageItem(ExtendedLineageItem elt,int i) 
		{ super.setObject(elt, i); }
	
	/**
	 * To add an ExtendedLineageItem to the end of the list. 
	 * @param elt (ExtendedLineageItem) ExtendedLineageItem to add. 
	 * @see ObjectListe#addObject(Object)
	 */
	public void addExtendedLineageItem(ExtendedLineageItem elt) 
		{ super.addObject(elt); }
	
	/**
	 * To add an ExtendedLineageItem at a specific position. 
	 * <br>If Position greater than current length : added at end of the list. 
	 * @param elt (ExtendedLineageItem)
	 * @param pos (int)
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void addExtendedLineageItem(ExtendedLineageItem elt,int pos) 
		{ super.addObject(elt, pos); }
	
	/**
	 * To know if an ExtendedLineageItem is present is the list. 
	 * @param elt (ExtendedLineageItem)
	 * @return (boolean)
	 * @see ObjectListe#has(Object)
	 */
	public boolean has(ExtendedLineageItem elt) 
		{ return super.has(elt); }
	
	/**
	 * To remove a specific ExtendedLineageItem (nothing append if not present). 
	 * @param elt (ExtendedLineageItem) ExtendedLineageItem to remove. 
	 * @see ObjectListe#removeObject(Object)
	 */
	public void removeExtendedLineageItem(ExtendedLineageItem elt) 
		{ super.removeObject(elt); }
	
	/**
	 * To remove an ExtendedLineageItem at a specific place in the liste.
	 * @param nbElt (int) Position of the ExtendedLineageItem
	 * @see ObjectListe#removeObject(int)
	 */
	public void removeExtendedLineageItem(int nbElt) 
		{ super.removeObject(nbElt); }
	
	/**
	 * Adapted place for methods and functions of sorting is here. 
	 * Your ExtendedLineageItem and Classes used to make the table[] liste must
	 * have something to sort them...
	 */
	
	
	
	
	
	/** !! Local specificity !! */
	public String toString() {
		String result = new String("EXTENDED LINEAGE\n");
		if (this.liste.length == 0) { result += "\tNO DATA\n"; }
		for (int i = 0 ; i < this.liste.length ; i++) 
			{ result += this.liste[i].toString()+"\n"; }
		return result;
	}
	
	/**
	 * Create an instance of ExtendedLineageItem and add it to the end of the list. 
	 * @param uniqueID (String)
	 * @param scientificName (String)
	 * @param rank (String)
	 */
	public void addExtendedLineageItem(String uniqueID,
									   String scientificName,
									   String rank) {
		ExtendedLineageItem elt = 
			new ExtendedLineageItem(uniqueID,scientificName,rank);
		this.addExtendedLineageItem(elt);
	}
	
	/**
	 * To obtain the list of names (simple lineage). 
	 * @return (StringListe) lineage. 
	 */
	public List<String> getSimpleLineage() {
		List<String> lineage = new ArrayList<String>();
		for (int i = 0 ; i < this.liste.length ; i++) 
			{ lineage.add(this.getExtendedLineageItem(i).getScientificName()); }
		return lineage;
	}
	
	public String getSimpleLineage(int i) {
		if (i > this.liste.length) { return null; }
		return this.getExtendedLineageItem(i).getScientificName();
	}
	
	public String getRank(int i) {
		if (i > this.liste.length) { return null; }
		return this.getExtendedLineageItem(i).getRank();
	}
	
	public String getUniqueID(int i) {
		if (i > this.liste.length) { return null; }
		return this.getExtendedLineageItem(i).getUniqueID();
	}
	
	
}

package gabywald.biosilico.structures;

import gabywald.global.structures.ObjectListe;

public class GeneticTreeNodeListe extends ObjectListe {
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
	public GeneticTreeNodeListe() { super(); }
	
	/**
	 * Constructor with a pre-made table of GeneticTreeNode's. 
	 * @param liste (GeneticTreeNode[])
	 * @see ObjectListe#ObjectListe(Object[])
	 */
	public GeneticTreeNodeListe(GeneticTreeNode[] liste) { super(liste); }
	
	/**
	 * To get the current list of GeneticTreeNode's as a table. 
	 * @return (GeneticTreeNode[]) A table of GeneticTreeNode's.
	 * @see ObjectListe#getListeObjects()
	 */
	public GeneticTreeNode[] getListeGeneticTreeNodes() {
		GeneticTreeNode[] tabReturn = new GeneticTreeNode[this.length()];
		for (int i = 0 ; i < this.length() ; i++) 
			{ tabReturn[i] = this.getGeneticTreeNode(i); }
		return tabReturn;
		// return (GeneticTreeNode[])super.getListeObjects(); 
	} 
	
	/**
	 * To get a specific GeneticTreeNode of the list. 
	 * @param i (int) Position of the GeneticTreeNode in the list. 
	 * @return (GeneticTreeNode) GeneticTreeNode at position i. 
	 * @see ObjectListe#getObject(int)
	 */
	public GeneticTreeNode getGeneticTreeNode(int i) 
		{ return (GeneticTreeNode)super.getObject(i); }
	
	/**
	 * To set a new list in the instance of GeneticTreeNodeListe. 
	 * @param liste (GeneticTreeNode[]) A table of GeneticTreeNode's. 
	 * @see ObjectListe#setListe(Object[])
	 */
	public void setListe(GeneticTreeNode[] liste) 
		{ super.setListe(liste); }
	
	/**
	 * To set an GeneticTreeNode at a specific place in the list, replace the old one. 
	 * @param elt (GeneticTreeNode) GeneticTreeNode to set. 
	 * @param i (int) Greater or equal to 0, and lower to liste.length.
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void setGeneticTreeNode(GeneticTreeNode elt,int i) 
		{ super.setObject(elt, i); }
	
	/**
	 * To add an GeneticTreeNode to the end of the list. 
	 * @param elt (GeneticTreeNode) GeneticTreeNode to add. 
	 * @see ObjectListe#addObject(Object)
	 */
	public void addGeneticTreeNode(GeneticTreeNode elt) 
		{ super.addObject(elt); }
	
	/**
	 * To add an GeneticTreeNode at a specific position. 
	 * <br>If Position greater than current length : added at end of the list. 
	 * @param elt (GeneticTreeNode)
	 * @param pos (int)
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void addGeneticTreeNode(GeneticTreeNode elt,int pos) 
		{ super.addObject(elt, pos); }
	
	/**
	 * To know if an GeneticTreeNode is present is the list. 
	 * @param elt (GeneticTreeNode)
	 * @return (boolean)
	 * @see ObjectListe#has(Object)
	 */
	public boolean has(GeneticTreeNode elt) 
		{ return super.has(elt); }
	
	/**
	 * To remove a specific GeneticTreeNode (nothing append if not present). 
	 * @param elt (GeneticTreeNode) GeneticTreeNode to remove. 
	 * @see ObjectListe#removeObject(Object)
	 */
	public void removeGeneticTreeNode(GeneticTreeNode elt) 
		{ super.removeObject(elt); }
	
	/**
	 * To remove an GeneticTreeNode at a specific place in the liste.
	 * @param nbElt (int) Position of the GeneticTreeNode
	 * @see ObjectListe#removeObject(int)
	 */
	public void removeGeneticTreeNode(int nbElt) 
		{ super.removeObject(nbElt); }
	
	/**
	 * Adapted place for methods and functions of sorting is here. 
	 * Your GeneticTreeNode and Classes used to make the table[] liste must
	 * have something to sort them...
	 */
	
	/**
	 * To add an GeneticTreeNodeliste to the end of the list. 
	 * @param elt (GeneticTreeNode) GeneticTreeNode to add. 
	 */
	public void addGeneticTreeNode(GeneticTreeNodeListe elts) { 
		for (int i = 0 ; i < elts.length() ; i++) 
			{ super.addObject(elts.getGeneticTreeNode(i)); }
	}

}

package gabywald.biosilico.structures;

import gabywald.biosilico.model.Agent;
import gabywald.global.structures.ObjectListe;

/**
 * To provide a set of Agent. 
 * @author Gabriel Chandesris (2008-2010)
 */
public class AgentListe extends ObjectListe {
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
	public AgentListe() { super(); }
	
	/**
	 * Constructor with a pre-made table of Agent's. 
	 * @param liste (Agent[])
	 * @see ObjectListe#ObjectListe(Object[])
	 */
	public AgentListe(Agent[] liste) { super(liste); }
	
	/**
	 * To get the current list of Agent's as a table. 
	 * @return (Agent[]) A table of Agent's.
	 * @see ObjectListe#getListeObjects()
	 */
	public Agent[] getListeObjectExamples() {
		Agent[] tabReturn = new Agent[this.length()];
		for (int i = 0 ; i < this.length() ; i++) 
			{ tabReturn[i] = this.getAgent(i); }
		return tabReturn;
		// return (ObjectExample[])super.getListeObjects(); 
	} 
	
	/**
	 * To get a specific Agent of the list. 
	 * @param i (int) Position of the Agent in the list. 
	 * @return (Agent) Agent at position i. 
	 * @see ObjectListe#getObject(int)
	 */
	public Agent getAgent(int i) 
		{ return (Agent)super.getObject(i); }
	
	/**
	 * To set a new list in the instance of AgentListe. 
	 * @param liste (Agent[]) A table of Agent's. 
	 * @see ObjectListe#setListe(Object[])
	 */
	public void setListe(Agent[] liste) 
		{ super.setListe(liste); }
	
	/**
	 * To set an Agent at a specific place in the list, replace the old one. 
	 * @param elt (Agent) Agent to set. 
	 * @param i (int) Greater or equal to 0, and lower to liste.length.
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void setAgent(Agent elt,int i) 
		{ super.setObject(elt, i); }
	
	/**
	 * To add an Agent to the end of the list. 
	 * @param elt (Agent) Agent to add. 
	 * @see ObjectListe#addObject(Object)
	 */
	public void addAgent(Agent elt) 
		{ super.addObject(elt); }
	
	/**
	 * To add an Agent at a specific position. 
	 * <br>If Position greater than current length : added at end of the list. 
	 * @param elt (Agent)
	 * @param pos (int)
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void addAgent(Agent elt,int pos) 
		{ super.addObject(elt, pos); }
	
	/**
	 * To know if an Agent is present is the list. 
	 * @param elt (Agent)
	 * @return (boolean)
	 * @see ObjectListe#has(Object)
	 */
	public boolean has(Agent elt) 
		{ return super.has(elt); }
	
	/**
	 * To remove a specific Agent (nothing append if not present). 
	 * @param elt (Agent) Agent to remove. 
	 * @see ObjectListe#removeObject(Object)
	 */
	public void removeAgent(Agent elt) 
		{ super.removeObject(elt); }
	
	/**
	 * To remove an Agent at a specific place in the liste.
	 * @param nbElt (int) Position of the Agent
	 * @see ObjectListe#removeObject(int)
	 */
	public void removeAgent(int nbElt) 
		{ super.removeObject(nbElt); }
	
	/**
	 * Adapted place for methods and functions of sorting is here. 
	 * Your Agent and Classes used to make the table[] liste must
	 * have something to sort them...
	 */
	
}

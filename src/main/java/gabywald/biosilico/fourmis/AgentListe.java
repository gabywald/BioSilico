package gabywald.biosilico.fourmis;

import gabywald.global.structures.ObjectListe;

/**
 * To provide a set of Agent. 
 * @author Gabriel Chandesris (2008-2010)
 */
public class AgentListe extends ObjectListe {
	/** The list of Agent's. */
	private Agent[] liste;
	
	/**
	 * To get the length of the current list of Agent's
	 * @return (int)
	 */
	public int length() { return this.liste.length; }
	
	/** Default constructor with a list of 0 Agent's.  */
	public AgentListe() { this.liste = new Agent[0]; }
	
	/**
	 * Constructor with a pre-made table of 'Agent. 
	 * @param liste (Agent[])
	 */
	public AgentListe(Agent[] liste) { this.liste = liste; }
	
	/**
	 * To get the current list of Agent's as a table. 
	 * @return (Agent[]) A table of Agent's.
	 */
	public Agent[] getListeAgents() { return this.liste; } 
	
	/**
	 * To get a specific Agent of the list. 
	 * @param i (int) Position of the Agent in the list. 
	 * @return (Agent) Agent at position i. 
	 */
	public Agent getAgent(int i) { 
		/** These controls with if to be sure... */ 
		if (i >= this.liste.length) { return null; }
		if (i < 0) { return null; }
		return this.liste[i]; 
	}
	
	/**
	 * To set a new list in the instance of AgentListe. 
	 * @param liste (Agent[]) A table of Agent's. 
	 */
	public void setListe (Agent[] liste) { this.liste = liste; }
	
	/**
	 * To set an Agent at a specific place in the list, replace the old one. 
	 * @param elt (Agent) Agent to set. 
	 * @param i (int) Greater or equal to 0, and lower to liste.length.
	 */
	public void setAgent(Agent elt,int i) {
		if ( (i < this.liste.length) && (i >= 0) ) 
			{ this.liste[i] = elt; }
	}
	
	/**
	 * To add an Agent to the end of the list. 
	 * @param elt (Agent) Agent to add. 
	 */
	public void addAgent(Agent elt) {
		Agent[] nextListe = new Agent[this.liste.length+1];
		for (int i = 0 ; i < this.liste.length ; i++) 
			{ nextListe[i] = this.liste[i]; }
		nextListe[this.liste.length] = elt;
		this.liste = nextListe;
	}
	
	/**
	 * To add an Agent at a specific position. 
	 * <br>If Position greater than current length : added at end of the list. 
	 * @param elt (Agent)
	 * @param pos (int)
	 * @see AgentListe#addAgent(Agent)
	 */
	public void addAgent(Agent elt,int pos) {
		if (pos >= this.liste.length) { this.addAgent(elt); }
		Agent[] nextListe = new Agent[this.liste.length+1];
		for (int i = 0 ; i < pos ; i++) 
			{ nextListe[i] = this.liste[i]; }
		nextListe[pos] = elt;
		for (int i = pos ; i < this.liste.length ; i++) 
			{ nextListe[i+1] = this.liste[i]; }	
		this.liste = nextListe;
	}
	
	/**
	 * To know if an Agent is present is the list. 
	 * @param elt (Agent)
	 * @return (boolean)
	 */
	public boolean has(Agent elt) {
		for (int i = 0 ; i < this.liste.length ; i++) {
			if (this.liste[i].equals(elt)) { return true; }
		}
		return false;
	}
	
	/**
	 * To remove a specific Agent (nothing append if not present). 
	 * @param elt (Agent) Agent to remove. 
	 */
	public void removeAgent(Agent elt) {
		if (this.has(elt)) {
			Agent[] nextListe = new Agent[this.liste.length-1];
			int i = 0;
			if (nextListe.length > 0) {
				while ( (i < this.liste.length) && (!this.liste[i].equals(elt)) ) 
					{ nextListe[i] = this.liste[i];i++; }
				if (this.liste[i].equals(elt)) {
					i++;
					while (i < this.liste.length)
						{ nextListe[i-1] = this.liste[i];i++; }
				}
			}
			this.liste = nextListe;
		}
	}
	
	/**
	 * To remove an Agent at a specific place in the liste.
	 * @param nbElt (int) Position of the Agent
	 */
	public void removeAgent(int nbElt) {
		if ( (nbElt >= 0) && (nbElt < this.liste.length) ) {
			Agent[] nextListe = new Agent[this.liste.length-1];
			int i = 0;
			while ( (i < this.liste.length) && (i != nbElt) ) 
				{ nextListe[i] = this.liste[i];i++; }
			if (i == nbElt) {
				i++;
				while (i < this.liste.length)
					{ nextListe[i-1] = this.liste[i];i++; }
			}
			this.liste = nextListe;
		}
	}
	
	/**
	 * To know if two AgentListe are equals (local instance and an other). 
	 * @param toCompare (AgentListe) Other AgentListe. 
	 * @return (boolean)
	 */
	public boolean equals(AgentListe toCompare) {
		if (this.liste.length != toCompare.length()) { return false; }
		/** Other criteria could be used as above, like average... */
		for (int i = 0 ; i < this.liste.length ; i++) {
			if (!this.liste[i].equals(toCompare.getAgent(i)))
				{ return false; }
		}
		return true;
	}
}

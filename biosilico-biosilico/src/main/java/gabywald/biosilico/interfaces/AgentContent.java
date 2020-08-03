package gabywald.biosilico.interfaces;

import java.util.List;

import gabywald.biosilico.model.Agent;

/**
 * This interface defines use of a list of Agent's in one instance of Agent. 
 * @author Gabriel Chandesris (2009-2010, 2020)
 */
public interface AgentContent {
	/** Length of the list. */
	public int getAgentListLength();
	/** To get the list of Agent's. */
	public List<Agent> getAgentListe();
	/** To remove an Agent in current instance of. 
	 * @return */
	public Agent remAgent(int i);
	/** To get an Agent in current instance of. */
	public Agent getAgent(int i);
	
	/**
	 * Determine if current WorldCase owns a certain type of object. 
	 * @param type (int) 901 to 939
	 * @return (int) Number of object's of that type. 
	 */
	public int hasAgentType(int type);
	
	/**
	 * In order to get an agent of a certain type. 
	 * @param type (int) 
	 * @return (Agent) Can be null.
	 * @see AgentContent#hasAgentType(int)
	 */
	public Agent getAgentType(int type);
	
	/**
	 * In order to put an object in (nothing if object is null).  
	 * @param object (Agent)
	 */
	public void addAgent(Agent object);
	
}

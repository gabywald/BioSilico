package gabywald.biosilico.interfaces;

import java.util.List;

import gabywald.biosilico.model.Agent;
import gabywald.biosilico.model.enums.AgentType;
import gabywald.biosilico.model.enums.StatusType;

/**
 * This interface defines use of a list of Agent's in one instance of Agent. 
 * @author Gabriel Chandesris (2009-2010, 2020)
 */
public interface IAgentContent {
	/** Length of the list. */
	public int getAgentListLength();
	/** To get the list of Agent's. */
	public List<Agent> getAgentListe();
	/** To remove an Agent in current instance of. 
	 * @return Removed Agent instance 
	 */
	public Agent remAgent(int i);
	/** To remove an Agent in current instance of. 
	 * @return If removal correctly done
	 */
	public boolean remAgent(Agent o);
	/** To get an Agent in current instance of. 
	 * @return Agent instance 
	 */
	public Agent getAgent(int i);
	
	/**
	 * Determine if current instance owns a certain type of object. 
	 * @param type AgentType
	 * @return (int) Number of object's of that type. 
	 */
	public int hasAgentType(AgentType type);
	
	/**
	 * In order to get an agent of a certain type. 
	 * @param type AgentType
	 * @return (Agent) Can be null.
	 * @see IAgentContent#hasAgentType(AgentType)
	 */
	public Agent getAgentType(AgentType type);
	
	/**
	 * Determine if current instance owns a certain type of object. 
	 * @param type StatusType
	 * @return (int) Number of object's of that type. 
	 */
	public int hasAgentStatus(StatusType status);
	
	/**
	 * In order to get an agent of a certain type. 
	 * @param type StatusType
	 * @return (Agent) Can be null.
	 * @see IAgentContent#hasAgentStatus(StatusType)
	 */
	public Agent getAgentStatus(StatusType type);
	
	/**
	 * In order to put an object in (nothing if object is null).  
	 * @param object (Agent)
	 */
	public void addAgent(Agent object);
	
}

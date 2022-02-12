package gabywald.biosilico.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import gabywald.biosilico.model.Agent;

import gabywald.biosilico.model.enums.AgentType;
import gabywald.biosilico.model.enums.ObjectType;
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
	 * @param type ObjectType
	 * @return (int) Number of object's of that type. 
	 */
	public int hasObjectType(ObjectType type);
	
	/**
	 * In order to get an agent of a certain type. 
	 * @param type ObjectType
	 * @return (Agent) Can be null.
	 * @see IAgentContent#hasAgentType(AgentType)
	 */
	public Agent getObjectType(ObjectType type);
	
	/**
	 * Determine if current instance owns a certain type of organism. 
	 * @param type AgentType
	 * @return (int) Number of object's of that type. 
	 */
	public int hasAgentType(AgentType type);
	
	/**
	 * In order to get an organism of a certain type. 
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
	 * Determine if current instance owns a certain type of object. 
	 * @param name String
	 * @return (int) Number of object's of that type. 
	 */
	public int hasAgentWithName(String name);
	
	/**
	 * In order to get an agent of a certain type. 
	 * @param type StatusType
	 * @return (Agent) Can be null.
	 * @see IAgentContent#hasAgentName(String)
	 */
	public Agent getAgentWithName(String name);
	
	/**
	 * In order to put an object in (nothing if object is null).  
	 * @param object (Agent)
	 */
	public void addAgent(Agent object);
	
	/**
	 * To know number of Agent's types present. 
	 * @param <T>
	 * @param typeStatus
	 * @param index
	 * @param agents
	 * @return (int)
	 */
	public static <T extends IChemicalsType> int hasType(T typeStatus, int index, List<Agent> agents) {
		return (int)agents.stream().filter( a -> (a.getChemicals().getVariable(index) == typeStatus.getIndex()) ).count();
	}
	
	/**
	 * Get the first Agent's instance according to a given type / status. 
	 * @param <T>
	 * @param typeStatus
	 * @param index
	 * @param agents
	 * @return (Agent)
	 */
	public static <T extends IChemicalsType> Agent getType(T typeStatus, int index, List<Agent> agents) {
		if (agents.stream().anyMatch( a -> a.getChemicals().getVariable(index) == typeStatus.getIndex() )) {
			Optional<Agent> optAgent = agents.stream().filter( a -> a.getChemicals().getVariable(index) == typeStatus.getIndex() ).findFirst();
			if (optAgent.isPresent()) { return optAgent.get(); }
		}
		return null;
	}
	
	/**
	 * To get a List of Agent's instances according to a certain type / status given. 
	 * @param <T>
	 * @param typeStatus
	 * @param index
	 * @param agents
	 * @return (List&lt;Agent&gt;)
	 */
	public static <T extends IChemicalsType> List<Agent> getListOfType(T typeStatus, int index, List<Agent> agents) {
		return agents.stream().filter( a -> a.getChemicals().getVariable(index) == typeStatus.getIndex() ).collect(Collectors.toList());
	}
	
	/**
	 * To know number of Agent present with a given name. 
	 * @param name
	 * @param agents
	 * @return (int)
	 */
	public static int hasName(String name, List<Agent> agents) {
		return (int)agents.stream().filter( a -> (a.getName().equals(name)) ).count();
	}
	
	/**
	 * To get a List of Agent's instances with a given name. 
	 * @param name
	 * @param agents
	 * @return (Agent)
	 */
	public static Agent getName(String name, List<Agent> agents) {
		if (agents.stream().anyMatch( a -> (a.getName().equals(name)) ) ) {
			Optional<Agent> optAgent = agents.stream().filter( a -> (a.getName().equals(name)) ).findFirst();
			if (optAgent.isPresent()) { return optAgent.get(); }
		}
		return null;
	}
	
}

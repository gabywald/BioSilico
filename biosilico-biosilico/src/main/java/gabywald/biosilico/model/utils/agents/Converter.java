package gabywald.biosilico.model.utils.agents;

import java.util.List;

import gabywald.biosilico.interfaces.IAgentContent;
import gabywald.biosilico.interfaces.IEnvironmentItem;
import gabywald.biosilico.model.Agent;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.enums.AgentType;
import gabywald.biosilico.model.enums.ObjectType;
import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.enums.StatusType;

/**
 * Generic Parametable converter. 
 * @author Gabriel Chandesris (2020)
 */
public class Converter extends UtilAgent {

	private AgentType at	= null;
	private StatusType st	= null;
	private ObjectType ot	= null;
	
	/**
	 * 
	 * @param at (AgentType) Used to find Agent. 
	 * @param st (StatusType) Used to find Agent. 
	 * @param ot (ObjectType) To be transfered at !
	 */
	public Converter(AgentType at, StatusType st, ObjectType ot) {
		super();
		this.setAlive( false );
		this.setEatable( false );
		this.setMovable( false );
		this.at	= at;
		this.st	= st;
		this.ot	= ot;
		this.setName("Converter of " + at.getName() + " " + st.getName() + " to " + ot.getName());
	}
	
	@Override
	public void execution(IEnvironmentItem local) {
		if ( (local.hasAgentType(this.at) > 0) && (local.hasAgentStatus(this.st) > 0) ) {
			List<Agent> viriditas = IAgentContent.getListOfType(this.at, StateType.AGENT_TYPE.getIndex(), local.getAgentListe());
			viriditas.stream().forEach( v -> {
				if (((Organism)v).getOrganismStatus() == this.st) {
					v.setObjectType(this.ot);
					v.setAlive( false );
					v.setMovable( true );
					v.setEatable( true );
				}
			});
		}
		// TODO make better of these DAEMON ! (movement ?! phases ?!)
	}
	
}

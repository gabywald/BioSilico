package gabywald.biosilico.fourmis;

import java.util.List;

/**
 * 
 * @author Gabriel Chandesris (2009, 2020)
 */
public interface AgentContent {
	public int getAgentListLength();
	public List<Agent> getAgentListe();
	public void addAgent(Agent elt);
	public void remAgent(int i);
	public Agent getAgent(int i);
	public boolean isAgentAlive(int i);
	public boolean isAgentMovable(int i);
	public boolean isAgentEatable(int i);
}

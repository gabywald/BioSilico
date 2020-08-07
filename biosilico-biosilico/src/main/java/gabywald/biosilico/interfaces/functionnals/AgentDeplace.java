package gabywald.biosilico.interfaces.functionnals;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
@FunctionalInterface
public interface AgentDeplace {
	/** Define the movement of the Agent (if it is moving). */
	public abstract boolean deplace();
}

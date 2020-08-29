package gabywald.biosilico.interfaces.functionnals;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
@FunctionalInterface
public interface IAgentSleep {
	/** Define the sleep state of agent (if apply). */
	public abstract boolean sleep();
}

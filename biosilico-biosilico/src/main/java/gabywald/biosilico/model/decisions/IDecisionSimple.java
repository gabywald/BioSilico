package gabywald.biosilico.model.decisions;

import java.util.function.Function;

import gabywald.biosilico.model.Agent;

/**
 * Aim here is to simplify for class BaseDecisionOnlyOneAttribute 
 * <br/>Pass the agent instance function as a parameter. 
 * @author Gabriel Chandesris (2020)
 * @param <T>
 */
@FunctionalInterface
public interface IDecisionSimple<T extends Agent> {

	public void action(T ofType, Function<T, Void> function);
	
}

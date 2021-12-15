package gabywald.pipeline.workflowbase.wip;

import java.util.function.Function;

/**
 * 
 * <br/>Note : see also IDecisionSimple in biosilico-biosilico module. 
 * <br/>NOTE : WIP / Work In Progress !!N
 * @author Gabriel Chandesris (2021)
 * @param <T>
 */
@FunctionalInterface
public interface IPipelineStepSimple<T> {
	public void action(T input,
						Function<T, Boolean> functionProcess,
						Function<Void, T> functionInput, 
						Function<Void, T> functionOutput, 
						Function<Void, T> functionError);
}

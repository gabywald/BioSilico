package gabywald.pipeline.workflowbase.wip;

import java.util.function.Function;

import gabywald.pipeline.workflowbase.IPipelineContainer;

/**
 * 
 * <br/>Note : see also IDecisionSimple in biosilico-biosilico module. 
 * <br/>NOTE : WIP / Work In Progress !!N
 * @author Gabriel Chandesris (2021)
 * @param <C> Data Type Definition of Container
 * @param <T> Data carried by IPipelineContainer
 */
@FunctionalInterface
public interface IPipelineStepSimple<C extends IPipelineContainer<T>, T> {
	public void action(C input,
						Function<T, Boolean> functionProcess,
						Function<Void, C> functionInput, 
						Function<Void, C> functionOutput, 
						Function<Void, C> functionError);
}

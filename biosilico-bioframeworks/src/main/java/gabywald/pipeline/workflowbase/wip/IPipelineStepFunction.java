package gabywald.pipeline.workflowbase.wip;

import java.util.function.Function;

import gabywald.pipeline.workflowbase.IPipelineContainer;
import gabywald.pipeline.workflowbase.IPipelineStep;

/**
 * 
 * <br/>Note : see also IDecisionSimple in biosilico-biosilico module. 
 * <br/>NOTE : WIP / Work In Progress !!N
 * @author Gabriel Chandesris (2021-2022)
 * @param <C> Data Type Definition of Container
 * @param <T> Data carried by IPipelineContainer
 */
// @FunctionalInterface
public interface IPipelineStepFunction<C extends IPipelineContainer<T>, T>
		extends IPipelineStep<C, T> {
	public void action(Function<C, Boolean> functionProcess);
}

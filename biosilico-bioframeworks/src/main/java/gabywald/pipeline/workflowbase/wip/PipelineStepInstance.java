package gabywald.pipeline.workflowbase.wip;

import java.util.function.Function;

import gabywald.pipeline.workflowbase.IPipelineContainer;
import gabywald.pipeline.workflowbase.IPipelineStep;

/**
 * 
 * <br/>NOTE : WIP / Work In Progress !!N
 * @author Gabriel Chandesris (2021)
 * @param <C> Data Type Definition of Container
 * @param <T> Data carried by IPipelineContainer
 */
public class PipelineStepInstance<C extends IPipelineContainer<T>, T> 
		implements IPipelineStepSimple<C, T>, IPipelineStep<C, T> {

	private Function<T, Boolean>  pFunc = null;
	private Function<Void, C> iFunc = null;
	private Function<Void, C> oFunc = null;
	private Function<Void, C> eFunc = null;
	
	private C input = null;
	private C output = null;
	private C error = null;
	
	@Override
	public void action(C input, 
			Function<T, Boolean> functionProcess, 
			Function<Void, C> functionInput,
			Function<Void, C> functionOutput, 
			Function<Void, C> functionError) {
		this.pFunc = functionProcess;
		this.iFunc = functionInput;
		this.oFunc = functionOutput;
		this.eFunc = functionError;
	}

	@Override
	public boolean process(C inputData) {
		return this.pFunc.apply( inputData.getElement() ).booleanValue();
	}

	@Override
	public C getInput() 
		{ return this.input; }

	@Override
	public C getOutput() 
		{ return this.output; }

	@Override
	public C getError() 
		{ return this.error; }

}

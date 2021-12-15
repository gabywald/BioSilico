package gabywald.pipeline.workflowbase.wip;

import java.util.function.Function;

import gabywald.pipeline.workflowbase.IPipelineStep;

/**
 * 
 * <br/>NOTE : WIP / Work In Progress !!N
 * @author Gabriel Chandesris (2021)
 * @param <T>
 */
public class PipelineStepInstance<T> implements IPipelineStepSimple<T>, IPipelineStep<T> {

	private Function<T, Boolean>  pFunc = null;
	private Function<Void, T> iFunc = null;
	private Function<Void, T> oFunc = null;
	private Function<Void, T> eFunc = null;
	
	private T input = null;
	private T output = null;
	private T error = null;
	
	@Override
	public void action(T input, 
			Function<T, Boolean> functionProcess, 
			Function<Void, T> functionInput,
			Function<Void, T> functionOutput, 
			Function<Void, T> functionError) {
		this.pFunc = functionProcess;
		this.iFunc = functionInput;
		this.oFunc = functionOutput;
		this.eFunc = functionError;
	}

	@Override
	public boolean process(T inputData) {
		return this.pFunc.apply(inputData).booleanValue();
	}

	@Override
	public T getInput() 
		{ return this.input; }

	@Override
	public T getOutput() 
		{ return this.output; }

	@Override
	public T getError() 
		{ return this.error; }

}

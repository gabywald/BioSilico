package gabywald.pipeline.workflowbase.wip;

import java.util.function.Function;

import gabywald.pipeline.workflowbase.IPipelineContainer;

/**
 * 
 * <br/>NOTE : WIP / Work In Progress !!
 * @author Gabriel Chandesris (2021-2022)
 * @param <C> Data Type Definition of Container
 * @param <T> Data carried by IPipelineContainer
 */
public class PipelineStepFunctionImpl<C extends IPipelineContainer<T>, T> 
		implements IPipelineStepFunction<C, T> {

	private Function<C, Boolean> pFunc = null;
	
	private C input = null;
	private C output = null;
	private String error = null;
	
	@Override
	public void action(Function<C, Boolean> functionProcess) {
		// this.input = input; // NOTE here clone ??
		this.pFunc = functionProcess;
	}
	
//	public Function<T, Boolean> buildFunction() {
//		return new Function<T, Boolean>() {
//			@Override
//			public Boolean apply(T t) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//		};
//	}
	
	@Override
	public boolean process(C inputData) {
		this.input = inputData; // NOTE clone content ?
		return this.process();
	}

	public boolean process() {
		boolean toReturn	= this.pFunc.apply( this.input ).booleanValue();
		this.output			= this.input; // NOTE clone content ?
		this.error			= this.input.getErrors(); 
		return toReturn;
	}

	@Override
	public C getInput() 
		{ return this.input; }

	@Override
	public C getOutput() 
		{ return this.output; }

	@Override
	public String getError() 
		{ return this.error; }

}

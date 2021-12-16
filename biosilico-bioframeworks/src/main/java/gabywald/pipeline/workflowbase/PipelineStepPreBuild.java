package gabywald.pipeline.workflowbase;

/**
 * 
 * @author Gabriel Chandesris (2021)
 * @param <T>
 */
public abstract class PipelineStepPreBuild<T> 
		implements IPipelineStep<IPipelineContainer<T>, T>{
	protected IPipelineContainer<T> input = null;
	protected IPipelineContainer<T> output = null;
	protected IPipelineContainer<T> error = null;

	@Override
	public IPipelineContainer<T> getInput() 
		{ return this.input; }

	@Override
	public IPipelineContainer<T> getOutput() 
		{ return this.output; }

	@Override
	public IPipelineContainer<T> getError() 
		{ return this.error; }
}

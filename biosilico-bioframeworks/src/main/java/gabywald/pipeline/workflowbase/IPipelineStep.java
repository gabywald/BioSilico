package gabywald.pipeline.workflowbase;

/**
 * This Interface defines elements of the Pipeline (I.E. "Handler"). 
 * <br />If processing step fail (aka return false), there is no output (way to check afterward). 
 * <br />Error handling ?! (if any error / exception in processing). 
 * @author Gabriel Chandesris (2021)
 * @param <C> Data Type Definition of Container
 * @param <T> Data carried by IPipelineContainer
 */
public interface IPipelineStep<C extends IPipelineContainer<T>, T > {
//	private C input;
//	private C output;

	public boolean process(C inputData);
	
	public C getInput();
	public C getOutput();
	public C getError();
}

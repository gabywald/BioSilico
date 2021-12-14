package gabywald.pipeline.workflowbase;

/**
 * This Interface defines elements of the Pipeline (I.E. "Handler"). 
 * <br />If processing step fail (aka return false), there is no output (way to check afterward). 
 * <br />Error handling ?! (if any error / exception in processing). 
 * @author Gabriel Chandesris (2021)
 * @param <T> Data Type Definition
 */
public interface IPipelineStep<T> {
//	private T input;
//	private T output;

	public boolean process(T inputData);
	
	public T getInput();
	public T getOutput();
	public T getError();
}

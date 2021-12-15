package gabywald.pipeline.workflowbase;

/**
 * This Interface defines Container of elements (items?) treated (or to be treated) by the pipeline.  
 * @author Gabriel Chandesris (2021)
 * @param <T> Data carried by IPipelineContainer
 */
public interface IPipelineContainer<T> {
	
	public T getElement();
	
	public T setElement(T elt);
	
	public void addError(IPipelineStep<IPipelineContainer<T>, T> step, String message);
}

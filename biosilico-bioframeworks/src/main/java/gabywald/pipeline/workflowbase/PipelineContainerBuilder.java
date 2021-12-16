package gabywald.pipeline.workflowbase;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Gabriel Chandesris (2021)
 */
public abstract class PipelineContainerBuilder {

	/**
	 * 
	 * @param <T>
	 * @param className
	 * @return
	 */
	public static <T> IPipelineContainer<T> build(Class<T> classe) {
		return new IPipelineContainer<T>() {
			private T eltContent = null;
			private Map<IPipelineStep<IPipelineContainer<T>, T>, String> errors = new HashMap<>(); 
			
			@Override
			public T setElement(T elt) {
				T toReturn = this.eltContent;
				this.eltContent = elt;
				return toReturn;
			}
			
			@Override
			public T getElement() {
				return this.eltContent;
			}
			
			@Override
			public void addError(IPipelineStep<IPipelineContainer<T>, T> step, String message) {
				this.errors.put(step, message);
			}
		};
	}
}

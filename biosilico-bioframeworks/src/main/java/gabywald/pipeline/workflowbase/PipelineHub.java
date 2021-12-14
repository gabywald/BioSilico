package gabywald.pipeline.workflowbase;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * This class correspond to Main Pipeline Representation (I.E. "Pipeline Manager"). 
 * @author Gabriel Chandesris (2021)
 * @param <T> Data Type Definition
 */
public class PipelineHub<T> {
	private List<IPipelineStep<T> > steps = new ArrayList<IPipelineStep<T> >();
	
	public int size() {
		return this.steps.size();
	}
	
	public boolean addStep(IPipelineStep<T> step) {
		return this.steps.add(step);
	}
	
	public IPipelineStep<T> getStep(int i) {
		return this.steps.get( i );
	}
	
	public IPipelineStep<T> remove(int i) {
		return this.steps.remove( i );
	}
	
	public boolean remove(IPipelineStep<T> step) {
		return this.steps.remove( step );
	}
	
	public T processAll(T data) {
		T input = data;
		for (IPipelineStep<T> step : this.steps) {
			boolean isOK = step.process( input );
			if ( ! isOK) { break; }
			input = step.getOutput();
		}
		return input;
	}
}

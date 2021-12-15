package gabywald.pipeline.workflowbase;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * This class correspond to Main Pipeline Representation (I.E. "Pipeline Manager"). 
 * @author Gabriel Chandesris (2021)
 * @param <C> Data Type Definition of Container
 * @param <T> Data carried by IPipelineContainer
 */
public class PipelineHub<C extends IPipelineContainer<T>, T> {
	private List<IPipelineStep<C, T> > steps = new ArrayList<IPipelineStep<C, T> >();
	
	public int size() {
		return this.steps.size();
	}
	
	public boolean addStep(IPipelineStep<C, T> step) {
		return this.steps.add(step);
	}
	
	public IPipelineStep<C, T> getStep(int i) {
		return this.steps.get( i );
	}
	
	public IPipelineStep<C, T> remove(int i) {
		return this.steps.remove( i );
	}
	
	public boolean remove(IPipelineStep<C, T> step) {
		return this.steps.remove( step );
	}
	
	public C processAll(C data) {
		C input = data;
		for (IPipelineStep<C, T> step : this.steps) {
			boolean isOK = step.process( input );
			if ( ! isOK) { break; }
			input = step.getOutput();
		}
		return input;
	}
}

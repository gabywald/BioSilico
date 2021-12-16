package gabywald.pipeline.workflowbase;

/**
 * 
 * @author Gabriel Chandesris (2021)
 */
public class PipelineStepBuilder {
	
	/**
	 * 
	 * @param <T>
	 * @param classe
	 * @return IPipelineStep instance. 
	 * @deprecated See PipelineStepPreBuild
	 */
	public static <T> IPipelineStep<IPipelineContainer<T>, T> build(Class<T> classe) {
		return new IPipelineStep<IPipelineContainer<T>, T>() {
			private IPipelineContainer<T> input = null;
			private IPipelineContainer<T> output = null;
			private IPipelineContainer<T> error = null;

			@Override
			public boolean process(IPipelineContainer<T> inputData) {
				this.input = inputData; // XXX NOTE clone ??
				this.output = inputData;
				if (classe.getName().equals("String")) {
					this.output.setElement( null );
				}
				return true;
			}
			
			@Override
			public IPipelineContainer<T> getInput() 
				{ return this.input; }

			@Override
			public IPipelineContainer<T> getOutput() 
				{ return this.output; }

			@Override
			public IPipelineContainer<T> getError() 
				{ return this.error; }

		};
	}
}

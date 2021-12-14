package gabywald.pipeline.workflowbase;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PipelineIdeasTests {

	@Test
	void test01() {
		
		PipelineHub<String> strph = new PipelineHub<String>();
		Assertions.assertNotNull( strph );
		Assertions.assertEquals(0, strph.size());
		
		strph.addStep(new IPipelineStep<String>() {
			private String input = null;
			private String output = null;
			private String error = null;

			@Override
			public boolean process(String inputData) {
				this.input = inputData;
				this.output = inputData.concat( "step1" );
				return true;
			}

			@Override
			public String getInput() 
				{ return this.input; }

			@Override
			public String getOutput() 
				{ return this.output; }

			@Override
			public String getError() 
				{ return this.error; }
			
		});
		
		Assertions.assertEquals(1, strph.size());
	}
	
	@Test
	void test02() {
		
		PipelineHub<String> strph = new PipelineHub<String>();
		Assertions.assertNotNull( strph );
		Assertions.assertEquals(0, strph.size());
		
		strph.addStep(new IPipelineStep<String>() {
			private String input = null;
			private String output = null;
			private String error = null;

			@Override
			public boolean process(String inputData) {
				this.input = inputData;
				this.output = inputData.concat( "step1" );
				return true;
			}

			@Override
			public String getInput() 
				{ return this.input; }

			@Override
			public String getOutput() 
				{ return this.output; }

			@Override
			public String getError() 
				{ return this.error; }
			
		});
		
		Assertions.assertEquals(1, strph.size());
		
		String result = strph.processAll( "" );
		
		Assertions.assertEquals("step1", result);
	}

}

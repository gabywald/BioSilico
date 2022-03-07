package gabywald.pipeline.workflowbase;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
/**
 * 
 * @author Gabriel Chandesris (2021)
 */
class PipelineIdeasTests {

	@Test
	void test01() {
		
		PipelineHub<IPipelineContainer<String>, String> strph = new PipelineHub<IPipelineContainer<String>, String>();
		Assertions.assertNotNull( strph );
		Assertions.assertEquals(0, strph.size());
		
		strph.addStep(new PipelineStepPreBuild<String>() {
			@Override
			public boolean process(IPipelineContainer<String> inputData) {
				this.input	= inputData; // XXX NOTE clone ??
				this.output	= inputData;
				this.output.setElement( this.output.getElement().concat( "step1" ) );
				return true;
			}
		});
		
		Assertions.assertEquals(1, strph.size());
	}
	
	@Test
	void test02() {
		
		PipelineHub<IPipelineContainer<String>, String> strph = new PipelineHub<IPipelineContainer<String>, String>();
		Assertions.assertNotNull( strph );
		Assertions.assertEquals(0, strph.size());
		
		strph.addStep(new PipelineStepPreBuild<String>() {
			@Override
			public boolean process(IPipelineContainer<String> inputData) {
				this.input	= inputData; // XXX NOTE clone ??
				this.output	= inputData;
				this.output.setElement( this.output.getElement().concat( "step1" ) );
				return true;
			}
		});
		
		Assertions.assertEquals(1, strph.size());
		
		IPipelineContainer<String> pcString = PipelineContainerBuilder.build(String.class);
		pcString.setElement( "" );
		
		IPipelineContainer<String> result = strph.processAll( pcString );
		
		Assertions.assertEquals("step1", result.getElement());
	}

}

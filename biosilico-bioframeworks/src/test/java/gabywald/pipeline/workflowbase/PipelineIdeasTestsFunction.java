package gabywald.pipeline.workflowbase;

import java.util.function.Function;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.pipeline.workflowbase.wip.PipelineStepFunctionImpl;
/**
 * 
 * @author Gabriel Chandesris (2022)
 */
class PipelineIdeasTestsFunction {

	@Test
	void test01() {

		PipelineHub<IPipelineContainer<String>, String> strph = new PipelineHub<IPipelineContainer<String>, String>();
		Assertions.assertNotNull( strph );
		Assertions.assertEquals(0, strph.size());

		PipelineStepFunctionImpl<IPipelineContainer<String>, String> psfi = 
				new PipelineStepFunctionImpl<IPipelineContainer<String>, String>();
		psfi.action(new Function<IPipelineContainer<String>, Boolean>() {
			@Override
			public Boolean apply(IPipelineContainer<String> step) {
				step.setElement( step.getElement().concat( "step1" ) );
				return true;
			}
		});

		strph.addStep(psfi);

		Assertions.assertEquals(1, strph.size());
	}

	@Test
	void test02() {

		PipelineHub<IPipelineContainer<String>, String> strph = new PipelineHub<IPipelineContainer<String>, String>();
		Assertions.assertNotNull( strph );
		Assertions.assertEquals(0, strph.size());

		PipelineStepFunctionImpl<IPipelineContainer<String>, String> psfi = 
				new PipelineStepFunctionImpl<IPipelineContainer<String>, String>();
		psfi.action(new Function<IPipelineContainer<String>, Boolean>() {
			@Override
			public Boolean apply(IPipelineContainer<String> step) {
				step.setElement( step.getElement().concat( "step1" ) );
				return true;
			}
		});

		strph.addStep(psfi);

		Assertions.assertEquals(1, strph.size());

		IPipelineContainer<String> pcString = PipelineContainerBuilder.build(String.class);
		pcString.setElement( "" );

		IPipelineContainer<String> result = strph.processAll( pcString );

		Assertions.assertEquals("step1", result.getElement());
	}

}

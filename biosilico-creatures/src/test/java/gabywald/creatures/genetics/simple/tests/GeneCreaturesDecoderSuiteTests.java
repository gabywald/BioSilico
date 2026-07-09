package gabywald.creatures.genetics.simple.tests;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.creatures.genetics.simple.Creatures1GenomeParser;
import gabywald.creatures.genetics.simple.decoder.GeneCreaturesDecoderSuite;
import gabywald.creatures.genetics.simple.decoder.IGeneCreaturesDecoder;

/**
 * 
 * @author Gabriel Chandesris (2020, 2026)
 */
class GeneCreaturesDecoderSuiteTests {

	@Test
	void testGetCreatures1Suite() {
		
		String packagePath		= Creatures1GenomeParser.PROPERTIES_BS_CREATURES.getProperty( "configuration.GeneCreaturesDecoderSuite.package" );
		Assertions.assertNotNull( packagePath );
		List<String> classes	= Arrays.asList( Creatures1GenomeParser.PROPERTIES_BS_CREATURES.getProperty( "configuration.GeneCreaturesDecoderSuite.classes.creatures1" ).split( ";" ) );
		Assertions.assertNotNull( classes );
		Assertions.assertTrue(classes.size() > 0);
		
		List<IGeneCreaturesDecoder> suite = GeneCreaturesDecoderSuite.getCreatures1Suite();
		Assertions.assertNotNull( suite );
		
		Assertions.assertEquals(classes.size(), suite.size());
	}

}

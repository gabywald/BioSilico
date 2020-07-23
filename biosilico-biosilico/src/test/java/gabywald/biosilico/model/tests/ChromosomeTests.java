package gabywald.biosilico.model.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.model.Chromosome;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class ChromosomeTests {

	@Test
	void testChromosome() {
		Chromosome chr = new Chromosome();
		Assertions.assertEquals(0, chr.getGeneNumber());
		Assertions.assertEquals(0, chr.length());
	}
	
	// TODO more tests for Chromosome !!
	
	// TODO complete these tests !!

}

package gabywald.biosilico.structures.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.structures.GeneticTranslator;
import gabywald.biosilico.structures.GeneticTreeNode;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class GeneticTreeNodeTests {

	@Test
	void testGeneticTreeNode() {
		GeneticTreeNode gtnStandard	= GeneticTreeNode.getGeneticCodeStandard();
		Assertions.assertNotNull( gtnStandard );
		Assertions.assertEquals(true, gtnStandard.isRoot());
		Assertions.assertEquals(false, gtnStandard.isLeave());
		
		GeneticTreeNode gtnGattaca	= GeneticTreeNode.getGeneticCodeGattaca();
		Assertions.assertNotNull( gtnGattaca );
		Assertions.assertEquals(true, gtnGattaca.isRoot());
		Assertions.assertEquals(false, gtnGattaca.isLeave());
		
		GeneticTreeNode gtnPhaseTwo	= GeneticTreeNode.getGeneticCodePhaseTwo();
		Assertions.assertNotNull( gtnPhaseTwo );
		Assertions.assertEquals(true, gtnPhaseTwo.isRoot());
		Assertions.assertEquals(false, gtnPhaseTwo.isLeave());
	}
	
	@Test
	void testTranslation() {
		Assertions.assertEquals("", GeneticTranslator.translationStandard("HELLQWQRLD*"));
		
		Assertions.assertEquals("", GeneticTranslator.translationGattaca("Mhelloworld*"));
	}
	
	@Test
	void testReverseTranslation() {
		Assertions.assertEquals("", GeneticTranslator.reverseStandard("HELLQWQRLD*"));
		
		Assertions.assertEquals("", GeneticTranslator.reverseGattaca("Mhelloworld*"));
		
		Assertions.assertEquals("", GeneticTranslator.reversePhaseTwo("MHello World !*"));
	}
	
	@Test
	void testTranslationSequence() {
		Assertions.assertEquals("", GeneticTranslator.translationStandard("HELLQWQRLD*"));
		
		Assertions.assertEquals("", GeneticTranslator.translationGattaca("Mhelloworld*"));
		
		Assertions.assertEquals("", GeneticTranslator.translationPhaseTwo("MHello World !*"));
	}
	
	@Test
	void testReverseTranslationSequence() {
		Assertions.assertEquals("caugaauuauuacaauggcaaagauuagauuaa", GeneticTranslator.reverseSequenceStandard("HELLQWQRLD*"));
		
		Assertions.assertEquals("GGAACGACAATGATGAGTCCTAGTCACATGAAGGGT", GeneticTranslator.reverseSequenceGattaca("Mhelloworld*"));
		
		Assertions.assertEquals("BUPUBUUVUUVUUBBVUBBVUBPUVVPVBVUUUBPUUVUVUBBVUUBVVVPVVBUVVBPU", GeneticTranslator.reverseSequencePhaseTwo("MHello World !*"));
	}

}

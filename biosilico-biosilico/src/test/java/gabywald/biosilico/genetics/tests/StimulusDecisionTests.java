package gabywald.biosilico.genetics.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.genetics.StimulusDecision;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class StimulusDecisionTests {

	@Test
	void testConstruction001() {
		StimulusDecision sdGene = new StimulusDecision(	false, false, false, false, 
														0, 999, 0, 50,
														false, false, 
														0, 0, 0, 0, 0, 0);

		Assertions.assertFalse(sdGene.canMutate());
		Assertions.assertFalse(sdGene.canDuplicate());
		Assertions.assertFalse(sdGene.canDelete());
		Assertions.assertFalse(sdGene.isActiv());
		Assertions.assertEquals(sdGene.getAgeMin(), 0);
		Assertions.assertEquals(sdGene.getAgeMax(), 999);
		Assertions.assertEquals(sdGene.getSexAct(), 0);
		Assertions.assertEquals(sdGene.getMutationRate(), 50);
		
		Assertions.assertFalse(sdGene.getPerception());
		Assertions.assertFalse(sdGene.getObject());
		
		Assertions.assertEquals(sdGene.getIndicator(), 0);
		Assertions.assertEquals(sdGene.getThreshold(), 0);
		Assertions.assertEquals(sdGene.getAttribute(), 0);
		Assertions.assertEquals(sdGene.getVariable(), 0);
		Assertions.assertEquals(sdGene.getValue(), 0);
		Assertions.assertEquals(sdGene.getScript(), 0);
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACGCTAGCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTGGT", 
				sdGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACGCTAGCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTGGT", 
				sdGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACGCTAGCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTGGT	false	false	false	false	0	999	0	50	false	false	0	0	0	0	0	0	", 
				sdGene.toString());
		
	}
	
	@Test
	void testConstruction002() {
		StimulusDecision sdGene = new StimulusDecision(	false, false, false, false, 
														0, 999, 0, 50,
														true, false, 
														0, 0, 0, 0, 0, 0);

		Assertions.assertFalse(sdGene.canMutate());
		Assertions.assertFalse(sdGene.canDuplicate());
		Assertions.assertFalse(sdGene.canDelete());
		Assertions.assertFalse(sdGene.isActiv());
		Assertions.assertEquals(sdGene.getAgeMin(), 0);
		Assertions.assertEquals(sdGene.getAgeMax(), 999);
		Assertions.assertEquals(sdGene.getSexAct(), 0);
		Assertions.assertEquals(sdGene.getMutationRate(), 50);
		
		Assertions.assertTrue(sdGene.getPerception());
		Assertions.assertFalse(sdGene.getObject());
		
		Assertions.assertEquals(sdGene.getIndicator(), 0);
		Assertions.assertEquals(sdGene.getThreshold(), 0);
		Assertions.assertEquals(sdGene.getAttribute(), 0);
		Assertions.assertEquals(sdGene.getVariable(), 0);
		Assertions.assertEquals(sdGene.getValue(), 0);
		Assertions.assertEquals(sdGene.getScript(), 0);
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTTAGCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTGGT", 
				sdGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTTAGCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTGGT", 
				sdGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTTAGCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTGGT	false	false	false	false	0	999	0	50	true	false	0	0	0	0	0	0	", 
				sdGene.toString());
		
	}
	
	@Test
	void testConstruction003() {
		StimulusDecision sdGene = new StimulusDecision(	false, false, false, false, 
														0, 999, 0, 50,
														false, true, 
														0, 0, 0, 0, 0, 0);

		Assertions.assertFalse(sdGene.canMutate());
		Assertions.assertFalse(sdGene.canDuplicate());
		Assertions.assertFalse(sdGene.canDelete());
		Assertions.assertFalse(sdGene.isActiv());
		Assertions.assertEquals(sdGene.getAgeMin(), 0);
		Assertions.assertEquals(sdGene.getAgeMax(), 999);
		Assertions.assertEquals(sdGene.getSexAct(), 0);
		Assertions.assertEquals(sdGene.getMutationRate(), 50);
		
		Assertions.assertFalse(sdGene.getPerception());
		Assertions.assertTrue(sdGene.getObject());
		
		Assertions.assertEquals(sdGene.getIndicator(), 0);
		Assertions.assertEquals(sdGene.getThreshold(), 0);
		Assertions.assertEquals(sdGene.getAttribute(), 0);
		Assertions.assertEquals(sdGene.getVariable(), 0);
		Assertions.assertEquals(sdGene.getValue(), 0);
		Assertions.assertEquals(sdGene.getScript(), 0);
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACGCTAACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTGGT", 
				sdGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACGCTAACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTGGT", 
				sdGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACGCTAACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTGGT	false	false	false	false	0	999	0	50	false	true	0	0	0	0	0	0	", 
				sdGene.toString());
		
	}
	
	@Test
	void testConstruction004() {
		StimulusDecision sdGene = new StimulusDecision(	false, false, false, false, 
														0, 999, 0, 50,
														true, true, 
														0, 0, 0, 0, 0, 0);

		Assertions.assertFalse(sdGene.canMutate());
		Assertions.assertFalse(sdGene.canDuplicate());
		Assertions.assertFalse(sdGene.canDelete());
		Assertions.assertFalse(sdGene.isActiv());
		Assertions.assertEquals(sdGene.getAgeMin(), 0);
		Assertions.assertEquals(sdGene.getAgeMax(), 999);
		Assertions.assertEquals(sdGene.getSexAct(), 0);
		Assertions.assertEquals(sdGene.getMutationRate(), 50);
		
		Assertions.assertTrue(sdGene.getPerception());
		Assertions.assertTrue(sdGene.getObject());
		
		Assertions.assertEquals(sdGene.getIndicator(), 0);
		Assertions.assertEquals(sdGene.getThreshold(), 0);
		Assertions.assertEquals(sdGene.getAttribute(), 0);
		Assertions.assertEquals(sdGene.getVariable(), 0);
		Assertions.assertEquals(sdGene.getValue(), 0);
		Assertions.assertEquals(sdGene.getScript(), 0);
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTTAACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTGGT", 
				sdGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTTAACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTGGT", 
				sdGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTTAACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTGGT	false	false	false	false	0	999	0	50	true	true	0	0	0	0	0	0	", 
				sdGene.toString());
		
	}
	
	@Test
	void testConstruction005() {
		StimulusDecision sdGene = new StimulusDecision(	false, false, false, false, 
														0, 999, 0, 50,
														false, false, 
														-1, -1, -1, -1, -1, -1);

		Assertions.assertFalse(sdGene.canMutate());
		Assertions.assertFalse(sdGene.canDuplicate());
		Assertions.assertFalse(sdGene.canDelete());
		Assertions.assertFalse(sdGene.isActiv());
		Assertions.assertEquals(sdGene.getAgeMin(), 0);
		Assertions.assertEquals(sdGene.getAgeMax(), 999);
		Assertions.assertEquals(sdGene.getSexAct(), 0);
		Assertions.assertEquals(sdGene.getMutationRate(), 50);
		
		Assertions.assertFalse(sdGene.getPerception());
		Assertions.assertFalse(sdGene.getObject());
		
		Assertions.assertEquals(sdGene.getIndicator(), 0);
		Assertions.assertEquals(sdGene.getThreshold(), 0);
		Assertions.assertEquals(sdGene.getAttribute(), 0);
		Assertions.assertEquals(sdGene.getVariable(), 0);
		Assertions.assertEquals(sdGene.getValue(), 0);
		Assertions.assertEquals(sdGene.getScript(), 0);
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACGCTAGCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTGGT", 
				sdGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACGCTAGCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTGGT", 
				sdGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACGCTAGCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTGGT	false	false	false	false	0	999	0	50	false	false	0	0	0	0	0	0	", 
				sdGene.toString());
		
	}
	
	@Test
	void testConstruction006() {
		StimulusDecision sdGene = new StimulusDecision(	false, false, false, false, 
														0, 999, 0, 50,
														false, false, 
														999, 999, 999, 999, 999, 999);

		Assertions.assertFalse(sdGene.canMutate());
		Assertions.assertFalse(sdGene.canDuplicate());
		Assertions.assertFalse(sdGene.canDelete());
		Assertions.assertFalse(sdGene.isActiv());
		Assertions.assertEquals(sdGene.getAgeMin(), 0);
		Assertions.assertEquals(sdGene.getAgeMax(), 999);
		Assertions.assertEquals(sdGene.getSexAct(), 0);
		Assertions.assertEquals(sdGene.getMutationRate(), 50);
		
		Assertions.assertFalse(sdGene.getPerception());
		Assertions.assertFalse(sdGene.getObject());
		
		Assertions.assertEquals(sdGene.getIndicator(), 999);
		Assertions.assertEquals(sdGene.getThreshold(), 999);
		Assertions.assertEquals(sdGene.getAttribute(), 999);
		Assertions.assertEquals(sdGene.getVariable(), 999);
		Assertions.assertEquals(sdGene.getValue(), 999);
		Assertions.assertEquals(sdGene.getScript(), 999);
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACGCTAGGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGGT", 
				sdGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACGCTAGGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGGT", 
				sdGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACGCTAGGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGGT	false	false	false	false	0	999	0	50	false	false	999	999	999	999	999	999	", 
				sdGene.toString());
		
	}
	
	@Test
	void testConstruction007() {
		StimulusDecision sdGene = new StimulusDecision(	false, false, false, false, 
														0, 999, 0, 50,
														false, false, 
														1050, 1050, 1050, 1050, 1050, 1050);

		Assertions.assertFalse(sdGene.canMutate());
		Assertions.assertFalse(sdGene.canDuplicate());
		Assertions.assertFalse(sdGene.canDelete());
		Assertions.assertFalse(sdGene.isActiv());
		Assertions.assertEquals(sdGene.getAgeMin(), 0);
		Assertions.assertEquals(sdGene.getAgeMax(), 999);
		Assertions.assertEquals(sdGene.getSexAct(), 0);
		Assertions.assertEquals(sdGene.getMutationRate(), 50);
		
		Assertions.assertFalse(sdGene.getPerception());
		Assertions.assertFalse(sdGene.getObject());
		
		Assertions.assertEquals(sdGene.getIndicator(), 999);
		Assertions.assertEquals(sdGene.getThreshold(), 999);
		Assertions.assertEquals(sdGene.getAttribute(), 999);
		Assertions.assertEquals(sdGene.getVariable(), 999);
		Assertions.assertEquals(sdGene.getValue(), 999);
		Assertions.assertEquals(sdGene.getScript(), 999);
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACGCTAGGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGGT", 
				sdGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACGCTAGGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGGT", 
				sdGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACGCTAGGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGGT	false	false	false	false	0	999	0	50	false	false	999	999	999	999	999	999	", 
				sdGene.toString());
		
	}
	
	@Test
	void testConstruction008() {
		StimulusDecision sdGene = new StimulusDecision(	false, false, false, false, 
														0, 999, 0, 50,
														false, false, 
														500, 500, 500, 500, 500, 500);

		Assertions.assertFalse(sdGene.canMutate());
		Assertions.assertFalse(sdGene.canDuplicate());
		Assertions.assertFalse(sdGene.canDelete());
		Assertions.assertFalse(sdGene.isActiv());
		Assertions.assertEquals(sdGene.getAgeMin(), 0);
		Assertions.assertEquals(sdGene.getAgeMax(), 999);
		Assertions.assertEquals(sdGene.getSexAct(), 0);
		Assertions.assertEquals(sdGene.getMutationRate(), 50);
		
		Assertions.assertFalse(sdGene.getPerception());
		Assertions.assertFalse(sdGene.getObject());
		
		Assertions.assertEquals(sdGene.getIndicator(), 500);
		Assertions.assertEquals(sdGene.getThreshold(), 500);
		Assertions.assertEquals(sdGene.getAttribute(), 500);
		Assertions.assertEquals(sdGene.getVariable(), 500);
		Assertions.assertEquals(sdGene.getValue(), 500);
		Assertions.assertEquals(sdGene.getScript(), 500);
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACGCTAGTTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTGGT", 
				sdGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACGCTAGTTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTGGT", 
				sdGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACGCTAGTTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTGGT	false	false	false	false	0	999	0	50	false	false	500	500	500	500	500	500	", 
				sdGene.toString());
		
	}

}

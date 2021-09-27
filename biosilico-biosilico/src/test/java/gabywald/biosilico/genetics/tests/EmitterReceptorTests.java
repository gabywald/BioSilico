package gabywald.biosilico.genetics.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.genetics.EmitterReceptor;
import gabywald.biosilico.genetics.builders.EmitterReceptorBuilder;

/**
 * 
 * @author Gabriel Chandesris (2020, 2021)
 */
class EmitterReceptorTests {

	@Test
	void testConstruction001() {
		EmitterReceptor erGene = new EmitterReceptor(false, false, false, false, 
													0, 999, 0, 50,
													0, 0, 0, 0, 0, false, false);

		Assertions.assertFalse(erGene.canMutate());
		Assertions.assertFalse(erGene.canDuplicate());
		Assertions.assertFalse(erGene.canDelete());
		Assertions.assertFalse(erGene.isActiv());
		Assertions.assertEquals(erGene.getAgeMin(), 0);
		Assertions.assertEquals(erGene.getAgeMax(), 999);
		Assertions.assertEquals(erGene.getSexAct(), 0);
		Assertions.assertEquals(erGene.getMutationRate(), 50);
		
		Assertions.assertEquals(erGene.getVariable(), 0);
		Assertions.assertEquals(erGene.getThreshold(), 0);
		Assertions.assertEquals(erGene.getIOnput(), 0);
		Assertions.assertEquals(erGene.getPosXNeurone(), 0);
		Assertions.assertEquals(erGene.getPosYNeurone(), 0);
		
		Assertions.assertFalse(erGene.getReceptor());
		Assertions.assertFalse(erGene.getInternal());
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCTAGGGT", 
				erGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCTAGGGT", 
				erGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCTAGGGT	false	false	false	false	0	999	0	50	0	0	0	0	0	false	false	", 
				erGene.toString());
	}
	
	@Test
	void testConstruction002() {
		EmitterReceptor erGene = new EmitterReceptor(false, false, false, false, 
													0, 999, 0, 50,
													-1, -1, -1, -1, -1, false, false);

		Assertions.assertFalse(erGene.canMutate());
		Assertions.assertFalse(erGene.canDuplicate());
		Assertions.assertFalse(erGene.canDelete());
		Assertions.assertFalse(erGene.isActiv());
		Assertions.assertEquals(erGene.getAgeMin(), 0);
		Assertions.assertEquals(erGene.getAgeMax(), 999);
		Assertions.assertEquals(erGene.getSexAct(), 0);
		Assertions.assertEquals(erGene.getMutationRate(), 50);
		
		Assertions.assertEquals(erGene.getVariable(), 0);
		Assertions.assertEquals(erGene.getThreshold(), 0);
		Assertions.assertEquals(erGene.getIOnput(), 0);
		Assertions.assertEquals(erGene.getPosXNeurone(), 0);
		Assertions.assertEquals(erGene.getPosYNeurone(), 0);
		
		Assertions.assertFalse(erGene.getReceptor());
		Assertions.assertFalse(erGene.getInternal());
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCTAGGGT", 
				erGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCTAGGGT", 
				erGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCTAGGGT	false	false	false	false	0	999	0	50	0	0	0	0	0	false	false	", 
				erGene.toString());
	}
	
	@Test
	void testConstruction003() {
		EmitterReceptor erGene = new EmitterReceptor(false, false, false, false, 
													0, 999, 0, 50,
													999, 999, 999, 99, 99, false, false);

		Assertions.assertFalse(erGene.canMutate());
		Assertions.assertFalse(erGene.canDuplicate());
		Assertions.assertFalse(erGene.canDelete());
		Assertions.assertFalse(erGene.isActiv());
		Assertions.assertEquals(erGene.getAgeMin(), 0);
		Assertions.assertEquals(erGene.getAgeMax(), 999);
		Assertions.assertEquals(erGene.getSexAct(), 0);
		Assertions.assertEquals(erGene.getMutationRate(), 50);
		
		Assertions.assertEquals(erGene.getVariable(), 999);
		Assertions.assertEquals(erGene.getThreshold(), 999);
		Assertions.assertEquals(erGene.getIOnput(), 999);
		Assertions.assertEquals(erGene.getPosXNeurone(), 99);
		Assertions.assertEquals(erGene.getPosYNeurone(), 99);
		
		Assertions.assertFalse(erGene.getReceptor());
		Assertions.assertFalse(erGene.getInternal());
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCCGCTAGGGT", 
				erGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCCGCTAGGGT", 
				erGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCCGCTAGGGT	false	false	false	false	0	999	0	50	999	999	999	99	99	false	false	", 
				erGene.toString());
	}
	
	@Test
	void testConstruction004() {
		EmitterReceptor erGene = new EmitterReceptor(false, false, false, false, 
													0, 999, 0, 50,
													1050, 1050, 1050, 106, 106, false, false);

		Assertions.assertFalse(erGene.canMutate());
		Assertions.assertFalse(erGene.canDuplicate());
		Assertions.assertFalse(erGene.canDelete());
		Assertions.assertFalse(erGene.isActiv());
		Assertions.assertEquals(erGene.getAgeMin(), 0);
		Assertions.assertEquals(erGene.getAgeMax(), 999);
		Assertions.assertEquals(erGene.getSexAct(), 0);
		Assertions.assertEquals(erGene.getMutationRate(), 50);
		
		Assertions.assertEquals(erGene.getVariable(), 999);
		Assertions.assertEquals(erGene.getThreshold(), 999);
		Assertions.assertEquals(erGene.getIOnput(), 999);
		Assertions.assertEquals(erGene.getPosXNeurone(), 99);
		Assertions.assertEquals(erGene.getPosYNeurone(), 99);
		
		Assertions.assertFalse(erGene.getReceptor());
		Assertions.assertFalse(erGene.getInternal());
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCCGCTAGGGT", 
				erGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCCGCTAGGGT", 
				erGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCCGCTAGGGT	false	false	false	false	0	999	0	50	999	999	999	99	99	false	false	", 
				erGene.toString());
	}
	
	@Test
	void testConstruction005() {
		EmitterReceptor erGene = new EmitterReceptor(false, false, false, false, 
													0, 999, 0, 50,
													0, 0, 0, 0, 0, true, false);

		Assertions.assertFalse(erGene.canMutate());
		Assertions.assertFalse(erGene.canDuplicate());
		Assertions.assertFalse(erGene.canDelete());
		Assertions.assertFalse(erGene.isActiv());
		Assertions.assertEquals(erGene.getAgeMin(), 0);
		Assertions.assertEquals(erGene.getAgeMax(), 999);
		Assertions.assertEquals(erGene.getSexAct(), 0);
		Assertions.assertEquals(erGene.getMutationRate(), 50);
		
		Assertions.assertEquals(erGene.getVariable(), 0);
		Assertions.assertEquals(erGene.getThreshold(), 0);
		Assertions.assertEquals(erGene.getIOnput(), 0);
		Assertions.assertEquals(erGene.getPosXNeurone(), 0);
		Assertions.assertEquals(erGene.getPosYNeurone(), 0);
		
		Assertions.assertTrue(erGene.getReceptor());
		Assertions.assertFalse(erGene.getInternal());
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTTAGGGT", 
				erGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTTAGGGT", 
				erGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTTAGGGT	false	false	false	false	0	999	0	50	0	0	0	0	0	true	false	", 
				erGene.toString());
	}
	
	@Test
	void testConstruction006() {
		EmitterReceptor erGene = new EmitterReceptor(false, false, false, false, 
													0, 999, 0, 50,
													0, 0, 0, 0, 0, false, true);

		Assertions.assertFalse(erGene.canMutate());
		Assertions.assertFalse(erGene.canDuplicate());
		Assertions.assertFalse(erGene.canDelete());
		Assertions.assertFalse(erGene.isActiv());
		Assertions.assertEquals(erGene.getAgeMin(), 0);
		Assertions.assertEquals(erGene.getAgeMax(), 999);
		Assertions.assertEquals(erGene.getSexAct(), 0);
		Assertions.assertEquals(erGene.getMutationRate(), 50);
		
		Assertions.assertEquals(erGene.getVariable(), 0);
		Assertions.assertEquals(erGene.getThreshold(), 0);
		Assertions.assertEquals(erGene.getIOnput(), 0);
		Assertions.assertEquals(erGene.getPosXNeurone(), 0);
		Assertions.assertEquals(erGene.getPosYNeurone(), 0);
		
		Assertions.assertFalse(erGene.getReceptor());
		Assertions.assertTrue(erGene.getInternal());
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCTAAGGT", 
				erGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCTAAGGT", 
				erGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCTAAGGT	false	false	false	false	0	999	0	50	0	0	0	0	0	false	true	", 
				erGene.toString());
	}
	
	@Test
	void testConstruction007() {
		EmitterReceptor erGene = new EmitterReceptor(false, false, false, false, 
													0, 999, 0, 50,
													0, 0, 0, 0, 0, true, true);

		Assertions.assertFalse(erGene.canMutate());
		Assertions.assertFalse(erGene.canDuplicate());
		Assertions.assertFalse(erGene.canDelete());
		Assertions.assertFalse(erGene.isActiv());
		Assertions.assertEquals(erGene.getAgeMin(), 0);
		Assertions.assertEquals(erGene.getAgeMax(), 999);
		Assertions.assertEquals(erGene.getSexAct(), 0);
		Assertions.assertEquals(erGene.getMutationRate(), 50);
		
		Assertions.assertEquals(erGene.getVariable(), 0);
		Assertions.assertEquals(erGene.getThreshold(), 0);
		Assertions.assertEquals(erGene.getIOnput(), 0);
		Assertions.assertEquals(erGene.getPosXNeurone(), 0);
		Assertions.assertEquals(erGene.getPosYNeurone(), 0);
		
		Assertions.assertTrue(erGene.getReceptor());
		Assertions.assertTrue(erGene.getInternal());
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTTAAGGT", 
				erGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTTAAGGT", 
				erGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTTAAGGT	false	false	false	false	0	999	0	50	0	0	0	0	0	true	true	", 
				erGene.toString());
	}
	
	@Test
	void testConstruction008() {
		EmitterReceptor erGene = new EmitterReceptor(false, false, false, false, 
													0, 999, 0, 50,
													500, 500, 500, 50, 50, false, false);

		Assertions.assertFalse(erGene.canMutate());
		Assertions.assertFalse(erGene.canDuplicate());
		Assertions.assertFalse(erGene.canDelete());
		Assertions.assertFalse(erGene.isActiv());
		Assertions.assertEquals(erGene.getAgeMin(), 0);
		Assertions.assertEquals(erGene.getAgeMax(), 999);
		Assertions.assertEquals(erGene.getSexAct(), 0);
		Assertions.assertEquals(erGene.getMutationRate(), 50);
		
		Assertions.assertEquals(erGene.getVariable(), 500);
		Assertions.assertEquals(erGene.getThreshold(), 500);
		Assertions.assertEquals(erGene.getIOnput(), 500);
		Assertions.assertEquals(erGene.getPosXNeurone(), 50);
		Assertions.assertEquals(erGene.getPosYNeurone(), 50);
		
		Assertions.assertFalse(erGene.getReceptor());
		Assertions.assertFalse(erGene.getInternal());
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTATTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTTTCCTTCGCTAGGGT", 
				erGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTATTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTTTCCTTCGCTAGGGT", 
				erGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTATTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTTTCCTTCGCTAGGGT	false	false	false	false	0	999	0	50	500	500	500	50	50	false	false	", 
				erGene.toString());
	}
	
	@Test
	void testConstruction009() {
		EmitterReceptorBuilder erb = new EmitterReceptorBuilder();
		EmitterReceptor erGene = erb.variable(500).threshold(500)
										.ioput(500).posx(50).posy(50)
										.receptor(false).internal(false)
										.agemax(999).mutation(50).build();

		Assertions.assertFalse(erGene.canMutate());
		Assertions.assertFalse(erGene.canDuplicate());
		Assertions.assertFalse(erGene.canDelete());
		Assertions.assertFalse(erGene.isActiv());
		Assertions.assertEquals(erGene.getAgeMin(), 0);
		Assertions.assertEquals(erGene.getAgeMax(), 999);
		Assertions.assertEquals(erGene.getSexAct(), 0);
		Assertions.assertEquals(erGene.getMutationRate(), 50);
		
		Assertions.assertEquals(erGene.getVariable(), 500);
		Assertions.assertEquals(erGene.getThreshold(), 500);
		Assertions.assertEquals(erGene.getIOnput(), 500);
		Assertions.assertEquals(erGene.getPosXNeurone(), 50);
		Assertions.assertEquals(erGene.getPosYNeurone(), 50);
		
		Assertions.assertFalse(erGene.getReceptor());
		Assertions.assertFalse(erGene.getInternal());
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTATTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTTTCCTTCGCTAGGGT", 
				erGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTATTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTTTCCTTCGCTAGGGT", 
				erGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTATTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTTTCCTTCGCTAGGGT	false	false	false	false	0	999	0	50	500	500	500	50	50	false	false	", 
				erGene.toString());
	}

}

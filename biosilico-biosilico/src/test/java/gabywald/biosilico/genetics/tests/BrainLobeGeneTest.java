package gabywald.biosilico.genetics.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.genetics.BrainLobeGene;
import gabywald.biosilico.genetics.builders.BrainLobeGeneBuilder;

class BrainLobeGeneTest {

	@Test
	void testConstruction001() {
		BrainLobeGene bGene = new BrainLobeGene(	false, false, false, false, 
													0, 999, 0, 50,
													0, 0, 0, 0, 0, 
													0, false, 0, false, 
													0, 0, 0, 0, false);

		Assertions.assertFalse(bGene.canMutate());
		Assertions.assertFalse(bGene.canDuplicate());
		Assertions.assertFalse(bGene.canDelete());
		Assertions.assertFalse(bGene.isActiv());
		Assertions.assertEquals(bGene.getAgeMin(), 0);
		Assertions.assertEquals(bGene.getAgeMax(), 999);
		Assertions.assertEquals(bGene.getSexAct(), 0);
		Assertions.assertEquals(bGene.getMutationRate(), 50);

		Assertions.assertEquals(bGene.getRestState(), 0);
		Assertions.assertEquals(bGene.getThreshold(), 0);
		Assertions.assertEquals(bGene.getDescent(), 0);
		Assertions.assertEquals(bGene.getDendritMin(), 0);
		Assertions.assertEquals(bGene.getDendritMax(), 0);
		Assertions.assertEquals(bGene.getProximity(), 0);
		Assertions.assertEquals(bGene.getReproduce(), false);
		Assertions.assertEquals(bGene.getReproduct(), 0);
		Assertions.assertEquals(bGene.getWTA(), false);
		Assertions.assertEquals(bGene.getLobeHeight(), 0);
		Assertions.assertEquals(bGene.getLobeWidth(), 0);
		Assertions.assertEquals(bGene.getLobePosX(), 0);
		Assertions.assertEquals(bGene.getLobePosY(), 0);
		Assertions.assertEquals(bGene.getReplace(), false);

		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAGCTTCTTCTTCTTCTTCTTCTTCTTTTCGGT", 
				bGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAGCTTCTTCTTCTTCTTCTTCTTCTTTTCGGT", 
				bGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAGCTTCTTCTTCTTCTTCTTCTTCTTTTCGGT	false	false	false	false	0	999	0	50	0	0	0	0	0	0	false	0	false	0	0	0	0	false	", 
				bGene.toString());

	}
	
	@Test
	void testConstruction002() {
		BrainLobeGene bGene = new BrainLobeGene(	false, false, false, false, 
													0, 999, 0, 50,
													0, 0, 0, 0, 0, 
													0, false, 0, false, 
													-1, -1, -1, -1, false);

		Assertions.assertFalse(bGene.canMutate());
		Assertions.assertFalse(bGene.canDuplicate());
		Assertions.assertFalse(bGene.canDelete());
		Assertions.assertFalse(bGene.isActiv());
		Assertions.assertEquals(bGene.getAgeMin(), 0);
		Assertions.assertEquals(bGene.getAgeMax(), 999);
		Assertions.assertEquals(bGene.getSexAct(), 0);
		Assertions.assertEquals(bGene.getMutationRate(), 50);

		Assertions.assertEquals(bGene.getRestState(), 0);
		Assertions.assertEquals(bGene.getThreshold(), 0);
		Assertions.assertEquals(bGene.getDescent(), 0);
		Assertions.assertEquals(bGene.getDendritMin(), 0);
		Assertions.assertEquals(bGene.getDendritMax(), 0);
		Assertions.assertEquals(bGene.getProximity(), 0);
		Assertions.assertEquals(bGene.getReproduce(), false);
		Assertions.assertEquals(bGene.getReproduct(), 0);
		Assertions.assertEquals(bGene.getWTA(), false);
		Assertions.assertEquals(bGene.getLobeHeight(), 0);
		Assertions.assertEquals(bGene.getLobeWidth(), 0);
		Assertions.assertEquals(bGene.getLobePosX(), 0);
		Assertions.assertEquals(bGene.getLobePosY(), 0);
		Assertions.assertEquals(bGene.getReplace(), false);

		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAGCTTCTTCTTCTTCTTCTTCTTCTTTTCGGT", 
				bGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAGCTTCTTCTTCTTCTTCTTCTTCTTTTCGGT", 
				bGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAGCTTCTTCTTCTTCTTCTTCTTCTTTTCGGT	false	false	false	false	0	999	0	50	0	0	0	0	0	0	false	0	false	0	0	0	0	false	", 
				bGene.toString());

	}
	
	@Test
	void testConstruction003() {
		BrainLobeGene bGene = new BrainLobeGene(	false, false, false, false, 
													0, 999, 0, 50,
													0, 0, 0, 0, 0, 
													0, false, 0, false, 
													99, 99, 99, 99, false);

		Assertions.assertFalse(bGene.canMutate());
		Assertions.assertFalse(bGene.canDuplicate());
		Assertions.assertFalse(bGene.canDelete());
		Assertions.assertFalse(bGene.isActiv());
		Assertions.assertEquals(bGene.getAgeMin(), 0);
		Assertions.assertEquals(bGene.getAgeMax(), 999);
		Assertions.assertEquals(bGene.getSexAct(), 0);
		Assertions.assertEquals(bGene.getMutationRate(), 50);

		Assertions.assertEquals(bGene.getRestState(), 0);
		Assertions.assertEquals(bGene.getThreshold(), 0);
		Assertions.assertEquals(bGene.getDescent(), 0);
		Assertions.assertEquals(bGene.getDendritMin(), 0);
		Assertions.assertEquals(bGene.getDendritMax(), 0);
		Assertions.assertEquals(bGene.getProximity(), 0);
		Assertions.assertEquals(bGene.getReproduce(), false);
		Assertions.assertEquals(bGene.getReproduct(), 0);
		Assertions.assertEquals(bGene.getWTA(), false);
		Assertions.assertEquals(bGene.getLobeHeight(), 99);
		Assertions.assertEquals(bGene.getLobeWidth(), 99);
		Assertions.assertEquals(bGene.getLobePosX(), 99);
		Assertions.assertEquals(bGene.getLobePosY(), 99);
		Assertions.assertEquals(bGene.getReplace(), false);

		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAGGCCGCCGCCGCCGCCGCCGCCGCCTTCGGT", 
				bGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAGGCCGCCGCCGCCGCCGCCGCCGCCTTCGGT", 
				bGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAGGCCGCCGCCGCCGCCGCCGCCGCCTTCGGT	false	false	false	false	0	999	0	50	0	0	0	0	0	0	false	0	false	99	99	99	99	false	", 
				bGene.toString());

	}
	
	@Test
	void testConstruction004() {
		BrainLobeGene bGene = new BrainLobeGene(	false, false, false, false, 
													0, 999, 0, 50,
													0, 0, 0, 0, 0, 
													0, false, 0, false, 
													105, 105, 105, 105, false);

		Assertions.assertFalse(bGene.canMutate());
		Assertions.assertFalse(bGene.canDuplicate());
		Assertions.assertFalse(bGene.canDelete());
		Assertions.assertFalse(bGene.isActiv());
		Assertions.assertEquals(bGene.getAgeMin(), 0);
		Assertions.assertEquals(bGene.getAgeMax(), 999);
		Assertions.assertEquals(bGene.getSexAct(), 0);
		Assertions.assertEquals(bGene.getMutationRate(), 50);

		Assertions.assertEquals(bGene.getRestState(), 0);
		Assertions.assertEquals(bGene.getThreshold(), 0);
		Assertions.assertEquals(bGene.getDescent(), 0);
		Assertions.assertEquals(bGene.getDendritMin(), 0);
		Assertions.assertEquals(bGene.getDendritMax(), 0);
		Assertions.assertEquals(bGene.getProximity(), 0);
		Assertions.assertEquals(bGene.getReproduce(), false);
		Assertions.assertEquals(bGene.getReproduct(), 0);
		Assertions.assertEquals(bGene.getWTA(), false);
		Assertions.assertEquals(bGene.getLobeHeight(), 99);
		Assertions.assertEquals(bGene.getLobeWidth(), 99);
		Assertions.assertEquals(bGene.getLobePosX(), 99);
		Assertions.assertEquals(bGene.getLobePosY(), 99);
		Assertions.assertEquals(bGene.getReplace(), false);

		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAGGCCGCCGCCGCCGCCGCCGCCGCCTTCGGT", 
				bGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAGGCCGCCGCCGCCGCCGCCGCCGCCTTCGGT", 
				bGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAGGCCGCCGCCGCCGCCGCCGCCGCCTTCGGT	false	false	false	false	0	999	0	50	0	0	0	0	0	0	false	0	false	99	99	99	99	false	", 
				bGene.toString());

	}
	
	@Test
	void testConstruction005() {
		BrainLobeGene bGene = new BrainLobeGene(	false, false, false, false, 
													0, 999, 0, 50,
													0, 0, 0, 0, 0, 
													0, true, 0, false, 
													0, 0, 0, 0, false);

		Assertions.assertFalse(bGene.canMutate());
		Assertions.assertFalse(bGene.canDuplicate());
		Assertions.assertFalse(bGene.canDelete());
		Assertions.assertFalse(bGene.isActiv());
		Assertions.assertEquals(bGene.getAgeMin(), 0);
		Assertions.assertEquals(bGene.getAgeMax(), 999);
		Assertions.assertEquals(bGene.getSexAct(), 0);
		Assertions.assertEquals(bGene.getMutationRate(), 50);

		Assertions.assertEquals(bGene.getRestState(), 0);
		Assertions.assertEquals(bGene.getThreshold(), 0);
		Assertions.assertEquals(bGene.getDescent(), 0);
		Assertions.assertEquals(bGene.getDendritMin(), 0);
		Assertions.assertEquals(bGene.getDendritMax(), 0);
		Assertions.assertEquals(bGene.getProximity(), 0);
		Assertions.assertEquals(bGene.getReproduce(), true);
		Assertions.assertEquals(bGene.getReproduct(), 0);
		Assertions.assertEquals(bGene.getWTA(), false);
		Assertions.assertEquals(bGene.getLobeHeight(), 0);
		Assertions.assertEquals(bGene.getLobeWidth(), 0);
		Assertions.assertEquals(bGene.getLobePosX(), 0);
		Assertions.assertEquals(bGene.getLobePosY(), 0);
		Assertions.assertEquals(bGene.getReplace(), false);

		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTTAGCTTCTTCTTCTTCTTCTTCTTCTTTTCGGT", 
				bGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTTAGCTTCTTCTTCTTCTTCTTCTTCTTTTCGGT", 
				bGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTTAGCTTCTTCTTCTTCTTCTTCTTCTTTTCGGT	false	false	false	false	0	999	0	50	0	0	0	0	0	0	true	0	false	0	0	0	0	false	", 
				bGene.toString());

	}
	
	@Test
	void testConstruction006() {
		BrainLobeGene bGene = new BrainLobeGene(	false, false, false, false, 
													0, 999, 0, 50,
													0, 0, 0, 0, 0, 
													0, false, 0, true, 
													0, 0, 0, 0, false);

		Assertions.assertFalse(bGene.canMutate());
		Assertions.assertFalse(bGene.canDuplicate());
		Assertions.assertFalse(bGene.canDelete());
		Assertions.assertFalse(bGene.isActiv());
		Assertions.assertEquals(bGene.getAgeMin(), 0);
		Assertions.assertEquals(bGene.getAgeMax(), 999);
		Assertions.assertEquals(bGene.getSexAct(), 0);
		Assertions.assertEquals(bGene.getMutationRate(), 50);

		Assertions.assertEquals(bGene.getRestState(), 0);
		Assertions.assertEquals(bGene.getThreshold(), 0);
		Assertions.assertEquals(bGene.getDescent(), 0);
		Assertions.assertEquals(bGene.getDendritMin(), 0);
		Assertions.assertEquals(bGene.getDendritMax(), 0);
		Assertions.assertEquals(bGene.getProximity(), 0);
		Assertions.assertEquals(bGene.getReproduce(), false);
		Assertions.assertEquals(bGene.getReproduct(), 0);
		Assertions.assertEquals(bGene.getWTA(), true);
		Assertions.assertEquals(bGene.getLobeHeight(), 0);
		Assertions.assertEquals(bGene.getLobeWidth(), 0);
		Assertions.assertEquals(bGene.getLobePosX(), 0);
		Assertions.assertEquals(bGene.getLobePosY(), 0);
		Assertions.assertEquals(bGene.getReplace(), false);

		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAACTTCTTCTTCTTCTTCTTCTTCTTTTCGGT", 
				bGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAACTTCTTCTTCTTCTTCTTCTTCTTTTCGGT", 
				bGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAACTTCTTCTTCTTCTTCTTCTTCTTTTCGGT	false	false	false	false	0	999	0	50	0	0	0	0	0	0	false	0	true	0	0	0	0	false	", 
				bGene.toString());

	}
	
	@Test
	void testConstruction007() {
		BrainLobeGene bGene = new BrainLobeGene(	false, false, false, false, 
													0, 999, 0, 50,
													0, 0, 0, 0, 0, 
													0, false, 0, false, 
													0, 0, 0, 0, true);

		Assertions.assertFalse(bGene.canMutate());
		Assertions.assertFalse(bGene.canDuplicate());
		Assertions.assertFalse(bGene.canDelete());
		Assertions.assertFalse(bGene.isActiv());
		Assertions.assertEquals(bGene.getAgeMin(), 0);
		Assertions.assertEquals(bGene.getAgeMax(), 999);
		Assertions.assertEquals(bGene.getSexAct(), 0);
		Assertions.assertEquals(bGene.getMutationRate(), 50);

		Assertions.assertEquals(bGene.getRestState(), 0);
		Assertions.assertEquals(bGene.getThreshold(), 0);
		Assertions.assertEquals(bGene.getDescent(), 0);
		Assertions.assertEquals(bGene.getDendritMin(), 0);
		Assertions.assertEquals(bGene.getDendritMax(), 0);
		Assertions.assertEquals(bGene.getProximity(), 0);
		Assertions.assertEquals(bGene.getReproduce(), false);
		Assertions.assertEquals(bGene.getReproduct(), 0);
		Assertions.assertEquals(bGene.getWTA(), false);
		Assertions.assertEquals(bGene.getLobeHeight(), 0);
		Assertions.assertEquals(bGene.getLobeWidth(), 0);
		Assertions.assertEquals(bGene.getLobePosX(), 0);
		Assertions.assertEquals(bGene.getLobePosY(), 0);
		Assertions.assertEquals(bGene.getReplace(), true);

		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAGCTTCTTCTTCTTCTTCTTCTTCTTTCTGGT", 
				bGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAGCTTCTTCTTCTTCTTCTTCTTCTTTCTGGT", 
				bGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAGCTTCTTCTTCTTCTTCTTCTTCTTTCTGGT	false	false	false	false	0	999	0	50	0	0	0	0	0	0	false	0	false	0	0	0	0	true	", 
				bGene.toString());

	}
	
	@Test
	void testConstruction008() {
		BrainLobeGene bGene = new BrainLobeGene(	false, false, false, false, 
													0, 999, 0, 50,
													-1, -1, -1, -1, -1, 
													-1, false, -1, false, 
													0, 0, 0, 0, false);

		Assertions.assertFalse(bGene.canMutate());
		Assertions.assertFalse(bGene.canDuplicate());
		Assertions.assertFalse(bGene.canDelete());
		Assertions.assertFalse(bGene.isActiv());
		Assertions.assertEquals(bGene.getAgeMin(), 0);
		Assertions.assertEquals(bGene.getAgeMax(), 999);
		Assertions.assertEquals(bGene.getSexAct(), 0);
		Assertions.assertEquals(bGene.getMutationRate(), 50);

		Assertions.assertEquals(bGene.getRestState(), -1);
		Assertions.assertEquals(bGene.getThreshold(), -1);
		Assertions.assertEquals(bGene.getDescent(), -1);
		Assertions.assertEquals(bGene.getDendritMin(), 0);
		Assertions.assertEquals(bGene.getDendritMax(), 0);
		Assertions.assertEquals(bGene.getProximity(), 0);
		Assertions.assertEquals(bGene.getReproduce(), false);
		Assertions.assertEquals(bGene.getReproduct(), 0);
		Assertions.assertEquals(bGene.getWTA(), false);
		Assertions.assertEquals(bGene.getLobeHeight(), 0);
		Assertions.assertEquals(bGene.getLobeWidth(), 0);
		Assertions.assertEquals(bGene.getLobePosX(), 0);
		Assertions.assertEquals(bGene.getLobePosY(), 0);
		Assertions.assertEquals(bGene.getReplace(), false);

		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCGCCTTCTTCGCCTTCTTCGCCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAGCTTCTTCTTCTTCTTCTTCTTCTTTTCGGT", 
				bGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCGCCTTCTTCGCCTTCTTCGCCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAGCTTCTTCTTCTTCTTCTTCTTCTTTTCGGT", 
				bGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCGCCTTCTTCGCCTTCTTCGCCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAGCTTCTTCTTCTTCTTCTTCTTCTTTTCGGT	false	false	false	false	0	999	0	50	-1	-1	-1	0	0	0	false	0	false	0	0	0	0	false	", 
				bGene.toString());

	}
	
	@Test
	void testConstruction009() {
		BrainLobeGene bGene = new BrainLobeGene(	false, false, false, false, 
													0, 999, 0, 50,
													999, 999, 999, 999, 999, 
													999, false, 999, false, 
													0, 0, 0, 0, false);

		Assertions.assertFalse(bGene.canMutate());
		Assertions.assertFalse(bGene.canDuplicate());
		Assertions.assertFalse(bGene.canDelete());
		Assertions.assertFalse(bGene.isActiv());
		Assertions.assertEquals(bGene.getAgeMin(), 0);
		Assertions.assertEquals(bGene.getAgeMax(), 999);
		Assertions.assertEquals(bGene.getSexAct(), 0);
		Assertions.assertEquals(bGene.getMutationRate(), 50);

		Assertions.assertEquals(bGene.getRestState(), 999);
		Assertions.assertEquals(bGene.getThreshold(), 999);
		Assertions.assertEquals(bGene.getDescent(), 999);
		Assertions.assertEquals(bGene.getDendritMin(), 999);
		Assertions.assertEquals(bGene.getDendritMax(), 999);
		Assertions.assertEquals(bGene.getProximity(), 999);
		Assertions.assertEquals(bGene.getReproduce(), false);
		Assertions.assertEquals(bGene.getReproduct(), 999);
		Assertions.assertEquals(bGene.getWTA(), false);
		Assertions.assertEquals(bGene.getLobeHeight(), 0);
		Assertions.assertEquals(bGene.getLobeWidth(), 0);
		Assertions.assertEquals(bGene.getLobePosX(), 0);
		Assertions.assertEquals(bGene.getLobePosY(), 0);
		Assertions.assertEquals(bGene.getReplace(), false);

		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCCGCGCCGCCGCCTAGCTTCTTCTTCTTCTTCTTCTTCTTTTCGGT", 
				bGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCCGCGCCGCCGCCTAGCTTCTTCTTCTTCTTCTTCTTCTTTTCGGT", 
				bGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCCGCGCCGCCGCCTAGCTTCTTCTTCTTCTTCTTCTTCTTTTCGGT	false	false	false	false	0	999	0	50	999	999	999	999	999	999	false	999	false	0	0	0	0	false	", 
				bGene.toString());

	}
	
	@Test
	void testConstruction010() {
		BrainLobeGene bGene = new BrainLobeGene(	false, false, false, false, 
													0, 999, 0, 50,
													999, 999, 999, 999, 999, 
													999, false, 999, false, 
													0, 0, 0, 0, false);

		Assertions.assertFalse(bGene.canMutate());
		Assertions.assertFalse(bGene.canDuplicate());
		Assertions.assertFalse(bGene.canDelete());
		Assertions.assertFalse(bGene.isActiv());
		Assertions.assertEquals(bGene.getAgeMin(), 0);
		Assertions.assertEquals(bGene.getAgeMax(), 999);
		Assertions.assertEquals(bGene.getSexAct(), 0);
		Assertions.assertEquals(bGene.getMutationRate(), 50);

		Assertions.assertEquals(bGene.getRestState(), 999);
		Assertions.assertEquals(bGene.getThreshold(), 999);
		Assertions.assertEquals(bGene.getDescent(), 999);
		Assertions.assertEquals(bGene.getDendritMin(), 999);
		Assertions.assertEquals(bGene.getDendritMax(), 999);
		Assertions.assertEquals(bGene.getProximity(), 999);
		Assertions.assertEquals(bGene.getReproduce(), false);
		Assertions.assertEquals(bGene.getReproduct(), 999);
		Assertions.assertEquals(bGene.getWTA(), false);
		Assertions.assertEquals(bGene.getLobeHeight(), 0);
		Assertions.assertEquals(bGene.getLobeWidth(), 0);
		Assertions.assertEquals(bGene.getLobePosX(), 0);
		Assertions.assertEquals(bGene.getLobePosY(), 0);
		Assertions.assertEquals(bGene.getReplace(), false);

		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCCGCGCCGCCGCCTAGCTTCTTCTTCTTCTTCTTCTTCTTTTCGGT", 
				bGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCCGCGCCGCCGCCTAGCTTCTTCTTCTTCTTCTTCTTCTTTTCGGT", 
				bGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCCGCGCCGCCGCCTAGCTTCTTCTTCTTCTTCTTCTTCTTTTCGGT	false	false	false	false	0	999	0	50	999	999	999	999	999	999	false	999	false	0	0	0	0	false	", 
				bGene.toString());

	}
	
	@Test
	void testConstruction011() {
		BrainLobeGene bGene = new BrainLobeGene(	false, false, false, false, 
													0, 999, 0, 50,
													500, 500, 500, 500, 500, 
													500, false, 500, false, 
													0, 0, 0, 0, false);

		Assertions.assertFalse(bGene.canMutate());
		Assertions.assertFalse(bGene.canDuplicate());
		Assertions.assertFalse(bGene.canDelete());
		Assertions.assertFalse(bGene.isActiv());
		Assertions.assertEquals(bGene.getAgeMin(), 0);
		Assertions.assertEquals(bGene.getAgeMax(), 999);
		Assertions.assertEquals(bGene.getSexAct(), 0);
		Assertions.assertEquals(bGene.getMutationRate(), 50);

		Assertions.assertEquals(bGene.getRestState(), 500);
		Assertions.assertEquals(bGene.getThreshold(), 500);
		Assertions.assertEquals(bGene.getDescent(), 500);
		Assertions.assertEquals(bGene.getDendritMin(), 500);
		Assertions.assertEquals(bGene.getDendritMax(), 500);
		Assertions.assertEquals(bGene.getProximity(), 500);
		Assertions.assertEquals(bGene.getReproduce(), false);
		Assertions.assertEquals(bGene.getReproduct(), 500);
		Assertions.assertEquals(bGene.getWTA(), false);
		Assertions.assertEquals(bGene.getLobeHeight(), 0);
		Assertions.assertEquals(bGene.getLobeWidth(), 0);
		Assertions.assertEquals(bGene.getLobePosX(), 0);
		Assertions.assertEquals(bGene.getLobePosY(), 0);
		Assertions.assertEquals(bGene.getReplace(), false);

		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTATTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTCGCTTCCTTCTTTAGCTTCTTCTTCTTCTTCTTCTTCTTTTCGGT", 
				bGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTATTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTCGCTTCCTTCTTTAGCTTCTTCTTCTTCTTCTTCTTCTTTTCGGT", 
				bGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTATTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTCGCTTCCTTCTTTAGCTTCTTCTTCTTCTTCTTCTTCTTTTCGGT	false	false	false	false	0	999	0	50	500	500	500	500	500	500	false	500	false	0	0	0	0	false	", 
				bGene.toString());

	}
	
	@Test
	void testConstruction012() {
		BrainLobeGene bGene = new BrainLobeGene(	false, false, false, false, 
													0, 999, 0, 50,
													0, 0, 0, 0, 0, 
													0, false, 0, false, 
													50, 50, 50, 50, false);

		Assertions.assertFalse(bGene.canMutate());
		Assertions.assertFalse(bGene.canDuplicate());
		Assertions.assertFalse(bGene.canDelete());
		Assertions.assertFalse(bGene.isActiv());
		Assertions.assertEquals(bGene.getAgeMin(), 0);
		Assertions.assertEquals(bGene.getAgeMax(), 999);
		Assertions.assertEquals(bGene.getSexAct(), 0);
		Assertions.assertEquals(bGene.getMutationRate(), 50);

		Assertions.assertEquals(bGene.getRestState(), 0);
		Assertions.assertEquals(bGene.getThreshold(), 0);
		Assertions.assertEquals(bGene.getDescent(), 0);
		Assertions.assertEquals(bGene.getDendritMin(), 0);
		Assertions.assertEquals(bGene.getDendritMax(), 0);
		Assertions.assertEquals(bGene.getProximity(), 0);
		Assertions.assertEquals(bGene.getReproduce(), false);
		Assertions.assertEquals(bGene.getReproduct(), 0);
		Assertions.assertEquals(bGene.getWTA(), false);
		Assertions.assertEquals(bGene.getLobeHeight(), 50);
		Assertions.assertEquals(bGene.getLobeWidth(), 50);
		Assertions.assertEquals(bGene.getLobePosX(), 50);
		Assertions.assertEquals(bGene.getLobePosY(), 50);
		Assertions.assertEquals(bGene.getReplace(), false);

		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAGTTCCTTTTCCTTTTCCTTTTCCTTTTCGGT", 
				bGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAGTTCCTTTTCCTTTTCCTTTTCCTTTTCGGT", 
				bGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAGTTCCTTTTCCTTTTCCTTTTCCTTTTCGGT	false	false	false	false	0	999	0	50	0	0	0	0	0	0	false	0	false	50	50	50	50	false	", 
				bGene.toString());

	}
	
	@Test
	void testConstruction013() {
		BrainLobeGeneBuilder blgb = new BrainLobeGeneBuilder();
		
		BrainLobeGene bGene = blgb.dmin(0).dmax(0).agemax(999).mutation(50).build();
				
		Assertions.assertFalse(bGene.canMutate());
		Assertions.assertFalse(bGene.canDuplicate());
		Assertions.assertFalse(bGene.canDelete());
		Assertions.assertFalse(bGene.isActiv());
		Assertions.assertEquals(bGene.getAgeMin(), 0);
		Assertions.assertEquals(bGene.getAgeMax(), 999);
		Assertions.assertEquals(bGene.getSexAct(), 0);
		Assertions.assertEquals(bGene.getMutationRate(), 50);

		Assertions.assertEquals(bGene.getRestState(), 0);
		Assertions.assertEquals(bGene.getThreshold(), 0);
		Assertions.assertEquals(bGene.getDescent(), 0);
		Assertions.assertEquals(bGene.getDendritMin(), 0);
		Assertions.assertEquals(bGene.getDendritMax(), 0);
		Assertions.assertEquals(bGene.getProximity(), 0);
		Assertions.assertEquals(bGene.getReproduce(), false);
		Assertions.assertEquals(bGene.getReproduct(), 0);
		Assertions.assertEquals(bGene.getWTA(), false);
		Assertions.assertEquals(bGene.getLobeHeight(), 0);
		Assertions.assertEquals(bGene.getLobeWidth(), 0);
		Assertions.assertEquals(bGene.getLobePosX(), 0);
		Assertions.assertEquals(bGene.getLobePosY(), 0);
		Assertions.assertEquals(bGene.getReplace(), false);

		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAGCTTCTTCTTCTTCTTCTTCTTCTTTTCGGT", 
				bGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAGCTTCTTCTTCTTCTTCTTCTTCTTTTCGGT", 
				bGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAGCTTCTTCTTCTTCTTCTTCTTCTTTTCGGT	false	false	false	false	0	999	0	50	0	0	0	0	0	0	false	0	false	0	0	0	0	false	", 
				bGene.toString());

	}
	
	@Test
	void testConstruction014() {
		BrainLobeGeneBuilder blgb = new BrainLobeGeneBuilder();
		
		BrainLobeGene bGene = blgb.dmin(0).dmax(0).width(50).heigth(50).posx(50).posy(50)
								.agemax(999).mutation(50).build();
				
		Assertions.assertFalse(bGene.canMutate());
		Assertions.assertFalse(bGene.canDuplicate());
		Assertions.assertFalse(bGene.canDelete());
		Assertions.assertFalse(bGene.isActiv());
		Assertions.assertEquals(bGene.getAgeMin(), 0);
		Assertions.assertEquals(bGene.getAgeMax(), 999);
		Assertions.assertEquals(bGene.getSexAct(), 0);
		Assertions.assertEquals(bGene.getMutationRate(), 50);

		Assertions.assertEquals(bGene.getRestState(), 0);
		Assertions.assertEquals(bGene.getThreshold(), 0);
		Assertions.assertEquals(bGene.getDescent(), 0);
		Assertions.assertEquals(bGene.getDendritMin(), 0);
		Assertions.assertEquals(bGene.getDendritMax(), 0);
		Assertions.assertEquals(bGene.getProximity(), 0);
		Assertions.assertEquals(bGene.getReproduce(), false);
		Assertions.assertEquals(bGene.getReproduct(), 0);
		Assertions.assertEquals(bGene.getWTA(), false);
		Assertions.assertEquals(bGene.getLobeHeight(), 50);
		Assertions.assertEquals(bGene.getLobeWidth(), 50);
		Assertions.assertEquals(bGene.getLobePosX(), 50);
		Assertions.assertEquals(bGene.getLobePosY(), 50);
		Assertions.assertEquals(bGene.getReplace(), false);

		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAGTTCCTTTTCCTTTTCCTTTTCCTTTTCGGT", 
				bGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAGTTCCTTTTCCTTTTCCTTTTCCTTTTCGGT", 
				bGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTCTTCTTTAGTTCCTTTTCCTTTTCCTTTTCCTTTTCGGT	false	false	false	false	0	999	0	50	0	0	0	0	0	0	false	0	false	50	50	50	50	false	", 
				bGene.toString());

	}
	

}

package gabywald.biosilico.genetics.tests;

import org.junit.jupiter.api.Assertions;
// import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.genetics.BiochemicalReaction;
import gabywald.biosilico.genetics.builders.BiochemicalReactionBuilder;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class BiochemicalReactionTests {

	@Test
	void testConstruction001() {
		BiochemicalReaction brGene = new BiochemicalReaction(	false, false, false, false, 
																0, 999, 0, 50, 
																0, 0, 0, 0, 0, 0, 0, 0, 10);

		Assertions.assertFalse(brGene.canMutate());
		Assertions.assertFalse(brGene.canDuplicate());
		Assertions.assertFalse(brGene.canDelete());
		Assertions.assertFalse(brGene.isActiv());
		Assertions.assertEquals(brGene.getAgeMin(), 0);
		Assertions.assertEquals(brGene.getAgeMax(), 999);
		Assertions.assertEquals(brGene.getSexAct(), 0);
		Assertions.assertEquals(brGene.getMutationRate(), 50);

		Assertions.assertEquals(brGene.getAchem(), 0);
		Assertions.assertEquals(brGene.getAcoef(), 0);
		Assertions.assertEquals(brGene.getBchem(), 0);
		Assertions.assertEquals(brGene.getBcoef(), 0);
		Assertions.assertEquals(brGene.getCchem(), 0);
		Assertions.assertEquals(brGene.getCcoef(), 0);
		Assertions.assertEquals(brGene.getDchem(), 0);
		Assertions.assertEquals(brGene.getDcoef(), 0);
		Assertions.assertEquals(brGene.getKMVMs(), 10);

		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTGGT", 
				brGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTGGT", 
				brGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTGGT	false	false	false	false	0	999	0	50	0	0	0	0	0	0	0	0	10	", 
				brGene.toString());

	}

	@Test
	void testConstruction002() {
		BiochemicalReaction brGene = new BiochemicalReaction(	false, false, false, false, 
																-50, 1050, 0, 50, 
																0, 0, 0, 0, 0, 0, 0, 0, 10);

		Assertions.assertFalse(brGene.canMutate());
		Assertions.assertFalse(brGene.canDuplicate());
		Assertions.assertFalse(brGene.canDelete());
		Assertions.assertFalse(brGene.isActiv());
		Assertions.assertEquals(brGene.getAgeMin(), 0);
		Assertions.assertEquals(brGene.getAgeMax(), 999);
		Assertions.assertEquals(brGene.getSexAct(), 0);
		Assertions.assertEquals(brGene.getMutationRate(), 50);

		Assertions.assertEquals(brGene.getAchem(), 0);
		Assertions.assertEquals(brGene.getAcoef(), 0);
		Assertions.assertEquals(brGene.getBchem(), 0);
		Assertions.assertEquals(brGene.getBcoef(), 0);
		Assertions.assertEquals(brGene.getCchem(), 0);
		Assertions.assertEquals(brGene.getCcoef(), 0);
		Assertions.assertEquals(brGene.getDchem(), 0);
		Assertions.assertEquals(brGene.getDcoef(), 0);
		Assertions.assertEquals(brGene.getKMVMs(), 10);

		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTGGT", 
				brGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTGGT", 
				brGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTGGT	false	false	false	false	0	999	0	50	0	0	0	0	0	0	0	0	10	", 
				brGene.toString());

	}

	@Test
	void testConstruction003() {
		BiochemicalReaction brGene = new BiochemicalReaction(	false, false, false, false, 
																0, 999, -100, -100, 
																0, 0, 0, 0, 0, 0, 0, 0, 10);

		Assertions.assertFalse(brGene.canMutate());
		Assertions.assertFalse(brGene.canDuplicate());
		Assertions.assertFalse(brGene.canDelete());
		Assertions.assertFalse(brGene.isActiv());
		Assertions.assertEquals(brGene.getAgeMin(), 0);
		Assertions.assertEquals(brGene.getAgeMax(), 999);
		Assertions.assertEquals(brGene.getSexAct(), 0);
		Assertions.assertEquals(brGene.getMutationRate(), 0);

		Assertions.assertEquals(brGene.getAchem(), 0);
		Assertions.assertEquals(brGene.getAcoef(), 0);
		Assertions.assertEquals(brGene.getBchem(), 0);
		Assertions.assertEquals(brGene.getBcoef(), 0);
		Assertions.assertEquals(brGene.getCchem(), 0);
		Assertions.assertEquals(brGene.getCcoef(), 0);
		Assertions.assertEquals(brGene.getDchem(), 0);
		Assertions.assertEquals(brGene.getDcoef(), 0);
		Assertions.assertEquals(brGene.getKMVMs(), 10);

		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTCTTCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTGGT", 
				brGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTCTTCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTGGT", 
				brGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTCTTCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTGGT	false	false	false	false	0	999	0	0	0	0	0	0	0	0	0	0	10	", 
				brGene.toString());

	}

	@Test
	void testConstruction004() {
		BiochemicalReaction brGene = new BiochemicalReaction(	false, false, false, false, 
																0, 999, 1050, 106, 
																0, 0, 0, 0, 0, 0, 0, 0, 10);

		Assertions.assertFalse(brGene.canMutate());
		Assertions.assertFalse(brGene.canDuplicate());
		Assertions.assertFalse(brGene.canDelete());
		Assertions.assertFalse(brGene.isActiv());
		Assertions.assertEquals(brGene.getAgeMin(), 0);
		Assertions.assertEquals(brGene.getAgeMax(), 999);
		Assertions.assertEquals(brGene.getSexAct(), 999);
		Assertions.assertEquals(brGene.getMutationRate(), 99);

		Assertions.assertEquals(brGene.getAchem(), 0);
		Assertions.assertEquals(brGene.getAcoef(), 0);
		Assertions.assertEquals(brGene.getBchem(), 0);
		Assertions.assertEquals(brGene.getBcoef(), 0);
		Assertions.assertEquals(brGene.getCchem(), 0);
		Assertions.assertEquals(brGene.getCcoef(), 0);
		Assertions.assertEquals(brGene.getDchem(), 0);
		Assertions.assertEquals(brGene.getDcoef(), 0);
		Assertions.assertEquals(brGene.getKMVMs(), 10);

		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCGCCGCCGCCGCCGCCGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTGGT", 
				brGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCGCCGCCGCCGCCGCCGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTGGT", 
				brGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCGCCGCCGCCGCCGCCGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTGGT	false	false	false	false	0	999	999	99	0	0	0	0	0	0	0	0	10	", 
				brGene.toString());

	}

	@Test
	void testConstruction005() {
		BiochemicalReaction brGene = new BiochemicalReaction(	false, false, false, false, 
																0, 999, 0, 50, 
																0, 0, 0, 0, 0, 0, 0, 0, 10);

		Assertions.assertFalse(brGene.canMutate());
		Assertions.assertFalse(brGene.canDuplicate());
		Assertions.assertFalse(brGene.canDelete());
		Assertions.assertFalse(brGene.isActiv());
		Assertions.assertEquals(brGene.getAgeMin(), 0);
		Assertions.assertEquals(brGene.getAgeMax(), 999);
		Assertions.assertEquals(brGene.getSexAct(), 0);
		Assertions.assertEquals(brGene.getMutationRate(), 50);

		Assertions.assertEquals(brGene.getAchem(), 0);
		Assertions.assertEquals(brGene.getAcoef(), 0);
		Assertions.assertEquals(brGene.getBchem(), 0);
		Assertions.assertEquals(brGene.getBcoef(), 0);
		Assertions.assertEquals(brGene.getCchem(), 0);
		Assertions.assertEquals(brGene.getCcoef(), 0);
		Assertions.assertEquals(brGene.getDchem(), 0);
		Assertions.assertEquals(brGene.getDcoef(), 0);
		Assertions.assertEquals(brGene.getKMVMs(), 10);

		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTGGT", 
				brGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTGGT", 
				brGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTGGT	false	false	false	false	0	999	0	50	0	0	0	0	0	0	0	0	10	", 
				brGene.toString());

	}

	@Test
	void testConstruction006() {
		BiochemicalReaction brGene = new BiochemicalReaction(	false, false, false, false, 
																0, 999, 0, 50, 
																-1, -1, -1, -1, -1, -1, -1, -1, -1);

		Assertions.assertFalse(brGene.canMutate());
		Assertions.assertFalse(brGene.canDuplicate());
		Assertions.assertFalse(brGene.canDelete());
		Assertions.assertFalse(brGene.isActiv());
		Assertions.assertEquals(brGene.getAgeMin(), 0);
		Assertions.assertEquals(brGene.getAgeMax(), 999);
		Assertions.assertEquals(brGene.getSexAct(), 0);
		Assertions.assertEquals(brGene.getMutationRate(), 50);

		Assertions.assertEquals(brGene.getAchem(), 0);
		Assertions.assertEquals(brGene.getAcoef(), 0);
		Assertions.assertEquals(brGene.getBchem(), 0);
		Assertions.assertEquals(brGene.getBcoef(), 0);
		Assertions.assertEquals(brGene.getCchem(), 0);
		Assertions.assertEquals(brGene.getCcoef(), 0);
		Assertions.assertEquals(brGene.getDchem(), 0);
		Assertions.assertEquals(brGene.getDcoef(), 0);
		Assertions.assertEquals(brGene.getKMVMs(), 0);

		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTGGT", 
				brGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTGGT", 
				brGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTGGT	false	false	false	false	0	999	0	50	0	0	0	0	0	0	0	0	0	", 
				brGene.toString());

	}

	@Test
	void testConstruction007() {
		BiochemicalReaction brGene = new BiochemicalReaction(	false, false, false, false, 
																0, 999, 0, 50, 
																1050, 1050, 1050, 1050, 1050, 1050, 1050, 1050, 1050);

		Assertions.assertFalse(brGene.canMutate());
		Assertions.assertFalse(brGene.canDuplicate());
		Assertions.assertFalse(brGene.canDelete());
		Assertions.assertFalse(brGene.isActiv());
		Assertions.assertEquals(brGene.getAgeMin(), 0);
		Assertions.assertEquals(brGene.getAgeMax(), 999);
		Assertions.assertEquals(brGene.getSexAct(), 0);
		Assertions.assertEquals(brGene.getMutationRate(), 50);

		Assertions.assertEquals(brGene.getAchem(), 999);
		Assertions.assertEquals(brGene.getAcoef(), 999);
		Assertions.assertEquals(brGene.getBchem(), 999);
		Assertions.assertEquals(brGene.getBcoef(), 999);
		Assertions.assertEquals(brGene.getCchem(), 999);
		Assertions.assertEquals(brGene.getCcoef(), 999);
		Assertions.assertEquals(brGene.getDchem(), 999);
		Assertions.assertEquals(brGene.getDcoef(), 999);
		Assertions.assertEquals(brGene.getKMVMs(), 999);

		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGGT", 
				brGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGGT", 
				brGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGCCGGT	false	false	false	false	0	999	0	50	999	999	999	999	999	999	999	999	999	", 
				brGene.toString());

	}
	
	@Test
	void testConstruction008() {
		BiochemicalReactionBuilder brb = new BiochemicalReactionBuilder();
		BiochemicalReaction brGene = brb.kmvm(10).agemax(999).mutation(50).build();

		Assertions.assertFalse(brGene.canMutate());
		Assertions.assertFalse(brGene.canDuplicate());
		Assertions.assertFalse(brGene.canDelete());
		Assertions.assertFalse(brGene.isActiv());
		Assertions.assertEquals(brGene.getAgeMin(), 0);
		Assertions.assertEquals(brGene.getAgeMax(), 999);
		Assertions.assertEquals(brGene.getSexAct(), 0);
		Assertions.assertEquals(brGene.getMutationRate(), 50);

		Assertions.assertEquals(brGene.getAchem(), 0);
		Assertions.assertEquals(brGene.getAcoef(), 0);
		Assertions.assertEquals(brGene.getBchem(), 0);
		Assertions.assertEquals(brGene.getBcoef(), 0);
		Assertions.assertEquals(brGene.getCchem(), 0);
		Assertions.assertEquals(brGene.getCcoef(), 0);
		Assertions.assertEquals(brGene.getDchem(), 0);
		Assertions.assertEquals(brGene.getDcoef(), 0);
		Assertions.assertEquals(brGene.getKMVMs(), 10);

		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTGGT", 
				brGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTGGT", 
				brGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCCTTGGT	false	false	false	false	0	999	0	50	0	0	0	0	0	0	0	0	10	", 
				brGene.toString());

	}
	
	@Test
	void testConstruction009() {
		BiochemicalReactionBuilder brb = new BiochemicalReactionBuilder();
		BiochemicalReaction brGene = brb.achem(500).acoef(500)
										.bchem(500).bcoef(500)
										.cchem(500).ccoef(500)
										.dchem(500).dcoef(500)
										.kmvm(10).agemax(999).mutation(50).build();

		Assertions.assertFalse(brGene.canMutate());
		Assertions.assertFalse(brGene.canDuplicate());
		Assertions.assertFalse(brGene.canDelete());
		Assertions.assertFalse(brGene.isActiv());
		Assertions.assertEquals(brGene.getAgeMin(), 0);
		Assertions.assertEquals(brGene.getAgeMax(), 999);
		Assertions.assertEquals(brGene.getSexAct(), 0);
		Assertions.assertEquals(brGene.getMutationRate(), 50);

		Assertions.assertEquals(brGene.getAchem(), 500);
		Assertions.assertEquals(brGene.getAcoef(), 500);
		Assertions.assertEquals(brGene.getBchem(), 500);
		Assertions.assertEquals(brGene.getBcoef(), 500);
		Assertions.assertEquals(brGene.getCchem(), 500);
		Assertions.assertEquals(brGene.getCcoef(), 500);
		Assertions.assertEquals(brGene.getDchem(), 500);
		Assertions.assertEquals(brGene.getDcoef(), 500);
		Assertions.assertEquals(brGene.getKMVMs(), 10);

		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTATTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTCTTCGCCTTGGT", 
				brGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTATTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTCTTCGCCTTGGT", 
				brGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTATTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTCTTCGCCTTGGT	false	false	false	false	0	999	0	50	500	500	500	500	500	500	500	500	10	", 
				brGene.toString());

	}

}

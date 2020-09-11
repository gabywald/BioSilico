package gabywald.biosilico.genetics.tests;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.genetics.Instinct;

class InstinctTests {

	@Test
	void testConstruction001() {
		Instinct iGene = new Instinct(	false, false, false, false, 
										0, 999, 0, 50,
										0, 0, 0, 0, 0, 0, 0, false, true);

		Assertions.assertFalse(iGene.canMutate());
		Assertions.assertFalse(iGene.canDuplicate());
		Assertions.assertFalse(iGene.canDelete());
		Assertions.assertFalse(iGene.isActiv());
		Assertions.assertEquals(iGene.getAgeMin(), 0);
		Assertions.assertEquals(iGene.getAgeMax(), 999);
		Assertions.assertEquals(iGene.getSexAct(), 0);
		Assertions.assertEquals(iGene.getMutationRate(), 50);
		
		Assertions.assertEquals(iGene.getPosXOrg(), 0);
		Assertions.assertEquals(iGene.getPosYOrg(), 0);
		Assertions.assertEquals(iGene.getPosXDes(), 0);
		Assertions.assertEquals(iGene.getPosYDes(), 0);
		Assertions.assertEquals(iGene.getWeight(), 0);
		Assertions.assertEquals(iGene.getVariable(), 0);
		Assertions.assertEquals(iGene.getThreshold(), 0);
		Assertions.assertFalse(iGene.getCheck());
		Assertions.assertTrue(iGene.isPositive());
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCTAAGGT", 
				iGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCTAAGGT", 
				iGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCTAAGGT	false	false	false	false	0	999	0	50	0	0	0	0	0	0	0	false	true	", 
				iGene.toString());
	}
	
	@Test
	void testConstruction002() {
		Instinct iGene = new Instinct(	false, false, false, false, 
										0, 999, 0, 50,
										99, 99, 99, 99, 0, 0, 0, false, true);

		Assertions.assertFalse(iGene.canMutate());
		Assertions.assertFalse(iGene.canDuplicate());
		Assertions.assertFalse(iGene.canDelete());
		Assertions.assertFalse(iGene.isActiv());
		Assertions.assertEquals(iGene.getAgeMin(), 0);
		Assertions.assertEquals(iGene.getAgeMax(), 999);
		Assertions.assertEquals(iGene.getSexAct(), 0);
		Assertions.assertEquals(iGene.getMutationRate(), 50);
		
		Assertions.assertEquals(iGene.getPosXOrg(), 99);
		Assertions.assertEquals(iGene.getPosYOrg(), 99);
		Assertions.assertEquals(iGene.getPosXDes(), 99);
		Assertions.assertEquals(iGene.getPosYDes(), 99);
		Assertions.assertEquals(iGene.getWeight(), 0);
		Assertions.assertEquals(iGene.getVariable(), 0);
		Assertions.assertEquals(iGene.getThreshold(), 0);
		Assertions.assertFalse(iGene.getCheck());
		Assertions.assertTrue(iGene.isPositive());
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGCCGCCCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCTAAGGT", 
				iGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGCCGCCCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCTAAGGT", 
				iGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGCCGCCCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCTAAGGT	false	false	false	false	0	999	0	50	99	99	99	99	0	0	0	false	true	", 
				iGene.toString());
	}
	
	@Test
	void testConstruction003() {
		Instinct iGene = new Instinct(	false, false, false, false, 
										0, 999, 0, 50,
										106, 106, 106, 106, 0, 0, 0, false, true);

		Assertions.assertFalse(iGene.canMutate());
		Assertions.assertFalse(iGene.canDuplicate());
		Assertions.assertFalse(iGene.canDelete());
		Assertions.assertFalse(iGene.isActiv());
		Assertions.assertEquals(iGene.getAgeMin(), 0);
		Assertions.assertEquals(iGene.getAgeMax(), 999);
		Assertions.assertEquals(iGene.getSexAct(), 0);
		Assertions.assertEquals(iGene.getMutationRate(), 50);
		
		Assertions.assertEquals(iGene.getPosXOrg(), 99);
		Assertions.assertEquals(iGene.getPosYOrg(), 99);
		Assertions.assertEquals(iGene.getPosXDes(), 99);
		Assertions.assertEquals(iGene.getPosYDes(), 99);
		Assertions.assertEquals(iGene.getWeight(), 0);
		Assertions.assertEquals(iGene.getVariable(), 0);
		Assertions.assertEquals(iGene.getThreshold(), 0);
		Assertions.assertFalse(iGene.getCheck());
		Assertions.assertTrue(iGene.isPositive());
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGCCGCCCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCTAAGGT", 
				iGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGCCGCCCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCTAAGGT", 
				iGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGCCGCCCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCTAAGGT	false	false	false	false	0	999	0	50	99	99	99	99	0	0	0	false	true	", 
				iGene.toString());
	}
	
	@Test
	void testConstruction004() {
		Instinct iGene = new Instinct(	false, false, false, false, 
										0, 999, 0, 50,
										-1, -1, -1, -1, -1, -1, -1, false, true);

		Assertions.assertFalse(iGene.canMutate());
		Assertions.assertFalse(iGene.canDuplicate());
		Assertions.assertFalse(iGene.canDelete());
		Assertions.assertFalse(iGene.isActiv());
		Assertions.assertEquals(iGene.getAgeMin(), 0);
		Assertions.assertEquals(iGene.getAgeMax(), 999);
		Assertions.assertEquals(iGene.getSexAct(), 0);
		Assertions.assertEquals(iGene.getMutationRate(), 50);
		
		Assertions.assertEquals(iGene.getPosXOrg(), 0);
		Assertions.assertEquals(iGene.getPosYOrg(), 0);
		Assertions.assertEquals(iGene.getPosXDes(), 0);
		Assertions.assertEquals(iGene.getPosYDes(), 0);
		Assertions.assertEquals(iGene.getWeight(), 0);
		Assertions.assertEquals(iGene.getVariable(), 0);
		Assertions.assertEquals(iGene.getThreshold(), 0);
		Assertions.assertFalse(iGene.getCheck());
		Assertions.assertTrue(iGene.isPositive());
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCTAAGGT", 
				iGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCTAAGGT", 
				iGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCTAAGGT	false	false	false	false	0	999	0	50	0	0	0	0	0	0	0	false	true	", 
				iGene.toString());
	}
	
	@Test
	void testConstruction005() {
		Instinct iGene = new Instinct(	false, false, false, false, 
										0, 999, 0, 50,
										0, 0, 0, 0, 999, 999, 999, false, true);

		Assertions.assertFalse(iGene.canMutate());
		Assertions.assertFalse(iGene.canDuplicate());
		Assertions.assertFalse(iGene.canDelete());
		Assertions.assertFalse(iGene.isActiv());
		Assertions.assertEquals(iGene.getAgeMin(), 0);
		Assertions.assertEquals(iGene.getAgeMax(), 999);
		Assertions.assertEquals(iGene.getSexAct(), 0);
		Assertions.assertEquals(iGene.getMutationRate(), 50);
		
		Assertions.assertEquals(iGene.getPosXOrg(), 0);
		Assertions.assertEquals(iGene.getPosYOrg(), 0);
		Assertions.assertEquals(iGene.getPosXDes(), 0);
		Assertions.assertEquals(iGene.getPosYDes(), 0);
		Assertions.assertEquals(iGene.getWeight(), 999);
		Assertions.assertEquals(iGene.getVariable(), 999);
		Assertions.assertEquals(iGene.getThreshold(), 999);
		Assertions.assertFalse(iGene.getCheck());
		Assertions.assertTrue(iGene.isPositive());
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTGCCGCCGCCGCCGCCGCCGCCGCCGCCCGCTAAGGT", 
				iGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTGCCGCCGCCGCCGCCGCCGCCGCCGCCCGCTAAGGT", 
				iGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTGCCGCCGCCGCCGCCGCCGCCGCCGCCCGCTAAGGT	false	false	false	false	0	999	0	50	0	0	0	0	999	999	999	false	true	", 
				iGene.toString());
	}
	
	@Test
	void testConstruction006() {
		Instinct iGene = new Instinct(	false, false, false, false, 
										0, 999, 0, 50,
										0, 0, 0, 0, 1050, 1050, 1050, false, true);

		Assertions.assertFalse(iGene.canMutate());
		Assertions.assertFalse(iGene.canDuplicate());
		Assertions.assertFalse(iGene.canDelete());
		Assertions.assertFalse(iGene.isActiv());
		Assertions.assertEquals(iGene.getAgeMin(), 0);
		Assertions.assertEquals(iGene.getAgeMax(), 999);
		Assertions.assertEquals(iGene.getSexAct(), 0);
		Assertions.assertEquals(iGene.getMutationRate(), 50);
		
		Assertions.assertEquals(iGene.getPosXOrg(), 0);
		Assertions.assertEquals(iGene.getPosYOrg(), 0);
		Assertions.assertEquals(iGene.getPosXDes(), 0);
		Assertions.assertEquals(iGene.getPosYDes(), 0);
		Assertions.assertEquals(iGene.getWeight(), 999);
		Assertions.assertEquals(iGene.getVariable(), 999);
		Assertions.assertEquals(iGene.getThreshold(), 999);
		Assertions.assertFalse(iGene.getCheck());
		Assertions.assertTrue(iGene.isPositive());
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTGCCGCCGCCGCCGCCGCCGCCGCCGCCCGCTAAGGT", 
				iGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTGCCGCCGCCGCCGCCGCCGCCGCCGCCCGCTAAGGT", 
				iGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTGCCGCCGCCGCCGCCGCCGCCGCCGCCCGCTAAGGT	false	false	false	false	0	999	0	50	0	0	0	0	999	999	999	false	true	", 
				iGene.toString());
	}
	
	@Test
	void testConstruction007() {
		Instinct iGene = new Instinct(	false, false, false, false, 
										0, 999, 0, 50,
										0, 0, 0, 0, 0, 0, 0, true, true);

		Assertions.assertFalse(iGene.canMutate());
		Assertions.assertFalse(iGene.canDuplicate());
		Assertions.assertFalse(iGene.canDelete());
		Assertions.assertFalse(iGene.isActiv());
		Assertions.assertEquals(iGene.getAgeMin(), 0);
		Assertions.assertEquals(iGene.getAgeMax(), 999);
		Assertions.assertEquals(iGene.getSexAct(), 0);
		Assertions.assertEquals(iGene.getMutationRate(), 50);
		
		Assertions.assertEquals(iGene.getPosXOrg(), 0);
		Assertions.assertEquals(iGene.getPosYOrg(), 0);
		Assertions.assertEquals(iGene.getPosXDes(), 0);
		Assertions.assertEquals(iGene.getPosYDes(), 0);
		Assertions.assertEquals(iGene.getWeight(), 0);
		Assertions.assertEquals(iGene.getVariable(), 0);
		Assertions.assertEquals(iGene.getThreshold(), 0);
		Assertions.assertTrue(iGene.getCheck());
		Assertions.assertTrue(iGene.isPositive());
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTTAAGGT", 
				iGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTTAAGGT", 
				iGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTTAAGGT	false	false	false	false	0	999	0	50	0	0	0	0	0	0	0	true	true	", 
				iGene.toString());
	}
	
	@Test
	void testConstruction008() {
		Instinct iGene = new Instinct(	false, false, false, false, 
										0, 999, 0, 50,
										0, 0, 0, 0, 500, 500, 500, false, true);

		Assertions.assertFalse(iGene.canMutate());
		Assertions.assertFalse(iGene.canDuplicate());
		Assertions.assertFalse(iGene.canDelete());
		Assertions.assertFalse(iGene.isActiv());
		Assertions.assertEquals(iGene.getAgeMin(), 0);
		Assertions.assertEquals(iGene.getAgeMax(), 999);
		Assertions.assertEquals(iGene.getSexAct(), 0);
		Assertions.assertEquals(iGene.getMutationRate(), 50);
		
		Assertions.assertEquals(iGene.getPosXOrg(), 0);
		Assertions.assertEquals(iGene.getPosYOrg(), 0);
		Assertions.assertEquals(iGene.getPosXDes(), 0);
		Assertions.assertEquals(iGene.getPosYDes(), 0);
		Assertions.assertEquals(iGene.getWeight(), 500);
		Assertions.assertEquals(iGene.getVariable(), 500);
		Assertions.assertEquals(iGene.getThreshold(), 500);
		Assertions.assertFalse(iGene.getCheck());
		Assertions.assertTrue(iGene.isPositive());
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTCGCTAAGGT", 
				iGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTCGCTAAGGT", 
				iGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTCGCTAAGGT	false	false	false	false	0	999	0	50	0	0	0	0	500	500	500	false	true	", 
				iGene.toString());
	}
	
	@Test
	void testConstruction009() {
		Instinct iGene = new Instinct(	false, false, false, false, 
										0, 999, 0, 50,
										50, 50, 50, 50, 0, 0, 0, false, true);

		Assertions.assertFalse(iGene.canMutate());
		Assertions.assertFalse(iGene.canDuplicate());
		Assertions.assertFalse(iGene.canDelete());
		Assertions.assertFalse(iGene.isActiv());
		Assertions.assertEquals(iGene.getAgeMin(), 0);
		Assertions.assertEquals(iGene.getAgeMax(), 999);
		Assertions.assertEquals(iGene.getSexAct(), 0);
		Assertions.assertEquals(iGene.getMutationRate(), 50);
		
		Assertions.assertEquals(iGene.getPosXOrg(), 50);
		Assertions.assertEquals(iGene.getPosYOrg(), 50);
		Assertions.assertEquals(iGene.getPosXDes(), 50);
		Assertions.assertEquals(iGene.getPosYDes(), 50);
		Assertions.assertEquals(iGene.getWeight(), 0);
		Assertions.assertEquals(iGene.getVariable(), 0);
		Assertions.assertEquals(iGene.getThreshold(), 0);
		Assertions.assertFalse(iGene.getCheck());
		Assertions.assertTrue(iGene.isPositive());
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTATTCCTTTTCCTTTTCCTTTTCCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCTAAGGT", 
				iGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTATTCCTTTTCCTTTTCCTTTTCCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCTAAGGT", 
				iGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTATTCCTTTTCCTTTTCCTTTTCCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCTAAGGT	false	false	false	false	0	999	0	50	50	50	50	50	0	0	0	false	true	", 
				iGene.toString());
	}
	
	@Test
	void testConstruction010() {
		Instinct iGene = new Instinct(	false, false, false, false, 
										0, 999, 0, 50,
										50, 50, 50, 50, 0, 0, 0, false, false);

		Assertions.assertFalse(iGene.canMutate());
		Assertions.assertFalse(iGene.canDuplicate());
		Assertions.assertFalse(iGene.canDelete());
		Assertions.assertFalse(iGene.isActiv());
		Assertions.assertEquals(iGene.getAgeMin(), 0);
		Assertions.assertEquals(iGene.getAgeMax(), 999);
		Assertions.assertEquals(iGene.getSexAct(), 0);
		Assertions.assertEquals(iGene.getMutationRate(), 50);
		
		Assertions.assertEquals(iGene.getPosXOrg(), 50);
		Assertions.assertEquals(iGene.getPosYOrg(), 50);
		Assertions.assertEquals(iGene.getPosXDes(), 50);
		Assertions.assertEquals(iGene.getPosYDes(), 50);
		Assertions.assertEquals(iGene.getWeight(), 0);
		Assertions.assertEquals(iGene.getVariable(), 0);
		Assertions.assertEquals(iGene.getThreshold(), 0);
		Assertions.assertFalse(iGene.getCheck());
		Assertions.assertFalse(iGene.isPositive());
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTATTCCTTTTCCTTTTCCTTTTCCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCTAGGGT", 
				iGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTATTCCTTTTCCTTTTCCTTTTCCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCTAGGGT", 
				iGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTATTCCTTTTCCTTTTCCTTTTCCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCTAGGGT	false	false	false	false	0	999	0	50	50	50	50	50	0	0	0	false	false	", 
				iGene.toString());
	}

}

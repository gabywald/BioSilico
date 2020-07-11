package gabywald.biosilico.genetics.tests;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.genetics.Instinct;

class InstinctTests {

	@Test
	void testConstruction001() {
		Instinct iGene = new Instinct(	false, false, false, false, 
										0, 999, 0, 50,
										0, 0, 0, 0, 0, 0, 0, false);

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
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCGGT", 
				iGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCGGT", 
				iGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCGGT	false	false	false	false	0	999	0	50	0	0	0	0	0	0	0	false	", 
				iGene.toString());
	}
	
	@Test
	void testConstruction002() {
		Instinct iGene = new Instinct(	false, false, false, false, 
										0, 999, 0, 50,
										99, 99, 99, 99, 0, 0, 0, false);

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
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGCCGCCCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCGGT", 
				iGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGCCGCCCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCGGT", 
				iGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGCCGCCCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCGGT	false	false	false	false	0	999	0	50	99	99	99	99	0	0	0	false	", 
				iGene.toString());
	}
	
	@Test
	void testConstruction003() {
		Instinct iGene = new Instinct(	false, false, false, false, 
										0, 999, 0, 50,
										106, 106, 106, 106, 0, 0, 0, false);

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
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGCCGCCCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCGGT", 
				iGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGCCGCCCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCGGT", 
				iGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGCCGCCCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCGGT	false	false	false	false	0	999	0	50	99	99	99	99	0	0	0	false	", 
				iGene.toString());
	}
	
	@Test
	void testConstruction004() {
		Instinct iGene = new Instinct(	false, false, false, false, 
										0, 999, 0, 50,
										-1, -1, -1, -1, -1, -1, -1, false);

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
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCGGT", 
				iGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCGGT", 
				iGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCGGT	false	false	false	false	0	999	0	50	0	0	0	0	0	0	0	false	", 
				iGene.toString());
	}
	
	@Test
	void testConstruction005() {
		Instinct iGene = new Instinct(	false, false, false, false, 
										0, 999, 0, 50,
										0, 0, 0, 0, 999, 999, 999, false);

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
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTGCCGCCGCCGCCGCCGCCGCCGCCGCCCGCGGT", 
				iGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTGCCGCCGCCGCCGCCGCCGCCGCCGCCCGCGGT", 
				iGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTGCCGCCGCCGCCGCCGCCGCCGCCGCCCGCGGT	false	false	false	false	0	999	0	50	0	0	0	0	999	999	999	false	", 
				iGene.toString());
	}
	
	@Test
	void testConstruction006() {
		Instinct iGene = new Instinct(	false, false, false, false, 
										0, 999, 0, 50,
										0, 0, 0, 0, 1050, 1050, 1050, false);

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
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTGCCGCCGCCGCCGCCGCCGCCGCCGCCCGCGGT", 
				iGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTGCCGCCGCCGCCGCCGCCGCCGCCGCCCGCGGT", 
				iGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTGCCGCCGCCGCCGCCGCCGCCGCCGCCCGCGGT	false	false	false	false	0	999	0	50	0	0	0	0	999	999	999	false	", 
				iGene.toString());
	}
	
	@Test
	void testConstruction007() {
		Instinct iGene = new Instinct(	false, false, false, false, 
										0, 999, 0, 50,
										0, 0, 0, 0, 0, 0, 0, true);

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
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTGGT", 
				iGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTGGT", 
				iGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTGGT	false	false	false	false	0	999	0	50	0	0	0	0	0	0	0	true	", 
				iGene.toString());
	}
	
	@Test
	void testConstruction008() {
		Instinct iGene = new Instinct(	false, false, false, false, 
										0, 999, 0, 50,
										0, 0, 0, 0, 500, 500, 500, false);

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
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTCGCGGT", 
				iGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTCGCGGT", 
				iGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTCTTCTTTTCCTTCTTTTCCTTCTTTTCCTTCTTCGCGGT	false	false	false	false	0	999	0	50	0	0	0	0	500	500	500	false	", 
				iGene.toString());
	}
	
	@Test
	void testConstruction009() {
		Instinct iGene = new Instinct(	false, false, false, false, 
										0, 999, 0, 50,
										50, 50, 50, 50, 0, 0, 0, false);

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
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTATTCCTTTTCCTTTTCCTTTTCCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCGGT", 
				iGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTATTCCTTTTCCTTTTCCTTTTCCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCGGT", 
				iGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTATTCCTTTTCCTTTTCCTTTTCCTTCTTCTTCTTCTTCTTCTTCTTCTTCTTCGCGGT	false	false	false	false	0	999	0	50	50	50	50	50	0	0	0	false	", 
				iGene.toString());
	}

}

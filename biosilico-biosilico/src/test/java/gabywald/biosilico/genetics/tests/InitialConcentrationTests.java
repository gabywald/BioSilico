package gabywald.biosilico.genetics.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.genetics.Gene;
import gabywald.biosilico.genetics.InitialConcentration;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class InitialConcentrationTests {

	@Test
	void testConstruction001() {
		InitialConcentration icGene = new InitialConcentration(	false, false, false, false, 
																0, 999, 0, 50,
																0, 0);

		Assertions.assertFalse(icGene.canMutate());
		Assertions.assertFalse(icGene.canDuplicate());
		Assertions.assertFalse(icGene.canDelete());
		Assertions.assertFalse(icGene.isActiv());
		Assertions.assertEquals(icGene.getAgeMin(), 0);
		Assertions.assertEquals(icGene.getAgeMax(), 999);
		Assertions.assertEquals(icGene.getSexAct(), 0);
		Assertions.assertEquals(icGene.getMutationRate(), 50);
		
		Assertions.assertEquals(icGene.getVariable(), 0);
		Assertions.assertEquals(icGene.getValue(), 0);
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTGGT", 
				icGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTGGT", 
				icGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTGGT	false	false	false	false	0	999	0	50	0	0	", 
				icGene.toString());
	}
	
	@Test
	void testConstruction002() {
		InitialConcentration icGene = new InitialConcentration(	false, false, false, false, 
																0, 999, 0, 50,
																-1, -1);

		Assertions.assertFalse(icGene.canMutate());
		Assertions.assertFalse(icGene.canDuplicate());
		Assertions.assertFalse(icGene.canDelete());
		Assertions.assertFalse(icGene.isActiv());
		Assertions.assertEquals(icGene.getAgeMin(), 0);
		Assertions.assertEquals(icGene.getAgeMax(), 999);
		Assertions.assertEquals(icGene.getSexAct(), 0);
		Assertions.assertEquals(icGene.getMutationRate(), 50);
		
		Assertions.assertEquals(icGene.getVariable(), 0);
		Assertions.assertEquals(icGene.getValue(), 0);
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTGGT", 
				icGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTGGT", 
				icGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCTTCTTCTTCTTCTTGGT	false	false	false	false	0	999	0	50	0	0	", 
				icGene.toString());
	}
	
	@Test
	void testConstruction003() {
		InitialConcentration icGene = new InitialConcentration(	false, false, false, false, 
																0, 999, 0, 50,
																999, 999);

		Assertions.assertFalse(icGene.canMutate());
		Assertions.assertFalse(icGene.canDuplicate());
		Assertions.assertFalse(icGene.canDelete());
		Assertions.assertFalse(icGene.isActiv());
		Assertions.assertEquals(icGene.getAgeMin(), 0);
		Assertions.assertEquals(icGene.getAgeMax(), 999);
		Assertions.assertEquals(icGene.getSexAct(), 0);
		Assertions.assertEquals(icGene.getMutationRate(), 50);
		
		Assertions.assertEquals(icGene.getVariable(), 999);
		Assertions.assertEquals(icGene.getValue(), 999);
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGGT", 
				icGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGGT", 
				icGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGGT	false	false	false	false	0	999	0	50	999	999	", 
				icGene.toString());
	}
	
	@Test
	void testConstruction004() {
		InitialConcentration icGene = new InitialConcentration(	false, false, false, false, 
																0, 999, 0, 50,
																1050, 1050);

		Assertions.assertFalse(icGene.canMutate());
		Assertions.assertFalse(icGene.canDuplicate());
		Assertions.assertFalse(icGene.canDelete());
		Assertions.assertFalse(icGene.isActiv());
		Assertions.assertEquals(icGene.getAgeMin(), 0);
		Assertions.assertEquals(icGene.getAgeMax(), 999);
		Assertions.assertEquals(icGene.getSexAct(), 0);
		Assertions.assertEquals(icGene.getMutationRate(), 50);
		
		Assertions.assertEquals(icGene.getVariable(), 999);
		Assertions.assertEquals(icGene.getValue(), 999);
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGGT", 
				icGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGGT", 
				icGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTAGCCGCCGCCGCCGCCGCCGGT	false	false	false	false	0	999	0	50	999	999	", 
				icGene.toString());
	}
	
	@Test
	void testConstruction005() {
		InitialConcentration icGene = new InitialConcentration(	false, false, false, false, 
																0, 999, 0, 50,
																500, 500);

		Assertions.assertFalse(icGene.canMutate());
		Assertions.assertFalse(icGene.canDuplicate());
		Assertions.assertFalse(icGene.canDelete());
		Assertions.assertFalse(icGene.isActiv());
		Assertions.assertEquals(icGene.getAgeMin(), 0);
		Assertions.assertEquals(icGene.getAgeMax(), 999);
		Assertions.assertEquals(icGene.getSexAct(), 0);
		Assertions.assertEquals(icGene.getMutationRate(), 50);
		
		Assertions.assertEquals(icGene.getVariable(), 500);
		Assertions.assertEquals(icGene.getValue(), 500);
		
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTATTCCTTCTTTTCCTTCTTGGT", 
				icGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTATTCCTTCTTTTCCTTCTTGGT", 
				icGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTATTCCTTCTTTTCCTTCTTGGT	false	false	false	false	0	999	0	50	500	500	", 
				icGene.toString());
	}
	
	@Test
	void testCloneEquals() {
		InitialConcentration icGene = new InitialConcentration(	false, false, false, false, 
																0, 999, 0, 50,
																0, 0);

		Gene icGeneClone = icGene.clone();
		Assertions.assertNotNull( icGeneClone );
		InitialConcentration bicGeneClone = (InitialConcentration) icGeneClone;
		Assertions.assertNotNull( bicGeneClone );
		
		Assertions.assertTrue( icGene.equals( icGeneClone ) );
		Assertions.assertTrue( icGene.equals( bicGeneClone ) );
		
		InitialConcentration icGeneOther1 = new InitialConcentration(	false, false, false, false, 
																		0, 999, 0, 50,
																		0, 1);

		Assertions.assertFalse( icGene.equals( icGeneOther1 ) );
		
		InitialConcentration icGeneOther2 = new InitialConcentration(	false, false, false, false, 
																		0, 999, 0, 50,
																		1, 0);
		
		Assertions.assertFalse( icGene.equals( icGeneOther2 ) );
	}

}

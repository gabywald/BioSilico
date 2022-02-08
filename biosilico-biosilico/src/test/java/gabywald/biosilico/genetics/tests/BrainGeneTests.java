package gabywald.biosilico.genetics.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.genetics.BrainGene;
import gabywald.biosilico.genetics.Gene;
import gabywald.biosilico.genetics.builders.BrainGeneBuilder;
import gabywald.biosilico.model.Brain;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class BrainGeneTests {

	@Test
	void testConstruction001() {
		BrainGene bGene = new BrainGene(	false, false, false, false, 
											0, 999, 0, 50,
											1, 1, 1, 0);

		Assertions.assertFalse(bGene.canMutate());
		Assertions.assertFalse(bGene.canDuplicate());
		Assertions.assertFalse(bGene.canDelete());
		Assertions.assertFalse(bGene.isActiv());
		Assertions.assertEquals(bGene.getAgeMin(), 0);
		Assertions.assertEquals(bGene.getAgeMax(), 999);
		Assertions.assertEquals(bGene.getSexAct(), 0);
		Assertions.assertEquals(bGene.getMutationRate(), 50);

		Assertions.assertEquals(bGene.getBrainHeight(), 1);
		Assertions.assertEquals(bGene.getBrainWidth(), 1);
		Assertions.assertEquals(bGene.getBrainDepth(), 1);
		Assertions.assertEquals(bGene.getBrainMore(), 0);

		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCGCCTTCGCCTTCGCCTTCTTGGT", 
				bGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCGCCTTCGCCTTCGCCTTCTTGGT", 
				bGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACTTCGCCTTCGCCTTCGCCTTCTTGGT	false	false	false	false	0	999	0	50	1	1	1	0	", 
				bGene.toString());

	}

	@Test
	void testConstruction002() {
		BrainGene bGene = new BrainGene(	false, false, false, false, 
				0, 999, 0, 50,
				0, 0, 0, 0);

		Assertions.assertFalse(bGene.canMutate());
		Assertions.assertFalse(bGene.canDuplicate());
		Assertions.assertFalse(bGene.canDelete());
		Assertions.assertFalse(bGene.isActiv());
		Assertions.assertEquals(bGene.getAgeMin(), 0);
		Assertions.assertEquals(bGene.getAgeMax(), 999);
		Assertions.assertEquals(bGene.getSexAct(), 0);
		Assertions.assertEquals(bGene.getMutationRate(), 50);

		Assertions.assertEquals(bGene.getBrainHeight(), Brain.MAX_HEIGHT);
		Assertions.assertEquals(bGene.getBrainWidth(), Brain.MAX_WIDTH);
		Assertions.assertEquals(bGene.getBrainDepth(), Brain.MAX_DEPTH);
		Assertions.assertEquals(bGene.getBrainMore(), 0);

		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACGCCTTCTTCGCCTTCTTCGCCTTCTTCTTCTTGGT", 
				bGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACGCCTTCTTCGCCTTCTTCGCCTTCTTCTTCTTGGT", 
				bGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACGCCTTCTTCGCCTTCTTCGCCTTCTTCTTCTTGGT	false	false	false	false	0	999	0	50	100	100	100	0	", 
				bGene.toString());

	}

	@Test
	void testConstruction003() {
		BrainGene bGene = new BrainGene(	false, false, false, false, 
				0, 999, 0, 50,
				Brain.MAX_HEIGHT, Brain.MAX_WIDTH, Brain.MAX_DEPTH, 0);

		Assertions.assertFalse(bGene.canMutate());
		Assertions.assertFalse(bGene.canDuplicate());
		Assertions.assertFalse(bGene.canDelete());
		Assertions.assertFalse(bGene.isActiv());
		Assertions.assertEquals(bGene.getAgeMin(), 0);
		Assertions.assertEquals(bGene.getAgeMax(), 999);
		Assertions.assertEquals(bGene.getSexAct(), 0);
		Assertions.assertEquals(bGene.getMutationRate(), 50);

		Assertions.assertEquals(bGene.getBrainHeight(), Brain.MAX_HEIGHT);
		Assertions.assertEquals(bGene.getBrainWidth(), Brain.MAX_WIDTH);
		Assertions.assertEquals(bGene.getBrainDepth(), Brain.MAX_DEPTH);
		Assertions.assertEquals(bGene.getBrainMore(), 0);

		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACGCCTTCTTCGCCTTCTTCGCCTTCTTCTTCTTGGT", 
				bGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACGCCTTCTTCGCCTTCTTCGCCTTCTTCTTCTTGGT", 
				bGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACGCCTTCTTCGCCTTCTTCGCCTTCTTCTTCTTGGT	false	false	false	false	0	999	0	50	100	100	100	0	", 
				bGene.toString());

	}

	@Test
	void testConstruction004() {
		BrainGene bGene = new BrainGene(	false, false, false, false, 
				0, 999, 0, 50,
				Brain.MAX_HEIGHT / 2, Brain.MAX_WIDTH /2, Brain.MAX_DEPTH / 2, 0);

		Assertions.assertFalse(bGene.canMutate());
		Assertions.assertFalse(bGene.canDuplicate());
		Assertions.assertFalse(bGene.canDelete());
		Assertions.assertFalse(bGene.isActiv());
		Assertions.assertEquals(bGene.getAgeMin(), 0);
		Assertions.assertEquals(bGene.getAgeMax(), 999);
		Assertions.assertEquals(bGene.getSexAct(), 0);
		Assertions.assertEquals(bGene.getMutationRate(), 50);

		Assertions.assertEquals(bGene.getBrainHeight(), Brain.MAX_HEIGHT / 2);
		Assertions.assertEquals(bGene.getBrainWidth(), Brain.MAX_WIDTH / 2);
		Assertions.assertEquals(bGene.getBrainDepth(), Brain.MAX_DEPTH / 2);
		Assertions.assertEquals(bGene.getBrainMore(), 0);

		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTATTCCTTTTCCTTTTCCTTCTTCTTGGT", 
				bGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTATTCCTTTTCCTTTTCCTTCTTCTTGGT", 
				bGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTATTCCTTTTCCTTTTCCTTCTTCTTGGT	false	false	false	false	0	999	0	50	50	50	50	0	", 
				bGene.toString());

	}

	@Test
	void testConstruction005() {
		BrainGene bGene = new BrainGene(	false, false, false, false, 
				0, 999, 0, 50,
				Brain.MAX_HEIGHT, Brain.MAX_WIDTH, Brain.MAX_DEPTH, 0);

		Assertions.assertFalse(bGene.canMutate());
		Assertions.assertFalse(bGene.canDuplicate());
		Assertions.assertFalse(bGene.canDelete());
		Assertions.assertFalse(bGene.isActiv());
		Assertions.assertEquals(bGene.getAgeMin(), 0);
		Assertions.assertEquals(bGene.getAgeMax(), 999);
		Assertions.assertEquals(bGene.getSexAct(), 0);
		Assertions.assertEquals(bGene.getMutationRate(), 50);

		Assertions.assertEquals(bGene.getBrainHeight(), Brain.MAX_HEIGHT);
		Assertions.assertEquals(bGene.getBrainWidth(), Brain.MAX_WIDTH);
		Assertions.assertEquals(bGene.getBrainDepth(), Brain.MAX_DEPTH);
		Assertions.assertEquals(bGene.getBrainMore(), 0);

		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACGCCTTCTTCGCCTTCTTCGCCTTCTTCTTCTTGGT", 
				bGene.reverseTranslation(false));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACGCCTTCTTCGCCTTCTTCGCCTTCTTCTTCTTGGT", 
				bGene.reverseTranslation(true));
		Assertions.assertEquals("GGACGCTAGTTCTGGCTTCTTCTTGCCGCCGCCCTTCTTCTTTTCCTTGTACGCCTTCTTCGCCTTCTTCGCCTTCTTCTTCTTGGT	false	false	false	false	0	999	0	50	100	100	100	0	", 
				bGene.toString());

	}
	
	@Test
	void testConstruction006() {
		BrainGeneBuilder bgb	= new BrainGeneBuilder();
		BrainGene bGene			= bgb.heigth(100).width(100).depth(1).activ(true).agemax(0).mutation(50).build();
		
		Assertions.assertFalse(bGene.canMutate());
		Assertions.assertFalse(bGene.canDuplicate());
		Assertions.assertFalse(bGene.canDelete());
		Assertions.assertTrue(bGene.isActiv());
		Assertions.assertEquals(bGene.getAgeMin(), 0);
		Assertions.assertEquals(bGene.getAgeMax(), 0);
		Assertions.assertEquals(bGene.getSexAct(), 0);
		Assertions.assertEquals(bGene.getMutationRate(), 50);

		Assertions.assertEquals(bGene.getBrainHeight(), 100);
		Assertions.assertEquals(bGene.getBrainWidth(), 100);
		Assertions.assertEquals(bGene.getBrainDepth(), 1);
		Assertions.assertEquals(bGene.getBrainMore(), 0);
		
	}

	@Test
	void testCloneEquals() {
		BrainGene bGene = new BrainGene(	false, false, false, false, 
											0, 999, 0, 50,
											1, 1, 1, 0);

		Gene bGeneClone = bGene.clone();
		Assertions.assertNotNull( bGeneClone );
		BrainGene bbGeneClone = (BrainGene) bGeneClone;
		Assertions.assertNotNull( bbGeneClone );
		
		Assertions.assertTrue( bGene.equals( bGeneClone ) );
		Assertions.assertTrue( bGene.equals( bbGeneClone ) );
		
		BrainGene bGeneOther = new BrainGene(	false, false, false, false, 
												0, 999, 0, 50,
												1, 1, 1, 1);
		
		Assertions.assertFalse( bGene.equals( bGeneOther ) );
	}

}

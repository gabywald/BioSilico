package gabywald.biosilico.structures.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.genetics.BiochemicalReaction;
import gabywald.biosilico.genetics.BrainGene;
import gabywald.biosilico.genetics.BrainLobeGene;
import gabywald.biosilico.genetics.EmitterReceptor;
import gabywald.biosilico.genetics.InitialConcentration;
import gabywald.biosilico.genetics.Instinct;
import gabywald.biosilico.genetics.StimulusDecision;
import gabywald.biosilico.genetics.builders.BiochemicalReactionBuilder;
import gabywald.biosilico.genetics.builders.BrainGeneBuilder;
import gabywald.biosilico.genetics.builders.BrainLobeGeneBuilder;
import gabywald.biosilico.genetics.builders.EmitterReceptorBuilder;
import gabywald.biosilico.genetics.builders.InitialConcentrationBuilder;
import gabywald.biosilico.genetics.builders.InstinctBuilder;
import gabywald.biosilico.genetics.builders.StimulusDecisionBuilder;
import gabywald.biosilico.structures.GeneMoreListe;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class GeneMoreListTests {

	@Test
	void testGeneMoreListe() {
		GeneMoreListe gml = new GeneMoreListe();
		Assertions.assertNotNull( gml );
		Assertions.assertEquals(0, gml.length());
	}

	@Test
	void testGeneMoreListeStringBoolean() {
		GeneMoreListe gml = new GeneMoreListe("initialGenes.txt", false);
		Assertions.assertNotNull( gml );
		Assertions.assertEquals(0, gml.length());
		
		// gml.readFile();
		// gml.printFile();
		
		GeneMoreListe gmlBIS = new GeneMoreListe("initialGenes.txt", true);
		Assertions.assertNotNull( gmlBIS );
		Assertions.assertEquals(0, gmlBIS.length());
		
		// gmlBIS.readFile();
		// gmlBIS.printFile();
	}

	@Test
	void testAddGenLength() {

		GeneMoreListe gml					= new GeneMoreListe();
		
		BiochemicalReactionBuilder bgmlb	= new BiochemicalReactionBuilder();
		BrainGeneBuilder bgb				= new BrainGeneBuilder();
		BrainLobeGeneBuilder blgb			= new BrainLobeGeneBuilder();
		EmitterReceptorBuilder erb			= new EmitterReceptorBuilder();
		InitialConcentrationBuilder icb		= new InitialConcentrationBuilder();
		InstinctBuilder	ib					= new InstinctBuilder();
		StimulusDecisionBuilder sdb			= new StimulusDecisionBuilder();
		
		gml.addGene(bgmlb.build());
		Assertions.assertEquals(1, gml.length());
		
		gml.addGene(bgb.build());
		Assertions.assertEquals(2, gml.length());
		
		gml.addGene(blgb.build());
		Assertions.assertEquals(3, gml.length());
		
		gml.addGene(erb.build());
		Assertions.assertEquals(4, gml.length());
		
		gml.addGene(icb.build());
		Assertions.assertEquals(5, gml.length());
		
		gml.addGene(ib.build());
		Assertions.assertEquals(6, gml.length());
		
		gml.addGene(sdb.build());
		Assertions.assertEquals(7, gml.length());
		
		Assertions.assertEquals(BiochemicalReaction.class, 	gml.getGene(0).getClass());
		Assertions.assertEquals(BrainGene.class, 			gml.getGene(1).getClass());
		Assertions.assertEquals(BrainLobeGene.class, 		gml.getGene(2).getClass());
		Assertions.assertEquals(EmitterReceptor.class, 		gml.getGene(3).getClass());
		Assertions.assertEquals(InitialConcentration.class, gml.getGene(4).getClass());
		Assertions.assertEquals(Instinct.class, 			gml.getGene(5).getClass());
		Assertions.assertEquals(StimulusDecision.class, 	gml.getGene(6).getClass());
		
		// gml.getGenesNames().stream().forEach( System.out::println );
		
	}
	
	// TODO complete these tests !! GeneMoreListTests

//	@Test
//	void testGetGenesNames() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetGeneName() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetGeneInt() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetGeneString() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetLastGeneName() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetType() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testRemoveGene() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testAddLineOfGeneMoreListeFile() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetGeneString1() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testReadFile() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testPrintFile() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testAddToChamps() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSetChamps() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testRemoveChamps() {
//		fail("Not yet implemented");
//	}

}

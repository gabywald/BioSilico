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
import gabywald.biosilico.structures.Pathway;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class PathwayTests {
	
	public static final String PATHWAY_NAME = "PathwayName";

	@Test
	void testPathway() {
		Pathway path = new Pathway( PathwayTests.PATHWAY_NAME );
		Assertions.assertNotNull( path );
		Assertions.assertEquals(PathwayTests.PATHWAY_NAME, path.getName());
		Assertions.assertEquals(0, path.length());
		Assertions.assertEquals(PathwayTests.PATHWAY_NAME, path.getName());
		
		BiochemicalReactionBuilder bgmlb	= new BiochemicalReactionBuilder();
		BrainGeneBuilder bgb				= new BrainGeneBuilder();
		BrainLobeGeneBuilder blgb			= new BrainLobeGeneBuilder();
		EmitterReceptorBuilder erb			= new EmitterReceptorBuilder();
		InitialConcentrationBuilder icb		= new InitialConcentrationBuilder();
		InstinctBuilder	ib					= new InstinctBuilder();
		StimulusDecisionBuilder sdb			= new StimulusDecisionBuilder();
		
		path.addGene(bgmlb.build());
		Assertions.assertEquals(1, path.length());
		
		path.addGene(bgb.build());
		Assertions.assertEquals(2, path.length());
		
		path.addGene(blgb.build());
		Assertions.assertEquals(3, path.length());
		
		path.addGene(erb.build());
		Assertions.assertEquals(4, path.length());
		
		path.addGene(icb.build());
		Assertions.assertEquals(5, path.length());
		
		path.addGene(ib.build());
		Assertions.assertEquals(6, path.length());
		
		path.addGene(sdb.build());
		Assertions.assertEquals(7, path.length());
		
		Assertions.assertEquals(BiochemicalReaction.class, 	path.getGene(0).getClass());
		Assertions.assertEquals(BrainGene.class, 			path.getGene(1).getClass());
		Assertions.assertEquals(BrainLobeGene.class, 		path.getGene(2).getClass());
		Assertions.assertEquals(EmitterReceptor.class, 		path.getGene(3).getClass());
		Assertions.assertEquals(InitialConcentration.class, path.getGene(4).getClass());
		Assertions.assertEquals(Instinct.class, 			path.getGene(5).getClass());
		Assertions.assertEquals(StimulusDecision.class, 	path.getGene(6).getClass());
		
		// gml.getGenesNames().stream().forEach( System.out::println );
		
	}

}

package gabywald.biosilico.model.tests;

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
import gabywald.biosilico.model.Chromosome;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class ChromosomeTests {

	@Test
	void testChromosome() {
		Chromosome chr = new Chromosome();
		Assertions.assertEquals(0, chr.length());
		
		BiochemicalReactionBuilder bchrb	= new BiochemicalReactionBuilder();
		BrainGeneBuilder bgb				= new BrainGeneBuilder();
		BrainLobeGeneBuilder blgb			= new BrainLobeGeneBuilder();
		EmitterReceptorBuilder erb			= new EmitterReceptorBuilder();
		InitialConcentrationBuilder icb		= new InitialConcentrationBuilder();
		InstinctBuilder	ib					= new InstinctBuilder();
		StimulusDecisionBuilder sdb			= new StimulusDecisionBuilder();
		
		chr.addGene(bchrb.build());
		Assertions.assertEquals(1, chr.length());
		
		chr.addGene(bgb.build());
		Assertions.assertEquals(2, chr.length());
		
		chr.addGene(blgb.build());
		Assertions.assertEquals(3, chr.length());
		
		chr.addGene(erb.build());
		Assertions.assertEquals(4, chr.length());
		
		chr.addGene(icb.build());
		Assertions.assertEquals(5, chr.length());
		
		chr.addGene(ib.build());
		Assertions.assertEquals(6, chr.length());
		
		chr.addGene(sdb.build());
		Assertions.assertEquals(7, chr.length());
		
		Assertions.assertEquals(BiochemicalReaction.class, 	chr.getGene(0).getClass());
		Assertions.assertEquals(BrainGene.class, 			chr.getGene(1).getClass());
		Assertions.assertEquals(BrainLobeGene.class, 		chr.getGene(2).getClass());
		Assertions.assertEquals(EmitterReceptor.class, 		chr.getGene(3).getClass());
		Assertions.assertEquals(InitialConcentration.class, chr.getGene(4).getClass());
		Assertions.assertEquals(Instinct.class, 			chr.getGene(5).getClass());
		Assertions.assertEquals(StimulusDecision.class, 	chr.getGene(6).getClass());
		
	}
	
}

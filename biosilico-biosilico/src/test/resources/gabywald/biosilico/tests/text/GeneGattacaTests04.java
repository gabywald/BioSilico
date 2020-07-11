package gabywald.biosilico.tests.text;

import gabywald.biosilico.genetics.BiochemicalReaction;
import gabywald.biosilico.genetics.BrainGene;
import gabywald.biosilico.genetics.BrainLobeGene;
import gabywald.biosilico.genetics.EmitterReceptor;
import gabywald.biosilico.genetics.Gene;
import gabywald.biosilico.genetics.GeneGattaca;
import gabywald.biosilico.genetics.InitialConcentration;
import gabywald.biosilico.genetics.Instinct;
import gabywald.biosilico.genetics.StimulusDecision;
import gabywald.biosilico.model.Chromosome;
import gabywald.global.view.text.Terminal;

/**
 * 
 * @author Gabriel Chandesris (2010)
 */
public class GeneGattacaTests04 {

	public static void main (String argv[]) {

		Chromosome basic_genome = new Chromosome();
		/** InitialConcentration */
		basic_genome.addGene(new InitialConcentration(
					true,true, true, true, 0, 0, 0, 25,
					15, 100));
		/** BiochemicalReaction */
		basic_genome.addGene(new BiochemicalReaction(
					false, false, false, true, 0, 999, 0, 25,
					1, 10, 
					2, 10, 
					3, 10, 
					4, 10, 
					5));
		/** Brain Gene */
		basic_genome.addGene(new BrainGene(
				true, true, true, true, 0, 0, 0, 25,
				30,30,30,30));
		/** BrainLobe receptors */
		basic_genome.addGene(new BrainLobeGene(
				true, true, true, true, 0, 0, 0, 25,
				0, 10, 1, 0, 0, 0, false, 0, false, 
				1, 30, 0, 0, false));
		/** ER Receptor */
		basic_genome.addGene(new EmitterReceptor(
					true, true, true, true, 0, 999, 0, 25,
					1,10,10,0,15,true,true));
		/** ER Emitter */
		basic_genome.addGene(new EmitterReceptor(
					true, true, true, true, 0, 999, 0, 25,
					1,10,10,29,15,false,true));
		/** SD Decision direction */
		basic_genome.addGene(new StimulusDecision(
					true, true, true, true, 0, 999, 0, 25,
					false,false,800,100,880,20,20,850));
		/** SD Decision object */
		basic_genome.addGene(new StimulusDecision(
					true, true, true, true, 0, 999, 0, 25,
					false,true,800,100,880,20,20,850));
		/** SD Perception direction */
		basic_genome.addGene(new StimulusDecision(
					true, true, true, true, 0, 999, 0, 25,
					true,false,800,100,880,20,20,850));
		/** SD Perception object */
		basic_genome.addGene(new StimulusDecision(
					true, true, true, true, 0, 999, 0, 25,
					true,true,800,100,880,20,20,850));
		
		/** IN not checker */
		basic_genome.addGene(new Instinct(
					true, true, true, true, 0, 999, 0, 25,
					0,10,99,10,555,20,20,false));
		
		/** IN checker */
		basic_genome.addGene(new Instinct(
					true, true, true, true, 0, 999, 0, 25,
					0,10,99,10,555,20,20,true));
		
		
		/** Getting content of genome */
		String tableOfGenes[] = new String[basic_genome.getGeneNumber()];
		for (int i = 0 ; i < tableOfGenes.length ; i++) {
			Gene tmp = basic_genome.getGene(i);
			String tmpRT = tmp.reverseTranslation(false);
			/** Terminal.ecrireStringln(tmpRT); /** tmpRT.length()+"\n"+ */
			tableOfGenes[i] = tmpRT;
		}
		Terminal.sautDeLigne();
		
		/** Writing sample translation */
		/*
		for (int i = 0 ; i < tableOfGenes.length ; i++) 
			{ Terminal.ecrireStringln(GeneGattaca.translation(tableOfGenes[i],0)+"\t\t*****"
					+"\n\t"+tableOfGenes[i]); }
		Terminal.sautDeLigne();Terminal.sautDeLigne();
		*/
		
		/** => */
		Gene genetable[] = new Gene[tableOfGenes.length];
		for (int i = 0 ; i < tableOfGenes.length ; i++) 
			{ genetable[i] = Gene.loadGene(tableOfGenes[i]); }
		for (int i = 0 ; i < tableOfGenes.length ; i++) 
			{ Terminal.ecrireStringln("=> "+
					GeneGattaca.translation(genetable[i].reverseTranslation(false),0)
					+"\t"+(genetable[i].reverseTranslation(false).equals(tableOfGenes[i]))
					+"\n =>"+GeneGattaca.translation(tableOfGenes[i], 0)+"\t*****"
					+"\n\t=> "+genetable[i].reverseTranslation(false)
					+"\n\t =>"+tableOfGenes[i]);Terminal.sautDeLigne(); }
	}
}

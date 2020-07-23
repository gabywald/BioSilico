package gabywald.biosilico.tests.text;

import gabywald.biosilico.genetics.BrainGene;
import gabywald.biosilico.genetics.BrainLobeGene;
import gabywald.biosilico.genetics.EmitterReceptor;
import gabywald.biosilico.genetics.Gene;
import gabywald.biosilico.genetics.GeneGattaca;
import gabywald.biosilico.model.Chromosome;
import gabywald.global.view.text.Terminal;

/**
 * 
 * @author Gabriel Chandesris (2010)
 */
public class GeneGattacaTests03 {

	public static void main (String argv[]) {
		/**
		for (int i = 0 ; i < 150 ; i++) {
			Terminal.ecrireString(((i < 100)?"0"+((i < 10)?"0":""):"")+i+" ");
			if (i%10 == 0) { Terminal.sautDeLigne(); }
		}
		 */

		Chromosome basic_genome = new Chromosome();
		/** Brain Gene */
		basic_genome.addGene(new BrainGene(
				true, true, true, true, 0, 0, 0, 25,
				30,30,30,30));
		
		/** Some BrainLobe Genes */
		/** receptors */
		basic_genome.addGene(new BrainLobeGene(
				true, true, true, true, 0, 0, 0, 25,
				0, 10, 1, 0, 0, 0, false, 0, false, 
				1, 30, 0, 0, false));
		
		/** decision */
		// Neuron decisionTest = new Neuron (0,10,1,1,16,4,false,0,true);
		// this.testBrain.setLobe(1, this.brainWidth,this.brainHeight-1, 0, decisionTest, false);
		basic_genome.addGene(new BrainLobeGene(
				true, true, true, true, 0, 0, 0, 25,
				0, 10, 1, 1, 16, 4, false, 0, true, 
				1, 15, 29, 0, false));
		
		/** emitter */
		// Neuron emittersTest = new Neuron (0,10,1,8,16,4,false,0);
		// this.testBrain.setLobe(1, 1, this.brainHeight-5, 8, emittersTest, false);
		basic_genome.addGene(new BrainLobeGene(
				true, true, true, true, 0, 0, 0, 25,
				0, 10, 1, 8, 16, 4, false, 0, false, 
				1, 15, 29, 15, false));
		
		/** conception */
		// Neuron conceptionUn = new Neuron (0,10,1,2,4,4,true,100);
		// this.testBrain.setLobe(1, this.brainWidth, 1, 0, conceptionUn, true);
		basic_genome.addGene(new BrainLobeGene(
				true, true, true, true, 0, 0, 0, 25,
				0, 10, 1, 2, 4, 4, true, 100, false, 
				1, 30, 1, 0, false));
		
		/** Receptor */
		for (int i = 0 ; i < 30 ; i++) {
			basic_genome.addGene(new EmitterReceptor(
					true, true, true, true, 0, 999, 0, 25,
					1,10,10,0,i,true,true));
		}
		
		/** Emitter */
		for (int i = 0 ; i < 30 ; i++) {
			basic_genome.addGene(new EmitterReceptor(
					true, true, true, true, 0, 999, 0, 25,
					1,10,10,29,i,false,true));
		}
		
		String tableOfGenes[] = new String[basic_genome.getGeneNumber()];
		for (int i = 0 ; i < tableOfGenes.length ; i++) {
			Gene tmp = basic_genome.getGene(i);
			String tmpRT = tmp.reverseTranslation(false);
			/** Terminal.ecrireStringln(tmpRT); /** tmpRT.length()+"\n"+ */
			tableOfGenes[i] = tmpRT;
		}
		Terminal.sautDeLigne();
		for (int i = 0 ; i < tableOfGenes.length ; i++) 
			{ Terminal.ecrireStringln(GeneGattaca.translation(tableOfGenes[i],0)+"\t\t*****"); }
		Terminal.sautDeLigne();Terminal.sautDeLigne();
		
		/** => */
		Gene genetable[] = new Gene[tableOfGenes.length];
		for (int i = 0 ; i < tableOfGenes.length ; i++) 
			{ genetable[i] = Gene.loadGene(tableOfGenes[i]); }
		/** Chromosome nextGenome = new Chromosome(genetable); */
		for (int i = 0 ; i < tableOfGenes.length ; i++) 
			{ Terminal.ecrireStringln("=> "+
					GeneGattaca.translation(genetable[i].reverseTranslation(false),0)
					+"\t"+(genetable[i].reverseTranslation(false).equals(tableOfGenes[i]))
					+"\n\t"+genetable[i].reverseTranslation(false)); }
		
	}
}

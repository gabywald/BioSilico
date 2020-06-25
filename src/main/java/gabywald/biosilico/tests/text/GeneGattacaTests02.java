package gabywald.biosilico.tests.text;

import gabywald.biosilico.genetics.BiochemicalReaction;
import gabywald.biosilico.genetics.Gene;
import gabywald.biosilico.genetics.GeneGattaca;
import gabywald.biosilico.genetics.InitialConcentration;
import gabywald.biosilico.model.Chromosome;
import gabywald.global.view.text.Terminal;

/**
 * 
 * @author Gabriel Chandesris (2010)
 */
public class GeneGattacaTests02 {

	public static void main (String argv[]) {
		/**
		for (int i = 0 ; i < 150 ; i++) {
			Terminal.ecrireString(((i < 100)?"0"+((i < 10)?"0":""):"")+i+" ");
			if (i%10 == 0) { Terminal.sautDeLigne(); }
		}
		 */

		Chromosome basic_genome = new Chromosome();
		/** Some InitialConcentration Genes */
		for (int i = 0 ; i < 20 ; i++) {
			basic_genome.addGene(new InitialConcentration(
					true,true, true, true, 0, 0, 0, 25,
					i, 100));
		}
		for (int i = 0 ; i < 20 ; i++) {
			basic_genome.addGene(new InitialConcentration(
					true,true, true, true, 100, 100, 0, 25,
					i, 500));
		}
		/** Some BiochemicalReaction Genes */
		for (int i = 0 ; i < 10 ; i++) {
			basic_genome.addGene(new BiochemicalReaction(
					false, false, false, true, 0, 999, 0, 25,
					i, 10, 
					i+1, 10, 
					i+2, 10, 
					i+3, 10, 
					5));
		}
		for (int i = 0 ; i < 10 ; i++) {
			basic_genome.addGene(new BiochemicalReaction(
					false, false, false, true, 0, 999, 0, 25,
					i+10, 10, 
					0, 0, 
					i+11, 10, 
					0, 0, 
					5));
		}
		for (int i = 0 ; i < 10 ; i++) {
			basic_genome.addGene(new BiochemicalReaction(
					false, false, false, true, 0, 999, 0, 25,
					i+12, 10, 
					0, 0, 
					i+13, 10, 
					i+14, 10, 
					50));
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
			{ Terminal.ecrireStringln(GeneGattaca.translation(tableOfGenes[i],0)); }
		
		Terminal.sautDeLigne();Terminal.sautDeLigne();Terminal.sautDeLigne();
		
		Gene genetable[] = new Gene[tableOfGenes.length];
		for (int i = 0 ; i < tableOfGenes.length ; i++) 
			{ genetable[i] = Gene.loadGene(tableOfGenes[i]); }
		/** Chromosome nextGenome = new Chromosome(genetable); */
		for (int i = 0 ; i < tableOfGenes.length ; i++) 
			{ Terminal.ecrireStringln("=> "+
					GeneGattaca.translation(genetable[i].reverseTranslation(false),0)
					+"\t"+(genetable[i].reverseTranslation(false).equals(tableOfGenes[i]))
					+"\n\t"+genetable[i].reverseTranslation(false)); }
		
		/**
M0246000000000000100*
M0246000000000001100*
M0246000000000002100*
M0246000000000003100*
M0246000000000004100*
M0246000000000005100*
M0246000000000006100*
M0246000000000007100*
M0246000000000008100*
M0246000000000009100*
M0246000000000010100*
M0246000000000011100*
M0246000000000012100*
M0246000000000013100*
M0246000000000014100*
M0246000000000015100*
M0246000000000016100*
M0246000000000017100*
M0246000000000018100*
M0246000000000019100*
M0246100100000000500*
M0246100100000001500*
M0246100100000002500*
M0246100100000003500*
M0246100100000004500*
M0246100100000005500*
M0246100100000006500*
M0246100100000007500*
M0246100100000008500*
M0246100100000009500*
M0246100100000010500*
M0246100100000011500*
M0246100100000012500*
M0246100100000013500*
M0246100100000014500*
M0246100100000015500*
M0246100100000016500*
M0246100100000017500*
M0246100100000018500*
M0246100100000019500*
M1356000999000000010001010002010003010005*
M1356000999000001010002010003010004010005*
M1356000999000002010003010004010005010005*
M1356000999000003010004010005010006010005*
M1356000999000004010005010006010007010005*
M1356000999000005010006010007010008010005*
M1356000999000006010007010008010009010005*
M1356000999000007010008010009010010010005*
M1356000999000008010009010010010011010005*
M1356000999000009010010010011010012010005*
M1356000999000010010000000011010000000005*
M1356000999000011010000000012010000000005*
M1356000999000012010000000013010000000005*
M1356000999000013010000000014010000000005*
M1356000999000014010000000015010000000005*
M1356000999000015010000000016010000000005*
M1356000999000016010000000017010000000005*
M1356000999000017010000000018010000000005*
M1356000999000018010000000019010000000005*
M1356000999000019010000000020010000000005*
M1356000999000012010000000013010014010050*
M1356000999000013010000000014010015010050*
M1356000999000014010000000015010016010050*
M1356000999000015010000000016010017010050*
M1356000999000016010000000017010018010050*
M1356000999000017010000000018010019010050*
M1356000999000018010000000019010020010050*
M1356000999000019010000000020010021010050*
M1356000999000020010000000021010022010050*
M1356000999000021010000000022010023010050*
M1356000999000021010000000022010023310050*
		 */
	}
}

package gabywald.biosilico.tests.text;

import gabywald.biosilico.genetics.BiochemicalReaction;
import gabywald.biosilico.genetics.GeneGattaca;
import gabywald.biosilico.genetics.InitialConcentration;
import gabywald.global.view.text.Terminal;

/**
 * 
 * @author Gabriel Chandesris (2010)
 */
public class GeneGattacaTests01 {

	public static void main (String argv[]) {
		/** 
		 * GeneGattaca test1 = new GeneGattaca(false,false,false,false,0,0,0);
		test1.canDelete();
		test1.canDuplicate();
		test1.canMutate();
		*/
		/**
		Terminal.ecrireStringln(GeneGattaca.translation("aaaaacaataagacaaccactacgataatcattatgagaagcagtagg", 0));
		Terminal.ecrireStringln(GeneGattaca.translation("caacaccatcagccaccccctccgctactccttctgcgacgccgtcgg", 0));
		Terminal.ecrireStringln(GeneGattaca.translation("taatactattagtcatcctcttcgttattctttttgtgatgctgttgg", 0));
		Terminal.ecrireStringln(GeneGattaca.translation("gaagacgatgaggcagccgctgcggtagtcgttgtgggaggcggtggg", 0));
		Terminal.sautDeLigne();
		Terminal.ecrireStringln(GeneGattaca.translation("aaaaacaataagacaaccactacgataatcattatgagaagcagtagg", 1));
		Terminal.ecrireStringln(GeneGattaca.translation("caacaccatcagccaccccctccgctactccttctgcgacgccgtcgg", 1));
		Terminal.ecrireStringln(GeneGattaca.translation("taatactattagtcatcctcttcgttattctttttgtgatgctgttgg", 1));
		Terminal.ecrireStringln(GeneGattaca.translation("gaagacgatgaggcagccgctgcggtagtcgttgtgggaggcggtggg", 1));
		Terminal.sautDeLigne();
		Terminal.ecrireStringln(GeneGattaca.translation("aaaaacaataagacaaccactacgataatcattatgagaagcagtagg", 2));
		Terminal.ecrireStringln(GeneGattaca.translation("caacaccatcagccaccccctccgctactccttctgcgacgccgtcgg", 2));
		Terminal.ecrireStringln(GeneGattaca.translation("taatactattagtcatcctcttcgttattctttttgtgatgctgttgg", 2));
		Terminal.ecrireStringln(GeneGattaca.translation("gaagacgatgaggcagccgctgcggtagtcgttgtgggaggcggtggg", 2));
		Terminal.sautDeLigne();
		Terminal.ecrireStringln(GeneGattaca.translation("aaaaacaataagacaaccactacgataatcattatgagaagcagtagg", 3));
		Terminal.ecrireStringln(GeneGattaca.translation("caacaccatcagccaccccctccgctactccttctgcgacgccgtcgg", 3));
		Terminal.ecrireStringln(GeneGattaca.translation("taatactattagtcatcctcttcgttattctttttgtgatgctgttgg", 3));
		Terminal.ecrireStringln(GeneGattaca.translation("gaagacgatgaggcagccgctgcggtagtcgttgtgggaggcggtggg", 3));
		Terminal.sautDeLigne();
		Terminal.sautDeLigne();
		Terminal.sautDeLigne();
		*/
		
		BiochemicalReaction testBR = new BiochemicalReaction(false, 
				true, 
				false, 
				true, 
				0, 9, 5, 25,
				10, 2, 
				11, 2, 
				8, 1, 
				4, 1, 
				25);
		Terminal.ecrireStringln(testBR.reverseTranslation(false));
		Terminal.sautDeLigne();
		Terminal.ecrireStringln(GeneGattaca.translation("GGACGCTAATTCTGACTTCTTCTTCTTCTTGCCCTTCTTTTCCTTCGCCTTCTTCTTTAACTTCGCCGCCTTCTTTAACTTCTTCGCCTTCTTCTTTAACTTCTTCGCCGCCTTCTTTAACTTTAATTCGGT", 0));
		Terminal.sautDeLigne();
		
		
		InitialConcentration testIC = new InitialConcentration(true, 
				 false, 
				 true, 
				 false, 
				 0, 9, 5, 25,
				 1, 
				 100);
		Terminal.ecrireStringln(testIC.reverseTranslation(false));
		Terminal.sautDeLigne();
		Terminal.ecrireStringln(GeneGattaca.translation("GGACTTTAGTCTTGGCTTCTTCTTCTTCTTGCCCTTCTTTTCCTTCTTCGCCGCCTTCTTGGT", 0));
		Terminal.sautDeLigne();
		
	}
}

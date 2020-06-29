package gabywald.biosilico.tests.text;

import gabywald.biosilico.structures.GeneticTreeNode;
import gabywald.biosilico.structures.GeneticTreeNodeListe;
import gabywald.global.view.text.Terminal;

/**
 * 
 * @author Gabriel Chandesris (2010)
 */
public class GeneticTreeNodeTests03 {

	public static void main (String argv[]) {	
		
		GeneticTreeNode standard = GeneticTreeNode.getGeneticCodeStandard();
		GeneticTreeNodeListe SGCLeaves = standard.getLeaves();
		Terminal.ecrireStringln("=======================\n\n"+SGCLeaves.length()+" items\n");
		for (int i = 0 ; i < SGCLeaves.length() ; i++) {
			GeneticTreeNode tmp = SGCLeaves.getGeneticTreeNode(i);
			Terminal.ecrireString("\t"+tmp.getCode()+"\t"+tmp.getValue()+"\t|");
			if ( (i != 0) && (i%8 == 0) ) { Terminal.sautDeLigne(); }
		}
		
		Terminal.sautDeLigne();
		Terminal.sautDeLigne();
		
		GeneticTreeNode gattaca = GeneticTreeNode.getGeneticCodeGattaca();
		GeneticTreeNodeListe GGCLeaves = gattaca.getLeaves();
		Terminal.ecrireStringln("=======================\n\n"+GGCLeaves.length()+" items\n");
		for (int i = 0 ; i < GGCLeaves.length() ; i++) {
			GeneticTreeNode tmp = GGCLeaves.getGeneticTreeNode(i);
			Terminal.ecrireString("\t"+tmp.getCode()+"\t"+tmp.getValue()+"\t|");
			if ( (i != 0) && (i%8 == 0) ) { Terminal.sautDeLigne(); }
		}
		
		Terminal.sautDeLigne();
		Terminal.sautDeLigne();
		
		GeneticTreeNode phasetwo = GeneticTreeNode.getGeneticCodePhaseTwo();
		GeneticTreeNodeListe PGCLeaves = phasetwo.getLeaves();
		Terminal.ecrireStringln("=======================\n\n"+PGCLeaves.length()+" items\n");
		for (int i = 0 ; i < PGCLeaves.length() ; i++) {
			GeneticTreeNode tmp = PGCLeaves.getGeneticTreeNode(i);
			Terminal.ecrireString("\t"+tmp.getCode()+"\t"+tmp.getValue()+"\t\t|");
			if ( (i != 0) && (i%8 == 0) ) { Terminal.sautDeLigne(); }
		}
		
		Terminal.sautDeLigne();
		Terminal.sautDeLigne();
		
		
	}
}

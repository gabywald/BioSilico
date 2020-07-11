package gabywald.biosilico.tests.text;

import java.util.List;

import gabywald.biosilico.structures.GeneticTreeNode;
import gabywald.global.view.text.Terminal;

/**
 * 
 * @author Gabriel Chandesris (2010)
 */
public class GeneticTreeNodeTests03 {

	public static void main (String argv[]) {	
		
		GeneticTreeNode standard = GeneticTreeNode.getGeneticCodeStandard();
		List<GeneticTreeNode> SGCLeaves = standard.getLeaves();
		Terminal.ecrireStringln("=======================\n\n"+SGCLeaves.size()+" items\n");
		for (int i = 0 ; i < SGCLeaves.size() ; i++) {
			GeneticTreeNode tmp = SGCLeaves.get(i);
			Terminal.ecrireString("\t"+tmp.getCode()+"\t"+tmp.getValue()+"\t|");
			if ( (i != 0) && (i%8 == 0) ) { Terminal.sautDeLigne(); }
		}
		
		Terminal.sautDeLigne();
		Terminal.sautDeLigne();
		
		GeneticTreeNode gattaca = GeneticTreeNode.getGeneticCodeGattaca();
		List<GeneticTreeNode> GGCLeaves = gattaca.getLeaves();
		Terminal.ecrireStringln("=======================\n\n"+GGCLeaves.size()+" items\n");
		for (int i = 0 ; i < GGCLeaves.size() ; i++) {
			GeneticTreeNode tmp = GGCLeaves.get(i);
			Terminal.ecrireString("\t"+tmp.getCode()+"\t"+tmp.getValue()+"\t|");
			if ( (i != 0) && (i%8 == 0) ) { Terminal.sautDeLigne(); }
		}
		
		Terminal.sautDeLigne();
		Terminal.sautDeLigne();
		
		GeneticTreeNode phasetwo = GeneticTreeNode.getGeneticCodePhaseTwo();
		List<GeneticTreeNode> PGCLeaves = phasetwo.getLeaves();
		Terminal.ecrireStringln("=======================\n\n"+PGCLeaves.size()+" items\n");
		for (int i = 0 ; i < PGCLeaves.size() ; i++) {
			GeneticTreeNode tmp = PGCLeaves.get(i);
			Terminal.ecrireString("\t"+tmp.getCode()+"\t"+tmp.getValue()+"\t\t|");
			if ( (i != 0) && (i%8 == 0) ) { Terminal.sautDeLigne(); }
		}
		
		Terminal.sautDeLigne();
		Terminal.sautDeLigne();
		
		
	}
}

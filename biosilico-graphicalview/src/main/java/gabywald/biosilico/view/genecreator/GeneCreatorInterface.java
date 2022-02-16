package gabywald.biosilico.view.genecreator;

import javax.swing.JButton;
import javax.swing.JTextField;

import gabywald.biosilico.view.GeneKitInterface;
import gabywald.biosilico.view.GeneListJScroll;
import gabywald.biosilico.view.GeneParametersViewer;
import gabywald.global.view.graph.IEnablerBorderLayoutPanels;

/**
 * 
 * @author Gabriel Chandesris (2022)
 */
public interface GeneCreatorInterface extends IEnablerBorderLayoutPanels, GeneKitInterface {
	public GeneParametersViewer getParameterViewer();
	public JTextField getGeneName();
	public JButton getAddGene2Pathway();
	public JButton getDeleteGene();
	public GeneListJScroll getBuildingGene();
	public GeneListJScroll getBuildingPathway();
	public JButton getCreatePathway();
	public JTextField getPathwayName();
}

package gabywald.biosilico.view;

import javax.swing.JButton;

import gabywald.global.view.graph.SelectBox;

/**
 * 
 * @author Gabriel Chandesris (2022)
 */
public interface GeneKitInterface {

	public JButton getCreateGene();
	public JButton getChangeGene();
	public JButton getMakeNeGene();
	public JButton getAddSavGene();
	
	public GeneParametersViewer getGenesViewer();
	
	public GeneParametersViewer getParameterViewer();
	
	public SelectBox getGeneTypeSelection();
	
	public void setGeneTypeSelection(int geneType);
	
	public GeneSelectBox getGeneSelection();
	
	public PathwaySelectBox getPathSelection();
	
}

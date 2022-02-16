package gabywald.biosilico.view.genetickit;

import javax.swing.JButton;

import gabywald.biosilico.view.GeneKitInterface;
import gabywald.biosilico.view.GeneListJScroll;
import gabywald.global.view.graph.IEnablerBorderLayoutPanels;

/**
 * 
 * @author Gabriel Chandesris (2022)
 */
public interface GeneticKitInterface extends IEnablerBorderLayoutPanels, GeneKitInterface {

	public JButton getAddPathway();

	public GeneListJScroll getGeneScroll(); 
}

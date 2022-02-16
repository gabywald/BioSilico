package gabywald.biosilico.view;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import gabywald.global.view.graph.GenericJFrame;
import gabywald.global.view.graph.SelectBox;

/**
 * This mother class defines a generic use of Gene's parameters and type selection. 
 * @author Gabriel Chandesris (2010, 2020, 2022)
 * @see GeneParametersViewer
 * @see gabywald.biosilico.view.genecreator.GeneCreatorJFrame
 * @see gabywald.biosilico.view.genetickit.GeneticKitJFrame
 */
@SuppressWarnings("serial")
public abstract class GeneKitJFrame extends GenericJFrame {
	/** For viewing the parameters. (Use CardLayout). */
	protected GeneParametersViewer parameterViewer;
	/** To select the Gene's Type. */
	protected SelectBox geneTypeSelection;
	/** To modify current selection of Gene. */
	protected JButton createGene, changeGene, makeneGene, addsavGene;
	
	/** Contain Gene Selector. */
	protected GeneSelectBox geneSelection;
	/** Contain Path Selector. */
	protected PathwaySelectBox pathSelection;
	/** To view List of chemicals. */
	protected ChemicalSelectBox chemicalBox;
	
	/** Default Constructor. */
	protected GeneKitJFrame() {
		this.chemicalBox			= ChemicalSelectBox.getInstance();
		this.geneSelection			= new GeneSelectBox();
		this.pathSelection			= new PathwaySelectBox();

		/** Cards for parameters */
		this.parameterViewer		= GeneParametersViewer.getInstance();
		this.geneTypeSelection		= new SelectBox(GeneParametersViewer.geneTypeListe);
		this.createGene				= new JButton("Create Gene");
		this.addsavGene				= new JButton("Add Gene to Agent");
		this.changeGene				= new JButton("Change Gene");
		this.makeneGene				= new JButton("As new Gene");
	}
	
	protected void setActionListener(ActionListener al) {
		// TODO create GeneKitActionListener dependant of GeneKitJFrame
		this.geneSelection.addActionListener( al );
		this.pathSelection.addActionListener( al );
		this.geneTypeSelection.addActionListener( al );
	}
	
	public JButton getCreateGene() { return this.createGene; }
	public JButton getChangeGene() { return this.changeGene; }
	public JButton getMakeNeGene() { return this.makeneGene; }
	public JButton getAddSavGene() { return this.addsavGene; }
	
	public GeneParametersViewer getGenesViewer() 
		{ return this.parameterViewer; }
	
	public GeneParametersViewer getParameterViewer() 
		{ return this.parameterViewer; }
	
	public SelectBox getGeneTypeSelection() 
		{ return this.geneTypeSelection; }
	
	public void setGeneTypeSelection(int geneType) 
		{ this.geneTypeSelection.setSelection(geneType); }
	
	public GeneSelectBox getGeneSelection() 
		{ return this.geneSelection; }
	
	public PathwaySelectBox getPathSelection() 
		{ return this.pathSelection; }
	
}

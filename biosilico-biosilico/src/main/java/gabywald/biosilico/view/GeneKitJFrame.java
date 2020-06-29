package gabywald.biosilico.view;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;

import gabywald.global.view.graph.GenericJFrame;
import gabywald.global.view.graph.SelectBox;

/**
 * This mother class defines a generic use of Gene's parameters and type selection. 
 * @author Gabriel Chandesris (2010, 2020)
 * @see GeneParametersViewer
 * @see GeneCreator
 * @see GeneticKit
 */
@SuppressWarnings("serial")
public abstract class GeneKitJFrame extends GenericJFrame 
									implements ActionListener {
	/** For viewing the parameters. (Use CardLayout). */
	protected GeneParametersViewer parameterViewer;
	/** To select the Gene's Type. */
	protected SelectBox geneTypeSelection;
	/** To modify current selection of Gene. */
	protected JButton createGene,changeGene,makeneGene,addsavGene;
	
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
		
		this.geneSelection.addActionListener(this);
		this.pathSelection.addActionListener(this);
		this.geneTypeSelection.addActionListener(this);
	}
	
	public JButton getCreateGene() { return this.createGene; }
	public JButton getChangeGene() { return this.changeGene; }
	public JButton getMakeNeGene() { return this.makeneGene; }
	public JButton getAddSavGene() { return this.addsavGene; }
	
	public GeneParametersViewer getGenesViewer() 
		{ return this.parameterViewer; }
	
	public JComboBox<String> getGeneTypeSelection() 
		{ return this.geneTypeSelection; }
	
	protected void setGeneTypeSelection(int geneType) 
	{ this.geneTypeSelection.setSelection(geneType); }
	
}

package gabywald.biosilico.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import gabywald.global.structures.StringListe;

/**
 * This class defines a graphical view to create and manipulate Gene's. 
 * <br><i>Design-Pattern Singleton. </i>
 * @author Gabriel Chandesris (2010)
 */
public class GeneCreator extends GeneKitJFrame {
	/** To avoid a Warning. */
	// private static final long serialVersionUID = 111L;
	/** Unique instance of this view. */
	private static GeneCreator instance = null;
	/** JPanel in Center of the JFrame. */
	private GeneKitsGBJPanel centerPanel;
	/** To see / read the current Gene's name. */
	private JTextField geneName;
	
	/** JPanel in West / Left of the JFrame. */
	private GeneKitsGBJPanel westernPanel;
	/** To view list of Gene's. */
	private GeneListJScroll buildingGene;
	/** To remove a Gene. */
	private JButton deleteGene;
	
	/** JPanel in East / Right of the JFrame */
	private GeneKitsGBJPanel easternPanel;
	/** To view  current Pathway "in the way" tobbe added. */
	private GeneListJScroll buildingPathway;
	/** Buttons to permit changes and add a pathway.  */
	private JButton addGene2pathway,createPathWay;
	/** TO write the name for a new pathway. */
	private JTextField pathwayName;
	
	/** Default constructor. */ 
	private GeneCreator() {
		/** Center JPanel */
		this.initCenterPanel();
		/** Western JPanel */
		this.initWesternPanel();
		/** Eastern JPanel */
		this.initEasternPanel();
		/** Positions in content of JFrame */
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(this.centerPanel,"Center");
		this.getContentPane().add(this.westernPanel,"West");
		this.getContentPane().add(this.easternPanel,"East");
		this.setTitle("Gene Creator");
		this.setVisible(true);
	}
	
	/**
	 * To get the current instance of graphical view. 
	 * @return (GenericJFrame)
	 */
	public static GeneCreator getInstance() {
		if (GeneCreator.instance == null) 
			{ GeneCreator.instance = new GeneCreator(); }
		return GeneCreator.instance;
	}
	
	private void initCenterPanel() {
		
		this.enableCenterPanel(false);
		
		this.createGene.addActionListener(this);
		this.changeGene.addActionListener(this);
		this.makeneGene.addActionListener(this);
		
		this.geneName = new JTextField();
		
		this.centerPanel = new GeneKitsGBJPanel();
		this.centerPanel.setPreferredSize(new Dimension(800,600));
		this.centerPanel.addBagComponent(this.geneTypeSelection		, 0, 0);
		this.centerPanel.addBagComponent(this.createGene			, 1, 0);
		this.centerPanel.addBagComponent(this.changeGene			, 2, 0);
		this.centerPanel.addBagComponent(this.makeneGene			, 3, 0);
		// this.centerPanel.addBagComponent(this.existentSelection	, 0, 1, 4);
		this.centerPanel.addBagComponent(this.parameterViewer		, 0, 2, 4);
		this.centerPanel.addBagComponent(new JLabel("Gene Name : ")	, 0, 3);
		this.centerPanel.addBagComponent(this.geneName				, 1, 3, 3);
		
		this.centerPanel.addBagComponent(new JLabel("\t")			, 0, 4, 4);
		
		this.centerPanel.addBagComponent(this.chemicalBox.getChemicalStockJLabel()	, 0, 5, 4);
		this.centerPanel.addBagComponent(this.chemicalBox			, 0, 6, 4);
	}
	
	private void initWesternPanel() {
		this.buildingGene	= new GeneListJScroll(this.geneSelection.getGeneNames());
		this.deleteGene		= new JButton("Delete Gene");
		this.deleteGene.addActionListener(this);
		this.westernPanel	= new GeneKitsGBJPanel();
		this.westernPanel.addBagComponent(this.buildingGene						, 0, 0);
		this.westernPanel.addBagComponent(this.deleteGene						, 0, 1);
		this.westernPanel.addBagComponent(new JLabel("\t")						, 0, 2);
		this.westernPanel.addBagComponent(new JLabel("Existent Pathways : ")	, 0, 3);
		this.westernPanel.addBagComponent(this.pathSelection					, 0, 4);
	}
	
	private void initEasternPanel() {
		this.buildingPathway	= new GeneListJScroll();
		this.addGene2pathway	= new JButton("Add Gene to Path");
		this.createPathWay		= new JButton("Create PathWay");
		this.pathwayName		= new JTextField();
		
		this.enableEasternPanel(false);
		
		this.addGene2pathway.addActionListener(this);
		// this.createGene.addActionListener(this);
		this.createPathWay.addActionListener(this);
		
		this.easternPanel = new GeneKitsGBJPanel();
		this.geneSelection.setPreferredSize(new Dimension(100,20));
		this.easternPanel.addBagComponent(this.geneSelection, 0, 0);
		this.easternPanel.addBagComponent(this.buildingPathway, 0, 1);
		this.easternPanel.addBagComponent(this.buildingPathway.getRemoveButton(), 0, 2);
		this.easternPanel.addBagComponent(new JLabel("Path Name : "), 0, 3);
		this.easternPanel.addBagComponent(this.pathwayName, 0, 4);
		this.easternPanel.addBagComponent(this.addGene2pathway, 0, 5);
		this.easternPanel.addBagComponent(this.createPathWay, 0, 6);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		if (source.equals(this.geneTypeSelection)) {
			// System.out.println("GENE TYPE SELECTION");
			/** Selection in Gene type menu. */
			int type = this.geneTypeSelection.getSelectedIndex();
			// System.out.println("Type selection : "+type);
			this.parameterViewer.selectCard(type);
			if (type > 0) { 
				((GeneJPanel)this.parameterViewer.getCard(type)).setDefaultValues();
				this.geneName.setText("");
				this.createGene.setEnabled(true);
				this.changeGene.setEnabled(false);
				this.makeneGene.setEnabled(false);
				this.geneSelection.setSelection(0);
			} else { 
				this.geneName.setText("");
				this.enableCenterPanel(false);
				this.geneSelection.setSelection(0);
			}
		} else if (source.equals(this.geneSelection)) {
			/** Selection in menu of previous defined genes. */
			// this.selectedGene = this.existentSelection.getSelectedIndex();
			// System.out.println("Existent selection : "+this.selected);
			if (this.geneSelection.getSelected() > 0) {
//				System.out.print("gene selection : ");
//				System.out.print(this.geneSelection.getSelectedGeneName());
//				System.out.println();
				this.geneName.setText(this.geneSelection.getSelectedGeneName());
				int geneType = this.geneSelection.getSelectedType();
				this.geneTypeSelection.setSelection(geneType);
				this.parameterViewer.selectCard(geneType);
				this.parameterViewer.setCompiledParameters
										(this.geneSelection.getSelectedGene(),
										geneType);
				// setGeneTypeSelection(geneType);
				this.createGene.setEnabled(false);
				this.changeGene.setEnabled(true);
				this.makeneGene.setEnabled(true);
				this.addGene2pathway.setEnabled(true);
			} else { 
				this.geneName.setText("");
				this.enableCenterPanel(false);
				this.addGene2pathway.setEnabled(false);
				this.setGeneTypeSelection(0);
				this.geneSelection.setSelection(0);
				this.parameterViewer.selectCard(0);
			}
		} else if ( (source.equals(this.createGene)) 
				|| (source.equals(this.makeneGene)) ) {
			if ( (!this.geneName.getText().equals("")) 
					&& (!this.geneName.getText()
							.equals(this.geneSelection.getLastGeneName())) ) {
				String oneMoreGene	= this.parameterViewer.getCompiledParameters(
										this.geneName.getText(),
										this.geneTypeSelection.getSelectedIndex());
				this.geneSelection.addGene(oneMoreGene);
				// System.out.println(oneMoreGene);
				this.createGene.setEnabled(false);
				this.changeGene.setEnabled(true);
				this.makeneGene.setEnabled(true);
			}
		} else if (source.equals(this.changeGene)) {
			String oneMoreGene	= this.parameterViewer.getCompiledParameters(
									this.geneName.getText(),
									this.geneTypeSelection.getSelectedIndex());
			// System.out.println(oneMoreGene);
			this.geneSelection.setCurrentGene(oneMoreGene);
		} else if (source.equals(this.deleteGene)) {
			int selectedGene = this.buildingGene.getSelectedIndex();
			this.buildingGene.removeCurrentSelection();
			this.geneSelection.remGene(selectedGene);
		} else if (source.equals(this.addGene2pathway)) {
			/** Adding a Gene to current Pathway. */
			this.buildingPathway.addString(this.geneSelection.getSelectedGeneName());
			if (this.buildingPathway.length() > 1) 
				{ this.createPathWay.setEnabled(true); }
		} else if (source.equals(this.createPathWay)) {
			/** Creating+Recording current Pathway. */
			String pathwayName			= this.pathwayName.getText();
			StringListe genesNamesList	= this.buildingPathway.getStringListe();
			if ( (!pathwayName.equals("")) && (genesNamesList.length() > 1) ) {
				/** Name ; number of genes ; \n[\tgene\n]+ */
				String compiledPathway = pathwayName+"\t"+genesNamesList.length();
				
				for (int i = 0 ; i < genesNamesList.length() ; i++) {
					String tmpGeneLine = this.geneSelection.getGeneString
											(genesNamesList.getString(i));
					if (!tmpGeneLine.equals(""))
						{ compiledPathway += "\t"+tmpGeneLine; }
				}
				this.pathSelection.addPathway(compiledPathway);
				this.pathwayName.setText("");
				this.buildingPathway.setStringListe(new StringListe());
			}
		}
		this.geneSelection.initSelection();
		if (this.buildingGene != null) 
			{ this.buildingGene.setStringListe(this.geneSelection.getGeneNames()); }
		
	}
	
	public void enableCenterPanel(boolean b) {
		this.createGene.setEnabled(b);
		this.changeGene.setEnabled(b);
		this.makeneGene.setEnabled(b);
	}

	public void enableEasternPanel(boolean b) {
		this.addGene2pathway.setEnabled(b);
		this.createPathWay.setEnabled(b);
	}

	public void enableNorthernPanel(boolean b)	{ ; }
	public void enableSouthPanel(boolean b)		{ ; }
	public void enableWesternPanel(boolean b)	{ ; }
	
}

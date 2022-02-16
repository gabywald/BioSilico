package gabywald.biosilico.view.genecreator;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import gabywald.biosilico.view.GeneKitJFrame;
import gabywald.biosilico.view.GeneKitsGBJPanel;
import gabywald.biosilico.view.GeneListJScroll;
import gabywald.biosilico.view.GeneParametersViewer;

/**
 * This class defines a graphical view to create and manipulate Gene's. 
 * <br><i>Design-Pattern Singleton. </i>
 * @author Gabriel Chandesris (2010, 2020, 2022)
 * @see GeneCreatorActionListener
 */
@SuppressWarnings("serial")
public class GeneCreatorJFrame extends GeneKitJFrame {
	
	private GeneCreatorActionListener localActionListener = null;
	
	/** Unique instance of this view. */
	private static GeneCreatorJFrame instance = null;
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
	private GeneCreatorJFrame() {
		
		this.localActionListener = new GeneCreatorActionListener( this );
		super.setActionListener( this.localActionListener );
		
		/** Center JPanel */
		this.initCenterPanel();
		/** Western JPanel */
		this.initWesternPanel();
		/** Eastern JPanel */
		this.initEasternPanel();
		/** Positions in content of JFrame */
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(this.centerPanel, BorderLayout.CENTER);
		this.getContentPane().add(this.westernPanel, BorderLayout.WEST);
		this.getContentPane().add(this.easternPanel, BorderLayout.EAST);
		this.setTitle("Gene Creator");
		this.setVisible(true);
	}
	
	/**
	 * To get the current instance of graphical view. 
	 * @return (GeneCreatorJFrame)
	 */
	public static GeneCreatorJFrame getInstance() {
		if (GeneCreatorJFrame.instance == null) 
			{ GeneCreatorJFrame.instance = new GeneCreatorJFrame(); }
		return GeneCreatorJFrame.instance;
	}
	
	private void initCenterPanel() {
		
		this.enableCenterPanel(false);
		
		this.createGene.addActionListener( this.localActionListener );
		this.changeGene.addActionListener( this.localActionListener );
		this.makeneGene.addActionListener( this.localActionListener );
		
		this.geneName = new JTextField();
		
		this.centerPanel = new GeneKitsGBJPanel();
		this.centerPanel.setPreferredSize(new Dimension(800, 600));
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
		this.deleteGene.addActionListener( this.localActionListener );
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
		
		this.addGene2pathway.addActionListener( this.localActionListener );
		// this.createGene.addActionListener( this.localActionListener );
		this.createPathWay.addActionListener( this.localActionListener );
		
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

	public GeneParametersViewer getParameterViewer() 
		{ return this.parameterViewer; }

	public JTextField getGeneName() 
		{ return this.geneName; }

	public Component getAddGene2Pathway() 
		{ return this.addGene2pathway; }

	public JButton getDeleteGene() 
		{ return this.deleteGene; }

	public GeneListJScroll getBuildingGene() 
		{ return this.buildingGene; }

	public GeneListJScroll getBuildingPathway() 
		{ return this.buildingPathway; }

	public JButton getCreatePathway() 
		{ return this.createPathWay; }

	public JTextField getPathwayName() 
		{ return this.pathwayName; }

}

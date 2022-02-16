package gabywald.biosilico.view.organismkit;

import javax.swing.JLabel;

import gabywald.biosilico.structures.GeneMoreListe;
import gabywald.biosilico.structures.PathwayListe;
import gabywald.biosilico.view.GeneKitsGBJPanel;
import gabywald.biosilico.view.GeneListJScroll;
import gabywald.biosilico.view.LineageListJScroll;
import gabywald.global.view.graph.GenericJFrame;

/**
 * This class defines a graphical view to manipulate precisely an Organism. 
 * <br><i>Design-Pattern Singleton. </i>
 * @author Gabriel Chandesris (2010, 2020, 2022)
 * TODO adding chromosome separation
 * TODO changing name(s)
 * TODO test organism
 */
@SuppressWarnings("serial")
public class OrganismKit extends GenericJFrame {
	/** Unique instance of this view. */
	private static OrganismKit instance = null;
	/** North Panel : to open a file or create an agent from scratch. */
	private OrganismSelectJPanel northPanel;
	/** Interaction for outputing result (save file || test). */
	private OrganismSaveJPanel southPanel;
	/** JPanel in West / Left of the JFrame. */
	private GeneKitsGBJPanel westernPanel;
	/** To view list of Gene's. */
	private GeneListJScroll buildingGene;
	
	/** The stock of Gene's. */
	private GeneMoreListe geneStock;
	/** The stock of Pathway's. */
	private PathwayListe pathStock;

	/** JPanel in center of the JFrame : organism names. */
	private OrganismNamesJPanel centerPanel;
	
	/** JPanel in East / right of the JFrame. */
	private GeneKitsGBJPanel easternPanel;
	/** To view list of lineage. */
	private LineageListJScroll buildingLineage;
	
	private OrganismKit() {
		this.initGenePath();
		this.centerPanel		= new OrganismNamesJPanel();
		this.initWesternPanel();
		this.initEasternPanel();
		this.initNorthPanel();
		this.initSouthPanel();
		
		this.getContentPane().add(this.northPanel, 		"North");
		this.getContentPane().add(this.southPanel, 		"South");
		this.getContentPane().add(this.westernPanel, 	"West");
		this.getContentPane().add(this.easternPanel, 	"East");
		this.getContentPane().add(this.centerPanel, 	"Center");
		this.setTitle("Organism Kit");
		this.setVisible(true);
	}
	
	/**
	 * To get the current instance of graphical view. 
	 * @return (OrganismKit)
	 */
	public static OrganismKit getInstance() {
		if (OrganismKit.instance == null) 
			{ OrganismKit.instance = new OrganismKit(); }
		return OrganismKit.instance;
	}
	
	private void initGenePath() {
		this.geneStock = new GeneMoreListe();
		this.geneStock.readFile();
		this.pathStock = new PathwayListe();
		this.pathStock.readFile();
	}
	
	private void initNorthPanel() {
		this.northPanel = new OrganismSelectJPanel();
		this.northPanel.setMainJFrame(this);
		this.northPanel.setGeneScroll(this.buildingGene);
		this.northPanel.setLineageScroll(this.buildingLineage);
		this.northPanel.setOrganismNamesPanel(this.centerPanel);
	}
	
	private void initSouthPanel() {
		this.southPanel = new OrganismSaveJPanel();
		this.southPanel.setMainJFrame(this);
		this.southPanel.setGeneScroll(this.buildingGene);
		this.southPanel.setLineageScroll(this.buildingLineage);
		this.southPanel.setGeneStock(this.geneStock);
		this.southPanel.setPathStock(this.pathStock);
		this.southPanel.setOrgSelectPanel(this.northPanel);
		this.southPanel.setOrganismNamesPanel(this.centerPanel);
	}
	
	private void initWesternPanel() {
		this.buildingGene	= new GeneListJScroll();

		this.westernPanel	= new GeneKitsGBJPanel();
		this.westernPanel.addBagComponent(new JLabel("\t\t")					, 0, 0, 2);
		this.westernPanel.addBagComponent(new JLabel("Genes List")				, 0, 1, 2);
		this.westernPanel.addBagComponent(this.buildingGene						, 0, 2, 2);
		this.westernPanel.addBagComponent(this.buildingGene.getUppeButton()		, 0, 3);
		this.westernPanel.addBagComponent(this.buildingGene.getDownButton()		, 1, 3);
		this.westernPanel.addBagComponent(this.buildingGene.getAddChromDelim()	, 0, 4, 2);
		this.enableWesternPanel(false);
	}
	
	private void initEasternPanel() {
		this.buildingLineage	= new LineageListJScroll();

		this.easternPanel		= new GeneKitsGBJPanel();
		this.easternPanel.addBagComponent(new JLabel("Lineage")					, 0, 0, 2);
		this.easternPanel.addBagComponent(this.buildingLineage					, 0, 1, 2);
		this.easternPanel.addBagComponent(this.buildingLineage.getAddButton()	, 0, 2);
		this.easternPanel.addBagComponent(this.buildingLineage.getRemButton()	, 1, 2);
		this.easternPanel.addBagComponent(this.buildingLineage.getUppButton()	, 0, 3);
		this.easternPanel.addBagComponent(this.buildingLineage.getDowButton()	, 1, 3);
		this.enableEasternPanel(false);
	}

	public void enableCenterPanel(boolean b)	
		{ this.centerPanel.enablePanel(b); }
	public void enableEasternPanel(boolean b)	
		{ this.buildingLineage.setEnabled(b); }
	public void enableNorthernPanel(boolean b)	{ 
		this.northPanel.enablePanel(b);
		this.enableWesternPanel(!b);
		this.enableEasternPanel(!b);
		this.enableSouthPanel(!b);
		this.enableCenterPanel(!b);
	}
	public void enableSouthPanel(boolean b)		
		{ this.southPanel.enablePanel(b); }
	public void enableWesternPanel(boolean b)	
		{ this.buildingGene.setEnabled(b); }

}

package gabywald.biosilico.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Genetic Kit Genuine User Interface (GUI).
 * This class defines manipulation of an organism about its Gene's (add / remove, pathways...). 
 * <br><i>Design-Pattern Singleton. </i>
 * @author Gabriel Chandesris (2009-2010, 2020)
 * @see GeneticKitActionListener
 */
@SuppressWarnings("serial")
public class GeneticKitJFrame extends GeneKitJFrame {
	
	private GeneticKitActionListener localActionListener = null;
	
	/** Unique instance of this view. */
	private static GeneticKitJFrame instance = null;
	/** North Panel : to open a file or create an agent from scratch. */
	private OrganismSelectJPanel northPanel;
	/** Center JPanel : "agent viewer". */
	private GeneKitsGBJPanel centerPanel;
	/** To add a pathway in current organism. */
	private JButton addPathWay;
	/** Western JPanel : list of genes of the agent. */
	private GeneKitsGBJPanel westernPanel;
	/** Scrolling into the list of genes of current agent.  */
	private GeneListJScroll geneScroll;
	/** Interaction for outputing result (save file || test). */
	private OrganismSaveJPanel southPanel;

	private GeneticKitJFrame() {
		
		this.localActionListener = new GeneticKitActionListener( this );
		super.setActionListener( this.localActionListener );
		
		/** Western JPanel */
		this.initWesternPanel();
		/** Center JPanel */
		this.initCenterPanel();
		/** Northern JPanel */
		this.northPanel = new OrganismSelectJPanel();
		this.northPanel.setMainJFrame(this);
		this.northPanel.setGeneScroll(this.geneScroll);
		/** South JPanel */
		this.southPanel = new OrganismSaveJPanel();
		this.southPanel.setMainJFrame(this);
		this.southPanel.setGeneScroll(this.geneScroll);
		this.southPanel.setGeneStock(this.geneSelection.getGeneStock());
		this.southPanel.setPathStock(this.pathSelection.getPathStock());
		this.southPanel.setOrgSelectPanel(this.northPanel);
		/** Positions in content of JFrame */
		this.getContentPane().add(this.westernPanel, BorderLayout.WEST);
		this.getContentPane().add(this.northPanel, BorderLayout.NORTH);
		this.getContentPane().add(this.southPanel, BorderLayout.SOUTH);
		this.getContentPane().add(this.centerPanel, BorderLayout.CENTER);
		this.setTitle("Genetic Kit");
		this.setVisible(true);
	}
	
	private void initWesternPanel() {
		this.geneScroll		= new GeneListJScroll();
		this.westernPanel	= new GeneKitsGBJPanel();
		
		this.enableWesternPanel(false);
		
		this.westernPanel.addBagComponent(new JLabel("Genes List")			, 0, 0);
		this.westernPanel.addBagComponent(this.geneScroll					, 0, 1);
		this.westernPanel.addBagComponent(this.geneScroll.getRemoveButton()	, 0, 2);
	}
	
	private void initCenterPanel() {
		// this.metabWaySelection	= new JComboBox(GeneticKit.metabolicListe);
		this.addPathWay	= new JButton("Add Metabolic Way to Agent");
		
		this.addsavGene.addActionListener( this.localActionListener  );
		this.addPathWay.addActionListener( this.localActionListener  );
		
		this.enableCenterPanel(false);
		this.createGene.setEnabled(false);
		this.addsavGene.setEnabled(false);
		this.addPathWay.setEnabled(false);

		this.initCenterPositions();
	}
	
	private void initCenterPositions() {
		this.centerPanel	= new GeneKitsGBJPanel();
		this.centerPanel.addBagComponent(this.geneSelection, 0, 0, 3);
		this.centerPanel.addBagComponent(this.geneTypeSelection, 0, 1);
		this.centerPanel.addBagComponent(this.createGene, 1, 1);
		this.centerPanel.addBagComponent(this.addsavGene, 2, 1);
		this.centerPanel.addBagComponent(this.parameterViewer, 0, 2, 3);
		this.centerPanel.addBagComponent(new JPanel(), 0, 3, 3);
		this.centerPanel.addBagComponent(this.pathSelection, 0, 4);
		this.centerPanel.addBagComponent(new GeneKitsGBJPanel(), 1, 4);
		this.centerPanel.addBagComponent(this.addPathWay, 2, 4);
		// this.agentViewer.addBagComponent(new JPanel(), 0, 4, 4);
		
//		JTextArea moreText = new JTextArea();
//		moreText.setEditable(false);
//		moreText.setColumns(30);
//		moreText.setRows(30);
//		moreText.setBackground(Color.LIGHT_GRAY);
//		moreText.setAlignmentX(5.0f);
//		moreText.setAlignmentY(5.0f);
//		GeneKitsGBJPanel more = new GeneKitsGBJPanel();
//		more.addBagComponent(moreText, 0, 0);
//		more.setBackground(Color.BLACK);
		
		this.centerPanel.addBagComponent(new JLabel(" "), 4, 0, 1, 2);
	}
	
	/**
	 * To get the current instance of graphical view. 
	 * @return (GeneticKit)
	 */
	public static GeneticKitJFrame getInstance() {
		if (GeneticKitJFrame.instance == null) 
			{ GeneticKitJFrame.instance = new GeneticKitJFrame(); }
		return GeneticKitJFrame.instance;
	}
	
	public void enableEasternPanel(boolean b)	{ ; }
	
	public void enableNorthernPanel(boolean b)
		{ this.northPanel.enablePanel(b); }
	
	public void enableWesternPanel(boolean b) {
		this.westernPanel.setEnabled(b);
		this.geneScroll.enableItemList(b);
		this.geneScroll.setEnabled(b);
	}
	
	public void enableSouthPanel(boolean b) 
		{ this.southPanel.enablePanel(b); }
	
	public void enableCenterPanel(boolean b) {
		this.geneTypeSelection.setEnabled(b);
		this.pathSelection.setEnabled(b);
		this.geneSelection.setEnabled(b);
	}

	public JButton getAddPathway() 
		{ return this.addPathWay; }

	public GeneListJScroll getGeneScroll() 
		{ return this.geneScroll; }

}



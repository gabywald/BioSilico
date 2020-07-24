package gabywald.biosilico.view;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gabywald.biosilico.structures.Pathway;

/**
 * Genetic Kit Genuine User Interface (GUI).
 * This class defines manipulation of an organism about its Gene's (add / remove, pathways...). 
 * <br><i>Design-Pattern Singleton. </i>
 * @author Gabriel Chandesris (2009-2010, 2020)
 * TODO export controller part from this view !!
 */
@SuppressWarnings("serial")
public class GeneticKit extends GeneKitJFrame {
	/** Unique instance of this view. */
	private static GeneticKit instance = null;
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

	private GeneticKit() {
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
		this.getContentPane().add(this.westernPanel,"West");
		this.getContentPane().add(this.northPanel,"North");
		this.getContentPane().add(this.southPanel,"South");
		this.getContentPane().add(this.centerPanel,"Center");
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
		
		this.addsavGene.addActionListener(this);
		this.addPathWay.addActionListener(this);
		
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
	public static GeneticKit getInstance() {
		if (GeneticKit.instance == null) 
			{ GeneticKit.instance = new GeneticKit(); }
		return GeneticKit.instance;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		if (source.equals(this.geneTypeSelection)) {
			int type = this.geneTypeSelection.getSelectedIndex();
			this.parameterViewer.selectCard(type);
			if (type != 0) {
				// this.sendMessage(this.geneTypeSelection.getSelectedIndex()+" G");
				this.addsavGene.setEnabled(false);
				// this.addsavGene.setEnabled(true);
				this.addPathWay.setEnabled(false);
				this.pathSelection.setSelection(0);
				this.geneSelection.setSelection(0);
			} else { 
				this.addsavGene.setEnabled(false);
				this.pathSelection.setSelection(0);
				this.geneSelection.setSelection(0);
			}
		} else if (source.equals(this.pathSelection)) {
			// ***** Selection in menu of previous defined pathways. 
			// this.selectedPath = this.pathSelection.getSelectedIndex();
			if (this.pathSelection.getSelected() > 0) {
				// this.sendMessage(this.pathSelection.getSelectedIndex()+" M");
				this.addPathWay.setEnabled(true);
				this.addsavGene.setEnabled(false);
				this.parameterViewer.selectCard(0);
				this.setGeneTypeSelection(0);
				this.geneSelection.setSelection(0);
			} else { 
				this.addPathWay.setEnabled(false);
				this.setGeneTypeSelection(0);
				this.geneSelection.setSelection(0);
			}
		} else if (source.equals(this.geneSelection)) {
			// ***** Selection in menu of previous defined genes. 
			// this.selectedGene = this.geneSelection.getSelectedIndex();
			if (this.geneSelection.getSelected() > 0) {
				// System.out.println(this.geneSelection.getSelected() );
				int geneType = this.geneSelection.getSelectedType();
				this.parameterViewer.selectCard(geneType);
				this.parameterViewer.setCompiledParameters
							(this.geneSelection.getSelectedGene(),
							geneType);
				this.setGeneTypeSelection(geneType);
				this.addsavGene.setEnabled(true);
				this.addPathWay.setEnabled(false);
				this.pathSelection.setSelection(0);
			} else {
				this.addsavGene.setEnabled(false);
				this.parameterViewer.selectCard(0);
				this.setGeneTypeSelection(0);
				this.pathSelection.setSelection(0);
			}
		} else if (source.equals(this.addsavGene)) {
			// ***** Selection in menu of previous defined genes. 
			// this.selectedGene = this.existentSelection.getSelectedIndex();
			if (this.geneSelection.getSelected() > 0) {
				// this.sendMessage("selected "+this.selectedGene);
				this.geneScroll.addString(this.geneSelection.getSelectedGeneName());
				this.enableWesternPanel(true);
				this.enableSouthPanel(true);
			} else { ; }
		} else if (source.equals(this.addPathWay)) {
			// ***** Selection in menu of previous defined pathways. 
			// this.selectedPath = this.pathSelection.getSelectedIndex();
			if (this.pathSelection.getSelected() > 0) {
				Pathway pathSelected = this.pathSelection.getSelectedPathway();
				for (int i = 0 ;  i < pathSelected.length() ; i++) 
					{ this.geneScroll.addString(pathSelected.getGeneName(i)); }
				this.enableWesternPanel(true);
				this.enableSouthPanel(true);
			} else { ; }
		}
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
	
}



package gabywald.biosilico.view.organismkit;

import gabywald.biosilico.data.FileOrganism;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.utils.Sequence;
import gabywald.biosilico.view.BioSilicoViewUtils;
import gabywald.biosilico.view.GeneKitsGBJPanel;
import gabywald.biosilico.view.LineageListJScroll;
import gabywald.global.view.graph.GenericJScroll;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * To open a file or create an agent from scratch, aim is to be on north / top of a JFrame.
 * @author Gabriel Chandesris (2010, 2020, 2022)
 * @see OrganismSelectActionListener
 * @see gabywald.biosilico.view.genetickit.GeneticKitJFrame
 * @see OrganismKit
 */
@SuppressWarnings("serial")
public class OrganismSelectJPanel extends GeneKitsGBJPanel {
	
	private OrganismSelectActionListener localActionListener = null;
	
	/** Button to load a file of an agent. */
	private JButton openAgentFile;
	/** Button to create an agent. */
	private JButton createAgent;
	
	/** Selection list of agent's type. */
	private JComboBox<String> agentTypeSelection;
	/** Field for the name of the agent. */
	private JTextField agentName;
	/** Gene list for a view on a organism. */
	private GenericJScroll geneScroll;
	/** Lineage for a view on a organism. */
	private LineageListJScroll lineageScroll;
	/** Link to panel of names of the organism (could be null). */
	private OrganismNamesJPanel orgNamesPanel;
	/** Instance of FileOrganism use when creation or selection. */
	private FileOrganism toLoad;
	
	/** Default Constructor. */
	public OrganismSelectJPanel() { this.init(); }

	private void init() {
		
		this.localActionListener = new OrganismSelectActionListener( this );
		
		this.openAgentFile		= new JButton("Open Agent File...");
		this.createAgent		= new JButton("Create an Agent");
		this.agentName			= new JTextField(20);
		this.agentTypeSelection	= new JComboBox<String>(BioSilicoViewUtils.agentTypeListe);

		this.openAgentFile.addActionListener( this.localActionListener );
		this.createAgent.addActionListener( this.localActionListener );

		this.enablePanel(true);

		this.addBagComponent(new JLabel("\t")				, 0, 0, 5);
		this.addBagComponent(this.openAgentFile				, 0, 1);
		this.addBagComponent(this.agentTypeSelection		, 1, 1);
		this.addBagComponent(this.createAgent				, 2, 1);
		this.addBagComponent(new JLabel("Name of agent : ")	, 3, 1);
		this.addBagComponent(this.agentName					, 4, 1);
	}

	public void enablePanel(boolean b) {
		this.openAgentFile.setEnabled(b);
		this.agentTypeSelection.setEnabled(b);
		this.createAgent.setEnabled(b);
		this.agentName.setEditable(b);
	}

	/**
	 * 
	 * @param type (int)
	 */
	void actionCreateAgent(int type) {
		String name = this.getNameOrganism();
		
		if (name.equals("")) { name = "anonymous"; }
		
		name = BioSilicoViewUtils.agentTypeNameListe[type] + name;
		
		if (this.orgNamesPanel != null) {
			this.orgNamesPanel.setScientificName(name);
			this.orgNamesPanel.setBioSilicoName(name);
		}
		/** Disable the North Panel */
		this.setNameOrganism(name);
		this.enablePanel(false);
		/** Enable other elements */
		if (this.mainFrame != null) { 
			this.mainFrame.enableNorthernPanel(false);
			this.mainFrame.enableCenterPanel(true);
		}
		
		String completePathFile =  BioSilicoViewUtils.BASE_ORGANISM_DIR_FILECHOOSER ;
		completePathFile += this.getNameOrganismUnited()+".gatorg";
		Organism orga = new Organism();
		orga.setNameScientific(name);
		orga.setNameBiosilico(name);
		this.toLoad = new FileOrganism(completePathFile,orga);
	}

	/**
	 * 
	 */
	void loadOrganism() {
		this.toLoad = BioSilicoViewUtils.loadOrganism(this.geneScroll);
		
		if (this.toLoad == null) {
			JOptionPane.showMessageDialog(this,
				    "File not found. ",
				    "Specific Error",
				    JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		this.agentName.setText(this.toLoad.getBioSilicoName());
		// ***** Gene's Scroll Treament 
		if (this.geneScroll != null) {
			for (int i = 0 ; i < this.toLoad.lengthListe() ; i++) {
				Sequence seqTmp = this.toLoad.getListe().get(i);
				this.geneScroll.addString(seqTmp.getNom());
			}
			if (this.geneScroll.length() > 0) 
				{ this.geneScroll.enableItemList(true); }
		}
		/** Lineage's Scroll Treatment */
		if (this.lineageScroll != null) {
			for (int i = 0 ; i < this.toLoad.lineageSize() ; i++) {
				this.lineageScroll.addString(this.toLoad.getSimpleLinage(i));
			}
			if (this.lineageScroll.length() > 0) 
				{ this.lineageScroll.enableItemList(true); }
		}
		// ***** Names' Treatment ! 
		if (this.orgNamesPanel != null) {
			List<String> allNames = this.toLoad.getAllNames();
			this.orgNamesPanel.setScientificName(allNames.get(0));
			this.orgNamesPanel.setBioSilicoName(allNames.get(1));
			this.orgNamesPanel.setCommonName(allNames.get(2));
			this.orgNamesPanel.setIncludeName(allNames.get(3));
			// ***** Removing the first 4 items. 
			for (int i = 0 ; i < 4 ; i++) { allNames.remove(0); }
			// ***** Remaining are others names. 
			this.orgNamesPanel.setOtherNames(allNames);
		}
		// ***** End Treatment 
		this.enablePanel(false);
		// ***** Enable other elements 
		if (this.mainFrame != null) { 
			this.mainFrame.enableNorthernPanel(false);
			this.mainFrame.enableCenterPanel(true);
		}
	}

	public void setNameOrganism(String name) 
		{ this.agentName.setText(name); }

	public String getNameOrganism() 
		{ return this.agentName.getText(); }

	/**
	 * To get the name without spaces. 
	 * @return (String)
	 */
	public String getNameOrganismUnited() {
		String name = this.getNameOrganism();
		String[] parts = name.split(" ");
		name = new String("");
		for (int i = 0 ; i < parts.length ; i++) 
			{ name += parts[i]; }
		return name;
	}

	public int getTypeOrganism() 
		{ return this.agentTypeSelection.getSelectedIndex(); }

	public void setGeneScroll(GenericJScroll scroll)
		{ this.geneScroll = scroll; }

	public void setLineageScroll(LineageListJScroll scroll)
		{ this.lineageScroll = scroll; }

	public void setOrganismNamesPanel(OrganismNamesJPanel names) 
		{ this.orgNamesPanel = names; }
	
	
	public FileOrganism getLoadOrganism() 
		{ return this.toLoad; }

	public JButton getOpenAgentFile() 
		{ return this.openAgentFile; }

	public JButton getCreateAgent() 
		{ return this.createAgent; }

	public int getAgentTypeSelected() 
		{ return this.agentTypeSelection.getSelectedIndex(); }

}

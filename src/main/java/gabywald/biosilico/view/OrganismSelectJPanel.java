package gabywald.biosilico.view;

import gabywald.biosilico.data.FileBiological;
import gabywald.biosilico.data.FileOrganism;
import gabywald.biosilico.data.FilterBioSilico;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.utils.Sequence;
import gabywald.global.structures.StringListe;
import gabywald.global.view.graph.GenericJScroll;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * To open a file or create an agent from scratch, aim is to be on north / top of a JFrame.
 * @author Gabriel Chandesris (2010)
 * @see GeneticKit
 * @see OrganismKit
 */
public class OrganismSelectJPanel extends GeneKitsGBJPanel {
	/** Button to load a file of an agent. */
	private JButton openAgentFile;
	/** Button to create an agent. */
	private JButton createAgent;
	/** This is used by {@link GeneticKit#agentTypeSelection}. */
	private static final String agentTypeListe[] = 
	{ "Organism", "-Daemon", "-Bacta", "-Viridita", "-Anima", "-Viria" };
	/** Selection list of agent's type. */
	private JComboBox agentTypeSelection;
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
	
	/** Default Constructor (self-ActionListener). */
	public OrganismSelectJPanel() { this.init(this); }

	/**
	 * Constrcutor with given ActionListener implementation. 
	 * @param act (ActionListener)
	 * @deprecated [auto-listener]
	 */
	public OrganismSelectJPanel(ActionListener act) { this.init(act); }

	private void init(ActionListener act) {
		this.openAgentFile		= new JButton("Open Agent File...");
		this.createAgent		= new JButton("Create an Agent");
		this.agentName			= new JTextField(20);
		this.agentTypeSelection	= new JComboBox(OrganismSelectJPanel.agentTypeListe);

		this.openAgentFile.addActionListener(act);
		this.createAgent.addActionListener(act);

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

	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		if (source.equals(this.openAgentFile)) 
			{ this.loadOrganism(); }
		else if (source.equals(this.createAgent)) {
			int type = this.agentTypeSelection.getSelectedIndex();
			if (type != 0) { this.actionCreateAgent(type); }
		}
	}

	private void actionCreateAgent(int type) {
		String name = this.getNameOrganism();
		if (name.equals("")) { name = "anonymous"; }
		switch(type) {
		case(1):name = "SilicoDaemon "+name;break;
		case(2):name = "SilicoBacter "+name;break;
		case(3):name = "SilicoViridita "+name;break;
		case(4):name = "SilicoAnima "+name;break;
		case(5):name = "SilicoViria "+name;break;
		}
		
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
		
		String completePathFile = FileBiological.DEFAULT_PATH_NAME;
		completePathFile += this.getNameOrganismUnited()+".gatorg";
		Organism orga = new Organism();
		orga.setNameScientific(name);
		orga.setNameBiosilico(name);
		this.toLoad = new FileOrganism(completePathFile,orga);
	}

	private void loadOrganism() {
		JFileChooser chooser = new JFileChooser(FileBiological.DEFAULT_PATH_NAME);
		chooser.setFileFilter(new FilterBioSilico());
		chooser.setAcceptAllFileFilterUsed(false);

		int returnVal = chooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String completeFilePath = chooser.getSelectedFile().getAbsolutePath();
			String extension = FilterBioSilico.getExtension(completeFilePath);
			if (extension.equals(FilterBioSilico.gatorg)) {
				this.toLoad = new FileOrganism(completeFilePath);
				// System.out.println(toLoad);
				this.agentName.setText(this.toLoad.getBioSilicoName());
				/** Gene's Scroll Treament */
				if (this.geneScroll != null) {
					for (int i = 0 ; i < this.toLoad.lengthListe() ; i++) {
						Sequence seqTmp = this.toLoad.getListe().getSequence(i);
						this.geneScroll.addString(seqTmp.getNom());
					}
					if (this.geneScroll.length() > 0) 
						{ this.geneScroll.enableItemList(true); }
				}
				/** Lineage's Scroll Treatment */
				if (this.lineageScroll != null) {
					for (int i = 0 ; i < this.toLoad.lengthLineage() ; i++) {
						this.lineageScroll.addString(this.toLoad.getSimpleLinage(i));
					}
					if (this.lineageScroll.length() > 0) 
						{ this.lineageScroll.enableItemList(true); }
				}
				/** Names' Treatment ! */
				if (this.orgNamesPanel != null) {
					StringListe allNames = this.toLoad.getAllNames();
					this.orgNamesPanel.setScientificName(allNames.getString(0));
					this.orgNamesPanel.setBioSilicoName(allNames.getString(1));
					this.orgNamesPanel.setCommonName(allNames.getString(2));
					this.orgNamesPanel.setIncludeName(allNames.getString(3));
					/** Removing the first 4 items. */
					allNames.removeString(0);allNames.removeString(0);
					allNames.removeString(0);allNames.removeString(0);
					/** Remaining are others names. */
					this.orgNamesPanel.setOtherNames(allNames);
				}
				/** End Treatment */
				this.enablePanel(false);
				/** Enable other elements */
				if (this.mainFrame != null) { 
					this.mainFrame.enableNorthernPanel(false);
					this.mainFrame.enableCenterPanel(true);
				}
			}
		}
	}

	public static void loadOrganism(GeneListJScroll geneScroll,
									LineageListJScroll lineageScroll,
									OrganismSelectJPanel orgSelectPane) {
		JFileChooser chooser = new JFileChooser(FileBiological.DEFAULT_PATH_NAME);
		chooser.setFileFilter(new FilterBioSilico());
		chooser.setAcceptAllFileFilterUsed(false);

		int returnVal = chooser.showOpenDialog(orgSelectPane);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String completeFilePath = chooser.getSelectedFile().getAbsolutePath();
			String extension = FilterBioSilico.getExtension(completeFilePath);
			if (extension.equals(FilterBioSilico.gatorg)) {
				FileOrganism toLoad = new FileOrganism(completeFilePath);
				// System.out.println(toLoad);
				orgSelectPane.setName(toLoad.getBioSilicoName());
				/** Gene's Scroll Treament */
				if (geneScroll != null) {
					for (int i = 0 ; i < toLoad.lengthListe() ; i++) {
						Sequence seqTmp = toLoad.getListe().getSequence(i);
						geneScroll.addString(seqTmp.getNom());
					}
					if (geneScroll.length() > 0) 
					{ geneScroll.enableItemList(true); }
				}
				/** Lineage's Scroll Treatment */
				if (lineageScroll != null) {
					for (int i = 0 ; i < toLoad.lengthLineage() ; i++) {
						lineageScroll.addString
						(toLoad.getSimpleLinage(i));
					}
					if (lineageScroll.length() > 0) 
						{ lineageScroll.enableItemList(true); }
				}
				/** End Treatment */
				orgSelectPane.enablePanel(false);
				orgSelectPane.setMainFrameEnable(false, 3);
				orgSelectPane.setMainFrameEnable(false, 5);
			}
		}
	}

	public static FileOrganism loadOrganism(GenericJScroll parent) {
		JFileChooser chooser = new JFileChooser(FileBiological.DEFAULT_PATH_NAME);
		chooser.setFileFilter(new FilterBioSilico());
		chooser.setAcceptAllFileFilterUsed(false);

		int returnVal = chooser.showOpenDialog(parent);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String completeFilePath = chooser.getSelectedFile().getAbsolutePath();
			String extension = FilterBioSilico.getExtension(completeFilePath);
			if (extension.equals(FilterBioSilico.gatorg)) {
				FileOrganism toLoad = new FileOrganism(completeFilePath);
				return toLoad;
			}
		}
		return null;
	}
	
	/**
	 * To enable parts of the main Frame. 
	 * @param b (boolean)
	 * @param i (int)
	 * @see gabywald.global.view.graph.GenericJFrame#setEnable(boolean, int)
	 */
	private void setMainFrameEnable(boolean b, int i) {
		if (this.mainFrame != null) { this.mainFrame.setEnable(b, i); } 
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
	
	
	
	
	public FileOrganism getLoadOrganism() { return this.toLoad; }
}

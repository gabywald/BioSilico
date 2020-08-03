package gabywald.biosilico.view;

import gabywald.biosilico.data.FileBiological;
import gabywald.biosilico.data.FileOrganism;
import gabywald.biosilico.data.FilterBioSilico;
import gabywald.biosilico.genetics.Gene;
import gabywald.biosilico.model.Chromosome;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.Organism.OrganismType;
import gabywald.biosilico.structures.GeneMoreListe;
import gabywald.biosilico.structures.Pathway;
import gabywald.biosilico.structures.PathwayListe;
import gabywald.global.exceptions.DataException;
import gabywald.global.view.graph.GenericJFrame;

import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

/**
 * To save Organism's intance with all data included (especially data from selected or created. 
 * @author Gabriel Chandesris (2010, 2020)
 * @see GeneticKit
 * @see OrganismKit
 */
@SuppressWarnings("serial")
public class OrganismSaveJPanel extends GeneKitsGBJPanel {
	/** To save current organism. */
	private JButton saveOrganism;
	/** To test current organism. */
	private JButton testOrganism;
	
	private GeneListJScroll geneScroll;
	private LineageListJScroll lineageScroll;
	private GeneMoreListe geneStock;
	private PathwayListe pathStock;
	private OrganismSelectJPanel orgSelectPanel;
	private OrganismNamesJPanel orgNamesPanel;
	/** Instance of FileOrganism to save.  */
	private FileOrganism toSave;
	
	/** Default Constructor (self-ActionListener). */
	public OrganismSaveJPanel() { this.init(this); }
	
	/**
	 * Constructor with given ActionListener implementation. 
	 * @param act (ActionListener)
	 * @deprecated [auto-listener]
	 */
	public OrganismSaveJPanel(ActionListener act) { this.init(act); }
	
	public void init(ActionListener act) {
		this.saveOrganism	= new JButton("Save Agent");
		this.testOrganism	= new JButton("Test Agent");
		
		this.saveOrganism.addActionListener(act);
		this.testOrganism.addActionListener(act);
		this.enablePanel(false);
		
		this.setSize(GenericJFrame.WIDTH, 20);
		this.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		this.addBagComponent(this.saveOrganism, 0, 0);
		this.addBagComponent(new JLabel("\t"), 0, 1);
		// this.southPanel.add(this.testOrganism);
	}
	
	public void enablePanel(boolean b) {
		this.saveOrganism.setEnabled(b);
		this.testOrganism.setEnabled(b);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		if (source.equals(this.saveOrganism)) { this.saveOrganism(); }
	}
	
	public void setGeneStock(GeneMoreListe stock) 
		{ this.geneStock = stock; }
	public void setPathStock(PathwayListe stock)
		{ this.pathStock = stock; }
	public void setGeneScroll(GeneListJScroll scroll)
		{ this.geneScroll = scroll; }
	public void setLineageScroll(LineageListJScroll scroll)
		{ this.lineageScroll = scroll; }
	public void setOrgSelectPanel(OrganismSelectJPanel north)
		{ this.orgSelectPanel = north; }
	public void setOrganismNamesPanel(OrganismNamesJPanel names)
		{ this.orgNamesPanel = names; }
	
	
	private void saveOrganism() {
		// ***** Establishing genome (gene list) ; Genetic / Organism Kit. 
		List<Chromosome> currentGenome	= new ArrayList<Chromosome>();
		Chromosome currentChromosome	= new Chromosome();
		if (this.geneScroll != null) {
			List<String> genesNamesList	= this.geneScroll.getStringListe();
			Gene tmp = null;
			for (int i = 0 ; i < genesNamesList.size() ; i++) { 
				tmp = this.geneStock.getGene(genesNamesList.get(i));
				if (tmp == null) {
					for (int j = 0 ; (j < this.pathStock.length()) && (tmp == null) ; j++) {
						Pathway path = this.pathStock.getPathway(j);
						tmp = path.getGeneMoreListe().getGene(genesNamesList.get(i));
					}
				} 
				// if (tmp != null) { currentChromosome.addGene(tmp); }
				if ( (genesNamesList.get(i).matches("^-*$")) 
						|| (tmp == null) ) {
					currentGenome.add(currentChromosome);
					currentChromosome = new Chromosome();
				} else { currentChromosome.addGene(tmp); }
//				else { currentChromosome.addGene
//						(GeneGattaca.getInstance(genesNamesList.getString(i))); }
				tmp = null;
			}
		}
		if (currentChromosome.length() > 0) 
			{ currentGenome.add(currentChromosome); }

		// ***** Setting Organism and FileOrganism. 
		String pathAndFileName = this.saveToFile();
		if (pathAndFileName != null) {
			if (this.orgSelectPanel != null) {
				// ******* If select Panel : FileOrganism instance already exists ! 
				this.toSave = this.orgSelectPanel.getLoadOrganism();
				this.toSave.setGenome(currentGenome);
				this.toSave.getOrganism().setOrganismType(OrganismType.values()[this.orgSelectPanel.getTypeOrganism() - 1]);
			} else // ***** This case should not happen often. 
				{ this.toSave = new FileOrganism(pathAndFileName,
											new Organism(currentGenome)); }
			
			String name = this.orgSelectPanel.getNameOrganism();
			this.toSave.setNameScientific(name);
			this.toSave.setNameBiosilico(name);
			if (this.orgNamesPanel != null) {
				this.toSave.setNameScientific(this.orgNamesPanel.getScientificName());
				this.toSave.setNameBiosilico(this.orgNamesPanel.getBioSilicoName());
				this.toSave.setNameCommon(this.orgNamesPanel.getCommonName());
				this.toSave.setNameIncluded(this.orgNamesPanel.getIncludeName());
				for (int i = 0 ; i < this.orgNamesPanel.getOthersNames().size() ; i++) 
					{ this.toSave.addOtherName(this.orgNamesPanel
												.getOthersNames().get(i)); }
			}
			// ***** Establishing lineage (similar to gene list) if could be completed. 
			if (this.lineageScroll != null) 
				{ this.toSave.setExtendedLineage
							(this.lineageScroll.getExtendedLineage()); }
			// ***** Recording the FileOrganism. 
			try { this.toSave.printFile(); } 
			catch (DataException e) { e.printStackTrace(); }
		}
	}
	
	private String saveToFile() {
		String completePathFile = FileBiological.DEFAULT_PATH_NAME;
		if (this.orgSelectPanel != null) 
			{ completePathFile += this.orgSelectPanel.getNameOrganismUnited() + ".gatorg"; }
		System.out.println(completePathFile);
		JFileChooser saver = new JFileChooser(completePathFile);
		saver.setFileFilter(new FilterBioSilico());
		saver.setAcceptAllFileFilterUsed(false);
		saver.setDialogType(JFileChooser.SAVE_DIALOG);
		// ***** To set a proposed name of file. 
		saver.setSelectedFile(new java.io.File(completePathFile));
		
		int returnVal = saver.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String completeFilePath = saver.getSelectedFile().getAbsolutePath();
			int lastIndexExtension = completeFilePath.lastIndexOf(".gatorg");
			if (lastIndexExtension >= 0) 
				{ return completeFilePath.substring(0, lastIndexExtension); }
			else { return completeFilePath; }
		}
		// return FileBiological.DEFAULT_PATH_NAME+this.orgSelectPanel.getNameOrganismUnited();
		return null;
	}
	
}

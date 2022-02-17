package gabywald.biosilico.view;

import javax.swing.JFileChooser;

import gabywald.biosilico.data.FileBiological;
import gabywald.biosilico.data.FileOrganism;
import gabywald.biosilico.data.FilterBioSilico;
import gabywald.biosilico.model.enums.AgentType;
import gabywald.global.view.graph.GenericJScroll;

/**
 * 
 * @author Gabriel Chandesris (2022)
 */
public abstract class BioSilicoViewUtils {

	/** This is used by {@link gabywald.biosilico.view.genetickit.GeneticKitJFrame#agentTypeSelection}. */
	public static String agentTypeListe[] = null;
		// { "Organism", "-Daemon", "-Bacta", "-Viridita", "-Anima", "-Viria" };
	/** This is used by {@link gabywald.biosilico.view.organismkit.OrganismSelectJPanel#actionCreateAgent}. */
	public static String agentTypeNameListe[] = null;
		// { "", "SilicoDaemon", "SilicoBacter", "SilicoViridita", "SilicoAnima", "SilicoViria" };
	
	static {
		int atlength = AgentType.values().length;
		
		BioSilicoViewUtils.agentTypeListe = new String[atlength + 1];
		BioSilicoViewUtils.agentTypeListe[0] = "Organism";
		for (int i = 0 ; i < atlength ; i++) 
			{ BioSilicoViewUtils.agentTypeListe[i+1] = "-" + AgentType.getFrom( AgentType.starter + i).getName(); }
		
		BioSilicoViewUtils.agentTypeNameListe = new String[atlength + 1];
		BioSilicoViewUtils.agentTypeNameListe[0] = ""; // Empty String !
		for (int i = 0 ; i < atlength ; i++) 
			{ BioSilicoViewUtils.agentTypeNameListe[i+1] = AgentType.PREFIX + AgentType.getFrom( AgentType.starter + i).getName(); }
		
	}
	
	/** FileBiological.BASE_MAIN_DIR + FileBiological.DEFAULT_PATH_NAME : "src/main/resources" + "biological/data/" */
	public static final String BASE_ORGANISM_DIR_FILECHOOSER = FileBiological.BASE_MAIN_DIR + FileBiological.DEFAULT_PATH_NAME;

	/**
	 * Load an Organism's instance from a file. 
	 * @param parent (GenericJScroll) Componenet's reference for Dialog. 
	 * @return (FileOrganism)
	 */
	public static FileOrganism loadOrganism(GenericJScroll parent) {
		JFileChooser chooser = new JFileChooser( BioSilicoViewUtils.BASE_ORGANISM_DIR_FILECHOOSER );
		chooser.setFileFilter( FilterBioSilico.FILTER_ORGANISM_ONLY );
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
	
}

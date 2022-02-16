package gabywald.biosilico.view;

import javax.swing.JFileChooser;

import gabywald.biosilico.data.FileBiological;
import gabywald.biosilico.data.FileOrganism;
import gabywald.biosilico.data.FilterBioSilico;
import gabywald.biosilico.view.genetickit.GeneticKitJFrame;
import gabywald.biosilico.view.organismkit.OrganismSelectJPanel;
import gabywald.global.view.graph.GenericJScroll;

/**
 * 
 * @author Gabriel Chandesris (2022)
 */
public interface BioSilicoViewUtils {

	/** This is used by {@link GeneticKitJFrame#agentTypeSelection}. */
	public static final String agentTypeListe[] = 
		{ "Organism", "-Daemon", "-Bacta", "-Viridita", "-Anima", "-Viria" };
	/** This is used by {@link OrganismSelectJPanel#actionCreateAgent}. */
	public static final String agentTypeNameListe[] = 
		{ "", "SilicoDaemon", "SilicoBacter", "SilicoViridita", "SilicoAnima", "SilicoViria" };
	
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

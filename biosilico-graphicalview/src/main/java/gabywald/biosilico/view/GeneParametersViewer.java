package gabywald.biosilico.view;

import gabywald.biosilico.genetics.Gene;
import gabywald.global.view.graph.CardJPanel;

/**
 * This kind of JPanel provides a Card Layout JPanel about Gene's and their parameters. 
 *  <br><i>Design-Pattern Singleton. </i>
 *  @author Gabriel Chandesris (2010, 2020, 2022)
 */
@SuppressWarnings("serial")
public class GeneParametersViewer extends CardJPanel {
	/** Unique instance of this view. */
	private static GeneParametersViewer instance = null;
	
	/** Default Constructor. */
	private GeneParametersViewer() { 
		super(GeneParametersViewerEnum.getTypeNames(), GeneParametersViewerEnum.getTypePanels());
	}
	
	/**
	 * To get the current instance of GeneJPanel chooser. 
	 * @return (GeneParametersViewer)
	 */
	public static GeneParametersViewer getInstance() {
		if (GeneParametersViewer.instance == null) 
			{ GeneParametersViewer.instance = new GeneParametersViewer(); }
		return GeneParametersViewer.instance;
	}
	
	public void setCompiledParameters(Gene gene, int type) {
		// ***** code ; name ;  type ; mutate ; duplicate ; delete ; active ; minimal age ; maximal age ; sex ; mutation rate ; others... 
		((GeneJPanel<?>)this.getCard(type)).setPanelSpecificValueWithGene(gene);
	}
	
	public String getCompiledParameters(String geneName, int geneType) {
		// ***** code ; name ;  type ; mutate ; duplicate ; delete ; active ; minimal age ; maximal age ; sex ; mutation rate ; others...
		StringBuilder sbMiddle = new StringBuilder();
		sbMiddle.append(geneName).append("\t").append(geneType).append("\t")
				.append(((GeneJPanel<?>) this.getCard(geneType)).getPanelSpecificValueWithGene().toString());
		return sbMiddle.toString();
	}
}

package gabywald.biosilico.view;

/**
 * Default empty card then others, list of Card JPanel's (GeneKitsGBJPanel). 
 * <br/>This is used by {@link GeneKitJFrame#GeneKitJFrame()} and inheritant classes.
 * <br/>This is used by {@link GeneParametersViewer}. 
 * @author Gabriel Chandesris (2022)
 */
public enum GeneParametersViewerEnum {
	DEFAULT		(0, "Choose a type of Gene", 	new GeneKitsGBJPanel()), 
	INITCONC	(1, "InitialConcentration", 	new InitConJPanel()), 
	BIOCHREACT	(2, "BiochemicalReaction", 		new BiochemJPanel()), 
	BRAIN		(3, "BrainGene", 				new BrainGeJPanel()), 
	BRAINLOBE	(4, "BrainLobeGene", 			new BrainLoJPanel()), 
	EMITRECEP	(5, "EmitterReceptor", 			new EmitterJPanel()), 
	STIMDEC		(6, "StimulusDecision", 		new StimuluJPanel()), 
	INSTINCT	(7, "Instinct", 				new InstincJPanel());
	
	private int index;
	private String name;
	private GeneKitsGBJPanel panel;

	private GeneParametersViewerEnum(int index, String name, GeneKitsGBJPanel geneKitsGBJPanel) {
		this.index	= index;
		this.name	= name;
		this.panel	= geneKitsGBJPanel;
	}
	
	public int getIndex()				{ return this.index; }
	public String getName()				{ return this.name; }
	public GeneKitsGBJPanel getPanel()	{ return this.panel; }
	
	public static String[] getTypeNames() {
		String[] toReturn = new String[GeneParametersViewerEnum.values().length];
		int i = 0;
		for (GeneParametersViewerEnum item : GeneParametersViewerEnum.values()) 
			{ toReturn[i++] = item.name; }
		return toReturn;
	}
	
	public static GeneKitsGBJPanel[] getTypePanels() {
		GeneKitsGBJPanel[] toReturn = new GeneKitsGBJPanel[GeneParametersViewerEnum.values().length];
		int i = 0;
		for (GeneParametersViewerEnum item : GeneParametersViewerEnum.values()) 
			{ toReturn[i++] = item.panel; }
		return toReturn;
	}
}

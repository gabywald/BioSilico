package gabywald.biosilico.launchers;

import gabywald.biosilico.view.GeneCreator;

/**
 * Launcher of Genetic Kit view. 
 * <br><i>Design-Pattern Singleton. </i>
 * @author Gabriel Chandesris (2010)
 */
public class GeneCreatorLauncher {
	/** Unique instance of this launcher. */
	private static GeneCreatorLauncher instance = null;
	/** View of the controller. */
	private GeneCreator localView;
	
	/**
	 * To get the unique instance of the launcher. 
	 * @return (GenKit)
	 */
	public static GeneCreatorLauncher getInstance() {
		if (GeneCreatorLauncher.instance == null) 
			{ GeneCreatorLauncher.instance = new GeneCreatorLauncher(); }
		return GeneCreatorLauncher.instance;
	}
	
	public void setView(GeneCreator view) { this.localView = view; }
	public GeneCreator getView() { return this.localView; }
	
	/** 
	 * MAIN launch for this view. 
	 * @param args (String[]) not used.
	 */
	public static void main(String[] args) {
		GeneCreatorLauncher controller = GeneCreatorLauncher.getInstance();
		controller.setView(GeneCreator.getInstance());
	}
}

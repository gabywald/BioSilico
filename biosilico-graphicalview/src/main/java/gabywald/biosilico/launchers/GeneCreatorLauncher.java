package gabywald.biosilico.launchers;

import gabywald.biosilico.view.GeneCreatorJFrame;

/**
 * Launcher of Genetic Kit view. 
 * <br><i>Design-Pattern Singleton. </i>
 * @author Gabriel Chandesris (2010)
 */
public class GeneCreatorLauncher {
	/** Unique instance of this launcher. */
	private static GeneCreatorLauncher instance = null;
	/** View of the controller. */
	private GeneCreatorJFrame localView;
	
	/**
	 * To get the unique instance of the launcher. 
	 * @return (GeneCreatorLauncher)
	 */
	public static GeneCreatorLauncher getInstance() {
		if (GeneCreatorLauncher.instance == null) 
			{ GeneCreatorLauncher.instance = new GeneCreatorLauncher(); }
		return GeneCreatorLauncher.instance;
	}
	
	public void setView(GeneCreatorJFrame view) { this.localView = view; }
	public GeneCreatorJFrame getView() { return this.localView; }
	
	/** 
	 * MAIN launch for this view. 
	 * @param args (String[]) not used.
	 */
	public static void main(String[] args) {
		GeneCreatorLauncher controller = GeneCreatorLauncher.getInstance();
		controller.setView(GeneCreatorJFrame.getInstance());
	}
}

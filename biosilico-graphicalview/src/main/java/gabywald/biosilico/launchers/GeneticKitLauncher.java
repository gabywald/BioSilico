package gabywald.biosilico.launchers;

import gabywald.biosilico.view.GeneticKitJFrame;

/**
 * Launcher of Genetic Kit view. 
 * <br><i>Design-Pattern Singleton. </i>
 * @author Gabriel Chandesris (2009-2010)
 */
public class GeneticKitLauncher {
	/** Unique instance of this launcher. */
	private static GeneticKitLauncher instance = null;
	/** View of the controller. */
	private GeneticKitJFrame localView;
	
	/**
	 * To get the unique instance of the launcher. 
	 * @return (GeneticKitLauncher)
	 */
	public static GeneticKitLauncher getInstance() {
		if (GeneticKitLauncher.instance == null) 
			{ GeneticKitLauncher.instance = new GeneticKitLauncher(); }
		return GeneticKitLauncher.instance;
	}
	
	public void setView(GeneticKitJFrame view) { this.localView = view; }
	public GeneticKitJFrame getView() { return this.localView; }
	
	/** 
	 * MAIN launch for this view. 
	 * @param args (String[]) not used.
	 */
	public static void main(String[] args) {
		GeneticKitLauncher controller = GeneticKitLauncher.getInstance();
		controller.setView(GeneticKitJFrame.getInstance());
	}
}

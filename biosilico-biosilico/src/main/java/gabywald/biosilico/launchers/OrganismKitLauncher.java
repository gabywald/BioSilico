package gabywald.biosilico.launchers;

import gabywald.biosilico.view.OrganismKit;

/**
 * Launcher of Organism Kit View. 
 * <br><i>Design-Pattern Singleton. </i>
 * @author Gabriel Chandesris (2010)
 */
public class OrganismKitLauncher {
	/** Unique instance of this launcher. */
	private static OrganismKitLauncher instance = null;
	/** View of the controller. */
	private OrganismKit localView;
	
	/**
	 * To get the unique instance of the launcher. 
	 * @return (GenKit)
	 */
	public static OrganismKitLauncher getInstance() {
		if (OrganismKitLauncher.instance == null) 
			{ OrganismKitLauncher.instance = new OrganismKitLauncher(); }
		return OrganismKitLauncher.instance;
	}
	
	public void setView(OrganismKit view) { this.localView = view; }
	public OrganismKit getView() { return this.localView; }
	
	/** 
	 * MAIN launch for this view. 
	 * @param args (String[]) not used.
	 */
	public static void main(String[] args) {
		OrganismKitLauncher controller = OrganismKitLauncher.getInstance();
		controller.setView(OrganismKit.getInstance());
	}
}

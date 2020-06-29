package gabywald.global.view.graph;

/**
 * Model of Launcher / controller associated with GenericJFrame. 
 * <br><i>Design-Pattern Singleton. </i>
 * @author Gabriel Chandesris (2010)
 */
public class GenericLauncher {
	/** Unique instance of this launcher. */
	private static GenericLauncher instance = null;
	/** View of the controller. */
	private GenericJFrame localView;
	
	/**
	 * To get the unique instance of the launcher. 
	 * @return (GenKit)
	 */
	public static GenericLauncher getInstance() {
		if (GenericLauncher.instance == null) 
			{ GenericLauncher.instance = new GenericLauncher(); }
		return GenericLauncher.instance;
	}
	
	public void setView(GenericJFrame view) { this.localView = view; }
	public GenericJFrame getView() { return this.localView; }
	
	/** 
	 * MAIN launch for this view. 
	 * @param args (String[]) not used.
	 */
//	public static void main(String[] args) {
//		GenericLauncher controller = GenericLauncher.getInstance();
//		controller.setView(GenericJFrame.getInstance());
//	}
}

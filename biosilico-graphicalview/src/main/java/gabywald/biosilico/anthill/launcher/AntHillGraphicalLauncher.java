package gabywald.biosilico.anthill.launcher;

import gabywald.biosilico.anthill.data.AntHillGraphicalModel;
import gabywald.biosilico.anthill.data.AntHillGraphicalModelBuilder;
import gabywald.biosilico.anthill.view.AntHillGraphicalFrame;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * Launcher of AntHill (Graphical) View. 
 * <br><i>Design-Pattern Singleton. </i>
 * @author Gabriel Chandesris (2022)
 */
public class AntHillGraphicalLauncher {
	/** Unique instance of this launcher. */
	private static AntHillGraphicalLauncher instance = null;
	/** View of the controller. */
	private AntHillGraphicalFrame localView;
	/** Model of the controller */
	private AntHillGraphicalModel localModel;
	
	/**
	 * To get the unique instance of the launcher. 
	 * @return (AntHillGraphicalLauncher)
	 */
	public static AntHillGraphicalLauncher getInstance() {
		if (AntHillGraphicalLauncher.instance == null) 
			{ AntHillGraphicalLauncher.instance = new AntHillGraphicalLauncher(); }
		return AntHillGraphicalLauncher.instance;
	}
	
	public void setView(AntHillGraphicalFrame view)	{ this.localView = view; }
	public AntHillGraphicalFrame getView()			{ return this.localView; }
	
	public void setModel(AntHillGraphicalModel model)	{ this.localModel = model; }
	public AntHillGraphicalModel getModel()			{ return this.localModel; }
	
	/** 
	 * MAIN launch for this view. 
	 * @param args (String[]) not used.
	 */
	public static void main(String[] args) {
		
		Logger.setLogLevel(LoggerLevel.LL_WARNING);
		
		AntHillGraphicalLauncher controller	= AntHillGraphicalLauncher.getInstance();
		// AntHillGraphicalModelBase model	= AntHillGraphicalModelBase.getInstance();
		AntHillGraphicalModelBuilder ahgmb = new AntHillGraphicalModelBuilder();
		ahgmb.setWorldDimension(1, 1);
		ahgmb.addAnt("A");
		ahgmb.addPlant("P");
		ahgmb.setWorldDimension(10, 10);
		AntHillGraphicalModel model = ahgmb.build();
		controller.setModel( model );
		controller.setView(AntHillGraphicalFrame.getInstance( model ));
	}
	
}

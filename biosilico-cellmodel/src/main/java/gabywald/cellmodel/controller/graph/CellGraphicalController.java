package gabywald.cellmodel.controller.graph;

import gabywald.cellmodel.model.Cellule;
import gabywald.cellmodel.view.graph.CellGraphicalView;
import gabywald.utilities.others.Terminal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Controller and Launcher of the GUI of cell-modelisation.
 * <br>Design-Pattern <i>Singleton</i> 
 * @author Gabriel Chandesris (2009)
 */
public class  CellGraphicalController implements Observer {
	/** Unique instance of this controller / launcher. */
	private static CellGraphicalController instance;
	/** Instance of cell model. */
	private Cellule modele;
	/** Instance of graphical view. */
	private CellGraphicalView view;
	/** Instance of cell thread. */
	private Thread cellThread;
	/** Indicates if control or not. */
	private boolean control = true;
	
	/** Default constructor. */
	private CellGraphicalController() {
		this.modele = Cellule.getCelluleObservable();
		this.view = CellGraphicalView.getCellGraphicalView();
		this.modele.addObserver(this);
		this.modele.addObserver(this.view);
		this.view.getJButtonStartStop()
			.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) 
					{ CellGraphicalController.getCellController().action(); }
			});
		this.cellThread = new Thread(this.modele);
	}

	/**
	 * To get the instance of Controller. 
	 * @return (CellGraphicalController)
	 */
	public static CellGraphicalController getCellController() {
		if (CellGraphicalController.instance == null) 
			{ CellGraphicalController.instance = new CellGraphicalController(); }
		return CellGraphicalController.instance;
	}
	
	/** To launch the simulation. */
	public static void main(String[] args) 
		{ CellGraphicalController.getCellController(); }

	public void update(Observable arg0, Object arg1) { ; }
	
	
	public void action() {
		if (this.control) {
			if (!this.cellThread.isAlive()) { this.cellThread.start(); }
			else { this.modele.event(this.control); }
			this.control = false;
			this.view.getJButtonStartStop().setText("Stop");
		} else {
			this.modele.event(this.control);
			Terminal.ecrireStringln(this.control+" <= 1");
			this.control = true;
			Terminal.ecrireStringln(this.control+" <= 2");
			// this.view.getJButtonStartStop().setEnabled(this.control);
			this.view.getJButtonStartStop().setText("Continue");
		}
		Terminal.ecrireStringln("============");
		/**
		if (this.cellThread.isAlive()) 
			{ this.modele.event(this.control); } 
		else { this.cellThread.start(); }
		if (this.control) {
			this.control = false;
			this.view.getJButtonStartStop().setText("Stop");
		} else {
			this.control = true;
			this.view.getJButtonStartStop().setText("Start");
		}*/
		
		
		/**
		if (this.control) { 
			if (this.cellThread.isAlive()) {
				try { this.cellThread.resume(); } 
				catch (InterruptedException e) { e.printStackTrace(); }
			} else { this.cellThread.start(); }
			this.control = false;
			this.view.getJButtonStartStop().setText("Stop");
		} else {
			this.cellThread.suspend();
			this.control = true;
			this.view.getJButtonStartStop().setText("Start");
		}
		*/
	}
	
	public Thread getCellThread() { return this.cellThread; }
}

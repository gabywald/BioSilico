package gabywald.cellmodel.controller.text;

import gabywald.cellmodel.model.Cellule;
import gabywald.global.structures.ObservableObject;
import gabywald.global.view.text.Terminal;

import java.util.Observable;
import java.util.Observer;

/**
 * Test viewer and launcher of the cell modelisation. 
 * <br>Design-Pattern <i>Singleton</i> 
 * @author Gabriel Chandesris (2009)
 */
public class CellModelView implements Observer {
	/** Unique instance of this controller / launcher. */
	private static CellModelView instance = null;
	
	/** 
	 * Constructor with given modelized cell. 
	 * @param cell (Cellule)
	 */
	private CellModelView(Cellule cell) { cell.addObserver(this); }
	
	/**
	 * To get instance of the text viewer. 
	 * @param cell (Cellule))
	 * @return (CellModelView)
	 */
	private static CellModelView getTextViewObservable(Cellule cell) {
		if (CellModelView.instance == null) 
			{ CellModelView.instance = new CellModelView(cell); }
		return CellModelView.instance;
	}

	/** To launch the simulation. */
	public static void main(String[] args) {
		Terminal.ecrireStringln("Launch Cell Model Text View");
		Cellule cell = Cellule.getCelluleObservable();
		CellModelView.getTextViewObservable(cell);
		Thread cellThread = new Thread(cell);
		cellThread.start();
	}
	
	public void update(Observable arg0, Object arg1) {
		ObservableObject obj_change = (ObservableObject)arg0;
		Terminal.ecrireStringln(obj_change.getState());
	}
}

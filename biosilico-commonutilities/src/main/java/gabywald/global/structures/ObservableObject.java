package gabywald.global.structures;

import java.util.Observable;

/**
 * This class defines objects which are runnable / threadable and mostly observable. 
 * <br>Useful for modelization and multi-thread work !
 * <br>basics elements are defined here, just to be extend !
 * @author Gabriel Chandesris (2009)
 */
public class ObservableObject 
		extends Observable 
		implements Runnable {
	/** For the state when changes. */
	private String currentState;
	
	/** Default constructor with empty state. */
	protected ObservableObject() { this.currentState = ""; }
	
	/** To send a signal of changes. */
	public void change() {
		this.setChanged();
		this.notifyObservers();
		this.clearChanged();
	}
	
	public String getState() { return this.currentState; }
	protected void setState(String newState) 
		{ this.currentState = newState; }
	protected void addState(String addState) 
		{ this.currentState += addState; }

	public void run() { this.currentState = "changement";this.change(); }

}

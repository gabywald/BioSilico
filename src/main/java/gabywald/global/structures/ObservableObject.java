package gabywald.global.structures;

import java.util.Observable;

/**
 * This class defines objects which are runnable / threadable and mostly observable. 
 * <br>Useful for modelization and multi-thread work !
 * <br>basics elements are defined here, just to be extend !
 * @author Gabriel Chandesris (2009)
 */
public class ObservableObject extends Observable implements Runnable {
	/** For the state when changes. */
	private String current_state;
	
	/** Default constructor with empty state. */
	protected ObservableObject() { this.current_state = ""; }
	
	/** To send a signal of changes. */
	public void change() {
		this.setChanged();
		this.notifyObservers();
		this.clearChanged();
	}
	
	public String getState() { return this.current_state; }
	protected void setState(String new_state) 
		{ this.current_state = new_state; }
	protected void addState(String add_state) 
		{ this.current_state += add_state; }

	public void run() { this.current_state = "changement";this.change(); }

}

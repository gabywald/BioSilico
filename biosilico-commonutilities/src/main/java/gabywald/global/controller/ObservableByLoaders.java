package gabywald.global.controller;

import java.util.Observable;

/**
 * This class defines a generic Observable element on which Graphical Interface reacts. 
 * @author Gabriel Chandesris (2011)
 */
public abstract class ObservableByLoaders extends Observable {
	/** Indicates modifications. Adds Observers if necessary. */
	public void changeAndNotify() {
		if (this.countObservers() == 0) {
			/** this.addObserver(new Observer()); */
		}
		this.setChanged();
		this.notifyObservers();
	}
}

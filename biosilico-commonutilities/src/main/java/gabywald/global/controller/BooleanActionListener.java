package gabywald.global.controller;

import java.awt.event.ActionListener;

/**
 * Specific Abstraction of ActionListener. 
 * @author Gabriel Chandesris (2011)
 */
public abstract class BooleanActionListener implements ActionListener {
	/** Conditionnal indicator for action. */
	protected boolean isAware;
	
	/**
	 * Constructor. 
	 * @param b (boolean)
	 */
	public BooleanActionListener(boolean b) 
		{ this.isAware = b; }
}

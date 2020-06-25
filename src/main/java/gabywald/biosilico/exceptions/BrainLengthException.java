package gabywald.biosilico.exceptions;

import gabywald.global.exceptions.GenericException;

/**
 * This Exception is generated if Brain encounter a problem of length. 
 * @author Gabriel Chandesris (2009-2010)
 * @see gabywald.biosilico.model.Brain#setLobe(int, int, int, int, gabywald.biosilico.model.Neuron, boolean)
 */
public class BrainLengthException extends GenericException { 
	/** To avoid Warning. */
	// private static final long serialVersionUID = 421L; 
	/**
	 * Default Constructor with message
	 * @param request (String)
	 */
	public BrainLengthException(String request) { super(request); }
}
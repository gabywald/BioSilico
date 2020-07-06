package gabywald.biosilico.exceptions;

import gabywald.global.exceptions.GenericException;

/**
 * This Exception is generated if Brain encounter a problem of Neuron replacement. 
 * @author Gabriel Chandesris (2009)
 * @see gabywald.biosilico.model.Brain#setLobe(int, int, int, int, gabywald.biosilico.model.Neuron, boolean)
 */
@SuppressWarnings("serial")
public class BrainLobeReplaceException extends GenericException { 
	/** To avoid Warning. */
	// private static final long serialVersionUID = 422L;
	/**
	 * Default Constructor with message
	 * @param request (String)
	 */
	public BrainLobeReplaceException(String request) { super(request); }
}
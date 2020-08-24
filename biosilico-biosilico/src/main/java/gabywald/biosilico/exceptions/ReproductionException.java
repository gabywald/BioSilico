package gabywald.biosilico.exceptions;

import gabywald.global.exceptions.GenericException;

/**
 * This Exception is generated if Reproduction encounter a problem (incompatibility for example). 
 * @author Gabriel Chandesris (2020)
 */
@SuppressWarnings("serial")
public class ReproductionException extends GenericException {
	/**
	 * Default Constructor with message
	 * @param request (String)
	 */
	public ReproductionException(String request) { super(request); }
}

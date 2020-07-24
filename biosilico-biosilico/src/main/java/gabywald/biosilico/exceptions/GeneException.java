package gabywald.biosilico.exceptions;

import gabywald.global.exceptions.GenericException;

/**
 * This Exception is generated when a problem occur when execution of a Gene. 
 * @author Gabriel Chandesris (2009-2010)
 * @see gabywald.biosilico.genetics.Gene#execution(gabywald.biosilico.model.Organism)
 */
@SuppressWarnings("serial")
public class GeneException extends GenericException { 
	/**
	 * Default Constructor with message
	 * @param request (String)
	 */
	public GeneException(String request) { super(request); }
}
package gabywald.biosilico.exceptions;

import gabywald.global.exceptions.GenericException;

/**
 * This Exception is generated if a problem occurs in GeneticTreeNode. 
 * @author Gabriel Chandesris (2009-2010)
 * @see gabywald.biosilico.structures.GeneticTreeNode
 */
@SuppressWarnings("serial")
public class GeneticTreeNodeException extends GenericException { 
	/** To avoid Warning. */
	// private static final long serialVersionUID = 424L; 
	/**
	 * Default Constructor with message
	 * @param request (String)
	 */
	public GeneticTreeNodeException(String request) { super(request); }
}

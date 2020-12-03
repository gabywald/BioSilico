package gabywald.creatures.exceptions;

import gabywald.global.exceptions.MessageException;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
@SuppressWarnings("serial")
public class GenomeParserException extends MessageException {
	public GenomeParserException(String message) 
		{ super(message); }
}

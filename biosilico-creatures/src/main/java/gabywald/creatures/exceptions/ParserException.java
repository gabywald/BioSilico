package gabywald.creatures.exceptions;

import gabywald.global.exceptions.MessageException;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
@SuppressWarnings("serial")
public class ParserException extends MessageException {
	public ParserException(String message) 
		{ super(message); }
}

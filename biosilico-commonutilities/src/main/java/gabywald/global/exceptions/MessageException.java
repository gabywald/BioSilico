package gabywald.global.exceptions;

/**
 * This Class to define a generic Exception with a message. 
 * @author Gabriel Chandesris (2010, 2014)
 * TODO Implicit or explicit Exception Inheritance...
 */
@SuppressWarnings("serial")
public class MessageException extends GenericException {
	public MessageException(String message) 
		{ super(message); }
}

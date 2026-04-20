package gabywald.global.exceptions;

/**
 * This Class to define a generic Exception with a message. 
 * @author Gabriel Chandesris (2010, 2014)
 * TODO Implicit or explicit Exception Inheritance...
 */
@SuppressWarnings("serial")
public abstract class GenericException extends Exception {
	/** To avoid Warning. */
	// private static final long serialVersionUID = 400L;
	/** The message of the Exception. */
	private String request;
	
	private Throwable cause;
	
	/**
	 * Default Constructor with message
	 * @param request (String) The message. 
	 */
	public GenericException(String request) {
		super(request);
		this.request	= request;
		this.cause		= null;
	}
	
	/**
	* Constructs a new GenericException with the specified cause.
	* @param cause (Throwable) The cause.
	*/
	public GenericException(Throwable cause) {
		super(cause.getMessage());
		this.request	= cause.getMessage();
		this.cause		= cause;
	}
	
	public String getRequest() 
		{ return this.request; }
	
	public Throwable getCause() 
		{ return this.cause; }
}

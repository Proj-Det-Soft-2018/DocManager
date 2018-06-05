package business.exception;

@SuppressWarnings("serial")
public class ValidationException extends Exception{
	
	public ValidationException(String message, Throwable ex) {
		super(message, ex);
	}
	public ValidationException(String message) {
		super(message);
	}
 
}

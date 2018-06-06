package persistence.exception;

@SuppressWarnings("serial")
public class DatabaseException extends Exception {
	
	public DatabaseException(String message, Throwable ex) {
		super(message, ex);
	}
}

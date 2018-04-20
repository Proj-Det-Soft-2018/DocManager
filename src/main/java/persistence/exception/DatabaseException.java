package persistence.exception;

@SuppressWarnings("serial")
public class DatabaseException extends Exception {
	public DatabaseException(String message) {
		super(message);
	}	
}

package business.service;

@SuppressWarnings("serial")
public class ValidationException extends RuntimeException{
	
	/**
	 * 
	 */
	private String header;
	private String field;
	private String message;
	
	public ValidationException(String header, String field, String message) {
		this.header = header;
		this.field = field;
		this.message = message;
	}

	public String getHeader() {
		return header;
	}

	public String getField() {
		return field;
	}

	public String getMessage() {
		return message;
	}
	
	

}

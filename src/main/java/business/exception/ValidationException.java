package business.exception;
/*
 * Exceção responsável por sinalizar todos os erros de validação do código
 */
@SuppressWarnings("serial")
public class ValidationException extends Exception{
	
	public ValidationException(String message, Throwable ex) {
		super(message, ex);
	}
	public ValidationException(String message) {
		super(message);
	}
 
}

package br.com.antunes.gustavo.usefultools.exception;

public class ToolException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ToolException(String message) {
		super(message);
	}
	
	public ToolException(String message, Throwable cause) {
		super(message, cause);
	}

}

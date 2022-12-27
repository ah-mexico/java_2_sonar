package com.colsanitas.loginadmin.exception;

public class NoDataFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 882911649812650507L;
	
	public NoDataFoundException() {
		super();
	}

	public NoDataFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoDataFoundException(String message) {
		super(message);
	}

	public NoDataFoundException(Throwable cause) {
		super(cause);
	}
}

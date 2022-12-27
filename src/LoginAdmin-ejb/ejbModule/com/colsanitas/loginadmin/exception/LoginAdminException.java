package com.colsanitas.loginadmin.exception;


public class LoginAdminException extends Exception{

	private static final long serialVersionUID = 8688723154305532814L;
	private String code;
	
	public LoginAdminException() {
		super();		
	}
	
	public LoginAdminException(String message) {
		super(message);
	}
	
	public LoginAdminException(Throwable cause) {
		super(cause);
	}
	
	public LoginAdminException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public LoginAdminException(String code, String message) {
		super(message);
		this.code = code;
	}

	public LoginAdminException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
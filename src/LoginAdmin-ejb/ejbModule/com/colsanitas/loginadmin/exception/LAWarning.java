package com.colsanitas.loginadmin.exception;

import com.osi.gaudi.exception.Warning;


public class LAWarning extends Warning{

	private static final long serialVersionUID = 8688723154305532814L;
	
	public LAWarning(String code, String message) {
		super(code, message);
	}

}
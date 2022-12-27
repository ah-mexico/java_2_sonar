package com.colsanitas.loginadmin.administracion.utils;

import com.colsanitas.loginadmin.administracion.view.UserResponseView;

/**
 * @author fapinto
 *
 */
public class Validator {

	/**
	 * 
	 */
	public Validator() {
		// TODO Auto-generated constructor stub
	}
	
	public static UserResponseView validateChangePassword(String login,String password,String newPassword) {
		UserResponseView  response= new UserResponseView();
		if( Utilidades.validateNotEmpty( login ) ){
			if( Utilidades.validateNotEmpty( password ) ){
				if( Utilidades.validateNotEmpty( newPassword ) ){
					String validate="";
					 validate=Utilidades.validatePassword(login, newPassword);
					 if(validate.length()==0) {
						 response.setResponseCode(EResponse.VALIDATION_OK.getCode());
						 response.setMessage( EResponse.VALIDATION_OK.getMessage());	
					 } else {
						 response.setResponseCode(EResponse.VALIDATION_ERROR.getCode());
						 response.setMessage(validate);
					 }						 
				} else { 
					response.setResponseCode(EResponse.VALIDATION_ERROR.getCode());response.setMessage(EResponse.VALIDATION_ERROR.getMessage()+" El atributo newPassword es requerido");
				}	
			} else {
				response.setResponseCode(EResponse.VALIDATION_ERROR.getCode());response.setMessage(EResponse.VALIDATION_ERROR.getMessage()+" El atributo password es requerido");
			}
		
		  } else {
			  response.setResponseCode(EResponse.VALIDATION_ERROR.getCode());response.setMessage(EResponse.VALIDATION_ERROR.getMessage()+ " El atributo login es requerido");
		  }		
		return response;
	}
	
}

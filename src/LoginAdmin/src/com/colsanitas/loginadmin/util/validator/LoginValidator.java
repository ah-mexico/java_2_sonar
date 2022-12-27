package com.colsanitas.loginadmin.util.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.osi.gaudi.cfg.Configurator;
import com.osi.his.sistema.util.JSFUtil;



/***
 * Clase Validator para la validacion de email. 
 * */
public class LoginValidator implements Validator{

	
	public void validate(FacesContext ctx, UIComponent cmp, Object value) throws ValidatorException {

		if(value != null){

			boolean result = validarLogin(value.toString());
			if(!result){
				String msgError = Configurator.getInstance().getString("administracion","msgValidacionLogin","");

				throw new ValidatorException(JSFUtil.getMessageFromBundleByMessage(msgError, FacesMessage.SEVERITY_ERROR));
			}				
		}

	}

	/**
	 * Realiza la validacion de login para las reglas :
	 * Sólo se permiten letras (a-z), números (0-9), puntos (.) y guiones (_ y -).
	 * No es sensible a mayúsculas.
	 * */
	private boolean validarLogin( String login ){
		if( login != null && !login.isEmpty() ) {
			Matcher m = null;
			boolean result = false;
			Pattern pLogin = Pattern.compile("[^a-zA-Z0-9._-]+");
			m = pLogin.matcher(login);
			result = m.find();
			if(!result)
				return true;
			else
				return false;
		} else {
			return true;
		}
	}
}

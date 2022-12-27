package com.colsanitas.loginadmin.util.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.osi.his.sistema.util.JSFUtil;


/***
 * Clase Validator para la validación de cualquier expresión regular..
 * */
public class RegexValidator implements Validator {

	String regex;
	String invalidMessage;
	
	public void validate(FacesContext ctx, UIComponent cmp, Object value)
			throws ValidatorException {

		this.regex = (String) cmp.getAttributes().get("regex");  
		this.invalidMessage = (String) cmp.getAttributes().get("invalidMessage");  
		
		String val = (String) value;
		if (!val.matches(regex)) {
			throw new ValidatorException(JSFUtil.getMessageFromBundleByMessage(invalidMessage, FacesMessage.SEVERITY_ERROR));
		}

	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getInvalidMessage() {
		return invalidMessage;
	}

	public void setInvalidMessage(String invalidMessage) {
		this.invalidMessage = invalidMessage;
	}
}


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
public class EmailValidator implements Validator{

	
	public void validate(FacesContext ctx, UIComponent cmp, Object value) throws ValidatorException {

		if(value != null){

			boolean result = validarEmail(value.toString());
			if(!result){
				String msgError = Configurator.getInstance().getString("administracion","msgValidacionMail","");

				throw new ValidatorException(JSFUtil.getMessageFromBundleByMessage(msgError, FacesMessage.SEVERITY_ERROR));
			}				
		}

	}

	/**
	 * Realiza la validacion de un email para las reglas :
	 * o	Que contenga caracteres especiales válidos para cuentas email, como son (@ arroba), (. punto), (_ guión bajo), (- guión), letras (a - z) y/o números (0 a 9).
	 * o	Que contenga una (@) arroba y mínimo un (.) punto entre los caracteres después de la arroba.
	 * o	Que no termine en (.) punto.
	 *
	 * */
	private boolean validarEmail(String email){
		if(email==null || email.trim().equals("")){
			return true;
		}else{

			String[] partesEmail = email.split("@");

			boolean validaEncabezado = false;
			boolean validaDominio = false;
			boolean result = false;
			Matcher m = null;
			if( partesEmail.length == 2 ){
				String encabezado = partesEmail[0]; 
				String dominio = partesEmail[1];
				
				if( encabezado != null && encabezado.trim().length() > 0 ){
					Pattern pEncabezado = Pattern.compile("[^a-zA-Z0-9._-]+");
					m = pEncabezado.matcher(encabezado);
					result = m.find();
					if(!result)
						validaEncabezado = true;
					else
						validaEncabezado = false;
				} else {
					validaEncabezado = false;
				}
				
				if( dominio != null && dominio.trim().length() > 0 ){
					Pattern pDominio = Pattern.compile("([a-zA-Z0-9_-]+[.])+[a-zA-Z0-9_-]+");
					m = pDominio.matcher(dominio);	    

					result = m.find();

					if(result) {		    	
						if( m.group().compareTo(dominio) == 0)
							validaDominio = true;
						else
							validaDominio = false;
					} else {
						validaDominio = false;
					}
				} else {
					validaDominio = false;
				}
			} else {
				return false;
			}
			return validaEncabezado && validaDominio;
		}

	}	

}

package com.colsanitas.loginadmin.util.validator;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.osi.his.sistema.util.JSFUtil;

public class ExpDateValidator implements Validator {

	public void validate(FacesContext ctx, UIComponent cmp, Object value) throws ValidatorException {

		if (value != null) {

			String result = validarExpDate((Date) value);
			if (result != null) {
				if (result.equals("F")) {
					throw new ValidatorException(
							JSFUtil.getMessageFromBundle("validator.fecha.fin.menor", FacesMessage.SEVERITY_ERROR));
				} else if (result.equals("N")) {
					throw new ValidatorException(JSFUtil.getMessageFromBundle("javax.faces.component.UIInput.REQUIRED",
							FacesMessage.SEVERITY_ERROR));
				} 
			}
		}
	}

	private String validarExpDate(Date fecha) {
		if (fecha == null || fecha.equals("")) {
			return "N";
		} else {
			Date today = new Date();

			if (fecha.before(today)) {
				return "F";
			}
			return null;
		}
	}

}

package com.colsanitas.loginadmin.administracion.enums;

import java.text.MessageFormat;

import com.osi.gaudi.cfg.Configurator;

/**
 * Define los tipos de documentos de un prestador.
 * @author yaaperez
 *
 */
public enum ETipoDocumento {
	
	CC         (Configurator.getInstance().getInt("administracion","cedulaCiuadadania", 1), "C.C."),
	CE         (Configurator.getInstance().getInt("administracion","cedulaExtranjeria", 6), "C.E."),
	PASAPORTE  (Configurator.getInstance().getInt("administracion","pasaporte", 8), "PASAPORTE."),
	NIT 	   (Configurator.getInstance().getInt("administracion","nit", 2), "NIT.");
	
	private int code;
	private String valor;
	
	private ETipoDocumento(int code, String valor) {
		this.code = code;		
		this.valor = valor;
	}
	
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}
	
	public String toString() {
		return toString(new Object[]{});
	}
	
	public String toString(Object... params) {
		if (params != null && params.length > 0) {
			MessageFormat mf  = new MessageFormat(valor);
			return mf.format(params);
		} else {
			return valor;
		}		
	}
	
	public String toFullString(Object... params) {
		return toString(params);
	}

}

/******************************************************************************* 
   EMPRESA:      Fundacion Universitaria Sanitas.
   PROYECTO:     Autenticacion
   ARCHIVO:      EResponse.java 
   PROPOSITO:    Mantener una enumeracion
   HISTORIAL DE CAMBIOS: 
   Version        Fecha        Autor           Descricion 
   ---------  ----------  ---------------  ------------------------------------- 
   1.0        12/04/2010  Camilo Muñoz	   1. Adicion objetos a la enumeracion: 
   										   USER_NOT_FOUND_GENERAL - EMAIL_INVALIDO
*******************************************************************************/ 

//==============================================================================
//PACKAGE
//==============================================================================
	
package com.colsanitas.loginadmin.administracion.utils;

import java.text.MessageFormat;
import com.osi.gaudi.msg.Messenger;
//==============================================================================
//CLASE EAccionPlantillaRole.java
//==============================================================================

	public enum EAccionPlantillaRole {

		ROLE_PERSIST		     (1, "PERSIST"),
		ROLE_INSERT     		 (2, "INSERT"),
		ROLE_DELETE		         (3, "DELETE"),
		ROLE_PERSISTUSER         (4, "PERSISTUSER");
	
//------------------------------------------------------------------------------
// ATRIBUTOS DE CLASE
//------------------------------------------------------------------------------	
	private final Messenger p = Messenger.getInstance();	
	private int code;
	private String message;

//------------------------------------------------------------------------------
//METODOS ANALIZADORES - MODIFICADORES
//------------------------------------------------------------------------------

	private EAccionPlantillaRole(int code, String message) {
		this.code = code;		
		this.message = p.getMsg("messages", message);
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}	
	public String toString() {
		return toString(new Object[]{});
	}
	public String toString(Object... params) {
		if (params != null && params.length > 0) {
			MessageFormat mf  = new MessageFormat(message);
			return mf.format(params);
		} else {
			return message;
		}		
	}
	public String toFullString(Object... params) {
		return toString(params);
	}
}//FIN EAccionPlantillaRole.java

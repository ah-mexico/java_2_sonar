/******************************************************************************* 
   EMPRESA:      Fundacion Universitaria Sanitas.
   PROYECTO:     Autenticacion
   ARCHIVO:      EResponse.java 
   PROPOSITO:    Mantener una enumeracion
   HISTORIAL DE CAMBIOS: 
   Version        Fecha        Autor           Descricion 
   ---------  ----------  ---------------  ------------------------------------- 
   1.0        12/04/2010  Camilo Mu�oz	   1. Adicion objetos a la enumeracion: 
   										   USER_NOT_FOUND_GENERAL - EMAIL_INVALIDO
*******************************************************************************/ 

//==============================================================================
//PACKAGE
//==============================================================================
	
	package com.colsanitas.loginadmin.administracion.utils;

import java.text.MessageFormat;
import com.osi.gaudi.msg.Messenger;
//==============================================================================
//CLASE EResponse.java
//==============================================================================

	public enum EResponse {

		VALIDATION_ERROR         (0, "VALIDATION_ERROR"),
		AUTHENTICATION_OK        (1, "AUTHENTICATION_OK"),
		AUTHENTICATION_ERROR     (2, "AUTHENTICATION_ERROR"),
		USER_REGISTERED          (3, "USER_REGISTERED"),
		USER_NOT_FOUND           (4, "USER_NOT_FOUND"),
		CHANGE_PASSWORD_REQUIRED (5, "CHANGE_PASSWORD_REQUIRED"),
		INACTIVE_ACCOUNT         (6, "INACTIVE_ACCOUNT"),
		PASSWD_CHANGED_OK        (7, "PASSWD_CHANGED_OK"),
		PASSWD_CHANGED_ERROR     (8, "PASSWD_CHANGED_ERROR"),
		CURRENT_PASSWD_ERROR     (9, "CURRENT_PASSWD_ERROR"),
		USER_FOUND               (10, "USER_FOUND"),
		TRANSACTION_OK           (11, "TRANSACTION_OK"),
		TRANSACTION_ERROR        (12, "TRANSACTION_ERROR"),
		PASSWD_CHANGED_LIMITED   (13, "PASSWD_CHANGED_LIMITED"),
		USER_REGISTERED_LDAP     (14, "USER_REGISTERED_LDAP"),
		BLOCK_ACCOUNT            (15, "BLOCK_ACCOUNT"),
		CHANGE_ROLE_OK           (16, "CHANGE_ROLE_OK"),
		CHANGE_ROLE_ERROR        (17, "CHANGE_ROLE_ERROR"),
		ROLE_NOT_FOUND           (18, "ROLE_NOT_FOUND"),
		ROLE_NOT_FOUND_FOR_DOMAIN(19, "ROLE_NOT_FOUND_FOR_DOMAIN"),
		ROLE_ASSOCIATE           (20, "ROLE_ASSOCIATE"),
		CREATE_ACCOUNT           (21, "CREATE_ACCOUNT"),
		USER_DUPLICATE           (22, "USER_DUPLICATE"),
		VALIDATION_OK            (23, "VALIDATION_OK"),
		PKI_VALIDATE_ERROR       (24, "PKI_VALIDATE_ERROR"),
		EMAIL_INVALIDO			 (25, "EMAIL_INVALIDO"),
		USER_NOT_FOUND_GENERAL   (26, "USER_NOT_FOUND_GENERAL"),
		REMEMBERPASSWORD_OK      (28, "REMEMBERPASSWORD_OK"),
		REMEMBERPASSWORD_ERROR   (29, "REMEMBERPASSWORD_ERROR"),
		REMEMBERPASSWORD_USERNOTFOUND   (30, "REMEMBERPASSWORD_USERNOTFOUND"),
		GETBASICDATA_OK      	 (31, "GETBASICDATA_OK"),
		GETBASICDATA_ERROR   	 (32, "GETBASICDATA_ERROR"),
		USER_MODIFY              (33, "USER_MODIFY"),
		EMAIL_SEND               (34, "EMAIL_SEND"),
		EMAIL_NO_SEND            (35, "EMAIL_NO_SEND"),
		RELATION_CREATE          (36, "RELATION_CREATE"),
		EMAIL_DUPLICATE           (37, "EMAIL_DUPLICATE"),
		AUTORIZACION_CORRECTAMENTE    (38, "AUTORIZACION_CORRECTAMENTE"),
		DESAUTORIZACION_CORRECTAMENTE (39, "DESAUTORIZACION_CORRECTAMENTE"),
		USER_AUTORIZADO			(40, "USER_AUTORIZADO"),
		USER_NO_REG_LDAP		(41, "USER_NO_REG_LDAP"),
		USER_EXISTS_TIPO_DOC		(42, "USER_EXISTS_TIPO_DOC"),
		PRESTADOR_NOT_MIGRATED (43, "PRESTADOR_NOT_MIGRATED ");
	
//------------------------------------------------------------------------------
// ATRIBUTOS DE CLASE
//------------------------------------------------------------------------------	
	private final Messenger p = Messenger.getInstance();	
	private int code;
	private String message;

//------------------------------------------------------------------------------
//METODOS ANALIZADORES - MODIFICADORES
//------------------------------------------------------------------------------

	private EResponse(int code, String message) {
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
}//FIN EResponse.java

/******************************************************************************* 
   EMPRESA:      GPM.
   PROYECTO:     LoginAdmin
   ARCHIVO:      EtipoRolLoginAdmin.java 
   PROPOSITO:    Mantener una enumeracion
   HISTORIAL DE CAMBIOS: 
   Version        Fecha        Autor           Descripcion 
   ---------  ----------  ---------------  ------------------------------------- 
   1.0        14/09/2012    Yasser Perez	   1. Adicion objetos a la enumeracion
*******************************************************************************/ 

//==============================================================================
//PACKAGE
//==============================================================================
	
package com.colsanitas.loginadmin.administracion.utils;


public enum EtipoRolLoginAdmin {

		ADMINISTRADOR		     ("A", "ADMINISTRADOR"),
		DELEGADO   		  		 ("D", "DELEGADO"),
		ADMINDELE		         ("AD", "ADMINISTRADORDELEGADO");
	
	//------------------------------------------------------------------------------
	// ATRIBUTOS DE CLASE
	//------------------------------------------------------------------------------	
		
	private String code;
	private String message;

	//------------------------------------------------------------------------------
	//METODOS ANALIZADORES - MODIFICADORES
	//------------------------------------------------------------------------------

	private EtipoRolLoginAdmin(String code, String message) {
		this.code = code;		
		this.message = message;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}	
	
}//FIN EtipoRolLoginAdmin

package com.colsanitas.loginadmin.administracion.business;

import javax.ejb.Local;

import com.colsanitas.loginadmin.administracion.view.UserResponseView;

/**
 * Define  los metodos necesarios para la gesti�n de un usuario de sistema. 
 * 
 * @author jjtrujillo
 *
 */
@Local
public interface IAdmnistrarPasswordEjb {

	/**
	 * Actualiza el password de un usuario, solicitando su password anterior
	 * @param userLogin login del usuario.
	 * @param oldPassword password actual del usuario
	 * @param newPassword nuevo password por el cual se reemplaza el actual
	 * @return resultado.
	 */
	public UserResponseView actualizarPassword(String userLogin, String oldPassword, String newPassword);
	public UserResponseView recordarPassword(String userLogin);
	
}

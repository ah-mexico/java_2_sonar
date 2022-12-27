package com.colsanitas.loginadmin.administracion.business;

import java.util.List;

import javax.ejb.Local;

import com.colsanitas.loginadmin.administracion.entity.PrestadorEntity;
import com.colsanitas.loginadmin.administracion.entity.RelacionEntity;
import com.colsanitas.loginadmin.administracion.entity.UserEntity;
import com.colsanitas.loginadmin.administracion.view.UserResponseView;

/**
 * Define  los metodos necesarios para la creación de un delegado. 
 * 
 * @author armarquez
 *
 */
@Local
public interface IRegistroDelegadoEjb {

	/**
	 * Consulta los prestadores relacionados a un usuario administrador.
	 * @param userLogin login del usuario.
	 * @return lista de prestadores asociados
	 */
	public List<PrestadorEntity> consultarPrestadoresDelAdministrador(String userLogin) throws Exception;

	public UserResponseView registrarRelacionDelegado(RelacionEntity relacionEntity);
	
	
	
}

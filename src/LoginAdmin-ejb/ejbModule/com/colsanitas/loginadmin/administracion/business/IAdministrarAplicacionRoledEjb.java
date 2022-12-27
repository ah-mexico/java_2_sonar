package com.colsanitas.loginadmin.administracion.business;

import java.util.List;

import javax.ejb.Local;

import com.colsanitas.loginadmin.administracion.entity.UserRoleEntity;
import com.colsanitas.loginadmin.exception.LoginAdminException;

/**
 * Define los metodos necesarios para la gestion de las apliaciones y roles a los usuarios. 
 * @author yaaperez
 */
@Local
public interface IAdministrarAplicacionRoledEjb {
	public List<UserRoleEntity> listarApliRoleUser( UserRoleEntity userRoleEntity, boolean check ) throws Exception;
	public void registrarAplicacionRoleUser( List<UserRoleEntity> listApliRoleReg ) throws LoginAdminException;
	public void registrarAplicacionRoleUser(List<UserRoleEntity> listApliRoleReg, String tipoRolLA) throws LoginAdminException;
	public  List<UserRoleEntity> consultarAdministradorDominio(String userlogin, List<UserRoleEntity> listUserRoleEntity) ;
		
}

package com.colsanitas.loginadmin.administracion.business;

import java.util.List;

import javax.ejb.Local;

import com.colsanitas.loginadmin.administracion.entity.UserEntity;
import com.colsanitas.loginadmin.administracion.view.TipoDocumentoView;
import com.colsanitas.loginadmin.administracion.view.UserResponseView;
import com.colsanitas.loginadmin.exception.LoginAdminException;

/**
 * Define  los metodos necesarios para la gesti�n de un usuario de sistema. 
 * 
 * @author jjtrujillo
 *
 */
@Local
public interface IGestionUsuariosEjb {

	
	public boolean isActivo(String login);

	public UserResponseView crearUsuario(UserEntity userEntity, String login);
	
	public UserResponseView actualizarUsuario(UserEntity userEntity);
	
	public List<TipoDocumentoView> listTipoDocs();
	
	public UserEntity consultUser( String usersLogin ) throws LoginAdminException;
	
	public List<UserEntity> listUser(String userName) throws LoginAdminException;
	
	public boolean tieneRolFuncionarioOsi(String userName, String rolAdminLoginAdmin) ;
	
	
	
}

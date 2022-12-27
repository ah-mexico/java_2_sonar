package com.colsanitas.loginadmin.administracion.dao.interfaces;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import com.colsanitas.loginadmin.administracion.entity.UserEntity;
import com.colsanitas.loginadmin.exception.LoginAdminException;

@Local
public interface IUsuarioDao {
	public void registrarUsuarioEntity(UserEntity userEntity, boolean flush) throws LoginAdminException;
	public UserEntity findById(String login) throws LoginAdminException;
	public UserEntity actualizarUsuarioEntity(UserEntity userEntity) throws LoginAdminException;
	public List<UserEntity> consultarUsuariosPrest(List<String> usersLogin, Long cdPerson)throws LoginAdminException;
	public UserEntity consultUser( String usersLogin ) throws LoginAdminException;
	public List<UserEntity> listUser(String userName) throws LoginAdminException;
	public List<UserEntity> buscarUsuariosCriterios( UserEntity datosBusq ) throws LoginAdminException;
	public List<UserEntity> buscarUsuariosPrest( UserEntity datosBusq, String userName ) throws LoginAdminException;
	
	/**
	 * Desactiva los usuarios del sistema para que no puedan ingresar al mismo. 
	 * 
	 * @param listaUserIds listado de ids de usuarios.
	 */
	public void desactivarUsuarioByIds(Set<String> listaUserIds);

	/**
	 * Reactiva los usuarios del sistema para que puedan ingresar nuevamente al mismo. 
	 * 
	 * @param listaUserIds listado de ids de usuarios.
	 */
	public void reactivarUsuarioByIds(List<String> listaUserIds);
	
	/**
	 * Retorna si un usuario ha sido bloqueado permanentemente.
	 * @param userId identificador del usuario.
	 */
	public boolean isLocked(String userId); 

}

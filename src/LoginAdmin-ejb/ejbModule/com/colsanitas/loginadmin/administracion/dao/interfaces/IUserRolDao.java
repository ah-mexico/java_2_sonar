package com.colsanitas.loginadmin.administracion.dao.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.colsanitas.loginadmin.administracion.entity.UserRoleEntity;

@Local
public interface IUserRolDao {
			
	public void registrarUserRolEntity(UserRoleEntity userRoleEntity, boolean flush);
	
	
	public UserRoleEntity findById(String login);
	
	
	public UserRoleEntity actualizarUsuarioEntity(UserRoleEntity userRoleEntity);
	
	
	public UserRoleEntity consultarRelUserRol( UserRoleEntity userRoleEntity ) throws Exception;
	
	public void deleteFromUserRole(UserRoleEntity userRoleEntity);

	public void eliminarUsuariosSinRelacion();
	
	/**
	 * Elimina los permisos creados por la aplicación a un usuario que haya quedado sin ningurna relación con prestador.
	 * 
	 * @param userId id del usuario
	 * @param idsRoles lista de roles 
	 * @param tipoRelacion
	 */
	public void eliminarUserRolSinAutorizacion( String userId, List<Long> idsRoles, int tipoRelacion );
	
	/**
	 * Elimina los roles creados por la aplicacion a un conjunto de usuarios.
	 * @param idUsuarios lista de usuarios a quitarle permisos.
	 * @param idsRoles roles a borrar en authorization.
	 */
	public void eliminarUserRolSinAutorizacion( List<String> idUsuarios, List<Long> idsRoles );
	
	public List<UserRoleEntity> listarRoleUserPrest( UserRoleEntity userRolPrest ) throws Exception;
	
	public List<UserRoleEntity> listarRolePrest(Long prestadorId, Long roleId ) throws Exception;
	
	public void eliminarUserRolSucursal( String idSucursal, List<String> idsUser );
	
	public void eliminarUserRolPrestador( Long idPrestador, List<String> idsUser);
	
	public void eliminarUserRolPrestador( Long idPrestador, String idsUser) throws Exception;
	
	public void eliminarUserRolePlantillaDele( Long idPrestador, Long idRole ) throws Exception;
	
	public void eliminarUserRolPrestador(Long idPrestador, String idUser, String tipoRolLA) throws Exception;
	
	public void actualizarUserRolPrestador(Long idPrestador, String idUser, String tipoRolLA) throws Exception;
	
}

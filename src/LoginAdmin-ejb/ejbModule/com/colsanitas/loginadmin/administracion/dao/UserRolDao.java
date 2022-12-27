package com.colsanitas.loginadmin.administracion.dao;


import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.jboss.seam.annotations.Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.loginadmin.administracion.dao.interfaces.IUserRolDao;
import com.colsanitas.loginadmin.administracion.entity.UserRoleEntity;
import com.colsanitas.loginadmin.administracion.enums.ETipoRelacion;
import com.colsanitas.loginadmin.administracion.utils.EtipoRolLoginAdmin;

/**
 * Define la funcionalidad necesaria para la persistencia y consulta de los usuario roles.
 * 
 * @author yaaperez
 *
 */

@Stateless
@Name("userRolDao")
@Local(IUserRolDao.class)
public class UserRolDao extends TemplateDao<UserRoleEntity, String> implements IUserRolDao{

	private static final long serialVersionUID = 5864575670394071637L;
	
	private static Logger logger = LoggerFactory.getLogger(UserRolDao.class);

	public UserRolDao(){
		super(UserRoleEntity.class);
	}
	public UserRolDao(Class<UserRoleEntity> entityClass) {
		super(entityClass);
	}

	public void registrarUserRolEntity(UserRoleEntity userRoleEntity, boolean flush) {
		create(userRoleEntity, flush);
	}

	public UserRoleEntity findById(String login) {
		return find(login);
	}

	public UserRoleEntity actualizarUsuarioEntity(UserRoleEntity userRoleEntity) {
		return update(userRoleEntity, false);
	}
	
	public UserRoleEntity consultarRelUserRol( UserRoleEntity userRoleEntity ) throws Exception { 
		UserRoleEntity resUserRoleEntity = null;
		Query query = null;
		String sql = "";
		try {
			sql = "select usro " 
				+ " from UserRoleEntity usro " 
				+ " where LOWER(usro.userId) = :userId "
				+ " and usro.roleId = :roleId";
			
			if( userRoleEntity.getPrestadorId() != null ) {
				sql += " and usro.prestadorId = :prestadorId";
			}
			
			query = getEm().createQuery(sql);
			
			query.setParameter("userId", userRoleEntity.getUserId());	
			query.setParameter("roleId", userRoleEntity.getRoleId());
			
			if( userRoleEntity.getPrestadorId() != null ) {
				query.setParameter("prestadorId", userRoleEntity.getPrestadorId());
			}
			
			resUserRoleEntity = (UserRoleEntity) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			logger.error("Error: UserRolDao.consultarRelUserRol "+e.getMessage(), e);
			throw e;
		}
		return resUserRoleEntity;
	}
	
	public void deleteFromUserRole(UserRoleEntity userRoleEntity){
		try {
			delete(userRoleEntity);
		} catch (Exception e) {
			logger.error("Error: UserRolDao.deleteFromUserRole "+e.getMessage(), e);
		}
	}
	public void eliminarUsuariosSinRelacion() {
		Query query = getEm().createQuery("DELETE UserRoleEntity u WHERE LOWER(u.userId)  NOT IN (SELECT DISTINCT r.usuario.userId FROM RelacionEntity r WHERE u.userId = r.usuario.userId AND r.estado = :activo)");
		query.executeUpdate();
	}
	
	public void eliminarUserRolSinAutorizacion( String userId, List<Long> idsRoles, int tipoRelacion ) {
		String sql = "";
		Query query = null;
		try {
			sql += " DELETE FROM UserRoleEntity usro ";
			sql += " where usro.userId = :userId ";
			sql += " and not exists ( select 1 from RelacionEntity rela where rela.estado = 1 and rela.tipoRelacion = :tipoRelacion and rela.usuario.userId = :userId ) ";
			sql += " and usro.roleId IN (:roleId) ";
			
			query = getEm().createQuery( sql );
			
			query.setParameter("tipoRelacion", tipoRelacion);
			query.setParameter("userId", userId);	
			query.setParameter("roleId", idsRoles);
			
			query.executeUpdate();
			
		} catch (Exception e) {
			logger.error("Error: UserRolDao.eliminarUserRolSinAutorizacion "+e.getMessage(), e);
		}
	}
	
	public void eliminarUserRolSinAutorizacion( List<String> idUsuarios, List<Long> idsRoles ) {
		try {
			
			String delete = "DELETE FROM UserRoleEntity usro  WHERE usro.userId IN (:idUsuarios) AND usro.roleId IN (:roleId) ";
			
			Query query = getEm().createQuery( delete );
			
			query.setParameter("idUsuarios", idUsuarios);	
			query.setParameter("roleId", idsRoles);
			
			query.executeUpdate();
			
		} catch (Exception e) {
			logger.error("Error: UserRolDao.eliminarUserRolSinAutorizacion "+e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<UserRoleEntity> listarRoleUserPrest( UserRoleEntity userRolPrest ) throws Exception {
		List<UserRoleEntity> listRoleUserPrest = null;
		String sql = "";
		Query query = null;
		try {
			
			sql += "select distinct usro ";
			sql += "from UserRoleEntity usro ";
			sql += "where usro.prestadorId = :prestadorId ";
			sql += "and usro.userId = :userId ";
			if( userRolPrest.getTipoRolLA() != null ) {
				sql += "and (usro.tipoRolLA = :tipoRol or usro.tipoRolLA = :tipoRolAD) ";
			}
			
			query = getEm().createQuery(sql);
			
			query.setParameter("prestadorId", userRolPrest.getPrestadorId());
			query.setParameter("userId", userRolPrest.getUserId());
			if( userRolPrest.getTipoRolLA() != null ) {
				query.setParameter("tipoRol", userRolPrest.getTipoRolLA());
				query.setParameter("tipoRolAD", EtipoRolLoginAdmin.ADMINDELE.getCode());
			}
			
			listRoleUserPrest = query.getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			logger.error("Error: UserRolDao.listarRoleUserPrest "+e.getMessage(), e);
			throw e;
		}
		logger.info("result DAO listRoleUserPrest:  " +listRoleUserPrest);
		return listRoleUserPrest;
	}
	
	/**
	 * Elimina la relacion entre usuario, sucursal y rol 
	 * de acuerdo a la Sucursal y lista de usuarios suministrados.
	 */
	public void eliminarUserRolSucursal( String idSucursal, List<String> idsUser) {
		String sql = "";
		Query query = null;
		
		sql += " DELETE FROM UserRoleEntity usro ";
		sql += " where usro.prestadorId = (select p.prestadorId from PrestadorEntity p where p.sucursal = :idSucursal )" ;
		sql += " and usro.userId in (:idUsuarios) ";
			 
		query = getEm().createQuery( sql );
		query.setParameter("idSucursal", idSucursal);	
		query.setParameter("idUsuarios", idsUser);
			
		query.executeUpdate();
		
	}
	
	/**
	 * Elimina la relacion entre usuario, Prestador y rol 
	 * de acuerdo al Prestador y lista de usuarios suministrados.
	 */
	public void eliminarUserRolPrestador( Long idPrestador, List<String> idsUser) {
		String sql = "";
		Query query = null;
		
		sql += " DELETE FROM UserRoleEntity usro ";
		sql += " where usro.prestadorId in (select p.prestadorId from PrestadorEntity p where p.cdperson = :idPrestador )" ;
		sql += " and usro.userId in (:idUsuarios) ";
			 
		query = getEm().createQuery( sql );
		query.setParameter("idPrestador", idPrestador);	
		query.setParameter("idUsuarios", idsUser);
			
		query.executeUpdate();
		
	}
	
	public void eliminarUserRolPrestador(Long idPrestador, String idUser) throws Exception {
		String sql = "";
		Query query = null;
		
		try {
			sql += " DELETE FROM UserRoleEntity usro ";
			sql += " where usro.prestadorId = :idPrestador " ;
			sql += " and usro.userId = :idUser ";
				 
			query = getEm().createQuery( sql );
			query.setParameter("idPrestador", idPrestador);	
			query.setParameter("idUser", idUser);
				
			query.executeUpdate();
		} catch (Exception e) {
			logger.error("Error: UserRolDao.eliminarUserRolPrestador "+e.getMessage(), e);
			throw e;
		}
	}
	
	public void eliminarUserRolePlantillaDele( Long idPrestador, Long idRole ) throws Exception {
		String sql = "";
		Query query = null;
		try {
			sql += " DELETE FROM UserRoleEntity ur ";
			sql += " WHERE ur.id IN ( SELECT DISTINCT usro.id FROM PresRolTemplateEntity temp, UserRoleEntity usro, RelacionEntity rela "; 
			sql += "	WHERE temp.roleId = usro.roleId AND temp.prestadorId = usro.prestadorId   " ;
			sql += "	AND usro.userId = rela.usuario.userId AND usro.prestadorId = rela.prestador.prestadorId ";
			sql += "	AND rela.tipoRelacion = :tipoRelacion AND temp.estado = :estadoRolPlantilla ";
			sql += "	AND temp.prestadorId = :idPrestador ";
			sql += "	AND temp.roleId = :idRole )";
			
				 
			query = getEm().createQuery( sql );
			query.setParameter("idPrestador", idPrestador);	
			query.setParameter("idRole", idRole);
			query.setParameter("tipoRelacion", ETipoRelacion.DELEGADO.getId());
			query.setParameter("estadoRolPlantilla", false);
				
			query.executeUpdate();
		} catch (Exception e) {
			logger.error("Error: UserRolDao.eliminarUserRolePlantillaDele "+e.getMessage(), e);
			throw e;
		}
	}
	
	public void eliminarUserRolPrestador(Long idPrestador, String idUser, String tipoRolLA) throws Exception {
		String sql = "";
		Query query = null;
		
		try {
			sql += "DELETE FROM UserRoleEntity usro ";
			sql += "where usro.prestadorId = :idPrestador " ;
			sql += "and usro.userId = :idUser ";
			sql += "and usro.tipoRolLA = :tipoRolLA ";
				 
			query = getEm().createQuery( sql );
			query.setParameter("idPrestador", idPrestador);	
			query.setParameter("idUser", idUser);
			query.setParameter("tipoRolLA", tipoRolLA);
				
			query.executeUpdate();
		} catch (Exception e) {
			logger.error("Error: UserRolDao.eliminarUserRolPrestador "+e.getMessage(), e);
			throw e;
		}
	}
	
	public void actualizarUserRolPrestador(Long idPrestador, String idUser, String tipoRolLA) throws Exception {
		String sql = "";
		Query query = null;
		
		try {
			sql += "UPDATE UserRoleEntity usro SET usro.tipoRolLA = :tipoRolLA WHERE usro.prestadorId = :idPrestador ";
			sql += "and usro.userId = :idUser ";
			sql += "and usro.tipoRolLA = :tipoRolLAAD ";
				 
			query = getEm().createQuery( sql );
			query.setParameter("tipoRolLA", tipoRolLA);
			query.setParameter("idPrestador", idPrestador);	
			query.setParameter("idUser", idUser);
			query.setParameter("tipoRolLAAD", EtipoRolLoginAdmin.ADMINDELE.getCode());
				
			query.executeUpdate();
		} catch (Exception e) {
			logger.error("Error: UserRolDao.actualizarUserRolPrestador "+e.getMessage(), e);
			throw e;
		}
	}
	
	
	/**
	 * metodo que consulta la lista de userRol segun el prestador y el rol
	 */
	@SuppressWarnings("unchecked")
	public List<UserRoleEntity> listarRolePrest(Long prestadorId, Long roleId)
			throws Exception {
		
		Query query = null;
		
		try {
			query = getEm().createNamedQuery(UserRoleEntity.FIND_USER_ROLE_BY_PREST);
			query.setParameter("id_prestador",prestadorId);
			query.setParameter("id_role", roleId);
			return query.getResultList();
		} catch (Exception e) {
			logger.error("Error: UserRolDao.actualizarUserRolPrestador "+e.getMessage(), e);
			throw e;
		}
	}
	
}

package com.colsanitas.loginadmin.administracion.dao;


import java.util.List;
import java.util.Set;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.jboss.seam.annotations.Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.loginadmin.administracion.dao.interfaces.IUsuarioDao;
import com.colsanitas.loginadmin.administracion.entity.PrestadorEntity;
import com.colsanitas.loginadmin.administracion.entity.UserEntity;
import com.colsanitas.loginadmin.administracion.enums.ETipoEstadoRelacion;
import com.colsanitas.loginadmin.exception.LoginAdminException;
import com.osi.gaudi.cfg.Configurator;

/**
 * Define la funcionalidad necesaria para la persistencia y consulta de los prestadores.
 * 
 * @author armarquez
 *
 */


@Stateless
@Name("usuarioDao")
@Local(IUsuarioDao.class)
public class UsuarioDao extends TemplateDao<UserEntity, String> implements IUsuarioDao{

	private static Logger logger = LoggerFactory.getLogger(UsuarioDao.class);
	private static final long serialVersionUID = 5864575670394071637L;

	public UsuarioDao(){
		super(UserEntity.class);
	}
	public UsuarioDao(Class<UserEntity> entityClass) {
		super(entityClass);
	}

	public void registrarUsuarioEntity(UserEntity userEntity, boolean flush) {
		create(userEntity, flush);
	}
	public List<PrestadorEntity> consultarPrestadoresDelAdministrador(String login) {
		return null;
	}
	

	public UserEntity findById(String login) throws LoginAdminException {
		try {
			return find(login);
		} catch (Exception e) {
			logger.error("Error: UsuarioDao.findById "+e.getMessage(), e);
			throw new LoginAdminException("Error en UsuarioDao.findById", e);
		}
		
	}

	public UserEntity actualizarUsuarioEntity(UserEntity userEntity) throws LoginAdminException {
		try {
			return update(userEntity, false);
		} catch (Exception e) {
			logger.error("Error: UsuarioDao.actualizarUsuarioEntity "+e.getMessage(), e);
			throw new LoginAdminException("Error en UsuarioDao.actualizarUsuarioEntity", e);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<UserEntity> consultarUsuariosPrest(List<String> usersLogin, Long idPrestador) throws LoginAdminException {
		List<UserEntity> listUser = null;
		Query query = null;
		String sql = "";
		try {
			sql += " select distinct user from RelacionEntity rel ";
			sql += " inner join rel.usuario user ";
			sql += " where  rel.estado = 1  ";
			if( usersLogin != null && !usersLogin.isEmpty() ){
				sql += " and LOWER(rel.usuario.userId) IN (:login) ";
			}
			if( idPrestador != null ){
				sql += " and rel.prestador.prestadorId = :idPrestador ";
			}
			query = getEm().createQuery(sql);
			if( usersLogin != null && !usersLogin.isEmpty() ){
				query.setParameter("login", usersLogin);
			}
			if( idPrestador != null ){
				query.setParameter("idPrestador", idPrestador);
			}
			listUser = query.getResultList();
		} catch (Exception e) {
			logger.error("Error: UsuarioDao.consultarUsuariosPrest "+e.getMessage(), e);
			throw new LoginAdminException("Error en UsuarioDao.consultarUsuariosPrest", e);
		}
		
		return listUser;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public UserEntity consultUser( String usersLogin ) throws LoginAdminException {
		UserEntity userEntity = null;
		String sql = "";
		try {
			sql += " select distinct user from UserEntity user where LOWER(user.userId) = :usersLogin ";
			Query query = getEm().createQuery(sql);
			query.setParameter("usersLogin", usersLogin);	
			userEntity = (UserEntity) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			logger.error("Error: UsuarioDao.consultUser "+e.getMessage(), e);
			throw new LoginAdminException("Error en UsuarioDao.consultUser", e);
		}
		return userEntity;
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<UserEntity> listUser(String userName) throws LoginAdminException {
		List<UserEntity> listUser = null;
		Query query = null;
		String sql = "";
		try {
			sql += " select distinct user from UserEntity user where UPPER(user.userName) like :userName ";
			query = getEm().createQuery(sql);
			query.setParameter("userName", (new StringBuilder("%")).append(userName.toUpperCase().replaceAll(" ", "%")).append("%").toString());
			listUser = query.getResultList();
		} catch (Exception e) {
			logger.error("Error: UsuarioDao.listUser "+e.getMessage(), e);
			throw new LoginAdminException("Error en UsuarioDao.listUser", e);
		}
		return listUser;
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<UserEntity> buscarUsuariosCriterios( UserEntity datosBusq ) throws LoginAdminException {
		List<UserEntity> listUser = null;
		Query query = null;
		StringBuffer sql = new StringBuffer(" select distinct user from UserEntity user left join user.relaciones rel where 1=1 ");
		try {
			
			if( datosBusq.getUserId() != null && !datosBusq.getUserId().isEmpty() ){
				if( datosBusq.isValUserInfo() ){
					sql.append(" and user.userId <> :userId ");
				} else {
					sql.append(" and user.userId = :userId ");
				}
			}
			if( datosBusq.getIdPrestador() != null ){
				sql.append(" and rel.estado = :estado and rel.prestador.prestadorId = :idPrestador ");
			}
			
			if( datosBusq.getDocument() != null && !datosBusq.getDocument().isEmpty() ){
				sql.append(" and user.tipoDoc = :tipoDoc and user.document = :document ");
			}
			if( datosBusq.getNombreCompleto() != null && !datosBusq.getNombreCompleto().isEmpty() ){
				sql.append(" and UPPER(user.userName || user.userLastName) like :nombreCompleto ");
			}
			if( datosBusq.getUserMail() != null && !datosBusq.getUserMail().isEmpty() ){
				sql.append(" and user.userMail = :mail ");
			}
			
			sql.append(" order by user.userName, user.userLastName");
			
			query = getEm().createQuery(sql.toString());
			
			if( datosBusq.getUserId() != null && !datosBusq.getUserId().isEmpty() ){
				query.setParameter("userId", datosBusq.getUserId());
			}
			if( datosBusq.getDocument() != null && !datosBusq.getDocument().isEmpty() ){
				query.setParameter("tipoDoc", datosBusq.getTipoDoc());
				query.setParameter("document", datosBusq.getDocument());
			}
			if( datosBusq.getNombreCompleto() != null && !datosBusq.getNombreCompleto().isEmpty() ){
				query.setParameter("nombreCompleto", (new StringBuilder("%")).append(datosBusq.getNombreCompleto().toUpperCase().replaceAll(" ", "%")).append("%").toString());
			}
			if( datosBusq.getUserMail() != null && !datosBusq.getUserMail().isEmpty() ){
				query.setParameter("mail", datosBusq.getUserMail());
			}
			if( datosBusq.getIdPrestador() != null ){
				query.setParameter("idPrestador", datosBusq.getIdPrestador());
			}
			if( datosBusq.getIdPrestador() != null ){
				query.setParameter("estado", ETipoEstadoRelacion.ACTIVA.toBoolean());
			}
			
			
			listUser = query.getResultList();
			
		} catch (Exception e) {
			logger.error("Error: UsuarioDao.buscarUsuariosFunc "+e.getMessage(), e);
			throw new LoginAdminException("Error en UsuarioDao.buscarUsuariosFunc", e);
		}
		return listUser;
	}
	
	
	/**
	 * Consulta los usuarios que tienen relaci�n con los prestadores que administra el usuario userName. 
	 * Adem�s, devuelve los usuarios externos que no tienen ninguna relaci�n.
	 * 
	 * @param datosBusq par�metros de b�squeda del usuario.
	 * @param userName nombre de usuario administrador
	 * @return usuarios que cumplen con las caracter�sticas.
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<UserEntity> buscarUsuariosPrest( UserEntity datosBusq, String userName ) throws LoginAdminException {
		List<UserEntity> listUser = null;
		Query query = null;
		StringBuffer sql = new StringBuffer(" select distinct user from RelacionEntity relacion JOIN relacion.usuario user where relacion.estado = :estado ");
		try {
			
			if( datosBusq.getIdPrestador() != null){
				sql.append(" and relacion.prestador.prestadorId = :idprestador  ");
			}
			if( datosBusq.getDocument() != null && !datosBusq.getDocument().isEmpty() ){
				sql.append(" and (user.tipoDoc = :tipoDoc or user.tipoDoc is null )  and user.document = :document ");
			}
			if( datosBusq.getNombreCompleto() != null && !datosBusq.getNombreCompleto().isEmpty() ){
				sql.append(" and UPPER(user.userName || user.userLastName) like :nombreCompleto ");
			}
			if( datosBusq.getUserMail() != null && !datosBusq.getUserMail().isEmpty() ){
				sql.append(" and user.userMail = :mail ");
			}
			query = getEm().createQuery(sql.toString());

			if( datosBusq.getDocument() != null && !datosBusq.getDocument().isEmpty() ){
				query.setParameter("tipoDoc", datosBusq.getTipoDoc());
				query.setParameter("document", datosBusq.getDocument());
			}
			if( datosBusq.getNombreCompleto() != null && !datosBusq.getNombreCompleto().isEmpty() ){
				query.setParameter("nombreCompleto", (new StringBuilder("%")).append(datosBusq.getNombreCompleto().toUpperCase().replaceAll(" ", "%")).append("%").toString());
			}
			if( datosBusq.getUserMail() != null && !datosBusq.getUserMail().isEmpty() ){
				query.setParameter("mail", datosBusq.getUserMail());
			}

			if( datosBusq.getUserMail() != null && !datosBusq.getUserMail().isEmpty() ){
				query.setParameter("mail", datosBusq.getUserMail());
			}
			if( datosBusq.getIdPrestador() != null){
				query.setParameter("idprestador", datosBusq.getIdPrestador());
			}
			
			query.setParameter("estado", ETipoEstadoRelacion.ACTIVA.toBoolean());

			listUser = query.getResultList();
		} catch (Exception e) {
			logger.error("Error: UsuarioDao.buscarUsuariosPrest "+e.getMessage(), e);
			throw new LoginAdminException("Error en UsuarioDao.buscarUsuariosPrest", e);
		}
		return listUser;
	}
	
	public void desactivarUsuarioByIds(Set<String> listaUserIds){
		throw new UnsupportedOperationException();
	}
	
	public boolean isLocked(String userId) {
		throw new UnsupportedOperationException();
	}

	public void reactivarUsuarioByIds(List<String> listaUserIds) {
		throw new UnsupportedOperationException();
	}
	public boolean existUserLogin(String userName) throws LoginAdminException {
		// TODO Auto-generated method stub
		return false;
	}
	
}

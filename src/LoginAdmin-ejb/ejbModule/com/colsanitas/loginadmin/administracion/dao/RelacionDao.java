package com.colsanitas.loginadmin.administracion.dao;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.security.Identity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.loginadmin.administracion.dao.interfaces.IRelacionDao;
import com.colsanitas.loginadmin.administracion.entity.PrestadorEntity;
import com.colsanitas.loginadmin.administracion.entity.RelacionEntity;
import com.colsanitas.loginadmin.administracion.enums.ETipoEstadoPrestador;
import com.colsanitas.loginadmin.administracion.enums.ETipoEstadoRelacion;
import com.colsanitas.loginadmin.administracion.enums.ETipoRelacion;
import com.colsanitas.loginadmin.exception.LoginAdminException;
import com.colsanitas.loginadmin.prestadores.business.GestionPrestadordEjb;
import com.osi.gaudi.cfg.Configurator;

@Stateless
@Name(value="relacionDao")
@Local(IRelacionDao.class)
public class RelacionDao extends TemplateDao<RelacionEntity, Long> implements IRelacionDao {

	
	private static final long serialVersionUID = 277237524225638521L;
	
	private static Logger logger = LoggerFactory.getLogger(GestionPrestadordEjb.class);
	
	@In
	private Identity identity;

	public RelacionDao() {
		super(RelacionEntity.class);
	}
	
	public RelacionDao(Class<RelacionEntity> entityClass) {
		super(entityClass);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void registrarRelacionNewTrans(RelacionEntity relacionEntity, boolean flush) throws LoginAdminException {
		try {
			create(relacionEntity, flush);
		} catch (Exception e) {
			logger.error("Error: RelacionDao.registrarRelacionNewTrans "+e.getMessage(), e);
			throw new LoginAdminException("Error en RelacionDao.registrarRelacionNewTrans", e);
		}
	}
	
	public void registrarRelacion(RelacionEntity relacionEntity, boolean flush) throws LoginAdminException {
		try {
			create(relacionEntity, flush);
		} catch (Exception e) {
			logger.error("Error: RelacionDao.registrarRelacion "+e.getMessage(), e);
			throw new LoginAdminException("Error en RelacionDao.registrarRelacion", e);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public RelacionEntity actualizarRelacionNewTrans(RelacionEntity relacionEntity, boolean flush) throws LoginAdminException {
		try {
			return update(relacionEntity, flush);
		} catch (Exception e) {
			logger.error("Error: RelacionDao.actualizarRelacionNewTrans "+e.getMessage(), e);
			throw new LoginAdminException("Error en RelacionDao.actualizarRelacionNewTrans", e);
		}
	}
	
	public RelacionEntity actualizarRelacion(RelacionEntity relacionEntity, boolean flush) throws LoginAdminException {
		try {
			return update(relacionEntity, flush);
		} catch (Exception e) {
			logger.error("Error: RelacionDao.actualizarRelacion "+e.getMessage(), e);
			throw new LoginAdminException("Error en RelacionDao.actualizarRelacion", e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<RelacionEntity> consultarRelacionUsuario(String userLogin, int tipoRelacion) throws LoginAdminException {
		List<RelacionEntity> listRel = null;
		Query query = null;
		try {
			query = getEm().createQuery("select rel" 
					+ " from RelacionEntity rel " 
					+ " inner join fetch rel.prestador prest " 
					+ " where LOWER(rel.usuario.userId) = :login " 
					+ " and rel.tipoRelacion = :tipoRel ");
			
			query.setParameter("login", userLogin);
			query.setParameter("tipoRel", tipoRelacion);
			
			listRel = query.getResultList();
			
		} catch (Exception e) {
			logger.error("Error: RelacionDao.consultarRelacionUsuario "+e.getMessage(), e);
			throw new LoginAdminException("Error en RelacionDao.consultarRelacionUsuario", e);
		}
		return listRel;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public RelacionEntity consultarRelacionUser( String userLogin, Long idPrestador, int tipoRel ) throws LoginAdminException {
		RelacionEntity relacionEntity = null;
		try {
			Query query = getEm().createQuery("select rel" 
					+ " from RelacionEntity rel " 
					+ " where rel.tipoRelacion = :tipoRel "
					+ " and LOWER(rel.usuario.userId) = :login"
					+ " and rel.prestador.prestadorId = :idPrestador");
			query.setParameter("tipoRel", tipoRel);	
			query.setParameter("login", userLogin);	
			query.setParameter("idPrestador", idPrestador);
			
			relacionEntity = (RelacionEntity) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			logger.error("Error: RelacionDao.consultarRelacionUser "+e.getMessage(), e);
			throw new LoginAdminException("Error en RelacionDao.consultarRelacionUsuario", e);
		}
		return relacionEntity;
	}
	
	@SuppressWarnings("unchecked")
	public List<RelacionEntity> consultarRelacionUserDesau(String userLogin, String prestLogin) throws LoginAdminException {
		List<RelacionEntity> listRel = null;
		Query query = null;
		try {
			String sql = " select DISTINCT rel from RelacionEntity rel inner join fetch rel.prestador prest inner join fetch rel.usuario user where LOWER(rel.usuario.userId) = :login " +
					     "and rel.tipoRelacion != :creacion and rel.estado = :estadoRel and prest.estado = :estadoPrest ";
			
			boolean roleValidation = identity.hasRole(Configurator.getInstance().getString("administracion","rolAdminPrestador", "")) && 
									 !identity.hasRole(Configurator.getInstance().getString("administracion","rolFuncionario", ""));
			if( roleValidation ){
				sql += " and rel.prestador.prestadorId in (select distinct rela.prestador.prestadorId from RelacionEntity rela where LOWER(rela.usuario.userId) = :prestLogin and rela.tipoRelacion = :tipoAdministrador and rela.estado = :estadoRel) ";
			}
			
			query = getEm().createQuery(sql);
			
			query.setParameter("login", userLogin);
			query.setParameter("creacion", ETipoRelacion.CREADO_POR.getId());
			query.setParameter("estadoRel", ETipoEstadoRelacion.ACTIVA.toBoolean());
			query.setParameter("estadoPrest", ETipoEstadoPrestador.ACTIVA.toBoolean());
			if( roleValidation ) {
				query.setParameter("tipoAdministrador", ETipoRelacion.ADMINISTRADOR.getId());
				query.setParameter("prestLogin", prestLogin);
			}
			
			listRel =query.getResultList();
			
		} catch (Exception e) {
			logger.error("Error: RelacionDao.consultarRelacionUserDesau "+e.getMessage(), e);
			throw new LoginAdminException("Error en RelacionDao.consultarRelacionUserDesau", e);
		}
		
		return listRel;
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<RelacionEntity> consultarRelacionUsuario(String userLogin) throws LoginAdminException {
		List<RelacionEntity> listRel = null;
		Query query = null;
		try {
			query = getEm().createQuery("select distinct rel " 
					+ " from RelacionEntity rel " 
					+ " inner join fetch rel.prestador prest " 
					+ " where LOWER(rel.usuario.userId) = :login "
					+ " and prest.estado = :estadoPrest "
					+ " and rel.estado = estadoRel ");
			
			query.setParameter("login", userLogin);
			query.setParameter("estadoRel", ETipoEstadoRelacion.ACTIVA.toBoolean());
			query.setParameter("estadoPrest", ETipoEstadoPrestador.ACTIVA.toBoolean());
			
			listRel =query.getResultList();
			
		} catch (Exception e) {
			logger.error("Error: RelacionDao.consultarRelacionUsuario "+e.getMessage(), e);
			throw new LoginAdminException("Error en RelacionDao.consultarRelacionUsuario", e);
		}
		return listRel;
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<RelacionEntity> consultarRelacionPrestador(List<Long> idsPrestadores) throws LoginAdminException {
		List<RelacionEntity> listRel = null;
		Query query = null;
		try {
			query = getEm().createQuery("select rel" 
					+ " from RelacionEntity rel " 
					+ " inner join fetch rel.prestador prest " 
					+ " where prest.cdperson IN ( :idsPrestadores ) "); 

			query.setParameter("idsPrestadores", idsPrestadores);
			
			listRel = query.getResultList();
			
		} catch (Exception e) {
			logger.error("Error: RelacionDao.consultarRelacionPrestador "+e.getMessage(), e);
			throw new LoginAdminException("Error en RelacionDao.consultarRelacionPrestador ", e);
		}
		return listRel;
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<RelacionEntity> consultarRelacionUsuarioDiferentePrest(String userLogin, int tipoRelacion, Long prestadorId) throws LoginAdminException {
		List<RelacionEntity> listRel = null;
		Query query = null;
		try {
			query = getEm().createQuery("select rel" 
					+ " from RelacionEntity rel " 
					+ " where LOWER(rel.usuario.userId) = :login " 
					+ " and rel.tipoRelacion = :tipoRel "
					+ " and rel.prestador.prestadorId <> :prestadorId ");
			
			query.setParameter("login", userLogin);
			query.setParameter("tipoRel", tipoRelacion);
			query.setParameter("prestadorId", prestadorId);
			
			listRel =query.getResultList();
			
		} catch (Exception e) {
			logger.error("Error: RelacionDao.consultarRelacionUsuarioDiferentePrest "+e.getMessage(), e);
			throw new LoginAdminException("Error en RelacionDao.consultarRelacionUsuarioDiferentePrest ", e);
		}
		return listRel;
	}

	public int desactivarRelacionesDePrestadores( List<Long> idPresadores, ETipoRelacion excepcion ) throws LoginAdminException {
		Query query = null;
		int res = 0;
		try {
			String sql = " UPDATE RelacionEntity SET estado = 0  WHERE prestador IN (SELECT p FROM PrestadorEntity p WHERE p.cdperson IN (:idPresadores)) AND tipoRelacion != :excepcion ";
			
			query = getEm().createQuery(sql);
			
			query.setParameter("idPresadores", idPresadores);
			query.setParameter("excepcion", excepcion.getId());	//No desactiva las relaciones de creación.
			
			res = query.executeUpdate();
		} catch (Exception e) {
			throw new LoginAdminException("Error en RelacionDao.desautorizarPrestadores ", e);
		}
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<RelacionEntity> consultarRelacionesDelUsuario(String userId, int tipoRelacion) throws LoginAdminException {
		List<RelacionEntity> listRel = null;
		Query query = null;
		String sql = "";
		try {
			sql += " select rel from RelacionEntity rel ";
			sql += " inner join fetch rel.prestador prest ";
			sql += " inner join fetch rel.usuario user ";
			sql += " where LOWER(rel.usuario.userId) = :userId ";
			sql += " and rel.tipoRelacion = :tipoRelacion ";
			sql += " and rel.estado = :estadoRel ";
			sql += " and prest.estado = :estadoPrest ";
			sql += " order by prest.razonSocial ";
			query = getEm().createQuery(sql);
			
			query.setParameter("userId", userId);
			query.setParameter("tipoRelacion", tipoRelacion);
			query.setParameter("estadoRel", ETipoEstadoRelacion.ACTIVA.toBoolean());
			query.setParameter("estadoPrest", ETipoEstadoPrestador.ACTIVA.toBoolean());
			
			listRel =query.getResultList();
		} catch (Exception e) {
			logger.error("Error: RelacionDao.consultarRelacionesDelUsuario "+e.getMessage(), e);
			throw new LoginAdminException("Error en RelacionDao.consultarRelacionesDelUsuario ", e);
		}
		return listRel;
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<RelacionEntity> consultarRelacionesDelPrestador(String idSucursalPrest, int tipoRelacion) throws LoginAdminException {
		List<RelacionEntity> listRel = null;
		Query query = null;
		String sql = "";
		try {
			sql += " select rel from RelacionEntity rel ";
			sql += " inner join fetch rel.prestador prest ";
			sql += " inner join fetch rel.usuario user ";
			sql += " where prest.sucursal = :idSucursalPrest ";
			sql += " and rel.tipoRelacion = :tipoRelacion ";
			sql += " and rel.estado = :estadoRel ";
			sql += " and prest.estado = :estadoPrest";
			sql += " order by user.userId ";
			query = getEm().createQuery(sql);
			
			query.setParameter("idSucursalPrest", idSucursalPrest);
			query.setParameter("tipoRelacion", tipoRelacion);
			query.setParameter("estadoRel", ETipoEstadoRelacion.ACTIVA.toBoolean());
			query.setParameter("estadoPrest", ETipoEstadoPrestador.ACTIVA.toBoolean());
			
			listRel =query.getResultList();
		} catch (Exception e) {
			logger.error("Error: RelacionDao.consultarRelacionesDelPrestador "+e.getMessage(), e);
			throw new LoginAdminException("Error en RelacionDao.consultarRelacionesDelPrestador ", e);
		}
		return listRel;
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PrestadorEntity> consultarPrestadoresAdministrados(String userLogin) throws LoginAdminException {
		List<PrestadorEntity> listPrest = null;
		Query query = null;
		try {
			query = getEm().createQuery("select distinct prest " 
					+ " from RelacionEntity rel " 
					+ " inner join rel.prestador prest " 
					+ " where LOWER(rel.usuario.userId) = :login and rel.tipoRelacion = :tipoRelacion " 
					+ " and rel.estado = :estado "
					+ " and prest.estado = :estadoPrest "
					+ " order by prest.razonSocial ");
			
			query.setParameter("login", userLogin);
			query.setParameter("estado", ETipoEstadoRelacion.ACTIVA.toBoolean());
			query.setParameter("tipoRelacion", ETipoRelacion.ADMINISTRADOR.getId());
			query.setParameter("estadoPrest", ETipoEstadoPrestador.ACTIVA.toBoolean());
			
			listPrest =query.getResultList();
			
		} catch (Exception e) {
			logger.error("Error: RelacionDao.consultarPrestadoresUser "+e.getMessage(), e);
			throw new LoginAdminException("Error en RelacionDao.consultarPrestadoresUser ", e);
		}
		return listPrest;
	}


	public List<String> consultarUsuariosSinRelacion(List<String> idUsuarios) {
		//Toca colocarle todas las relalciones disponibles.
		return consultarUsuariosSinRelacion(idUsuarios, Arrays.asList(ETipoRelacion.ADMINISTRADOR.getId(), ETipoRelacion.DELEGADO.getId()));
	}
	
	@SuppressWarnings("unchecked")
	public List<String> consultarUsuariosSinRelacion(List<String> idUsuarios, List<Integer> tipoRelacion){
		
		String select = "SELECT DISTINCT u.userId FROM RelacionEntity r JOIN r.usuario u WHERE u.userId IN (:idUsuarios) AND u.userId NOT IN (SELECT rela.usuario.userId from RelacionEntity rela where rela.estado = :activa AND rela.tipoRelacion IN (:tipoRelacion)) ";

		Query query = getEm().createQuery( select );
		query.setParameter("idUsuarios", idUsuarios);	
		query.setParameter("tipoRelacion", tipoRelacion);	
		query.setParameter("activa", ETipoEstadoRelacion.ACTIVA.toBoolean());	

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<String> consultarUsuariosRelacionadosConSucursales(List<Long> idPresadores){
		String select = "SELECT DISTINCT r.usuario.userId FROM RelacionEntity r WHERE r.prestador.cdperson IN (:idPresadores) AND r.estado = :activa)";
		
		Query query = getEm().createQuery( select );
		query.setParameter("idPresadores", idPresadores);	
		query.setParameter("activa", ETipoEstadoRelacion.ACTIVA.toBoolean());	

		return query.getResultList();
	}
	
	/**
	 * Consulta usuarios asociados a  las Sucursales retiradas
	 */
	@SuppressWarnings("unchecked")
	public List<String> consultarUsuariosRelacionadosConSucursalRetirada(String idSucursal){
		String select = "SELECT DISTINCT r.usuario.userId FROM RelacionEntity r WHERE r.prestador.sucursal = :idSucursal AND r.estado = :activa)";
		
		Query query = getEm().createQuery( select );
		query.setParameter("idSucursal", idSucursal);	
		query.setParameter("activa", ETipoEstadoRelacion.ACTIVA.toBoolean());	

		return query.getResultList();
	}
	
	/**
	 * Desactiva relaciones de usuarios con la sucursal suministrada
	 */
	
	public int desactivarRelacionesDeSucursal( String idSucursal, ETipoRelacion excepcion ){ 
		Query query = null;
		int res = 0;
		
		String sql = " UPDATE RelacionEntity SET estado = 0  WHERE prestador IN (SELECT p FROM PrestadorEntity p WHERE p.sucursal = :idSucursal) AND tipoRelacion != :excepcion ";
			
		query = getEm().createQuery(sql);
		query.setParameter("idSucursal", idSucursal);
		query.setParameter("excepcion", excepcion.getId());	//No desactiva las relaciones de creación.
			
		res = query.executeUpdate();
		
		return res;
	}
	
	/**
	 * Consulta usuarios asociados a  un Prestador
	 */
	@SuppressWarnings("unchecked")
	public List<String> consultarUsuariosRelacionadosConPrestador(Long idPrestador){
		String select = "SELECT DISTINCT r.usuario.userId FROM RelacionEntity r WHERE r.prestador.cdperson = :idPrestador AND r.estado = :activa)";
		
		Query query = getEm().createQuery( select );
		query.setParameter("idPrestador", idPrestador);	
		query.setParameter("activa", ETipoEstadoRelacion.ACTIVA.toBoolean());	

		return query.getResultList();
	}

	/**
	 * 
	 * Metodo encargado de consultar si un prestador tiene un administrador de prestadores asociado
	 * Modificado por Softmangement
     * Yency Serrano
     * 21/03/2013
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<RelacionEntity> consultaPrestadorTieneAdminPrestador(String nit) throws LoginAdminException {
		List<RelacionEntity> listPrest = null;
		Query query = null;
		try {
			query = getEm().createQuery("select distinct rel " 
					+ " from RelacionEntity rel " 
					+ " inner join fetch rel.prestador prest " 
					+ " where rel.tipoRelacion = :tipoRelacion " 
					+ " and rel.estado = :estado "
					+ " and prest.sucursal = :nit"); // + " and prest.numId = :nit"); --Se quito
									
			query.setParameter("nit",nit);
			query.setParameter("estado", ETipoEstadoRelacion.ACTIVA.toBoolean());
			query.setParameter("tipoRelacion", ETipoRelacion.ADMINISTRADOR.getId());
			
			listPrest =query.getResultList();
			
		} catch (Exception e) {
			logger.error("Error: RelacionDao.consultarPrestadoresUser "+e.getMessage(), e);
			throw new LoginAdminException("Error en RelacionDao.consultarPrestadoresUser ", e);
		}
		return listPrest;
	}
	
	
	
}

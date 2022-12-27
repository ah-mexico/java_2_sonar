package com.colsanitas.loginadmin.administracion.dao;


import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.sql.DataSource;


import org.jboss.seam.annotations.Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.colsanitas.loginadmin.administracion.dao.interfaces.IPrestadorDao;
import com.colsanitas.loginadmin.administracion.entity.PrestadorEntity;
import com.colsanitas.loginadmin.administracion.enums.ETipoEstadoPrestador;
import com.colsanitas.loginadmin.dto.DatosPrestadorDTO;
import com.colsanitas.loginadmin.dto.SucursalesByPrestadorDTO;
import com.colsanitas.loginadmin.exception.LoginAdminException;
import com.colsanitas.loginadmin.exception.NoDataFoundException;



/**
 * Define la funcionalidad necesaria para la persistencia y consulta de los prestadores.
 * 
 * @author armarquez
 *
 */


@Stateless
@Name("prestadorDao")
@Local(IPrestadorDao.class)
public class PrestadorDao extends TemplateDao<PrestadorEntity, Long> implements IPrestadorDao {
	private static final long serialVersionUID = 5864575670394071637L;
	
	private static Logger logger = LoggerFactory.getLogger(PrestadorDao.class);

	//Nombre del Paquete en Authorization
	private final String PCK_NAME = "PCK_PRESTADORES";
		
	//Nombre del DataSource de Authorization
	private static final String DS_AUTHORIZATION = "java:LoginAdminDatasource"; 
		
	//DataSource
	@Resource(mappedName=DS_AUTHORIZATION, name="ds", type=javax.sql.DataSource.class, shareable=false)
	private DataSource ds;
	
	
	public PrestadorDao(){
		super(PrestadorEntity.class);
	}
	
	public PrestadorDao(Class<PrestadorEntity> entityClass) {
		super(entityClass);
	}

	@SuppressWarnings("unchecked")
	public List<PrestadorEntity> consultarPrestadoresDelAdministrador(String userLogin) throws LoginAdminException {
		try {
			Query query = getEm().createQuery("select prest" 
					+ " from PrestadorEntity prest, " 
					+ "RelacionEntity rel " 
					+ " where prest.prestadorId = rel.prestador.prestadorId "
					+ " and LOWER(rel.usuario.userId) = :login");
			query.setParameter("login", userLogin);				
			List<PrestadorEntity> presList = query.getResultList();
			return presList;
		} catch (Exception e) {
			logger.error("Error: PrestadorDao.consultarPrestadoresDelAdministrador "+e.getMessage(), e);
			throw new LoginAdminException("Error en PrestadorDao.consultarPrestadoresDelAdministrador", e);
		}
		
	}
	
	public void registrarPrestador( PrestadorEntity prestadorEntity, boolean flush) throws LoginAdminException {
		try {
			create(prestadorEntity, flush);
		} catch (Exception e) {
			logger.error("Error: PrestadorDao.registrarPrestador "+e.getMessage(), e);
			throw new LoginAdminException("Error en PrestadorDao.registrarPrestador", e);
		}
	}
	
	public PrestadorEntity actualizarPrestador( PrestadorEntity prestadorEntity ) {
		return update(prestadorEntity, false);
	}

	public PrestadorEntity findById(Long id) throws LoginAdminException{
		try {
			return find(id);
		} catch (Exception e) {
			logger.error("Error: PrestadorDao.findById "+e.getMessage(), e);
			throw new LoginAdminException("Error en PrestadorDao.findById ", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public PrestadorEntity findByNumID(String numId) throws LoginAdminException {
		try {
			Query query = getEm().createQuery("select prest" 
					+ " from PrestadorEntity prest "  
					+ " where prest.numId = :numId");
					query.setParameter("numId", numId);		
			
					List<PrestadorEntity> presList =query.getResultList();
			return (presList.size() > 0 ? presList.get(0) : null);
		} catch (Exception e) {
			logger.error("Error: PrestadorDao.findByNumID "+e.getMessage(), e);
			throw new LoginAdminException("Error en PrestadorDao.findByNumID ", e);
		}
	}
	
	public PrestadorEntity consultarPrestador(Long cdperson) throws LoginAdminException {
		PrestadorEntity presList = null;
		try {
			Query query = getEm().createQuery("select prest" 
					+ " from PrestadorEntity prest "  
					+ " where prest.cdperson = :cdperson");
					query.setParameter("cdperson", cdperson);		
			
			presList = (PrestadorEntity) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			logger.error("Error: PrestadorDao.consultarPrestador "+e.getMessage(), e);
			throw new LoginAdminException("Error en PrestadorDao.consultarPrestador ", e);
		}
		
		return presList;
	}
	
	public PrestadorEntity consultarPrestador( PrestadorEntity prestadorEntity ) throws LoginAdminException {
		String sql = "";
		PrestadorEntity prestador = null;
		try {
			sql += " select prest from PrestadorEntity prest ";
			sql += " where prest.numId = :numId ";
			sql += " 	and prest.sucursal = :sucursal ";
			Query query = getEm().createQuery( sql );
			query.setParameter("numId", prestadorEntity.getNumId());
			query.setParameter("sucursal", prestadorEntity.getSucursal());
			prestador = (PrestadorEntity) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			logger.error("Error: PrestadorDao.consultarPrestador "+e.getMessage(), e);
			throw new LoginAdminException("Error en PrestadorDao.consultarPrestador ", e);
		}
		return prestador;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PrestadorEntity consultPrest( String codigoSucursal ) throws LoginAdminException {
		PrestadorEntity prestadorEntity = null;
		String sql = "";
		try {
			sql += " select distinct pres from PrestadorEntity pres where pres.sucursal = :sucursal ";
			Query query = getEm().createQuery(sql);
			query.setParameter("sucursal", codigoSucursal);	
			prestadorEntity = (PrestadorEntity) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			logger.error("Error: PrestadorDao.consultPrest "+e.getMessage(), e);
			throw new LoginAdminException("Error en PrestadorDao.consultPrest ", e);
		}
		return prestadorEntity;
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PrestadorEntity> listPrestadores(String razonSocial) throws LoginAdminException {
		List<PrestadorEntity> listPrest = null;
		Query query = null;
		String sql = "";
		try {
			sql += " select distinct pres from PrestadorEntity pres where UPPER(pres.razonSocial) like :razonSocial ";
			query = getEm().createQuery(sql);
			query.setParameter("razonSocial", (new StringBuilder("%")).append(razonSocial.toUpperCase().replaceAll(" ", "%")).append("%").toString());
			listPrest = query.getResultList();
		} catch (Exception e) {
			logger.error("Error: PrestadorDao.listPrestadores "+e.getMessage(), e);
			throw new LoginAdminException("Error en PrestadorDao.listPrestadores ", e);
		}
		return listPrest;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PrestadorEntity consultPrestActivo( PrestadorEntity entityPrest ) throws LoginAdminException {
		PrestadorEntity prestadorEntity = null;
		String sql = "";
		try {
			sql += " select distinct pres from PrestadorEntity pres where pres.estado = :estado and pres.sucursal = :sucursal ";
			Query query = getEm().createQuery(sql);
			query.setParameter("estado", ETipoEstadoPrestador.ACTIVA.toBoolean());
			query.setParameter("sucursal", entityPrest.getSucursal());	
			prestadorEntity = (PrestadorEntity) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			logger.error("Error: PrestadorDao.consultPrest "+e.getMessage(), e);
			throw new LoginAdminException("Error en PrestadorDao.consultPrest ", e);
		}
		return prestadorEntity;
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PrestadorEntity> listPrestadoresActivos( PrestadorEntity entityPrest ) throws LoginAdminException {
		List<PrestadorEntity> listPrest = null;
		Query query = null;
		String sql = "";
		try {
			sql += " select distinct pres from PrestadorEntity pres where pres.estado = :estado and UPPER(pres.razonSocial) like :razonSocial ";
			query = getEm().createQuery(sql);
			query.setParameter("estado", ETipoEstadoPrestador.ACTIVA.toBoolean());
			query.setParameter("razonSocial", (new StringBuilder("%")).append(entityPrest.getRazonSocial().toUpperCase().replaceAll(" ", "%")).append("%").toString());
			listPrest = query.getResultList();
		} catch (Exception e) {
			logger.error("Error: PrestadorDao.listPrestadores "+e.getMessage(), e);
			throw new LoginAdminException("Error en PrestadorDao.listPrestadoresActivos ", e);
		}
		return listPrest;
	}
	
	
	public int desactivarSucursal(String sucursalId){ 
		
		Query query = null;
		int res = 0;
		
		String sql = " UPDATE PrestadorEntity SET estado = 0  WHERE sucursal = :sucursalId ";
		
		query = getEm().createQuery(sql);
		query.setParameter("sucursalId", sucursalId);
			
		res = query.executeUpdate();
		
		return res;
		
	}

	
	public int desactivarPrestador(Long prestadorId){ 
		
		Query query = null;
		int res = 0;
		
		String sql = " UPDATE PrestadorEntity SET estado = 0  WHERE cdperson = :prestadorId ";
		
		query = getEm().createQuery(sql);
		query.setParameter("prestadorId", prestadorId);
			
		res = query.executeUpdate();
		
		return res;
		
	}
	/**
	 * Creado por Softmanagement
	 * Metodo encargado de consultar los prestadores de un dominio por numero de identificacion
	 * @param dominio
	 * @param numide
	 * @return
	 */
	@Override
	public PrestadorEntity consultarPrestadoresPorDominioNumIden(
			String dominio, String numIden) throws LoginAdminException {
		// TODO Auto-generated method stub
		
		StringBuilder hql = new StringBuilder(200);
		hql.append(" select distinct p.cdperson,p.num_id, p.tipo_id ,p.razonsocial,p.sucursal_id,p.estado,p.prestador_id " );
		hql.append(" from auth_tb_user_role ur,auth_tb_role r,auth_tb_domain a,auth_tb_prestador p");
		hql.append(" where ur.role_id = r.role_id and a.domain_id = r.domain_id");
		hql.append(" and p.prestador_id = ur.prestador_id");
		hql.append(" and a.domain_name = '");
		hql.append( dominio);
		hql.append("' and p.sucursal_id='");
		hql.append( numIden);
		hql.append("'");
		hql.append(" union");
		hql.append(" select distinct p.cdperson,p.num_id, p.tipo_id ,p.razonsocial,p.sucursal_id,p.estado,p.prestador_id ");
		hql.append(" from auth_tb_prestador p");
		hql.append(" where p.estado = 0");
		hql.append(" and p.sucursal_id='");
		hql.append( numIden);
		hql.append("'");
	    System.out.println(hql);
	
		
		Query query = getEm().createNativeQuery(hql.toString()).setHint("org.hibernate.readOnly", true);

		
		PrestadorEntity prestador = new PrestadorEntity();
		List resultado = query.getResultList();

		
		for (int i=0; i< resultado.size();i++){
			Object[] registro = (Object[])resultado.get(i); 
			
			
			prestador.setCdperson( ((BigDecimal)  registro[0]).longValue() );		 
			prestador.setNumId( (String) registro[1] );
			prestador.setTipoId((String) registro[2]);
			prestador.setRazonSocial((String)  registro[3] );
			prestador.setSucursal( (String )  registro[4] );
			prestador.setEstado( ((BigDecimal)registro[5]).intValue() == 1   );
			
			
			prestador.setPrestadorId(((BigDecimal)  registro[6]).longValue() );
				
		}
		
		return prestador;
	}
	/**
	 * Creado por Softmanagement
	 * Metodo encargado de consultar los prestadores de un dominio por nombre del prestador
	 * @param dominio
	 * @param numide
	 * @return
	 */
	
	@Override
	public List<PrestadorEntity> consultarPrestadoresPorDominioNombre(
			String dominio, String nombre) throws LoginAdminException {
		StringBuilder hql = new StringBuilder(200);
		hql.append(" select distinct p.cdperson,p.num_id, p.tipo_id ,p.razonsocial,p.sucursal_id,p.estado,p.prestador_id " );
		hql.append(" from auth_tb_user_role ur,auth_tb_role r,auth_tb_domain a,auth_tb_prestador p");
		hql.append(" where ur.role_id = r.role_id and a.domain_id = r.domain_id");
		hql.append(" and p.prestador_id = ur.prestador_id");
		hql.append(" and a.domain_name = '");
		hql.append( dominio);
		hql.append("'  and p.razonsocial like TRIM(UPPER('%");
		hql.append( nombre);
		hql.append("%'))  ");
		hql.append(" union");
		hql.append(" select distinct p.cdperson,p.num_id, p.tipo_id ,p.razonsocial,p.sucursal_id,p.estado,p.prestador_id ");
		hql.append(" from auth_tb_prestador p");
		hql.append(" where p.estado = 0");
		
		
		hql.append("  and p.razonsocial like TRIM(UPPER('%");
		hql.append( nombre);
		hql.append("%'))  ");
		
		Query query = getEm().createNativeQuery(hql.toString()).setHint("org.hibernate.readOnly", true);

		
		List <PrestadorEntity> prestadores = new ArrayList<PrestadorEntity>();
		
		
		
		List resultado = query.getResultList();

		
		for (int i=0; i< resultado.size();i++){
			Object[] registro = (Object[])resultado.get(i); 
			PrestadorEntity prestador = new PrestadorEntity();
			
			prestador.setCdperson(  ((BigDecimal)  registro[0]).longValue() );		 
			prestador.setNumId( (String) registro[1] );
			prestador.setTipoId((String) registro[2]);
			prestador.setRazonSocial((String)  registro[3] );
			prestador.setSucursal( (String )  registro[4] );
			prestador.setEstado( ((BigDecimal)registro[5]).intValue() == 1   );
			
			prestador.setPrestadorId(((BigDecimal)  registro[6]).longValue() );
			
			prestadores.add(prestador);
				
		}
		
		return prestadores;
		
	}
	
	
	public DatosPrestadorDTO consultaDatosPrestadorByID(DatosPrestadorDTO dtoIn) throws LoginAdminException {
				 
		
		DatosPrestadorDTO datosPrestador = null;
		DatosPrestadorDTO dtoPrestador=new DatosPrestadorDTO();
		
		 //Nombre del SP
		final String CALL_SP = "{CALL " + PCK_NAME + ".PC_CONSULTA_DATOS_PRESTADOR(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14)}";
		
		//Parametros entrada del SP
		final int I_CODIGO_PER = 1;		
		final int I_CODIGO_NEG = 2;
		final int I_ESTADO = 3;
		final int I_NOMBRES = 4;
		final int I_NRO_ID = 5;
		final int I_TIPO_ID = 6;
		final int I_RAZON_SOC = 7;
				 
		//Parametros salida del SP
		final int O_CODIGO_PER = 8;
		final int O_COD_TIPO_NEG = 9;
		final int O_ESTADO = 10;
		final int O_NOMBRES = 11;
		final int O_NRO_ID = 12;
		final int O_TIPO_ID = 13;
		final int O_RAZON_SOC = 14;
		 
		Connection conn = null;
		CallableStatement cstmt = null;
	try {
		//se obtiene la conexion JDBC
		conn = ds.getConnection();
		
		//Prepara el CallableStatement
		cstmt = conn.prepareCall(CALL_SP);
		
		//Parametros de Entrada
		dtoPrestador.setCodigoPersona(dtoIn.getCodigoPersona());
		dtoPrestador.setCodigoTipoIDNegocio(dtoIn.getCodigoTipoIDNegocio());
		dtoPrestador.setEstado(dtoIn.getEstado());
		dtoPrestador.setNombre(dtoIn.getNombre());
		dtoPrestador.setNumeroID(dtoIn.getNumeroID());
		dtoPrestador.setRazonSocial(dtoIn.getRazonSocial());
		dtoPrestador.setTipoID(dtoIn.getTipoID());
		
		
		cstmt.setString(I_CODIGO_PER, dtoPrestador.getCodigoPersona());
		cstmt.setString(I_CODIGO_NEG, dtoPrestador.getCodigoTipoIDNegocio());
		cstmt.setString(I_ESTADO, dtoPrestador.getEstado());
		cstmt.setString(I_NOMBRES, dtoPrestador.getNombre());
		cstmt.setString(I_NRO_ID, dtoPrestador.getNumeroID());
		cstmt.setString(I_TIPO_ID, dtoPrestador.getTipoID().toString());
		cstmt.setString(I_RAZON_SOC, dtoPrestador.getRazonSocial());
				
		
	 //Parametros de Salida
	    cstmt.registerOutParameter(O_CODIGO_PER, oracle.jdbc.OracleTypes.VARCHAR);
		cstmt.registerOutParameter(O_COD_TIPO_NEG, oracle.jdbc.OracleTypes.VARCHAR);
		cstmt.registerOutParameter(O_ESTADO, oracle.jdbc.OracleTypes.VARCHAR);
		cstmt.registerOutParameter(O_NOMBRES, oracle.jdbc.OracleTypes.VARCHAR);
		cstmt.registerOutParameter(O_NRO_ID, oracle.jdbc.OracleTypes.VARCHAR);
		cstmt.registerOutParameter(O_TIPO_ID, oracle.jdbc.OracleTypes.VARCHAR);
		cstmt.registerOutParameter(O_RAZON_SOC, oracle.jdbc.OracleTypes.VARCHAR);
		
	//Invocacion del SP
		cstmt.execute();
		
	//Obtiene los parametros de Salida
	 if (cstmt.getString(O_CODIGO_PER)!=null){
		 datosPrestador=new DatosPrestadorDTO();
		 datosPrestador.setCodigoPersona(cstmt.getString(O_CODIGO_PER)); 
		 datosPrestador.setCodigoTipoIDNegocio(cstmt.getString(O_COD_TIPO_NEG));
		 datosPrestador.setEstado(cstmt.getString(O_ESTADO));
		 datosPrestador.setNombre(cstmt.getString(O_NOMBRES));
		 datosPrestador.setNumeroID(cstmt.getString(O_NRO_ID));
		 datosPrestador.setTipoID(cstmt.getLong(O_TIPO_ID));
		 datosPrestador.setRazonSocial(cstmt.getString(O_RAZON_SOC));
	 }
	  return datosPrestador;
    } catch(Exception e) {
		  throw new LoginAdminException("Error ejecutando consultaDatosPrestadorByID por.." + e.getMessage(), e);
		}finally{			
			try{
				 if(cstmt != null){
					cstmt.close();
					cstmt = null;
					}	
				}catch (SQLException e) {
					logger.warn("Error cerrando CallableStatement en consultaDatosPrestadorByID..", e);
				}
			try{
				if(conn != null){
						conn.close();
						conn = null;
				}	
			}catch (SQLException e) {
					logger.warn("Error cerrando Connection en consultaDatosPrestadorByID.. ", e);
				}
			}
		}
 
 public List<SucursalesByPrestadorDTO> consultaSucursalesPrestadorByID(SucursalesByPrestadorDTO dtoIn) throws NoDataFoundException,LoginAdminException {
	  final String CALL_SP = "{CALL " + PCK_NAME + ".PC_CONSULTA_SUC_PRESTADOR(?1,?2,?3,?4,?5,?6)}";
	
	 //	 Parametros de entrada/salida de la FN
	 final int I_CODIGO_PER = 1;
	 final int I_CODIGO_PRE = 2;
	 final int I_NRO_ID = 3;
	 final int I_NOM_SUC = 4;
	 final int I_TIPO_ID = 5;
	 final int O_PRESTADOR = 6;
			
	//Parametros del Cursor
	 final int v_cod_pre = 1;
	 final int COD_SUC = 2;
     final int NOM_SUC = 3;
     final int O_I_NRO_ID = 4;
     final int O_I_TIPO_ID = 5;
     
     Connection conn = null;
	 CallableStatement cstmt = null;
	 ResultSet rs = null;
	  try {
			
			//se obtiene la conexion JDBC
			conn = ds.getConnection();
			
			//Prepara el CallableStatement
			cstmt = conn.prepareCall(CALL_SP);
			
			//Parametros de Entrada
			cstmt.setString(I_CODIGO_PER,dtoIn.getCodigoPersonaPrestador());
			cstmt.setString(I_CODIGO_PRE, dtoIn.getCodigoPrestador());
			cstmt.setString(I_NRO_ID, dtoIn.getNumeroIDPrestador());
			cstmt.setString(I_NOM_SUC, dtoIn.getSucursalPrestador());
			cstmt.setString(I_TIPO_ID, dtoIn.getTipoIDPrestador());
						
			//Parametros de Salida
			cstmt.registerOutParameter(O_PRESTADOR, oracle.jdbc.OracleTypes.CURSOR);
				
			//Invocacion del SP
			cstmt.execute();
	    
			//Obtiene el valor de retorno
			List<SucursalesByPrestadorDTO> listSucursal = new ArrayList<SucursalesByPrestadorDTO>(); 
			
			rs = (ResultSet) cstmt.getObject(O_PRESTADOR);
			
		
			while(rs.next()){
				SucursalesByPrestadorDTO sucursal = new SucursalesByPrestadorDTO();
				
				sucursal.setCodigoPersonaPrestador(rs.getString(v_cod_pre));
				sucursal.setCodigoPrestador(rs.getString(COD_SUC));
				sucursal.setNumeroIDPrestador(rs.getString(O_I_NRO_ID));
				sucursal.setSucursalPrestador(rs.getString(NOM_SUC));
				sucursal.setTipoIDPrestador(rs.getString(O_I_TIPO_ID));
				
				listSucursal.add(sucursal);
			 }
			
			//Valida si la consulta no trae sucursales para el prestador
			if(listSucursal.isEmpty()){
				throw new NoDataFoundException("No se encontraron sucursales para el prestador ingresado "+ dtoIn.getTipoIDPrestador()+ " y numero de documento "+  dtoIn.getNumeroIDPrestador());
			}
					
			return listSucursal;
			
		}catch (NoDataFoundException e) {
			throw e;
		}catch(Exception e) {
			if (e.getMessage() != null && e.getMessage().startsWith("Cursor is closed")) {
             	return new ArrayList<SucursalesByPrestadorDTO>();
			 }
			 else {
			throw new LoginAdminException("Error ejecutando PrestadorDao.consultaSucursalesPrestadorByID para tipo de documento "+ dtoIn.getTipoIDPrestador() + " y numero de documento "+ dtoIn.getNumeroIDPrestador(), e);
			 }
		}finally{
			try{
				if(rs != null){
					rs.close();
					rs = null;
				}	
			}catch (SQLException e) {
				logger.warn("Error cerrando ResultSet en PrestadorDao.consultaSucursalesPrestadorByID", e);
			}
			
			try{
				if(cstmt != null){
					cstmt.close();
					cstmt = null;
				}	
			}catch (SQLException e) {
				logger.warn("Error cerrando CallableStatement en PrestadorDao.consultaSucursalesPrestadorByID", e);
			}
			
			try{
				if(conn != null){
					conn.close();
					conn = null;
				}	
			}catch (SQLException e) {
				
				logger.warn("Error cerrando Connection en PrestadorDao.consultaSucursalesPrestadorByID", e);
			}
		}
	}
 
	
}	



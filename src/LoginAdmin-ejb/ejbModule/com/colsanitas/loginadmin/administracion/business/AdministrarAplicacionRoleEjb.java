package com.colsanitas.loginadmin.administracion.business;

import static org.jboss.seam.ScopeType.STATELESS;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.xml.namespace.QName;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.loginadmin.administracion.dao.interfaces.IUserRolDao;
import com.colsanitas.loginadmin.administracion.entity.UserRoleEntity;
import com.colsanitas.loginadmin.administracion.utils.EAccionPlantillaRole;
import com.colsanitas.loginadmin.administracion.utils.EtipoRolLoginAdmin;
import com.colsanitas.loginadmin.exception.LoginAdminException;
import com.osi.gaudi.cfg.Configurator;
import com.osi.gaudi.exception.Failure;
import com.osi.gaudi.security.authorization.clientews.Domain;
import com.osi.gaudi.security.authorization.clientews.IAuthorization;
import com.osi.gaudi.security.authorization.clientews.Role;
import com.osi.gaudi.security.authorization.clientews.ServiceAuthorization;

@Scope(STATELESS)
@Stateless
@Name("administrarAplicacionRoleEjb")
@Local(IAdministrarAplicacionRoledEjb.class)
public class AdministrarAplicacionRoleEjb implements IAdministrarAplicacionRoledEjb, Serializable {

	private static final long serialVersionUID = -8810911142084929353L;

	private final Logger logger = LoggerFactory.getLogger(AdministrarAplicacionRoleEjb.class);
	
	@In(create = true)
	private IUserRolDao userRolDao;
	
	private static final String targetNameSpace="http://ws.authorization.security.gaudi.osi.com/";
	private static final String serviceName="serviceAuthorization";
	private static final QName qname = new QName(targetNameSpace, serviceName);
	private URL endpoint_authorization = null;
	private IAuthorization authorization;
	
	private final String wsdlAuthorization = Configurator.getInstance().getString("sistema", "wsdlAuthorization","http://desarrollo.colsanitas.com:8080/Authorization/Authorization?wsdl");

	public List<UserRoleEntity> listarApliRoleUser( UserRoleEntity userRoleEntity, boolean check ) throws Exception {
		List<UserRoleEntity> listUserRole = null;
		List<UserRoleEntity> listUserRoleFinal = null;
		Role rol = null;
		List<String> listDomainNoPermitidos = null;
		boolean isDomainPermitido = true;
		try {
			listUserRole = userRolDao.listarRoleUserPrest( userRoleEntity );
			if( listUserRole != null && !listUserRole.isEmpty() ){
				if ( authorization == null ) {
					establecerConexionWSAuthorization();
				}
				if( listUserRole != null && !listUserRole.isEmpty() ){
					listUserRoleFinal = new LinkedList<UserRoleEntity>();
					listDomainNoPermitidos = Configurator.getInstance().getArray("administracion", "domainNoPermitidos", null);
					for( UserRoleEntity userRole: listUserRole ) {
						isDomainPermitido = true;
						rol = authorization.getRoleById( userRole.getRoleId() );
						logger.info("::: listarApliUser rol " + isDomainPermitido);
						if( rol != null ){
							if( listDomainNoPermitidos != null && !listDomainNoPermitidos.isEmpty() ){
								listDomainNoPer:
								for( String domainNoPermitido: listDomainNoPermitidos ) {
									if( rol.getDomain() != null && rol.getDomain().getShortName().equals( domainNoPermitido ) ){
										isDomainPermitido = false;
										break listDomainNoPer;
									}
								}
							} 
						} else {
							isDomainPermitido = false;
						}
						logger.info("Validacion isDomainPermitido  " + isDomainPermitido);
						if( isDomainPermitido ) {
							userRole.setAccion( EAccionPlantillaRole.ROLE_PERSIST.getMessage() );
							userRole.setRole( rol );
							//modificacion softmanagement 10/04/2013 userRole.setSelected( check );
							
							listUserRoleFinal.add( userRole );
						}
						
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error: AdministrarAplicacionRoleEjb.listarApliRoleUser  "+e.getMessage(), e);
			throw new LoginAdminException("Error en AdministrarAplicacionRoleEjb.listarApliRoleUser  ", e);
		} finally {
			listUserRole = null;
		}
		
		
		
		
		return listUserRoleFinal;
	}

	public void registrarAplicacionRoleUser(List<UserRoleEntity> listApliRoleReg) throws LoginAdminException {
		UserRoleEntity existUserRoleEntity = null;
		try {
			if( listApliRoleReg != null && !listApliRoleReg.isEmpty() ) {
				for( UserRoleEntity userRolePrest: listApliRoleReg ) {
					existUserRoleEntity = userRolDao.consultarRelUserRol( userRolePrest );
					if( userRolePrest.getAccion().equals( EAccionPlantillaRole.ROLE_INSERT.getMessage() )  ) {
						if( existUserRoleEntity == null ) {
							userRolDao.registrarUserRolEntity(userRolePrest, false);
						}
					} else if( userRolePrest.getAccion().equals( EAccionPlantillaRole.ROLE_DELETE.getMessage() ) ){
						if( existUserRoleEntity != null ) {
							userRolDao.deleteFromUserRole( existUserRoleEntity );
						}
					}
					
				}
			}
		} catch (LoginAdminException e) {
			logger.error("Ocurrio un error al tratar de registrar la lista de aplicaciones roles  ", e);
			throw new LoginAdminException("Error en AdministrarAplicacionRoleEjb.listarApliRoleUser", e);
		} catch ( Exception e ){
			logger.error("Ocurrio un error al tratar de registrar la lista de aplicaciones roles  ", e);
			throw new LoginAdminException("Error en AdministrarAplicacionRoleEjb.listarApliRoleUser", e);
		}
		
	}
	
	private void establecerConexionWSAuthorization() {
		// Establece la conexion con el servicio de Authorizacion
		try {
			logger.info("wsdlAuthorization:  " + wsdlAuthorization);
			endpoint_authorization = new URL(wsdlAuthorization);
			ServiceAuthorization sa = new ServiceAuthorization(endpoint_authorization, qname);
		    authorization = sa.getPortAuthorization();
		} catch (MalformedURLException e) {
			logger.error("No se pudo cargar el servicio de authorizacion", e);
			throw new Failure("AuthorizationNotLoaded", "No se pudo cargar el servicio de authorizacion", e);
		} catch ( Exception e ) {
			e.printStackTrace();
			logger.error("Ocurrío un error al tratar de conectarce al web service Authorization:  ", e);
		}
	}
	
	public void registrarAplicacionRoleUser(List<UserRoleEntity> listApliRoleReg, String tipoRolLA) throws LoginAdminException {
		UserRoleEntity existUserRoleEntity = null;
		try {
			if( listApliRoleReg != null && !listApliRoleReg.isEmpty() ) {
				for( UserRoleEntity userRolePrest: listApliRoleReg ) {
					existUserRoleEntity = userRolDao.consultarRelUserRol( userRolePrest );
					if( userRolePrest.getAccion().equals( EAccionPlantillaRole.ROLE_INSERT.getMessage() )  ) {
						if( existUserRoleEntity == null ) {
							if( tipoRolLA.equals( EtipoRolLoginAdmin.ADMINISTRADOR.getCode() ) ) {
								userRolePrest.setTipoRolLA(  EtipoRolLoginAdmin.ADMINISTRADOR.getCode() );
								userRolDao.registrarUserRolEntity(userRolePrest, false);
							} else if( tipoRolLA.equals( EtipoRolLoginAdmin.DELEGADO.getCode() ) ) {
								userRolePrest.setTipoRolLA(  EtipoRolLoginAdmin.DELEGADO.getCode() );
								userRolDao.registrarUserRolEntity(userRolePrest, false);
							}
						} else if( tipoRolLA.equals( EtipoRolLoginAdmin.ADMINISTRADOR.getCode() ) && existUserRoleEntity.getTipoRolLA() != null && existUserRoleEntity.getTipoRolLA().equals( EtipoRolLoginAdmin.DELEGADO.getCode() )) {
							existUserRoleEntity.setTipoRolLA(  EtipoRolLoginAdmin.ADMINDELE.getCode() );
							userRolDao.actualizarUsuarioEntity(existUserRoleEntity);
						} else if( tipoRolLA.equals( EtipoRolLoginAdmin.DELEGADO.getCode() ) && existUserRoleEntity.getTipoRolLA() != null && existUserRoleEntity.getTipoRolLA().equals( EtipoRolLoginAdmin.ADMINISTRADOR.getCode() )) {
							existUserRoleEntity.setTipoRolLA(  EtipoRolLoginAdmin.ADMINDELE.getCode() );
							userRolDao.actualizarUsuarioEntity(existUserRoleEntity);
						} else if( tipoRolLA.equals( EtipoRolLoginAdmin.ADMINISTRADOR.getCode() ) ) {
							existUserRoleEntity.setTipoRolLA(  EtipoRolLoginAdmin.ADMINISTRADOR.getCode() );
							userRolDao.actualizarUsuarioEntity(existUserRoleEntity);
						} else if( tipoRolLA.equals( EtipoRolLoginAdmin.DELEGADO.getCode() ) ) {
							existUserRoleEntity.setTipoRolLA(  EtipoRolLoginAdmin.DELEGADO.getCode() );
							userRolDao.actualizarUsuarioEntity(existUserRoleEntity);
						} 
					} else if( userRolePrest.getAccion().equals( EAccionPlantillaRole.ROLE_DELETE.getMessage() ) ){
						if( existUserRoleEntity != null && tipoRolLA.equals( EtipoRolLoginAdmin.ADMINISTRADOR.getCode() ) && existUserRoleEntity.getTipoRolLA() != null && existUserRoleEntity.getTipoRolLA().equals( EtipoRolLoginAdmin.ADMINISTRADOR.getCode() ) ) {
							userRolDao.deleteFromUserRole( existUserRoleEntity );
						} else if( existUserRoleEntity != null && tipoRolLA.equals( EtipoRolLoginAdmin.ADMINISTRADOR.getCode() ) && existUserRoleEntity.getTipoRolLA() != null && existUserRoleEntity.getTipoRolLA().equals( EtipoRolLoginAdmin.ADMINDELE.getCode() ) ){
							existUserRoleEntity.setTipoRolLA(  EtipoRolLoginAdmin.DELEGADO.getCode() );
							userRolDao.actualizarUsuarioEntity(existUserRoleEntity);
						} else if( existUserRoleEntity != null && tipoRolLA.equals( EtipoRolLoginAdmin.DELEGADO.getCode() ) && existUserRoleEntity.getTipoRolLA() != null && existUserRoleEntity.getTipoRolLA().equals( EtipoRolLoginAdmin.DELEGADO.getCode() ) ){
							userRolDao.deleteFromUserRole( existUserRoleEntity );
						} else if( existUserRoleEntity != null && tipoRolLA.equals( EtipoRolLoginAdmin.DELEGADO.getCode() ) && existUserRoleEntity.getTipoRolLA() != null && existUserRoleEntity.getTipoRolLA().equals( EtipoRolLoginAdmin.ADMINDELE.getCode() ) ){
							existUserRoleEntity.setTipoRolLA(  EtipoRolLoginAdmin.ADMINISTRADOR.getCode() );
							userRolDao.actualizarUsuarioEntity(existUserRoleEntity);
						} else if( existUserRoleEntity != null ) {
							userRolDao.deleteFromUserRole( existUserRoleEntity );
						}
					}
				}
			}
		} catch (LoginAdminException e) {
			logger.error("Ocurrio un error al tratar de registrar la lista de aplicaciones roles  ", e);
			throw new LoginAdminException("Error en AdministrarAplicacionRoleEjb.listarApliRoleUser", e);
		} catch ( Exception e ){
			logger.error("Ocurrio un error al tratar de registrar la lista de aplicaciones roles  ", e);
			throw new LoginAdminException("Error en AdministrarAplicacionRoleEjb.listarApliRoleUser", e);
		}
		
	}
	
	
	
	
	 /**
	  * Metodo encargado de consumir el servicio para consultar los dominios q 
	  * estan asociados al usuario q esta logueado
	  * Softmanagement S.A. 
	  * Yency Serrano .
	  * 20/03/2013
	  */
	public  List<UserRoleEntity> consultarAdministradorDominio(String userlogin, List<UserRoleEntity> listUserRoleEntity) {
		
		
		List<Domain> domainList = null;
		try {
			domainList = cargarDominioUser(userlogin);
		} catch (LoginAdminException e) {
			// TODO Auto-generated catch block
			logger.error("Ocurrio un error al tratar de cargar la lista de dominios q tiene el usuario", e);
			
			e.printStackTrace();
		}
	
		
		if(domainList != null && listUserRoleEntity != null ){
		
		
			for(Domain dominio:domainList){
				
				for(UserRoleEntity  userRol:listUserRoleEntity){
					if(dominio.getName().equalsIgnoreCase(userRol.getRole().getDomain().getName())){
							userRol.setAdministradorDominio(true);
					}
					
				}
				
			}
		}
		return listUserRoleEntity;
		
	}
	/**
	 * Lista todos los dominios donde el usuario es administrador consumiendo el
	 * web service de authorization
	 * 
	 * @author Yasser Pérez
	 * @param userLogin
	 *            usuario de consulta para listar los dominios asociados
	 * @throws LoginAdminException
	 *             control de excepciones propias de la aplicación.
	 * @return Lista de dominios asociados al usuario de consulta
	 */
	public List<Domain> cargarDominioUser(String userLogin)
			throws LoginAdminException {
		List<Domain> listDomain = null;
		try {
			if (authorization == null) {
				establecerConexionWSAuthorization();
			}
			listDomain = authorization.getDomainsByAdministrator(userLogin)
					.getItem();
		} catch (Exception e) {
			listDomain = null;
			logger.error(
					"Error: AdministrarAplicacionRoleEjb.cargarDomunioUser  "
							+ e.getMessage(), e);
			throw new LoginAdminException(
					"Error en AdministrarAplicacionRoleEjb.cargarDominioUser ",
					e);
		} finally {
		}
		return listDomain;
	}

}

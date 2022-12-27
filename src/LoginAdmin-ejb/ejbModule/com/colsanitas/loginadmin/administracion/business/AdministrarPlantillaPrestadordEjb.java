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
import org.jboss.seam.security.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.loginadmin.administracion.dao.interfaces.IPresRolTemplateDao;
import com.colsanitas.loginadmin.administracion.dao.interfaces.IUserRolDao;
import com.colsanitas.loginadmin.administracion.entity.PresRolTemplateEntity;
import com.colsanitas.loginadmin.administracion.entity.PrestadorEntity;
import com.colsanitas.loginadmin.administracion.entity.UserRoleEntity;
import com.colsanitas.loginadmin.administracion.utils.EAccionPlantillaRole;
import com.colsanitas.loginadmin.administracion.utils.EtipoRolLoginAdmin;
import com.colsanitas.loginadmin.exception.LoginAdminException;
import com.osi.gaudi.cfg.Configurator;
import com.osi.gaudi.exception.Failure;
import com.osi.gaudi.security.authorization.clientews.Domain;

import com.osi.gaudi.security.authorization.clientews.GroupRole;
import com.osi.gaudi.security.authorization.clientews.IAuthorization;
import com.osi.gaudi.security.authorization.clientews.Role;
import com.osi.gaudi.security.authorization.clientews.RoleArray;
import com.osi.gaudi.security.authorization.clientews.ServiceAuthorization;



@Scope(STATELESS)
@Stateless
@Name("administrarPlantillaPrestadordEjb")
@Local(IAdministrarPlantillaPrestadordEjb.class)
public class AdministrarPlantillaPrestadordEjb implements IAdministrarPlantillaPrestadordEjb, Serializable {

	private static final long serialVersionUID = -3821937830124478220L;
	
	private final Logger logger = LoggerFactory.getLogger(AdministrarPlantillaPrestadordEjb.class);
	
	@In(create = true)
	private IPresRolTemplateDao presRolTemplateDao;
	
	@In(create = true)
	private IUserRolDao userRolDao;
	
	
	@In(create=true)
    private IGestionUsuariosEjb gestionUsuariosEjb;
	
    @In Credentials credentials;
	
	private static final String targetNameSpace="http://ws.authorization.security.gaudi.osi.com/";
	private static final String serviceName="serviceAuthorization";
	private static final QName qname = new QName(targetNameSpace, serviceName);
	private URL endpoint_authorization = null;
	private IAuthorization authorization;
	
	private final String wsdlAuthorization = Configurator.getInstance().getString("sistema", "wsdlAuthorization","http://desarrollo.colsanitas.com:8080/Authorization/Authorization?wsdl");

	public PresRolTemplateEntity consultarPlantillaPrestador( PresRolTemplateEntity presRolTemplateEntity ) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public PresRolTemplateEntity actualizarPlantillaPrestador( PresRolTemplateEntity presRolTemplateEntity, boolean flush ) throws LoginAdminException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	  * Lista todos los dominios donde el usuario es administrador
	  * consumiendo el web service de authorization
	  *	@author Yasser Pérez
	  * @param userLogin usuario de consulta para listar los dominios asociados
	  * @throws LoginAdminException control de excepciones propias de la aplicación.
	  * @return Lista de dominios asociados al usuario de consulta
	  */
	public List<Domain> cargarDominioUser( String userLogin ) throws LoginAdminException {
		List<Domain> listDomain = null;
		try {
			if ( authorization == null ) {
				establecerConexionWSAuthorization();
			}
			listDomain = authorization.getDomainsByAdministrator( userLogin ).getItem();
		} catch (Exception e) {
			listDomain = null;
			logger.error("Error: AdministrarPlantillaPrestadordEjb.cargarDomunioUser  "+e.getMessage(), e);
			throw new LoginAdminException("Error en AdministrarPlantillaPrestadordEjb.cargarDominioUser ", e);
		} finally {}
		return listDomain;
	}
	
	/**
	  * Lista todos los grupos de roles asociados al dominio
	  * @author Yasser Pérez
	  * @param idDomain identificacion del dominio de consulta
	  * @throws LoginAdminException control de excepciones propias de la aplicación.
	  * @return Lista de grupos de roles asociados al dominio de consulta
	  */
	public List<GroupRole> cargarGrupoRolDomain( Long idDomain ) throws LoginAdminException {
		List<GroupRole> listGroupRole = null;
		try {
			if ( authorization == null ) {
				establecerConexionWSAuthorization();
			}
			listGroupRole = authorization.getGroupRolesbyDomain( idDomain ).getItem();
		} catch (Exception e) {
			listGroupRole = null;
			logger.error("Error: AdministrarPlantillaPrestadordEjb.cargarDomunioUser  "+e.getMessage(), e);
			throw new LoginAdminException("Error en AdministrarPlantillaPrestadordEjb.cargarGrupoRolDomain ", e);
		} finally {}
		return listGroupRole;
	}
	
	/**
	  * Lista todos los roles asociados al id del grupo rol y al nombre del dominio
	  * @author Yasser Pérez
	  * @param groupRole objeto con datos de id del grupo rol y nombre corto del dominio
	  * @throws LoginAdminException control de excepciones propias de la aplicación.
	  * @return Lista de roles asociados
	  */
	public List<Role> cargarRolesByDomainGrpRole( GroupRole groupRole ) throws LoginAdminException {
		List<Role> listRole = null;
		RoleArray roles = null;
		try {
			if ( authorization == null ) {
				establecerConexionWSAuthorization();
			}
			roles = authorization.getRolesByDomainGrpRole(groupRole.getId(), groupRole.getDomain().getShortName());
			if( roles != null ) {
				listRole= new LinkedList<Role>();
				for( Role  rol: roles.getItem()){
					listRole.add(rol);
				}	
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {}
		return listRole;
	}
	
	public List<PresRolTemplateEntity> listarPlantillaPrestador( PrestadorEntity prestadorEntity, boolean estado, boolean check ) throws Exception {
		List<PresRolTemplateEntity> listRolPlantilla = null;
		try {
			listRolPlantilla = presRolTemplateDao.listarPlantillaPrestador( prestadorEntity, estado );
			if( listRolPlantilla != null && !listRolPlantilla.isEmpty() ) {
				if ( authorization == null ) {
					establecerConexionWSAuthorization();
				}
				for( PresRolTemplateEntity rolTemplate: listRolPlantilla ) {
					rolTemplate.setAccion(EAccionPlantillaRole.ROLE_PERSIST.getMessage());
					rolTemplate.setRole( authorization.getRoleById( rolTemplate.getRoleId() ) );
					
					//rolTemplate.setSelected( check );
				}
				
				//Realizar cambio yency
				//Modificado por Softmanagement
				//Abril 10 de 2012
				
				List<Domain> domainList = null;
				
				
				try {
					domainList = cargarDominioUser(credentials.getUsername());
				} catch (LoginAdminException e) {
					// TODO Auto-generated catch block
					logger.error("Error: AdministraPlantillaRolWeb.cargarDominioUser  "+e.getMessage(), e);
					e.printStackTrace();
				}
					
				if( domainList != null && !domainList.isEmpty() ) {
					
					consultarAdministradorDominio(domainList ,listRolPlantilla);
				
				}
				
				//fin
			}
		} catch (Exception e) {
			logger.error("Error: AdministrarAutorizacionEjb.consultarRelacionUserDesau  "+e.getMessage(), e);
			throw new LoginAdminException("Error en AdministrarAutorizacionEjb.consultarRelacionUserDesau", e);
		}
		return listRolPlantilla;
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
	
	public void registrarPlantillaPrestador( List<PresRolTemplateEntity> listRolTemp ) throws LoginAdminException {
		PresRolTemplateEntity existRolTemplate = null;
		try {
			if( listRolTemp != null && !listRolTemp.isEmpty() ) {
				for( PresRolTemplateEntity rolTemplate: listRolTemp ) {
					existRolTemplate = presRolTemplateDao.consultarPlantillaPrestador( rolTemplate );
					if( rolTemplate.getAccion().equals( EAccionPlantillaRole.ROLE_INSERT.getMessage() )  ) {
						if( existRolTemplate != null ) {
							if( !existRolTemplate.isEstado() ){
								existRolTemplate.setRole( rolTemplate.getRole() );
								existRolTemplate.setEstado( rolTemplate.isEstado() );
								presRolTemplateDao.actualizarPlantillaPrestador(existRolTemplate, false );
							}
						} else {
							presRolTemplateDao.registrarPlantillaPrestador(rolTemplate, false);
						}
					} else if( rolTemplate.getAccion().equals( EAccionPlantillaRole.ROLE_DELETE.getMessage() ) ){
						if( existRolTemplate != null ) {
							if( existRolTemplate.isEstado() ){
								existRolTemplate.setEstado( rolTemplate.isEstado() );
								presRolTemplateDao.actualizarPlantillaPrestador(existRolTemplate, false);
								actualizarUserRol(existRolTemplate.getPrestadorId(), existRolTemplate.getRoleId());
							}
						}
					}
					
				}
			}
		} catch (LoginAdminException e) {
			logger.error("Ocurrio un error al tratar de registrar la plantilla de roles  ", e);
			throw new LoginAdminException("Error en AdministrarPlantillaPrestadordEjb.registrarPlantillaPrestador", e);
		} catch ( Exception e ){
			logger.error("Ocurrio un error al tratar de registrar la plantilla de roles  ", e);
			throw new LoginAdminException("Error en AdministrarPlantillaPrestadordEjb.registrarPlantillaPrestador", e);
		}
	}

	
	/**
	 * Metodo que actualiza los permisos de las aplicaciones a los delegados
	 * @param prestadorId
	 * @param roleId
	 * @throws Exception 
	 */
	private void actualizarUserRol(Long prestadorId, Long roleId) throws Exception {
		
		List<UserRoleEntity> userRolList = userRolDao.listarRolePrest(prestadorId, roleId);
		
		for (UserRoleEntity ur : userRolList){
			if(ur.getTipoRolLA().equals(EtipoRolLoginAdmin.ADMINDELE.getCode())){
				ur.setTipoRolLA(EtipoRolLoginAdmin.ADMINISTRADOR.getCode());
			}else if(ur.getTipoRolLA().equals(EtipoRolLoginAdmin.DELEGADO.getCode())){
				userRolDao.deleteFromUserRole(ur);
			}
		}
		
	}

	
	

	
	 /**
	  * Metodo encargado de consumir el servicio para consultar los dominios q 
	  * estan asociados al usuario q esta logueado
	  * Softmanagement S.A. 
	  * Yency Serrano .
	  * 09/04/2013
	  */
	public boolean consultarAdministradorDominio(List<Domain> dominios, List<PresRolTemplateEntity> listRolePlantPrest) {
		
		//Se busca si el usuario logueado es funcionario osi para mostrarle o no los check
		boolean administradorFunOsi = gestionUsuariosEjb.tieneRolFuncionarioOsi(credentials.getUsername() ,Configurator.getInstance().getString("administracion", "USER_LOGIN_ADMIN",null));
			
		for(Domain dominio:dominios){
			for(PresRolTemplateEntity  presRol:listRolePlantPrest){
				
				
				if(dominio.getName().equalsIgnoreCase(presRol.getRole().getDomain().getName()) || !administradorFunOsi){
					
					presRol.setAdministradorDominio(true);
					//presRol.setSelected(true);
				}
				
			}
			
		}
			return false;
		
	}

}

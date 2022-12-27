package com.colsanitas.loginadmin.administracion.business;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.namespace.QName;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.loginadmin.administracion.dao.interfaces.IRelacionDao;
import com.colsanitas.loginadmin.administracion.dao.interfaces.IUsuarioDao;
import com.colsanitas.loginadmin.administracion.entity.PasswordData;
import com.colsanitas.loginadmin.administracion.entity.RelacionEntity;
import com.colsanitas.loginadmin.administracion.entity.UserEntity;
import com.colsanitas.loginadmin.administracion.enums.ETipoDocumento;
import com.colsanitas.loginadmin.administracion.enums.ETipoRelacion;
import com.colsanitas.loginadmin.administracion.utils.EResponse;
import com.colsanitas.loginadmin.administracion.utils.MailSenderUtil;
import com.colsanitas.loginadmin.administracion.utils.PasswordGenerator;
import com.colsanitas.loginadmin.administracion.view.TipoDocumentoView;
import com.colsanitas.loginadmin.administracion.view.UserResponseView;
import com.colsanitas.loginadmin.exception.LoginAdminException;
import com.osi.gaudi.cfg.Configurator;
import com.osi.gaudi.exception.Failure;
import com.osi.gaudi.security.authorization.clientews.IAuthorization;
import com.osi.gaudi.security.authorization.clientews.Role;
import com.osi.gaudi.security.authorization.clientews.RoleArray;
import com.osi.gaudi.security.authorization.clientews.ServiceAuthorization;


@Stateless
@Name("gestionUsuariosEjb")
@Local(IGestionUsuariosEjb.class)
public class GestionUsuariosEjb implements IGestionUsuariosEjb {

	 @In(create = true)
	 private IUsuarioDao usuarioDao;
	 
	 @In(create = true)
	 private IRelacionDao relacionDao;
	 
	 @In(create = true)
	 private IUsuarioDao ldapUsuarioDao;
	 
	 private static Logger logger = LoggerFactory.getLogger(GestionUsuariosEjb.class);

	 /**
	  * Variables usadas para consumir el servicio de authorization
	  * Softmanagement S.A. 
	  * Yency Serrano .
	  * 20/03/2013
	  */
	private static final String targetNameSpace="http://ws.authorization.security.gaudi.osi.com/";
	private static final String serviceName="serviceAuthorization";
	private static final QName qname = new QName(targetNameSpace, serviceName);
	private URL endpoint_authorization = null;
	private IAuthorization authorization;
	
	private final String wsdlAuthorization = Configurator.getInstance().getString("sistema", "wsdlAuthorization","http://desarrollo.colsanitas.com:8080/Authorization/Authorization?wsdl");
    /**FIN */
	 
	public UserResponseView actualizarUsuario(UserEntity userEntity) {
		UserResponseView userResponseView = new UserResponseView();
		UserEntity userByValidacion = null;
		try {
			
			// No puede haber un usuario con el mismo email
			userByValidacion = new UserEntity();
			userByValidacion.setUserMail(userEntity.getUserMail());
			userByValidacion.setValUserInfo(true);
			userByValidacion.setUserId( userEntity.getUserId() );
			List<UserEntity> resultado = usuarioDao.buscarUsuariosCriterios(userByValidacion);
			

			if ( resultado == null || resultado.isEmpty() ) {
				
				//Validacion usuarios para TIPO y # DOCUMENTO
				userByValidacion = new UserEntity();
				userByValidacion.setValUserInfo(true);
				userByValidacion.setUserId( userEntity.getUserId() );
				userByValidacion.setTipoDoc( userEntity.getTipoDoc() );
				userByValidacion.setDocument( userEntity.getDocument() );
				resultado = usuarioDao.buscarUsuariosCriterios(userByValidacion);
				
				if ( resultado == null || resultado.isEmpty() ) {
					ldapUsuarioDao.actualizarUsuarioEntity(userEntity);
					if(usuarioDao.findById(userEntity.getUserId()) != null){
						if( userEntity != null && ( userEntity.getUserLastName() == null || userEntity.getUserLastName().isEmpty() ) ){
							userEntity.setUserLastName("");
						}
						usuarioDao.actualizarUsuarioEntity(userEntity);
						userResponseView.setMessage(EResponse.USER_MODIFY.getMessage());
						userResponseView.setResponseCode(EResponse.USER_MODIFY.getCode());

						MailSenderUtil mailSender = new MailSenderUtil();
						if( !mailSender.sendEmailMofifyUser(userEntity) ) {
							userResponseView.setMessage(userResponseView.getMessage() + " " + EResponse.EMAIL_NO_SEND.getMessage());
						}
					}
				} else {
					userResponseView.setMessage(EResponse.USER_EXISTS_TIPO_DOC.getMessage());
					userResponseView.setResponseCode(EResponse.USER_EXISTS_TIPO_DOC.getCode());
				}
			} else {
				userResponseView.setMessage(EResponse.EMAIL_DUPLICATE.getMessage());
				userResponseView.setResponseCode(EResponse.EMAIL_DUPLICATE.getCode());
			}
		} catch (Exception e) {
			userResponseView.setResponseCode(EResponse.TRANSACTION_ERROR.getCode());
			userResponseView.setMessage(EResponse.TRANSACTION_ERROR.getMessage());
			logger.error("Error: GestionUsuariosEjb.actualizarUsuario  "+e.getMessage(), e);
		}
		return userResponseView;
	}

	
	public boolean isActivo(String login) {
		return false;
	}

	
	/**
	 * Crea el usuario en LDAP como en base de datos.
	 */
	public UserResponseView crearUsuario(UserEntity nuevoUsuario, String loginCreador) {
		UserResponseView userResponseView = new UserResponseView();
		UserEntity userByValidacion = null;
		try {
			// No puede haber un usuario con el mismo email
			userByValidacion = new UserEntity();
			userByValidacion.setUserMail(nuevoUsuario.getUserMail());
			List<UserEntity> resultado = usuarioDao.buscarUsuariosCriterios(userByValidacion);
			
			if ( resultado == null || resultado.isEmpty() ) {
				
				//Validacion usuarios para TIPO y # DOCUMENTO
				userByValidacion = new UserEntity();
				userByValidacion.setTipoDoc( nuevoUsuario.getTipoDoc() );
				userByValidacion.setDocument( nuevoUsuario.getDocument() );
				resultado = usuarioDao.buscarUsuariosCriterios(userByValidacion);
				
				if ( resultado == null || resultado.isEmpty() ) {
				
					String passwordInicial = PasswordGenerator.getPassword(8);
					nuevoUsuario.setUserPassword(passwordInicial);
					
					//Todos los usuarios creados se deben colocar en minusculas.
					String loginString = (nuevoUsuario.getUserLogin() + Configurator.getInstance().getString("administracion", "prefijo", ".Pres"));
					nuevoUsuario.setUserLogin(loginString.toLowerCase());
					nuevoUsuario.setUserId(nuevoUsuario.getUserLogin());
					nuevoUsuario.setBlockedUser(false);
	
					
					// ESTO NO DEBRA MANEJARSE DESDE LA APLICACION SINO APLICANDO LAS POLITICAS DE LDAP.
					PasswordData pwdData = new PasswordData();
					Long expiration = Configurator.getInstance().getInt("administracion", "pwd_expiration_days", 90) * 86400000L;
					pwdData.setFechaExpiracion(new Date(System.currentTimeMillis() + expiration));
					nuevoUsuario.setPwdData(pwdData);
					//-----------------------------------------------------------------------------------
	
					try {
						ldapUsuarioDao.registrarUsuarioEntity(nuevoUsuario, false);
						userResponseView.setResponseCode(EResponse.USER_REGISTERED.getCode());
						userResponseView.setMessage(EResponse.USER_REGISTERED.getMessage());
						
						nuevoUsuario.setRelaciones(generarRelacionesPrestadoresAdministrados(nuevoUsuario, loginCreador));
						usuarioDao.registrarUsuarioEntity(nuevoUsuario, false); 
						userResponseView.setMessage(EResponse.USER_REGISTERED.getMessage());
						userResponseView.setResponseCode(EResponse.USER_REGISTERED.getCode());
					} catch (Failure f) {
						userResponseView.setMessage(f.getMessage());
					}	
					
					if (userResponseView.getResponseCode() == EResponse.USER_REGISTERED.getCode()){
						MailSenderUtil mailSender = new MailSenderUtil();
						if(!mailSender.sendEmailNewUser(nuevoUsuario)) {
							userResponseView.setMessage(userResponseView.getMessage() + " " + EResponse.EMAIL_NO_SEND.getMessage());
						}
					}
				} else {
					userResponseView.setMessage(EResponse.USER_EXISTS_TIPO_DOC.getMessage());
					userResponseView.setResponseCode(EResponse.USER_EXISTS_TIPO_DOC.getCode());
				}
			
			} else {
				userResponseView.setMessage(EResponse.EMAIL_DUPLICATE.getMessage());
				userResponseView.setResponseCode(EResponse.EMAIL_DUPLICATE.getCode());
			}
		} catch (Exception e) {
			userResponseView.setMessage(EResponse.TRANSACTION_ERROR.getMessage());
			userResponseView.setResponseCode(EResponse.TRANSACTION_ERROR.getCode());
			logger.error("Error: GestionUsuariosEjb.crearUsuarioLDAP  "+e.getMessage(), e);
		}
		return userResponseView;
	}

	/**
	 * Genera todas las relaciones de tipo "Creador Por" para las cuales un login es administrador del prestador.
	 * @param usuario nuevo usuario a crear.
	 * @param login usuario que est� creado al nuevo.
	 * @return relaciones de tipo creado por.
	 * @throws LoginAdminException
	 */

	private Set<RelacionEntity> generarRelacionesPrestadoresAdministrados(UserEntity usuario, String login) throws LoginAdminException{
		List<RelacionEntity> relacionesDeAdmon = relacionDao.consultarRelacionesDelUsuario(login, ETipoRelacion.ADMINISTRADOR.getId());
		HashSet<RelacionEntity> relaciones = new HashSet<RelacionEntity>();
		
		for (RelacionEntity rel : relacionesDeAdmon){
			RelacionEntity relacionCreadoPor = new RelacionEntity();
			relacionCreadoPor.setPrestador(rel.getPrestador());
			relacionCreadoPor.setTipoRelacion(ETipoRelacion.CREADO_POR.getId());
			relacionCreadoPor.setEstado(true);
			relacionCreadoPor.setUsuario(usuario);
			relaciones.add(relacionCreadoPor);
		}
		return relaciones;
	}

	public List<TipoDocumentoView> listTipoDocs(){
		List<TipoDocumentoView> listTipoDocs = null;
		TipoDocumentoView tipoDocumentoView = null;
		try {
			listTipoDocs = new ArrayList<TipoDocumentoView>();
			tipoDocumentoView = new TipoDocumentoView();
			tipoDocumentoView.setId( new Long(ETipoDocumento.CC.getCode()) );
			tipoDocumentoView.setName( ETipoDocumento.CC.getValor() );
			listTipoDocs.add( tipoDocumentoView );
			
			tipoDocumentoView = new TipoDocumentoView();
			tipoDocumentoView.setId( new Long(ETipoDocumento.CE.getCode()) );
			tipoDocumentoView.setName( ETipoDocumento.CE.getValor() );
			listTipoDocs.add( tipoDocumentoView );
			
			tipoDocumentoView = new TipoDocumentoView();
			tipoDocumentoView.setId( new Long(ETipoDocumento.NIT.getCode()) );
			tipoDocumentoView.setName( ETipoDocumento.NIT.getValor() );
			listTipoDocs.add( tipoDocumentoView );
			
			tipoDocumentoView = new TipoDocumentoView();
			tipoDocumentoView.setId( new Long(ETipoDocumento.PASAPORTE.getCode()) );
			tipoDocumentoView.setName( ETipoDocumento.PASAPORTE.getValor() );
			listTipoDocs.add( tipoDocumentoView );
			
		} catch (Exception e) {
			logger.error("Error: GestionUsuariosEjb.listTipoDocs  "+e.getMessage(), e);		
		}
		return listTipoDocs;
	}
	

	/* Metodos consultas de usuario en GUIAuthorization */
	public UserEntity consultUser( String usersLogin ) throws LoginAdminException {
		try {
			return this.usuarioDao.consultUser(usersLogin);
		} catch (Exception e) {
			logger.error("Error: GestionUsuariosEjb.consultUser "+e.getMessage(), e);
			throw new LoginAdminException("Error en GestionUsuariosEjb.consultUser", e);
		}
		
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<UserEntity> listUser(String userName) throws LoginAdminException {
		try {
			return this.usuarioDao.listUser(userName);
		} catch (Exception e) {
			logger.error("Error: GestionUsuariosEjb.listUser "+e.getMessage(), e);
			throw new LoginAdminException("Error en GestionUsuariosEjb.listUser", e);
		}
		
	}
	

	
	/**
	 * Metodo encargado de establecer la conexion con el Web Service Authorization
	 * Softmanagement S.A. 
	  * Yency Serrano .
	  * 20/03/2013
	 */
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


	 /**
	  * Metodo encargado de consumir el servicio para capturar el rol q tiene el usuario
	  * Softmanagement S.A. 
	  * Yency Serrano .
	  * 20/03/2013
	  */
	
	public boolean tieneRolFuncionarioOsi(String userName,String rolAdminLoginAdmin) {
		// TODO Auto-generated method stub
        RoleArray rolesUsuario = null;
		
		if ( authorization == null ) {
			establecerConexionWSAuthorization();
		}
		
		rolesUsuario = authorization.getRolesByUserName( userName );
		

		
		for (Role rol: rolesUsuario.getItem()){
			if(rolAdminLoginAdmin.equalsIgnoreCase(rol.getName())){
			   return true;	
			}
		}
		
		
		return false;
	}
	
	
	
}

	

	
package com.colsanitas.loginadmin.administracion.business;

import static org.jboss.seam.ScopeType.STATELESS;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.loginadmin.administracion.dao.interfaces.IPrestadorDao;
import com.colsanitas.loginadmin.administracion.dao.interfaces.IRelacionDao;
import com.colsanitas.loginadmin.administracion.dao.interfaces.IUserRolDao;
import com.colsanitas.loginadmin.administracion.dao.interfaces.IUsuarioDao;
import com.colsanitas.loginadmin.administracion.entity.PrestadorEntity;
import com.colsanitas.loginadmin.administracion.entity.RelacionEntity;
import com.colsanitas.loginadmin.administracion.entity.UserEntity;
import com.colsanitas.loginadmin.administracion.entity.UserRoleEntity;
import com.colsanitas.loginadmin.administracion.enums.ETipoRelacion;
import com.colsanitas.loginadmin.administracion.utils.EResponse;
import com.colsanitas.loginadmin.administracion.utils.EtipoRolLoginAdmin;
import com.colsanitas.loginadmin.administracion.utils.MailSenderUtil;
import com.colsanitas.loginadmin.administracion.view.UserResponseView;
import com.colsanitas.loginadmin.dto.DatosPrestadorDTO;
import com.colsanitas.loginadmin.dto.SucursalesByPrestadorDTO;
import com.colsanitas.loginadmin.exception.LoginAdminException;
import com.colsanitas.loginadmin.exception.NoDataFoundException;
import com.osi.gaudi.cfg.Configurator;
import com.osi.gaudi.exception.Failure;

@Scope(STATELESS)
@Stateless
@Name("administrarAutorizacionEjb")
@Local(IAdministrarAutorizaciondEjb.class)
public class AdministrarAutorizacionEjb implements IAdministrarAutorizaciondEjb, Serializable {
	
	private static final long serialVersionUID = 5091958132447056606L;

	private static Logger logger = LoggerFactory.getLogger(AdministrarAutorizacionEjb.class);

	@In(create = true)
	private IRelacionDao relacionDao;
	@In(create = true)
	private IPrestadorDao prestadorDao;
	@In(create = true)
	private IUsuarioDao usuarioDao;
	@In(create = true)
	private IUserRolDao userRolDao;
	@In(create = true)
	private IUsuarioDao ldapUsuarioDao;
	
		
	public UserResponseView registrarRelacion( UserEntity userEntity, PrestadorEntity prestadorEntity, int tipoRelacion, boolean estado, String loginSession ) throws LoginAdminException {
		RelacionEntity relacionEntity;
		RelacionEntity relacionAux;
		UserResponseView userResponseDTO= null;
		UserRoleEntity userRoleEntity = null;
		List<String> idRoles = null;
		PrestadorEntity prestadorAux = null;
		boolean userAutorizado = false;
		List<RelacionEntity> listRelacioUser = null;
		try {
			
			if( tipoRelacion == ETipoRelacion.ADMINISTRADOR.getId() ){
				idRoles = Configurator.getInstance().getArray("administracion", "idRolAdminPrest", null);
			}
			
			userResponseDTO = new UserResponseView();
			prestadorAux = prestadorDao.consultarPrestador( prestadorEntity );
			if( prestadorAux != null ){
				prestadorAux.setEstado( true );
				prestadorEntity = prestadorDao.actualizarPrestador( prestadorAux );
				prestadorAux = null;
			} else {
				prestadorDao.registrarPrestador( prestadorEntity, false );
			}
			
			if( usuarioDao.findById( userEntity.getUserId() ) == null ){
				usuarioDao.registrarUsuarioEntity(userEntity, false);
			} 
			relacionEntity = new RelacionEntity();
			relacionEntity.setPrestador(prestadorEntity);
			relacionEntity.setUsuario(userEntity);
			relacionEntity.setTipoRelacion(tipoRelacion);
			relacionEntity.setEstado(estado);
			
			String userLogin = userEntity.getUserId();
			userLogin = (userLogin != null ?  userLogin.toLowerCase() : userLogin); // Para guardar compatiblidad con pruebas.
			relacionAux = relacionDao.consultarRelacionUser(userLogin, prestadorEntity.getPrestadorId(), tipoRelacion);
			if( relacionAux == null ) {
				relacionDao.registrarRelacion( relacionEntity, true );
			} else if( relacionAux.getEstado() != estado ) {
				relacionAux.setEstado( estado );
				relacionDao.actualizarRelacion(relacionAux, true);
			} else if( relacionEntity.getEstado() == estado && estado ) {
				userResponseDTO.setResponseCode(EResponse.USER_AUTORIZADO.getCode());
				userResponseDTO.setMessage(EResponse.USER_AUTORIZADO.getMessage());
				userAutorizado = true;
			}
			
			
			if( userEntity != null ){
				if( userEntity != null && idRoles != null ){
					UserRoleEntity existUserRoleEntity = null;
					for(String idRol: idRoles) {
						userRoleEntity = new UserRoleEntity();
						userRoleEntity.setRoleId( new Long (idRol) );
						userRoleEntity.setUserId( userEntity.getUserId() );
						userRoleEntity.setPrestadorId(prestadorEntity.getPrestadorId());
						existUserRoleEntity = userRolDao.consultarRelUserRol(userRoleEntity);
						if( existUserRoleEntity == null ) {
							if( tipoRelacion == ETipoRelacion.ADMINISTRADOR.getId() ) {
								userRoleEntity.setTipoRolLA(  EtipoRolLoginAdmin.ADMINISTRADOR.getCode() );
								userRolDao.registrarUserRolEntity(userRoleEntity, false);
							} else if( tipoRelacion == ETipoRelacion.DELEGADO.getId() ) {
								userRoleEntity.setTipoRolLA(  EtipoRolLoginAdmin.DELEGADO.getCode() );
								userRolDao.registrarUserRolEntity(userRoleEntity, false);
							}
						} else if( tipoRelacion == ETipoRelacion.ADMINISTRADOR.getId() && existUserRoleEntity.getTipoRolLA() != null && existUserRoleEntity.getTipoRolLA().equals( EtipoRolLoginAdmin.DELEGADO.getCode() )) {
							existUserRoleEntity.setTipoRolLA(  EtipoRolLoginAdmin.ADMINDELE.getCode() );
							userRolDao.actualizarUsuarioEntity(existUserRoleEntity);
						} else if( tipoRelacion == ETipoRelacion.DELEGADO.getId() && existUserRoleEntity.getTipoRolLA() != null && existUserRoleEntity.getTipoRolLA().equals( EtipoRolLoginAdmin.ADMINISTRADOR.getCode() )) {
							existUserRoleEntity.setTipoRolLA(  EtipoRolLoginAdmin.ADMINDELE.getCode() );
							userRolDao.actualizarUsuarioEntity(existUserRoleEntity);
						} else if( tipoRelacion == ETipoRelacion.ADMINISTRADOR.getId() ) {
							existUserRoleEntity.setTipoRolLA(  EtipoRolLoginAdmin.ADMINISTRADOR.getCode() );
							userRolDao.actualizarUsuarioEntity(existUserRoleEntity);
						} else if( tipoRelacion == ETipoRelacion.DELEGADO.getId() ) {
							existUserRoleEntity.setTipoRolLA(  EtipoRolLoginAdmin.DELEGADO.getCode() );
							userRolDao.actualizarUsuarioEntity(existUserRoleEntity);
						}
					}
				}
			}
			
			if( userEntity != null && userEntity.getUserId() != null && loginSession != null ){
				listRelacioUser = relacionDao.consultarRelacionUserDesau(userEntity.getUserId(), loginSession);
				userResponseDTO.setListRelacioUser(listRelacioUser);
			}
			
			//Se modifica el acceso de autenticacion
			modificarAccessoDelUsuario(relacionEntity.getUsuario().getUserId(), estado);

			if( !userAutorizado ){


				userResponseDTO.setMessage(EResponse.TRANSACTION_OK.getMessage());
				userResponseDTO.setResponseCode(EResponse.TRANSACTION_OK.getCode());
				
				MailSenderUtil mailSender = new MailSenderUtil();
				mailSender.sendEmailChangeRelationship(relacionEntity);
			}
		} catch (Failure e) {
			if( estado ){
				userResponseDTO.setMessage(EResponse.AUTORIZACION_CORRECTAMENTE.getMessage()+ " "+ EResponse.EMAIL_NO_SEND.getMessage());
			} else {
				userResponseDTO.setMessage(EResponse.DESAUTORIZACION_CORRECTAMENTE.getMessage()+ " "+ EResponse.EMAIL_NO_SEND.getMessage());
			}
			userResponseDTO.setResponseCode(EResponse.EMAIL_NO_SEND.getCode());
			logger.error("Error: AdministrarAutorizacionEjb.registrarRelacion  "+e.getMessage(), e);
		} catch (LoginAdminException e) {
			userResponseDTO.setMessage(EResponse.TRANSACTION_ERROR.getMessage());
			userResponseDTO.setResponseCode(EResponse.TRANSACTION_ERROR.getCode());
			logger.error("Error: AdministrarAutorizacionEjb.registrarRelacion  "+e.getMessage(), e);
			throw new LoginAdminException("Error en AdministrarAutorizacionEjb.registrarRelacion", e);
		} catch (Exception e) {
			userResponseDTO.setMessage(EResponse.TRANSACTION_ERROR.getMessage());
			userResponseDTO.setResponseCode(EResponse.TRANSACTION_ERROR.getCode());
			logger.error("Error: AdministrarAutorizacionEjb.registrarRelacion  "+e.getMessage(), e);
			throw new LoginAdminException("Error en AdministrarAutorizacionEjb.registrarRelacion", e);
		}
	    return userResponseDTO;
	}
	
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PrestadorEntity consultPrest( String codigoSucursal ) throws LoginAdminException {
		PrestadorEntity prestador = null;
		try {
			prestador = prestadorDao.consultPrest(codigoSucursal);
		}  catch (Exception e) {
			logger.error("Error: AdministrarAutorizacionEjb.consultPrest  "+e.getMessage(), e);
			throw new LoginAdminException("Error en AdministrarAutorizacionEjb.consultPrest", e);
		}
		return prestador;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PrestadorEntity> listPrestadores( String razonSocial ) throws LoginAdminException {
		List<PrestadorEntity> ListPrest = null;
		try {
			ListPrest = prestadorDao.listPrestadores(razonSocial);
		}  catch (Exception e) {
			logger.error("Error: AdministrarAutorizacionEjb.listPrestadores  "+e.getMessage(), e);
			throw new LoginAdminException("Error en AdministrarAutorizacionEjb.listPrestadores", e);
		}
		return ListPrest;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<UserEntity> consultarUsuariosPrest(List<String> usersLogin, Long idPrestador) throws LoginAdminException {
		List<UserEntity> listUser = null;
		try {
			for (String userLogin : usersLogin){
				userLogin = (userLogin != null ?  userLogin.toLowerCase() : userLogin); // Para guardar compatiblidad con pruebas.
			}

			listUser = this.usuarioDao.consultarUsuariosPrest(usersLogin, idPrestador);
		} catch (Exception e) {
			logger.error("Error: AdministrarAutorizacionEjb.consultarUsuariosPrest  "+e.getMessage(), e);
			throw new LoginAdminException("Error en AdministrarAutorizacionEjb.consultarUsuariosPrest ", e);
		}
		return listUser;
	}
	
	public List<RelacionEntity> consultarRelacionUserDesau(String userLogin, String prestLogin) throws LoginAdminException {
		try {
			userLogin = (userLogin != null ?  userLogin.toLowerCase() : userLogin); // Para guardar compatiblidad con pruebas.
			prestLogin = (prestLogin != null ?  prestLogin.toLowerCase() : prestLogin); // Para guardar compatiblidad con pruebas.
			return relacionDao.consultarRelacionUserDesau(userLogin, prestLogin);
		} catch (Exception e) {
			logger.error("Error: AdministrarAutorizacionEjb.consultarRelacionUserDesau  "+e.getMessage(), e);
			throw new LoginAdminException("Error en AdministrarAutorizacionEjb.consultarRelacionUserDesau", e);
		}
	}
	
	public UserResponseView actualizarRelacion( RelacionEntity relacionEntity, int tipoRelacion,boolean estado ) throws LoginAdminException {
		UserResponseView userResponseDTO = new UserResponseView();
		try {
			relacionEntity.setEstado(estado);
			relacionEntity.setTipoRelacion(tipoRelacion);
			relacionDao.actualizarRelacion(relacionEntity, false);
			
			if( relacionEntity != null && relacionEntity.getPrestador() != null && relacionEntity.getUsuario() != null ){
				if( tipoRelacion == ETipoRelacion.ADMINISTRADOR.getId() ) {
					userRolDao.eliminarUserRolPrestador( relacionEntity.getPrestador().getPrestadorId(), relacionEntity.getUsuario().getUserId(), EtipoRolLoginAdmin.ADMINISTRADOR.getCode() );
					userRolDao.actualizarUserRolPrestador( relacionEntity.getPrestador().getPrestadorId(), relacionEntity.getUsuario().getUserId(), EtipoRolLoginAdmin.DELEGADO.getCode() );
				} else if( tipoRelacion == ETipoRelacion.DELEGADO.getId() ){
					userRolDao.eliminarUserRolPrestador( relacionEntity.getPrestador().getPrestadorId(), relacionEntity.getUsuario().getUserId(), EtipoRolLoginAdmin.DELEGADO.getCode() );
					userRolDao.actualizarUserRolPrestador( relacionEntity.getPrestador().getPrestadorId(), relacionEntity.getUsuario().getUserId(), EtipoRolLoginAdmin.ADMINISTRADOR.getCode() );
				}
			}
			
			modificarAccessoDelUsuario(relacionEntity.getUsuario().getUserId(), estado);



			userResponseDTO.setMessage(EResponse.TRANSACTION_OK.getMessage());
			userResponseDTO.setResponseCode(EResponse.TRANSACTION_OK.getCode());
			
			MailSenderUtil mailSender = new MailSenderUtil();
			mailSender.sendEmailChangeRelationship(relacionEntity);
		} catch (Failure e) {
			if( estado ){
				userResponseDTO.setMessage(EResponse.AUTORIZACION_CORRECTAMENTE.getMessage()+ " "+ EResponse.EMAIL_NO_SEND.getMessage());
			} else {
				userResponseDTO.setMessage(EResponse.DESAUTORIZACION_CORRECTAMENTE.getMessage()+ " "+ EResponse.EMAIL_NO_SEND.getMessage());
			}
			userResponseDTO.setResponseCode(EResponse.EMAIL_NO_SEND.getCode());
			logger.error("Error: AdministrarAutorizacionEjb.actualizarUsuario  "+e.getMessage(), e);
		} catch (Exception e) {
			userResponseDTO.setMessage(EResponse.TRANSACTION_ERROR.getMessage());
			userResponseDTO.setResponseCode(EResponse.TRANSACTION_ERROR.getCode());
			logger.error("Error: AdministrarAutorizacionEjb.actualizarUsuario  "+e.getMessage(), e);
			throw new LoginAdminException("Error en AdministrarAutorizacionEjb.actualizarUsuario", e);
		}
	    return userResponseDTO;
	}
	
	public List<RelacionEntity> consultarRelacionUsuario(String userLogin) throws LoginAdminException {
		try {
			return relacionDao.consultarRelacionUsuario(userLogin);
		} catch (Exception e) {
			logger.error("Error: AdministrarAutorizacionEjb.consultarRelacionUsuario "+e.getMessage(), e);
			throw new LoginAdminException("Error en AdministrarAutorizacionEjb.actualizarUsuario", e);
		}
	}
	
	public List<SucursalesByPrestadorDTO> consultaSucursalesPrestador( SucursalesByPrestadorDTO sucursalesByPrestadorDTO ) throws LoginAdminException, NoDataFoundException {
		List<SucursalesByPrestadorDTO> listSucursales = null;
        try {
			listSucursales= prestadorDao.consultaSucursalesPrestadorByID(sucursalesByPrestadorDTO);
			
		} catch (LoginAdminException e) {
			logger.error("Error al ejecutar: AdministrarAutorizacionEjb.consultaSucursalesPrestador"+e.getMessage(), e);
			throw e;
		}
	 	catch (NoDataFoundException e) {
			e.printStackTrace(); 
			logger.error("Error al ejecutar: AdministrarAutorizacionEjb.consultaSucursalesPrestador"+e.getMessage(), e);
			 throw e;
			 		
		}catch (Exception e) {
			e.printStackTrace();
			 logger.error("Error al ejecutar: AdministrarAutorizacionEjb.consultaSucursalesPrestador",e.getMessage(), e);
			 throw new LoginAdminException(e.getMessage());
		}
        
			return listSucursales;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<UserEntity> buscarUsuariosCriterios( UserEntity datosBusq ) throws LoginAdminException {
		return usuarioDao.buscarUsuariosCriterios(datosBusq);
	}
	
	
	public DatosPrestadorDTO consultarPrestadorBioSanitas( DatosPrestadorDTO datosPrestadorDTO ) throws LoginAdminException {
		DatosPrestadorDTO resDatosPrestadorDTO = null;
        try {
			resDatosPrestadorDTO= prestadorDao.consultaDatosPrestadorByID(datosPrestadorDTO);
		} catch (LoginAdminException e) {
			e.printStackTrace();
			logger.error("Error al ejecutar: AdministrarAutorizacionEjb.consultarPrestadorBioSanitas",e.getMessage(), e);
			throw e;
		}
        catch (Exception e) {
         e.printStackTrace();
         logger.error("Error al ejecutar: AdministrarAutorizacionEjb.consultarPrestadorBioSanitas",e.getMessage(), e);
  	     throw new LoginAdminException(e.getMessage());
  		}
      	return resDatosPrestadorDTO;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PrestadorEntity> consultarPrestadoresAdministrados(String userLogin) throws LoginAdminException {
		try {
			userLogin = (userLogin != null ?  userLogin.toLowerCase() : userLogin); // Para guardar compatiblidad con pruebas.
			return relacionDao.consultarPrestadoresAdministrados(userLogin);
		} catch (Exception e) {
			logger.error("Error: AdministrarAutorizacionEjb.consultarPrestadoresUser "+e.getMessage(), e);
			throw new LoginAdminException("Error en AdministrarAutorizacionEjb.consultarPrestadoresUser", e);
		}
	}
	
	/**
	 * Modifica el accesos a los sistema para un usuario que ha tenido un cambio en sus relaciones.
	 * @param userId id del usuario que ha tenido un cambio.
	 * @param isAutorizacion tipo de cambio que tuvo el usuario.
	 */
	private void modificarAccessoDelUsuario(String userId, boolean isAutorizacion){
		// Se encarga de bloquear el usuario en caso que se haya quedado sin relaciones o reactivarlo en caso contrario.
		List<String> usuario = Arrays.asList(userId);
		
		if (isAutorizacion){ // Es una autorzacion.
			if (ldapUsuarioDao.isLocked(usuario.get(0))){ // Si esta bloqueado lo desbloquea.
				ldapUsuarioDao.reactivarUsuarioByIds(usuario);
			}
		} else { //Es una desautorizacion.
			List<String> idSinrelacion = relacionDao.consultarUsuariosSinRelacion(usuario);
			ldapUsuarioDao.desactivarUsuarioByIds(new HashSet<String>(idSinrelacion));
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PrestadorEntity consultPrestActivo( PrestadorEntity entityPrest ) throws LoginAdminException {
		PrestadorEntity prestador = null;
		try {
			prestador = prestadorDao.consultPrestActivo(entityPrest);
		}  catch (Exception e) {
			logger.error("Error: AdministrarAutorizacionEjb.consultPrest  "+e.getMessage(), e);
			throw new LoginAdminException("Error en AdministrarAutorizacionEjb.consultPrest", e);
		}
		return prestador;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PrestadorEntity> listPrestadoresActivos( PrestadorEntity entityPrest ) throws LoginAdminException {
		List<PrestadorEntity> ListPrest = null;
		try {
			ListPrest = prestadorDao.listPrestadoresActivos(entityPrest);
		}  catch (Exception e) {
			logger.error("Error: AdministrarAutorizacionEjb.listPrestadores  "+e.getMessage(), e);
			throw new LoginAdminException("Error en AdministrarAutorizacionEjb.listPrestadoresActivos ", e);
		}
		return ListPrest;
	}

   /**
    * Metodo encargado de identificar si un prestador ya tiene asociado al menos un administrador de prestadores
    * Modificado por Softmangement
    * Yency Serrano
    * 21/03/2013
    * 
    */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public boolean consultaPrestadorTieneAdminPrestador(String nit) throws LoginAdminException {
		List<RelacionEntity> lista=null;
		boolean tieneAdminPrestador = false;
		try {
			lista = relacionDao.consultaPrestadorTieneAdminPrestador(nit);
			
			if(lista.size()>0)
			{
				tieneAdminPrestador = true;
			}
		} catch (Exception e) {
			logger.error("Error: AdministrarAutorizacionEjb.consultarRelacionUsuario "+e.getMessage(), e);
			throw new LoginAdminException("Error en AdministrarAutorizacionEjb.actualizarUsuario", e);
		}
		return tieneAdminPrestador;
	}
	
}

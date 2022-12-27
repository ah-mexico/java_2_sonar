package com.colsanitas.loginadmin.administracion.business;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
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
import com.colsanitas.loginadmin.administracion.utils.MailSenderUtil;
import com.colsanitas.loginadmin.administracion.view.UserResponseView;
import com.colsanitas.loginadmin.dto.DatosPrestadorDTO;
import com.colsanitas.loginadmin.dto.SucursalesByPrestadorDTO;
import com.colsanitas.loginadmin.exception.LoginAdminException;
import com.colsanitas.loginadmin.exception.NoDataFoundException;
import com.osi.gaudi.cfg.Configurator;
import com.osi.gaudi.exception.Failure;

@Stateless
@Name("gestionAdministradorEjb")
@Local(IGestionAdmnistradorEjb.class)
public class GestionAdministradorEjb implements IGestionAdmnistradorEjb, Serializable {

	private static Logger logger = LoggerFactory.getLogger(GestionAdministradorEjb.class);
	
	private static final long serialVersionUID = 1L;

	@In(create = true)
	private IRelacionDao relacionDao;
	@In(create = true)
	private IPrestadorDao prestadorDao;
	@In(create = true)
	private IUsuarioDao usuarioDao;
	
	@In(create = true)
	IUserRolDao userRolDao;
	
	 
	public UserResponseView registrarRelacion(UserEntity userEntity, PrestadorEntity prestadorEntity, int tipoRelacion, boolean estado)  {

		RelacionEntity relacionEntity;
		UserResponseView userResponseDTO= new UserResponseView();
		UserRoleEntity userRoleEntity = null;
		
		List<String> idRoles = null;
		try {
			relacionEntity = registrarUsuarioPrestador(userEntity, prestadorEntity, tipoRelacion, estado);
			relacionDao.registrarRelacion(relacionEntity, false); 
			
			if( userEntity != null ){
				
				
				if( tipoRelacion == ETipoRelacion.ADMINISTRADOR.getId() ){
					idRoles = Configurator.getInstance().getArray("administracion", "idRolAdminPrest", null);
				} else if (tipoRelacion == ETipoRelacion.DELEGADO.getId() ){
					idRoles = Configurator.getInstance().getArray("administracion", "idRolAdminDelegado", null);
				}
				
				if( userEntity != null && idRoles != null ){
					for(String idRol: idRoles) {
						userRoleEntity = new UserRoleEntity();
						userRoleEntity.setRoleId( new Long (idRol) );
						userRoleEntity.setUserId( userEntity.getUserId() );
						if( userRolDao.consultarRelUserRol(userRoleEntity) == null  ){
							userRolDao.registrarUserRolEntity(userRoleEntity, false);
						}
					}
				}
			}
			
			try {


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
				logger.error("Error: GestionUsuariosEjb.actualizarUsuario  "+e.getMessage(), e);
			} catch (Exception e) {
				userResponseDTO.setMessage(EResponse.TRANSACTION_ERROR.getMessage());
				userResponseDTO.setResponseCode(EResponse.TRANSACTION_ERROR.getCode());
				logger.error("Error: GestionUsuariosEjb.actualizarUsuario  "+e.getMessage(), e);
			}
			
			
		} catch (Exception e) {
			userResponseDTO.setMessage(EResponse.TRANSACTION_ERROR.getMessage());
			userResponseDTO.setResponseCode(EResponse.TRANSACTION_ERROR.getCode());
			e.printStackTrace();
		}
	    
	    return userResponseDTO;
	    
	}
	
	public UserResponseView actualizarRelacion(RelacionEntity relacionEntity, int tipoRelacion,boolean estado) {
	
		UserResponseView userResponseDTO = new UserResponseView();

		try {
			relacionEntity.setEstado(estado);
			relacionEntity.setTipoRelacion(tipoRelacion);
			relacionDao.actualizarRelacionNewTrans(relacionEntity, true);
			try {


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
				logger.error("Error: GestionUsuariosEjb.actualizarUsuario  "+e.getMessage(), e);
			} catch (Exception e) {
				userResponseDTO.setMessage(EResponse.TRANSACTION_ERROR.getMessage());
				userResponseDTO.setResponseCode(EResponse.TRANSACTION_ERROR.getCode());
				logger.error("Error: GestionUsuariosEjb.actualizarUsuario  "+e.getMessage(), e);
			}
			userResponseDTO.setMessage(EResponse.TRANSACTION_OK.getMessage());
			userResponseDTO.setResponseCode(EResponse.TRANSACTION_OK.getCode());
		} catch (Exception e) {
			userResponseDTO.setMessage(EResponse.TRANSACTION_ERROR.getMessage());
			userResponseDTO.setResponseCode(EResponse.TRANSACTION_ERROR.getCode());
			e.printStackTrace();
		}
	    return userResponseDTO;
	}
	
	
	public RelacionEntity registrarUsuarioPrestador(UserEntity userEntity, PrestadorEntity prestadorEntity, int tipoRelacion, boolean estado) throws Exception {
		RelacionEntity relacionEntity = null;
		try {
			PrestadorEntity prestadorAuxEntity = prestadorDao.findByNumID(prestadorEntity.getNumId());
			if (prestadorAuxEntity == null)
				  prestadorDao.registrarPrestador(prestadorEntity, false);
			else
				prestadorEntity=prestadorAuxEntity;
			
			if (usuarioDao.findById(userEntity.getUserLogin()) == null){
		          usuarioDao.registrarUsuarioEntity(userEntity, true);// pruebas, usuario se registra cuandp se crea														
			}
			
			relacionEntity = new RelacionEntity();
			relacionEntity.setPrestador(prestadorEntity);
			relacionEntity.setUsuario(userEntity);
			relacionEntity.setTipoRelacion(tipoRelacion);
			relacionEntity.setEstado(estado);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return relacionEntity;
	}
	
	public List<RelacionEntity> consultarRelacionUsuario( String userLogin, int tipoRel) throws Exception {
		try {
			return relacionDao.consultarRelacionUsuario(userLogin, tipoRel);
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	public RelacionEntity consultarRelacionUser( String userLogin, Long cdPerson, int tipoRel ) throws Exception {
		PrestadorEntity prestadorEntity = null;
		RelacionEntity relacionEntity = null;
		try {
			prestadorEntity = prestadorDao.consultarPrestador(cdPerson);
			if(prestadorEntity != null && prestadorEntity.getPrestadorId() != null){
				relacionEntity = relacionDao.consultarRelacionUser(userLogin, prestadorEntity.getPrestadorId(), tipoRel);
			} else {
				relacionEntity = null;
			}
			
		} catch (Exception e) {
			throw e;
		}
		return relacionEntity;
	}
	
	public RelacionEntity consultarRelacionPres( String userLogin, Long idPerson, int tipoRel ) throws Exception {

		RelacionEntity relacionEntity = null;
		try {
			
				relacionEntity = relacionDao.consultarRelacionUser(userLogin, idPerson, tipoRel);
			
			
		} catch (Exception e) {
			throw e;
		}
		return relacionEntity;
	}

	public List<RelacionEntity> consultarRelacionUserDesau(String userLogin, String prestLogin) throws Exception {
		try {
			return relacionDao.consultarRelacionUserDesau(userLogin, prestLogin);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public DatosPrestadorDTO consultarPrestadorBioSanitas( DatosPrestadorDTO datosPrestadorDTO ) throws LoginAdminException{
	 	DatosPrestadorDTO resDatosPrestadorDTO = null;
        try {
			resDatosPrestadorDTO= prestadorDao.consultaDatosPrestadorByID(datosPrestadorDTO);
		} catch (LoginAdminException e) {
			e.printStackTrace();
			logger.error("Error al ejecutar: GestionAdministradorEjb.consultarPrestadorBioSanitas",e.getMessage(), e);
			throw e;
		}
        catch (Exception e) {
         e.printStackTrace();
         logger.error("Error al ejecutar: GestionAdministradorEjb.consultarPrestadorBioSanitas",e.getMessage(), e);
  	     throw new LoginAdminException(e.getMessage());
  		}
      	return resDatosPrestadorDTO;
	}
	
	public List<SucursalesByPrestadorDTO> consultaSucursalesPrestador( SucursalesByPrestadorDTO sucursalesByPrestadorDTO ) throws LoginAdminException, NoDataFoundException{
		
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
	
	public List<RelacionEntity> consultarRelacionUsuario(String userLogin) throws Exception {
		try {
			return relacionDao.consultarRelacionUsuario(userLogin);
		} catch (Exception e) {
			throw e;
		}
	}
}

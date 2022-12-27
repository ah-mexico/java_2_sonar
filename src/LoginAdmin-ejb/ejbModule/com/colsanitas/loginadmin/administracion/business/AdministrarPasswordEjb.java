package com.colsanitas.loginadmin.administracion.business;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.loginadmin.administracion.entity.UserEntity;
import com.colsanitas.loginadmin.administracion.ldap.dao.LDAPUsuarioDao;
import com.colsanitas.loginadmin.administracion.utils.EResponse;
import com.colsanitas.loginadmin.administracion.utils.MailSenderUtil;
import com.colsanitas.loginadmin.administracion.utils.PasswordGenerator;
import com.colsanitas.loginadmin.administracion.utils.Validator;
import com.colsanitas.loginadmin.administracion.view.UserResponseView;
import com.colsanitas.loginadmin.exception.LoginAdminException;
import com.osi.gaudi.cfg.Configurator;
import com.osi.gaudi.exception.Failure;

@Stateless
@Name("administrarPasswordEjb")
@Local(IAdmnistrarPasswordEjb.class)
public class AdministrarPasswordEjb implements IAdmnistrarPasswordEjb {
	
	private static Logger logger = LoggerFactory.getLogger(AdministrarPasswordEjb.class);
	private final boolean remenberNewPassWord = Configurator.getInstance().getBool("administracion", "remenberNewPassWord", false);

	@In(create = true)
	LDAPUsuarioDao ldapUsuarioDao;
	
	public UserResponseView actualizarPassword(String userLogin, String oldPassword, String newPassword) {
		userLogin = (userLogin != null ?  userLogin.toLowerCase() : userLogin);

		UserResponseView response =  new UserResponseView();
		response = Validator.validateChangePassword(userLogin, oldPassword, newPassword);

		if (response.getResponseCode() == EResponse.VALIDATION_OK.getCode()) {
			try {
				ldapUsuarioDao.actualizarPassword(userLogin, oldPassword, newPassword);
				response.setResponseCode(EResponse.PASSWD_CHANGED_OK.getCode());response.setMessage(EResponse.PASSWD_CHANGED_OK.getMessage());
			} catch (LoginAdminException e) {
				response.setMessage(e.getMessage());
			} catch (Failure e){
				logger.warn("No se pudo cambiar la clave del usuario.",e);
				response.setResponseCode(EResponse.USER_NOT_FOUND_GENERAL.getCode());response.setMessage(EResponse.USER_NOT_FOUND_GENERAL.getMessage());
			}
		}
		return response;
	}
	

	public UserResponseView recordarPassword(String userLogin) {
		userLogin = (userLogin != null ?  userLogin.toLowerCase() : userLogin);

		UserResponseView response = new UserResponseView();		
		UserEntity user = null;
		
		try {
			//Solo si el usuario no está bloqueado permanentemente puede cambiar el password.
			if (! ldapUsuarioDao.isLocked(userLogin)){
				user = ldapUsuarioDao.findById(userLogin);
			}
		} catch (LoginAdminException e) {
			logger.error("No se encontró el usuario en LDAP", e);
		}
		

		if ( user == null){
			response.setResponseCode(EResponse.REMEMBERPASSWORD_USERNOTFOUND.getCode());
			response.setMessage(EResponse.REMEMBERPASSWORD_USERNOTFOUND.getMessage());			
		} else {

			//Cambia el password si así lo definen las políticas.
			if (this.remenberNewPassWord){
				String rememberPassword = PasswordGenerator.getPassword();
				cambiarPasswordByAdmin(userLogin, rememberPassword);
				user.setUserPassword(rememberPassword);
			}

			if( user.getUserMail() != null && !user.getUserMail().isEmpty()){
				response.setResponseCode(EResponse.REMEMBERPASSWORD_OK.getCode());
				response.setMessage(EResponse.REMEMBERPASSWORD_OK.getMessage());
				MailSenderUtil mailSender = new MailSenderUtil();
				if( !mailSender.sendEmailRememberPassword(user) ){
					response.setResponseCode(EResponse.EMAIL_INVALIDO.getCode());
					response.setMessage(EResponse.EMAIL_INVALIDO.getMessage() + " " + EResponse.EMAIL_NO_SEND.getMessage());
				}
			} 
		}					 		

		return response;
	}

	private void cambiarPasswordByAdmin(String userLogin, String newPassword) {
		userLogin = (userLogin != null ?  userLogin.toLowerCase() : userLogin);
		try {
			ldapUsuarioDao.actualizarPasswordByAdmin(userLogin, newPassword);
		} catch (LoginAdminException e) {
			logger.error("No es posible la actualizar el password del usuario ", e);
		}
	}
	

}

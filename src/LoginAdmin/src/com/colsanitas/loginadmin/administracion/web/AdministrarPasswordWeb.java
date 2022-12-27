package com.colsanitas.loginadmin.administracion.web;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.security.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.loginadmin.administracion.business.IAdmnistrarPasswordEjb;
import com.colsanitas.loginadmin.administracion.utils.EResponse;
import com.colsanitas.loginadmin.administracion.view.UserResponseView;
import com.osi.his.sistema.util.JSFUtil;

@Name("administrarPasswordWeb")
@Scope(ScopeType.CONVERSATION)
public class AdministrarPasswordWeb implements Serializable {
    
	private static final long serialVersionUID = -8854717648207135346L;
	
	private static Logger logger = LoggerFactory.getLogger(AdministrarPasswordWeb.class);

    @In Credentials credentials;
    @In StatusMessages statusMessages;
    
    private String urlAfterChangePassword;

    private String usuario;
	private String claveActual;
    private String claveNueva;
    private String confirmarClave;
	
	@In(create = true)
	private IAdmnistrarPasswordEjb administrarPasswordEjb;
    private UserResponseView userResponseView;
    
	@Create
	public void inicializador() {}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClaveActual() {
		return claveActual;
	}

	public void setClaveActual(String claveActual) {
		this.claveActual = claveActual;
	}

	public String getClaveNueva() {
		return claveNueva;
	}

	public void setClaveNueva(String claveNueva) {
		this.claveNueva = claveNueva;
	}

	public String getConfirmarClave() {
		return confirmarClave;
	}

	public void setConfirmarClave(String confirmarClave) {
		this.confirmarClave = confirmarClave;
	}
	
	public UserResponseView getUserResponseView() {
		return userResponseView;
	}

	public void setUserResponseView(UserResponseView userResponseView) {
		this.userResponseView = userResponseView;
	}
	
    public String getUrlAfterChangePassword() {
		return urlAfterChangePassword;
	}

	public void setUrlAfterChangePassword(String urlAfterChangePassword) {
		this.urlAfterChangePassword = urlAfterChangePassword;
	}
	
	/***
	 * Actualiza el password de un usuario
	 */
	public void actualizaPassword(){
		try {
			if( this.claveNueva != null && this.confirmarClave != null && this.claveNueva.equals(this.confirmarClave) ){
				this.userResponseView = this.administrarPasswordEjb.actualizarPassword(this.usuario, this.claveActual, this.claveNueva);
				if( this.userResponseView != null && this.userResponseView.getResponseCode() != EResponse.PASSWD_CHANGED_OK.getCode() ) {
					JSFUtil.addErrorMessage("formAdminPassword", "adm_clave_err_validacion", this.userResponseView.getMessage());
				} else{
					redireccionar(this.urlAfterChangePassword);
				}
			} else {
				JSFUtil.addErrorMessage("formAdminPassword", "adm_clave_err_confirm");
			}
		} catch (Exception e) {
			JSFUtil.addErrorMessage("formAdminPassword", "mensaje_error_inesperado");
			logger.error("Error: RegistrarAdministradorWeb.AdministrarPasswordWeb  "+e.getMessage(), e);
		}
	}
	
	public void recordarPassword(){
		try {
			this.userResponseView = this.administrarPasswordEjb.recordarPassword(this.usuario);
			if( this.userResponseView != null && (this.userResponseView.getResponseCode() == EResponse.REMEMBERPASSWORD_USERNOTFOUND.getCode() || this.userResponseView.getResponseCode() == EResponse.EMAIL_INVALIDO.getCode()) ) {
				JSFUtil.addErrorMessage("formRecordarClave", "respuesta", this.userResponseView.getMessage());
			}
		} catch (Exception e) {
			JSFUtil.addErrorMessage("formRecordarClave", "mensaje_error_inesperado");
			logger.error("Error: RegistrarAdministradorWeb.recordarPassword  "+e.getMessage(), e);
		}
	}
	/**
	 * Redirecciona a la URL despues de cambiar el password
	 */
	public void redireccionar(String urlRedireccionar){
		if (urlRedireccionar != null){
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(urlRedireccionar);
			} catch (IOException e) {
				logger.error("Error haciendo la redireccion por rol: " , e);
			}
		}
	
	}
	
}

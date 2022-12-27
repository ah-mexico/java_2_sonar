package com.colsanitas.loginadmin.administracion.web;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.loginadmin.authentication.AuthenticatorEjb;
import com.osi.gaudi.cfg.Configurator;

@Name("menuWeb")
@Scope(ScopeType.PAGE)
public class MenuWeb implements Serializable {

	private static final long serialVersionUID = -1136597307612024408L;

	private static Logger logger = LoggerFactory.getLogger(AuthenticatorEjb.class);
	
	private String urlAdminUser;
	private String urlAdminPwd;
	private String urlAdminPlantillaRole;
	
	@Create
	@Begin(join=true)
	public void inicializador() {
		
	}

	public String getUrlAdminUser() {
		urlAdminUser = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() +  Configurator.getInstance().getString("sistema","urlConsultarUser", "");
		return urlAdminUser;
	}

	public void setUrlAdminUser(String urlAdminUser) {
		this.urlAdminUser = urlAdminUser;
	}

	public String getUrlAdminPwd() {
		urlAdminPwd = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() +  Configurator.getInstance().getString("sistema","urlPublicAdminPwd", "");
		return urlAdminPwd;
	}

	public void setUrlAdminPwd(String urlAdminPwd) {
		this.urlAdminPwd = urlAdminPwd;
	}

	public String getUrlAdminPlantillaRole() {
		urlAdminPlantillaRole = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() +  Configurator.getInstance().getString("sistema","urlAdminPlantillaRole", "");
		return urlAdminPlantillaRole;
	}

	public void setUrlAdminPlantillaRole(String urlAdminPlantillaRole) {
		this.urlAdminPlantillaRole = urlAdminPlantillaRole;
	}
	
	public void irAdminUsuario(){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(getUrlAdminUser());
		} catch (Exception e) {
			logger.error("Error realizar el redirect: " , e);
		}
	}
	
	public void irAdminPassword(){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(getUrlAdminPwd());
		} catch (Exception e) {
			logger.error("Error realizar el redirect: " , e);
		}
	}
	
	public void irAdminPlantillaRole(){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(getUrlAdminPlantillaRole());
		} catch (Exception e) {
			logger.error("Error realizar el redirect: " , e);
		}
	}
	
}

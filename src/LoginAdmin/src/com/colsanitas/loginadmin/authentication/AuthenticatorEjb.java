package com.colsanitas.loginadmin.authentication;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.osi.gaudi.cfg.Configurator;
import com.osi.gaudi.security.authorization.clientews.Permission;
import com.osi.gaudi.security.web.SecurityFilter;

@Scope(ScopeType.SESSION)
@Name("authenticator")
public class AuthenticatorEjb implements Serializable {

	private static final long serialVersionUID = 8039364904102779440L;
	private static Logger logger = LoggerFactory.getLogger(AuthenticatorEjb.class);
	public static final String APP_NAME = Configurator.getInstance().getString("sistema","applicationName", "LoginAdmin");

	
	private String urlGUIAuthorization = Configurator.getInstance().getString("sistema","urlGUIAuthorization", "http://pruebas.colsanitas.com:8080/GUIAuthorization"); 
	private String ssoURLLogout = Configurator.getInstance().getString("sistema","ssoURLLogout", "https://desarrollo.colsanitas.com:8443/sso/logout");
	private String rolValidoLoginAdmin = Configurator.getInstance().getString("administracion","rolValidoLoginAdmin", "");

	
	@In
	private Identity identity;
	
	@In 
	private Credentials credentials;
	
	private String version;

	@Begin(join=true)
	public boolean authenticate() {
			boolean autenticado = false;
			HttpSession session = ((HttpServletRequest) FacesContext.getCurrentInstance()
			.getExternalContext().getRequest()).getSession();

			String userName = (String) session.getAttribute(SecurityFilter.ATTRIB_USER_NAME);
			if (userName != null){
				@SuppressWarnings("unchecked")
				List<Permission> permissions = (List<Permission>) session.getAttribute(SecurityFilter.ATTRIB_PERMISSIONS);
				logger.info("Estableciendo login automatico con user: " + userName);
				credentials.setUsername(userName);
	
				
				//Adiciono los permisos para controlar las pantallas
				for (Permission permiso: permissions) {
					identity.addRole(permiso.getName());
				}
				
				autenticado = true;
				this.version = Configurator.getInstance().getString("sistema","versionLoginAdmin", "");
				
			} else {
				identity.logout();
			}

			return autenticado;
	}
	
	public String getLogoutURL(){
			return ssoURLLogout; 
	}

	public String getGuiAuthorizationURL(){
		return urlGUIAuthorization;
	}
	
	public String getDefaultView(){
		return Configurator.getInstance().getString("sistema", (identity.hasRole(rolValidoLoginAdmin) ? "urlConsultarUser" : "urlPublicAdminPwd"), ""); 
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void redirectDefaultView(){
		String urlDestino = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + getDefaultView();

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(urlDestino);
		} catch (IOException e) {
			logger.error("Error haciendo la redireccion por rol: " , e);
		}
	
	}
}

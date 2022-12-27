package com.colsanitas.loginadmin.administracion.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.loginadmin.administracion.entity.PrestadorEntity;
import com.colsanitas.loginadmin.administracion.entity.RelacionEntity;
import com.colsanitas.loginadmin.administracion.enums.ETipoRelacion;
import com.colsanitas.loginadmin.relacion.business.IGestionRelaciondEjb;
import com.osi.gaudi.cfg.Configurator;
import com.osi.his.sistema.util.JSFUtil;

/**Bean encargado de redirigir a la aplicación para la descarga 
 * y activacion del lector de huellas
 * @since 12/09/2012
 * @author legranados
 *
 */
@Name("administraAutenticadorWeb")
@Scope(ScopeType.PAGE)
public class AdministraAutenticadorWeb {

	private static final long serialVersionUID = 6751558519780956697L;

	private static Logger logger = LoggerFactory.getLogger(AdministraAutenticadorWeb.class);
	
	//Constantes usadas para envio por URL
	public static final String TIPO_ID = "tipoId";
	public static final String NUMERO_ID = "numeroId";
	public static final String URL_REGRESO = "urlCallBack";
	public static final String SEPARADOR_PARAMETROS = "&";
	public static final String ASIGNACION_PARAMETROS = "=";
	
	
	private StringBuffer urlActivacionLicencia;
	private String urlDescargeInstalador;
	
	
	List<RelacionEntity> pretadores;
	private Long cdperson;
	
	private String mensaje;

    @In Credentials credentials;
    @In StatusMessages statusMessages;
    
    @In(create=true)
    private IGestionRelaciondEjb gestionRelaciondEjb;
    
   
	
	@In
	private Identity identity;
	
	/**
	 * Constructor	
	 */
	public AdministraAutenticadorWeb(){
		urlActivacionLicencia = new StringBuffer(Configurator.getInstance().getString("administracion", "URL_AUTENTICADOR_ACTIVACION_LICENCIA",""));
		urlDescargeInstalador = Configurator.getInstance().getString("administracion", "URL_AUTENTICADOR_DESCAGAR_INSTALADOR","");
		
		cdperson =null;
		pretadores = null;
		mensaje = null;
	}

	/**
	 * Metodo encargada de redireccionar al instalador del lector de huellas
	 */
	public void descargarInstaladorAction(){
		
		mensaje = null;
		
		try {
			FacesContext.getCurrentInstance().responseComplete();
			FacesContext.getCurrentInstance().getExternalContext().redirect(urlDescargeInstalador);
		} catch (IOException e) {
			JSFUtil.addErrorMessage("formAdministrarAutenticador:pgMsgParamAuth", "administrarAutenticador_ErrorInstaldor");
			mensaje = "Error al descargar el instalador";
			logger.error("Error en AdministraAutenticadorWeb.descargarInstaladorAction al direccionar a URL " + urlDescargeInstalador, e);
			
		}
	}
	
	/**
	 * Metodo encargado de redireccionar a la activación de la licencia para el lector de huellas
	 * Este metodo valida que el prestador tenga el tipo y numero de documento registrado, sino es
	 * asi produce un error.
	 */
	public void activarLicenciaAction(){
		
		try {
			mensaje = null;
			
			FacesContext.getCurrentInstance().responseComplete();
			
			PrestadorEntity prestador = new PrestadorEntity(); 
			
			//Busca el prestador seleccionado
			for (RelacionEntity p : pretadores) {
				if(p.getPrestador().getCdperson().equals(cdperson)){
					prestador = p.getPrestador();
				}
			}
			
			
			//Agrega los parametros de la url
			urlActivacionLicencia.append(TIPO_ID);
			urlActivacionLicencia.append(ASIGNACION_PARAMETROS);
			urlActivacionLicencia.append(prestador.getTipoId());
			urlActivacionLicencia.append(SEPARADOR_PARAMETROS);
			urlActivacionLicencia.append(NUMERO_ID);
			urlActivacionLicencia.append(ASIGNACION_PARAMETROS);
			urlActivacionLicencia.append(prestador.getNumId());
			urlActivacionLicencia.append(SEPARADOR_PARAMETROS);
			urlActivacionLicencia.append(URL_REGRESO);
			urlActivacionLicencia.append(ASIGNACION_PARAMETROS);
			urlActivacionLicencia.append(Configurator.getInstance().getString("administracion", "URL_LOGIN_ADMIN",""));
			
			logger.info("URL: " + urlActivacionLicencia.toString());
			FacesContext.getCurrentInstance().getExternalContext().redirect(urlActivacionLicencia.toString());
		
			
		} catch (IOException e) {
			
			mensaje = "Error al consultar las licencias del prestador";
			logger.error("Error en AdministraAutenticadorWeb.activarLicenciaAction al direccionar a URL " + urlDescargeInstalador, e);
		}
		
	}
	
	public  List<SelectItem> getPrestadores() throws Exception{

		ArrayList<SelectItem> prestadoresList;

		try {
				
			prestadoresList = new ArrayList<SelectItem>();

			// Consulta los prestadores relacionados al administrador
			pretadores = gestionRelaciondEjb.consultarRelacionesDelUsuario(credentials.getUsername(), ETipoRelacion.ADMINISTRADOR.getId());
			
			
			
			
			for (RelacionEntity p : pretadores) {
				prestadoresList.add(new SelectItem(p.getPrestador().getCdperson(), p.getPrestador().getRazonSocial()));
			}

			return prestadoresList;

		} catch (Exception e) {
			mensaje = "Error consultando prestadores asociados";
			logger.error("Error consultando prestadores asociados al administrador " + identity.getCredentials().getUsername(), e);
			throw e;
		}

	}

	public StringBuffer getUrlActivacionLicencia() {
		return urlActivacionLicencia;
	}


	public void setUrlActivacionLicencia(StringBuffer urlActivacionLicencia) {
		this.urlActivacionLicencia = urlActivacionLicencia;
	}


	public String getUrlDescargeInstalador() {
		return urlDescargeInstalador;
	}


	public void setUrlDescargeInstalador(String urlDescargeInstalador) {
		this.urlDescargeInstalador = urlDescargeInstalador;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List<RelacionEntity> getPretadores() {
		return pretadores;
	}

	public void setPretadores(List<RelacionEntity> pretadores) {
		this.pretadores = pretadores;
	}

	public Long getCdperson() {
		return cdperson;
	}

	public void setCdperson(Long cdperson) {
		this.cdperson = cdperson;
	}

	
	
}

package com.colsanitas.loginadmin.administracion.web;

import java.util.LinkedList;
import java.util.List;


import javax.faces.model.SelectItem;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.loginadmin.administracion.business.IAdministrarAutorizaciondEjb;
import com.colsanitas.loginadmin.administracion.business.IGestionUsuariosEjb;
import com.colsanitas.loginadmin.administracion.entity.PrestadorEntity;
import com.colsanitas.loginadmin.administracion.entity.RelacionEntity;
import com.colsanitas.loginadmin.administracion.entity.UserEntity;
import com.colsanitas.loginadmin.administracion.enums.ETipoRelacion;
import com.colsanitas.loginadmin.administracion.utils.EResponse;
import com.colsanitas.loginadmin.administracion.view.PrestadorView;
import com.colsanitas.loginadmin.administracion.view.TipoDocumentoView;
import com.colsanitas.loginadmin.administracion.view.UserResponseView;
import com.colsanitas.loginadmin.dto.DatosPrestadorDTO;
import com.colsanitas.loginadmin.dto.SucursalesByPrestadorDTO;
import com.colsanitas.loginadmin.exception.LoginAdminException;
import com.osi.gaudi.cfg.Configurator;
import com.osi.his.sistema.util.CrudTableExtWeb;
import com.osi.his.sistema.util.JSFUtil;
import com.osi.his.sistema.util.finder.AbstractFinder;

@Name("consultarUsuarioWeb")
@Scope(ScopeType.SESSION)
public class ConsultarUsuarioWeb extends CrudTableExtWeb<UserEntity> {

	private static final long serialVersionUID = -4645047852542043784L;
	
	private static Logger logger = LoggerFactory.getLogger(ConsultarUsuarioWeb.class);

    @In Credentials credentials;
    @In StatusMessages statusMessages;
    
    @In(create=true, required=false)
	AdministrarAplicacionesAdministradorWeb administrarAplicacionesAdministradorWeb;
    
    @In
	private Identity identity;
    
    private String login;
    private Long tipoDocSel;
    private String prestadorSel;
    private String loginUserSel;
    private String tipoDocBusq;
	private String numeroDocBusq;
    private String nombreBusq;
    private String sucursalSel;
    private String emailBusq;
    private String codigoPrestBusq;
   
    
	private List<UserEntity> listUser;
    private List<SelectItem> listTipoDocs;
    private LinkedList<SelectItem> listPres = new LinkedList<SelectItem>();
    private List<PrestadorEntity> listPrestRelaccion;
    private List<RelacionEntity> listRelUser;
    private LinkedList<SelectItem> listaSucursales;
    
	@In(create = true, value = "prestadorFinder")
	private AbstractFinder<PrestadorView> prestadorFinder;
	
	@In(create = true, value = "prestadorLocalFinder")
	private AbstractFinder<PrestadorEntity> prestadorLocalFinder;
    
	@In(create=true)
    private IGestionUsuariosEjb gestionUsuariosEjb;
	
	@In(create = true)
	IAdministrarAutorizaciondEjb administrarAutorizacionEjb;
	
	private UserResponseView userResponseView;
	private UserEntity usuarioAdmin;
	private PrestadorEntity prestadorEntitySel;
	private RelacionEntity relacionPresDesau;
	private RelacionEntity relacionGesPrestApli;
	
	private PrestadorView prestadorSelected = new PrestadorView();
	private PrestadorEntity prestadorLocalSelected = new PrestadorEntity();
	
	private DatosPrestadorDTO datosPrestadorDTO;
	
	private boolean tieneRolAdmin;
	
	private boolean administradorFunOsi ;
	
	public boolean isTieneRolAdmin() {
		return tieneRolAdmin;
	}

	public void setTieneRolAdmin(boolean tieneRolAdmin) {
		this.tieneRolAdmin = tieneRolAdmin;
	}
	public boolean isAdministradorFunOsi() {
		return administradorFunOsi;
	}

	public void setAdministradorFunOsi(boolean administradorFunOsi) {
		
		this.administradorFunOsi = administradorFunOsi;
	}

	@Create
	@Begin(join=true)
	public void inicializador() {
		this.usuarioAdmin = null;
		
		administradorFunOsi = gestionUsuariosEjb.tieneRolFuncionarioOsi(credentials.getUsername() ,Configurator.getInstance().getString("administracion", "USER_LOGIN_ADMIN",null));
		
    	cargarListTipoDocs();
    	if( identity.hasRole( Configurator.getInstance().getString("administracion", "rolAdminPrestador","") ) ){
    		cargarPrestadoresAdm();
		}

		this.setPrestadorLocalSelected(new PrestadorEntity());
    	this.loginUserSel = null;
    	this.datosPrestadorDTO = null;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	public List<UserEntity> getListUser() {
		return listUser;
	}

	public void setListUser(List<UserEntity> listUser) {
		this.listUser = listUser;
	}
	
	public List<SelectItem> getListTipoDocs() {
		return listTipoDocs;
	}

	public void setListTipoDocs(List<SelectItem> listTipoDocs) {
		this.listTipoDocs = listTipoDocs;
	}
	
	public Long getTipoDocSel() {
		return tipoDocSel;
	}

	public void setTipoDocSel(Long tipoDocSel) {
		this.tipoDocSel = tipoDocSel;
	}
	
	public LinkedList<SelectItem> getListPres() {
		return listPres;
	}

	public void setListPres(LinkedList<SelectItem> listPres) {
		this.listPres = listPres;
	}
	
	public UserResponseView getUserResponseView() {
		return userResponseView;
	}

	public void setUserResponseView(UserResponseView userResponseView) {
		this.userResponseView = userResponseView;
	}
	
	public UserEntity getUsuarioAdmin() {
		return usuarioAdmin;
	}

	public void setUsuarioAdmin(UserEntity usuarioAdmin) {
		this.usuarioAdmin = usuarioAdmin;
	}
	
	public PrestadorView getPrestadorSelected() {
		return prestadorSelected;
	}

	public void setPrestadorSelected(PrestadorView prestadorSelected) {
		this.prestadorSelected = prestadorSelected;
	}
	
	public AbstractFinder<PrestadorView> getPrestadorFinder() {
		return prestadorFinder;
	}

	public void setPrestadorFinder(AbstractFinder<PrestadorView> prestadorFinder) {
		this.prestadorFinder = prestadorFinder;
	}
	
	public PrestadorEntity getPrestadorEntitySel() {
		return prestadorEntitySel;
	}

	public void setPrestadorEntitySel(PrestadorEntity prestadorEntitySel) {
		this.prestadorEntitySel = prestadorEntitySel;
	}
	
	public List<PrestadorEntity> getListPrestRelaccion() {
		return listPrestRelaccion;
	}

	public void setListPrestRelaccion(List<PrestadorEntity> listPrestRelaccion) {
		this.listPrestRelaccion = listPrestRelaccion;
	}
	
	public String getPrestadorSel() {
		return prestadorSel;
	}

	public void setPrestadorSel(String prestadorSel) {
		this.prestadorSel = prestadorSel;
	}
	
	public List<RelacionEntity> getListRelUser() {
		return listRelUser;
	}

	public void setListRelUser(List<RelacionEntity> listRelUser) {
		this.listRelUser = listRelUser;
	}
	
	public String getLoginUserSel() {
		return loginUserSel;
	}

	public void setLoginUserSel(String loginUserSel) {
		this.loginUserSel = loginUserSel;
	}
	
	public RelacionEntity getRelacionPresDesau() {
		return relacionPresDesau;
	}

	public void setRelacionPresDesau(RelacionEntity relacionPresDesau) {
		this.relacionPresDesau = relacionPresDesau;
	}
	
	public String getTipoDocBusq() {
		return tipoDocBusq;
	}

	public void setTipoDocBusq(String tipoDocBusq) {
		this.tipoDocBusq = tipoDocBusq;
	}

	public String getNumeroDocBusq() {
		return numeroDocBusq;
	}

	public void setNumeroDocBusq(String numeroDocBusq) {
		this.numeroDocBusq = numeroDocBusq;
	}

	public String getNombreBusq() {
		return nombreBusq;
	}

	public void setNombreBusq(String nombreBusq) {
		this.nombreBusq = nombreBusq;
	}
	
	public PrestadorEntity getPrestadorLocalSelected() {
		return prestadorLocalSelected;
	}

	public void setPrestadorLocalSelected(PrestadorEntity prestadorLocalSelected) {
		this.prestadorLocalSelected = prestadorLocalSelected;
	}

	public Long getPrestadorLocalSelectedID() {
		return prestadorLocalSelected.getPrestadorId();
	}

	public void setPrestadorLocalSelectedID(Long prestadorLocalSelectedID) {
		for(SelectItem p : getListPres()){
			if (((Long) p.getValue()).compareTo(prestadorLocalSelectedID) == 0){
				prestadorLocalSelected.setPrestadorId((Long) p.getValue());
				prestadorLocalSelected.setRazonSocial(p.getLabel());
				break;
			}
		}
	}

	
	public AbstractFinder<PrestadorEntity> getPrestadorLocalFinder() {
		return prestadorLocalFinder;
	}

	public void setPrestadorLocalFinder( AbstractFinder<PrestadorEntity> prestadorLocalFinder ) {
		this.prestadorLocalFinder = prestadorLocalFinder;
	}
	
	public String getSucursalSel() {
		return sucursalSel;
	}

	public void setSucursalSel(String sucursalSel) {
		this.sucursalSel = sucursalSel;
	}
	
	public LinkedList<SelectItem> getListaSucursales() {
		return listaSucursales;
	}

	public void setListaSucursales(LinkedList<SelectItem> listaSucursales) {
		this.listaSucursales = listaSucursales;
	}
	
	public String getEmailBusq() {
		return emailBusq;
	}

	public void setEmailBusq(String emailBusq) {
		this.emailBusq = emailBusq;
	}
	
	public DatosPrestadorDTO getDatosPrestadorDTO() {
		return datosPrestadorDTO;
	}

	public void setDatosPrestadorDTO(DatosPrestadorDTO datosPrestadorDTO) {
		this.datosPrestadorDTO = datosPrestadorDTO;
	}
	
	public String getCodigoPrestBusq() {
		return codigoPrestBusq;
	}

	public void setCodigoPrestBusq(String codigoPrestBusq) {
		this.codigoPrestBusq = codigoPrestBusq;
	}
	
	public RelacionEntity getRelacionGesPrestApli() {
		return relacionGesPrestApli;
	}

	public void setRelacionGesPrestApli(RelacionEntity relacionGesPrestApli) {
		this.relacionGesPrestApli = relacionGesPrestApli;
	}

	public void buscarUsuarios(){
		this.loginUserSel = null;
		UserEntity datosBusq = null;
		try {
			//Para que cuando el finder le borren la info de la sucusal
			if (prestadorLocalSelected != null && prestadorLocalSelected.getSucursal() != null && prestadorLocalSelected.getSucursal().isEmpty()){
				prestadorLocalSelected.setPrestadorId(null);
			}
			
			if( (this.numeroDocBusq == null || (this.numeroDocBusq != null && this.numeroDocBusq.isEmpty()) ) 
					&& (this.nombreBusq == null || (this.nombreBusq != null && this.nombreBusq.isEmpty()) )
					&& (this.emailBusq == null || (this.emailBusq != null && this.emailBusq.isEmpty()) )
					&& (prestadorLocalSelected == null || (prestadorLocalSelected.getPrestadorId() == null))
				){
				JSFUtil.addWarnMessage("formConsulUser:pgBusqUser", "seleccionar_criterio_busqueda");
			} else {
				
				datosBusq = new UserEntity();
				datosBusq.setTipoDoc(this.tipoDocBusq);
				datosBusq.setDocument(this.numeroDocBusq);
				datosBusq.setUserName(this.nombreBusq);
				datosBusq.setUserMail(this.emailBusq);
				datosBusq.setIdPrestador( prestadorLocalSelected != null && (prestadorLocalSelected.getPrestadorId() != null)? prestadorLocalSelected.getPrestadorId() : null );
				
				this.listUser = administrarAutorizacionEjb.buscarUsuariosCriterios(datosBusq);
				
				if( this.listUser != null && !this.listUser.isEmpty() ){
					for( UserEntity user: this.listUser ){
						
						
						/**
						 * Modificado por Softmanagement s.a.
						 * Yency Serrano
						 * 20/03/2013
						 */
						String rolUsuarioAdmin = "";
						
						
							rolUsuarioAdmin = Configurator.getInstance().getString("administracion", "USER_LOGIN_ADMIN",null);
							tieneRolAdmin= gestionUsuariosEjb.tieneRolFuncionarioOsi(user.getUserId(),rolUsuarioAdmin);
					       	  
						    user.setAdminOsi(tieneRolAdmin);
						    	
						    
						
						forTipoDoc:
						for( SelectItem item: this.listTipoDocs ){
							if( item.getValue().toString().equals(user.getTipoDoc()) ){
								user.setUserDocument( item.getLabel() + user.getDocument() );
								break forTipoDoc;
							}
						}
					}
				}
				setElementList(this.listUser);
				if( this.listUser == null ){
					JSFUtil.addWarnMessage("formConsulUser:pgDataListUser", "no_registro");
				} else if(this.listUser != null && this.listUser.isEmpty() ) {
					JSFUtil.addWarnMessage("formConsulUser:pgDataListUser", "no_registro");
				}
				this.listRelUser = null;
			}
		} catch (LoginAdminException e) {
			logger.error("Error: ConsultarUsuarioWeb.buscarUsuarios  "+e.getMessage(), e);
			JSFUtil.addErrorMessage("formConsulUser:pgDataListUser", "mensaje_error_inesperado");
		} catch (Exception e) {
			logger.error("Error: ConsultarUsuarioWeb.buscarUsuarios  "+e.getMessage(), e);
			JSFUtil.addErrorMessage("formConsulUser:pgDataListUser", "mensaje_error_inesperado");
		}
		
	}
    
    public void cargarListTipoDocs() {
		 List<TipoDocumentoView> lTipoDocs = null;
		 try {
			 lTipoDocs = gestionUsuariosEjb.listTipoDocs();
			 this.listTipoDocs = new LinkedList<SelectItem>();
			 for (TipoDocumentoView tipoDoc : lTipoDocs) {
				this.listTipoDocs.add(new SelectItem(tipoDoc.getId(), tipoDoc.getName()));
			 }
			 if(lTipoDocs != null && lTipoDocs.size() > 0){
				 this.tipoDocSel = lTipoDocs.get(0).getId();
				 this.tipoDocBusq = String.valueOf(lTipoDocs.get(0).getId());
			 }
		} catch (Exception e) {
			JSFUtil.addErrorMessage("formConsulUser", "mensaje_error_inesperado");
		}
	 }
    
    public void cargarPrestadoresAdm(){
		try {
			this.listPrestRelaccion = administrarAutorizacionEjb.consultarPrestadoresAdministrados(credentials.getUsername());

			this.listPres = new LinkedList<SelectItem>();
			for (PrestadorEntity prestador : this.listPrestRelaccion) {
				this.listPres.add(new SelectItem(prestador.getPrestadorId(), prestador.getRazonSocial()));
			}
			if (this.listPrestRelaccion != null && this.listPrestRelaccion.size() > 0) {
				this.prestadorSel = String.valueOf(this.listPrestRelaccion.get(0).getPrestadorId());
			}
			
		} catch (Exception e) {
			JSFUtil.addErrorMessage("formConsulUser", "mensaje_error_inesperado");
			logger.error("Error: ConsultarUsuarioWeb.cargarPrestadoresAdm  "+e.getMessage(), e);
		}
    }

	@Override
	public boolean isDeleteable(int selection) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEditable(int selection) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected UserEntity doCreate(UserEntity element) {
		this.listRelUser = null;
		this.loginUserSel = null;
		String loginUser = null;
		try {
			if( this.listUser == null || ( this.listUser != null && this.listUser.isEmpty() ) ){
				this.listUser = new LinkedList<UserEntity>();
				setElementList(this.listUser);
			}
			this.usuarioAdmin = null;
			if( element != null ){
				loginUser = element.getUserLogin();
				element.setUserDocument(element.getTipoDoc()+"."+element.getDocument());
				this.userResponseView = gestionUsuariosEjb.crearUsuario(element, credentials.getUsername());
				
				if (this.userResponseView != null && this.userResponseView.getResponseCode() != EResponse.USER_REGISTERED.getCode() && this.userResponseView.getResponseCode() != EResponse.EMAIL_NO_SEND.getCode()) {
					element.setUserLogin(loginUser);
					JSFUtil.addErrorMessage("formConsulUser:pgDataListUser", "respuesta", this.userResponseView.getMessage());
					this.doNotAdd=true;
				} else if( this.userResponseView != null && this.userResponseView.getResponseCode() == EResponse.EMAIL_NO_SEND.getCode() ){
					for( SelectItem item: listTipoDocs ){
						if( item.getValue().toString().equals( element.getTipoDoc() ) ){
							element.setUserDocument(item.getLabel().toString()+element.getDocument());
						}
					}
					JSFUtil.addWarnMessage("formConsulUser:pgDataListUser", "respuesta", this.userResponseView.getMessage());
				} else if (this.userResponseView != null && this.userResponseView.getResponseCode() == EResponse.USER_REGISTERED.getCode()) {
					for( SelectItem item: listTipoDocs ){
						if( item.getValue().toString().equals( element.getTipoDoc() ) ){
							element.setUserDocument(item.getLabel().toString()+element.getDocument());
						}
					}
					JSFUtil.addInfoMessage("formConsulUser:pgDataListUser", "respuesta", this.userResponseView.getMessage());
				} else if (this.userResponseView != null && this.userResponseView.getResponseCode() == EResponse.USER_EXISTS_TIPO_DOC.getCode()) {
					for( SelectItem item: listTipoDocs ){
						if( item.getValue().toString().equals( element.getTipoDoc() ) ){
							element.setUserDocument(item.getLabel().toString()+element.getDocument());
						}
					}
					JSFUtil.addInfoMessage("formConsulUser:pgDataListUser", "respuesta", this.userResponseView.getMessage());
				}
			}
		} catch (Exception e) {
			this.doNotAdd=true;
			logger.error("Error: ConsultarUsuarioWeb.doCreate  "+e.getMessage(), e);
			JSFUtil.addErrorMessage("formConsulUser:pgDataListUser", "mensaje_error_inesperado");
		}
		return element;
	}

	@Override
	protected UserEntity doUpdate(UserEntity element) {
		this.listRelUser = null;
		this.loginUserSel = null;
		try {
			this.usuarioAdmin = null;
			if( element != null ){
				element.setUserDocument(element.getTipoDoc()+"."+element.getDocument());
				this.userResponseView = gestionUsuariosEjb.actualizarUsuario(element);
				
				if (this.userResponseView != null && this.userResponseView.getResponseCode() == EResponse.EMAIL_NO_SEND.getCode()) {
					for( SelectItem item: listTipoDocs ){
						if( item.getValue().toString().equals( element.getTipoDoc() ) ){
							element.setUserDocument(item.getLabel().toString()+element.getDocument());
						}
					}
					JSFUtil.addWarnMessage("formConsulUser:pgDataListUser", "respuesta", this.userResponseView.getMessage());
				} else if (this.userResponseView != null && this.userResponseView.getResponseCode() != EResponse.USER_MODIFY.getCode()) {
					JSFUtil.addErrorMessage("formConsulUser:pgDataListUser", "respuesta", this.userResponseView.getMessage());
					this.doNotAdd=true;
				} else if (this.userResponseView != null && this.userResponseView.getResponseCode() == EResponse.USER_MODIFY.getCode()) {
					for( SelectItem item: listTipoDocs ){
						if( item.getValue().toString().equals( element.getTipoDoc() ) ){
							element.setUserDocument(item.getLabel().toString()+element.getDocument());
						}
					}
					JSFUtil.addInfoMessage("formConsulUser:pgDataListUser", "respuesta", this.userResponseView.getMessage());
				} else if (this.userResponseView != null && this.userResponseView.getResponseCode() == EResponse.USER_EXISTS_TIPO_DOC.getCode()) {
					for( SelectItem item: listTipoDocs ){
						if( item.getValue().toString().equals( element.getTipoDoc() ) ){
							element.setUserDocument(item.getLabel().toString()+element.getDocument());
						}
					}
					JSFUtil.addInfoMessage("formConsulUser:pgDataListUser", "respuesta", this.userResponseView.getMessage());
				}
			}
		} catch (Exception e) {
			this.doNotAdd=true;
			logger.error("Error: ConsultarUsuarioWeb.doUpdate  "+e.getMessage(), e);
			JSFUtil.addErrorMessage("formConsulUser:pgDataListUser", "mensaje_error_inesperado");
		}
		return element;
	}

	@Override
	protected void doRemove(UserEntity element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected UserEntity clone(UserEntity element) {
		// TODO Auto-generated method stub
		return element.clone();
	}

	@Override
	protected UserEntity createNew() {
		UserEntity nuevo = new UserEntity();
		return nuevo;
	}

	@Override
	protected boolean isIdSet(UserEntity element) {
		return (element.getUserId() != null && !element.getUserId().isEmpty());
	}

	@Override
	public void seleccionar(UserEntity element) {
		
		
	}
	
	public void cargarAutAdmin( UserEntity element ){
	
		try {
			this.usuarioAdmin = element;
			this.prestadorSelected = new PrestadorView();
			this.listaSucursales = null;
			this.datosPrestadorDTO = null;
			this.codigoPrestBusq = null;
			cargarListTipoDocs();
			
		
		      
		    } catch (Exception e) {
			
		}
		    
		
	}
	
	public void cargarAutDele( UserEntity element ) {
		limpiarDatos();
	
		this.prestadorSelected = new PrestadorView();
		this.listaSucursales = null;
		this.datosPrestadorDTO = null;
		this.codigoPrestBusq = null;
		
		
		cargarPrestadoresAdm();
		
		try {
			this.usuarioAdmin = element;
			if( this.prestadorSel != null && !this.prestadorSel.isEmpty() ){
				for( PrestadorEntity prestador: this.listPrestRelaccion ){
					if( prestador.getPrestadorId().toString().equals(this.prestadorSel) ){
						this.prestadorEntitySel = new PrestadorEntity();
						this.prestadorEntitySel.setPrestadorId(prestador.getPrestadorId());
						this.prestadorEntitySel.setCdperson(prestador.getCdperson());
						this.prestadorEntitySel.setTipoId(prestador.getTipoId());
						this.prestadorEntitySel.setNumId(prestador.getNumId());
						this.prestadorEntitySel.setRazonSocial(prestador.getRazonSocial());
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error: ConsultarUsuarioWeb.cargarAutDele  "+e.getMessage(), e);
			JSFUtil.addErrorMessage("formConsulUser", "mensaje_error_inesperado");
		}
	}
	
	public void prestadorSeleccionado(){}
	
	public void seleccionarPrest(){
		try {
			this.prestadorEntitySel = new PrestadorEntity();
			
			if( this.prestadorSel != null && !this.prestadorSel.isEmpty() ){
				for( PrestadorEntity prestador: this.listPrestRelaccion ){
					if( prestador.getPrestadorId().toString().equals(this.prestadorSel) ){
						this.prestadorEntitySel.setPrestadorId(prestador.getPrestadorId());
						this.prestadorEntitySel.setCdperson(prestador.getCdperson());
						this.prestadorEntitySel.setTipoId(prestador.getTipoId());
						this.prestadorEntitySel.setNumId(prestador.getNumId());
						this.prestadorEntitySel.setRazonSocial(prestador.getRazonSocial());
						this.prestadorEntitySel.setSucursal(prestador.getSucursal());
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error: ConsultarUsuarioWeb.seleccionarPrest  "+e.getMessage(), e);
			JSFUtil.addErrorMessage("formConsulUser", "mensaje_error_inesperado");
		}
	}
	
	public void autorizarAdmin(){
		PrestadorEntity prestadorEntity = null;
		this.userResponseView = null;
		String estadoPrestAzimut = "";
		try {
			estadoPrestAzimut = Configurator.getInstance().getString("administracion", "estadoActPrestadorAzimut", "ACTIVO");
			if( this.datosPrestadorDTO != null && this.datosPrestadorDTO.getCodigoPersona() != null && !this.datosPrestadorDTO.getRazonSocial().isEmpty() ) {
				prestadorEntity = new PrestadorEntity();
				prestadorEntity.setCdperson(Long.valueOf(this.datosPrestadorDTO.getCodigoPersona()));
				prestadorEntity.setTipoId(String.valueOf( this.datosPrestadorDTO.getTipoID() ) );
				prestadorEntity.setNumId(this.datosPrestadorDTO.getNumeroID());
				prestadorEntity.setRazonSocial(this.datosPrestadorDTO.getRazonSocial());
				prestadorEntity.setEstado( this.datosPrestadorDTO.getEstado() != null && this.datosPrestadorDTO.getEstado().equals(estadoPrestAzimut)  );
				if( this.sucursalSel != null && this.listaSucursales != null ){
					prestadorEntity.setSucursal(this.sucursalSel);
					if( this.listaSucursales != null && this.listaSucursales.size() > 0 ){
						for( SelectItem item: this.listaSucursales) {
							if( item.getValue().toString().equals(this.sucursalSel) ){
								prestadorEntity.setRazonSocial(item.getLabel());
								break;
							}
						}
					}
				} else {
					prestadorEntity.setSucursal(this.datosPrestadorDTO.getNumeroID());
					prestadorEntity.setRazonSocial(this.datosPrestadorDTO.getRazonSocial());
				}
				
				this.userResponseView = this.administrarAutorizacionEjb.registrarRelacion(this.usuarioAdmin, prestadorEntity, ETipoRelacion.ADMINISTRADOR.getId(), true, this.credentials.getUsername());
				if( this.userResponseView.getResponseCode() == EResponse.USER_AUTORIZADO.getCode() && this.userResponseView.getMessage().equals(EResponse.USER_AUTORIZADO.getMessage()) ) {
					JSFUtil.addWarnMessage("includePnlAutorizarAdmin:formAutAdmin", "usuario_autorizado");
				} else if( this.userResponseView.getResponseCode() == EResponse.TRANSACTION_OK.getCode() ){
					if( this.loginUserSel != null && !this.loginUserSel.isEmpty() && this.usuarioAdmin != null && this.usuarioAdmin.getUserId() != null && this.loginUserSel.equals(this.usuarioAdmin.getUserId()) ){
						this.listRelUser = this.userResponseView.getListRelacioUser();
						
					}
					JSFUtil.addInfoMessage("formConsulUser:pgDataListUser", "usuario_autorizado_correctamente");
				} else if( this.userResponseView.getResponseCode() == EResponse.EMAIL_NO_SEND.getCode() ){
					if( this.loginUserSel != null && !this.loginUserSel.isEmpty() ){
						this.listRelUser = this.userResponseView.getListRelacioUser();
					}
					JSFUtil.addWarnMessage("formConsulUser:pgDataListUser", "respuesta", this.userResponseView.getMessage());
				}
			} else {
				JSFUtil.addWarnMessage("includePnlAutorizarAdmin:formAutAdmin", "seleccion_prestador_aut");
			}
		} catch (Exception e) {
			logger.error("Error: ConsultarUsuarioWeb.autorizarAdmin  "+e.getMessage(), e);
			JSFUtil.addErrorMessage("formConsulUser:pgDataListUser", "mensaje_error_inesperado");
		}
	}
	
	public void autorizarDele(){
		this.userResponseView = null;
		try {
			seleccionarPrest();
			if( this.prestadorEntitySel != null ){
				if( this.usuarioAdmin != null ){
					this.userResponseView = this.administrarAutorizacionEjb.registrarRelacion(this.usuarioAdmin, this.prestadorEntitySel, ETipoRelacion.DELEGADO.getId(), true, this.credentials.getUsername());
					if( this.userResponseView.getResponseCode() == EResponse.USER_AUTORIZADO.getCode() && this.userResponseView.getMessage().equals(EResponse.USER_AUTORIZADO.getMessage()) ) {
						JSFUtil.addWarnMessage("includePnlAutorizarDele:formAutDele", "usuario_autorizado");
					} else if( this.userResponseView.getResponseCode() == EResponse.TRANSACTION_OK.getCode() ){
						if( this.loginUserSel != null && !this.loginUserSel.isEmpty() ){
							this.listRelUser = this.userResponseView.getListRelacioUser();
						}
						JSFUtil.addInfoMessage("formConsulUser:pgDataListUser", "usuario_autorizado_correctamente");
					} else if( this.userResponseView.getResponseCode() == EResponse.EMAIL_NO_SEND.getCode() ){
						if( this.loginUserSel != null && !this.loginUserSel.isEmpty() && this.usuarioAdmin != null && this.usuarioAdmin.getUserId() != null && this.loginUserSel.equals(this.usuarioAdmin.getUserId()) ){
							this.listRelUser = this.userResponseView.getListRelacioUser();
						}
						JSFUtil.addWarnMessage("formConsulUser:pgDataListUser", "respuesta", this.userResponseView.getMessage());
					}
				}
			} else {
				JSFUtil.addWarnMessage("includePnlAutorizarDele:formAutDele", "seleccionar_prestador_aut");
			}
		} catch (Exception e) {
			logger.error("Error: ConsultarUsuarioWeb.autorizarDele  "+e.getMessage(), e);
			JSFUtil.addErrorMessage("formConsulUser:pgDataListUser", "mensaje_error_inesperado");
		}
		
	}
	
	public void autorizarDeleFuncionarioOsi(){
		PrestadorEntity prestadorEntity = null;
		this.userResponseView = null;
		String estadoPrestAzimut = "";
	
		try {
			estadoPrestAzimut = Configurator.getInstance().getString("administracion", "estadoActPrestadorAzimut", "ACTIVO");
			if( this.datosPrestadorDTO != null && this.datosPrestadorDTO.getCodigoPersona() != null && !this.datosPrestadorDTO.getRazonSocial().isEmpty() ) {
				prestadorEntity = new PrestadorEntity();
				prestadorEntity.setCdperson(Long.valueOf(this.datosPrestadorDTO.getCodigoPersona()));
				prestadorEntity.setTipoId(String.valueOf( this.datosPrestadorDTO.getTipoID() ) );
				prestadorEntity.setNumId(this.datosPrestadorDTO.getNumeroID());
				prestadorEntity.setRazonSocial(this.datosPrestadorDTO.getRazonSocial());
				prestadorEntity.setEstado( this.datosPrestadorDTO.getEstado() != null && this.datosPrestadorDTO.getEstado().equals(estadoPrestAzimut));
				if( this.sucursalSel != null && this.listaSucursales != null ){
					prestadorEntity.setSucursal(this.sucursalSel);
					if( this.listaSucursales != null && this.listaSucursales.size() > 0 ){
						for( SelectItem item: this.listaSucursales) {
							if( item.getValue().toString().equals(this.sucursalSel) ){
								prestadorEntity.setRazonSocial(item.getLabel());
								break;
							}
						}
					}
				} else {
					prestadorEntity.setSucursal(this.datosPrestadorDTO.getNumeroID());
					prestadorEntity.setRazonSocial(this.datosPrestadorDTO.getRazonSocial());
				}
				
				if ( administrarAutorizacionEjb.consultaPrestadorTieneAdminPrestador(prestadorEntity.getSucursal())){//Se cambio a sucursal
				
					this.userResponseView = this.administrarAutorizacionEjb.registrarRelacion(this.usuarioAdmin, prestadorEntity, ETipoRelacion.DELEGADO.getId(), true, this.credentials.getUsername());
					if( this.userResponseView.getResponseCode() == EResponse.USER_AUTORIZADO.getCode() && this.userResponseView.getMessage().equals(EResponse.USER_AUTORIZADO.getMessage()) ) {
						JSFUtil.addWarnMessage("includePnlAutorizarDele:formAutDeleFunOsi", "usuario_autorizado");
					} else if( this.userResponseView.getResponseCode() == EResponse.TRANSACTION_OK.getCode() ){
						if( this.loginUserSel != null && !this.loginUserSel.isEmpty() && this.usuarioAdmin != null && this.usuarioAdmin.getUserId() != null && this.loginUserSel.equals(this.usuarioAdmin.getUserId()) ){
							this.listRelUser = this.userResponseView.getListRelacioUser();
							
						}
						JSFUtil.addInfoMessage("formConsulUser:pgDataListUser", "usuario_autorizado_correctamente");
					} else if( this.userResponseView.getResponseCode() == EResponse.EMAIL_NO_SEND.getCode() ){
						if( this.loginUserSel != null && !this.loginUserSel.isEmpty() ){
							this.listRelUser = this.userResponseView.getListRelacioUser();
						}
						JSFUtil.addWarnMessage("formConsulUser:pgDataListUser", "respuesta", this.userResponseView.getMessage());
					}
				}
				else{
					JSFUtil.addErrorMessage("includePnlAutorizarDele:formAutDeleFunOsi", "existe_admin_prestador");
					
				}
				
			} else {
				JSFUtil.addWarnMessage("includePnlAutorizarDele:formAutDeleFunOsi", "seleccion_prestador_aut");
			}
		} catch (Exception e) {
			logger.error("Error: ConsultarUsuarioWeb.autorizarAdmin  "+e.getMessage(), e);
			JSFUtil.addErrorMessage("formConsulUser:pgDataListUser", "mensaje_error_inesperado");
		}
	}
	
	public void selPrestador(){}
	
	public void listarRelacionesUser(){
		try {
			this.listRelUser = this.administrarAutorizacionEjb.consultarRelacionUserDesau(this.loginUserSel, this.credentials.getUsername());
			if( listRelUser == null ){
				JSFUtil.addWarnMessage("formConsulUser:pgListRelUser", "no_relaciones");
			} else if(listRelUser != null && listRelUser.size() == 0) {
				JSFUtil.addWarnMessage("formConsulUser:pgListRelUser", "no_relaciones");
			}
		} catch (Exception e) {
			logger.error("Error: ConsultarUsuarioWeb.listarRelacionesUser  "+e.getMessage(), e);
			JSFUtil.addErrorMessage("formConsulUser:pgListRelUser", "mensaje_error_inesperado");
		}
	}
	
	public void cargarDesautorizarPrest( RelacionEntity relacionEntity ){
		this.relacionPresDesau = relacionEntity;
	}
	
	public void desautorizarPrest(){
		this.userResponseView = null;
		try {
			if( this.relacionPresDesau != null ){
				if( this.relacionPresDesau.getTipoRelacion() == ETipoRelacion.ADMINISTRADOR.getId() ) {
					this.userResponseView = this.administrarAutorizacionEjb.actualizarRelacion(this.relacionPresDesau, ETipoRelacion.ADMINISTRADOR.getId(), false);
				} else {
					this.userResponseView = this.administrarAutorizacionEjb.actualizarRelacion(this.relacionPresDesau, ETipoRelacion.DELEGADO.getId(), false);
				}
				if( this.userResponseView != null && this.userResponseView.getResponseCode() == EResponse.TRANSACTION_OK.getCode() ){
					this.listRelUser = this.administrarAutorizacionEjb.consultarRelacionUserDesau(this.loginUserSel, this.credentials.getUsername());
					JSFUtil.addInfoMessage("formConsulUser:pgListRelUser", "usuario_desautorizado_correctamente");
				} else if( this.userResponseView != null && this.userResponseView.getResponseCode() == EResponse.EMAIL_NO_SEND.getCode() ){
					this.listRelUser = this.administrarAutorizacionEjb.consultarRelacionUserDesau(this.loginUserSel, this.credentials.getUsername());
					JSFUtil.addWarnMessage("formConsulUser:pgListRelUser", "respuesta", this.userResponseView.getMessage());
				} else {
					JSFUtil.addErrorMessage("formConsulUser:pgListRelUser", "error_desautorizar");
				}
			}
		} catch (Exception e) {
			logger.error("Error: ConsultarUsuarioWeb.desautorizarPrest  "+e.getMessage(), e);
			JSFUtil.addErrorMessage("formConsulUser:pgListRelUser", "mensaje_error_inesperado");
		}
	}
	
	public String getNombreRelacionFromId(int idRelacion){
		return ETipoRelacion.fromInt(idRelacion).toString();
	}
	
	public void cancelardesautorizarPrest(){
		this.relacionPresDesau = null;
	}
	
	public void consultaSucursalesPrestador(){
		SucursalesByPrestadorDTO sucursalesByPrestadorDTO = null;
		List<SucursalesByPrestadorDTO> listSucursales = null;
		this.sucursalSel = null;
		this.listaSucursales = null;
		try {
			if( this.datosPrestadorDTO != null ){
				sucursalesByPrestadorDTO = new SucursalesByPrestadorDTO();
				sucursalesByPrestadorDTO.setNumeroIDPrestador( this.datosPrestadorDTO.getNumeroID() );
				sucursalesByPrestadorDTO.setTipoIDPrestador( this.datosPrestadorDTO.getCodigoTipoIDNegocio() );
			
				listSucursales = this.administrarAutorizacionEjb.consultaSucursalesPrestador( sucursalesByPrestadorDTO );
					
				if( listSucursales != null && listSucursales.size() > 0 ){
					this.listaSucursales = new LinkedList<SelectItem>();
					this.listaSucursales.add(new SelectItem(this.datosPrestadorDTO.getNumeroID(), this.datosPrestadorDTO.getRazonSocial()));
					for( SucursalesByPrestadorDTO sucursal: listSucursales){
						this.listaSucursales.add(new SelectItem(sucursal.getCodigoPrestador(), sucursal.getSucursalPrestador()));
					}
					this.sucursalSel = this.datosPrestadorDTO.getNumeroID();
				} else {
					this.listaSucursales = new LinkedList<SelectItem>();
					this.listaSucursales.add(new SelectItem(this.datosPrestadorDTO.getNumeroID(), this.datosPrestadorDTO.getRazonSocial()));
					this.sucursalSel = this.datosPrestadorDTO.getNumeroID();
					
					
				}
			}
		} catch (Exception e) {
			logger.error("Error: ConsultarUsuarioWeb.consultaSucursalesPrestador  "+e.getMessage(), e);
			JSFUtil.addErrorMessage("includePnlAutorizarAdmin:formAutAdmin", "mensaje_error_inesperado");
		}
	}
	
	public void buscarPrestadorAzimut(){
		
		
		if(codigoPrestBusq != null && !codigoPrestBusq.equalsIgnoreCase("")){
		DatosPrestadorDTO datBusPrest = null;
		try {
			datBusPrest = new DatosPrestadorDTO();
			datBusPrest.setTipoID( this.tipoDocSel );
			datBusPrest.setNumeroID( this.codigoPrestBusq );
			
			this.datosPrestadorDTO = this.administrarAutorizacionEjb.consultarPrestadorBioSanitas( datBusPrest );
			
			if( this.datosPrestadorDTO != null ){
				if( this.datosPrestadorDTO.getEstado() != null && this.datosPrestadorDTO.getEstado().equals(Configurator.getInstance().getString("administracion", "estadoActPrestadorAzimut", "ACTIVO")) ){
					consultaSucursalesPrestador();
				} else if( this.datosPrestadorDTO.getEstado() != null && this.datosPrestadorDTO.getEstado().equals(Configurator.getInstance().getString("administracion", "estadoRetiradoPrestadorAzimut", "RETIRADO")) ) {
					limpiarDatos();
					JSFUtil.addWarnMessage("includePnlAutorizarAdmin:formAutAdmin", "PRESTADOR_RETIRADO");
					JSFUtil.addWarnMessage("includePnlAutorizarDele:formAutDeleFunOsi", "PRESTADOR_RETIRADO");
					} else {
					limpiarDatos();
					JSFUtil.addWarnMessage("includePnlAutorizarAdmin:formAutAdmin", "prestador_no_encontrado");
					JSFUtil.addWarnMessage("includePnlAutorizarDele:formAutDeleFunOsi", "prestador_no_encontrado");
				}
			} else {
				this.listaSucursales = null;
				JSFUtil.addWarnMessage("includePnlAutorizarAdmin:formAutAdmin", "prestador_no_encontrado");
				JSFUtil.addWarnMessage("includePnlAutorizarDele:formAutDeleFunOsi", "prestador_no_encontrado");
			}
		} catch ( LoginAdminException e ) {
			logger.error("Error: ConsultarUsuarioWeb.buscarPrestadorAzimut ", e);
			if( e.getCause() != null && e.getCause().getMessage().equalsIgnoreCase( Configurator.getInstance().getString("administracion", "msgExepcionPrestNotMig", null) ) ) {
				JSFUtil.addWarnMessage("includePnlAutorizarAdmin:formAutAdmin", "PRESTADOR_NOT_MIGRATED");
				JSFUtil.addWarnMessage("includePnlAutorizarDele:formAutDeleFunOsi", "PRESTADOR_NOT_MIGRATED");
			}
			limpiarDatos();
		} catch (Exception e) {
			logger.error("Error: ConsultarUsuarioWeb.buscarPrestadorAzimut ", e);
			JSFUtil.addErrorMessage("includePnlAutorizarAdmin:formAutAdmin", "mensaje_error_inesperado");
			JSFUtil.addWarnMessage("includePnlAutorizarDele:formAutDeleFunOsi", "mensaje_error_inesperado");
			limpiarDatos();
		}
		 codigoPrestBusq = "";
		}else
		{	limpiarDatos();
		    JSFUtil.addWarnMessage("includePnlAutorizarAdmin:formAutAdmin", "documento_requerido");
			JSFUtil.addWarnMessage("includePnlAutorizarDele:formAutDeleFunOsi", "documento_requerido");
	    }
		
	}
	
	public void inicializaValores(){
		this.listRelUser = null;
	}
	
	public void cargarGesAplicacionesAdmin( RelacionEntity relacionEntity ){
		this.relacionGesPrestApli = relacionEntity;
		this.administrarAplicacionesAdministradorWeb.listarAplicacionesUserRole( ETipoRelacion.ADMINISTRADOR.getId() );
	    
	}
	
	public void cargarGesAplicacionesDelegado( RelacionEntity relacionEntity ){
		this.relacionGesPrestApli = relacionEntity;
		this.administrarAplicacionesAdministradorWeb.listarAplicacionesUserRoleDele();
		
	}
	
	public void limpiarDatos()
	{
		this.listaSucursales = null;
	    this.datosPrestadorDTO = null;
	}
}

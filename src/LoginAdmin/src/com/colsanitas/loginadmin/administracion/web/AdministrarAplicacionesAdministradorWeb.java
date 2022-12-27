package com.colsanitas.loginadmin.administracion.web;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

import com.colsanitas.loginadmin.administracion.business.IAdministrarAplicacionRoledEjb;
import com.colsanitas.loginadmin.administracion.business.IAdministrarAutorizaciondEjb;
import com.colsanitas.loginadmin.administracion.business.IAdministrarPlantillaPrestadordEjb;
import com.colsanitas.loginadmin.administracion.business.IGestionUsuariosEjb;
import com.colsanitas.loginadmin.administracion.entity.PresRolTemplateEntity;
import com.colsanitas.loginadmin.administracion.entity.UserRoleEntity;
import com.colsanitas.loginadmin.administracion.enums.ETipoRelacion;
import com.colsanitas.loginadmin.administracion.utils.EAccionPlantillaRole;
import com.colsanitas.loginadmin.administracion.utils.EtipoRolLoginAdmin;
import com.osi.gaudi.cfg.Configurator;
import com.osi.gaudi.security.authorization.clientews.Domain;
import com.osi.gaudi.security.authorization.clientews.GroupRole;
import com.osi.gaudi.security.authorization.clientews.Role;
import com.osi.his.sistema.util.JSFUtil;

@Name("administrarAplicacionesAdministradorWeb")
@Scope(ScopeType.PAGE)
public class AdministrarAplicacionesAdministradorWeb {

	private static final long serialVersionUID = 6751558519780956697L;

	private static Logger logger = LoggerFactory.getLogger(AdministrarAplicacionesAdministradorWeb.class);

    @In Credentials credentials;
    @In StatusMessages statusMessages;
    
    private String login;
    
    private Long idDomain;
    private Long idGroupRol;
    private List<Long> listRolSelec; 
    
    private Role roleSel;
    
    private boolean checkAll;
    
    private List<SelectItem> listDomain;
    private List<SelectItem> listGroupRole;
    private List<Role> listRole;
    private List<UserRoleEntity> listApliRolUser;
    private List<UserRoleEntity> listElimApliRolUser;
    private List<PresRolTemplateEntity> listRolePlantPrest;
    
    private Map<Long, GroupRole> groupRoleMap;
    
    @In(create=true, required=false)
	ConsultarUsuarioWeb consultarUsuarioWeb;
    
	@In(create = true)
	IAdministrarAutorizaciondEjb administrarAutorizacionEjb;
	
	@In(create = true)
	IAdministrarPlantillaPrestadordEjb administrarPlantillaPrestadordEjb;
	
	@In(create = true)
	IAdministrarAplicacionRoledEjb administrarAplicacionRoleEjb;
	
	@In
  	private Identity identity;
	
	@In(create=true)
    private IGestionUsuariosEjb gestionUsuariosEjb;
	
	@Create
	@Begin(join=true)
	public void inicializador() {
		try {
			this.listApliRolUser = null;
			this.roleSel = new Role();
			if( identity.hasRole( Configurator.getInstance().getString("administracion", "permisoFuncionarioPlantillaRole","") ) ){
				this.listDomain = new LinkedList<SelectItem>();
				this.listGroupRole = new LinkedList<SelectItem>();
				cargarDominioUser();
				cargarGrupoRolDomain();
			}
			this.checkAll = false;
		} catch (Exception e) {
			logger.error("Error: AdministraPlantillaRolWeb.inicializador  "+e.getMessage(), e);
			JSFUtil.addErrorMessage("includePnlGesApliAdmin:formGesApliAdmin:pgMsgInfoAdminRol", "mensaje_error_inesperado");
		}
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	public List<Role> getListRole() {
		return listRole;
	}

	public void setListRole(List<Role> listRole) {
		this.listRole = listRole;
	}
	
	public Long getIdDomain() {
		return idDomain;
	}

	public void setIdDomain(Long idDomain) {
		this.idDomain = idDomain;
	}
	
	public Long getIdGroupRol() {
		return idGroupRol;
	}

	public void setIdGroupRol(Long idGroupRol) {
		this.idGroupRol = idGroupRol;
	}
	
	public List<SelectItem> getListDomain() {
		return listDomain;
	}

	public void setListDomain(List<SelectItem> listDomain) {
		this.listDomain = listDomain;
	}

	public List<SelectItem> getListGroupRole() {
		return listGroupRole;
	}

	public void setListGroupRole(List<SelectItem> listGroupRole) {
		this.listGroupRole = listGroupRole;
	}

	public Role getRoleSel() {
		return roleSel;
	}

	public void setRoleSel(Role roleSel) {
		this.roleSel = roleSel;
	}

	public List<Long> getListRolSelec() {
		return listRolSelec;
	}

	public void setListRolSelec(List<Long> listRolSelec) {
		this.listRolSelec = listRolSelec;
	}

	public List<UserRoleEntity> getlistApliRolUser() {
		return listApliRolUser;
	}

	public void setlistApliRolUser(List<UserRoleEntity> listApliRolUser) {
		this.listApliRolUser = listApliRolUser;
	}

	public boolean isCheckAll() {
		return checkAll;
	}

	public List<UserRoleEntity> getlistElimApliRolUser() {
		return listElimApliRolUser;
	}

	public void setlistElimApliRolUser(
			List<UserRoleEntity> listElimApliRolUser) {
		this.listElimApliRolUser = listElimApliRolUser;
	}

	public void setCheckAll(boolean checkAll) {
		this.checkAll = checkAll;
	}

	public List<PresRolTemplateEntity> getListRolePlantPrest() {
		return listRolePlantPrest;
	}

	public void setListRolePlantPrest(List<PresRolTemplateEntity> listRolePlantPrest) {
		this.listRolePlantPrest = listRolePlantPrest;
	}

	public void cargarDominioUser(){
		List<Domain> domainList = null;
		List<String> listDomainNoPermitidos = null;
		boolean isDomainPermitido = true;
		try {
			domainList = this.administrarPlantillaPrestadordEjb.cargarDominioUser(credentials.getUsername());
			if( domainList != null && !domainList.isEmpty() ) {
				listDomainNoPermitidos = Configurator.getInstance().getArray("administracion", "domainNoPermitidos", null);
				this.listDomain = new LinkedList<SelectItem>();
				for (Domain domain: domainList) {
					isDomainPermitido = true;
					listDomainNoPer:
					if( listDomainNoPermitidos != null && !listDomainNoPermitidos.isEmpty() ){
						for( String domainNoPermitido: listDomainNoPermitidos ) {
							if( domain.getShortName().equals( domainNoPermitido ) ){
								isDomainPermitido = false;
								break listDomainNoPer;
							}
						}
					} 
					if( isDomainPermitido ){
						this.listDomain.add(new SelectItem(domain.getId(), domain.getShortName()));
					}
				}
				this.idDomain = domainList.get(0).getId();
			} else {
				this.idDomain = null;
			}
		} catch (Exception e) {
			logger.error("Error: AdministraPlantillaRolWeb.cargarDominioUser  "+e.getMessage(), e);
			JSFUtil.addErrorMessage("formConsulUser:pgDataListUser", "mensaje_error_inesperado");
		} finally {
			domainList = null;
		}
	}
	
	public void cargarGrupoRolDomain(){
		List<GroupRole> groupList = null;
		try {
			this.roleSel = new Role();
			if( this.idDomain != null ){
				groupList = this.administrarPlantillaPrestadordEjb.cargarGrupoRolDomain( this.idDomain );
				if( groupList != null && !groupList.isEmpty() ){
					this.listGroupRole = new LinkedList<SelectItem>();
					this.groupRoleMap = new LinkedHashMap<Long, GroupRole>();
					for (GroupRole groupRol: groupList) {
						this.listGroupRole.add(new SelectItem(groupRol.getId(), groupRol.getName()));
						this.groupRoleMap.put(groupRol.getId(), groupRol);
					}
					this.idGroupRol = groupList.get(0).getId();
				} else {
					this.groupRoleMap = null;
					this.idGroupRol = null;
				}
			}
		} catch (Exception e) {
			this.groupRoleMap = null;
			logger.error("Error: AdministraPlantillaRolWeb.cargarDominioUser  "+e.getMessage(), e);
			JSFUtil.addErrorMessage("formConsulUser:pgDataListUser", "mensaje_error_inesperado");
		} finally {
			groupList = null;
		}
	}
	
	public void cargarRolesByDomainGrpRole(){
		GroupRole groupRoleSel = null;
		try {
			if( this.groupRoleMap != null && !this.groupRoleMap.isEmpty() ){
				groupRoleSel = this.groupRoleMap.get( this.idGroupRol );
			}
			
			if( groupRoleSel != null && groupRoleSel.getDomain() != null ) {
				this.listRole =  this.administrarPlantillaPrestadordEjb.cargarRolesByDomainGrpRole( groupRoleSel );
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			groupRoleSel = null;
		}
	}
	
	public void seleccionarRol( Role rolSel ) {
		if( rolSel != null ){
			this.roleSel = rolSel;
		} else {
			this.roleSel = new Role();
		}
	}
	
	public void listarAplicacionesUserRole( int tipoRel ) {
		UserRoleEntity userRoleEntityBus = null;
		try {
			logger.info("------    listarAplicacionesUserRole");
			cargarDominioUser();
			if( this.listDomain == null || ( this.listDomain != null && this.listDomain.isEmpty() ) ){
				this.listDomain = new LinkedList<SelectItem>();
				this.listGroupRole = new LinkedList<SelectItem>();
				if( identity.hasRole( Configurator.getInstance().getString("administracion", "permisoFuncionarioPlantillaRole","") ) ) {
					JSFUtil.addWarnMessage("includePnlGesApliAdmin:formGesApliAdmin:pgMsgInfoAdminRol", "admin_apli_user_not_apli_admin" );
				}
			} else {
				cargarGrupoRolDomain();
			}
			logger.info("Relacion:  " + this.consultarUsuarioWeb.getRelacionGesPrestApli());
			if( this.consultarUsuarioWeb.getRelacionGesPrestApli() != null ) {
				userRoleEntityBus = new UserRoleEntity();
				userRoleEntityBus.setUserId( this.consultarUsuarioWeb.getRelacionGesPrestApli().getUsuario().getUserId() );
				userRoleEntityBus.setPrestadorId( this.consultarUsuarioWeb.getRelacionGesPrestApli().getPrestador().getPrestadorId() );
				if( ETipoRelacion.ADMINISTRADOR.getId() == tipoRel ){
					userRoleEntityBus.setTipoRolLA( EtipoRolLoginAdmin.ADMINISTRADOR.getCode() );
				} else if( ETipoRelacion.DELEGADO.getId() == tipoRel ) {
					userRoleEntityBus.setTipoRolLA( EtipoRolLoginAdmin.DELEGADO.getCode() );
				}
				logger.info("UserId:  " + userRoleEntityBus.getUserId());
				logger.info("PrestadorId:  " + userRoleEntityBus.getPrestadorId());
				this.listApliRolUser = this.administrarAplicacionRoleEjb.listarApliRoleUser(userRoleEntityBus, this.checkAll);
			
				logger.info("listApliRolUser:  " + this.listApliRolUser);
			    
				/**
				 * Se habilita un campo boolean en la lista de AplicacionesRoles para 
				 * permitirle al usuario seleccionar solamente los dominios a los cuales puede
				 * acceder
				 * Modificado por Softmanagement
				 * Yency serrano
				 * 20/03/2013
				 */
				
				this.listApliRolUser = administrarAplicacionRoleEjb.consultarAdministradorDominio(credentials.getUsername(), listApliRolUser);
			} else {
				JSFUtil.addWarnMessage("includePnlGesApliAdmin:formGesApliAdmin:pgMsgInfoAdminRol", "admin_apli_admin_msg_sel_relacion");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error: AdministraPlantillaRolWeb.listarPlantillaRolePrest  "+e.getMessage(), e);
			JSFUtil.addErrorMessage("includePnlGesApliAdmin:formGesApliAdmin:pgMsgInfoAdminRol", "mensaje_error_inesperado");
		}
		
	}
	
	public void agregarRolPlantilla() {
		UserRoleEntity UserRoleEntity = null;
		int index = 0;
		try {
			if( this.roleSel != null && this.roleSel.getName() != null && this.roleSel.getDescription() != null ){
				if( this.listApliRolUser == null ){
					this.listApliRolUser = new LinkedList<UserRoleEntity>(); 
				}
				
				if( this.consultarUsuarioWeb.getRelacionGesPrestApli() != null ) {
					UserRoleEntity = new UserRoleEntity();
					UserRoleEntity.setUserId( this.consultarUsuarioWeb.getRelacionGesPrestApli().getUsuario().getUserId() );
					UserRoleEntity.setPrestadorId( this.consultarUsuarioWeb.getRelacionGesPrestApli().getPrestador().getPrestadorId() );
					UserRoleEntity.setRoleId( this.roleSel.getId() );
					UserRoleEntity.setRole( this.roleSel );
					UserRoleEntity.setAccion(EAccionPlantillaRole.ROLE_INSERT.getMessage());
					UserRoleEntity.setSelected( this.checkAll );
					
					index = listApliRolUser.indexOf( UserRoleEntity );
					if( index < 0 ) {
						this.listApliRolUser.add( UserRoleEntity );
					} else {
						JSFUtil.addWarnMessage("includePnlGesApliAdmin:formGesApliAdmin:pgMsgInfoAdminRol", "admin_apli_admin_msg_rol_exis");
					}
					
					if( this.listElimApliRolUser != null && !this.listElimApliRolUser.isEmpty() ){
						index = listElimApliRolUser.indexOf( UserRoleEntity );
						if( index >= 0 ) {
							listElimApliRolUser.remove( index );
						}
					}
				} else {
					JSFUtil.addWarnMessage("includePnlGesApliAdmin:formGesApliAdmin:pgMsgInfoAdminRol", "admin_apli_admin_msg_sel_relacion");
				}
			} else {
				JSFUtil.addWarnMessage("includePnlGesApliAdmin:formGesApliAdmin:pgMsgInfoAdminRol", "admin_apli_admin_msg_sel_role");
			}
		} catch (Exception e) {
			logger.error("Error: AdministraPlantillaRolWeb.cargarDominioUser  "+e.getMessage(), e);
			JSFUtil.addErrorMessage("includePnlGesApliAdmin:formGesApliAdmin:pgMsgInfoAdminRol", "mensaje_error_inesperado");
		}
		
		
		/**
		 * Se habilita un campo boolean en la lista de AplicacionesRoles para 
		 * permitirle al usuario seleccionar solamente los dominios a los cuales puede
		 * acceder
		 * Modificado por Softmanagement
		 * Yency serrano
		 * 23/03/2013
		 */
		
		this.listApliRolUser = administrarAplicacionRoleEjb.consultarAdministradorDominio(credentials.getUsername(), listApliRolUser);

	}
	
	public void eliminarRolesPlantilla() {
		UserRoleEntity rolTemplate = null;
		int numElim = 0;
		int index = 0;
		
		//Modificado por Softmanagement
		//Abril 10 de 2013
		if (this.checkAll)
		{
			seleccionarTodos();
			
		}
		
		
		
		try {
			if( this.listApliRolUser != null && !this.listApliRolUser.isEmpty() ) {
				if( this.listElimApliRolUser == null ){
					this.listElimApliRolUser = new LinkedList<UserRoleEntity>(); 
				}
				
				for( int i = 0; i < this.listApliRolUser.size(); i++ ){
					rolTemplate = ( UserRoleEntity ) this.listApliRolUser.get( i );
					if( rolTemplate.isSelected() ) {
						index = listElimApliRolUser.indexOf(rolTemplate);
						if(index < 0){
							rolTemplate.setAccion( EAccionPlantillaRole.ROLE_DELETE.getMessage() );
							this.listElimApliRolUser.add( rolTemplate );
						}
						numElim++;
					}
				}
				if( numElim > 0  ){
					this.listApliRolUser.removeAll(this.listElimApliRolUser);
				} else {
					JSFUtil.addWarnMessage("includePnlGesApliAdmin:formGesApliAdmin:pgMsgInfoAdminRol", "admin_apli_admin_msg_not_select_rol_elim");
				}
			} else {
				JSFUtil.addWarnMessage("includePnlGesApliAdmin:formGesApliAdmin:pgMsgInfoAdminRol", "admin_apli_admin_msg_not_exis_reg_roles_ges");
			}
			this.checkAll = false;
		} catch (Exception e) {
			logger.error("Error: AdministraPlantillaRolWeb.cargarDominioUser  "+e.getMessage(), e);
			JSFUtil.addErrorMessage("includePnlGesApliAdmin:formGesApliAdmin:pgMsgInfoAdminRol", "mensaje_error_inesperado");
		}
	}
	
	
	public void guardarAplicacionesRoles() {
		UserRoleEntity userRoleEntityBus = null;
		UserRoleEntity userRoleEntityAdd = null;
		String accion = "";
		try {
			
			if( identity.hasRole( Configurator.getInstance().getString("administracion", "permisoFuncionarioPlantillaRole","") ) ){
				if( ( this.listApliRolUser != null && !this.listApliRolUser.isEmpty() ) || ( this.listElimApliRolUser != null && !this.listElimApliRolUser.isEmpty() ) ){
					if( this.listApliRolUser == null ) {
						this.listApliRolUser = new LinkedList<UserRoleEntity>(); 
					}
					if( this.listElimApliRolUser != null && !this.listElimApliRolUser.isEmpty() ){
						this.listApliRolUser.addAll( listElimApliRolUser );
					}
					
					this.administrarAplicacionRoleEjb.registrarAplicacionRoleUser( this.listApliRolUser );
					this.listElimApliRolUser = null;
									
					userRoleEntityBus = new UserRoleEntity();
					userRoleEntityBus.setUserId( this.consultarUsuarioWeb.getRelacionGesPrestApli().getUsuario().getUserId() );
					userRoleEntityBus.setPrestadorId( this.consultarUsuarioWeb.getRelacionGesPrestApli().getPrestador().getPrestadorId() );
					this.listApliRolUser = this.administrarAplicacionRoleEjb.listarApliRoleUser(userRoleEntityBus, this.checkAll);
					JSFUtil.addInfoMessage("includePnlGesApliAdmin:formGesApliAdmin:pgMsgInfoAdminRol", "admin_apli_admin_msg_reg_role_user");
				} else {
					JSFUtil.addWarnMessage("includePnlGesApliAdmin:formGesApliAdmin:pgMsgInfoAdminRol", "admin_apli_admin_msg_not_exis_reg_roles_ges");
				}
			} else {
				if( this.listRolePlantPrest != null && !this.listRolePlantPrest.isEmpty() ) {
					this.listApliRolUser = new LinkedList<UserRoleEntity>();
					for( PresRolTemplateEntity plantPresAux: this.listRolePlantPrest ){
						if( plantPresAux.isSelected() || plantPresAux.getAccion().equals( EAccionPlantillaRole.ROLE_PERSISTUSER.getMessage() ) ){
							accion = plantPresAux.isSelected() && !plantPresAux.getAccion().equals( EAccionPlantillaRole.ROLE_PERSISTUSER.getMessage() ) ? EAccionPlantillaRole.ROLE_INSERT.getMessage() : ( !plantPresAux.isSelected() && plantPresAux.getAccion().equals( EAccionPlantillaRole.ROLE_PERSISTUSER.getMessage() ) ) ? EAccionPlantillaRole.ROLE_DELETE.getMessage() : EAccionPlantillaRole.ROLE_PERSIST.getMessage();
							userRoleEntityAdd = new UserRoleEntity();
							userRoleEntityAdd.setUserId( this.consultarUsuarioWeb.getRelacionGesPrestApli().getUsuario().getUserId() );
							userRoleEntityAdd.setPrestadorId( this.consultarUsuarioWeb.getRelacionGesPrestApli().getPrestador().getPrestadorId() );
							userRoleEntityAdd.setRoleId( plantPresAux.getRoleId() );
							userRoleEntityAdd.setRole( plantPresAux.getRole() );
							userRoleEntityAdd.setAccion( accion );
							this.listApliRolUser.add( userRoleEntityAdd );
						}
					}
					
					this.administrarAplicacionRoleEjb.registrarAplicacionRoleUser( this.listApliRolUser );
					this.listElimApliRolUser = null;
					
					userRoleEntityBus = new UserRoleEntity();
					userRoleEntityBus.setUserId( this.consultarUsuarioWeb.getRelacionGesPrestApli().getUsuario().getUserId() );
					userRoleEntityBus.setPrestadorId( this.consultarUsuarioWeb.getRelacionGesPrestApli().getPrestador().getPrestadorId() );
					this.listApliRolUser = this.administrarAplicacionRoleEjb.listarApliRoleUser(userRoleEntityBus, this.checkAll);
					marcarRoleAsignadosUser();
					JSFUtil.addInfoMessage("includePnlGesApliDele:formGesApliDele:pgMsgInfoAdminRolDele", "admin_apli_admin_msg_reg_role_user");
				} else {
					JSFUtil.addInfoMessage("includePnlGesApliDele:formGesApliDele:pgMsgInfoAdminRolDele", "admin_apli_admin_msg_not_exis_reg_roles_ges");
				}
			}
			
		} catch (Exception e) {
			logger.error("Error: AdministraPlantillaRolWeb.listarPlantillaRolePrest  "+e.getMessage(), e);
			if( identity.hasRole( Configurator.getInstance().getString("administracion", "permisoFuncionarioPlantillaRole","") ) ){
				JSFUtil.addErrorMessage("includePnlGesApliAdmin:formGesApliAdmin:pgMsgInfoAdminRol", "mensaje_error_inesperado");
			} else {
				JSFUtil.addInfoMessage("includePnlGesApliDele:formGesApliDele:pgMsgInfoAdminRolDele", "mensaje_error_inesperado");
			}
		} finally {
			userRoleEntityBus = null;
			userRoleEntityAdd = null;
		}
	}
	
	public void cancelarProceso() {
		try {
			this.listApliRolUser = null;
			this.roleSel = new Role();
			this.listDomain = new LinkedList<SelectItem>();
			this.listGroupRole = new LinkedList<SelectItem>();
			this.checkAll = false;
		} catch (Exception e) {
			logger.error("Error: AdministraPlantillaRolWeb.listarPlantillaRolePrest  "+e.getMessage(), e);
			JSFUtil.addErrorMessage("includePnlGesApliAdmin:formGesApliAdmin:pgMsgInfoAdminRol", "mensaje_error_inesperado");
		}
	}
	
	public void seleccionarTodos(){
		if( this.listApliRolUser != null && !this.listApliRolUser.isEmpty() ){
			for( UserRoleEntity pr: this.listApliRolUser  ){
				/**
				 * Modificado por Softmanagement
				 * Yency Serrano
				 * 21/03/2013
				*/
				if(pr.isAdministradorDominio()){
				pr.setSelected( this.checkAll );
				}
				else{
					pr.setSelected(false );
				}
			}
		}
	}
	
	public void seleccionarTodosLisDele(){
		
		if( this.listRolePlantPrest != null && !this.listRolePlantPrest.isEmpty() ){
			for( PresRolTemplateEntity presRoleTempl: this.listRolePlantPrest  ){
				/**
				 * Modificado por Softmanagement
				 * Yency Serrano
				 * 09/04/2013
				*/
				if(presRoleTempl.isAdministradorDominio()){
			        presRoleTempl.setSelected( this.checkAll );
				}
				else{			
					presRoleTempl.setSelected(false);
				}
				
				boolean administradorFunOsi = gestionUsuariosEjb.tieneRolFuncionarioOsi(credentials.getUsername() ,Configurator.getInstance().getString("administracion", "USER_LOGIN_ADMIN",null));
				if(!administradorFunOsi){
					 presRoleTempl.setSelected( this.checkAll );
				}
				
			}
		}
		
		
			
	}
	
	public void listarAplicacionesUserRoleDele(){
		try {
			if( this.consultarUsuarioWeb.getRelacionGesPrestApli() != null ) {
				this.listRolePlantPrest = this.administrarPlantillaPrestadordEjb.listarPlantillaPrestador( this.consultarUsuarioWeb.getRelacionGesPrestApli().getPrestador(), true, this.checkAll );
				if( this.listRolePlantPrest != null && !this.listRolePlantPrest.isEmpty() ){
					marcarRoleAsignadosUser();
					
					
					
					/**.
					 * Modificado por Softmanagement
					 * Yency Serrano
					 * 09/04/2013
					 * */
					this.administrarPlantillaPrestadordEjb.consultarAdministradorDominio(
							administrarPlantillaPrestadordEjb.cargarDominioUser(credentials.getUsername()), listRolePlantPrest);
					
					
					if( this.listRolePlantPrest != null && !this.listRolePlantPrest.isEmpty() ){
						for( PresRolTemplateEntity presRoleTempl: this.listRolePlantPrest  ){
							/**
							 * Modificado por Softmanagement
							 * Yency Serrano
							 * 09/04/2013
							*/
							
							
							boolean administradorFunOsi = gestionUsuariosEjb.tieneRolFuncionarioOsi(credentials.getUsername() ,Configurator.getInstance().getString("administracion", "USER_LOGIN_ADMIN",null));
							if(!administradorFunOsi){
								
								 presRoleTempl.setAdministradorDominio(true );
							}
							
						}
					}
					
				
					
					
					
					
					
				} else {
					JSFUtil.addWarnMessage("includePnlGesApliDele:formGesApliDele:pgMsgInfoAdminRolDele", "admin_apli_user_not_ges_plant_role_prest");
				}
			} else {
				JSFUtil.addWarnMessage("includePnlGesApliDele:formGesApliDele:pgMsgInfoAdminRolDele", "admin_apli_admin_msg_sel_relacion");
			}
		} catch (Exception e) {
			logger.error("Error: AdministraPlantillaRolWeb.listarPlantillaRolePrest  "+e.getMessage(), e);
			JSFUtil.addErrorMessage("includePnlGesApliDele:formGesApliDele:pgMsgInfoAdminRolDele", "mensaje_error_inesperado");
		}
	}
	
	public void marcarRoleAsignadosUser(){
		try {
			if( this.listRolePlantPrest != null && !this.listRolePlantPrest.isEmpty() ) {
				listarAplicacionesUserRole( ETipoRelacion.DELEGADO.getId() );
				if( this.listApliRolUser != null && !this.listApliRolUser.isEmpty() ) {
					for( PresRolTemplateEntity prestRoleTemp: this.listRolePlantPrest ) {
						listUserRolePres:
						for( UserRoleEntity userRolePres: this.listApliRolUser ){
							if( prestRoleTemp.getPrestadorId().equals( userRolePres.getPrestadorId() ) 
									&& prestRoleTemp.getRoleId().equals( userRolePres.getRoleId() ) ) {
								prestRoleTemp.setSelected(true);
								prestRoleTemp.setAccion( EAccionPlantillaRole.ROLE_PERSISTUSER.getMessage() );
								break listUserRolePres;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error: AdministraPlantillaRolWeb.listarPlantillaRolePrest  "+e.getMessage(), e);
			JSFUtil.addErrorMessage("includePnlGesApliDele:formGesApliDele:pgMsgInfoAdminRolDele", "mensaje_error_inesperado");
		}
	}
	
	public void guardarAplicacionesRoles( String opcion ) {
		UserRoleEntity userRoleEntityBus = null;
		UserRoleEntity userRoleEntityAdd = null;
		String accion = "";
		try {
			if( opcion != null && opcion.equals("ADMINISTRADOR") ) {
				if( ( this.listApliRolUser != null && !this.listApliRolUser.isEmpty() ) || ( this.listElimApliRolUser != null && !this.listElimApliRolUser.isEmpty() ) ){
					if( this.listApliRolUser == null ) {
						this.listApliRolUser = new LinkedList<UserRoleEntity>(); 
					}
					if( this.listElimApliRolUser != null && !this.listElimApliRolUser.isEmpty() ){
						this.listApliRolUser.addAll( listElimApliRolUser );
					}
					
					this.administrarAplicacionRoleEjb.registrarAplicacionRoleUser( this.listApliRolUser, EtipoRolLoginAdmin.ADMINISTRADOR.getCode() );
					this.listElimApliRolUser = null;
									
					userRoleEntityBus = new UserRoleEntity();
					userRoleEntityBus.setUserId( this.consultarUsuarioWeb.getRelacionGesPrestApli().getUsuario().getUserId() );
					userRoleEntityBus.setPrestadorId( this.consultarUsuarioWeb.getRelacionGesPrestApli().getPrestador().getPrestadorId() );
					this.listApliRolUser = this.administrarAplicacionRoleEjb.listarApliRoleUser(userRoleEntityBus, this.checkAll);
					
					this.listApliRolUser = administrarAplicacionRoleEjb.consultarAdministradorDominio( credentials.getUsername()  ,listApliRolUser);
					
					JSFUtil.addInfoMessage("includePnlGesApliAdmin:formGesApliAdmin:pgMsgInfoAdminRol", "admin_apli_admin_msg_reg_role_user");
				} else {
					JSFUtil.addWarnMessage("includePnlGesApliAdmin:formGesApliAdmin:pgMsgInfoAdminRol", "admin_apli_admin_msg_not_exis_reg_roles_ges");
				}
			} else if( opcion != null && opcion.equals("DELEGADO") ) {
				if( this.listRolePlantPrest != null && !this.listRolePlantPrest.isEmpty() ) {
					this.listApliRolUser = new LinkedList<UserRoleEntity>();
					for( PresRolTemplateEntity plantPresAux: this.listRolePlantPrest ){
						
						
						 logger.error("dominio "+plantPresAux.getRole().getDomain().getName() +" Es admin" +plantPresAux.getAdministradorDominio() +" esta selecionado="+plantPresAux.isSelected());
						 
						if( plantPresAux.isSelected() || plantPresAux.getAccion().equals( EAccionPlantillaRole.ROLE_PERSISTUSER.getMessage() ) ){
						  
						    logger.error("Esta seleccionado "+plantPresAux.getRole().getDomain().getName() );
							
							accion = plantPresAux.isSelected() && !plantPresAux.getAccion().equals( EAccionPlantillaRole.ROLE_PERSISTUSER.getMessage() ) ? EAccionPlantillaRole.ROLE_INSERT.getMessage() : ( !plantPresAux.isSelected() && plantPresAux.getAccion().equals( EAccionPlantillaRole.ROLE_PERSISTUSER.getMessage() ) ) ? EAccionPlantillaRole.ROLE_DELETE.getMessage() : EAccionPlantillaRole.ROLE_PERSIST.getMessage();
							userRoleEntityAdd = new UserRoleEntity();
							userRoleEntityAdd.setUserId( this.consultarUsuarioWeb.getRelacionGesPrestApli().getUsuario().getUserId() );
							userRoleEntityAdd.setPrestadorId( this.consultarUsuarioWeb.getRelacionGesPrestApli().getPrestador().getPrestadorId() );
							userRoleEntityAdd.setRoleId( plantPresAux.getRoleId() );
							userRoleEntityAdd.setRole( plantPresAux.getRole() );
							userRoleEntityAdd.setAccion( accion );
							this.listApliRolUser.add( userRoleEntityAdd );
						}
					}
					
					this.administrarAplicacionRoleEjb.registrarAplicacionRoleUser( this.listApliRolUser, EtipoRolLoginAdmin.DELEGADO.getCode() );
					this.listElimApliRolUser = null;
					
					userRoleEntityBus = new UserRoleEntity();
					userRoleEntityBus.setUserId( this.consultarUsuarioWeb.getRelacionGesPrestApli().getUsuario().getUserId() );
					userRoleEntityBus.setPrestadorId( this.consultarUsuarioWeb.getRelacionGesPrestApli().getPrestador().getPrestadorId() );
					this.listApliRolUser = this.administrarAplicacionRoleEjb.listarApliRoleUser(userRoleEntityBus, this.checkAll);
					marcarRoleAsignadosUser();
					JSFUtil.addInfoMessage("includePnlGesApliDele:formGesApliDele:pgMsgInfoAdminRolDele", "admin_apli_admin_msg_reg_role_user");
				} else {
					JSFUtil.addInfoMessage("includePnlGesApliDele:formGesApliDele:pgMsgInfoAdminRolDele", "admin_apli_admin_msg_not_exis_reg_roles_ges");
				}
			}
			
		} catch (Exception e) {
			logger.error("Error: AdministraPlantillaRolWeb.listarPlantillaRolePrest  "+e.getMessage(), e);
			if( identity.hasRole( Configurator.getInstance().getString("administracion", "permisoFuncionarioPlantillaRole","") ) ){
				JSFUtil.addErrorMessage("includePnlGesApliAdmin:formGesApliAdmin:pgMsgInfoAdminRol", "mensaje_error_inesperado");
			} else {
				JSFUtil.addInfoMessage("includePnlGesApliDele:formGesApliDele:pgMsgInfoAdminRolDele", "mensaje_error_inesperado");
			}
		} finally {
			userRoleEntityBus = null;
			userRoleEntityAdd = null;
		}
	}
}

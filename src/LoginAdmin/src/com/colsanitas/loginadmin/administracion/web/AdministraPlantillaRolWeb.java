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

import com.colsanitas.loginadmin.administracion.business.IAdministrarAutorizaciondEjb;
import com.colsanitas.loginadmin.administracion.business.IAdministrarPlantillaPrestadordEjb;
import com.colsanitas.loginadmin.administracion.entity.PresRolTemplateEntity;
import com.colsanitas.loginadmin.administracion.entity.PrestadorEntity;
import com.colsanitas.loginadmin.administracion.utils.EAccionPlantillaRole;
import com.colsanitas.loginadmin.exception.LoginAdminException;
import com.osi.gaudi.cfg.Configurator;
import com.osi.gaudi.security.authorization.clientews.Domain;
import com.osi.gaudi.security.authorization.clientews.GroupRole;
import com.osi.gaudi.security.authorization.clientews.Role;
import com.osi.his.sistema.util.JSFUtil;
import com.osi.his.sistema.util.finder.AbstractFinder;

@Name("administraPlantillaRolWeb")
@Scope(ScopeType.PAGE)
public class AdministraPlantillaRolWeb {

	private static final long serialVersionUID = 6751558519780956697L;

	private static Logger logger = LoggerFactory.getLogger(AdministraPlantillaRolWeb.class);

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
    private List<PresRolTemplateEntity> listRolePlantPrest;
    private List<PresRolTemplateEntity> listElimRolePlantPrest;
    
    private Map<Long, GroupRole> groupRoleMap;
    
	@In(create = true, value = "prestadorLocalFinder")
	private AbstractFinder<PrestadorEntity> prestadorLocalFinder;
    
	@In(create = true)
	IAdministrarAutorizaciondEjb administrarAutorizacionEjb;
	
	@In(create = true)
	IAdministrarPlantillaPrestadordEjb administrarPlantillaPrestadordEjb;
	
	@In
	private Identity identity;
	
	private PrestadorEntity prestadorLocalSelected = new PrestadorEntity();
	
	@Create
	@Begin(join=true)
	public void inicializador() {
		try {
	          
			if( identity.hasRole( Configurator.getInstance().getString("administracion", "permisoFuncionarioPlantillaRole","") ) ){
				this.listRolePlantPrest = null;
				this.roleSel = new Role();
				this.listDomain = new LinkedList<SelectItem>();
				this.listGroupRole = new LinkedList<SelectItem>();
				cargarDominioUser();
				if( this.listDomain == null || ( this.listDomain != null && this.listDomain.isEmpty() ) ){
					JSFUtil.addWarnMessage("formAdminPlant:pgMsgInfoAdminRol", "admin_apli_user_not_apli_admin" );
				} else {
					cargarGrupoRolDomain();
					if( this.prestadorLocalSelected == null || 
							( this.prestadorLocalSelected != null && ((this.prestadorLocalSelected.getSucursal() == null || this.prestadorLocalSelected.getSucursal().isEmpty()) 
										|| (this.prestadorLocalSelected.getRazonSocial() == null || this.prestadorLocalSelected.getRazonSocial().isEmpty()) ) ) ){
						JSFUtil.addWarnMessage("formAdminPlant:pgMsgInfoAdminRol", "plantilla_roles_msg_sel_prest_gest_rol");
					}
				}
						}
		} catch (Exception e) {
			logger.error("Error: AdministraPlantillaRolWeb.inicializador  "+e.getMessage(), e);
			JSFUtil.addErrorMessage("formAdminPlant:pgMsgInfoAdminRol", "mensaje_error_inesperado");
		}
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
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
	
	public AbstractFinder<PrestadorEntity> getPrestadorLocalFinder() {
		return prestadorLocalFinder;
	}

	public void setPrestadorLocalFinder( AbstractFinder<PrestadorEntity> prestadorLocalFinder ) {
		this.prestadorLocalFinder = prestadorLocalFinder;
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

	public List<PresRolTemplateEntity> getListRolePlantPrest() {
		return listRolePlantPrest;
	}

	public void setListRolePlantPrest(List<PresRolTemplateEntity> listRolePlantPrest) {
		this.listRolePlantPrest = listRolePlantPrest;
	}

	public boolean isCheckAll() {
		return checkAll;
	}

	public List<PresRolTemplateEntity> getListElimRolePlantPrest() {
		return listElimRolePlantPrest;
	}

	public void setListElimRolePlantPrest(
			List<PresRolTemplateEntity> listElimRolePlantPrest) {
		this.listElimRolePlantPrest = listElimRolePlantPrest;
	}

	public void setCheckAll(boolean checkAll) {
		this.checkAll = checkAll;
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
			if( this.prestadorLocalSelected != null && this.prestadorLocalSelected.getPrestadorId() != null ){
				if( this.groupRoleMap != null && !this.groupRoleMap.isEmpty() ){
					groupRoleSel = this.groupRoleMap.get( this.idGroupRol );
				}
				
				if( groupRoleSel != null && groupRoleSel.getDomain() != null ) {
					this.listRole =  this.administrarPlantillaPrestadordEjb.cargarRolesByDomainGrpRole( groupRoleSel );
				}
			} else {
				JSFUtil.addWarnMessage("formAdminPlant:pgMsgInfoAdminRol", "plantilla_roles_msg_sel_prest_gest_rol");
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
	
	public void agregarRolPlantilla() {
		PresRolTemplateEntity presRolTemplateEntity = null;
		int index = 0;
		try {
			if( this.roleSel != null && this.roleSel.getName() != null && this.roleSel.getDescription() != null ){
				if( this.listRolePlantPrest == null ){
					this.listRolePlantPrest = new LinkedList<PresRolTemplateEntity>(); 
				}
				if(this.roleSel != null && this.listRolePlantPrest != null ) {
					presRolTemplateEntity = new PresRolTemplateEntity();
					presRolTemplateEntity.setPrestadorId( this.prestadorLocalSelected.getPrestadorId() );
					presRolTemplateEntity.setRoleId( this.roleSel.getId() );
					presRolTemplateEntity.setRole( this.roleSel );
					presRolTemplateEntity.setAccion(EAccionPlantillaRole.ROLE_INSERT.getMessage());
					presRolTemplateEntity.setEstado(true);
					presRolTemplateEntity.setSelected( this.checkAll );
					if( this.prestadorLocalSelected != null && this.prestadorLocalSelected.getPrestadorId() != null ){
						index = listRolePlantPrest.indexOf( presRolTemplateEntity );
						if( index < 0 ) {
							this.listRolePlantPrest.add( presRolTemplateEntity );
						} else {
							JSFUtil.addWarnMessage("formAdminPlant:pgMsgInfoAdminRol", "plantilla_roles_msg_rol_exis_plan");
						}
						
						if( this.listElimRolePlantPrest != null && !this.listElimRolePlantPrest.isEmpty() ){
							index = listElimRolePlantPrest.indexOf( presRolTemplateEntity );
							if( index >= 0 ) {
								listElimRolePlantPrest.remove( index );
							}
						}
					} else {
						JSFUtil.addWarnMessage("formAdminPlant:pgMsgInfoAdminRol", "plantilla_roles_msg_sel_prest_gest_rol");
					}
				}
			} else {
				JSFUtil.addWarnMessage("formAdminPlant:pgMsgInfoAdminRol", "plantilla_roles_msg_sel_role");
			}
		} catch (Exception e) {
			logger.error("Error: AdministraPlantillaRolWeb.cargarDominioUser  "+e.getMessage(), e);
			JSFUtil.addErrorMessage("formAdminPlant:pgMsgInfoAdminRol", "mensaje_error_inesperado");
		}
		
		/**
		 * Modificado por Softmangement 
		 * Yency Serrano
		 * 20/03/2013
		 * Deshabilitar los dominios a los q no este habilitado el usuario q esta logueado
		 */
		List<Domain> domainList = null;
		
	
			try {
				domainList = this.administrarPlantillaPrestadordEjb.cargarDominioUser(credentials.getUsername());
			} catch (LoginAdminException e) {
				// TODO Auto-generated catch block
				logger.error("Error: AdministraPlantillaRolWeb.cargarDominioUser  "+e.getMessage(), e);
				e.printStackTrace();
			}
				
			if( domainList != null && !domainList.isEmpty() ) {
				administrarPlantillaPrestadordEjb.consultarAdministradorDominio(domainList ,listRolePlantPrest);
			}
	    /**fin softmanagement*/	
	}
	
	public void eliminarRolesPlantilla() {
		PresRolTemplateEntity rolTemplate = null;
		int numElim = 0;
		int index = 0;
		//Modificado por Softmanagement
		//Abril 10 de 2013
		if(this.checkAll){
			seleccionarTodos();
		}
		
		try {
			if( this.prestadorLocalSelected != null && this.prestadorLocalSelected.getPrestadorId() != null ){
				if( this.listRolePlantPrest != null && !this.listRolePlantPrest.isEmpty() ) {
					if( this.listElimRolePlantPrest == null ){
						this.listElimRolePlantPrest = new LinkedList<PresRolTemplateEntity>(); 
					}
					
					for( int i = 0; i < this.listRolePlantPrest.size(); i++ ){
						rolTemplate = ( PresRolTemplateEntity ) this.listRolePlantPrest.get( i );
						if( rolTemplate.isSelected() ) {
							index = listElimRolePlantPrest.indexOf(rolTemplate);
							if(index < 0){
								rolTemplate.setAccion( EAccionPlantillaRole.ROLE_DELETE.getMessage() );
								rolTemplate.setEstado( false );
								this.listElimRolePlantPrest.add( rolTemplate );
							}
							numElim++;
						}
					}
					if( numElim > 0  ){
						this.listRolePlantPrest.removeAll(this.listElimRolePlantPrest);
						System.out.println("Eliminar="+this.listElimRolePlantPrest.size());
					} else {
						JSFUtil.addWarnMessage("formAdminPlant:pgMsgInfoAdminRol", "plantilla_roles_msg_not_select_rol_elim");
					}
				} else {
					JSFUtil.addWarnMessage("formAdminPlant:pgMsgInfoAdminRol", "plantilla_roles_msg_not_exis_reg_roles_ges_plant");
				}
			} else  {
				JSFUtil.addWarnMessage("formAdminPlant:pgMsgInfoAdminRol", "plantilla_roles_msg_sel_prest_gest_rol");
			}
			this.checkAll = false;
		} catch (Exception e) {
			logger.error("Error: AdministraPlantillaRolWeb.cargarDominioUser  "+e.getMessage(), e);
			JSFUtil.addErrorMessage("formAdminPlant:pgMsgInfoAdminRol", "mensaje_error_inesperado");
		}
	}
	
	public void listarPlantillaRolePrest() {
		try {
			if( this.prestadorLocalSelected == null || 
					( this.prestadorLocalSelected != null && ((this.prestadorLocalSelected.getSucursal() == null || this.prestadorLocalSelected.getSucursal().isEmpty()) 
								|| (this.prestadorLocalSelected.getRazonSocial() == null || this.prestadorLocalSelected.getRazonSocial().isEmpty()) ) ) ){
				JSFUtil.addWarnMessage("formAdminPlant:pgMsgInfoAdminRol", "plantilla_roles_msg_sel_prest_gest_rol");
			} else {
				this.listRolePlantPrest = this.administrarPlantillaPrestadordEjb.listarPlantillaPrestador( this.prestadorLocalSelected, true, this.checkAll );
				
				
				/**
				 * Modificado por Softmangement 
				 * Yency Serrano
				 * 20/03/2013
				 * Deshabilitar los dominios a los q no este habilitado el usuario q esta logueado
				 */
				List<Domain> domainList = null;
				
			
					domainList = this.administrarPlantillaPrestadordEjb.cargarDominioUser(credentials.getUsername());
						
					if( domainList != null && !domainList.isEmpty() ) {
						administrarPlantillaPrestadordEjb.consultarAdministradorDominio(domainList ,listRolePlantPrest);
					}
			    /**fin softmanagement*/		
				
				if( this.listRolePlantPrest == null || this.listRolePlantPrest.isEmpty() ){
					JSFUtil.addWarnMessage("formAdminPlant:pgMsgInfoAdminRol", "plantilla_roles_msg_prest_sel_not_plant");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error: AdministraPlantillaRolWeb.listarPlantillaRolePrest  "+e.getMessage(), e);
			JSFUtil.addErrorMessage("formAdminPlant:pgMsgInfoAdminRol", "mensaje_error_inesperado");
		}
		
	}
	
	public void guardarPlantillaRoles() {
		try {
			if( ( this.listRolePlantPrest != null && !this.listRolePlantPrest.isEmpty() ) || ( this.listElimRolePlantPrest != null && !this.listElimRolePlantPrest.isEmpty() ) ){
				if( this.listRolePlantPrest == null ) {
					this.listRolePlantPrest = new LinkedList<PresRolTemplateEntity>(); 
				}
				if( this.listElimRolePlantPrest != null && !this.listElimRolePlantPrest.isEmpty() ){
					this.listRolePlantPrest.addAll( listElimRolePlantPrest );
				}
				
				this.administrarPlantillaPrestadordEjb.registrarPlantillaPrestador( this.listRolePlantPrest );
				this.listElimRolePlantPrest = null;
								
				this.listRolePlantPrest = this.administrarPlantillaPrestadordEjb.listarPlantillaPrestador( this.prestadorLocalSelected, true, this.checkAll );
				
				
				//Modificado por Softmanagement
				//Abril 10 de 2013
				List<Domain> domainList = null;
				
				
				try {
					domainList = this.administrarPlantillaPrestadordEjb.cargarDominioUser(credentials.getUsername());
				} catch (LoginAdminException e) {
					// TODO Auto-generated catch block
					logger.error("Error: AdministraPlantillaRolWeb.cargarDominioUser  "+e.getMessage(), e);
					e.printStackTrace();
				}
					
				if( domainList != null && !domainList.isEmpty() ) {
					administrarPlantillaPrestadordEjb.consultarAdministradorDominio(domainList ,listRolePlantPrest);
				}
				
				
				JSFUtil.addInfoMessage("formAdminPlant:pgMsgInfoAdminRol", "plantilla_roles_msg_reg_exitoso");
			} else {
				JSFUtil.addWarnMessage("formAdminPlant:pgMsgInfoAdminRol", "plantilla_roles_msg_not_exis_reg_roles_ges_plant");
			}
		} catch (Exception e) {
			logger.error("Error: AdministraPlantillaRolWeb.listarPlantillaRolePrest  "+e.getMessage(), e);
			JSFUtil.addErrorMessage("formAdminPlant:pgMsgInfoAdminRol", "mensaje_error_inesperado");
		}
	}
	
	public void cancelarProceso() {
		try {
			if( this.listDomain == null || ( this.listDomain != null && this.listDomain.isEmpty() ) ){
				JSFUtil.addWarnMessage("formAdminPlant:pgMsgInfoAdminRol", "admin_apli_user_not_apli_admin" );
			} else {
				this.roleSel = new Role();
				this.prestadorLocalSelected = new PrestadorEntity();
				this.listElimRolePlantPrest = null;
				this.listRolePlantPrest = null;
				this.checkAll = false;
				JSFUtil.addWarnMessage("formAdminPlant:pgMsgInfoAdminRol", "plantilla_roles_msg_sel_prest_gest_rol");
			}
		} catch (Exception e) {
			logger.error("Error: AdministraPlantillaRolWeb.listarPlantillaRolePrest  "+e.getMessage(), e);
			JSFUtil.addErrorMessage("formAdminPlant:pgMsgInfoAdminRol", "mensaje_error_inesperado");
		}
	}
	
	public void seleccionarTodos(){
		if( this.listRolePlantPrest != null && !this.listRolePlantPrest.isEmpty() ){
			for( PresRolTemplateEntity pr: this.listRolePlantPrest  ){
			    //Modificado por Softmanagement
				//Yency Serrano
				//20/03/2013
				
				if(pr.isAdministradorDominio()){
				  pr.setSelected( this.checkAll );
			  }
			}
		}
	}
}

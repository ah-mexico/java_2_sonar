package com.colsanitas.loginadmin.administracion.business;

import java.util.List;

import javax.ejb.Local;

import com.colsanitas.loginadmin.administracion.entity.PresRolTemplateEntity;
import com.colsanitas.loginadmin.administracion.entity.PrestadorEntity;
import com.colsanitas.loginadmin.exception.LoginAdminException;
import com.osi.gaudi.security.authorization.clientews.Domain;
import com.osi.gaudi.security.authorization.clientews.GroupRole;
import com.osi.gaudi.security.authorization.clientews.Role;


/**
 * Define  los metodos necesarios para la gestion de plantilla de roles utilizado por los administradores prestadores. 
 * @author yaaperez
 */
@Local
public interface IAdministrarPlantillaPrestadordEjb {
	public PresRolTemplateEntity consultarPlantillaPrestador( PresRolTemplateEntity presRolTemplateEntity ) throws Exception ;
	public PresRolTemplateEntity actualizarPlantillaPrestador( PresRolTemplateEntity presRolTemplateEntity, boolean flush ) throws LoginAdminException ;
	public List<Domain> cargarDominioUser( String userLogin ) throws LoginAdminException;
	public List<GroupRole> cargarGrupoRolDomain( Long idDomain ) throws LoginAdminException;
	public List<Role> cargarRolesByDomainGrpRole( GroupRole groupRole ) throws LoginAdminException;
	public List<PresRolTemplateEntity> listarPlantillaPrestador( PrestadorEntity prestadorEntity, boolean estado, boolean check ) throws Exception;
	public void registrarPlantillaPrestador( List<PresRolTemplateEntity> listRolTemp ) throws LoginAdminException;
    public boolean consultarAdministradorDominio(List<Domain> dominios, List<PresRolTemplateEntity> listRolePlantPrest);
}

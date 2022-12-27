package com.colsanitas.loginadmin.administracion.dao.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.colsanitas.loginadmin.administracion.entity.PresRolTemplateEntity;
import com.colsanitas.loginadmin.administracion.entity.PrestadorEntity;
import com.colsanitas.loginadmin.exception.LoginAdminException;

@Local
public interface IPresRolTemplateDao {
	public PresRolTemplateEntity consultarPlantillaPrestador( PresRolTemplateEntity presRolTemplateEntity ) throws Exception ;
	public List<PresRolTemplateEntity> listarPlantillaPrestador( PrestadorEntity prestadorEntity, boolean estado ) throws Exception;
	public void registrarPlantillaPrestador( PresRolTemplateEntity presRolTemplateEntity, boolean flush ) throws LoginAdminException ;
	public PresRolTemplateEntity actualizarPlantillaPrestador( PresRolTemplateEntity presRolTemplateEntity, boolean flush ) throws LoginAdminException ;
}

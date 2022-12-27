package com.colsanitas.loginadmin.administracion.dao.interfaces;

import javax.ejb.Local;

import com.colsanitas.loginadmin.administracion.entity.UserEntity;
@Local
public interface IAdministradorDao {

	public void registrarAdministrador(UserEntity userEntity);
	
}

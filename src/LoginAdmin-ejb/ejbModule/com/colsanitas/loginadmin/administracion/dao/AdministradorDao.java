package com.colsanitas.loginadmin.administracion.dao;


import javax.ejb.Local;
import javax.ejb.Stateless;

import org.jboss.seam.annotations.Name;

import com.colsanitas.loginadmin.administracion.dao.interfaces.IAdministradorDao;
import com.colsanitas.loginadmin.administracion.entity.UserEntity;


@Stateless
@Name("administradorDao")
@Local(IAdministradorDao.class)
public class AdministradorDao extends TemplateDao<UserEntity, Long> implements IAdministradorDao{

	
	public AdministradorDao(Class<UserEntity> entityClass) {
		super(entityClass);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 

	
	public void registrarAdministrador(UserEntity userEntity)
	{
		create(userEntity, true);
		
	}
	
	
	
	
}

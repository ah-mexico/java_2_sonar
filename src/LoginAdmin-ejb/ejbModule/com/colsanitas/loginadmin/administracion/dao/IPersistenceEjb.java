package com.colsanitas.loginadmin.administracion.dao;

import javax.ejb.Local;
import javax.persistence.EntityManager;


/**
 * @author pescobar
 *
 */
@Local
public interface IPersistenceEjb {

	/**
	 * @return EntityManager
	 */
	EntityManager getEm(); 
	
}

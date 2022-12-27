package com.colsanitas.loginadmin.administracion.dao;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.seam.annotations.Name;


/**
 * @author pescobar
 *
 */
@Stateless
@Name("persistenceEjb")
public class PersistenceEjb implements IPersistenceEjb, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6511552504892379638L;
	@PersistenceContext(unitName="LoginAdmin")
	private EntityManager em; 
	
	public EntityManager getEm() {
		return em;
	}
}

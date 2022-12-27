package com.colsanitas.loginadmin.administracion.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.annotations.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Define los métodos comunes de un DAO que utiliza el entityManager
 * @author armarquez
 *
 * @param <TEntity> Clase con el tipo de dato que se persiste. 
 * @param <TId> tipo de dato del id de la clase que se persiste.
 */

public class TemplateDao<TEntity, TId> implements Serializable{
	
	private static Logger logger = LoggerFactory.getLogger(TemplateDao.class);
	
	private static final long serialVersionUID = 5468809221325332602L;

    @In(create = true)
    private IPersistenceEjb persistenceEjb;

    private final Class<TEntity> entityClass;
    
    public TemplateDao(Class<TEntity> entityClass) {
        this.entityClass = entityClass;
    }

    protected EntityManager getEm() {
        return persistenceEjb.getEm();
    }

    public void create(TEntity entity, boolean flush) {
        persistenceEjb.getEm().persist(entity);
        if( flush ){
        	getEm().flush();
        }
        
    }

    public TEntity update(TEntity entity, boolean flush) {
    	TEntity entityRes = null;
    	try {
    		entityRes = persistenceEjb.getEm().merge(entity);
        	if( flush ){
        		getEm().flush();
        	}
		} catch (Exception e) {
			logger.error("Error: TemplateDao.update "+e.getMessage(), e);
		}
    	return entityRes;
    }

    public void delete(TEntity entity) {
        TEntity temp = persistenceEjb.getEm().merge(entity);
    	persistenceEjb.getEm().remove(temp);
    }

    public TEntity find(TId id) {
        return persistenceEjb.getEm().find(entityClass, id);
    }
    
    protected Query setReadOnly(Query query) {
    	return this.setReadOnly(query, true);
    }
    
    protected Query setReadOnly( Query query, boolean readOnly ) {
    	/**
    	 * Se modifica por que el read only estaba afectando el funcionamiento al no permitir modificar
    	 * los Entities
    	 */
    	return query;
    }
    
     /**
     *  Limita la consulta a devolver los resultados desde el registro indicado por la variable 
     *  "primero" y trae un nï¿½mero máximo de resultados.
     *  Si el número de resultados en un nï¿½mero menor que cero no realiza la paginaciï¿½n 
     * 
     * @param query consulta a limitar.
     * @param primero primer registro a a traer.
     * @param resultados mï¿½ximo nï¿½mero de resultados a traer.
     */
    public void limitar(Query query, int primero, int resultados){
        // 
        if (resultados > 0 && primero >= 0){
        	query.setFirstResult(primero);
        	query.setMaxResults(resultados);
        }
    }
    
}

/**
 * 
 */
package com.colsanitas.loginadmin.administracion.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.jboss.seam.annotations.Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.loginadmin.administracion.dao.interfaces.IPresRolTemplateDao;
import com.colsanitas.loginadmin.administracion.entity.PresRolTemplateEntity;
import com.colsanitas.loginadmin.administracion.entity.PrestadorEntity;
import com.colsanitas.loginadmin.exception.LoginAdminException;

/**
 * @author yaaperez
 *
 */
@Stateless
@Name("presRolTemplateDao")
@Local(IPresRolTemplateDao.class)
public class PresRolTemplateDao extends TemplateDao<PresRolTemplateEntity, String> implements IPresRolTemplateDao {

	private static final long serialVersionUID = 71376723544861763L;
	
	private static Logger logger = LoggerFactory.getLogger(PresRolTemplateDao.class);
	
	public PresRolTemplateDao(){
		super(PresRolTemplateEntity.class);
	}
	
	public PresRolTemplateDao(Class<PresRolTemplateEntity> entityClass) {
		super(entityClass);
	}

	public PresRolTemplateEntity consultarPlantillaPrestador( PresRolTemplateEntity presRolTemplateEntity ) throws Exception {
		PresRolTemplateEntity resPresRolTemplateEntity = null;
		Query query = null;
		try {
			query = getEm().createQuery("select prtm " 
					+ " from PresRolTemplateEntity prtm " 
					+ " where prtm.prestadorId = :prestadorId "
					+ " and prtm.roleId = :roleId");
			
			query.setParameter("prestadorId", presRolTemplateEntity.getPrestadorId());	
			query.setParameter("roleId", presRolTemplateEntity.getRoleId());	
			
			resPresRolTemplateEntity = (PresRolTemplateEntity) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			logger.error("Error: PresRolTemplateDao.consultarPlantillaPrestador "+e.getMessage(), e);
			throw e;
		}
		return resPresRolTemplateEntity;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<PresRolTemplateEntity> listarPlantillaPrestador( PrestadorEntity prestadorEntity, boolean estado ) throws Exception {
		List<PresRolTemplateEntity> listRolPlantilla = null;
		Query query = null;
		try {
			query = getEm().createQuery("select prtm " 
					+ " from PresRolTemplateEntity prtm " 
					+ " where prtm.prestadorId = :prestadorId" 
					+ " and prtm.estado = :estado " );
			
			query.setParameter("prestadorId", prestadorEntity.getPrestadorId());
			query.setParameter("estado", estado);
			
			listRolPlantilla = query.getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			logger.error("Error: PresRolTemplateDao.listarPlantillaPrestador "+e.getMessage(), e);
			throw e;
		}
		return listRolPlantilla;
	}
	
	public void registrarPlantillaPrestador( PresRolTemplateEntity presRolTemplateEntity, boolean flush )throws LoginAdminException {
		create(presRolTemplateEntity, flush);
	}

	public PresRolTemplateEntity actualizarPlantillaPrestador( PresRolTemplateEntity presRolTemplateEntity, boolean flush ) throws LoginAdminException {
		return update(presRolTemplateEntity, false);
		
	}

}

package com.colsanitas.loginadmin.relacion.business;

import static org.jboss.seam.ScopeType.STATELESS;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.loginadmin.administracion.dao.interfaces.IRelacionDao;
import com.colsanitas.loginadmin.administracion.entity.RelacionEntity;
import com.colsanitas.loginadmin.exception.LoginAdminException;

@Scope(STATELESS)
@Stateless
@Name("gestionRelaciondEjb")
@Local(IGestionRelaciondEjb.class)
public class GestionRelaciondEjb implements IGestionRelaciondEjb, Serializable {
	
	private static final long serialVersionUID = -7982002047904449625L;

	private static Logger logger = LoggerFactory.getLogger(GestionRelaciondEjb.class);
	
	@In(create = true)
	private IRelacionDao relacionDao;

	public List<RelacionEntity> consultarRelacionesDelUsuario(String userId, int tipoRelacion)throws LoginAdminException {
		List<RelacionEntity> listRelacion = null;
		try {
			userId = (userId != null ?  userId.toLowerCase() : userId);
			listRelacion = relacionDao.consultarRelacionesDelUsuario(userId, tipoRelacion);
		} catch (Exception e) {
			logger.error("Error: GestionRelaciondEjb.consultarRelacionesDelUsuario "+e.getMessage(), e);
			throw new LoginAdminException("Error en GestionRelaciondEjb.consultarRelacionesDelUsuario ", e);
		}
		return listRelacion;
	}

	public List<RelacionEntity> consultarRelacionesDelPrestador( String idSucursalPrest, int tipoRelacion) throws LoginAdminException {
		List<RelacionEntity> listRelacion = null;
		try {
			listRelacion = relacionDao.consultarRelacionesDelPrestador(idSucursalPrest, tipoRelacion);
		} catch (Exception e) {
			logger.error("Error: GestionRelaciondEjb.consultarRelacionesDelPrestador "+e.getMessage(), e);
			throw new LoginAdminException("Error en GestionRelaciondEjb.consultarRelacionesDelUsuario ", e);
		}
		return listRelacion;
	}
	
}

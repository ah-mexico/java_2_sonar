package com.colsanitas.loginadmin.relacion.business;

import java.util.List;

import javax.ejb.Local;

import com.colsanitas.loginadmin.administracion.entity.RelacionEntity;
import com.colsanitas.loginadmin.exception.LoginAdminException;

/**
 * Define  los metodos necesarios para gestion de prestadores.
 * 
 * @author yaaperez
 *
 */
@Local
public interface IGestionRelaciondEjb {
	public List<RelacionEntity> consultarRelacionesDelUsuario(String userId, int tipoRelacion) throws LoginAdminException;
	public List<RelacionEntity> consultarRelacionesDelPrestador(String idSucursalPrest, int tipoRelacion) throws LoginAdminException;
}

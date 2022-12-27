package com.colsanitas.loginadmin.integration.relaciones;

import java.util.List;

import javax.jws.WebService;

import org.jboss.seam.Seam;
import org.jboss.seam.annotations.In;
import org.jboss.seam.contexts.Lifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.loginadmin.administracion.entity.RelacionEntity;
import com.colsanitas.loginadmin.dto.PrestadorDTO;
import com.colsanitas.loginadmin.prestadores.business.IGestionPrestadordEjb;
import com.colsanitas.loginadmin.relacion.business.GestionRelaciondEjb;
import com.colsanitas.loginadmin.relacion.business.IGestionRelaciondEjb;
/***
 * Implementan los m�todos que conciernen la entidad Relaci�n
 * @author armarquez.
 *
 */
@WebService(endpointInterface = "com.colsanitas.loginadmin.integration.relaciones.IRelacion", serviceName="Relacion")
public class Relacion implements IRelacion {

	private static Logger logger = LoggerFactory.getLogger(GestionRelaciondEjb.class);

	private IGestionRelaciondEjb gestionRelaciondEjb;

	@In(create = true)
	private IGestionPrestadordEjb gestionPrestadordEjb;
	public Relacion(){}

	public List<RelacionEntity> consultarRelacionesDelUsuario(String userId, int idTipoRelacion) {
		List<RelacionEntity> listRelacion = null;
		try {
			Lifecycle.beginCall();
			gestionRelaciondEjb = (IGestionRelaciondEjb) Seam.componentForName("gestionRelaciondEjb").newInstance();
			listRelacion = gestionRelaciondEjb.consultarRelacionesDelUsuario(userId, idTipoRelacion);
			Lifecycle.endCall();
		} catch (Exception e) {
			logger.error("Error: GestionRelaciondEjb.consultarRelacionesDelUsuario "+e.getMessage(), e);
		}
		return listRelacion;
	}

	public List<RelacionEntity> consultarRelacionesDelPrestador(String idSucursalPrest, int idTipoRelacion) {
		List<RelacionEntity> listRelacion = null;
		try {
			Lifecycle.beginCall();
			gestionRelaciondEjb = (IGestionRelaciondEjb) Seam.componentForName("gestionRelaciondEjb").newInstance();
			listRelacion = gestionRelaciondEjb.consultarRelacionesDelPrestador(idSucursalPrest, idTipoRelacion);
			Lifecycle.endCall();
		} catch (Exception e) {
			logger.error("Error: GestionRelaciondEjb.consultarRelacionesDelPrestador "+e.getMessage(), e);
		}
		return listRelacion;
	}

	public List<PrestadorDTO> consultarPrestadoresPorDominioNombre(String dominio, String nombrePrestador) {
		List<PrestadorDTO> listPrestador = null;
		try {
			Lifecycle.beginCall();
			gestionPrestadordEjb = (IGestionPrestadordEjb) Seam.componentForName("gestionPrestadordEjb").newInstance();
			listPrestador = gestionPrestadordEjb.consultarPrestadoresPorDominioNombre(dominio, nombrePrestador);
			Lifecycle.endCall();
		} catch (Exception e) {
			logger.error("Error: gestionPrestadordEjb.consultarPrestadoresPorDominioNombre "+e.getMessage(), e);
		}
		return listPrestador;
	}

	public PrestadorDTO consultarPrestadoresPorDominioNumIden(String dominio, String numidentificacion) {
		PrestadorDTO prestador = null;
		try {
			Lifecycle.beginCall();
			gestionPrestadordEjb = (IGestionPrestadordEjb) Seam.componentForName("gestionPrestadordEjb").newInstance();
			prestador = gestionPrestadordEjb.consultarPrestadoresPorDominioNumId(dominio, numidentificacion);
			Lifecycle.endCall();
		} catch (Exception e) {
			logger.error("Error: gestionPrestadordEjb.consultarPrestadoresPorDominioNombre "+e.getMessage(), e);
		}
		return prestador;
	}

}

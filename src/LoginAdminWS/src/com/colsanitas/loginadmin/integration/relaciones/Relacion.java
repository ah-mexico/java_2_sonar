package com.colsanitas.loginadmin.integration.relaciones;

import java.util.ArrayList;
import java.util.List;


import javax.jws.WebService;

import org.jboss.seam.Seam;
import org.jboss.seam.annotations.In;
import org.jboss.seam.contexts.Lifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.loginadmin.administracion.entity.RelacionEntity;
import com.colsanitas.loginadmin.dto.DatosPrestadorDTO;
import com.colsanitas.loginadmin.dto.PrestadorDTO;
import com.colsanitas.loginadmin.dto.SucursalesByPrestadorDTO;
import com.colsanitas.loginadmin.exception.LoginAdminException;
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

	
	/* (non-Javadoc)
	 * @see com.colsanitas.loginadmin.integration.relaciones.IRelacion#consultaDatosPrestadorByID(com.colsanitas.loginadmin.dto.DatosPrestadorDTO)
	 */
	public DatosPrestadorDTO consultaDatosPrestadorByID(DatosPrestadorDTO dtoIn) throws LoginAdminException {
		DatosPrestadorDTO datosPrestador= new DatosPrestadorDTO();
		try {
		 		Lifecycle.beginCall();
		 		if(dtoIn != null &&  dtoIn.getTipoID() != null && dtoIn.getNumeroID() != null){
				gestionPrestadordEjb = (IGestionPrestadordEjb) Seam.componentForName("gestionPrestadordEjb").newInstance();
				datosPrestador = gestionPrestadordEjb.consultaDatosPrestadorByID(dtoIn);
				Lifecycle.endCall();
			}
		 else {
		  throw new LoginAdminException("Datos de Entrada del WS LoginAdminEJBWS.consultaDatosPrestadorByID NULOS");
		 }
			}
		 catch (LoginAdminException e) {
		     logger.error("Error al ejecutar servicio LoginAdminEJBWS.consultaDatosPrestadorByID"+ e.getMessage(), e);
			 throw e;
    	}
		catch (Exception e) {
		     logger.error("Error al ejecutar servicio LoginAdminEJBWS.consultaDatosPrestadorByID"+ e.getMessage(), e);
		 throw new LoginAdminException(e.getMessage());
		}
	 return datosPrestador;	
    }


	/* (non-Javadoc)
	 * @see com.colsanitas.loginadmin.integration.relaciones.IRelacion#consultaSucursalesPrestadorByID(com.colsanitas.loginadmin.dto.SucursalesByPrestadorDTO)
	 */
	public List<SucursalesByPrestadorDTO> consultaSucursalesPrestadorByID(SucursalesByPrestadorDTO dtoIn)throws LoginAdminException  {
		List<SucursalesByPrestadorDTO> retorno = new ArrayList<SucursalesByPrestadorDTO>();
		try {
			
			Lifecycle.beginCall();
			if(dtoIn != null &&  dtoIn.getTipoIDPrestador() != null && dtoIn.getNumeroIDPrestador() != null){
			gestionPrestadordEjb = (IGestionPrestadordEjb) Seam.componentForName("gestionPrestadordEjb").newInstance();
			retorno = gestionPrestadordEjb.consultaSucursalesPrestadorByID(dtoIn);
			Lifecycle.endCall();
		} else {
		  throw new LoginAdminException("Datos de Entrada del WS LoginAdminEJBWS.SucursalesByPrestadorDTO No pueden ser NUOS");
	   }
		}
		  catch (LoginAdminException e) {
			logger.error("Error al ejecutar el servicio consultaSucursalesPrestadorByID "+e.getMessage(), e);
		 throw e;
		}
		catch (Exception e) {
		  logger.error("Error al ejecutar servicio LoginAdminEJBWS.consultaSucursalesPrestador", e);
	    throw new LoginAdminException(e.getMessage());
		}
	return retorno;
   }

 }

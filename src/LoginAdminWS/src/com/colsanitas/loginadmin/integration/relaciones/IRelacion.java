package com.colsanitas.loginadmin.integration.relaciones;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.colsanitas.loginadmin.administracion.entity.RelacionEntity;
import com.colsanitas.loginadmin.dto.DatosPrestadorDTO;
import com.colsanitas.loginadmin.dto.PrestadorDTO;
import com.colsanitas.loginadmin.dto.SucursalesByPrestadorDTO;
import com.colsanitas.loginadmin.exception.LoginAdminException;

/***
 * 
 * Definición del servicio que se le va a ofrecer a las aplicaciones en los 
 * concerniente a las relaciones entre el prestador y el usuario.
 * 
 * @author armarquez
 * 
 */
@WebService
public interface IRelacion {
	/**
	 * Permite obtener todas las relaciones de un tipo especifico 
	 * que tiene  un usuario asociadas a los prestadores.
	 * 
	 * @param login identicador del usuario. 
	 * @param idTipoRelacion identificador del tipo de relación.
	 * @return todas las relaciones que tiene el usuario con los prestadores.
	 */
	@WebMethod
	public List<RelacionEntity> consultarRelacionesDelUsuario(@WebParam(name = "userId") String login, @WebParam(name = "idTipoRelacion") int idTipoRelacion);

	/**
	 * Permite obtener todas las relaciones de un tipo especifico que tiene  
	 * un prestador con los usuarios.
	 * 
	 * @param idPrestador identicador único del prestador en el sistema 
	 * @param idTipoRelacion identificador del tipo de relación.
	 * @return todas las relaciones que tiene el prestador con los usuarios.
	 */
	@WebMethod
	public List<RelacionEntity> consultarRelacionesDelPrestador(@WebParam(name = "idSucursalPrest") String idPrestador, @WebParam(name = "idTipoRelacion") int idTipoRelacion);
	/**
	 * Metodo encargado de consultar los prestadores por dominio y numero identificacion
	 * @param dominio
	 * @param numIden
	 * @return
	 * @throws LoginAdminException
	 */
	@WebMethod
	public PrestadorDTO consultarPrestadoresPorDominioNumIden (String dominio, String numIden);
	
	/**
	 * Metodo encargao de consultar los prestadores por dominio y nombre del prestador
	 * @param dominio
	 * @param nombre
	 * @return
	 * @throws LoginAdminException
	 */
	@WebMethod
	public List<PrestadorDTO> consultarPrestadoresPorDominioNombre (String dominio, String nombre);

	
	/** Metodo encargado de consultar si el prestador se encuentra activo en Beyond Health
	 * @param dtoIn
	 * @return
	 */
	@WebMethod
	@WebResult(name = "return")
	public DatosPrestadorDTO consultaDatosPrestadorByID(@WebParam(name = "dtoIn") DatosPrestadorDTO dtoIn) throws LoginAdminException;
		    	
	
	/** Metodo encargado de consultar las sucursales asociadas de un prestador activo en Beyond Health
	 * @param dtoIn
	 * @return
	 */
	@WebMethod
	@WebResult(name = "return")
	public List<SucursalesByPrestadorDTO> consultaSucursalesPrestadorByID(@WebParam(name="dtoIn")SucursalesByPrestadorDTO dtoIn)throws LoginAdminException; 
	
	
	
}

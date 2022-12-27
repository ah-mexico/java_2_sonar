package com.colsanitas.loginadmin.prestadores.business;

import java.util.List;

import javax.ejb.Local;
import com.colsanitas.loginadmin.dto.DatosPrestadorDTO;
import com.colsanitas.loginadmin.dto.PrestadorDTO;
import com.colsanitas.loginadmin.dto.SucursalesByPrestadorDTO;
import com.colsanitas.loginadmin.exception.LoginAdminException;

/**
 * Define  los metodos necesarios para gestion de prestadores.
 * 
 * @author yaaperez
 *
 */
@Local
public interface IGestionPrestadordEjb {
	/**
	 * Recibe un listado de cdPersons y desactiva a todos sus usuarios relacionados.
	 * @param idSucursalPrestadores
	 */
	public void desactivarRelacionadosPrestadores( List<Long> idSucursalPrestadores );
	
	/**
	 * Recibe una sucursal (cdpresta)  y desactiva a todos los usuarios asociados.
	 * @param idSucursalPrestadores
	 */
	public void desactivarSucursalRetirada(String idSucursal);
	
	/**
	 * Creado por Softmanagement
	 * Metodo encargado de consultar los prestadores de un dominio por numero de identificacion
	 * @param dominio
	 * @param numide
	 * @return
	 */
	public PrestadorDTO consultarPrestadoresPorDominioNumId(String dominio, String numide);
	

	/**
	 * Creado por Softmanagement
	 * Metodo encargado de consultar los prestadores de un dominio por nombre del prestador
	 * @param dominio
	 * @param numide
	 * @return
	 */
	public List <PrestadorDTO> consultarPrestadoresPorDominioNombre(String dominio, String nombrePrestador);
	
	
	/**Metodo encargado de consultar los prestadores activos en Beyond Health
	 * @param dtoIn
	 * @return
	 */
	public DatosPrestadorDTO consultaDatosPrestadorByID(DatosPrestadorDTO dtoIn) throws LoginAdminException;
	
	/** Metodo encargado de consultar las sucursales asociadas a un prestador activo en Beyond Health
	 * @param dtoIn
	 * @return
	 */
	public List<SucursalesByPrestadorDTO> consultaSucursalesPrestadorByID(SucursalesByPrestadorDTO dtoIn) throws LoginAdminException;;

}

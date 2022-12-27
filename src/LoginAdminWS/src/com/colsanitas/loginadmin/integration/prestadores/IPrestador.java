package com.colsanitas.loginadmin.integration.prestadores;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/***
 * Definición del Servicio Web que se va a consultar de Azimut.
 * 
 * @author armarquez
 * 
 */
@WebService
public interface IPrestador {
	@WebMethod
	public void desactivarRelacionadosPrestadores(@WebParam(name = "idPrestadores") String[] idPrestadores );
	
	@WebMethod
	public void desactivarSucursalesRetiradas(@WebParam(name = "idSucursales") String[] idPrestadores );
}

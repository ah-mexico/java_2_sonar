package com.colsanitas.loginadmin.administracion.dao.interfaces;

import java.util.List;

import javax.ejb.Local;
import com.colsanitas.loginadmin.administracion.entity.PrestadorEntity;
import com.colsanitas.loginadmin.dto.DatosPrestadorDTO;
import com.colsanitas.loginadmin.dto.SucursalesByPrestadorDTO;
import com.colsanitas.loginadmin.exception.LoginAdminException;
import com.colsanitas.loginadmin.exception.NoDataFoundException;

@Local
public interface IPrestadorDao {
	public List<PrestadorEntity> consultarPrestadoresDelAdministrador(String login) throws LoginAdminException;
	public void registrarPrestador( PrestadorEntity prestadorEntity, boolean flush) throws LoginAdminException;
	public PrestadorEntity actualizarPrestador( PrestadorEntity prestadorEntity );
	public PrestadorEntity findById(Long id) throws LoginAdminException;
	public PrestadorEntity findByNumID(String numId) throws LoginAdminException;
	public PrestadorEntity consultarPrestador(Long cdperson) throws LoginAdminException;
	public PrestadorEntity consultarPrestador( PrestadorEntity prestadorEntity ) throws LoginAdminException;
	public PrestadorEntity consultPrest( String codigoSucursal ) throws LoginAdminException;
	public List<PrestadorEntity> listPrestadores(String razonSocial) throws LoginAdminException;
	public PrestadorEntity consultPrestActivo( PrestadorEntity entityPrest ) throws LoginAdminException;
	public List<PrestadorEntity> listPrestadoresActivos( PrestadorEntity entityPrest ) throws LoginAdminException;
	public int desactivarSucursal(String idSucursal); 
	public int desactivarPrestador(Long prestadorId);
	public PrestadorEntity consultarPrestadoresPorDominioNumIden (String dominio, String numIden)throws LoginAdminException;
	public List<PrestadorEntity> consultarPrestadoresPorDominioNombre (String dominio, String nombre)throws LoginAdminException;
	/** metodo que permite consultar si un prestador se encuentra activo en Beyond Health
	 * @param dtoIn
	 * @return
	 * @throws LoginAdminException
	 */
	public DatosPrestadorDTO consultaDatosPrestadorByID(DatosPrestadorDTO dtoIn) throws LoginAdminException;
	
	/** metodo que permite consultar las sucursales de un prestador activo en Beyond Health
	 * @param dtoIn
	 * @return
	 * @throws NoDataFoundException
	 * @throws LoginAdminException
	 */
	public List<SucursalesByPrestadorDTO> consultaSucursalesPrestadorByID(SucursalesByPrestadorDTO dtoIn)throws NoDataFoundException,LoginAdminException;
}

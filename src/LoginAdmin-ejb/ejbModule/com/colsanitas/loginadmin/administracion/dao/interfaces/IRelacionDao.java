package com.colsanitas.loginadmin.administracion.dao.interfaces;



import java.util.List;

import javax.ejb.Local;

import com.colsanitas.loginadmin.administracion.entity.PrestadorEntity;
import com.colsanitas.loginadmin.administracion.entity.RelacionEntity;
import com.colsanitas.loginadmin.administracion.enums.ETipoRelacion;
import com.colsanitas.loginadmin.exception.LoginAdminException;

@Local
public interface IRelacionDao {

	public void registrarRelacion(RelacionEntity relacionEntity, boolean flush) throws LoginAdminException ;

	public RelacionEntity consultarRelacionUser( String userLogin, Long idPrestador, int tipoRel ) throws LoginAdminException;
	
	public List<RelacionEntity> consultarRelacionUsuario(String userLogin, int tipoRelacion) throws LoginAdminException;
	
	public List<RelacionEntity> consultarRelacionUserDesau(String userLogin, String prestLogin) throws LoginAdminException;
	
	public List<RelacionEntity> consultarRelacionUsuario(String userLogin) throws LoginAdminException;
	
	public List<RelacionEntity> consultarRelacionUsuarioDiferentePrest(String userLogin, int tipoRelacion, Long prestadorId) throws LoginAdminException;

	public RelacionEntity actualizarRelacion(RelacionEntity relacionEntity, boolean flush) throws LoginAdminException;
	
	public RelacionEntity actualizarRelacionNewTrans(RelacionEntity relacionEntity, boolean flush) throws LoginAdminException;
	
	public void registrarRelacionNewTrans(RelacionEntity relacionEntity, boolean flush) throws LoginAdminException;
	
	/**
	 * Desactiva las relaciones de todas las sucursales pertenecientes a la lista de prestadores suministradas.
	 * Salvo las relaciones de tipo excepción.
	 * @param idPresadores  lista de ids de prestadores. 
	 * @param excepcion excepción a la regla.
	 * @return numero de relaciones desactivadas.
	 * @throws LoginAdminException
	 */
	public int desactivarRelacionesDePrestadores( List<Long> idPresadores, ETipoRelacion excepcion ) throws LoginAdminException;
	
	public List<RelacionEntity> consultarRelacionesDelUsuario(String userId, int tipoRelacion) throws LoginAdminException;
	
	public List<RelacionEntity> consultarRelacionesDelPrestador(String idSucursalPrest, int tipoRelacion) throws LoginAdminException;
	
	public List<PrestadorEntity> consultarPrestadoresAdministrados(String userLogin) throws LoginAdminException;

	/**
	 * Consulta la lista de usuarios que no tienen ceirto tipo de relación con prestadores a partir de un conjunto de usuarios pasados por parametros.
	 * @param tipoRelacion tipo de relación que no debe tener el usuario.
	 * @param usuarios lista de usuarios a verificar.
	 * @return lista de usuarios sin relaciones.
	 */
	public List<String> consultarUsuariosSinRelacion(List<String> idUsuarios, List<Integer> tipoRelacion);

	/**
	 * Consulta la lista de usuarios que no tienen ningún relación con prestadores a partir de un conjunto de usuarios pasados por parametros.
	 * @param usuarios lista de usuarios a verificar.
	 * @return lista de usuarios sin relaciones.
	 */
	public List<String> consultarUsuariosSinRelacion(List<String> idUsuarios);

	/**
	 * Consulta los ids de los usuarios que tienen alguna relación con la sucursales pertenecientes a la lista de prestadores suministradas.
	 * @param idSucursales listado de ids de sucursales.
	 * @return Listado de ids de usuarios.
	 */
	public List<String> consultarUsuariosRelacionadosConSucursales(List<Long> idPrestadores);
	
	/**
	 * Consulta los ids de los usuarios que tienen alguna relación con la sucursal suministrada.
	 * @param idSucursal  id de sucursal.
	 * @return Listado de ids de usuarios.
	 */
	public List<String> consultarUsuariosRelacionadosConSucursalRetirada(String idSucursal);
	
	/**
	 * Desactiva relaciones de usuarios con la sucursal suministrada
	 * 
	 * @param idSucursal
	 * @param excepcion
	 * @return
	 * 
	 */
	public int desactivarRelacionesDeSucursal( String idSucursal, ETipoRelacion excepcion ); 

	/**
	 * Consulta los ids de los usuarios que tienen alguna relación con el prestador suministrado.
	 * @param idPrestador  id del prestador
	 * @return Listado de ids de usuarios.
	 */
	
	public List<String> consultarUsuariosRelacionadosConPrestador(Long idPrestador);
	
	/**
	 * 
	 * Metodo encargado de consultar si un prestador tiene un administrador de prestadores asociado
	 * Modificado por Softmangement
     * Yency Serrano
     * 21/03/2013
	 */
	
	public List<RelacionEntity> consultaPrestadorTieneAdminPrestador(String nit) throws LoginAdminException ;
}

package com.colsanitas.loginadmin.administracion.business;

import java.util.List;

import javax.ejb.Local;

import com.colsanitas.loginadmin.administracion.entity.PrestadorEntity;
import com.colsanitas.loginadmin.administracion.entity.RelacionEntity;
import com.colsanitas.loginadmin.administracion.entity.UserEntity;
import com.colsanitas.loginadmin.administracion.view.UserResponseView;
import com.colsanitas.loginadmin.dto.DatosPrestadorDTO;
import com.colsanitas.loginadmin.dto.SucursalesByPrestadorDTO;
import com.colsanitas.loginadmin.exception.LoginAdminException;
import com.colsanitas.loginadmin.exception.NoDataFoundException;

/**
 * Define  los metodos necesarios para la gestion de autorizaciones del sistemas. 
 * 
 * @author yaaperez
 *
 */
@Local
public interface IAdministrarAutorizaciondEjb {
	public UserResponseView registrarRelacion( UserEntity userEntity, PrestadorEntity prestadorEntity, int tipoRelacion, boolean estado, String loginSession ) throws LoginAdminException ;
	public PrestadorEntity consultPrest( String codigoSucursal ) throws LoginAdminException ;
	public List<PrestadorEntity> listPrestadores( String razonSocial ) throws LoginAdminException ;
	public List<UserEntity> consultarUsuariosPrest(List<String> usersLogin, Long idPrestador) throws LoginAdminException ;
	public List<RelacionEntity> consultarRelacionUserDesau(String userLogin, String prestLogin) throws LoginAdminException ;
	public UserResponseView actualizarRelacion( RelacionEntity relacionEntity, int tipoRelacion,boolean estado ) throws LoginAdminException ;
	public List<RelacionEntity> consultarRelacionUsuario(String userLogin) throws LoginAdminException ;
	public List<SucursalesByPrestadorDTO> consultaSucursalesPrestador( SucursalesByPrestadorDTO sucursalesByPrestadorDTO ) throws LoginAdminException,NoDataFoundException ;
	public List<UserEntity> buscarUsuariosCriterios( UserEntity datosBusq ) throws LoginAdminException ;
	public DatosPrestadorDTO consultarPrestadorBioSanitas( DatosPrestadorDTO datosPrestadorDTO ) throws LoginAdminException ;
	public List<PrestadorEntity> consultarPrestadoresAdministrados(String userLogin) throws LoginAdminException;
	public PrestadorEntity consultPrestActivo( PrestadorEntity entityPrest ) throws LoginAdminException;
	public List<PrestadorEntity> listPrestadoresActivos( PrestadorEntity entityPrest ) throws LoginAdminException;
	public boolean consultaPrestadorTieneAdminPrestador(String nit) throws LoginAdminException ;
	
}

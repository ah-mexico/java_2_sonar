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
 * Define los metodos necesarios para la gesti�n de un usuario de sistema.
 * 
 * @author jjtrujillo
 * 
 */
@Local
public interface IGestionAdmnistradorEjb {

	public UserResponseView registrarRelacion(UserEntity userEntity, PrestadorEntity prestadorEntity, int tipoRelacion,boolean estado);
	
	public UserResponseView actualizarRelacion(RelacionEntity relacionEntity, int tipoRelacion,boolean estado);

	public RelacionEntity consultarRelacionUser( String userLogin, Long idPrestador, int tipoRel) throws Exception;
	
	public RelacionEntity consultarRelacionPres( String userLogin, Long idPerson, int tipoRel ) throws Exception;
	
	public List<RelacionEntity> consultarRelacionUsuario( String userLogin, int tipoRel) throws Exception;
	
	public List<RelacionEntity> consultarRelacionUserDesau(String userLogin, String prestLogin) throws Exception;
	
	public DatosPrestadorDTO consultarPrestadorBioSanitas( DatosPrestadorDTO datosPrestadorDTO ) throws LoginAdminException;
	
	public List<RelacionEntity> consultarRelacionUsuario(String userLogin) throws Exception;
	
	public List<SucursalesByPrestadorDTO> consultaSucursalesPrestador( SucursalesByPrestadorDTO sucursalesByPrestadorDTO ) throws LoginAdminException,NoDataFoundException;
}

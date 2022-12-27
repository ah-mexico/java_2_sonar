package com.colsanitas.loginadmin.prestadores.business;

import static org.jboss.seam.ScopeType.STATELESS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


import javax.ejb.Local;
import javax.ejb.Stateless;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.loginadmin.administracion.dao.interfaces.IPrestadorDao;
import com.colsanitas.loginadmin.administracion.dao.interfaces.IRelacionDao;
import com.colsanitas.loginadmin.administracion.dao.interfaces.IUserRolDao;
import com.colsanitas.loginadmin.administracion.dao.interfaces.IUsuarioDao;
import com.colsanitas.loginadmin.administracion.entity.PrestadorEntity;
import com.colsanitas.loginadmin.administracion.enums.ETipoRelacion;
import com.colsanitas.loginadmin.dto.DatosPrestadorDTO;
import com.colsanitas.loginadmin.dto.PrestadorDTO;
import com.colsanitas.loginadmin.dto.SucursalesByPrestadorDTO;
import com.colsanitas.loginadmin.exception.LoginAdminException;


@Scope(STATELESS)
@Stateless
@Name("gestionPrestadordEjb")
@Local(IGestionPrestadordEjb.class)
public class GestionPrestadordEjb implements IGestionPrestadordEjb, Serializable {	
	private static final long serialVersionUID = -7982002047904449625L;
	
	private static Logger logger = LoggerFactory.getLogger(GestionPrestadordEjb.class);

	@In(create = true)
	private IRelacionDao relacionDao;
	
	@In(create = true)
	private IUserRolDao userRolDao;
	
	@In(create = true)
	private IUsuarioDao ldapUsuarioDao;
	
	@In(create = true)
	private IPrestadorDao prestadorDao;
	
	
	
	public void desactivarRelacionadosPrestadores( List<Long> idsPrestadores ) {
		logger.info("Se llama el método desactivarRelacionadosPrestadores con los valores: " + Arrays.toString(idsPrestadores.toArray()));
		
		int actualizados = 0;
	
		try {
			if( idsPrestadores != null && idsPrestadores.size() > 0 ) {
				if( idsPrestadores != null && !idsPrestadores.isEmpty() ){
					
					Iterator<Long> it = idsPrestadores.iterator();
					while (it.hasNext()){
						Long idPrestador = it.next();
						prestadorDao.desactivarPrestador(idPrestador);
						
						List<String> usuariosPrestador = relacionDao.consultarUsuariosRelacionadosConPrestador(idPrestador);
						if (usuariosPrestador.size() > 0){
							userRolDao.eliminarUserRolPrestador(idPrestador, usuariosPrestador);
						}
					}
					
					//Consulta los usuarios que actualmente tienen	una de las sucursales asociada.
					List<String> idUsuarios = relacionDao.consultarUsuariosRelacionadosConSucursales(idsPrestadores);
					
					actualizados = relacionDao.desactivarRelacionesDePrestadores(idsPrestadores, ETipoRelacion.CREADO_POR);
					if ( actualizados > 0) {
						
						if (idUsuarios.size() > 0){
							
							//Los que quedaron sin relaciones con prestadores se deben desactivar.
							idUsuarios = relacionDao.consultarUsuariosSinRelacion(idUsuarios);
							
							//Para que no puedan autenticarse.
							ldapUsuarioDao.desactivarUsuarioByIds(new HashSet<String>(idUsuarios));
							
						} else {
							logger.info("Se desactivaron la relaciones pero no Usuarios");
						}
					} else {
						logger.info("No se actualizo ningun registro");
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error: GestionPrestadordEjb.desactivarRelacionadosPrestadores  "+e.getMessage(), e);
		}
	}
	
	
	
	
		
	/**
	 * Metodo que recibe una Sucursal que ha salido de operacion 
	 * para desactivar la sucursal  y a los usuarios asociados.
	 * 
	 */
	
	public void desactivarSucursalRetirada(String idSucursal){ 
	//throws LoginAdminException{
		//try{
		int actualizados = 0;
		//Desactivar Sucursal
		int desactivarSucursal = prestadorDao.desactivarSucursal(idSucursal);
		
		if (desactivarSucursal >= 0){
		
			logger.info("desactivando sucursal retirada "+idSucursal);	
			//Consulta listado de usuarios relacionados con la Sucursal.	
			List<String> idUsuarios = relacionDao.consultarUsuariosRelacionadosConSucursalRetirada(idSucursal);
		
			//Desactivar Relaciones de Usuarios con la Sucursal
			actualizados = relacionDao.desactivarRelacionesDeSucursal(idSucursal, ETipoRelacion.CREADO_POR);
		
			if ( actualizados > 0) {
			
				if (idUsuarios.size() > 0){
					userRolDao.eliminarUserRolSucursal(idSucursal, idUsuarios);
					//Los que quedaron sin relaciones con prestadores se deben desactivar.
					idUsuarios = relacionDao.consultarUsuariosSinRelacion(idUsuarios);
				
					//	Para que no puedan autenticarse.
					ldapUsuarioDao.desactivarUsuarioByIds(new HashSet<String>(idUsuarios));
				
				} else {
					logger.info("Se desactivaron la relaciones pero no Usuarios");
				}
			} else {
			logger.info("No se actualizo ninguna relacion de usuarios con sucursal");
			}
		}else{
			logger.info("No se actualizo ninguna sucursal con el id suministrado ");
		}
		
	} 
	/**
	 * Creado por Softmanagement
	 * Metodo encargado de consultar los prestadores de un dominio por nombre del prestador
	 * @param dominio
	 * @param numide
	 * @return
	 */
	public List <PrestadorDTO> consultarPrestadoresPorDominioNombre(String dominio, String nombrePrestador){
		List <PrestadorEntity> prestadores = new ArrayList<PrestadorEntity>();
		List <PrestadorDTO> prestadoresDTO= new ArrayList<PrestadorDTO>();
		try {
			prestadores = prestadorDao.consultarPrestadoresPorDominioNombre(dominio, nombrePrestador);
		
			if(prestadores!=null && !prestadores.isEmpty()){
				for(PrestadorEntity p:prestadores){
					PrestadorDTO dto = new PrestadorDTO();
					dto.setEstado(p.isEstado());
					dto.setPrestadorEntity(p);
					
					prestadoresDTO.add(dto);
					
				}
			}
		
		} catch (LoginAdminException e) {
			logger.info("Fallo consulta de prestadores por nombre ");
		}
		return prestadoresDTO;
	}	
	/**
	 * Creado por Softmanagement
	 * Metodo encargado de consultar los prestadores de un dominio por nombre del prestador
	 * @param dominio
	 * @param numide
	 * @return
	 */
	public PrestadorDTO consultarPrestadoresPorDominioNumId(String dominio, String numide){
		PrestadorEntity prestadores = new PrestadorEntity();
		PrestadorDTO dto = new PrestadorDTO();
		
		try {
			prestadores = prestadorDao.consultarPrestadoresPorDominioNumIden(dominio, numide);
			dto.setEstado(prestadores.isEstado());
			dto.setPrestadorEntity(prestadores);			
			
		} catch (LoginAdminException e) {
			logger.info("Fallo consulta de prestadores por nombre ");
		}
		return dto;
	}


	/* (non-Javadoc)
	 * @see com.colsanitas.loginadmin.prestadores.business.IGestionPrestadordEjb#consultaDatosPrestadorByID(com.colsanitas.loginadmin.dto.DatosPrestadorDTO)
	 */
	public DatosPrestadorDTO consultaDatosPrestadorByID(DatosPrestadorDTO dtoIn) throws LoginAdminException {
		logger.info("Se invoca el metodo WSRelacion.consultaDatosPrestadorByID: " + dtoIn.getTipoID()+ " - " + dtoIn.getNumeroID());
		DatosPrestadorDTO datosPrestador = new DatosPrestadorDTO();
		
		try {
			datosPrestador = prestadorDao.consultaDatosPrestadorByID(dtoIn);
			
		} catch (Exception e) {
			logger.error("WSRelacion.consultaDatosPrestadorByID:Fallo consulta de prestadores por ID");
			throw new LoginAdminException(e.getMessage());
		}
		return datosPrestador;
	}


	/* (non-Javadoc)
	 * @see com.colsanitas.loginadmin.prestadores.business.IGestionPrestadordEjb#consultaSucursalesPrestadorByID(com.colsanitas.loginadmin.dto.SucursalesByPrestadorDTO)
	 */
	public List<SucursalesByPrestadorDTO> consultaSucursalesPrestadorByID(SucursalesByPrestadorDTO dtoIn) throws LoginAdminException {
		List<SucursalesByPrestadorDTO> retorno = new ArrayList<SucursalesByPrestadorDTO>();
		try {
			retorno = prestadorDao.consultaSucursalesPrestadorByID(dtoIn);
			
		} catch (Exception e) {
			logger.info("WSRelacion.consultaSucursalesPrestadorByID:Fallo consulta de sucursales por prestadores");
			throw new LoginAdminException(e.getMessage());
		}
		return retorno;
	}	
	
 }

package com.colsanitas.loginadmin.administracion.business;

import java.util.List;

import javax.ejb.Stateless;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import com.colsanitas.loginadmin.administracion.dao.interfaces.IPrestadorDao;
import com.colsanitas.loginadmin.administracion.dao.interfaces.IRelacionDao;
import com.colsanitas.loginadmin.administracion.dao.interfaces.IUsuarioDao;
import com.colsanitas.loginadmin.administracion.entity.PrestadorEntity;
import com.colsanitas.loginadmin.administracion.entity.RelacionEntity;
import com.colsanitas.loginadmin.administracion.utils.EResponse;
import com.colsanitas.loginadmin.administracion.view.UserResponseView;

@Stateless
@Name("registroDelegadoEjb")
public class RegistroDelegadoEjb implements IRegistroDelegadoEjb {

	@In(create = true)
	private IPrestadorDao prestadorDao;

	@In(create = true)
	private IRelacionDao relacionDao;

	@In(create = true)
	private IUsuarioDao usuarioDao;

	public String consultarPrestador() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<PrestadorEntity> consultarPrestadoresDelAdministrador(
			String userLogin) throws Exception {
		try {
			return prestadorDao.consultarPrestadoresDelAdministrador(userLogin);
		} catch (Exception e) {
			throw e;
		}
	}

	public UserResponseView registrarRelacionDelegado(
			RelacionEntity relacionEntity) {
		UserResponseView userResponseDTO = new UserResponseView();
		try {
			if (usuarioDao.findById(relacionEntity.getUsuario().getUserId()) == null)
				usuarioDao.registrarUsuarioEntity(relacionEntity.getUsuario(), true);// Registra usuario si no existe
											// pruebas

			relacionDao.registrarRelacion(relacionEntity, false); // registra la
															// relación
			userResponseDTO.setMessage(EResponse.TRANSACTION_OK.getMessage());
		} catch (Exception e) {
			userResponseDTO
					.setMessage(EResponse.TRANSACTION_ERROR.getMessage());
			e.printStackTrace();
		}

		return userResponseDTO;

	}

}

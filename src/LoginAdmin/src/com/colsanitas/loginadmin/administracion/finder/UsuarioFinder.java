package com.colsanitas.loginadmin.administracion.finder;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.colsanitas.loginadmin.administracion.business.IGestionUsuariosEjb;
import com.colsanitas.loginadmin.administracion.entity.UserEntity;
import com.osi.his.sistema.util.finder.AbstractFinder;

@Name("usuarioFinder")
@Scope(ScopeType.CONVERSATION)
public class UsuarioFinder extends AbstractFinder<UserEntity>implements Serializable {

	private static final long serialVersionUID = 1617432402997941968L;
	
	private static Logger logger = LoggerFactory.getLogger(UsuarioFinder.class);
	
	@In(create = true)
	private IGestionUsuariosEjb gestionUsuariosEjb;

	@Override
	public void findByCode(UserEntity entity) {
		UserEntity userEntityAux = null;
		try {
			userEntityAux = this.gestionUsuariosEjb.consultUser(entity.getUserId());
			if( userEntityAux != null ){
				entity.setUserId(userEntityAux.getUserId());
				entity.setUserLogin(userEntityAux.getUserId());
				entity.setUserName(userEntityAux.getUserName());
				entity.setUserLastName(userEntityAux.getUserLastName());
				entity.setUserMail(userEntityAux.getUserMail());
				entity.setBlockedUser(userEntityAux.getBlockedUser());
			}
		} catch (Exception e) {
			logger.error("Error: UsuarioFinder.findByCode  "+e.getMessage(), e);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<UserEntity> findByText(String userName) {
		List<UserEntity> listaUsuarios = null;
		try {
			listaUsuarios = gestionUsuariosEjb.listUser( userName );
		} catch (Exception e) {
			logger.error("Error: UsuarioFinder.findByText  "+e.getMessage(), e);
		}
		return listaUsuarios;
	}

	@Override
	public List<UserEntity> normalizar(List<UserEntity> lista) {
		return null;
	}
	
	@Override
	public String getFetchLabel(){
	    return "nombreCompleto";
	}
	
	@Override
	public String getFetchValue(){
	    return "userId";
	}

}
